package com.example.testjpa.controller;


import com.example.testjpa.dao.MeterDao;
import com.example.testjpa.dao.PersonDao;
import com.example.testjpa.dto.PeronWithMeterOnly;
import com.example.testjpa.dto.PersonDto;
import com.example.testjpa.form.PersonFormPayload;
import com.example.testjpa.model.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.query.criteria.internal.expression.function.LowerFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

/**
 * Created by cloud on 2019/9/28.
 */
@RequestMapping("/p")
@RestController
public class PersonController {
    @Autowired
    PersonDao personDao;
    @Resource
    EntityManager entityManager;
    @Resource
    MeterDao meterDao;

    @RequestMapping("/add")
    @ResponseBody
    public Object add() {

        Person person = new Person();
        person.setDeleteMark(0);
        person.setAge(18);
        person.setName("周");

        List<Meter> meterList = new ArrayList<>();
        Meter meter = new Meter();
        meter.setCode("1");
        meter.setType("D");
        meter.setPerson(person);
        meterList.add(meter);

        List<Pet> pets = new ArrayList<>();
        Pet pet = new Pet();
        pet.setName("狗狗");
        pets.add(pet);

        person.setPets(pets);
        person.setMeters(meterList);

        personDao.save(person);
        return "ok";
    }

    @RequestMapping("/delete")
    public Object delete(Integer id) {
        Person person = personDao.findById(id).orElse(null);
        if (person != null) {
            personDao.delete(person);
        }
        return "ok";
    }

    @RequestMapping("/update")
    public Object update(Integer id) {
        Person person = personDao.findById(id).get();
        person.setName("qq");
        personDao.saveAndFlush(person);
        person.setName("tt");
        personDao.save(person);

        return "ok";
    }

    @RequestMapping("/{id}")
    public Object showUserForm(@PathVariable("id") Person person) {
        //@EnableSpringDataWebSupport 支持 自动根据传入Id 查询person
        // https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#core.web.basic
        return person;
    }


    @RequestMapping("/list")
    public Object list() {
        //Example查询 包括匹配模式
        //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#query-by-example.matchers
        Person searchPerson = new Person();
        searchPerson.setName("jack");
        searchPerson.setIdCard("510200X");
        Example<Person> personExample = Example.of(searchPerson,
                ExampleMatcher.matching().withMatcher("name", contains().ignoreCase())
        );
        return personDao.findAll(personExample);
    }


    @RequestMapping("/list2")
    public Object list3(@QuerydslPredicate(root = Person.class) Predicate predicate) {
        //QDSL WEB支持
        //https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#core.web.type-safe
        //QPerson p = QPerson.person;
        //personDao.findAll(p.id.eq(1));
        return personDao.findAll(predicate);
    }


    @RequestMapping("/list3")
    public Object list4() {
        //动态预测映射
        //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projection.dynamic
        Collection<PersonDto> ps = personDao.findAllDtoedBy();
        return ps;
    }

    @RequestMapping("/list4")
    public Object list3() {
        //动态预测映射
        //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projection.dynamic
        Sort sort = Sort.by(Sort.Order.desc("meters.id"));
        Collection<PeronWithMeterOnly> ps = personDao.findAllPeronWithMeterOnlyOrderBy(sort);
        return ps;
    }

    @RequestMapping("/list5")
    public Object list5() {
        // HQL
        // https://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html#hql
        List<Person> p = entityManager.createQuery(" select DISTINCT p from Person p left join fetch p.pets ").getResultList();
        return p;
    }

    @RequestMapping("/list6")
    public Object list6() {
        // CriteriaQuery
        // https://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html#criteria
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Person> query = builder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);
        query.select(root).where(
                builder.or(
                        builder.equal(root.get("id"), 12),
                        builder.equal(root.get("id"), 18),
                        builder.equal(builder.function(LowerFunction.NAME, String.class, root.get("name")), "周蜀S".toLowerCase())
                )
        );

        List a = entityManager.createQuery(query).getResultList().stream().map(person -> {
            person.setPets(null); //排除关联对象 避免在json序列化是 触发get导致进行懒加载
            person.setMeters(null);
            return person;
        }).collect(Collectors.toList());

        /*CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        countQuery.select(builder.count(countQuery.from(Person.class)));*/

        return a;
    }

    @RequestMapping("/list7")
    public Object list7() {
        // CriteriaQuery  查询部分字段
        // https://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html#criteria
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root<Person> root = query.from(Person.class);
        query.select(builder.array(root.get("id"), root.get("name")));

        return entityManager.createQuery(query).getResultList();
    }

    @RequestMapping("/list8")
    public Object list8() {
        // CriteriaQuery 通过构造器 映射DTO对象
        // https://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html#criteria
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PersonDto> query = builder.createQuery(PersonDto.class);
        Root<Person> root = query.from(Person.class);
        query.select(builder.construct(PersonDto.class, root.get("name"), root.get("age")));
        return entityManager.createQuery(query).getResultList();
    }

    @RequestMapping("/list9")
    public Object list9() {
        // 通过元数据 进行类型安全查询 避免魔法值的影响
        // 生成元数据类型 需引入hibernate-jpamodelgen,通过mvn编译就能生成实体类对应的元数据类
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PersonDto> query = builder.createQuery(PersonDto.class);
        Root<Person> root = query.from(Person.class);
        query.select(builder.construct(PersonDto.class, root.get(Person_.NAME), root.get(Person_.AGE)));
        query.where(builder.greaterThan(root.get(Person_.age), 18));
        return entityManager.createQuery(query).getResultList();
    }

    @RequestMapping("/list10")
    public Object list10() {
        //QueryDsl查询 一般查询
        QPerson qPerson = QPerson.person;
        final Iterable<Person> all = personDao.findAll(
                qPerson.id.isNotNull()
        );
        return all;
    }

    @RequestMapping("/list11")
    public Object list11() {
        //QueryDsl查询 一般查询按多方条件过滤
        QPerson qPerson = QPerson.person;
        final Iterable<Person> all = personDao.findAll(
                qPerson.meters.any().id.eq(1)
        );
        return all;
    }

    @RequestMapping("/list12")
    public Object list12() {
        //基于jpaQuery实现更复杂的查询
        JPAQuery query = new JPAQuery(entityManager);
        QPerson qPerson = QPerson.person;
        final JPAQueryBase from = query.select(qPerson.id)
                .from(qPerson);
        return from.fetch();
    }

    @RequestMapping("/list13")
    public Object list13() {
        //基于jpaQuery实现更复杂的查询
        JPAQuery<Person> query = new JPAQuery(entityManager);
        QPerson qPerson = QPerson.person;
        QMeter qmeter = QMeter.meter;

        final JPAQuery from = query.from(qPerson)
                .leftJoin(qPerson.meters, qmeter)
                .groupBy(qPerson.id)
                .orderBy(qmeter.count().desc()).limit(1);

        return from.fetch();
    }

    @RequestMapping("/list14")
    public Object list14() {
        //基于jpaQuery实现更复杂的查询
        JPAQuery<Person> query = new JPAQuery(entityManager);
        QPerson qPerson = QPerson.person;
        QMeter qmeter = QMeter.meter;
        QPerson qPersonBase = QPerson.person;
        //JPA规则子查询内无法使用limit limit1不会起效 会报错
        //而且JPA 也不允许 select xx from (sub_query)这写法  需要用原生SQL支持才行
        final JPQLQuery<Person> from = query.select(qPersonBase).from(qPersonBase).where(
                qPersonBase.meters.size().eq(
                        JPAExpressions.select(qmeter.id.count().intValue()).from(qPerson)
                                .leftJoin(qPerson.meters, qmeter)
                                .groupBy(qPerson.id)
                                .orderBy(qmeter.count().desc()).limit(1)
                )
        );
        return from.fetch();
    }


    @RequestMapping("/procedure")
    public Object callPlus1InOut() {
        //调用存储过程
        Map a = personDao.plus1(1);
        return a;
    }


    @RequestMapping("/param")
    public Object test(@RequestParam MultiValueMap<String, String> parameters) {
        //使用MultiValueMap接受请求参数
        //e.: http://127.0.0.1:8080/p/param?id=1&id=2&id=3---->return 1,2,3
        return Optional.ofNullable(parameters.get("id")).orElse(Arrays.asList("empty")).stream().reduce((s, s2) -> s + "," + s2);
    }

    @RequestMapping("/form")
    public Object test(@RequestBody @Valid PersonFormPayload personFormPayload) {
        //使用ProjectedPayload接受请求参数 需要引入json-path包
        //使用@Valid或@Validated进行参数校验，@Validated多了一个group分组验证功能
        //
        // e.:  {"name": "张小花", "meters": [{"code":"N1","type":"远传"},{"code":"N2","type":"远传"}]}


        String meters = personFormPayload.getMeters().stream().map(m -> {
            return "{code:" + m.getCode() + ",meterType:" + m.getMeterType() + "}";
        }).reduce((s, s2) -> s + "," + s2).get();


        return String.format("Received name: %s, metercodes: %s,meters: [%s]", personFormPayload.getName(), personFormPayload.getMeterCodes(), meters);
    }


}

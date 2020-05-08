package com.example.testjpa.dao;


import com.example.testjpa.dto.PeronWithMeterOnly;
import com.example.testjpa.dto.PersonDto;
import com.example.testjpa.model.Person;
import com.example.testjpa.model.QPerson;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by cloud on 2019/9/28.
 */
public interface PersonDao extends JpaRepository<Person, Integer>, QuerydslPredicateExecutor<Person>, QuerydslBinderCustomizer<QPerson>,JpaSpecificationExecutor {


    Collection<PersonDto> findAllDtoedBy();

    Collection<PeronWithMeterOnly> findAllPeronWithMeterOnlyOrderBy(Sort sort);

    Stream<Person> findPeopleByAge(Integer age);

    @Procedure(name = "plus1")
    Map<String, Optional>  plus1(@Param("arg") Integer arg);

    @Override
    default void customize(QuerydslBindings bindings, QPerson person) {
        //自定义  @QuerydslPredicate 查询绑定
        bindings.bind(person.name).first((path, value) -> path.contains(value));
        bindings.bind(String.class)
                .first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }



}

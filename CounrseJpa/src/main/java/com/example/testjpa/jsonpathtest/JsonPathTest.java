package com.example.testjpa.jsonpathtest;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by cloud on 2020/5/8.
 */
public class JsonPathTest {

    public static void main(String[] args) {
        // 修改全局配置 禁止在运行过程中修改全局配置 建议在启动类中一次行配置
        Configuration.setDefaults(new Configuration.Defaults() {
            private final JsonProvider jsonProvider = new JacksonJsonProvider();
            private final MappingProvider mappingProvider = new JacksonMappingProvider();

            @Override
            public JsonProvider jsonProvider() {
                return jsonProvider;
            }

            @Override
            public MappingProvider mappingProvider() {
                return mappingProvider;
            }

            @Override
            public Set<Option> options() {
                return EnumSet.noneOf(Option.class);
            }
        });

        // 自定义当前JsonPath配置可使用:JsonPath.using().parse().read()
        String json = "{\"name\": \"张小花\", \"meters\": [{\"code\":\"N1\",\"type\":\"远传\"},{\"code\":\"N2\",\"type\":\"远传\"}]}";
        Object read = JsonPath.read(json, "$.name");
        System.out.println(read);
    }

}

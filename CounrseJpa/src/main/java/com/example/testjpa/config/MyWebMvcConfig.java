package com.example.testjpa.config;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

// 发现替换后序列化代理对象有问题 还是用默认配置
// https://www.oschina.net/question/1172115_2244016?sort=time
/*@Configuration*/
public class MyWebMvcConfig implements WebMvcConfigurer {


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        //要删除原来默认的json解析
        for (int i = converters.size() - 1; i >= 0; i--) {
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {


                converters.remove(i);
            }
        }

        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();

        // 解决乱码的问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);

        //自定义配置...
        FastJsonConfig config = new FastJsonConfig();

        config.setSerializerFeatures(SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteEnumUsingToString,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.DisableCircularReferenceDetect);
        config.setSerializeFilters(new PropertyPreFilter() {
            @Override
            public boolean apply(JSONSerializer serializer, Object object, String name) {
                if (name.equals("decoratedClass")) {
                    return false;
                }

                return true;
            }
        });


        fastJsonHttpMessageConverter.setFastJsonConfig(config);

        converters.add(fastJsonHttpMessageConverter);
    }


}

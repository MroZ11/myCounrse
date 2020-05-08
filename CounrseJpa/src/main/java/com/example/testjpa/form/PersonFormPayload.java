package com.example.testjpa.form;

import org.springframework.data.web.JsonPath;
import org.springframework.data.web.ProjectedPayload;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 可支持嵌套
 * 可用 @Valid 进行参数校验 和实体类使用方法一致
 * 查看@JSONPATH语法 https://blog.csdn.net/lwg_1540652358/article/details/84111339
 */
@ProjectedPayload
public interface PersonFormPayload {

    @JsonPath({"$.name"})
    @Size(max = 2)
    String getName();

    @JsonPath({"$.meters..code"})
    List<String> getMeterCodes();

}

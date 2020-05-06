package test;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

/**
 * Created by cloud on 2019/03/19.
 */
@Data // 相当于 get + set + tostring + equals + hash +全参构造
@Log // 日志
@Builder //生成builder模式 不支持无参方法
public class Bird {

    private String name;

    private int age;

    private boolean male;

    public void eat() {
        System.out.println(this.name + " eating");
        log.info("do eat");
    }

    public void sing(@NonNull String word) { // NonNul参数验证  官方不建议这么用 建议用JSR305实践
        System.out.println(this.name + " singing ~" + word);
    }



    public void flyHign(int height) {
        Assert.isTrue(height>=5,"must 5 ");

        System.out.println(this.name + " fly " +height+"m height");
    }

}

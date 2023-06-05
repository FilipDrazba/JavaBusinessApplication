package pl.edu.pb.wi.controlleer.annotation;

import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping("api/v1")
public @interface ApiRequestMapping {

}

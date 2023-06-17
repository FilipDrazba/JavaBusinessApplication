package pl.edu.pb.wi.controlleer.annotation;

import pl.edu.pb.wi.entity.Role;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccountType {
    Role.RoleType accountType ();        // aka Role

}

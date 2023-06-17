package pl.edu.pb.wi.config.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.edu.pb.wi.controlleer.annotation.AccountType;
import pl.edu.pb.wi.entity.Role;
import pl.edu.pb.wi.entity.User;
import pl.edu.pb.wi.exceptions.UnauthorizedAccessException;

@Aspect
@Component
public class AccountTypeAspect {

    @Before("@annotation(pl.edu.pb.wi.controlleer.annotation.AccountType)")
    public void validateAccountTypeAccess(JoinPoint joinPoint) throws UnauthorizedAccessException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AccountType accountTypeAnnotation = methodSignature.getMethod().getAnnotation(AccountType.class);
        Role.RoleType requiredRole = accountTypeAnnotation.accountType();

        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Role userRole = authenticatedUser.getRole();

        if(!userRole.getName().equals(requiredRole.getName())) {
            throw new UnauthorizedAccessException("Current user is not allowed to access this resource");
        }
    }
}

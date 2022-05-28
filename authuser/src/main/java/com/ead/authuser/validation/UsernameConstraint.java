package com.ead.authuser.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameConstraintImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD}) // pode ser usada em métodos e propriedades
@Retention(RetentionPolicy.RUNTIME) // validação em tempo de execução
public @interface UsernameConstraint {

    String message() default "Invalid username";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package org.easypay.common;

import javax.validation.*;
import java.util.Set;

/**
 * 이 추상클래를 구현하는 클래스의 멤버들을 유효성 검사를 하기 위한 추상클래스 이다.
 * @param <T>
 */
public abstract class SelfValidating<T> {

    private Validator validator;

    public SelfValidating() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Evaluates all Bean Validations on the attributes of this
     * instance.
     */
    protected void validateSelf() {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}

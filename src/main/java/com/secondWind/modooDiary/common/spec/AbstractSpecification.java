package com.secondWind.modooDiary.common.spec;

import com.secondWind.modooDiary.common.exception.ApiException;

public abstract class AbstractSpecification<T> implements Specification<T> {

    @Override
    public abstract boolean isSatisfiedBy(T t);

    public abstract void check(T t) throws ApiException;

    @Override
    public Specification<T> and(Specification<T> specification) {
        return new AndSpecification<T>(this, specification);
    }
}

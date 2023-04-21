package com.secondWind.modooDiary.common.spec;

public interface Specification<T> {
    boolean isSatisfiedBy(T t);

    Specification<T> and(Specification<T> specification);

}

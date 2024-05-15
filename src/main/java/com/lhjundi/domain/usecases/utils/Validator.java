package com.lhjundi.domain.usecases.utils;

import java.util.Collection;

public abstract class Validator<T> {
    public abstract Notification validade(T type);

    public static boolean nullOrEmpty(String string){
        return string == null || string.isEmpty();
    }

    public static boolean nullOrEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }
}

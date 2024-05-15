package com.lhjundi.domain.usecases.utils;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO)
 * Template of everything I can expect from any type of class
 * that manages persistence in the database.
 */
public interface DAO <T, K>{
    K create(T type);

    Optional<T> searchOne(K key);

    List<T> searchAll();

    boolean update(T type);

    boolean deleteByKey(K key);

    boolean delete(T type);
}

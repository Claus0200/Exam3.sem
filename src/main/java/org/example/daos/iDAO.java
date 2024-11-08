package org.example.daos;

import java.util.List;

public interface iDAO<T, I> {

    List<T> getAll();

    T getById(I i);

    T create(T t);

    T update(I i, T t);

    T delete(I i);

}

package org.example.KPRestAPI.Repositories;

import org.example.KPRestAPI.Entity.BaseEntity;
//общий репозиторный шаблон
public interface IRestRepository <T extends BaseEntity>{
    T[] select();
    T select(Integer id);
    T insert(T entity);
    T update(Integer id, T entity);
    T delete(Integer id);
}

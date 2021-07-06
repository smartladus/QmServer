package com.smarladu.qmserver.repository.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @program: QmServer
 * @description: 数据库服务基类，提供基础的数据库操作
 * @author: Eason Wu
 * @create: 2021/6/30
 */

@Slf4j
@Repository
public abstract class BaseRepository<T> {
    protected String collection = "";
    private Class<T> entityClass;

    {
        setCollection();
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
        this.entityClass = (Class<T>) trueType;
    }

    /**
     * 初始化集合名，设置collection值为表名
     */
    abstract protected void setCollection();

    @Autowired
    protected MongoTemplate mongoTemplate;

    public int replaceAll(List<T> list) {
        mongoTemplate.dropCollection(collection);
        return mongoTemplate.insert(list, collection).size();
    }

    public List<T> getAll() {
        return mongoTemplate.findAll(entityClass, collection);
    }

    public void insert(T item) {
        mongoTemplate.insert(item, collection);
    }

    public T findOneByField (String field, String value) {
        Query query = new Query(Criteria.where(field).is(value));
        return mongoTemplate.findOne(query, entityClass, collection);
    }
}

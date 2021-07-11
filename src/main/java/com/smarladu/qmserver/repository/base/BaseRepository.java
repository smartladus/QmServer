package com.smarladu.qmserver.repository.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

    /**
     * 取得某一列的值
     * @param field 字段名
     * @return 某个字段的所有值，除了该字段，其余字段都为空
     */
    public List<T> getFieldList(String field) {
        Query query = new Query();
        query.fields().include(field);
        return mongoTemplate.find(query, entityClass, collection);
    }

    /**
     * 模糊查找取得某一字段的值
     * @param field 字段名
     * @param fieldSeg 模糊查找的内容
     * @return 某个字段的所有值，除了该字段，其余字段都为空
     */
    public List<T> getFuzzyFieldList(String field, String fieldSeg) {
        Query query = new Query();
        Pattern pattern = Pattern.compile("^.*" + fieldSeg + ".*$", Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("task_no").regex(pattern));
        query.fields().include(field);
        return mongoTemplate.find(query, entityClass, collection);
    }
}

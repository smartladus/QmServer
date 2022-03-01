package com.smarladu.qmserver.repository.base;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.result.DeleteResult;
import com.smarladu.qmserver.entity.PageResult;
import com.smarladu.qmserver.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    private final Class<T> entityClass;
    private final Field[] fields;

    {
        setCollection();
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
        this.entityClass = (Class<T>) trueType;
        fields = entityClass.getDeclaredFields();
    }

    /**
     * 初始化集合名，设置collection值为表名
     */
    abstract protected void setCollection();

    @Autowired
    protected MongoTemplate mongoTemplate;

    public Collection<T> replaceAll(List<T> list) {
        mongoTemplate.dropCollection(collection);
        return mongoTemplate.insert(list, collection);
    }

    public Collection<T> saveAll(List<T> list) {
        return mongoTemplate.insert(list, collection);
    }

    public List<T> getAll() {
        return mongoTemplate.findAll(entityClass, collection);
    }

    public PageResult<T> getAllByPage() {
        Pageable pageable = PageRequest.of(1, 10);
        Query query = new Query();
        long total = mongoTemplate.count(query, entityClass, collection);
        query.with(pageable);
        return new PageResult<T>(total, mongoTemplate.find(query, entityClass, collection));
    }

    public List<T> getAll(String fieldToSort, boolean asc) {
        Query query = new Query();
        if (asc) {
            query.with(Sort.by(Sort.Order.asc(fieldToSort)));
        } else {
            query.with(Sort.by(Sort.Order.desc(fieldToSort)));
        }
        return mongoTemplate.find(query, entityClass, collection);
    }

    public T insert(T item) {
        return mongoTemplate.insert(item, collection);
    }

    public T save(T item) {
        return mongoTemplate.save(item, collection);
    }

    public T findOneByFieldVal (String field, String value) {
        Query query = new Query(Criteria.where(field).is(value));
        return mongoTemplate.findOne(query, entityClass, collection);
    }

    public List<T> findByFieldVal (String field, String value) {
        Query query = new Query(Criteria.where(field).is(value));
        return mongoTemplate.find(query, entityClass, collection);
    }

    public List<T> findByFieldVal (String field, String value, String fieldToSort, boolean asc) {
        Query query = new Query(Criteria.where(field).is(value));
        if (asc) {
            query.with(Sort.by(Sort.Order.asc(fieldToSort)));
        } else {
            query.with(Sort.by(Sort.Order.desc(fieldToSort)));
        }
        return mongoTemplate.find(query, entityClass, collection);
    }

    public boolean existsByField (String field, String value) {
        Query query = new Query(Criteria.where(field).is(value));
        return mongoTemplate.exists(query, entityClass, collection);
    }

    public DeleteResult deleteByFieldVal (String field, String value) {
        Query query = new Query(Criteria.where(field).is(value));
        return mongoTemplate.remove(query, collection);
    }

    /**
     * 取得某一列的值
     * @param fieldName 字段名
     * @return 某个字段的所有值，以JSON格式提供，如果字段未找到则返回null
     */
    public List<Object> getFieldList(String fieldName) {
        Query query = new Query();
        query.fields().include(fieldName);
        List<T> list = mongoTemplate.find(query, entityClass, collection);
        String camelFieldName = JsonUtils.snakeToCamel(fieldName);
        for (Field field : fields) {
            field.setAccessible(true);
            // 找到符合的字段则
            if (camelFieldName.equals(field.getName())) {
                return list.stream().map(t -> {
                    try {
                        return JSON.toJSON(field.get(t));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());
            }
        }
        return null;
    }

    /**
     * 模糊查找取得某一字段的值
     * @param fieldName 字段名
     * @param fieldSeg 模糊查找的内容
     * @return 某个字段的所有值，除了该字段，其余字段都为空
     */
    public List<T> getFuzzyFieldList(String fieldName, String fieldSeg) {
        Query query = new Query();
        Pattern pattern = Pattern.compile("^.*" + fieldSeg + ".*$", Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where(fieldName).regex(pattern));
        query.fields().include(fieldName);
        return mongoTemplate.find(query, entityClass, collection);
    }
}

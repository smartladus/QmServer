package com.smarladu.qmserver.repository.cert;

import com.smarladu.qmserver.entity.CertCategory;
import com.smarladu.qmserver.repository.base.BaseRepository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/6/30
 */

@Repository
public class CertCategoryRepository extends BaseRepository<CertCategory> {

    @Override
    protected void setCollection() {
        collection = "cert_category";
    }

    public List<CertCategory> findByRegion(String abbr) {
        Query query = new Query(Criteria.where("region").is(abbr));
        return mongoTemplate.find(query, CertCategory.class, collection);
    }
}

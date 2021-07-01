package com.smarladu.qmserver.repository.cert;

import com.smarladu.qmserver.entity.Region;
import com.smarladu.qmserver.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/6/30
 */

@Repository
public class RegionRepository extends BaseRepository<Region> {
    @Override
    protected void setCollection() {
        collection = "region";
    }
}

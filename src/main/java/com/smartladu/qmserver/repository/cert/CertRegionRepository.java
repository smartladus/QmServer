package com.smartladu.qmserver.repository.cert;

import com.smartladu.qmserver.entity.Region;
import com.smartladu.qmserver.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/6/30
 */

@Repository
public class CertRegionRepository extends BaseRepository<Region> {

    @Override
    protected void setCollection() {
        collection = "cert_region";
    }
}

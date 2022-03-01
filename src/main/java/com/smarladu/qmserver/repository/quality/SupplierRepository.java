package com.smarladu.qmserver.repository.quality;

import com.smarladu.qmserver.entity.Supplier;
import com.smarladu.qmserver.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: QmServer
 * @description:
 * @author: Eason Wu
 * @create: 2021/9/28
 */

@Repository
public class SupplierRepository  extends BaseRepository<Supplier> {
    @Override
    protected void setCollection() {
        collection = "suppliers";
    }
}

package com.smartladu.qmserver.repository.quality;

import com.smartladu.qmserver.entity.Supplier;
import com.smartladu.qmserver.repository.base.BaseRepository;
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

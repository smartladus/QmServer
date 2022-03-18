package com.smartladu.qmserver.repository.cert;

import com.smartladu.qmserver.entity.certtask.CertTaskStat;
import com.smartladu.qmserver.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;


@Repository
public class CertTaskStatRepository extends BaseRepository<CertTaskStat> {
    @Override
    protected void setCollection() {
        collection = "cert_task_stat";
    }
}

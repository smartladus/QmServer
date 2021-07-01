package com.smarladu.qmserver.repository.cert;

import com.smarladu.qmserver.entity.certtask.CertTask;
import com.smarladu.qmserver.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CertTaskRepository extends BaseRepository<CertTask> {
    @Override
    protected void setCollection() {
        collection = "cert_kanban";
    }
}

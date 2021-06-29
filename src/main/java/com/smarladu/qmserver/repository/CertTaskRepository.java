package com.smarladu.qmserver.repository;

import com.smarladu.qmserver.entity.CertTask;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CertTaskRepository extends MongoRepository<CertTask, String> {
}

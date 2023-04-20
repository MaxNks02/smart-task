package com.example.smartbank_task_webflux.dao.repo;

import com.example.smartbank_task_webflux.dao.model.BaseModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BaseRepo<E extends BaseModel> extends ReactiveCrudRepository<E, Long> {
}

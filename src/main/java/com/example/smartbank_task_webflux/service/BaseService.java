package com.example.smartbank_task_webflux.service;

import com.example.smartbank_task_webflux.dao.dto.BaseDto;
import com.example.smartbank_task_webflux.dao.mapper.BaseMapper;
import com.example.smartbank_task_webflux.dao.model.BaseModel;
import com.example.smartbank_task_webflux.dao.repo.BaseRepo;
import com.example.smartbank_task_webflux.exception.CustomNotFoundException;
import com.example.smartbank_task_webflux.exception.DatabaseException;
import com.example.smartbank_task_webflux.exception.handler.ApiErrorMessages;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class BaseService<R extends BaseRepo<E>, E extends BaseModel, D extends BaseDto, M extends BaseMapper<E, D>> {


    private final R repository;
    private final M mapper;

    public BaseService(R repository, M mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public R getRepository() {
        return repository;
    }

    public M getMapper() {
        return mapper;
    }

    public Flux<D> getAll() {
        return repository
                .findAll()
                .map(mapper::toDto)
                .onErrorMap(throwable -> new DatabaseException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + " %s", throwable.getMessage() + " %s")));
    }

    @Transactional
    public Mono<D> create(D input) {
        E entity = mapper.toEntity(input);
        return repository.save(entity).map(mapper::toDto)
                .onErrorMap(throwable -> new DatabaseException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + " %s", throwable.getMessage() + " %s")));

    }

    public Mono<D> getById(long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new CustomNotFoundException(ApiErrorMessages.NOT_FOUND + " with id ( " + id + " ) ")));
    }

    @Transactional
    public Mono<Void> deleteById(long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .switchIfEmpty(Mono.error(new CustomNotFoundException(ApiErrorMessages.NOT_FOUND + " with id ( " + id + " ) ")))
                .flatMap(e -> repository.deleteById(id));
    }

    public abstract Mono<D> update(D input, Long id);

}

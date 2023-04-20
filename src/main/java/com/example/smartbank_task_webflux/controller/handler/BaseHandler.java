package com.example.smartbank_task_webflux.controller.handler;

import com.example.smartbank_task_webflux.dao.dto.BaseDto;
import com.example.smartbank_task_webflux.dao.mapper.BaseMapper;
import com.example.smartbank_task_webflux.dao.model.BaseModel;
import com.example.smartbank_task_webflux.dao.repo.BaseRepo;
import com.example.smartbank_task_webflux.exception.BadRequestException;
import com.example.smartbank_task_webflux.service.BaseService;
import lombok.Getter;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Getter
public class BaseHandler<S extends BaseService<? extends BaseRepo<E>, E, D, M>,
        E extends BaseModel, D extends BaseDto, M extends BaseMapper<E, D>> {

    private final S service;
    private final Mono<ServerResponse> noContent = ServerResponse.noContent().build();

    public BaseHandler(S service) {
        this.service = service;
    }

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        return service
                .getAll()
                .collectList()
                .flatMap(r -> ServerResponse
                        .ok().bodyValue(r).onErrorResume(Mono::error));
    }

    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        if (id.length() == 0 || id.equals("0")) throw new BadRequestException("Id is not given!");

        return service
                .getById(Long.parseLong(id))
                .flatMap(r -> ServerResponse.ok().bodyValue(r).onErrorResume(Mono::error));
    }

    public Mono<ServerResponse> deleteById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        if (id.length() == 0 || id.equals("0")) throw new BadRequestException("Id is not given!");

        return service
                .deleteById(Long.parseLong(id))
                .then()
                .then(noContent);
    }

}

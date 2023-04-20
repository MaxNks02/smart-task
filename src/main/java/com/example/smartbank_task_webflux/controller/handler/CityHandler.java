package com.example.smartbank_task_webflux.controller.handler;

import com.example.smartbank_task_webflux.dao.dto.CityDto;
import com.example.smartbank_task_webflux.dao.mapper.CityMapper;
import com.example.smartbank_task_webflux.dao.model.City;
import com.example.smartbank_task_webflux.service.CityService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.InputStream;

@Component
public class CityHandler extends BaseHandler<CityService, City, CityDto, CityMapper>{

    private final Logger LOG = LoggerFactory.getLogger(CityService.class);
    private final Gson gson;


    public CityHandler(CityService service, Gson gson) {
        super(service);
        this.gson = gson;
    }

    public Mono<ServerResponse> sortByNumber(ServerRequest serverRequest){
        LOG.info("RESPONSE FROM INDIVIDUAL DETAILS: {} \t\t  ", gson.toJson("Sorting by code City Handler"));
        return serverRequest.body(BodyExtractors.toMultipartData()).
                flatMap(parts -> getService().sortByCode(fileToInputStream(parts)).collectList().
                        flatMap(r -> ServerResponse.ok().bodyValue(r).onErrorResume(Mono::error)));

    }

    public Mono<ServerResponse> sortByName(ServerRequest serverRequest){
        LOG.info("RESPONSE FROM INDIVIDUAL DETAILS: {} \t\t  ", gson.toJson("Sorting by name City Handler"));
        return serverRequest.body(BodyExtractors.toMultipartData()).
                flatMap(parts -> getService().sortByName(fileToInputStream(parts)).collectList().
                        flatMap(r -> ServerResponse.ok().bodyValue(r).onErrorResume(Mono::error)));

    }

    public Mono<ServerResponse> saveAll(ServerRequest serverRequest){
        LOG.info("RESPONSE FROM INDIVIDUAL DETAILS: {} \t\t  ", gson.toJson("Saving All data City Handler"));
        return serverRequest.body(BodyExtractors.toMultipartData()).
                flatMap(parts -> getService().saveAll(fileToInputStream(parts)).collectList().
                        flatMap(r -> ServerResponse.ok().bodyValue("All data saved").onErrorResume(Mono::error)));

    }

    public Mono<ServerResponse> save(ServerRequest serverRequest){
        LOG.info("RESPONSE FROM INDIVIDUAL DETAILS: {} \t\t  ", gson.toJson("saving data City Handler"));
        return serverRequest.bodyToMono(CityDto.class)
                .flatMap(getService()::create)
                .flatMap(r -> ServerResponse.ok().bodyValue(r).onErrorResume(Mono::error));

    }

    public Mono<ServerResponse> getAll(ServerRequest serverRequest){
        LOG.info("RESPONSE FROM INDIVIDUAL DETAILS: {} \t\t  ", gson.toJson("getting all City Handler"));
        return getService().getAll().collectList().flatMap(r -> ServerResponse
                .ok().bodyValue(r).onErrorResume(Mono::error));

    }

    public Mono<ServerResponse> getById(ServerRequest serverRequest){
        LOG.info("RESPONSE FROM INDIVIDUAL DETAILS: {} \t\t  ", gson.toJson("getting by id City Handler"));
        return getService().getById(Long.parseLong(serverRequest.pathVariable("id"))).flatMap(r -> ServerResponse
                .ok().bodyValue(r).onErrorResume(Mono::error));

    }

    public Mono<ServerResponse> deleteById(ServerRequest serverRequest){
        LOG.info("RESPONSE FROM INDIVIDUAL DETAILS: {} \t\t  ", gson.toJson("deleting by id City Handler"));
        return getService().deleteById(Long.parseLong(serverRequest.pathVariable("id"))).then(getNoContent());

    }


    private Mono<InputStream> fileToInputStream(MultiValueMap<String, Part> part){
      return   DataBufferUtils.join((part
                        .toSingleValueMap().get("file").content()))
                .map(DataBuffer::asInputStream);
    }



}

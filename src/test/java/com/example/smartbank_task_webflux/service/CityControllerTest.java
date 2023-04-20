package com.example.smartbank_task_webflux.service;

import com.example.smartbank_task_webflux.dao.dto.CityDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.io.File;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerTest {

    @Autowired
    private WebTestClient client;

    private final String pathToFile = "C:\\abbos\\empty.txt";

    //negative tests
    @Test
    public void shouldSaveSingleCityThrowInternalEx() {


        client.post().uri("/api/city/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(CityDto.builder().number(12445L).build()), CityDto.class)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.error").isEqualTo("Internal Server Error")
                .jsonPath("$.status").isEqualTo(500);
    }

    @Test
    public void findByIdNotFound() {


        client.get().uri("/api/city/getById/{id}", 1243214)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.error").isEqualTo("Not Found")
                .jsonPath("$.status").isEqualTo(404);

    }

    @Test
    public void deleteByIdNotFound() {


        client.delete().uri("/api/city/getById/{id}", 1243214)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.error").isEqualTo("Not Found")
                .jsonPath("$.status").isEqualTo(404);

    }


    @Test
    public void sortByNameThrowBadReqEx() {

        client.post().uri("/api/city/sortByName")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(fromFile(new File(pathToFile))))
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.error").isEqualTo("Bad Request")
                .jsonPath("$.status").isEqualTo(400);
    }

    @Test
    public void sortByNumberThrowBadReqEx() {

        client.post().uri("/api/city/sortByNumber")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(fromFile(new File(pathToFile))))
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.error").isEqualTo("Bad Request")
                .jsonPath("$.status").isEqualTo(400);
    }


    @Test
    public void saveAllThrowBadReqEx() {

        client.post().uri("/api/city/saveAll")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(fromFile(new File(pathToFile))))
                .exchange()
                .expectStatus().is5xxServerError()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.error").isEqualTo("Internal Server Error")
                .jsonPath("$.status").isEqualTo(500);
    }




    private MultiValueMap<String, HttpEntity<?>> fromFile(File file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new FileSystemResource(file));
        return builder.build();
    }


}


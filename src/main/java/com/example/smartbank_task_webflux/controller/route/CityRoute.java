package com.example.smartbank_task_webflux.controller.route;

import com.example.smartbank_task_webflux.controller.handler.CityHandler;
import com.example.smartbank_task_webflux.dao.dto.CityDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CityRoute {

    @Bean
    @RouterOperations({
            @RouterOperation(path = "/api/city/sortByNumber",
                    consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = CityHandler.class,
                    beanMethod = "sortByNumber",
                    operation = @Operation(operationId = "sortByNumber",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = CityDto.class
                                            ))
                                    )
                            }

                    )),

            @RouterOperation(path = "/api/city/sortByName",
                    consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = CityHandler.class,
                    beanMethod = "sortByName",
                    operation = @Operation(operationId = "sortByName",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = CityDto.class
                                            ))
                                    )
                            }

                    )),

            @RouterOperation(path = "/api/city/saveAll",
                    consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = CityHandler.class,
                    beanMethod = "saveAll",
                    operation = @Operation(operationId = "saveAll",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = CityDto.class
                                            ))
                                    )
                            }

                    )),
            @RouterOperation(path = "/api/city/save",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = CityHandler.class,
                    beanMethod = "save",
                    operation = @Operation(operationId = "save",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = CityDto.class
                                            ))
                                    )
                            }

                    )),

            @RouterOperation(path = "/api/city/getAll",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.GET,
                    beanClass = CityHandler.class,
                    beanMethod = "getAll",
                    operation = @Operation(operationId = "getAll",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = CityDto.class
                                            ))
                                    )
                            }

                    )),

            @RouterOperation(path = "/api/city/getById/{id}",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.GET,
                    beanClass = CityHandler.class,
                    beanMethod = "getAll",
                    operation = @Operation(operationId = "getById",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = CityDto.class
                                            ))
                                    )
                            },
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id")}

                    )),

            @RouterOperation(path = "/api/city/deleteById/{id}",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.GET,
                    beanClass = CityHandler.class,
                    beanMethod = "deleteById",
                    operation = @Operation(operationId = "deleteById",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(
                                                    implementation = CityDto.class
                                            ))
                                    )
                            },
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id")}

                    )),

    })


    public RouterFunction<ServerResponse> cityActions(CityHandler handler) {
        return RouterFunctions.route()
                .POST("/api/city/sortByNumber", RequestPredicates.contentType(MediaType.MULTIPART_FORM_DATA), handler::sortByNumber)
                .POST("/api/city/sortByName", RequestPredicates.contentType(MediaType.MULTIPART_FORM_DATA), handler::sortByName)
                .POST("/api/city/saveAll", RequestPredicates.contentType(MediaType.MULTIPART_FORM_DATA), handler::saveAll)
                .POST("/api/city/save", handler::save)
                .GET("/api/city/getAll", handler::getAll)
                .GET("/api/city/getById/{id}", handler::getById)
                .DELETE("/api/city/deleteById/{id}", handler::deleteById)

                .build();
    }
}

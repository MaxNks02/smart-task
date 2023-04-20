package com.example.smartbank_task_webflux.dao.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class CityDto extends BaseDto {
    @NotEmpty(message = "city number cannot be null or empty!")
    @JsonProperty("code")
    private Long number;

    @NotEmpty(message = "city name cannot be null or empty!")
    @JsonProperty("name")
    private String name;
}

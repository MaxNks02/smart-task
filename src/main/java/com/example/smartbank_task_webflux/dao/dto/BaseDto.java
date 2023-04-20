package com.example.smartbank_task_webflux.dao.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 5190598237215532904L;

    @JsonProperty("id")
    private Long id;
    @JsonProperty("created_at")
    private String createdAt = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
}


package com.example.smartbank_task_webflux.dao.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 5190598237215532904L;

    @JsonProperty("id")
    @Id
    private Long id;

    @JsonProperty("created_at")
    @Column("created_at")
    private String createdAt = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
}

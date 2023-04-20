package com.example.smartbank_task_webflux.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotEmpty;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "city")
public class City extends BaseModel  {

    @Column("code")
    @NotEmpty(message = "city code should not be empty!")
    private Long number;
    @Column("name")
    @NotEmpty(message = "city name should not be empty!")
    private String name;

}

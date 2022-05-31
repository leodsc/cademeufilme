package com.cademeufilme.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="movie")
@Data
public class MovieModel {

    @NotNull
    private String title;

    @NotNull
    private String streaming;
}

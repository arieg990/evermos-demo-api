package com.example.evermos.demo.controller.metadata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties
public class EmptyMetadata implements Serializable {

    public static EmptyMetadata empty() {
        return new EmptyMetadata();
    }
}

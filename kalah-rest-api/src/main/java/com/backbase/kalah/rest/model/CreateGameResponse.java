package com.backbase.kalah.rest.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder(toBuilder = true)
@JsonPropertyOrder({"id", "uri"})
public class CreateGameResponse {

    private String id;
    private String uri;
}

package com.backbase.kalah.rest.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Value
@SuperBuilder(toBuilder = true)
@JsonPropertyOrder({"id", "url", "status"})
public class MakeMoveResponse {

    private String id;
    private String url;
    private Map<String, String> status;
}

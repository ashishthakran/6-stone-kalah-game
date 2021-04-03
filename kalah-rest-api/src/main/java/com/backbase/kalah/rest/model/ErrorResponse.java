package com.backbase.kalah.rest.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class ErrorResponse {

    private String message;
}

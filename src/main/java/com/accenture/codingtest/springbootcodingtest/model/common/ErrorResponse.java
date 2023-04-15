package com.accenture.codingtest.springbootcodingtest.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResponse {
    @JsonProperty("code")
    public int code = 40000;

    @JsonProperty("description")
    public String description;
}

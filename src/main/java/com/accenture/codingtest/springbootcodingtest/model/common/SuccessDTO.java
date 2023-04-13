package com.accenture.codingtest.springbootcodingtest.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SuccessDTO {

    @JsonProperty("id")
    private String id;

}


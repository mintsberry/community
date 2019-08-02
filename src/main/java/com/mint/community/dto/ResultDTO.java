package com.mint.community.dto;

import com.mint.community.exception.CustomizeException;
import com.mint.community.exception.CustomizeStatusCode;
import lombok.Data;

@Data
public class ResultDTO<T> {
    private int code;
    private String message;
    private T data;


    public void setStatus(CustomizeStatusCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public void setStatus(CustomizeException customizeException) {
        this.code = customizeException.getCode();
        this.message = customizeException.getMessage();
    }
}

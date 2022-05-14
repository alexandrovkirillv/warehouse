package com.test.warehouse.exception;

import lombok.Data;

@Data
public class GlobalException extends Exception {
    private final String message;
}

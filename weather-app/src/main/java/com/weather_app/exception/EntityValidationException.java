package com.weather_app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EntityValidationException extends RuntimeException {

  private ExceptionPayload exceptionPayload;

}

package com.jwiem.rxwebapi.api.response;

import com.jwiem.rxwebapi.utils.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Response<T> {
  private ErrorCode errorCode;
  private T data;

  public static Response successNoData() {
    return Response.builder()
            .build();
  }

  public static <T> Response<T> successWithData(T data) {
    return Response.<T>builder()
            .data(data)
            .build();
  }

  public static Response error(ErrorCode errorCode) {
    return Response.builder()
            .errorCode(errorCode)
            .build();
  }
}

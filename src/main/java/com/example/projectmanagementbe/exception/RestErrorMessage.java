package com.example.projectmanagementbe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class RestErrorMessage {

  /**
   * Status to be returned in the response.
   */
  private HttpStatus status;

  /**
   * Message to be returned in the response body.
   */
  private String message;

}

package com.example.blogging_application_api.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExceptionDetail {
    private LocalDateTime dateTime;
    private String message; // message detailing the error
    private String path;    // path of the request
    private int status;     // HTTP status code
}

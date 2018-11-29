package pl.danowski.rafal.homelibraryserver.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private Integer status;
    private String message;
    private Long timeStamp;

}

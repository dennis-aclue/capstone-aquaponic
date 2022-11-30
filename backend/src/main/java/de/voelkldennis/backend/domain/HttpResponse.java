package de.voelkldennis.backend.domain;

import org.springframework.http.HttpStatus;

// send the response to the client (browser) and give back only information that the client needs
public class HttpResponse {
    private int httpStatusCode;     // 200, 201, 400, 401, 500, ...
    private HttpStatus httpStatus;  // OK, CREATED, BAD_REQUEST, UNAUTHORIZED, INTERNAL_SERVER_ERROR, ...
    private String reason;          // OK, CREATED, BAD_REQUEST, UNAUTHORIZED, INTERNAL_SERVER_ERROR, ...
    private String message;         // OK, CREATED, BAD_REQUEST, UNAUTHORIZED, INTERNAL_SERVER_ERROR, ...


    public HttpResponse() {
    }

    public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
    }


    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

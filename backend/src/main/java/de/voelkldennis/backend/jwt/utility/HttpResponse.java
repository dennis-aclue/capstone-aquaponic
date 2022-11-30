package de.voelkldennis.backend.jwt.utility;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.util.Date;

// send the response to the client (browser) and give back only information that the client needs
public class HttpResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss a", timezone = "Europe/Berlin")
    private Date timeStamp;
    private int httpStatusCode;     // 200, 201, 400, 401, 500, ...
    private HttpStatus httpStatus;  // OK, CREATED, BAD_REQUEST, UNAUTHORIZED, INTERNAL_SERVER_ERROR, ...
    private String reason;          // OK, CREATED, BAD_REQUEST, UNAUTHORIZED, INTERNAL_SERVER_ERROR, ...
    private String message;         // OK, CREATED, BAD_REQUEST, UNAUTHORIZED, INTERNAL_SERVER_ERROR, ...

    public HttpResponse() {
    }

    public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
        this.timeStamp = new Date();
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
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

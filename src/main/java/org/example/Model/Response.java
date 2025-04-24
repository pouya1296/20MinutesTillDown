package org.example.Model;

public class Response {
    public String responseMessage;
    public boolean success;
    public Response(String responseMessage, boolean success) {
        this.responseMessage = responseMessage;
        this.success = success;
    }
}

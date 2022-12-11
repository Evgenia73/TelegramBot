package org.example.domain;

import java.util.List;

public class AnswerMessage {

    private String stringResponse;

    public String getStringResponse() {
        return stringResponse;
    }

    public void setStringResponse(String stringResponse) {
        this.stringResponse = stringResponse;
    }

    private List<String> responseList;

    public List<String> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<String> responseList) {
        this.responseList = responseList;
    }

    public boolean hasResponseList() {
        return responseList != null;
    }

}

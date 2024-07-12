package com.travelbnb.payload;




import java.util.Date;


public class ErrorDetails {

    private String errorMessage;

    private Date date;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWebRequest() {
        return webRequest;
    }

    public void setWebRequest(String webRequest) {
        this.webRequest = webRequest;
    }

    private String webRequest;


    public ErrorDetails(String errorMessage,Date date,String webRequest){
        this.errorMessage=errorMessage;
        this.date=date;
        this.webRequest=webRequest;
    }
}

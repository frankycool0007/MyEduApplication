package com.tecxpert.myeduapplication.model;

public class Pdf {
    String topic_name,url,key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getUrl() {
        return url;
    }
    public void Pdf(String topic_name,String url){
        this.topic_name=topic_name;
        this.url=url;
    }
}

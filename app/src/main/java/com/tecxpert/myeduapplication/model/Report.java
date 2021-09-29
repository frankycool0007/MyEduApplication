package com.tecxpert.myeduapplication.model;

import com.google.firebase.database.core.Repo;

public class Report {
    String subject_name,topic_name,type;
    long mills;

    public String getTopic_name() {
        return topic_name;
    }

    public long getMills() {
        return mills;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public String getType() {
        return type;
    }
    public Report(String topic_name,String subject_name,String type,long mills){
        this.mills=mills;
        this.type=type;
        this.subject_name=subject_name;
        this.topic_name=topic_name;
    }
    public Report(){}
}

package com.yjcloud.transfer.entity;

import com.google.gson.Gson;

/**
 * Created by hhc on 17/9/13.
 */
public class MessageDTO {

    public MessageDTO(Object data) {
        this.data = data;
    }

    public MessageDTO(String name, Object data) {
        this.name = name;
        this.data = data;
    }

    private String name;
    private Object data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unchecked")
    public <T> T getData() {
        return (T) data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

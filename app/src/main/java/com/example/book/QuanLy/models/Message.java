package com.example.book.QuanLy.models;public class Message {    public String getName() {        return name;    }    public void setName(String name) {        this.name = name;    }    public String getContent() {        return content;    }    public void setContent(String content) {        this.content = content;    }    public String getTime() {        return time;    }    public void setTime(String time) {        this.time = time;    }    String name;    String content;    String time;    public Message(String name, String content, String time) {        this.name = name;        this.content = content;        this.time = time;    }}
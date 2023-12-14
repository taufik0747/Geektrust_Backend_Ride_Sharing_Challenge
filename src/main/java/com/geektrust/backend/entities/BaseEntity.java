package com.geektrust.backend.entities;

public abstract class BaseEntity {
    protected final String id;

    public BaseEntity(String id){
        this.id = id; 
    }

    public String getId(){
        return id;
    }
}

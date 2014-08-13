package com.github.mirreck.bean.domain;

import java.util.Date;

/**
 * Created by thomas.decoster on 13/08/2014.
 */
public class Task {
    private String name;
    private Date dueDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
}

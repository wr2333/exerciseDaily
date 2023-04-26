package com.example.jiuYe2.model;

import java.util.Date;
import lombok.Data;

@Data
public class Feed {

    private Integer id;
    private Integer userId;
    private Integer type;
    private String data;
    private Date createdTime;

}
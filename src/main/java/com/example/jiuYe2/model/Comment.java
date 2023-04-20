package com.example.jiuYe2.model;

import java.util.Date;
import lombok.Data;

@Data
public class Comment {

    private Integer id;
    private Integer userId;
    private Integer entityId;
    private Integer entityType;
    private String content;
    private Date createdDate;
    private Integer status;

}
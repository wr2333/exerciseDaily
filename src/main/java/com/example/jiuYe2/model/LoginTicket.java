package com.example.jiuYe2.model;

import java.util.Date;
import lombok.Data;

@Data
public class LoginTicket {

    private Integer id;
    private Integer userId;
    private String ticket;
    private Date expired;
    private Integer status;
}
package com.example.jiuYe2.dao;

import com.example.jiuYe2.model.LoginTicket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginTicketDAO {

    int addTicket(LoginTicket ticket);

    LoginTicket getTicketByTicket(String ticket);

    int updateStatus(@Param("ticket") String ticket, @Param("status") int status);

}
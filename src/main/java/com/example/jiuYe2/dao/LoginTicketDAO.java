package com.example.jiuYe2.dao;

import com.example.jiuYe2.model.LoginTicket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginTicketDAO {

    String TABLE_NAME = " login_ticket ";
    String INSERT_FIELDS = " user_id, ticket, expired, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    int addTicket(LoginTicket ticket);

    LoginTicket getTicketByTicket(String ticket);

    int updateStatus(@Param("ticket") String ticket, @Param("status") int status);

}
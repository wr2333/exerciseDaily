package com.example.jiuYe2.service;

import org.springframework.stereotype.Service;

@Service
public class IndexService {

    public String getIndexPage(){
        return "This is an index page.";
    }

}

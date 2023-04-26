package com.example.jiuYe2.async.handler;

import com.example.jiuYe2.async.EventBase;
import com.example.jiuYe2.async.EventType;
import com.example.jiuYe2.service.SearchService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component
public class CommentHandler implements EventHandler {

    @Resource
    SearchService searchService;

    @Override
    public void handle(EventBase eventBase) {
        try {
            searchService.indexComment(eventBase.getEntityId(), eventBase.getExtraElem("content"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<EventType> getAllSupportedEventType() {
        return Arrays.asList(EventType.COMMENT);
    }
}

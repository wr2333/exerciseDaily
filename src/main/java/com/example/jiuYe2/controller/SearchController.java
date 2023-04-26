package com.example.jiuYe2.controller;

import com.example.jiuYe2.model.Comment;
import com.example.jiuYe2.service.SearchService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Resource
    SearchService searchService;

    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    @ResponseBody
    public String getCommentSearch(@RequestParam String keyword) throws SolrServerException, IOException {
        List<Comment> comments = searchService.searchComment(keyword, 0, 10, "<em>", "</em>");
         return comments.toString();
    }

}

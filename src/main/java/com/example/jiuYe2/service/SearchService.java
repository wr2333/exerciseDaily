package com.example.jiuYe2.service;

import com.example.jiuYe2.model.Comment;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Resource
    CommentService commentService;

    private final HttpSolrClient client = new HttpSolrClient.Builder("http://localhost:8983/solr/#/jiuYe").build();

    public List<Comment> searchComment(String keyword, int offset, int count, String hlPre, String hlpost) throws SolrServerException, IOException {
        List<Comment> comments = new ArrayList<>();
        SolrQuery query = new SolrQuery(keyword);
        query.setStart(offset);
        query.setRows(count);
        query.setHighlight(true);
        query.setHighlightSimplePre(hlPre);
        query.setHighlightSimplePost(hlpost);
        QueryResponse response = client.query(query);
        // highlighting内部构造为Map<String, Map<String, List>>
        response.getHighlighting().forEach((id, cols) -> {
            // 根据id获取评论，并将内容换成高亮内容。
            Comment comment = commentService.getCommentById(Integer.parseInt(id));
            comment.setContent(cols.get("content").get(0));
        });
        return comments;
    }

    public boolean indexComment(int id, String content) throws SolrServerException, IOException {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", id);
        document.setField("content", content);
        UpdateResponse response = client.add(document, 1000);
        return response != null && response.getStatus() == 0;

    }

}


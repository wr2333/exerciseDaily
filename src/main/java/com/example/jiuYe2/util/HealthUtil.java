package com.example.jiuYe2.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class HealthUtil implements InitializingBean {

    private class TrieNode {
        private final Map<Character, TrieNode> children = new HashMap<>();
        private boolean leaf = false;

        public void addChild(Character key, TrieNode node) {
            children.put(key, node);
        }

        public TrieNode getChild(Character key) {
            return children.get(key);
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        public boolean isLeaf() {
            return this.leaf;
        }

    }

    private final TrieNode root = new TrieNode();

    @Override
    public void afterPropertiesSet() throws Exception {
        initKeyWord();
    }

    private void initKeyWord() throws IOException {
        Stream<String> lines = Files.lines(Paths.get("E:\\codeOfAll\\JavaCode\\jiuYe2\\src\\main\\resources\\static\\keyWord.txt"));
        lines.forEach(line -> {
            TrieNode point = root;
            for (int i = 0; i < line.length(); i++) {
                Character character = line.charAt(i);
                TrieNode node = point.getChild(character);
                if (node == null) {
                    node = new TrieNode();
                    point.addChild(line.charAt(i), node);
                }
                point = node;
                if (i == line.length() - 1) {
                    point.setLeaf(true);
                }
            }
        });
    }

    private boolean isSymbol(Character character) {
        int code = (int) character;
        return !(code > 0x2e80 && code < 0x9fff);
    }

    public String filter(String content) {
        if (StringUtils.isEmpty(content)) {
            return content;
        }

        StringBuilder result = new StringBuilder();
        Character character = null;
        TrieNode node = root;
        int begin = 0;
        int temp = 0;

        while (temp < content.length()) {
            character = content.charAt(temp);
            if (isSymbol(character)) {
                if (node == root) {
                    result.append(character);
                    begin++;
                }
                temp++;
                continue;
            }
            node = node.getChild(character);
            if (node == null) {
                result.append(content.charAt(begin));
                begin = begin + 1;
                temp = begin;
                node = root;
            } else if (node.isLeaf()) {
                result.append("***");
                begin = temp + 1;
                temp = begin;
                node = root;
            } else {
                temp++;
            }
        }
        result.append(content.substring(begin));    //最后一串字符
        return result.toString();
    }

//    public static void main(String[] args) throws IOException {
//        HealthUtil healthUtil = new HealthUtil();
//        healthUtil.initKeyWord();
//        System.out.println(healthUtil.filter("...我超原1批!!啊啊啊"));
//    }

}

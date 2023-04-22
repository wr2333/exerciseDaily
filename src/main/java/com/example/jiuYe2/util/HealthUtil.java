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

    // 字典树，用于快速检索
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
        // 按行无序读取文件内容，有序则为forEachOrdered()
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
        // 东亚文字范围
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
                // 若符号在本次检索开头，则直接输出
                if (node == root) {
                    result.append(character);
                    begin++;
                }
                temp++;
                continue;
            }
            // 字典树向下检索一层
            node = node.getChild(character);
            // 若找不到子节点，则以begin位置开头，temp位置结尾的字符串不为敏感词，输出begin位置的字符并后移一位。
            if (node == null) {
                result.append(content.charAt(begin));
                begin = begin + 1;
                temp = begin;
                node = root;
                // 若找到子结点且为叶子结点，则以begin位置开头，temp位置结尾的字符串为敏感词，和谐并后移至temp之后。
            } else if (node.isLeaf()) {
                result.append("***");
                begin = temp + 1;
                temp = begin;
                node = root;
            } else {
                // 若找到子结点但不为叶子结点，说明有可能为敏感词，temp继续后移并检索。
                temp++;
            }
        }
        // 输出最后一串字符。
        result.append(content.substring(begin));
        return result.toString();
    }

//    public static void main(String[] args) throws IOException {
//        HealthUtil healthUtil = new HealthUtil();
//        healthUtil.initKeyWord();
//        System.out.println(healthUtil.filter("...我超原1批!!啊啊啊"));
//    }

}

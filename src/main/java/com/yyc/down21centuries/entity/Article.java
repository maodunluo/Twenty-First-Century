package com.yyc.down21centuries.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author yuechao
 */
public class Article implements Serializable {
    
    private static final long serialVersionUID = 2449616483849024383L;
    /**
     * 文章的地址
     */
    private String address;
    /**
     * 文章标题
     */
    private String topic;
    /**
     * 作者
     */
    private String author;

    public Article() {
    }

    public Article(String address, String topic, String author) {
        this.address = address;
        this.topic = topic;
        this.author = author;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Article article = (Article) o;
        return Objects.equals(address, article.address) &&
                Objects.equals(topic, article.topic) &&
                Objects.equals(author, article.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, topic, author);
    }

    @Override
    public String toString() {
        return "Article{" +
                "address='" + address + '\'' +
                ", topic='" + topic + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}

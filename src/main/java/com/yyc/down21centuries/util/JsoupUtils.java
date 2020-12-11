package com.yyc.down21centuries.util;

import com.yyc.down21centuries.entity.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuechao
 */
@Component
public class JsoupUtils {

    private static final String PREURL = "http://www.cuhk.edu.hk/ics/21c/";

    private JsoupUtils(){}

    public static List<Article> parse(String webContent) {
        Document doc = Jsoup.parse(webContent);
        Element element = doc.getElementsByClass("content").first();
        Elements authors = element.select(".c2");
        Elements pdfs = element.select(".c3 > a");

        List<String> authorList = new ArrayList<>();
        for (Element author : authors) {
            authorList.add(author.text());
        }
        List<String> pdfTopicList = new ArrayList<>();
        List<String> pdfUrlList = new ArrayList<>();
        for (int i = 0; i < pdfs.size(); i++) {
            if (pdfs.get(i).text().contains("附录")) {
                authorList.add(i, authorList.get(i - 1));
            }
            pdfTopicList.add(pdfs.get(i).text());
            pdfUrlList.add(PREURL + pdfs.get(i).attr("href").substring(6));
        }

        return getArticleList(pdfs, authorList, pdfTopicList, pdfUrlList);
    }

    private static List<Article> getArticleList(Elements pdfs, List<String> authorList, List<String> pdfTopicList, List<String> pdfUrlList) {
        List<Article> articleList = new ArrayList<>(pdfs.size());
        String count = pdfUrlList
                .get(0)
                .substring(pdfUrlList.get(0).lastIndexOf("c") + 1, pdfUrlList.get(0).lastIndexOf('-'));
        for (int i = 0; i < pdfs.size(); i++) {
            Article article = new Article();
            article.setAddress(pdfUrlList.get(i));
            article.setTopic(pdfTopicList.get(i));
            //21到88期格式不一样
            if (20 >= Integer.parseInt(count) || Integer.parseInt(count) >=89) {
                article.setAuthor(authorList.get(i));
            }
            articleList.add(article);
        }
        return articleList;
    }
}

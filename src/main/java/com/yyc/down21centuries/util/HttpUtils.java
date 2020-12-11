package com.yyc.down21centuries.util;

import com.yyc.down21centuries.entity.Article;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuechao
 */
@Component
public class HttpUtils {

    private static final int OK = 200;
    private static final String URLSUFFIX = ".html";
    private static final String PDFSUFFIX = ".pdf";
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private final PoolingHttpClientConnectionManager cm;

    public HttpUtils() {
        this.cm = new PoolingHttpClientConnectionManager();
        //设置连接池最大连接数
        this.cm.setMaxTotal(100);
        //设置每个主机的最大连接数
        this.cm.setDefaultMaxPerRoute(10);
    }

    /**
     * 得到全文版的urlList
     *
     * @param preUrl url前缀
     * @param fullTextIndex 目前有全文版的期数
     * @return 拼接好的urlList
     */
    public List<String> getUrlList(String preUrl, int fullTextIndex) {
        List<String> urlList = new ArrayList<>();
        for (int i = 1; i <= fullTextIndex; i++) {
            String url;
            if (i < 10) {
                url = preUrl + "c00" + i + URLSUFFIX;
            } else if (i < 100) {
                url = preUrl + "c0" + i + URLSUFFIX;
            } else {
                url = preUrl + "c" + i + URLSUFFIX;
            }
            urlList.add(url);
        }
        return urlList;
    }
    
    /**
     * 根据请求地址下载url页面
     *
     * @param url 网站地址
     * @return content
     */
    public String doGetHtml(String url) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(getConfig());
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == OK && response.getEntity() != null) {
                return EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return "";
    }

    /**
     * 下载pdf
     *
     * @param articleList 文章对象list
     * @param count       第几期
     */
    public void doGetPdf(List<Article> articleList, String count) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        int index = 1;
        String pathName = "21centuries/" + count + File.separator;
        for (Article article : articleList) {
            String url = article.getAddress();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(getConfig());
            CloseableHttpResponse response = null;
            String name = index + "." + article.getTopic() + "." + PDFSUFFIX;
            if (20 >= Integer.parseInt(count) || Integer.parseInt(count) >= 89) {
                name = index + "." + article.getTopic() + "." + article.getAuthor() + PDFSUFFIX;
            }
            index++;
            Path path = Paths.get(pathName);
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            try (OutputStream outputStream = new FileOutputStream(new File(pathName + name))) {
                response = httpClient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == OK && response.getEntity() != null) {
                    response.getEntity().writeTo(outputStream);
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            } finally {
                try {
                    if (response != null) {
                        response.close();
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    private RequestConfig getConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(300)
                .setSocketTimeout(10 * 1000)
                .build();
    }
}

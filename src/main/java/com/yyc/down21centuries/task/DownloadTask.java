package com.yyc.down21centuries.task;

import com.yyc.down21centuries.entity.Article;
import com.yyc.down21centuries.util.HttpUtils;
import com.yyc.down21centuries.util.JsoupUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yuechao
 */
@Component
public class DownloadTask {

    private static final Logger logger = LoggerFactory.getLogger(DownloadTask.class);
    private static final int FULLTEXTINDEX = 178;
    private static final String PREURL = "http://www.cuhk.edu.hk/ics/21c/cn/issues/";

    final HttpUtils httpUtils;

    public DownloadTask(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }

    @Scheduled(initialDelay = 1000, fixedRate = Long.MAX_VALUE)
    public void downloadTask() {
        List<String> urlList = httpUtils.getUrlList(PREURL, FULLTEXTINDEX);
        for (String url : urlList) {
            String content = httpUtils.doGetHtml(url);
            List<Article> articleList = JsoupUtils.parse(content);
            String count = url.substring(url.lastIndexOf('c') + 1, url.lastIndexOf('.'));
            httpUtils.doGetPdf(articleList, count);
        }
        logger.info("pdf抓取完成");
    }
}

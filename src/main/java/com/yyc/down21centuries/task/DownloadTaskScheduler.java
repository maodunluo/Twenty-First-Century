package com.yyc.down21centuries.task;

import com.yyc.down21centuries.entity.Article;
import com.yyc.down21centuries.util.CharacterUtils;
import com.yyc.down21centuries.util.HttpUtils;
import com.yyc.down21centuries.util.JsoupUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * @author yuechao
 */
@Component
public class DownloadTaskScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DownloadTaskScheduler.class);
    private static final String PREURL = "http://www.cuhk.edu.hk/ics/21c/cn/issues/";

    final HttpUtils httpUtils;

    final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;

    public DownloadTaskScheduler(HttpUtils httpUtils, ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.httpUtils = httpUtils;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    /**
     * 下载入口，需要输入模式和下载的期数
     */
    @Scheduled(initialDelay = 1000, fixedRate = Long.MAX_VALUE)
    public void chooseMode() {
        logger.info("请输入模式：");
        logger.info("1：自动下载全部");
        logger.info("2：选择下载");
        int inputNumber = CharacterUtils.getInputNumber();
        switch (inputNumber) {
            case 1:
                int plusNumber = getPlusIssueNumber();
                startDownloadTask(1, 179 + plusNumber);
                stopCron();
                break;
            case 2:
                logger.info("请输入您要下载的起始期数（默认为1）：");
                int startIndex = CharacterUtils.getInputNumber();
                logger.info("请输入您要下载的结束期数（默认为1）：");
                int endIndex = CharacterUtils.getInputNumber();
                startDownloadTask(startIndex, endIndex);
                stopCron();
                break;
            default:
                logger.warn("请输入正确的数字");
        }
    }

    /**
     * 开始下载期刊
     *
     * @param startIndex 起始期数
     * @param endIndex   结束期数
     */
    private void startDownloadTask(int startIndex, int endIndex) {
        List<String> urlList = httpUtils.getUrlList(PREURL, startIndex, endIndex);
        for (String url : urlList) {
            String content = httpUtils.doGetHtml(url);
            List<Article> articleList = JsoupUtils.parse(content);
            String count = url.substring(url.lastIndexOf('c') + 1, url.lastIndexOf('.'));
            httpUtils.doGetPdf(articleList, count);
        }
        logger.info("pdf抓取完成");
    }

    /**
     * 返回现在这期相对于179期增加的期数
     *
     * @return 期数
     */
    private int getPlusIssueNumber() {
        DateTime currentDateTime = DateTime.now();
        DateTime issue179 = new DateTime(2020, 12, 1, 0, 0, 0);
        int monthRange = (currentDateTime.getYear() - issue179.getYear()) * 12 +
                currentDateTime.getMonthOfYear() - issue179.getMonthOfYear();
        return monthRange / 2;
    }

    /**
     * 结束定时任务
     */
    private void stopCron() {
        if (future != null) {
            future.cancel(true);
        }
        logger.info("DynamicTask.stopCron()");
    }
}

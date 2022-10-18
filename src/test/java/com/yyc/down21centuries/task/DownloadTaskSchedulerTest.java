package com.yyc.down21centuries.task;

import com.yyc.down21centuries.util.HttpUtils;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

class DownloadTaskSchedulerTest {

  @Test
  void startDownloadTaskTest() {
    HttpUtils httpUtils = new HttpUtils();
    DownloadTaskScheduler downloadTaskScheduler = new DownloadTaskScheduler(httpUtils,
        new ThreadPoolTaskScheduler());

    downloadTaskScheduler.startDownloadTask(189, 190);
  }

}
package com.analytics.processor.config;

import com.analytics.processor.domain.sales.service.ProcessSaleAnalyticsService;
import com.analytics.processor.listener.CustomDirectoryChangeListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.File;
import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FileWatcherConfig {

    private final HomePathProperties homePathProperties;
    private final ProcessSaleAnalyticsService processSaleAnalyticsService;

    //TODO: Testar com mais de 100 arquivos? Teste de integração.
    @Bean
    public FileSystemWatcher fileSystemWatcher() {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(1000L), Duration.ofMillis(500L));
        fileSystemWatcher.addSourceDirectory(new File(homePathProperties.getHomePathIn()));
        fileSystemWatcher.addListener(new CustomDirectoryChangeListener(processSaleAnalyticsService));
        fileSystemWatcher.start();

        log.info("FileWatcher - Started");

        return fileSystemWatcher;
    }

    @PreDestroy
    public void onDestroy() {
        fileSystemWatcher().stop();
    }
}

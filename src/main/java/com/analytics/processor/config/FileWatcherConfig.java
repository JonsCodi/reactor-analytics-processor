package com.analytics.processor.config;

import com.analytics.processor.domain.sales.service.ProcessSaleAnalyticsService;
import com.analytics.processor.listener.CustomDirectoryChangeListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        String home = System.getProperty("user.home");

        File dataIn = new File(home.concat(homePathProperties.getDataInPath()));
        File dataOut = new File(home.concat(homePathProperties.getDataOutPath()));

        createDirectories(dataIn, dataOut);

        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(300L), Duration.ofMillis(100L));
        fileSystemWatcher.addSourceDirectory(dataIn);
        fileSystemWatcher.addListener(new CustomDirectoryChangeListener(processSaleAnalyticsService));
        fileSystemWatcher.start();

        log.info("FileWatcher - Started");

        return fileSystemWatcher;
    }

    private void createDirectories(File dataIn, File dataOut) {
        dataIn.mkdirs();
        dataOut.mkdirs();
    }

}

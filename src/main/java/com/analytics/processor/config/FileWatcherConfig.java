package com.analytics.processor.config;

import com.analytics.processor.listener.CustomDirectoryChangeLister;
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

    @Bean
    public FileSystemWatcher fileSystemWatcher() {
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(3000L), Duration.ofMillis(1000L));
        fileSystemWatcher.addSourceDirectory(new File(homePathProperties.getHomePathIn()));
        fileSystemWatcher.addListener(new CustomDirectoryChangeLister());
        fileSystemWatcher.start();

        log.info("FileWatcher - Started");

        return fileSystemWatcher;
    }

    @PreDestroy
    public void onDestroy() {
        fileSystemWatcher().stop();
    }
}

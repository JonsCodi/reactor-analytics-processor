package com.analytics.processor.listener;

import com.analytics.processor.domain.sales.service.ProcessSaleAnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomDirectoryChangeListener implements FileChangeListener {

    private final ProcessSaleAnalyticsService processSaleAnalyticsService;

    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        Optional<Set<ChangedFile>> optionalChangedFiles = changeSet.stream()
                .map(ChangedFiles::getFiles)
                .findAny();

        if (optionalChangedFiles.isPresent()) {
            log.info("Component - DirectoryChangeListener");

            Set<ChangedFile> setChangedFile = optionalChangedFiles.get();

            processSaleAnalyticsService.processFiles(setChangedFile);
        }
    }

}

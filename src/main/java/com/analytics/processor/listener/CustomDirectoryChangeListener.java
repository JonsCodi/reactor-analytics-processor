package com.analytics.processor.listener;

import com.analytics.processor.service.IFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomDirectoryChangeListener implements FileChangeListener {

    private final IFileService fileChangeService;

    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        Optional<Set<ChangedFile>> optionalChangedFiles = changeSet.stream()
                .map(ChangedFiles::getFiles)
                .findAny();

        if (optionalChangedFiles.isPresent()) {
            log.info("Component - DirectoryChangeListener");

            Set<ChangedFile> setChangedFile = optionalChangedFiles.get();
            List<File> files = fileChangeService.getRecentAddedFiles(setChangedFile);

            List<String> filesString = files.stream()
                    .map(fileChangeService::fileToString)
                    .collect(Collectors.toList());

        }
    }

}

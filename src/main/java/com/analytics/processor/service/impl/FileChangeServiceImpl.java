package com.analytics.processor.service.impl;

import com.analytics.processor.service.IFileChangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileChangeServiceImpl implements IFileChangeService {

    @Override
    public List<File> getRecentAddedFiles(Set<ChangedFile> changedFiles) {
        log.info("Service - getRecentAddedFiles");

        return changedFiles
                .stream()
                .filter(file -> file.getType().equals(ChangedFile.Type.ADD))
                .map(file -> {
                    log.info("Service - File: {}", file.getFile().getName());

                    return file.getFile();
                })
                .collect(Collectors.toList());
    }
}

package com.analytics.processor.service.impl;

import com.analytics.processor.config.HomePathProperties;
import com.analytics.processor.service.IFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Lazy
@RequiredArgsConstructor
@Service
public class FileServiceImpl implements IFileService {

    private final HomePathProperties homePathProperties;

    @Override
    public List<File> getRecentAddedFiles(Set<ChangedFile> changedFiles) {
        log.info("Service - getRecentAddedFiles");

        return changedFiles
                .stream()
                .filter(this::isAddedOrModifyEvent)
                .map(file -> {
                    log.info("Service - File.name: {}", file.getFile().getName());

                    return file.getFile();
                })
                .collect(Collectors.toList());
    }

    private boolean isAddedOrModifyEvent(ChangedFile file) {
        return file.getType().equals(ChangedFile.Type.ADD)
                || file.getType().equals(ChangedFile.Type.MODIFY);
    }

    @Override
    public void create(Path path, String content) {
        log.info("Service - create | Path: {}", path.getFileName());

        try {
            Files.deleteIfExists(path);

            Files.writeString(path, content, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("Service - create | Error : {}", e.getLocalizedMessage());

            e.printStackTrace();
        }
    }

    @Override
    public String fileToStringList(File file) {
        log.info("Service - fileToString | file.name: {}", file.getName());
        try {
            return Files.readString(file.toPath());
        } catch (IOException e) {
            log.error("IOException error: {}", e.getLocalizedMessage());

            e.printStackTrace();
        }

        return Strings.EMPTY;
    }

    @Override
    public File create(FilePart filePart) {
        return new File(homePathProperties.getHomePathIn()
                .concat(filePart.filename()));
    }
}



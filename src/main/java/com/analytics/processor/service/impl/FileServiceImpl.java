package com.analytics.processor.service.impl;

import com.analytics.processor.config.HomePathProperties;
import com.analytics.processor.service.IFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

@Slf4j
@Lazy
@Service
public class FileServiceImpl implements IFileService {

    @Override
    public List<File> getRecentAddedFiles(Set<ChangedFile> changedFiles) {
        log.info("Service - getRecentAddedFiles");

        return changedFiles
                .stream()
                .filter(file -> file.getType().equals(ChangedFile.Type.ADD))
                .map(file -> {
                    log.info("Service - File.name: {}", file.getFile().getName());

                    return file.getFile();
                })
                .collect(Collectors.toList());
    }

    @Override
    public void create(String folder, String fileName) {
        log.info("Service - create | folder: {} | fileName: {}", folder, fileName);

        File newFile = new File(folder.concat(fileName));

        try (OutputStream out =
                     new BufferedOutputStream(Files.newOutputStream(newFile.toPath(), CREATE, APPEND))
        ) {
            out.write(Files.readAllBytes(newFile.toPath()));
        } catch (IOException x) { //TODO: Handler...
            log.error("IOException error: {}", x.getLocalizedMessage());

            x.printStackTrace();
        }
    }

    @Override
    public String fileToString(File file) {
        log.info("Service - fileToString | file.name: {}", file.getName());
        try {
            return Files.readString(file.toPath());
        } catch (IOException e) {
            log.error("IOException error: {}", e.getLocalizedMessage());

            e.printStackTrace();
        }

        return Strings.EMPTY;
    }
}



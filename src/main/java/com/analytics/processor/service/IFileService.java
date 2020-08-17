package com.analytics.processor.service;

import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.http.codec.multipart.FilePart;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public interface IFileService {

    List<File> getRecentAddedFiles(Set<ChangedFile> changedFiles);

    void create(Path folder, String fileName);

    String fileToStringList(File file);

    File create(FilePart filePart);

}

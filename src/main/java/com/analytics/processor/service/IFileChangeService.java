package com.analytics.processor.service;

import org.springframework.boot.devtools.filewatch.ChangedFile;

import java.io.File;
import java.util.List;
import java.util.Set;

public interface IFileChangeService {

    List<File> getRecentAddedFiles(Set<ChangedFile> changedFiles);

}

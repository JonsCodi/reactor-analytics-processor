package com.analytics.processor.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class CustomDirectoryChangeListener implements FileChangeListener {

    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        for(ChangedFiles cfiles : changeSet) {
            for(ChangedFile cfile: cfiles.getFiles()) {
                if(cfile.getType().equals(ChangedFile.Type.ADD)) {
                   log.info("Operation: " + cfile.getType()
                            + " On file: "+ cfile.getFile().getName() + " is done");
                }
            }
        }
    }

}

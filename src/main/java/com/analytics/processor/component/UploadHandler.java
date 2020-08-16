package com.analytics.processor.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class UploadHandler {

    @Value("${service.home-path}")
    private String homePath;

    private final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final String DELIMITER_TWO_POINTS = ":";
    private final String DELIMITER = "-";

    public Mono<ServerResponse> transfer(ServerRequest request) {
        return request.multipartData().flatMap(parts -> {
            try {
                transferFiles(parts);

                return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(
                        BodyInserters.fromValue(Map.of("status", "Successfully uploaded"))
                );
            } catch (Exception e) {
                return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(
                        BodyInserters.fromValue(Map.of("status", e.getLocalizedMessage()))
                );
            }
        });
    }

    private void transferFiles(MultiValueMap<String, Part> parts) {
        parts.get("files").forEach(
                part -> {
                    FilePart filePart = (FilePart) part;

                    File newFile = createFile(filePart);

                    filePart.transferTo(newFile);
                }
        );
    }

    private File createFile(FilePart filePart) {
        return new File(homePath
                                .concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
                                        .replace(DELIMITER_TWO_POINTS, DELIMITER))
                                .concat(DELIMITER)
                                .concat(filePart.filename()));
    }

}

package com.analytics.processor.component;

import com.analytics.processor.config.HomePathProperties;
import com.analytics.processor.service.IFileService;
import lombok.RequiredArgsConstructor;
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
import java.util.Map;

@RequiredArgsConstructor
@Component
public class UploadHandler {

    private final IFileService fileService;

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

                    File newFile = fileService.create(filePart);

                    filePart.transferTo(newFile);
                }
        );
    }

}

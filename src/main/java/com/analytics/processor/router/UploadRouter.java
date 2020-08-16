package com.analytics.processor.router;

import com.analytics.processor.component.UploadHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UploadRouter {

    @Bean
    public RouterFunction<ServerResponse> route(UploadHandler uploadHandler) {
        return RouterFunctions
                .route(RequestPredicates.POST("/upload-files")
                                .and(RequestPredicates.accept(MediaType.MULTIPART_FORM_DATA)),
                        uploadHandler::transfer);
    }

}

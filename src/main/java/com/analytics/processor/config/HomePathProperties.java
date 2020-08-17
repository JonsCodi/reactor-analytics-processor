package com.analytics.processor.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Getter
@Component
@Lazy
public class HomePathProperties {

    @Value("${service.data-in}")
    private String dataInPath;

    @Value("${service.data-out}")
    private String dataOutPath;

}

package com.analytics.processor.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Getter
@Component
@Lazy
public class HomePathProperties {

    @Value("${service.home-path-in}")
    private String homePathIn;

    @Value("${service.home-path-out")
    private String homePathOut;

}

package com.tianye.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "projectUrl")
@Data
@Component
public class ProjectUrlConfig {

    private String wechatMpAuthorize;

    private String wechatOpenAuthorize;

    private String sell;
}

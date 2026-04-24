package com.jayant.OderService.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
@Data
public class FeaturesEnableConfig {

    //todo This class is made to demonstrate the use of RefreshScope , if we want to disable or enable some features on the go,
    // we could just do that in github and then hit the refresh api to get the feature, we dont have to re run the whole application

    @Value("${features.user-tracking-enabled}")
    private boolean isUserTrackingEnabled;

}

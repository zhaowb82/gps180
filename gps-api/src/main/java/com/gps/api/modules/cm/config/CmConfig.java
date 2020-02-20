
package com.gps.api.modules.cm.config;

import com.gps.api.modules.cm.chinamobile.CmApi;
import com.gps.api.modules.cm.luat.LuatApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CmConfig {

    @Bean
    public CmApi cmApi() {
        return new CmApi();
    }
    @Bean
    public LuatApi luatApi() {
        return new LuatApi();
    }
}

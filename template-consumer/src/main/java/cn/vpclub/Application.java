/**
 *  Copyright 2005-2015 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package cn.vpclub;

import cn.vpclub.spring.boot.cors.autoconfigure.CorsConfiguration;
import cn.vpclub.spring.boot.cors.autoconfigure.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({CorsProperties.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(
                Application.class);
        application.addListeners(
                new ApplicationPidFileWriter("app.pid"));
        application.run(args);
    }
    @Bean
    public CorsConfiguration corsConfiguration(){
        return new CorsConfiguration();
    }
}

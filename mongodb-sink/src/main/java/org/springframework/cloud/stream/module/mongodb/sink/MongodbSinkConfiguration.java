/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.stream.module.mongodb.sink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.integration.mongodb.outbound.MongoDbStoringMessageHandler;
import org.springframework.messaging.MessageHandler;

/**
 * Creates a MongoDB connection factory from the default settings.
 *
 * TODO: Make the MongoDB connection settings more configurable
 *
 * @author Soby Chacko
 */
@Configuration
@EnableConfigurationProperties(MongodbSinkProperties.class)
public class MongodbSinkConfiguration {

    @Autowired
    private MongoDbFactory mongoDbFactory;

    @Autowired
    private MongodbSinkProperties mongodbSinkProperties;

    @Bean
    @Qualifier
    public MessageHandler redisSinkMessageHandler() {
        final MongoDbStoringMessageHandler mongoDbStoringMessageHandler =
                new MongoDbStoringMessageHandler(mongoDbFactory);
        if (mongodbSinkProperties.isCollectionNameProvided()) {
            mongoDbStoringMessageHandler.setCollectionNameExpression(mongodbSinkProperties.getCollectionNameExpression());
        }
        return mongoDbStoringMessageHandler;
    }
}

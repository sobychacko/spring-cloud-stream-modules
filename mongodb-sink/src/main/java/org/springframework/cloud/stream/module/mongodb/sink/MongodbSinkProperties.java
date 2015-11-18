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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.expression.Expression;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

/**
 * Various MongoDB properties that can be bound from Spring Environment.
 *
 * @author Soby Chacko
 */
@ConfigurationProperties
public class MongodbSinkProperties {

    private SpelExpressionParser parser = new SpelExpressionParser();

    /**
     * A SpEL expression to use for collection name.
     */
    private String collectionNameExpression;

    /**
     * A literal collection name to use when persisting to a document.
     */
    private String collectionName;

    public Expression getCollectionNameExpression() {
        return collectionName != null ? new LiteralExpression(collectionName) : parser.parseExpression(collectionNameExpression);
    }

    public void setCollectionNameExpression(String collectionNameExpression) {
        this.collectionNameExpression = collectionNameExpression;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public boolean isCollectionNameProvided() {
        return StringUtils.hasText(collectionName) || StringUtils.hasText(collectionNameExpression);
    }

}

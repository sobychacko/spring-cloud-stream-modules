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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.cloud.stream.annotation.Bindings;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.module.mongodb.sink.domain.Customer;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This is an integration test that requires a MongoDB server instance to be running.
 *
 * Currently assuming default connection settings.
 *
 * @author Soby Chacko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MongodbSinkApplication.class)
@WebIntegrationTest({"collectionName=test"})
@DirtiesContext
public class MongodbSinkApplicationTests {

    @Autowired
    @Bindings(MongodbSink.class)
    private Sink sink;

    @Autowired
    private MongoOperations mongoTemplate;

    @After
    public void cleanup() {
        mongoTemplate.dropCollection("test");
    }

    @Test
    public void contextLoads() {
        assertNotNull(this.sink.input());
    }

    @Test
    public void testSink() {

        Message<Customer> message = new GenericMessage<>(new Customer("Foo", "Bar"));

        List<Customer> allBefore = mongoTemplate.findAll(Customer.class, "test");
        assertEquals(0, allBefore.size());

        sink.input().send(message);

        List<Customer> allAfter = mongoTemplate.findAll(Customer.class, "test");
        assertEquals(1, allAfter.size());

        Customer customer = allAfter.get(0);
        assertEquals(customer.getFirstName(), "Foo");
        assertEquals(customer.getLastName(), "Bar");
    }

}


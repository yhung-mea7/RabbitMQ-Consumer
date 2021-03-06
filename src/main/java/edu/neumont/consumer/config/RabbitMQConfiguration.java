package edu.neumont.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    private static final String QUEUE = "emailQueue";
    private static final String TOPIC = "emailExchange";
    private static final String serviceQueue = "serviceQueue";
//    private static final String serviceExchange = "serviceExchange";

    @Bean
    public Queue queue()
    {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Queue serviceQueue()
    {
        return new Queue(serviceQueue, true);
    }
//
//    @Bean
//    public TopicExchange serviceExchange()
//    {
//        return new TopicExchange(serviceExchange);
//    }

    @Bean
    public TopicExchange exchange()
    {
        return new TopicExchange(TOPIC);
    }

    @Bean
    public Binding binding(TopicExchange exchange)
    {
        return BindingBuilder.bind(queue()).to(exchange).with("email.*");
    }

    @Bean
    public Binding serviceBinding(TopicExchange exchange)
    {
        return BindingBuilder.bind(serviceQueue()).to(exchange).with("service.*");
    }

}

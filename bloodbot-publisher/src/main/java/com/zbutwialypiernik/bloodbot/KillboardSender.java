package com.zbutwialypiernik.bloodbot;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

public class KillboardSender {

    private final static String QUEUE_NAME = "bloodbot.killboard";

    private final Connection connection;

    public KillboardSender(Connection connection) throws IOException {
        this.connection = connection;
        Channel channel = connection.createChannel();



        //channel.basicPublish("bloodbot", "killboard", null, );

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}

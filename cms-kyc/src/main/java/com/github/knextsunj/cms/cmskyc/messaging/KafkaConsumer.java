package com.github.knextsunj.cms.cmskyc.messaging;

public interface KafkaConsumer {

    void receive(String message);
}

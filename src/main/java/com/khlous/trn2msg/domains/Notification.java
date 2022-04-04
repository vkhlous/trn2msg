package com.khlous.trn2msg.domains;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.khlous.trn2msg.common.Common;
import com.khlous.trn2msg.common.TransactionDecoder;
import com.khlous.trn2msg.domains.serializers.MessageListSerializer;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Map;

@JsonTypeName("root")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@Slf4j
public class Notification {

    @JsonProperty("msg-list")
    @JsonSerialize(using = MessageListSerializer.class)
    private final LinkedList<Message> msgList;

    @JsonProperty("totals")
    @JsonSerialize(as = Totals.class)
    private final Totals totals;

    public Notification() {
        this.msgList = new LinkedList<>();
        this.totals = new Totals();
    }

    // Faulty messages are NOT counted in "Totals".
    public void addMessage(char[] transactionString) {
        Message message = new Message(transactionString);
        this.msgList.add(message);
        if (message.isFaulty() == false) {
            this.totals.addCount();
            this.totals.addSum(message.getAmount());
        }
    }

    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error(String.format("ERROR: Notification object can not be serialized. Exception desc: %s", e.getMessage()));
        }
        return null;
    }
}

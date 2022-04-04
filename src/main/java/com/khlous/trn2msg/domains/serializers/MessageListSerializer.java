package com.khlous.trn2msg.domains.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.khlous.trn2msg.domains.Message;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MessageListSerializer extends JsonSerializer<LinkedList<Message>> {

    @Override
    public void serialize(LinkedList<Message> messages, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        jsonGenerator.writeStartArray();

        for (Message msg : messages) {
            // Faulty messages are NOT displayed in notification file.
            if (msg.isFaulty() == false){
                jsonGenerator.writeString(msg.getMsgString());
            }
        }

        jsonGenerator.writeEndArray();
    }
}

package com.khlous.trn2msg.domains.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.khlous.trn2msg.domains.Message;

import java.io.IOException;
import java.util.LinkedList;

public class SumSerializer extends JsonSerializer<Double> {
    @Override
    public void serialize(Double sum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString( String.format("%.2f", sum));
    }
}

package com.khlous.trn2msg.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.khlous.trn2msg.domains.serializers.SumSerializer;

import java.time.LocalDateTime;

import static com.khlous.trn2msg.common.Common.C_TOTALS_DATE_TIME_PATTERN;

public class Totals {

    @JsonProperty("cnt")
    private int count = 0;

    @JsonProperty("sum")
    @JsonSerialize(using= SumSerializer.class)
    private double sum = 0;

    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = C_TOTALS_DATE_TIME_PATTERN)
    private LocalDateTime date = LocalDateTime.now();

    protected void addCount() {
        this.count++;
    }

    protected int getCount( ) {
        return count;
    }

    protected void addSum(double sum) {
        this.sum += sum;
    }
}

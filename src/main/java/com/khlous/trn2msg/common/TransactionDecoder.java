package com.khlous.trn2msg.common;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TransactionDecoder {

    public static Map<Common.Field, char[]> decodeToMap(char[] transactionString) throws Exception {
        log.info("Start transaction line decoding into map.");
        int length = String.valueOf(transactionString).replaceAll("[^\\d.]", "").length(); // Non digits chars check.
        if (length == Common.C_TRANSACTION_LINE_LENGTH) {
            Map<Common.Field, char[]> transactionByField = new HashMap<>();
            int curPos = 0;

            //Looping through enum to split transaction line by fields.
            for (Common.Field f : Common.Field.values()) {
                char[] temp = new char[f.length];
                System.arraycopy(transactionString, curPos, temp, 0, f.length);
                curPos += f.length;
                transactionByField.put(f, temp);
            }
            log.info("End. Transaction line decoded into map.");

            return transactionByField;
        } else {
            throw new IllegalArgumentException(String.format("Transaction line has incorrect size - %d. Line will not be processed.",length));
        }
    }
}

package com.khlous.trn2msg.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khlous.trn2msg.common.Common;
import com.khlous.trn2msg.common.TransactionDecoder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;

@Slf4j
public class Message {

    private String transactionTypeStr;

    private String pan;

    private double amount;

    private String currencyStr;

    private String dateStr;

    private String errorMsg;
    private boolean error = false;

    public String getMsgString() {
        return String.format(Common.C_MSG_PATTERN, this.transactionTypeStr, this.pan, this.dateStr, this.amount, this.currencyStr);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public boolean isFaulty(){
        return error;
    }


    public Message(char[] transactionString){
        log.info("Start message generation from transaction string.");
        log.info(String.format("Transaction string: \"%s\"", String.valueOf(transactionString))); // Security flaw - pan is not masked.
        try {
            Map<Common.Field, char[]> transactionByFields = TransactionDecoder.decodeToMap(transactionString);
            setTransactionType(transactionByFields);
            setAmount(transactionByFields);
            setCurrency(transactionByFields);
            setDate(transactionByFields);
            setPan(transactionByFields);
            log.info(String.format("Message generated: \"%s\"", getMsgString()));
        } catch (Exception e) {
            this.error = true;
            this.errorMsg = e.getMessage();
            log.error(String.format("ERROR: Message can not be generated from transaction string. Transaction string: \"%s\". Exception desc: %s", String.valueOf(transactionString), e.getMessage()));
        }
        log.info("End message generation.");
    }

    private void setTransactionType(Map<Common.Field, char[]> transactionByFields) throws Exception {
        char[] transactionType = transactionByFields.get(Common.Field.TRANSACTION_TYPE);
        int temp = Integer.parseInt(String.valueOf(transactionType));
        this.transactionTypeStr = Common.TransactionType.getTransactionTypeById(temp).name();
    }

    private void setPan(Map<Common.Field, char[]> transactionByFields) {
        char[] pan = transactionByFields.get(Common.Field.PAN);
        this.pan = maskPAN(pan);
    }

    private void setAmount(Map<Common.Field, char[]> transactionByFields) {
        char[] amount = transactionByFields.get(Common.Field.AMOUNT);
        double temp = Double.parseDouble(String.valueOf(amount));
        // Separating minor units
        this.amount = temp/(Math.pow(10, Common.C_CCY_EXPONENT));
    }

    private void setCurrency(Map<Common.Field, char[]> transactionByFields) throws Exception {
        char[] currency = transactionByFields.get(Common.Field.CCY);
        int temp = Integer.parseInt(String.valueOf(currency));
        this.currencyStr = Common.Currency.getCurrencyById(temp).name();
    }

    private void setDate(Map<Common.Field, char[]> transactionByFields) {
        char[] date = transactionByFields.get(Common.Field.TRANSACTION_TIME);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Common.C_T_FILE_DATE_TIME_PATTERN);
        LocalDateTime temp = LocalDateTime.parse(String.valueOf(date), formatter);
        formatter = DateTimeFormatter.ofPattern(Common.C_MSG_DATE_TIME_PATTERN);

        this.dateStr = temp.format(formatter);
    }

    // Masks pan 6+4
    private String maskPAN(char[] pan) {
        Arrays.fill(pan, Common.C_PAN_UNMASK_PREFIX_LENGTH, pan.length - Common.C_PAN_UNMASK_SUFFIX_LENGTH, Common.C_PAN_MASK_CHAR);
        return String.valueOf(pan);
    }

    protected double getAmount(){
        return this.amount;
    }
}
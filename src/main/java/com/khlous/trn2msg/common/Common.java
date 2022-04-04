package com.khlous.trn2msg.common;

public class Common {

    public static final String C_MSG_PATTERN = "%s with card %s on %s, amount %.2f %s";
    public static final String C_MSG_DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm";
    public static final String C_TOTALS_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String C_T_FILE_DATE_TIME_PATTERN = "yyyyMMddHHmmss";
    public static final int C_TRANSACTION_LINE_LENGTH = 47;
    public static final int C_CCY_EXPONENT = 2;
    public static final int C_PAN_UNMASK_PREFIX_LENGTH = 6;
    public static final int C_PAN_UNMASK_SUFFIX_LENGTH = 4;
    public static final char C_PAN_MASK_CHAR = '*';

    public enum TransactionType {
        PURCHASE(0, "Purchase"),
        WITHDRAWAL(1, "Withdrawal");

        private final int id;
        private final String value;
        TransactionType(int id, String value) {
            this.id = id;
            this.value = value;
        }
        
        public static TransactionType getTransactionTypeById(int id) throws Exception {
            for (TransactionType tt : TransactionType.values()) {
                if(tt.id == id){
                    return tt;
                }
            }
            throw new Exception(String.format("No Transaction Type with ID %d exist.", id));
        }

        public String getTransactionTypeValue(){
            return value;
        }
    }

    // For a bigger scale project would be beneficial to preload values from file/db and store in a map for more efficient lookup.
    public enum Currency {
        USD(840, "usd"),
        EUR(978, "eur"),
        GPB(826, "gpb"),
        RUB(643, "rub");

        private final int id;
        private final String value;
        Currency(int id, String value) {
            this.id = id;
            this.value = value;
        }

        public static Currency getCurrencyById(int id) throws Exception {
            for (Currency currency : Currency.values()) {
                if(currency.id == id){
                    return currency;
                }
            }
            throw new Exception(String.format("No Currency with ID %d exist.", id));
        }

        public String getCurrencyValue(){
            return value;
        }
    }

    public enum Field {
        TRANSACTION_TYPE(1, 2),
        PAN(2, 16),
        AMOUNT(3, 12),
        TRANSACTION_TIME(4, 14),
        CCY(5, 3);

        public final int id;
        public final int length;

        Field(int id, int length) {
            this.id = id;
            this.length = length;
        }
    }
}

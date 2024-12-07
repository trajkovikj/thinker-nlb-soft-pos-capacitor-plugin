package io.thinker.plugins.nlbsoftpos.responses;

public class TransactionResponseStatusFactory {
    public static TransactionResponseStatus getStatusByKey(String key) {
        if(key.equals("EXECUTED"))
            return new TransactionResponseStatus("EXECUTED", 1000);

        if(key.equals("REJECTED"))
            return new TransactionResponseStatus("REJECTED", 1001);

        if(key.equals("TIMEOUT"))
            return new TransactionResponseStatus("TIMEOUT", 1002);

        if(key.equals("NETWORK_ERROR"))
            return new TransactionResponseStatus("NETWORK_ERROR", 1003);

        if(key.equals("VALIDATION_ERROR"))
            return new TransactionResponseStatus("VALIDATION_ERROR", 2001);

        if(key.equals("RESPONSE_JSON_PARSE_ERROR"))
            return new TransactionResponseStatus("RESPONSE_JSON_PARSE_ERROR", 2002);

        if(key.equals("MISSING_RESPONSE_DATA"))
            return new TransactionResponseStatus("MISSING_RESPONSE_DATA", 2003);

        if(key.equals("NLB_STATUS_CODE_NOT_MAPPED"))
            return new TransactionResponseStatus("NLB_STATUS_CODE_NOT_MAPPED", 2004);

        return new TransactionResponseStatus("UNKNOWN_ERROR", 2000);
    }

    public static TransactionResponseStatus getStatusByNlbCode(String nlbCode) {
        if(nlbCode.equals("00"))
            return TransactionResponseStatusFactory.getStatusByKey("EXECUTED");

        if(nlbCode.equals("05"))
            return TransactionResponseStatusFactory.getStatusByKey("REJECTED");

        if(nlbCode.equals("82"))
            return TransactionResponseStatusFactory.getStatusByKey("TIMEOUT");

        if(nlbCode.equals("networkError"))
            return TransactionResponseStatusFactory.getStatusByKey("NETWORK_ERROR");

        return TransactionResponseStatusFactory.getStatusByKey("NLB_STATUS_CODE_NOT_MAPPED");
    }

    public static TransactionResponseStatus getUnknownErrorResponseStatus() {
        return TransactionResponseStatusFactory.getStatusByKey("UNKNOWN_ERROR");
    }
}

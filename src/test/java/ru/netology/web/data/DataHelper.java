package ru.netology.web.data;

import lombok.Value;


public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }


    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }


    @Value
    public static class Card {
        String number;
        int balance;
        String id;
    }

    public static Card getFirstCardInfo() {
        return new Card("5559 0000 0000 0001", 10000, null);
    }

    public static Card getSecondCardInfo() {
        return new Card("5559 0000 0000 0002", 10000, null);
    }
}

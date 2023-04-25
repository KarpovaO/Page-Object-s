package ru.netology.web.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.web.data.DataHelper.getAuthInfo;

class MoneyTransferTest {

    final Random rand = new Random();

    @BeforeAll
    static void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {

        DashboardPage dashboardPage = new DashboardPage();
        DataHelper.Card[] cards = dashboardPage.getAllCardsInfo();

        DataHelper.Card firstCard = cards[0];
        DataHelper.Card secondCard = cards[1];
        TransferPage transferPage = dashboardPage.selectCard(firstCard.getId());

        int amount = rand.nextInt(secondCard.getBalance());

        transferPage.transfer(secondCard.getNumber(), amount);

        int expectedFirstCardBalance = firstCard.getBalance() + amount;
        int expectedSecondCardBalance = secondCard.getBalance() - amount;

        int actualFirstCardBalance = dashboardPage.getCardBalance(firstCard.getId());
        int actualSecondCardBalance = dashboardPage.getCardBalance(secondCard.getId());

        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

}

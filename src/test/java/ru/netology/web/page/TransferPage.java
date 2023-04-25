package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.page;

public class TransferPage {
    @FindBy(css = "[data-test-id=amount] input")
    private SelenideElement amountElem;

    @FindBy(css = "[data-test-id=from] input")
    private SelenideElement fromElem;

    @FindBy(css = "[data-test-id=action-transfer]")
    private SelenideElement action;

    public void transfer (String fromCardNumber, Integer amount) {
        fromElem.setValue(fromCardNumber);
        amountElem.setValue(amount.toString());
        action.click();
    }


}

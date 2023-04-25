package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.openqa.selenium.support.FindBy;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.web.data.DataHelper.getFirstCardInfo;
import static ru.netology.web.data.DataHelper.getSecondCardInfo;

public class DashboardPage {
  private final ElementsCollection cards = $$(".list__item div");
  private final String balanceStart = "баланс: ";
  private final String balanceFinish = " р.";
  @FindBy(css = "[data-test-id=action-deposit]")
  private SelenideElement button_hovered;


  public DashboardPage() {
    SelenideElement heading = $("[data-test-id=dashboard]");
    heading.shouldBe(visible);
  }

  public TransferPage selectCard(){
    button_hovered.click();
    return page(TransferPage.class);
  }

  public TransferPage selectCard(String id){
    SelenideElement elem = $("[data-test-id=\"" + id + "\"]");
    SelenideElement depositBtn = elem.$("[data-test-id=action-deposit]");
    depositBtn.click();
    return page(TransferPage.class);
  }

  public DataHelper.Card[] getAllCardsInfo() {
    ElementsCollection cardElems = $$(".list__item");
    DataHelper.Card[] cards = new DataHelper.Card[cardElems.size()];
    int i = 0;
    for (SelenideElement elem : cardElems) {
      SelenideElement cardInfo = elem.$("[data-test-id]");
      String cardNumber;
      if (i == 0) {
        cardNumber = getFirstCardInfo().getNumber();
      }
      else {
        cardNumber = getSecondCardInfo().getNumber();
      }
      String id = cardInfo.data("test-id");
      String text = cardInfo.text();
      int balance = extractBalance(text);
      cards[i++] = new DataHelper.Card(cardNumber, balance, id);
    }
    return cards;
  }

  public int getCardBalance(String id) {
      // TODO: перебрать все карты и найти по атрибуту data-test-id
      SelenideElement elem = $("[data-test-id=\"" + id + "\"]");
      String text = elem.text();
      return extractBalance(text);
    }

    private int extractBalance(String text) {
      val start = text.indexOf(balanceStart);
      val finish = text.indexOf(balanceFinish);
      val value = text.substring(start + balanceStart.length(), finish);
      return Integer.parseInt(value);
    }
  }


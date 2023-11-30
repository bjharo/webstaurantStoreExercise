package pageobjects.modals;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import pageobjects.PageBase;

public class EmptyCartModal extends PageBase {
    @FindBy(css="ddiv[role = 'alertdialog']")
    private WebElement parent;

    @FindBy(css="div[role = 'alertdialog'] button.bg-green-500")
    private WebElement emptyCartBtn;

    public EmptyCartModal(SearchContext context) {
       super(context);
    }

    public void emptyCart() {
        wait.until(e -> emptyCartBtn.isDisplayed());
        emptyCartBtn.click();
    }
}

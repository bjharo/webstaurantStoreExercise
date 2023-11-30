package pageObjects.widgets;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageObjects.PageBase;

public class AddedToCartToastWidget extends PageBase {
    @FindBy(css="[data-role = 'notification'] .notification__description")
    private WebElement itemDescription;

    @FindBy(css="[data-role = 'notification'] a[href *= 'viewcart']")
    private WebElement viewCartBtn;

    @FindBy(css="[data-role = 'notification']")
    private WebElement parent;

    public AddedToCartToastWidget(SearchContext context) {
        super(context);
    }

    public String getDescription() {
        return itemDescription.getText();
    }

    public void goToCart() {
        viewCartBtn.click();
    }

    public boolean isToastDisplayed() {
        return waitForElementToBeDisplayed(parent);
    }
}

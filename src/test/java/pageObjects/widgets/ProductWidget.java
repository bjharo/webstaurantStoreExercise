package pageObjects.widgets;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageObjects.PageBase;

public class ProductWidget extends PageBase {

    @FindBy(css="[data-testid = 'itemDescription']")
    private WebElement description;

    @FindBy(css="[data-testid = 'itemAddCart']")
    private WebElement addToCartBtn;

    @FindBy(css=".pricing table > tbody > tr > td:nth-child(1)")
    private WebElement smallQtyPrice;

    public ProductWidget(SearchContext context) {
        super(context);
    }

    public boolean isAddToCartButtonDisplayed() {
        try {
            return waitForElementToBeDisplayed(addToCartBtn);
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public String getDescription() {
        return description.getText();
    }

    public String getSmallQtyPrice() {
        return smallQtyPrice.getText();
    }

    public void clickAddToCart() {
        addToCartBtn.click();
    }
}

package pageObjects.widgets;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageObjects.PageBase;

public class CartItemWidget extends PageBase {
    @FindBy(css=".itemDescription")
    private WebElement description;

    @FindBy(css=".itemPrice")
    private WebElement price;

    @FindBy(css="[data-testid = 'total-price']")
    private WebElement totalPrice;

    @FindBy(css="input.quantityInput")
    private WebElement quantityField;

    public CartItemWidget(SearchContext context) {
        super(context);
    }

    public String getDescription() {
        return description.getText();
    }

    public String getPrice() {
        return price.getText();
    }

    public String getQuantity() {
        return quantityField.getAttribute("value");
    }

    public String getTotal() {
        return totalPrice.getText();
    }
}

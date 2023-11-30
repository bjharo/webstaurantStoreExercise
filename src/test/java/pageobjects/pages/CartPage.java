package pageobjects.pages;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.widgets.CartItemWidget;
import pageobjects.PageBase;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends PageBase {
    @FindBy(css=".emptyCartButton")
    private WebElement emptyCartButton;

    @FindBy(css=".cartItem")
    private List<WebElement> cartItems;

    @FindBy(css=".cartEmpty .empty-cart__text")
    private WebElement emptyCartText;

    public CartPage(SearchContext context) {
        super(context);
    }

    public void emptyCart() {
        emptyCartButton.click();
    }

    public List<CartItemWidget> getCartItems() {
        // check for at least one item in the cart before we try to return them
        // if there are no items, return an empty list
        if (!waitForElementToBeDisplayed(cartItems.get(0))) {
            return new ArrayList<>();
        }
        return cartItems.stream().map(CartItemWidget::new).toList();
    }

    public String getEmptyCartText() {
        wait.until(e -> emptyCartText.isDisplayed());
        return emptyCartText.getText();
    }

    public boolean isEmptyCartMessageDisplayed(String message) {
        return waitForElementToContainText(emptyCartText, message);
    }
}

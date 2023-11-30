package actions;

import org.openqa.selenium.WebDriver;
import pageObjects.modals.EmptyCartModal;
import pageObjects.pages.CartPage;
import pageObjects.pages.ProductListPage;
import pageObjects.widgets.AddedToCartToastWidget;
import pageObjects.widgets.HeaderRowWidget;
import pageObjects.widgets.ProductWidget;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestActions {
    final private WebDriver webDriver;

    public TestActions(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void performSearchForItems(String searchTerm) {
        var headerRow = new HeaderRowWidget(webDriver);
        headerRow.performSearch(searchTerm);
    }

    public List<ProductWidget> getAllItemsFromPage() {
        var productListPage = new ProductListPage(webDriver);
        return productListPage.getProducts();
    }

    public ProductWidget getLastAvailableItem() {
        var productListPage = new ProductListPage(webDriver);
        var availableItems = productListPage.getAvailableProducts();

        assertNotEquals(0, availableItems, "There are no items on the page that are available to " +
                "add to a cart.");

        return availableItems.get(availableItems.size() - 1);
    }

    public void addItemToCart(ProductWidget item) {
        assertTrue(item.isAddToCartButtonDisplayed(), "Unable to find the Add to Cart button on the " +
                "item '" + item.getDescription() + "'.");

        item.clickAddToCart();
    }

    public void verifyAddToCartToastAndGoToCart(String itemDescription) {
        var toast = new AddedToCartToastWidget(webDriver);

        assertTrue(toast.isToastDisplayed(), "Could not confirm that the toast for adding an item to the " +
                "cart was displayed.");
        assertEquals(itemDescription, toast.getDescription(), "The name of the item on the toast does not match " +
                "the one added to the cart.");

        toast.goToCart();
    }

    public void verifySingleItemInCart(String itemDescription, int itemQuantity, BigDecimal price) {
        var dollarFormat = new DecimalFormat("$#.##");
        var cartPage = new CartPage(webDriver);
        var itemsInCart = cartPage.getCartItems();

        assertEquals(1, itemsInCart.size(), "The number of items in the cart is not correct.");

        var item = itemsInCart.get(0);

        assertEquals(itemDescription, item.getDescription(), "The description of the item in " +
                "the cart is not correct.");
        assertEquals(String.valueOf(itemQuantity), item.getQuantity(), "The quantity of the item in the cart " +
                "is not correct.");
        assertEquals(dollarFormat.format(price), item.getPrice(), "The price of the item in the cart is not " +
                "correct.");
        assertEquals(dollarFormat.format(price.multiply(BigDecimal.valueOf(itemQuantity))), item.getTotal(),
                "The total cost of the item in the cart is not correct.");
    }

    public void emptyCart() {
        var cartPage = new CartPage(webDriver);
        var modal = new EmptyCartModal(webDriver);

        cartPage.emptyCart();
        modal.emptyCart();
    }

    public void verifyEmptyCart() {
        var cartPage = new CartPage(webDriver);
        assertTrue(cartPage.isEmptyCartMessageDisplayed("Your cart is empty."), "Could not verify that the " +
                "cart is empty.");
    }

    public void verifyPageSearchResults(final List<ProductWidget> itemsFromPage,
                                         final String expectedSubstring,
                                         final int pageNumber) {

        var itemDescriptions = itemsFromPage.stream().map(ProductWidget::getDescription).toList();
        assertNotEquals(0, itemsFromPage.size(), "There were not results returned by the search.");
        assertAll(
                () -> itemDescriptions.forEach(item -> assertTrue(item.contains(expectedSubstring),
                        "Expected '" + expectedSubstring + "' in '" + item + "' on page " + pageNumber + "."))
        );
    }

    public boolean goToNextPageOfResultsIfExists() {
        var productListPage = new ProductListPage(webDriver);
        var moreResults = productListPage.isThereAnotherPageOfResults();

        if (moreResults) {
            productListPage.goToNextResultsPage();
        }

        return moreResults;
    }
}

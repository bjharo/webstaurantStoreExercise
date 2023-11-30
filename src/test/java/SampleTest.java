import actions.TestActions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleTest {
    final private String url = "https://www.webstaurantstore.com/";
    final private ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    final private ThreadLocal<TestActions> actions = new ThreadLocal<>();

    @BeforeEach
    public void setup() {
        var chromeOptions = new ChromeOptions();
        var headlessEnVar = System.getProperty("use.headless");

        if (headlessEnVar != null && headlessEnVar.equalsIgnoreCase("true")) {
            chromeOptions.addArguments("--headless=new");
        }

        var driver = WebDriverManager.chromedriver().capabilities(chromeOptions).create();
        webDriver.set(driver);
        webDriver.get().manage().window().maximize();

        actions.set(new TestActions(webDriver.get()));

        webDriver.get().get(url);
    }

    @AfterEach
    public void teardown() {
        actions.remove();
        webDriver.get().quit();
        webDriver.remove();
    }

    @Test
    @DisplayName("Search and Verify First Page of Results Match and Add Last Available (Not Sold-Out) Item to Cart")
    public void verifySearchResultsAndAddLastItemToCart() {
        final var searchTerm = "stainless work table";
        final var expectedSearchSubstring = "Table";

        actions.get().performSearchForItems(searchTerm);

        final var itemsFromPage = actions.get().getAllItemsFromPage();
        // we cannot reliably assume that the last item on the page can be added to the cart as it could be sold out
        // so, get the last item on the page that is available (has an add to cart button) to be added to the cart
        final var finalAvailableItem = actions.get().getLastAvailableItem();
        final var finalItemDescription = finalAvailableItem.getDescription();
        final var finalItemSmallQtyPrice = new BigDecimal(finalAvailableItem.getSmallQtyPrice().replaceAll("[^0-9.]", ""));

        actions.get().verifyPageSearchResults(itemsFromPage, expectedSearchSubstring, 1);
        actions.get().addItemToCart(finalAvailableItem);
        actions.get().verifyAddToCartToastAndGoToCart(finalItemDescription);
        actions.get().verifySingleItemInCart(finalItemDescription, 1, finalItemSmallQtyPrice);
        actions.get().emptyCart();
        actions.get().verifyEmptyCart();
    }

    @Test
    @DisplayName("Search and Verify All Pages of Results Match (Up to 20 Pages)")
    public void verifyResultsFromAllPages() {
        final var searchTerm = "stainless work table";
        final var expectedSearchSubstring = "Table";

        actions.get().performSearchForItems(searchTerm);

        var pageNumber = 1;

        do {
            var itemsOnPage = actions.get().getAllItemsFromPage();

            actions.get().verifyPageSearchResults(itemsOnPage, expectedSearchSubstring, pageNumber);

            if (!actions.get().goToNextPageOfResultsIfExists()) {
                break;
            }

            pageNumber++;
        } while (pageNumber <= 20); // prevents the tests from taking too long when there are a massive amount of results
    }
}

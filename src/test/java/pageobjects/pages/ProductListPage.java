package pageobjects.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import pageobjects.PageBase;
import pageobjects.widgets.ProductWidget;

import java.util.ArrayList;
import java.util.List;

public class ProductListPage extends PageBase {
    @FindBy(css="[data-testid = 'productBoxContainer']")
    private List<WebElement> productBoxes;

    @FindBy(xpath="//div[contains(@data-testid, 'productBoxContainer')][.//input[contains(@data-testid, 'itemAddCart')]]")
    private List<WebElement> availableProductBoxes;

    @FindBy(css="#paging li.rounded-r-md a[aria-label *= 'go to page']")
    private WebElement goToNextPageBtn;

    public ProductListPage(SearchContext context) {
        super(context);
    }

    public List<ProductWidget> getProducts() {
        if (!waitForElementToBeDisplayed(productBoxes.get(0))) {
            return new ArrayList<>();
        }
        return productBoxes.stream().map(ProductWidget::new).toList();
    }

    public List<ProductWidget> getAvailableProducts() {
        if (!waitForElementToBeDisplayed(availableProductBoxes.get(0))) {
            return new ArrayList<>();
        }
        return availableProductBoxes.stream().map(ProductWidget::new).toList();
    }

    public boolean isThereAnotherPageOfResults()
    {
        return waitForElementToBeDisplayed(goToNextPageBtn);
    }

    public void goToNextResultsPage()
    {
        goToNextPageBtn.click();
    }
}

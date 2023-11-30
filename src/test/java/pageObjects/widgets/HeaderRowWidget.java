package pageObjects.widgets;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageObjects.PageBase;

public class HeaderRowWidget extends PageBase {

    @FindBy(css="[data-testid = 'searchval']")
    private WebElement searchField;

    @FindBy(css="#searchForm button[type = 'submit']")
    private WebElement searchBtn;

    public HeaderRowWidget(SearchContext context) {
        super(context);
    }

    public void performSearch(String searchTerm)
    {
        searchField.clear();
        searchField.sendKeys(searchTerm);
        searchBtn.click();
    }
}

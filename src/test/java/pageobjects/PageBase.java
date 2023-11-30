package pageobjects;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public abstract class PageBase {
    final protected SearchContext context;
    final protected FluentWait<SearchContext> wait;

    public PageBase(SearchContext context)
    {
        this.context = context;
        this.wait = new FluentWait<>(context)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
        PageFactory.initElements(context, this);
    }

    protected boolean waitForElementToBeDisplayed(WebElement element) {
        try {
            return wait.until(e -> element.isDisplayed());
        } catch (TimeoutException ex) {
            return false;
        }
    }

    protected boolean waitForElementToContainText(WebElement element, String expectedText) {
        try {
            return wait.until(e -> element.getText().contains(expectedText));
        } catch (TimeoutException ex) {
            return false;
        }
    }
}

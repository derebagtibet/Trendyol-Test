import net.bytebuddy.asm.Advice;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TrendyolBV {
    private final static int TIMEOUT = 5;
    String url;
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    String searchInput;



    public TrendyolBV(String url,String searchInput) {
        this.searchInput = searchInput;
        this.url = url;
        initializeDriver();
        initializeWait();
        initializeActions();
    }

    private void initializeWait() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }

    private void initializeActions() {
        actions = new Actions(driver);
    }

    private void initializeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-infobars"); //
        options.addArguments("--whitelisted-ips=''"); //options
        options.addArguments("--disable-notifications"); //
        options.addArguments("--start-maximized"); //
        driver = new ChromeDriver(options);
    }

    void giveInputsToSearchBar(String input) {
        WebElement clickOnGender = wait.until(driver ->
                driver.findElement(By.xpath("//*[@id=\"gender-popup-modal\"]/div/div/div[2]/div/div[2]/a/span[2]"))
        );
        clickOnGender.click();

        WebElement searchBox = wait.until(driver ->
                driver.findElement(By.xpath("//input[@tabindex='1']"))
        );
    //  String val = searchBox.getAttribute("value");
    //  System.out.println(val);  EN SON SORUNUMUZ TEST İÇİN 50 KERE A GİRDİKTEN SONRA BUNU
    //  HTML İÇİNDEN VALUE ÇEKİP YANİ 50 TANE A YI ÇEKİP İNPUT İLE ASSERTEQUALS YAP
        searchBox.clear();
        searchBox.click();
        searchBox.sendKeys(input);
        searchBox.sendKeys(Keys.ENTER);

    }

    public boolean isProductVisibleByTitleText(String productTitlePart) {
        try {
            WebElement productTitle = driver.findElement(By.xpath(
                    "//span[contains(@class, 'prdct-desc-cntnr-name') and contains(text(), '" + productTitlePart + "')]"
            ));
            return productTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }




    void connect() {
        driver.get(url);
    }

    WebElement getSearchBox(){
        return driver.findElement(By.xpath("//input[@tabindex='1']"));
    }

    void run() {
        connect();
        giveInputsToSearchBar(searchInput); // bu fonksiyonun inputunu nerde vericen yada inputsuz çalıştırabilirmisin
        quit();
    }

     void quit() {
    driver.quit();
    }

    public static void main(String[] args) {

        TrendyolBV test = new TrendyolBV("https://www.trendyol.com/","a");
        test.run();
    }
}
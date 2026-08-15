import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TrendyolPartitioning {
    private static final int TIMEOUT = 5;
    String url;
    Actions actions;
    WebDriver webDriver;
    WebDriverWait webDriverWait;

    public TrendyolPartitioning(String url) {
        this.url = url;
        initializeDriver();
        initializeWait();
        initializeActions();
    }

    private void initializeWait() {
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(TIMEOUT));
    }
    private void initializeActions() {
        actions = new Actions(webDriver);
    }

    private void initializeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-infobars"); //
        options.addArguments("--whitelisted-ips=''"); //options
        options.addArguments("--disable-notifications"); //
        webDriver = new ChromeDriver(options);
    }

    public boolean checkEmail(String email){
        try{
            WebElement emailInput = webDriver.findElement(By.xpath("//*[@id=\"login-email\"]"));
            emailInput.clear();
            emailInput.click();
            emailInput.sendKeys(email);
            emailInput.sendKeys(Keys.ENTER);
            WebElement clickOnPassword = webDriver.findElement(By.xpath("//*[@id=\"login-password-input\"]"));
            clickOnPassword.click();
            clickOnPassword.clear();
            clickOnPassword.sendKeys("Dipoldipol2");



            WebElement clickOnButton = webDriver.findElement(By.xpath("//*[@id=\"login-register\"]/div[3]/div[1]/form/button"));
            clickOnButton.click();


            webDriverWait.until(ExpectedConditions.or(
                    ExpectedConditions.urlToBe("https://www.trendyol.com/"),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"error-box-wrapper\"]"))
            ));

            if (webDriver.getCurrentUrl().equals("https://www.trendyol.com/")) {
                return true;
            }

            WebElement errorBox = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[@id=\"error-box-wrapper\"]")
            ));
            String errorText = errorBox.getText();
            if (errorText.contains("E-posta adresiniz ve/veya şifreniz hatalı.")) {
                return true;
            }

            return false;

        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    void giveInputs(String input){

        WebElement clickOnPassword = webDriver.findElement(By.xpath("//*[@id=\"register-password-input\"]"));
        clickOnPassword.click();
        clickOnPassword.clear();
        clickOnPassword.sendKeys("123456.abC");

        WebElement clickOnEmail = webDriver.findElement(By.xpath("//*[@id=\"register-email\"]"));
        clickOnEmail.click();
        clickOnEmail.clear();
        clickOnEmail.sendKeys(input);

    }

    //şifre değiştirme için xpath //*[@id="change-password-container"]/form

    void connect(){
        webDriver.get(url);
    }




    void run(){
        connect();
        checkEmail("");
        quit();
    }

    void quit(){
        webDriver.quit();
    }

    public static void main(String[] args) {
        TrendyolPartitioning test = new TrendyolPartitioning("https://www.trendyol.com/giris?cb=%2F");
        test.run();
    }
}

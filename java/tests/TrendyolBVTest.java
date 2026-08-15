import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TrendyolBVTest {
    //arrange
    TrendyolBV toBeTested; ;
    @BeforeEach
    void setUp() {
        toBeTested = new TrendyolBV("https://www.trendyol.com/","");
        toBeTested.connect();
    }
    //act



    @Order(1)
    @Test
    void emptyStringInputsToSearchBar() {
        String secondInput = " ";
        toBeTested.giveInputsToSearchBar(secondInput);
        String secondValue = toBeTested.getSearchBox().getDomAttribute("value");
        System.out.println("Expected :" + secondValue);
        System.out.println("Actual   :" + secondInput);
        assertNotEquals(secondInput, secondValue);
    }

    @Order(2)
    @Test
    void giveOneInputToSearchBar() {
        String secondInput = "a";
        toBeTested.giveInputsToSearchBar(secondInput);
        String secondValue = toBeTested.getSearchBox().getDomAttribute("value");
        System.out.println("Expected :" + secondValue);
        System.out.println("Actual   :" + secondInput);
        assertNotEquals(secondInput, secondValue);
    }

    //2,3,4 verilerinde pil çıkıyor
    @Order(3)
    @Test
    void testProductVisibleForDuracellAAKalem() {
        String input = "aa";
        toBeTested.giveInputsToSearchBar(input);
        boolean isVisible = toBeTested.isProductVisibleByTitleText("Alkalin AA Kalem");
        assertTrue(isVisible);
    }
    //5,6,9,10 inputlarında bitane yağ çıkıyo
    @Order(4)
    @ParameterizedTest
    @ValueSource(ints = {5, 6, 9, 10})
    void testOilMixProductAppears(int inputLength) {
        String input = "a".repeat(inputLength);
        toBeTested.giveInputsToSearchBar(input);

        boolean isVisible = toBeTested.isProductVisibleByTitleText("Oil Mix");
        System.out.println("Input length: " + inputLength + " Product visible: " + isVisible);

        assertTrue(isVisible);
    }


    //11,12,13,14 lastikbasınç sensöro
    @Order(5)
    @ParameterizedTest
    @ValueSource(ints = {11, 12, 13, 14})
    void testIthalLastikProductAppears(int inputLength) {
        String input = "a".repeat(inputLength);
        toBeTested.giveInputsToSearchBar(input);

        boolean isVisible = toBeTested.isProductVisibleByTitleText("İTHAL LASTİK BASINÇ");
        System.out.println("Input length: " + inputLength + " Product visible: " + isVisible);

        assertTrue(isVisible);
    }



    //sevimlihayvan kupa bardak 15,16,18,19 
    @Order(6)
    @ParameterizedTest
    @ValueSource(ints = {15, 16, 18, 19})
    void testSevimliHayvanKupaBardakAppears(int inputLength) {
        String input = "a".repeat(inputLength);
        toBeTested.giveInputsToSearchBar(input);

        boolean isVisible = toBeTested.isProductVisibleByTitleText("Aaaaaaaaaaaaaaaaaaaaa");
        System.out.println("Input length: " + inputLength + " Product visible: " + isVisible);

        assertTrue(isVisible);
    }

    //
    @Order(7)
    @ParameterizedTest
    @ValueSource(ints = {30, 31, 39, 40})
    void testVitesTopuzuProductAppears(int inputLength) {
        String input = "a".repeat(inputLength);
        toBeTested.giveInputsToSearchBar(input);

        boolean isVisible = toBeTested.isProductVisibleByTitleText("Vites Topuzu Kia");
        System.out.println("Input length: " + inputLength + " Product visible: " + isVisible);

        assertTrue(isVisible);
    }

    @Order(8)
    @Test
    void test41CharLongInput() {
        String input = "a".repeat(41);
        toBeTested.giveInputsToSearchBar(input);

        try {
            WebElement noProductFoundMessage = new WebDriverWait(toBeTested.driver, Duration.ofSeconds(5))
                    .until(driver -> driver.findElement(By.xpath("//h2[contains(text(), 'aramanız için ürün bulunamadı')]")));

            assertTrue(noProductFoundMessage.isDisplayed(), "Message not displayed for 41-character input.");
        } catch (Exception e) {
            fail("Expected 'no product found' message not found: " + e.getMessage());
        }
    }



    @Order(9)
    @Test
    void wrongTestInputsToSearchBar(){
      String input = "a".repeat(51);
      toBeTested.giveInputsToSearchBar(input);
      String value = toBeTested.getSearchBox().getDomAttribute("value");
        System.out.println("Expected :" + value);
        System.out.println("Actual   :" + input);
      assertNotEquals(input, value);
    }

    @Order(10)
    @Test
    void trueInputs(){
        //DOĞRU DÖNMESİ GEREKİYOKEN YANLIŞ DÖNÜYOR NEDENİ TRENDYOL BV giveInputsToSearchbar()
        //fonksiyonunda yazıyor
        String input = "a".repeat(50);
        toBeTested.giveInputsToSearchBar(input);
        String value = toBeTested.getSearchBox().getDomAttribute("value");
        System.out.println("Expected :" + value);
        System.out.println("Actual   :" + input);
        assertEquals(input, value);
    }



    //assert
    @AfterEach
    void close() throws InterruptedException {
        Thread.sleep(3000);
        toBeTested.quit();
    }



}
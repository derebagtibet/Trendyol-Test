import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class TrendyolPartitioningTest {

    TrendyolPartitioning toBeTested;

// bir tane fonksiyonda denersen bütün testleri birtane assertion yanlış dönse bile fonksiyon duruyor devamı gelmiyor
// diğer türlü hepsi için ayrı fonksiyonda olursa 8 kere açılca
// üye ola daha basmıyoruz bide 3 tane kutucuğu seçip elle robot değilim dicez

    @BeforeEach
    void setUp() throws InterruptedException {
        toBeTested = new TrendyolPartitioning("https://www.trendyol.com/giris?cb=%2F");
        toBeTested.connect();


    }
//
//    @Test
//    void testAllEmailsManually() throws InterruptedException {
//
//        assertAll(
//                () -> assertTrue(toBeTested.checkEmail("cangere@hotmail.com"), "Geçerli e-posta başarısız"),
//                () -> assertFalse(toBeTested.checkEmail(""), "Boş e-posta geçerli sayıldı"),
//                () -> assertFalse(toBeTested.checkEmail("testexample.com"), "‘@’ eksik e-posta geçerli sayıldı"),
//                () -> assertFalse(toBeTested.checkEmail("test@"), "Domain eksik e-posta geçerli sayıldı"),
//                () -> assertFalse(toBeTested.checkEmail("@example.com"), "Local-part eksik e-posta geçerli sayıldı"),
//                () -> assertFalse(toBeTested.checkEmail("test@examplecom"), "Domain’de nokta yok ama geçerli sayıldı"), // galiba oldu 
//                () -> assertFalse(toBeTested.checkEmail("te st@exa!mple.com"), "Karakterleri bozuk e-posta geçerli sayıldı"),
//                () -> assertFalse(toBeTested.checkEmail("a".repeat(200) + "@example.com"), "Çok uzun e-posta geçerli sayıldı")
//        );
   // }

    //popup xpath = //*[@id="login-register"]/div[3]/div[1]/div/div/div


    @AfterEach
    void tearDown() throws InterruptedException {

        Thread.sleep(4000);
        toBeTested.quit();
    }

    @Test
    void testValidEmail() throws InterruptedException {
        //bazen üye ol basınca popup çıkmıyo
        assertTrue(toBeTested.checkEmail("cangere@hotmail.com"));

    }

    @Test
    void testEmptyEmail() {
        assertFalse(toBeTested.checkEmail(""));
    }

    @Test
    void testMissingAtSymbol() {
        assertFalse(toBeTested.checkEmail("testexample.com"));
    }

    @Test
    void trueInputs() throws InterruptedException {  assertTrue(toBeTested.checkEmail("morbardak8@gmail.com")); //link değiştimi diye kontrol etmek için bu inputu sonradan ekledim
    Thread.sleep(3000);}

    @Test
    void testMissingDomain() {
        assertFalse(toBeTested.checkEmail("test@"));
    }

    @Test
    void testMissingLocalPart() {
        assertFalse(toBeTested.checkEmail("@example.com"));
    }

    @Test
    void testNoDotInDomain() { assertFalse(toBeTested.checkEmail("test@examplecom")); }

    @Test
    void testInvalidCharacters() {
        assertFalse(toBeTested.checkEmail("te st@exa!mple.com"));
    }

    @Test
    void testOverlyLongEmail() {
        assertFalse(toBeTested.checkEmail("a".repeat(250) + "@example.com"));
    }
}






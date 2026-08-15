# SE2226 – Yazılım Testi Projesi (Trendyol)

Bu proje, **SE2226 Yazılım Testi** dersi kapsamında iki kara kutu (black-box) test
tasarım tekniğinin **canlı Trendyol e-ticaret sitesi** üzerinde Selenium WebDriver ile
nasıl uygulandığını gösterir:

- **Sınır Değer Analizi (Boundary Value Analysis)** — `TrendyolBV` / `TrendyolBVTest`:
  arama çubuğunun kabul ettiği giriş uzunluğu test edilir (1, 50 ve 51 karakter sınırları).
- **Eşdeğerlik Bölümleme (Equivalence Partitioning)** — `TrendyolPartitioning` /
  `TrendyolPartitioningTest`: giriş/kayıt formundaki e-posta girişleri geçerli/geçersiz
  sınıflara ayrılarak test edilir.

> ⚠️ **Önemli:** Testler mock kullanmaz; **gerçek Trendyol web sitesine** bağlanıp gerçek
> tarayıcıyı (Chrome) açar ve canlı DOM üzerinde doğrulama yapar. Bu yüzden testler kırılgandır:
> Trendyol'un HTML yapısı, CSS sınıfları veya Türkçe arayüz metinleri değişirse testler
> bozulabilir (bu durumda XPath/CSS seçicilerin güncellenmesi gerekir, mantık hatası değildir).

## Proje Yapısı

```
se2226/
├── java/
│   ├── src/                         # Selenium sayfa-sarmalayıcı sınıfları
│   │   ├── TrendyolBV.java
│   │   └── TrendyolPartitioning.java
│   └── tests/                       # JUnit 5 test sınıfları
│       ├── TrendyolBVTest.java
│       └── TrendyolPartitioningTest.java
├── selenium/                        # Selenium IDE kayıtları (.side)
└── docs/                            # Ödev dokümanları (test planı, sınır/eşdeğerlik tabloları)
```

> Not: Sınıflar **paket (package) bildirimi içermez**; hepsi default pakettedir. Bu yüzden
> `src/` ve `tests/` klasörlerinin birlikte derlenmesi gerekir.

## Gereksinimler

- **JDK 11+** (JUnit 5 ve güncel Selenium için)
- **Google Chrome** (kurulu)
- **ChromeDriver** — kurulu Chrome sürümüyle eşleşen sürüm
- Aşağıdaki kütüphaneler (classpath'e eklenmeli):
  - **Selenium Java** (örn. `selenium-java`)
  - **JUnit 5** — `junit-jupiter-api`, `junit-jupiter-engine`, `junit-jupiter-params`
  - **ByteBuddy** (Selenium'un bağımlılığı; ayrıca `TrendyolBV.java` içinde kullanılmayan
    bir `net.bytebuddy` import'u bulunur)

## Kurulum & Build

Bu projede **hazır bir build dosyası yoktur** (Maven `pom.xml` veya Gradle yok). Aşağıdaki
iki yöntemden biriyle çalıştırabilirsiniz.

### Yöntem 1 — IntelliJ IDEA (önerilen)

1. Projeyi IntelliJ'de açın.
2. **File → Project Structure → Libraries** menüsünden Selenium ve JUnit 5 JAR'larını ekleyin
   (ya da Maven/Gradle indeksinden bu bağımlılıkları çekin).
3. `java/src` ve `java/tests` klasörlerini **Sources** olarak işaretleyin
   (Project Structure → Modules → Mark as Sources / Test Sources).
4. ChromeDriver'ı PATH'e ekleyin (ya da Selenium Manager'ın otomatik indirmesine izin verin).

### Yöntem 2 — Komut satırından manuel derleme

JAR'ları bir `lib/` klasörüne koyduğunuzu varsayarsak (Windows PowerShell):

```powershell
# Derleme
javac -cp "lib/*" -d out (Get-ChildItem -Recurse java/src,java/tests -Filter *.java).FullName
```

```bash
# Derleme (Git Bash / Linux / macOS)
javac -cp "lib/*" -d out $(find java/src java/tests -name '*.java')
```

## Testleri Çalıştırma

### IntelliJ üzerinden

- Bir test sınıfını veya tek bir test metodunu çalıştırmak için, editör kenarındaki
  **yeşil çalıştır (▶) ikonuna** tıklayın.
- Tek bir test: ör. `TrendyolBVTest` → `trueInputs` metodunu tek başına çalıştırabilirsiniz.

### Komut satırından (JUnit 5 Console Launcher)

`junit-platform-console-standalone.jar` ile:

```bash
# Tüm testleri çalıştır
java -jar junit-platform-console-standalone.jar -cp "out;lib/*" --scan-classpath

# Sadece bir test sınıfı
java -jar junit-platform-console-standalone.jar -cp "out;lib/*" -c TrendyolBVTest

# Sadece tek bir test metodu
java -jar junit-platform-console-standalone.jar -cp "out;lib/*" -m TrendyolBVTest#trueInputs
```

> Windows'ta classpath ayıracı `;`, Linux/macOS'ta `:` kullanılır.

## Testler Hakkında Notlar

- **TrendyolBVTest** — Arama çubuğuna farklı uzunlukta girişler (`"a".repeat(n)`) gönderir.
  Beklenti, arama kutusunun girişi 50 karakterde kesmesidir: `trueInputs` (50) geçmeli,
  `wrongTestInputs` (51) farklı sonuç vermelidir. Testten önce bir cinsiyet seçim
  popup'ı kapatılır.
- **TrendyolPartitioningTest** — `checkEmail` metodu, geçerli e-postada ana sayfaya yönlenmeyi
  ya da geçersiz e-postada beklenen hata kutusunu kontrol eder. `true` dönmesi "girişin beklenen
  eşdeğerlik sınıfına uygun davrandığı" anlamına gelir (mutlaka başarılı giriş değil).
- Testler tarayıcıyı görünür tutmak için `@AfterEach` içinde `Thread.sleep(...)` kullanır.
- **CAPTCHA / "robot değilim"** doğrulaması veya açılan popup'lar bazı çalıştırmalarda akışı
  kesebilir; bu durumda manuel müdahale gerekebilir.
- Selenium IDE kayıtları (`selenium/*.side`), Java testlerine paralel senaryolardır ve
  Selenium IDE tarayıcı eklentisiyle açılıp oynatılabilir.

## Yasal Uyarı / Amaç

Bu proje **yalnızca eğitim amaçlıdır** ve SE2226 Yazılım Testi dersi kapsamında, kara kutu
test tasarım tekniklerini (Sınır Değer Analizi ve Eşdeğerlik Bölümleme) öğrenmek için
hazırlanmıştır. Trendyol yalnızca bu test tekniklerini gerçek bir web uygulaması üzerinde
göstermek amacıyla örnek olarak seçilmiştir; proje Trendyol ile bir bağlantısı olmadığı gibi
herhangi bir ticari, zararlı veya yetkisiz kullanım amacı taşımaz. Testler manuel ve düşük
hacimde çalışacak şekilde tasarlanmıştır; otomatik/yoğun istek (yük testi, scraping vb.)
amacıyla kullanılmamalıdır. Üçüncü taraf siteler üzerinde çalıştırırken ilgili sitenin
kullanım koşullarına uymak kullanıcının sorumluluğundadır.

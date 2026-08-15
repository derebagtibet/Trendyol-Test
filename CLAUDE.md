# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A SE2226 (Software Testing) course project. It demonstrates two black-box test-design
techniques applied to the live Trendyol e-commerce site via Selenium WebDriver:

- **Boundary Value Analysis** — `TrendyolBV` / `TrendyolBVTest`: treats the search bar's
  accepted input length as the variable under test (boundaries around 1, 50, 51 characters).
- **Equivalence Partitioning** — `TrendyolPartitioning` / `TrendyolPartitioningTest`:
  partitions email-address inputs on the login/register form into valid/invalid classes.

The `docs/` PDFs (BoundaryValueAnalysis, DecisionTables, equivalence-class table, test plan,
use-case table) are the assignment artifacts the code is meant to satisfy. `selenium/*.side`
are Selenium IDE recordings that parallel the Java tests, and `videos/` holds demo captures.

## Build & run

There is **no build descriptor** (no Maven `pom.xml` or Gradle build). The project is run
from an IDE (IntelliJ) with the classpath configured manually. To work on it you need on the
classpath: **Selenium Java**, **JUnit 5 (Jupiter + params)**, and **ByteBuddy** (a Selenium
transitive dep — note `TrendyolBV.java` also has a stray unused `net.bytebuddy` import), plus
a matching **ChromeDriver** for the locally installed Chrome.

- Source classes live in `java/src/`, tests in `java/tests/`. **No package declarations** —
  everything is in the default package, so tests reference `TrendyolBV` / `TrendyolPartitioning`
  directly and both folders must be compiled together.
- Each source class also has a `main()` for running the flow standalone, outside JUnit.
- Run a single test from the IDE gutter, or with the JUnit 5 console launcher targeting one
  class/method (e.g. select `TrendyolBVTest#trueInputs`).

## Architecture & conventions

- **No mocking — tests drive the real Trendyol website.** They open Chrome, navigate to live
  URLs, and assert against live DOM. They are inherently brittle: absolute XPaths, CSS hashes,
  and **Turkish UI strings** are matched literally (e.g. `"aramanız için ürün bulunamadı"`,
  `"E-posta adresiniz ve/veya şifreniz hatalı."`). When the site markup or copy changes, these
  break — expect to re-capture selectors rather than assume a logic bug.
- **Source classes are thin page wrappers**: constructor builds a `ChromeDriver` (with
  `--start-maximized`, `--disable-notifications`, etc.), a `WebDriverWait` (5s `TIMEOUT`), and
  `Actions`. `connect()` loads the URL; `quit()` closes the driver; `run()` chains them.
  Tests call these helpers directly rather than going through `main()`.
- **`TrendyolBV` quirk to know before changing it:** `giveInputsToSearchBar` first dismisses a
  gender-selection popup, then types into the search box. The intended assertion is that the
  search box truncates input at 50 chars — `trueInputs` (50) should pass while `wrongTestInputs`
  (51) should differ. The inline Turkish comments flag that this currently misbehaves; treat
  that method as the suspected root cause noted in the test comments.
- **`TrendyolPartitioning.checkEmail`** submits the login form with a hardcoded password and
  returns `true` for *either* a successful redirect to the homepage *or* the expected
  "invalid credentials" error box — i.e. `true` means "behaved as the partition expects,"
  not "login succeeded." Read that polarity carefully before adding assertions.
- **Test ordering & timing:** `TrendyolBVTest` uses `@TestMethodOrder(OrderAnnotation)` with
  explicit `@Order`, and both suites use `Thread.sleep(...)` in `@AfterEach` to leave the
  browser visible. Parameterized boundary cases use `@ValueSource(ints = {...})` with
  `"a".repeat(n)` to build inputs of a given length.

## Working notes

- Comments throughout the code and the commented-out `assertAll` block in
  `TrendyolPartitioningTest` are in Turkish and capture the authors' reasoning and known
  failure cases — preserve/translate them rather than deleting when refactoring.
- CAPTCHA / "I am not a robot" and intermittent popups can interrupt the register/login flow;
  the comments acknowledge runs sometimes require manual interaction.

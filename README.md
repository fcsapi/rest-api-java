# FCS API - Java Client for Forex, Crypto & Stock Market Data

Official **Java** REST API client for [FCS API](https://fcsapi.com) - Access real-time and historical **Forex**, **Cryptocurrency**, and **Stock** market data with simple method calls.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/Java-8+-orange.svg)](https://www.oracle.com/java/)
[![](https://jitpack.io/v/fcsapi/rest-api-java.svg)](https://jitpack.io/#fcsapi/rest-api-java)

## Features

- **Forex Data** - Real-time rates for 2,000+ currency pairs including majors, minors, exotics, and commodities (Gold, Silver, Oil)
- **Cryptocurrency Data** - Live prices for 13,000+ coins from top exchanges (Binance, Coinbase, Kraken) with market cap and rankings
- **Stock Market Data** - 50,000+ global stocks with company profiles, earnings, dividends, and financial statements
- **Technical Analysis** - Built-in indicators (RSI, MACD, SMA, EMA), pivot points, and moving averages
- **Historical Data** - OHLCV candles with customizable timeframes (1m to 1M) for backtesting and charting
- **Simple Integration** - Clean Java API with intuitive method calls and comprehensive documentation

## Installation

### Gradle (Recommended)
```groovy
implementation 'com.github.fcsapi:rest-api-java:v4.0.0'
```

Add JitPack repository to your `build.gradle`:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

### Maven
Add to your `pom.xml`:
```xml
<dependency>
    <groupId>com.github.fcsapi</groupId>
    <artifactId>rest-api-java</artifactId>
    <version>v4.0.0</version>
</dependency>
```

Add JitPack repository:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

### Manual Installation (Clone)
For simple projects without build tools:
```bash
git clone https://github.com/fcsapi/rest-api-java
```
Copy files from `src/main/java/com/fcsapi/` to your project and add [Gson](https://github.com/google/gson) dependency.

```java
import com.fcsapi.*;
```

## Quick Start

```java
import com.fcsapi.FcsApi;

FcsApi fcsapi = new FcsApi();

// Forex
Map<String, Object> response = fcsapi.getForex().getLatestPrice("EURUSD");

// Crypto
response = fcsapi.getCrypto().getLatestPrice("BINANCE:BTCUSDT");

// Stock
response = fcsapi.getStock().getLatestPrice("NASDAQ:AAPL");
```

## Authentication Methods

The library supports 4 authentication methods for different security needs:

### Method 1: Default Configuration (Recommended)
Set your API key once in `src/main/java/com/fcsapi/FcsConfig.java`:
```java
private String accessKey = "YOUR_API_KEY_HERE";
```
Then simply use:
```java
FcsApi fcsapi = new FcsApi();
```

### Method 2: Direct API Key
Pass API key directly (overrides config):
```java
FcsApi fcsapi = new FcsApi("YOUR_API_KEY");
```

### Method 3: IP Whitelist (No Key Required)
Whitelist your server IP at [FCS Dashboard](https://fcsapi.com/dashboard/profile):
```java
FcsConfig config = FcsConfig.withIpWhitelist();
FcsApi fcsapi = new FcsApi(config);
```

### Method 4: Token-Based Authentication (Secure for Frontend)
Generate secure tokens on backend, use on frontend without exposing API key:
```java
// Backend: Generate token
FcsConfig config = FcsConfig.withToken("YOUR_API_KEY", "YOUR_PUBLIC_KEY", 3600);
FcsApi fcsapi = new FcsApi(config);
Map<String, Object> tokenData = fcsapi.generateToken();
// Returns: {"_token": "...", "_expiry": 1234567890, "_public_key": "..."}

// Send tokenData to frontend for secure API calls
```

**Token Expiry Options:**
| Seconds | Duration |
|---------|----------|
| 300 | 5 minutes |
| 900 | 15 minutes |
| 1800 | 30 minutes |
| 3600 | 1 hour |
| 86400 | 24 hours |

## Project Structure

```
rest-api-java/
├── src/main/java/com/fcsapi/
│   ├── FcsApi.java           # Main API client
│   ├── FcsConfig.java        # Configuration & authentication
│   ├── FcsForex.java         # Forex module
│   ├── FcsCrypto.java        # Crypto module
│   └── FcsStock.java         # Stock module
├── examples/
│   ├── CryptoExample/        # Crypto API example
│   ├── ForexExample/         # Forex API example
│   ├── StockExample/         # Stock API example
│   └── AuthExample/          # Authentication examples
├── pom.xml                   # Maven build file
├── README.md
├── FUNCTIONS.md
└── LICENSE
```

## API Reference

### Forex API

```java
// ==================== Symbol List ====================
fcsapi.getForex().getSymbolsList();                    // All symbols
fcsapi.getForex().getSymbolsList("forex");             // Forex only
fcsapi.getForex().getSymbolsList("commodity");         // Commodities only

// ==================== Latest Prices ====================
fcsapi.getForex().getLatestPrice("EURUSD");
fcsapi.getForex().getLatestPrice("EURUSD,GBPUSD,USDJPY");
fcsapi.getForex().getAllPrices("FX");                  // All from exchange

// ==================== Commodities ====================
fcsapi.getForex().getCommodities();                    // All commodities
fcsapi.getForex().getCommodities("XAUUSD");           // Gold
fcsapi.getForex().getCommoditySymbols();              // Commodity symbols list

// ==================== Currency Converter ====================
fcsapi.getForex().convert("EUR", "USD", 100);         // Convert 100 EUR to USD

// ==================== Base Currency ====================
fcsapi.getForex().getBasePrices("USD");               // USD to all currencies

// ==================== Cross Rates ====================
fcsapi.getForex().getCrossRates("USD", "forex", "1D");

// ==================== Historical Data ====================
fcsapi.getForex().getHistory("EURUSD");
fcsapi.getForex().getHistory("EURUSD", "1D", 500);

// ==================== Profile ====================
fcsapi.getForex().getProfile("EUR");

// ==================== Exchanges ====================
fcsapi.getForex().getExchanges();

// ==================== Technical Analysis ====================
fcsapi.getForex().getMovingAverages("EURUSD", "1D");  // EMA & SMA
fcsapi.getForex().getIndicators("EURUSD", "1D");      // RSI, MACD, etc.
fcsapi.getForex().getPivotPoints("EURUSD", "1D");     // Pivot Points

// ==================== Performance ====================
fcsapi.getForex().getPerformance("EURUSD");           // Highs, lows, volatility

// ==================== Economy Calendar ====================
fcsapi.getForex().getEconomyCalendar();

// ==================== Top Movers ====================
fcsapi.getForex().getTopGainers();
fcsapi.getForex().getTopLosers();
fcsapi.getForex().getMostActive();

// ==================== Search ====================
fcsapi.getForex().search("EUR");
```

### Crypto API

```java
// ==================== Symbol List ====================
fcsapi.getCrypto().getSymbolsList();                   // All crypto
fcsapi.getCrypto().getSymbolsList("crypto", "BINANCE"); // Binance only
fcsapi.getCrypto().getCoinsList();                     // Coins with market cap

// ==================== Latest Prices ====================
fcsapi.getCrypto().getLatestPrice("BTCUSDT");
fcsapi.getCrypto().getLatestPrice("BINANCE:BTCUSDT");
fcsapi.getCrypto().getAllPrices("BINANCE");

// ==================== Coin Data (Market Cap, Rank, Supply) ====================
fcsapi.getCrypto().getCoinData();                      // Top coins with full data
fcsapi.getCrypto().getTopByMarketCap(100);            // Top 100 by market cap
fcsapi.getCrypto().getTopByRank(50);                  // Top 50 by rank

// ==================== Crypto Converter ====================
fcsapi.getCrypto().convert("BTC", "USD", 1);          // 1 BTC to USD
fcsapi.getCrypto().convert("ETH", "BTC", 10);         // 10 ETH to BTC

// ==================== Base Currency ====================
fcsapi.getCrypto().getBasePrices("BTC");              // BTC to all

// ==================== Historical Data ====================
fcsapi.getCrypto().getHistory("BINANCE:BTCUSDT");
fcsapi.getCrypto().getHistory("BTCUSDT", "1D", 500);

// ==================== Profile ====================
fcsapi.getCrypto().getProfile("BTC");

// ==================== Exchanges ====================
fcsapi.getCrypto().getExchanges();

// ==================== Technical Analysis ====================
fcsapi.getCrypto().getMovingAverages("BINANCE:BTCUSDT", "1D");
fcsapi.getCrypto().getIndicators("BINANCE:BTCUSDT", "1D");
fcsapi.getCrypto().getPivotPoints("BINANCE:BTCUSDT", "1D");

// ==================== Top Movers ====================
fcsapi.getCrypto().getTopGainers();
fcsapi.getCrypto().getTopLosers();
fcsapi.getCrypto().getHighestVolume();

// ==================== Search ====================
fcsapi.getCrypto().search("bitcoin");
```

### Stock API

```java
// ==================== Symbol List ====================
fcsapi.getStock().getSymbolsList();                    // All stocks
fcsapi.getStock().getSymbolsList("NASDAQ");           // NASDAQ only

// ==================== Indices ====================
fcsapi.getStock().getIndicesList("united-states");    // US indices
fcsapi.getStock().getIndicesLatest();                 // All indices prices
fcsapi.getStock().getIndicesLatest("NASDAQ:NDX,SP:SPX"); // Specific indices

// ==================== Latest Prices ====================
fcsapi.getStock().getLatestPrice("AAPL");
fcsapi.getStock().getLatestPrice("NASDAQ:AAPL");
fcsapi.getStock().getAllPrices("NASDAQ");
fcsapi.getStock().getLatestByCountry("united-states", "technology");
fcsapi.getStock().getLatestByIndices("NASDAQ:NDX");

// ==================== Historical Data ====================
fcsapi.getStock().getHistory("NASDAQ:AAPL");
fcsapi.getStock().getHistory("AAPL", "1D", 500);

// ==================== Profile ====================
fcsapi.getStock().getProfile("AAPL");

// ==================== Exchanges ====================
fcsapi.getStock().getExchanges();

// ==================== Financial Data ====================
fcsapi.getStock().getEarnings("NASDAQ:AAPL");         // EPS, Revenue
fcsapi.getStock().getRevenue("NASDAQ:AAPL");          // Revenue segments
fcsapi.getStock().getBalanceSheet("NASDAQ:AAPL", "annual");
fcsapi.getStock().getIncomeStatements("NASDAQ:AAPL", "annual");
fcsapi.getStock().getCashFlow("NASDAQ:AAPL", "annual");
fcsapi.getStock().getDividends("NASDAQ:AAPL");
fcsapi.getStock().getStatistics("NASDAQ:AAPL");
fcsapi.getStock().getForecast("NASDAQ:AAPL");
fcsapi.getStock().getStockData("NASDAQ:AAPL", "profile,earnings,dividends");

// ==================== Technical Analysis ====================
fcsapi.getStock().getMovingAverages("NASDAQ:AAPL", "1D");
fcsapi.getStock().getIndicators("NASDAQ:AAPL", "1D");
fcsapi.getStock().getPivotPoints("NASDAQ:AAPL", "1D");

// ==================== Top Movers ====================
fcsapi.getStock().getTopGainers();
fcsapi.getStock().getTopLosers();
fcsapi.getStock().getMostActive();

// ==================== Search & Filter ====================
fcsapi.getStock().search("Apple");
fcsapi.getStock().getBySector("technology", 50);
fcsapi.getStock().getByCountry("united-states", 50);
```

## Response Handling

```java
Map<String, Object> response = fcsapi.getForex().getLatestPrice("EURUSD");

// Check if successful
if (fcsapi.isSuccess()) {
    Object data = response.get("response");
    System.out.println(data);
} else {
    System.out.println("Error: " + fcsapi.getError());
}

// Get last response info
Map<String, Object> lastResponse = fcsapi.getLastResponse();

// Get response data only
Object data = fcsapi.getResponseData();
```

## Time Periods

Available timeframes for price data:

| Period | Description |
|--------|-------------|
| `1` or `1m` | 1 minute |
| `5` or `5m` | 5 minutes |
| `15` or `15m` | 15 minutes |
| `30` or `30m` | 30 minutes |
| `1h` or `60` | 1 hour |
| `4h` or `240` | 4 hours |
| `1D` | 1 day |
| `1W` | 1 week |
| `1M` | 1 month |

## Examples

### Forex Example
```java
import com.fcsapi.FcsApi;
import java.util.Map;

FcsApi fcsapi = new FcsApi();

// Get EUR/USD latest price
Map<String, Object> response = fcsapi.getForex().getLatestPrice("EURUSD");
if (fcsapi.isSuccess()) {
    System.out.println(response);
}

// Convert 1000 EUR to USD
Map<String, Object> conversion = fcsapi.getForex().convert("EUR", "USD", 1000);
if (fcsapi.isSuccess()) {
    System.out.println(conversion);
}
```

### Crypto Example
```java
import com.fcsapi.FcsApi;
import java.util.Map;

FcsApi fcsapi = new FcsApi();

// Get Bitcoin price from Binance
Map<String, Object> response = fcsapi.getCrypto().getLatestPrice("BINANCE:BTCUSDT");
if (fcsapi.isSuccess()) {
    System.out.println(response);
}

// Get top 100 coins by market cap
Map<String, Object> coins = fcsapi.getCrypto().getTopByMarketCap(100);
if (fcsapi.isSuccess()) {
    System.out.println(coins);
}
```

### Stock Example
```java
import com.fcsapi.FcsApi;
import java.util.Map;

FcsApi fcsapi = new FcsApi();

// Get Apple stock price
Map<String, Object> response = fcsapi.getStock().getLatestPrice("NASDAQ:AAPL");
if (fcsapi.isSuccess()) {
    System.out.println(response);
}

// Get Apple earnings data
Map<String, Object> earnings = fcsapi.getStock().getEarnings("NASDAQ:AAPL");
if (fcsapi.isSuccess()) {
    System.out.println(earnings);
}
```

## Get API Key

1. Visit [FCS API](https://fcsapi.com)
2. Sign up for a free account
3. Get your API key from the dashboard

## Documentation

For complete API documentation, visit:
- [Forex API Documentation](https://fcsapi.com/document/forex-api)
- [Crypto API Documentation](https://fcsapi.com/document/crypto-api)
- [Stock API Documentation](https://fcsapi.com/document/stock-api)

## Support

- Email: support@fcsapi.com
- Website: [fcsapi.com](https://fcsapi.com)

## License

MIT License - see [LICENSE](LICENSE) file for details.

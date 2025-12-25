# FCS API - C# Functions Reference

Quick reference for all available functions in the FCS API C# library.

---

## Authentication Methods

```csharp
using FcsApi;

// Method 1: Default (uses key from FcsConfig.cs)
var fcsapi = new FcsApi.FcsApi();

// Method 2: Pass API Key directly (override)
var fcsapi = new FcsApi.FcsApi("YOUR_API_KEY");

// Method 3: IP Whitelist (no key needed if IP whitelisted in account)
var config = FcsConfig.WithIpWhitelist();
var fcsapi = new FcsApi.FcsApi(config);

// Method 4: Token-Based (secure for frontend apps)
var config = FcsConfig.WithToken("API_KEY", "PUBLIC_KEY", 3600);
var fcsapi = new FcsApi.FcsApi(config);
var tokenData = fcsapi.GenerateToken();  // Send to frontend
```

### Set Default API Key
Edit `src/FcsConfig.cs` and set your key:
```csharp
public string AccessKey { get; set; } = "YOUR_API_KEY_HERE";
```

### Token Expiry Values
| Seconds | Duration |
|---------|----------|
| 300 | 5 minutes |
| 900 | 15 minutes |
| 1800 | 30 minutes |
| 3600 | 1 hour |
| 86400 | 24 hours |

---

## Crypto Functions

```csharp
fcsapi.Crypto.GetSymbolsList(type, subType, exchange)
fcsapi.Crypto.GetCoinsList()
fcsapi.Crypto.GetLatestPrice(symbol, period, type, exchange, getProfile)
fcsapi.Crypto.GetAllPrices(exchange, period, type)
fcsapi.Crypto.GetCoinData(symbol, limit, sortBy)
fcsapi.Crypto.GetTopByMarketCap(limit)
fcsapi.Crypto.GetTopByRank(limit)
fcsapi.Crypto.Convert(pair1, pair2, amount)
fcsapi.Crypto.GetBasePrices(symbol, exchange, fallback)
fcsapi.Crypto.GetCrossRates(symbol, exchange, type, period, crossrates, fallback)
fcsapi.Crypto.GetHistory(symbol, period, length, fromDate, toDate, page, isChart)
fcsapi.Crypto.GetProfile(symbol)
fcsapi.Crypto.GetExchanges(type, subType)
fcsapi.Crypto.Advanced(params)
fcsapi.Crypto.GetMovingAverages(symbol, period, exchange)
fcsapi.Crypto.GetIndicators(symbol, period, exchange)
fcsapi.Crypto.GetPivotPoints(symbol, period, exchange)
fcsapi.Crypto.GetPerformance(symbol, exchange)
fcsapi.Crypto.GetTopGainers(exchange, limit, period, type)
fcsapi.Crypto.GetTopLosers(exchange, limit, period, type)
fcsapi.Crypto.GetHighestVolume(exchange, limit, period, type)
fcsapi.Crypto.GetSortedData(sortColumn, sortDirection, limit, type, exchange, period)
fcsapi.Crypto.Search(query, type)
fcsapi.Crypto.MultiUrl(urls, baseUrl)
```

### Parameters
| Parameter | Values |
|-----------|--------|
| `type` | crypto, coin, futures, dex, dominance |
| `subType` | spot, swap, index |
| `exchange` | BINANCE, COINBASE, KRAKEN, BYBIT |
| `period` | 1m, 5m, 15m, 30m, 1h, 4h, 1D, 1W, 1M |
| `sortBy` | perf.rank_asc, perf.market_cap_desc, perf.circulating_supply_desc |
| `sortColumn` | active.c, active.chp, active.v, active.h, active.l, perf.rank, perf.market_cap |
| `sortDirection` | asc, desc |

---

## Forex Functions

```csharp
fcsapi.Forex.GetSymbolsList(type, subType, exchange)
fcsapi.Forex.GetLatestPrice(symbol, period, type, exchange, getProfile)
fcsapi.Forex.GetAllPrices(exchange, period, type)
fcsapi.Forex.GetCommodities(symbol, period)
fcsapi.Forex.GetCommoditySymbols()
fcsapi.Forex.Convert(pair1, pair2, amount, type)
fcsapi.Forex.GetBasePrices(symbol, type, exchange, fallback)
fcsapi.Forex.GetCrossRates(symbol, type, period, exchange, crossrates, fallback)
fcsapi.Forex.GetHistory(symbol, period, length, fromDate, toDate, page, isChart)
fcsapi.Forex.GetProfile(symbol)
fcsapi.Forex.GetExchanges(type, subType)
fcsapi.Forex.Advanced(params)
fcsapi.Forex.GetMovingAverages(symbol, period, exchange)
fcsapi.Forex.GetIndicators(symbol, period, exchange)
fcsapi.Forex.GetPivotPoints(symbol, period, exchange)
fcsapi.Forex.GetPerformance(symbol, exchange)
fcsapi.Forex.GetEconomyCalendar(symbol, country, fromDate, toDate)
fcsapi.Forex.GetTopGainers(type, limit, period, exchange)
fcsapi.Forex.GetTopLosers(type, limit, period, exchange)
fcsapi.Forex.GetMostActive(type, limit, period, exchange)
fcsapi.Forex.GetSortedData(sortColumn, sortDirection, limit, type, exchange, period)
fcsapi.Forex.Search(query, type, exchange)
fcsapi.Forex.MultiUrl(urls, baseUrl)
```

### Parameters
| Parameter | Values |
|-----------|--------|
| `type` | forex, commodity |
| `subType` | spot, synthetic |
| `exchange` | FX, ONA, SFO, FCM |
| `period` | 1m, 5m, 15m, 30m, 1h, 4h, 1D, 1W, 1M |
| `country` | US, GB, DE, JP, AU, CA |

---

## Stock Functions

```csharp
// Symbol/List
fcsapi.Stock.GetSymbolsList(exchange, country, sector, indices)
fcsapi.Stock.Search(query, exchange, country)

// Indices
fcsapi.Stock.GetIndicesList(country, exchange)
fcsapi.Stock.GetIndicesLatest(symbol, country, exchange)

// Latest Prices
fcsapi.Stock.GetLatestPrice(symbol, period, exchange, getProfile)
fcsapi.Stock.GetAllPrices(exchange, period)
fcsapi.Stock.GetLatestByCountry(country, sector, period)
fcsapi.Stock.GetLatestByIndices(indices, period)

// Historical
fcsapi.Stock.GetHistory(symbol, period, length, fromDate, toDate, page, isChart)

// Profile & Info
fcsapi.Stock.GetProfile(symbol)
fcsapi.Stock.GetExchanges(type, subType)

// Financial Data
fcsapi.Stock.GetEarnings(symbol, duration)
fcsapi.Stock.GetRevenue(symbol)
fcsapi.Stock.GetDividends(symbol, format)
fcsapi.Stock.GetBalanceSheet(symbol, duration, format)
fcsapi.Stock.GetIncomeStatements(symbol, duration, format)
fcsapi.Stock.GetCashFlow(symbol, duration, format)
fcsapi.Stock.GetStatistics(symbol, duration)
fcsapi.Stock.GetForecast(symbol)
fcsapi.Stock.GetStockData(symbol, dataColumn, duration, format)

// Technical Analysis
fcsapi.Stock.GetMovingAverages(symbol, period)
fcsapi.Stock.GetIndicators(symbol, period)
fcsapi.Stock.GetPivotPoints(symbol, period)
fcsapi.Stock.GetPerformance(symbol)

// Top Movers & Sorting
fcsapi.Stock.GetTopGainers(exchange, limit, period, country)
fcsapi.Stock.GetTopLosers(exchange, limit, period, country)
fcsapi.Stock.GetMostActive(exchange, limit, period, country)
fcsapi.Stock.GetSortedData(sortColumn, sortDirection, limit, exchange, country, period)

// Filter
fcsapi.Stock.GetBySector(sector, limit, exchange)
fcsapi.Stock.GetByCountry(country, limit, exchange)

// Advanced
fcsapi.Stock.Advanced(params)
fcsapi.Stock.MultiUrl(urls, baseUrl)
```

### Parameters
| Parameter | Values |
|-----------|--------|
| `type` | stock, index, fund, structured, dr |
| `subType` | spot, main, cfd, common, preferred |
| `exchange` | NASDAQ, NYSE, LSE, TSE, HKEX, BSE |
| `period` | 1m, 5m, 15m, 30m, 1h, 4h, 1D, 1W, 1M |
| `duration` | annual, interim, both |
| `format` | plain, inherit |
| `dataColumn` | earnings, revenue, profile, dividends, balance_sheet, income_statements, statistics, cash_flow |

---

## Common Response Fields

| Field | Description |
|-------|-------------|
| `o` | Open price |
| `h` | High price |
| `l` | Low price |
| `c` | Close/Current price |
| `v` | Volume |
| `t` | Unix timestamp |
| `ch` | Change amount |
| `chp` | Change percentage |

---

## Quick Examples

```csharp
// Initialize (uses key from FcsConfig.cs)
var fcsapi = new FcsApi.FcsApi();

// Crypto
fcsapi.Crypto.GetLatestPrice("BINANCE:BTCUSDT");
fcsapi.Crypto.GetHistory("BINANCE:BTCUSDT", "1D", 100);
fcsapi.Crypto.GetCoinData(null, 50, "perf.rank_asc");

// Forex
fcsapi.Forex.GetLatestPrice("FX:EURUSD");
fcsapi.Forex.Convert("EUR", "USD", 100);

// Stock
fcsapi.Stock.GetLatestPrice("NASDAQ:AAPL");
fcsapi.Stock.GetTopGainers("NASDAQ", 10);
fcsapi.Stock.GetEarnings("NASDAQ:AAPL", "annual");
fcsapi.Stock.GetDividends("NASDAQ:AAPL");
fcsapi.Stock.GetBalanceSheet("NASDAQ:AAPL", "annual");
fcsapi.Stock.GetStockData("NASDAQ:AAPL", "profile,earnings,dividends");
```

---

## Token Authentication Example

```csharp
// Backend: Generate token
var config = FcsConfig.WithToken("YOUR_API_KEY", "YOUR_PUBLIC_KEY", 3600);
var fcsapi = new FcsApi.FcsApi(config);
var tokenData = fcsapi.GenerateToken();

// Send tokenData to frontend:
// {
//     "_token": "abc123...",
//     "_expiry": 1764164233,
//     "_public_key": "your_public_key"
// }

// Frontend (JavaScript): Use token
// fetch('https://api-v4.fcsapi.com/forex/latest?symbol=EURUSD' +
//       '&_public_key=' + tokenData._public_key +
//       '&_expiry=' + tokenData._expiry +
//       '&_token=' + tokenData._token)
```

---

## Get API Key

Get your API key at: https://fcsapi.com

/**
 * FCS API - Forex Module
 *
 * @package FcsApi
 * @author FCS API <support@fcsapi.com>
 */

package com.fcsapi;

import java.util.HashMap;
import java.util.Map;

/**
 * Forex API Module
 */
public class FcsForex {

    private final FcsApi api;
    private static final String BASE = "forex/";

    /**
     * Initialize Forex module
     * @param api FcsApi instance
     */
    public FcsForex(FcsApi api) {
        this.api = api;
    }

    // ==================== Symbol List ====================

    /**
     * Get list of all forex symbols
     * @param type Filter by type: forex, commodity
     * @param subType Filter: spot, synthetic
     * @param exchange Filter by exchange: FX, ONA, SFO, FCM
     * @return API response or null
     */
    public Map<String, Object> getSymbolsList(String type, String subType, String exchange) {
        Map<String, Object> params = new HashMap<>();
        if (type != null && !type.isEmpty()) params.put("type", type);
        if (subType != null && !subType.isEmpty()) params.put("sub_type", subType);
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        return api.request(BASE + "list", params);
    }

    public Map<String, Object> getSymbolsList(String type) {
        return getSymbolsList(type, null, null);
    }

    public Map<String, Object> getSymbolsList() {
        return getSymbolsList(null, null, null);
    }

    // ==================== Latest Prices ====================

    /**
     * Get latest prices for symbols
     * @param symbol Symbol(s) comma-separated: EURUSD,GBPUSD or FX:EURUSD
     * @param period Time period: 1m,5m,15m,30m,1h,4h,1D,1W,1M
     * @param type forex or commodity
     * @param exchange Exchange name: FX, ONA, SFO
     * @param getProfile Include profile info
     * @return API response or null
     */
    public Map<String, Object> getLatestPrice(String symbol, String period, String type, String exchange, boolean getProfile) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("period", period != null ? period : "1D");
        if (type != null && !type.isEmpty()) params.put("type", type);
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        if (getProfile) params.put("get_profile", 1);
        return api.request(BASE + "latest", params);
    }

    public Map<String, Object> getLatestPrice(String symbol, String period) {
        return getLatestPrice(symbol, period, null, null, false);
    }

    public Map<String, Object> getLatestPrice(String symbol) {
        return getLatestPrice(symbol, "1D", null, null, false);
    }

    /**
     * Get all latest prices by exchange
     */
    public Map<String, Object> getAllPrices(String exchange, String period, String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("exchange", exchange);
        params.put("period", period != null ? period : "1D");
        if (type != null && !type.isEmpty()) params.put("type", type);
        return api.request(BASE + "latest", params);
    }

    public Map<String, Object> getAllPrices(String exchange) {
        return getAllPrices(exchange, "1D", null);
    }

    // ==================== Commodities ====================

    /**
     * Get commodity prices (Gold, Silver, Oil, etc.)
     */
    public Map<String, Object> getCommodities(String symbol, String period) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "commodity");
        params.put("period", period != null ? period : "1D");
        if (symbol != null && !symbol.isEmpty()) params.put("symbol", symbol);
        return api.request(BASE + "latest", params);
    }

    public Map<String, Object> getCommodities(String symbol) {
        return getCommodities(symbol, "1D");
    }

    public Map<String, Object> getCommodities() {
        return getCommodities(null, "1D");
    }

    /**
     * Get commodity symbols list
     */
    public Map<String, Object> getCommoditySymbols() {
        return getSymbolsList("commodity");
    }

    // ==================== Currency Converter ====================

    /**
     * Currency converter
     */
    public Map<String, Object> convert(String pair1, String pair2, double amount, String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("pair1", pair1);
        params.put("pair2", pair2);
        params.put("amount", amount);
        if (type != null && !type.isEmpty()) params.put("type", type);
        return api.request(BASE + "converter", params);
    }

    public Map<String, Object> convert(String pair1, String pair2, double amount) {
        return convert(pair1, pair2, amount, null);
    }

    public Map<String, Object> convert(String pair1, String pair2) {
        return convert(pair1, pair2, 1, null);
    }

    // ==================== Base Currency ====================

    /**
     * Get base currency prices (USD to all currencies)
     */
    public Map<String, Object> getBasePrices(String symbol, String type, String exchange, boolean fallback) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("type", type != null ? type : "forex");
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        if (fallback) params.put("fallback", 1);
        return api.request(BASE + "base_latest", params);
    }

    public Map<String, Object> getBasePrices(String symbol) {
        return getBasePrices(symbol, "forex", null, false);
    }

    // ==================== Cross Currency ====================

    /**
     * Get cross currency rates with OHLC data
     */
    public Map<String, Object> getCrossRates(String symbol, String exchange, String type, String period, boolean crossrates, boolean fallback) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("type", type != null ? type : "forex");
        params.put("period", period != null ? period : "1D");
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        if (crossrates) params.put("crossrates", 1);
        if (fallback) params.put("fallback", 1);
        return api.request(BASE + "cross", params);
    }

    public Map<String, Object> getCrossRates(String symbol, String type, String period) {
        return getCrossRates(symbol, null, type, period, false, false);
    }

    // ==================== Historical Data ====================

    /**
     * Get historical prices (OHLCV candles)
     */
    public Map<String, Object> getHistory(String symbol, String period, int length, String fromDate, String toDate, int page, boolean isChart) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("period", period != null ? period : "1D");
        params.put("length", length);
        params.put("page", page);
        if (fromDate != null && !fromDate.isEmpty()) params.put("from", fromDate);
        if (toDate != null && !toDate.isEmpty()) params.put("to", toDate);
        if (isChart) params.put("is_chart", 1);
        return api.request(BASE + "history", params);
    }

    public Map<String, Object> getHistory(String symbol, String period, int length) {
        return getHistory(symbol, period, length, null, null, 1, false);
    }

    public Map<String, Object> getHistory(String symbol) {
        return getHistory(symbol, "1D", 300, null, null, 1, false);
    }

    // ==================== Profile ====================

    /**
     * Get currency profile details
     */
    public Map<String, Object> getProfile(String symbol) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        return api.request(BASE + "profile", params);
    }

    // ==================== Exchanges ====================

    /**
     * Get available exchanges
     */
    public Map<String, Object> getExchanges(String type, String subType) {
        Map<String, Object> params = new HashMap<>();
        if (type != null && !type.isEmpty()) params.put("type", type);
        if (subType != null && !subType.isEmpty()) params.put("sub_type", subType);
        return api.request(BASE + "exchanges", params);
    }

    public Map<String, Object> getExchanges() {
        return getExchanges(null, null);
    }

    // ==================== Advanced Query ====================

    /**
     * Advanced query with filters, sorting, pagination, merging
     */
    public Map<String, Object> advanced(Map<String, Object> parameters) {
        return api.request(BASE + "advance", parameters);
    }

    // ==================== Technical Analysis ====================

    /**
     * Get Moving Averages (EMA & SMA)
     */
    public Map<String, Object> getMovingAverages(String symbol, String period, String exchange) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("period", period != null ? period : "1D");
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        return api.request(BASE + "ma_avg", params);
    }

    public Map<String, Object> getMovingAverages(String symbol, String period) {
        return getMovingAverages(symbol, period, null);
    }

    /**
     * Get Technical Indicators (RSI, MACD, etc.)
     */
    public Map<String, Object> getIndicators(String symbol, String period, String exchange) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("period", period != null ? period : "1D");
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        return api.request(BASE + "indicators", params);
    }

    public Map<String, Object> getIndicators(String symbol, String period) {
        return getIndicators(symbol, period, null);
    }

    /**
     * Get Pivot Points
     */
    public Map<String, Object> getPivotPoints(String symbol, String period, String exchange) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("period", period != null ? period : "1D");
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        return api.request(BASE + "pivot_points", params);
    }

    public Map<String, Object> getPivotPoints(String symbol, String period) {
        return getPivotPoints(symbol, period, null);
    }

    // ==================== Performance ====================

    /**
     * Get Performance Data
     */
    public Map<String, Object> getPerformance(String symbol, String exchange) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        return api.request(BASE + "performance", params);
    }

    public Map<String, Object> getPerformance(String symbol) {
        return getPerformance(symbol, null);
    }

    // ==================== Economy Calendar ====================

    /**
     * Get Economic Calendar Events
     */
    public Map<String, Object> getEconomyCalendar(String symbol, String country, String fromDate, String toDate) {
        Map<String, Object> params = new HashMap<>();
        if (symbol != null && !symbol.isEmpty()) params.put("symbol", symbol);
        if (country != null && !country.isEmpty()) params.put("country", country);
        if (fromDate != null && !fromDate.isEmpty()) params.put("from", fromDate);
        if (toDate != null && !toDate.isEmpty()) params.put("to", toDate);
        return api.request(BASE + "economy_cal", params);
    }

    public Map<String, Object> getEconomyCalendar() {
        return getEconomyCalendar(null, null, null, null);
    }

    // ==================== Top Movers ====================

    /**
     * Get top gainers
     */
    public Map<String, Object> getTopGainers(String type, int limit, String period, String exchange) {
        return getSortedData("active.chp", "desc", limit, type, exchange, period);
    }

    public Map<String, Object> getTopGainers() {
        return getTopGainers("forex", 20, "1D", null);
    }

    /**
     * Get top losers
     */
    public Map<String, Object> getTopLosers(String type, int limit, String period, String exchange) {
        return getSortedData("active.chp", "asc", limit, type, exchange, period);
    }

    public Map<String, Object> getTopLosers() {
        return getTopLosers("forex", 20, "1D", null);
    }

    /**
     * Get most active by volume
     */
    public Map<String, Object> getMostActive(String type, int limit, String period, String exchange) {
        return getSortedData("active.v", "desc", limit, type, exchange, period);
    }

    public Map<String, Object> getMostActive() {
        return getMostActive("forex", 20, "1D", null);
    }

    // ==================== Custom Sorting ====================

    /**
     * Get data with custom sorting
     */
    public Map<String, Object> getSortedData(String sortColumn, String sortDirection, int limit, String type, String exchange, String period) {
        Map<String, Object> params = new HashMap<>();
        params.put("period", period != null ? period : "1D");
        params.put("sort_by", sortColumn + "_" + sortDirection);
        params.put("per_page", limit);
        params.put("merge", "latest");
        if (type != null && !type.isEmpty()) params.put("type", type);
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        return advanced(params);
    }

    // ==================== Search ====================

    /**
     * Search symbols
     */
    public Map<String, Object> search(String query, String type, String exchange) {
        Map<String, Object> params = new HashMap<>();
        params.put("search", query);
        if (type != null && !type.isEmpty()) params.put("type", type);
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        return api.request(BASE + "search", params);
    }

    public Map<String, Object> search(String query) {
        return search(query, null, null);
    }

    // ==================== Multiple/Parallel Requests ====================

    /**
     * Execute multiple API requests
     */
    public Map<String, Object> multiUrl(String[] urls, String baseUrl) {
        Map<String, Object> params = new HashMap<>();
        params.put("url", String.join(",", urls));
        if (baseUrl != null && !baseUrl.isEmpty()) params.put("base", baseUrl);
        return api.request(BASE + "multi_url", params);
    }
}

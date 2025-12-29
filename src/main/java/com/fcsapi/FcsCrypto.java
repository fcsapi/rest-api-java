/**
 * FCS API - Crypto Module
 *
 * @package FcsApi
 * @author FCS API <support@fcsapi.com>
 */

package com.fcsapi;

import java.util.HashMap;
import java.util.Map;

/**
 * Crypto API Module
 */
public class FcsCrypto {

    private final FcsApi api;
    private static final String BASE = "crypto/";

    /**
     * Initialize Crypto module
     * @param api FcsApi instance
     */
    public FcsCrypto(FcsApi api) {
        this.api = api;
    }

    // ==================== Symbol List ====================

    /**
     * Get list of all crypto symbols
     */
    public Map<String, Object> getSymbolsList(String type, String subType, String exchange) {
        Map<String, Object> params = new HashMap<>();
        if (type != null && !type.isEmpty()) params.put("type", type);
        if (subType != null && !subType.isEmpty()) params.put("sub_type", subType);
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        return api.request(BASE + "list", params);
    }

    public Map<String, Object> getSymbolsList(String type, String exchange) {
        return getSymbolsList(type, null, exchange);
    }

    public Map<String, Object> getSymbolsList() {
        return getSymbolsList("crypto", null, null);
    }

    /**
     * Get list of all coins
     */
    public Map<String, Object> getCoinsList() {
        return getSymbolsList("coin", null, null);
    }

    // ==================== Latest Prices ====================

    /**
     * Get latest prices
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

    // ==================== Coin Data ====================

    /**
     * Get coin data with rank, market cap, supply
     */
    public Map<String, Object> getCoinData(String symbol, int limit, String sortBy) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "coin");
        params.put("sort_by", sortBy != null ? sortBy : "perf.rank_asc");
        params.put("per_page", limit);
        params.put("merge", "latest,perf");
        if (symbol != null && !symbol.isEmpty()) params.put("symbol", symbol);
        return api.request(BASE + "advance", params);
    }

    public Map<String, Object> getCoinData() {
        return getCoinData(null, 100, "perf.rank_asc");
    }

    /**
     * Get top coins by market cap
     */
    public Map<String, Object> getTopByMarketCap(int limit) {
        return getCoinData(null, limit, "perf.market_cap_desc");
    }

    /**
     * Get top coins by rank
     */
    public Map<String, Object> getTopByRank(int limit) {
        return getCoinData(null, limit, "perf.rank_asc");
    }

    // ==================== Crypto Converter ====================

    /**
     * Crypto converter
     */
    public Map<String, Object> convert(String pair1, String pair2, double amount) {
        Map<String, Object> params = new HashMap<>();
        params.put("pair1", pair1);
        params.put("pair2", pair2);
        params.put("amount", amount);
        return api.request(BASE + "converter", params);
    }

    public Map<String, Object> convert(String pair1, String pair2) {
        return convert(pair1, pair2, 1);
    }

    // ==================== Base Currency ====================

    /**
     * Get base currency prices
     */
    public Map<String, Object> getBasePrices(String symbol, String exchange, boolean fallback) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        if (fallback) params.put("fallback", 1);
        return api.request(BASE + "base_latest", params);
    }

    public Map<String, Object> getBasePrices(String symbol) {
        return getBasePrices(symbol, null, false);
    }

    // ==================== Cross Currency ====================

    /**
     * Get cross currency rates
     */
    public Map<String, Object> getCrossRates(String symbol, String exchange, String type, String period, boolean crossrates, boolean fallback) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("type", type != null ? type : "crypto");
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
     * Get historical prices
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
     * Get coin profile details
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
     * Advanced query
     */
    public Map<String, Object> advanced(Map<String, Object> parameters) {
        return api.request(BASE + "advance", parameters);
    }

    // ==================== Technical Analysis ====================

    /**
     * Get Moving Averages
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
     * Get Technical Indicators
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

    // ==================== Top Movers ====================

    /**
     * Get top gainers
     */
    public Map<String, Object> getTopGainers(String exchange, int limit, String period, String type) {
        return getSortedData("active.chp", "desc", limit, type, exchange, period);
    }

    public Map<String, Object> getTopGainers() {
        return getTopGainers(null, 20, "1D", "crypto");
    }

    /**
     * Get top losers
     */
    public Map<String, Object> getTopLosers(String exchange, int limit, String period, String type) {
        return getSortedData("active.chp", "asc", limit, type, exchange, period);
    }

    public Map<String, Object> getTopLosers() {
        return getTopLosers(null, 20, "1D", "crypto");
    }

    /**
     * Get highest volume
     */
    public Map<String, Object> getHighestVolume(String exchange, int limit, String period, String type) {
        return getSortedData("active.v", "desc", limit, type, exchange, period);
    }

    public Map<String, Object> getHighestVolume() {
        return getHighestVolume(null, 20, "1D", "crypto");
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
        return api.request(BASE + "advance", params);
    }

    // ==================== Search ====================

    /**
     * Search coins/tokens
     */
    public Map<String, Object> search(String query, String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("search", query);
        if (type != null && !type.isEmpty()) params.put("type", type);
        return api.request(BASE + "list", params);
    }

    public Map<String, Object> search(String query) {
        return search(query, null);
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

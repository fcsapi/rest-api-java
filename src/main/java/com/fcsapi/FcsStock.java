/**
 * FCS API - Stock Module
 *
 * @package FcsApi
 * @author FCS API <support@fcsapi.com>
 */

package com.fcsapi;

import java.util.HashMap;
import java.util.Map;

/**
 * Stock API Module
 */
public class FcsStock {

    private final FcsApi api;
    private static final String BASE = "stock/";

    /**
     * Initialize Stock module
     * @param api FcsApi instance
     */
    public FcsStock(FcsApi api) {
        this.api = api;
    }

    // ==================== Symbol/Stock List ====================

    /**
     * Get list of all stock symbols
     */
    public Map<String, Object> getSymbolsList(String exchange, String country, String sector, String indices) {
        Map<String, Object> params = new HashMap<>();
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        if (country != null && !country.isEmpty()) params.put("country", country);
        if (sector != null && !sector.isEmpty()) params.put("sector", sector);
        if (indices != null && !indices.isEmpty()) params.put("indices", indices);
        return api.request(BASE + "list", params);
    }

    public Map<String, Object> getSymbolsList(String exchange) {
        return getSymbolsList(exchange, null, null, null);
    }

    public Map<String, Object> getSymbolsList() {
        return getSymbolsList(null, null, null, null);
    }

    // ==================== Indices ====================

    /**
     * Get list of market indices by country
     */
    public Map<String, Object> getIndicesList(String country, String exchange) {
        Map<String, Object> params = new HashMap<>();
        if (country != null && !country.isEmpty()) params.put("country", country);
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        return api.request(BASE + "indices", params);
    }

    public Map<String, Object> getIndicesList(String country) {
        return getIndicesList(country, null);
    }

    /**
     * Get latest index prices
     */
    public Map<String, Object> getIndicesLatest(String symbol, String country, String exchange) {
        Map<String, Object> params = new HashMap<>();
        if (symbol != null && !symbol.isEmpty()) params.put("symbol", symbol);
        if (country != null && !country.isEmpty()) params.put("country", country);
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        return api.request(BASE + "indices_latest", params);
    }

    public Map<String, Object> getIndicesLatest(String symbol) {
        return getIndicesLatest(symbol, null, null);
    }

    public Map<String, Object> getIndicesLatest() {
        return getIndicesLatest(null, null, null);
    }

    // ==================== Latest Prices ====================

    /**
     * Get latest stock prices
     */
    public Map<String, Object> getLatestPrice(String symbol, String period, String exchange, boolean getProfile) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("period", period != null ? period : "1D");
        params.put("get_profile", getProfile ? 1 : 0);
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        return api.request(BASE + "latest", params);
    }

    public Map<String, Object> getLatestPrice(String symbol, String period) {
        return getLatestPrice(symbol, period, null, false);
    }

    public Map<String, Object> getLatestPrice(String symbol) {
        return getLatestPrice(symbol, "1D", null, false);
    }

    /**
     * Get all latest prices by exchange
     */
    public Map<String, Object> getAllPrices(String exchange, String period) {
        Map<String, Object> params = new HashMap<>();
        params.put("exchange", exchange);
        params.put("period", period != null ? period : "1D");
        return api.request(BASE + "latest", params);
    }

    public Map<String, Object> getAllPrices(String exchange) {
        return getAllPrices(exchange, "1D");
    }

    /**
     * Get latest prices by country and sector
     */
    public Map<String, Object> getLatestByCountry(String country, String sector, String period) {
        Map<String, Object> params = new HashMap<>();
        params.put("country", country);
        params.put("period", period != null ? period : "1D");
        if (sector != null && !sector.isEmpty()) params.put("sector", sector);
        return api.request(BASE + "latest", params);
    }

    public Map<String, Object> getLatestByCountry(String country, String sector) {
        return getLatestByCountry(country, sector, "1D");
    }

    /**
     * Get latest prices by indices
     */
    public Map<String, Object> getLatestByIndices(String indices, String period) {
        Map<String, Object> params = new HashMap<>();
        params.put("indices", indices);
        params.put("period", period != null ? period : "1D");
        return api.request(BASE + "latest", params);
    }

    public Map<String, Object> getLatestByIndices(String indices) {
        return getLatestByIndices(indices, "1D");
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
     * Get stock profile/company details
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

    // ==================== Financial Data ====================

    /**
     * Get earnings data
     */
    public Map<String, Object> getEarnings(String symbol, String duration) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("duration", duration != null ? duration : "both");
        return api.request(BASE + "earnings", params);
    }

    public Map<String, Object> getEarnings(String symbol) {
        return getEarnings(symbol, "both");
    }

    /**
     * Get revenue data
     */
    public Map<String, Object> getRevenue(String symbol) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        return api.request(BASE + "revenue", params);
    }

    /**
     * Get dividends data
     */
    public Map<String, Object> getDividends(String symbol, String format) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("format", format != null ? format : "plain");
        return api.request(BASE + "dividend", params);
    }

    public Map<String, Object> getDividends(String symbol) {
        return getDividends(symbol, "plain");
    }

    /**
     * Get balance sheet data
     */
    public Map<String, Object> getBalanceSheet(String symbol, String duration, String format) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("duration", duration != null ? duration : "annual");
        params.put("format", format != null ? format : "plain");
        return api.request(BASE + "balance_sheet", params);
    }

    public Map<String, Object> getBalanceSheet(String symbol, String duration) {
        return getBalanceSheet(symbol, duration, "plain");
    }

    /**
     * Get income statement data
     */
    public Map<String, Object> getIncomeStatements(String symbol, String duration, String format) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("duration", duration != null ? duration : "annual");
        params.put("format", format != null ? format : "plain");
        return api.request(BASE + "income_statements", params);
    }

    public Map<String, Object> getIncomeStatements(String symbol, String duration) {
        return getIncomeStatements(symbol, duration, "plain");
    }

    /**
     * Get cash flow data
     */
    public Map<String, Object> getCashFlow(String symbol, String duration, String format) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("duration", duration != null ? duration : "annual");
        params.put("format", format != null ? format : "plain");
        return api.request(BASE + "cash_flow", params);
    }

    public Map<String, Object> getCashFlow(String symbol, String duration) {
        return getCashFlow(symbol, duration, "plain");
    }

    /**
     * Get statistics
     */
    public Map<String, Object> getStatistics(String symbol, String duration) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("duration", duration != null ? duration : "annual");
        return api.request(BASE + "statistics", params);
    }

    public Map<String, Object> getStatistics(String symbol) {
        return getStatistics(symbol, "annual");
    }

    /**
     * Get forecast
     */
    public Map<String, Object> getForecast(String symbol) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        return api.request(BASE + "forecast", params);
    }

    /**
     * Get combined financial data
     */
    public Map<String, Object> getStockData(String symbol, String dataColumn, String duration, String format) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("data_column", dataColumn != null ? dataColumn : "profile,earnings,dividends");
        params.put("duration", duration != null ? duration : "annual");
        params.put("format", format != null ? format : "plain");
        return api.request(BASE + "stock_data", params);
    }

    public Map<String, Object> getStockData(String symbol, String dataColumn) {
        return getStockData(symbol, dataColumn, "annual", "plain");
    }

    // ==================== Technical Analysis ====================

    /**
     * Get Moving Averages
     */
    public Map<String, Object> getMovingAverages(String symbol, String period) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("period", period != null ? period : "1D");
        return api.request(BASE + "ma_avg", params);
    }

    /**
     * Get Technical Indicators
     */
    public Map<String, Object> getIndicators(String symbol, String period) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("period", period != null ? period : "1D");
        return api.request(BASE + "indicators", params);
    }

    /**
     * Get Pivot Points
     */
    public Map<String, Object> getPivotPoints(String symbol, String period) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("period", period != null ? period : "1D");
        return api.request(BASE + "pivot_points", params);
    }

    // ==================== Performance ====================

    /**
     * Get Performance Data
     */
    public Map<String, Object> getPerformance(String symbol) {
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        return api.request(BASE + "performance", params);
    }

    // ==================== Advanced Query ====================

    /**
     * Advanced query
     */
    public Map<String, Object> advanced(Map<String, Object> parameters) {
        return api.request(BASE + "advance", parameters);
    }

    // ==================== Top Movers ====================

    /**
     * Get top gainers
     */
    public Map<String, Object> getTopGainers(String exchange, int limit, String period, String country) {
        return getSortedData("active.chp", "desc", limit, exchange, country, period);
    }

    public Map<String, Object> getTopGainers() {
        return getTopGainers(null, 20, "1D", null);
    }

    /**
     * Get top losers
     */
    public Map<String, Object> getTopLosers(String exchange, int limit, String period, String country) {
        return getSortedData("active.chp", "asc", limit, exchange, country, period);
    }

    public Map<String, Object> getTopLosers() {
        return getTopLosers(null, 20, "1D", null);
    }

    /**
     * Get most active
     */
    public Map<String, Object> getMostActive(String exchange, int limit, String period, String country) {
        return getSortedData("active.v", "desc", limit, exchange, country, period);
    }

    public Map<String, Object> getMostActive() {
        return getMostActive(null, 20, "1D", null);
    }

    // ==================== Custom Sorting ====================

    /**
     * Get data with custom sorting
     */
    public Map<String, Object> getSortedData(String sortColumn, String sortDirection, int limit, String exchange, String country, String period) {
        Map<String, Object> params = new HashMap<>();
        params.put("period", period != null ? period : "1D");
        params.put("sort_by", sortColumn + "_" + sortDirection);
        params.put("per_page", limit);
        params.put("merge", "latest");
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        if (country != null && !country.isEmpty()) params.put("country", country);
        return advanced(params);
    }

    // ==================== Search ====================

    /**
     * Search stocks
     */
    public Map<String, Object> search(String query, String exchange, String country) {
        Map<String, Object> params = new HashMap<>();
        params.put("search", query);
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        if (country != null && !country.isEmpty()) params.put("country", country);
        return api.request(BASE + "list", params);
    }

    public Map<String, Object> search(String query) {
        return search(query, null, null);
    }

    // ==================== Filter by Sector/Country ====================

    /**
     * Get stocks by sector
     */
    public Map<String, Object> getBySector(String sector, int limit, String exchange) {
        Map<String, Object> params = new HashMap<>();
        params.put("sector", sector);
        params.put("per_page", limit);
        params.put("merge", "latest");
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        return advanced(params);
    }

    public Map<String, Object> getBySector(String sector, int limit) {
        return getBySector(sector, limit, null);
    }

    /**
     * Get stocks by country
     */
    public Map<String, Object> getByCountry(String country, int limit, String exchange) {
        Map<String, Object> params = new HashMap<>();
        params.put("country", country);
        params.put("per_page", limit);
        params.put("merge", "latest");
        if (exchange != null && !exchange.isEmpty()) params.put("exchange", exchange);
        return advanced(params);
    }

    public Map<String, Object> getByCountry(String country, int limit) {
        return getByCountry(country, limit, null);
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

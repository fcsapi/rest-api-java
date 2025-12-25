/**
 * FCS API - REST API Client
 *
 * Java client for Forex, Cryptocurrency, and Stock market data
 *
 * @package FcsApi
 * @author FCS API <support@fcsapi.com>
 * @link https://fcsapi.com
 */

package com.fcsapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * FCS API REST Client
 * Main client class for accessing Forex, Crypto, and Stock market data.
 */
public class FcsApi {

    private static final String BASE_URL = "https://api-v4.fcsapi.com/";
    private final Gson gson = new Gson();
    private Map<String, Object> lastResponse = new HashMap<>();

    /** Configuration instance */
    private FcsConfig config;

    // Lazy-loaded modules
    private FcsForex forex;
    private FcsCrypto crypto;
    private FcsStock stock;

    /**
     * Get Forex API module (lazy loading)
     */
    public FcsForex getForex() {
        if (forex == null) forex = new FcsForex(this);
        return forex;
    }

    /**
     * Get Crypto API module (lazy loading)
     */
    public FcsCrypto getCrypto() {
        if (crypto == null) crypto = new FcsCrypto(this);
        return crypto;
    }

    /**
     * Get Stock API module (lazy loading)
     */
    public FcsStock getStock() {
        if (stock == null) stock = new FcsStock(this);
        return stock;
    }

    /**
     * Constructor with default config
     */
    public FcsApi() {
        this.config = new FcsConfig();
    }

    /**
     * Constructor with FcsConfig
     * @param config FcsConfig object
     */
    public FcsApi(FcsConfig config) {
        this.config = config != null ? config : new FcsConfig();
    }

    /**
     * Constructor with API key string
     * @param apiKey API key string
     */
    public FcsApi(String apiKey) {
        this.config = FcsConfig.withAccessKey(apiKey);
    }

    /**
     * Set request timeout
     * @param seconds Timeout in seconds
     * @return Self for method chaining
     */
    public FcsApi setTimeout(int seconds) {
        config.setTimeout(seconds);
        return this;
    }

    /**
     * Get config
     * @return FcsConfig instance
     */
    public FcsConfig getConfig() {
        return config;
    }

    /**
     * Generate token for frontend use
     * Only works when AuthMethod is 'token'
     * @return Map with _token, _expiry, _public_key
     */
    public Map<String, Object> generateToken() {
        return config.generateToken();
    }

    /**
     * Make API request (POST with form data)
     * @param endpoint API endpoint
     * @param parameters Request parameters
     * @return Response data or null on error
     */
    public Map<String, Object> request(String endpoint, Map<String, Object> parameters) {
        if (parameters == null) {
            parameters = new HashMap<>();
        }

        // Add authentication parameters
        Map<String, Object> authParams = config.getAuthParams();
        parameters.putAll(authParams);

        String url = BASE_URL + endpoint;

        try {
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(config.getConnectTimeout() * 1000);
            conn.setReadTimeout(config.getTimeout() * 1000);
            conn.setDoOutput(true);

            // Build form data
            StringBuilder formData = new StringBuilder();
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                if (formData.length() > 0) formData.append("&");
                formData.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                formData.append("=");
                formData.append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
            }

            // Send request
            try (OutputStream os = conn.getOutputStream()) {
                os.write(formData.toString().getBytes(StandardCharsets.UTF_8));
            }

            // Read response
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }

            // Parse JSON response
            lastResponse = gson.fromJson(response.toString(), new TypeToken<Map<String, Object>>(){}.getType());
            return lastResponse;

        } catch (Exception e) {
            lastResponse = new HashMap<>();
            lastResponse.put("status", false);
            lastResponse.put("code", 0);
            lastResponse.put("msg", "Request Error: " + e.getMessage());
            lastResponse.put("response", null);
            return null;
        }
    }

    /**
     * Make API request with no parameters
     * @param endpoint API endpoint
     * @return Response data or null on error
     */
    public Map<String, Object> request(String endpoint) {
        return request(endpoint, null);
    }

    /**
     * Get last response
     * @return Last response map
     */
    public Map<String, Object> getLastResponse() {
        return lastResponse;
    }

    /**
     * Get response data only
     * @return Response data or null
     */
    public Object getResponseData() {
        return lastResponse.get("response");
    }

    /**
     * Check if last request was successful
     * @return True if successful, false otherwise
     */
    public boolean isSuccess() {
        Object status = lastResponse.get("status");
        if (status instanceof Boolean) {
            return (Boolean) status;
        }
        return false;
    }

    /**
     * Get error message from last response
     * @return Error message or null if successful
     */
    public String getError() {
        if (isSuccess()) return null;
        Object msg = lastResponse.get("msg");
        return msg != null ? msg.toString() : "Unknown error";
    }
}

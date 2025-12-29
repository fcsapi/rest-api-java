/**
 * FCS API - Configuration
 *
 * Authentication options:
 * 1. access_key - Simple API key authentication
 * 2. ip_whitelist - No key needed if IP is whitelisted in your account https://fcsapi.com/dashboard/profile
 * 3. token - Secure token-based authentication (recommended for frontend)
 *
 * @package FcsApi
 * @author FCS API <support@fcsapi.com>
 */

package com.fcsapi;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * FCS API Configuration class
 * Supports multiple authentication methods:
 * - access_key: Simple API key authentication
 * - ip_whitelist: IP whitelist (no key needed)
 * - token: Secure token-based authentication
 */
public class FcsConfig {

    /** Authentication method: 'access_key', 'ip_whitelist', 'token' */
    private String authMethod = "access_key";

    /** API Access Key (Private Key) - Get from: https://fcsapi.com/dashboard */
    private String accessKey = "YOUR_ACCESS_KEY_HERE";

    /** Public Key (for token-based auth) - Get from: https://fcsapi.com/dashboard */
    private String publicKey = "YOUR_PUBLIC_KEY_HERE";

    /** Token expiry time in seconds. Options: 300 (5min), 900 (15min), 1800 (30min), 3600 (1hr), 86400 (24hr) */
    private int tokenExpiry = 3600;

    /** Request timeout in seconds */
    private int timeout = 30;

    /** Connection timeout in seconds */
    private int connectTimeout = 5;

    // Getters and Setters
    public String getAuthMethod() { return authMethod; }
    public void setAuthMethod(String authMethod) { this.authMethod = authMethod; }

    public String getAccessKey() { return accessKey; }
    public void setAccessKey(String accessKey) { this.accessKey = accessKey; }

    public String getPublicKey() { return publicKey; }
    public void setPublicKey(String publicKey) { this.publicKey = publicKey; }

    public int getTokenExpiry() { return tokenExpiry; }
    public void setTokenExpiry(int tokenExpiry) { this.tokenExpiry = tokenExpiry; }

    public int getTimeout() { return timeout; }
    public void setTimeout(int timeout) { this.timeout = timeout; }

    public int getConnectTimeout() { return connectTimeout; }
    public void setConnectTimeout(int connectTimeout) { this.connectTimeout = connectTimeout; }

    /**
     * Create config with access_key method
     * @param accessKey Your API access key
     * @return FcsConfig instance
     */
    public static FcsConfig withAccessKey(String accessKey) {
        FcsConfig config = new FcsConfig();
        config.authMethod = "access_key";
        config.accessKey = accessKey;
        return config;
    }

    /**
     * Create config with IP whitelist method (no key needed)
     * @return FcsConfig instance
     */
    public static FcsConfig withIpWhitelist() {
        FcsConfig config = new FcsConfig();
        config.authMethod = "ip_whitelist";
        return config;
    }

    /**
     * Create config with token-based authentication
     * @param accessKey Your private API key (kept on server)
     * @param publicKey Your public key
     * @param tokenExpiry Token validity in seconds
     * @return FcsConfig instance
     */
    public static FcsConfig withToken(String accessKey, String publicKey, int tokenExpiry) {
        FcsConfig config = new FcsConfig();
        config.authMethod = "token";
        config.accessKey = accessKey;
        config.publicKey = publicKey;
        config.tokenExpiry = tokenExpiry;
        return config;
    }

    /**
     * Generate authentication token
     * Use this on your backend, then send token to frontend
     * @return Map with _token, _expiry, _public_key
     */
    public Map<String, Object> generateToken() {
        long expiry = System.currentTimeMillis() / 1000 + tokenExpiry;
        String message = publicKey + expiry;
        String token = computeHmacSha256(message, accessKey);

        Map<String, Object> result = new HashMap<>();
        result.put("_token", token);
        result.put("_expiry", expiry);
        result.put("_public_key", publicKey);
        return result;
    }

    /**
     * Get authentication parameters for API request
     * @return Map with authentication parameters
     */
    public Map<String, Object> getAuthParams() {
        Map<String, Object> params = new HashMap<>();

        switch (authMethod) {
            case "ip_whitelist":
                return params;
            case "token":
                return generateToken();
            default: // access_key
                params.put("access_key", accessKey);
                return params;
        }
    }

    /**
     * Compute HMAC-SHA256 hash
     */
    private static String computeHmacSha256(String message, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hashBytes = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to compute HMAC-SHA256", e);
        }
    }
}

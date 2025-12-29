/*
 * FCS API - Authentication Example
 * Get your API key at: https://fcsapi.com
 *
 * This example shows different authentication methods:
 * 1. Default config (from FcsConfig.java)
 * 2. Direct API key
 * 3. IP Whitelist (no key needed)
 * 4. Token-based (for frontend security)
 *
 * Run: javac -cp ".:../../lib/*" AuthExample.java && java -cp ".:../../lib/*" AuthExample
 */

package examples;

import com.fcsapi.FcsApi;
import com.fcsapi.FcsConfig;
import java.util.Map;

public class AuthExample {

    public static void main(String[] args) {
        // ==========================================
        // Method 1: Default Configuration
        // ==========================================
        // Uses settings from FcsConfig.java
        System.out.println("=== Method 1: Default Config ===\n");
        FcsApi fcsapi1 = new FcsApi();
        System.out.println("Auth Method: " + fcsapi1.getConfig().getAuthMethod());

        // ==========================================
        // Method 2: Direct API Key
        // ==========================================
        // Pass API key directly
        System.out.println("\n=== Method 2: Direct API Key ===\n");
        FcsApi fcsapi2 = new FcsApi("YOUR_API_KEY_HERE");
        System.out.println("Auth Method: " + fcsapi2.getConfig().getAuthMethod());
        System.out.println("Access Key: " + fcsapi2.getConfig().getAccessKey());

        // ==========================================
        // Method 3: IP Whitelist
        // ==========================================
        // No API key needed if IP is whitelisted in dashboard
        System.out.println("\n=== Method 3: IP Whitelist ===\n");
        FcsConfig ipConfig = FcsConfig.withIpWhitelist();
        FcsApi fcsapi3 = new FcsApi(ipConfig);
        System.out.println("Auth Method: " + fcsapi3.getConfig().getAuthMethod());

        // ==========================================
        // Method 4: Token-Based Authentication
        // ==========================================
        // For secure frontend use - token generated on backend
        System.out.println("\n=== Method 4: Token-Based ===\n");
        FcsConfig tokenConfig = FcsConfig.withToken(
            "YOUR_ACCESS_KEY",   // Private key (keep on server)
            "YOUR_PUBLIC_KEY",   // Public key (can be shared)
            3600                  // Token expiry in seconds (1 hour)
        );
        FcsApi fcsapi4 = new FcsApi(tokenConfig);

        // Generate token to send to frontend
        Map<String, Object> tokenData = fcsapi4.generateToken();
        System.out.println("Generated Token Data:");
        System.out.println("  _token: " + tokenData.get("_token"));
        System.out.println("  _expiry: " + tokenData.get("_expiry"));
        System.out.println("  _public_key: " + tokenData.get("_public_key"));

        // ==========================================
        // Make API Call with Any Method
        // ==========================================
        System.out.println("\n=== Making API Call ===\n");
        // Using Method 1 (default config)
        Map<String, Object> result = fcsapi1.getForex().getLatestPrice("FX:EURUSD");

        if (fcsapi1.isSuccess()) {
            System.out.println("Success! Response:");
            System.out.println(result);
        } else {
            System.out.println("Error: " + fcsapi1.getError());
        }
    }
}

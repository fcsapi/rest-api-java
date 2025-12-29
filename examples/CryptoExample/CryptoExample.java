/*
 * FCS API - Crypto Example
 * Get your API key at: https://fcsapi.com
 *
 * This example only uses the Crypto module (FcsCrypto)
 * Other modules (Forex, Stock) are not loaded
 *
 * Run: javac -cp ".:../../lib/*" CryptoExample.java && java -cp ".:../../lib/*" CryptoExample
 */

package examples;

import com.fcsapi.FcsApi;
import com.fcsapi.FcsCrypto;
import java.util.Map;

public class CryptoExample {

    public static void main(String[] args) {
        // Initialize API - only Crypto module will be used
        FcsApi fcsapi = new FcsApi();

        // Access only Crypto module
        FcsCrypto crypto = fcsapi.getCrypto();

        System.out.println("=== Symbols List ===\n");
        Map<String, Object> result = crypto.getSymbolsList("crypto", null, "BINANCE");
        System.out.println(result);

        System.out.println("\n\n=== Latest Price ===\n");
        result = crypto.getLatestPrice("BINANCE:BTCUSDT");
        System.out.println(result);

        System.out.println("\n\n=== Historical Data ===\n");
        result = crypto.getHistory("BINANCE:BTCUSDT", "1D", 5);
        System.out.println(result);

        System.out.println("\n\n=== Profile ===\n");
        result = crypto.getProfile("BTC");
        System.out.println(result);

        System.out.println("\n\n=== Top by Market Cap ===\n");
        result = crypto.getTopByMarketCap(10);
        System.out.println(result);

        System.out.println("\n\n=== Convert 1 BTC to USD ===\n");
        result = crypto.convert("BTC", "USD", 1);
        System.out.println(result);
    }
}

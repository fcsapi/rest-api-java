/*
 * FCS API - Forex Example
 * Get your API key at: https://fcsapi.com
 *
 * This example only uses the Forex module (FcsForex)
 * Other modules (Crypto, Stock) are not loaded
 *
 * Run: javac -cp ".:../../lib/*" ForexExample.java && java -cp ".:../../lib/*" ForexExample
 */

package examples;

import com.fcsapi.FcsApi;
import com.fcsapi.FcsForex;
import java.util.Map;

public class ForexExample {

    public static void main(String[] args) {
        // Initialize API - only Forex module will be used
        FcsApi fcsapi = new FcsApi();

        // Access only Forex module
        FcsForex forex = fcsapi.getForex();

        System.out.println("=== Symbols List ===\n");
        Map<String, Object> result = forex.getSymbolsList("forex", "spot", null);
        System.out.println(result);

        System.out.println("\n\n=== Latest Price ===\n");
        result = forex.getLatestPrice("FX:EURUSD");
        System.out.println(result);

        System.out.println("\n\n=== Historical Data ===\n");
        result = forex.getHistory("EURUSD", "1D", 5);
        System.out.println(result);

        System.out.println("\n\n=== Profile ===\n");
        result = forex.getProfile("EUR");
        System.out.println(result);
    }
}

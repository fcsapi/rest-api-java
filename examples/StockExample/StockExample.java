/*
 * FCS API - Stock Example
 * Get your API key at: https://fcsapi.com
 *
 * This example only uses the Stock module (FcsStock)
 * Other modules (Forex, Crypto) are not loaded
 *
 * Run: javac -cp ".:../../lib/*" StockExample.java && java -cp ".:../../lib/*" StockExample
 */

package examples;

import com.fcsapi.FcsApi;
import com.fcsapi.FcsStock;
import java.util.Map;

public class StockExample {

    public static void main(String[] args) {
        // Initialize API - only Stock module will be used
        FcsApi fcsapi = new FcsApi();

        // Access only Stock module
        FcsStock stock = fcsapi.getStock();

        System.out.println("=== Symbols List ===\n");
        Map<String, Object> result = stock.getSymbolsList("NASDAQ");
        System.out.println(result);

        System.out.println("\n\n=== Latest Price ===\n");
        result = stock.getLatestPrice("NASDAQ:AAPL");
        System.out.println(result);

        System.out.println("\n\n=== Historical Data ===\n");
        result = stock.getHistory("NASDAQ:AAPL", "1D", 5);
        System.out.println(result);

        System.out.println("\n\n=== Profile ===\n");
        result = stock.getProfile("AAPL");
        System.out.println(result);

        System.out.println("\n\n=== Earnings ===\n");
        result = stock.getEarnings("NASDAQ:AAPL");
        System.out.println(result);

        System.out.println("\n\n=== Indices Latest ===\n");
        result = stock.getIndicesLatest("NASDAQ:NDX,SP:SPX");
        System.out.println(result);
    }
}

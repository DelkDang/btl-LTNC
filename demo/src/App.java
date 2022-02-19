import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;


public class App {
    public static void main(String[] args) throws IOException
    {
        String[] arr = new String[] {"bitcoin", "ethereum", "tether", "binancecoin", "usd-coin", "ripple", "cardano", "solana", "avalanche-2", "terra-luna"};
        for(int i = 0; i < arr.length; i++)
        {
            String coin = arr[i];
            double price = getPriceCoin(coin);
            System.out.println("price of " + coin + " = " + price);
        }
        
    }

    private static double getPriceCoin(String coin) throws IOException
    {
        String getURL = "https://api.coingecko.com/api/v3/simple/price?ids=" + coin + "&vs_currencies=usd";
        double price = -1;
        URL url = new URL(getURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK)
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String input;
            StringBuffer data = new StringBuffer();
            while((input = in.readLine()) != null)
            {
                data.append(input);
            }
            in.close();
            JSONObject obj = new JSONObject(data.toString());
            price = obj.getJSONObject(coin).getDouble("usd");
        }
        return price;
    }
}
 
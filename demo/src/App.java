import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.json.JSONObject;


public class App {
    public static void main(String[] args) throws IOException
    {
        String[] arr = new String[] {"bitcoin", "ethereum", "tether", "binancecoin", "usd-coin", "ripple", "cardano", "solana", "avalanche-2", "terra-luna"};
        int len = arr.length;

        JFrame window = new JFrame();
        JPanel mainPanel = new JPanel();
        JPanel coinPanel = new JPanel();
        JPanel pricePanel = new JPanel();
        window.setTitle("Demo code");
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set layout
        BorderLayout winLayout = new BorderLayout();
        GridLayout gLayout1 = new GridLayout(1, 2);
        GridLayout gLayout2 = new GridLayout(len, 1);
        window.setLayout(winLayout);
        mainPanel.setLayout(gLayout1);
        coinPanel.setLayout(gLayout2);
        pricePanel.setLayout(gLayout2);

        window.add(mainPanel, BorderLayout.CENTER);
        mainPanel.add(coinPanel);
        mainPanel.add(pricePanel);
        

        JLabel[] priceLabel = new JLabel[len];
        for(int i = 0; i < len; i++)
        {
            JLabel coinLabel = new JLabel(arr[i]);
            priceLabel[i] = new JLabel(getPriceOf(arr[i]));
            pricePanel.add(priceLabel[i]);
            coinPanel.add(coinLabel);
        }
        window.setVisible(true);
    }
    

    private static String getPriceOf(String coin) throws IOException
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
                data.append(input);
            in.close();
            JSONObject obj = new JSONObject(data.toString());
            price = obj.getJSONObject(coin).getDouble("usd");
        }
        return Double.toString(price);
    }
}
 
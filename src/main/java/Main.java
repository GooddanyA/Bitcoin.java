import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;

class Main {
    private static double price;
    private static double max;
    private static double min= 10000000;
    public static void main(String[] args) throws InterruptedException {
        String urlAdr = "https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT";
        final URLConnection[] urlConnection = {null};
        final URL[] url = {null};
        final InputStreamReader[] inputStreamReader = {null};
        final BufferedReader[] bufferedReader = {null};

        Thread run = new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                long end = start + 10000;

                while (System.currentTimeMillis() < end) {
                    try {
                        url[0] = new URL(urlAdr);
                        urlConnection[0] = url[0].openConnection();
                        inputStreamReader[0] = new InputStreamReader(urlConnection[0].getInputStream());
                        bufferedReader[0] = new BufferedReader(inputStreamReader[0]);

                        String jsonString = bufferedReader[0].readLine();
                        JSONObject json = new JSONObject(jsonString);
                        price = Double.parseDouble(String.valueOf(json.get("price")));
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                        Date date = new Date(System.currentTimeMillis());
                        System.out.print(formatter.format(date));
                        System.out.println("      price BTCUSDH: " + price);
                        sleep(1000);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    } finally {
                        try {
                            inputStreamReader[0].close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            bufferedReader[0].close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (max <= price){
                        max = price;
                    }
                    if(min >= price){
                        min=price;

                    }

                }
            }
        });
        run.start();
        sleep(10200);
        System.out.println("The maximum value of Bitcoin for a given period of time "+max);
        System.out.println("The minimum value of Bitcoin for a given period of time "+min);
    }

}


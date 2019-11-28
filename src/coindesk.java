import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import static java.lang.Integer.parseInt;

public class coindesk {

    /*
     *  Main entry of the programme
     * */

    public static void main(String []args) throws Exception {

        String urlString = "https://api.coindesk.com/v1/bpi/historical/close.json?currency=" + args[0];
        LocalDate localDate = LocalDate.now();
        LocalDate currentMonth = localDate.minus(2, ChronoUnit.DAYS);
        URL url = new URL(urlString);

        if(parseInt(args[1]) == 0) {
            currentBtcPrice(url, currentMonth, args[0]);
        } else {
            LocalDate previousMonth = localDate.minus(1, ChronoUnit.MONTHS)
                    .minus(0, ChronoUnit.DAYS);
            getHistoryBtcPrice(url, currentMonth, previousMonth, args[0]);
        }
    }

    /* This function developed to display current month Bitcoin price.
     *
     * @param URL needs URL class instance initialized by URL string
     * @param currentMonth which takes current month as instance of LocaleDate
     * @param currency takes currency code
     */
    private static void currentBtcPrice(URL url, LocalDate currentMonth, String currency) throws Exception {
        try {
            InputStream inputStream = url.openStream();
            BufferedReader readBuffer = new BufferedReader(new InputStreamReader(inputStream));
            JsonObject jsonObject = new JsonParser().parse(readBuffer.readLine()).getAsJsonObject();
            jsonObject = jsonObject.get("bpi").getAsJsonObject();
            System.out.println(" Price for 1 BTC = " + jsonObject.get(currentMonth.toString()) + " " + currency);
        } catch (FileNotFoundException currencyNotFound) {
            System.out.println("Wrong currency code.");
        }
    }

    /* This function developed to display previous month Bitcoin price and current month.
     *
     * @param URL needs URL class instance initialized by URL string
     * @param currentMonth which takes current month as instance of LocaleDate
     * @param previousMonth which takes previous month as instance of LocaleDate
     * @param currency takes currency code
     */
    private static void getHistoryBtcPrice(URL url, LocalDate currentMonth, LocalDate priviousMonth, String currency)
            throws Exception {
        try {
            InputStream inputStream = url.openStream();
            BufferedReader readBuffer = new BufferedReader(new InputStreamReader(inputStream));
            JsonObject jsonObject = new JsonParser().parse(readBuffer.readLine()).getAsJsonObject();
            jsonObject = jsonObject.get("bpi").getAsJsonObject();

            System.out.println("Previous Month " + currency + " Price for 1 BTC = "
                    + jsonObject.get(priviousMonth.toString()));
            System.out.println(" Current Month "
                    + currency + " Price for 1 BTC = " + jsonObject.get(currentMonth.toString()));
        } catch (FileNotFoundException currencyNotFound) {
            System.out.println("Wrong currency code.");
        }
    }
}

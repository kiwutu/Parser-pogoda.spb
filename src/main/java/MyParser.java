import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyParser {

    public static void main(String[] args) throws Exception {
        tableElementsQuery();
    }


    // request for html
    private static Document getPage() throws IOException {
        String url = "http://pogoda.spb.ru/";
        return Jsoup.parse(new URL(url), 3000);
    }

    // cuts elements from a string (2 characters, period, 2 characters)
    private static final Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");

    private static String getDateFromString(String stringDate) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()) return matcher.group();
        throw new Exception("Can't extract date from string!");
    }

    private static void tableElementsQuery() throws Exception {
        Document page = getPage();

        // data table query
        Element tableWth = page.select("table[class=wt]").first();
        // table elements line by line (Date, Phenomena, Temperature, Pressure, Humidity, Wind)
        Elements names = tableWth.select("tr[class=wth]");
        // time of day line by line(Morning, Afternoon, Evening, Night)
        Elements values = tableWth.select("tr[valign=top]");


        for (Element name : names) {
            String dateString = name.select("th[id=dt]").text();
            String date = getDateFromString(dateString);
            System.out.println(date + "  Явления  Температура  Давление  Влажность  Ветер");
            System.out.println();
            printForValues(values);
            System.out.println();
//            indexLine+=4;
        }

    }

    static int indexLine = 0;
    private static void printForValues(Elements values) {


        Element valueLn = values.get(3);
        String a = valueLn.text();
        a = a.substring(0, a.indexOf(" "));


        if (indexLine == 0) {
            switch (a) {
                case "Утро":
                    for (int i = 0; i < 3; i++, indexLine++) {
                        Element valueLine = values.get(indexLine);
                        for (Element td : valueLine.select("td")) {
                            System.out.print(td.text() + "    ");
                        }
                        System.out.println();
                    }
                    break;

                case "День":
                    for (int i = 0; i < 2; i++, indexLine++) {
                        Element valueLine = values.get(indexLine);
                        for (Element td : valueLine.select("td")) {
                            System.out.print(td.text() + "    ");
                        }
                        System.out.println();
                    }

                    break;
                case "Вечер":
                    for (int i = 0; i < 1; i++, indexLine++) {
                        Element valueLine = values.get(indexLine);
                        for (Element td : valueLine.select("td")) {
                            System.out.print(td.text() + "    ");
                        }
                        System.out.println();
                    }

                    break;
                case "Ночь":
                    for (int i = 0; i < 4; i++, indexLine++) {
                        Element valueLine = values.get(indexLine);
                        for (Element td : valueLine.select("td")) {
                            System.out.print(td.text() + "    ");
                        }
                        System.out.println();
                    }

                    break;
            }
//            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        } else {
            for (
                    int i = 0;
                    indexLine < (indexLine + 4) && i < 4;
                    i++, indexLine++
            ) {
                if (indexLine >= values.size()) break;
                Element valueLine = values.get(indexLine);
                for (Element td : valueLine.select("td")) {
                    System.out.print(td.text() + "    ");
                }
                System.out.println();
            }


        }
    }
}





























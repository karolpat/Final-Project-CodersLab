package pl.coderslab.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Currency {

	public List<Double> getCurrency() throws Exception {

		String sURL = "http://api.nbp.pl/api/exchangerates/tables/A?format=json";
		URL url = new URL(sURL);
		URLConnection con = url.openConnection();
		InputStream is = con.getInputStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder line = new StringBuilder();
		String next = null;
		while ((next = br.readLine()) != null) {
			line.append(next);
		}

		String tmp = line.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\{", "").replaceAll("\\}", "")
				.replaceAll("\"", "").replaceAll(":", " ").replaceAll(",", " ");
		String[] split = tmp.split("\\s");
		String[] currency = new String[6];
		
		for (String s : split) {
			currency[0] = split[18];
			currency[1] = split[20];
			currency[2] = split[59];
			currency[3] = split[61];
			currency[4] = split[80];
			currency[5] = split[82];
		}
		
		List<Double> currencies = new ArrayList<>();
		currencies.add(Double.parseDouble(currency[1]));
		currencies.add(Double.parseDouble(currency[3]));
		currencies.add(Double.parseDouble(currency[5]));
		System.out.println(Double.parseDouble(currency[1])+ " <-usd, eur-> " + Double.parseDouble(currency[3]) + " gbp-> " + Double.parseDouble(currency[5]));

		return currencies;
	}

}

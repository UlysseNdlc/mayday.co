
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpURLConnectionMayday {

	//main de test
	public static void main(String[] args) throws Exception {

		HttpURLConnectionMayday http = new HttpURLConnectionMayday();

		System.out.println(http.getCoordonnees());
		http.addCoordonnees(49.001, 2.1231);
	}

	// HTTP GET request
	private void addCoordonnees(double latitude, double longitude) throws Exception {

		String url = "http://mayday-co.esiea.fr/ajout_de_coordonnees.php?lat=" + latitude + "&long=" + longitude;
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		BufferedReader sent = new BufferedReader(new InputStreamReader(con.getInputStream()));
		sent.close();
	}
	
	
	private String getCoordonnees() throws Exception {

		String url = "http://mayday-co.esiea.fr/ajax.php";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//return ajax object
		return response.toString();

	}
}
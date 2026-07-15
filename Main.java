import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import org.json.JSONObject;
public class Main{
    public static void main(String[] args)throws IOException,InterruptedException{
        Scanner sc=new Scanner(System.in);
        System.out.println("=================================");
        System.out.println("      Currency Converter");
        System.out.println("=================================");
        System.out.print("Enter source currency (USD,INR,EUR): ");
        String from=sc.nextLine().toUpperCase();
        System.out.print("Enter target currency (USD,INR,EUR): ");
        String to=sc.nextLine().toUpperCase();
        System.out.print("Enter amount: ");
        double amount=sc.nextDouble();
        String apiKey="YOUR_API_KEY";
        String url="https://v6.exchangerate-api.com/v6/"+apiKey+"/latest/"+from;
        HttpClient client=HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response=client.send(request,HttpResponse.BodyHandlers.ofString());
        JSONObject json=new JSONObject(response.body());
        String result=json.getString("result");
        if(result.equals("success")){
            JSONObject rates=json.getJSONObject("conversion_rates");
            double rate=rates.getDouble(to);
            double convertedAmount=amount*rate;
            System.out.println("\n--------------------------------");
            System.out.printf("%.2f %s = %.2f %s%n",amount,from,convertedAmount,to);
            System.out.println("--------------------------------");
        }
        else{
            System.out.println("Invalid currency code or API error.");
        }
        sc.close();
    }
}

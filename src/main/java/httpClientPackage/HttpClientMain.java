package httpClientPackage;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wtorales
 * -u URL a la que se quiere invocar
 * -m Metodo http a ser utilizado. Ej. GET, POST
 * -p Parametros para enviar en la peticion
 */
public class HttpClientMain {
    public static final String[] para = {"-u", "-m", "-p"};

    public static void main(String[] args) throws Exception {
        if (args == null || args.length == 0) throw new Exception("Faltan parametros para la consulta");
        if (!(para[0].equals(args[0]) && para[1].equals(args[2]) && para[2].equals(args[4])))
            throw new Exception("Faltan parametros para la consulta");
        String URL = args[1];
        String metodo = args[3];
        String queryParams = args[5];
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
        	FileWriter fichero = null;
            PrintWriter pw = null;
            if (metodo.equals("GET")) {
            	HttpGet httpGet = null;
            	if (queryParams != null && !queryParams.isEmpty()) {
            		httpGet = new HttpGet(URL+"?"+queryParams);
            	} else {
            		httpGet = new HttpGet(URL);
            	}
                try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
                    try {
                        fichero = new FileWriter("miHttpClient.txt");
                        pw = new PrintWriter(fichero);

                        System.out.println("Metodo: "+httpGet.getMethod()+"\n");
                        pw.println("Metodo: "+httpGet.getMethod()+"\n");
                        System.out.println(response1.getVersion()+" "+response1.getCode() + " " + response1.getReasonPhrase());
                        pw.println(response1.getVersion()+" "+response1.getCode() + " " + response1.getReasonPhrase());
                        List<Header> httpHeaders = Arrays.asList(response1.getHeaders());        
                        for (Header header : httpHeaders) {
                            System.out.println("Header: "+header.getName() + " Value: " + header.getValue());
                            pw.println("Header: "+header.getName() + " Value: " + header.getValue());
                        };
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                       try {
	                       if (null != fichero)
	                          fichero.close();
                       } catch (Exception e2) {
                          e2.printStackTrace();
                       }
                    }
                    HttpEntity entity1 = response1.getEntity();
                    EntityUtils.consume(entity1);
                }
            } else if (metodo.equals("POST")) {
                HttpPost httpPost = null;
                if (queryParams != null && !queryParams.isEmpty()) {
                	httpPost = new HttpPost(URL+"?"+queryParams);
                } else {
                	httpPost = new HttpPost(URL);
                }
                List<NameValuePair> nvps = new ArrayList<>();
                nvps.add(new BasicNameValuePair("username", "vip"));
                nvps.add(new BasicNameValuePair("password", "secret"));
                httpPost.setEntity(new UrlEncodedFormEntity(nvps));
                try (CloseableHttpResponse response2 = httpclient.execute(httpPost)) {
                	try {
                        fichero = new FileWriter("miHttpClient.txt");
                        pw = new PrintWriter(fichero);
                        System.out.println("Metodo: "+httpPost.getMethod()+"\n");
                        pw.println("Metodo: "+httpPost.getMethod()+"\n");
                        System.out.println(response2.getVersion()+" "+response2.getCode() + " " + response2.getReasonPhrase());
                        pw.println(response2.getVersion()+" "+response2.getCode() + " " + response2.getReasonPhrase());
                        List<Header> httpHeaders = Arrays.asList(response2.getHeaders());        
                        for (Header header : httpHeaders) {
                            System.out.println("Header: "+header.getName() + " Value: " + header.getValue());
                            pw.println("Header: "+header.getName() + " Value: " + header.getValue());
                        };
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                       try {
	                       if (null != fichero)
	                          fichero.close();
                       } catch (Exception e2) {
                          e2.printStackTrace();
                       }
                    }
                    HttpEntity entity2 = response2.getEntity();
                    EntityUtils.consume(entity2);
                }
            }

        }
    }
}

package android.example.thequotes;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class QueryUtils {
    public static final String LOG_TAG=QueryUtils.class.getSimpleName();
    private static int size;
    private QueryUtils() {
    }
    private static URL createUrl(String stringUrl) throws MalformedURLException {
        URL url=null;
        try{
            url=new URL(stringUrl);
        }catch (MalformedURLException e)
        {
            Log.e(LOG_TAG,"problem building the url ",e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url)throws IOException
    {
        String jsonResponse="";
        if(url==null)
        {
            return jsonResponse;
        }
        HttpsURLConnection urlConnection=null;
        InputStream inputStream=null;
        try {
            urlConnection=(HttpsURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);//? these are in milliseconds
            urlConnection.setConnectTimeout(15000);//? these are in milliseconds, I want to return false if the URL takes more then 15 seconds to connect
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200)
            {
                inputStream=urlConnection.getInputStream();
                jsonResponse=readFromStream(inputStream);
                size = inputStream.available();
            }
            else
            {
                Log.e(LOG_TAG,"Error Response Code "+urlConnection.getResponseCode());
            }
        }catch (IOException e)
        {
            Log.e(LOG_TAG,"problem retrieving the quotes json result",e);
        }
        finally {
            if(urlConnection!=null)
            {
                urlConnection.disconnect();
            }
            if(inputStream!=null)
            {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output=new StringBuilder();
        if(inputStream!=null)
        {
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader reader=new BufferedReader(inputStreamReader);
            String line=reader.readLine();
            while (line!=null)
            {
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }
    /*
    private static List<Quote> extractFeaturesFromJson(String quotesJSON) {
        if(TextUtils.isEmpty(quotesJSON))
        {
            return null;
        }
        List<Quote> quotesArray=new ArrayList<>();
        try {
            JSONObject quote=new JSONObject(quotesJSON);
            JSONArray quotes=new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
     */
    /*
    public static List<Quote> fetchQuotesData(String requestUrl) throws IOException {
        URL url=createUrl(requestUrl);
        String jsonResponse=null;
        try {
            jsonResponse=makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG,"problem making the Http request.",e);
        }
        return extractFeaturesFromJson(jsonResponse);
    }
     */
}

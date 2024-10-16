package com.example.demoapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class JSONDIsplayActivity extends AppCompatActivity {
    TextView xml,json;
    int mode = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_sondisplay);
        xml=(TextView) findViewById(R.id.xmlid);
        json=(TextView) findViewById(R.id.jsonid);
        mode= getIntent().getIntExtra("mode",0);
        if (mode==1){
            parsexml();
        }else if(mode==2){
            parsejson();
        }
    }

    private void parsejson() {
        try {
            InputStream is = getAssets().open("input.json");
            byte[] data = new byte[is.available()];
            is.read(data);
            String datas= new String(data);
            Log.e("data","ParseJSON"+datas);
            JSONObject js = new JSONObject(datas);
            JSONObject emp = js.getJSONObject("Employee");
            json.setText("CityName : "+emp.getString("CityName")+"\n");
            json.append("Latitude : "+emp.getString("Latitude")+"\n");
            json.append("Longitude : "+emp.getString("Longitude")+"\n");
            json.append("Temperature : "+emp.getString("Temperature")+"\n");
            json.append("Humidity : "+emp.getString("Humidity")+"\n");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /*private void parsexml(){
        try {
            InputStream is = getAssets().open("input.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse(is);
            NodeList nl = d.getElementsByTagName("Employee");
            Node n = nl.item(0);
            Element emp = (Element)n;
            xml.setText("cityName"+emp.getElementsByTagName("cityName").item(0).getTextContent()+"\n");
            xml.setText("Latitude"+emp.getElementsByTagName("Latitude").item(0).getTextContent()+"\n");
            xml.setText("Longitude"+emp.getElementsByTagName("Longitude").item(0).getTextContent()+"\n");
            xml.setText("Temperature"+emp.getElementsByTagName("Temperature").item(0).getTextContent()+"\n");
            xml.setText("Humidity"+emp.getElementsByTagName("Humidity").item(0).getTextContent()+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }*/


    private void parsexml() {
        try {
            InputStream is = getAssets().open("input.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse(is);
            NodeList nl = d.getElementsByTagName("Employee");
            Node n = nl.item(0);
            Element emp = (Element) n;

            // Initialize TextView content with append
            xml.setText("CityName: " + emp.getElementsByTagName("cityName").item(0).getTextContent() + "\n");
            xml.append("Latitude: " + emp.getElementsByTagName("Latitude").item(0).getTextContent() + "\n");
            xml.append("Longitude: " + emp.getElementsByTagName("Longitude").item(0).getTextContent() + "\n");
            xml.append("Temperature: " + emp.getElementsByTagName("Temperature").item(0).getTextContent() + "\n");
            xml.append("Humidity: " + emp.getElementsByTagName("Humidity").item(0).getTextContent() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

}
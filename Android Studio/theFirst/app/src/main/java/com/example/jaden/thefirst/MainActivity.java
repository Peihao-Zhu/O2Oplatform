package com.example.jaden.thefirst;

import android.content.Intent;
import android.net.wifi.aware.DiscoverySession;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.system.Os.read;

public class MainActivity extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Button Login;
    Button Register;
    EditText Username;
    EditText Password;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login=(Button)findViewById(R.id.button_login);
        Register=(Button)findViewById(R.id.button_register);
        Username=(EditText)findViewById(R.id.editText_login_username);
        Password=(EditText)findViewById(R.id.editText_login_password);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            username=Username.getText().toString().trim();
            password=Password.getText().toString().trim();
           // Toast.makeText(MainActivity.this,username+""+password,Toast.LENGTH_SHORT).show();
            SendByHttpClient(username,password);

           Intent intent =new Intent(MainActivity.this,HomePage.class);
           startActivity(intent);
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,RegisterPage.class);
                startActivity(intent);
            }
        });
    }


    public void SendByHttpClient(final String username, final String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.print("nihao");
                    //Log.d(MainActivity,"");
                    JSONObject userjson=new JSONObject();
                    userjson.put("username",username);
                    userjson.put("password",password);
                    String message=String.valueOf(userjson);
                    URL url=new URL("http://10.23.81.177:8080/Test/Demo");
                    //URL url=new URL("http://www.baidu.com");
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=RequestBody.create(JSON,message);
                    Request request=new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    //showResponse(responseData);
                    //解析json数据
                    //parseJSONWithJSONObject(r=esponseData);

                    Message Message=new Message();//
                    Message.what=SHOW_RESPONSE;
                    Message.obj=responseData;
                    handler.sendMessage(Message);//使用Message传递消息给线程

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void showResponse(String response)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,"nihap",Toast.LENGTH_LONG).show();
            }
        });
    }
    public static final int SHOW_RESPONSE=1;
    public Handler handler=new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    private void parseJSONWithJSONObject(String jsonData){
        try{
            JSONArray jsonArray=new JSONArray(jsonData);
            for(int i=0;i<jsonArray.length();i++)
            {

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

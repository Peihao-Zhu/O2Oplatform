package com.example.jaden.thefirst;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.jaden.thefirst.MainActivity.JSON;

public class RegisterPage extends AppCompatActivity {

    EditText setUsername;
    EditText setPassword;
    EditText confirmPassword;
    Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        confirm=(Button)findViewById(R.id.button_confirm);
        setUsername=(EditText)findViewById(R.id.editText_register_username);
        setPassword=(EditText)findViewById(R.id.editText_register_password);
        confirmPassword=(EditText)findViewById(R.id.editText_register_password2);
        String username=setUsername.getText().toString().trim();
        String password=setPassword.getText().toString().trim();
        String confirmerpassword=confirmPassword.getText().toString().trim();
        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(!password.equals(confirmerpassword))
                {
                    Toast.makeText(RegisterPage.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    setPassword.setText("");
                    confirmPassword.setText("");

                }
                else {
                    SendByHttpClient(username,password);
                    //Intent intent = new Intent(RegisterPage.this, MainActivity.class);
                    //startActivity(intent);
                }
            }
        });
    }

    public void SendByHttpClient(final String username, final String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
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
    public static final int SHOW_RESPONSE=1;
    public Handler handler=new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    Toast.makeText(RegisterPage.this, response, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}

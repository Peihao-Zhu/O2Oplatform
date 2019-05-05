package com.Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.DBTool.DBUtil;

/**
 * Servlet implementation class Demo
 */
@WebServlet("/Demo")
public class Demo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Demo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		//处理json内容
		System.out.println(111);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json");
        PrintWriter out = response.getWriter();
        String acceptjson = "";
        String username="";
        String password="";
        boolean type=false;
        try {
        	InputStreamReader reader=new InputStreamReader((ServletInputStream)request.getInputStream(),"utf-8");
            BufferedReader br = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer("");
            String temp;
            while((temp = br.readLine()) != null){
                sb.append(temp);
            }
            br.close();
            //以上的过程都从request中读取json，并将json转换成string，这样可以显示出来。最终String类型的json就是acceptjson　　
            acceptjson = sb.toString();
            System.out.println("=======json is==========="+acceptjson);
            if(acceptjson != ""){
                //System.out.println("get the json successfully");
                JSONObject jo = new JSONObject(acceptjson);
                username=jo.getString("username");
                password=jo.getString("password");
                ResultSet rs;
                int issuccess = 0; 
                ArrayList<String>list1=null;
                	Connection con=DBUtil.getConnection();
                	Statement stmt=con.createStatement();
                	String sql="select * from userinfo where username=\""+username+"\" and password=\""+password+"\"";
                	//String sql="insert into UserInfo values(\""+username+"\",\""+password+"\")";
                	System.out.println(sql);
                	//issuccess=stmt.executeUpdate(sql);
                	rs=stmt.executeQuery(sql);
                	list1=new ArrayList<String>();
        		    while(rs.next())
        		    {
        		    	//list1.add(rs.getString(0));
        		    	type=true;
        		    }
        		    //System.out.println(list1.toString());
        		    rs.close();
        		    
                	
                	DBUtil.Clse();
                	//out用来向客户端返回数据。如果用网页访问这个网址，查询数据库的结果就显示在网页上，如果用android访问，就返回给android手机
        		    //out.println(list1.toString());
                	//out.println(issuccess);
                	out.println(type);
                	out.flush();
                	out.close();
            }
            else{
                System.out.println("get the json failed");
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


}

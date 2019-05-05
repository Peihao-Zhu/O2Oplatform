package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String ID = request.getParameter("ID"); //用于接收前段输入的ID值，此处参数须和input控件的name值一致
        String PW= request.getParameter("PW");//用于接收前段输入的PW值，此处参数须和input控件的name值一致
        boolean type=false;//用于判断账号和密码是否与数据库中查询结果一致
        response.setContentType("text/html; charset=gbk");
        PrintWriter out = response.getWriter();
        ResultSet rs;
        int issuccess = 0; 
        ArrayList<String>list1=null;
        try
        {
        	Connection con=DBUtil.getConnection();
        	Statement stmt=con.createStatement();
        	//String sql="select * from UserInfo where username=\""+ID+"\" and password=\""+PW+"\"";
        	String sql="insert into UserInfo values(\""+ID+"\",\""+PW+"\")";
        	System.out.println(sql);
        	issuccess=stmt.executeUpdate(sql);
        	list1=new ArrayList<String>();
		   /* while(rs.next())
		    {
		    	list1.add(rs.getString(0));
		    	type=true;
		    }
		    System.out.println(list1.toString());
		    rs.close();
		    */
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
        finally
        {
        	DBUtil.Clse();
        	//out用来向客户端返回数据。如果用网页访问这个网址，查询数据库的结果就显示在网页上，如果用android访问，就返回给android手机
		    //out.println(list1.toString());
        	out.println(issuccess);
        	out.flush();
        	out.close();
        }


	}

}

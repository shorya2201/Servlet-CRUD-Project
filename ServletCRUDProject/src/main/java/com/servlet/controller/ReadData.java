package com.servlet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReadData
 */
@WebServlet("/ReadData")
public class ReadData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	@Override
	public void init() throws ServletException {
		try {
			connection = DriverManager.getConnection("jdbc:mysql:///pw_projects", "root", "shorya2201");
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.valueOf(request.getParameter("id"));

		String insertQuery = "select * from project1 where id=?";
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		if (connection != null) {
			try {
				pstmt = connection.prepareStatement(insertQuery);
				if (pstmt != null) {

					pstmt.setInt(1, id);

					resultSet = pstmt.executeQuery();
					System.out.println(resultSet);
					while (resultSet.next()) {
						pw.println(
								"<html><head><style>table{width:50%; margin:30px auto; }     th,td{border:1px solid black;}</style><title>InsertData</title></head>");
						pw.println("<body>");
						pw.println("<table style='border: 1px solid black;'>");
						pw.println("<tr>");
						pw.println("<th>ID</th><th>Name</th><th>Email</th><th>City</th><th>Country</th>");
						pw.println("</tr>");
						pw.println("<tr style='border: 1px solid black;'>");
						pw.println("<td>" + resultSet.getInt(1) + "</td><td>" + resultSet.getString(2) + "</td><td>"
								+ resultSet.getString(3) + "</td><td>" + resultSet.getString(4) + "</td><td>"
								+ resultSet.getString(5) + "</td>");
						pw.println("</tr></table></body>");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}

package com.servlet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InsertData
 */
@WebServlet("/InsertData")
public class InsertData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

	@Override
	public void init() throws ServletException {
		try {
			connection = DriverManager.getConnection("jdbc:mysql:///pw_projects", "root", "shorya2201");
			connection.setAutoCommit(true);
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		String insertQuery = "insert into project1(name, email, city, country) values(?,?,?,?)";
		PreparedStatement pstmt = null;

		int rowsAffected = 0;
		if (connection != null) {
			try {
				pstmt = connection.prepareStatement(insertQuery);
				if (pstmt != null) {

					pstmt.setString(1, name);
					pstmt.setString(2, email);
					pstmt.setString(3, city);
					pstmt.setString(4, country);

					rowsAffected = pstmt.executeUpdate();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		pw.println("<html><head><title>InsertData</title></head>");
		pw.println("<body>");
		if (rowsAffected > 0) {
			pw.println("<h1 style = 'color:green; text-align:center;' > Data Inserted Successful!!</h1>");
			pw.println("<a style='text-align:center;' href='index.html'>Home Page!!!</a>");
		} else {
			pw.println("<h1 style = 'color:red; text-align:center;'>Data Insertion Failed!!</h1>");
			pw.println("<a style='text-align:center;' href='index.html'>Try Againn!!!</a>");
		}

	}
}
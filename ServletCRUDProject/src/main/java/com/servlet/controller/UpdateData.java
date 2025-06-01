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
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/UpdateData")
public class UpdateData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connection = null;

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
		String record = request.getParameter("record");
		String detail = request.getParameter("details");

		PreparedStatement pstmt = null;
		String updateQuery = "";

		if (record.equals("name")) {
			updateQuery = "update project1 SET name = ? WHERE id = ?";
		} else if (record.equals("email")) {
			updateQuery = "update project1 SET email = ? WHERE id = ?";
		} else if (record.equals("city")) {
			updateQuery = "update project1 SET city = ? WHERE id = ?";
		} else if (record.equals("country")) {
			updateQuery = "update project1 SET country = ? WHERE id = ?";
		}

		int rowsAffected = 0;

		try {
			if (connection != null) {
				pstmt = connection.prepareStatement(updateQuery);
				if (pstmt != null) {
					pstmt.setString(1, detail);
					pstmt.setInt(2, id);
					rowsAffected = pstmt.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		if (rowsAffected > 0) {
			pw.println("<h1 style = 'color:green; text-align:center;' > Data Updated Successful!!</h1>");
		} else {
			pw.println("<h1 style = 'color:red; text-align:center;'>Data Updation Failed!!</h1>");
			pw.println("<a style='text-align:center;' href='index.html'>Try Againn!!!</a>");
		}

	}

}

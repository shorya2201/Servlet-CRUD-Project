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
 * Servlet implementation class DeleteData
 */
@WebServlet("/DeleteData")
public class DeleteData extends HttpServlet {
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

		PreparedStatement pstmt = null;
		String deleteQuery = "delete FROM project1 WHERE id = ?";
		int rowsAffected = 0;

		if (connection != null) {
			try {
				pstmt = connection.prepareStatement(deleteQuery);
				if (pstmt != null) {
					pstmt.setInt(1, id);
					rowsAffected = pstmt.executeUpdate();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		if (rowsAffected > 0) {
			pw.println("<h1 style = 'color:green; text-align:center;' > Data Deletion Successful!!</h1>");
		} else {
			pw.println("<h1 style = 'color:red; text-align:center;'>Data Deletion Failed!!</h1>");
			pw.println("<a style='text-align:center;' href='index.html'>Try Againn!!!</a>");
		}

	}

}

package com.emp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/EmpServlet")
public class EmpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String id, name, dept, salary;

	public EmpServlet() {

	}

	public EmpServlet(String id, String name, String dept, String salary) {
		super();
		this.id = id;
		this.name = name;
		this.dept = dept;
		this.salary = salary;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	ArrayList<EmpServlet> al = new ArrayList<EmpServlet>();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		try {

			Class.forName("org.postgresql.Driver");

			Connection c = DriverManager.getConnection("jdbc:postgresql://192.168.110.48:5432/plf_training",
					"plf_training_admin", "pff123");
			out.println("connection established");
			Statement st = c.createStatement();
			String query = "select * from Emplo";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("id");
				String Name = rs.getString("name");
				String dept = rs.getString("department");
				String sal = rs.getString("salary");

				EmpServlet product = new EmpServlet(id, Name, dept, sal);
				al.add(product);

			}

		} catch (Exception e) {
			out.println("<P>hii<p>");
		}
		Gson gson = new GsonBuilder().create();
		String EmpJson = gson.toJson(al);
		out.println("<script>");
		out.println("var EmployeeData = " + EmpJson + ";");
		out.println("localStorage.setItem('EmployeeData', JSON.stringify(EmployeeData));");
		out.println("localStorage.setItem('state', 0");
		out.println("</script>");
	}

}

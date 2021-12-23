/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdbc.DatabaseConnection;
import models.Customer;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CustomerControllerServlet", urlPatterns = {"/CustomerControllerServlet"})
public class CustomerControllerServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException,SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            List<Customer> customers = new ArrayList<>();
            // lay du lieu search tu thanh dia chi
            String searchName = request.getParameter("search");
            //
            int check = 1;
            // neu la lan dau tien ( searchName = null ) cho bien check = 0
            if (searchName == null ) {
                check = 0;
            }
            
            // neu search trong ( search == "" ) va bien check = 1 ( de tranh hien thi bao loi lan dau chua nhap vao search)
            if ((searchName == null || "".equals(searchName)) && check == 1) {
                // cho search = ""
                searchName = "";
                // bao loi
                request.setAttribute("errorMessage", "Required to enter search field!");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            // check lan dau - hien het ket qua
            if(check == 0)
                searchName = "";
            
            // cau lenh sql
            myConn = DatabaseConnection.getConnection();

            myStmt = myConn.createStatement();

            String sql = "SELECT * FROM customer where name like '%" + searchName + "%'";

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {
                int id = myRs.getInt("id");
                String name = myRs.getString("name");
                int age = myRs.getInt("age");
                String address = myRs.getString("address");
                customers.add(new Customer(id, name, age, address));
            }
            // cho list customer vao session
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("index.jsp").forward(request, response);
            
            // exception
        } catch (Exception e) {
            System.out.println("error");
        } finally {

            // close all
            if (myRs != null) try {
                myRs.close();
            } catch (SQLException e) {
                // ignore
            } else {
                out.println("hello err rs");
            }

            if (myStmt != null) try {
                myStmt.close();
            } catch (SQLException e) {
                // ignore
            } else {
                out.println("hello err stmt");
            }

            if (myConn != null) try {
                myConn.close();
            } catch (SQLException e) {
                // ignore
            } else {
                out.println("hello world of err");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

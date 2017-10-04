package com.dm.app;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "FactorizerServlet", urlPatterns = {"/FactorizerServlet"})
public class FactorizerServlet extends HttpServlet {


        private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Integer> factorList = new ArrayList<>();
        int factorSum = 0;
        boolean isPrime = false;
        boolean isPerfect = false;

        String input = request.getParameter("numberToFactor");
        int numberToFactor = Integer.parseInt(input);

        for (int i = 1; i < numberToFactor; i++){
            if (numberToFactor%i == 0) {
                factorList.add(i);
                factorSum += i;
            }
        }

        if (factorSum == numberToFactor){
            isPerfect = true;
        }

        if (factorSum == 1){
            isPrime = true;
        }


        request.setAttribute("numberToFactor", numberToFactor);
        request.setAttribute("factors", factorList);
        request.setAttribute("isPrime", isPrime);
        request.setAttribute("isPerfect", isPerfect);

        RequestDispatcher rd = request.getRequestDispatcher("result.jsp");
        rd.forward(request, response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}

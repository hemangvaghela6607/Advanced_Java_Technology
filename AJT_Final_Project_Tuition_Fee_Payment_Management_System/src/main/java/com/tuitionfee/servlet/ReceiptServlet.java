package com.tuitionfee.servlet;

import com.tuitionfee.model.Payment;
import com.tuitionfee.model.User;
import com.tuitionfee.service.PaymentService;
import com.tuitionfee.util.SpringContextHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Controller for displaying and printing formal payment receipts.
 */
@WebServlet(name = "ReceiptServlet", urlPatterns = {"/receipt", "/student/receipt"})
public class ReceiptServlet extends HttpServlet {

    private PaymentService paymentService;

    @Override
    public void init() throws ServletException {
        this.paymentService = SpringContextHelper.getBean("paymentService", PaymentService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User loggedInUser = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (loggedInUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Please+login+to+view+receipts.");
            return;
        }

        String receiptNo = req.getParameter("receiptNo");
        String paymentIdStr = req.getParameter("paymentId");

        Payment payment = null;
        if (receiptNo != null && !receiptNo.trim().isEmpty()) {
            payment = paymentService.getPaymentByReceiptNumber(receiptNo.trim());
        } else if (paymentIdStr != null && !paymentIdStr.trim().isEmpty()) {
            try {
                int paymentId = Integer.parseInt(paymentIdStr);
                payment = paymentService.getPaymentById(paymentId);
            } catch (NumberFormatException ignored) {}
        }

        if (payment == null) {
            req.setAttribute("errorMessage", "Receipt not found.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        // Security check: If student, verify that the receipt belongs to them
        if (loggedInUser.isStudent()) {
            int receiptUserId = payment.getFeeAssignment().getStudent().getUser().getUserId();
            if (receiptUserId != loggedInUser.getUserId()) {
                resp.sendRedirect(req.getContextPath() + "/student/dashboard?error=Access+denied+to+this+receipt.");
                return;
            }
        }

        req.setAttribute("payment", payment);
        req.getRequestDispatcher("/student/receipt.jsp").forward(req, resp);
    }
}

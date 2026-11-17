/**
 * Client-Side Validation Utilities for Tuition Fee Management System
 */

document.addEventListener("DOMContentLoaded", function () {
    // Auto-dismiss alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert-dismissible');
    alerts.forEach(function (alert) {
        setTimeout(function () {
            const bsAlert = bootstrap.Alert.getOrCreateInstance(alert);
            bsAlert.close();
        }, 5000);
    });
});

/**
 * Validates registration form inputs
 */
function validateRegisterForm() {
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword") ? document.getElementById("confirmPassword").value : password;
    const rollNumber = document.getElementById("rollNumber").value.trim();

    if (password.length < 6) {
        alert("Password must be at least 6 characters long.");
        return false;
    }

    if (password !== confirmPassword) {
        alert("Passwords do not match.");
        return false;
    }

    if (!rollNumber) {
        alert("Please enter a valid roll number.");
        return false;
    }

    return true;
}

/**
 * Validates simulated payment form inputs
 */
function validatePaymentForm(maxBalance) {
    const amountInput = document.getElementById("amount");
    const amount = parseFloat(amountInput.value);

    if (isNaN(amount) || amount <= 0) {
        alert("Please enter a valid payment amount greater than zero.");
        amountInput.focus();
        return false;
    }

    if (amount > (maxBalance + 0.01)) {
        alert("Payment amount (₹" + amount.toFixed(2) + ") cannot exceed the remaining balance of ₹" + maxBalance.toFixed(2) + ".");
        amountInput.focus();
        return false;
    }

    return confirm("Confirm simulated payment of ₹" + amount.toFixed(2) + "?");
}

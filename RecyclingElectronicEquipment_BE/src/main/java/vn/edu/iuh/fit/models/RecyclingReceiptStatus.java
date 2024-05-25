package vn.edu.iuh.fit.models;

public enum RecyclingReceiptStatus {
    WAITING_FOR_DEVICE("Waiting for device"),
    RECEIVED("Received"),
    ASSESSED("Assessed"),
    PAID("Paid"),
    RECYCLING("Recycling"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");



    private final String status;

    RecyclingReceiptStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

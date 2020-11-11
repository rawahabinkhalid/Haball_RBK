package com.haball.Payment;

public class PaymentLedgerModel {
    private String BalanceAmount;
    private String CompanyId;
    private String CompanyName;
    private String ConsolidateId;
    private String CreatedBy;
    private String CreatedDate;
    private String CreditAmount;
    private String DebitAmount;
    private String DeliveryNoteId;
    private String DistributorId;
    private String DistributorName;
    private String DocumentNumber;
    private String DocumentType;
    private String ID;
    private String InvoiceId;
    private String LastChangedBy;
    private String LastChangedDate;
    private String PaymentId;
    private String PrePaidRequestId;
    private String ReferenceId;
    private String SubInvoiceId;
    private String TransactionDate;

    public PaymentLedgerModel(String balanceAmount, String companyId, String companyName, String consolidateId, String createdBy, String createdDate, String creditAmount, String debitAmount, String deliveryNoteId, String distributorId, String distributorName, String documentNumber, String documentType, String ID, String invoiceId, String lastChangedBy, String lastChangedDate, String paymentId, String prePaidRequestId, String referenceId, String subInvoiceId, String transactionDate) {
        BalanceAmount = balanceAmount;
        CompanyId = companyId;
        CompanyName = companyName;
        ConsolidateId = consolidateId;
        CreatedBy = createdBy;
        CreatedDate = createdDate;
        CreditAmount = creditAmount;
        DebitAmount = debitAmount;
        DeliveryNoteId = deliveryNoteId;
        DistributorId = distributorId;
        DistributorName = distributorName;
        DocumentNumber = documentNumber;
        DocumentType = documentType;
        this.ID = ID;
        InvoiceId = invoiceId;
        LastChangedBy = lastChangedBy;
        LastChangedDate = lastChangedDate;
        PaymentId = paymentId;
        PrePaidRequestId = prePaidRequestId;
        ReferenceId = referenceId;
        SubInvoiceId = subInvoiceId;
        TransactionDate = transactionDate;
    }

    public String getBalanceAmount() {
        return BalanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        BalanceAmount = balanceAmount;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getConsolidateId() {
        return ConsolidateId;
    }

    public void setConsolidateId(String consolidateId) {
        ConsolidateId = consolidateId;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getCreditAmount() {
        return CreditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        CreditAmount = creditAmount;
    }

    public String getDebitAmount() {
        return DebitAmount;
    }

    public void setDebitAmount(String debitAmount) {
        DebitAmount = debitAmount;
    }

    public String getDeliveryNoteId() {
        return DeliveryNoteId;
    }

    public void setDeliveryNoteId(String deliveryNoteId) {
        DeliveryNoteId = deliveryNoteId;
    }

    public String getDistributorId() {
        return DistributorId;
    }

    public void setDistributorId(String distributorId) {
        DistributorId = distributorId;
    }

    public String getDistributorName() {
        return DistributorName;
    }

    public void setDistributorName(String distributorName) {
        DistributorName = distributorName;
    }

    public String getDocumentNumber() {
        return DocumentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        DocumentNumber = documentNumber;
    }

    public String getDocumentType() {
        return DocumentType;
    }

    public void setDocumentType(String documentType) {
        DocumentType = documentType;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getInvoiceId() {
        return InvoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        InvoiceId = invoiceId;
    }

    public String getLastChangedBy() {
        return LastChangedBy;
    }

    public void setLastChangedBy(String lastChangedBy) {
        LastChangedBy = lastChangedBy;
    }

    public String getLastChangedDate() {
        return LastChangedDate;
    }

    public void setLastChangedDate(String lastChangedDate) {
        LastChangedDate = lastChangedDate;
    }

    public String getPaymentId() {
        return PaymentId;
    }

    public void setPaymentId(String paymentId) {
        PaymentId = paymentId;
    }

    public String getPrePaidRequestId() {
        return PrePaidRequestId;
    }

    public void setPrePaidRequestId(String prePaidRequestId) {
        PrePaidRequestId = prePaidRequestId;
    }

    public String getReferenceId() {
        return ReferenceId;
    }

    public void setReferenceId(String referenceId) {
        ReferenceId = referenceId;
    }

    public String getSubInvoiceId() {
        return SubInvoiceId;
    }

    public void setSubInvoiceId(String subInvoiceId) {
        SubInvoiceId = subInvoiceId;
    }

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        TransactionDate = transactionDate;
    }
}

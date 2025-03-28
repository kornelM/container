package com.document.generator;

public class Transaction {
    private String transactionDate;
    private String postingDate;
    private String sender;
    private String senderAccount;
    private String recipient;
    private String recipientAccount;
    private String description;
    private String amount;
    private String currency;

    public Transaction(String transactionDate, String postingDate, String sender, String senderAccount,
                       String recipient, String recipientAccount, String description,
                       String amount, String currency) {
        this.transactionDate = transactionDate;
        this.postingDate = postingDate;
        this.sender = sender;
        this.senderAccount = senderAccount;
        this.recipient = recipient;
        this.recipientAccount = recipientAccount;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
    }

    public String getDetails() {
        return String.join("\n",
                "Nadawca: " + sender,
                "Numer rachunku nadawcy: " + senderAccount,
                "Odbiorca: " + recipient,
                "Numer rachunku odbiorcy: " + recipientAccount,
                "Opis transakcji: " + description
        );
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
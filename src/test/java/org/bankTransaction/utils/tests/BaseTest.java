package org.bankTransaction.utils.tests;

import java.util.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import org.bankTransaction.reporting.Reporter;
import org.bankTransaction.utils.pojo.BankTransactionData;


public class BaseTest {

    private final String endpointUrl = "https://637ef9e65b1cc8d6f937c7f2.mockapi.io/apitest/BankTransactions";
    private int statusCodeOfGettingAllData;
    private List<BankTransactionData> availableTransactionsInTheEndpoint;
    //private List<BankTransactionData> fakeTransactionsToPost;

    ///////////////////////////////////////////////////////////////////// GETTING all data
    protected int getStatusCodeOfGettingAllTransactions() {
        return statusCodeOfGettingAllData;
    }
    protected List<BankTransactionData> getAvailableTransactionsInTheEndpoint() {
        return availableTransactionsInTheEndpoint;
    }
    protected List<BankTransactionData> getAllEndpointTransactions() {
        List<BankTransactionData> allBankTransactionData = new ArrayList<>();
        RestAssured.baseURI = this.endpointUrl;
        RequestSpecification fetchRequest = given();

        Response res = null;
        JsonPath json = null;
        try {
            res = fetchRequest.get(endpointUrl);
            json = res.jsonPath();
        }
        catch (Exception e) {
            Reporter.error(e.toString());
        }
        try {
            assert json != null;
            allBankTransactionData = json.getList(".", BankTransactionData.class);
        } catch (Exception e) {
            Reporter.error(e.toString());
        }
        availableTransactionsInTheEndpoint = allBankTransactionData;
        statusCodeOfGettingAllData = res.getStatusCode();
        return allBankTransactionData;
    }

    ////////////////////////////////////////////////////////////////////////DELETING THE ENDPOINT DATA
    protected void checkIfThereAreTransactionsAndDeleteThem(){
        if (getAvailableTransactionsInTheEndpoint().size() > 0){
            Reporter.info("Deleting transactions...");
            leaveTheEndpointEmpty(getAvailableTransactionsInTheEndpoint());
        }
    }
    protected void deleteTransactionById(int id){
        String deleteUrl = endpointUrl + "/" + id;
            given().contentType("application/json").when().delete(deleteUrl);
    }
    protected void leaveTheEndpointEmpty(List<BankTransactionData> availableTransactionsInTheEndpoint){
        if (availableTransactionsInTheEndpoint.size() > 0){
            for (BankTransactionData i : availableTransactionsInTheEndpoint){
                try {
                    deleteTransactionById(i.getId());
                } catch (Exception e){
                    Reporter.error(e.toString());
                }
            }
        }
    }
    //////////////////////////////////////////////////////////// Updates existing account number

    protected int updateAccountNumber(int transactionId){
        Faker javaFakerInstance = Faker.instance(new Locale("en-US"));
        int newAccountNumber = javaFakerInstance.number().numberBetween(0, 999);
        Map<String, Integer> accountNumberToSendAsJson = new HashMap<String, Integer>();
        accountNumberToSendAsJson.put("accountNumber", newAccountNumber);
        Response response = given().contentType("application/json").body(accountNumberToSendAsJson).when().put(this.endpointUrl + "/" + transactionId);
        return response.getStatusCode();
    }

    protected boolean checkForDuplicatedEmails() {
        List<String> transactionEmails = new ArrayList<>();
        getAllEndpointTransactions()
                .forEach(transaction -> {
                    transactionEmails.add(transaction.getEmail());
                });

        return transactionEmails.stream().distinct().count() == availableTransactionsInTheEndpoint.size();
    }

////////////////////////////////////////////////////////////////////////

    protected boolean checkForDuplicatedEmails(List<BankTransactionData> listOfTransactions) {
        List<String> transactionEmails = new ArrayList<>();
        listOfTransactions.forEach(transaction -> {
            transactionEmails.add(transaction.getEmail());
        });
        return !(transactionEmails.stream().distinct().count() == listOfTransactions.size());
    }

    protected int createATransaction(BankTransactionData bankTransactionData) {
        Response response = given().contentType("application/json").body(bankTransactionData).when().post(this.endpointUrl);

        return response.getStatusCode();
    }



    protected List<BankTransactionData> createTenFakeTransactionsAndOneWithDuplicatedEmail() {
        List<BankTransactionData> fakeBankTransactions = new ArrayList<BankTransactionData>();
        Faker javaFaker = Faker.instance(new Locale("en-US"));

        for (int i = 0; i < 10; i++){
            String name = javaFaker.name().firstName();
            String lastName = javaFaker.name().lastName();
            int accountNumber = javaFaker.number().numberBetween(1,999);
            double amount = javaFaker.number().randomDouble(9, 99,999);
            String transactionType = javaFaker.options().option("deposit", "withdrawal", "transfer");
            String email = javaFaker.internet().emailAddress();
            boolean active = javaFaker.bool().bool();
            String country = javaFaker.address().country();
            String telephone = javaFaker.phoneNumber().phoneNumber();

            BankTransactionData newTransaction = new BankTransactionData(
                    name, lastName, accountNumber, amount, transactionType, email, active, country, telephone);

            fakeBankTransactions.add(newTransaction);
        }

            String name = javaFaker.name().firstName();
            String lastName = javaFaker.name().lastName();
            int accountNumber = javaFaker.number().numberBetween(1,999);
            double amount = javaFaker.number().randomDouble(9, 99,999);
            String transactionType = javaFaker.options().option("deposit", "withdrawal", "transfer");
            String email = fakeBankTransactions.get(1).getEmail();
            boolean active = javaFaker.bool().bool();
            String country = javaFaker.address().country();
            String telephone = javaFaker.phoneNumber().phoneNumber();

        BankTransactionData newDuplicatedTransaction = new BankTransactionData(
                name, lastName, accountNumber, amount, transactionType, email, active, country, telephone);

            fakeBankTransactions.add(newDuplicatedTransaction);

        return fakeBankTransactions;
    }



    protected long createAndPostFakeTransactions() {
        List<BankTransactionData> transactions = createTenFakeTransactionsAndOneWithDuplicatedEmail();
        List<Integer> listOfCreatedTransactionsStatusCodes = new ArrayList<>();
        boolean areEmailsDuplicated = checkForDuplicatedEmails(transactions);
        if (areEmailsDuplicated){
            for (int i = 0; i < transactions.size(); i++) {
                boolean emailIsDuplicated = false;

                for (int x = 0; x < transactions.size() && !emailIsDuplicated; x++) {
                    if (transactions.get(i).getEmail().equals(transactions.get(x).getEmail()) && i < x) {
                        emailIsDuplicated = true;
                    }
                }
                if (!emailIsDuplicated) {
                    listOfCreatedTransactionsStatusCodes.add(createATransaction(transactions.get(i)));
                }
            }
        }
        else {
            for (BankTransactionData i: transactions){
                listOfCreatedTransactionsStatusCodes.add(createATransaction(i));
            }
        }
        return listOfCreatedTransactionsStatusCodes.stream().filter(statusCode -> statusCode.equals(201)).count();
    }

}

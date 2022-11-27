package org.bankTransaction.utils.tests;

import java.util.*;
import org.bankTransaction.utils.pojo.BankTransactionData;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import org.bankTransaction.reporting.Reporter;


/**
 * @author Zeus Vargas
 * A superclass with methods to get, post, put, delete and handle information created and/or fetched from the mock api.
 * This is the place to change the <endpointUrl> link to your own mock api if you want to do so.
 */
public class BaseTest {

    private final String endpointUrl = "https://637ef9e65b1cc8d6f937c7f2.mockapi.io/apitest/BankTransactions";
    private int statusCodeOfGettingAllData;
    private List<BankTransactionData> availableTransactionsInTheEndpoint;

    /**
     * This method
     * @return the status code of the GET request made to fetch all the data from the endpoint.
     */
    protected int getStatusCodeOfGettingAllTransactions() {
        return statusCodeOfGettingAllData;
    }

    /**
     * This method
     * @return all the data available in the endpoint saved in the class to limit the amount of get requests made.
     */
    protected List<BankTransactionData> getAvailableTransactionsInTheEndpoint() {
        return availableTransactionsInTheEndpoint;
    }

    /**
     * Makes the GET request to the endpoint and
     * @return the response as a list of <BankTransactionsData> object.
     */
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

    /**
     * Checks the amount of transactions available in the endpoint and if it is higher than 0, it deletes those records.
     */
    protected void checkIfThereAreTransactionsAndDeleteThem(){
        if (getAvailableTransactionsInTheEndpoint().size() > 0){
            Reporter.info("Deleting transactions...");
            leaveTheEndpointEmpty(getAvailableTransactionsInTheEndpoint());
        }
    }
    /**
     * Receives the list of the
     * @param availableTransactionsInTheEndpoint and iterates to delete them one by one.
     */
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
    /**
     * Receives the
     * @param id of the transaction to delete and makes a delete request to delete the transaction from the endpoint.
     */
    protected void deleteTransactionById(int id){
        String deleteUrl = endpointUrl + "/" + id;
            given().contentType("application/json").when().delete(deleteUrl);
    }


    /**
     * Receives a
     * @param transactionId then creates a fake account number and sends it as json in a PUT request to the endpoint.
     * @return the status code of the PUT request which should be 200 if the accountNumber update was successful.
     */
    protected int updateAccountNumber(int transactionId){
        Faker javaFakerInstance = Faker.instance(new Locale("en-US"));
        int newAccountNumber = javaFakerInstance.number().numberBetween(0, 999);
        Map<String, Integer> accountNumberToSendAsJson = new HashMap<>();
        accountNumberToSendAsJson.put("accountNumber", newAccountNumber);
        Response response = given().contentType("application/json").body(accountNumberToSendAsJson).when().put(this.endpointUrl + "/" + transactionId);
        return response.getStatusCode();
    }

    /**
     *
     * @return
     */
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
        List<BankTransactionData> fakeBankTransactions = new ArrayList<>();
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

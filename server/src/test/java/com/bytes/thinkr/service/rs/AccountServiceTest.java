package com.bytes.thinkr.service.rs;

import com.bytes.thinkr.factory.data.AccountDataFactory;
import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.entity.account.Account;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Kent on 1/8/2016.
 */

public class AccountServiceTest extends ServiceTest {

    // The list of entities (detached)
    List<Account> accounts;

    // The list of entities ids (attached)
    Set<Long> accountIds;

    @Before
    public void startup(){

        // Create entities
        accounts = AccountDataFactory.getInstance().generate(5);
        String email = "AccountServiceTest";
        for (int i = 0; i < accounts.size(); i++) {
            accounts.get(i).getClient().setEmail(email + i + "@thinkr.com");
        }

        accountIds = new HashSet<>();
    }

    @After
    public void teardown() {

        // Remove entities from database
        for (Long id : accountIds){
            String path = "account/" + id;
            target.path(path).request().delete();
        }
    }

    @Test
    public void testGetHtml() throws Exception {
        Response response = target.path("account").request().accept(MediaType.TEXT_HTML).get(Response.class);
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testCreateAccount() throws Exception {

        for (Account account : accounts) {
            Account response = post("account", account, Account.class);
            ValidationInfo validation = response.getValidation();
            if (validation.hasError()) {

                // Existing account is acceptable
                Assert.assertEquals("Account creation status",
                        ValidationInfo.UserId.Existing.toString(),
                        validation.get(ValidationInfo.Type.UserId));
            } else {
                Assert.assertNotNull("Id is null", response.getId());
                accountIds.add(response.getId());
            }
        }
    }

    @Test
    public void testUpdateAccount() throws Exception {

        for (Account account : accounts) {

            // Create the account in the database
            account = create(account);

            // Update the account
            String updatedEmail = "testUpdateAccount" + account.getId() + "@thinkr.com";
            account.getClient().setEmail(updatedEmail);
            account.getClient().getName().setFirst("Kaelyn" + account.getId());
            account.getClient().getName().setLast("Lam" + account.getId());

            Account updatedAccount = post("account/" + account.getId(), account, Account.class);
            Assert.assertEquals("Updating account email...", updatedEmail, updatedAccount.getClient().getEmail());
        }
    }

    @Test
    public void testDeleteAccount() throws Exception {

        for (Account account : accounts) {
            Account response = post("account", account, Account.class);
            ValidationInfo validation = response.getValidation();
            Assert.assertTrue("Account creation status", !validation.hasError());
        }

        // Delete using email
        for (Account account : accounts) {
            String path = "account/email/" + account.getClient().getEmail();
            Response result = target.path(path).request().delete();
            Assert.assertEquals("Account deletion by email status: " + path, 200, result.getStatus());
        }

        ArrayList<Long> accountIds = new ArrayList<>(accounts.size());
        for (Account account : accounts) {
            Account response = post("account", account, Account.class);
            ValidationInfo validation = response.getValidation();
            Assert.assertTrue("Account creation status", !validation.hasError());
            accountIds.add(response.getId());
        }

        // Delete using account id
        for (Long id : accountIds) {
            String path = "account/" + id;
            Response result = target.path(path).request().delete();
            Assert.assertEquals("Account deletion by id status: " + path, 200, result.getStatus());
        }
    }

    @Test
    public void testAuthenticate() throws Exception {

        for(Account account : accounts) {
            Account a = create(account);

            // "authenticate/{email}/{password}";
            String path = "account/authenticate/"
                    + a.getClient().getEmail() + "/"
                    + account.getClient().getPassword();
            String response = get(path, String.class);
            Assert.assertEquals("Account authentication", "true", response);
        }
    }

    @Test
    public void testFind() throws Exception {

        for (Account account : accounts) {
            Account persistedAccount = create(account);

            // Find by id
            String path = "account/" + persistedAccount.getId();
            Account retrievedAccount = get(path, Account.class);
            Assert.assertEquals("Retrieved account by id",
                    persistedAccount.getId(),
                    retrievedAccount.getId());

            // Find by email
            path = "account/email/" + persistedAccount.getClient().getEmail();
            retrievedAccount = get(path, Account.class);
            Assert.assertEquals("Retrieved account by email",
                    persistedAccount.getId(),
                    retrievedAccount.getId());
        }
    }

    private Account create(Account account) {

        // Create the entity in the database
        account = post("account", account, Account.class);
        ValidationInfo validation = account.getValidation();
        Assert.assertTrue("Account creation status", !validation.hasError());
        accountIds.add(account.getId());
        return account;

    }
}
package com.bytes.thinkr.service.rs;

import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.factory.data.AccountDataFactory;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Kent on 1/8/2016.
 */
public class AccountServiceTest extends ServiceTest {

    @Test
    public void testGetHtml() throws Exception {
        Response response = target.path("account").request().accept(MediaType.TEXT_HTML).get(Response.class);
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testCreateAccount() throws Exception {

        Account account = AccountDataFactory.getInstance().generate(1).get(0);
        account.getClient().setEmail("Kentative123@live.com");

        Account response = post("account", account.getClient(), Account.class);

        ValidationInfo validation = response.getValidation();

        if (validation.hasError()) {

            // Existing account is acceptable
            System.out.print(validation);
            Assert.assertEquals(
                ValidationInfo.UserId.Existing.toString(),
                validation.get(ValidationInfo.Type.UserId));

        } else {
            System.out.print(response);
            Assert.assertNotNull(response.getId());
        }
    }

    /**
     * This test covers the following methods:
     * 1. create
     * 2. update
     * 3. delete
     * @throws Exception
     */
    @Test
    public void testUpdateAccount() throws Exception {

        Account account = AccountDataFactory.getInstance().generate(1).get(0);
        String email = "beforeEmailUpdated@thinkr.com";
        account.getClient().setEmail(email);

        System.out.println("Retrieving account with email : " + email);
        Account retrievedAccount = get("account/email/" + email, Account.class);
        System.out.println("Result: " + retrievedAccount.getValidation());

        // Create the account if not found
        if (retrievedAccount.getValidation().hasError()) {
            System.out.print("Creating new account.");
            account = post("account", account, Account.class);
            System.out.println(" result: \n" + account.getValidation());
        } else {
            account = retrievedAccount;
        }

        // Update the account
        String updatedEmail = "afterEmailUpdated@thinkr.com";
        account.getClient().setEmail(updatedEmail);
        account.getClient().getName().setFirst("Kaelyn");
        account.getClient().getName().setLast("Lam");

        System.out.print("Updating account...");
        Account updatedAccount = post("account/" + account.getId(), account, Account.class);
        System.out.println("result: " + updatedAccount.getValidation());
        Assert.assertEquals(updatedEmail, updatedAccount.getClient().getEmail());

        // Delete the account
        String path = "account/" + updatedAccount.getId();
        Response result = target.path(path).request().delete();
        Assert.assertEquals("Delete account: " + path, 200, result.getStatus());
    }

    @Test
    public void testDeleteAccount() throws Exception {

//        String path = "account/email/beforeEmailUpdated@thinkr.com";
        String path = "account/4";
        Response result = target.path(path).request().delete();
        Assert.assertEquals("Delete account: " + path, 200, result.getStatus());
    }

    @Test
    public void testIsExistingUserValid() throws Exception {

    }

    @Test
    public void testFind() throws Exception {

    }
}
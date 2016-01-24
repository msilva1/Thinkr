package com.bytes.thinkr.service.rs;

import com.bytes.thinkr.model.ValidationInfo;
import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.factory.data.AccountDataFactory;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Kent on 1/8/2016.
 */
public class AccountServiceTest extends ServiceTest {

    @Test
    public void testGetHtml() throws Exception {

        String html = target.path("account").request().accept(MediaType.TEXT_HTML).get(String.class);
        Assert.assertNotNull(html);

    }

    @Test
    public void testCreateAccount() throws Exception {

        Account account = AccountDataFactory.getInstance().generate(1).get(0);
        account.getClient().setEmail("Kentative123@live.com");
        String path = "account";
        Account response = post(path, account.getClient(), Account.class);

        ValidationInfo validation = response.getValidation();
        System.out.print(validation);
        if (validation.hasError()) {

            System.out.print(validation);
            // Existing account is acceptable
            Assert.assertEquals(
                validation.get(ValidationInfo.Type.UserId),
                ValidationInfo.UserId.Existing.toString());

        } else {
            Assert.assertNotNull(response.getId());
        }
    }

    @Test
    public void testUpdateAccount() throws Exception {

    }

    @Test
    public void testDeleteAccount() throws Exception {

        String delete = delete("account/email/Kentative123@live.com");
        System.out.print(delete);
    }

    @Test
    public void testIsExistingUserValid() throws Exception {

    }

    @Test
    public void testFind() throws Exception {

    }
}
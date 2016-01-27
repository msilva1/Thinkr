/*
 * Copyright (c) 2016. Thinkr edu
 */

package com.bytes.thinkr.factory.merge;

import com.bytes.thinkr.model.entity.account.Account;
import com.bytes.thinkr.model.entity.account.Client;
import com.bytes.thinkr.model.entity.account.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Kent on 1/23/2016.
 */
public class AccountMergeFactory extends MergeFactory<Account> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountMergeFactory.class.getName());

    private static AccountMergeFactory instance;
    public static AccountMergeFactory getInstance() {
        if (instance == null) {
            instance = new AccountMergeFactory();
        }
        return instance;
    }

    @Override
    public Account merge(Account source, Account destination) {

        setDataMap(getDataMap(source), destination);
        return destination;

    }

    /**
     * The list of modifiable fields
     */
    private enum Field implements IMergeField {
        DisplayName,
        Email,
        Password,
        UserType,
        FirstName,
        LastName,
        MiddleName,
        Suffix,
        Prefix
    }

    /**
     *
     * @param account
     * @return
     */
    public static MergeMap getDataMap(Account account) {

        Client client = account.getClient();
        MergeMap<Field, Object> dataMap = new MergeMap<>();
        if (client == null) {
            LOGGER.debug("Unable to merge, client data not found.");
            return dataMap;
        }

        dataMap.put(Field.DisplayName, client.getDisplayName());
        dataMap.put(Field.Email, client.getEmail());
        dataMap.put(Field.Password, client.getPassword());
        dataMap.put(Field.UserType, client.getUserType());

        Name name = client.getName();
        if (name == null) {
            LOGGER.debug("Client name has no data.");
            return dataMap;
        }
        dataMap.put(Field.FirstName, name.getFirst());
        dataMap.put(Field.LastName, name.getLast());
        dataMap.put(Field.MiddleName, name.getMiddle());
        dataMap.put(Field.Suffix, name.getSuffix());
        dataMap.put(Field.Prefix, name.getPrefix());
        return dataMap;
    }


    /**
     * @param dataMap
     * @param account
     * @return
     */
    public static int setDataMap(MergeMap<Field, Object> dataMap, Account account) {

        Client client = account.getClient();
        Name name = client.getName();
        if (name == null) {

            LOGGER.debug("Name data is missing in database, creating new instance.");
            name = new Name();
            client.setName(name);
        }

        int count = 0;
        for (Field field : dataMap.keySet()) {
            Object data = dataMap.get(field);
            if(data != null) {
                count++;
                switch (field) {

                    // Client
                    case DisplayName:
                        client.setDisplayName(data.toString());
                        break;

                    case Email:
                        client.setEmail(data.toString());
                        break;

                    case Password:
                        client.setPassword(data.toString());
                        break;

                    case UserType:
                        client.setUserType((Client.Type) data);
                        break;

                    // Name
                    case FirstName:
                        name.setFirst(data.toString());
                        break;

                    case LastName:
                        name.setLast(data.toString());
                        break;

                    case MiddleName:
                        name.setMiddle(data.toString());
                        break;

                    case Suffix:
                        name.setSuffix(data.toString());
                        break;

                    case Prefix:
                        name.setPrefix(data.toString());
                        break;
                }
            }
        }

        LOGGER.debug("Updated " + count + " entries");
        return count;
    }
}

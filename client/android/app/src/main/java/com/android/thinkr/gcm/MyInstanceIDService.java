package com.android.thinkr.gcm;

import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by ryanclinton on 1/2/16.
 */

public class MyInstanceIDService extends InstanceIDListenerService {
    public void onTokenRefresh() {
        refreshAllTokens();
    }

    private void refreshAllTokens() {

        InstanceID iid = InstanceID.getInstance(this);
        // assuming you have defined TokenList as
        // some generalized store for your tokens
//        ArrayList<TokenList> tokenList = TokensList.get();
//        for(tokenItem : tokenList) {
//            tokenItem.token =
//                    iid.getToken(tokenItem.authorizedEntity,tokenItem.scope,tokenItem.options);
//            // send this tokenItem.token to your server
//        }
    }
}

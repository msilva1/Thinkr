package com.android.thinkr.gcm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ryanclinton on 1/3/16.
 */
public class GcmMessage implements Serializable{
    private List<String> registration_ids;
    private Map<String,String> data;

    public void addRegId(String regId){
        if(registration_ids == null)
            registration_ids = new ArrayList<>();
        registration_ids.add(regId);
    }

    public void createData(String message1, String message2){
        if(data == null)
            data = new HashMap<>();

        data.put("message1", message1);
        data.put("message2", message2);
    }

    public Map<String, String> getData(){
        return data;
    }

    public List<String> getRegistration_ids(){
        return registration_ids;
    }
}

package com.demo.android.smsapp.asynctasks;

import android.os.AsyncTask;

import com.demo.android.smsapp.activities.SMSHistoryActivity;
import com.demo.android.smsapp.models.SMSModel;

import java.util.ArrayList;
/**
 * Created by DU on 12/18/2017.
 */
public class SMSGroupByAsynTask extends AsyncTask<ArrayList<SMSModel>,Void,ArrayList<String>> {

    SMSHistoryActivity activity;

    public SMSGroupByAsynTask(SMSHistoryActivity activity){
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected ArrayList<String> doInBackground(ArrayList<SMSModel>... params) {
        ArrayList<SMSModel> allSMSModels = params[0];
        ArrayList<String> addresses = new ArrayList<>();

        for(int i=0; i<allSMSModels.size(); i++){
            if(!addresses.contains(allSMSModels.get(i).getAddress()))
            {
                addresses.add(allSMSModels.get(i).getAddress());
            }
        }

        return addresses;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
        // execution of result of Long time consuming operation
        activity.setAdapter(result);
    }

}

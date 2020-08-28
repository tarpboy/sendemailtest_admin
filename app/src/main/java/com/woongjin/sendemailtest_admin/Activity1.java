package com.woongjin.sendemailtest_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.woongjin.sendemailtest_admin.Adapter.AddrListAdapter;
import com.woongjin.sendemailtest_admin.Popup.ProgressPopup;
import com.woongjin.sendemailtest_admin.WebConnect.ApiGetContractHist;
import com.woongjin.sendemailtest_admin.WebConnect.InterfaceAsyncResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Activity1 extends AppCompatActivity implements View.OnClickListener {


    private AddrListAdapter mAdapter;
    ListView addrList;

    Button bt_new;
    Button bt_search;
    EditText et_search_param;


    Context mContext;

    ArrayList<HashMap<String, String>> arryContractList = new ArrayList<HashMap<String, String>>();

    ArrayList<HashMap<String, String>> contractHistList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> personList = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mContext = this;

//        // Access a Cloud Firestore instance from your Activity
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        // Create a new user with a first and last name
//        Map<String, Object> user = new HashMap<>();
//        user.put("first", "Ada");
//        user.put("last", "Lovelace");
//        user.put("born", 1815);
//
//        // Add a new document with a generated ID
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("Jonathan", "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("Jonathan", "Error adding document", e);
//                    }
//                });




        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = { Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if(!hasPermissions(mContext, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        bt_new = (Button)findViewById(R.id.bt_new);
        bt_new.setOnClickListener(this);

        bt_search = (Button)findViewById(R.id.bt_search);
        bt_search.setOnClickListener(this);


        et_search_param = (EditText)findViewById(R.id.et_search_param);
        addrList = (ListView) findViewById(R.id.home_rvList);


//        getContractHist();

    }



    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onClick(View v) {


        if(v.equals(bt_new))
        {
            Intent intent = new Intent(this, Activity2.class);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        else if(v.equals(bt_search))
        {
            if(!"".equals(et_search_param.getText().toString()))
            {
                getContractHistWithName();
            }
            else
            {
                getContractHist();
            }



        }



    }





    public void getContractHist()
    {
        arryContractList.clear();
        final ProgressPopup dialog = new ProgressPopup(mContext);

        if(!dialog.isShowing())
        {
            dialog.setMessage("로딩중...");
            dialog.show();
        }



        // Access a Cloud Firestore instance from your Activity
        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        // Create a new user with a first and last name
        db.collection("CT_TB_CONTRACT_HIST")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            contractHistList.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("Jonathan", "contractHistList findAll  => " + document.getData());

                                Map<String,Object> map = document.getData();
                                HashMap<String,String> newMap =new HashMap<String,String>();
                                for (Map.Entry<String, Object> entry : map.entrySet()) {
                                    if(entry.getValue() instanceof String){
                                        newMap.put(entry.getKey(), (String) entry.getValue());
                                    }
                                }
                                contractHistList.add(newMap);
                            }


                            db.collection("CT_TB_PERSON")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                personList.clear();
                                                ArrayList<HashMap<String, String>> arryItem2 = new ArrayList<HashMap<String, String>>();

                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Log.e("Jonathan", "personList findAll  => " + document.getData());

                                                    Map<String,Object> map = document.getData();
                                                    HashMap<String,String> newMap =new HashMap<String,String>();
                                                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                                                        if(entry.getValue() instanceof String){
                                                            newMap.put(entry.getKey(), (String) entry.getValue());
                                                        }
                                                    }
                                                    personList.add(newMap);
                                                }


                                                for(int i = 0 ; i< contractHistList.size() ;i++)
                                                {
                                                    for(int j = 0; j < personList.size() ;j++)
                                                    {
                                                        if(contractHistList.get(i).get("NAME_PLACE").equals(personList.get(j).get("C_NAME")) && contractHistList.get(i).get("TEL_PLACE").equals(personList.get(j).get("PHONE")))
                                                        {
                                                            Log.e("Jonathan",  i + " aaa :: " +contractHistList.get(i).get("NAME_PLACE") + "  " + personList.get(j).get("C_NAME"));
                                                            Log.e("Jonathan",  i + " bbb :: " +contractHistList.get(i).get("TEL_PLACE") + "  " + personList.get(j).get("PHONE"));

                                                            HashMap<String, String> getDataMap = new HashMap<String, String>();

                                                            Iterator<String> keys = contractHistList.get(i).keySet().iterator();
                                                            while (keys.hasNext()) {

                                                                String key = keys.next();
                                                                getDataMap.put(key, contractHistList.get(i).get(key));
                                                            }

                                                            Iterator<String> keys1 = personList.get(j).keySet().iterator();
                                                            while (keys1.hasNext()) {

                                                                String key = keys1.next();
                                                                getDataMap.put(key, personList.get(j).get(key));
                                                            }

                                                            arryItem2.add(getDataMap);
                                                        }
                                                    }
                                                }

                                                arryContractList = (ArrayList<HashMap<String, String>>)arryItem2.clone();

                                                Log.e("Jonathan", "arryContractList :: " +arryContractList.size());
                                                for(int i = 0 ;i < arryContractList.size() ;i++)
                                                {
                                                    Log.e("Jonathan", "arryContractList :: " +arryContractList.get(i));
                                                }

                                                mAdapter = new AddrListAdapter(mContext, arryContractList, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                    }
                                                });
                                                addrList.setAdapter(mAdapter);



                                                if(dialog.isShowing())
                                                {
                                                    dialog.dismiss();
                                                }


                                            } else {
                                                Log.w("Jonathan", "Error getting documents. personList ", task.getException());
                                            }
                                        }
                                    });


                        } else {
                            Log.w("Jonathan", "Error getting documents. contractHistList", task.getException());
                        }
                    }
                });












//        arryContractList.clear();
//        HashMap<String,String> infoData = new HashMap<String, String>();
//        if(!"".equals(et_search_param.getText().toString()))
//        {
//            infoData.put("NAME", et_search_param.getText().toString());
//        }
//
//        final ApiGetContractHist getContractHist = new ApiGetContractHist(this, new InterfaceAsyncResponse() {
//            @Override
//            public void processFinish(String output) {
//                try {
//
//                    JSONObject result = new JSONObject(output);
//                    JSONArray jArr = result.getJSONArray("getData");
//                    ArrayList<HashMap<String, String>> arryItem2 = new ArrayList<HashMap<String, String>>();
//
//
//                    for(int i = 0 ; i < jArr.length() ; i++)
//                    {
//                        HashMap<String, String> getDataMap = new HashMap<String, String>();
//
//                        Iterator<String> key = jArr.getJSONObject(i).keys();
//                        while (key.hasNext())
//                        {
//                            String DataKey = key.next();
//                            getDataMap.put(DataKey, jArr.getJSONObject(i).get(DataKey).toString());
//
//                        }
//
//                        arryItem2.add(getDataMap);
//                    }
//
//                    arryContractList = (ArrayList<HashMap<String, String>>)arryItem2.clone();
//
//                    for(int i = 0 ;i < arryContractList.size() ;i++)
//                    {
//                        Log.e("Jonathan", "arryContractList :: " +arryContractList.get(i));
//                    }
//
//                    mAdapter = new AddrListAdapter(mContext, arryContractList, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                        }
//                    });
//                    addrList.setAdapter(mAdapter);
//
//
//
//
//                }
//                catch (Exception e)
//                {
//
//                }
//            }
//        });
//
//        getContractHist.execute(infoData);

    }


    public void getContractHistWithName()
    {
        arryContractList.clear();
        final ProgressPopup dialog = new ProgressPopup(mContext);

        if(!dialog.isShowing())
        {
            dialog.setMessage("로딩중...");
            dialog.show();
        }



        // Access a Cloud Firestore instance from your Activity
        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        // Create a new user with a first and last name
        db.collection("CT_TB_CONTRACT_HIST")
                .whereEqualTo("NAME_PLACE", et_search_param.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            contractHistList.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("Jonathan", "contractHistList findAll  => " + document.getData());

                                Map<String,Object> map = document.getData();
                                HashMap<String,String> newMap =new HashMap<String,String>();
                                for (Map.Entry<String, Object> entry : map.entrySet()) {
                                    if(entry.getValue() instanceof String){
                                        newMap.put(entry.getKey(), (String) entry.getValue());
                                    }
                                }
                                contractHistList.add(newMap);
                            }


                            db.collection("CT_TB_PERSON")
                                    .whereEqualTo("C_NAME", et_search_param.getText().toString())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                personList.clear();
                                                ArrayList<HashMap<String, String>> arryItem2 = new ArrayList<HashMap<String, String>>();

                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Log.e("Jonathan", "personList findAll  => " + document.getData());

                                                    Map<String,Object> map = document.getData();
                                                    HashMap<String,String> newMap =new HashMap<String,String>();
                                                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                                                        if(entry.getValue() instanceof String){
                                                            newMap.put(entry.getKey(), (String) entry.getValue());
                                                        }
                                                    }
                                                    personList.add(newMap);
                                                }


                                                for(int i = 0 ; i< contractHistList.size() ;i++)
                                                {
                                                    for(int j = 0; j < personList.size() ;j++)
                                                    {
                                                        if(contractHistList.get(i).get("NAME_PLACE").equals(personList.get(j).get("C_NAME")) && contractHistList.get(i).get("TEL_PLACE").equals(personList.get(j).get("PHONE")))
                                                        {
                                                            Log.e("Jonathan",  i + " aaa :: " +contractHistList.get(i).get("NAME_PLACE") + "  " + personList.get(j).get("C_NAME"));
                                                            Log.e("Jonathan",  i + " bbb :: " +contractHistList.get(i).get("TEL_PLACE") + "  " + personList.get(j).get("PHONE"));

                                                            HashMap<String, String> getDataMap = new HashMap<String, String>();

                                                            Iterator<String> keys = contractHistList.get(i).keySet().iterator();
                                                            while (keys.hasNext()) {

                                                                String key = keys.next();
                                                                getDataMap.put(key, contractHistList.get(i).get(key));
                                                            }

                                                            Iterator<String> keys1 = personList.get(j).keySet().iterator();
                                                            while (keys1.hasNext()) {

                                                                String key = keys1.next();
                                                                getDataMap.put(key, personList.get(j).get(key));
                                                            }

                                                            arryItem2.add(getDataMap);
                                                        }
                                                    }
                                                }

                                                arryContractList = (ArrayList<HashMap<String, String>>)arryItem2.clone();

                                                Log.e("Jonathan", "arryContractList :: " +arryContractList.size());
                                                for(int i = 0 ;i < arryContractList.size() ;i++)
                                                {
                                                    Log.e("Jonathan", "arryContractList :: " +arryContractList.get(i));
                                                }

                                                mAdapter = new AddrListAdapter(mContext, arryContractList, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                    }
                                                });
                                                addrList.setAdapter(mAdapter);



                                                if(dialog.isShowing())
                                                {
                                                    dialog.dismiss();
                                                }


                                            } else {
                                                Log.w("Jonathan", "Error getting documents. personList ", task.getException());
                                            }
                                        }
                                    });


                        } else {
                            Log.w("Jonathan", "Error getting documents. contractHistList", task.getException());
                        }
                    }
                });


    }

}

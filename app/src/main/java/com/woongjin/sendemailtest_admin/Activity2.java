package com.woongjin.sendemailtest_admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.woongjin.sendemailtest_admin.WebConnect.ApiGetContracts;
import com.woongjin.sendemailtest_admin.WebConnect.InterfaceAsyncResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Activity2 extends AppCompatActivity implements View.OnClickListener {



    Context mContext;

    LinearLayout ll_contract;

    ArrayList<HashMap<String, String>> arryContractList = new ArrayList<HashMap<String, String>>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contract);


        mContext = this;

        ll_contract = (LinearLayout)findViewById(R.id.ll_contract);


        ApiGetContracts();


    }








    @Override
    public void onClick(View v) {


//        if(v.equals(""))
//        {
//
//        }


    }




    ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> mOriginArrayList = new ArrayList<>();


    public static String nullCheck(Object value) {

        return value == null ? "" : value.toString().trim().equals("null") ? "" : value.toString().trim();
    }

    public void ApiGetContracts()
    {
        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name

        db.collection("CT_TB_CONTRACT")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            mArrayList.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("Jonathan", "findAll  => " + document.getData());

                                Map<String,Object> map = document.getData();
                                HashMap<String,String> newMap =new HashMap<String,String>();
                                for (Map.Entry<String, Object> entry : map.entrySet()) {
                                    if(entry.getValue() instanceof String){
                                        newMap.put(entry.getKey(), (String) entry.getValue());
                                    }
                                }
                                mArrayList.add(newMap);
                            }

                            mOriginArrayList = (ArrayList<HashMap<String, String>>) mArrayList.clone();

                            Collections.shuffle(mArrayList);

                            Collections.sort(mArrayList, new Comparator<HashMap<String, String>>(){
                                public int compare(HashMap<String, String> obj1, HashMap<String, String> obj2) {

                                    return nullCheck(obj2.get("num")).compareTo(nullCheck(obj1.get("num"))); // To compare string values
                                }
                            });


                            arryContractList = (ArrayList<HashMap<String, String>>)mArrayList.clone();

                            createMenu();


                        } else {
                            Log.w("Jonathan", "Error getting documents.", task.getException());
                        }
                    }
                });



//        HashMap<String,String> LoginInfo = new HashMap<String, String>();
//
//        final ApiGetContracts getContracts = new ApiGetContracts(this, new InterfaceAsyncResponse() {
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
//                    createMenu();
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
//        getContracts.execute(LoginInfo);

    }





    public void createMenu() {
        ll_contract.removeAllViews();

        for(int i = 0 ; i < arryContractList.size() ; i++)
        {

            String SCREEN1_CD = "";
            String SCREEN1_NM = "";

            SCREEN1_CD = arryContractList.get(i).get("CONT_NO");
            SCREEN1_NM = arryContractList.get(i).get("CONT_NAME");

            Log.e("Jonathan", i + "SCREEN1_CD :: " + SCREEN1_CD );
            Log.e("Jonathan", i + "SCREEN1_NM :: " + SCREEN1_NM );


            //1Depth 만들기
            final LinearLayout ll_depth_total1 = makeLinear();

            //1Depth 셋팅
            final View view1Depth = setting1Depth(R.layout.lay_menu_depth1_item, arryContractList.get(i), SCREEN1_NM);
            view1Depth.setTag(arryContractList.get(i));
            //1Depth 붙이기
            ll_depth_total1.addView(view1Depth);
            ll_depth_total1.setTag(arryContractList.get(i));
            view1Depth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(mContext, Activity3.class);
                    intent.putExtra("CONTRACT", (Serializable) view1Depth.getTag());
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

//                    Toast.makeText(mContext, "MENU :: " + view1Depth.getTag().toString() , Toast.LENGTH_SHORT).show();

                }
            });

            ll_contract.addView(ll_depth_total1);




        }




    }


    public final LinearLayout makeLinear()
    {

        final LinearLayout ll_depth_total = new LinearLayout(this);
        LinearLayout.LayoutParams ll_depth1_total_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll_depth_total.setOrientation(LinearLayout.VERTICAL);
        ll_depth_total.setLayoutParams(ll_depth1_total_params);



        return ll_depth_total;
    }


    public View setting1Depth(int layout_id, final HashMap<String, String> code, final String name)
    {
        final View view = LayoutInflater.from(this).inflate(layout_id, null);
        RelativeLayout rl1Depth = (RelativeLayout) view.findViewById(R.id.rl_menu_1depth_layout);
        TextView tvMenuNameD1 = (TextView) view.findViewById(R.id.tv_menu_depth1_name);


        rl1Depth.setTag(code);
        tvMenuNameD1.setText(name);



        return view;
    }



}

package com.woongjin.sendemailtest_admin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.woongjin.sendemailtest_admin.Popup.NoticePopup;
import com.woongjin.sendemailtest_admin.Popup.OnEventOkListener;
import com.woongjin.sendemailtest_admin.Popup.ProgressPopup;
import com.woongjin.sendemailtest_admin.WebConnect.ApiInsertContracts;
import com.woongjin.sendemailtest_admin.WebConnect.ApiInsertPerson;
import com.woongjin.sendemailtest_admin.WebConnect.InterfaceAsyncResponse;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ActivityContractForm extends AppCompatActivity {


    Context mContext;
    WebView myWebView;


    String html = "";


    Button btn_send_email;



    HashMap<String,String> contractdb = new HashMap<>();
    HashMap<String,String> mContractData = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_form);

        mContext = this;



        contractdb = (HashMap<String, String>) getIntent().getSerializableExtra("contractdb");
        mContractData = (HashMap<String,String>)getIntent().getSerializableExtra("contract");




        Log.e("Jonathan", "contractdb :: " + contractdb);
        Log.e("Jonathan", "mContractData :: " + mContractData);



        btn_send_email = (Button)findViewById(R.id.btn_send_email);
        btn_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                for(int i = 0 ;i < 50 ; i++)
//                {
//                    name = contractdb.get("NAME_PLACE") + getRandomNumberString();
//                    tel = "01000" + getRandomNumberString();
//
//                    savePerson();
//                }

                savePerson();

            }
        });


        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setBuiltInZoomControls(true);

        if (Build.VERSION.SDK_INT >= 21) {
            myWebView.enableSlowWholeDocumentDraw ();
        }




        try {

            html = mContractData.get("CONT_CON");
            html = html.replace("{NAME_PLACE}", contractdb.get("NAME_PLACE"));
            html = html.replace("{TEL_PLACE}", contractdb.get("TEL_PLACE"));
            html = html.replace("{ADRS_PLACE}", "");
            html = html.replace("{JUMIN_PLACE}", "");
            html = html.replace("{LOC_PLACE}", contractdb.get("LOC_PLACE"));

            html = html.replace("{BN_PLACE}", contractdb.get("BN_PLACE"));
            html = html.replace("{BNM_PLACE}", contractdb.get("BNM_PLACE"));
            html = html.replace("{BCEO_PLACE}", contractdb.get("BCEO_PLACE"));

            html = html.replace("{MINP_PLACE}", contractdb.get("MINP_PLACE"));
            html = html.replace("{LUNCH_PLACE}", contractdb.get("LUNCH_PLACE"));



            html = html.replace("{CHECK_P_PLACE}", contractdb.get("CHECK_P_PLACE"));
            html = html.replace("{AGREE_P_PLACE}", contractdb.get("AGREE_P_PLACE"));
            html = html.replace("{HAP_P_PLACE}", contractdb.get("HAP_P_PLACE"));



            //일급
            if("CT0003".equals(mContractData.get("CONT_NO")))
            {
                html = html.replace("{H_PAY_PLACE}", contractdb.get("H_PAY_PLACE"));
                html = html.replace("{D_PAY_PLACE}", contractdb.get("D_PAY_PLACE"));
                html = html.replace("{BD_PAY_PLACE}", contractdb.get("BD_PAY_PLACE"));
                html = html.replace("{BD_RAT_PLACE}", contractdb.get("BD_RAT_PLACE"));
                html = html.replace("{WL_PAY_PLACE}", contractdb.get("WL_PAY_PLACE"));
                html = html.replace("{WL_RAT_PLACE}", contractdb.get("WL_RAT_PLACE"));
                html = html.replace("{REMARK1}", contractdb.get("REMARK1"));
                html = html.replace("{REMARK2}", contractdb.get("REMARK2"));
            }
            //월급
            else if("CT0004".equals(mContractData.get("CONT_NO")))
            {

                html = html.replace("{SUSP_PT_PLACE}", contractdb.get("SUSP_PT_PLACE"));
                html = html.replace("{MMPAY_PLACE}", contractdb.get("MMPAY_PLACE"));
                html = html.replace("{B_W_T_PLACE}", contractdb.get("B_W_T_PLACE"));
                html = html.replace("{B_W_P_PLACE}", contractdb.get("B_W_P_PLACE"));
                html = html.replace("{B_H_T_PLACE}", contractdb.get("B_H_T_PLACE"));
                html = html.replace("{B_H_P_PLACE}", contractdb.get("B_H_P_PLACE"));
                html = html.replace("{REMARK1}", contractdb.get("REMARK1"));
                html = html.replace("{REMARK2}", contractdb.get("REMARK2"));
            }


            html = html.replace("{YY_PLACE}", contractdb.get("YY_PLACE"));
            html = html.replace("{MM_PLACE}", contractdb.get("MM_PLACE"));
            html = html.replace("{DD_PLACE}", contractdb.get("DD_PLACE"));

            html = html.replace("{CONT_FM_D_PLACE}", contractdb.get("CONT_FM_D_PLACE"));
            html = html.replace("{CONT_TO_D_PLACE}", contractdb.get("CONT_TO_D_PLACE"));

            html = html.replace("{GAP_PLACE}", contractdb.get("GAP_PLACE"));
            html = html.replace("{WORK_P_PLACE}", contractdb.get("WORK_P_PLACE"));



            Drawable myDrawable = getResources().getDrawable(R.drawable.stamp);
            Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
            String imgageBase64_stamp = Base64.encodeToString(bitmapToByteArray(myLogo), Base64.DEFAULT);
            String image_stamp = "data:image/png;base64," + imgageBase64_stamp;

            html = html.replace("{IMAGE_STAMP}", image_stamp);



            myWebView.loadDataWithBaseURL("", html, "text/html", "UTF-8", null);
        } catch (Exception xxx) {
            Log.e("Jonathan", "Load assets/page.html", xxx);
        }


        //Load the image into your WebView:
//        myWebView.loadDataWithBaseURL("", html, "text/html","utf-8", "");
//        myWebView.loadUrl(html);


        saveHtmlFile();



    }





    private void saveHtmlFile() {

        String fileName = "";
        String ex_storage = FileManager.getExternalPath();
        // Get Absolute Path in External Sdcard
        String path = ex_storage + "/" + "CONTRACT_FOLDER";

        fileName = "contract.html";
        File file = new File(path, fileName);

        try {
            FileOutputStream out = new FileOutputStream(file);
            byte[] data = html.getBytes();
            out.write(data);
            out.close();
            Log.e("Jonathan", "File Save : " + file.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static byte[] bitmapToByteArray(Bitmap $bitmap) {

        if ($bitmap == null)
            return null;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        $bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    String name = "";
    String tel = "";


    public void savePerson()
    {

        HashMap<String,String> insertPersonData = new HashMap<String, String>();

        Iterator<String> keys = contractdb.keySet().iterator();
        while (keys.hasNext()) {

            String key = keys.next();
            insertPersonData.put(key, contractdb.get(key));
        }


        final HashMap<String, String> personMap = new HashMap<>();
        personMap.put("C_NAME", insertPersonData.get("NAME_PLACE"));
        personMap.put("PHONE", insertPersonData.get("TEL_PLACE"));


        //테스트 주석처리
//        personMap.put("C_NAME", name);
//        personMap.put("PHONE", tel);
//        Log.e("Jonathan", " name :: " + name + " tel :: " + tel);
        //테스트 주석처리


        personMap.put("PASWD", getRandomNumberString());

        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        // Add a new document with a generated ID
        db.collection("CT_TB_PERSON")
                .whereEqualTo("C_NAME", personMap.get("C_NAME"))
                .whereEqualTo("PHONE", personMap.get("PHONE"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("Jonathan", "findAll  => " + document.getData());

                                Map<String, Object> map = document.getData();
                                HashMap<String, String> newMap = new HashMap<String, String>();
                                for (Map.Entry<String, Object> entry : map.entrySet()) {
                                    if (entry.getValue() instanceof String) {
                                        newMap.put(entry.getKey(), (String) entry.getValue());
                                    }
                                }
                                newMap.put("id", document.getId());
                                arrayList.add(newMap);
                            }


                            if(arrayList.size() > 0)
                            {
                                //기존에 이름이 등록된 경우. (핸드폰번호 같음)
                                //
                                FirebaseFirestore db = FirebaseFirestore.getInstance();

                                // Add a new document with a generated ID
                                db.collection("CT_TB_PERSON")
                                        .document(arrayList.get(0).get("id"))
                                        .set(personMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                saveFormat();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("Jonathan", "Error adding document", e);

                                                NoticePopup popup = new NoticePopup(mContext, "저장에 실패하였습니다.\n 다시 시도해 주세요.", new OnEventOkListener() {
                                                    @Override
                                                    public void onOk() {
                                                        // TODO Auto-generated method stub

                                                    }
                                                });
                                                popup.setCanceledOnTouchOutside(false);
                                                popup.show();

                                            }
                                        });

                            }
                            else
                            {
                                //기존에 이름이 등록되지 않은경우.

                                // Add a new document with a generated ID
                                db.collection("CT_TB_PERSON")
                                        .add(personMap)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d("Jonathan", "DocumentSnapshot added with ID: " + documentReference.getId());
                                                saveFormat();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("Jonathan", "Error adding document", e);

                                                NoticePopup popup = new NoticePopup(mContext, "저장에 실패하였습니다.\n 다시 시도해 주세요.", new OnEventOkListener() {
                                                    @Override
                                                    public void onOk() {
                                                        // TODO Auto-generated method stub

                                                    }
                                                });
                                                popup.setCanceledOnTouchOutside(false);
                                                popup.show();

                                            }
                                        });
                            }



                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Jonathan", "Error adding document", e);

                        NoticePopup popup = new NoticePopup(mContext, "저장에 실패하였습니다.\n 다시 시도해 주세요.", new OnEventOkListener() {
                            @Override
                            public void onOk() {
                                // TODO Auto-generated method stub

                            }
                        });
                        popup.setCanceledOnTouchOutside(false);
                        popup.show();

                    }
                });









//        final ApiInsertPerson apiInsertPerson = new ApiInsertPerson(this, new InterfaceAsyncResponse() {
//            @Override
//            public void processFinish(String output) {
//                try {
//
//                    JSONObject result = new JSONObject(output);
//                    if("1".equals(result.getString("getData")))
//                    {
//                        saveFormat();
//
//                    }
//                    else
//                    {
//                        NoticePopup popup = new NoticePopup(mContext, "저장에 실패하였습니다.\n 다시 시도해 주세요.", new OnEventOkListener() {
//                            @Override
//                            public void onOk() {
//                                // TODO Auto-generated method stub
//
//                            }
//                        });
//                        popup.setCanceledOnTouchOutside(false);
//                        popup.show();
//
//                    }
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
//        apiInsertPerson.execute(insertPersonData);


    }


    //랜덤 키 생성
    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }







    public void saveFormat()
    {
        final ProgressPopup dialog = new ProgressPopup(mContext);

        if(!dialog.isShowing())
        {
            dialog.setMessage("로딩중...");
            dialog.show();
        }



        HashMap<String,String> insertData = new HashMap<String, String>();

        Iterator<String> keys = contractdb.keySet().iterator();
        while (keys.hasNext()) {

            String key = keys.next();
            insertData.put(key, contractdb.get(key));
        }


        Iterator<String> keys2 = mContractData.keySet().iterator();
        while (keys2.hasNext()) {

            String key = keys2.next();
            insertData.put(key, mContractData.get(key));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());


//        insertData.put("NAME_PLACE", name);
//        insertData.put("TEL_PLACE", tel);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String newDocRef = db.collection("CT_TB_CONTRACT_HIST").document().getId();


        insertData.put("UPLD_DT", currentDateandTime);
        insertData.put("SEND_YN", "N");
        insertData.put("SEQ", newDocRef);

        // Add a new document with a generated ID
        db.collection("CT_TB_CONTRACT_HIST").document(newDocRef)
                .set(insertData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dialog.dismiss();
                        finish();
                    }
                })
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("Jonathan", "DocumentSnapshot added with ID: " + documentReference.getId());
////                        saveFormat();
//
//                        dialog.dismiss();
//                        finish();
//
//                    }
//                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Jonathan", "Error adding document", e);

                        NoticePopup popup = new NoticePopup(mContext, "저장에 실패하였습니다.\n 다시 시도해 주세요.", new OnEventOkListener() {
                            @Override
                            public void onOk() {
                                // TODO Auto-generated method stub

                            }
                        });
                        popup.setCanceledOnTouchOutside(false);
                        popup.show();

                    }
                });






//        final ApiInsertContracts getContracts = new ApiInsertContracts(this, new InterfaceAsyncResponse() {
//            @Override
//            public void processFinish(String output) {
//                try {
//
//                    JSONObject result = new JSONObject(output);
//                    if("1".equals(result.getString("getData")))
//                    {
//
//                        NoticePopup popup = new NoticePopup(mContext, "저장 성공하였습니다.", new OnEventOkListener() {
//                            @Override
//                            public void onOk() {
//                                // TODO Auto-generated method stub
//
//                                Intent intent = new Intent(mContext, Activity1.class);
//                                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//
//
//                            }
//                        });
//                        popup.setCanceledOnTouchOutside(false);
//                        popup.show();
//
//                    }
//                    else
//                    {
//                        NoticePopup popup = new NoticePopup(mContext, "저장에 실패하였습니다.\n 다시 시도해 주세요.", new OnEventOkListener() {
//                            @Override
//                            public void onOk() {
//                                // TODO Auto-generated method stub
//
//                            }
//                        });
//                        popup.setCanceledOnTouchOutside(false);
//                        popup.show();
//
//                    }
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
//        getContracts.execute(insertData);

    }




}

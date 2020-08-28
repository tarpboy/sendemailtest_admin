package com.woongjin.sendemailtest_admin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.View.GONE;

public class Activity3 extends AppCompatActivity {


    Context mContext ;


    Button btn_next;


    TextView tv_cont_no;
    TextView tv_cont_name;

    EditText et_from_date;
    EditText et_to_date;
    EditText et_name;
    EditText et_tel;
    EditText et_email;
    EditText et_loc;


    EditText et_minp;
    EditText et_lunch;


    EditText et_bnm;    //사업자명
    EditText et_bn;     //사업자번호
    EditText et_bceo;   //대표자



    EditText et_hpay;
    EditText et_dpay;

    EditText et_BD_PAY;
    EditText et_BD_RAT;
    EditText et_WL_PAY;
    EditText et_WL_RAT;
    EditText et_REMARK1;
    EditText et_REMARK2;


    EditText et_susp_pt;        //수습 퍼센트
    EditText et_mmpay;          //월급

    EditText et_B_W_T_PLACE;
    EditText et_B_H_T_PLACE;
    EditText et_B_W_P_PLACE;
    EditText et_B_H_P_PLACE;
    EditText et_REMARK1_month;
    EditText et_REMARK2_month;





    LinearLayout agreeLayout_daily;
    LinearLayout agreeLayout_month;

    LinearLayout ll_daily;
    LinearLayout ll_month;




    HashMap<String,String> mContractData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        mContext = this;



        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }




        mContractData = (HashMap<String,String>)getIntent().getSerializableExtra("CONTRACT");


        agreeLayout_daily = (LinearLayout)findViewById(R.id.agreeLayout_daily);
        agreeLayout_month = (LinearLayout)findViewById(R.id.agreeLayout_month);
        ll_daily = (LinearLayout)findViewById(R.id.ll_daily);
        ll_month = (LinearLayout)findViewById(R.id.ll_month);

        agreeLayout_daily.setVisibility(GONE);
        agreeLayout_month.setVisibility(GONE);
        ll_daily.setVisibility(GONE);
        ll_month.setVisibility(GONE);


        tv_cont_no = (TextView)findViewById(R.id.tv_cont_no);
        tv_cont_name = (TextView)findViewById(R.id.tv_cont_name);


        et_from_date = (EditText)findViewById(R.id.et_from_date);
        et_to_date = (EditText)findViewById(R.id.et_to_date);

        et_name = (EditText)findViewById(R.id.et_name);
        et_tel = (EditText)findViewById(R.id.et_tel);
        et_email = (EditText)findViewById(R.id.et_email);
        et_loc = (EditText)findViewById(R.id.et_loc);
        et_minp = (EditText)findViewById(R.id.et_minp);
        et_lunch = (EditText)findViewById(R.id.et_lunch);

        et_bnm = (EditText)findViewById(R.id.et_bnm);
        et_bn = (EditText)findViewById(R.id.et_bn);
        et_bceo = (EditText)findViewById(R.id.et_bceo);




        //일급


        et_hpay = (EditText)findViewById(R.id.et_hpay);
        et_dpay = (EditText)findViewById(R.id.et_dpay);


        et_BD_PAY = (EditText)findViewById(R.id.et_BD_PAY);
        et_BD_RAT = (EditText)findViewById(R.id.et_BD_RAT);
        et_WL_PAY = (EditText)findViewById(R.id.et_WL_PAY);
        et_WL_RAT = (EditText)findViewById(R.id.et_WL_RAT);
        et_REMARK1 = (EditText)findViewById(R.id.et_REMARK1);
        et_REMARK2 = (EditText)findViewById(R.id.et_REMARK2);





        //월급

        et_susp_pt = (EditText)findViewById(R.id.et_susp_pt);
        et_mmpay = (EditText)findViewById(R.id.et_mmpay);


        et_B_W_T_PLACE = (EditText)findViewById(R.id.et_B_W_T_PLACE);
        et_B_H_T_PLACE = (EditText)findViewById(R.id.et_B_H_T_PLACE);
        et_B_W_P_PLACE = (EditText)findViewById(R.id.et_B_W_P_PLACE);
        et_B_H_P_PLACE = (EditText)findViewById(R.id.et_B_H_P_PLACE);
        et_REMARK1_month = (EditText)findViewById(R.id.et_REMARK1_month);
        et_REMARK2_month = (EditText)findViewById(R.id.et_REMARK2_month);






        tv_cont_no.setText(mContractData.get("CONT_NO"));
        tv_cont_name.setText("< " + mContractData.get("CONT_NAME") + ">");


        //일급
        if("CT0003".equals(mContractData.get("CONT_NO")))
        {
            agreeLayout_daily.setVisibility(View.VISIBLE);
            ll_daily.setVisibility(View.VISIBLE);
        }
        //월급
        else if("CT0004".equals(mContractData.get("CONT_NO")))
        {
            agreeLayout_month.setVisibility(View.VISIBLE);
            ll_month.setVisibility(View.VISIBLE);
        }


        btn_next = (Button)findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, ActivityContractForm.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);

                HashMap<String, String> setData = new HashMap<>();
                setData.put("NAME_PLACE", et_name.getText().toString());
                setData.put("TEL_PLACE", et_tel.getText().toString());
                setData.put("LOC_PLACE", et_loc.getText().toString());
                setData.put("CONT_FM_D_PLACE", et_from_date.getText().toString());
                setData.put("CONT_TO_D_PLACE", et_to_date.getText().toString());

                setData.put("BN_PLACE", et_bn.getText().toString());
                setData.put("BNM_PLACE", et_bnm.getText().toString());
                setData.put("BCEO_PLACE", et_bceo.getText().toString());

                setData.put("MINP_PLACE", et_minp.getText().toString());
                setData.put("LUNCH_PLACE", et_lunch.getText().toString());


                setData.put("CHECK_P_PLACE",   et_name.getText().toString());
                setData.put("AGREE_P_PLACE", et_name.getText().toString());
                setData.put("HAP_P_PLACE", et_name.getText().toString());

                setData.put("GAP_PLACE", et_bnm.getText().toString() + "  " + et_bceo.getText().toString());
                setData.put("WORK_P_PLACE", et_name.getText().toString());







                //일급
                if("CT0003".equals(mContractData.get("CONT_NO")))
                {
                    setData.put("H_PAY_PLACE", et_hpay.getText().toString());
                    setData.put("D_PAY_PLACE", et_dpay.getText().toString());
                    setData.put("BD_PAY_PLACE", et_BD_PAY.getText().toString());
                    setData.put("BD_RAT_PLACE", et_BD_RAT.getText().toString());
                    setData.put("WL_PAY_PLACE", et_WL_PAY.getText().toString());
                    setData.put("WL_RAT_PLACE", et_WL_RAT.getText().toString());
                    setData.put("REMARK1", et_REMARK1.getText().toString());
                    setData.put("REMARK2", et_REMARK2.getText().toString());
                }
                //월급
                else if("CT0004".equals(mContractData.get("CONT_NO")))
                {
                    setData.put("SUSP_PT_PLACE", et_susp_pt.getText().toString());
                    setData.put("MMPAY_PLACE", et_mmpay.getText().toString());
                    setData.put("B_W_T_PLACE", et_B_W_T_PLACE.getText().toString());
                    setData.put("B_W_P_PLACE", et_B_W_P_PLACE.getText().toString());
                    setData.put("B_H_T_PLACE", et_B_H_T_PLACE.getText().toString());
                    setData.put("B_H_P_PLACE", et_B_H_P_PLACE.getText().toString());

                    setData.put("REMARK1", et_REMARK1_month.getText().toString());
                    setData.put("REMARK2", et_REMARK2_month.getText().toString());
                }


                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                String yy = currentDateandTime.substring(0, 4);
                String mm = currentDateandTime.substring(4, 6);
                String dd = currentDateandTime.substring(6);


                setData.put("YY_PLACE", yy);
                setData.put("MM_PLACE", mm);
                setData.put("DD_PLACE", dd);




                Log.e("Jonathan", "mContractData : " +mContractData);

                intent.putExtra("contractdb", (HashMap<String, String>)setData);
                intent.putExtra("contract", mContractData);





                startActivity(intent);
            }
        });





    }











}

 package com.woongjin.sendemailtest_admin.WebConnect;

 import android.content.Context;
 import android.icu.text.SimpleDateFormat;
 import android.net.ConnectivityManager;
 import android.net.NetworkInfo;
 import android.util.Log;

 import com.woongjin.sendemailtest_admin.Define;

 import org.json.JSONObject;

 import java.util.ArrayList;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.Locale;
 import java.util.Map;
 import java.util.Random;


 public class WebApi
{
	
//	public static final String SERVER_URL = "http://ec2-52-78-121-164.ap-northeast-2.compute.amazonaws.com:8080/doHelloParamTest";
//    public static final String SERVER_URL = "http://222.122.20.35:8080/onWedding";
    public static final String SERVER_URL = "http://115.68.20.80:8080/onWedding";

//    public static final String SCHEMA = "" + SCHEMA + "";
    public static final String SCHEMA = "onWedding.";



	private static final int CONNECTION_TIME_OUT = 10 * 1000;

	public static class NetCheck {
		public static final int NETWORK_DISCONNECTED = -1;
		public static final int NETWORK_CONNECTED_3G = 0;
		public static final int NETWORK_CONNECTED_WIFI = 1;
		public static final int NETWORK_CONNECTED_ETC = 2;


		public static int NetStateCheck(Context mCtx){
			ConnectivityManager cm = (ConnectivityManager)mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			if(info == null || !info.isConnected()){
				return NETWORK_DISCONNECTED;
			}
			switch(info.getType()){
			case ConnectivityManager.TYPE_WIFI:
				return NETWORK_CONNECTED_WIFI;
			case  ConnectivityManager.TYPE_MOBILE:
				return NETWORK_CONNECTED_3G;
			default:
				return NETWORK_CONNECTED_ETC;
			}
		}
	}






    public static JSONObject getContract(Context context, HashMap<String, String> sendData) throws Exception
    {


        String query = "SELECT * " +
                "FROM " + SCHEMA + "CT_TB_CONTRACT " +
                "";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;


    }



    public static JSONObject getContractHist(Context context, HashMap<String, String> sendData) throws Exception
    {


        String  query = "SELECT * ";
                query += "FROM " + SCHEMA + "CT_TB_CONTRACT_HIST A " ;
                query +="LEFT OUTER JOIN " + SCHEMA + "CT_TB_PERSON B ON B.C_NAME = A.NAME_PLACE  AND  B.PHONE = A.TEL_PLACE ";
                if(sendData.containsKey("A.NAME_PLACE"))
                {
                    query += "WHERE NAME  LIKE '%" + sendData.get("NAME")  + "%' ";
                }

                query += "ORDER BY A.UPLD_DT DESC " ;




                query += "";

        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;


    }




    // 계약저장
    public static JSONObject insertContract(Context context, HashMap<String, String> getOrderMap ) throws Exception
    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());


        String query = "";
        //쿼리를 여기서 만든다.


        query =  "INSERT INTO " + SCHEMA + "CT_TB_CONTRACT_HIST " +

                "(" +
                "CONT_NO" + "," +
                "CONT_NAME" + "," +

                "BN_PLACE" + "," +
                "BNM_PLACE" + "," +
                "BCEO_PLACE" + "," +
                "NAME_PLACE" + "," +
                "TEL_PLACE" + "," +
                "LOC_PLACE" + "," +
                "CONT_FM_D_PLACE" + "," +
                "CONT_TO_D_PLACE" + "," +
                "MINP_PLACE" + "," +
                "LUNCH_PLACE" + "," +
                "YY_PLACE" + "," +
                "MM_PLACE" + "," +
                "DD_PLACE" + "," +
                "GAP_PLACE" + "," +
                "WORK_P_PLACE" + "," +
                "CHECK_P_PLACE" + "," +
                "AGREE_P_PLACE" + "," +
                "HAP_P_PLACE" + "," +

                "D_PAY_PLACE" + "," +
                "H_PAY_PLACE" + "," +

                "SUSP_PT_PLACE" + "," +
                "MMPAY_PLACE" + "," +

                "BD_RAT_PLACE" + "," +
                "BD_PAY_PLACE" + "," +
                "WL_RAT_PLACE" + "," +
                "WL_PAY_PLACE" + "," +

                "B_W_T_PLACE" + "," +
                "B_W_P_PLACE" + "," +
                "B_H_T_PLACE" + "," +
                "B_H_P_PLACE" + "," +

                "SEND_YN" + "," +

                "REMARK1" + "," +
                "REMARK2" + "," +

                "UPLD_DT" +
                ") " +

                "VALUES " +

                "(" +
                "'" +  getOrderMap.get("CONT_NO") + "'" + "," +
                "'" +  getOrderMap.get("CONT_NAME") + "'" + "," +

                "'" +  getOrderMap.get("BN_PLACE") + "'" + "," +
                "'" +  getOrderMap.get("BNM_PLACE") + "'" + "," +
                "'" +  getOrderMap.get("BCEO_PLACE") + "'" + "," +
                "'" +  getOrderMap.get("NAME_PLACE") + "'" + "," +
                "'" +  getOrderMap.get("TEL_PLACE") + "'" + "," +
                "'" +  getOrderMap.get("LOC_PLACE") + "'" + "," +
                "'" +  getOrderMap.get("CONT_FM_D_PLACE") + "'" + "," +
                "'" +  getOrderMap.get("CONT_TO_D_PLACE") + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("MINP_PLACE")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("LUNCH_PLACE")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("YY_PLACE")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("MM_PLACE")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("DD_PLACE")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("GAP_PLACE")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("WORK_P_PLACE")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("CHECK_P_PLACE")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("AGREE_P_PLACE")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("HAP_P_PLACE")) + "'" + "," +

                "'" +  Define.nullCheck(getOrderMap.get("D_PAY_PLACE")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("H_PAY_PLACE")) + "'" + "," +

                "'" +  Define.nullCheck(getOrderMap.get("SUSP_PT_PLACE")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("MMPAY_PLACE")) + "'" + "," +


                "'" +  Define.nullCheck(getOrderMap.get("BD_RAT_PLACE")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("BD_PAY_PLACE")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("WL_RAT_PLACE")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("WL_PAY_PLACE")) + "'" + "," +

                "'" +  Define.nullCheck(getOrderMap.get("B_W_T_PLACE")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("B_W_P_PLACE")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("B_H_T_PLACE")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("B_H_P_PLACE")) + "'" + "," +

                "'" +  "N" + "'" + "," +

                "'" +  Define.nullCheck(getOrderMap.get("REMARK1")) + "'" + "," +
                "'" +  Define.nullCheck(getOrderMap.get("REMARK2")) + "'" + "," +

                "'" +  currentDateandTime + "'" +
                ")" ;






        JSONObject returnJson = WebConnection.insertQuery(context, query);

        return returnJson;

    }




    //사람 찾기
    public static JSONObject getPerson(Context context, HashMap<String, String> sendData) throws Exception
    {


        String query = "SELECT COUNT(C_NAME) AS C_COUNT " +
                "FROM " + SCHEMA + "CT_TB_PERSON " +
                "WHERE C_NAME  = '" + sendData.get("NAME_PLACE")  + "' " +
                "AND PHONE = '" + sendData.get("TEL_PLACE") + "' " +
                "";


        JSONObject returnJson = WebConnection.selectQuery(context, query);

        return returnJson;


    }



    // 사람저장
    public static JSONObject insertPerson(Context context, HashMap<String, String> getOrderMap ) throws Exception
    {

        String query = "";
        //쿼리를 여기서 만든다.

        query =  "INSERT INTO " + SCHEMA + "CT_TB_PERSON " +

                "(" +
                "C_NAME" + "," +
                "PHONE" + "," +
                "PASWD"+
                ") " +

                "VALUES " +

                "(" +
                "'" +  getOrderMap.get("NAME_PLACE") + "'" + "," +
                "'" +  getOrderMap.get("TEL_PLACE") + "'" + "," +
                "'" +  getRandomNumberString() + "'" +
                ")" ;



        JSONObject returnJson = WebConnection.insertQuery(context, query);

        return returnJson;

    }





    //사람 업데이트
    public static JSONObject updatePerson(Context context, HashMap<String, String> getData) throws Exception
    {


        String query = "";
        //쿼리를 여기서 만든다.

        query += "UPDATE " + SCHEMA + "CT_TB_PERSON SET ";
        query += "C_NAME"   +  " = " + "'" + getData.get("NAME_PLACE")    +  "', ";
        query += "PHONE"   +  " = " + "'" + getData.get("TEL_PLACE")    +  "', " +
                "PASWD"   +  " = " + "'" + getRandomNumberString() + "' " +
                "WHERE C_NAME = '" + getData.get("NAME_PLACE")  + "' " +
                "AND PHONE = '" + getData.get("TEL_PLACE") + "' " ;

        JSONObject returnJson = WebConnection.updateQuery(context, query);

        return returnJson;


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







}


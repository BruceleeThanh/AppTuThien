package com.example.vuduc.services;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.error.ServerError;
import com.android.volley.error.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.vuduc.libs.ApiConstants;
import com.example.vuduc.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Brucelee Thanh on 15/12/2016.
 */

public class AppRequest {

    private CustomRequest customRequest;
    private Response.Listener<JSONObject> response = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            if (JsonUtil.getInt(response, ApiConstants.DEF_CODE, 0) == 1) {
                if (onAppRequestListener != null)
                    onAppRequestListener.onAppResponse(response);
            } else if (onAppRequestListener != null)
                onAppRequestListener.onAppError(JsonUtil.getInt(response, ApiConstants.DEF_CODE, 0),
                        JsonUtil.getString(response, ApiConstants.DEF_MSG, ""));

        }
    };

    private Response.ErrorListener error = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            NetworkResponse response = error.networkResponse;
            if (error instanceof ServerError && response != null) {
                try {
                    String res = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                    // Now you can use any deserializer to make sense of data
                    JSONObject obj = new JSONObject(res);
                } catch (UnsupportedEncodingException e1) {
                    // Couldn't properly decode data to string
                    e1.printStackTrace();
                } catch (JSONException e2) {
                    // returned data is not JSONObject?
                    e2.printStackTrace();
                }
            }
            if (onAppRequestListener != null)
                onAppRequestListener.onAppError(-404, "Sự cố kết nối. Vui lòng kiểm tra kết nối mạng hoặc thử lại sau!");
        }
    };

    public AppRequest(Context context, int method, String url, Map<String, String> params, boolean hasCache) {
        customRequest = new CustomRequest(method, url, params, response, error);
        MySingleton.getInstance(context).addToRequestQueue(customRequest, hasCache);
    }

    private OnAppRequestListener onAppRequestListener;
    public OnNotifyResponseReceived onNotifyResponseReceived;

    public void setOnAppRequestListener(OnAppRequestListener listener) {
        onAppRequestListener = listener;
    }

    public void setOnNotifyResponseReceived(OnNotifyResponseReceived listener){
        onNotifyResponseReceived = listener;
    }

    public interface OnAppRequestListener {
        void onAppResponse(JSONObject response);
        void onAppError(int errorCode, String errorMsg);
    }

    public interface OnNotifyResponseReceived{
        void onNotify();
    }
}

package com.vukstankovic.cutout;

import android.os.Build;
import android.view.DisplayCutout;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class CDVCutout extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("has")) {
            this.has(callbackContext);
            return true;
        }
        return false;
    }

    private void has(CallbackContext callbackContext) {
        boolean cutout = false;
        int insetTop = 0;
        int insetBottom = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            DisplayCutout displayCutout = cordova.getActivity().getWindow().getDecorView().getRootWindowInsets().getDisplayCutout();
            if (displayCutout != null) {
                insetTop = displayCutout.getBoundingRects().get(0).height();
                insetBottom = cordova.getActivity().getWindow().getDecorView().getRootWindowInsets().getStableInsetBottom();
                cutout = true;
            }
            System.out.println(insetTop);
            System.out.println(insetBottom);
        }
        JSONObject cutoutInfo = new JSONObject();
        try {
            cutoutInfo.put("cutout", cutout);
            cutoutInfo.put("insetTop", insetTop);
            cutoutInfo.put("insetBottom", insetBottom);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        callbackContext.success(cutoutInfo);
    }
}

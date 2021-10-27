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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            DisplayCutout displayCutout = cordova.getActivity().getWindow().getDecorView().getRootWindowInsets().getDisplayCutout();
            System.out.println(displayCutout);
            if (displayCutout != null) {
                insetTop = displayCutout.getBoundingRects().get(0).height();
                cutout = true;
            }
        }
        JSONObject cutoutInfo = new JSONObject();
        try {
            cutoutInfo.put("cutout", cutout);
            cutoutInfo.put("insetTop", insetTop);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        callbackContext.success(cutoutInfo);
    }
}

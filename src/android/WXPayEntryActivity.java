package __PACKAGE_NAME__;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.apache.cordova.LOG;
import org.json.JSONException;
import org.json.JSONObject;

import news.chen.yu.ionic.UShareWechat;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (UShareWechat.wxAPI == null) {
            startMainActivity();
        } else {
            UShareWechat.wxAPI.handleIntent(getIntent(), this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);

        if (UShareWechat.wxAPI == null) {
            startMainActivity();
        } else {
            UShareWechat.wxAPI.handleIntent(intent, this);
        }

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d("u-share-wechat-resp", resp.toString());

        if (UShareWechat.currentCallbackContext == null) {
            startMainActivity();
            return ;
        }

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        auth(resp);
                        break;

                    case ConstantsAPI.COMMAND_PAY_BY_WX:
                    default:
                        UShareWechat.currentCallbackContext.success();
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                UShareWechat.currentCallbackContext.error("用户取消操作");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                UShareWechat.currentCallbackContext.error("授权失败");
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                UShareWechat.currentCallbackContext.error("发送失败");
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                UShareWechat.currentCallbackContext.error("不支持此类型操作");
                break;
            case BaseResp.ErrCode.ERR_COMM:
                UShareWechat.currentCallbackContext.error("通常错误：" + resp.errStr);
                break;
            default:
                UShareWechat.currentCallbackContext.error("未知错误：" + resp.errCode);
                break;
        }

        finish();
    }

    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    protected void auth(BaseResp resp) {
        SendAuth.Resp res = ((SendAuth.Resp) resp);

        Log.d("u-share-wechat-auth", res.toString());

        if (UShareWechat.currentCallbackContext == null) {
            return ;
        }

        JSONObject response = new JSONObject();
        try {
            response.put("code", res.code);
            response.put("state", res.state);
            response.put("country", res.country);
            response.put("lang", res.lang);
        } catch (JSONException e) {
            Log.e("u-share-wechat-auth", e.getMessage());
        }

        UShareWechat.currentCallbackContext.success(response);
    }

    protected void startMainActivity() {
        LOG.d("u-share-start-payment", "start main activity");
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage(getApplicationContext().getPackageName());
        getApplicationContext().startActivity(intent);
    }
}
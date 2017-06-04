package tbs;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

/**
 * Created by jia on 2017/5/18.
 */

public class TBS_Application extends Application {//使用腾讯X5内核
    private static TBS_Application instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        initTbs();
    }

    private void initTbs() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //LogUtil.i("onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };

        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
               // LogUtil.i("onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                //LogUtil.i("onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                //LogUtil.i("onDownloadProgress:" + i);
            }
        });

        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    public static TBS_Application getInstance() {
        return instance;
    }

}

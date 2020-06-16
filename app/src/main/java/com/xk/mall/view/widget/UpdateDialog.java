package com.xk.mall.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.pgyersdk.update.PgyUpdateManager;
import com.xk.mall.R;
import com.xk.mall.model.entity.UpdateAppBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 删除 历史 确认是否 弹窗
 */
public class UpdateDialog extends Dialog implements View.OnClickListener{

    private Button submitTxt;
    private ImageView cancelTxt;
    private TextView tvContent;
    private TextView tvTitle;

    private Context mContext;
    private String title;//标题
    private String content;
    private RelativeLayout rlLoading;
    private TextView tvProgress;
    private ProgressBar pbLoading;
    private ImageView ivHead;
    private UpdateAppBean updateAppBean;

    public UpdateDialog(Context context, int themeResId, UpdateAppBean updateAppBean) {
        super(context, themeResId);
        this.mContext = context;
        this.updateAppBean = updateAppBean;
        this.content = updateAppBean.getVersionNotes();
        this.title = "V" + updateAppBean.getInternallyVersionNumber();
    }

    public void setProgress(int progress){
        pbLoading.setProgress(progress);
        tvProgress.setText(progress + "%");
        int width = pbLoading.getWidth();
        tvProgress.setX(width * progress / 100 + ConvertUtils.dp2px(8));
    }

    private Thread downLoadThread;

    private boolean interceptFlag = false;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private int progress;
    /* 下载包安装路径 */
    private static final String savePath =
            Environment.getExternalStorageDirectory().getPath() + "/xk/";

    private static final String saveFileName = savePath + "app.apk";

    private void installApk() {
        Uri uri = Uri.parse(saveFileName);
        try {
            //防止蒲公英安装代码有问题
            PgyUpdateManager.installApk(uri);
        }catch (Exception e){
            try{
                AppUtils.installApp(uri.getPath());
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_update_dialog);
        setCanceledOnTouchOutside(false);
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(Integer integer){
        if(integer == DOWN_UPDATE){
            pbLoading.setProgress(progress);
            tvProgress.setText(progress + "%");
            int width = pbLoading.getWidth();
            tvProgress.setX(width * progress / 100 + ConvertUtils.dp2px(8));
        }else if(integer == DOWN_OVER){
            installApk();
        }
    }

    private void initView(){
        tvContent = findViewById(R.id.tv_content);
        tvTitle = findViewById(R.id.tv_update_title);
        tvTitle.setText(title + " 更新内容");
        tvContent.setText(content);
        rlLoading = findViewById(R.id.rl_loading);
        pbLoading = findViewById(R.id.pb_update_loading);
        tvProgress = findViewById(R.id.tv_progress);
        ivHead = findViewById(R.id.iv_update_head);
        submitTxt = findViewById(R.id.btn_submit);
        submitTxt.setOnClickListener(this);
        cancelTxt = findViewById(R.id.img_cha);
        cancelTxt.setOnClickListener(this);
        if(updateAppBean.getIfForceUpdate() == 1){
            cancelTxt.setVisibility(View.GONE);
            this.setCancelable(false);
        }
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(updateAppBean.getDownloadLink());

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(savePath);
                if(!file.exists()){
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                if(!file.exists()){
                    file.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do{
                    int numread = is.read(buf);
                    count += numread;
                    progress =(int)(((float)count / length) * 100);
                    //更新进度
                    EventBus.getDefault().post(DOWN_UPDATE);
//                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if(numread <= 0){
                        //下载完成通知安装
//                        mHandler.sendEmptyMessage(DOWN_OVER);
                        EventBus.getDefault().post(DOWN_OVER);
                        break;
                    }
                    fos.write(buf,0,numread);
                }while(!interceptFlag);//点击取消就停止下载.

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                Log.e("UpdateManager", e.getMessage());
            } catch(IOException e){
                Log.e("UpdateManager", e.getMessage());
            }
        }
    };

    /**
     * 下载apk
     */

    private void downloadApk(){
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_cha:
                this.dismiss();
                break;
            case R.id.btn_submit:
                downloadApk();
                submitTxt.setEnabled(false);
                submitTxt.setVisibility(View.GONE);
                rlLoading.setVisibility(View.VISIBLE);
                break;
        }
    }

}

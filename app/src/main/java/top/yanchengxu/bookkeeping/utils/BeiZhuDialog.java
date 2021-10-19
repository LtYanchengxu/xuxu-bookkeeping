package top.yanchengxu.bookkeeping.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import top.yanchengxu.bookkeeping.R;

public class BeiZhuDialog extends Dialog implements View.OnClickListener {

    EditText et;
    Button cancelBtn, ensureBtn;
    onEnsureListener onEnsureListener;

    // 设定回调接口方法
    public void setOnEnsureListener(BeiZhuDialog.onEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public BeiZhuDialog(@NonNull Context context) {

        super(context);

        // 设置对话框显示布局
        setContentView(R.layout.dialog_beizhu);
        et = findViewById(R.id.dialog_beizhu_et);
        cancelBtn = findViewById(R.id.dialog_beizhu_btn_cancel);
        ensureBtn = findViewById(R.id.dialog_beizhu_btn_ensure);
        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);

    }

    public interface onEnsureListener {
        void onEnsure();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.dialog_beizhu_btn_cancel:
                cancel();
                break;
            case R.id.dialog_beizhu_btn_ensure:
                if (onEnsureListener != null) {
                    onEnsureListener.onEnsure();
                }
                break;
        }

    }

    public String getEditText(){
        return et.getText().toString().trim();
    }

    /**
     * 设置Dialog宽度与屏幕尺寸一致
     */

    public void setDialogSize(){
//        获取当前窗口对象
        Window window = getWindow();
//        获取窗口对象的参数
        WindowManager.LayoutParams wlp = window.getAttributes();
//        获取屏幕宽度
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int)(d.getWidth());  //对话框窗口为屏幕窗口
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
        handler.sendEmptyMessageDelayed(1,100);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //自动弹出软键盘的方法
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };


}

package top.yanchengxu.bookkeeping.utils;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import top.yanchengxu.bookkeeping.R;

public class KeyBoardUtils {

    private Keyboard k1;
    private KeyboardView keyboardView;
    private EditText editText;

    public interface OnEnsureListener{
        public void onEnsure();
    }

    public OnEnsureListener getOnEnsureListener() {
        return onEnsureListener;
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    OnEnsureListener onEnsureListener;

    public KeyBoardUtils(KeyboardView keyboardView, EditText editText) {
        this.keyboardView = keyboardView;
        this.editText = editText;

        this.editText.setInputType(InputType.TYPE_NULL);  // 防止弹出系统键盘
        k1 = new Keyboard(this.editText.getContext(), R.xml.key);
        this.keyboardView.setKeyboard(k1);  // 引入自定义键盘
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false);
        this.keyboardView.setOnKeyboardActionListener(listener);

    }
    OnKeyboardActionListener listener = new OnKeyboardActionListener() {
        @Override
        public void onPress(int i) {

        }

        @Override
        public void onRelease(int i) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();
            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE:   //点击了删除键
                    if (editable != null && editable.length() > 0) {
                        if (start>0) {
                            editable.delete(start - 1, start);
                        }
                    }
                    break;
                case Keyboard.KEYCODE_CANCEL:   //点击了清零
                    editable.clear();
                    break;
                case Keyboard.KEYCODE_DONE:    //点击了完成
                    onEnsureListener.onEnsure();   //通过接口回调的方法，当点击确定时，可以调用这个方法
                    break;
                default:  //其他数字直接插入
                    editable.insert(start,Character.toString((char)primaryCode));
                    break;
            }
        }

        @Override
        public void onText(CharSequence charSequence) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };

    //    显示键盘
    public void showKeyboard(){
        int visibility = keyboardView.getVisibility();
        if (visibility == View.INVISIBLE ||visibility==View.GONE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    //    隐藏键盘
    public void hideKeyboard(){
        int visibility = keyboardView.getVisibility();
        if (visibility== View.VISIBLE||visibility==View.INVISIBLE) {
            keyboardView.setVisibility(View.GONE);
        }
    }

}

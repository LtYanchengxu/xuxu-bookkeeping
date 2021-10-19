package top.yanchengxu.bookkeeping.frag_record;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import top.yanchengxu.bookkeeping.R;
import top.yanchengxu.bookkeeping.db.AccountBean;
import top.yanchengxu.bookkeeping.db.TypeBean;
import top.yanchengxu.bookkeeping.utils.BeiZhuDialog;
import top.yanchengxu.bookkeeping.utils.KeyBoardUtils;
import top.yanchengxu.bookkeeping.utils.SelectTimeDialog;

/**
 * 记录页面-支出模块
 */
public abstract class BaseRecordFragment extends Fragment implements View.OnClickListener {

    KeyboardView keyboardView;
    EditText moneyEt;
    ImageView typeIv;
    GridView typeGv;
    TextView typeTv, beizhuTv, timeTv;
    List<TypeBean> typeList;
    TypeBaseAdapter adapter;
    AccountBean accountBean;  // 记账


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBean = new AccountBean();
        accountBean.setTypename("其他");
        accountBean.setsImageId(R.mipmap.ic_qita_fs);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_outcome, container, false);
        initView(view);
        // 初始化时间
        setInitTime();
        // 给GridView填充数据
        loadDataToGV();
        // 设置GridView每一项的点击事件
        setGVListener();
        return view;
    }

    /**
     * 获取当前时间 显示在TimeTv上
     */
    private void setInitTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = simpleDateFormat.format(date);
        timeTv.setText(time);

        accountBean.setTime(time);

        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int month = instance.get(Calendar.MONTH) + 1;
        int day = instance.get(Calendar.DAY_OF_MONTH);
        accountBean.setYear(year);
        accountBean.setMonth(month);
        accountBean.setDay(day);

    }

    /*
        GridView每一项的点击事件
     */
    private void setGVListener() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectedPos = position;
                // 提示数据源发生变化了
                adapter.notifyDataSetInvalidated();
                TypeBean typeBean = typeList.get(position);

                String typename = typeBean.getTypename();
                typeTv.setText(typename);
                accountBean.setTypename(typename);

                int simageId = typeBean.getSimageId();
                typeIv.setImageResource(simageId);
                accountBean.setsImageId(simageId);
            }
        });

    }

    public void loadDataToGV() {
        typeList = new ArrayList<>();
        adapter = new TypeBaseAdapter(getContext(), typeList);
        typeGv.setAdapter(adapter);
        /*
        // 需要抽取到子类中
        // 获取数据库中的数据源
        List<TypeBean> outList = DBManager.getTypeList(0);
        typeList.addAll(outList);
        adapter.notifyDataSetChanged();
        */
    }

    private void initView(View view) {
        keyboardView = view.findViewById(R.id.frag_record_keyboard);
        moneyEt = view.findViewById(R.id.frag_record_et_money);
        typeIv = view.findViewById(R.id.frag_record_iv);
        typeGv = view.findViewById(R.id.frag_record_gv);
        typeTv = view.findViewById(R.id.frag_record_tv_type);
        beizhuTv = view.findViewById(R.id.frag_record_tv_note);
        timeTv = view.findViewById(R.id.frag_record_tv_time);

        beizhuTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);

        KeyBoardUtils keyBoardUtils = new KeyBoardUtils(keyboardView, moneyEt);
        keyBoardUtils.showKeyboard();
        keyBoardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                // 点击了确定按钮
                // 获取输入钱数
                String moneyStr = moneyEt.getText().toString();
                if (TextUtils.isEmpty(moneyStr) || "0".equals(moneyStr)) {
                    getActivity().finish();
                    return;
                }
                float money = Float.parseFloat(moneyStr);
                accountBean.setMoney(money);

                // 返回记录记录信息并保存在数据库中

                saveAccountToDB();

                // 返回上一级页面
                getActivity().finish();
            }
        });
    }

    /**
     * 抽象方法 子类必须实现
     */
    public abstract void saveAccountToDB();


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_record_tv_time:
                showTimeDialog();
                break;

            case R.id.frag_record_tv_note:
                showBZDialog();
                break;
        }
    }

    private void showTimeDialog() {
        SelectTimeDialog selectTimeDialog = new SelectTimeDialog(getContext());
        selectTimeDialog.show();
        selectTimeDialog.setOnEnsureListener(new SelectTimeDialog.OnEnsureListener() {
            @Override
            public void onEnsure(String time, int year, int month, int day) {
                timeTv.setText(time);
                accountBean.setTime(time);
                accountBean.setYear(year);
                accountBean.setMonth(month);
                accountBean.setDay(day);
            }
        });
    }

    private void showBZDialog() {

        BeiZhuDialog beiZhuDialog = new BeiZhuDialog(getContext());
        beiZhuDialog.show();
        beiZhuDialog.setDialogSize();
        beiZhuDialog.setOnEnsureListener(new BeiZhuDialog.onEnsureListener() {
            @Override
            public void onEnsure() {
                String msg = beiZhuDialog.getEditText();
                if (!TextUtils.isEmpty(msg)) {
                    beizhuTv.setText(msg);
                    accountBean.setBeizhu(msg);
                }
                beiZhuDialog.cancel();
            }
        });
    }
}
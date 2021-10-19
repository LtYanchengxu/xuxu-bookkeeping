package top.yanchengxu.bookkeeping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import top.yanchengxu.bookkeeping.adapter.RecordPagerAdapter;
import top.yanchengxu.bookkeeping.frag_record.IncomeFragment;
import top.yanchengxu.bookkeeping.frag_record.BaseRecordFragment;
import top.yanchengxu.bookkeeping.frag_record.OutcomeFragment;

public class RecordActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        // 查找控件
        tabLayout = findViewById(R.id.record_tabs);
        viewPager = findViewById(R.id.record_vp);
        initPager();
    }

    private void initPager() {
        //
        List<Fragment> fragmentList = new ArrayList<>();
        OutcomeFragment outcomeFragment = new OutcomeFragment();
        IncomeFragment incomeFragment = new IncomeFragment();
        fragmentList.add(outcomeFragment);
        fragmentList.add(incomeFragment);

        // 创建适配器
        RecordPagerAdapter pagerAdapter = new RecordPagerAdapter(getSupportFragmentManager(), fragmentList);
        // 设置适配器
        viewPager.setAdapter(pagerAdapter);
        // 将TabLayout和ViewPaper关联
        tabLayout.setupWithViewPager(viewPager);

    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.record_iv_back:
                finish();
                break;
        }
    }
}
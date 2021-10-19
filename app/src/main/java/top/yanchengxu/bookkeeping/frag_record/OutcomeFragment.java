package top.yanchengxu.bookkeeping.frag_record;


import java.util.List;

import top.yanchengxu.bookkeeping.R;
import top.yanchengxu.bookkeeping.db.DBManager;
import top.yanchengxu.bookkeeping.db.TypeBean;

public class OutcomeFragment extends BaseRecordFragment {

    @Override
    public void loadDataToGV() {
        super.loadDataToGV();
        // 获取数据库中的数据源
        List<TypeBean> outList = DBManager.getTypeList(0);
        typeList.addAll(outList);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.ic_qita_fs);

    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(0);
        DBManager.insertItemToAccounttb(accountBean);
    }


}
package top.yanchengxu.bookkeeping;

import android.app.Application;

import top.yanchengxu.bookkeeping.db.DBManager;

/**
 * 表示全局应用的类
 */
public class UniteApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDB(getApplicationContext());

    }


}

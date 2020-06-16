package com.xk.mall.dao;

import android.content.Context;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.orhanobut.logger.Logger;
import com.xk.mall.gen.DaoMaster;
import com.xk.mall.gen.DaoSession;
import com.xk.mall.gen.UserBeanDao;

import org.greenrobot.greendao.database.Database;

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    public static final String DB_NAME = "xk.db";

    public MySQLiteOpenHelper(Context context){
        super(context,DB_NAME,null);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        //数据库升级操作，待测试
        if (oldVersion < newVersion) {
            Logger.i("version", oldVersion + "---先前和更新之后的版本---" + newVersion);
            MigrationHelper.migrate(db, UserBeanDao.class);
            //更改过的实体类(新增的不用加)   更新UserDao文件 可以添加多个  XXDao.class 文件
//             MigrationHelper.getInstance().migrate(db, UserDao.class,XXDao.class);
        }
    }

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,
                    DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }


}
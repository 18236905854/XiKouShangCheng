package com.xk.mall.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.xk.mall.model.entity.AddressServerBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ADDRESS_SERVER_BEAN".
*/
public class AddressServerBeanDao extends AbstractDao<AddressServerBean, Void> {

    public static final String TABLENAME = "ADDRESS_SERVER_BEAN";

    /**
     * Properties of entity AddressServerBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property AreaId = new Property(0, int.class, "areaId", false, "AREA_ID");
        public final static Property ParentId = new Property(1, int.class, "parentId", false, "PARENT_ID");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Level = new Property(3, int.class, "level", false, "LEVEL");
    }


    public AddressServerBeanDao(DaoConfig config) {
        super(config);
    }
    
    public AddressServerBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ADDRESS_SERVER_BEAN\" (" + //
                "\"AREA_ID\" INTEGER NOT NULL UNIQUE ," + // 0: areaId
                "\"PARENT_ID\" INTEGER NOT NULL ," + // 1: parentId
                "\"NAME\" TEXT," + // 2: name
                "\"LEVEL\" INTEGER NOT NULL );"); // 3: level
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ADDRESS_SERVER_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AddressServerBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getAreaId());
        stmt.bindLong(2, entity.getParentId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
        stmt.bindLong(4, entity.getLevel());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AddressServerBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getAreaId());
        stmt.bindLong(2, entity.getParentId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
        stmt.bindLong(4, entity.getLevel());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public AddressServerBean readEntity(Cursor cursor, int offset) {
        AddressServerBean entity = new AddressServerBean( //
            cursor.getInt(offset + 0), // areaId
            cursor.getInt(offset + 1), // parentId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.getInt(offset + 3) // level
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AddressServerBean entity, int offset) {
        entity.setAreaId(cursor.getInt(offset + 0));
        entity.setParentId(cursor.getInt(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLevel(cursor.getInt(offset + 3));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(AddressServerBean entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(AddressServerBean entity) {
        return null;
    }

    @Override
    public boolean hasKey(AddressServerBean entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}

package ultis.DBHelper.repository;

import ultis.DBHelper.bound.EntityMapper;
import ultis.DBHelper.convert.Convert;
import ultis.DBHelper.executor.QueryExecutor;
import ultis.DBHelper.model.Model;
import ultis.DBHelper.query.QueryHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Repository<T> extends Model implements EntityMapper<T> {
    protected String table;
    // define cac cols cho UPDATE INSERT DELETE
    private ArrayList<String> fillable;
    // de ho giup vt cau truy van
    protected QueryHelper queryHelper;
    // de thuc hien cau truy van
    protected QueryExecutor<T> queryExecutor;

    public void init() {
        this.table = tableName();
        this.fillable = getFillable();
        queryHelper = new QueryHelper("", table);
        queryExecutor = new QueryExecutor<>();
    }
    @Override
    // dc implement tu entity mapper, interface giup 1 entityDao co the dinh nghia cach map dulieu vao object
    public T mapper(ResultSet rs) throws SQLException {
        return null;
    }
// save de luu
    public T save(T data) {
        // vt ham covert entity to hashmap de chuyen doi tu object sang hashmap 
        // trong cau lenh insert se chuyen doi hashmap co dang (col,col2,col3) Values (valu1,value2,value3) 
        // dung trong vc thuc hien cau truy van thay vi dung ps.set 
        HashMap<String, Object> objData = Convert.convertEntityToHashMap(data, fillable);
        String sql = queryHelper.insert(objData).build();
        if (queryExecutor.updateQuery(sql) > 0) {
            return data;
        }
        return null;
    }

    public int update(T updatedData, String... updateCondition) {
        HashMap<String, Object> objData = Convert.convertEntityToHashMap(updatedData, fillable);
        queryHelper.update(objData).where();
        for (String cond : updateCondition) {
            queryHelper.condition(cond);
        }
        return queryExecutor.updateQuery(queryHelper.endCondition().build());
    }


}

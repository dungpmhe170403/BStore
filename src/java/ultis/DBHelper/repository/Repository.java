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
    protected QueryHelper queryHelper;
    protected QueryExecutor<T> queryExecutor;

    public void init() {
        this.table = tableName();
        this.fillable = getFillable();
        queryHelper = new QueryHelper("", table);
        queryExecutor = new QueryExecutor<>();
    }
    @Override
    public T mapper(ResultSet rs) throws SQLException {
        return null;
    }

    public T save(T data) {
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

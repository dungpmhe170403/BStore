package ultis.DBHelper.bound;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityMapper<T> {
    T mapper(ResultSet rs) throws SQLException;
}

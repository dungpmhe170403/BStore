package ultis.DBHelper.query;

import ultis.DBHelper.bound.SortOrder;

import java.util.*;

import static ultis.DBHelper.convert.Convert.convertKeyValueToColumnAndValues;
import static ultis.DBHelper.convert.Convert.keysValuesJoin;


public class QueryHelper {
    public StringBuilder queryBuilder;
    private String table;
    private boolean isFirstOrderBy;
    private boolean isFirstGroupBy;
    private boolean orderByIsSet;
    private boolean isCountQuery;
    private boolean recordLimited;
    private int limitedRecord;
    private List<String> joinClauses;
    private int offsetCount;
    private int limitCount;
    private Set<String> multiCondition;

    public QueryHelper(String initialQuery, String table) {
        queryBuilder = new StringBuilder(initialQuery);
        this.table = table;
        isFirstOrderBy = true;
        isFirstGroupBy = true;
        orderByIsSet = false;
        recordLimited = false;
        offsetCount = 0;
        limitCount = 0;
        limitedRecord = 0;
        joinClauses = new ArrayList<>();
        multiCondition = new HashSet<>();
    }

    public QueryHelper copy() {
        return new QueryHelper(queryBuilder.toString(), table);
    }

    private void destroy() {
        isFirstOrderBy = true;
        isFirstGroupBy = true;
        orderByIsSet = false;
        recordLimited = false;
        offsetCount = 0;
        limitCount = 0;
        limitedRecord = 0;
        joinClauses.clear();
        multiCondition.clear();
        queryBuilder = new StringBuilder();
    }

    public QueryHelper insert(HashMap<String, Object> keysValues) {
        HashMap<String, String> columnAndValues = convertKeyValueToColumnAndValues(keysValues);
        queryBuilder.append("INSERT INTO " + "[").append(table).append("]").append("(").append(columnAndValues.get("columns")).append(")").append(" VALUES(").append(columnAndValues.get("values")).append(") ");
        return this;
    }

    public QueryHelper update(HashMap<String, Object> keysValues) {
        ArrayList<String> columnsAndValues = keysValuesJoin(keysValues, "=");
        queryBuilder.append(String.format("UPDATE [%s] SET %s ", table, String.join(", ", columnsAndValues)));
        System.out.println(queryBuilder.toString());
        return this;
    }

    public QueryHelper select(String... columns) {
        queryBuilder.append("SELECT ").append(String.join(", ", Arrays.asList(columns))).append(String.format(" FROM [%s]", table));
        return this;
    }

    public QueryHelper limit(int lim) {
        recordLimited = true;
        limitedRecord = lim;
        return this;
    }

    public QueryHelper orderBy(String col, SortOrder sortOrder) {
        if (isFirstOrderBy) {
            queryBuilder.append(" ORDER BY ");
            isFirstOrderBy = false;
        } else {
            queryBuilder.append(", ");
        }
        orderByIsSet = true;
        queryBuilder.append(col).append(" ").append(sortOrder.toString());
        System.out.println(queryBuilder.toString() + "collect");
        return this;
    }

    public QueryHelper groupBy(String... cols) {
        if (isFirstGroupBy) {
            queryBuilder.append(" GROUP BY ");
            isFirstGroupBy = false;
        } else {
            queryBuilder.append(", ");
        }
        queryBuilder.append(String.join(", ", Arrays.asList(cols)));
        return this;
    }

    public QueryHelper innerJoin(String table, String... conditions) {
        StringBuilder joinClause = new StringBuilder(" INNER JOIN ")
                .append(table)
                .append(" ON ");
        joinClause.append(String.join(" AND ", Arrays.asList(conditions)));
        joinClauses.add(joinClause.toString());
        return this;
    }

    public QueryHelper where() {
        queryBuilder.append(" WHERE ");
        return this;
    }

    public String toString() {
        return queryBuilder.toString();
    }

    public QueryHelper condition(String condition) {
        multiCondition.add(condition);
        return this;
    }

    public QueryHelper endCondition() {
        queryBuilder.append(String.join(" AND ", multiCondition));
        return this;
    }

    public void noWhere() {
        queryBuilder.replace(queryBuilder.indexOf("WHERE"), queryBuilder.indexOf("WHERE") + "WHERE".length(), "");
    }

    public QueryHelper count() {
        isCountQuery = true;
        return this;
    }

    public QueryHelper offsetCount(int offset) {
        this.offsetCount = offset;
        return this;
    }

    public QueryHelper limitCount(int limit) {
        this.limitCount = limit;
        return this;
    }

    public QueryHelper joinned() {
        if (joinClauses.size() > 0) {
            queryBuilder.append(String.join(" ", joinClauses));
        }
        return this;
    }

    public String build() {
        if (joinClauses.size() > 0) {
            queryBuilder.append(String.join(" ", joinClauses));
        }
        if (isCountQuery) {
            queryBuilder.insert(0, "SELECT COUNT(*) FROM (");
            queryBuilder.append(") AS subquery");
        }
        if (offsetCount >= 0 && limitCount > 0) {
            if (!orderByIsSet) {
                queryBuilder.append("ORDER BY (SELECT NULL) ");
            }
            queryBuilder.append(String.format(" OFFSET %d ROWS FETCH NEXT %d ROWS ONLY", offsetCount, limitCount));
        }
        if (recordLimited) {
            queryBuilder.insert(0, "SET ROWCOUNT " + limitedRecord + "\n");
            queryBuilder.append("\n" + "SET ROWCOUNT ").append(limitedRecord);
        }
        String sql = queryBuilder.toString();
        destroy();
        return sql;
    }
}

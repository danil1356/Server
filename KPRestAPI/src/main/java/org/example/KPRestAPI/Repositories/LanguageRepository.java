package org.example.KPRestAPI.Repositories;

import org.example.KPRestAPI.Entity.Language;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class LanguageRepository implements IRestRepository<Language>{
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\",\"name\" " +
            "FROM \"_language\" " +
            "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\",\"name\"" +
            "FROM \"_language\" " +
            "WHERE \"id\" = ?";

    private static String insertQuery = "INSERT INTO \"_language\"(\"name\") " +
            "VALUES (?) " +
            "RETURNING \"id\",\"name\"";

    private static String updateQuery = "UPDATE \"_language\" " +
            "SET \"name\" = ?" +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\",\"name\"";

    private static String deleteQuery = "DELETE FROM \"_language\" " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\",\"name\"";


    public LanguageRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Language[] select() {
        ArrayList<Language> values = new ArrayList<Language>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);

        while (rowSet.next()) {
            values.add(
                    new Language(
                            rowSet.getInt(1),
                            rowSet.getString(2)
                    ));
        }

        Language[] result = new Language[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Language select(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Language(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }

    @Override
    public Language insert(Language entity) {
        Object[] params = new Object[] {entity.getName()};
        int[] types = new int[] {Types.VARCHAR};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Language(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }

    @Override
    public Language update(Integer id, Language entity) {
        Object[] params = new Object[] { entity.getName(), id };
        int[] types = new int[] { Types.VARCHAR, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Language(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }

    @Override
    public Language delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Language(
                rowSet.getInt(1),
                rowSet.getString(2)
        );
    }
}

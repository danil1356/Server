package org.example.KPRestAPI.Repositories;

import org.example.KPRestAPI.Entity.AffixMorphemes;
import org.example.KPRestAPI.Entity.Allomorphes;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class AllomorphesRepository implements IRestRepository<Allomorphes>{
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"value\", \"id_affix\"" +
            "FROM \"allomorphes\" " +
            "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"value\", \"id_affix\"" +
            "FROM \"allomorphes\" " +
            "WHERE \"id\" = ?";

    private static String selectByIdAffixQuery = "SELECT \"id\", \"value\", \"id_affix\"" +
            "FROM \"allomorphes\" " +
            "WHERE \"id_affix\" = ?";

    private static String insertQuery = "INSERT INTO \"allomorphes\"(\"value\", \"id_affix\") " +
            "VALUES (?, ?) " +
            "RETURNING \"id\", \"value\", \"id_affix\"";

    private static String updateQuery = "UPDATE \"allomorphes\" " +
            "SET \"value\" = ?, \"id_affix\" = ?" +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"value\", \"id_affix\"";

    private static String deleteQuery = "DELETE FROM \"allomorphes\" " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"value\", \"id_affix\"";


    public AllomorphesRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Allomorphes[] select() {
        ArrayList<Allomorphes> values = new ArrayList<Allomorphes>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);

        while (rowSet.next()) {
            values.add(
                    new Allomorphes(
                            rowSet.getInt(1),
                            rowSet.getString(2),
                            rowSet.getInt(3)
                    ));
        }

        Allomorphes[] result = new Allomorphes[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Allomorphes select(Integer id) {
        Object[] params = new Object[] {id};
        int[] types = new int[]{Types.INTEGER};

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()){return null;}
        return new Allomorphes(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getInt(3)
        );
    }

    public Allomorphes[] selectByIdAffixQuery(Integer affixId) {
        ArrayList<Allomorphes> values = new ArrayList<Allomorphes>();
        Object[] params = new Object[] { affixId };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdAffixQuery, params, types);
        while (rowSet.next()) {
            values.add(new Allomorphes(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getInt(3)
            ));
        }
        Allomorphes[] result = new Allomorphes[values.size()];
        result = values.toArray(result);
        return result;
    }


    @Override
    public Allomorphes insert(Allomorphes entity) {
        Object[] params = new Object[] { entity.getValue(), entity.getIdAffix()};
        int[] types = new int[] { Types.VARCHAR, Types.INTEGER};

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Allomorphes(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getInt(3)
        );
    }

    @Override
    public Allomorphes update(Integer id, Allomorphes entity) {
        Object[] params = new Object[] { entity.getValue(), entity.getIdAffix(),id };
        int[] types = new int[] { Types.VARCHAR, Types.INTEGER, Types.INTEGER };

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Allomorphes(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getInt(3)
        );
    }

    @Override
    public Allomorphes delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Allomorphes(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getInt(3)
        );
    }
}

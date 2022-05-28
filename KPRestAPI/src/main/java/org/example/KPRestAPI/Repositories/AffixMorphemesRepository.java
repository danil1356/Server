package org.example.KPRestAPI.Repositories;

import org.example.KPRestAPI.Entity.AffixMorphemes;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class AffixMorphemesRepository implements IRestRepository<AffixMorphemes>{
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"id_language\", \"name\"" +
            "FROM \"affix_morphemes\" " +
            "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"id_language\", \"name\"" +
            "FROM \"affix_morphemes\" " +
            "WHERE \"id\" = ?";

    private static String selectByIdLanguageQuery = "SELECT \"id\", \"id_language\", \"name\" " +
            "FROM \"affix_morphemes\" " +
            "WHERE \"id_language\" = ?";

    private static String insertQuery = "INSERT INTO \"affix_morphemes\"(\"id_language\", \"name\") " +
            "VALUES (?, ?) " +
            "RETURNING \"id\", \"id_language\", \"name\"";

    private static String updateQuery = "UPDATE \"affix_morphemes\" " +
            "SET \"id_language\" = ?, \"name\" = ?" +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"id_language\", \"name\"";

    private static String deleteQuery = "DELETE FROM \"affix_morphemes\" " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"id_language\", \"name\"";


    public AffixMorphemesRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public AffixMorphemes[] select() {
        ArrayList<AffixMorphemes> values = new ArrayList<AffixMorphemes>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);

        while (rowSet.next()) {
            values.add(
                    new AffixMorphemes(
                            rowSet.getInt(1),
                            rowSet.getInt(2),
                            rowSet.getString(3)
                    ));
        }

        AffixMorphemes[] result = new AffixMorphemes[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public AffixMorphemes select(Integer id)
    {
        Object[] params = new Object[] {id};
        int[] types = new int[]{Types.INTEGER};

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()){return null;}
        return new AffixMorphemes(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getString(3)
        );
    }

    public AffixMorphemes[] selectByIdLanguage(Integer languageId) {
        ArrayList<AffixMorphemes> values = new ArrayList<AffixMorphemes>();
        Object[] params = new Object[] { languageId };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdLanguageQuery, params, types);
        while (rowSet.next()) {
            values.add(new AffixMorphemes(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getString(3)
            ));
        }
        AffixMorphemes[] result = new AffixMorphemes[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public AffixMorphemes insert(AffixMorphemes entity) {
        Object[] params = new Object[] { entity.getIdLanguage(), entity.getName()};
        int[] types = new int[] { Types.INTEGER, Types.VARCHAR};

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new AffixMorphemes(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getString(3)
        );
    }

    @Override
    public AffixMorphemes update(Integer id, AffixMorphemes entity) {
        Object[] params = new Object[] { entity.getIdLanguage(), entity.getName(),id };
        int[] types = new int[] { Types.INTEGER, Types.VARCHAR, Types.INTEGER };

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new AffixMorphemes(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getString(3)
        );
    }

    @Override
    public AffixMorphemes delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new AffixMorphemes(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getString(3)
        );
    }
}

package org.example.KPRestAPI.Repositories;

import org.example.KPRestAPI.Entity.MorphConcepts;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class MorphConceptsRepository {
    protected JdbcOperations jdbcOperations;

    private static String selectByIdRootMorphQuery = "SELECT \"id\",\"id_morph\", \"id_concepts\"" +
            "FROM \"morph_concepts\" " +
            "WHERE \"id_morph\" = ?";

    private static String selectByIdConceptQuery = "SELECT \"id\",\"id_morph\", \"id_concepts\"" +
            "FROM \"morph_concepts\" " +
            "WHERE \"id_concepts\" = ?";

    private static String insertQuery = "INSERT INTO \"morph_concepts\"(\"id_morph\", \"id_concepts\") " +
            "VALUES (?, ?) "+
            "RETURNING \"id\", \"id_morph\", \"id_concepts\"";

    private static String deleteQuery = "DELETE FROM \"morph_concepts\" " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"id_morph\", \"id_concepts\"";

    private static String updateQuery = "UPDATE \"morph_concepts\" " +
            "SET \"id_morph\" = ?, \"id_concepts\" = ?" +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"id_morph\", \"id_concepts\"";

    public MorphConceptsRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public MorphConcepts[] selectByIdRootMorph(Integer languageId) {
        ArrayList<MorphConcepts> values = new ArrayList<MorphConcepts>();
        Object[] params = new Object[] { languageId };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdRootMorphQuery, params, types);
        while (rowSet.next()) {
            values.add(new MorphConcepts(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3)
            ));
        }
        MorphConcepts[] result = new MorphConcepts[values.size()];
        result = values.toArray(result);
        return result;
    }

    public MorphConcepts[] selectByIdConcept(Integer languageId) {
        ArrayList<MorphConcepts> values = new ArrayList<MorphConcepts>();
        Object[] params = new Object[] { languageId };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdConceptQuery, params, types);
        while (rowSet.next()) {
            values.add(new MorphConcepts(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getInt(3)
            ));
        }
        MorphConcepts[] result = new MorphConcepts[values.size()];
        result = values.toArray(result);
        return result;
    }

    public MorphConcepts insert(MorphConcepts entity) {
        Object[] params = new Object[] { entity.getId_morph(), entity.getId_concepts()};
        int[] types = new int[] {Types.INTEGER, Types.INTEGER};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new MorphConcepts(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3)
        );
    }


    public MorphConcepts delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);

        if (!rowSet.next()) {
            return null;
        }
        return new MorphConcepts(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3)
        );
    }

    public MorphConcepts update(Integer id, MorphConcepts entity) {
        Object[] params = new Object[] { entity.getId_concepts(), entity.getId_morph(), id };
        int[] types = new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new MorphConcepts(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getInt(3)
        );
    }
}

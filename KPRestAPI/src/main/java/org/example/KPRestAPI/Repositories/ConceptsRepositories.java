package org.example.KPRestAPI.Repositories;

import org.example.KPRestAPI.Entity.Concepts;
import org.example.KPRestAPI.Entity.RootMorphemes;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class ConceptsRepositories implements IRestRepository<Concepts>{
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"name_eng\", \"name_ru\", \"giperonim\" " +
            "FROM \"concepts\" " +
            "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"name_eng\", \"name_ru\", \"giperonim\" " +
            "FROM \"concepts\" " +
            "WHERE \"id\" = ?";

    private static String insertQuery = "INSERT INTO \"concepts\"(\"name_eng\", \"name_ru\", \"giperonim\") " +
            "VALUES (?, ?, ?) " +
            "RETURNING \"id\", \"name_eng\", \"name_ru\", \"giperonim\"";

    private static String updateQuery = "UPDATE \"concepts\" " +
            "SET \"name_eng\" = ?, \"name_ru\" = ?, \"giperonim\" = ?" +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"name_eng\", \"name_ru\", \"giperonim\"";

    private static String deleteQuery = "DELETE FROM \"concepts\" " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"name_eng\", \"name_ru\", \"giperonim\"";

    private static String selectByIdRootQuery ="SELECT \"concepts\".\"id\", \"concepts\".\"name_eng\", \"concepts\".\"name_ru\", \"concepts\".\"giperonim\" " +
            "FROM \"concepts\" " +
            "JOIN \"morph_concepts\" ON \"morph_concepts\".\"id_concepts\" = \"concepts\".\"id\" "+
            "WHERE \"morph_concepts\".\"id_morph\" = ?";


    public ConceptsRepositories(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public Concepts[] selectByIdRoot(Integer Id) {
        ArrayList<Concepts> values = new ArrayList<Concepts>();
        Object[] params = new Object[] { Id };
        int[] types = new int[] { Types.INTEGER };

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdRootQuery, params, types);
        while (rowSet.next()) {
            values.add(new Concepts(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3),
                    rowSet.getString(4)
            ));
        }
        Concepts[] result = new Concepts[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Concepts[] select() {
        ArrayList<Concepts> values = new ArrayList<Concepts>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);

        while (rowSet.next()) {
            values.add(
                    new Concepts(
                            rowSet.getInt(1),
                            rowSet.getString(2),
                            rowSet.getString(3),
                            rowSet.getString(4)
                    ));
        }

        Concepts[] result = new Concepts[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public Concepts select(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);

        if (!rowSet.next()) {
            return null;
        }
        return new Concepts(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4)
        );
    }

    @Override
    public Concepts insert(Concepts entity) {
        Object[] params = new Object[] {entity.getNameEng(), entity.getNameRu(), entity.getGiperonim()};
        int[] types = new int[] {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Concepts(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4)
        );
    }

    @Override
    public Concepts update(Integer id, Concepts entity) {
        Object[] params = new Object[] { entity.getNameEng(), entity.getNameRu(), entity.getGiperonim(), id };
        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new Concepts(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4)
        );
    }

    @Override
    public Concepts delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);

        if (!rowSet.next()) {
            return null;
        }
        return new Concepts(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getString(4)
        );
    }
}

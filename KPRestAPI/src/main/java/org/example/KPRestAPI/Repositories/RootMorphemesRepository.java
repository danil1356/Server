package org.example.KPRestAPI.Repositories;

import org.example.KPRestAPI.Entity.AffixMorphemes;
import org.example.KPRestAPI.Entity.RootMorphemes;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class RootMorphemesRepository implements IRestRepository<RootMorphemes>{
    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"id\", \"id_language\", \"name\", \"value\" " +
            "FROM \"root_morphemes\" " +
            "ORDER BY \"id\"";

    private static String selectByIdQuery = "SELECT \"id\", \"id_language\", \"name\", \"value\" " +
            "FROM \"root_morphemes\" " +
            "WHERE \"id\" = ?";

    private static String selectByIdLanguageQuery = "SELECT \"id\", \"id_language\", \"name\", \"value\" " +
            "FROM \"root_morphemes\" " +
            "WHERE \"id_language\" = ?";

    ///*
    private static String selectByIdConceptQuery ="SELECT \"root_morphemes\".\"id\", \"root_morphemes\".\"id_language\", \"root_morphemes\".\"name\", \"root_morphemes\".\"value\" " +
            "FROM \"root_morphemes\" " +
            "JOIN \"morph_concepts\" ON \"morph_concepts\".\"id_morph\" = \"root_morphemes\".\"id\" "+
            "WHERE \"morph_concepts\".\"id_concepts\" = ?";
    ///*

    private static String insertQuery = "INSERT INTO \"root_morphemes\"(\"id_language\", \"name\", \"value\") " +
            "VALUES (?, ?, ?) " +
            "RETURNING \"id\", \"id_language\", \"name\", \"value\"";

    private static String updateQuery = "UPDATE \"root_morphemes\" " +
            "SET \"id_language\" = ?, \"name\" = ?, \"value\" = ?" +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"id_language\", \"name\", \"value\"";

    private static String deleteQuery = "DELETE FROM \"root_morphemes\" " +
            "WHERE \"id\" = ? " +
            "RETURNING \"id\", \"id_language\", \"name\", \"value\"";

    public RootMorphemesRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    ///*
    public RootMorphemes[] selectByIdConcept(Integer Id) {
        ArrayList<RootMorphemes> values = new ArrayList<RootMorphemes>();
        Object[] params = new Object[] { Id };
        int[] types = new int[] { Types.INTEGER };

        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdConceptQuery, params, types);
        while (rowSet.next()) {
            values.add(new RootMorphemes(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getString(3),
                    rowSet.getString(4)
            ));
        }
        RootMorphemes[] result = new RootMorphemes[values.size()];
        result = values.toArray(result);
        return result;
    }
    ///*

    @Override
    public RootMorphemes[] select() {
        ArrayList<RootMorphemes> values = new ArrayList<RootMorphemes>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);

        while (rowSet.next()) {
            values.add(
                    new RootMorphemes(
                            rowSet.getInt(1),
                            rowSet.getInt(2),
                            rowSet.getString(3),
                            rowSet.getString(4)
                    ));
        }

        RootMorphemes[] result = new RootMorphemes[values.size()];
        result = values.toArray(result);
        return result;
    }


    @Override
    public RootMorphemes select(Integer id)
    {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new RootMorphemes(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getString(3),
                rowSet.getString(4)
        );
    }

    public RootMorphemes[] selectByIdLanguage(Integer languageId) {
        ArrayList<RootMorphemes> values = new ArrayList<RootMorphemes>();
        Object[] params = new Object[] { languageId };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdLanguageQuery, params, types);
        while (rowSet.next()) {
            values.add(new RootMorphemes(
                    rowSet.getInt(1),
                    rowSet.getInt(2),
                    rowSet.getString(3),
                    rowSet.getString(4)
            ));
        }
        RootMorphemes[] result = new RootMorphemes[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public RootMorphemes insert(RootMorphemes entity) {
        Object[] params = new Object[] { entity.getIdLanguage(), entity.getName(), entity.getValue()};
        int[] types = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR};
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new RootMorphemes(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getString(3),
                rowSet.getString(4)
        );
    }

    @Override
    public RootMorphemes update(Integer id, RootMorphemes entity) {
        Object[] params = new Object[] { entity.getIdLanguage(), entity.getName(), entity.getValue(), id };
        int[] types = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new RootMorphemes(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getString(3),
                rowSet.getString(4)
        );
    }

    @Override
    public RootMorphemes delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new RootMorphemes(
                rowSet.getInt(1),
                rowSet.getInt(2),
                rowSet.getString(3),
                rowSet.getString(4)
        );
    }
}

package org.example.KPRestAPI.Entity;

public class MorphConcepts extends BaseEntity {
    private Integer id_morph;
    private Integer id_concepts;

    public MorphConcepts(Integer id, Integer id_morph, Integer id_concepts)
    {
        super(id);
        this.id_morph = id_morph;
        this.id_concepts = id_concepts;
    }

    public Integer getId_morph(){return id_morph;}
    public Integer getId_concepts(){return id_concepts;}

    public void setId_morph(Integer id_morph){this.id_morph = id_morph;}
    public void setId_concepts(Integer id_concepts){this.id_concepts = id_concepts;}
}

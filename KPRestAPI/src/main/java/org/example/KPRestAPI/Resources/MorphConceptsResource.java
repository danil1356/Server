package org.example.KPRestAPI.Resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.KPRestAPI.Entity.MorphConcepts;

public class MorphConceptsResource {
    private Integer id;
    private Integer id_morph;
    private Integer id_concepts;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RootMorphemesResource[] rootMorphemesResours;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ConceptsResource[] conceptsResources;

    public MorphConceptsResource(){}

    public MorphConceptsResource(MorphConcepts morphConcepts)
    {
        this.id = morphConcepts.getId();
        this.id_morph = morphConcepts.getId_morph();
        this.id_concepts = morphConcepts.getId_concepts();
    }

    public Integer getId(){return id;}
    public Integer getId_morph(){return id_morph;}
    public Integer getId_concepts(){return id_concepts;}

    public void setId(Integer id){this.id = id;}
    public void setId_morph(Integer id_morph){this.id_morph = id_morph;}
    public void setId_concepts(Integer id_concepts){this.id_concepts = id_concepts;}

    public RootMorphemesResource[] getRootMorphemesResours(){return rootMorphemesResours;}
    public ConceptsResource [] getConceptsResources(){return conceptsResources;}

    public void setRootMorphemesResours(RootMorphemesResource[] rootMorphemesResours){this.rootMorphemesResours = rootMorphemesResours;}
    public void setConceptsResources(ConceptsResource[] conceptsResources){this.conceptsResources = conceptsResources;}

    public MorphConcepts toEntity()
    {
        return new MorphConcepts(
                this.id,
                this.id_morph,
                this.id_concepts
        );
    }
}

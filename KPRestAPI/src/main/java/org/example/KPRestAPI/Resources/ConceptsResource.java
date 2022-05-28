package org.example.KPRestAPI.Resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.KPRestAPI.Entity.Concepts;

public class ConceptsResource  {
    private Integer id;
    private String name_eng;
    private String name_ru;
    private String giperonim;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MorphConceptsResource[] morphConceptsResources;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RootMorphemesResource[] rootMorphemesResources;/////*

    public ConceptsResource(){}

    public ConceptsResource(Concepts concepts)
    {
        this.id = concepts.getId();
        this.name_eng = concepts.getNameEng();
        this.name_ru = concepts.getNameRu();
        this.giperonim = concepts.getGiperonim();
    }

    public Integer getId(){return id;}
    public String getName_eng(){return name_eng;}
    public String getName_ru(){return name_ru;}
    public String getGiperonim(){return giperonim;}

    public void setId(Integer id){this.id = id;}
    public void setName_eng(String name_eng){this.name_eng = name_eng;}
    public void setName_ru(String name_ru){this.name_ru = name_ru;}
    public void setGiperonim(String giperonim){this.giperonim = giperonim;}

    public MorphConceptsResource[] getMorphConceptsResources(){return morphConceptsResources;}
    public void setMorphConceptsResources(MorphConceptsResource[] morphConceptsResources){this.morphConceptsResources = morphConceptsResources;}


    /////*
    public void setRootMorphemesResources(RootMorphemesResource[] resource){this.rootMorphemesResources = resource;}
    public RootMorphemesResource[] getRootMorphemesResources(){return rootMorphemesResources;}
    ////*



    public Concepts toEntity()
    {
        return new Concepts(
                this.id,
                this.name_eng,
                this.name_ru,
                this.giperonim
        );
    }
}

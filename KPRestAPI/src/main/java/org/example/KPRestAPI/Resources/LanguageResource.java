package org.example.KPRestAPI.Resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.KPRestAPI.Entity.Language;

public class LanguageResource {
    private Integer id;
    private String name;

    //в связи один ко многим это "много"
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private RootMorphemesResource[] rootMorphemesResours;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AffixMorphemesResource[] affixMorphemesResources;


    public LanguageResource(){};

    public LanguageResource (Language language)
    {
        this.id = language.getId();
        this.name = language.getName();
    }

    public Integer getId (){return id;}
    public String getName(){return  name;}

    public void setId(Integer id){this.id = id;}
    public void setName(String name){this.name = name;}

    public RootMorphemesResource[] getRootMorphemesResours(){return rootMorphemesResours;}
    public AffixMorphemesResource[] getAffixMorphemesResources(){return affixMorphemesResources;}

    public void setRootMorphemesResours(RootMorphemesResource[] rootMorphemesResours){ this.rootMorphemesResours = rootMorphemesResours;}
    public void setAffixMorphemesResources(AffixMorphemesResource[] affixMorphemesResources){this.affixMorphemesResources = affixMorphemesResources;}

    public Language toEntity()
    {
        return new Language(
                this.id,
                this.name
        );

    }
}

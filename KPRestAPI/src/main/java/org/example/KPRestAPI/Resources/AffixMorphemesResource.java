package org.example.KPRestAPI.Resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.KPRestAPI.Entity.AffixMorphemes;

public class AffixMorphemesResource extends BaseResource {
    private  Integer id;
    private Integer id_language;
    private String name;

    //в связи один ко многим это один
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LanguageResource languageResource;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AllomorphesResource[] allomorphesResources;


    public AffixMorphemesResource(){}

    public AffixMorphemesResource(AffixMorphemes affixMorphemes)
    {
        this.id = affixMorphemes.getId();
        this.id_language = affixMorphemes.getIdLanguage();
        this.name = affixMorphemes.getName();
    }

    public Integer getId(){return id;}
    public Integer getId_language(){return id_language;}
    public String getName(){return name;}

    public void setId(Integer id){this.id = id;}
    public void setId_language(Integer id_language){this.id_language = id_language;}
    public void setName (String name){ this.name = name;}

    public LanguageResource getLanguageResource(){return this.languageResource;}
    public AllomorphesResource[] getAllomorphesResources(){return allomorphesResources;}

    public void setLanguageResource (LanguageResource languageResource){this.languageResource = languageResource;}
    public void setAllomorphesResources(AllomorphesResource[] allomorphesResources){this.allomorphesResources = allomorphesResources;}

    public AffixMorphemes toEntity()
    {
        return new AffixMorphemes
                (
                        this.id,
                        this.id_language,
                        this.name
                );
    }
}

package org.example.KPRestAPI.Resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.KPRestAPI.Entity.RootMorphemes;

public class RootMorphemesResource extends BaseResource {
    private Integer id;
    private Integer id_language;
    private String name;
    private String value;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LanguageResource languageResource;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ConceptsResource[] conceptsResources;

    public RootMorphemesResource(){}

    public RootMorphemesResource(RootMorphemes rootMorphemes)
    {
        this.id = rootMorphemes.getId();
        this.id_language = rootMorphemes.getIdLanguage();
        this.name = rootMorphemes.getName();
        this.value = rootMorphemes.getValue();
    }

    public Integer getId() {return id;}
    public Integer getId_language() {return id_language;}
    public String getName(){return name;}
    public String getValue(){return value;}

    public void setId (Integer id){this.id = id;}
    public void setId_language(Integer id_language){this.id_language = id_language;}
    public void setName(String name){this.name = name;}
    public void setValue(String value){this.value = value;}


    public LanguageResource getLanguageResource(){return this.languageResource;}
    public void setLanguageResource(LanguageResource languageResource){this.languageResource = languageResource;}


    public void setConceptsResources(ConceptsResource[] resource){this.conceptsResources = resource;}
    public ConceptsResource[] getConceptsResources(){return conceptsResources;}


    public RootMorphemes toEntity()
    {
        return new RootMorphemes(
            this.id,
            this.id_language,
            this.name,
            this.value
    );
    }
}

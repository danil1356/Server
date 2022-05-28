package org.example.KPRestAPI.Entity;

public class AffixMorphemes extends BaseEntity{
    private Integer idLanguage;
    private String name;

    public AffixMorphemes(Integer id, Integer idLanguage,String name) {
        super(id);
        this.idLanguage = idLanguage;
        this.name = name;
    }

    public Integer getIdLanguage(){return idLanguage;}
    public String getName(){return name;}

    public void setIdLanguage(Integer idLanguage){this.idLanguage = idLanguage;}
    public void setName(String name){this.name = name;}
}
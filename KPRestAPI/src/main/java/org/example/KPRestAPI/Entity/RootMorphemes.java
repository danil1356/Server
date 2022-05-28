package org.example.KPRestAPI.Entity;

public class RootMorphemes extends BaseEntity {
    private Integer idLanguage;
    private String name;
    private String value;

    public RootMorphemes(Integer id, Integer idLanguage,String name,String value) {
        super(id);
        this.idLanguage = idLanguage;
        this.name = name;
        this.value = value;
    }

    public Integer getIdLanguage(){return idLanguage;}
    public String getName(){return name;}
    public String getValue(){return value;}

    public void setIdLanguage(Integer idLanguage){this.idLanguage = idLanguage;}
    public void setName(String name){this.name = name;}
    public void setValue(String value){this.value = value;}
}
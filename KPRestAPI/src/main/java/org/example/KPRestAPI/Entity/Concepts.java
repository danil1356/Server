package org.example.KPRestAPI.Entity;

public class Concepts extends BaseEntity{
    private String NameEng;
    private String NameRu;
    private String Giperonim;

    public Concepts(Integer id, String NameEng, String NameRu, String Giperonim) {
        super(id);
        this.NameEng = NameEng;
        this.NameRu = NameRu;
        this.Giperonim = Giperonim;
    }

    public String getNameEng (){return NameEng;}
    public String getNameRu(){return NameRu;}
    public String getGiperonim(){return Giperonim;}

    public void setNameEng (String nameEng){this.NameEng = nameEng;}
    public void setNameRu (String nameRu){this.NameRu = nameRu;}
    public void setGiperonim(String giperonim){this.Giperonim = giperonim;}
}

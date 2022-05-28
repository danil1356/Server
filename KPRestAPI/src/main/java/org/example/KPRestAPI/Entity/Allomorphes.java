package org.example.KPRestAPI.Entity;

public class Allomorphes extends BaseEntity{
    private String value;
    private Integer idAffix;

    public Allomorphes(Integer id, String value, Integer idAffix) {
        super(id);
        this.value = value;
        this.idAffix = idAffix;
    }

    public Integer getIdAffix(){return idAffix;}
    public String getValue(){return value;}

    public void setIdAffix(Integer idAffix){this.idAffix = idAffix;}
    public void setValue(String value){this.value = value;}
}

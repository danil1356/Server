package org.example.KPRestAPI.Resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.KPRestAPI.Entity.Allomorphes;

public class AllomorphesResource extends BaseResource {
    private Integer id;
    private String value;
    private Integer id_affix;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AffixMorphemesResource affixMorphemesResource;

    public AllomorphesResource(){}

    public AllomorphesResource(Allomorphes allomorphes)
    {
        this.id = allomorphes.getId();
        this.value = allomorphes.getValue();
        this.id_affix = allomorphes.getIdAffix();
    }

    public Integer getId(){return id;}
    public String getValue(){return value;}
    public Integer getId_affix(){return id_affix;}

    public void setId(Integer id){this.id = id;}
    public void setValue(String value){this.value = value;}
    public void setId_affix(Integer id_affix){this.id_affix = id_affix;}

    public AffixMorphemesResource getAffixMorphemesResource(){return this.affixMorphemesResource;}
    public void setAffixMorphemesResource(AffixMorphemesResource affixMorphemesResource){this.affixMorphemesResource = affixMorphemesResource;}

    public Allomorphes toEntity()
    {
        return new Allomorphes(
                this.id,
                this.value,
                this.id_affix
        );
    }
}

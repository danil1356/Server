package org.example.KPRestAPI.Controllers;


import org.example.KPRestAPI.Entity.Allomorphes;
import org.example.KPRestAPI.Repositories.AffixMorphemesRepository;
import org.example.KPRestAPI.Repositories.AllomorphesRepository;
import org.example.KPRestAPI.Resources.AffixMorphemesResource;
import org.example.KPRestAPI.Resources.AllomorphesResource;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/allomorphes")
public class AllomorphesController {

    private final AffixMorphemesRepository affixMorphemesRepository;
    private final AllomorphesRepository allomorphesRepository;

    public AllomorphesController(AffixMorphemesRepository affixMorphemesRepository, AllomorphesRepository allomorphesRepository) {
        this.affixMorphemesRepository = affixMorphemesRepository;
        this.allomorphesRepository = allomorphesRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    AllomorphesResource[] getAll(@RequestParam(required = false) Integer affixId,
                                 @RequestParam(required = false) Object expand) {
        Allomorphes[] entities = affixId == null ?
                allomorphesRepository.select() :
                allomorphesRepository.selectByIdAffixQuery(affixId);
        return Arrays.stream(entities)
                .map(entity -> {
                    AllomorphesResource resource = new AllomorphesResource(entity);
                    if (expand != null){
                        resource.setAffixMorphemesResource(new AffixMorphemesResource(
                                affixMorphemesRepository.select(entity.getIdAffix()))
                        );
                    }
                    return resource;
                })
                .toArray(AllomorphesResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    AllomorphesResource get(@PathVariable Integer id,
                        @RequestParam(required = false) Object expand) {
        Allomorphes entity = allomorphesRepository.select(id);
        if (entity == null) return null;
        AllomorphesResource resource = new AllomorphesResource(entity);
        if (expand != null){
            resource.setAffixMorphemesResource(
                    new AffixMorphemesResource(affixMorphemesRepository.select(entity.getIdAffix()))
            );
        }
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    AllomorphesResource post(@RequestBody AllomorphesResource resource) {
        Allomorphes entity = allomorphesRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new AllomorphesResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    AllomorphesResource put(@PathVariable Integer id,
                            @RequestBody AllomorphesResource resource) {
        Allomorphes entity = allomorphesRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new AllomorphesResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    AllomorphesResource delete(@PathVariable Integer id) {
        Allomorphes entity = allomorphesRepository.delete(id);
        if (entity == null) return null;
        AllomorphesResource resource = new AllomorphesResource(entity);
        return resource;
    }
}

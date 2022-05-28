package org.example.KPRestAPI.Controllers;

import org.example.KPRestAPI.Entity.AffixMorphemes;
import org.example.KPRestAPI.Repositories.AffixMorphemesRepository;
import org.example.KPRestAPI.Repositories.AllomorphesRepository;
import org.example.KPRestAPI.Repositories.LanguageRepository;
import org.example.KPRestAPI.Resources.AffixMorphemesResource;
import org.example.KPRestAPI.Resources.AllomorphesResource;
import org.example.KPRestAPI.Resources.LanguageResource;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/affix_morphemes")

public class AffixMorphController {
    private final AffixMorphemesRepository affixMorphemesRepository;
    private final LanguageRepository languageRepository;
    private final AllomorphesRepository allomorphesRepository;

    public AffixMorphController(AffixMorphemesRepository affixMorphemesRepository, LanguageRepository languageRepository, AllomorphesRepository allomorphesRepository) {
        this.affixMorphemesRepository = affixMorphemesRepository;
        this.languageRepository = languageRepository;
        this.allomorphesRepository = allomorphesRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    AffixMorphemesResource[] getAll(@RequestParam(required = false) Integer languageId,
                                    @RequestParam(required = false) Object expand){
        AffixMorphemes[] entities = languageId == null ?
                affixMorphemesRepository.select() :
                affixMorphemesRepository.selectByIdLanguage(languageId);
        return Arrays.stream(entities)
                .map(entity -> {
                    AffixMorphemesResource resource = new AffixMorphemesResource(entity);
                    if (expand != null){
                        resource.setLanguageResource(new LanguageResource(
                                languageRepository.select(entity.getIdLanguage()))
                        );

                        resource.setAllomorphesResources(
                                Arrays.stream(allomorphesRepository.selectByIdAffixQuery(entity.getId()))
                                        .map(e -> new AllomorphesResource(e))
                                        .toArray(AllomorphesResource[]::new));
                    }
                    return resource;
                })
                .toArray(AffixMorphemesResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    AffixMorphemesResource get(@PathVariable Integer id,
                               @RequestParam(required = false) Object expand) {
        AffixMorphemes entity = affixMorphemesRepository.select(id);
        if (entity == null) return null;
        AffixMorphemesResource resource = new AffixMorphemesResource(entity);
        if (expand != null){
            resource.setLanguageResource(
                    new LanguageResource(languageRepository.select(entity.getIdLanguage()))
            );
            resource.setAllomorphesResources(
                    Arrays.stream(allomorphesRepository.selectByIdAffixQuery(entity.getId()))
                            .map(e -> new AllomorphesResource(e))
                            .toArray(AllomorphesResource[]::new));
        }
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    AffixMorphemesResource post(@RequestBody AffixMorphemesResource resource) {
        AffixMorphemes entity = affixMorphemesRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new AffixMorphemesResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    AffixMorphemesResource put(@PathVariable Integer id,
                               @RequestBody AffixMorphemesResource resource) {
        AffixMorphemes entity = affixMorphemesRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new AffixMorphemesResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    AffixMorphemesResource delete(@PathVariable Integer id) {
        AffixMorphemes entity = affixMorphemesRepository.delete(id);
        if (entity == null) return null;
        AffixMorphemesResource resource = new AffixMorphemesResource(entity);
        return resource;
    }
}

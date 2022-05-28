package org.example.KPRestAPI.Controllers;

import org.example.KPRestAPI.Entity.Language;
import org.example.KPRestAPI.Repositories.AffixMorphemesRepository;
import org.example.KPRestAPI.Repositories.LanguageRepository;
import org.example.KPRestAPI.Repositories.RootMorphemesRepository;
import org.example.KPRestAPI.Resources.AffixMorphemesResource;
import org.example.KPRestAPI.Resources.LanguageResource;
import org.example.KPRestAPI.Resources.RootMorphemesResource;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/_language")

public class LanguageController {
    private final LanguageRepository languageRepository;
    private final AffixMorphemesRepository affixMorphemesRepository;
    private final RootMorphemesRepository rootMorphemesRepository;

    public LanguageController(LanguageRepository languageRepository, AffixMorphemesRepository affixMorphemesRepository, RootMorphemesRepository rootMorphemesRepository) {
        this.languageRepository = languageRepository;
        this.affixMorphemesRepository = affixMorphemesRepository;
        this.rootMorphemesRepository = rootMorphemesRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    LanguageResource[] getAll(@RequestParam(required = false) Object expand) {
        return Arrays.stream(languageRepository.select())
                .map(entity -> {
                    LanguageResource resource = new LanguageResource(entity);
                    if (expand != null)
                    {
                        resource.setAffixMorphemesResources(
                                Arrays.stream(affixMorphemesRepository.selectByIdLanguage(entity.getId()))
                                        .map(e -> new AffixMorphemesResource(e))
                                        .toArray(AffixMorphemesResource[]::new));

                        resource.setRootMorphemesResours(
                                Arrays.stream(rootMorphemesRepository.selectByIdLanguage(entity.getId()))
                                        .map(e -> new RootMorphemesResource(e))
                                        .toArray(RootMorphemesResource[]::new));
                    }
                    return resource;
                })
                .toArray(LanguageResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    LanguageResource get(@PathVariable Integer id,
                          @RequestParam(required = false) Object expand) {
        Language entity = languageRepository.select(id);
        if (entity == null) return null;
        LanguageResource resource = new LanguageResource(entity);
        if (expand != null){
            resource.setAffixMorphemesResources(
                    Arrays.stream(affixMorphemesRepository.selectByIdLanguage(entity.getId()))
                            .map(e -> new AffixMorphemesResource(e))
                            .toArray(AffixMorphemesResource[]::new));

            resource.setRootMorphemesResours(
                    Arrays.stream(rootMorphemesRepository.selectByIdLanguage(entity.getId()))
                        .map(e -> new RootMorphemesResource(e))
                        .toArray(RootMorphemesResource[]::new));
        }
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    LanguageResource post(@RequestBody LanguageResource resource) {
        Language entity = languageRepository.insert(resource.toEntity());
        if (entity == null) return null;
        return new LanguageResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    LanguageResource put(@PathVariable Integer id,
                          @RequestBody LanguageResource resource) {
        Language entity = languageRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        return new LanguageResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    LanguageResource delete(@PathVariable Integer id) {
        Language entity = languageRepository.delete(id);
        if (entity == null) return null;
        return new LanguageResource(entity);
    }
}

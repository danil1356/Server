package org.example.KPRestAPI.Controllers;


import org.example.KPRestAPI.Entity.RootMorphemes;
import org.example.KPRestAPI.Repositories.ConceptsRepositories;
import org.example.KPRestAPI.Repositories.LanguageRepository;
import org.example.KPRestAPI.Repositories.MorphConceptsRepository;
import org.example.KPRestAPI.Repositories.RootMorphemesRepository;
import org.example.KPRestAPI.Resources.*;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/root_morphemes")
public class RootMorphController {
    private final RootMorphemesRepository rootMorphemesRepository;
    private final LanguageRepository languageRepository;
    private final MorphConceptsRepository morphConceptsRepository;
    private final ConceptsRepositories conceptsRepositories;


    public RootMorphController(RootMorphemesRepository rootMorphemesRepository, LanguageRepository languageRepository, MorphConceptsRepository morphConceptsRepository, ConceptsRepositories conceptsRepositories) {
        this.rootMorphemesRepository = rootMorphemesRepository;
        this.languageRepository = languageRepository;
        this.morphConceptsRepository = morphConceptsRepository;
        this.conceptsRepositories = conceptsRepositories;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    RootMorphemesResource[] getAll(@RequestParam(required = false) Integer languageId,
                                   @RequestParam(required = false) Object expand){
        RootMorphemes[] entities = languageId == null ?
                rootMorphemesRepository.select() :
                rootMorphemesRepository.selectByIdLanguage(languageId);
        return Arrays.stream(entities)
                .map(entity -> {
                    RootMorphemesResource resource = new RootMorphemesResource(entity);
                    if (expand != null)
                    {
                        resource.setLanguageResource(new LanguageResource(
                                languageRepository.select(entity.getIdLanguage())));
                        resource.setConceptsResources(Arrays.stream(conceptsRepositories.selectByIdRoot(entity.getId()))
                                .map(e -> new ConceptsResource(e))
                                .toArray(ConceptsResource[]::new));

                    }
                    return resource;
                })
                .toArray(RootMorphemesResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    RootMorphemesResource get(@PathVariable Integer id,
                              @RequestParam(required = false) Object expand) {
        RootMorphemes entity = rootMorphemesRepository.select(id);
        if (entity == null) return null;
        RootMorphemesResource resource = new RootMorphemesResource(entity);
        if (expand != null)
        {
            resource.setLanguageResource(
                    new LanguageResource(languageRepository.select(entity.getIdLanguage())));
            resource.setConceptsResources(Arrays.stream(conceptsRepositories.selectByIdRoot(entity.getId()))
                    .map(e -> new ConceptsResource(e))
                    .toArray(ConceptsResource[]::new));
        }
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    RootMorphemesResource post(@RequestBody RootMorphemesResource resource) {
        RootMorphemes entity = rootMorphemesRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new RootMorphemesResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    RootMorphemesResource put(@PathVariable Integer id,
                              @RequestBody RootMorphemesResource resource) {
        RootMorphemes entity = rootMorphemesRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new RootMorphemesResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    RootMorphemesResource delete(@PathVariable Integer id) {
        RootMorphemes entity = rootMorphemesRepository.delete(id);
        if (entity == null) return null;
        RootMorphemesResource resource = new RootMorphemesResource(entity);
        return resource;
    }
}

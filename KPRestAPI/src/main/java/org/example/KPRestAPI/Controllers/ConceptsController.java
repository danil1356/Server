package org.example.KPRestAPI.Controllers;


import org.example.KPRestAPI.Entity.Concepts;
import org.example.KPRestAPI.Entity.MorphConcepts;
import org.example.KPRestAPI.Entity.RootMorphemes;
import org.example.KPRestAPI.Repositories.ConceptsRepositories;
import org.example.KPRestAPI.Repositories.MorphConceptsRepository;
import org.example.KPRestAPI.Repositories.RootMorphemesRepository;
import org.example.KPRestAPI.Resources.*;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/concepts")
public class ConceptsController {
    private final ConceptsRepositories conceptsRepositories;
    private final MorphConceptsRepository morphConceptsRepository;
    private final RootMorphemesRepository rootMorphemesRepository; ////*

    public ConceptsController(ConceptsRepositories conceptsRepositories, MorphConceptsRepository morphConceptsRepository, RootMorphemesRepository rootMorphemesRepository) {
        this.conceptsRepositories = conceptsRepositories;
        this.morphConceptsRepository = morphConceptsRepository;
        this.rootMorphemesRepository = rootMorphemesRepository; /////*
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    ConceptsResource[] getAll(@RequestParam(required = false) Object expand) {
        return Arrays.stream(conceptsRepositories.select())
                .map(entity -> {
                    ConceptsResource resource = new ConceptsResource(entity);
                    if (expand != null)
                    {
                        resource.setRootMorphemesResources(Arrays.stream(rootMorphemesRepository.selectByIdConcept(entity.getId()))
                                .map(e -> new RootMorphemesResource(e))
                                .toArray(RootMorphemesResource[]::new));
                    }
                    return resource;
                })
                .toArray(ConceptsResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ConceptsResource get(@PathVariable Integer id,
                         @RequestParam(required = false) Object expand) {
        Concepts entity = conceptsRepositories.select(id);
        if (entity == null) return null;
        ConceptsResource resource = new ConceptsResource(entity);
        if (expand != null){
            resource.setRootMorphemesResources(Arrays.stream(rootMorphemesRepository.selectByIdConcept(entity.getId()))
                    .map(e -> new RootMorphemesResource(e))
                    .toArray(RootMorphemesResource[]::new));
        }
        return resource;
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    ConceptsResource post(@RequestBody ConceptsResource resource) {
        Concepts entity = conceptsRepositories.insert(resource.toEntity());
        if (entity == null) return null;
        return new ConceptsResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ConceptsResource put(@PathVariable Integer id,
                         @RequestBody ConceptsResource resource) {
        Concepts entity = conceptsRepositories.update(id, resource.toEntity());
        if (entity == null) return null;
        return new ConceptsResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ConceptsResource delete(@PathVariable Integer id) {
        Concepts entity = conceptsRepositories.delete(id);
        if (entity == null) return null;
        return new ConceptsResource(entity);
    }

    //id - айди концепта которому мы добавляем корневую морфему
    @RequestMapping(value = "/postRoot/{id}", method = RequestMethod.POST)
    RootMorphemesResource postR(@PathVariable Integer id,
            @RequestBody RootMorphemesResource resource)
    {
        RootMorphemes entity = rootMorphemesRepository.insert(resource.toEntity());
        if (entity == null) return null;
        resource = new RootMorphemesResource(entity);

        MorphConcepts entity2 = new MorphConcepts(0, resource.getId(), id);
        morphConceptsRepository.insert(entity2);
        return resource;
    }

    @RequestMapping(value = "/deletRoot/{id}", method = RequestMethod.DELETE)
    MorphConcepts deleteR(@PathVariable Integer id) {
        MorphConcepts entity = morphConceptsRepository.delete(id);
        if (entity == null) return null;
        return new MorphConcepts(entity.getId(),entity.getId_morph(),entity.getId_concepts());
    }

    @RequestMapping(value = "/updateRoot/{id}", method = RequestMethod.PUT)
    MorphConcepts putR(@PathVariable Integer id,
                         @RequestBody MorphConcepts resource) {
        MorphConcepts entity = morphConceptsRepository.update(id, resource);
        if (entity == null) return null;
        return new MorphConcepts(entity.getId(),entity.getId_morph(),entity.getId_concepts());
    }


    @RequestMapping(value = "/postRoot", method = RequestMethod.POST)
    MorphConcepts postR2(@RequestBody MorphConcepts resource) {
        MorphConcepts entity = morphConceptsRepository.insert(resource);
        if (entity == null) return null;
        resource = new MorphConcepts(entity.getId(),entity.getId_morph(),entity.getId_concepts());
        return resource;
    }
}

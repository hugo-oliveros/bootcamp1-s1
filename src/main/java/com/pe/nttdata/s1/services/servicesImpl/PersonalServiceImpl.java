package com.pe.nttdata.s1.services.servicesImpl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.mongodb.client.model.Filters;
import com.pe.nttdata.s1.commons.GenericServiceImpl;
import com.pe.nttdata.s1.dao.PersonalRepository;
import com.pe.nttdata.s1.entity.Persona;
import com.pe.nttdata.s1.services.PersonalService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class PersonalServiceImpl extends GenericServiceImpl<Persona, String> implements PersonalService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PersonalRepository personalRepository;

    @Override
    public CrudRepository<Persona, String> getDao() {
        return personalRepository;
    }

    @Override
    public Mono<Persona> findById(String _id) throws JsonProcessingException, IllegalArgumentException {
        return Mono.just(new Gson().fromJson(Objects.requireNonNull(mongoTemplate
                .getCollection(mongoTemplate
                        .getCollectionName(Persona.class))
                .find(Filters
                        .eq("_id", new ObjectId(_id)))
                .first()).toJson(), Persona.class));
    }

    @Override
    public Void deleteById(String _id) {
        mongoTemplate
                .getCollection(mongoTemplate
                        .getCollectionName(Persona.class))
                .deleteOne(Filters
                        .eq("_id", new ObjectId(_id)));
        return null;
    }

}
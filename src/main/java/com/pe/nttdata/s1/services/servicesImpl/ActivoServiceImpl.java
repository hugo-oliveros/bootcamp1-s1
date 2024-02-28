package com.pe.nttdata.s1.services.servicesImpl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.pe.nttdata.s1.commons.GenericServiceImpl;
import com.pe.nttdata.s1.dao.ActivoRepository;
import com.pe.nttdata.s1.entity.Activo;
import com.pe.nttdata.s1.services.ActivoService;
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
public class ActivoServiceImpl extends GenericServiceImpl<Activo, String> implements ActivoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ActivoRepository activoRepository;

    @Override
    public CrudRepository<Activo, String> getDao() {
        return activoRepository;
    }

    @Override
    public Mono<Activo> findById(String _id) throws JsonProcessingException, IllegalArgumentException {
        return Mono.just(new Gson().fromJson(Objects.requireNonNull(mongoTemplate
                .getCollection(mongoTemplate
                        .getCollectionName(Activo.class))
                .find(Filters
                        .eq("_id", new ObjectId(_id)))
                .first()).toJson(), Activo.class));
    }

    @Override
    public Void update(Activo activo) {
        UpdateResult updateResult = mongoTemplate.getCollection(mongoTemplate.getCollectionName(Activo.class))
                .updateOne(Filters.eq("_id", new ObjectId(activo.get_id().toHexString())),
                        Updates.set("tarjeta.montoConsumed", activo.getTarjeta().getMontoConsumed().toString()));

        System.out.println( updateResult.getModifiedCount() );
        System.out.println( activo.getTarjeta().getMontoConsumed().toString() );

        return null;
    }

    @Override
    public Void deleteById(String _id) {
        mongoTemplate
                .getCollection(mongoTemplate
                        .getCollectionName(Activo.class))
                .deleteOne(Filters
                        .eq("_id", new ObjectId(_id)));
        return null;
    }

}
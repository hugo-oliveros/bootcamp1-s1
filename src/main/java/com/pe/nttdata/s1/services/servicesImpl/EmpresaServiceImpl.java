package com.pe.nttdata.s1.services.servicesImpl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.mongodb.client.model.Filters;
import com.pe.nttdata.s1.commons.GenericServiceImpl;
import com.pe.nttdata.s1.dao.EmpresaRepository;
import com.pe.nttdata.s1.entity.Empresa;
import com.pe.nttdata.s1.services.EmpresaService;
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
public class EmpresaServiceImpl extends GenericServiceImpl<Empresa, String> implements EmpresaService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public CrudRepository<Empresa, String> getDao() {
        return empresaRepository;
    }

    @Override
    public Mono<Empresa> findById(String _id) throws JsonProcessingException, IllegalArgumentException {
        return Mono.just(new Gson().fromJson(Objects.requireNonNull(mongoTemplate
                .getCollection(mongoTemplate
                        .getCollectionName(Empresa.class))
                .find(Filters
                        .eq("_id", new ObjectId(_id)))
                .first()).toJson(), Empresa.class));
    }

    @Override
    public Void deleteById(String _id) {
         mongoTemplate
                .getCollection(mongoTemplate
                        .getCollectionName(Empresa.class))
                .deleteOne(Filters
                        .eq("_id", new ObjectId(_id))).getDeletedCount();
        return null;
    }

}
package com.pe.nttdata.primer.services.services;

import com.pe.nttdata.primer.entity.Activo;
import com.pe.nttdata.primer.entity.Pasivo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@Slf4j
@AllArgsConstructor
public class BancoService {

    @Autowired
    private PasivoService pasivoService;

    @Autowired
    private ActivoService activoService;

    @Autowired
    private MessageSource messageSource;


    public Flux<Pasivo> findAllPasivo() {
        return pasivoService.findAll();
    }

    public Flux<Activo> findAllActivo() {
        return activoService.findAll();
    }

    public Mono<Pasivo> findPasivoById(String id){
        return pasivoService.findById(id);
    }

    public static Predicate<Pasivo> filterPasivo(String dni,String typeCta) {
        return (Pasivo req) -> (req.getPersona().getDni().equals(dni) && req.getType().equals(typeCta));
    }

    public static Predicate<Activo> filterActivo(String dni, String nombreEmpresa) {
        return (Activo req) -> (req.getPersona().getDni().equals(dni) ||
                req.getEmpresa().getNombre().equals(nombreEmpresa));
    }

    public Mono<Pasivo> saveWithVerification
            (Pasivo pasivo) {
        return Mono.from(findAllPasivo()
                .filter(filterPasivo(pasivo.getPersona().getDni(),pasivo.getType()))
                        .map(req->{
                            req = Pasivo.builder().build();
                            req.setDescrip("Client " +
                                    pasivo.getPersona().getDni() +
                                    " is exists type of cta.: " +
                                    pasivo.getType());
                            return req;
                        })).switchIfEmpty(pasivoService.save(pasivo));
    }

    public Mono<Activo> saveBusiness
            (Activo activo) {

        return Mono.from(findAllActivo()
                .filter(filterActivo(activo.getPersona().getDni(),activo.getEmpresa().getNombre()))
                .map(req->{
                    activo.getTarjeta().setMontoConsumed(req.getTarjeta()
                            .getMontoConsumed()
                            .add(activo.getTarjeta().getMontoConsumed()));
                    return req;
                }))
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .flatMap(optional -> {
                    if (optional.isPresent()) {
                        return activoService.update(optional.get().getId().toString(),activo);
                    }
                    return Mono.empty();
                }).switchIfEmpty(activoService.save(activo));
    }

    public Mono<Void> deleteById(String id) {
        return pasivoService.deleteById(id);
    }

}
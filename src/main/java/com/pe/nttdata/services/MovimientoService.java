package com.pe.nttdata.services;

import com.pe.nttdata.entity.Moviento;
import com.pe.nttdata.repository.MovimientoRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 *Implement {@link MovimientoService} <br/>
 *<b>Class</b>: {@link MovimientoService}<br/>
 *<b>Copyright</b>: &Copy; 2024 NTTDATA Per&uacute;. <br/>
 *<b>Company</b>: NTTDATA del Per&uacute;. <br/>
 *
 *@author NTTDATA Per&uacute;. (EVE) <br/>
 *<u>Developed by</u>: <br/>
 *<ul>
 *<li>Hugo Oliveros Monti</li>
 *</ul>
 *<u>Changes</u>:<br/>
 *<ul>
 *<li>feb. 29, 2024 (acronym) Creation class.</li>
 *</ul>
 *@version 1.0
 */
@Service
@Slf4j
public class MovimientoService {

    /**
     * <>p</>
     * .
     * ActivoRepository activoRepository
     **/
    @Autowired
    private MovimientoRepository movimientoRepository;

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @return return all elements from Mono passing
     * for reactivate Flux and return Status OK.
     *
     **/
    public Flux<Moviento> findAll() {
        return movimientoRepository.findAll();
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param id paramter id
     * @return return all elements from Mono passing
     * for reactivate Flux and return Status OK.
     *
     **/
    public Mono<Moviento> findById(final String id) {
        return movimientoRepository.findById(id);
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param moviento paramter activo entity
     * @return return all elements from Mono passing
     * for reactivate Flux and return Status OK.
     *
     **/
    public Mono<Moviento> save(final Moviento moviento) {
        return movimientoRepository.save(moviento);
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param id Parameter id
     * @param moviento Parameter activo
     * @return return all elements from Mono passing
     * for reactivate Flux and return Status OK.
     *
     **/
    public  Mono<Moviento> update(final String id, final Moviento moviento) {
        return movimientoRepository.findById(id)
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .flatMap(optional -> {
                    if (optional.isPresent()) {
                        moviento.setId(new ObjectId(id));
                        return movimientoRepository.save(moviento);
                    }
                    return Mono.empty();
                })
                .switchIfEmpty(
                        Mono.defer(() ->{
                            Moviento actError = Moviento.builder().build();
                            actError.setDescrip("The document was not found id document... "+id);
                            return Mono.just(actError);
                        }))
                .onErrorResume(error->{
                    Moviento movError = Moviento.builder().build();
                    movError.setDescrip("Error found...: "+error);
                    return Mono.just(movError);
                });
    }

    /**
     * <>p</>
     * .
     * Flux all elements from Mongo passing
     * for reactivate Flux
     * @param id Parameter id
     * @return return all elements from Mono Void
     *
     **/
    public Mono<Void> deleteById(final String id) {
        return movimientoRepository.deleteById(id);
    }

}

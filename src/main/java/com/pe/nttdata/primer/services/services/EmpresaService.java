package com.pe.nttdata.primer.services.services;

import com.pe.nttdata.primer.entity.Empresa;
import com.pe.nttdata.primer.repository.EmpresaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *Implement EmpresaServiceImpl. <br/>
 *<b>Class</b>: {@link EmpresaService}<br/>
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
public class EmpresaService {

  @Autowired
  private EmpresaRepository empresaRepository;

  public Flux<Empresa> findAll() {
    return empresaRepository.findAll();
  }

  public Mono<Empresa> findById(String id) {
    return empresaRepository.findById(id);
  }

  public Mono<Empresa> save(Empresa empresa) {
    return empresaRepository.save(empresa);
  }

  public Mono<Void> deleteById(String id) {
    return empresaRepository.deleteById(id);
  }

}
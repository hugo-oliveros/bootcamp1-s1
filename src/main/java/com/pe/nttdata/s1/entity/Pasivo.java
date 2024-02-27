package com.pe.nttdata.s1.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pe.nttdata.s1.domain.BaseDomain;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;


@Document(collection = "Pasivo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Pasivo extends BaseDomain implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @JsonSerialize(using = NoObjectIdSerializer.class)
    private ObjectId _id;
    private String type;
    private long maxMoviento;
    private String descrip;
    private Persona persona;

}

package com.ppaw.deliveryApp.Configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Audit implements Serializable {

    @Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Setter
    @JsonIgnore
    private Boolean isDeleted = false;

}
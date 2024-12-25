package com.ppaw.deliveryApp.Configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Audit implements Serializable {

//    @CreatedDate
//    @Column(name = "date_created", nullable = false, updatable = false)
//    @JsonIgnore
//    private LocalDateTime dateCreated;
//
//    @LastModifiedDate
//    @Column(name = "date_updated", nullable = false)
//    @JsonIgnore
//    private LocalDateTime dateUpdated;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    @Setter
    @JsonIgnore
    private Boolean isDeleted = false;

}
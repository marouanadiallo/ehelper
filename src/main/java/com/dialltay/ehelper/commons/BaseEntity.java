package com.dialltay.ehelper.commons;

import lombok.Getter;
import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

@Getter
@MappedSuperclass
public abstract class BaseEntity<ID> implements Persistable<ID> {

    @Transient
    private boolean isNew = true;

    /**
     * Entity state-detection strategy
     * Here we use the second strategy proposed by the Spring Data JPA documentation:
     * <a href="https://docs.spring.io/spring-data/jpa/reference/jpa/entity-persistence.html">
     */
    @Override
    public boolean isNew() {
        return isNew;
    }

    /**
     * Marks the entity as not new after a load from the database or before persisting it.
     * @see Persistable#isNew()
     */
    @PostLoad
    @PrePersist
    void markNotNew() {
        this.isNew = false;
    }

    public abstract ID getId();
}

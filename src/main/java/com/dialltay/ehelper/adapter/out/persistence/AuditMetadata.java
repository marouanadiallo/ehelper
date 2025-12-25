package com.dialltay.ehelper.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Embeddable
public record AuditMetadata (
        @CreatedBy
        @Column(name = "created_by", length = 150, nullable = false, updatable = false)
        String createdBy,

        @LastModifiedBy
        @Column(name = "modified_by", length = 150, nullable = false)
        String modifiedBy,

        @CreatedDate
        @Column(name = "created_at", nullable = false, updatable = false)
        Instant createdAt,

        @LastModifiedDate
        @Column(name = "modified_at", nullable = false)
        Instant modifiedAt
) {

    public static AuditMetadata defaultMetadata() {
        return new AuditMetadata(
                "system",
                "system",
                Instant.now(),
                Instant.now()
        );
    }
}

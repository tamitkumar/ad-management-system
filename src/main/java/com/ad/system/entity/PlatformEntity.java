package com.ad.system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "platforms", schema = "ad_system")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer platformId;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "platforms")
    private Set<AdEntity> ads;

    @OneToMany(mappedBy = "platform")
    private Set<AdStatusEntity> adStatuses;
}

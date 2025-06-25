package com.ad.system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "clients", schema = "ad_system")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @Column(nullable = false)
    private String name;
    @Column(name = "contact_info")
    private String contactInfo;
    @Version
    private Long version;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<CampaignEntity> campaigns;
}

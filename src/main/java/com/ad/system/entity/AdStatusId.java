package com.ad.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdStatusId implements Serializable {

    private String ad;
    private Integer platform;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdStatusId that)) return false;
        return Objects.equals(ad, that.ad) && Objects.equals(platform, that.platform);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ad, platform);
    }
}

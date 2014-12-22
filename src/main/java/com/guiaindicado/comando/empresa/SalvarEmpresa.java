package com.guiaindicado.comando.empresa;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.google.common.base.Objects;

public class SalvarEmpresa extends IndicarEmpresa {

    @NumberFormat(style = Style.NUMBER)
    private Double latitude;

    @NumberFormat(style = Style.NUMBER)
    private Double longitude;
    
    public SalvarEmpresa() {
        super();
        latitude = 0.0;
        longitude = 0.0;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = Objects.firstNonNull(latitude, 0.0);
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = Objects.firstNonNull(longitude, 0.0);
    }
}

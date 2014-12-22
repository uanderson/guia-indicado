package com.guiaindicado.comando.empresa;

import com.google.common.base.Objects;

public class AvaliarEmpresa {

    private String ip;
    private Integer empresa;
    private Integer nota;

    public AvaliarEmpresa() {
        ip = "";
        empresa = 0;
        nota = 0;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = Objects.firstNonNull(ip, "");
    }

    public Integer getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Integer empresa) {
        this.empresa = Objects.firstNonNull(empresa, 0);
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = Objects.firstNonNull(nota, 0);
    }
}

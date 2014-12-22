package com.guiaindicado.dominio.avaliacao;

public class SinteseAvaliacao {

    private long total;
    private double media;

    public SinteseAvaliacao(long total, double media) {
        this.total = total;
        this.media = media;
    }

    public int getTotal() {
        return Long.valueOf(total).intValue();
    }

    public int getMedia() {
        return (int) Math.ceil(media);
    }
}

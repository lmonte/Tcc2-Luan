package com.example.qr_readerexample.models;

/**
 * Created by LuanMonte on 03/04/16.
 */
public class EquipamentoBO {

    private Long id;
    private String name;
    private byte[] video;

    public EquipamentoBO() {
        super();
    }

    public EquipamentoBO(final Long id, final String name, final byte[] video) {
        this.id = id;
        this.name = name;
        this.video = video;
    }

    ///////////////////////////////
    /////////Get-Set//////////////
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public byte[] getVideo() {
        return video;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }
}

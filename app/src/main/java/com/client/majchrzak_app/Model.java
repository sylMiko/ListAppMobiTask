package com.client.majchrzak_app;

public class Model {
    private String id, zadanie, opis, status, nazwisko, opistester, statustester, statusdeweloper;

    public Model(String id, String zadanie, String opis, String status, String nazwisko, String opistester, String statustester, String statusdeweloper) {
        this.id = id;
        this.zadanie = zadanie;
        this.opis = opis;
        this.status = status;
        this.nazwisko = nazwisko;
        this.opistester = opistester;
        this.statustester = statustester;
        this.statusdeweloper = statusdeweloper;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZadanie() {
        return zadanie;
    }

    public void setZadanie(String zadanie) {
        this.zadanie = zadanie;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getStatustester() {
        return statustester;
    }
    public void setStatustester(String statustester) {
        this.statustester = statustester;
    }

    public String getStatusdeweloper() {
        return statusdeweloper;
    }

    public void setStatusdeweloper(String statusdeweloper) {
        this.statusdeweloper = statusdeweloper;
    }



    public String getOpistester() {
        return opistester;
    }

    public void setOpistester(String opistester) {
        this.opistester = opistester;
    }

    public Model() {

    }



}

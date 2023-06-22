package com.example.tabu.Model;

import java.util.List;

public class Tabu {
    private String kelime;
    private List<String> yasakli;

    public Tabu() {

    }

    public Tabu(String kelime, List<String> yasakli) {
        this.kelime = kelime;
        this.yasakli = yasakli;
    }

    public String getKelime() {
        return kelime;
    }

    public String getYasakli() {
        String text = "";

        for (String kelime : yasakli) {
            text = text + kelime + "\n";
        }
        return text;
    }
}

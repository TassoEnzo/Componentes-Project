package com.ifsp.frete.service;

import com.ifsp.frete.entity.Frete;
import org.springframework.stereotype.Service;

@Service
public class FreteService {

    public Frete calcularFrete(String cep) {

        String prefixo = cep.substring(0, 2);

        double valor;

        switch (prefixo) {
             // Sudeste
            case "01":
            case "02":
            case "03":
            case "04":
            case "10":
            case "11":
            case "12":
                valor = 15.0;
                break;

             // Sul
            case "80":
            case "81":
            case "82":
            case "90":
            case "91":
                valor = 20.0;
                break;

             // Centro-Oeste
            case "70":
            case "71":
            case "72":
            case "73":
            case "74":
                valor = 22.0;
                break;

             // Nordeste    
            case "40":
            case "41":
            case "42":
            case "43":
            case "44":
                valor = 25.0;
                break;

             // Norte e outros
            default:
                valor = 30.0;
        }

        return new Frete(cep, valor);
    }
}

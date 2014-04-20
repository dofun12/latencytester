/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lemano.pingchartgenerator.helpers;

import org.lemano.pingchartgenerator.model.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevim
 */
public class Converter {
//    public static Dominio toDominio(Dominio dd){
//        Color co = new Color(dd.getColor());
//        Dominio d = new Dominio(dd.getNome(), co,dd.getEndereco());
//        return d;
//    }
//    public static Dominio toDominioData(Dominio d){
//        Integer rgb = d.getColor().getRGB();
//        Dominio dd = new Dominio();
//        dd.setColor(rgb);
//        dd.setEndereco(d.getEndereco());
//        dd.setNome(d.getNome());
//        return dd;
//    }
//    public static List<Dominio> toDominio(List<Dominio> dds){
//        List<Dominio> ds = new ArrayList<Dominio>();
//        for(Dominio dd:dds){
//            ds.add(toDominio(dd));
//        }
//        return ds;
//    }
//    public static List<Dominio> toDominioData(List<Dominio> ds){
//        List<Dominio> dds = new ArrayList<Dominio>();
//        for(Dominio d:ds){
//            dds.add(Converter.toDominioData(d));
//        }
//        return dds;
//    }
//    
    public static Color hexToColor(String hex) {
        return new Color(
                Integer.valueOf(hex.substring(1, 3), 16),
                Integer.valueOf(hex.substring(3, 5), 16),
                Integer.valueOf(hex.substring(5, 7), 16));
    }
}

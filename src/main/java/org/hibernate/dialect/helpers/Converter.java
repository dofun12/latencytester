/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.hibernate.dialect.helpers;

import org.lemano.pingchartgenerator.model.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevim
 */
public class Converter {
    public static Dominio toDominio(DominioData dd){
        Color co = new Color(dd.getColor());
        Dominio d = new Dominio(dd.getNome(), co,dd.getEndereco());
        return d;
    }
    public static DominioData toDominioData(Dominio d){
        Integer rgb = d.getColor().getRGB();
        DominioData dd = new DominioData();
        dd.setColor(rgb);
        dd.setEndereco(d.getEndereco());
        dd.setNome(d.getNome());
        return dd;
    }
    public static List<Dominio> toDominio(List<DominioData> dds){
        List<Dominio> ds = new ArrayList<Dominio>();
        for(DominioData dd:dds){
            ds.add(toDominio(dd));
        }
        return ds;
    }
    public static List<DominioData> toDominioData(List<Dominio> ds){
        List<DominioData> dds = new ArrayList<DominioData>();
        for(Dominio d:ds){
            dds.add(Converter.toDominioData(d));
        }
        return dds;
    }
}

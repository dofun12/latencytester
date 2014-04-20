/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lemano.diapingchartgeneratorlpers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import org.lemano.pingchartgenerator.model.Cor;
import org.lemano.pingchartgenerator.model.Dominio;

/**
 *
 * @author Kevim
 */
public class JListPersonalizado extends AbstractListModel{
    public List<Dominio> dominios = new ArrayList<Dominio>();

    public JListPersonalizado(List<Dominio> ds) {
        dominios = new ArrayList<Dominio>();
        Dominio d = new Dominio();
        d.setId(-1);
        d.setNome("Novo dominio");
        d.setEndereco("");
        dominios.add(d);
        this.dominios.addAll(ds);
        
    }
    
    public Dominio getDominio(int index){
        return dominios.get(index);
    }
    
    public void add(Dominio d){
        dominios.add(d);
    }
    
    @Override
    public int getSize() {
        return dominios.size();
    }

    @Override
    public Object getElementAt(int index) {
        return dominios.get(index).getNome();
    }
    
}

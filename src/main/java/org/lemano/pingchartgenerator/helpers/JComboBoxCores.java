/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lemano.pingchartgenerator.helpers;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import org.lemano.pingchartgenerator.model.Cor;

/**
 *
 * @author Kevim
 */
public class JComboBoxCores extends AbstractListModel implements ComboBoxModel {
    private List<Cor> keysCores = new ArrayList<Cor>();
    private Cor corSelecionada;

    public JComboBoxCores(List<Cor> corSelecionada) {
        this.keysCores = corSelecionada;
    }
    
    @Override
    public int getSize() {
        return keysCores.size();
    }

    @Override
    public Object getElementAt(int index) {
        return keysCores.get(index);
    }
    
    @Override
    public void setSelectedItem(Object anItem) {
        this.corSelecionada = (Cor)anItem;
    }

    @Override
    public Object getSelectedItem() {
        return corSelecionada;
    }

}

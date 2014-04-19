/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lemano.pingchartgenerator.model;

import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import java.awt.Color;

/**
 *
 * @author Kevim
 */
public class Dominio{
    private ITrace2D trace;
    private String nome;
    private Color color;
    private String endereco;
    
    public Dominio(String n,Color c,String e) {
        trace = new Trace2DLtd(200);
        this.nome = n;
        this.color = c;
        this.endereco = e;
        
        trace.setColor(c);
        trace.setName(n);
    }

    public ITrace2D getTrace() {
        return trace;
    }

    public void setTrace(ITrace2D trace) {
        this.trace = trace;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    
}

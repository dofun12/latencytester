/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.lemano.pingchartgenerator.model;

import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.lemano.pingchartgenerator.helpers.Converter;

/**
 *
 * @author Kevim
 */
@Entity
@Table(name = "dominio")
public class Dominio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "endereco")
    private String endereco;
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    @ManyToOne
    private Cor colorId;
    
    @Transient
    private ITrace2D trace;

    public Dominio() {
        
    }

    public Dominio(Integer id, String nome, String endereco, Cor colorId) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.colorId = colorId;
    }

    
    
    public Dominio(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Cor getColorId() {
        return colorId;
    }

    public void setColorId(Cor colorId) {
        this.colorId = colorId;
    }

    public ITrace2D getTrace() {
        if(trace==null){
            this.trace = new Trace2DLtd(200);
            trace.setColor(Converter.hexToColor(colorId.getHex()));
            trace.setName(nome);
        }    
        return trace;
    }

    public void setTrace(ITrace2D trace) {
        this.trace = trace;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dominio)) {
            return false;
        }
        Dominio other = (Dominio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.lemano.pingchartgenerator.model.DominioData[ id=" + id + " ]";
    }
    
}

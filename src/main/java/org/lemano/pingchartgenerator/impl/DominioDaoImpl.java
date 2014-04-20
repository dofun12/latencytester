/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lemano.pingchartgenerator.impl;

import java.util.ArrayList;
import java.util.List;
import org.lemano.pingchartgenerator.dao.DominioDao;
import org.lemano.pingchartgenerator.db.DataBaseManager;
import org.lemano.pingchartgenerator.helpers.Converter;
import org.lemano.pingchartgenerator.model.Cor;
import org.lemano.pingchartgenerator.model.Dominio;

/**
 *
 * @author Kevim
 */
public class DominioDaoImpl extends DataBaseManager {

    public List<Dominio> getDominios() {
        List<Dominio> dominios = getEntityManager().createQuery("from Dominio d").getResultList();
        getEntityManager().close();
        return dominios;
    }

    public void save(List<Dominio> dominios) {
        for (Dominio d : dominios) {
            saveInternal(d);
        }
        getEntityManager().close();
    }

    public void save(Dominio d) {
        saveInternal(d);
        getEntityManager().close();
    }

    public void deletar(Dominio d){
        getEntityManager().getTransaction().begin();
        if (d!=null && d.getId() != null) {
            Dominio tmp = getEntityManager().find(Dominio.class, d.getId());
            if (tmp!=null) {
                getEntityManager().remove(tmp);
                getEntityManager().flush();
                getEntityManager().getTransaction().commit();
            }    
        }    
    }
    
    private void saveInternal(Dominio d) {
        getEntityManager().getTransaction().begin();
        if (d.getId() == null) {
            getEntityManager().persist(d);
            getEntityManager().flush();
            getEntityManager().getTransaction().commit();
        } else {
            Dominio dd = getEntityManager().find(Dominio.class, d.getId());
            if (dd == null) {
                dd = new Dominio();
            }
            dd.setColorId(getCor(d.getColorId().getId()));
            dd.setEndereco(d.getEndereco());
            dd.setNome(d.getNome());
            getEntityManager().merge(dd);
            getEntityManager().flush();
            getEntityManager().getTransaction().commit();
        }
    }

    public Cor getCor(Integer id) {
        return getEntityManager().find(Cor.class, id);
    }

    public List<Cor> getCores() {
        List<Cor> cores = getEntityManager().createQuery("from Cor c").getResultList();
        getEntityManager().close();
        return cores;
    }
}

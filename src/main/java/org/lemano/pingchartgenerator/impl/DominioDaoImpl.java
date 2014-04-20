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
import org.hibernate.dialect.helpers.Converter;
import org.lemano.pingchartgenerator.model.Dominio;
import org.lemano.pingchartgenerator.model.DominioData;

/**
 *
 * @author Kevim
 */
public class DominioDaoImpl extends DataBaseManager implements DominioDao{

    @Override
    public List<Dominio> getDominios() {
        List<DominioData> dominios = getEntityManager().createQuery("from DominioData d").getResultList();
        getEntityManager().close();
        return Converter.toDominio(dominios);
    }

    @Override
    public void save(List<Dominio> dominios) {
        for (Dominio d : dominios) {
            save(d);
        }
        getEntityManager().close();
    }

    @Override
    public void save(Dominio d) {
        getEntityManager().getTransaction().begin();
        DominioData dd = getEntityManager().find(DominioData.class, d.getNome());
        if (dd == null) {
            dd = new DominioData();
        }
        dd.setColor(d.getColor().getRGB());
        dd.setEndereco(d.getEndereco());
        dd.setNome(d.getNome());
        getEntityManager().merge(dd);
        getEntityManager().flush();
        getEntityManager().getTransaction().commit();
    }
}

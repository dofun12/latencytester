/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lemano.pingchartgenerator.dao;

import java.util.List;
import org.lemano.pingchartgenerator.model.Dominio;

/**
 *
 * @author Kevim
 */
public abstract interface DominioDao {
    public List<Dominio> getDominios();
    public void save(List<Dominio> dominios);
    public void save(Dominio dominio);
}

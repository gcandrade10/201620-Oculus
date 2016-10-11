/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.oculus.audiovisuales.persistence;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import co.edu.uniandes.oculus.audiovisuales.entities.PuntoDeAtencionEntity;
import java.util.List;
import javax.persistence.Query;
/**
 *
 * @author am.espinosa11
 */
@Stateless
public class PuntoDeAtencionPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(PuntoDeAtencionPersistence.class.getName());
    
   @PersistenceContext(unitName = "OculusPU")
    protected EntityManager em; 
   
    public PuntoDeAtencionEntity find(Long id) 
    {
        LOGGER.log(Level.INFO, "Consultando punto de atención con id={0}", id);
        return em.find(PuntoDeAtencionEntity.class, id);
    }
    
     public List<PuntoDeAtencionEntity> findAll() 
     {
        LOGGER.info("Consultando todos los puntos de atención");
        Query q = em.createQuery("select u from PuntoDeAtencionEntity u");
        return q.getResultList();
    }

    public PuntoDeAtencionEntity create(PuntoDeAtencionEntity entity) 
    {
        LOGGER.info("Creando un punto de atención nuevo");
        em.persist(entity);
        LOGGER.info("punto de atención creado");
        return entity;
    }
    
     public PuntoDeAtencionEntity update(PuntoDeAtencionEntity entity) 
     {
        LOGGER.log(Level.INFO, "Actualizando punto de atención con id={0}", entity.getId());
        return em.merge(entity);
    }

    public void delete(Long id) 
    {
        LOGGER.log(Level.INFO, "Borrando punto de atención con id={0}", id);
        PuntoDeAtencionEntity entity = em.find(PuntoDeAtencionEntity.class, id);
        em.remove(entity);
    }

}
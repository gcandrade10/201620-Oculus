/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.oculus.audiovisuales.test.logic;

import co.edu.uniandes.oculus.audiovisuales.api.IProfesorLogic;
import co.edu.uniandes.oculus.audiovisuales.api.IReservaLogic;
import co.edu.uniandes.oculus.audiovisuales.ejbs.*;
import co.edu.uniandes.oculus.audiovisuales.entities.ProfesorEntity;
import co.edu.uniandes.oculus.audiovisuales.entities.PuntoDeAtencionEntity;
import co.edu.uniandes.oculus.audiovisuales.entities.ReservaEntity;
import co.edu.uniandes.oculus.audiovisuales.persistence.ProfesorPersistence;
import co.edu.uniandes.oculus.audiovisuales.persistence.ReservaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author fa.lopez10
 */
@RunWith(Arquillian.class)
public class ReservaLogicTest {
    
    public ReservaLogicTest() {
    }
    ProfesorEntity fatherEntity;
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private IReservaLogic reservaLogic;
    
    @Inject
    private ReservaPersistence reservaPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<ReservaEntity> reservaData = new ArrayList<ReservaEntity>();
    private List<ProfesorEntity> profesorData = new ArrayList<ProfesorEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProfesorEntity.class.getPackage())
                .addPackage(ProfesorLogic.class.getPackage())
                .addPackage(IProfesorLogic.class.getPackage())
                .addPackage(ProfesorPersistence.class.getPackage())
                .addPackage(ReservaPersistence.class.getPackage())
                .addPackage(ReservaEntity.class.getPackage())
                .addPackage(ReservaLogic.class.getPackage())
                .addPackage(IReservaLogic.class.getPackage())
                .addPackage(ProfesorEntity.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
    public void setUp() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    
    private void clearData()
    {
        em.createQuery("DELETE FROM ReservaEntity").executeUpdate();
        em.createQuery("DELETE FROM ProfesorEntity").executeUpdate();
        
        em.createQuery("DELETE FROM EquipoEntity").executeUpdate();
        
        
    }
    
    private void insertData()
    {
        fatherEntity = factory.manufacturePojo(ProfesorEntity.class);
        fatherEntity.setId(1L);
        em.persist(fatherEntity);
        for (int i = 0; i < 3; i++) {
            ReservaEntity entity = factory.manufacturePojo(ReservaEntity.class);
            entity.setProfesor(fatherEntity);
            em.persist(entity);
            reservaData.add(entity);

            
        }
        fatherEntity.setReservas((ArrayList<ReservaEntity>) reservaData);
        em.persist(fatherEntity);
        
    }
    
    
    /**
     * Test of getReservas method, of class ReservaLogic.
     */
    @Test
    public void testGetReservasByIdProfesor() throws Exception {
        long idP = setUp2().getId();
        List <ReservaEntity> list = reservaLogic.getReservasByIdProfesor(idP);
        TypedQuery<ReservaEntity> q = em.createQuery("SELECT u FROM ReservaEntity u WHERE  u.profesor.id = :id",ReservaEntity.class);
        q = q.setParameter("id", idP);
        List<ReservaEntity> resers = q.getResultList();
        Assert.assertEquals(resers.size(), list.size());
        for (ReservaEntity  entidad :list)
        {
            boolean encontrado = false;
            for (ReservaEntity entidadAlmacenada : resers)
            {
                if(entidad.getId().equals(entidadAlmacenada.getId()))
                {
                    encontrado=true;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }

    /**
     * Test of getReserva method, of class ReservaLogic.
     */
    @Test
    public void testGetReserva() throws Exception {
        ReservaEntity entidad = reservaData.get(0);
        ReservaEntity resultado = reservaLogic.getReserva(entidad.getId());
        Assert.assertNotNull(resultado);
        Assert.assertEquals(entidad.getEstado(), resultado.getEstado());
        Assert.assertEquals(entidad.getFecha(), resultado.getFecha());
        Assert.assertEquals(entidad.getId(), resultado.getId());
    }

    /**
     * Test of createReserva method, of class ReservaLogic.
     */
    @Test
    public void testCreateReserva() throws Exception {
        ReservaEntity nuevaEntidad = factory.manufacturePojo(ReservaEntity.class);
        ReservaEntity resultado = reservaLogic.createReserva(fatherEntity.getId(),nuevaEntidad);
        Assert.assertNotNull(resultado);
        reservaData.add(resultado);
        ReservaEntity entidad = em.find(ReservaEntity.class, resultado.getId());
        Assert.assertEquals(nuevaEntidad.getFecha(),entidad.getFecha());
        Assert.assertEquals(nuevaEntidad.getEstado(),entidad.getEstado());
        Assert.assertEquals(nuevaEntidad.getId(),entidad.getId());
    }

    /**
     * Test of updateReserva method, of class ReservaLogic.
     */
    @Test
    public void testUpdateReserva() throws Exception {
        ReservaEntity entidad = reservaData.get(0);
        ReservaEntity entidadGenerada = factory.manufacturePojo(ReservaEntity.class);
        entidadGenerada.setId(entidad.getId());
        reservaLogic.updateReserva(fatherEntity.getId(),entidadGenerada);
        ReservaEntity r = em.find(ReservaEntity.class, entidad.getId());
        Assert.assertEquals(entidadGenerada.getEstado(), r.getEstado());
        Assert.assertEquals(entidadGenerada.getId(), r.getId());
        Assert.assertEquals(entidadGenerada.getFecha(), r.getFecha());
    }

    /**
     * Test of cancelarReserva method, of class ReservaLogic.
     */
    @Test
    public void testCancelarReserva() throws Exception {
        ReservaEntity entidad = reservaData.get(0);
        ReservaEntity entidadGenerada = factory.manufacturePojo(ReservaEntity.class);
        entidadGenerada.setId(entidad.getId());
        entidadGenerada.setEstado(ReservaEntity.RESERVA_CANCELADA);
        reservaLogic.cancelarReserva(entidad.getId());
        ReservaEntity r = em.find(ReservaEntity.class, entidad.getId());
        Assert.assertEquals(entidadGenerada.getEstado(), r.getEstado());
        Assert.assertEquals(entidadGenerada.getId(), r.getId());
    }

    /**
     * Test of deleteReserva method, of class ReservaLogic.
     */
    @Test
    public void testDeleteReserva() throws Exception {
        ReservaEntity entidad = reservaData.get(1);
        reservaLogic.deleteReserva(entidad.getId());
        reservaData.remove(entidad);
        ReservaEntity deleted = em.find(ReservaEntity.class, entidad.getId());
        Assert.assertNull(deleted);
    }

    private ProfesorEntity setUp2() 
    {
         PodamFactory f = new PodamFactoryImpl();
        ProfesorEntity nuevaEntidad = f.manufacturePojo(ProfesorEntity.class);
        try
        {
            utx.begin();
            em.joinTransaction();
            em.persist(nuevaEntidad);
            utx.commit();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        for (int i = 0; i < reservaData.size(); i++)
        {
            
            reservaData.get(i).setProfesor(nuevaEntidad);
            reservaPersistence.update(reservaData.get(i));
            Assert.assertEquals( reservaPersistence.find(reservaData.get(i).getId()).getProfesor().getId() , nuevaEntidad.getId());
            Assert.assertNotNull(reservaPersistence.find(reservaData.get(i).getId()).getProfesor());
        }
        return nuevaEntidad;
    }
    
    
    
}

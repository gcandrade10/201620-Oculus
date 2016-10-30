/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.oculus.audiovisuales.api;

import co.edu.uniandes.oculus.audiovisuales.entities.AdministradorEntity;
import co.edu.uniandes.oculus.audiovisuales.entities.EquipoEntity;
import java.util.List;

/**
 *
 * @author gc.andrade10
 */
public interface IEquipoLogic 
{
    public List <EquipoEntity> getEquipos();
    public EquipoEntity getEquipo(Long id);
    public EquipoEntity getEquipoByName(String name);
    public EquipoEntity createEquipo( EquipoEntity e);
    public EquipoEntity updateEquipo(EquipoEntity e);
    public void deleteEquipo(Long id);
    public List<EquipoEntity> getEquiposByIdPuntoDeAtencion(Long idPuntoDeAtencion);
}

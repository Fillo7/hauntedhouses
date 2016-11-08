/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.api.facade;

import java.util.List;
import cz.muni.fi.pa165.api.dto.AbilityDTO;

/**
 *
 * @author Kristyna Loukotova
 * @version 08.11.2016
 */
public interface AbilityFacade {
    List<AbilityDTO> findAll();
    AbilityDTO findById(Long id);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.JComponent;

/**
 *Interfaz para proveer componentes del view al controlador
 * @author Carlos
 */
public interface ComponentProvider {
  JComponent getComponentById(String id);  
}

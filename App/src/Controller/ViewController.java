/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
abstract public class ViewController {
    
    protected  String TAG="",TAG_ERROR="";
    
    public ViewController(){}
    
    protected void AlertDialog(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje,TAG,JOptionPane.INFORMATION_MESSAGE);
    }
    
    protected void AlertDialogError(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje,TAG_ERROR,JOptionPane.ERROR_MESSAGE);
    }
    
    protected int AlertDialogQuestion(String mensaje){                
        return JOptionPane.showConfirmDialog(null, mensaje, TAG, JOptionPane.YES_NO_OPTION);
    }
    
    //obtener los campos del formulario
    public abstract void bind();
    
    //resetear formulario
    public abstract void reset();
    
    
}

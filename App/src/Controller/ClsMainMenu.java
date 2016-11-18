/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Dao;
import View.frmBuscarCita;
import View.frmBuscarCliente;
import View.frmBuscarHistoriaClinica;
import View.frmBuscarMascota;
import View.frmGenerarConceptoComprobantePago;
import View.frmMantenerCliente;
import View.frmMantenerMascota;
import View.frmRegistrarCita;
import View.frmRegistrarHistorialClinico;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class ClsMainMenu {    
    private final Map<String,javax.swing.JFrame> mFormList= new HashMap();
    
    public ClsMainMenu(){
        //inicializar conexión con bd
        Dao.init();
        //agregar todos los formularios a la lista
        mFormList.put("Mantener Cliente",new frmMantenerCliente());
        mFormList.put("Buscar Cliente",new frmBuscarCliente());
        mFormList.put("Mantener Mascota",new frmMantenerMascota());
        mFormList.put("Buscar Mascota",new frmBuscarMascota());
        mFormList.put("Registrar Historial Clinico",new frmRegistrarHistorialClinico());
        mFormList.put("Buscar Historia Clínica",new frmBuscarHistoriaClinica());
        mFormList.put("Generar Concepto de boleta de venta",new frmGenerarConceptoComprobantePago());
        mFormList.put("Registrar Cita Médica",new frmRegistrarCita());
        mFormList.put("Buscar Cita Médica",new frmBuscarCita());
               
    }
    
    //invocar los formularios y mostrarlos
    public void invocar(String formName){
        mFormList.get(formName).setVisible(true);
    }
}

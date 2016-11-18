/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Cita;
import Model.Mascota;
import View.frmBuscarCita;
import com.toedter.calendar.JDateChooser;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class ClsBuscarCita extends ViewController{
    private frmBuscarCita mBuscarCitaView;
    
    private ArrayList<Vector<String>> mCitaList;
    //campos
    private JComboBox mCmbDoctor;
    private JTable mTbCitas;    
    private JComponent mLblTablaVacia;
    private JDateChooser mDtpFecDesde;  
    private JDateChooser mDtpFecHasta;
    private DefaultTableModel mTbModel;
    //tabla 
        //columnas
    private final String []mTableColumns =new String[]{"Doctor","Cliente DNI","Mascota","Fecha"};
    
    public ClsBuscarCita(frmBuscarCita buscarCitaView){
        mBuscarCitaView=buscarCitaView;
    }
    
    
    public void buscarCita(){
        try{
            //obtener componentes
            //bind();                    
        
            //crear un nuevo table model
            clearTable();
                        
            //obteniendo los datos de la bd
            
            mCitaList =   Cita.getCitaList((mCmbDoctor.getSelectedItem().equals("Todos")?-1:mCmbDoctor.getSelectedIndex()),
                                            mDtpFecDesde.getCalendar(),mDtpFecHasta.getCalendar());  
            
             mLblTablaVacia.setVisible(mCitaList.isEmpty());    
             for(Vector<String> cita: mCitaList)
                 mTbModel.addRow(cita);
                      
            
        }catch(NullPointerException  err){            
            System.out.println(TAG_ERROR+": "+err);
            AlertDialogError("Error con el servidor");   
        }catch(Exception err2){
            System.out.println(TAG_ERROR+": "+err2);
            AlertDialogError("Error con el servidor");   
        }
    }
    
    //autocargar el combo
    private void autoCargarComboDoctor(){        
        mCmbDoctor.removeAllItems();
        mCmbDoctor.addItem("Dr.Ramirez");
        mCmbDoctor.addItem("Dr.Torres");
        mCmbDoctor.addItem("Dr.Gomez");
        mCmbDoctor.addItem("Dr.Nano");
        mCmbDoctor.addItem("Todos");
    }
        
    //vaciar la tabla
    private void clearTable(){        
        mTbModel = new DefaultTableModel(null,mTableColumns);
        mTbCitas.setModel(mTbModel);
    }
    
    @Override
    public void bind() {
        mCmbDoctor= (JComboBox) mBuscarCitaView.getComponentById("cita_cmbparametrobusqueda");  
        mTbCitas= (JTable) mBuscarCitaView.getComponentById("cita_tb");  
        mLblTablaVacia=  mBuscarCitaView.getComponentById("cita_lbltablavacia");
        mDtpFecDesde=(JDateChooser) mBuscarCitaView.getComponentById("cita_fecdesde");  
        mDtpFecHasta= (JDateChooser) mBuscarCitaView.getComponentById("cita_fechasta");  
        
        mDtpFecDesde.setDate(new Date());
        mDtpFecHasta.setDate(new Date());
        autoCargarComboDoctor();
        
        mCmbDoctor.setSelectedItem("Todos");
    }

    @Override
    public void reset() {
        clearTable();
        mLblTablaVacia.setVisible(true);
    }
    
}

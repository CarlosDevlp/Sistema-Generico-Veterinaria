/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.HistoriaClinica;
import Model.Mascota;
import View.frmBuscarHistoriaClinica;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class ClsBuscarHistoriaClinica extends ViewController{
    
    private frmBuscarHistoriaClinica mBuscarHistoriaClinicaView;
    //private Callback mCallback;
    private ArrayList<HistoriaClinica> mHCList;
    //campos
    private JTextField mTxtBuscar;
    private JComboBox  mCmbParametroBusqueda;
    private JTable mTbHC;
    private JComponent mLblTablaVacia;
    private DefaultTableModel mTbModel;
    //tabla 
        //columnas
    private final String []mTableColumns =new String[]{"Numero HC","Nombre","Propietario DNI","Especie","Raza","Edad","Temperatura"};
    
    public ClsBuscarHistoriaClinica(frmBuscarHistoriaClinica buscarHistoriaClinicaView){
        mBuscarHistoriaClinicaView  = buscarHistoriaClinicaView ;
    }

    public void buscarHC(){
       try{
            //obtener componentes
             //bind();
        
            
        
            //crear un nuevo table model
            clearTable();
                        
            //obteniendo los datos de la bd
            ArrayList<Vector<String>> arr=HistoriaClinica.buscarHistoriaClinica(mCmbParametroBusqueda.getSelectedIndex(), mTxtBuscar.getText());
                                    
             for(Vector<String> row: arr)
                 mTbModel.addRow(row);
                      
            
        }catch(NullPointerException  err){            
            System.out.println(TAG_ERROR+": "+err);
            AlertDialogError("Error con el servidor");   
        }catch(Exception err2){
            System.out.println(TAG_ERROR+": "+err2);
            AlertDialogError("Error con el servidor");   
        } 
    }
    //vaciar la tabla
    private void clearTable(){        
        mTbModel = new DefaultTableModel(null,mTableColumns);
        mTbHC.setModel(mTbModel);
    }
    
    @Override
    public void bind() {
        mTxtBuscar =((JTextField)  mBuscarHistoriaClinicaView.getComponentById("hc_txtbuscar"));
        mCmbParametroBusqueda = ((JComboBox) mBuscarHistoriaClinicaView.getComponentById("hc_cmbparametrobusqueda"));
        mTbHC=((JTable) mBuscarHistoriaClinicaView.getComponentById("hc_tb"));
        mLblTablaVacia= mBuscarHistoriaClinicaView.getComponentById("hc_lbltablavacia");                        
        mTbHC.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    @Override
    public void reset() {
        clearTable();
        mTxtBuscar.setText("");
        mCmbParametroBusqueda.setSelectedIndex(0);
        mLblTablaVacia.setVisible(true);
    }
    
}

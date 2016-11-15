/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Callback;
import Model.Mascota;
import View.frmBuscarMascota;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Carlos
 */
public class ClsBuscarMascota extends ViewController {    
    private frmBuscarMascota mBuscarMascotaView;
    private Callback mCallback;    
    private ArrayList<Mascota> mMascotaList;
    //campos
    private JTextField mTxtBuscar;
    private JComboBox  mCmbParametroBusqueda;
    private JTable mTbMascotas;
    private JComponent mLblTablaVacia;
    private DefaultTableModel mTbModel;
    //tabla 
        //columnas
    private final String []mTableColumns =new String[]{"Id","Nombre","Propietario DNI","Especie","Raza","Color"};
    
    
    public ClsBuscarMascota(frmBuscarMascota buscarMascotaView){
        TAG="ClsBuscarMascota";
        TAG_ERROR="ClsBuscarMascota-Error";
        mBuscarMascotaView=buscarMascotaView;        
    }
    
    
    public void setCallback(Callback callback){
        mCallback=callback;
    }
    
    public void buscarMascota(){
      try{
            //obtener componentes
             //bind();
        
            mTbMascotas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
            //crear un nuevo table model
            clearTable();
                        
            //obteniendo los datos de la bd
            mMascotaList =   Mascota.getMascotaList(mCmbParametroBusqueda.getSelectedIndex(), mTxtBuscar.getText());                                    
            
             mLblTablaVacia.setVisible(mMascotaList.isEmpty());    
             for(Mascota mascota: mMascotaList)
                 mTbModel.addRow(mascota.toVector());
                      
            
        }catch(NullPointerException  err){            
            System.out.println(TAG_ERROR+": "+err);
            AlertDialogError("Error con el servidor");   
        }catch(Exception err2){
            System.out.println(TAG_ERROR+": "+err2);
            AlertDialogError("Error con el servidor");   
        }
    }
    //seleccionar mascota y 
    public void seleccionarMascota(){
        if(mCallback!=null){
            
            
            if(mTbMascotas!=null && mTbMascotas.getSelectedRowCount()>0){
                
                TableModel model=   mTbMascotas.getModel();               
                int selectedItemPosition=mTbMascotas.getSelectedRow();                
                String mascotaId=model.getValueAt(selectedItemPosition, 0).toString();
                Mascota auxMascota=null;
                
                for(int i=0;i<mMascotaList.size();i++)            
                    if(mMascotaList.get(i).compareId(mascotaId))
                        auxMascota=mMascotaList.get(i);
                    
                
                if(auxMascota!=null)
                    mCallback.execute(auxMascota.toArray());
                
                
                reset();
                mBuscarMascotaView.setVisible(false);
                
            }else
                AlertDialogError("Selecciona una fila de la tabla");   
       }else
            AlertDialogError("Necesitas abrir Mantener Mascota");   
    }
    
    //vaciar la tabla
    private void clearTable(){        
        mTbModel = new DefaultTableModel(null,mTableColumns);
        mTbMascotas.setModel(mTbModel);
    }
    
    
    //obtener componentes
    @Override
    public void bind() {
        mTxtBuscar =((JTextField)  mBuscarMascotaView.getComponentById("mascota_txtbuscar"));
        mCmbParametroBusqueda = ((JComboBox) mBuscarMascotaView.getComponentById("mascota_cmbparametrobusqueda"));
        mTbMascotas=((JTable) mBuscarMascotaView.getComponentById("mascota_tb"));
        mLblTablaVacia=mBuscarMascotaView.getComponentById("mascota_lbltablavacia");        
    }
    //resetear formulario
    @Override
    public void reset(){
        //bind();
        clearTable();
        mTxtBuscar.setText("");
        mCmbParametroBusqueda.setSelectedIndex(0);
        mLblTablaVacia.setVisible(true);
    }


    
    
  
}

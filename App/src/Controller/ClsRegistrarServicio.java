/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Callback;
import Model.Cliente;
import Model.Servicio;
import Model.Mascota;
import Model.TipoServicio;
import View.frmBuscarCliente;
import View.frmBuscarMascota;
import View.frmRegistrarServicio;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class ClsRegistrarServicio extends ViewController {
    private final frmRegistrarServicio mGenerarConceptoComprobantePagoView;
    private final frmBuscarMascota mFormBuscarMascota;
    private final javax.swing.JFrame mFormBuscarCliente;//extend

    private Mascota mMascota;
    private Cliente mCliente;
    private ArrayList<TipoServicio> mTipoServicioList;    
    private TipoServicio mSelectedServicio;
    private Servicio mServicio;
             
    private boolean mReady;
    //campos    
    private JTextField mTxtNombres;
    private JTextField mTxtApellidos;
    private JTextField mTxtDNI;
    private JTextField mTxtTelefono;
    private JTextField mTxtDireccion;
    private JTextField mTxtMNombre;
    private JComboBox mCmbEspecie;
    private JTextField mTxtRaza;
    private JTextField mTxtColor;
    private JRadioButton mRdbMacho;
    private JRadioButton mRdbHembra;
    //private JTextField mTxtEdad;
    
    private JComboBox mCmbServicioTipo;
    private JTextField mTxtFecha;
    private JTextField mTxtSubTotal;
    private JTextField mTxtIGV;
    private JTextField mTxtTotal;
    private JComponent mBtnGenerar;
    private JComponent mBtnAgregarServicio;
    private JComponent mBtnEliminarServicio;
    private JComponent mBtnImprimir;
    private JComponent mLblTablaVacia;
    private JTable mTbServicios;
    private DefaultTableModel mTbModel;
    //tabla 
        //columnas
    private final String []mTableColumns =new String[]{"Servicio","Precio"};    
    
    public ClsRegistrarServicio(frmRegistrarServicio generarConceptoComprobantePagoView){
         TAG="ClsRegistrarServicio";
         TAG_ERROR="ClsRegistrarServicio-Error";
         mGenerarConceptoComprobantePagoView=generarConceptoComprobantePagoView;
         mFormBuscarMascota= new frmBuscarMascota();
         
         
         mFormBuscarMascota.getClsBuscarMascota().setCallback(new Callback(){
             @Override
             public void execute(String[] args) {
                
                    mMascota= new Mascota(args);                                                         
                    
                    mTxtMNombre.setText(mMascota.getNombre());
                    mCmbEspecie.setSelectedItem(mMascota.getEspecie());
                    mTxtRaza.setText(mMascota.getRaza());
                    mTxtColor.setText(mMascota.getColor());
                    
                    switch(mMascota.getGenero()){
                        case "M":
                            mRdbMacho.setSelected(true);
                            mRdbHembra.setSelected(false);
                            mMascota.setGenero("M");
                            break;
                        default:
                            mRdbHembra.setSelected(true);
                            mRdbMacho.setSelected(false);
                            mMascota.setGenero("H");

                    }
                    //una vez obtenido los datos de la mascota,
                    //obtenemos los datos del cliente
                   consultarCliente();
             }
             
         });
         
         
         mFormBuscarCliente= new frmBuscarCliente();
         mTipoServicioList= new ArrayList();
         mReady=false;
         mServicio= new Servicio();
    }
    
    //mostrar formulario de búsqueda de cliente
    public void invocarBuscarMascota(){
        mFormBuscarMascota.setVisible(true);
    }
    //mostrar formulario de búsqueda de cliente
    public void invocarBuscarCliente(){
        mFormBuscarCliente.setVisible(true);
    }
    
    
    public void consultarCliente(){
        try{
            mCliente=Cliente.consultarCliente(mMascota.getPropietarioDNI());       
            mTxtNombres.setText(mCliente.getNombre());
            mTxtApellidos.setText(mCliente.getApellidos());    
            mTxtDNI.setText(mCliente.getDni());
           // mTxtEdad.setText(mCliente.getEdad()+"");
            mTxtTelefono.setText(mCliente.getTelefono());    
            mTxtDireccion.setText(mCliente.getDireccion());
                        
            setTiempoActual();
            mCmbServicioTipo.setEnabled(true);
            calcularMonto();
            mBtnGenerar.setEnabled(true);            
            mBtnAgregarServicio.setEnabled(true);
            mBtnEliminarServicio.setEnabled(true);
            mBtnImprimir.setEnabled(true);
        }catch(NullPointerException  err){            
                System.out.println("ClsMantenerMascota: "+err);
                AlertDialogError("Error con el servidor"); 
        }catch(Exception err2){
            System.out.println("ClsMantenerMascota: "+err2);
            AlertDialogError("No existe el cliente con ese DNI"); 
        }
    }

    //setear tiempo actual al txtfecha una vez cargado los datos del cliente y su macota
    private void setTiempoActual(){
        Calendar fecha=Calendar.getInstance();                
        mTxtFecha.setText(fecha.get(Calendar.DAY_OF_MONTH)+"/"+(fecha.get(Calendar.MONTH)+1)+"/"+fecha.get(Calendar.YEAR));
    }
    
    private void cargarComboServicios(){
        
        if(mTipoServicioList.isEmpty()){
            try{
                mTipoServicioList= TipoServicio.getServicioList();
                mCmbServicioTipo.removeAllItems();
                for(TipoServicio servicio:mTipoServicioList)  
                    mCmbServicioTipo.addItem(servicio.getNombre());
                
                
            }catch(NullPointerException  err){            
                System.out.println("ClsMantenerMascota: "+err);
                AlertDialogError("Error con el servidor"); 
            }catch(Exception err2){
                System.out.println("ClsMantenerMascota: "+err2);
                AlertDialogError("Error con el servidor"); 
            }
        }
    }
    
    
    public void agregarServicioATabla(){
        Vector<String> row= new Vector();
        int index=mCmbServicioTipo.getSelectedIndex();
        row.add(mTipoServicioList.get(index).getNombre()+"");
        row.add(mTipoServicioList.get(index).getPrecio()+"");                
        
        mTbModel.addRow(row);        
        mServicio.addServicioId(mTipoServicioList.get(index).getId());
        
        mLblTablaVacia.setVisible(mTbModel.getRowCount()==0);
        
        calcularMonto();        
    }
    
    public void eliminarServicioDeTabla(){
        if(mTbServicios!=null && mTbServicios.getSelectedRowCount()>0){               
                
                int selectedItemPosition=mTbServicios.getSelectedRow();                
                mTbModel.removeRow(selectedItemPosition);
                mServicio.removeServicioId(selectedItemPosition);
            }else
                AlertDialogError("Selecciona una fila de la tabla");   
        
        mLblTablaVacia.setVisible(mTbModel.getRowCount()==0);
        calcularMonto();
    }
    //calcular monto a pagar y mostrarlo en la interfaz
    public void calcularMonto(){
        
        if(mReady){
            int tam =mTbModel.getRowCount();
            float precio=0;
               for(int i=0;i<tam;i++)                   
                    precio+=Float.parseFloat(mTbModel.getValueAt(i, 1)+"");
            
            
            
            float igv=precio*0.18f;
            float total=precio+igv;
            DecimalFormat df = new DecimalFormat("0.00");
            mTxtSubTotal.setText(df.format(precio)+"");
            mTxtIGV.setText( df.format(igv)+"");
            mTxtTotal.setText( df.format(total)+"");
        }
    }
    public void registrarServicio(){
        try{
            mServicio.setMascotaId(mMascota.getId());
            mServicio.setClienteId(mCliente.getId());        
            //mConceptoComprobantePago.setServicioId(mSelectedServicio.getId());
            mServicio.setIgv(mTxtIGV.getText().replace(",", "."));
            mServicio.setMontoTotal(mTxtTotal.getText().replace(",", "."));
            mServicio.setEstado(false);//estado de no cancelado
            mServicio.insert();
            AlertDialog("¡Se ha registrado un nuevo servicio con el código #"+mServicio.getGeneratedId()+" !");
            clearTable();        
            mGenerarConceptoComprobantePagoView.setVisible(false);
            reset();
        }catch(NullPointerException  err){            
                System.out.println("ClsMantenerMascota: "+err);
                AlertDialogError("Error con el servidor"); 
        }catch(Exception err2){
                System.out.println("ClsMantenerMascota: "+err2);
                AlertDialogError("Error con el servidor"); 
        }
    }
    
    
    //vaciar la tabla
    private void clearTable(){        
        mTbModel = new DefaultTableModel(null,mTableColumns);
        mTbServicios.setModel(mTbModel);
    }
    
    @Override
    public void bind() {
       //propietario
        mTxtNombres= (JTextField) mGenerarConceptoComprobantePagoView.getComponentById("cliente_nombres");
        mTxtApellidos= (JTextField) mGenerarConceptoComprobantePagoView.getComponentById("cliente_apellidos");
        mTxtDNI= (JTextField) mGenerarConceptoComprobantePagoView.getComponentById("cliente_dni");        
        mTxtTelefono= (JTextField) mGenerarConceptoComprobantePagoView.getComponentById("cliente_telefono");
        mTxtDireccion= (JTextField) mGenerarConceptoComprobantePagoView.getComponentById("cliente_direccion");        
        
        //mascota
        mTxtMNombre= (JTextField) mGenerarConceptoComprobantePagoView.getComponentById("mascota_nombre");        
        mCmbEspecie= (JComboBox) mGenerarConceptoComprobantePagoView.getComponentById("mascota_especie");
        mTxtRaza= (JTextField) mGenerarConceptoComprobantePagoView.getComponentById("mascota_raza");
        mTxtColor= (JTextField) mGenerarConceptoComprobantePagoView.getComponentById("mascota_color");                      
        mRdbMacho= (JRadioButton) mGenerarConceptoComprobantePagoView.getComponentById("mascota_rdbmacho");
        mRdbHembra= (JRadioButton) mGenerarConceptoComprobantePagoView.getComponentById("mascota_rdbhembra");
        //mTxtEdad= (JTextField) mGenerarConceptoComprobantePagoView.getComponentById("mascota_edad");

        
        //servicio
        mCmbServicioTipo=(JComboBox) mGenerarConceptoComprobantePagoView.getComponentById("servicio_tipo");
        mTxtFecha=(JTextField) mGenerarConceptoComprobantePagoView.getComponentById("servicio_fecha");
        mTxtSubTotal=(JTextField) mGenerarConceptoComprobantePagoView.getComponentById("servicio_subtotal");
        mTxtIGV=(JTextField) mGenerarConceptoComprobantePagoView.getComponentById("servicio_igv");
        mTxtTotal=(JTextField) mGenerarConceptoComprobantePagoView.getComponentById("servicio_total");
        mBtnGenerar= mGenerarConceptoComprobantePagoView.getComponentById("servicio_btngenerar");
        mBtnImprimir= mGenerarConceptoComprobantePagoView.getComponentById("servicio_btnimprimir");
        mBtnAgregarServicio=mGenerarConceptoComprobantePagoView.getComponentById("servicio_btnagregarservicio");
        mBtnEliminarServicio=mGenerarConceptoComprobantePagoView.getComponentById("servicio_btneliminarservicio");
        mTbServicios=(JTable) mGenerarConceptoComprobantePagoView.getComponentById("servicio_tb");
        mLblTablaVacia=mGenerarConceptoComprobantePagoView.getComponentById("servicio_lbltablavacia"); 
        
        //cargar servicios en el combobox
        cargarComboServicios();
        mReady=true;


        //crear un nuevo table model
        clearTable();        
    }

    @Override
    public void reset() {
        //propietario
        mTxtNombres.setText("");
        mTxtApellidos.setText("");
        mTxtDNI.setText("");        
        mTxtTelefono.setText("");
        mTxtDireccion.setText("");        
        
        //mascota
        mTxtMNombre.setText("");        
        mCmbEspecie.setSelectedIndex(0);
        mTxtRaza.setText("");
        mTxtColor.setText("");                      
        mRdbMacho.setSelected(true);
        mRdbHembra.setSelected(false);
       // mTxtEdad.setText("");

        
        //servicio
        mCmbServicioTipo.setEnabled(false);
        if(mCmbServicioTipo.getItemCount()>0)
            mCmbServicioTipo.setSelectedIndex(0);
        mTxtFecha.setText("--/--/----");
        mTxtSubTotal.setText("00.00");
        mTxtIGV.setText("00.00");
        mTxtTotal.setText("00.00");
        mBtnGenerar.setEnabled(false);        
        mBtnAgregarServicio.setEnabled(false);
        mBtnEliminarServicio.setEnabled(false);
        mBtnImprimir.setEnabled(false);
        
        
        mLblTablaVacia.setVisible(true);
    }
}

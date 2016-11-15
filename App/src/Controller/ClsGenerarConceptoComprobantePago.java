/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Callback;
import Model.Cliente;
import Model.ConceptoComprobantePago;
import Model.Mascota;
import Model.Servicio;
import View.frmBuscarCliente;
import View.frmBuscarMascota;
import View.frmGenerarConceptoComprobantePago;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public class ClsGenerarConceptoComprobantePago extends ViewController {
    private final frmGenerarConceptoComprobantePago mGenerarConceptoComprobantePagoView;
    private final frmBuscarMascota mFormBuscarMascota;
    private final javax.swing.JFrame mFormBuscarCliente;//extend

    private Mascota mMascota;
    private Cliente mCliente;
    private ArrayList<Servicio> mServicioList;
    private Servicio mSelectedServicio;
    private ConceptoComprobantePago mConceptoComprobantePago;
             
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
    private JTextField mTxtHC;    
    
    private JComboBox mCmbServicioTipo;
    private JTextField mTxtFecha;
    private JTextField mTxtSubTotal;
    private JTextField mTxtIGV;
    private JTextField mTxtTotal;
    private JComponent mBtnGenerar;
    
    public ClsGenerarConceptoComprobantePago(frmGenerarConceptoComprobantePago generarConceptoComprobantePagoView){
         TAG="ClsGenerarConceptoComprobantePago";
         TAG_ERROR="ClsGenerarConceptoComprobantePago-Error";
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
         mServicioList= new ArrayList();
         mReady=false;
         mConceptoComprobantePago= new ConceptoComprobantePago();
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
        
        if(mServicioList.isEmpty()){
            try{
                mServicioList= Servicio.getServicioList();
                mCmbServicioTipo.removeAllItems();
                for(Servicio servicio:mServicioList)  
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
    
    //calcular monto a pagar y mostrarlo en la interfaz
    public void calcularMonto(){
        
        if(mReady){
            int index=mCmbServicioTipo.getSelectedIndex();
            mSelectedServicio=mServicioList.get(index);
            float precio=mSelectedServicio.getPrecio();
            float igv=precio*0.18f;
            float total=precio+igv;
            DecimalFormat df = new DecimalFormat("0.00");
            mTxtSubTotal.setText(df.format(precio)+"");
            mTxtIGV.setText( df.format(igv)+"");
            mTxtTotal.setText( df.format(total)+"");
        }
    }
    public void generarConceptoComprobantePago(){
        try{
            mConceptoComprobantePago.setMascotaId(mMascota.getId());
            mConceptoComprobantePago.setClienteId(mCliente.getId());        
            mConceptoComprobantePago.setServicioId(mSelectedServicio.getId());
            mConceptoComprobantePago.setIgv(mTxtIGV.getText().replace(",", "."));
            mConceptoComprobantePago.setMontoTotal(mTxtTotal.getText().replace(",", "."));
            mConceptoComprobantePago.setEstado(false);//estado de no cancelado
            mConceptoComprobantePago.insert();
            AlertDialog("¡Se ha generado un nuevo concepto de comprobante con el código #"+mConceptoComprobantePago.getGeneratedId()+" !");
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
        //mTxtHC= (JTextField) mGenerarConceptoComprobantePagoView.getComponentById("mascota_numerohc");
        
        //servicio
        mCmbServicioTipo=(JComboBox) mGenerarConceptoComprobantePagoView.getComponentById("servicio_tipo");
        mTxtFecha=(JTextField) mGenerarConceptoComprobantePagoView.getComponentById("servicio_fecha");
        mTxtSubTotal=(JTextField) mGenerarConceptoComprobantePagoView.getComponentById("servicio_subtotal");
        mTxtIGV=(JTextField) mGenerarConceptoComprobantePagoView.getComponentById("servicio_igv");
        mTxtTotal=(JTextField) mGenerarConceptoComprobantePagoView.getComponentById("servicio_total");
        mBtnGenerar= mGenerarConceptoComprobantePagoView.getComponentById("servicio_btngenerar");
        
        
        //cargar servicios en el combobox
        cargarComboServicios();
        mReady=true;
        
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
        //mTxtHC.setText("");
        
        //servicio
        mCmbServicioTipo.setEnabled(false);
        if(mCmbServicioTipo.getItemCount()>0)
            mCmbServicioTipo.setSelectedIndex(0);
        mTxtFecha.setText("--/--/----");
        mTxtSubTotal.setText("00.00");
        mTxtIGV.setText("00.00");
        mTxtTotal.setText("00.00");
        mBtnGenerar.setEnabled(false);
    }
}

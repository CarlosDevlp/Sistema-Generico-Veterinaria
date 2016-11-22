/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Callback;
import Model.Cita;
import Model.Cliente;
import Model.Mascota;
import Model.Servicio;
import Model.TipoServicio;
import View.frmBuscarCita;
import View.frmBuscarMascota;
import View.frmRegistrarCita;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public class ClsRegistrarCita extends ViewController{
    
    private frmRegistrarCita mRegistrarCitaView;
    private frmBuscarCita mBuscarCitaView; 
    private final frmBuscarMascota mFormBuscarMascota;
    private Cita mCita;
    private Servicio mServicio;
    private Mascota mMascota;
    private Cliente mCliente;
    //campos
    private JTextField mTxtNombres;
    private JTextField mTxtApellidos;
    private JTextField mTxtDNI;    
    
    private JTextField mTxtMNombre;
    private JComboBox mCmbEspecie;
    private JTextField mTxtRaza;    
    private JRadioButton mRdbMacho;
    private JRadioButton mRdbHembra;    

    
    private JComboBox mCmbDoctor;
    private JDateChooser mDtpFecha;
    private JSpinner  mSpnHora;
    private JComponent mBtnRegistrar;
    private JComponent mBtnBuscarCita;
    
    public ClsRegistrarCita(frmRegistrarCita registrarCitaView){
        TAG="ClsRegistrarCita";
        TAG_ERROR="ClsRegistrarCita-Error";
        
        mRegistrarCitaView=registrarCitaView;
         mBuscarCitaView= new frmBuscarCita();
         mFormBuscarMascota= new frmBuscarMascota();

         mFormBuscarMascota.getClsBuscarMascota().setCallback(new Callback(){
             @Override
             public void execute(String[] args) {
                
                    mMascota= new Mascota(args);
                    mTxtMNombre.setText(mMascota.getNombre());
                    mCmbEspecie.setSelectedItem(mMascota.getEspecie());
                    mTxtRaza.setText(mMascota.getRaza());
                    
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
         mCita= new Cita();
         mServicio=new Servicio();
    }       
    
    //registrar la cita en la bd
    public void registrarCita(){
        try{
            
            mCita.setIdCliente(mCliente.getId());
            mCita.setIdMascota(mMascota.getId());
            mCita.setIdDoctor(mCmbDoctor.getSelectedIndex()+"");
            mCita.setHora(mSpnHora.getValue()+"");
            mCita.setFecha(mDtpFecha.getCalendar());
            
           TipoServicio auxTipoServicio= TipoServicio.getServicioWhereNombre("Consulta");
            
            mServicio.setClienteId(mCliente.getId());
            mServicio.setMascotaId(mMascota.getId());            
            mServicio.setTipoServicioId(auxTipoServicio.getId());
            mServicio.setSubTotal(auxTipoServicio.getPrecio());
            mServicio.insert();
            
            mCita.setIdServicio(mServicio.getGeneratedId());
            
            //System.out.print();
            //mDtpFecha.getCalendar().get(Calendar.)
            mCita.insert();
            AlertDialog("Se ha registrado la cita médica con éxito.");
            
            reset();
            mRegistrarCitaView.setVisible(false);
        }catch(NullPointerException  err){            
                System.out.println(TAG_ERROR+": "+err);
                AlertDialogError("Error con el servidor");
        }catch(Exception err2){
            System.out.println(TAG_ERROR+": "+err2);
            AlertDialogError(err2.getMessage());
        }
        
        
    }
    
    public void invocarBuscarCita(){
        mBuscarCitaView.setVisible(true);
    }
    
    //mostar formulario de búsqueda de cliente
    public void invocarBuscarMascota(){
        mFormBuscarMascota.setVisible(true);
    }
    
    public void consultarCliente(){
        try{
            mCliente=Cliente.consultarCliente(mMascota.getPropietarioDNI());       
            mTxtNombres.setText(mCliente.getNombre());
            mTxtApellidos.setText(mCliente.getApellidos());    
            mTxtDNI.setText(mCliente.getDni());
            
            //habilitar el resto de los campos
            mCmbDoctor.setEnabled(true);
            mDtpFecha.setEnabled(true);
            mSpnHora.setEnabled(true);
            mBtnRegistrar.setEnabled(true);
            mBtnBuscarCita.setEnabled(true);
            mDtpFecha.setDate(new Date());
            
            
            
        }catch(NullPointerException  err){            
                System.out.println(TAG_ERROR+": "+err);
                AlertDialogError("Error con el servidor"); 
        }catch(Exception err2){
            System.out.println(TAG_ERROR+": "+err2);
            AlertDialogError("No existe el cliente con ese DNI"); 
        }
    }
    
    
    
        
    //autocargar el combo
    private void autoCargarComboDoctor(){
        mCmbDoctor.removeAllItems();
        mCmbDoctor.addItem("Dr.Ramirez");
        mCmbDoctor.addItem("Dr.Torres");
        mCmbDoctor.addItem("Dr.Gomez");
        mCmbDoctor.addItem("Dr.Nano");
    }
    
    
    @Override
    public void bind() {
        //propietario
        mTxtNombres= (JTextField) mRegistrarCitaView.getComponentById("cliente_nombres");
        mTxtApellidos= (JTextField) mRegistrarCitaView.getComponentById("cliente_apellidos");
        mTxtDNI= (JTextField) mRegistrarCitaView.getComponentById("cliente_dni");
        
        //mascota
        mTxtMNombre= (JTextField) mRegistrarCitaView.getComponentById("mascota_nombre");        
        mCmbEspecie= (JComboBox) mRegistrarCitaView.getComponentById("mascota_especie");
        mTxtRaza= (JTextField) mRegistrarCitaView.getComponentById("mascota_raza");
        mRdbMacho= (JRadioButton) mRegistrarCitaView.getComponentById("mascota_rdbmacho");
        mRdbHembra= (JRadioButton) mRegistrarCitaView.getComponentById("mascota_rdbhembra");       
        
        //cita        
        mCmbDoctor=(JComboBox) mRegistrarCitaView.getComponentById("cita_doctor");
        mDtpFecha=(JDateChooser) mRegistrarCitaView.getComponentById("cita_fecha");
        mSpnHora=(JSpinner) mRegistrarCitaView.getComponentById("cita_hora");
        mBtnRegistrar= mRegistrarCitaView.getComponentById("cita_btnregistrar");
        mBtnBuscarCita= mRegistrarCitaView.getComponentById("cita_btnbuscarcita");
        autoCargarComboDoctor();
        
        mDtpFecha.setMinSelectableDate(new Date());
    }

    @Override
    public void reset() {
       //cliente
        mTxtNombres.setText("");
        mTxtApellidos.setText("");
        mTxtDNI.setText("");        
        
       //mascota
        mTxtMNombre.setText("");        
        mCmbEspecie.setSelectedIndex(0);
        mTxtRaza.setText("");
        mRdbMacho.setSelected(true);
        mRdbHembra.setSelected(false);
        
        //mCmbDoctor.setSelectedIndex(0);
        mDtpFecha.setDate(new Date());
        mSpnHora.setValue("9:00");
        
        mCmbDoctor.setEnabled(false);        
        mDtpFecha.setEnabled(false);
        mSpnHora.setEnabled(false);
        mBtnRegistrar.setEnabled(false);
        mBtnBuscarCita.setEnabled(false);
        
    }
    
}

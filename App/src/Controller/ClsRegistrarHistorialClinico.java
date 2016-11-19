/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Callback;
import Model.Cliente;
import Model.HistoriaClinica;
import Model.Mascota;
import View.frmBuscarMascota;
import View.frmRegistrarHistorialClinico;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public class ClsRegistrarHistorialClinico extends ViewController {
    private final frmBuscarMascota mFormBuscarMascota;
    //private final javax.swing.JFrame mFormBuscarCliente;//extend
    private frmRegistrarHistorialClinico mRegistrarHistorialClinicoView;
    
    private HistoriaClinica mHistoriaClinica;
    private Mascota mMascota;
    private Cliente mCliente;
    
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
    private JTextField mTxtMEdad;
    private JRadioButton mRdbMacho;
    private JRadioButton mRdbHembra;    
    private JTextField mTxtHC;
    
    
    
    private JTextField mTxtTemperatura;
    private JTextField mTxtPulso;
    private JTextField mTxtEstadoHidratacion;
    private JTextField mTxtFrecuenciaCardiaca;
    private JTextField mTxtFrecuenciaRespiratoria;

    public ClsRegistrarHistorialClinico(frmRegistrarHistorialClinico registrarHistorialClinicoView ){
        TAG="ClsRegistrarHistorialClinico";
        TAG_ERROR="ClsRegistrarHistorialClinico-Error";
        mRegistrarHistorialClinicoView=registrarHistorialClinicoView;
        
        mFormBuscarMascota= new frmBuscarMascota();           
        mFormBuscarMascota.getClsBuscarMascota().setCallback(new Callback(){
             @Override
             public void execute(String[] args) {
                
                    mMascota= new Mascota(args);                                                         
                    
                    mTxtMNombre.setText(mMascota.getNombre());
                    mCmbEspecie.setSelectedItem(mMascota.getEspecie());
                    mTxtRaza.setText(mMascota.getRaza());
                    mTxtColor.setText(mMascota.getColor());
                    mTxtMEdad.setText(mMascota.getEdad()+"");
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
        
        mHistoriaClinica= new HistoriaClinica();
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
            mTxtTelefono.setText(mCliente.getTelefono());    
            mTxtDireccion.setText(mCliente.getDireccion());     
            
            
            
            //habilitar el resto de los campos
            mTxtTemperatura.setEnabled(true);
            mTxtPulso.setEnabled(true);
            mTxtEstadoHidratacion.setEnabled(true);
            mTxtFrecuenciaCardiaca.setEnabled(true);
            mTxtFrecuenciaRespiratoria.setEnabled(true);
        }catch(NullPointerException  err){            
                System.out.println("ClsMantenerMascota: "+err);
                AlertDialogError("Error con el servidor"); 
        }catch(Exception err2){
            System.out.println("ClsMantenerMascota: "+err2);
            AlertDialogError("No existe el cliente con ese DNI"); 
        }
    }
    
    public void agregarHistorialClinico(){
        try{ 
            if(!validarFormulario()){
                        AlertDialogError("No debe haber campos vacíos");
                        return;}
            
            
            mHistoriaClinica.setMascotaId(mMascota.getId());
            mHistoriaClinica.setTemperatura(mTxtTemperatura.getText());
            mHistoriaClinica.setPulso(mTxtPulso.getText());
            mHistoriaClinica.setEstadoHidratacion(mTxtEstadoHidratacion.getText());
            mHistoriaClinica.setFrecuenciaCardiaca(mTxtFrecuenciaCardiaca.getText());
            mHistoriaClinica.setFrecuenciaRespiratoria(mTxtFrecuenciaRespiratoria.getText());
            
            mHistoriaClinica.insert();
            
            AlertDialog("Se ha registrado el historial clínico con éxito");
            
            mRegistrarHistorialClinicoView.setVisible(false);
        }catch(NullPointerException  err){            
                System.out.println(TAG_ERROR+": "+err);
                AlertDialogError("Error con el servidor"); 
        }catch(Exception err2){
                System.out.println(TAG_ERROR+": "+err2);
                AlertDialogError("Error con el servidor"); 
        }
    }
    
    private boolean validarFormulario(){
        return !(
                 (mTxtTemperatura.getText().trim()).isEmpty() || 
                 (mTxtPulso.getText().trim()).isEmpty() || 
                 (mTxtEstadoHidratacion.getText().trim()).isEmpty() || 
                 (mTxtFrecuenciaCardiaca.getText().trim()).isEmpty() ||
                 (mTxtFrecuenciaRespiratoria.getText().trim()).isEmpty());
    }
    
    //mostar formulario de búsqueda de cliente
    /*public void invocarBuscarCliente(){
        mFormBuscarCliente.setVisible(true);
    }*/

    @Override
    public void bind() {
        //propietario
        mTxtNombres= (JTextField) mRegistrarHistorialClinicoView.getComponentById("cliente_nombres");
        mTxtApellidos= (JTextField) mRegistrarHistorialClinicoView.getComponentById("cliente_apellidos");
        mTxtDNI= (JTextField) mRegistrarHistorialClinicoView.getComponentById("cliente_dni");        
        mTxtTelefono= (JTextField) mRegistrarHistorialClinicoView.getComponentById("cliente_telefono");
        mTxtDireccion= (JTextField) mRegistrarHistorialClinicoView.getComponentById("cliente_direccion");        
        
        //mascota
        mTxtMNombre= (JTextField) mRegistrarHistorialClinicoView.getComponentById("mascota_nombre");        
        mCmbEspecie= (JComboBox) mRegistrarHistorialClinicoView.getComponentById("mascota_especie");
        mTxtRaza= (JTextField) mRegistrarHistorialClinicoView.getComponentById("mascota_raza");
        mTxtColor= (JTextField) mRegistrarHistorialClinicoView.getComponentById("mascota_color");                      
        mRdbMacho= (JRadioButton) mRegistrarHistorialClinicoView.getComponentById("mascota_rdbmacho");
        mRdbHembra= (JRadioButton) mRegistrarHistorialClinicoView.getComponentById("mascota_rdbhembra");
        mTxtMEdad= (JTextField) mRegistrarHistorialClinicoView.getComponentById("mascota_edad");
        
        
        //hc
        mTxtTemperatura=(JTextField) mRegistrarHistorialClinicoView.getComponentById("hc_temperatura");
        mTxtPulso=(JTextField) mRegistrarHistorialClinicoView.getComponentById("hc_pulso");
        mTxtEstadoHidratacion=(JTextField) mRegistrarHistorialClinicoView.getComponentById("hc_estadohidratacion");
        mTxtFrecuenciaCardiaca=(JTextField) mRegistrarHistorialClinicoView.getComponentById("hc_frecuenciacardiaca");
        mTxtFrecuenciaRespiratoria=(JTextField) mRegistrarHistorialClinicoView.getComponentById("hc_frecuenciarespiratoria");
        
        mTxtHC=(JTextField) mRegistrarHistorialClinicoView.getComponentById("hc_numero");
        
        mTxtHC.setText(mHistoriaClinica.getGeneratedId());
    }

    @Override
    public void reset() {
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
        mTxtMEdad.setText("");
        
        
        //hc
        mTxtTemperatura.setEnabled(false);
        mTxtPulso.setEnabled(false);
        mTxtEstadoHidratacion.setEnabled(false);
        mTxtFrecuenciaCardiaca.setEnabled(false);
        mTxtFrecuenciaRespiratoria.setEnabled(false);
    }
}

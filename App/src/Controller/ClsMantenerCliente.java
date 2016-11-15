/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Cliente;
import View.frmBuscarCliente;
import View.frmMantenerCliente;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public class ClsMantenerCliente extends ViewController {
    private final javax.swing.JFrame mFormBuscarCliente;//extend
    private frmMantenerCliente mMantenerClienteView;
    private Cliente mCliente;
    
    //campos
    private JTextField mTxtDNI;
    private JTextField mTxtNombres;
    private JTextField mTxtApellidos;    
    private JRadioButton mRdbMasculino;
    private JRadioButton mRdbFemenino;
    private JTextField mTxtEdad;
    private JTextField mTxtTelefono;    
    private JTextField mTxtDireccion;
    private JTextField mTxtCorreo;
    
    
    private JComponent mBtnAgregar;
    private JComponent mBtnActualizar;
    private JComponent mBtnEliminar;
    
    
    public ClsMantenerCliente(frmMantenerCliente mantenerClienteView){
        TAG="ClsMantenerCliente";
        TAG_ERROR="ClsMantenerCliente-Error";
        
        mMantenerClienteView= mantenerClienteView;
        mFormBuscarCliente= new frmBuscarCliente();
    }
            
    
    //mostar formulario de búsqueda de cliente
    public void invocarBuscarCliente(){
        mFormBuscarCliente.setVisible(true);
    }

    
    public void agregarCliente(){
         try{
            //bind();
            
             if(!validarFormulario()){
                    AlertDialogError("No debe haber campos vacíos");
                    return;
             }
            
            
            Cliente cliente=new Cliente();
            
            cliente.setDni(mTxtDNI.getText());
            
            cliente.setNombre(mTxtNombres.getText());
            cliente.setApellidos(mTxtApellidos.getText());    
            cliente.setDni(mTxtDNI.getText());
            cliente.setEdad(Integer.parseInt(mTxtEdad.getText()));
            cliente.setTelefono(mTxtTelefono.getText());    
            cliente.setDireccion(mTxtDireccion.getText());
            cliente.setCorreo(mTxtCorreo.getText());
            
            cliente.setGenero( (mRdbMasculino.isSelected()?"M":"F") );
            
            //agregar cliente a la bd
            cliente.agregarCliente();
                    
            mCliente=cliente;                                      
            
            AlertDialog("Se agrego el cliente al registro exitosamente"); 
            reset();
            mMantenerClienteView.setVisible(false);
            
        }catch(NullPointerException  err){            
                System.out.println("ClsMantenerMascota: "+err);
                AlertDialogError("Error con el servidor"); 
        }catch(Exception err2){
            System.out.println("ClsMantenerMascota: "+err2);
            AlertDialogError("No existe el cliente con ese DNI"); 
        }
    }
    
       
    public void actualizarCliente(){
         try{
            //bind();
            
             if(!validarFormulario()){
                    AlertDialogError("No debe haber campos vacíos");
                    return;
             }
            
            
            Cliente cliente=new Cliente();
            
            cliente.setDni(mTxtDNI.getText());
            
            cliente.setNombre(mTxtNombres.getText());
            cliente.setApellidos(mTxtApellidos.getText());    
            cliente.setDni(mTxtDNI.getText());
            cliente.setEdad(Integer.parseInt(mTxtEdad.getText()));
            cliente.setTelefono(mTxtTelefono.getText());    
            cliente.setDireccion(mTxtDireccion.getText());
            cliente.setCorreo(mTxtCorreo.getText());
            
            cliente.setGenero( (mRdbMasculino.isSelected()?"M":"F") );
            
            //agregar cliente a la bd
            cliente.actualizarCliente();
                    
            mCliente=cliente;                                      
            
            AlertDialog("Se actualizaron los datos del cliente exitosamente"); 
            
            reset();
            mMantenerClienteView.setVisible(false);
            
        }catch(NullPointerException  err){            
                System.out.println("ClsMantenerMascota: "+err);
                AlertDialogError("Error con el servidor"); 
        }catch(Exception err2){
            System.out.println("ClsMantenerMascota: "+err2);
            AlertDialogError("No existe el cliente con ese DNI"); 
        }
        
    }
    
    public void eliminarCliente(){
         try{            
            
             if(!validarDNI()){
                    AlertDialogError("Es necesario que el campo DNI no esté vacío");
                    return;
             }
            
            
            if(AlertDialogQuestion("¿Desea eliminar al cliente del registro?")==0){
                Cliente.eliminarCliente(mTxtDNI.getText());
                AlertDialog("Se eliminó al cliente del registro exitosamente"); 
                reset();
                mMantenerClienteView.setVisible(false);
            }
        }catch(NullPointerException  err){            
                System.out.println("ClsMantenerMascota: "+err);
                AlertDialogError("Error con el servidor"); 
        }catch(Exception err2){
            System.out.println("ClsMantenerMascota: "+err2);
            AlertDialogError("No existe el cliente con ese DNI"); 
        }
    }
    
    public void consultarCliente(){
        try{
           // bind();
            
            mCliente=Cliente.consultarCliente(mTxtDNI.getText());
       
            mTxtNombres.setText(mCliente.getNombre());
            mTxtApellidos.setText(mCliente.getApellidos());    
            mTxtDNI.setText(mCliente.getDni());
            mTxtEdad.setText(mCliente.getEdad()+"");
            mTxtTelefono.setText(mCliente.getTelefono());    
            mTxtDireccion.setText(mCliente.getDireccion());
            mTxtCorreo.setText(mCliente.getCorreo());
            
            
            switch(mCliente.getGenero()){
                    case "F":
                            mRdbMasculino.setSelected(false);
                            mRdbFemenino.setSelected(true);
                    break;
                    default:
                            mRdbMasculino.setSelected(true);
                            mRdbFemenino.setSelected(false);
            }
            
            mBtnAgregar.setEnabled(false);
            mBtnActualizar.setEnabled(true);
            mBtnEliminar.setEnabled(true);
            
        }catch(NullPointerException  err){            
                System.out.println("ClsMantenerMascota: "+err);
                AlertDialogError("Error con el servidor"); 
        }catch(Exception err2){
            System.out.println("ClsMantenerMascota: "+err2);
            AlertDialogError("No existe el cliente con ese DNI"); 
        }
    }
 
    private boolean validarFormulario(){
        return ! ((mTxtNombres.getText().trim()).isEmpty() ||
                (mTxtApellidos.getText().trim()).isEmpty() ||    
                (mTxtDNI.getText().trim()).isEmpty() ||  
                (mTxtEdad.getText().trim()).isEmpty() ||
                (mTxtTelefono.getText().trim()).isEmpty() ||    
                (mTxtDireccion.getText().trim()).isEmpty() ||
                (mTxtCorreo.getText().trim()).isEmpty());
    }
    
    
    private boolean validarDNI(){
        return  !(mTxtDNI.getText().trim()).isEmpty();
    }
    
    @Override
    public void bind() {
        mTxtNombres= (JTextField) mMantenerClienteView.getComponentById("cliente_nombres");
        mTxtApellidos= (JTextField) mMantenerClienteView.getComponentById("cliente_apellidos");    
        mTxtDNI= (JTextField) mMantenerClienteView.getComponentById("cliente_dni");
        mRdbMasculino= (JRadioButton) mMantenerClienteView.getComponentById("cliente_rdbmasculino");
        mRdbFemenino= (JRadioButton) mMantenerClienteView.getComponentById("cliente_rdbfemenino");
        mTxtEdad= (JTextField) mMantenerClienteView.getComponentById("cliente_edad");
        mTxtTelefono= (JTextField) mMantenerClienteView.getComponentById("cliente_telefono");    
        mTxtDireccion= (JTextField) mMantenerClienteView.getComponentById("cliente_direccion");
        mTxtCorreo= (JTextField) mMantenerClienteView.getComponentById("cliente_correo");

        mBtnAgregar=  mMantenerClienteView.getComponentById("cliente_btnagregar");
        mBtnActualizar=  mMantenerClienteView.getComponentById("cliente_btnactualizar");
        mBtnEliminar=  mMantenerClienteView.getComponentById("cliente_btneliminar");
    }

    @Override
    public void reset() {
        mTxtNombres.setText("");
        mTxtApellidos.setText("");    
        mTxtDNI.setText("");
        mTxtEdad.setText("");
        mTxtTelefono.setText("");    
        mTxtDireccion.setText("");
        mTxtCorreo.setText("");
        
        mRdbMasculino.setSelected(true);
        mRdbFemenino.setSelected(false);
        
        mBtnAgregar.setEnabled(true);
        mBtnActualizar.setEnabled(false);
        mBtnEliminar.setEnabled(false);
    }
}

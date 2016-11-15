/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Model.Callback;
import Model.Mascota;
import View.frmBuscarMascota;
import View.frmMantenerMascota;
import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public class ClsMantenerMascota extends ViewController{
    private frmMantenerMascota mMantenerMascotaView;
    private final frmBuscarMascota mFormBuscarMascota;    
    private Mascota mMascota;
    
    //campos
    private JTextField mTxtNombre;
    private JTextField mTxtPropietarioDNI;
    private JRadioButton mRdbMacho;
    private JRadioButton mRdbHembra;
    private JTextField mTxtEdad;
    private JComboBox  mCmbEspecie;
    private JTextField mTxtRaza;
    private JTextField mTxtColor;
    private JTextField mTxtNumeroHC;
    
    private JComponent mBtnAgregar;
    private JComponent mBtnActualizar;
    
    
    
    
    public ClsMantenerMascota(frmMantenerMascota mantenerMascotaView){
        TAG="ClsMantenerMascota";
        TAG_ERROR="ClsMantenerMascota-Error";
        
        mMantenerMascotaView= mantenerMascotaView;
        mFormBuscarMascota= new frmBuscarMascota();
        
        mFormBuscarMascota.getClsBuscarMascota().setCallback(new Callback(){
            @Override
            public void execute(String[] args) {
                
                //bind();
                
                mBtnAgregar.setEnabled(false);
                mBtnActualizar.setEnabled(true);
                
                
                mMascota= new Mascota(args);
                
        
                
                mTxtNombre.setText(mMascota.getNombre());
                mTxtPropietarioDNI.setText(mMascota.getPropietarioDNI());
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
                mTxtEdad.setText(mMascota.getEdad()+"");
                mCmbEspecie.setSelectedItem(mMascota.getEspecie());
                mTxtRaza.setText(mMascota.getRaza());
                mTxtColor.setText(mMascota.getColor());
                mTxtNumeroHC.setText(mMascota.getNumeroHC());
                
            }
            
        });
        
        
    }
    
    //mostar formulario de búsqueda de cliente
    public void invocarBuscarMascota(){
        mFormBuscarMascota.setVisible(true);
    }
    //agregar mascota
    public void agregarMascota(){
        
        try{
            
            Mascota mascota= new Mascota();
            
            //obtener componentes
            //bind();

            
             if(!validarFormulario()){
                    AlertDialogError("No debe haber campos vacíos");
                    return;
                }
             
            mBtnAgregar.setEnabled(true);
            mBtnActualizar.setEnabled(false);
            
            mMascota.setNombre(  mTxtNombre.getText() );
            mMascota.setPropietarioDNI( mTxtPropietarioDNI.getText());           
            mMascota.setGenero( (mRdbMacho.isSelected()?"M":"H") );
            mMascota.setEdad( Integer.parseInt(mTxtEdad.getText()) );
            mMascota.setEspecie(  mCmbEspecie.getSelectedItem().toString() );
            mMascota.setRaza(  mTxtRaza.getText() );
            mMascota.setColor(  mTxtColor.getText() );
            mMascota.setNumeroHC( mTxtNumeroHC.getText());
                                         
            
            mascota.save();
            
            mMascota= mascota;
            AlertDialog("¡Se ha agregado la mascota a la base de datos!");            
            
            //cerrando el formulario
            reset();
            mMantenerMascotaView.setVisible(false);
            
        }catch(NullPointerException  err){            
            System.out.println("ClsMantenerMascota: "+err);
            AlertDialogError("Error con el servidor"); 
        }catch(Exception err2){
            System.out.println("ClsMantenerMascota: "+err2);
            AlertDialogError("Error con el servidor"); 
        }finally{           
        }
        
    }
    //actualizar mascota
    public void actualizarMascota(){
        if(mMascota!=null){
            try{
                //obtener componentes
                bind();
                
                if(!validarFormulario()){
                    AlertDialogError("No debe haber campos vacíos");
                    return;
                }
                mMascota.setNombre(  mTxtNombre.getText() );
                
                mMascota.setEspecie(  mCmbEspecie.getSelectedItem().toString() );
                mMascota.setRaza(  mTxtRaza.getText() );
                mMascota.setColor(  mTxtColor.getText() );
                                
                mMascota.update();
                
                
                
                AlertDialog("Datos de la mascota actualizados satisfactoriamente");
                //cerrando el formulario
                reset();
                mMantenerMascotaView.setVisible(false);
                
                
            }catch(NullPointerException  err){            
                System.out.println("ClsMantenerMascota: "+err);
                AlertDialogError("Error con el servidor"); 
            }catch(Exception err2){
                System.out.println("ClsMantenerMascota: "+err2);
                AlertDialogError("Error con el servidor"); 
            }
        }else
            AlertDialogError("Debes buscar y elegir a la mascota que deseas actualizar");
    }

    private boolean validarFormulario(){
        return !((mTxtNombre.getText().trim()).isEmpty() ||
                (mTxtEdad.getText().trim()).isEmpty()    ||
                (mTxtPropietarioDNI.getText().trim()).isEmpty()    ||
                (mTxtRaza.getText().trim()).isEmpty()    ||
                (mTxtColor.getText().trim()).isEmpty()  ||
                (mTxtNumeroHC.getText().trim()).isEmpty());
    }
    
    
    @Override
    public void bind() {
    
        mTxtNombre=(JTextField) mMantenerMascotaView.getComponentById("mascota_nombre");
        mTxtPropietarioDNI=  (JTextField) mMantenerMascotaView.getComponentById("mascota_propietario");
        mRdbMacho= (JRadioButton) mMantenerMascotaView.getComponentById("mascota_rdbmacho");
        mRdbHembra= (JRadioButton) mMantenerMascotaView.getComponentById("mascota_rdbhembra");
        mTxtEdad= (JTextField) mMantenerMascotaView.getComponentById("mascota_edad");
        mCmbEspecie=(JComboBox) mMantenerMascotaView.getComponentById("mascota_especie");
        mTxtRaza=(JTextField) mMantenerMascotaView.getComponentById("mascota_raza");
        mTxtColor=(JTextField) mMantenerMascotaView.getComponentById("mascota_color");
        mTxtNumeroHC=(JTextField) mMantenerMascotaView.getComponentById("mascota_numerohc");
        
        mBtnAgregar= mMantenerMascotaView.getComponentById("mascota_btnagregar");
        mBtnActualizar= mMantenerMascotaView.getComponentById("mascota_btnactualizar");
        
        
    }
    
    @Override
    public void reset() {
        
        /*if(mMascota==null){
           bind();
        }*/
        
        mTxtNombre.setText("");
        mTxtPropietarioDNI.setText("");
        mRdbMacho.setSelected(true);
        mRdbHembra.setSelected(false);
        mTxtEdad.setText("");
        mCmbEspecie.setSelectedIndex(0);
        mTxtRaza.setText("");
        mTxtColor.setText("");
        mTxtNumeroHC.setText("");
        
        
        mBtnAgregar.setEnabled(true);
        mBtnActualizar.setEnabled(false);
        
        
    }


    
  

}

/*Entidad Cliente*/
package Model;

import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author carlos
 */
public class Cliente extends Persona {    
    //private static final String []estado= {"DEUDOR","NORMAL"}; <--
    private String mId;
    //private double _credito;<---
    
    public Cliente(){                
        super();
    }
    //cuando el cliente solo quiere ingresar datos básicos
    /*public Cliente(String _nombre,String _dni){
        super(_nombre,  _dni, "vacío","vacío","vacío", "vacío", 0);        
        this._idCliente="CLIENT"+Cliente.generador();
        this._tipo="vacío";
    
    }*/
    //cuando el cliente quiere dejar muchos datos
    public Cliente( String dni, String nombres, String apellidos,String genero,int edad,String telefono,String direccion,String correo) {
        super(dni, nombres, apellidos,genero,edad,telefono,direccion,correo);        
    }
    
    
    public Cliente(String id, String dni, String nombres, String apellidos,String genero,int edad,String telefono,String direccion,String correo) {
        super(dni, nombres, apellidos,genero,edad,telefono,direccion,correo);        
        mId=id;
    }
    //setear crédito al cliente
    /*public void setCredito(double credito) {
        this.mCredito = credito;
    }*/

    public String getId() {
        return mId;
    }
    
    
    public static Cliente consultarCliente(String dni) throws Exception{
        
        ArrayList<String> auxPersona= Dao.select("*", "PERSONA", "PERSONA_DNI='"+dni+"'").get(0);
        ArrayList<String> auxCliente= Dao.select("ID_CLIENTE", "CLIENTE", "PERSONA_DNI='"+dni+"'").get(0);
        return new Cliente(auxCliente.get(0),auxPersona.get(0),auxPersona.get(1),auxPersona.get(2),auxPersona.get(3),Integer.parseInt(auxPersona.get(4)),
                           auxPersona.get(5),auxPersona.get(6),auxPersona.get(7));
    }
            
    
    public void agregarCliente(){
        Dao.insert("PERSONA_DNI,NOMBRES,APELLIDOS,GENERO,EDAD,TELEFONO,DIRECCION,CORREO",new String []{getDni(),getNombre(),getApellidos(),getGenero(),getEdad()+"",getTelefono(),getDireccion(),getCorreo()},"PERSONA");
        Dao.insert("PERSONA_DNI",new String[]{getDni()},"CLIENTE");
    }
    
    
    public void actualizarCliente(){
        Dao.update("NOMBRES,APELLIDOS,GENERO,EDAD,TELEFONO,DIRECCION,CORREO",new String []{getNombre(),getApellidos(),getGenero(),getEdad()+"",getTelefono(),getDireccion(),getCorreo()},"PERSONA","PERSONA_DNI = '"+getDni()+"'");
    }
        
    
    public static void eliminarCliente(String dni){
        Dao.delete("CLIENTE", "PERSONA_DNI = '"+dni+"'");
        Dao.delete("PERSONA", "PERSONA_DNI = '"+dni+"'");
        
    }
    //typecast
    public Vector toVector(){
        Vector<String> vct=new Vector();
        
        vct.add(getDni()); 
        vct.add(getNombre());
        vct.add(getApellidos());     
        vct.add(getTelefono());
        vct.add(getDireccion());
        
        return vct;
    }
 
    
}

/*Entidad Abstracta Persona*/
package Model;

/**
 *
 * @author carlos
 */
public abstract class Persona{    
    private String mDni;
    private String mNombres;
    private String mApellidos;
    private String mGenero;
    private int mEdad;
    private String mTelefono;
    private String mDireccion;
    private String mCorreo;
    public Persona(){
        //función por defecto para su uso en factories
    }
    public Persona( String dni, String nombres, String apellidos,String genero,int edad,String telefono,String direccion,String correo) {
        mDni = dni;
        mNombres = nombres;
        mApellidos = apellidos;                        
        mGenero= genero;
        mEdad= edad;
        mTelefono = telefono;
        mDireccion = direccion;
        mCorreo= correo;
    }

   //setters and getters    
    public String getNombre() {
        return mNombres;
    }

    public void setNombre(String _nombre) {
        this.mNombres = _nombre;
    }

    public String getDni() {
        return mDni;
    }

    public void setDni(String _dni) {
        this.mDni = _dni;
    }

    public String getDireccion() {
        return mDireccion;
    }

    public void setDireccion(String _direccion) {
        this.mDireccion = _direccion;
    }
  
    public String getTelefono() {
        return mTelefono;
    }

    public void setTelefono(String telefono) {
        this.mTelefono = telefono;
    }

    public String getApellidos() {
        return mApellidos;
    }

    public void setApellidos(String mApellidos) {
        this.mApellidos = mApellidos;
    }

    public String getGenero() {
        return mGenero;
    }

    public void setGenero(String mGenero) {
        this.mGenero = mGenero;
    }

    public int getEdad() {
        return mEdad;
    }

    public void setEdad(int mEdad) {
        this.mEdad = mEdad;
    }

    public String getCorreo() {
        return mCorreo;
    }

    public void setCorreo(String mCorreo) {
        this.mCorreo = mCorreo;
    }
    
    
    public String []toArray(){
        
        return new String[]{mDni ,mNombres ,mApellidos ,mGenero,mEdad+"",mTelefono ,mDireccion ,mCorreo};
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Carlos
 */
public class TipoServicio {
    private String mId;
    private String mNombre;
    private float  mPrecio;
    
    
    public TipoServicio(){        
        
    }    

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getNombre() {
        return mNombre;
    }

    public void setNombre(String nombre) {
        mNombre = nombre;
    }

    public float getPrecio() {
        return mPrecio;
    }

    public void setPrecio(float precio) {        
       mPrecio = precio;
    }
    

    public static ArrayList<TipoServicio> getServicioList(){
        ArrayList<TipoServicio> servicioList= new ArrayList();
        TipoServicio auxServicio;        
        
        ArrayList<ArrayList<String>> resultList=Dao.select("ID_TIPO_SERVICIO,NOMBRE,PRECIO", "TIPO_SERVICIO", null);
        
        for(ArrayList<String> row: resultList){
            auxServicio=new TipoServicio();
            auxServicio.setId(row.get(0));
            auxServicio.setNombre(row.get(1));
            auxServicio.setPrecio( Float.parseFloat(row.get(2)) );
            
            servicioList.add(auxServicio);
        }
        
        return servicioList;
    }
    
    
}

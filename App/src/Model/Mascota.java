/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class Mascota {
    private String mId;
    private String mNombre;  
    private String mPropietarioDNI;
    private String mGenero;
    private int mEdad;    
    private String mEspecie;
    private String mRaza;
    private String mColor;
    
    
    
    public Mascota(){
        
    }

    public Mascota(String []args){
        mId=args[0];
        mNombre=args[1];
        mPropietarioDNI=args[2];
        mGenero=args[3];
        mEdad=Integer.parseInt(args[4]);
        mEspecie=args[5];
        mRaza=args[6];
        mColor=args[7];
    }
    
    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }
            
    public String getNombre() {
        return mNombre;
    }

    public void setNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public String getGenero() {
        return mGenero;
    }

    public void setGenero(String genero) {
        mGenero = genero;
    }

    public String getEspecie() {
        return mEspecie;
    }

    public void setEspecie(String mEspecie) {
        this.mEspecie = mEspecie;
    }

    public String getRaza() {
        return mRaza;
    }

    public void setRaza(String mRaza) {
        this.mRaza = mRaza;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String mColor) {
        this.mColor = mColor;
    }

    public String getPropietarioDNI() {
        return mPropietarioDNI;
    }

    public void setPropietarioDNI(String mPropietarioDNI) {
        this.mPropietarioDNI = mPropietarioDNI;
    }

    public int getEdad() {
        return mEdad;
    }

    public void setEdad(int mEdad) {
        this.mEdad = mEdad;
    }

    
    
    
    public boolean compareId(String otherId){
        return mId.equals(otherId);
    }
    
    
    
    public void insert() throws Exception{   
        Dao.insert("NOMBRE,PROPIETARIO_DNI,GENERO,EDAD,ESPECIE,RAZA,COLOR", new String[]{mNombre,mPropietarioDNI,mGenero,mEdad+"",mEspecie,mRaza,mColor}, "MASCOTA");
    }
    
    public void update(){          
        Dao.update("NOMBRE,PROPIETARIO_DNI,GENERO,EDAD,ESPECIE,RAZA,COLOR", new String[]{mNombre,mPropietarioDNI,mGenero,mEdad+"",mEspecie,mRaza,mColor}, "MASCOTA","ID_MASCOTA = "+mId);
    }
    
    public Vector toVector(){
         Vector<String> vct=new Vector();
        
        vct.add(mId);
        vct.add(mNombre);
        vct.add(mPropietarioDNI);
        vct.add(mEspecie);        
        vct.add(mRaza);
        vct.add(mColor);
        
        
        return vct;
    }
    
    public String []toArray(){        
        return new String[]{mId,mNombre,mPropietarioDNI,mGenero,mEdad+"",mEspecie,mRaza,mColor};  
    }
    
    public static ArrayList<Mascota> getMascotaList(int columnIndex, String value){
       
        ArrayList<Mascota> mascotaList= new ArrayList();
        Mascota auxMascota;        
        String column="NOMBRE";
                
        switch(columnIndex){
            case 0:
                column="NOMBRE";
                break;
            case 1:
                column="ESPECIE";
                break;    
            case 2:
                column="COLOR";
                break;
            case 3:
                column="RAZA";
                break;
            case 4:
                column="PROPIETARIO_DNI";
                
        }
        ArrayList<ArrayList<String>> resultList=Dao.select("ID_MASCOTA,NOMBRE,PROPIETARIO_DNI,GENERO,EDAD,ESPECIE,RAZA,COLOR", "MASCOTA", ((value.isEmpty())? null:column+" = '"+value+"'"));
        //System.out.println(resultList.size()); 
        for(ArrayList<String> row: resultList){
            
            auxMascota=new Mascota();
            
            //System.out.println(row.get(0));                        
            auxMascota.setId(row.get(0));
            auxMascota.setNombre(row.get(1));
            auxMascota.setPropietarioDNI(row.get(2));
            auxMascota.setGenero(row.get(3));            
            auxMascota.setEdad(Integer.parseInt(row.get(4)) );
            auxMascota.setEspecie(row.get(5));    
            auxMascota.setRaza(row.get(6));
            auxMascota.setColor(row.get(7));

            
            mascotaList.add(auxMascota);
        }
        return mascotaList;
    }
    
    
    
    
}

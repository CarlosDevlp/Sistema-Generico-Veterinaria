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
public class HistoriaClinica {
    private String mId;//Numero HC
    private String mNutricion;
    private String mEstiloVida;   
    private String mTemperatura;
    private String mPulso;
    private String mEstadoHidratacion;
    private String mFrecuenciaCardiaca;
    private String mFrecuenciaRespiratoria; 
    private String mMascotaId;
    
    public HistoriaClinica(){
        
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getNutricion() {
        return mNutricion;
    }

    public void setNutricion(String nutricion) {
        mNutricion = nutricion;
    }

    public String getEstiloVida() {
        return mEstiloVida;
    }

    public void setEstiloVida(String estiloVida) {
        mEstiloVida = estiloVida;
    }

    public String getTemperatura() {
        return mTemperatura;
    }

    public void setTemperatura(String temperatura) {
        mTemperatura = temperatura;
    }

    public String getPulso() {
        return mPulso;
    }

    public void setPulso(String pulso) {
        mPulso = pulso;
    }

    public String getEstadoHidratacion() {
        return mEstadoHidratacion;
    }

    public void setEstadoHidratacion(String estadoHidratacion) {
        mEstadoHidratacion = estadoHidratacion;
    }

    public String getFrecuenciaCardiaca() {
        return mFrecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(String frecuenciaCardiaca) {
        mFrecuenciaCardiaca = frecuenciaCardiaca;
    }

    public String getFrecuenciaRespiratoria() {
        return mFrecuenciaRespiratoria;
    }

    public void setFrecuenciaRespiratoria(String frecuenciaRespiratoria) {
        mFrecuenciaRespiratoria = frecuenciaRespiratoria;
    }

    public String getMascotaId() {
        return mMascotaId;
    }

    public void setMascotaId(String mascotaId) {
        mMascotaId = mascotaId;
    }
    
    
    
    public void insert(){
        Dao.insert("MASCOTA_ID_MASCOTA,NUTRICION,ESTILO_VIDA,TEMPERATURA,PULSO,ESTADO_HIDRATACION,FRECUENCIA_CARDIACA,FRECUENCIA_RESPIRATORIA",
                   new String[]{mMascotaId,mNutricion,mEstiloVida,mTemperatura,mPulso,mEstadoHidratacion,mFrecuenciaCardiaca,mFrecuenciaRespiratoria},
                   "HISTORIA_CLINICA");
    }
    
    public String getGeneratedId(){
        
        Dao.isTableEmpty("HISTORIA_CLINICA");
        
        if(mId==null){            
            if(!Dao.isTableEmpty("HISTORIA_CLINICA"))
                mId=(Integer.parseInt(Dao.selectLastRow("NUMERO_HC","NUMERO_HC","HISTORIA_CLINICA").get(0))+1)+"";
            else
                mId="1";
        }   
        
        return mId;
    }
    
    //public ArrayList<HistoriaClinica> getHistoriaClinicaList(int columnIndex, String value){
    public static ArrayList<Vector<String>> buscarHistoriaClinica(int columnIndex, String value){
        
        ArrayList<Vector<String>> hcList= new ArrayList();        
        String column="NOMBRE";        
        
        switch(columnIndex){
            case 0:
                column="HISTORIA_CLINICA.NUMERO_HC";
                break;
            case 1:
                column="NOMBRE";
                break;
            case 2:
                column="ESPECIE";
                break;                
            case 3:
                column="RAZA";
                break;
            case 4:
                column="PROPIETARIO_DNI";                
        }
        
        
        ArrayList<ArrayList<String>> resultList=Dao.select("HISTORIA_CLINICA.NUMERO_HC,NOMBRE,PROPIETARIO_DNI,ESPECIE,RAZA,EDAD,NUTRICION,TEMPERATURA", new String[]{"MASCOTA","HISTORIA_CLINICA"},
                                                            new String[]{"MASCOTA.ID_MASCOTA","HISTORIA_CLINICA.MASCOTA_ID_MASCOTA"} ,((value.isEmpty())? null:column+" = '"+value+"'"));
        //System.out.println(resultList.size()); 
        for(ArrayList<String> row: resultList)
            hcList.add(new Vector(row));
        
        
        return hcList;
        
    }

}

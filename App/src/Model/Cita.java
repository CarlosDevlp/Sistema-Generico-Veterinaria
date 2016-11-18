/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class Cita {
    private String mId;
    private String mIdCliente;
    private String mIdMascota;
    private String mIdDoctor;
    private Calendar mFecha;
    private String mHora;
    //private Cliente mCliente;
    //private Mascota mMascota;
    
    public Cita(){
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getIdCliente() {
        return mIdCliente;
    }

    public void setIdCliente(String idCliente) {
        mIdCliente = idCliente;
    }

    public String getIdMascota() {
        return mIdMascota;
    }

    public void setIdMascota(String idMascota) {
        mIdMascota = idMascota;
    }

    public String getIdDoctor() {
        return mIdDoctor;
    }

    public void setIdDoctor(String idDoctor) {
        mIdDoctor = idDoctor;
    }

    public Calendar getFecha() {
        return mFecha;
    }

    public void setFecha(Calendar fecha) {
        mFecha = fecha;
    }

    public String getHora() {
        return mHora;
    }

    public void setHora(String hora) {
        mHora = hora;
    }
    
    

    /*public Cliente getCliente() {
        return mCliente;
    }

    public void setCliente(Cliente mCliente) {
        this.mCliente = mCliente;
    }

    public Mascota getMascota() {
        return mMascota;
    }

    public void setMascota(Mascota mascota) {
        mMascota = mascota;
    }*/
 
    public void insert() throws Exception{
        
        //String date=mFecha.get(Calendar.YEAR)+"-"+(mFecha.get(Calendar.MONTH)+1)+"-"+mFecha.get(Calendar.DAY_OF_MONTH)+" "+mFecha.get(Calendar.HOUR)+":"+mFecha.get(Calendar.MINUTE)+":"+mFecha.get(Calendar.SECOND);        
        String date=mFecha.get(Calendar.YEAR)+"-"+(mFecha.get(Calendar.MONTH)+1)+"-"+mFecha.get(Calendar.DAY_OF_MONTH)+" "+mHora+":0";  
        
        ArrayList<ArrayList<String>> resultList=Dao.select("*", "CITA", "FECHA='"+date+"' AND ID_DOCTOR="+mIdDoctor);
        
        
        if(resultList.isEmpty())
            Dao.insert("FECHA,ID_DOCTOR,ID_MASCOTA,ID_CLIENTE",new String[]{date,mIdDoctor,mIdMascota,mIdCliente},"CITA");
        else 
            throw new Exception("No se puede registrar la cita con esa fecha");
          
    }    
    
    public static ArrayList<Vector<String>> getCitaList(int doctorId,Calendar desde,Calendar hasta){
        ArrayList<Vector<String>> citaList= new ArrayList();        
        Vector<String> auxCita;
        
        ArrayList<String> doctorList=new ArrayList<>();
        doctorList.add("Dr.Ramirez");
        doctorList.add("Dr.Torres");
        doctorList.add("Dr.Gomez");
        doctorList.add("Dr.Nano");
        
        String strDesde=desde.get(Calendar.YEAR)+"-"+(desde.get(Calendar.MONTH)+1)+"-"+desde.get(Calendar.DAY_OF_MONTH);
        String strHasta=hasta.get(Calendar.YEAR)+"-"+(hasta.get(Calendar.MONTH)+1)+"-"+hasta.get(Calendar.DAY_OF_MONTH);        
        // "FECHA,ID_DOCTOR,CITA.ID_MASCOTA,ID_CLIENTE", 
          ArrayList<ArrayList<String>> resultList=Dao.select(
                  "ID_DOCTOR,PROPIETARIO_DNI,MASCOTA.NOMBRE,FECHA", 
                  new String[]{"CITA","MASCOTA"},
                  new String[]{"MASCOTA.ID_MASCOTA","CITA.ID_MASCOTA"} ,                 
                  ((doctorId!=-1)?"ID_DOCTOR="+doctorId+" AND ":"")+" CITA.FECHA BETWEEN DATE('"+strDesde+"') AND DATE('"+strHasta+"')"
                          );
        
        for(ArrayList<String> row: resultList){
            auxCita=new Vector();
            auxCita.add(doctorList.get(Integer.parseInt(row.get(0))));
            auxCita.add(row.get(1));
            auxCita.add(row.get(2));
            auxCita.add(row.get(3));
            citaList.add(auxCita);
        }
        
        return citaList;
    }
    
    
}

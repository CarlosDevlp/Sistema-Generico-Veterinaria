/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


/**
 *
 * @author Carlos
 */
public class Servicio {
    private String mId;
    private String mMascotaId;
    private String mClienteId;
    private String mTipoServicioId;
    //private ArrayList<String> mServicioIdList;
    //private String mIgv;
    //private String mMontoTotal;
    private float mSubTotal;
    private boolean mEstado;
    
    public Servicio (){        
    }

    public String getId() {
        
        return mId;
    }

    public void setId(String id) {
        
        mId = id;
    }

    public String getMascotaId() {
        return mMascotaId;
    }

    public void setMascotaId(String mascotaId) {
        mMascotaId = mascotaId;
    }

    public String getClienteId() {
        return mClienteId;
    }

    public void setClienteId(String clienteId) {
        mClienteId = clienteId;
    }

    public String getTipoServicioId() {
        return mTipoServicioId;
    }

    public void setTipoServicioId(String tipoServicioId) {
        mTipoServicioId = tipoServicioId;
    }

    
    
    public float getSubTotal() {    
        return mSubTotal;
    }

    /*
    public ArrayList<String> getServicioIdList() {
    return mServicioIdList;
    }
    public void setServicioIdList(ArrayList<String> servicioIdList) {
    mServicioIdList = servicioIdList;
    }
    public void addServicioId(String servicioId) {
    mServicioIdList.add(servicioId);
    }
    public void removeServicioId(int index) {
    mServicioIdList.remove(index);
    }
    public String getIgv() {
    return mIgv;
    }
    public void setIgv(String igv) {
    mIgv = igv;
    }
    public String getMontoTotal() {
    return mMontoTotal;
    }
    public void setMontoTotal(String montoTotal) {
    mMontoTotal = montoTotal;
    }
     */
    public void setSubTotal(float subTotal) {
        mSubTotal = subTotal;
    }

    public boolean isEstado() {
        return mEstado;
    }

    public void setEstado(boolean estado) {
        mEstado = estado;
    }

    //gurdar concepto en la base de datos
    public void insert() throws Exception{
        Dao.insert("ID_MASCOTA,ID_CLIENTE,ID_TIPO_SERVICIO,SUBTOTAL,ESTADO",
                    new String[]{mMascotaId,mClienteId,mTipoServicioId,mSubTotal+"","PENDIENTE"},"SERVICIO");
        //getGeneratedId();
        //for(String servicioId:mServicioIdList)
          //  Dao.insert("ID_SERVICIO,ID_TIPO_SERVICIO",new String[]{mId,servicioId},"DETALLE_SERVICIO");
        
    }
    
    public String getGeneratedId(){
        if(mId==null){            
            mId=Dao.selectLastRow("ID_SERVICIO","ID_SERVICIO","SERVICIO").get(0);
        }           
        return mId;
    }
}

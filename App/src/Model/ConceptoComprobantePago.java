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
public class ConceptoComprobantePago {
    private String mId;
    private String mMascotaId;
    private String mClienteId;
    private String mServicioId;
    private String mIgv;
    private String mMontoTotal;
    private boolean mEstado;
    
    public ConceptoComprobantePago (){
    
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

    public String getServicioId() {
        return mServicioId;
    }

    public void setServicioId(String servicioId) {
        mServicioId = servicioId;
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

    public boolean isEstado() {
        return mEstado;
    }

    public void setEstado(boolean estado) {
        mEstado = estado;
    }

    //gurdar concepto en la base de datos
    public void insert(){
        Dao.insert("IGV,MONTO_TOTAL,SERVICIO_ID_SERVICIO,MASCOTA_ID_MASCOTA,ID_CLIENTE,ESTADO",new String[]{mIgv,mMontoTotal,mServicioId,mMascotaId,mClienteId,"0"},"CONCEPTO_DE_COMPROBANTE_DE_PAGO");
    }
    
    public String getGeneratedId(){
        if(mId==null){            
            mId=Dao.selectLastRow("ID_CONCEPTO_DE_COMPROBANTE_DE_PAGO","ID_CONCEPTO_DE_COMPROBANTE_DE_PAGO","CONCEPTO_DE_COMPROBANTE_DE_PAGO").get(0);        
        }   
        
        return mId;
    }
}

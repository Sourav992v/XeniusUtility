package radius.xenius.sems.feeder;

/**
 * Created by Pratima Singh on 16-02-2017.
 */

public class Feeder_data {
    private String feeder_name;
    private String last_reading_updated;
    private String serial_no;
    private String KVAh;
    private String grid_load_sanctioned;
    private Double instant_cum_KVA;
    private Double R_Voltage;
    private Double Y_Voltage;
    private Double B_Voltage;
    private Double R_Current;
    private Double Y_Current;
    private Double B_Current;
    private Double R_PF;
    private Double Y_PF;
    private Double B_PF;
    private Double cumm_pf;
    private Double max_demand_KVA;
    private String uom;
    private String MC_UID;
    private int digital_input2;
    private int digital_input3;
    private int meter_ct_mf;
    private int Hrs;

    public int getHrs() {
        return Hrs;
    }

    public void setHrs(int hrs) {
        Hrs = hrs;
    }

    public int getMeter_ct_mf() {
        return meter_ct_mf;
    }

    public void setMeter_ct_mf(int meter_ct_mf) {
        this.meter_ct_mf = meter_ct_mf;
    }

    public int getDigital_input2() {
        return digital_input2;
    }

    public void setDigital_input2(int digital_input2) {
        this.digital_input2 = digital_input2;
    }

    public int getDigital_input3() {
        return digital_input3;
    }

    public void setDigital_input3(int digital_input3) {
        this.digital_input3 = digital_input3;
    }

    public String getMC_UID() {
        return MC_UID;
    }

    public void setMC_UID(String MC_UID) {
        this.MC_UID = MC_UID;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

      public Double getMax_demand_KVA() {
        return max_demand_KVA;
    }

    public void setMax_demand_KVA(Double max_demand_KVA) {
        this.max_demand_KVA = max_demand_KVA;
    }

    public Double getR_PF() {
        return R_PF;
    }

    public void setR_PF(Double r_PF) {
        R_PF = r_PF;
    }

    public Double getB_Current() {
        return B_Current;
    }

    public void setB_Current(Double b_Current) {
        B_Current = b_Current;
    }

    public Double getY_PF() {
        return Y_PF;
    }

    public void setY_PF(Double y_PF) {
        Y_PF = y_PF;
    }

    public Double getB_PF() {
        return B_PF;
    }

    public void setB_PF(Double b_PF) {
        B_PF = b_PF;
    }
    public Double getCumm_pf() {
        return cumm_pf;
    }

    public void setCumm_pf(Double cumm_pf) {
        this.cumm_pf = cumm_pf;
    }

    public Double getR_Voltage() {
        return R_Voltage;
    }

    public void setR_Voltage(Double r_Voltage) {
        R_Voltage = r_Voltage;
    }

    public Double getY_Voltage() {
        return Y_Voltage;
    }

    public void setY_Voltage(Double y_Voltage) {
        Y_Voltage = y_Voltage;
    }

    public Double getR_Current() {
        return R_Current;
    }

    public void setR_Current(Double r_Current) {
        R_Current = r_Current;
    }

    public Double getB_Voltage() {
        return B_Voltage;
    }

    public void setB_Voltage(Double b_Voltage) {
        B_Voltage = b_Voltage;
    }

    public Double getY_Current() {
        return Y_Current;
    }

    public void setY_Current(Double y_Current) {
        Y_Current = y_Current;
    }


    public Double getInstant_cum_KVA() {
        return instant_cum_KVA;
    }

    public void setInstant_cum_KVA(Double instant_cum_KVA) {
        this.instant_cum_KVA = instant_cum_KVA;
    }

    public String getGrid_load_sanctioned() {
        return grid_load_sanctioned;
    }

    public void setGrid_load_sanctioned(String grid_load_sanctioned) {
        this.grid_load_sanctioned = grid_load_sanctioned;
    }


    public String getKVAh() {
        return KVAh;
    }

    public void setKVAh(String KVAh) {
        this.KVAh = KVAh;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public void setFeeder_name(String feeder_name) {
        this.feeder_name = feeder_name;
    }

    public String getFeeder_name() {
        return feeder_name;
    }

    public String getLast_reading_updated() {
        return last_reading_updated;
    }

    public void setLast_reading_updated(String last_reading_updated) {
        this.last_reading_updated = last_reading_updated;
    }

}

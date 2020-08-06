package org.alium.trackerlibrary.retrofit;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class AliumData {

    private JSONArray dim = new JSONArray();// [] {"", "button", "FCM Token", "×"}; //null;               //Element on which action is done.
    private String did = "";                 //device_id || android_id
    private String bvrs = "";                //build_version
    private String pth = "com.example.loginapp.MainActivity";//null;                 //screen/path/route
    private String scrnsz = "";              //screen_size
    private String orgs = "";                //operating_system
    private JSONArray gloc = new JSONArray();//new Float[]{};             //geo_location       --------- Based on App Permissions
    private String st = "";                  //state              --------- Based on App Permissions
    private String ct = "";                  //city               --------- Based on App Permissions
    private String ctry = "";                //country            --------- Based on App Permissions
    private String rgn = "";                 //region             --------- Based on App Permissions
    private String ntwp = "";                //network provider   --------- Based on App Permissions
    private String ssn ="sd4xg5s-44f5-54edf-65d65" ;//null;                 //session
    private String tsls = "01:50 pm";//null;    //time since last login/session
    private String aId = "";                 //app_id
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy'T'hh:mm:ss'Z'")
//    @JSONField(format = "MM/dd/yyyy hh:mm:ss")
    private String aitd = "";                 //app install date
    private String hnm = "My Local Host"; //  null;                 //current_hostname
    private String uia = "";                 //user ip_address
    private static String vstid ;              //visitor id
    private String ua = "";                  //user_agent
    private String cmp = "";                 //company_name
    private Long tz = 0L;//(Timestamp) new Date();                  //timezone
    private String evnt = "click";//null;      //event_name
    private String fcm = "";                   //FCM token

    public AliumData(){

    }

    public AliumData(JSONArray dim, String did, String bvrs, String pth, String scrnsz, String orgs, JSONArray gloc, String st, String ct, String ctry, String rgn, String ntwp, String ssn, String tsls, String aId, String aitd, String hnm, String uia, String ua, String cmp, Long tz, String evnt, String fcm) {
        this.dim = dim;
        this.did = did;
        this.bvrs = bvrs;
        this.pth = pth;
        this.scrnsz = scrnsz;
        this.orgs = orgs;
        this.gloc = gloc;
        this.st = st;
        this.ct = ct;
        this.ctry = ctry;
        this.rgn = rgn;
        this.ntwp = ntwp;
        this.ssn = ssn;
        this.tsls = tsls;
        this.aId = aId;
        this.aitd = aitd;
        this.hnm = hnm;
        this.uia = uia;
        this.ua = ua;
        this.cmp = cmp;
        this.tz = tz;
        this.evnt = evnt;
        this.fcm = fcm;
    }


    public JSONArray getDim() {
        return dim;
    }

    public void setDim(JSONArray dim) {
        this.dim = dim;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getBvrs() {
        return bvrs;
    }

    public void setBvrs(String bvrs) {
        this.bvrs = bvrs;
    }

    public String getPth() {
        return pth;
    }

    public void setPth(String pth) {
        this.pth = pth;
    }

    public String getScrnsz() {
        return scrnsz;
    }

    public void setScrnsz(String scrnsz) {
        this.scrnsz = scrnsz;
    }

    public String getOrgs() {
        return orgs;
    }

    public void setOrgs(String orgs) {
        this.orgs = orgs;
    }

    public JSONArray getGloc() {
        return gloc;
    }

    public void setGloc(JSONArray gloc) {
        this.gloc = gloc;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getCtry() {
        return ctry;
    }

    public void setCtry(String ctry) {
        this.ctry = ctry;
    }

    public String getRgn() {
        return rgn;
    }

    public void setRgn(String rgn) {
        this.rgn = rgn;
    }

    public String getNtwp() {
        return ntwp;
    }

    public void setNtwp(String ntwp) {
        this.ntwp = ntwp;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getTsls() {
        return tsls;
    }

    public void setTsls(String tsls) {
        this.tsls = tsls;
    }

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String getAitd() {
        return aitd;
    }

    public void setAitd(String aitd) {
        this.aitd = aitd;
    }

    public String getHnm() {
        return hnm;
    }

    public void setHnm(String hnm) {
        this.hnm = hnm;
    }

    public String getUia() {
        return uia;
    }

    public void setUia(String uia) {
        this.uia = uia;
    }

    public static String getVstid() {
        return vstid;
    }

    public static void setVstid(String vstid) {
        AliumData.vstid = vstid;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getCmp() {
        return cmp;
    }

    public void setCmp(String cmp) {
        this.cmp = cmp;
    }

    public Long getTz() {
        return tz;
    }

    public void setTz(Long tz) {
        this.tz = tz;
    }

    public String getEvnt() {
        return evnt;
    }

    public void setEvnt(String evnt) {
        this.evnt = evnt;
    }

    public String getFcm() {
        return fcm;
    }

    public void setFcm(String fcm) {
        this.fcm = fcm;
    }


  @Override
    public String toString() {
        return "AliumData{" +
                "dim=" + dim +
                ", did='" + did + '\'' +
                ", bvrs='" + bvrs + '\'' +
                ", pth='" + pth + '\'' +
                ", scrnsz='" + scrnsz + '\'' +
                ", orgs='" + orgs + '\'' +
                ", gloc=" + gloc +
                ", st='" + st + '\'' +
                ", ct='" + ct + '\'' +
                ", ctry='" + ctry + '\'' +
                ", rgn='" + rgn + '\'' +
                ", ntwp='" + ntwp + '\'' +
                ", ssn='" + ssn + '\'' +
                ", tsls='" + tsls + '\'' +
                ", aId='" + aId + '\'' +
                ", aitd='" + aitd + '\'' +
                ", hnm='" + hnm + '\'' +
                ", uia='" + uia + '\'' +
                ", ua='" + ua + '\'' +
                ", cmp='" + cmp + '\'' +
                ", tz=" + tz +
                ", evnt='" + evnt + '\'' +
                ", fcm='" + fcm + '\'' +
                '}';
    }


    public JSONObject toJSON() throws JSONException {

        JSONObject obj = new JSONObject();
        obj.put("dim", dim);
        obj.put("did", did);
        obj.put("bvrs", bvrs);
        obj.put("pth", pth);
        obj.put("scrnsz", scrnsz);
        obj.put("orgs",orgs);
        obj.put("gloc",gloc);
        obj.put("st", st);
        obj.put("ct", ct);
        obj.put("ctry", ctry);
        obj.put("rgn", rgn);
        obj.put("ntwp", ntwp);
        obj.put("ssn", ssn);
        obj.put("tsls", tsls);
        obj.put("aId", aId);
        obj.put("aitd", aitd);
        obj.put("hnm", hnm);
        obj.put("uia", uia);
        obj.put("vstid", vstid);
        obj.put("ua", ua);
        obj.put("cmp", cmp);
        obj.put("tz", tz);
        obj.put("event", evnt);
        obj.put("fcm", fcm);
        return obj;
    }
}

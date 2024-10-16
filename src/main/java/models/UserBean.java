package models;


import java.io.Serializable;

/**
 * User bean represents the user connected to the Grit Academy Portal
 */
public class UserBean implements Serializable {

    //shoudl be set to the same is it is in the DB
    private String id = "0";
    private String name= "";
    private USER_TYPE userType;
    private PRIVILEGE_TYPE privilegeType = PRIVILEGE_TYPE.user;
    private STATE_TYPE stateType = STATE_TYPE.anonymous;

    public UserBean(){}
    public UserBean(String id, USER_TYPE userType, String privilegeType, STATE_TYPE stateType){
        this.id=id;
        this.userType=userType;
        if(privilegeType.equals("admin")){
            this.privilegeType = PRIVILEGE_TYPE.admin;
        } else if(privilegeType.equals("superadmin")){
            this.privilegeType = PRIVILEGE_TYPE.superadmin;
        }
        this.stateType=stateType;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setStateType(STATE_TYPE stateType) {
        this.stateType = stateType;
    }

    public void setPrivilegeType(PRIVILEGE_TYPE privilegeType) {
        this.privilegeType = privilegeType;
    }

    public void setUserType(USER_TYPE userType) {
        this.userType = userType;
    }

    public PRIVILEGE_TYPE getPrivilegeType() {
        return privilegeType;
    }

    public STATE_TYPE getStateType() {
        return stateType;
    }

    public USER_TYPE getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return "userType: "+userType + " privilegeType: " + privilegeType + " stateType: "  + stateType;
    }
}




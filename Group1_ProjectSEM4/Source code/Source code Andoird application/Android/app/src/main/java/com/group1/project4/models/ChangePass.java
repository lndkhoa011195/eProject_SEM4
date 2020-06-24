package com.group1.project4.models;

public class ChangePass {
    Integer customerId;
    String oldPassword;
    String newPassword;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public ChangePass(Integer customerId, String oldPassword, String newPassword) {
        this.customerId = customerId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public ChangePass() {
    }
}

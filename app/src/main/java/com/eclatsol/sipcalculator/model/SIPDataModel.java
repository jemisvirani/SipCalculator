package com.eclatsol.sipcalculator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class SIPDataModel implements Serializable {
    private String currentValue;
    ArrayList<HashMap<String, String>> detalList = null;
    String finalClosingAmount;
    String maturityDate;
    String selectedType;
    String strAmountInvested;
    String strExpectedAmount;
    String strWealthGain;

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String str) {
        maturityDate = str;
    }

    public String getFinalClosingAmount() {
        return finalClosingAmount;
    }

    public void setFinalClosingAmount(String str) {
        finalClosingAmount = str;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String str) {
        selectedType = str;
    }

    public String getStrAmountInvested() {
        return strAmountInvested;
    }

    public void setStrAmountInvested(String str) {
        strAmountInvested = str;
    }

    public String getStrExpectedAmount() {
        return strExpectedAmount;
    }

    public void setStrExpectedAmount(String str) {
        strExpectedAmount = str;
    }

    public String getStrWealthGain() {
        return strWealthGain;
    }

    public void setStrWealthGain(String str) {
        strWealthGain = str;
    }

    public ArrayList<HashMap<String, String>> getDetalList() {
        return detalList;
    }

    public void setDetalList(ArrayList<HashMap<String, String>> arrayList) {
        detalList = arrayList;
    }

    public String toString() {
        return "SIPDataModel{maturityDate='" + this.maturityDate + "', finalClosingAmount='" + this.finalClosingAmount + "', selectedType='" + this.selectedType + "', strAmountInvested='" + this.strAmountInvested + "', strExpectedAmount='" + this.strExpectedAmount + "', strWealthGain='" + this.strWealthGain + "', detalList=" + this.detalList + '}';
    }

    public void setCurrentValue(String str) {
        this.currentValue = str;
    }

    public String getCurrentValue() {
        return this.currentValue;
    }
}

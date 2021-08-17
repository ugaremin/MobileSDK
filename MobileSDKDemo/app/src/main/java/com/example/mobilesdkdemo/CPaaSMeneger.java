package com.example.mobilesdkdemo;

import com.rbbn.cpaas.mobile.CPaaS;
import com.rbbn.cpaas.mobile.utilities.services.ServiceInfo;
import com.rbbn.cpaas.mobile.utilities.services.ServiceType;

import java.util.ArrayList;
import java.util.List;

public class CPaaSMeneger {

    private static CPaaSMeneger instance = new CPaaSMeneger();

    private  CPaaS cPaaS;

    public static CPaaSMeneger getInstance(){

        if (instance==null){
            instance=new CPaaSMeneger();
        }
        return instance;
    }

    private CPaaSMeneger() {

    }

    public  CPaaS getCpaas()
    {
        return cPaaS;
    }

    public void setCpaas(CPaaS cPaaS) {
        this.cPaaS = cPaaS;
    }


}

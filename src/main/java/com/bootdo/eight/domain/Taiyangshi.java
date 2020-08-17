package com.bootdo.eight.domain;

import java.time.LocalDateTime;

/**
 * Created by god on 2020/1/4.
 */
public class Taiyangshi {
    private double jingdu;
    private double weidu;
    private LocalDateTime normalTaiyang;
    private LocalDateTime realTaiyang;

    public double getJingdu() {
        return jingdu;
    }

    public void setJingdu(double jingdu) {
        this.jingdu = jingdu;
    }

    public double getWeidu() {
        return weidu;
    }

    public void setWeidu(double weidu) {
        this.weidu = weidu;
    }

    public LocalDateTime getNormalTaiyang() {
        return normalTaiyang;
    }

    public void setNormalTaiyang(LocalDateTime normalTaiyang) {
        this.normalTaiyang = normalTaiyang;
    }

    public LocalDateTime getRealTaiyang() {
        return realTaiyang;
    }

    public void setRealTaiyang(LocalDateTime realTaiyang) {
        this.realTaiyang = realTaiyang;
    }
}

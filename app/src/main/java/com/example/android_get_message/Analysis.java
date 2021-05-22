package com.example.android_get_message;

public class Analysis {
    public float pm2_5 = -1;
    public float pm10 = -1;
    public int aqi;
    public int iaqi_pm2_5;
    public int iaqi_pm10;
    public String iaqi_pm2_5_desc;
    public String iaqi_pm10_desc;
    public String desc;

    public Analysis() {
    }

    public void calculate() {
        if (pm2_5 < 35) {
            iaqi_pm2_5 = 0;
            iaqi_pm2_5_desc = "0 ~ 50";
        } else if (pm2_5 < 75) {
            iaqi_pm2_5 = 50;
            iaqi_pm2_5_desc = "50 ~ 100";
        } else if (pm2_5 < 115) {
            iaqi_pm2_5 = 100;
            iaqi_pm2_5_desc = "100 ~ 150";
        } else if (pm2_5 < 150) {
            iaqi_pm2_5 = 150;
            iaqi_pm2_5_desc = "150 ~ 200";
        } else if (pm2_5 < 250) {
            iaqi_pm2_5 = 200;
            iaqi_pm2_5_desc = "200 ~ 300";
        } else if (pm2_5 < 350) {
            iaqi_pm2_5 = 300;
            iaqi_pm2_5_desc = "300 ~ 400";
        } else if (pm2_5 < 500) {
            iaqi_pm2_5 = 400;
            iaqi_pm2_5_desc = "400 ~ 500";
        } else {
            iaqi_pm2_5 = 500;
            iaqi_pm2_5_desc = "> 500";
        }

        if (pm10 < 50) {
            iaqi_pm10 = 0;
            iaqi_pm10_desc = "0 ~ 50";
        } else if (pm10 < 150) {
            iaqi_pm10 = 50;
            iaqi_pm10_desc = "50 ~ 100";
        } else if (pm10 < 250) {
            iaqi_pm10 = 100;
            iaqi_pm10_desc = "100 ~ 150";
        } else if (pm10 < 350) {
            iaqi_pm10 = 150;
            iaqi_pm10_desc = "150 ~ 200";
        } else if (pm10 < 420) {
            iaqi_pm10 = 200;
            iaqi_pm10_desc = "200 ~ 300";
        } else if (pm10 < 500) {
            iaqi_pm10 = 300;
            iaqi_pm10_desc = "300 ~ 400";
        } else if (pm10 < 600) {
            iaqi_pm10 = 400;
            iaqi_pm10_desc = "400 ~ 500";
        } else {
            iaqi_pm10 = 500;
            iaqi_pm10_desc = "> 500";
        }

        aqi = Math.max(iaqi_pm2_5, iaqi_pm10);
        calculateDesc(aqi);
    }

    public void calculateDesc(int aqi) {
        switch (aqi) {
            case 0:
                desc = "AQI 级别：一级，优；\n对健康的影响：空气质量令人满意，基本无空气污染\n建议采取的措施：各类人群可正常活动";
                break;
            case 50:
                desc = "AQI 级别：二级，良；\n对健康的影响：空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响\n建议采取的措施：空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响";
                break;
            case 100:
                desc = "AQI 级别：三级，轻度污染；\n对健康的影响：易感人群症状有轻度加剧，健康人群出现刺激症状\n儿童、老年人及心脏病、呼吸系统疾病患者应减少长时间、高强度的户外锻炼";
                break;
            case 150:
                desc = "AQI 级别：四级，中度污染；\n对健康的影响：进一步加剧易感人群症状，可能对健康人群心脏、呼吸系统有影响\n建议采取的措施：儿童、老年人及心脏病、呼吸系统疾病患者应避免长时间、高强度的户外锻炼，一般人群适量减少户外运动";
                break;
            case 200:
                desc = "AQI 级别：五级，重度污染；\n对健康的影响：心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状\n建议采取的措施：儿童、老年人及心脏病、呼吸系统疾病患者应停留在室内，停止户外活动，一般人群应避免户外活动";
                break;
            default:
                desc = "AQI 级别：六级，严重污染；\n对健康的影响：健康人群运动耐受力降低，有明显强烈症状，提前出现某些疾病\n建议采取的措施：儿童、老年人及心脏病、呼吸系统疾病患者应停留在室内，避免体力消耗，一般人群应避免户外活动";
                break;
        }
    }
}

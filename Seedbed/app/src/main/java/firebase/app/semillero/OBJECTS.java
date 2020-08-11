package firebase.app.semillero;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;


class SENSORS {

     String temperature;
    String humidity;
    String humidityground;
    String CO2;
    String LDR;
    String pumpCurrentState;
    String lightCurrentState;
    String ventilatorCurrentState;
    String humidifierCurrentState;
    String gasAlert;
    String waterLevel;


    public SENSORS( String temperature,  String humidity,  String humidityground,  String CO2,  String LDR, String pumpCurrentState, String lightCurrentState,String ventilatorCurrentState, String humidifierCurrentState, String gasAlert,String waterLevel) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.humidityground = humidityground;
        this.CO2 = CO2;
        this.LDR = LDR;
        this.pumpCurrentState=pumpCurrentState;
        this.lightCurrentState=lightCurrentState;
        this.ventilatorCurrentState=ventilatorCurrentState;
        this.humidifierCurrentState=humidifierCurrentState;
        this.gasAlert=gasAlert;
        this.waterLevel=waterLevel;
    }

    public SENSORS() {
    }

    public  String getTemperature() {
        return temperature;
    }
    public  String getHumidityAir() { return humidity; }
    public  String getHumidityGround() { return humidityground; }
    public  String getCO2() { return CO2; }
    public  String getLDR() { return LDR; }
    public  String getPumpCurrentState() { return pumpCurrentState; }
    public  String getLightCurrentState() { return lightCurrentState; }
    public  String getVentilatorCurrentState() { return ventilatorCurrentState; }
    public  String getHumidifierCurrentState() { return humidifierCurrentState; }
    public  String getGasAlert() { return gasAlert; }
    public  String getWaterLevel() { return waterLevel; }
}

class ACTUATORS  {
    public String pump;
    public String ventilator;
    public String lights;
    public String humidifier;

    public ACTUATORS( String pump,  String ventilator,  String lights,  String humidifier) {
        this.pump= pump;
        this.ventilator = ventilator;
        this.lights = lights;
        this.humidifier = humidifier;
    }
    public ACTUATORS() {
    }
    public  String getPump() {
        return pump;
    }

    public  String getVentilator() {
        return ventilator;
    }

    public  String getLights() {
        return lights;
    }
    public  String getHumidifier() {
        return humidifier;
    }
}

class USERS {

    String name;
    String email;
    String password;
    ServerTimestamp createdAt;
    String token;


    public USERS( String name,  String email,  String password,   ServerTimestamp createdAt,  String token) {
        this.name = name;
        this.password =password;
        this.email = email;
        this.createdAt = createdAt;
        this.token = token;
    }

    public USERS() {
    }

    public  String getName() {
        return name;
    }

    public  String getPassword() {
        return password;
    }

    public  String getEmail() {
        return email;
    }

    public ServerTimestamp getCreatedAt() { return createdAt; }
    public  String getToken() { return token; }

}



class DataPoint {
     private com.google.firebase.Timestamp timestamp;
    String temperature;
    String humidityRef;
     String humidityground;
     String CO2;
     String LDR;

    public DataPoint(Timestamp timestamp, String temperature, String humidityRef, String humidityground, String CO2, String LDR) {
         this.timestamp = timestamp;
        this.temperature = temperature;
        this.humidityRef = humidityRef;
        this.humidityground = humidityground;
        this.CO2 = CO2;
        this.LDR = LDR;
    }

    public DataPoint() {
    }

    public Timestamp getTimestamp() { return timestamp; }
    public  String getTemperature() {
        return temperature;
    }
    public  String getHumidityAir() {
        return humidityRef;
    }
    public  String getHumidityGround() {
        return humidityground;
    }
    public  String getCO2() { return CO2; }
    public  String getLDR() { return LDR; }
}

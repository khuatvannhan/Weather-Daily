package com.nhankv.weather.model;

import java.util.ArrayList;

/**
 * Created by snowflower on 20/09/2016.
 */

public class Forecast {
    public double latitude;
    public double longitude;
    public Currently currently = new Currently();
    public ForecastHourly hourly = new ForecastHourly();
    public ForecastDaily daily = new ForecastDaily();

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Currently getCurrently() {
        return currently;
    }

    public void setCurrently(Currently currently) {
        this.currently = currently;
    }

    public ForecastHourly getHourly() {
        return hourly;
    }

    public void setHourly(ForecastHourly hourly) {
        this.hourly = hourly;
    }

    public ForecastDaily getDaily() {
        return daily;
    }

    public void setDaily(ForecastDaily daily) {
        this.daily = daily;
    }

    public static class Currently {
        public long time;
        public String summary;
        public String icon;
        public double precipIntensity;
        public double precipProbability;
        public String precipType;
        public double temperature;
        public double apparentTemperature;
        public double dewPoint;
        public double humidity;
        public double windSpeed;
        public double windBearing;
        public double visibility;
        public double cloudCover;
        public double pressure;
        public double ozone;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public double getPrecipIntensity() {
            return precipIntensity;
        }

        public void setPrecipIntensity(double precipIntensity) {
            this.precipIntensity = precipIntensity;
        }

        public double getPrecipProbability() {
            return precipProbability;
        }

        public void setPrecipProbability(double precipProbability) {
            this.precipProbability = precipProbability;
        }

        public String getPrecipType() {
            return precipType;
        }

        public void setPrecipType(String precipType) {
            this.precipType = precipType;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public double getApparentTemperature() {
            return apparentTemperature;
        }

        public void setApparentTemperature(double apparentTemperature) {
            this.apparentTemperature = apparentTemperature;
        }

        public double getDewPoint() {
            return dewPoint;
        }

        public void setDewPoint(double dewPoint) {
            this.dewPoint = dewPoint;
        }

        public double getHumidity() {
            return humidity;
        }

        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }

        public double getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(double windSpeed) {
            this.windSpeed = windSpeed;
        }

        public double getWindBearing() {
            return windBearing;
        }

        public void setWindBearing(double windBearing) {
            this.windBearing = windBearing;
        }

        public double getVisibility() {
            return visibility;
        }

        public void setVisibility(double visibility) {
            this.visibility = visibility;
        }

        public double getCloudCover() {
            return cloudCover;
        }

        public void setCloudCover(double cloudCover) {
            this.cloudCover = cloudCover;
        }

        public double getPressure() {
            return pressure;
        }

        public void setPressure(double pressure) {
            this.pressure = pressure;
        }

        public double getOzone() {
            return ozone;
        }

        public void setOzone(double ozone) {
            this.ozone = ozone;
        }
    }

    public static class ForecastHourly {
        public String summary;
        public String icon;
        public ArrayList<HourlyData> data = new ArrayList<HourlyData>();

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public ArrayList<HourlyData> getData() {
            return data;
        }

        public void setData(ArrayList<HourlyData> data) {
            this.data = data;
        }

        public static class HourlyData extends Currently {

        }
    }

    public static class ForecastDaily {
        public String summary;
        public String icon;
        public ArrayList<DailyData> data = new ArrayList<DailyData>();

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public ArrayList<DailyData> getData() {
            return data;
        }

        public void setData(ArrayList<DailyData> daily) {
            this.data = daily;
        }

        public static class DailyData extends Currently{
            public double sunriseTime;
            public double sunsetTime;
            public double moonPhase;
            public double precipIntensityMax;
            public double precipIntensityMaxTime;
            public double temperatureMin;
            public double temperatureMinTime;
            public double temperatureMax;
            public double temperatureMaxTime;
            public double apparentTemperatureMin;
            public double apparentTemperatureMinTime;
            public double apparentTemperatureMax;
            public double apparentTemperatureMaxTime;

            public double getSunriseTime() {
                return sunriseTime;
            }

            public void setSunriseTime(double sunriseTime) {
                this.sunriseTime = sunriseTime;
            }

            public double getSunsetTime() {
                return sunsetTime;
            }

            public void setSunsetTime(double sunsetTime) {
                this.sunsetTime = sunsetTime;
            }

            public double getMoonPhase() {
                return moonPhase;
            }

            public void setMoonPhase(double moonPhase) {
                this.moonPhase = moonPhase;
            }

            public double getPrecipIntensityMax() {
                return precipIntensityMax;
            }

            public void setPrecipIntensityMax(double precipIntensityMax) {
                this.precipIntensityMax = precipIntensityMax;
            }

            public double getPrecipIntensityMaxTime() {
                return precipIntensityMaxTime;
            }

            public void setPrecipIntensityMaxTime(double precipIntensityMaxTime) {
                this.precipIntensityMaxTime = precipIntensityMaxTime;
            }

            public double getTemperatureMin() {
                return temperatureMin;
            }

            public void setTemperatureMin(double temperatureMin) {
                this.temperatureMin = temperatureMin;
            }

            public double getTemperatureMinTime() {
                return temperatureMinTime;
            }

            public void setTemperatureMinTime(double temperatureMinTime) {
                this.temperatureMinTime = temperatureMinTime;
            }

            public double getTemperatureMax() {
                return temperatureMax;
            }

            public void setTemperatureMax(double temperatureMax) {
                this.temperatureMax = temperatureMax;
            }

            public double getTemperatureMaxTime() {
                return temperatureMaxTime;
            }

            public void setTemperatureMaxTime(double temperatureMaxTime) {
                this.temperatureMaxTime = temperatureMaxTime;
            }

            public double getApparentTemperatureMin() {
                return apparentTemperatureMin;
            }

            public void setApparentTemperatureMin(double apparentTemperatureMin) {
                this.apparentTemperatureMin = apparentTemperatureMin;
            }

            public double getApparentTemperatureMinTime() {
                return apparentTemperatureMinTime;
            }

            public void setApparentTemperatureMinTime(double apparentTemperatureMinTime) {
                this.apparentTemperatureMinTime = apparentTemperatureMinTime;
            }

            public double getApparentTemperatureMax() {
                return apparentTemperatureMax;
            }

            public void setApparentTemperatureMax(double apparentTemperatureMax) {
                this.apparentTemperatureMax = apparentTemperatureMax;
            }

            public double getApparentTemperatureMaxTime() {
                return apparentTemperatureMaxTime;
            }

            public void setApparentTemperatureMaxTime(double apparentTemperatureMaxTime) {
                this.apparentTemperatureMaxTime = apparentTemperatureMaxTime;
            }
        }
    }

}

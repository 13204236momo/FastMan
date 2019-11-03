package com.tianshang.common.entity.personal;

import java.util.List;

public class AddressEntity {


    /**
     * province : 北京市
     * city : [{"name":"市辖区","city_id":"1","county":["东城区"]}]
     */

    private String province;
    private List<CityBean> city;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    public static class CityBean {
        /**
         * name : 市辖区
         * city_id : 1
         * county : ["东城区"]
         */

        private String name;
        private String city_id;
        private List<String> county;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public List<String> getCounty() {
            return county;
        }

        public void setCounty(List<String> county) {
            this.county = county;
        }
    }
}

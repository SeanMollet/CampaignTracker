/*
 * Copyright 2018 Malmoset, LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.malmoset.campaigndata.Loot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author sean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coins {

    public Coins(@JsonProperty("cp") String cp, @JsonProperty("sp") String sp, @JsonProperty("ep") String ep, @JsonProperty("gp") String gp, @JsonProperty("pp") String pp) {
        this.cp = cp;
        this.sp = sp;
        this.ep = ep;
        this.gp = gp;
        this.pp = pp;
    }

    public Coins() {
        this.cp = "";
        this.sp = "";
        this.ep = "";
        this.gp = "";
        this.pp = "";
    }
    private String cp;
    private String sp;
    private String ep;
    private String gp;
    private String pp;

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public String getEp() {
        return ep;
    }

    public void setEp(String ep) {
        this.ep = ep;
    }

    public String getGp() {
        return gp;
    }

    public void setGp(String gp) {
        this.gp = gp;
    }

    public String getPp() {
        return pp;
    }

    public void setPp(String pp) {
        this.pp = pp;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoytea.models.arasaac;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author miguel
 */
public class ArasaacObject {

    @JsonProperty("_id")
    private Integer id;

    @JsonProperty("_id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(Integer id) {
        this.id = id;
    }
}

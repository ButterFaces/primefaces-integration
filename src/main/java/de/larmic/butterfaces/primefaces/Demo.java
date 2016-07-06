package de.larmic.butterfaces.primefaces;

import de.larmic.butterfaces.model.text.AutoCompleteModel;
import de.larmic.butterfaces.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
@SuppressWarnings("serial")
public class Demo implements Serializable {

    private final List<String> autoCompleteValues = new ArrayList<String>();
    private String value;
    private int number;

    @PostConstruct
    public void init() {
        autoCompleteValues.add("test");
        autoCompleteValues.add("tetest");
        autoCompleteValues.add("test1 ButterFaces");
        autoCompleteValues.add("test2");
        autoCompleteValues.add("ButterFaces");
        autoCompleteValues.add("ButterFaces JSF");
        autoCompleteValues.add("ButterFaces Mojarra");
        autoCompleteValues.add("ButterFaces Component");
        autoCompleteValues.add("JSF");
        autoCompleteValues.add("JSF 2");
        autoCompleteValues.add("JSF 2.2");
    }

    public AutoCompleteModel getAutoCompleteModel() {
        return new AutoCompleteModel() {
            @Override
            public List<String> autoComplete(Object value) {
                final List<String> values = new ArrayList<String>();

                if (StringUtils.isNotEmpty(value.toString())) {
                    for (String autoCompleteValue : autoCompleteValues) {
                        if (autoCompleteValue.toLowerCase().contains(value.toString().toLowerCase())) {
                            values.add(autoCompleteValue);
                        }
                    }
                }

                return values;
            }
        };
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

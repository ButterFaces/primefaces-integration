# PrimeFaces integration

As well as ButterFaces [PrimeFaces](http://www.primefaces.org/) comes with jQuery. This does not work out of the box.

## Update
With ButterFaces 2.1.8 no additional configuration is needed. It just works out-of-the-box. For further version look below.

## Problem
ButterFaces deliveres css and javascript as first part of html head to allow custom class handling. PrimeFaces deliveres its own jQuery after ButterFaces, so ButterFaces jQuery plugins does not work anymore.

## Solution
You can add your own version of jQuery or use jQuery from ButterFaces. You have to disable jQuery from PrimeFaces. Unfortunately you can not disable jQuery by a simple web.xml parameter (like in ButterFaces), but you can remove it from head by a `javax.faces.event.SystemEventListener`.
```java
package de.larmic.butterfaces.primefaces.event;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

public class HandleResourceListener implements SystemEventListener {

    public boolean isListenerForSource(Object source) {
        return source instanceof UIViewRoot;
    }

    public void processEvent(SystemEvent event) throws AbortProcessingException {
        final UIViewRoot source = (UIViewRoot) event.getSource();
        final FacesContext context = FacesContext.getCurrentInstance();

        for (UIComponent resource : source.getComponentResources(context, "head")) {
            final String resourceLibrary = (String) resource.getAttributes().get("library");
            final String resourceName = (String) resource.getAttributes().get("name");
            if ("primefaces".equals(resourceLibrary) && "jquery/jquery.js".equals(resourceName)) {
                source.removeComponentResource(context, resource);
            }
        }
    }
}
```
Activate it in your `faces-config.xml`
```xml
<?xml version="1.0" encoding="UTF-8"?>
<faces-config version="2.0"
              xmlns="http://java.sun.com/xml/ns/javaee"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-facesconfig_2_0.xsd">

    <application>
        <system-event-listener>
            <system-event-listener-class>de.larmic.butterfaces.primefaces.event.HandleResourceListener</system-event-listener-class>
            <system-event-class>javax.faces.event.PreRenderViewEvent</system-event-class>
        </system-event-listener>
    </application>

</faces-config>
```

## Start showcase
```
mvn clean package wildfly-swarm:run
```
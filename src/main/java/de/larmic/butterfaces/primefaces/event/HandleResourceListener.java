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
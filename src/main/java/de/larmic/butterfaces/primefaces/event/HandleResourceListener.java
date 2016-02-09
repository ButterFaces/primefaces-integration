package de.larmic.butterfaces.primefaces.event;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import java.util.Iterator;

public class HandleResourceListener implements SystemEventListener {

    public boolean isListenerForSource(Object source) {
        return source instanceof UIViewRoot;
    }

    public void processEvent(SystemEvent event) throws AbortProcessingException {
        final UIViewRoot source = (UIViewRoot) event.getSource();
        final FacesContext context = FacesContext.getCurrentInstance();
        final Iterator<UIComponent> resourceIterator = source.getComponentResources(context, "head").iterator();

        while (resourceIterator.hasNext()) {
            final UIComponent resource = resourceIterator.next();
            final String resourceLibrary = (String) resource.getAttributes().get("library");
            final String resourceName = (String) resource.getAttributes().get("name");
            if ("primefaces".equals(resourceLibrary) && "jquery/jquery.js".equals(resourceName)) {
                source.removeComponentResource(context, resource);
                resourceIterator.remove();
            }
        }
    }
}
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.core.SchemaEntry;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdUtil;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDResourceImpl;


public class SchemaEntryImpl
		implements SchemaEntry {
	
	private final String location;
	
	private String namespace;
	
	private XSDResourceImpl schemaResource;
	
	private boolean unloaded = false;
	
	private SchemaResourceAdapter schemaResourceAdapter;
	
	
	SchemaEntryImpl(String location) {
		this.location = location;
		this.schemaResourceAdapter = new SchemaResourceAdapter();
		refresh();
	}
	
	
	public String getLocation() {
		return this.location;
	}
	
	public String getNamespace() {
		return this.namespace;
	}
	
	public boolean isLoaded() {
		return this.schemaResource != null;
	}
	
	public XsdSchema getXsdSchema(String namespace) {
		if (this.unloaded) {
			refresh();
			this.unloaded = false;
		}
		
		if (! StringTools.stringsAreEqual(this.namespace, namespace)) {
			return null;
		}
		
		if (this.schemaResource != null) {
			return XsdUtil.getSchema(this.schemaResource.getSchema());
		}
		
		return null;
	}
	
	public void refresh() {
		unload();
		
		String resolvedUri = XsdUtil.getResolvedUri(this.location);
		if (resolvedUri == null) {
			return;
		}
		
		XSDSchema schema = XsdUtil.buildXSDModel(resolvedUri);
		XSDResourceImpl schemaResource = (schema == null) ? null : (XSDResourceImpl) schema.eResource();
		
		if (schemaResource != null) {
			schemaResource.eAdapters().add(this.schemaResourceAdapter);
			this.schemaResource = schemaResource;
			String namespace = schema.getTargetNamespace();
			this.namespace = (namespace == null) ? "" : namespace;
		}
	}
	
	void unload() {
		if (this.schemaResource != null) {
			this.schemaResource.eAdapters().remove(this.schemaResourceAdapter);
		}
		this.unloaded = true;
		this.schemaResource = null;
	}
	
	void dispose() {
		if (this.schemaResource != null) {
			this.schemaResource.eAdapters().remove(this.schemaResourceAdapter);
		}
	}
	
	
	
	private class SchemaResourceAdapter
			extends AdapterImpl {
		
		@Override
		public void notifyChanged(Notification msg) {
			if (msg.getFeatureID(Resource.class) == Resource.RESOURCE__IS_LOADED
					&& msg.getNewBooleanValue() == false) {
				SchemaEntryImpl.this.unload();
			}
		}
	}
}

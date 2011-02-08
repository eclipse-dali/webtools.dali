/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.SchemaLibrary;
import org.eclipse.jpt.jaxb.core.xsd.XsdUtil;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDResourceImpl;

public class SchemaLibraryImpl
		implements SchemaLibrary {
	
	private JaxbProject project;
	
	private final Map<String, String> schemaLocations;
	
	private final Map<String, XSDResourceImpl> schemaResources;
	
	private Adapter schemaResourceAdapter = new SchemaResourceAdapter();
	
	
	SchemaLibraryImpl(JaxbProject project) {
		this.project = project;
		this.schemaLocations = new HashMap<String, String>();
		this.schemaResources = new HashMap<String, XSDResourceImpl>();
		readProjectPreferences();
	}
	
	
	public XSDSchema getSchema(String namespace) {
		String resolvedUri = getResolvedUri(namespace);
		if (resolvedUri == null) {
			return null;
		}
		
		XSDResourceImpl schemaResource = this.schemaResources.get(namespace);
		XSDSchema schema = (schemaResource == null) ? null : schemaResource.getSchema();
		if (schemaResource != null) {
			if (schema != null && schemaResource.getURI().toString().equals(resolvedUri) && schemaResource.isLoaded()) {
				return schema;
			}
			else {
				removeSchemaResource(namespace, schemaResource);
			}
		}
		
		return addSchema(namespace, resolvedUri);
	}
	
	protected String getResolvedUri(String namespace) {
		String location = this.schemaLocations.get(namespace);
		return XsdUtil.getResolvedUri(namespace, location);
	}
	
	protected XSDSchema addSchema(String namespace, String resolvedUri) {
		XSDSchema schema = XsdUtil.buildXSDModel(resolvedUri);
		XSDResourceImpl schemaResource = (XSDResourceImpl) schema.eResource();
		if (schemaResource != null) {
			schemaResource.eAdapters().add(this.schemaResourceAdapter);
			this.schemaResources.put(namespace, schemaResource);
			return schema;
		}
		return null;
	}
	
	protected void removeSchemaResource(XSDResourceImpl schemaResource) {
		for (String namespace : this.schemaResources.keySet()) {
			if (schemaResource.equals(this.schemaResources.get(namespace))) {
				removeSchemaResource(namespace, schemaResource);
			}
		}
	}
	
	protected void removeSchemaResource(String namespace, XSDResourceImpl schemaResource) {
		schemaResource.eAdapters().remove(this.schemaResourceAdapter);
		this.schemaResources.remove(namespace);
	}
	
	protected void readProjectPreferences() {
		Map<String, String> schemaMap = JptJaxbCorePlugin.getSchemaLocationMap(this.project.getProject());
		this.schemaLocations.putAll(schemaMap);
	}
	
	public Map<String, String> getSchemaLocations() {
		return Collections.unmodifiableMap(this.schemaLocations);
	}
	
	public void setSchemaLocations(Map<String, String> schemaLocations) {
		this.schemaLocations.clear();
		JptJaxbCorePlugin.setSchemaLocationMap(this.project.getProject(), schemaLocations);
		readProjectPreferences();
	}
	
	
	private class SchemaResourceAdapter
			extends AdapterImpl {
		
		@Override
		public void notifyChanged(Notification msg) {
			if (msg.getFeatureID(Resource.class) == Resource.RESOURCE__IS_LOADED
					&& msg.getNewBooleanValue() == false) {
				removeSchemaResource((XSDResourceImpl) msg.getNotifier());
			}
		}
	}
}

/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import java.io.IOException;
import java.net.MalformedURLException;
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
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.xml.core.internal.XMLCorePlugin;
import org.eclipse.wst.xml.core.internal.catalog.provisional.ICatalog;
import org.eclipse.wst.xsd.contentmodel.internal.XSDImpl;
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
		String resolvedUri = null;
		String location = this.schemaLocations.get(namespace);
		
		ICatalog catalog = XMLCorePlugin.getDefault().getDefaultXMLCatalog();
		
		if (! StringTools.stringIsEmpty(location)) {
			try {
				resolvedUri = catalog.resolveSystem(location);
				if (resolvedUri == null) {
					resolvedUri = catalog.resolveURI(location);
				}
			}
			catch (MalformedURLException me) {
				JptJaxbCorePlugin.log(me);
				resolvedUri = null;
			}
			catch (IOException ie) {
				JptJaxbCorePlugin.log(ie);
				resolvedUri = null;
			}
		}
		
		if (resolvedUri == null && namespace != null) {
			if ( ! (location != null && location.endsWith(".xsd"))) { //$NON-NLS-1$ 
				try {
					resolvedUri = catalog.resolvePublic(namespace, location);
					if (resolvedUri == null) {
						resolvedUri = catalog.resolveURI(namespace);
					}
				}
				catch (MalformedURLException me) {
					JptJaxbCorePlugin.log(me);
					resolvedUri = null;
				}
				catch (IOException ie) {
            		JptJaxbCorePlugin.log(ie);
					resolvedUri = null;
				}
			}
		}
		
		// if resolvedUri is null, assume the location is resolved
		return (resolvedUri != null) ? resolvedUri : location;
	}
	
	protected XSDSchema addSchema(String namespace, String resolvedUri) {
		XSDSchema schema = XSDImpl.buildXSDModel(resolvedUri);
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

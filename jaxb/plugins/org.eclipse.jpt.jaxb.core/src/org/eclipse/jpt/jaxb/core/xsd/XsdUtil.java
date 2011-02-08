/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors:
 *  	IBM Corporation - initial API and implementation
 *  		(copied mainly from org.eclipse.wst.xsd.contentmodel.internal.XSDImpl)
 *  	Oracle - extensions and modifications
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.xsd;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.wst.common.uriresolver.internal.provisional.URIResolverPlugin;
import org.eclipse.wst.xml.core.internal.XMLCorePlugin;
import org.eclipse.wst.xml.core.internal.catalog.provisional.ICatalog;
import org.eclipse.wst.xsd.contentmodel.internal.util.XSDSchemaLocatorAdapterFactory;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaContent;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.impl.XSDImportImpl;
import org.eclipse.xsd.impl.XSDSchemaImpl;
import org.eclipse.xsd.util.XSDConstants;
import org.eclipse.xsd.util.XSDResourceImpl;
import org.eclipse.xsd.util.XSDSwitch;

/**
 * Utility class for building XSD model and its extensions for JAXB
 */
public class XsdUtil {
	
	static final XsdAdapterFactoryImpl adapterFactory = new XsdAdapterFactoryImpl();
	
	public static String getResolvedUri(String namespace, String location) {
		String resolvedUri = null;
		
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
	
	
	/**
	 * Given uri for an XML Schema document, parse the document and build
	 * corresponding EMF object.
	 */
	public static XSDSchema buildXSDModel(String uriString) {
		XSDSchema xsdSchema = null;
		
		try {
			// if XML Schema for Schema is requested, get it through schema model 
			if (uriString.endsWith("2001/XMLSchema.xsd")) {
				xsdSchema = XSDSchemaImpl.getSchemaForSchema(XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001);			
			}
			else { 	
				ResourceSet resourceSet = new ResourceSetImpl();
				resourceSet.getAdapterFactories().add(new XSDSchemaLocatorAdapterFactory());
				
				URI uri = URI.createURI(uriString);   
				
				// CS : bug 113537 ensure we perform physical resolution before opening a stream for the resource
				String physicalLocation = URIResolverPlugin.createResolver().resolvePhysicalLocation("", "", uriString);       
				InputStream inputStream = resourceSet.getURIConverter().createInputStream(URI.createURI(physicalLocation));
				XSDResourceImpl resource = (XSDResourceImpl)resourceSet.createResource(URI.createURI("*.xsd"));
				resource.setURI(uri);
				resource.load(inputStream, null);         
				xsdSchema = resource.getSchema();      
			}
			handleImports(xsdSchema);
		}
		catch (Exception e) {
			JptJaxbCorePlugin.log(e);
		}
		return xsdSchema;
	}
	
	private static void handleImports(XSDSchema xsdSchema) {
		if (xsdSchema != null) {
			for (XSDSchemaContent content : xsdSchema.getContents()) {
				if (content instanceof XSDImportImpl) {
					XSDImportImpl anImport = (XSDImportImpl) content;
					try {
						if (anImport.getSchemaLocation() != null) {
							anImport.importSchema();
						}
					}
					catch (Exception e) {
						JptJaxbCorePlugin.log(e);
					}
				}
			}
		}
	}
	
	public static Object getAdapter(Notifier notifier) {
		return adapterFactory.adapt(notifier);
	}
	
	
	/**
	 * The Factory for the XSD adapter model. It provides a create method for each
	 * non-abstract class of the model.
	 */
	public static class XsdAdapterFactoryImpl
			extends AdapterFactoryImpl {
		
		@Override
		public Adapter createAdapter(Notifier target) {
			XSDSwitch xsdSwitch = new XSDSwitch() {
				@Override
				public Object caseXSDSchema(XSDSchema object) {
					return new XsdSchema(object);
				}
				
				@Override
				public Object caseXSDSimpleTypeDefinition(XSDSimpleTypeDefinition object) {
					return new XsdSimpleTypeDefinition(object);
				}
				
				@Override
				public Object caseXSDComplexTypeDefinition(XSDComplexTypeDefinition object) {
					return new XsdComplexTypeDefinition(object);
				}
				
				@Override
				public Object caseXSDElementDeclaration(XSDElementDeclaration object) {
					return new XsdElementDeclaration(object);
				}
			};
			
			Object o = xsdSwitch.doSwitch((EObject) target);
			Adapter result = null;
			if (o instanceof Adapter) {
				result = (Adapter) o;
			}
			return result;
		}
		
		public Adapter adapt(Notifier target) {
			return adapt(target, this);
		}
	}
}

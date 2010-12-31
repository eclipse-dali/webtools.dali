/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.xsd.XSDSchema;



public class SchemaLibraryTests
		extends JaxbTestCase {
	
	public SchemaLibraryTests(String name) {
		super(name);
	}
	
	// tests schema for namespace that is set in catalog, but not in project properties
	public void testUnsetNamespace() {
		String namespace = "http://www.eclipse.org/eclipselink/xsds/persistence/orm";
		
		// test initial load
		XSDSchema schema = getJaxbProject().getSchemaLibrary().getSchema(namespace);
		assertNotNull(schema);
		
		// test cached load
		schema = getJaxbProject().getSchemaLibrary().getSchema(namespace);
		assertNotNull(schema);
		
		// test unload/reload
		schema.eResource().unload();
		schema = getJaxbProject().getSchemaLibrary().getSchema(namespace);
		assertNotNull(schema);
	}
	
	// tests schema for registered uri that is set in project properties (namespace not registered in catalog)
	public void testSetRegisteredUriUnregisteredNamespace() {
		String namespace = "http://java.sun.com/xml/ns/persistence/orm";
		
		// set namespace in project properties\
		Map<String, String> schemaLocations = new HashMap<String, String>();
		schemaLocations.put(namespace, "http://java.sun.com/xml/ns/persistence/orm_2_0.xsd");
		getJaxbProject().getSchemaLibrary().setSchemaLocations(schemaLocations);
		
		// test initial load
		XSDSchema schema = getJaxbProject().getSchemaLibrary().getSchema(namespace);
		assertNotNull(schema);
		
		// test cached load
		schema = getJaxbProject().getSchemaLibrary().getSchema(namespace);
		assertNotNull(schema);
		
		// test unload/reload
		schema.eResource().unload();
		schema = getJaxbProject().getSchemaLibrary().getSchema(namespace);
		assertNotNull(schema);
	}
	
	// tests schema for registerd uri that is set in project properties (namespace already registered in catalog)
	public void testSetRegisteredUriRegisteredNamespace() {
		String namespace = "http://www.eclipse.org/eclipselink/xsds/persistence/orm";
		
		// set namespace in project properties\
		Map<String, String> schemaLocations = new HashMap<String, String>();
		schemaLocations.put(namespace, "http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_1_0.xsd");
		getJaxbProject().getSchemaLibrary().setSchemaLocations(schemaLocations);
		
		// test initial load
		XSDSchema schema = getJaxbProject().getSchemaLibrary().getSchema(namespace);
		assertNotNull(schema);
		
		// test cached load
		schema = getJaxbProject().getSchemaLibrary().getSchema(namespace);
		assertNotNull(schema);
		
		// test unload/reload
		schema.eResource().unload();
		schema = getJaxbProject().getSchemaLibrary().getSchema(namespace);
		assertNotNull(schema);
	}
	
	// tests schema for unregistered uri that is set in project properties (local file)
	public void testSetUnregisterdUri() throws Exception {
		IFile schemaFile = getJaxbProject().getProject().getFile(new Path("customer.xsd"));
		String contents = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\""
				+ " targetNamespace=\"http://www.example.org/customer-example\">"
				+ "</xs:schema>";
		schemaFile.create(new ByteArrayInputStream(contents.getBytes()), true, null);
		
		String namespace = "http://www.example.org/customer-example";
		
		// set namespace in project properties\
		Map<String, String> schemaLocations = new HashMap<String, String>();
		schemaLocations.put(namespace, "platform:/resource/" + BASE_PROJECT_NAME + "/customer.xsd");
		getJaxbProject().getSchemaLibrary().setSchemaLocations(schemaLocations);
		
		// test initial load
		XSDSchema schema = getJaxbProject().getSchemaLibrary().getSchema(namespace);
		assertNotNull(schema);
		
		// test cached load
		schema = getJaxbProject().getSchemaLibrary().getSchema(namespace);
		assertNotNull(schema);
		
		// test unload/reload
		schema.eResource().unload();
		schema = getJaxbProject().getSchemaLibrary().getSchema(namespace);
		assertNotNull(schema);
	}
}

/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.oxm;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbContextRoot;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlSchema;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSchema;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmFactory;

public class OxmXmlSchemaTests
		extends OxmContextModelTestCase {
	
	public OxmXmlSchemaTests(String name) {
		super(name);
	}
	
	
	protected void addOxmFile() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(CR);
		sb.append("<xml-bindings").append(CR);
		sb.append("    version=\"2.4\"").append(CR);
		sb.append("    xmlns=\"http://www.eclipse.org/eclipselink/xsds/persistence/oxm\"").append(CR);
		sb.append("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"").append(CR);
		sb.append("    xsi:schemaLocation=\"http://www.eclipse.org/eclipselink/xsds/persistence/oxm http://www.eclipse.org/eclipselink/xsds/eclipselink_oxm_2_4.xsd\"").append(CR);
		sb.append("    package-name=\"").append(PACKAGE_NAME).append("\"").append(CR);
		sb.append("    />").append(CR);
		addOxmFile("oxm.xml", sb);
	}
	
	protected ICompilationUnit createPackageInfoWithXmlSchema() throws CoreException {
		return createTestPackageInfo("@XmlSchema", JAXB.XML_SCHEMA);
	}
	
	public void testUpdateNamespace() throws Exception {
		createPackageInfoWithXmlSchema();
		JaxbPackageInfo packageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		
		addOxmFile();
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile(PACKAGE_NAME);
		OxmXmlSchema xmlSchema = oxmFile.getXmlBindings().getXmlSchema();
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		
		assertNull(eXmlBindings.getXmlSchema());
		assertNotNull(xmlSchema);
		assertEquals("", xmlSchema.getNamespace());
		assertNull(xmlSchema.getSpecifiedNamespace());
		assertEquals("", xmlSchema.getDefaultNamespace());
		
		packageInfo.getXmlSchema().setSpecifiedNamespace("foo");
		
		assertNull(eXmlBindings.getXmlSchema());
		assertNotNull(xmlSchema);
		assertEquals("foo", xmlSchema.getNamespace());
		assertNull(xmlSchema.getSpecifiedNamespace());
		assertEquals("foo", xmlSchema.getDefaultNamespace());
		
		EXmlSchema eXmlSchema = OxmFactory.eINSTANCE.createEXmlSchema();
		eXmlBindings.setXmlSchema(eXmlSchema);
		eXmlSchema.setNamespace("bar");
		oxmResource.save();
		
		assertNotNull(eXmlBindings.getXmlSchema());
		assertNotNull(xmlSchema);
		assertEquals("bar", xmlSchema.getNamespace());
		assertEquals("bar", xmlSchema.getSpecifiedNamespace());
		assertEquals("foo", xmlSchema.getDefaultNamespace());
		
		eXmlSchema.setNamespace(null);
		oxmResource.save();
		
		assertNotNull(eXmlBindings.getXmlSchema());
		assertNotNull(xmlSchema);
		assertEquals("foo", xmlSchema.getNamespace());
		assertNull(xmlSchema.getSpecifiedNamespace());
		assertEquals("foo", xmlSchema.getDefaultNamespace());
	}
	
	public void testModifyNamespace() throws Exception {
		addOxmFile();
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile(PACKAGE_NAME);
		OxmXmlSchema xmlSchema = oxmFile.getXmlBindings().getXmlSchema();
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		
		assertNull(eXmlBindings.getXmlSchema());
		assertNotNull(xmlSchema);
		assertEquals("", xmlSchema.getNamespace());
		assertNull(xmlSchema.getSpecifiedNamespace());
		assertEquals("", xmlSchema.getDefaultNamespace());
		
		xmlSchema.setSpecifiedNamespace("bar");
		oxmResource.save();
		EXmlSchema eXmlSchema = eXmlBindings.getXmlSchema();
		
		assertFileContentsContains("oxm.xml", "<xml-schema", true);
		assertFileContentsContains("oxm.xml", "namespace=\"bar\"", true);
		assertNotNull(eXmlSchema);
		assertEquals("bar", eXmlSchema.getNamespace());
		assertEquals("bar", xmlSchema.getNamespace());
		assertEquals("bar", xmlSchema.getSpecifiedNamespace());
		assertEquals("", xmlSchema.getDefaultNamespace());
		
		xmlSchema.setSpecifiedNamespace(null);
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "namespace=", false);
		assertNotNull(eXmlBindings.getXmlSchema());
		assertEquals("", xmlSchema.getNamespace());
		assertNull(xmlSchema.getSpecifiedNamespace());
		assertEquals("", xmlSchema.getDefaultNamespace());
	}
}

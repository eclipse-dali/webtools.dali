/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.oxm;

import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbContextRoot;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;

@SuppressWarnings("nls")
public class OxmJavaTypeTests
		extends OxmContextModelTestCase {
	
	public OxmJavaTypeTests(String name) {
		super(name);
	}
	
	
	protected void addOxmFile(String fileName, String packageName, String typeName) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(CR);
		sb.append("<xml-bindings").append(CR);
		sb.append("    version=\"2.4\"").append(CR);
		sb.append("    xmlns=\"http://www.eclipse.org/eclipselink/xsds/persistence/oxm\"").append(CR);
		sb.append("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"").append(CR);
		sb.append("    xsi:schemaLocation=\"http://www.eclipse.org/eclipselink/xsds/persistence/oxm http://www.eclipse.org/eclipselink/xsds/eclipselink_oxm_2_4.xsd\"").append(CR);
		sb.append("    package-name=\"").append(packageName).append("\">").append(CR);
		sb.append("    <java-types>").append(CR);
		sb.append("        <java-type name=\"").append(typeName).append("\"/>").append(CR);
		sb.append("    </java-types>").append(CR);
		sb.append("</xml-bindings>").append(CR);
		addOxmFile(fileName, sb);
	}
	
	public void testUpdateName() throws Exception {
		addOxmFile("oxm.xml", "test.oxm", "Foo");
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile("test.oxm");
		OxmXmlBindings xmlBindings = oxmFile.getXmlBindings();
		OxmJavaType javaType = xmlBindings.getJavaType(0);
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		EJavaType eJavaType = eXmlBindings.getJavaTypes().get(0);
		
		assertEquals("Foo", eJavaType.getName());
		assertEquals("Foo", javaType.getSpecifiedName());
		assertEquals("test.oxm.Foo", javaType.getQualifiedName());
		assertEquals("Foo", javaType.getSimpleName());
		
		eJavaType.setName("test.oxm2.Bar");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "name=\"test.oxm2.Bar\"", true);
		assertEquals("test.oxm2.Bar", eJavaType.getName());
		assertEquals("test.oxm2.Bar", javaType.getSpecifiedName());
		assertEquals("test.oxm2.Bar", javaType.getQualifiedName());
		assertEquals("Bar", javaType.getSimpleName());
		
		eJavaType.setName("int");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "name=\"int\"", true);
		assertEquals("int", eJavaType.getName());
		assertEquals("int", javaType.getSpecifiedName());
		assertEquals("int", javaType.getQualifiedName());
		assertEquals("int", javaType.getSimpleName());
		
		eJavaType.setName("String");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "name=\"String\"", true);
		assertEquals("String", eJavaType.getName());
		assertEquals("String", javaType.getSpecifiedName());
		assertEquals("java.lang.String", javaType.getQualifiedName());
		assertEquals("String", javaType.getSimpleName());
		
		eXmlBindings.setPackageName("test.oxm2");
		eJavaType.setName("Foo");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "name=\"Foo\"", true);
		assertEquals("Foo", eJavaType.getName());
		assertEquals("Foo", javaType.getSpecifiedName());
		assertEquals("test.oxm2.Foo", javaType.getQualifiedName());
		assertEquals("Foo", javaType.getSimpleName());
	}
	
	public void testModifyName() throws Exception {
		addOxmFile("oxm.xml", "test.oxm", "Foo");
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile("test.oxm");
		OxmXmlBindings xmlBindings = oxmFile.getXmlBindings();
		OxmJavaType javaType = xmlBindings.getJavaType(0);
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		EJavaType eJavaType = eXmlBindings.getJavaTypes().get(0);
		
		assertEquals("Foo", eJavaType.getName());
		assertEquals("Foo", javaType.getSpecifiedName());
		assertEquals("test.oxm.Foo", javaType.getQualifiedName());
		assertEquals("Foo", javaType.getSimpleName());
		
		javaType.setSpecifiedName("test.oxm2.Bar");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "name=\"test.oxm2.Bar\"", true);
		assertEquals("test.oxm2.Bar", eJavaType.getName());
		assertEquals("test.oxm2.Bar", javaType.getSpecifiedName());
		assertEquals("test.oxm2.Bar", javaType.getQualifiedName());
		assertEquals("Bar", javaType.getSimpleName());
		
		javaType.setSpecifiedName("int");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "name=\"int\"", true);
		assertEquals("int", eJavaType.getName());
		assertEquals("int", javaType.getSpecifiedName());
		assertEquals("int", javaType.getQualifiedName());
		assertEquals("int", javaType.getSimpleName());
		
		eJavaType.setName("String");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "name=\"String\"", true);
		assertEquals("String", eJavaType.getName());
		assertEquals("String", javaType.getSpecifiedName());
		assertEquals("java.lang.String", javaType.getQualifiedName());
		assertEquals("String", javaType.getSimpleName());
		
		xmlBindings.setSpecifiedPackageName("test.oxm2");
		javaType.setSpecifiedName("Foo");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "name=\"Foo\"", true);
		assertEquals("Foo", eJavaType.getName());
		assertEquals("Foo", javaType.getSpecifiedName());
		assertEquals("test.oxm2.Foo", javaType.getQualifiedName());
		assertEquals("Foo", javaType.getSimpleName());
	}
}

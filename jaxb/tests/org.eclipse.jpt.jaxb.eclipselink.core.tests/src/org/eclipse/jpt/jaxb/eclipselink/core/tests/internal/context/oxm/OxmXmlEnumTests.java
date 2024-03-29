/*******************************************************************************
 * Copyright (c) 2013, 2019 IBM Corporation and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.oxm;

import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnum;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnumMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbContextRoot;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlEnum;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlEnum;

public class OxmXmlEnumTests
		extends OxmContextModelTestCase {
	
	public OxmXmlEnumTests(String name) {
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
		sb.append("    <xml-enums>").append(CR);
		sb.append("        <xml-enum java-enum=\"").append(typeName).append("\"/>").append(CR);
		sb.append("    </xml-enums>").append(CR);
		sb.append("</xml-bindings>").append(CR);
		addOxmFile(fileName, sb);
	}
	
	public void testUpdateName() throws Exception {
		addOxmFile("oxm.xml", "test.oxm", "Foo");
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile("test.oxm");
		OxmXmlBindings xmlBindings = oxmFile.getXmlBindings();
		OxmXmlEnum xmlEnum = xmlBindings.getXmlEnum(0);
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		EXmlEnum eXmlEnum = eXmlBindings.getXmlEnums().get(0);
		
		assertEquals("Foo", eXmlEnum.getJavaEnum());
		assertEquals("Foo", xmlEnum.getSpecifiedJavaEnum());
		assertEquals("test.oxm.Foo", xmlEnum.getTypeName().getFullyQualifiedName());
		assertEquals("Foo", xmlEnum.getTypeName().getSimpleName());
		
		eXmlEnum.setJavaEnum("test.oxm2.Bar");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "java-enum=\"test.oxm2.Bar\"", true);
		assertEquals("test.oxm2.Bar", eXmlEnum.getJavaEnum());
		assertEquals("test.oxm2.Bar", xmlEnum.getSpecifiedJavaEnum());
		assertEquals("test.oxm2.Bar", xmlEnum.getTypeName().getFullyQualifiedName());
		assertEquals("Bar", xmlEnum.getTypeName().getSimpleName());
		
		eXmlEnum.setJavaEnum("test.oxm2.Foo$Bar");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "java-enum=\"test.oxm2.Foo$Bar\"", true);
		assertEquals("test.oxm2.Foo$Bar", eXmlEnum.getJavaEnum());
		assertEquals("test.oxm2.Foo$Bar", xmlEnum.getSpecifiedJavaEnum());
		assertEquals("test.oxm2.Foo$Bar", xmlEnum.getTypeName().getFullyQualifiedName());
		assertEquals("Foo$Bar", xmlEnum.getTypeName().getTypeQualifiedName());
		assertEquals("Bar", xmlEnum.getTypeName().getSimpleName());
		
		eXmlEnum.setJavaEnum("int");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "java-enum=\"int\"", true);
		assertEquals("int", eXmlEnum.getJavaEnum());
		assertEquals("int", xmlEnum.getSpecifiedJavaEnum());
		assertEquals("int", xmlEnum.getTypeName().getFullyQualifiedName());
		assertEquals("int", xmlEnum.getTypeName().getSimpleName());
		
		eXmlEnum.setJavaEnum("String");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "java-enum=\"String\"", true);
		assertEquals("String", eXmlEnum.getJavaEnum());
		assertEquals("String", xmlEnum.getSpecifiedJavaEnum());
		assertEquals("java.lang.String", xmlEnum.getTypeName().getFullyQualifiedName());
		assertEquals("String", xmlEnum.getTypeName().getSimpleName());
		
		eXmlBindings.setPackageName("test.oxm2");
		eXmlEnum.setJavaEnum("Foo");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "java-enum=\"Foo\"", true);
		assertEquals("Foo", eXmlEnum.getJavaEnum());
		assertEquals("Foo", xmlEnum.getSpecifiedJavaEnum());
		assertEquals("test.oxm2.Foo", xmlEnum.getTypeName().getFullyQualifiedName());
		assertEquals("Foo", xmlEnum.getTypeName().getSimpleName());
	}
	
	public void testModifyName() throws Exception {
		addOxmFile("oxm.xml", "test.oxm", "Foo");
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile("test.oxm");
		OxmXmlBindings xmlBindings = oxmFile.getXmlBindings();
		OxmXmlEnum xmlEnum = xmlBindings.getXmlEnum(0);
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		EXmlEnum eXmlEnum = eXmlBindings.getXmlEnums().get(0);
		
		assertEquals("Foo", eXmlEnum.getJavaEnum());
		assertEquals("Foo", xmlEnum.getSpecifiedJavaEnum());
		assertEquals("test.oxm.Foo", xmlEnum.getTypeName().getFullyQualifiedName());
		assertEquals("Foo", xmlEnum.getTypeName().getSimpleName());
		
		xmlEnum.setSpecifiedJavaEnum("test.oxm2.Bar");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "java-enum=\"test.oxm2.Bar\"", true);
		assertEquals("test.oxm2.Bar", eXmlEnum.getJavaEnum());
		assertEquals("test.oxm2.Bar", xmlEnum.getSpecifiedJavaEnum());
		assertEquals("test.oxm2.Bar", xmlEnum.getTypeName().getFullyQualifiedName());
		assertEquals("Bar", xmlEnum.getTypeName().getSimpleName());
		
		xmlEnum.setSpecifiedJavaEnum("int");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "java-enum=\"int\"", true);
		assertEquals("int", eXmlEnum.getJavaEnum());
		assertEquals("int", xmlEnum.getSpecifiedJavaEnum());
		assertEquals("int", xmlEnum.getTypeName().getFullyQualifiedName());
		assertEquals("int", xmlEnum.getTypeName().getSimpleName());
		
		eXmlEnum.setJavaEnum("String");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "java-enum=\"String\"", true);
		assertEquals("String", eXmlEnum.getJavaEnum());
		assertEquals("String", xmlEnum.getSpecifiedJavaEnum());
		assertEquals("java.lang.String", xmlEnum.getTypeName().getFullyQualifiedName());
		assertEquals("String", xmlEnum.getTypeName().getSimpleName());
		
		xmlBindings.setSpecifiedPackageName("test.oxm2");
		xmlEnum.setSpecifiedJavaEnum("Foo");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "java-enum=\"Foo\"", true);
		assertEquals("Foo", eXmlEnum.getJavaEnum());
		assertEquals("Foo", xmlEnum.getSpecifiedJavaEnum());
		assertEquals("test.oxm2.Foo", xmlEnum.getTypeName().getFullyQualifiedName());
		assertEquals("Foo", xmlEnum.getTypeName().getSimpleName());
	}
	
	public void testUpdateValue() throws Exception {
		createEnumWithXmlType();
		addOxmFile("oxm.xml", PACKAGE_NAME, TYPE_NAME);
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile(PACKAGE_NAME);
		OxmXmlBindings xmlBindings = oxmFile.getXmlBindings();
		OxmXmlEnum xmlEnum = xmlBindings.getXmlEnum(0);
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		EXmlEnum eXmlEnum = eXmlBindings.getXmlEnums().get(0);
		JavaEnumMapping javaEnumMapping = ((JavaEnum) xmlEnum.getJavaType()).getMapping();
		
		assertNull(eXmlEnum.getValue());
		assertEquals("java.lang.String", xmlEnum.getValue());
		assertEquals("java.lang.String", xmlEnum.getDefaultValue());
		assertNull(xmlEnum.getSpecifiedValue());
		
		javaEnumMapping.setSpecifiedValue("java.util.Date");
		
		assertNull(eXmlEnum.getValue());
		assertEquals("java.util.Date", xmlEnum.getValue());
		assertEquals("java.util.Date", xmlEnum.getDefaultValue());
		assertNull(xmlEnum.getSpecifiedValue());
		
		xmlBindings.setXmlMappingMetadataComplete(true);
		oxmResource.save();
		
		assertNull(eXmlEnum.getValue());
		assertEquals("java.lang.String", xmlEnum.getValue());
		assertEquals("java.lang.String", xmlEnum.getDefaultValue());
		assertNull(xmlEnum.getSpecifiedValue());
		
		javaEnumMapping.setSpecifiedValue(null);
		xmlBindings.setXmlMappingMetadataComplete(false);
		eXmlEnum.setValue("java.lang.Integer");
		oxmResource.save();
		
		assertEquals("java.lang.Integer", eXmlEnum.getValue());
		assertEquals("java.lang.Integer", xmlEnum.getValue());
		assertEquals("java.lang.String", xmlEnum.getDefaultValue());
		assertEquals("java.lang.Integer", xmlEnum.getSpecifiedValue());
		
		eXmlEnum.setValue(null);
		oxmResource.save();
		
		assertNull(eXmlEnum.getValue());
		assertEquals("java.lang.String", xmlEnum.getValue());
		assertEquals("java.lang.String", xmlEnum.getDefaultValue());
		assertNull(xmlEnum.getSpecifiedValue());
	}
	
	public void testModifyValue() throws Exception {
		createEnumWithXmlType();
		addOxmFile("oxm.xml", PACKAGE_NAME, TYPE_NAME);
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile(PACKAGE_NAME);
		OxmXmlBindings xmlBindings = oxmFile.getXmlBindings();
		OxmXmlEnum xmlEnum = xmlBindings.getXmlEnum(0);
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		EXmlEnum eXmlEnum = eXmlBindings.getXmlEnums().get(0);
		
		assertNull(eXmlEnum.getValue());
		assertNull(xmlEnum.getSpecifiedValue());
		
		xmlEnum.setSpecifiedValue("java.util.Date");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "value=\"java.util.Date\"", true);
		assertEquals("java.util.Date", eXmlEnum.getValue());
		assertEquals("java.util.Date", xmlEnum.getSpecifiedValue());
		
		xmlEnum.setSpecifiedValue("java.lang.Integer");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "value=\"java.lang.Integer\"", true);
		assertEquals("java.lang.Integer", eXmlEnum.getValue());
		assertEquals("java.lang.Integer", xmlEnum.getSpecifiedValue());
		
		xmlEnum.setSpecifiedValue(null);
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "value", false);
		assertNull(eXmlEnum.getValue());
		assertNull(xmlEnum.getSpecifiedValue());
	}
}

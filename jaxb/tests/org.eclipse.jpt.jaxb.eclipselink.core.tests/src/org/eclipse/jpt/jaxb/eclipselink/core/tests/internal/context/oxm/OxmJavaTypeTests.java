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

import java.beans.Introspector;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jaxb.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbContextRoot;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlType;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmFactory;

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
		assertEquals("test.oxm.Foo", javaType.getTypeName().getFullyQualifiedName());
		assertEquals("Foo", javaType.getTypeName().getSimpleName());
		
		eJavaType.setName("test.oxm2.Bar");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "name=\"test.oxm2.Bar\"", true);
		assertEquals("test.oxm2.Bar", eJavaType.getName());
		assertEquals("test.oxm2.Bar", javaType.getSpecifiedName());
		assertEquals("test.oxm2.Bar", javaType.getTypeName().getFullyQualifiedName());
		assertEquals("Bar", javaType.getTypeName().getSimpleName());
		
		eJavaType.setName("test.oxm2.Foo$Bar");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "name=\"test.oxm2.Foo$Bar\"", true);
		assertEquals("test.oxm2.Foo$Bar", eJavaType.getName());
		assertEquals("test.oxm2.Foo$Bar", javaType.getSpecifiedName());
		assertEquals("test.oxm2.Foo$Bar", javaType.getTypeName().getFullyQualifiedName());
		assertEquals("Foo$Bar", javaType.getTypeName().getTypeQualifiedName());
		assertEquals("Bar", javaType.getTypeName().getSimpleName());
		
		eJavaType.setName("int");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "name=\"int\"", true);
		assertEquals("int", eJavaType.getName());
		assertEquals("int", javaType.getSpecifiedName());
		assertEquals("int", javaType.getTypeName().getFullyQualifiedName());
		assertEquals("int", javaType.getTypeName().getSimpleName());
		
		eJavaType.setName("String");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "name=\"String\"", true);
		assertEquals("String", eJavaType.getName());
		assertEquals("String", javaType.getSpecifiedName());
		assertEquals("java.lang.String", javaType.getTypeName().getFullyQualifiedName());
		assertEquals("String", javaType.getTypeName().getSimpleName());
		
		eXmlBindings.setPackageName("test.oxm2");
		eJavaType.setName("Foo");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "name=\"Foo\"", true);
		assertEquals("Foo", eJavaType.getName());
		assertEquals("Foo", javaType.getSpecifiedName());
		assertEquals("test.oxm2.Foo", javaType.getTypeName().getFullyQualifiedName());
		assertEquals("Foo", javaType.getTypeName().getSimpleName());
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
		assertEquals("test.oxm.Foo", javaType.getTypeName().getFullyQualifiedName());
		assertEquals("Foo", javaType.getTypeName().getSimpleName());
		
		javaType.setSpecifiedName("test.oxm2.Bar");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "name=\"test.oxm2.Bar\"", true);
		assertEquals("test.oxm2.Bar", eJavaType.getName());
		assertEquals("test.oxm2.Bar", javaType.getSpecifiedName());
		assertEquals("test.oxm2.Bar", javaType.getTypeName().getFullyQualifiedName());
		assertEquals("Bar", javaType.getTypeName().getSimpleName());
		
		javaType.setSpecifiedName("int");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "name=\"int\"", true);
		assertEquals("int", eJavaType.getName());
		assertEquals("int", javaType.getSpecifiedName());
		assertEquals("int", javaType.getTypeName().getFullyQualifiedName());
		assertEquals("int", javaType.getTypeName().getSimpleName());
		
		eJavaType.setName("String");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "name=\"String\"", true);
		assertEquals("String", eJavaType.getName());
		assertEquals("String", javaType.getSpecifiedName());
		assertEquals("java.lang.String", javaType.getTypeName().getFullyQualifiedName());
		assertEquals("String", javaType.getTypeName().getSimpleName());
		
		xmlBindings.setSpecifiedPackageName("test.oxm2");
		javaType.setSpecifiedName("Foo");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "name=\"Foo\"", true);
		assertEquals("Foo", eJavaType.getName());
		assertEquals("Foo", javaType.getSpecifiedName());
		assertEquals("test.oxm2.Foo", javaType.getTypeName().getFullyQualifiedName());
		assertEquals("Foo", javaType.getTypeName().getSimpleName());
	}
	
	public void testUpdateSuperTypeName() throws Exception {
		createClassWithXmlType();
		addOxmFile("oxm.xml", PACKAGE_NAME, TYPE_NAME);
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile(PACKAGE_NAME);
		OxmXmlBindings xmlBindings = oxmFile.getXmlBindings();
		OxmJavaType javaType = xmlBindings.getJavaType(0);
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		EJavaType eJavaType = eXmlBindings.getJavaTypes().get(0);
		
		assertNull(eJavaType.getSuperType());
		assertEquals("java.lang.Object", javaType.getSuperTypeName());
		assertEquals("java.lang.Object", javaType.getDefaultSuperTypeName());
		assertNull(javaType.getSpecifiedSuperTypeName());
		
		eJavaType.setSuperType("foo");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "super-type=\"foo\"", true);
		assertEquals("foo", eJavaType.getSuperType());
		assertEquals("foo", javaType.getSuperTypeName());
		assertEquals("java.lang.Object", javaType.getDefaultSuperTypeName());
		assertEquals("foo", javaType.getSpecifiedSuperTypeName());
		
		eJavaType.setSuperType(null);
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "super-type", false);
		assertNull(eJavaType.getSuperType());
		assertEquals("java.lang.Object", javaType.getSuperTypeName());
		assertEquals("java.lang.Object", javaType.getDefaultSuperTypeName());
		assertNull(javaType.getSpecifiedSuperTypeName());
	}
	
	public void testModifySuperTypeName() throws Exception {
		createClassWithXmlType();
		addOxmFile("oxm.xml", PACKAGE_NAME, TYPE_NAME);
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile(PACKAGE_NAME);
		OxmXmlBindings xmlBindings = oxmFile.getXmlBindings();
		OxmJavaType javaType = xmlBindings.getJavaType(0);
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		EJavaType eJavaType = eXmlBindings.getJavaTypes().get(0);
		
		assertNull(eJavaType.getSuperType());
		assertEquals("java.lang.Object", javaType.getSuperTypeName());
		assertEquals("java.lang.Object", javaType.getDefaultSuperTypeName());
		assertNull(javaType.getSpecifiedSuperTypeName());
		
		javaType.setSpecifiedSuperTypeName("foo");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "super-type=\"foo\"", true);
		assertEquals("foo", eJavaType.getSuperType());
		assertEquals("foo", javaType.getSuperTypeName());
		assertEquals("java.lang.Object", javaType.getDefaultSuperTypeName());
		assertEquals("foo", javaType.getSpecifiedSuperTypeName());
		
		javaType.setSpecifiedSuperTypeName(null);
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "super-type", false);
		assertNull(eJavaType.getSuperType());
		assertEquals("java.lang.Object", javaType.getSuperTypeName());
		assertEquals("java.lang.Object", javaType.getDefaultSuperTypeName());
		assertNull(javaType.getSpecifiedSuperTypeName());
	}
	
	public void testUpdateSuperclass() throws Exception {
		createClassWithXmlType();
		addOxmFile("oxm.xml", PACKAGE_NAME, "Foo");
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile(PACKAGE_NAME);
		OxmXmlBindings xmlBindings = oxmFile.getXmlBindings();
		OxmJavaType javaType = xmlBindings.getJavaType(0);
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		EJavaType eJavaType = eXmlBindings.getJavaTypes().get(0);
		
		assertNull(eJavaType.getSuperType());
		assertNull(javaType.getSuperclass());
		
		eJavaType.setSuperType(PACKAGE_NAME + "." + TYPE_NAME);
		oxmResource.save();
		
		assertNotNull(eJavaType.getSuperType());
		assertNotNull(javaType.getSuperclass());
		
		eJavaType.setSuperType(null);
		oxmResource.save();
		
		assertNull(eJavaType.getSuperType());
		assertNull(javaType.getSuperclass());
	}
	
	public void testUpdateXmlTransient() throws Exception {
		createClassWithXmlType();
		addOxmFile("oxm.xml", PACKAGE_NAME, TYPE_NAME);
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile(PACKAGE_NAME);
		OxmXmlBindings xmlBindings = oxmFile.getXmlBindings();
		OxmJavaType oxmMapping = xmlBindings.getJavaType(0);
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		EJavaType eJavaType = eXmlBindings.getJavaTypes().get(0);
		JavaTypeMapping javaMapping = oxmMapping.getJavaType().getMapping();
		
		assertFalse(javaMapping.isXmlTransient());
		assertFalse(oxmMapping.isDefaultXmlTransient());
		assertNull(oxmMapping.getSpecifiedXmlTransient());
		assertFalse(oxmMapping.isXmlTransient());
		
		javaMapping.setXmlTransient(true);
		
		assertTrue(javaMapping.isXmlTransient());
		assertTrue(oxmMapping.isDefaultXmlTransient());
		assertNull(oxmMapping.getSpecifiedXmlTransient());
		assertTrue(oxmMapping.isXmlTransient());
		
		xmlBindings.setXmlMappingMetadataComplete(true);
		oxmResource.save();
		
		assertTrue(javaMapping.isXmlTransient());
		assertFalse(oxmMapping.isDefaultXmlTransient());
		assertNull(oxmMapping.getSpecifiedXmlTransient());
		assertFalse(oxmMapping.isXmlTransient());
		
		xmlBindings.setXmlMappingMetadataComplete(false);
		oxmResource.save();
		
		eJavaType.setXmlTransient(Boolean.FALSE);
		oxmResource.save();
		
		assertTrue(javaMapping.isXmlTransient());
		assertTrue(oxmMapping.isDefaultXmlTransient());
		assertEquals(Boolean.FALSE, oxmMapping.getSpecifiedXmlTransient());
		assertFalse(oxmMapping.isXmlTransient());
		
		eJavaType.setXmlTransient(Boolean.TRUE);
		oxmResource.save();
		
		assertTrue(javaMapping.isXmlTransient());
		assertTrue(oxmMapping.isDefaultXmlTransient());
		assertEquals(Boolean.TRUE, oxmMapping.getSpecifiedXmlTransient());
		assertTrue(oxmMapping.isXmlTransient());
		
		javaMapping.setXmlTransient(false);
		
		assertFalse(javaMapping.isXmlTransient());
		assertFalse(oxmMapping.isDefaultXmlTransient());
		assertEquals(Boolean.TRUE, oxmMapping.getSpecifiedXmlTransient());
		assertTrue(oxmMapping.isXmlTransient());
		
		eJavaType.setXmlTransient(null);
		oxmResource.save();
		
		assertFalse(javaMapping.isXmlTransient());
		assertFalse(oxmMapping.isDefaultXmlTransient());
		assertNull(oxmMapping.getSpecifiedXmlTransient());
		assertFalse(oxmMapping.isXmlTransient());
	}
	
	public void testModifyXmlTransient() throws Exception {
		createClassWithXmlType();
		addOxmFile("oxm.xml", PACKAGE_NAME, TYPE_NAME);
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile(PACKAGE_NAME);
		OxmXmlBindings xmlBindings = oxmFile.getXmlBindings();
		OxmJavaType oxmMapping = xmlBindings.getJavaType(0);
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		EJavaType eJavaType = eXmlBindings.getJavaTypes().get(0);
		
		assertNull(eJavaType.getXmlTransient());
		assertNull(oxmMapping.getSpecifiedXmlTransient());
		assertFalse(oxmMapping.isXmlTransient());
		
		oxmMapping.setSpecifiedXmlTransient(Boolean.TRUE);
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "xml-transient=\"true\"", true);
		assertEquals(Boolean.TRUE, eJavaType.getXmlTransient());
		assertEquals(Boolean.TRUE, oxmMapping.getSpecifiedXmlTransient());
		assertTrue(oxmMapping.isXmlTransient());
		
		oxmMapping.setSpecifiedXmlTransient(Boolean.FALSE);
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "xml-transient=\"false\"", true);
		assertEquals(Boolean.FALSE, eJavaType.getXmlTransient());
		assertEquals(Boolean.FALSE, oxmMapping.getSpecifiedXmlTransient());
		assertFalse(oxmMapping.isXmlTransient());
		
		oxmMapping.setSpecifiedXmlTransient(null);
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "xml-transient", false);
		assertNull(eJavaType.getXmlTransient());
		assertNull(oxmMapping.getSpecifiedXmlTransient());
		assertFalse(oxmMapping.isXmlTransient());
	}
	
	public void testUpdateQNameName() throws Exception {
		createClassWithXmlType();
		addOxmFile("oxm.xml", PACKAGE_NAME, TYPE_NAME);
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile(PACKAGE_NAME);
		OxmXmlBindings xmlBindings = oxmFile.getXmlBindings();
		OxmJavaType oxmMapping = xmlBindings.getJavaType(0);
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		EJavaType eJavaType = eXmlBindings.getJavaTypes().get(0);
		JavaTypeMapping javaMapping = oxmMapping.getJavaType().getMapping();
		
		String defaultName = Introspector.decapitalize(TYPE_NAME);
		
		assertEquals(defaultName, javaMapping.getQName().getName());
		assertEquals(defaultName, oxmMapping.getQName().getDefaultName());
		assertNull(oxmMapping.getQName().getSpecifiedName());
		assertEquals(defaultName, oxmMapping.getQName().getName());
		
		javaMapping.getQName().setSpecifiedName("foo");
		
		assertEquals("foo", javaMapping.getQName().getName());
		assertEquals("foo", oxmMapping.getQName().getDefaultName());
		assertNull(oxmMapping.getQName().getSpecifiedName());
		assertEquals("foo", oxmMapping.getQName().getName());
		
		xmlBindings.setXmlMappingMetadataComplete(true);
		oxmResource.save();
		
		assertEquals("foo", javaMapping.getQName().getName());
		assertEquals(defaultName, oxmMapping.getQName().getDefaultName());
		assertNull(oxmMapping.getQName().getSpecifiedName());
		assertEquals(defaultName, oxmMapping.getQName().getName());
		
		xmlBindings.setXmlMappingMetadataComplete(false);
		oxmResource.save();
		
		EXmlType xmlType = OxmFactory.eINSTANCE.createEXmlType();
		eJavaType.setXmlType(xmlType);
		xmlType.setName("foo");
		oxmResource.save();
		
		assertEquals("foo", javaMapping.getQName().getName());
		assertEquals("foo", oxmMapping.getQName().getDefaultName());
		assertEquals("foo", oxmMapping.getQName().getSpecifiedName());
		assertEquals("foo", oxmMapping.getQName().getName());
		
		xmlType.setName("bar");
		oxmResource.save();
		
		assertEquals("foo", javaMapping.getQName().getName());
		assertEquals("foo", oxmMapping.getQName().getDefaultName());
		assertEquals("bar", oxmMapping.getQName().getSpecifiedName());
		assertEquals("bar", oxmMapping.getQName().getName());
		
		javaMapping.getQName().setSpecifiedName(null);
		
		assertEquals(defaultName, javaMapping.getQName().getName());
		assertEquals(defaultName, oxmMapping.getQName().getDefaultName());
		assertEquals("bar", oxmMapping.getQName().getSpecifiedName());
		assertEquals("bar", oxmMapping.getQName().getName());
		
		xmlType.setName(null);
		oxmResource.save();
		
		assertEquals(defaultName, javaMapping.getQName().getName());
		assertEquals(defaultName, oxmMapping.getQName().getDefaultName());
		assertNull(oxmMapping.getQName().getSpecifiedName());
		assertEquals(defaultName, oxmMapping.getQName().getName());
	}
	
	public void testModifyQNameName() throws Exception {
		createClassWithXmlType();
		addOxmFile("oxm.xml", PACKAGE_NAME, TYPE_NAME);
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile(PACKAGE_NAME);
		OxmXmlBindings xmlBindings = oxmFile.getXmlBindings();
		OxmJavaType oxmMapping = xmlBindings.getJavaType(0);
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		EJavaType eJavaType = eXmlBindings.getJavaTypes().get(0);
		JavaTypeMapping javaMapping = oxmMapping.getJavaType().getMapping();
		
		String defaultName = Introspector.decapitalize(TYPE_NAME);
		
		assertEquals(defaultName, javaMapping.getQName().getName());
		assertEquals(defaultName, oxmMapping.getQName().getDefaultName());
		assertNull(oxmMapping.getQName().getSpecifiedName());
		assertEquals(defaultName, oxmMapping.getQName().getName());
		
		oxmMapping.getQName().setSpecifiedName("foo");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "<xml-type", true);
		assertFileContentsContains("oxm.xml", "name=\"foo\"", true);
		assertNotNull(eJavaType.getXmlType());
		assertEquals("foo", eJavaType.getXmlType().getName());
		assertEquals(defaultName, oxmMapping.getQName().getDefaultName());
		assertEquals("foo", oxmMapping.getQName().getSpecifiedName());
		assertEquals("foo", oxmMapping.getQName().getName());
		
		oxmMapping.getQName().setSpecifiedName("bar");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml","<xml-type", true);
		assertFileContentsContains("oxm.xml", "name=\"bar\"", true);
		assertNotNull(eJavaType.getXmlType());
		assertEquals("bar", eJavaType.getXmlType().getName());
		assertEquals(defaultName, oxmMapping.getQName().getDefaultName());
		assertEquals("bar", oxmMapping.getQName().getSpecifiedName());
		assertEquals("bar", oxmMapping.getQName().getName());
		
		oxmMapping.getQName().setSpecifiedName(null);
		oxmResource.save();
		
		// can't test removal of name attribute, since java type has one too
		assertNull(eJavaType.getXmlType().getName());
		assertEquals(defaultName, oxmMapping.getQName().getDefaultName());
		assertNull(oxmMapping.getQName().getSpecifiedName());
		assertEquals(defaultName, oxmMapping.getQName().getName());
	}
	
	public void testUpdateQNameNamespace() throws Exception {
		createClassWithXmlType();
		addOxmFile("oxm.xml", PACKAGE_NAME, TYPE_NAME);
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile(PACKAGE_NAME);
		OxmXmlBindings xmlBindings = oxmFile.getXmlBindings();
		OxmJavaType oxmMapping = xmlBindings.getJavaType(0);
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		EJavaType eJavaType = eXmlBindings.getJavaTypes().get(0);
		JavaTypeMapping javaMapping = oxmMapping.getJavaType().getMapping();
		
		String defaultNamespace = "";
		
		assertEquals(defaultNamespace, javaMapping.getQName().getNamespace());
		assertEquals(defaultNamespace, oxmMapping.getQName().getDefaultNamespace());
		assertNull(oxmMapping.getQName().getSpecifiedNamespace());
		assertEquals(defaultNamespace, oxmMapping.getQName().getNamespace());
		
		javaMapping.getQName().setSpecifiedNamespace("foo");
		
		assertEquals("foo", javaMapping.getQName().getNamespace());
		assertEquals("foo", oxmMapping.getQName().getDefaultNamespace());
		assertNull(oxmMapping.getQName().getSpecifiedNamespace());
		assertEquals("foo", oxmMapping.getQName().getNamespace());
		
		xmlBindings.setXmlMappingMetadataComplete(true);
		oxmResource.save();
		
		assertEquals("foo", javaMapping.getQName().getNamespace());
		assertEquals(defaultNamespace, oxmMapping.getQName().getDefaultNamespace());
		assertNull(oxmMapping.getQName().getSpecifiedNamespace());
		assertEquals(defaultNamespace, oxmMapping.getQName().getNamespace());
		
		xmlBindings.setXmlMappingMetadataComplete(false);
		oxmResource.save();
		
		EXmlType xmlType = OxmFactory.eINSTANCE.createEXmlType();
		eJavaType.setXmlType(xmlType);
		xmlType.setNamespace("foo");
		oxmResource.save();
		
		assertEquals("foo", javaMapping.getQName().getNamespace());
		assertEquals("foo", oxmMapping.getQName().getDefaultNamespace());
		assertEquals("foo", oxmMapping.getQName().getSpecifiedNamespace());
		assertEquals("foo", oxmMapping.getQName().getNamespace());
		
		xmlType.setNamespace("bar");
		oxmResource.save();
		
		assertEquals("foo", javaMapping.getQName().getNamespace());
		assertEquals("foo", oxmMapping.getQName().getDefaultNamespace());
		assertEquals("bar", oxmMapping.getQName().getSpecifiedNamespace());
		assertEquals("bar", oxmMapping.getQName().getNamespace());
		
		javaMapping.getQName().setSpecifiedNamespace(null);
		
		assertEquals(defaultNamespace, javaMapping.getQName().getNamespace());
		assertEquals(defaultNamespace, oxmMapping.getQName().getDefaultNamespace());
		assertEquals("bar", oxmMapping.getQName().getSpecifiedNamespace());
		assertEquals("bar", oxmMapping.getQName().getNamespace());
		
		xmlType.setNamespace(null);
		oxmResource.save();
		
		assertEquals(defaultNamespace, javaMapping.getQName().getNamespace());
		assertEquals(defaultNamespace, oxmMapping.getQName().getDefaultNamespace());
		assertNull(oxmMapping.getQName().getSpecifiedNamespace());
		assertEquals(defaultNamespace, oxmMapping.getQName().getNamespace());
	}
	
	public void testModifyQNameNamespace() throws Exception {
		createClassWithXmlType();
		addOxmFile("oxm.xml", PACKAGE_NAME, TYPE_NAME);
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile(PACKAGE_NAME);
		OxmXmlBindings xmlBindings = oxmFile.getXmlBindings();
		OxmJavaType oxmMapping = xmlBindings.getJavaType(0);
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		EJavaType eJavaType = eXmlBindings.getJavaTypes().get(0);
		JavaTypeMapping javaMapping = oxmMapping.getJavaType().getMapping();
		
		String defaultNamespace = "";
		
		assertEquals(defaultNamespace, javaMapping.getQName().getNamespace());
		assertEquals(defaultNamespace, oxmMapping.getQName().getDefaultNamespace());
		assertNull(oxmMapping.getQName().getSpecifiedNamespace());
		assertEquals(defaultNamespace, oxmMapping.getQName().getNamespace());
		
		oxmMapping.getQName().setSpecifiedNamespace("foo");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml","<xml-type", true);
		assertFileContentsContains("oxm.xml", "namespace=\"foo\"", true);
		assertNotNull(eJavaType.getXmlType());
		assertEquals("foo", eJavaType.getXmlType().getNamespace());
		assertEquals(defaultNamespace, oxmMapping.getQName().getDefaultNamespace());
		assertEquals("foo", oxmMapping.getQName().getSpecifiedNamespace());
		assertEquals("foo", oxmMapping.getQName().getNamespace());
		
		oxmMapping.getQName().setSpecifiedNamespace("bar");
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml","<xml-type", true);
		assertFileContentsContains("oxm.xml", "namespace=\"bar\"", true);
		assertNotNull(eJavaType.getXmlType());
		assertEquals("bar", eJavaType.getXmlType().getNamespace());
		assertEquals(defaultNamespace, oxmMapping.getQName().getDefaultNamespace());
		assertEquals("bar", oxmMapping.getQName().getSpecifiedNamespace());
		assertEquals("bar", oxmMapping.getQName().getNamespace());
		
		oxmMapping.getQName().setSpecifiedNamespace(null);
		oxmResource.save();
		
		assertFileContentsContains("oxm.xml", "namespace=", false);
		assertNull(eJavaType.getXmlType().getNamespace());
		assertEquals(defaultNamespace, oxmMapping.getQName().getDefaultNamespace());
		assertNull(oxmMapping.getQName().getSpecifiedNamespace());
		assertEquals(defaultNamespace, oxmMapping.getQName().getNamespace());
	}
}

/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.persistence;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnitTransactionType;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlProperties;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlProperty;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

@SuppressWarnings("nls")
public class PersistenceUnitTests extends ContextModelTestCase
{
	
	protected static final String INNER_CLASS_NAME = "InnerAnnotationTestType";
	protected static final String FULLY_QUALIFIED_INNER_CLASS_NAME = PACKAGE_NAME + "." + TYPE_NAME + "." + INNER_CLASS_NAME;

	public static final String OTHER_TYPE_NAME = "OtherTestType";
	public static final String FULLY_QUALIFIED_OTHER_TYPE_NAME = PACKAGE_NAME + "." + OTHER_TYPE_NAME;

	public static final String ORM2_XML_FILE_NAME = "orm2.xml";
	public static final String ORM3_XML_FILE_NAME = "orm3.xml";

	public PersistenceUnitTests(String name) {
		super(name);
	}
		
	public void testUpdateName() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that names are initially equal
		assertEquals(xmlPersistenceUnit.getName(), persistenceUnit.getName());
		
		// set name to different name, test equality
		xmlPersistenceUnit.setName("newName");
		
		assertEquals(xmlPersistenceUnit.getName(), persistenceUnit.getName());
		
		// set name to empty string, test equality
		xmlPersistenceUnit.setName("");
		
		assertEquals(xmlPersistenceUnit.getName(), persistenceUnit.getName());
		
		// set name to null, test equality
		xmlPersistenceUnit.setName(null);
		
		assertEquals(xmlPersistenceUnit.getName(), persistenceUnit.getName());
		
		// set name back to non-null, test equality
		xmlPersistenceUnit.setName("newName");
		
		assertEquals(xmlPersistenceUnit.getName(), persistenceUnit.getName());
	}
	
	public void testModifyName() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that names are initially equal
		assertEquals(xmlPersistenceUnit.getName(), persistenceUnit.getName());
		
		// set name to different name, test equality
		persistenceUnit.setName("newName");
		
		assertEquals(xmlPersistenceUnit.getName(), persistenceUnit.getName());
		
		// set name to empty string, test equality
		persistenceUnit.setName("");
		
		assertEquals(xmlPersistenceUnit.getName(), persistenceUnit.getName());
		
		// set name to null, test equality
		persistenceUnit.setName(null);
		
		assertEquals(xmlPersistenceUnit.getName(), persistenceUnit.getName());
		
		// set name back to non-null, test equality
		persistenceUnit.setName("newName");
		
		assertEquals(xmlPersistenceUnit.getName(), persistenceUnit.getName());
	}
	
	public void testUpdateTransactionType() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// 1 - initial value is default
		assertNull(xmlPersistenceUnit.getTransactionType());
		assertNull(persistenceUnit.getSpecifiedTransactionType());
		
		// 2 - set value, context changed
		xmlPersistenceUnit.setTransactionType(XmlPersistenceUnitTransactionType.JTA);
		
		assertEquals(PersistenceUnitTransactionType.JTA, persistenceUnit.getSpecifiedTransactionType());
		
		xmlPersistenceUnit.setTransactionType(XmlPersistenceUnitTransactionType.RESOURCE_LOCAL);
		
		assertEquals(PersistenceUnitTransactionType.RESOURCE_LOCAL, persistenceUnit.getSpecifiedTransactionType());
		
		// 3 - unset value, context changed
		xmlPersistenceUnit.setTransactionType(null);
		
		assertNull(persistenceUnit.getSpecifiedTransactionType());
	}
	
	public void testModifyTransactionType() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// 1 - initial value is default
		assertNull(xmlPersistenceUnit.getTransactionType());
		assertNull(persistenceUnit.getSpecifiedTransactionType());
		
		// 2 - set context value, resource changed
		persistenceUnit.setSpecifiedTransactionType(PersistenceUnitTransactionType.JTA);
		
		assertEquals(XmlPersistenceUnitTransactionType.JTA, xmlPersistenceUnit.getTransactionType());
		
		persistenceUnit.setSpecifiedTransactionType(PersistenceUnitTransactionType.RESOURCE_LOCAL);
		
		assertEquals(XmlPersistenceUnitTransactionType.RESOURCE_LOCAL, xmlPersistenceUnit.getTransactionType());
		
		// 3 - set context value to default, resource unset
		persistenceUnit.setSpecifiedTransactionType(null);
		
		assertNull(persistenceUnit.getSpecifiedTransactionType());
		assertNull(xmlPersistenceUnit.getTransactionType());
	}
	
	public void testUpdateDescription() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that descriptions are initially equal
		assertEquals(xmlPersistenceUnit.getDescription(), persistenceUnit.getDescription());
		
		// set description to different description, test equality
		xmlPersistenceUnit.setDescription("newDescription");
		
		assertEquals(xmlPersistenceUnit.getDescription(), persistenceUnit.getDescription());
		
		// set description to empty string, test equality
		xmlPersistenceUnit.setDescription("");
		
		assertEquals(xmlPersistenceUnit.getDescription(), persistenceUnit.getDescription());
		
		// set description to null, test equality
		xmlPersistenceUnit.setDescription(null);
		
		assertEquals(xmlPersistenceUnit.getDescription(), persistenceUnit.getDescription());
		
		// set description back to non-null, test equality
		xmlPersistenceUnit.setDescription("newDescription");
		
		assertEquals(xmlPersistenceUnit.getDescription(), persistenceUnit.getDescription());
	}
	
	public void testModifyDescription() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that descriptions are initially equal
		assertEquals(xmlPersistenceUnit.getDescription(), persistenceUnit.getDescription());
		
		// set description to different description, test equality
		persistenceUnit.setDescription("newDescription");
		
		assertEquals(xmlPersistenceUnit.getDescription(), persistenceUnit.getDescription());
		
		// set description to empty string, test equality
		persistenceUnit.setDescription("");
		
		assertEquals(xmlPersistenceUnit.getDescription(), persistenceUnit.getDescription());
		
		// set description to null, test equality
		persistenceUnit.setDescription(null);
		
		assertEquals(xmlPersistenceUnit.getDescription(), persistenceUnit.getDescription());
		
		// set description back to non-null, test equality
		persistenceUnit.setDescription("newDescription");
		
		assertEquals(xmlPersistenceUnit.getDescription(), persistenceUnit.getDescription());
	}
	
	public void testUpdateProvider() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that providers are initially equal
		assertEquals(xmlPersistenceUnit.getProvider(), persistenceUnit.getProvider());
		
		// set provider to different provider, test equality
		xmlPersistenceUnit.setProvider("newProvider");
		
		assertEquals(xmlPersistenceUnit.getProvider(), persistenceUnit.getProvider());
		
		// set provider to empty string, test equality
		xmlPersistenceUnit.setProvider("");
		
		assertEquals(xmlPersistenceUnit.getProvider(), persistenceUnit.getProvider());
		
		// set provider to null, test equality
		xmlPersistenceUnit.setProvider(null);
		
		assertEquals(xmlPersistenceUnit.getProvider(), persistenceUnit.getProvider());
		
		// set provider back to non-null, test equality
		xmlPersistenceUnit.setProvider("newProvider");
		
		assertEquals(xmlPersistenceUnit.getProvider(), persistenceUnit.getProvider());
	}
	
	public void testModifyProvider() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that providers are initially equal
		assertEquals(xmlPersistenceUnit.getProvider(), persistenceUnit.getProvider());
		
		// set provider to different provider, test equality
		persistenceUnit.setProvider("newProvider");
		
		assertEquals(xmlPersistenceUnit.getProvider(), persistenceUnit.getProvider());
		
		// set provider to empty string, test equality
		persistenceUnit.setProvider("");
		
		assertEquals(xmlPersistenceUnit.getProvider(), persistenceUnit.getProvider());
		
		// set provider to null, test equality
		persistenceUnit.setProvider(null);
		
		assertEquals(xmlPersistenceUnit.getProvider(), persistenceUnit.getProvider());
		
		// set provider back to non-null, test equality
		persistenceUnit.setProvider("newProvider");
		
		assertEquals(xmlPersistenceUnit.getProvider(), persistenceUnit.getProvider());
	}
	
	public void testUpdateJtaDataSource() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that jtaDataSources are initially equal
		assertEquals(xmlPersistenceUnit.getJtaDataSource(), persistenceUnit.getJtaDataSource());
		
		// set jtaDataSource to different jtaDataSource, test equality
		xmlPersistenceUnit.setJtaDataSource("newJtaDataSource");
		
		assertEquals(xmlPersistenceUnit.getJtaDataSource(), persistenceUnit.getJtaDataSource());
		
		// set jtaDataSource to empty string, test equality
		xmlPersistenceUnit.setJtaDataSource("");
		
		assertEquals(xmlPersistenceUnit.getJtaDataSource(), persistenceUnit.getJtaDataSource());
		
		// set jtaDataSource to null, test equality
		xmlPersistenceUnit.setJtaDataSource(null);
		
		assertEquals(xmlPersistenceUnit.getJtaDataSource(), persistenceUnit.getJtaDataSource());
		
		// set jtaDataSource back to non-null, test equality
		xmlPersistenceUnit.setJtaDataSource("newJtaDataSource");
		
		assertEquals(xmlPersistenceUnit.getJtaDataSource(), persistenceUnit.getJtaDataSource());
	}
	
	public void testModifyJtaDataSource() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that jtaDataSources are initially equal
		assertEquals(xmlPersistenceUnit.getJtaDataSource(), persistenceUnit.getJtaDataSource());
		
		// set jtaDataSource to different jtaDataSource, test equality
		persistenceUnit.setJtaDataSource("newJtaDataSource");
		
		assertEquals(xmlPersistenceUnit.getJtaDataSource(), persistenceUnit.getJtaDataSource());
		
		// set jtaDataSource to empty string, test equality
		persistenceUnit.setJtaDataSource("");
		
		assertEquals(xmlPersistenceUnit.getJtaDataSource(), persistenceUnit.getJtaDataSource());
		
		// set jtaDataSource to null, test equality
		persistenceUnit.setJtaDataSource(null);
		
		assertEquals(xmlPersistenceUnit.getJtaDataSource(), persistenceUnit.getJtaDataSource());
		
		// set jtaDataSource back to non-null, test equality
		persistenceUnit.setJtaDataSource("newJtaDataSource");
		
		assertEquals(xmlPersistenceUnit.getJtaDataSource(), persistenceUnit.getJtaDataSource());
	}
	
	public void testUpdateNonJtaDataSource() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that nonJtaDataSources are initially equal
		assertEquals(xmlPersistenceUnit.getNonJtaDataSource(), persistenceUnit.getNonJtaDataSource());
		
		// set nonJtaDataSource to different nonJtaDataSource, test equality
		xmlPersistenceUnit.setNonJtaDataSource("newNonJtaDataSource");
		
		assertEquals(xmlPersistenceUnit.getNonJtaDataSource(), persistenceUnit.getNonJtaDataSource());
		
		// set nonJtaDataSource to empty string, test equality
		xmlPersistenceUnit.setNonJtaDataSource("");
		
		assertEquals(xmlPersistenceUnit.getNonJtaDataSource(), persistenceUnit.getNonJtaDataSource());
		
		// set nonJtaDataSource to null, test equality
		xmlPersistenceUnit.setNonJtaDataSource(null);
		
		assertEquals(xmlPersistenceUnit.getNonJtaDataSource(), persistenceUnit.getNonJtaDataSource());
		
		// set nonJtaDataSource back to non-null, test equality
		xmlPersistenceUnit.setNonJtaDataSource("newNonJtaDataSource");
		
		assertEquals(xmlPersistenceUnit.getNonJtaDataSource(), persistenceUnit.getNonJtaDataSource());
	}
	
	public void testModifyNonJtaDataSource() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that nonJtaDataSources are initially equal
		assertEquals(xmlPersistenceUnit.getNonJtaDataSource(), persistenceUnit.getNonJtaDataSource());
		
		// set nonJtaDataSource to different nonJtaDataSource, test equality
		persistenceUnit.setNonJtaDataSource("newNonJtaDataSource");
		
		assertEquals(xmlPersistenceUnit.getNonJtaDataSource(), persistenceUnit.getNonJtaDataSource());
		
		// set nonJtaDataSource to empty string, test equality
		persistenceUnit.setNonJtaDataSource("");
		
		assertEquals(xmlPersistenceUnit.getNonJtaDataSource(), persistenceUnit.getNonJtaDataSource());
		
		// set nonJtaDataSource to null, test equality
		persistenceUnit.setNonJtaDataSource(null);
		
		assertEquals(xmlPersistenceUnit.getNonJtaDataSource(), persistenceUnit.getNonJtaDataSource());
		
		// set nonJtaDataSource back to non-null, test equality
		persistenceUnit.setNonJtaDataSource("newNonJtaDataSource");
		
		assertEquals(xmlPersistenceUnit.getNonJtaDataSource(), persistenceUnit.getNonJtaDataSource());
	}
	
	public void testUpdateJarFileRefs1() {
		// TODO
	}
	
	public void testUpdateJarFileRefs2() {
		// TODO
	}
	
	public void testUpdateImpliedMappingFileRef1() throws Exception {
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that there is one initially
		JptXmlResource ormResource = getOrmXmlResource();
		assertTrue(ormResource.fileExists());
		assertNotNull(persistenceUnit.getImpliedMappingFileRef());
		
		// remove orm.xml
		deleteResource(ormResource);
		
		assertFalse(ormResource.fileExists());
		assertNull(persistenceUnit.getImpliedMappingFileRef());
	}
	
	public void testUpdateImpliedMappingFileRef2() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that there is one initially
		JptXmlResource ormResource = getOrmXmlResource();
		assertTrue(ormResource.fileExists());
		assertNotNull(persistenceUnit.getImpliedMappingFileRef());
		
		// add specified orm.xml
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("META-INF/orm.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		
		assertEquals(1, persistenceUnit.getSpecifiedMappingFileRefsSize());
		
		assertTrue(ormResource.fileExists());
		assertNull(persistenceUnit.getImpliedMappingFileRef());
	}
	
	public void testUpdateSpecifiedMappingFileRefs1() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test there are none initially
		assertEquals(0, xmlPersistenceUnit.getMappingFiles().size());
		assertEquals(0, persistenceUnit.getSpecifiedMappingFileRefsSize());
		
		// add mapping file ref, test that it's added to context
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		
		assertEquals(1, persistenceUnit.getSpecifiedMappingFileRefsSize());
		
		// add another ...
		xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm2.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		
		assertEquals(2, persistenceUnit.getSpecifiedMappingFileRefsSize());
	}
	
	public void testUpdateSpecifiedMappingFileRefs2() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// add two mapping file refs and test that there are two existing in xml and context
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm2.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		
		assertEquals(xmlPersistenceUnit.getMappingFiles().size(), 2);
		assertEquals(2, persistenceUnit.getSpecifiedMappingFileRefsSize());
		
		// remove mapping file ref from xml, test that it's removed from context
		xmlMappingFileRef = xmlPersistenceUnit.getMappingFiles().get(0);
		xmlPersistenceUnit.getMappingFiles().remove(xmlMappingFileRef);
		
		assertEquals(1, persistenceUnit.getSpecifiedMappingFileRefsSize());
		
		// remove another one ...
		xmlMappingFileRef = xmlPersistenceUnit.getMappingFiles().get(0);
		xmlPersistenceUnit.getMappingFiles().remove(xmlMappingFileRef);
		
		assertEquals(0, persistenceUnit.getSpecifiedMappingFileRefsSize());
	}
	
	public void testModifySpecifiedMappingFileRefs1() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test there are none initially
		assertEquals(0, xmlPersistenceUnit.getMappingFiles().size());
		assertEquals(0, persistenceUnit.getSpecifiedMappingFileRefsSize());
		
		// add mapping file ref, test that it's added to resource
		persistenceUnit.addSpecifiedMappingFileRef("foo");
		
		assertEquals(1, xmlPersistenceUnit.getMappingFiles().size());
		assertEquals("foo", xmlPersistenceUnit.getMappingFiles().get(0).getFileName());
		
		// add another ...
		persistenceUnit.addSpecifiedMappingFileRef("bar");
		assertEquals("foo", xmlPersistenceUnit.getMappingFiles().get(0).getFileName());
		assertEquals("bar", xmlPersistenceUnit.getMappingFiles().get(1).getFileName());
		
		assertEquals(2, xmlPersistenceUnit.getMappingFiles().size());
		
		// add another, testing order
		persistenceUnit.addSpecifiedMappingFileRef(0, "baz");
		assertEquals(3, xmlPersistenceUnit.getMappingFiles().size());
		assertEquals("baz", xmlPersistenceUnit.getMappingFiles().get(0).getFileName());
		assertEquals("foo", xmlPersistenceUnit.getMappingFiles().get(1).getFileName());
		assertEquals("bar", xmlPersistenceUnit.getMappingFiles().get(2).getFileName());
	}
	
	public void testModifySpecifiedMappingFileRefs2() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// add two mapping file refs and test that there are two existing in xml and context
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm2.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		
		assertEquals(2, xmlPersistenceUnit.getMappingFiles().size());
		assertEquals(2, persistenceUnit.getSpecifiedMappingFileRefsSize());
		
		// remove mapping file ref from context, test that it's removed from xml
		persistenceUnit.removeSpecifiedMappingFileRef(0);
		
		assertEquals(1, xmlPersistenceUnit.getMappingFiles().size());
			
		// remove another one ...
		persistenceUnit.removeSpecifiedMappingFileRef(0);
		
		assertEquals(0, xmlPersistenceUnit.getMappingFiles().size());
	}
	
	public void testUpdateClassRefs1() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test there are none initially
		assertEquals(0, xmlPersistenceUnit.getClasses().size());
		assertEquals(0, persistenceUnit.getSpecifiedClassRefsSize());
		
		// add mapping file ref, test that it's added to context
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Bar");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		
		assertEquals(1, persistenceUnit.getSpecifiedClassRefsSize());
		
		// add another ...
		xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Baz");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		
		assertEquals(2, persistenceUnit.getSpecifiedClassRefsSize());
	}
	
	public void testUpdateClassRefs2() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// add two class refs and test that there are two existing in xml and context
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Bar");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Baz");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		
		assertEquals(2, xmlPersistenceUnit.getClasses().size());
		assertEquals(2, persistenceUnit.getSpecifiedClassRefsSize());
		
		// remove class ref from xml, test that it's removed from context
		xmlClassRef = xmlPersistenceUnit.getClasses().get(0);
		xmlPersistenceUnit.getClasses().remove(xmlClassRef);
		
		assertEquals(1, persistenceUnit.getSpecifiedClassRefsSize());
		
		// remove another one ...
		xmlClassRef = xmlPersistenceUnit.getClasses().get(0);
		xmlPersistenceUnit.getClasses().remove(xmlClassRef);
		
		assertEquals(0, persistenceUnit.getSpecifiedClassRefsSize());
	}
	
	public void testModifyClassRefs1() throws Exception {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test there are none initially
		assertEquals(0, xmlPersistenceUnit.getClasses().size());
		assertEquals(0, persistenceUnit.getSpecifiedClassRefsSize());
		
		// add class ref, test that it's added to context
		persistenceUnit.addSpecifiedClassRef("Foo");
		
		getPersistenceXmlResource().save(null);
		assertEquals(1, xmlPersistenceUnit.getClasses().size());
		assertEquals("Foo", xmlPersistenceUnit.getClasses().get(0).getJavaClass());
	
		// add another ...
		persistenceUnit.addSpecifiedClassRef("Bar");
		
		assertEquals(2, xmlPersistenceUnit.getClasses().size());
		assertEquals("Foo", xmlPersistenceUnit.getClasses().get(0).getJavaClass());
		assertEquals("Bar", xmlPersistenceUnit.getClasses().get(1).getJavaClass());
	
		
		persistenceUnit.addSpecifiedClassRef(0, "Baz");
		
		assertEquals(3, xmlPersistenceUnit.getClasses().size());
		assertEquals("Baz", xmlPersistenceUnit.getClasses().get(0).getJavaClass());
		assertEquals("Foo", xmlPersistenceUnit.getClasses().get(1).getJavaClass());
		assertEquals("Bar", xmlPersistenceUnit.getClasses().get(2).getJavaClass());
	}
	
	public void testModifyClassRefs2() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// add two class refs and test that there are two existing in xml and context
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Bar");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Baz");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		
		assertEquals(2, xmlPersistenceUnit.getClasses().size());
		assertEquals(2, persistenceUnit.getSpecifiedClassRefsSize());
		
		// remove class ref from context, test that it's removed from xml
		persistenceUnit.removeSpecifiedClassRef(0);
		
		assertEquals(1, xmlPersistenceUnit.getClasses().size());
		
		// remove another one ...
		persistenceUnit.removeSpecifiedClassRef(0);
		
		assertEquals(0, xmlPersistenceUnit.getClasses().size());
	}

	public void testImpliedClassRefs1() throws Exception {
		createTestEntityWithPersistentInnerClass();
		
		getJpaProject().setDiscoversAnnotatedClasses(false);
		getPersistenceUnit().setSpecifiedExcludeUnlistedClasses(Boolean.TRUE);
		assertEquals(0, getPersistenceUnit().getImpliedClassRefsSize());
		
		getJpaProject().setDiscoversAnnotatedClasses(true);
		getPersistenceUnit().setSpecifiedExcludeUnlistedClasses(Boolean.FALSE);
		assertEquals(2, getPersistenceUnit().getImpliedClassRefsSize());
		this.verifyVirtualClassRef(FULLY_QUALIFIED_TYPE_NAME);
		this.verifyVirtualClassRef(FULLY_QUALIFIED_INNER_CLASS_NAME);
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(1, getPersistenceUnit().getImpliedClassRefsSize());
		this.verifyVirtualClassRef(FULLY_QUALIFIED_INNER_CLASS_NAME);
		
		removeXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(2, getPersistenceUnit().getImpliedClassRefsSize());
		this.verifyVirtualClassRef(FULLY_QUALIFIED_TYPE_NAME);
		this.verifyVirtualClassRef(FULLY_QUALIFIED_INNER_CLASS_NAME);

		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(1, getPersistenceUnit().getImpliedClassRefsSize());
		this.verifyVirtualClassRef(FULLY_QUALIFIED_INNER_CLASS_NAME);
		
		addXmlClassRef(FULLY_QUALIFIED_INNER_CLASS_NAME);
		assertEquals(0, getPersistenceUnit().getImpliedClassRefsSize());
		
		removeXmlClassRef(FULLY_QUALIFIED_INNER_CLASS_NAME);
		assertEquals(1, getPersistenceUnit().getImpliedClassRefsSize());
		this.verifyVirtualClassRef(FULLY_QUALIFIED_INNER_CLASS_NAME);

		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_INNER_CLASS_NAME);
		assertEquals(0, getPersistenceUnit().getImpliedClassRefsSize());
	}

	protected void verifyVirtualClassRef(String className) {
		if (this.getVirtualClassRef(className) == null) {
			fail("missing virtual class ref: " + className);
		}
	}
	
	protected ClassRef getVirtualClassRef(String className) {
		for (ClassRef ref : this.getPersistenceUnit().getImpliedClassRefs()) {
			if (ObjectTools.equals(ref.getClassName(), className)) {
				return ref;
			}
		}
		return null;
	}
	
	public void testImpliedClassRefs2() throws Exception {
		createTestEntity();
		getJpaProject().setDiscoversAnnotatedClasses(true);
		JavaResourceType javaType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(1, IterableTools.size(getPersistenceUnit().getImpliedClassRefs()));
		
		javaType.removeAnnotation(JPA.ENTITY);
		getJpaProject().synchronizeContextModel();
		assertEquals(0, getPersistenceUnit().getImpliedClassRefsSize());
		
		javaType.addAnnotation(JPA.EMBEDDABLE);
		getJpaProject().synchronizeContextModel();
		assertEquals(1, getPersistenceUnit().getImpliedClassRefsSize());
		
		javaType.removeAnnotation(JPA.EMBEDDABLE);
		getJpaProject().synchronizeContextModel();
		assertEquals(0, getPersistenceUnit().getImpliedClassRefsSize());
		
		javaType.addAnnotation(JPA.MAPPED_SUPERCLASS);
		getJpaProject().synchronizeContextModel();
		assertEquals(1, getPersistenceUnit().getImpliedClassRefsSize());
	}
	
	public void testRenamePersistentTypeImpliedClassRefs() throws Exception {
		getJavaProjectTestHarness().getJpaProject().setDiscoversAnnotatedClasses(true);
		ICompilationUnit testType = createTestEntity();
		@SuppressWarnings("unused")
		ICompilationUnit otherTestType = this.createTestOtherTypeEntity();
		
		ClassRef testTypeClassRef = this.getVirtualClassRef(FULLY_QUALIFIED_TYPE_NAME);
		ClassRef otherTestTypeClassRef = this.getVirtualClassRef(FULLY_QUALIFIED_OTHER_TYPE_NAME);
		
		assertNotNull(testTypeClassRef);
		assertNotNull(otherTestTypeClassRef);
		
		JavaPersistentType testJavaPersistentType = testTypeClassRef.getJavaPersistentType();
		JavaPersistentType otherTestJavaPersistentType = otherTestTypeClassRef.getJavaPersistentType();
		
		testType.findPrimaryType().rename("TestType2", false, null);
		
		testTypeClassRef = this.getVirtualClassRef("test.TestType2");
		otherTestTypeClassRef = this.getVirtualClassRef(FULLY_QUALIFIED_OTHER_TYPE_NAME);
		
		assertNotNull(testTypeClassRef);
		assertNotNull(otherTestTypeClassRef);
		
		assertEquals(otherTestJavaPersistentType, otherTestTypeClassRef.getJavaPersistentType());
		assertNotSame(testJavaPersistentType, testTypeClassRef.getJavaPersistentType());
	}
	
	public void testUpdateExcludeUnlistedClasses() throws Exception {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// 1 - initial value is default
		assertNull(persistenceUnit.getSpecifiedExcludeUnlistedClasses());
		assertFalse(persistenceUnit.excludesUnlistedClasses());
		assertNull(xmlPersistenceUnit.getExcludeUnlistedClasses());
		
		// 2 - set value, context changed
		xmlPersistenceUnit.setExcludeUnlistedClasses(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, persistenceUnit.getSpecifiedExcludeUnlistedClasses());
		assertTrue(persistenceUnit.excludesUnlistedClasses());
		assertEquals(Boolean.TRUE, xmlPersistenceUnit.getExcludeUnlistedClasses());
		
		xmlPersistenceUnit.setExcludeUnlistedClasses(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, persistenceUnit.getSpecifiedExcludeUnlistedClasses());
		assertFalse(persistenceUnit.excludesUnlistedClasses());
		assertEquals(Boolean.FALSE, xmlPersistenceUnit.getExcludeUnlistedClasses());
		
		// 3 - unset value, context changed
		xmlPersistenceUnit.setExcludeUnlistedClasses(null);
		
		assertNull(persistenceUnit.getSpecifiedExcludeUnlistedClasses());
		assertFalse(persistenceUnit.excludesUnlistedClasses());
		assertNull(xmlPersistenceUnit.getExcludeUnlistedClasses());
	}
	
	public void testModifyExcludeUnlistedClasses() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// 1 - initial value is default
		assertNull(persistenceUnit.getSpecifiedExcludeUnlistedClasses());
		assertFalse(persistenceUnit.excludesUnlistedClasses());
		assertNull(xmlPersistenceUnit.getExcludeUnlistedClasses());
		
		// 2 - set value, resource changed
		persistenceUnit.setSpecifiedExcludeUnlistedClasses(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, persistenceUnit.getSpecifiedExcludeUnlistedClasses());
		assertTrue(persistenceUnit.excludesUnlistedClasses());
		assertEquals(Boolean.TRUE, xmlPersistenceUnit.getExcludeUnlistedClasses());
		
		persistenceUnit.setSpecifiedExcludeUnlistedClasses(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, persistenceUnit.getSpecifiedExcludeUnlistedClasses());
		assertFalse(persistenceUnit.excludesUnlistedClasses());
		assertEquals(Boolean.FALSE, xmlPersistenceUnit.getExcludeUnlistedClasses());
		
		// 3 - set context to default, resource unset
		persistenceUnit.setSpecifiedExcludeUnlistedClasses(null);
		
		assertNull(persistenceUnit.getSpecifiedExcludeUnlistedClasses());
		assertFalse(persistenceUnit.excludesUnlistedClasses());
		assertNull(xmlPersistenceUnit.getExcludeUnlistedClasses());
	}
	
	public void testUpdateProperties1() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test there are none initially
		assertNull(xmlPersistenceUnit.getProperties());
		assertEquals(0, persistenceUnit.getPropertiesSize());
		
		// add "properties", test that there's no real change to context
		XmlProperties xmlProperties = PersistenceFactory.eINSTANCE.createXmlProperties();
		xmlPersistenceUnit.setProperties(xmlProperties);
		assertEquals(0, persistenceUnit.getPropertiesSize());
		
		// add property, test that it's added to context
		XmlProperty xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		xmlProperty.setName("foo");
		xmlProperty.setValue("bar");
		xmlProperties.getProperties().add(xmlProperty);
		
		assertEquals(1, persistenceUnit.getPropertiesSize());
		
		// add another ...
		xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		xmlProperty.setName("FOO");
		xmlProperty.setValue("BAR");
		xmlProperties.getProperties().add(xmlProperty);
		
		assertEquals(2, persistenceUnit.getPropertiesSize());
	}
	
	public void testUpdateProperties2() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// add two properties and test that there are two existing in xml and context
		XmlProperties xmlProperties = PersistenceFactory.eINSTANCE.createXmlProperties();
		xmlPersistenceUnit.setProperties(xmlProperties);
		XmlProperty xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		xmlProperty.setName("foo");
		xmlProperty.setValue("bar");
		xmlProperties.getProperties().add(xmlProperty);
		xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		xmlProperty.setName("FOO");
		xmlProperty.setValue("BAR");
		xmlProperties.getProperties().add(xmlProperty);
		
		assertEquals(2, xmlPersistenceUnit.getProperties().getProperties().size());
		assertEquals(2, persistenceUnit.getPropertiesSize());
		
		// remove property from xml, test that it's removed from context
		xmlProperty = xmlProperties.getProperties().get(0);
		xmlProperties.getProperties().remove(xmlProperty);
		
		assertEquals(1, persistenceUnit.getPropertiesSize());
		
		// remove another one ...
		xmlProperty = xmlProperties.getProperties().get(0);
		xmlProperties.getProperties().remove(xmlProperty);
		
		assertEquals(0, persistenceUnit.getPropertiesSize());
	}
	
	public void testModifyProperties1() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test there are none initially
		assertNull(xmlPersistenceUnit.getProperties());
		assertEquals(0, persistenceUnit.getPropertiesSize());
		
		// add property, test that it's added to resource
		persistenceUnit.addProperty().setName("foo");
		
		assertNotNull(xmlPersistenceUnit.getProperties());
		assertEquals(1, xmlPersistenceUnit.getProperties().getProperties().size());
		assertEquals("foo", xmlPersistenceUnit.getProperties().getProperties().get(0).getName());
		
		// add another ...
		persistenceUnit.addProperty().setName("bar");
		
		assertEquals(2, xmlPersistenceUnit.getProperties().getProperties().size());
		assertEquals("foo", xmlPersistenceUnit.getProperties().getProperties().get(0).getName());
		assertEquals("bar", xmlPersistenceUnit.getProperties().getProperties().get(1).getName());

		// add another testing order
		persistenceUnit.addProperty(0).setName("baz");
		
		assertEquals(3, xmlPersistenceUnit.getProperties().getProperties().size());
		assertEquals("baz", xmlPersistenceUnit.getProperties().getProperties().get(0).getName());
		assertEquals("foo", xmlPersistenceUnit.getProperties().getProperties().get(1).getName());
		assertEquals("bar", xmlPersistenceUnit.getProperties().getProperties().get(2).getName());
	}
	
	public void testModifyProperties2() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// add two properties and test that there are two existing in xml and context
		XmlProperties xmlProperties = PersistenceFactory.eINSTANCE.createXmlProperties();
		xmlPersistenceUnit.setProperties(xmlProperties);
		XmlProperty xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		xmlProperty.setName("foo");
		xmlProperty.setValue("bar");
		xmlProperties.getProperties().add(xmlProperty);
		xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		xmlProperty.setName("FOO");
		xmlProperty.setValue("BAR");
		xmlProperties.getProperties().add(xmlProperty);
		
		assertEquals(2, xmlPersistenceUnit.getProperties().getProperties().size());
		assertEquals(2, persistenceUnit.getPropertiesSize());
		
		// remove property from context, test that it's removed from resource
		persistenceUnit.removeProperty("foo");
		
		assertEquals(1, xmlPersistenceUnit.getProperties().getProperties().size());
		
		// remove another one.  test that properties object is nulled
		persistenceUnit.removeProperty("FOO", "BAR");
		
		assertNull(xmlPersistenceUnit.getProperties());
	}
	
	public void testModifyProperties3() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// add two properties and test that there are two existing in xml and context
		persistenceUnit.setProperty("foo", "bar", false);
		persistenceUnit.setProperty("FOO", "BAR", false);
		
		assertEquals(2, xmlPersistenceUnit.getProperties().getProperties().size());
		assertEquals(2, persistenceUnit.getPropertiesSize());
		
		// remove property from context, test that it's removed from resource
		persistenceUnit.removeProperty("foo", "bar");
		
		assertEquals(1, xmlPersistenceUnit.getProperties().getProperties().size());
		
		// remove another one, test that properties object is nulled
		persistenceUnit.removeProperty("FOO");
		
		assertNull(xmlPersistenceUnit.getProperties());
	}
	
	public void testModifyProperties4() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// add two properties and test that there are two existing in xml and context
		persistenceUnit.setProperty("foo", "bar", false);
		persistenceUnit.setProperty("FOO", "BAR", false);
		
		assertEquals(2, xmlPersistenceUnit.getProperties().getProperties().size());
		assertEquals(2, persistenceUnit.getPropertiesSize());
		
		// modify a property, test its value
		persistenceUnit.setProperty("foo", "", false);
		assertEquals("", persistenceUnit.getProperty("foo").getValue());

		persistenceUnit.setProperty("foo", "BAR", false);
		assertEquals("BAR", persistenceUnit.getProperty("foo").getValue());
		
		// remove property from context, test that it's removed from resource
		persistenceUnit.removeProperty("FOO");
		assertNull(persistenceUnit.getProperty("FOO"));
		assertEquals(1, xmlPersistenceUnit.getProperties().getProperties().size());

		// remove by setting value to null, test that properties object is nulled
		persistenceUnit.setProperty("notExist", null, false);
		assertNull(persistenceUnit.getProperty("notExist"));

		persistenceUnit.setProperty("foo", null, false);
		assertNull(persistenceUnit.getProperty("foo"));
		assertNull(xmlPersistenceUnit.getProperties());
	}
	
	public void testModifyProperties5() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// testing duplicate keys, add four properties and test that there are four existing in xml and context
		persistenceUnit.setProperty("FOO", "BAR", false);
		persistenceUnit.setProperty("foo", "bar 3", true);
		persistenceUnit.setProperty("foo", "bar 2", true);
		persistenceUnit.setProperty("foo", "bar 1", true);
		
		assertEquals(4, xmlPersistenceUnit.getProperties().getProperties().size());
		assertEquals(4, persistenceUnit.getPropertiesSize());
		
		// remove a property, test that there are four existing in xml and context
		persistenceUnit.removeProperty("foo", "bar 1");
		assertEquals(3, xmlPersistenceUnit.getProperties().getProperties().size());
		assertEquals(3, persistenceUnit.getPropertiesSize());
	}
	
	public void testAccessProperty() {
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// add two properties and try to access it.
		persistenceUnit.setProperty("foo", "bar", false);
		persistenceUnit.setProperty("FOO", "BAR", false);
		
		PersistenceUnit.Property property = persistenceUnit.getProperty("foo");
		assertNotNull(property);
		assertEquals("bar", property.getValue());
		assertNotNull(persistenceUnit.getProperty("FOO"));
		assertEquals("BAR", persistenceUnit.getProperty("FOO").getValue());
		assertNull(persistenceUnit.getProperty("notExist"));
	}
	
	private PersistenceUnit.Property persistenceUnitFirstProperty() {
		return getPersistenceUnit().getProperties().iterator().next();
	}

	public void testUpdatePropertyName() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		
		// add property for testing
		XmlProperties xmlProperties = PersistenceFactory.eINSTANCE.createXmlProperties();
		xmlPersistenceUnit.setProperties(xmlProperties);
		XmlProperty xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		xmlProperties.getProperties().add(xmlProperty);
		
		// test that names are initially equal
		assertEquals(xmlProperty.getName(), persistenceUnitFirstProperty().getName());
		
		// set name to different name, test equality
		xmlProperty.setName("newName");

		assertEquals(xmlProperty.getName(), persistenceUnitFirstProperty().getName());
		
		// set name to empty string, test equality
		xmlProperty.setName("");

		assertEquals(xmlProperty.getName(), persistenceUnitFirstProperty().getName());
		
		// set name back to non-null, test equality
		xmlProperty.setName("newName");

		assertEquals(xmlProperty.getName(), persistenceUnitFirstProperty().getName());
	}
	
	public void testUpdatePropertyValue() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		
		// add property for testing
		XmlProperties xmlProperties = PersistenceFactory.eINSTANCE.createXmlProperties();
		xmlPersistenceUnit.setProperties(xmlProperties);
		XmlProperty xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		xmlProperties.getProperties().add(xmlProperty);
		
		// test that values are initially equal
		assertEquals(xmlProperty.getValue(), persistenceUnitFirstProperty().getValue());
		
		// set value to different value, test equality
		xmlProperty.setValue("newValue");
		
		assertEquals(xmlProperty.getValue(), persistenceUnitFirstProperty().getValue());
		
		// set value to empty string, test equality
		xmlProperty.setValue("");
		
		assertEquals(xmlProperty.getValue(), persistenceUnitFirstProperty().getValue());
		
		// set value to null, test equality
		xmlProperty.setValue(null);
		
		assertEquals(xmlProperty.getValue(), persistenceUnitFirstProperty().getValue());
		
		// set value back to non-null, test equality
		xmlProperty.setValue("newValue");
		
		assertEquals(xmlProperty.getValue(), persistenceUnitFirstProperty().getValue());
	}	
	
	public void testGetDefaultAccess() throws Exception {
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		createOrmXmlFile(ORM2_XML_FILE_NAME);
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		ListIterator<MappingFileRef> mappingFileRefs = getPersistenceUnit().getMappingFileRefs().iterator();
		OrmXml ormMappingFile = (OrmXml) mappingFileRefs.next().getMappingFile();
		OrmXml orm2MappingFile = (OrmXml) mappingFileRefs.next().getMappingFile();
		
		assertEquals(null, persistenceUnit.getDefaultAccess());
		
		ormMappingFile.getRoot().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedAccess(AccessType.PROPERTY);		
		assertEquals(AccessType.PROPERTY, persistenceUnit.getDefaultAccess());
		
		ormMappingFile.getRoot().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedAccess(AccessType.FIELD);	
		assertEquals(AccessType.FIELD, persistenceUnit.getDefaultAccess());
		
		ormMappingFile.getRoot().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedAccess(null);	
		assertFalse(ormMappingFile.getRoot().getPersistenceUnitMetadata().resourceExists());
		assertEquals(null, persistenceUnit.getDefaultAccess());
				
		orm2MappingFile.getRoot().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedAccess(AccessType.FIELD);	
		assertEquals(AccessType.FIELD, persistenceUnit.getDefaultAccess());
	}
	
	public void testGetDefaultSchema() throws Exception {
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		createOrmXmlFile(ORM2_XML_FILE_NAME);
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		ListIterator<MappingFileRef> mappingFileRefs = getPersistenceUnit().getMappingFileRefs().iterator();
		OrmXml ormMappingFile = (OrmXml) mappingFileRefs.next().getMappingFile();
		OrmXml orm2MappingFile = (OrmXml) mappingFileRefs.next().getMappingFile();
		
		assertEquals(null, persistenceUnit.getDefaultSchema());
		
		ormMappingFile.getRoot().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedSchema("FOO");		
		assertEquals("FOO", persistenceUnit.getDefaultSchema());
		
		ormMappingFile.getRoot().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedSchema(null);	
		assertFalse(ormMappingFile.getRoot().getPersistenceUnitMetadata().resourceExists());
		assertEquals(null, persistenceUnit.getDefaultSchema());
				
		orm2MappingFile.getRoot().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedSchema("BAR");	
		assertEquals("BAR", persistenceUnit.getDefaultSchema());
	}
	
	public void testGetDefaultCatalog() throws Exception {
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		createOrmXmlFile(ORM2_XML_FILE_NAME);
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		ListIterator<MappingFileRef> mappingFileRefs = getPersistenceUnit().getMappingFileRefs().iterator();
		OrmXml ormMappingFile = (OrmXml) mappingFileRefs.next().getMappingFile();
		OrmXml orm2MappingFile = (OrmXml) mappingFileRefs.next().getMappingFile();
		
		assertEquals(null, persistenceUnit.getDefaultCatalog());
		
		ormMappingFile.getRoot().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedCatalog("FOO");		
		assertEquals("FOO", persistenceUnit.getDefaultCatalog());
		
		ormMappingFile.getRoot().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedCatalog(null);	
		assertFalse(ormMappingFile.getRoot().getPersistenceUnitMetadata().resourceExists());
		assertEquals(null, persistenceUnit.getDefaultCatalog());
				
		orm2MappingFile.getRoot().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedCatalog("BAR");	
		assertEquals("BAR", persistenceUnit.getDefaultCatalog());
	}
	
	protected void createOrmXmlFile(String fileName) throws Exception {
		IDataModel config =
			DataModelFactory.createDataModel(new OrmFileCreationDataModelProvider());
		config.setProperty(JptFileCreationDataModelProperties.CONTAINER_PATH, 
				getJpaProject().getProject().getFolder("src/META-INF").getFullPath());
		config.setProperty(JptFileCreationDataModelProperties.FILE_NAME, fileName);
		config.getDefaultOperation().execute(null, null);
		
		addXmlMappingFileRef("META-INF/" + fileName);
		getPersistenceXmlResource().save(null);
	}

	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestOtherTypeEntity() throws Exception {
		return this.createTestType(PACKAGE_NAME, OTHER_TYPE_NAME + ".java", OTHER_TYPE_NAME, new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY);
			}
				@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}

		});
	}

	private ICompilationUnit createTestEntityWithPersistentInnerClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendMemberTypeTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("     public static class " + INNER_CLASS_NAME + " {}").append(CR);
			}
		});
	}

	public void testPersistentType() throws Exception {
		getJpaProject().setDiscoversAnnotatedClasses(false);	
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		createTestEntity();
		
		//persistentType not listed in persistence.xml and discoverAnnotatedClasses is false
		//still find the persistentType because of changes for bug 190317
		assertFalse(getJpaProject().discoversAnnotatedClasses());
		assertNotNull(persistenceUnit.getPersistentType(FULLY_QUALIFIED_TYPE_NAME));
		
		//test persistentType not listed in persistence.xml, discover annotated classes set to true
		getJpaProject().setDiscoversAnnotatedClasses(true);	
		assertNotNull(persistenceUnit.getPersistentType(FULLY_QUALIFIED_TYPE_NAME));
		
		//test persistentType list as class in persistence.xml
		getJpaProject().setDiscoversAnnotatedClasses(false);
		XmlJavaClassRef classRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		classRef.setJavaClass(FULLY_QUALIFIED_TYPE_NAME);
		getXmlPersistenceUnit().getClasses().add(classRef);
		assertNotNull(persistenceUnit.getPersistentType(FULLY_QUALIFIED_TYPE_NAME));

		
		//test persistentType from orm.xml file that is specified in the persistence.xml
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		assertNotNull(persistenceUnit.getPersistentType("model.Foo"));
		assertEquals(ormPersistentType, persistenceUnit.getPersistentType("model.Foo"));

		//test persistentType from orm.xml file that is implied(not specified) in the persistence.xml
		getXmlPersistenceUnit().getMappingFiles().remove(0);
		assertNotNull(persistenceUnit.getPersistentType("model.Foo"));
	}

	public void testGetMappingFileRefsContaining() throws Exception {
		createOrmXmlFile(ORM2_XML_FILE_NAME);
		createOrmXmlFile(ORM3_XML_FILE_NAME);
		PersistenceUnit persistenceUnit = getPersistenceUnit();

		Iterable<MappingFileRef> mappingFileRefs = persistenceUnit.getMappingFileRefsContaining(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(true, IterableTools.isEmpty(mappingFileRefs));

		OrmXml ormXml = (OrmXml) IterableTools.get(persistenceUnit.getMappingFileRefs(), 0).getMappingFile();
		OrmXml orm2Xml = (OrmXml) IterableTools.get(persistenceUnit.getMappingFileRefs(), 1).getMappingFile();
		OrmXml orm3Xml = (OrmXml) IterableTools.get(persistenceUnit.getMappingFileRefs(), 2).getMappingFile();

		ormXml.getRoot().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);

		mappingFileRefs = persistenceUnit.getMappingFileRefsContaining(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(1, IterableTools.size(mappingFileRefs));
		assertEquals(ormXml, IterableTools.get(mappingFileRefs, 0).getMappingFile());

		orm2Xml.getRoot().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);

		mappingFileRefs = persistenceUnit.getMappingFileRefsContaining(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(2, IterableTools.size(mappingFileRefs));
		assertEquals(ormXml, IterableTools.get(mappingFileRefs, 0).getMappingFile());
		assertEquals(orm2Xml, IterableTools.get(mappingFileRefs, 1).getMappingFile());

		orm3Xml.getRoot().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);

		mappingFileRefs = persistenceUnit.getMappingFileRefsContaining(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(3, IterableTools.size(mappingFileRefs));
		assertEquals(ormXml, IterableTools.get(mappingFileRefs, 0).getMappingFile());
		assertEquals(orm2Xml, IterableTools.get(mappingFileRefs, 1).getMappingFile());
		assertEquals(orm3Xml, IterableTools.get(mappingFileRefs, 2).getMappingFile());
		
	}	
	
//TODO
//	String getDefaultSchema();
//	String getDefaultCatalog();
//	AccessType getDefaultAccess();
//	boolean getDefaultCascadePersist();

}

/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.persistence;

import java.io.IOException;
import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmResource;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnitTransactionType;
import org.eclipse.jpt.core.resource.persistence.XmlProperties;
import org.eclipse.jpt.core.resource.persistence.XmlProperty;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class PersistenceUnitTests extends ContextModelTestCase
{
	
	protected static final String INNER_CLASS_NAME = "InnerAnnotationTestType";
	protected static final String FULLY_QUALIFIED_INNER_CLASS_NAME = PACKAGE_NAME + "." + TYPE_NAME + "." + INNER_CLASS_NAME;

	public PersistenceUnitTests(String name) {
		super(name);
	}
		
	public void testUpdateName() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
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
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
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
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
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
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
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
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
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
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
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
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
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
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
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
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
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
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
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
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
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
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
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
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// test that there is one initially
		OrmResource ormResource = ormResource();
		assertTrue(ormResource.exists());
		assertNotNull(persistenceUnit.getImpliedMappingFileRef());
		
		// remove orm.xml
		deleteResource(ormResource);
		
		assertFalse(ormResource.exists());
		assertNull(persistenceUnit.getImpliedMappingFileRef());
	}
	
	public void testUpdateImpliedMappingFileRef2() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// test that there is one initially
		OrmResource ormResource = ormResource();
		assertTrue(ormResource.exists());
		assertNotNull(persistenceUnit.getImpliedMappingFileRef());
		
		// add specified orm.xml
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("META-INF/orm.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		
		assertEquals(1, CollectionTools.size(persistenceUnit.specifiedMappingFileRefs()));
		
		assertTrue(ormResource.exists());
		assertNull(persistenceUnit.getImpliedMappingFileRef());
	}
	
	public void testUpdateSpecifiedMappingFileRefs1() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// test there are none initially
		assertEquals(0, xmlPersistenceUnit.getMappingFiles().size());
		assertEquals(0, persistenceUnit.specifiedMappingFileRefsSize());
		
		// add mapping file ref, test that it's added to context
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		
		assertEquals(1, CollectionTools.size(persistenceUnit.specifiedMappingFileRefs()));
		
		// add another ...
		xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm2.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		
		assertEquals(2, CollectionTools.size(persistenceUnit.specifiedMappingFileRefs()));
	}
	
	public void testUpdateSpecifiedMappingFileRefs2() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// add two mapping file refs and test that there are two existing in xml and context
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm2.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		
		assertEquals(xmlPersistenceUnit.getMappingFiles().size(), 2);
		assertEquals(2, CollectionTools.size(persistenceUnit.specifiedMappingFileRefs()));
		
		// remove mapping file ref from xml, test that it's removed from context
		xmlMappingFileRef = xmlPersistenceUnit.getMappingFiles().get(0);
		xmlPersistenceUnit.getMappingFiles().remove(xmlMappingFileRef);
		
		assertEquals(1, CollectionTools.size(persistenceUnit.specifiedMappingFileRefs()));
		
		// remove another one ...
		xmlMappingFileRef = xmlPersistenceUnit.getMappingFiles().get(0);
		xmlPersistenceUnit.getMappingFiles().remove(xmlMappingFileRef);
		
		assertEquals(0, CollectionTools.size(persistenceUnit.specifiedMappingFileRefs()));
	}
	
	public void testModifySpecifiedMappingFileRefs1() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// test there are none initially
		assertEquals(0, xmlPersistenceUnit.getMappingFiles().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.specifiedMappingFileRefs()));
		
		// add mapping file ref, test that it's added to resource
		persistenceUnit.addSpecifiedMappingFileRef().setFileName("foo");
		
		assertEquals(1, xmlPersistenceUnit.getMappingFiles().size());
		assertEquals("foo", xmlPersistenceUnit.getMappingFiles().get(0).getFileName());
		
		// add another ...
		persistenceUnit.addSpecifiedMappingFileRef().setFileName("bar");
		assertEquals("foo", xmlPersistenceUnit.getMappingFiles().get(0).getFileName());
		assertEquals("bar", xmlPersistenceUnit.getMappingFiles().get(1).getFileName());
		
		assertEquals(2, xmlPersistenceUnit.getMappingFiles().size());
		
		// add another, testing order
		persistenceUnit.addSpecifiedMappingFileRef(0).setFileName("baz");
		assertEquals(3, xmlPersistenceUnit.getMappingFiles().size());
		assertEquals("baz", xmlPersistenceUnit.getMappingFiles().get(0).getFileName());
		assertEquals("foo", xmlPersistenceUnit.getMappingFiles().get(1).getFileName());
		assertEquals("bar", xmlPersistenceUnit.getMappingFiles().get(2).getFileName());
	}
	
	public void testModifySpecifiedMappingFileRefs2() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// add two mapping file refs and test that there are two existing in xml and context
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm2.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		
		assertEquals(2, xmlPersistenceUnit.getMappingFiles().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.specifiedMappingFileRefs()));
		
		// remove mapping file ref from context, test that it's removed from xml
		persistenceUnit.removeSpecifiedMappingFileRef(0);
		
		assertEquals(1, xmlPersistenceUnit.getMappingFiles().size());
			
		// remove another one ...
		persistenceUnit.removeSpecifiedMappingFileRef(0);
		
		assertEquals(0, xmlPersistenceUnit.getMappingFiles().size());
	}
	
	public void testUpdateClassRefs1() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// test there are none initially
		assertEquals(0, xmlPersistenceUnit.getClasses().size());
		assertEquals(0, persistenceUnit.specifiedClassRefsSize());
		
		// add mapping file ref, test that it's added to context
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Bar");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		
		assertEquals(1, persistenceUnit.specifiedClassRefsSize());
		
		// add another ...
		xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Baz");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		
		assertEquals(2, persistenceUnit.specifiedClassRefsSize());
	}
	
	public void testUpdateClassRefs2() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// add two class refs and test that there are two existing in xml and context
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Bar");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Baz");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		
		assertEquals(2, xmlPersistenceUnit.getClasses().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.specifiedClassRefs()));
		
		// remove class ref from xml, test that it's removed from context
		xmlClassRef = xmlPersistenceUnit.getClasses().get(0);
		xmlPersistenceUnit.getClasses().remove(xmlClassRef);
		
		assertEquals(1, CollectionTools.size(persistenceUnit.specifiedClassRefs()));
		
		// remove another one ...
		xmlClassRef = xmlPersistenceUnit.getClasses().get(0);
		xmlPersistenceUnit.getClasses().remove(xmlClassRef);
		
		assertEquals(0, CollectionTools.size(persistenceUnit.specifiedClassRefs()));
	}
	
	public void testModifyClassRefs1() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// test there are none initially
		assertEquals(0, xmlPersistenceUnit.getClasses().size());
		assertEquals(0, persistenceUnit.specifiedClassRefsSize());
		
		// add class ref, test that it's added to context
		persistenceUnit.addSpecifiedClassRef().setClassName("Foo");
		
		try {
			persistenceResource().save(null);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(1, xmlPersistenceUnit.getClasses().size());
		assertEquals("Foo", xmlPersistenceUnit.getClasses().get(0).getJavaClass());
	
		// add another ...
		persistenceUnit.addSpecifiedClassRef().setClassName("Bar");
		
		assertEquals(2, xmlPersistenceUnit.getClasses().size());
		assertEquals("Foo", xmlPersistenceUnit.getClasses().get(0).getJavaClass());
		assertEquals("Bar", xmlPersistenceUnit.getClasses().get(1).getJavaClass());
	
		
		persistenceUnit.addSpecifiedClassRef(0).setClassName("Baz");
		
		assertEquals(3, xmlPersistenceUnit.getClasses().size());
		assertEquals("Baz", xmlPersistenceUnit.getClasses().get(0).getJavaClass());
		assertEquals("Foo", xmlPersistenceUnit.getClasses().get(1).getJavaClass());
		assertEquals("Bar", xmlPersistenceUnit.getClasses().get(2).getJavaClass());
	}
	
	public void testModifyClassRefs2() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// add two class refs and test that there are two existing in xml and context
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Bar");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Baz");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		
		assertEquals(2, xmlPersistenceUnit.getClasses().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.specifiedClassRefs()));
		
		// remove class ref from context, test that it's removed from xml
		persistenceUnit.removeSpecifiedClassRef(0);
		
		assertEquals(1, xmlPersistenceUnit.getClasses().size());
		
		// remove another one ...
		persistenceUnit.removeSpecifiedClassRef(0);
		
		assertEquals(0, xmlPersistenceUnit.getClasses().size());
	}

	public void testImpliedClassRefs() throws Exception {
		createTestEntityWithPersistentInnerClass();
		ListIterator<ClassRef> classRefs = persistenceUnit().impliedClassRefs();
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, classRefs.next().getClassName());
		assertEquals(FULLY_QUALIFIED_INNER_CLASS_NAME, classRefs.next().getClassName());
		
		jpaProject().setDiscoversAnnotatedClasses(true);
		classRefs = persistenceUnit().impliedClassRefs();
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, classRefs.next().getClassName());
		assertEquals(FULLY_QUALIFIED_INNER_CLASS_NAME, classRefs.next().getClassName());
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		classRefs = persistenceUnit().impliedClassRefs();
		assertEquals(FULLY_QUALIFIED_INNER_CLASS_NAME, classRefs.next().getClassName());
		assertFalse(classRefs.hasNext());
		
		
		removeXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		classRefs = persistenceUnit().impliedClassRefs();
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, classRefs.next().getClassName());
		assertEquals(FULLY_QUALIFIED_INNER_CLASS_NAME, classRefs.next().getClassName());
		
		entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		classRefs = persistenceUnit().impliedClassRefs();
		assertEquals(FULLY_QUALIFIED_INNER_CLASS_NAME, classRefs.next().getClassName());
		assertFalse(classRefs.hasNext());
		
		addXmlClassRef(FULLY_QUALIFIED_INNER_CLASS_NAME);
		classRefs = persistenceUnit().impliedClassRefs();
		assertFalse(classRefs.hasNext());
		
		removeXmlClassRef(FULLY_QUALIFIED_INNER_CLASS_NAME);
		classRefs = persistenceUnit().impliedClassRefs();
		assertEquals(FULLY_QUALIFIED_INNER_CLASS_NAME, classRefs.next().getClassName());
		assertFalse(classRefs.hasNext());

		entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_INNER_CLASS_NAME);
		classRefs = persistenceUnit().impliedClassRefs();
		assertFalse(classRefs.hasNext());
	}
	
	public void testUpdateExcludeUnlistedClasses() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// 1 - initial value is default
		assertNull(persistenceUnit.getSpecifiedExcludeUnlistedClasses());
		assertFalse(persistenceUnit.isExcludeUnlistedClasses());
		assertNull(xmlPersistenceUnit.getExcludeUnlistedClasses());
		
		// 2 - set value, context changed
		xmlPersistenceUnit.setExcludeUnlistedClasses(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, persistenceUnit.getSpecifiedExcludeUnlistedClasses());
		assertTrue(persistenceUnit.isExcludeUnlistedClasses());
		assertEquals(Boolean.TRUE, xmlPersistenceUnit.getExcludeUnlistedClasses());
		
		xmlPersistenceUnit.setExcludeUnlistedClasses(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, persistenceUnit.getSpecifiedExcludeUnlistedClasses());
		assertFalse(persistenceUnit.isExcludeUnlistedClasses());
		assertEquals(Boolean.FALSE, xmlPersistenceUnit.getExcludeUnlistedClasses());
		
		// 3 - unset value, context changed
		xmlPersistenceUnit.setExcludeUnlistedClasses(null);
		
		assertNull(persistenceUnit.getSpecifiedExcludeUnlistedClasses());
		assertFalse(persistenceUnit.isExcludeUnlistedClasses());
		assertNull(xmlPersistenceUnit.getExcludeUnlistedClasses());
	}
	
	public void testModifyExcludeUnlistedClasses() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// 1 - initial value is default
		assertNull(persistenceUnit.getSpecifiedExcludeUnlistedClasses());
		assertFalse(persistenceUnit.isExcludeUnlistedClasses());
		assertNull(xmlPersistenceUnit.getExcludeUnlistedClasses());
		
		// 2 - set value, resource changed
		persistenceUnit.setSpecifiedExcludeUnlistedClasses(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, persistenceUnit.getSpecifiedExcludeUnlistedClasses());
		assertTrue(persistenceUnit.isExcludeUnlistedClasses());
		assertEquals(Boolean.TRUE, xmlPersistenceUnit.getExcludeUnlistedClasses());
		
		persistenceUnit.setSpecifiedExcludeUnlistedClasses(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, persistenceUnit.getSpecifiedExcludeUnlistedClasses());
		assertFalse(persistenceUnit.isExcludeUnlistedClasses());
		assertEquals(Boolean.FALSE, xmlPersistenceUnit.getExcludeUnlistedClasses());
		
		// 3 - set context to default, resource unset
		persistenceUnit.setSpecifiedExcludeUnlistedClasses(null);
		
		assertNull(persistenceUnit.getSpecifiedExcludeUnlistedClasses());
		assertFalse(persistenceUnit.isExcludeUnlistedClasses());
		assertNull(xmlPersistenceUnit.getExcludeUnlistedClasses());
	}
	
	public void testUpdateProperties1() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// test there are none initially
		assertNull(xmlPersistenceUnit.getProperties());
		assertEquals(0, CollectionTools.size(persistenceUnit.properties()));
		
		// add "properties", test that there's no real change to context
		XmlProperties xmlProperties = PersistenceFactory.eINSTANCE.createXmlProperties();
		xmlPersistenceUnit.setProperties(xmlProperties);
		assertEquals(0, CollectionTools.size(persistenceUnit.properties()));
		
		// add property, test that it's added to context
		XmlProperty xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		xmlProperty.setName("foo");
		xmlProperty.setValue("bar");
		xmlProperties.getProperties().add(xmlProperty);
		
		assertEquals(1, CollectionTools.size(persistenceUnit.properties()));
		
		// add another ...
		xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		xmlProperty.setName("FOO");
		xmlProperty.setValue("BAR");
		xmlProperties.getProperties().add(xmlProperty);
		
		assertEquals(2, CollectionTools.size(persistenceUnit.properties()));
	}
	
	public void testUpdateProperties2() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
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
		assertEquals(2, CollectionTools.size(persistenceUnit.properties()));
		
		// remove property from xml, test that it's removed from context
		xmlProperty = xmlProperties.getProperties().get(0);
		xmlProperties.getProperties().remove(xmlProperty);
		
		assertEquals(1, CollectionTools.size(persistenceUnit.properties()));
		
		// remove another one ...
		xmlProperty = xmlProperties.getProperties().get(0);
		xmlProperties.getProperties().remove(xmlProperty);
		
		assertEquals(0, CollectionTools.size(persistenceUnit.properties()));
	}
	
	public void testModifyProperties1() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// test there are none initially
		assertNull(xmlPersistenceUnit.getProperties());
		assertEquals(0, persistenceUnit.propertiesSize());
		
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
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
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
		assertEquals(2, CollectionTools.size(persistenceUnit.properties()));
		
		// remove property from context, test that it's removed from resource
		persistenceUnit.removeProperty("foo");
		
		assertEquals(1, xmlPersistenceUnit.getProperties().getProperties().size());
		
		// remove another one.  test that properties object is nulled
		persistenceUnit.removeProperty("FOO", "BAR");
		
		assertNull(xmlPersistenceUnit.getProperties());
	}
	
	public void testModifyProperties3() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// add two properties and test that there are two existing in xml and context
		persistenceUnit.putProperty("foo", "bar", false);
		persistenceUnit.putProperty("FOO", "BAR", false);
		
		assertEquals(2, xmlPersistenceUnit.getProperties().getProperties().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.properties()));
		
		// remove property from context, test that it's removed from resource
		persistenceUnit.removeProperty("foo", "bar");
		
		assertEquals(1, xmlPersistenceUnit.getProperties().getProperties().size());
		
		// remove another one, test that properties object is nulled
		persistenceUnit.removeProperty("FOO");
		
		assertNull(xmlPersistenceUnit.getProperties());
	}
	
	public void testModifyProperties4() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// add two properties and test that there are two existing in xml and context
		persistenceUnit.putProperty("foo", "bar", false);
		persistenceUnit.putProperty("FOO", "BAR", false);
		
		assertEquals(2, xmlPersistenceUnit.getProperties().getProperties().size());
		assertEquals(2, persistenceUnit.propertiesSize());
		
		// modify a property, test its value
		persistenceUnit.putProperty("foo", "", false);
		assertEquals("", persistenceUnit.getProperty("foo").getValue());

		persistenceUnit.putProperty("foo", "BAR", false);
		assertEquals("BAR", persistenceUnit.getProperty("foo").getValue());
		
		// remove property that doesn't from context, test that the resource is unchanged
		persistenceUnit.removeProperty("notExist");
		assertEquals(2, xmlPersistenceUnit.getProperties().getProperties().size());
		
		// remove property from context, test that it's removed from resource
		persistenceUnit.removeProperty("FOO");
		assertNull(persistenceUnit.getProperty("FOO"));
		assertEquals(1, xmlPersistenceUnit.getProperties().getProperties().size());

		// remove by setting value to null, test that properties object is nulled
		persistenceUnit.putProperty("notExist", null, false);
		assertNull(persistenceUnit.getProperty("notExist"));

		persistenceUnit.putProperty("foo", null, false);
		assertNull(persistenceUnit.getProperty("foo"));
		assertNull(xmlPersistenceUnit.getProperties());
	}
	
	public void testModifyProperties5() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// testing duplicate keys, add four properties and test that there are four existing in xml and context
		persistenceUnit.putProperty("FOO", "BAR", false);
		persistenceUnit.putProperty("foo", "bar 3", true);
		persistenceUnit.putProperty("foo", "bar 2", true);
		persistenceUnit.putProperty("foo", "bar 1", true);
		
		assertEquals(4, xmlPersistenceUnit.getProperties().getProperties().size());
		assertEquals(4, CollectionTools.size(persistenceUnit.properties()));
		
		// modify a property, test its value
		persistenceUnit.replacePropertyValue("foo", "bar 2", "bar two");
		
		Property property = persistenceUnit.getProperty("foo", "bar two");
		assertEquals("bar two", property.getValue());

		// remove a property, test that there are four existing in xml and context
		persistenceUnit.removeProperty("foo", "bar 1");
		assertEquals(3, xmlPersistenceUnit.getProperties().getProperties().size());
		assertEquals(3, CollectionTools.size(persistenceUnit.properties()));
	}
	
	public void testAccessProperty() {
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// add two properties and try to access it.
		persistenceUnit.putProperty("foo", "bar", false);
		persistenceUnit.putProperty("FOO", "BAR", false);
		
		Property property = persistenceUnit.getProperty("foo");
		assertNotNull(property);
		assertEquals("bar", property.getValue());
		assertTrue(persistenceUnit.containsProperty("FOO"));
		assertEquals("BAR", persistenceUnit.getProperty("FOO").getValue());
		assertNull(persistenceUnit.getProperty("notExist"));
	}
	
	public void testUpdatePropertyName() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// add property for testing
		XmlProperties xmlProperties = PersistenceFactory.eINSTANCE.createXmlProperties();
		xmlPersistenceUnit.setProperties(xmlProperties);
		XmlProperty xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		xmlProperties.getProperties().add(xmlProperty);
		Property property = persistenceUnit.properties().next();
		
		// test that names are initially equal
		assertEquals(xmlProperty.getName(), property.getName());
		
		// set name to different name, test equality
		xmlProperty.setName("newName");
		
		assertEquals(xmlProperty.getName(), property.getName());
		
		// set name to empty string, test equality
		xmlProperty.setName("");
		
		assertEquals(xmlProperty.getName(), property.getName());
		
		// set name to null, test equality
		xmlProperty.setName(null);
		
		assertEquals(xmlProperty.getName(), property.getName());
		
		// set name back to non-null, test equality
		xmlProperty.setName("newName");
		
		assertEquals(xmlProperty.getName(), property.getName());
	}
	
	public void testUpdatePropertyValue() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		// add property for testing
		XmlProperties xmlProperties = PersistenceFactory.eINSTANCE.createXmlProperties();
		xmlPersistenceUnit.setProperties(xmlProperties);
		XmlProperty xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		xmlProperties.getProperties().add(xmlProperty);
		Property property = persistenceUnit.properties().next();
		
		// test that values are initially equal
		assertEquals(xmlProperty.getValue(), property.getValue());
		
		// set value to different value, test equality
		xmlProperty.setValue("newValue");
		
		assertEquals(xmlProperty.getValue(), property.getValue());
		
		// set value to empty string, test equality
		xmlProperty.setValue("");
		
		assertEquals(xmlProperty.getValue(), property.getValue());
		
		// set value to null, test equality
		xmlProperty.setValue(null);
		
		assertEquals(xmlProperty.getValue(), property.getValue());
		
		// set value back to non-null, test equality
		xmlProperty.setValue("newValue");
		
		assertEquals(xmlProperty.getValue(), property.getValue());
	}
	
	
	public void testGetDefaultAccess() throws Exception {
		createOrmXmlFile();		
		PersistenceUnit persistenceUnit = persistenceUnit();
		
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);		
		assertEquals(AccessType.PROPERTY, persistenceUnit.getDefaultAccess());
		
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.FIELD);	
		assertEquals(AccessType.FIELD, persistenceUnit.getDefaultAccess());
	}

	protected void createOrmXmlFile() throws Exception {
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		persistenceResource().save(null);
	}
	
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}

	private IType createTestEntity() throws Exception {
		createEntityAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
		});
	}
	
	private IType createTestEntityWithPersistentInnerClass() throws Exception {
		createEntityAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
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
		PersistenceUnit persistenceUnit = persistenceUnit();
		createTestEntity();
		
		//persistentType not listed in persistence.xml and discoverAnnotatedClasses is false
		//still find the persistentType because of changes for bug 190317
		assertFalse(jpaProject().discoversAnnotatedClasses());
		assertNotNull(persistenceUnit.getPersistentType(FULLY_QUALIFIED_TYPE_NAME));
		
		//test persistentType not listed in persistence.xml, discover annotated classes set to true
		jpaProject().setDiscoversAnnotatedClasses(true);	
		assertNotNull(persistenceUnit.getPersistentType(FULLY_QUALIFIED_TYPE_NAME));
		
		//test persistentType list as class in persistence.xml
		jpaProject().setDiscoversAnnotatedClasses(false);
		XmlJavaClassRef classRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		classRef.setJavaClass(FULLY_QUALIFIED_TYPE_NAME);
		xmlPersistenceUnit().getClasses().add(classRef);
		assertNotNull(persistenceUnit.getPersistentType(FULLY_QUALIFIED_TYPE_NAME));

		
		//test persistentType from orm.xml file that is specified in the persistence.xml
		createOrmXmlFile();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		assertNotNull(persistenceUnit.getPersistentType("model.Foo"));
		assertEquals(ormPersistentType, persistenceUnit.getPersistentType("model.Foo"));

		//test persistentType from orm.xml file that is implied(not specified) in the persistence.xml
		xmlPersistenceUnit().getMappingFiles().remove(0);
		assertNotNull(persistenceUnit.getPersistentType("model.Foo"));
	}
	
//TODO
//	String getDefaultSchema();
//	String getDefaultCatalog();
//	AccessType getDefaultAccess();
//	boolean getDefaultCascadePersist();

}

/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.persistence;

import org.eclipse.jpt.core.internal.context.base.IPersistenceUnit;
import org.eclipse.jpt.core.internal.context.base.IProperty;
import org.eclipse.jpt.core.internal.context.base.PersistenceUnitTransactionType;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnitTransactionType;
import org.eclipse.jpt.core.internal.resource.persistence.XmlProperties;
import org.eclipse.jpt.core.internal.resource.persistence.XmlProperty;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class PersistenceUnitTests extends ContextModelTestCase
{
	public PersistenceUnitTests(String name) {
		super(name);
	}
	
	protected XmlPersistenceUnit xmlPersistenceUnit() {
		PersistenceResource prm = persistenceResource();
		return prm.getPersistence().getPersistenceUnits().get(0);
	}
	
	protected IPersistenceUnit persistenceUnit() {
		return jpaContent().getPersistenceXml().getPersistence().persistenceUnits().next();
	}
	
	public void testUpdateName() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
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
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
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
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// 1 - initial value is default
		assertNull(xmlPersistenceUnit.getTransactionType());
		assertEquals(persistenceUnit.getTransactionType(), PersistenceUnitTransactionType.DEFAULT);
		
		// 2 - set value, context changed
		xmlPersistenceUnit.setTransactionType(XmlPersistenceUnitTransactionType.JTA);
		
		assertEquals(persistenceUnit.getTransactionType(), PersistenceUnitTransactionType.JTA);
		
		xmlPersistenceUnit.setTransactionType(XmlPersistenceUnitTransactionType.RESOURCE_LOCAL);
		
		assertEquals(persistenceUnit.getTransactionType(), PersistenceUnitTransactionType.RESOURCE_LOCAL);
		
		// 3 - unset value, context changed
		xmlPersistenceUnit.unsetTransactionType();
		
		assertEquals(persistenceUnit.getTransactionType(), PersistenceUnitTransactionType.DEFAULT);
	}
	
	public void testModifyTransactionType() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// 1 - initial value is default
		assertNull(xmlPersistenceUnit.getTransactionType());
		assertEquals(persistenceUnit.getTransactionType(), PersistenceUnitTransactionType.DEFAULT);
		
		// 2 - set context value, resource changed
		persistenceUnit.setTransactionType(PersistenceUnitTransactionType.JTA);
		
		assertEquals(xmlPersistenceUnit.getTransactionType(), XmlPersistenceUnitTransactionType.JTA);
		
		persistenceUnit.setTransactionType(PersistenceUnitTransactionType.RESOURCE_LOCAL);
		
		assertEquals(xmlPersistenceUnit.getTransactionType(), XmlPersistenceUnitTransactionType.RESOURCE_LOCAL);
		
		// 3 - set context value to default, resource unset
		persistenceUnit.setTransactionTypeToDefault();
		
		assertTrue(persistenceUnit.isTransactionTypeDefault());
		assertFalse(xmlPersistenceUnit.isSetTransactionType());
	}
	
	public void testUpdateDescription() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
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
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
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
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
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
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
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
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
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
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
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
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
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
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
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
	
	public void testUpdateMappingFileRefs1() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// test there are none initially
		assertEquals(xmlPersistenceUnit.getMappingFiles().size(), 0);
		assertEquals(CollectionTools.size(persistenceUnit.mappingFileRefs()), 0);
		
		// add mapping file ref, test that it's added to context
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		
		assertEquals(CollectionTools.size(persistenceUnit.mappingFileRefs()), 1);
		
		// add another ...
		xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm2.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		
		assertEquals(CollectionTools.size(persistenceUnit.mappingFileRefs()), 2);
	}
	
	public void testUpdateMappingFileRefs2() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// add two mapping file refs and test that there are two existing in xml and context
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm2.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		
		assertEquals(xmlPersistenceUnit.getMappingFiles().size(), 2);
		assertEquals(CollectionTools.size(persistenceUnit.mappingFileRefs()), 2);
		
		// remove mapping file ref from xml, test that it's removed from context
		xmlMappingFileRef = xmlPersistenceUnit.getMappingFiles().get(0);
		xmlPersistenceUnit.getMappingFiles().remove(xmlMappingFileRef);
		
		assertEquals(CollectionTools.size(persistenceUnit.mappingFileRefs()), 1);
		
		// remove another one ...
		xmlMappingFileRef = xmlPersistenceUnit.getMappingFiles().get(0);
		xmlPersistenceUnit.getMappingFiles().remove(xmlMappingFileRef);
		
		assertEquals(CollectionTools.size(persistenceUnit.mappingFileRefs()), 0);
	}
	
	public void testModifyMappingFileRefs1() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// test there are none initially
		assertEquals(xmlPersistenceUnit.getMappingFiles().size(), 0);
		assertEquals(CollectionTools.size(persistenceUnit.mappingFileRefs()), 0);
		
		// add mapping file ref, test that it's added to resource
		persistenceUnit.addMappingFileRef();
		
		assertEquals(xmlPersistenceUnit.getMappingFiles().size(), 1);
		
		// add another ...
		persistenceUnit.addMappingFileRef();
		
		assertEquals(xmlPersistenceUnit.getMappingFiles().size(), 2);
	}
	
	public void testModifyMappingFileRefs2() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// add two mapping file refs and test that there are two existing in xml and context
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("orm2.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		
		assertEquals(xmlPersistenceUnit.getMappingFiles().size(), 2);
		assertEquals(CollectionTools.size(persistenceUnit.mappingFileRefs()), 2);
		
		// remove mapping file ref from context, test that it's removed from xml
		persistenceUnit.removeMappingFileRef(0);
		
		assertEquals(xmlPersistenceUnit.getMappingFiles().size(), 1);
			
		// remove another one ...
		persistenceUnit.removeMappingFileRef(0);
		
		assertEquals(xmlPersistenceUnit.getMappingFiles().size(), 0);
	}
	
	public void testUpdateClassRefs1() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// test there are none initially
		assertEquals(xmlPersistenceUnit.getClasses().size(), 0);
		assertEquals(CollectionTools.size(persistenceUnit.classRefs()), 0);
		
		// add mapping file ref, test that it's added to context
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Bar");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		
		assertEquals(CollectionTools.size(persistenceUnit.classRefs()), 1);
		
		// add another ...
		xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Baz");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		
		assertEquals(CollectionTools.size(persistenceUnit.classRefs()), 2);
	}
	
	public void testUpdateClassRefs2() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// add two class refs and test that there are two existing in xml and context
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Bar");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Baz");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		
		assertEquals(xmlPersistenceUnit.getClasses().size(), 2);
		assertEquals(CollectionTools.size(persistenceUnit.classRefs()), 2);
		
		// remove class ref from xml, test that it's removed from context
		xmlClassRef = xmlPersistenceUnit.getClasses().get(0);
		xmlPersistenceUnit.getClasses().remove(xmlClassRef);
		
		assertEquals(CollectionTools.size(persistenceUnit.classRefs()), 1);
		
		// remove another one ...
		xmlClassRef = xmlPersistenceUnit.getClasses().get(0);
		xmlPersistenceUnit.getClasses().remove(xmlClassRef);
		
		assertEquals(CollectionTools.size(persistenceUnit.classRefs()), 0);
	}
	
	public void testModifyClassRefs1() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// test there are none initially
		assertEquals(xmlPersistenceUnit.getClasses().size(), 0);
		assertEquals(CollectionTools.size(persistenceUnit.classRefs()), 0);
		
		// add class ref, test that it's added to context
		persistenceUnit.addClassRef();
		
		assertEquals(xmlPersistenceUnit.getClasses().size(), 1);
		
		// add another ...
		persistenceUnit.addClassRef();
		
		assertEquals(xmlPersistenceUnit.getClasses().size(), 2);
	}
	
	public void testModifyClassRefs2() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// add two class refs and test that there are two existing in xml and context
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Bar");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Baz");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		
		assertEquals(xmlPersistenceUnit.getClasses().size(), 2);
		assertEquals(CollectionTools.size(persistenceUnit.classRefs()), 2);
		
		// remove class ref from context, test that it's removed from xml
		persistenceUnit.removeClassRef(0);
		
		assertEquals(xmlPersistenceUnit.getClasses().size(), 1);
		
		// remove another one ...
		persistenceUnit.removeClassRef(0);
		
		assertEquals(xmlPersistenceUnit.getClasses().size(), 0);
	}
	
	public void testUpdateExcludeUnlistedClasses() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// 1 - initial value is default
		assertFalse(xmlPersistenceUnit.isSetExcludeUnlistedClasses());
		assertTrue(persistenceUnit.isExcludeUnlistedClassesDefault());
		assertEquals(persistenceUnit.getExcludeUnlistedClasses(), xmlPersistenceUnit.isExcludeUnlistedClasses());
		
		// 2 - set value, context changed
		xmlPersistenceUnit.setExcludeUnlistedClasses(true);
		
		assertTrue(xmlPersistenceUnit.isSetExcludeUnlistedClasses());
		assertFalse(persistenceUnit.isExcludeUnlistedClassesDefault());
		assertEquals(persistenceUnit.getExcludeUnlistedClasses(), xmlPersistenceUnit.isExcludeUnlistedClasses());
		
		xmlPersistenceUnit.setExcludeUnlistedClasses(false);
		
		assertTrue(xmlPersistenceUnit.isSetExcludeUnlistedClasses());
		assertFalse(persistenceUnit.isExcludeUnlistedClassesDefault());
		assertEquals(persistenceUnit.getExcludeUnlistedClasses(), xmlPersistenceUnit.isExcludeUnlistedClasses());
		
		// 3 - unset value, context changed
		xmlPersistenceUnit.unsetExcludeUnlistedClasses();
		
		assertFalse(xmlPersistenceUnit.isSetExcludeUnlistedClasses());
		assertTrue(persistenceUnit.isExcludeUnlistedClassesDefault());
		assertEquals(persistenceUnit.getExcludeUnlistedClasses(), xmlPersistenceUnit.isExcludeUnlistedClasses());
	}
	
	public void testModifyExcludeUnlistedClasses() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// 1 - initial value is default
		assertFalse(xmlPersistenceUnit.isSetExcludeUnlistedClasses());
		assertTrue(persistenceUnit.isExcludeUnlistedClassesDefault());
		assertEquals(persistenceUnit.getExcludeUnlistedClasses(), xmlPersistenceUnit.isExcludeUnlistedClasses());
		
		// 2 - set value, resource changed
		persistenceUnit.setExcludeUnlistedClasses(true);
		
		assertTrue(xmlPersistenceUnit.isSetExcludeUnlistedClasses());
		assertFalse(persistenceUnit.isExcludeUnlistedClassesDefault());
		assertEquals(persistenceUnit.getExcludeUnlistedClasses(), xmlPersistenceUnit.isExcludeUnlistedClasses());
		
		persistenceUnit.setExcludeUnlistedClasses(false);
		
		assertTrue(xmlPersistenceUnit.isSetExcludeUnlistedClasses());
		assertFalse(persistenceUnit.isExcludeUnlistedClassesDefault());
		assertEquals(persistenceUnit.getExcludeUnlistedClasses(), xmlPersistenceUnit.isExcludeUnlistedClasses());
		
		// 3 - set context to default, resource unset
		persistenceUnit.setExcludeUnlistedClassesToDefault();
		
		assertFalse(xmlPersistenceUnit.isSetExcludeUnlistedClasses());
		assertTrue(persistenceUnit.isExcludeUnlistedClassesDefault());
		assertEquals(persistenceUnit.getExcludeUnlistedClasses(), xmlPersistenceUnit.isExcludeUnlistedClasses());
	}
	
	public void testUpdateProperties1() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// test there are none initially
		assertNull(xmlPersistenceUnit.getProperties());
		assertEquals(CollectionTools.size(persistenceUnit.properties()), 0);
		
		// add "properties", test that there's no real change to context
		XmlProperties xmlProperties = PersistenceFactory.eINSTANCE.createXmlProperties();
		xmlPersistenceUnit.setProperties(xmlProperties);
		assertEquals(CollectionTools.size(persistenceUnit.properties()), 0);
		
		// add property, test that it's added to context
		XmlProperty xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		xmlProperty.setName("foo");
		xmlProperty.setValue("bar");
		xmlProperties.getProperties().add(xmlProperty);
		
		assertEquals(CollectionTools.size(persistenceUnit.properties()), 1);
		
		// add another ...
		xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		xmlProperty.setName("FOO");
		xmlProperty.setValue("BAR");
		xmlProperties.getProperties().add(xmlProperty);
		
		assertEquals(CollectionTools.size(persistenceUnit.properties()), 2);
	}
	
	public void testUpdateProperties2() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
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
		
		assertEquals(xmlPersistenceUnit.getProperties().getProperties().size(), 2);
		assertEquals(CollectionTools.size(persistenceUnit.properties()), 2);
		
		// remove property from xml, test that it's removed from context
		xmlProperty = xmlProperties.getProperties().get(0);
		xmlProperties.getProperties().remove(xmlProperty);
		
		assertEquals(CollectionTools.size(persistenceUnit.properties()), 1);
		
		// remove another one ...
		xmlProperty = xmlProperties.getProperties().get(0);
		xmlProperties.getProperties().remove(xmlProperty);
		
		assertEquals(CollectionTools.size(persistenceUnit.properties()), 0);
	}
	
	public void testModifyProperties1() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// test there are none initially
		assertNull(xmlPersistenceUnit.getProperties());
		assertEquals(CollectionTools.size(persistenceUnit.properties()), 0);
		
		// add property, test that it's added to resource
		persistenceUnit.addProperty();
		
		assertNotNull(xmlPersistenceUnit.getProperties());
		assertEquals(xmlPersistenceUnit.getProperties().getProperties().size(), 1);
		
		// add another ...
		persistenceUnit.addProperty();
		
		assertEquals(xmlPersistenceUnit.getProperties().getProperties().size(), 2);
	}
	
	public void testModifyProperties2() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
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
		
		assertEquals(xmlPersistenceUnit.getProperties().getProperties().size(), 2);
		assertEquals(CollectionTools.size(persistenceUnit.properties()), 2);
		
		// remove property from context, test that it's removed from resource
		persistenceUnit.removeProperty(0);
		
		assertEquals(xmlPersistenceUnit.getProperties().getProperties().size(), 1);
		
		// remove another one.  test that properties object is nulled
		persistenceUnit.removeProperty(0);
		
		assertNull(xmlPersistenceUnit.getProperties());
	}
	
	public void testUpdatePropertyName() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// add property for testing
		XmlProperties xmlProperties = PersistenceFactory.eINSTANCE.createXmlProperties();
		xmlPersistenceUnit.setProperties(xmlProperties);
		XmlProperty xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		xmlProperties.getProperties().add(xmlProperty);
		IProperty property = persistenceUnit.properties().next();
		
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
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// add property for testing
		XmlProperties xmlProperties = PersistenceFactory.eINSTANCE.createXmlProperties();
		xmlPersistenceUnit.setProperties(xmlProperties);
		XmlProperty xmlProperty = PersistenceFactory.eINSTANCE.createXmlProperty();
		xmlProperties.getProperties().add(xmlProperty);
		IProperty property = persistenceUnit.properties().next();
		
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
}

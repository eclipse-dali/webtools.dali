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
package org.eclipse.jpt.core.tests.internal.context;

import org.eclipse.jpt.core.internal.context.base.IPersistenceUnit;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModel;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class PersistenceUnitTests extends ContextModelTestCase
{
	public PersistenceUnitTests(String name) {
		super(name);
	}
	
	protected XmlPersistenceUnit xmlPersistenceUnit() {
		PersistenceResourceModel prm = persistenceResourceModel();
		return prm.getPersistence().getPersistenceUnits().get(0);
	}
	
	protected IPersistenceUnit persistenceUnit() {
		return jpaContent().getPersistenceXml().getPersistence().persistenceUnits().next();
	}
	
	public void testSetName() {
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
	
	public void testSetTransactionType() {
		// TODO
	}
	
	public void testSetDescription() {
		// TODO
	}
	
	public void testSetProvider() {
		// TODO
	}
	
	public void testSetJtaDataSource() {
		// TODO
	}
	
	public void testSetNonJtaDataSource() {
		// TODO
	}
	
	public void testAddJarFileRef() {
		// TODO
	}
	
	public void testRemoveJarFileRef() {
		// TODO
	}
	
	public void testAddMappingFileRef() {
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
	
	public void testRemoveMappingFileRef() {
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
	
	public void testAddClassRef() {
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
	
	public void testRemoveClassRef() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// add two mapping file refs and test that there are two existing in xml and context
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Bar");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass("com.foo.Baz");
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
		
		assertEquals(xmlPersistenceUnit.getClasses().size(), 2);
		assertEquals(CollectionTools.size(persistenceUnit.classRefs()), 2);
		
		// remove mapping file ref from xml, test that it's removed from context
		xmlClassRef = xmlPersistenceUnit.getClasses().get(0);
		xmlPersistenceUnit.getClasses().remove(xmlClassRef);
		
		assertEquals(CollectionTools.size(persistenceUnit.classRefs()), 1);
		
		// remove another one ...
		xmlClassRef = xmlPersistenceUnit.getClasses().get(0);
		xmlPersistenceUnit.getClasses().remove(xmlClassRef);
		
		assertEquals(CollectionTools.size(persistenceUnit.classRefs()), 0);
	}
}

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

import org.eclipse.jpt.core.internal.context.base.IPersistence;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class PersistenceTests extends ContextModelTestCase
{
	public PersistenceTests(String name) {
		super(name);
	}
	
	
	protected XmlPersistence xmlPersistence() {
		return persistenceResource().getPersistence();
	}
	
	protected IPersistence persistence() {
		return jpaContent().getPersistenceXml().getPersistence();
	}
	
	public void testModifyAddPersistencUnit() {
		XmlPersistence xmlPersistence = xmlPersistence();
		IPersistence persistence = persistence();
		
		// clear xml persistence units, test that it's clear in context
		xmlPersistence.getPersistenceUnits().clear();
		assertEquals(CollectionTools.size(persistence.persistenceUnits()), 0);
		
		// add persistence unit, test that it's added to resource
		persistence.addPersistenceUnit();
		
		assertEquals(CollectionTools.size(persistence.persistenceUnits()), 1);
		
		// add another ...
		persistence.addPersistenceUnit();
		
		assertEquals(CollectionTools.size(persistence.persistenceUnits()), 2);
	}
	
	public void testModifyRemovePersistenceUnit() {
		XmlPersistence xmlPersistence = xmlPersistence();
		IPersistence persistence = persistence();
		
		// add a persistence unit and test that there are two existing xml and context persistence unit
		XmlPersistenceUnit xmlPersistenceUnit = PersistenceFactory.eINSTANCE.createXmlPersistenceUnit();
		xmlPersistenceUnit.setName("test");
		xmlPersistence.getPersistenceUnits().add(xmlPersistenceUnit);
		
		assertEquals(xmlPersistence.getPersistenceUnits().size(), 2);
		assertEquals(CollectionTools.size(persistence.persistenceUnits()), 2);
		
		// remove persistence unit, test that it's removed from resource
		persistence.removePersistenceUnit(0);
		
		assertEquals(CollectionTools.size(persistence.persistenceUnits()), 1);
		
		// remove another one ...
		persistence.removePersistenceUnit(0);
		
		assertEquals(CollectionTools.size(persistence.persistenceUnits()), 0);
	}
	
	public void testUpdateAddPersistenceUnit() throws Exception {
		PersistenceResource prm = persistenceResource();
		XmlPersistence xmlPersistence = prm.getPersistence();
		IPersistence persistence = jpaContent().getPersistenceXml().getPersistence();
		
		// clear xml persistence units, test that it's clear in context
		xmlPersistence.getPersistenceUnits().clear();
		
		assertTrue(CollectionTools.list(persistence.persistenceUnits()).isEmpty());
		
		// add xml persistence unit, test that it's added to context
		XmlPersistenceUnit xmlPersistenceUnit = PersistenceFactory.eINSTANCE.createXmlPersistenceUnit();
		xmlPersistenceUnit.setName("test");
		xmlPersistence.getPersistenceUnits().add(xmlPersistenceUnit);
		
		assertTrue(CollectionTools.size(persistence.persistenceUnits()) == 1);
		
		// add another ...
		xmlPersistenceUnit = PersistenceFactory.eINSTANCE.createXmlPersistenceUnit();
		xmlPersistenceUnit.setName("test2");
		xmlPersistence.getPersistenceUnits().add(xmlPersistenceUnit);
		
		assertTrue(CollectionTools.size(persistence.persistenceUnits()) == 2);
	}
	
	public void testUpdateRemovePersistenceUnit() throws Exception {
		PersistenceResource prm = persistenceResource();
		XmlPersistence xmlPersistence = prm.getPersistence();
		IPersistence persistence = jpaContent().getPersistenceXml().getPersistence();
		
		// add a persistence unit and test that there are two existing xml and context persistence unit
		XmlPersistenceUnit xmlPersistenceUnit = PersistenceFactory.eINSTANCE.createXmlPersistenceUnit();
		xmlPersistenceUnit.setName("test");
		xmlPersistence.getPersistenceUnits().add(xmlPersistenceUnit);
		
		assertTrue(xmlPersistence.getPersistenceUnits().size() == 2);
		assertTrue(CollectionTools.size(persistence.persistenceUnits()) == 2);
		
		// remove persistence unit from xml, test that it's removed from context
		xmlPersistenceUnit = xmlPersistence.getPersistenceUnits().get(0);
		xmlPersistence.getPersistenceUnits().remove(xmlPersistenceUnit);
		
		assertTrue(CollectionTools.size(persistence.persistenceUnits()) == 1);
		
		// remove another one ...
		xmlPersistenceUnit = xmlPersistence.getPersistenceUnits().get(0);
		xmlPersistence.getPersistenceUnits().remove(xmlPersistenceUnit);
		
		assertTrue(CollectionTools.size(persistence.persistenceUnits()) == 0);
	}
}

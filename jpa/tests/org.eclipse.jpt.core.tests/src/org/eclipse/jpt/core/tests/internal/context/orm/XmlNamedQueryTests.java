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
package org.eclipse.jpt.core.tests.internal.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.orm.XmlEntity;
import org.eclipse.jpt.core.internal.context.orm.XmlNamedQuery;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.context.orm.XmlQueryHint;
import org.eclipse.jpt.core.internal.resource.orm.NamedQuery;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlNamedQueryTests extends ContextModelTestCase
{
	public XmlNamedQueryTests(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		persistenceResource().save(null);
	}

	public void testUpdateName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedQuery xmlNamedQuery = xmlEntity.addNamedQuery(0);
		
		NamedQuery namedQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedQueries().get(0);
		
		assertNull(xmlNamedQuery.getName());
		assertNull(namedQueryResource.getName());
				
		//set name in the resource model, verify context model updated
		namedQueryResource.setName("newName");
		assertEquals("newName", xmlNamedQuery.getName());
		assertEquals("newName", namedQueryResource.getName());
	
		//set name to null in the resource model
		namedQueryResource.setName(null);
		assertNull(xmlNamedQuery.getName());
		assertNull(namedQueryResource.getName());
	}
	
	public void testModifyName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedQuery xmlNamedQuery = xmlEntity.addNamedQuery(0);
		
		NamedQuery namedQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedQueries().get(0);
		
		assertNull(xmlNamedQuery.getName());
		assertNull(namedQueryResource.getName());
				
		//set name in the context model, verify resource model updated
		xmlNamedQuery.setName("newName");
		assertEquals("newName", xmlNamedQuery.getName());
		assertEquals("newName", namedQueryResource.getName());
	
		//set name to null in the context model
		xmlNamedQuery.setName(null);
		assertNull(xmlNamedQuery.getName());
		assertNull(namedQueryResource.getName());
	}
	
	public void testUpdateQuery() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedQuery xmlNamedQuery = xmlEntity.addNamedQuery(0);
		
		NamedQuery namedQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedQueries().get(0);
		
		assertNull(xmlNamedQuery.getQuery());
		assertNull(namedQueryResource.getQuery());
				
		//set name in the resource model, verify context model updated
		namedQueryResource.setQuery("newName");
		assertEquals("newName", xmlNamedQuery.getQuery());
		assertEquals("newName", namedQueryResource.getQuery());
	
		//set name to null in the resource model
		namedQueryResource.setQuery(null);
		assertNull(xmlNamedQuery.getQuery());
		assertNull(namedQueryResource.getQuery());
	}
	
	public void testModifyQuery() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedQuery xmlNamedQuery = xmlEntity.addNamedQuery(0);
		
		NamedQuery namedQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedQueries().get(0);
		
		assertNull(xmlNamedQuery.getQuery());
		assertNull(namedQueryResource.getQuery());
				
		//set name in the context model, verify resource model updated
		xmlNamedQuery.setQuery("newName");
		assertEquals("newName", xmlNamedQuery.getQuery());
		assertEquals("newName", namedQueryResource.getQuery());
	
		//set name to null in the context model
		xmlNamedQuery.setQuery(null);
		assertNull(xmlNamedQuery.getQuery());
		assertNull(namedQueryResource.getQuery());
	}
	
	public void testAddHint() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedQuery xmlNamedQuery = xmlEntity.addNamedQuery(0);
		
		NamedQuery namedQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedQueries().get(0);

		XmlQueryHint queryHint = xmlNamedQuery.addHint(0);
		queryHint.setName("FOO");
				
		assertEquals("FOO", namedQueryResource.getHints().get(0).getName());
		
		XmlQueryHint queryHint2 = xmlNamedQuery.addHint(0);
		queryHint2.setName("BAR");
		
		assertEquals("BAR", namedQueryResource.getHints().get(0).getName());
		assertEquals("FOO", namedQueryResource.getHints().get(1).getName());
		
		XmlQueryHint queryHint3 = xmlNamedQuery.addHint(1);
		queryHint3.setName("BAZ");
		
		assertEquals("BAR", namedQueryResource.getHints().get(0).getName());
		assertEquals("BAZ", namedQueryResource.getHints().get(1).getName());
		assertEquals("FOO", namedQueryResource.getHints().get(2).getName());
		
		ListIterator<XmlQueryHint> queryHints = xmlNamedQuery.hints();
		assertEquals(queryHint2, queryHints.next());
		assertEquals(queryHint3, queryHints.next());
		assertEquals(queryHint, queryHints.next());
		
		queryHints = xmlNamedQuery.hints();
		assertEquals("BAR", queryHints.next().getName());
		assertEquals("BAZ", queryHints.next().getName());
		assertEquals("FOO", queryHints.next().getName());
	}
	
	public void testRemoveHint() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedQuery xmlNamedQuery = xmlEntity.addNamedQuery(0);
		
		NamedQuery namedQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedQueries().get(0);

		xmlNamedQuery.addHint(0).setName("FOO");
		xmlNamedQuery.addHint(1).setName("BAR");
		xmlNamedQuery.addHint(2).setName("BAZ");
		
		assertEquals(3, namedQueryResource.getHints().size());
		
		xmlNamedQuery.removeHint(0);
		assertEquals(2, namedQueryResource.getHints().size());
		assertEquals("BAR", namedQueryResource.getHints().get(0).getName());
		assertEquals("BAZ", namedQueryResource.getHints().get(1).getName());

		xmlNamedQuery.removeHint(0);
		assertEquals(1, namedQueryResource.getHints().size());
		assertEquals("BAZ", namedQueryResource.getHints().get(0).getName());
		
		xmlNamedQuery.removeHint(0);
		assertEquals(0, namedQueryResource.getHints().size());
	}
	
	public void testMoveHint() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedQuery xmlNamedQuery = xmlEntity.addNamedQuery(0);
		
		NamedQuery namedQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedQueries().get(0);

		xmlNamedQuery.addHint(0).setName("FOO");
		xmlNamedQuery.addHint(1).setName("BAR");
		xmlNamedQuery.addHint(2).setName("BAZ");
		
		assertEquals(3, namedQueryResource.getHints().size());
		
		
		xmlNamedQuery.moveHint(2, 0);
		ListIterator<XmlQueryHint> hints = xmlNamedQuery.hints();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());

		assertEquals("BAR", namedQueryResource.getHints().get(0).getName());
		assertEquals("BAZ", namedQueryResource.getHints().get(1).getName());
		assertEquals("FOO", namedQueryResource.getHints().get(2).getName());


		xmlNamedQuery.moveHint(0, 1);
		hints = xmlNamedQuery.hints();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("FOO", hints.next().getName());

		assertEquals("BAZ", namedQueryResource.getHints().get(0).getName());
		assertEquals("BAR", namedQueryResource.getHints().get(1).getName());
		assertEquals("FOO", namedQueryResource.getHints().get(2).getName());
	}
	
	public void testUpdateHints() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedQuery xmlNamedQuery = xmlEntity.addNamedQuery(0);
		
		NamedQuery namedQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedQueries().get(0);

		namedQueryResource.getHints().add(OrmFactory.eINSTANCE.createQueryHint());
		namedQueryResource.getHints().add(OrmFactory.eINSTANCE.createQueryHint());
		namedQueryResource.getHints().add(OrmFactory.eINSTANCE.createQueryHint());
		
		namedQueryResource.getHints().get(0).setName("FOO");
		namedQueryResource.getHints().get(1).setName("BAR");
		namedQueryResource.getHints().get(2).setName("BAZ");

		ListIterator<XmlQueryHint> hints = xmlNamedQuery.hints();
		assertEquals("FOO", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertFalse(hints.hasNext());
		
		namedQueryResource.getHints().move(2, 0);
		hints = xmlNamedQuery.hints();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());

		namedQueryResource.getHints().move(0, 1);
		hints = xmlNamedQuery.hints();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());

		namedQueryResource.getHints().remove(1);
		hints = xmlNamedQuery.hints();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());

		namedQueryResource.getHints().remove(1);
		hints = xmlNamedQuery.hints();
		assertEquals("BAZ", hints.next().getName());
		assertFalse(hints.hasNext());
		
		namedQueryResource.getHints().remove(0);
		assertFalse(xmlNamedQuery.hints().hasNext());
	}
}
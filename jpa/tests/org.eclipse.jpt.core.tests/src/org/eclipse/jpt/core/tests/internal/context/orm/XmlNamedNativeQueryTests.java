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
import org.eclipse.jpt.core.internal.context.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.context.orm.XmlQueryHint;
import org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlNamedNativeQueryTests extends ContextModelTestCase
{
	public XmlNamedNativeQueryTests(String name) {
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
		XmlNamedNativeQuery xmlNamedNativeQuery = xmlEntity.addNamedNativeQuery(0);
		
		NamedNativeQuery namedNativeQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);
		
		assertNull(xmlNamedNativeQuery.getName());
		assertNull(namedNativeQueryResource.getName());
				
		//set name in the resource model, verify context model updated
		namedNativeQueryResource.setName("newName");
		assertEquals("newName", xmlNamedNativeQuery.getName());
		assertEquals("newName", namedNativeQueryResource.getName());
	
		//set name to null in the resource model
		namedNativeQueryResource.setName(null);
		assertNull(xmlNamedNativeQuery.getName());
		assertNull(namedNativeQueryResource.getName());
	}
	
	public void testModifyName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedNativeQuery xmlNamedNativeQuery = xmlEntity.addNamedNativeQuery(0);
		
		NamedNativeQuery namedNativeQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);
		
		assertNull(xmlNamedNativeQuery.getName());
		assertNull(namedNativeQueryResource.getName());
				
		//set name in the context model, verify resource model updated
		xmlNamedNativeQuery.setName("newName");
		assertEquals("newName", xmlNamedNativeQuery.getName());
		assertEquals("newName", namedNativeQueryResource.getName());
	
		//set name to null in the context model
		xmlNamedNativeQuery.setName(null);
		assertNull(xmlNamedNativeQuery.getName());
		assertNull(namedNativeQueryResource.getName());
	}
	
	public void testUpdateQuery() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedNativeQuery xmlNamedNativeQuery = xmlEntity.addNamedNativeQuery(0);
		
		NamedNativeQuery namedNativeQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);
		
		assertNull(xmlNamedNativeQuery.getQuery());
		assertNull(namedNativeQueryResource.getQuery());
				
		//set name in the resource model, verify context model updated
		namedNativeQueryResource.setQuery("newName");
		assertEquals("newName", xmlNamedNativeQuery.getQuery());
		assertEquals("newName", namedNativeQueryResource.getQuery());
	
		//set name to null in the resource model
		namedNativeQueryResource.setQuery(null);
		assertNull(xmlNamedNativeQuery.getQuery());
		assertNull(namedNativeQueryResource.getQuery());
	}
	
	public void testModifyQuery() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedNativeQuery xmlNamedNativeQuery = xmlEntity.addNamedNativeQuery(0);
		
		NamedNativeQuery namedNativeQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);
		
		assertNull(xmlNamedNativeQuery.getQuery());
		assertNull(namedNativeQueryResource.getQuery());
				
		//set name in the context model, verify resource model updated
		xmlNamedNativeQuery.setQuery("newName");
		assertEquals("newName", xmlNamedNativeQuery.getQuery());
		assertEquals("newName", namedNativeQueryResource.getQuery());
	
		//set name to null in the context model
		xmlNamedNativeQuery.setQuery(null);
		assertNull(xmlNamedNativeQuery.getQuery());
		assertNull(namedNativeQueryResource.getQuery());
	}
	
	public void testAddHint() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedNativeQuery xmlNamedNativeQuery = xmlEntity.addNamedNativeQuery(0);
		
		NamedNativeQuery namedNativeQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);

		XmlQueryHint queryHint = xmlNamedNativeQuery.addHint(0);
		queryHint.setName("FOO");
				
		assertEquals("FOO", namedNativeQueryResource.getHints().get(0).getName());
		
		XmlQueryHint queryHint2 = xmlNamedNativeQuery.addHint(0);
		queryHint2.setName("BAR");
		
		assertEquals("BAR", namedNativeQueryResource.getHints().get(0).getName());
		assertEquals("FOO", namedNativeQueryResource.getHints().get(1).getName());
		
		XmlQueryHint queryHint3 = xmlNamedNativeQuery.addHint(1);
		queryHint3.setName("BAZ");
		
		assertEquals("BAR", namedNativeQueryResource.getHints().get(0).getName());
		assertEquals("BAZ", namedNativeQueryResource.getHints().get(1).getName());
		assertEquals("FOO", namedNativeQueryResource.getHints().get(2).getName());
		
		ListIterator<XmlQueryHint> queryHints = xmlNamedNativeQuery.hints();
		assertEquals(queryHint2, queryHints.next());
		assertEquals(queryHint3, queryHints.next());
		assertEquals(queryHint, queryHints.next());
		
		queryHints = xmlNamedNativeQuery.hints();
		assertEquals("BAR", queryHints.next().getName());
		assertEquals("BAZ", queryHints.next().getName());
		assertEquals("FOO", queryHints.next().getName());
	}
	
	public void testRemoveHint() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedNativeQuery xmlNamedNativeQuery = xmlEntity.addNamedNativeQuery(0);
		
		NamedNativeQuery namedNativeQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);

		xmlNamedNativeQuery.addHint(0).setName("FOO");
		xmlNamedNativeQuery.addHint(1).setName("BAR");
		xmlNamedNativeQuery.addHint(2).setName("BAZ");
		
		assertEquals(3, namedNativeQueryResource.getHints().size());
		
		xmlNamedNativeQuery.removeHint(0);
		assertEquals(2, namedNativeQueryResource.getHints().size());
		assertEquals("BAR", namedNativeQueryResource.getHints().get(0).getName());
		assertEquals("BAZ", namedNativeQueryResource.getHints().get(1).getName());

		xmlNamedNativeQuery.removeHint(0);
		assertEquals(1, namedNativeQueryResource.getHints().size());
		assertEquals("BAZ", namedNativeQueryResource.getHints().get(0).getName());
		
		xmlNamedNativeQuery.removeHint(0);
		assertEquals(0, namedNativeQueryResource.getHints().size());
	}
	
	public void testMoveHint() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedNativeQuery xmlNamedNativeQuery = xmlEntity.addNamedNativeQuery(0);
		
		NamedNativeQuery namedNativeQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);

		xmlNamedNativeQuery.addHint(0).setName("FOO");
		xmlNamedNativeQuery.addHint(1).setName("BAR");
		xmlNamedNativeQuery.addHint(2).setName("BAZ");
		
		assertEquals(3, namedNativeQueryResource.getHints().size());
		
		
		xmlNamedNativeQuery.moveHint(2, 0);
		ListIterator<XmlQueryHint> hints = xmlNamedNativeQuery.hints();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());

		assertEquals("BAR", namedNativeQueryResource.getHints().get(0).getName());
		assertEquals("BAZ", namedNativeQueryResource.getHints().get(1).getName());
		assertEquals("FOO", namedNativeQueryResource.getHints().get(2).getName());


		xmlNamedNativeQuery.moveHint(0, 1);
		hints = xmlNamedNativeQuery.hints();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("FOO", hints.next().getName());

		assertEquals("BAZ", namedNativeQueryResource.getHints().get(0).getName());
		assertEquals("BAR", namedNativeQueryResource.getHints().get(1).getName());
		assertEquals("FOO", namedNativeQueryResource.getHints().get(2).getName());
	}
	
	public void testUpdateHints() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedNativeQuery xmlNamedNativeQuery = xmlEntity.addNamedNativeQuery(0);
		
		NamedNativeQuery namedNativeQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);

		namedNativeQueryResource.getHints().add(OrmFactory.eINSTANCE.createQueryHint());
		namedNativeQueryResource.getHints().add(OrmFactory.eINSTANCE.createQueryHint());
		namedNativeQueryResource.getHints().add(OrmFactory.eINSTANCE.createQueryHint());
		
		namedNativeQueryResource.getHints().get(0).setName("FOO");
		namedNativeQueryResource.getHints().get(1).setName("BAR");
		namedNativeQueryResource.getHints().get(2).setName("BAZ");

		ListIterator<XmlQueryHint> hints = xmlNamedNativeQuery.hints();
		assertEquals("FOO", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertFalse(hints.hasNext());
		
		namedNativeQueryResource.getHints().move(2, 0);
		hints = xmlNamedNativeQuery.hints();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());

		namedNativeQueryResource.getHints().move(0, 1);
		hints = xmlNamedNativeQuery.hints();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());

		namedNativeQueryResource.getHints().remove(1);
		hints = xmlNamedNativeQuery.hints();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());

		namedNativeQueryResource.getHints().remove(1);
		hints = xmlNamedNativeQuery.hints();
		assertEquals("BAZ", hints.next().getName());
		assertFalse(hints.hasNext());
		
		namedNativeQueryResource.getHints().remove(0);
		assertFalse(xmlNamedNativeQuery.hints().hasNext());
	}
	
	
	public void testUpdateResultSetMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedNativeQuery xmlNamedNativeQuery = xmlEntity.addNamedNativeQuery(0);
		
		NamedNativeQuery namedNativeQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);
		
		assertEquals(null, namedNativeQueryResource.getResultSetMapping());
		assertEquals(null, xmlNamedNativeQuery.getResultSetMapping());

		//set name in the resource model, verify context model updated
		namedNativeQueryResource.setResultSetMapping("foo");
		assertEquals("foo", namedNativeQueryResource.getResultSetMapping());
		assertEquals("foo", xmlNamedNativeQuery.getResultSetMapping());
		
		//set name to null in the resource model
		namedNativeQueryResource.setResultSetMapping(null);
		assertNull(namedNativeQueryResource.getResultSetMapping());
		assertNull(xmlNamedNativeQuery.getResultSetMapping());
	}
	
	public void testModifyResultSetMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedNativeQuery xmlNamedNativeQuery = xmlEntity.addNamedNativeQuery(0);
		
		NamedNativeQuery namedNativeQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);
		
		assertEquals(null, namedNativeQueryResource.getResultSetMapping());
		assertEquals(null, xmlNamedNativeQuery.getResultSetMapping());

		//set name in the context model, verify resource model updated
		xmlNamedNativeQuery.setResultSetMapping("foo");
		assertEquals("foo", namedNativeQueryResource.getResultSetMapping());
		assertEquals("foo", xmlNamedNativeQuery.getResultSetMapping());
		
		//set name to null in the context model
		xmlNamedNativeQuery.setResultSetMapping(null);
		assertNull(namedNativeQueryResource.getResultSetMapping());
		assertNull(xmlNamedNativeQuery.getResultSetMapping());
	}
	
	public void testUpdateResultClass() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedNativeQuery xmlNamedNativeQuery = xmlEntity.addNamedNativeQuery(0);
		
		NamedNativeQuery namedNativeQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);
		
		assertEquals(null, namedNativeQueryResource.getResultClass());
		assertEquals(null, xmlNamedNativeQuery.getResultClass());

		//set name in the resource model, verify context model updated
		namedNativeQueryResource.setResultClass("foo");
		assertEquals("foo", namedNativeQueryResource.getResultClass());
		assertEquals("foo", xmlNamedNativeQuery.getResultClass());
		
		//set name to null in the resource model
		namedNativeQueryResource.setResultClass(null);
		assertNull(namedNativeQueryResource.getResultClass());
		assertNull(xmlNamedNativeQuery.getResultClass());
	}
	
	public void testModifyResultClass() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlNamedNativeQuery xmlNamedNativeQuery = xmlEntity.addNamedNativeQuery(0);
		
		NamedNativeQuery namedNativeQueryResource = ormResource().getEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);
		
		assertEquals(null, namedNativeQueryResource.getResultClass());
		assertEquals(null, xmlNamedNativeQuery.getResultClass());

		//set name in the context model, verify resource model updated
		xmlNamedNativeQuery.setResultClass("foo");
		assertEquals("foo", namedNativeQueryResource.getResultClass());
		assertEquals("foo", xmlNamedNativeQuery.getResultClass());
		
		//set name to null in the context model
		xmlNamedNativeQuery.setResultClass(null);
		assertNull(namedNativeQueryResource.getResultClass());
		assertNull(xmlNamedNativeQuery.getResultClass());
	}
}
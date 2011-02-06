/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryHint;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmNamedNativeQueryTests extends ContextModelTestCase
{
	public OrmNamedNativeQueryTests(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptJpaCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString());
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}

	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedNativeQuery ormNamedNativeQuery = ormEntity.getQueryContainer().addNamedNativeQuery(0);
		
		XmlNamedNativeQuery namedNativeQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);
		
		assertNull(ormNamedNativeQuery.getName());
		assertNull(namedNativeQueryResource.getName());
				
		//set name in the resource model, verify context model updated
		namedNativeQueryResource.setName("newName");
		assertEquals("newName", ormNamedNativeQuery.getName());
		assertEquals("newName", namedNativeQueryResource.getName());
	
		//set name to null in the resource model
		namedNativeQueryResource.setName(null);
		assertNull(ormNamedNativeQuery.getName());
		assertNull(namedNativeQueryResource.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedNativeQuery ormNamedNativeQuery = ormEntity.getQueryContainer().addNamedNativeQuery(0);
		
		XmlNamedNativeQuery namedNativeQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);
		
		assertNull(ormNamedNativeQuery.getName());
		assertNull(namedNativeQueryResource.getName());
				
		//set name in the context model, verify resource model updated
		ormNamedNativeQuery.setName("newName");
		assertEquals("newName", ormNamedNativeQuery.getName());
		assertEquals("newName", namedNativeQueryResource.getName());
	
		//set name to null in the context model
		ormNamedNativeQuery.setName(null);
		assertNull(ormNamedNativeQuery.getName());
		assertNull(namedNativeQueryResource.getName());
	}
	
	public void testUpdateQuery() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedNativeQuery ormNamedNativeQuery = ormEntity.getQueryContainer().addNamedNativeQuery(0);
		
		XmlNamedNativeQuery namedNativeQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);
		
		assertNull(ormNamedNativeQuery.getQuery());
		assertNull(namedNativeQueryResource.getQuery());
				
		//set name in the resource model, verify context model updated
		namedNativeQueryResource.setQuery("newName");
		assertEquals("newName", ormNamedNativeQuery.getQuery());
		assertEquals("newName", namedNativeQueryResource.getQuery());
	
		//set name to null in the resource model
		namedNativeQueryResource.setQuery(null);
		assertNull(ormNamedNativeQuery.getQuery());
		assertNull(namedNativeQueryResource.getQuery());
	}
	
	public void testModifyQuery() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedNativeQuery ormNamedNativeQuery = ormEntity.getQueryContainer().addNamedNativeQuery(0);
		
		XmlNamedNativeQuery namedNativeQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);
		
		assertNull(ormNamedNativeQuery.getQuery());
		assertNull(namedNativeQueryResource.getQuery());
				
		//set name in the context model, verify resource model updated
		ormNamedNativeQuery.setQuery("newName");
		assertEquals("newName", ormNamedNativeQuery.getQuery());
		assertEquals("newName", namedNativeQueryResource.getQuery());
	
		//set name to null in the context model
		ormNamedNativeQuery.setQuery(null);
		assertNull(ormNamedNativeQuery.getQuery());
		assertNull(namedNativeQueryResource.getQuery());
	}
	
	public void testAddHint() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedNativeQuery ormNamedNativeQuery = ormEntity.getQueryContainer().addNamedNativeQuery(0);
		
		XmlNamedNativeQuery namedNativeQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);

		OrmQueryHint queryHint = ormNamedNativeQuery.addHint(0);
		queryHint.setName("FOO");
				
		assertEquals("FOO", namedNativeQueryResource.getHints().get(0).getName());
		
		OrmQueryHint queryHint2 = ormNamedNativeQuery.addHint(0);
		queryHint2.setName("BAR");
		
		assertEquals("BAR", namedNativeQueryResource.getHints().get(0).getName());
		assertEquals("FOO", namedNativeQueryResource.getHints().get(1).getName());
		
		OrmQueryHint queryHint3 = ormNamedNativeQuery.addHint(1);
		queryHint3.setName("BAZ");
		
		assertEquals("BAR", namedNativeQueryResource.getHints().get(0).getName());
		assertEquals("BAZ", namedNativeQueryResource.getHints().get(1).getName());
		assertEquals("FOO", namedNativeQueryResource.getHints().get(2).getName());
		
		ListIterator<OrmQueryHint> queryHints = ormNamedNativeQuery.getHints().iterator();
		assertEquals(queryHint2, queryHints.next());
		assertEquals(queryHint3, queryHints.next());
		assertEquals(queryHint, queryHints.next());
		
		queryHints = ormNamedNativeQuery.getHints().iterator();
		assertEquals("BAR", queryHints.next().getName());
		assertEquals("BAZ", queryHints.next().getName());
		assertEquals("FOO", queryHints.next().getName());
	}
	
	public void testRemoveHint() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedNativeQuery ormNamedNativeQuery = ormEntity.getQueryContainer().addNamedNativeQuery(0);
		
		XmlNamedNativeQuery namedNativeQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);

		ormNamedNativeQuery.addHint(0).setName("FOO");
		ormNamedNativeQuery.addHint(1).setName("BAR");
		ormNamedNativeQuery.addHint(2).setName("BAZ");
		
		assertEquals(3, namedNativeQueryResource.getHints().size());
		
		ormNamedNativeQuery.removeHint(0);
		assertEquals(2, namedNativeQueryResource.getHints().size());
		assertEquals("BAR", namedNativeQueryResource.getHints().get(0).getName());
		assertEquals("BAZ", namedNativeQueryResource.getHints().get(1).getName());

		ormNamedNativeQuery.removeHint(0);
		assertEquals(1, namedNativeQueryResource.getHints().size());
		assertEquals("BAZ", namedNativeQueryResource.getHints().get(0).getName());
		
		ormNamedNativeQuery.removeHint(0);
		assertEquals(0, namedNativeQueryResource.getHints().size());
	}
	
	public void testMoveHint() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedNativeQuery ormNamedNativeQuery = ormEntity.getQueryContainer().addNamedNativeQuery(0);
		
		XmlNamedNativeQuery namedNativeQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);

		ormNamedNativeQuery.addHint(0).setName("FOO");
		ormNamedNativeQuery.addHint(1).setName("BAR");
		ormNamedNativeQuery.addHint(2).setName("BAZ");
		
		assertEquals(3, namedNativeQueryResource.getHints().size());
		
		
		ormNamedNativeQuery.moveHint(2, 0);
		ListIterator<OrmQueryHint> hints = ormNamedNativeQuery.getHints().iterator();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());

		assertEquals("BAR", namedNativeQueryResource.getHints().get(0).getName());
		assertEquals("BAZ", namedNativeQueryResource.getHints().get(1).getName());
		assertEquals("FOO", namedNativeQueryResource.getHints().get(2).getName());


		ormNamedNativeQuery.moveHint(0, 1);
		hints = ormNamedNativeQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("FOO", hints.next().getName());

		assertEquals("BAZ", namedNativeQueryResource.getHints().get(0).getName());
		assertEquals("BAR", namedNativeQueryResource.getHints().get(1).getName());
		assertEquals("FOO", namedNativeQueryResource.getHints().get(2).getName());
	}
	
	public void testUpdateHints() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedNativeQuery ormNamedNativeQuery = ormEntity.getQueryContainer().addNamedNativeQuery(0);
		
		XmlNamedNativeQuery namedNativeQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);

		namedNativeQueryResource.getHints().add(OrmFactory.eINSTANCE.createXmlQueryHint());
		namedNativeQueryResource.getHints().add(OrmFactory.eINSTANCE.createXmlQueryHint());
		namedNativeQueryResource.getHints().add(OrmFactory.eINSTANCE.createXmlQueryHint());
		
		namedNativeQueryResource.getHints().get(0).setName("FOO");
		namedNativeQueryResource.getHints().get(1).setName("BAR");
		namedNativeQueryResource.getHints().get(2).setName("BAZ");

		ListIterator<OrmQueryHint> hints = ormNamedNativeQuery.getHints().iterator();
		assertEquals("FOO", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertFalse(hints.hasNext());
		
		namedNativeQueryResource.getHints().move(2, 0);
		hints = ormNamedNativeQuery.getHints().iterator();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());

		namedNativeQueryResource.getHints().move(0, 1);
		hints = ormNamedNativeQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());

		namedNativeQueryResource.getHints().remove(1);
		hints = ormNamedNativeQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());

		namedNativeQueryResource.getHints().remove(1);
		hints = ormNamedNativeQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertFalse(hints.hasNext());
		
		namedNativeQueryResource.getHints().remove(0);
		assertFalse(ormNamedNativeQuery.getHints().iterator().hasNext());
	}
	
	
	public void testUpdateResultSetMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedNativeQuery ormNamedNativeQuery = ormEntity.getQueryContainer().addNamedNativeQuery(0);
		
		XmlNamedNativeQuery namedNativeQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);
		
		assertEquals(null, namedNativeQueryResource.getResultSetMapping());
		assertEquals(null, ormNamedNativeQuery.getResultSetMapping());

		//set name in the resource model, verify context model updated
		namedNativeQueryResource.setResultSetMapping("foo");
		assertEquals("foo", namedNativeQueryResource.getResultSetMapping());
		assertEquals("foo", ormNamedNativeQuery.getResultSetMapping());
		
		//set name to null in the resource model
		namedNativeQueryResource.setResultSetMapping(null);
		assertNull(namedNativeQueryResource.getResultSetMapping());
		assertNull(ormNamedNativeQuery.getResultSetMapping());
	}
	
	public void testModifyResultSetMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedNativeQuery ormNamedNativeQuery = ormEntity.getQueryContainer().addNamedNativeQuery(0);
		
		XmlNamedNativeQuery namedNativeQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);
		
		assertEquals(null, namedNativeQueryResource.getResultSetMapping());
		assertEquals(null, ormNamedNativeQuery.getResultSetMapping());

		//set name in the context model, verify resource model updated
		ormNamedNativeQuery.setResultSetMapping("foo");
		assertEquals("foo", namedNativeQueryResource.getResultSetMapping());
		assertEquals("foo", ormNamedNativeQuery.getResultSetMapping());
		
		//set name to null in the context model
		ormNamedNativeQuery.setResultSetMapping(null);
		assertNull(namedNativeQueryResource.getResultSetMapping());
		assertNull(ormNamedNativeQuery.getResultSetMapping());
	}
	
	public void testUpdateResultClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedNativeQuery ormNamedNativeQuery = ormEntity.getQueryContainer().addNamedNativeQuery(0);
		
		XmlNamedNativeQuery namedNativeQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);
		
		assertEquals(null, namedNativeQueryResource.getResultClass());
		assertEquals(null, ormNamedNativeQuery.getResultClass());

		//set name in the resource model, verify context model updated
		namedNativeQueryResource.setResultClass("foo");
		assertEquals("foo", namedNativeQueryResource.getResultClass());
		assertEquals("foo", ormNamedNativeQuery.getResultClass());
		
		//set name to null in the resource model
		namedNativeQueryResource.setResultClass(null);
		assertNull(namedNativeQueryResource.getResultClass());
		assertNull(ormNamedNativeQuery.getResultClass());
	}
	
	public void testModifyResultClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedNativeQuery ormNamedNativeQuery = ormEntity.getQueryContainer().addNamedNativeQuery(0);
		
		XmlNamedNativeQuery namedNativeQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedNativeQueries().get(0);
		
		assertEquals(null, namedNativeQueryResource.getResultClass());
		assertEquals(null, ormNamedNativeQuery.getResultClass());

		//set name in the context model, verify resource model updated
		ormNamedNativeQuery.setResultClass("foo");
		assertEquals("foo", namedNativeQueryResource.getResultClass());
		assertEquals("foo", ormNamedNativeQuery.getResultClass());
		
		//set name to null in the context model
		ormNamedNativeQuery.setResultClass(null);
		assertNull(namedNativeQueryResource.getResultClass());
		assertNull(ormNamedNativeQuery.getResultClass());
	}
}
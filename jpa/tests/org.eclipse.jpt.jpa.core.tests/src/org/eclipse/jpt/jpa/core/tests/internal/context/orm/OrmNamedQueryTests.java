/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryHint;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmNamedQueryTests extends ContextModelTestCase
{
	public OrmNamedQueryTests(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}

	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedQuery ormNamedQuery = ormEntity.getQueryContainer().addNamedQuery(0);
		
		XmlNamedQuery namedQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedQueries().get(0);
		
		assertNull(ormNamedQuery.getName());
		assertNull(namedQueryResource.getName());
				
		//set name in the resource model, verify context model updated
		namedQueryResource.setName("newName");
		assertEquals("newName", ormNamedQuery.getName());
		assertEquals("newName", namedQueryResource.getName());
	
		//set name to null in the resource model
		namedQueryResource.setName(null);
		assertNull(ormNamedQuery.getName());
		assertNull(namedQueryResource.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedQuery ormNamedQuery = ormEntity.getQueryContainer().addNamedQuery(0);
		
		XmlNamedQuery namedQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedQueries().get(0);
		
		assertNull(ormNamedQuery.getName());
		assertNull(namedQueryResource.getName());
				
		//set name in the context model, verify resource model updated
		ormNamedQuery.setName("newName");
		assertEquals("newName", ormNamedQuery.getName());
		assertEquals("newName", namedQueryResource.getName());
	
		//set name to null in the context model
		ormNamedQuery.setName(null);
		assertNull(ormNamedQuery.getName());
		assertNull(namedQueryResource.getName());
	}
	
	public void testUpdateQuery() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedQuery ormNamedQuery = ormEntity.getQueryContainer().addNamedQuery(0);
		
		XmlNamedQuery namedQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedQueries().get(0);
		
		assertNull(ormNamedQuery.getQuery());
		assertNull(namedQueryResource.getQuery());
				
		//set name in the resource model, verify context model updated
		namedQueryResource.setQuery("newName");
		assertEquals("newName", ormNamedQuery.getQuery());
		assertEquals("newName", namedQueryResource.getQuery());
	
		//set name to null in the resource model
		namedQueryResource.setQuery(null);
		assertNull(ormNamedQuery.getQuery());
		assertNull(namedQueryResource.getQuery());
	}
	
	public void testModifyQuery() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedQuery ormNamedQuery = ormEntity.getQueryContainer().addNamedQuery(0);
		
		XmlNamedQuery namedQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedQueries().get(0);
		
		assertNull(ormNamedQuery.getQuery());
		assertNull(namedQueryResource.getQuery());
				
		//set name in the context model, verify resource model updated
		ormNamedQuery.setQuery("newName");
		assertEquals("newName", ormNamedQuery.getQuery());
		assertEquals("newName", namedQueryResource.getQuery());
	
		//set name to null in the context model
		ormNamedQuery.setQuery(null);
		assertNull(ormNamedQuery.getQuery());
		assertNull(namedQueryResource.getQuery());
	}
	
	public void testAddHint() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedQuery ormNamedQuery = ormEntity.getQueryContainer().addNamedQuery(0);
		
		XmlNamedQuery namedQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedQueries().get(0);

		OrmQueryHint queryHint = ormNamedQuery.addHint(0);
		queryHint.setName("FOO");
				
		assertEquals("FOO", namedQueryResource.getHints().get(0).getName());
		
		OrmQueryHint queryHint2 = ormNamedQuery.addHint(0);
		queryHint2.setName("BAR");
		
		assertEquals("BAR", namedQueryResource.getHints().get(0).getName());
		assertEquals("FOO", namedQueryResource.getHints().get(1).getName());
		
		OrmQueryHint queryHint3 = ormNamedQuery.addHint(1);
		queryHint3.setName("BAZ");
		
		assertEquals("BAR", namedQueryResource.getHints().get(0).getName());
		assertEquals("BAZ", namedQueryResource.getHints().get(1).getName());
		assertEquals("FOO", namedQueryResource.getHints().get(2).getName());
		
		ListIterator<OrmQueryHint> queryHints = ormNamedQuery.getHints().iterator();
		assertEquals(queryHint2, queryHints.next());
		assertEquals(queryHint3, queryHints.next());
		assertEquals(queryHint, queryHints.next());
		
		queryHints = ormNamedQuery.getHints().iterator();
		assertEquals("BAR", queryHints.next().getName());
		assertEquals("BAZ", queryHints.next().getName());
		assertEquals("FOO", queryHints.next().getName());
	}
	
	public void testRemoveHint() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedQuery ormNamedQuery = ormEntity.getQueryContainer().addNamedQuery(0);
		
		XmlNamedQuery namedQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedQueries().get(0);

		ormNamedQuery.addHint(0).setName("FOO");
		ormNamedQuery.addHint(1).setName("BAR");
		ormNamedQuery.addHint(2).setName("BAZ");
		
		assertEquals(3, namedQueryResource.getHints().size());
		
		ormNamedQuery.removeHint(0);
		assertEquals(2, namedQueryResource.getHints().size());
		assertEquals("BAR", namedQueryResource.getHints().get(0).getName());
		assertEquals("BAZ", namedQueryResource.getHints().get(1).getName());

		ormNamedQuery.removeHint(0);
		assertEquals(1, namedQueryResource.getHints().size());
		assertEquals("BAZ", namedQueryResource.getHints().get(0).getName());
		
		ormNamedQuery.removeHint(0);
		assertEquals(0, namedQueryResource.getHints().size());
	}
	
	public void testMoveHint() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedQuery ormNamedQuery = ormEntity.getQueryContainer().addNamedQuery(0);
		
		XmlNamedQuery namedQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedQueries().get(0);

		ormNamedQuery.addHint(0).setName("FOO");
		ormNamedQuery.addHint(1).setName("BAR");
		ormNamedQuery.addHint(2).setName("BAZ");
		
		assertEquals(3, namedQueryResource.getHints().size());
		
		
		ormNamedQuery.moveHint(2, 0);
		ListIterator<OrmQueryHint> hints = ormNamedQuery.getHints().iterator();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());

		assertEquals("BAR", namedQueryResource.getHints().get(0).getName());
		assertEquals("BAZ", namedQueryResource.getHints().get(1).getName());
		assertEquals("FOO", namedQueryResource.getHints().get(2).getName());


		ormNamedQuery.moveHint(0, 1);
		hints = ormNamedQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("FOO", hints.next().getName());

		assertEquals("BAZ", namedQueryResource.getHints().get(0).getName());
		assertEquals("BAR", namedQueryResource.getHints().get(1).getName());
		assertEquals("FOO", namedQueryResource.getHints().get(2).getName());
	}
	
	public void testUpdateHints() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedQuery ormNamedQuery = ormEntity.getQueryContainer().addNamedQuery(0);
		
		XmlNamedQuery namedQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedQueries().get(0);

		namedQueryResource.getHints().add(OrmFactory.eINSTANCE.createXmlQueryHint());
		namedQueryResource.getHints().add(OrmFactory.eINSTANCE.createXmlQueryHint());
		namedQueryResource.getHints().add(OrmFactory.eINSTANCE.createXmlQueryHint());
		
		namedQueryResource.getHints().get(0).setName("FOO");
		namedQueryResource.getHints().get(1).setName("BAR");
		namedQueryResource.getHints().get(2).setName("BAZ");

		ListIterator<OrmQueryHint> hints = ormNamedQuery.getHints().iterator();
		assertEquals("FOO", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertFalse(hints.hasNext());
		
		namedQueryResource.getHints().move(2, 0);
		hints = ormNamedQuery.getHints().iterator();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());

		namedQueryResource.getHints().move(0, 1);
		hints = ormNamedQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());

		namedQueryResource.getHints().remove(1);
		hints = ormNamedQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());

		namedQueryResource.getHints().remove(1);
		hints = ormNamedQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertFalse(hints.hasNext());
		
		namedQueryResource.getHints().remove(0);
		assertFalse(ormNamedQuery.getHints().iterator().hasNext());
	}
}
/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmAssociationOverrideTests extends ContextModelTestCase
{
	public OrmAssociationOverrideTests(String name) {
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
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverride());
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);
		OrmSpecifiedAssociationOverride ormAssociationOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		
		assertNull(ormAssociationOverride.getName());
		assertNull(xmlAssociationOverride.getName());
		assertTrue(overrideContainer.getOverrides().iterator().hasNext());
		assertFalse(entityResource.getAssociationOverrides().isEmpty());
		
		//set name in the resource model, verify context model updated
		xmlAssociationOverride.setName("FOO");
		assertEquals("FOO", ormAssociationOverride.getName());
		assertEquals("FOO", xmlAssociationOverride.getName());
	
		//set name to null in the resource model
		xmlAssociationOverride.setName(null);
		assertNull(ormAssociationOverride.getName());
		assertNull(xmlAssociationOverride.getName());
		
		xmlAssociationOverride.setName("FOO");
		assertEquals("FOO", ormAssociationOverride.getName());
		assertEquals("FOO", xmlAssociationOverride.getName());

		entityResource.getAssociationOverrides().remove(0);
		assertFalse(overrideContainer.getOverrides().iterator().hasNext());
		assertTrue(entityResource.getAssociationOverrides().isEmpty());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverride());
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);
		OrmSpecifiedAssociationOverride ormAssociationOverride = overrideContainer.getSpecifiedOverrides().iterator().next();

		assertNull(ormAssociationOverride.getName());
		assertNull(xmlAssociationOverride.getName());
		
		//set name in the context model, verify resource model modified
		ormAssociationOverride.setName("foo");
		assertEquals("foo", ormAssociationOverride.getName());
		assertEquals("foo", xmlAssociationOverride.getName());
		
		//set name to null in the context model
		ormAssociationOverride.setName(null);
		assertNull(ormAssociationOverride.getName());
		assertNull(entityResource.getAssociationOverrides().get(0).getName());
	}
	
	
	public void testAddSpecifiedJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverride());
		OrmSpecifiedAssociationOverride ormAssociationOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		OrmSpecifiedJoinColumnRelationshipStrategy joiningStrategy = ormAssociationOverride.getRelationship().getJoinColumnStrategy();
		
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);
		
		OrmSpecifiedJoinColumn joinColumn = joiningStrategy.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", xmlAssociationOverride.getJoinColumns().get(0).getName());
		
		OrmSpecifiedJoinColumn joinColumn2 = joiningStrategy.addSpecifiedJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", xmlAssociationOverride.getJoinColumns().get(0).getName());
		assertEquals("FOO", xmlAssociationOverride.getJoinColumns().get(1).getName());
		
		OrmSpecifiedJoinColumn joinColumn3 = joiningStrategy.addSpecifiedJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", xmlAssociationOverride.getJoinColumns().get(0).getName());
		assertEquals("BAZ", xmlAssociationOverride.getJoinColumns().get(1).getName());
		assertEquals("FOO", xmlAssociationOverride.getJoinColumns().get(2).getName());
		
		ListIterator<OrmSpecifiedJoinColumn> joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverride());
		OrmSpecifiedAssociationOverride ormAssociationOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		OrmSpecifiedJoinColumnRelationshipStrategy joiningStrategy = ormAssociationOverride.getRelationship().getJoinColumnStrategy();
		
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);
		
		joiningStrategy.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joiningStrategy.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joiningStrategy.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, xmlAssociationOverride.getJoinColumns().size());
		
		joiningStrategy.removeSpecifiedJoinColumn(0);
		assertEquals(2, xmlAssociationOverride.getJoinColumns().size());
		assertEquals("BAR", xmlAssociationOverride.getJoinColumns().get(0).getName());
		assertEquals("BAZ", xmlAssociationOverride.getJoinColumns().get(1).getName());

		joiningStrategy.removeSpecifiedJoinColumn(0);
		assertEquals(1, xmlAssociationOverride.getJoinColumns().size());
		assertEquals("BAZ", xmlAssociationOverride.getJoinColumns().get(0).getName());
		
		joiningStrategy.removeSpecifiedJoinColumn(0);
		assertEquals(0, xmlAssociationOverride.getJoinColumns().size());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverride());
		OrmSpecifiedAssociationOverride ormAssociationOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		OrmSpecifiedJoinColumnRelationshipStrategy joiningStrategy = ormAssociationOverride.getRelationship().getJoinColumnStrategy();
		
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);

		joiningStrategy.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joiningStrategy.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joiningStrategy.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, xmlAssociationOverride.getJoinColumns().size());
		
		
		joiningStrategy.moveSpecifiedJoinColumn(2, 0);
		ListIterator<OrmSpecifiedJoinColumn> joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", xmlAssociationOverride.getJoinColumns().get(0).getName());
		assertEquals("BAZ", xmlAssociationOverride.getJoinColumns().get(1).getName());
		assertEquals("FOO", xmlAssociationOverride.getJoinColumns().get(2).getName());


		joiningStrategy.moveSpecifiedJoinColumn(0, 1);
		joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", xmlAssociationOverride.getJoinColumns().get(0).getName());
		assertEquals("BAR", xmlAssociationOverride.getJoinColumns().get(1).getName());
		assertEquals("FOO", xmlAssociationOverride.getJoinColumns().get(2).getName());
	}
	
	public void testUpdateJoinColumns() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverride());
		OrmSpecifiedAssociationOverride ormAssociationOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		OrmSpecifiedJoinColumnRelationshipStrategy joiningStrategy = ormAssociationOverride.getRelationship().getJoinColumnStrategy();
		
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);
	
		xmlAssociationOverride.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		xmlAssociationOverride.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		xmlAssociationOverride.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		
		xmlAssociationOverride.getJoinColumns().get(0).setName("FOO");
		xmlAssociationOverride.getJoinColumns().get(1).setName("BAR");
		xmlAssociationOverride.getJoinColumns().get(2).setName("BAZ");

		ListIterator<OrmSpecifiedJoinColumn> joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		xmlAssociationOverride.getJoinColumns().move(2, 0);
		joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		xmlAssociationOverride.getJoinColumns().move(0, 1);
		joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		xmlAssociationOverride.getJoinColumns().remove(1);
		joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		xmlAssociationOverride.getJoinColumns().remove(1);
		joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		xmlAssociationOverride.getJoinColumns().remove(0);
		assertFalse(joiningStrategy.getSpecifiedJoinColumns().iterator().hasNext());
	}
}
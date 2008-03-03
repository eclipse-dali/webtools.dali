/*******************************************************************************
 *  Copyright (c) 2008 Oracle. 
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
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class OrmAssociationOverrideTests extends ContextModelTestCase
{
	public OrmAssociationOverrideTests(String name) {
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverride xmlAssociationOverride = ormEntity.addSpecifiedAssociationOverride(0);
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		XmlAssociationOverride associationOverrideResource = entityResource.getAssociationOverrides().get(0);
		
		assertNull(xmlAssociationOverride.getName());
		assertNull(associationOverrideResource.getName());
		assertTrue(ormEntity.associationOverrides().hasNext());
		assertFalse(entityResource.getAssociationOverrides().isEmpty());
		
		//set name in the resource model, verify context model updated
		associationOverrideResource.setName("FOO");
		ormResource().save(null);
		assertEquals("FOO", xmlAssociationOverride.getName());
		assertEquals("FOO", associationOverrideResource.getName());
	
		//set name to null in the resource model
		associationOverrideResource.setName(null);
		ormResource().save(null);
		assertNull(xmlAssociationOverride.getName());
		assertNull(associationOverrideResource.getName());
		
		associationOverrideResource.setName("FOO");
		assertEquals("FOO", xmlAssociationOverride.getName());
		assertEquals("FOO", associationOverrideResource.getName());

		entityResource.getAssociationOverrides().remove(0);
		ormResource().save(null);
		assertFalse(ormEntity.associationOverrides().hasNext());
		assertTrue(entityResource.getAssociationOverrides().isEmpty());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverride xmlAssociationOverride = ormEntity.addSpecifiedAssociationOverride(0);
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		XmlAssociationOverride associationOverrideResource = entityResource.getAssociationOverrides().get(0);

		assertNull(xmlAssociationOverride.getName());
		assertNull(associationOverrideResource.getName());
		
		//set name in the context model, verify resource model modified
		xmlAssociationOverride.setName("foo");
		assertEquals("foo", xmlAssociationOverride.getName());
		assertEquals("foo", associationOverrideResource.getName());
		
		//set name to null in the context model
		xmlAssociationOverride.setName(null);
		assertNull(xmlAssociationOverride.getName());
		assertNull(entityResource.getAssociationOverrides().get(0).getName());
	}
	
	
	public void testAddSpecifiedJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverride associationOverride = ormEntity.addSpecifiedAssociationOverride(0);
		
		XmlAssociationOverride associationOverrideResource = ormResource().getEntityMappings().getEntities().get(0).getAssociationOverrides().get(0);
		
		OrmJoinColumn joinColumn = associationOverride.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", associationOverrideResource.getJoinColumns().get(0).getName());
		
		OrmJoinColumn joinColumn2 = associationOverride.addSpecifiedJoinColumn(0);
		ormResource().save(null);
		joinColumn2.setSpecifiedName("BAR");
		ormResource().save(null);
		
		assertEquals("BAR", associationOverrideResource.getJoinColumns().get(0).getName());
		assertEquals("FOO", associationOverrideResource.getJoinColumns().get(1).getName());
		
		OrmJoinColumn joinColumn3 = associationOverride.addSpecifiedJoinColumn(1);
		ormResource().save(null);
		joinColumn3.setSpecifiedName("BAZ");
		ormResource().save(null);
		
		assertEquals("BAR", associationOverrideResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", associationOverrideResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", associationOverrideResource.getJoinColumns().get(2).getName());
		
		ListIterator<OrmJoinColumn> joinColumns = associationOverride.specifiedJoinColumns();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = associationOverride.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverride associationOverride = ormEntity.addSpecifiedAssociationOverride(0);
		
		XmlAssociationOverride associationOverrideResource = ormResource().getEntityMappings().getEntities().get(0).getAssociationOverrides().get(0);
		
		associationOverride.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		associationOverride.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		associationOverride.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, associationOverrideResource.getJoinColumns().size());
		
		associationOverride.removeSpecifiedJoinColumn(0);
		assertEquals(2, associationOverrideResource.getJoinColumns().size());
		assertEquals("BAR", associationOverrideResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", associationOverrideResource.getJoinColumns().get(1).getName());

		associationOverride.removeSpecifiedJoinColumn(0);
		assertEquals(1, associationOverrideResource.getJoinColumns().size());
		assertEquals("BAZ", associationOverrideResource.getJoinColumns().get(0).getName());
		
		associationOverride.removeSpecifiedJoinColumn(0);
		assertEquals(0, associationOverrideResource.getJoinColumns().size());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverride associationOverride = ormEntity.addSpecifiedAssociationOverride(0);
		
		XmlAssociationOverride associationOverrideResource = ormResource().getEntityMappings().getEntities().get(0).getAssociationOverrides().get(0);

		associationOverride.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		associationOverride.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		associationOverride.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, associationOverrideResource.getJoinColumns().size());
		
		
		associationOverride.moveSpecifiedJoinColumn(2, 0);
		ListIterator<OrmJoinColumn> joinColumns = associationOverride.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", associationOverrideResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", associationOverrideResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", associationOverrideResource.getJoinColumns().get(2).getName());


		associationOverride.moveSpecifiedJoinColumn(0, 1);
		joinColumns = associationOverride.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", associationOverrideResource.getJoinColumns().get(0).getName());
		assertEquals("BAR", associationOverrideResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", associationOverrideResource.getJoinColumns().get(2).getName());
	}
	
	public void testUpdateJoinColumns() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverride associationOverride = ormEntity.addSpecifiedAssociationOverride(0);
		
		XmlAssociationOverride associationOverrideResource = ormResource().getEntityMappings().getEntities().get(0).getAssociationOverrides().get(0);
	
		associationOverrideResource.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumnImpl());
		associationOverrideResource.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumnImpl());
		associationOverrideResource.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumnImpl());
		
		associationOverrideResource.getJoinColumns().get(0).setName("FOO");
		associationOverrideResource.getJoinColumns().get(1).setName("BAR");
		associationOverrideResource.getJoinColumns().get(2).setName("BAZ");

		ListIterator<OrmJoinColumn> joinColumns = associationOverride.specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		associationOverrideResource.getJoinColumns().move(2, 0);
		joinColumns = associationOverride.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		associationOverrideResource.getJoinColumns().move(0, 1);
		joinColumns = associationOverride.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		associationOverrideResource.getJoinColumns().remove(1);
		joinColumns = associationOverride.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		associationOverrideResource.getJoinColumns().remove(1);
		joinColumns = associationOverride.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		associationOverrideResource.getJoinColumns().remove(0);
		assertFalse(associationOverride.specifiedJoinColumns().hasNext());
	}
}
/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
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
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}

	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		
		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverrideImpl());
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);
		OrmAssociationOverride ormAssociationOverride = ormEntity.specifiedAssociationOverrides().next();
		
		assertNull(ormAssociationOverride.getName());
		assertNull(xmlAssociationOverride.getName());
		assertTrue(ormEntity.associationOverrides().hasNext());
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
		assertFalse(ormEntity.associationOverrides().hasNext());
		assertTrue(entityResource.getAssociationOverrides().isEmpty());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();

		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverrideImpl());
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);
		OrmAssociationOverride ormAssociationOverride = ormEntity.specifiedAssociationOverrides().next();

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

		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverrideImpl());
		OrmAssociationOverride ormAssociationOverride = ormEntity.specifiedAssociationOverrides().next();
		
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);
		
		OrmJoinColumn joinColumn = ormAssociationOverride.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", xmlAssociationOverride.getJoinColumns().get(0).getName());
		
		OrmJoinColumn joinColumn2 = ormAssociationOverride.addSpecifiedJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", xmlAssociationOverride.getJoinColumns().get(0).getName());
		assertEquals("FOO", xmlAssociationOverride.getJoinColumns().get(1).getName());
		
		OrmJoinColumn joinColumn3 = ormAssociationOverride.addSpecifiedJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", xmlAssociationOverride.getJoinColumns().get(0).getName());
		assertEquals("BAZ", xmlAssociationOverride.getJoinColumns().get(1).getName());
		assertEquals("FOO", xmlAssociationOverride.getJoinColumns().get(2).getName());
		
		ListIterator<OrmJoinColumn> joinColumns = ormAssociationOverride.specifiedJoinColumns();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = ormAssociationOverride.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverrideImpl());
		OrmAssociationOverride ormAssociationOverride = ormEntity.specifiedAssociationOverrides().next();
		
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);
		
		ormAssociationOverride.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		ormAssociationOverride.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		ormAssociationOverride.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, xmlAssociationOverride.getJoinColumns().size());
		
		ormAssociationOverride.removeSpecifiedJoinColumn(0);
		assertEquals(2, xmlAssociationOverride.getJoinColumns().size());
		assertEquals("BAR", xmlAssociationOverride.getJoinColumns().get(0).getName());
		assertEquals("BAZ", xmlAssociationOverride.getJoinColumns().get(1).getName());

		ormAssociationOverride.removeSpecifiedJoinColumn(0);
		assertEquals(1, xmlAssociationOverride.getJoinColumns().size());
		assertEquals("BAZ", xmlAssociationOverride.getJoinColumns().get(0).getName());
		
		ormAssociationOverride.removeSpecifiedJoinColumn(0);
		assertEquals(0, xmlAssociationOverride.getJoinColumns().size());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverrideImpl());
		OrmAssociationOverride ormAssociationOverride = ormEntity.specifiedAssociationOverrides().next();
		
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);

		ormAssociationOverride.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		ormAssociationOverride.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		ormAssociationOverride.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, xmlAssociationOverride.getJoinColumns().size());
		
		
		ormAssociationOverride.moveSpecifiedJoinColumn(2, 0);
		ListIterator<OrmJoinColumn> joinColumns = ormAssociationOverride.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", xmlAssociationOverride.getJoinColumns().get(0).getName());
		assertEquals("BAZ", xmlAssociationOverride.getJoinColumns().get(1).getName());
		assertEquals("FOO", xmlAssociationOverride.getJoinColumns().get(2).getName());


		ormAssociationOverride.moveSpecifiedJoinColumn(0, 1);
		joinColumns = ormAssociationOverride.specifiedJoinColumns();
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
		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverrideImpl());
		OrmAssociationOverride ormAssociationOverride = ormEntity.specifiedAssociationOverrides().next();
		
		XmlAssociationOverride xmlAssociationOverride = entityResource.getAssociationOverrides().get(0);
	
		xmlAssociationOverride.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumnImpl());
		xmlAssociationOverride.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumnImpl());
		xmlAssociationOverride.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumnImpl());
		
		xmlAssociationOverride.getJoinColumns().get(0).setName("FOO");
		xmlAssociationOverride.getJoinColumns().get(1).setName("BAR");
		xmlAssociationOverride.getJoinColumns().get(2).setName("BAZ");

		ListIterator<OrmJoinColumn> joinColumns = ormAssociationOverride.specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		xmlAssociationOverride.getJoinColumns().move(2, 0);
		joinColumns = ormAssociationOverride.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		xmlAssociationOverride.getJoinColumns().move(0, 1);
		joinColumns = ormAssociationOverride.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		xmlAssociationOverride.getJoinColumns().remove(1);
		joinColumns = ormAssociationOverride.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		xmlAssociationOverride.getJoinColumns().remove(1);
		joinColumns = ormAssociationOverride.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		xmlAssociationOverride.getJoinColumns().remove(0);
		assertFalse(ormAssociationOverride.specifiedJoinColumns().hasNext());
	}
}
/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_3.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkEntity;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMultitenant;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMultitenantHolder;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.MultitenantType;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_3.context.EclipseLink2_3ContextModelTestCase;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.EclipseLinkMultitenantType;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.orm.OrmEclipseLinkMultitenancy;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.orm.OrmTenantDiscriminatorColumn;

@SuppressWarnings("nls")
public class OrmEclipseLinkMultitenancyTests
	extends EclipseLink2_3ContextModelTestCase
{
	protected static final String SUB_TYPE_NAME = "AnnotationTestTypeChild";
	protected static final String FULLY_QUALIFIED_SUB_TYPE_NAME = PACKAGE_NAME + "." + SUB_TYPE_NAME;


	public OrmEclipseLinkMultitenancyTests(String name) {
		super(name);
	}

	public void testUpdateSpecifiedType() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlMultitenantHolder multitenantHolder = (XmlMultitenantHolder) getXmlEntityMappings().getEntities().get(0);
		multitenantHolder.setMultitenant(EclipseLinkOrmFactory.eINSTANCE.createXmlMultitenant());

		OrmEclipseLinkMultitenancy ormMultitenancy = ((OrmEclipseLinkEntity) ormPersistentType.getMapping()).getMultitenancy();
		XmlMultitenant multitenantResource = multitenantHolder.getMultitenant();
		assertNull(ormMultitenancy.getSpecifiedType());
		assertNull(multitenantResource.getType());

		//set type in the resource model, verify context model updated
		multitenantResource.setType(MultitenantType.SINGLE_TABLE);
		assertEquals(EclipseLinkMultitenantType.SINGLE_TABLE, ormMultitenancy.getSpecifiedType());
		assertEquals(MultitenantType.SINGLE_TABLE, multitenantResource.getType());

		//set type to null in the resource model
		multitenantResource.setType(null);
		assertNull(ormMultitenancy.getSpecifiedType());
		assertNull(multitenantResource.getType());

		multitenantResource.setType(MultitenantType.TABLE_PER_TENANT);
		assertEquals(EclipseLinkMultitenantType.TABLE_PER_TENANT, ormMultitenancy.getSpecifiedType());
		assertEquals(MultitenantType.TABLE_PER_TENANT, multitenantResource.getType());

		multitenantHolder.setMultitenant(null);
		assertNull(ormMultitenancy.getSpecifiedType());
		assertNull(ormMultitenancy.getType());
		assertNull(multitenantHolder.getMultitenant());
	}

	public void testModifySpecifiedType() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlMultitenantHolder multitenantHolder = (XmlMultitenantHolder) getXmlEntityMappings().getEntities().get(0);
		multitenantHolder.setMultitenant(EclipseLinkOrmFactory.eINSTANCE.createXmlMultitenant());

		OrmEclipseLinkMultitenancy ormMultitenancy = ((OrmEclipseLinkEntity) ormPersistentType.getMapping()).getMultitenancy();
		XmlMultitenant multitenantResource = multitenantHolder.getMultitenant();
		assertNull(ormMultitenancy.getSpecifiedType());
		assertNull(multitenantResource.getType());

		//set type in the context model, verify resource model modified
		ormMultitenancy.setSpecifiedType(EclipseLinkMultitenantType.SINGLE_TABLE);
		assertEquals(EclipseLinkMultitenantType.SINGLE_TABLE, ormMultitenancy.getSpecifiedType());
		assertEquals(MultitenantType.SINGLE_TABLE, multitenantResource.getType());

		//set type to null in the context model
		ormMultitenancy.setSpecifiedType(null);
		assertNull(ormMultitenancy.getSpecifiedType());
		assertNull(multitenantResource.getType());	
	}

	public void testUpdateSpecifiedIncludeCriteria() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlMultitenantHolder multitenantHolder = (XmlMultitenantHolder) getXmlEntityMappings().getEntities().get(0);
		multitenantHolder.setMultitenant(EclipseLinkOrmFactory.eINSTANCE.createXmlMultitenant());

		OrmEclipseLinkMultitenancy ormMultitenancy = ((OrmEclipseLinkEntity) ormPersistentType.getMapping()).getMultitenancy();
		XmlMultitenant multitenantResource = multitenantHolder.getMultitenant();

		assertNull(ormMultitenancy.getSpecifiedIncludeCriteria());
		assertNull(multitenantResource.getIncludeCriteria());

		//set enumerated in the resource model, verify context model updated
		multitenantResource.setIncludeCriteria(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormMultitenancy.getSpecifiedIncludeCriteria());
		assertEquals(Boolean.TRUE, multitenantResource.getIncludeCriteria());

		multitenantResource.setIncludeCriteria(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormMultitenancy.getSpecifiedIncludeCriteria());
		assertEquals(Boolean.FALSE, multitenantResource.getIncludeCriteria());

		//set enumerated to null in the resource model
		multitenantResource.setIncludeCriteria(null);
		assertNull(ormMultitenancy.getSpecifiedIncludeCriteria());
		assertNull(multitenantResource.getIncludeCriteria());
	}

	public void testModifySpecifiedIncludeCriteria() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlMultitenantHolder multitenantHolder = (XmlMultitenantHolder) getXmlEntityMappings().getEntities().get(0);
		multitenantHolder.setMultitenant(EclipseLinkOrmFactory.eINSTANCE.createXmlMultitenant());

		OrmEclipseLinkMultitenancy ormMultitenancy = ((OrmEclipseLinkEntity) ormPersistentType.getMapping()).getMultitenancy();
		XmlMultitenant multitenantResource = multitenantHolder.getMultitenant();

		assertNull(ormMultitenancy.getSpecifiedIncludeCriteria());
		assertNull(multitenantResource.getIncludeCriteria());

		//set enumerated in the context model, verify resource model updated
		ormMultitenancy.setSpecifiedIncludeCriteria(Boolean.TRUE);
		assertEquals(Boolean.TRUE, multitenantResource.getIncludeCriteria());
		assertEquals(Boolean.TRUE, ormMultitenancy.getSpecifiedIncludeCriteria());

		ormMultitenancy.setSpecifiedIncludeCriteria(Boolean.FALSE);
		assertEquals(Boolean.FALSE, multitenantResource.getIncludeCriteria());
		assertEquals(Boolean.FALSE, ormMultitenancy.getSpecifiedIncludeCriteria());

		//set enumerated to null in the context model
		ormMultitenancy.setSpecifiedIncludeCriteria(null);
		assertNull(multitenantResource.getIncludeCriteria());
		assertNull(ormMultitenancy.getSpecifiedIncludeCriteria());
	}

	public void testAddSpecifiedTenantDiscriminatorColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlMultitenantHolder multitenantHolder = (XmlMultitenantHolder) getXmlEntityMappings().getEntities().get(0);
		multitenantHolder.setMultitenant(EclipseLinkOrmFactory.eINSTANCE.createXmlMultitenant());

		OrmEclipseLinkMultitenancy ormMultitenancy = ((OrmEclipseLinkEntity) ormPersistentType.getMapping()).getMultitenancy();
		XmlMultitenant multitenantResource = multitenantHolder.getMultitenant();

		OrmTenantDiscriminatorColumn tenantDiscriminatorColumn = ormMultitenancy.addSpecifiedTenantDiscriminatorColumn(0);
		tenantDiscriminatorColumn.setSpecifiedName("FOO");

		assertEquals("FOO", multitenantResource.getTenantDiscriminatorColumns().get(0).getName());

		OrmTenantDiscriminatorColumn tenantDiscriminatorColumn2 = ormMultitenancy.addSpecifiedTenantDiscriminatorColumn(0);
		tenantDiscriminatorColumn2.setSpecifiedName("BAR");

		assertEquals("BAR", multitenantResource.getTenantDiscriminatorColumns().get(0).getName());
		assertEquals("FOO", multitenantResource.getTenantDiscriminatorColumns().get(1).getName());

		OrmTenantDiscriminatorColumn tenantDiscriminatorColumn3 = ormMultitenancy.addSpecifiedTenantDiscriminatorColumn(1);
		tenantDiscriminatorColumn3.setSpecifiedName("BAZ");

		assertEquals("BAR", multitenantResource.getTenantDiscriminatorColumns().get(0).getName());
		assertEquals("BAZ", multitenantResource.getTenantDiscriminatorColumns().get(1).getName());
		assertEquals("FOO", multitenantResource.getTenantDiscriminatorColumns().get(2).getName());

		ListIterator<OrmTenantDiscriminatorColumn> tenantDiscriminatorColumns = ormMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals(tenantDiscriminatorColumn2, tenantDiscriminatorColumns.next());
		assertEquals(tenantDiscriminatorColumn3, tenantDiscriminatorColumns.next());
		assertEquals(tenantDiscriminatorColumn, tenantDiscriminatorColumns.next());

		tenantDiscriminatorColumns = ormMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAR", tenantDiscriminatorColumns.next().getName());
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());
	}

	public void testRemoveSpecifiedTenantDiscriminatorColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlMultitenantHolder multitenantHolder = (XmlMultitenantHolder) getXmlEntityMappings().getEntities().get(0);
		multitenantHolder.setMultitenant(EclipseLinkOrmFactory.eINSTANCE.createXmlMultitenant());

		OrmEclipseLinkMultitenancy ormMultitenancy = ((OrmEclipseLinkEntity) ormPersistentType.getMapping()).getMultitenancy();
		XmlMultitenant multitenantResource = multitenantHolder.getMultitenant();

		ormMultitenancy.addSpecifiedTenantDiscriminatorColumn(0).setSpecifiedName("FOO");
		ormMultitenancy.addSpecifiedTenantDiscriminatorColumn(1).setSpecifiedName("BAR");
		ormMultitenancy.addSpecifiedTenantDiscriminatorColumn(2).setSpecifiedName("BAZ");

		assertEquals(3, multitenantResource.getTenantDiscriminatorColumns().size());

		ormMultitenancy.removeSpecifiedTenantDiscriminatorColumn(0);
		assertEquals(2, multitenantResource.getTenantDiscriminatorColumns().size());
		assertEquals("BAR", multitenantResource.getTenantDiscriminatorColumns().get(0).getName());
		assertEquals("BAZ", multitenantResource.getTenantDiscriminatorColumns().get(1).getName());

		ormMultitenancy.removeSpecifiedTenantDiscriminatorColumn(0);
		assertEquals(1, multitenantResource.getTenantDiscriminatorColumns().size());
		assertEquals("BAZ", multitenantResource.getTenantDiscriminatorColumns().get(0).getName());

		ormMultitenancy.removeSpecifiedTenantDiscriminatorColumn(0);
		assertEquals(0, multitenantResource.getTenantDiscriminatorColumns().size());
	}

	public void testMoveSpecifiedTenantDiscriminatorColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlMultitenantHolder multitenantHolder = (XmlMultitenantHolder) getXmlEntityMappings().getEntities().get(0);
		multitenantHolder.setMultitenant(EclipseLinkOrmFactory.eINSTANCE.createXmlMultitenant());

		OrmEclipseLinkMultitenancy ormMultitenancy = ((OrmEclipseLinkEntity) ormPersistentType.getMapping()).getMultitenancy();
		XmlMultitenant multitenantResource = multitenantHolder.getMultitenant();

		ormMultitenancy.addSpecifiedTenantDiscriminatorColumn(0).setSpecifiedName("FOO");
		ormMultitenancy.addSpecifiedTenantDiscriminatorColumn(1).setSpecifiedName("BAR");
		ormMultitenancy.addSpecifiedTenantDiscriminatorColumn(2).setSpecifiedName("BAZ");

		assertEquals(3, multitenantResource.getTenantDiscriminatorColumns().size());


		ormMultitenancy.moveSpecifiedTenantDiscriminatorColumn(2, 0);
		ListIterator<OrmTenantDiscriminatorColumn> tenantDiscriminatorColumns = ormMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAR", tenantDiscriminatorColumns.next().getName());
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());

		assertEquals("BAR", multitenantResource.getTenantDiscriminatorColumns().get(0).getName());
		assertEquals("BAZ", multitenantResource.getTenantDiscriminatorColumns().get(1).getName());
		assertEquals("FOO", multitenantResource.getTenantDiscriminatorColumns().get(2).getName());


		ormMultitenancy.moveSpecifiedTenantDiscriminatorColumn(0, 1);
		tenantDiscriminatorColumns = ormMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertEquals("BAR", tenantDiscriminatorColumns.next().getName());
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());

		assertEquals("BAZ", multitenantResource.getTenantDiscriminatorColumns().get(0).getName());
		assertEquals("BAR", multitenantResource.getTenantDiscriminatorColumns().get(1).getName());
		assertEquals("FOO", multitenantResource.getTenantDiscriminatorColumns().get(2).getName());
	}

	public void testUpdateTenantDiscriminatorColumns() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlMultitenantHolder multitenantHolder = (XmlMultitenantHolder) getXmlEntityMappings().getEntities().get(0);
		multitenantHolder.setMultitenant(EclipseLinkOrmFactory.eINSTANCE.createXmlMultitenant());

		OrmEclipseLinkMultitenancy ormMultitenancy = ((OrmEclipseLinkEntity) ormPersistentType.getMapping()).getMultitenancy();
		XmlMultitenant multitenantResource = multitenantHolder.getMultitenant();

		multitenantResource.getTenantDiscriminatorColumns().add(EclipseLinkOrmFactory.eINSTANCE.createXmlTenantDiscriminatorColumn());
		multitenantResource.getTenantDiscriminatorColumns().add(EclipseLinkOrmFactory.eINSTANCE.createXmlTenantDiscriminatorColumn());
		multitenantResource.getTenantDiscriminatorColumns().add(EclipseLinkOrmFactory.eINSTANCE.createXmlTenantDiscriminatorColumn());

		multitenantResource.getTenantDiscriminatorColumns().get(0).setName("FOO");
		multitenantResource.getTenantDiscriminatorColumns().get(1).setName("BAR");
		multitenantResource.getTenantDiscriminatorColumns().get(2).setName("BAZ");

		ListIterator<OrmTenantDiscriminatorColumn> tenantDiscriminatorColumns = ormMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());
		assertEquals("BAR", tenantDiscriminatorColumns.next().getName());
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());

		multitenantResource.getTenantDiscriminatorColumns().move(2, 0);
		tenantDiscriminatorColumns = ormMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAR", tenantDiscriminatorColumns.next().getName());
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());

		multitenantResource.getTenantDiscriminatorColumns().move(0, 1);
		tenantDiscriminatorColumns = ormMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertEquals("BAR", tenantDiscriminatorColumns.next().getName());
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());

		multitenantResource.getTenantDiscriminatorColumns().remove(1);
		tenantDiscriminatorColumns = ormMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());

		multitenantResource.getTenantDiscriminatorColumns().remove(1);
		tenantDiscriminatorColumns = ormMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());

		multitenantResource.getTenantDiscriminatorColumns().remove(0);
		assertFalse(ormMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator().hasNext());
	}

}

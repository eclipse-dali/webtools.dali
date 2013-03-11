/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMultitenantType2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmSpecifiedTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMultitenant;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMultitenantHolder;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.MultitenantType;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLink2_3ContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLink2_3OrmMultitenancyTests
	extends EclipseLink2_3ContextModelTestCase
{
	protected static final String SUB_TYPE_NAME = "AnnotationTestTypeChild";
	protected static final String FULLY_QUALIFIED_SUB_TYPE_NAME = PACKAGE_NAME + "." + SUB_TYPE_NAME;


	public EclipseLink2_3OrmMultitenancyTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}

	private ICompilationUnit createTestMultitenantMappedSuperclass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(
					JPA.MAPPED_SUPERCLASS,
					EclipseLink.MULTITENANT,
					EclipseLink.TENANT_DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
				sb.append("@Multitenant").append(CR);
				sb.append("@TenantDiscriminatorColumn(name=\"MS_TENANT_ID\")").append(CR);
			}
		});
	}

	private ICompilationUnit createTestMultitenantRootEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(
					JPA.ENTITY,
					JPA.INHERITANCE,
					EclipseLink.MULTITENANT,
					EclipseLink.TENANT_DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Inheritenace").append(CR);
				sb.append("@Multitenant").append(CR);
				sb.append("@TenantDiscriminatorColumn(name=\"ROOT_ENTITY_TENANT_ID\")").append(CR);
			}
		});
	}

	private void createTestSubType() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("AnnotationTestTypeChild").append(" ");
				sb.append("extends " + TYPE_NAME + " ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
	}

	public void testUpdateSpecifiedType() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlMultitenantHolder multitenantHolder = (XmlMultitenantHolder) getXmlEntityMappings().getEntities().get(0);
		multitenantHolder.setMultitenant(EclipseLinkOrmFactory.eINSTANCE.createXmlMultitenant());

		OrmEclipseLinkMultitenancy2_3 ormMultitenancy = ((EclipseLinkOrmEntity) ormPersistentType.getMapping()).getMultitenancy();
		XmlMultitenant multitenantResource = multitenantHolder.getMultitenant();
		assertNull(ormMultitenancy.getSpecifiedType());
		assertNull(multitenantResource.getType());

		//set type in the resource model, verify context model updated
		multitenantResource.setType(MultitenantType.SINGLE_TABLE);
		assertEquals(EclipseLinkMultitenantType2_3.SINGLE_TABLE, ormMultitenancy.getSpecifiedType());
		assertEquals(MultitenantType.SINGLE_TABLE, multitenantResource.getType());

		//set type to null in the resource model
		multitenantResource.setType(null);
		assertNull(ormMultitenancy.getSpecifiedType());
		assertNull(multitenantResource.getType());

		multitenantResource.setType(MultitenantType.TABLE_PER_TENANT);
		assertEquals(EclipseLinkMultitenantType2_3.TABLE_PER_TENANT, ormMultitenancy.getSpecifiedType());
		assertEquals(MultitenantType.TABLE_PER_TENANT, multitenantResource.getType());

		multitenantResource.setType(MultitenantType.VPD);
		assertEquals(EclipseLinkMultitenantType2_3.VPD, ormMultitenancy.getSpecifiedType());
		assertEquals(MultitenantType.VPD, multitenantResource.getType());

		multitenantHolder.setMultitenant(null);
		assertNull(ormMultitenancy.getSpecifiedType());
		assertNull(ormMultitenancy.getType());
		assertNull(multitenantHolder.getMultitenant());
	}

	public void testModifySpecifiedType() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlMultitenantHolder multitenantHolder = (XmlMultitenantHolder) getXmlEntityMappings().getEntities().get(0);
		multitenantHolder.setMultitenant(EclipseLinkOrmFactory.eINSTANCE.createXmlMultitenant());

		OrmEclipseLinkMultitenancy2_3 ormMultitenancy = ((EclipseLinkOrmEntity) ormPersistentType.getMapping()).getMultitenancy();
		XmlMultitenant multitenantResource = multitenantHolder.getMultitenant();
		assertNull(ormMultitenancy.getSpecifiedType());
		assertNull(multitenantResource.getType());

		//set type in the context model, verify resource model modified
		ormMultitenancy.setSpecifiedType(EclipseLinkMultitenantType2_3.SINGLE_TABLE);
		assertEquals(EclipseLinkMultitenantType2_3.SINGLE_TABLE, ormMultitenancy.getSpecifiedType());
		assertEquals(MultitenantType.SINGLE_TABLE, multitenantResource.getType());

		ormMultitenancy.setSpecifiedType(EclipseLinkMultitenantType2_3.VPD);
		assertEquals(EclipseLinkMultitenantType2_3.VPD, ormMultitenancy.getSpecifiedType());
		assertEquals(MultitenantType.VPD, multitenantResource.getType());

		ormMultitenancy.setSpecifiedType(EclipseLinkMultitenantType2_3.TABLE_PER_TENANT);
		assertEquals(EclipseLinkMultitenantType2_3.TABLE_PER_TENANT, ormMultitenancy.getSpecifiedType());
		assertEquals(MultitenantType.TABLE_PER_TENANT, multitenantResource.getType());

		//set type to null in the context model
		ormMultitenancy.setSpecifiedType(null);
		assertNull(ormMultitenancy.getSpecifiedType());
		assertNull(multitenantResource.getType());	
	}

	public void testUpdateSpecifiedIncludeCriteria() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlMultitenantHolder multitenantHolder = (XmlMultitenantHolder) getXmlEntityMappings().getEntities().get(0);
		multitenantHolder.setMultitenant(EclipseLinkOrmFactory.eINSTANCE.createXmlMultitenant());

		OrmEclipseLinkMultitenancy2_3 ormMultitenancy = ((EclipseLinkOrmEntity) ormPersistentType.getMapping()).getMultitenancy();
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

		OrmEclipseLinkMultitenancy2_3 ormMultitenancy = ((EclipseLinkOrmEntity) ormPersistentType.getMapping()).getMultitenancy();
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

		OrmEclipseLinkMultitenancy2_3 ormMultitenancy = ((EclipseLinkOrmEntity) ormPersistentType.getMapping()).getMultitenancy();
		XmlMultitenant multitenantResource = multitenantHolder.getMultitenant();

		OrmSpecifiedTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn = ormMultitenancy.addSpecifiedTenantDiscriminatorColumn(0);
		tenantDiscriminatorColumn.setSpecifiedName("FOO");

		assertEquals("FOO", multitenantResource.getTenantDiscriminatorColumns().get(0).getName());

		OrmSpecifiedTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn2 = ormMultitenancy.addSpecifiedTenantDiscriminatorColumn(0);
		tenantDiscriminatorColumn2.setSpecifiedName("BAR");

		assertEquals("BAR", multitenantResource.getTenantDiscriminatorColumns().get(0).getName());
		assertEquals("FOO", multitenantResource.getTenantDiscriminatorColumns().get(1).getName());

		OrmSpecifiedTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn3 = ormMultitenancy.addSpecifiedTenantDiscriminatorColumn(1);
		tenantDiscriminatorColumn3.setSpecifiedName("BAZ");

		assertEquals("BAR", multitenantResource.getTenantDiscriminatorColumns().get(0).getName());
		assertEquals("BAZ", multitenantResource.getTenantDiscriminatorColumns().get(1).getName());
		assertEquals("FOO", multitenantResource.getTenantDiscriminatorColumns().get(2).getName());

		ListIterator<OrmSpecifiedTenantDiscriminatorColumn2_3> tenantDiscriminatorColumns = ormMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
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

		OrmEclipseLinkMultitenancy2_3 ormMultitenancy = ((EclipseLinkOrmEntity) ormPersistentType.getMapping()).getMultitenancy();
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

		OrmEclipseLinkMultitenancy2_3 ormMultitenancy = ((EclipseLinkOrmEntity) ormPersistentType.getMapping()).getMultitenancy();
		XmlMultitenant multitenantResource = multitenantHolder.getMultitenant();

		ormMultitenancy.addSpecifiedTenantDiscriminatorColumn(0).setSpecifiedName("FOO");
		ormMultitenancy.addSpecifiedTenantDiscriminatorColumn(1).setSpecifiedName("BAR");
		ormMultitenancy.addSpecifiedTenantDiscriminatorColumn(2).setSpecifiedName("BAZ");

		assertEquals(3, multitenantResource.getTenantDiscriminatorColumns().size());


		ormMultitenancy.moveSpecifiedTenantDiscriminatorColumn(2, 0);
		ListIterator<OrmSpecifiedTenantDiscriminatorColumn2_3> tenantDiscriminatorColumns = ormMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
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

		OrmEclipseLinkMultitenancy2_3 ormMultitenancy = ((EclipseLinkOrmEntity) ormPersistentType.getMapping()).getMultitenancy();
		XmlMultitenant multitenantResource = multitenantHolder.getMultitenant();

		multitenantResource.getTenantDiscriminatorColumns().add(EclipseLinkOrmFactory.eINSTANCE.createXmlTenantDiscriminatorColumn());
		multitenantResource.getTenantDiscriminatorColumns().add(EclipseLinkOrmFactory.eINSTANCE.createXmlTenantDiscriminatorColumn());
		multitenantResource.getTenantDiscriminatorColumns().add(EclipseLinkOrmFactory.eINSTANCE.createXmlTenantDiscriminatorColumn());

		multitenantResource.getTenantDiscriminatorColumns().get(0).setName("FOO");
		multitenantResource.getTenantDiscriminatorColumns().get(1).setName("BAR");
		multitenantResource.getTenantDiscriminatorColumns().get(2).setName("BAZ");

		ListIterator<OrmSpecifiedTenantDiscriminatorColumn2_3> tenantDiscriminatorColumns = ormMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
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

	//MappedSuperclass specifies multitenancy, all subclass entities are multitenant by default.
	public void testTenantDiscriminatorColumnsWithMappedSuperclass() throws Exception {
		createTestMultitenantMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType ormPersistentType2 = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");

		OrmEclipseLinkMultitenancy2_3 multitenancy = ((EclipseLinkOrmEntity) ormPersistentType2.getMapping()).getMultitenancy();
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("MS_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());

		//if the entity specifies multitenant, then it does not get the tenant discriminator columns from the mapped superclass
		multitenancy.setSpecifiedType(EclipseLinkMultitenantType2_3.SINGLE_TABLE);
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());


		multitenancy.setSpecifiedMultitenant(false);
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertFalse(multitenancy.isMultitenant());
		assertEquals(0, multitenancy.getTenantDiscriminatorColumnsSize());

		OrmEclipseLinkMultitenancy2_3 mappedSuperclassMultitenancy = ((OrmEclipseLinkMappedSuperclass) ormPersistentType.getMapping()).getMultitenancy();
		mappedSuperclassMultitenancy.setSpecifiedMultitenant(true);
		mappedSuperclassMultitenancy.addSpecifiedTenantDiscriminatorColumn().setSpecifiedName("MS_TENANT_ID");
	
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("MS_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());

		multitenancy.setSpecifiedType(EclipseLinkMultitenantType2_3.SINGLE_TABLE);
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());
	}

	//Root entity specifies multitenancy and SINGLE_TABLE inheritance, all subclass entities are multitenant by default.
	public void testTenantDiscriminatorColumnsWithInheritance() throws Exception {
		createTestMultitenantRootEntity();
		createTestSubType();

		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");

		OrmEclipseLinkMultitenancy2_3 multitenancy = ((EclipseLinkOrmEntity) ormPersistentType.getMapping()).getMultitenancy();
		assertTrue(multitenancy.isMultitenant()); //multitenant by default from root entity
		assertFalse(multitenancy.isSpecifiedMultitenant());
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("ROOT_ENTITY_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());

		ormPersistentType.getMapping().getRootEntity().setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		assertTrue(multitenancy.isMultitenant()); //multitenant by default from root entity
		assertFalse(multitenancy.isSpecifiedMultitenant());
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("ROOT_ENTITY_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());

		ormPersistentType.getMapping().getRootEntity().setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		assertFalse(multitenancy.isMultitenant()); //not multitenant since inheritance strategy is table per class
		assertFalse(multitenancy.isSpecifiedMultitenant());
		assertEquals(0, multitenancy.getTenantDiscriminatorColumnsSize());

		EclipseLinkPersistenceUnitDefaults persistenceUnitDefaults = (EclipseLinkPersistenceUnitDefaults) getMappingFile().getRoot().getPersistenceUnitMetadata().getPersistenceUnitDefaults();
		persistenceUnitDefaults.addTenantDiscriminatorColumn().setSpecifiedName("PU_TENANT_ID");
		assertFalse(multitenancy.isMultitenant()); //not multitenant since inheritance strategy is table per class
		assertFalse(multitenancy.isSpecifiedMultitenant());
		assertEquals(0, multitenancy.getTenantDiscriminatorColumnsSize());

		//get the default tenant discriminator column from the persistence unit defaults instead of the root entity since inheritance strategy is table per class
		multitenancy.setSpecifiedMultitenant(true);
		assertTrue(multitenancy.isSpecifiedMultitenant());
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("PU_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());		

		multitenancy.addSpecifiedTenantDiscriminatorColumn().setSpecifiedName("CHILD_TENANT_ID");
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("CHILD_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());		

		ormPersistentType.getMapping().getRootEntity().setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("CHILD_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());		
	}

	public void testTenantDiscriminatorColumnsWithPersistenceUnitDefaults() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);

		EclipseLinkPersistenceUnitDefaults persistenceUnitDefaults = (EclipseLinkPersistenceUnitDefaults) getMappingFile().getRoot().getPersistenceUnitMetadata().getPersistenceUnitDefaults();
		persistenceUnitDefaults.addTenantDiscriminatorColumn().setSpecifiedName("PU_TENANT_ID");

		OrmEclipseLinkMultitenancy2_3 multitenancy = ((EclipseLinkOrmEntity) ormPersistentType.getMapping()).getMultitenancy();
		assertFalse(multitenancy.isMultitenant());
		assertEquals(0, multitenancy.getTenantDiscriminatorColumnsSize());

		multitenancy.setSpecifiedType(EclipseLinkMultitenantType2_3.SINGLE_TABLE);
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("PU_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());

		//if there are specified tenant discriminator columns then there should not be any default ones
		multitenancy.addSpecifiedTenantDiscriminatorColumn().setSpecifiedName("ENTITY_TENANT_ID");
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals(0, multitenancy.getDefaultTenantDiscriminatorColumnsSize());
		assertEquals("ENTITY_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());

		//if entity is not SINGLE_TABLE multitenant than there are no default tenant discriminator columns
		multitenancy.setSpecifiedMultitenant(false);
		assertEquals(0, multitenancy.getTenantDiscriminatorColumnsSize());
	}

	public void testTenantDiscriminatorColumnsWithEntityMappingsDefaults() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);

		EclipseLinkEntityMappings entityMappings = (EclipseLinkEntityMappings) getMappingFile().getRoot();
		entityMappings.addSpecifiedTenantDiscriminatorColumn().setSpecifiedName("EM_TENANT_ID");

		OrmEclipseLinkMultitenancy2_3 multitenancy = ((EclipseLinkOrmEntity) ormPersistentType.getMapping()).getMultitenancy();
		assertFalse(multitenancy.isMultitenant());
		assertEquals(0, multitenancy.getTenantDiscriminatorColumnsSize());

		multitenancy.setSpecifiedType(EclipseLinkMultitenantType2_3.SINGLE_TABLE);
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("EM_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());

		//if entity is not SINGLE_TABLE multitenant than there are no default tenant discriminator columns
		multitenancy.setSpecifiedType(EclipseLinkMultitenantType2_3.TABLE_PER_TENANT);
		assertEquals(0, multitenancy.getTenantDiscriminatorColumnsSize());

		//if entity is not SINGLE_TABLE multitenant than there are no default tenant discriminator columns
		multitenancy.setSpecifiedMultitenant(false);
		assertEquals(0, multitenancy.getTenantDiscriminatorColumnsSize());


		EclipseLinkPersistenceUnitDefaults persistenceUnitDefaults = (EclipseLinkPersistenceUnitDefaults) getMappingFile().getRoot().getPersistenceUnitMetadata().getPersistenceUnitDefaults();
		persistenceUnitDefaults.addTenantDiscriminatorColumn().setSpecifiedName("PU_TENANT_ID");

		multitenancy.setSpecifiedType(EclipseLinkMultitenantType2_3.SINGLE_TABLE);
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("EM_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());
	}

	public void testTenantDiscriminatorColumnsWithJavaMultitenant() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		((EclipseLinkEntity) ormPersistentType.getJavaPersistentType().getMapping()).getMultitenancy().setSpecifiedMultitenant(true);
				
		OrmEclipseLinkMultitenancy2_3 multitenancy = ((EclipseLinkOrmEntity) ormPersistentType.getMapping()).getMultitenancy();
		assertTrue(multitenancy.isMultitenant()); //isMultitenant is true because the java entity specifies @Multitenant
		assertFalse(multitenancy.isSpecifiedMultitenant());
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());

		EclipseLinkEntityMappings entityMappings = (EclipseLinkEntityMappings) getMappingFile().getRoot();
		entityMappings.addSpecifiedTenantDiscriminatorColumn().setSpecifiedName("EM_TENANT_ID");

		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("EM_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());


		OrmPersistenceUnitMetadata persistenceUnitMetadata = (OrmPersistenceUnitMetadata) getMappingFile().getRoot().getPersistenceUnitMetadata();
		persistenceUnitMetadata.setXmlMappingMetadataComplete(true);
		assertFalse(multitenancy.isMultitenant());
		assertEquals(0, multitenancy.getTenantDiscriminatorColumnsSize());
	}
}

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

import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.DiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.orm.OrmDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmDiscriminatorColumnTests extends ContextModelTestCase
{
	public OrmDiscriminatorColumnTests(String name) {
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
	
	private void createTestAbstractEntity() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public abstract class ").append(TYPE_NAME).append(" ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, FILE_NAME, sourceWriter);
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
	
	public void testUpdateSpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmDiscriminatorColumn ormColumn = ormEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormColumn.getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the resource model, verify context model updated
		entityResource.setDiscriminatorColumn(OrmFactory.eINSTANCE.createXmlDiscriminatorColumn());
		entityResource.getDiscriminatorColumn().setName("FOO");
		assertEquals("FOO", ormColumn.getSpecifiedName());
		assertEquals("FOO", entityResource.getDiscriminatorColumn().getName());
	
		//set name to null in the resource model
		entityResource.getDiscriminatorColumn().setName(null);
		assertNull(ormColumn.getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn().getName());
		
		entityResource.getDiscriminatorColumn().setName("FOO");
		assertEquals("FOO", ormColumn.getSpecifiedName());
		assertEquals("FOO", entityResource.getDiscriminatorColumn().getName());

		entityResource.setDiscriminatorColumn(null);
		assertNull(ormColumn.getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
	public void testModifySpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmDiscriminatorColumn ormColumn = ormEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormColumn.getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the context model, verify resource model modified
		ormColumn.setSpecifiedName("foo");
		assertEquals("foo", ormColumn.getSpecifiedName());
		assertEquals("foo", entityResource.getDiscriminatorColumn().getName());
		
		//set name to null in the context model
		ormColumn.setSpecifiedName(null);
		assertNull(ormColumn.getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
//	public void testUpdateDefaultNameFromJavaTable() throws Exception {
//		createTestEntity();
//		
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlEntity ormEntity = (XmlEntity) ormPersistentType.getMapping();
//		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
//		
//		ormEntity.javaEntity().getTable().setSpecifiedName("Foo");
//		assertEquals("Foo", ormEntity.getTable().getDefaultName());
//		
//		ormEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
//		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
//
//		ormEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
//		ormEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
//		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
//	
//		ormEntity.setSpecifiedMetadataComplete(null);
//		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
//		
//		ormEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
//		assertEquals("Foo", ormEntity.getTable().getDefaultName());
//		
//		ormEntity.getTable().setSpecifiedName("Bar");
//		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
//	}
//	
//	public void testUpdateDefaultNameNoJava() throws Exception {
//		createTestEntity();
//		
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlEntity ormEntity = (XmlEntity) ormPersistentType.getMapping();
//		assertEquals("Foo", ormEntity.getTable().getDefaultName());
//	}
//	
//	public void testUpdateDefaultNameFromParent() throws Exception {
//		createTestEntity();
//		createTestSubType();
//		
//		OrmPersistentType parentOrmPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		OrmPersistentType childOrmPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
//		XmlEntity parentXmlEntity = (XmlEntity) parentOrmPersistentType.getMapping();
//		XmlEntity childXmlEntity = (XmlEntity) childOrmPersistentType.getMapping();
//		
//		assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
//		assertEquals(TYPE_NAME, childXmlEntity.getTable().getDefaultName());
//		
//		parentXmlEntity.getTable().setSpecifiedName("FOO");
//		assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
//		assertEquals("FOO", childXmlEntity.getTable().getDefaultName());
//
//		parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
//		assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
//		assertEquals("AnnotationTestTypeChild", childXmlEntity.getTable().getDefaultName());
//	}
	
	public void testUpdateSpecifiedLength() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmDiscriminatorColumn ormColumn = ormEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormColumn.getSpecifiedLength());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the resource model, verify context model updated
		entityResource.setDiscriminatorColumn(OrmFactory.eINSTANCE.createXmlDiscriminatorColumn());
		entityResource.getDiscriminatorColumn().setLength(Integer.valueOf(8));
		assertEquals(Integer.valueOf(8), ormColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(8), entityResource.getDiscriminatorColumn().getLength());
	
		//set name to null in the resource model
		entityResource.getDiscriminatorColumn().setLength(null);
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(entityResource.getDiscriminatorColumn().getLength());
		
		entityResource.getDiscriminatorColumn().setLength(Integer.valueOf(11));
		assertEquals(Integer.valueOf(11), ormColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(11), entityResource.getDiscriminatorColumn().getLength());

		entityResource.setDiscriminatorColumn(null);
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
	public void testModifySpecifiedLength() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmDiscriminatorColumn ormColumn = ormEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormColumn.getSpecifiedLength());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the context model, verify resource model modified
		ormColumn.setSpecifiedLength(Integer.valueOf(7));
		assertEquals(Integer.valueOf(7), ormColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(7), entityResource.getDiscriminatorColumn().getLength());
		
		//set name to null in the context model
		ormColumn.setSpecifiedLength(null);
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(entityResource.getDiscriminatorColumn());
	}

	public void testUpdateSpecifiedColumnDefinition() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmDiscriminatorColumn ormColumn = ormEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormColumn.getColumnDefinition());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the resource model, verify context model updated
		entityResource.setDiscriminatorColumn(OrmFactory.eINSTANCE.createXmlDiscriminatorColumn());
		entityResource.getDiscriminatorColumn().setColumnDefinition("FOO");
		assertEquals("FOO", ormColumn.getColumnDefinition());
		assertEquals("FOO", entityResource.getDiscriminatorColumn().getColumnDefinition());
	
		//set name to null in the resource model
		entityResource.getDiscriminatorColumn().setColumnDefinition(null);
		assertNull(ormColumn.getColumnDefinition());
		assertNull(entityResource.getDiscriminatorColumn().getColumnDefinition());
		
		entityResource.getDiscriminatorColumn().setColumnDefinition("FOO");
		assertEquals("FOO", ormColumn.getColumnDefinition());
		assertEquals("FOO", entityResource.getDiscriminatorColumn().getColumnDefinition());

		entityResource.setDiscriminatorColumn(null);
		assertNull(ormColumn.getColumnDefinition());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
	public void testModifySpecifiedColumnDefinition() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmDiscriminatorColumn ormColumn = ormEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormColumn.getColumnDefinition());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the context model, verify resource model modified
		ormColumn.setColumnDefinition("foo");
		assertEquals("foo", ormColumn.getColumnDefinition());
		assertEquals("foo", entityResource.getDiscriminatorColumn().getColumnDefinition());
		
		//set name to null in the context model
		ormColumn.setColumnDefinition(null);
		assertNull(ormColumn.getColumnDefinition());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
	public void testUpdateSpecifiedDiscriminatorType() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmDiscriminatorColumn ormColumn = ormEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormColumn.getSpecifiedDiscriminatorType());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set discriminator type in the resource model, verify context model updated
		entityResource.setDiscriminatorColumn(OrmFactory.eINSTANCE.createXmlDiscriminatorColumn());
		entityResource.getDiscriminatorColumn().setDiscriminatorType(org.eclipse.jpt.jpa.core.resource.orm.DiscriminatorType.STRING);
		assertEquals(DiscriminatorType.STRING, ormColumn.getSpecifiedDiscriminatorType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.DiscriminatorType.STRING, entityResource.getDiscriminatorColumn().getDiscriminatorType());
	
		//set discriminator type to null in the resource model
		entityResource.getDiscriminatorColumn().setDiscriminatorType(null);
		assertNull(ormColumn.getSpecifiedDiscriminatorType());
		assertNull(entityResource.getDiscriminatorColumn().getDiscriminatorType());
		
		entityResource.getDiscriminatorColumn().setDiscriminatorType(org.eclipse.jpt.jpa.core.resource.orm.DiscriminatorType.CHAR);
		assertEquals(DiscriminatorType.CHAR, ormColumn.getSpecifiedDiscriminatorType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.DiscriminatorType.CHAR, entityResource.getDiscriminatorColumn().getDiscriminatorType());

		entityResource.setDiscriminatorColumn(null);
		assertNull(ormColumn.getSpecifiedDiscriminatorType());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
	public void testModifySpecifiedDiscriminatorType() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmDiscriminatorColumn ormColumn = ormEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormColumn.getSpecifiedDiscriminatorType());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set discriminator type in the context model, verify resource model modified
		ormColumn.setSpecifiedDiscriminatorType(DiscriminatorType.STRING);
		assertEquals(DiscriminatorType.STRING, ormColumn.getSpecifiedDiscriminatorType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.DiscriminatorType.STRING, entityResource.getDiscriminatorColumn().getDiscriminatorType());
		
		//set discriminator type to null in the context model
		ormColumn.setSpecifiedDiscriminatorType(null);
		assertNull(ormColumn.getSpecifiedDiscriminatorType());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
	public void testDefaultsNoDiscriminatorColumnInJava() throws Exception {
		createTestAbstractEntity();
		createTestSubType();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity abstractEntity = (OrmEntity) persistentType.getMapping();

		OrmPersistentType childPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + "." + "AnnotationTestTypeChild");
		OrmEntity childEntity = (OrmEntity) childPersistentType.getMapping();

		//test defaults with single-table inheritance, no specified discriminator column set
		assertEquals(InheritanceType.SINGLE_TABLE, abstractEntity.getDefaultInheritanceStrategy());
		assertEquals(DiscriminatorColumn.DEFAULT_NAME, abstractEntity.getDiscriminatorColumn().getDefaultName());
		assertEquals(DiscriminatorColumn.DEFAULT_LENGTH, abstractEntity.getDiscriminatorColumn().getDefaultLength());
		assertEquals(DiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE, abstractEntity.getDiscriminatorColumn().getDefaultDiscriminatorType());
		
		assertEquals(InheritanceType.SINGLE_TABLE, childEntity.getDefaultInheritanceStrategy());
		assertEquals(DiscriminatorColumn.DEFAULT_NAME, childEntity.getDiscriminatorColumn().getDefaultName());
		assertEquals(DiscriminatorColumn.DEFAULT_LENGTH, childEntity.getDiscriminatorColumn().getDefaultLength());
		assertEquals(DiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE, childEntity.getDiscriminatorColumn().getDefaultDiscriminatorType());

		
		//test defaults with single-table inheritance, specified discriminator column set on root
		abstractEntity.getDiscriminatorColumn().setSpecifiedName("DTYPE2");
		abstractEntity.getDiscriminatorColumn().setSpecifiedLength(Integer.valueOf(5));
		abstractEntity.getDiscriminatorColumn().setSpecifiedDiscriminatorType(DiscriminatorType.CHAR);
		
		assertEquals(InheritanceType.SINGLE_TABLE, abstractEntity.getDefaultInheritanceStrategy());
		assertEquals(DiscriminatorColumn.DEFAULT_NAME, abstractEntity.getDiscriminatorColumn().getDefaultName());
		assertEquals(DiscriminatorColumn.DEFAULT_LENGTH, abstractEntity.getDiscriminatorColumn().getDefaultLength());
		assertEquals(DiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE, abstractEntity.getDiscriminatorColumn().getDefaultDiscriminatorType());
		assertEquals("DTYPE2", abstractEntity.getDiscriminatorColumn().getSpecifiedName());
		assertEquals(Integer.valueOf(5), abstractEntity.getDiscriminatorColumn().getSpecifiedLength());
		assertEquals(DiscriminatorType.CHAR, abstractEntity.getDiscriminatorColumn().getSpecifiedDiscriminatorType());
		
		assertEquals(InheritanceType.SINGLE_TABLE, childEntity.getDefaultInheritanceStrategy());
		assertEquals("DTYPE2", childEntity.getDiscriminatorColumn().getDefaultName());
		assertEquals(5, childEntity.getDiscriminatorColumn().getDefaultLength());
		assertEquals(DiscriminatorType.CHAR, childEntity.getDiscriminatorColumn().getDefaultDiscriminatorType());
		assertEquals(null, childEntity.getDiscriminatorColumn().getSpecifiedName());
		assertEquals(null, childEntity.getDiscriminatorColumn().getSpecifiedLength());
		assertEquals(null, childEntity.getDiscriminatorColumn().getSpecifiedDiscriminatorType());
		
		
		//test defaults with table-per-class inheritance, discriminator column does not apply
		abstractEntity.getDiscriminatorColumn().setSpecifiedName(null);
		abstractEntity.getDiscriminatorColumn().setSpecifiedLength(null);
		abstractEntity.getDiscriminatorColumn().setSpecifiedDiscriminatorType(null);
		abstractEntity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		
		assertEquals(InheritanceType.TABLE_PER_CLASS, abstractEntity.getInheritanceStrategy());
		assertEquals(null, abstractEntity.getDiscriminatorColumn().getDefaultName());
		assertEquals(0, abstractEntity.getDiscriminatorColumn().getDefaultLength());
		assertEquals(null, abstractEntity.getDiscriminatorColumn().getDefaultDiscriminatorType());
		
		assertEquals(InheritanceType.TABLE_PER_CLASS, childEntity.getDefaultInheritanceStrategy());
		assertEquals(null, childEntity.getDiscriminatorColumn().getDefaultName());
		assertEquals(0, childEntity.getDiscriminatorColumn().getDefaultLength());
		assertEquals(null, childEntity.getDiscriminatorColumn().getDefaultDiscriminatorType());
	}
	
	public void testDefaultsDiscriminatorColumnInJava() throws Exception {
		createTestAbstractEntity();
		createTestSubType();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity abstractEntity = (OrmEntity) persistentType.getMapping();

		OrmPersistentType childPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + "." + "AnnotationTestTypeChild");
		OrmEntity childEntity = (OrmEntity) childPersistentType.getMapping();
		
		((Entity) persistentType.getJavaPersistentType().getMapping()).getDiscriminatorColumn().setSpecifiedName("FOO");
		((Entity) persistentType.getJavaPersistentType().getMapping()).getDiscriminatorColumn().setSpecifiedLength(Integer.valueOf(5));
		((Entity) persistentType.getJavaPersistentType().getMapping()).getDiscriminatorColumn().setSpecifiedDiscriminatorType(DiscriminatorType.CHAR);
		
		//test defaults with single-table inheritance, specified discriminator column set in java
		assertEquals(InheritanceType.SINGLE_TABLE, abstractEntity.getDefaultInheritanceStrategy());
		assertEquals("FOO", abstractEntity.getDiscriminatorColumn().getDefaultName());
		assertEquals(5, abstractEntity.getDiscriminatorColumn().getDefaultLength());
		assertEquals(DiscriminatorType.CHAR, abstractEntity.getDiscriminatorColumn().getDefaultDiscriminatorType());
		
		assertEquals(InheritanceType.SINGLE_TABLE, childEntity.getDefaultInheritanceStrategy());
		assertEquals("FOO", childEntity.getDiscriminatorColumn().getDefaultName());
		assertEquals(5, childEntity.getDiscriminatorColumn().getDefaultLength());
		assertEquals(DiscriminatorType.CHAR, childEntity.getDiscriminatorColumn().getDefaultDiscriminatorType());

		
		//test defaults with single-table inheritance, specified discriminator column set in java, metadata-complete true
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertEquals(InheritanceType.SINGLE_TABLE, abstractEntity.getDefaultInheritanceStrategy());
		assertEquals(DiscriminatorColumn.DEFAULT_NAME, abstractEntity.getDiscriminatorColumn().getDefaultName());
		assertEquals(DiscriminatorColumn.DEFAULT_LENGTH, abstractEntity.getDiscriminatorColumn().getDefaultLength());
		assertEquals(DiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE, abstractEntity.getDiscriminatorColumn().getDefaultDiscriminatorType());
		
		assertEquals(InheritanceType.SINGLE_TABLE, childEntity.getDefaultInheritanceStrategy());
		assertEquals(DiscriminatorColumn.DEFAULT_NAME, childEntity.getDiscriminatorColumn().getDefaultName());
		assertEquals(DiscriminatorColumn.DEFAULT_LENGTH, childEntity.getDiscriminatorColumn().getDefaultLength());
		assertEquals(DiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE, childEntity.getDiscriminatorColumn().getDefaultDiscriminatorType());
		
		
		//test defaults with single-table inheritance, specified discriminator column set in orm
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		abstractEntity.getDiscriminatorColumn().setSpecifiedName("BAR");
		abstractEntity.getDiscriminatorColumn().setSpecifiedLength(Integer.valueOf(6));
		abstractEntity.getDiscriminatorColumn().setSpecifiedDiscriminatorType(DiscriminatorType.INTEGER);
		
		assertEquals(InheritanceType.SINGLE_TABLE, abstractEntity.getDefaultInheritanceStrategy());
		assertEquals(DiscriminatorColumn.DEFAULT_NAME, abstractEntity.getDiscriminatorColumn().getDefaultName());
		assertEquals(DiscriminatorColumn.DEFAULT_LENGTH, abstractEntity.getDiscriminatorColumn().getDefaultLength());
		assertEquals(DiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE, abstractEntity.getDiscriminatorColumn().getDefaultDiscriminatorType());
		
		assertEquals(InheritanceType.SINGLE_TABLE, childEntity.getDefaultInheritanceStrategy());
		assertEquals("BAR", childEntity.getDiscriminatorColumn().getDefaultName());
		assertEquals(6, childEntity.getDiscriminatorColumn().getDefaultLength());
		assertEquals(DiscriminatorType.INTEGER, childEntity.getDiscriminatorColumn().getDefaultDiscriminatorType());

	}
}
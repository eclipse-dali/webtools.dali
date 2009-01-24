/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTable;
import org.eclipse.jpt.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlTable;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class OrmTableTests extends ContextModelTestCase
{
	public OrmTableTests(String name) {
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
	
	private ICompilationUnit createTestEntity() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
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
	
	public void testUpdateSpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmTable ormTable = ormEntity.getTable();
		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		assertNull(ormTable.getSpecifiedName());
		assertNull(entityResource.getTable());
		
		//set name in the resource model, verify context model updated
		entityResource.setTable(OrmFactory.eINSTANCE.createXmlTable());
		entityResource.getTable().setName("FOO");
		assertEquals("FOO", ormTable.getSpecifiedName());
		assertEquals("FOO", entityResource.getTable().getName());
	
		//set name to null in the resource model
		entityResource.getTable().setName(null);
		assertNull(ormTable.getSpecifiedName());
		assertNull(entityResource.getTable().getName());
		
		entityResource.getTable().setName("FOO");
		assertEquals("FOO", ormTable.getSpecifiedName());
		assertEquals("FOO", entityResource.getTable().getName());

		entityResource.setTable(null);
		assertNull(ormTable.getSpecifiedName());
		assertNull(entityResource.getTable());
	}
	
	public void testModifySpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmTable ormTable = ((OrmEntity) ormPersistentType.getMapping()).getTable();
		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		assertNull(ormTable.getSpecifiedName());
		assertNull(entityResource.getTable());
		
		//set name in the context model, verify resource model modified
		ormTable.setSpecifiedName("foo");
		assertEquals("foo", ormTable.getSpecifiedName());
		assertEquals("foo", entityResource.getTable().getName());
		
		//set name to null in the context model
		ormTable.setSpecifiedName(null);
		assertNull(ormTable.getSpecifiedName());
		assertNull(entityResource.getTable());
	}
	
	public void testUpdateDefaultNameFromJavaTable() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
		
		ormEntity.getJavaEntity().getTable().setSpecifiedName("Foo");
		assertEquals("Foo", ormEntity.getTable().getDefaultName());
		
		ormEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());

		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		ormEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
	
		ormEntity.setSpecifiedMetadataComplete(null);
		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertEquals("Foo", ormEntity.getTable().getDefaultName());
		
		ormEntity.getTable().setSpecifiedName("Bar");
		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
	}
	
	public void testUpdateDefaultNameNoJava() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		assertEquals("Foo", ormEntity.getTable().getDefaultName());
	}
	
	public void testUpdateDefaultNameFromEntityName() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		
		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
		ormEntity.setSpecifiedName("foo");
		
		assertEquals("foo", ormEntity.getTable().getDefaultName());
		
		ormEntity.setSpecifiedName(null);
		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
		
		ormEntity.getJavaEntity().setSpecifiedName("foo");
		assertEquals("foo", ormEntity.getTable().getDefaultName());
		
		ormEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
	}
	
	public void testUpdateDefaultNameFromParent() throws Exception {
		createTestEntity();
		createTestSubType();
		
		OrmPersistentType parentOrmPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType childOrmPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		OrmEntity parentXmlEntity = (OrmEntity) parentOrmPersistentType.getMapping();
		OrmEntity childXmlEntity = (OrmEntity) childOrmPersistentType.getMapping();
		
		assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
		assertEquals(TYPE_NAME, childXmlEntity.getTable().getDefaultName());
		
		parentXmlEntity.getTable().setSpecifiedName("FOO");
		assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
		assertEquals("FOO", childXmlEntity.getTable().getDefaultName());

		parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
		assertEquals("AnnotationTestTypeChild", childXmlEntity.getTable().getDefaultName());
	}

	public void testUpdateSpecifiedSchema() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmTable ormTable = ormEntity.getTable();
		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		assertNull(ormTable.getSpecifiedSchema());
		assertNull(entityResource.getTable());
		
		//set schema in the resource model, verify context model updated
		entityResource.setTable(OrmFactory.eINSTANCE.createXmlTable());
		entityResource.getTable().setSchema("FOO");
		assertEquals("FOO", ormTable.getSpecifiedSchema());
		assertEquals("FOO", entityResource.getTable().getSchema());
	
		//set Schema to null in the resource model
		entityResource.getTable().setSchema(null);
		assertNull(ormTable.getSpecifiedSchema());
		assertNull(entityResource.getTable().getSchema());
		
		entityResource.getTable().setSchema("FOO");
		assertEquals("FOO", ormTable.getSpecifiedSchema());
		assertEquals("FOO", entityResource.getTable().getSchema());

		entityResource.setTable(null);
		assertNull(ormTable.getSpecifiedSchema());
		assertNull(entityResource.getTable());
	}
	
	public void testUpdateDefaultSchemaFromJavaTable() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		assertNull(ormEntity.getTable().getDefaultSchema());
		
		ormEntity.getJavaEntity().getTable().setSpecifiedSchema("Foo");
		assertEquals("Foo", ormEntity.getTable().getDefaultSchema());
		
		ormEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertNull(ormEntity.getTable().getDefaultSchema());

		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		ormEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertNull(ormEntity.getTable().getDefaultSchema());
	
		ormEntity.setSpecifiedMetadataComplete(null);
		assertNull(ormEntity.getTable().getDefaultSchema());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertEquals("Foo", ormEntity.getTable().getDefaultSchema());
		
		ormEntity.getTable().setSpecifiedName("Bar");
		assertNull(ormEntity.getTable().getDefaultSchema());
	}
	
	public void testUpdateDefaultSchemaNoJava() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		assertNull(ormEntity.getTable().getDefaultSchema());
	}
	
	public void testUpdateDefaultSchemaFromParent() throws Exception {
		createTestEntity();
		createTestSubType();
		
		OrmPersistentType parentOrmPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType childOrmPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		OrmEntity parentXmlEntity = (OrmEntity) parentOrmPersistentType.getMapping();
		OrmEntity childXmlEntity = (OrmEntity) childOrmPersistentType.getMapping();
		
		assertNull(parentXmlEntity.getTable().getDefaultSchema());
		assertNull(childXmlEntity.getTable().getDefaultSchema());
		
		parentXmlEntity.getTable().setSpecifiedSchema("FOO");
		assertNull(parentXmlEntity.getTable().getDefaultSchema());
		assertEquals("FOO", childXmlEntity.getTable().getDefaultSchema());

		parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		assertNull(parentXmlEntity.getTable().getDefaultSchema());
		assertNull(childXmlEntity.getTable().getDefaultSchema());
	}
	
	public void testUpdateDefaultSchemaFromPersistenceUnitDefaults() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		assertNull(ormEntity.getTable().getDefaultSchema());
		
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedSchema("FOO");
		assertEquals("FOO", ormEntity.getTable().getDefaultSchema());
		
		getEntityMappings().setSpecifiedSchema("BAR");
		assertEquals("BAR", ormEntity.getTable().getDefaultSchema());
		
		ormEntity.getJavaEntity().getTable().setSpecifiedSchema("JAVA_SCHEMA");
		assertEquals("JAVA_SCHEMA", ormEntity.getTable().getDefaultSchema());
		
		ormEntity.getTable().setSpecifiedName("BLAH");
		//xml entity now has a table element so default schema is not taken from java
		assertEquals("BAR", ormEntity.getTable().getDefaultSchema());

		
		getEntityMappings().setSpecifiedSchema(null);
		assertEquals("FOO", ormEntity.getTable().getDefaultSchema());

		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedSchema(null);
		assertNull(ormEntity.getTable().getDefaultSchema());
		
		ormEntity.getTable().setSpecifiedName(null);
		assertEquals("JAVA_SCHEMA", ormEntity.getTable().getDefaultSchema());
	}

	public void testModifySpecifiedSchema() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmTable ormTable = ormEntity.getTable();
		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		assertNull(ormTable.getSpecifiedSchema());
		assertNull(entityResource.getTable());
		
		//set Schema in the context model, verify resource model modified
		ormTable.setSpecifiedSchema("foo");
		assertEquals("foo", ormTable.getSpecifiedSchema());
		assertEquals("foo", entityResource.getTable().getSchema());
		
		//set Schema to null in the context model
		ormTable.setSpecifiedSchema(null);
		assertNull(ormTable.getSpecifiedSchema());
		assertNull(entityResource.getTable());
	}
	
	public void testUpdateSpecifiedCatalog() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmTable ormTable = ormEntity.getTable();
		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		assertNull(ormTable.getSpecifiedCatalog());
		assertNull(entityResource.getTable());
		
		//set Catalog in the resource model, verify context model updated
		entityResource.setTable(OrmFactory.eINSTANCE.createXmlTable());
		entityResource.getTable().setCatalog("FOO");
		assertEquals("FOO", ormTable.getSpecifiedCatalog());
		assertEquals("FOO", entityResource.getTable().getCatalog());
	
		//set Catalog to null in the resource model
		entityResource.getTable().setCatalog(null);
		assertNull(ormTable.getSpecifiedCatalog());
		assertNull(entityResource.getTable().getCatalog());
		
		entityResource.getTable().setCatalog("FOO");
		assertEquals("FOO", ormTable.getSpecifiedCatalog());
		assertEquals("FOO", entityResource.getTable().getCatalog());

		entityResource.setTable(null);
		assertNull(ormTable.getSpecifiedCatalog());
		assertNull(entityResource.getTable());
	}
	
	public void testModifySpecifiedCatalog() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmTable ormTable = ormEntity.getTable();
		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		assertNull(ormTable.getSpecifiedCatalog());
		assertNull(entityResource.getTable());
		
		//set Catalog in the context model, verify resource model modified
		ormTable.setSpecifiedCatalog("foo");
		assertEquals("foo", ormTable.getSpecifiedCatalog());
		assertEquals("foo", entityResource.getTable().getCatalog());
		
		//set Catalog to null in the context model
		ormTable.setSpecifiedCatalog(null);
		assertNull(ormTable.getSpecifiedCatalog());
		assertNull(entityResource.getTable());
	}
	
	public void testUpdateDefaultCatalogFromJavaTable() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		assertNull(ormEntity.getTable().getDefaultCatalog());
		
		ormEntity.getJavaEntity().getTable().setSpecifiedCatalog("Foo");
		assertEquals("Foo", ormEntity.getTable().getDefaultCatalog());
		
		ormEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertNull(ormEntity.getTable().getDefaultCatalog());

		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		ormEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertNull(ormEntity.getTable().getDefaultCatalog());
	
		ormEntity.setSpecifiedMetadataComplete(null);
		assertNull(ormEntity.getTable().getDefaultCatalog());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertEquals("Foo", ormEntity.getTable().getDefaultCatalog());
		
		ormEntity.getTable().setSpecifiedName("Bar");
		assertNull(ormEntity.getTable().getDefaultCatalog());
	}
	
	public void testUpdateDefaultCatalogNoJava() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		assertNull(ormEntity.getTable().getDefaultCatalog());
	}
	
	public void testUpdateDefaultCatalogFromParent() throws Exception {
		createTestEntity();
		createTestSubType();
		
		OrmPersistentType parentOrmPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType childOrmPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		OrmEntity parentXmlEntity = (OrmEntity) parentOrmPersistentType.getMapping();
		OrmEntity childXmlEntity = (OrmEntity) childOrmPersistentType.getMapping();
		
		assertNull(parentXmlEntity.getTable().getDefaultCatalog());
		assertNull(childXmlEntity.getTable().getDefaultCatalog());
		
		parentXmlEntity.getTable().setSpecifiedCatalog("FOO");
		assertNull(parentXmlEntity.getTable().getDefaultCatalog());
		assertEquals("FOO", childXmlEntity.getTable().getDefaultCatalog());

		parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		assertNull(parentXmlEntity.getTable().getDefaultCatalog());
		assertNull(childXmlEntity.getTable().getDefaultCatalog());
	}
	
	public void testUpdateDefaultCatalogFromPersistenceUnitDefaults() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		assertNull(ormEntity.getTable().getDefaultCatalog());
		
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedCatalog("FOO");
		assertEquals("FOO", ormEntity.getTable().getDefaultCatalog());
		
		getEntityMappings().setSpecifiedCatalog("BAR");
		assertEquals("BAR", ormEntity.getTable().getDefaultCatalog());
		
		ormEntity.getJavaEntity().getTable().setSpecifiedCatalog("JAVA_CATALOG");
		assertEquals("JAVA_CATALOG", ormEntity.getTable().getDefaultCatalog());
		
		ormEntity.getTable().setSpecifiedName("BLAH");
		//xml entity now has a table element so default schema is not taken from java
		assertEquals("BAR", ormEntity.getTable().getDefaultCatalog());

		
		getEntityMappings().setSpecifiedCatalog(null);
		assertEquals("FOO", ormEntity.getTable().getDefaultCatalog());

		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedCatalog(null);
		assertNull(ormEntity.getTable().getDefaultCatalog());
		
		ormEntity.getTable().setSpecifiedName(null);
		assertEquals("JAVA_CATALOG", ormEntity.getTable().getDefaultCatalog());
}

//	
//	public void testUpdateName() throws Exception {
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlEntity ormEntity = (XmlEntity) ormPersistentType.getMapping();
//		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
//		assertEquals("Foo", ormEntity.getName());
//		
//		//set class in the resource model, verify context model updated
//		entityResource.setClassName("com.Bar");
//		assertEquals("Bar", ormEntity.getName());
//		
//		entityResource.setName("Baz");
//		assertEquals("Baz", ormEntity.getName());
//		
//		//set class to null in the resource model
//		entityResource.setClassName(null);
//		assertEquals("Baz", ormEntity.getName());
//		
//		entityResource.setName(null);
//		assertNull(ormEntity.getName());
//	}

	public void testUniqueConstraints() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		
		ListIterator<OrmUniqueConstraint> uniqueConstraints = ormEntity.getTable().uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());

		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		XmlTable tableResource = OrmFactory.eINSTANCE.createXmlTable();
		entityResource.setTable(tableResource);
		
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		tableResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "foo");
		
		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		tableResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "bar");
		
		uniqueConstraints = ormEntity.getTable().uniqueConstraints();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("bar", uniqueConstraints.next().columnNames().next());
		assertEquals("foo", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsSize() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		
		assertEquals(0,  ormEntity.getTable().uniqueConstraintsSize());

		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		XmlTable tableResource = OrmFactory.eINSTANCE.createXmlTable();
		entityResource.setTable(tableResource);
		
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		tableResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "foo");
		
		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		tableResource.getUniqueConstraints().add(1, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "bar");
		
		assertEquals(2,  ormEntity.getTable().uniqueConstraintsSize());
	}

	public void testAddUniqueConstraint() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		
		OrmTable table = ormEntity.getTable();
		table.addUniqueConstraint(0).addColumnName(0, "FOO");
		table.addUniqueConstraint(0).addColumnName(0, "BAR");
		table.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		XmlTable tableResource = entityResource.getTable();
		
		ListIterator<XmlUniqueConstraint> uniqueConstraints = tableResource.getUniqueConstraints().listIterator();
		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().get(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testAddUniqueConstraint2() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		
		OrmTable table = ormEntity.getTable();
		table.addUniqueConstraint(0).addColumnName(0, "FOO");
		table.addUniqueConstraint(1).addColumnName(0, "BAR");
		table.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		XmlTable tableResource = entityResource.getTable();
		
		ListIterator<XmlUniqueConstraint> uniqueConstraints = tableResource.getUniqueConstraints().listIterator();
		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().get(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		
		OrmTable table = ormEntity.getTable();
		table.addUniqueConstraint(0).addColumnName(0, "FOO");
		table.addUniqueConstraint(1).addColumnName(0, "BAR");
		table.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		XmlTable tableResource = entityResource.getTable();
		
		assertEquals(3, tableResource.getUniqueConstraints().size());

		table.removeUniqueConstraint(1);
		
		ListIterator<XmlUniqueConstraint> uniqueConstraintResources = tableResource.getUniqueConstraints().listIterator();
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));		
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertFalse(uniqueConstraintResources.hasNext());
		
		Iterator<OrmUniqueConstraint> uniqueConstraints = table.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());		
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		
		table.removeUniqueConstraint(1);
		uniqueConstraintResources = tableResource.getUniqueConstraints().listIterator();
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));		
		assertFalse(uniqueConstraintResources.hasNext());

		uniqueConstraints = table.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());		
		assertFalse(uniqueConstraints.hasNext());

		
		table.removeUniqueConstraint(0);
		uniqueConstraintResources = tableResource.getUniqueConstraints().listIterator();
		assertFalse(uniqueConstraintResources.hasNext());
		uniqueConstraints = table.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		
		OrmTable table = ormEntity.getTable();
		table.addUniqueConstraint(0).addColumnName(0, "FOO");
		table.addUniqueConstraint(1).addColumnName(0, "BAR");
		table.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		XmlTable tableResource = entityResource.getTable();
		
		assertEquals(3, tableResource.getUniqueConstraints().size());
		
		
		table.moveUniqueConstraint(2, 0);
		ListIterator<OrmUniqueConstraint> uniqueConstraints = table.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());

		ListIterator<XmlUniqueConstraint> uniqueConstraintResources = tableResource.getUniqueConstraints().listIterator();
		assertEquals("BAR", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));


		table.moveUniqueConstraint(0, 1);
		uniqueConstraints = table.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());

		uniqueConstraintResources = tableResource.getUniqueConstraints().listIterator();
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("BAR", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));
	}
	
	public void testUpdateUniqueConstraints() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		
		OrmTable table = ormEntity.getTable();
		
		XmlEntity entityResource = getOrmXmlResource().getEntityMappings().getEntities().get(0);
		XmlTable tableResource = OrmFactory.eINSTANCE.createXmlTable();
		entityResource.setTable(tableResource);
	
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		tableResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "FOO");

		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		tableResource.getUniqueConstraints().add(1, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "BAR");

		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		tableResource.getUniqueConstraints().add(2, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "BAZ");

		
		ListIterator<OrmUniqueConstraint> uniqueConstraints = table.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		tableResource.getUniqueConstraints().move(2, 0);
		uniqueConstraints = table.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableResource.getUniqueConstraints().move(0, 1);
		uniqueConstraints = table.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableResource.getUniqueConstraints().remove(1);
		uniqueConstraints = table.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableResource.getUniqueConstraints().remove(1);
		uniqueConstraints = table.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		tableResource.getUniqueConstraints().remove(0);
		uniqueConstraints = table.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
	}

//TODO not yet supporting unique constriants from java
//	public void testUniqueConstraintsFromJava() throws Exception {
//		createTestEntity();
//		
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
//				
//		ListIterator<OrmUniqueConstraint> uniqueConstraints = ormEntity.getTable().uniqueConstraints();
//		assertFalse(uniqueConstraints.hasNext());
//
//		JavaEntity javaEntity = (JavaEntity) ormPersistentType.getJavaPersistentType().getMapping();
//		javaEntity.getTable().addUniqueConstraint(0).addColumnName(0, "FOO");
//		javaEntity.getTable().addUniqueConstraint(1).addColumnName(0, "BAR");
//		javaEntity.getTable().addUniqueConstraint(2).addColumnName(0, "BAZ");
//		
//		
//		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
//		assertNull(entityResource.getTable());
//
//		uniqueConstraints = ormEntity.getTable().uniqueConstraints();
//		assertTrue(uniqueConstraints.hasNext());
//		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
//		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
//		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
//		assertFalse(uniqueConstraints.hasNext());
//		
//		entityResource.setTable(OrmFactory.eINSTANCE.createXmlTableImpl());
//		assertEquals(0,  ormEntity.getTable().uniqueConstraintsSize());
//	}

}
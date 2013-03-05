/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.SecondaryTable;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.SpecifiedSecondaryTable;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedSecondaryTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedSecondaryTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualSecondaryTable;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.jpa.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmSecondaryTableTests extends ContextModelTestCase
{
	public OrmSecondaryTableTests(String name) {
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
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
				
		//set name in the resource model, verify context model updated
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createXmlSecondaryTable());
		entityResource.getSecondaryTables().get(0).setName("FOO");
		OrmSpecifiedSecondaryTable secondaryTable = ormEntity.getSpecifiedSecondaryTables().iterator().next();
		assertEquals("FOO", secondaryTable.getSpecifiedName());
		assertEquals("FOO", entityResource.getSecondaryTables().get(0).getName());
	
		//set name to null in the resource model
		entityResource.getSecondaryTables().get(0).setName(null);
		assertNull(secondaryTable.getSpecifiedName());
		assertNull(entityResource.getSecondaryTables().get(0).getName());
		
		entityResource.getSecondaryTables().remove(0);
		assertFalse(ormEntity.getSpecifiedSecondaryTables().iterator().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());
	}
	
	public void testModifySpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		//set name in the context model, verify resource model modified
		OrmSpecifiedSecondaryTable secondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		secondaryTable.setSpecifiedName("foo");
		
		assertEquals("foo", secondaryTable.getSpecifiedName());
		assertEquals("foo", entityResource.getSecondaryTables().get(0).getName());
		
		//set name to null in the context model
		secondaryTable.setSpecifiedName(null);
		assertNull(secondaryTable.getSpecifiedName());
		assertNull(entityResource.getSecondaryTables().get(0).getName());
		
		ormEntity.removeSpecifiedSecondaryTable(0);
		assertFalse(ormEntity.getSpecifiedSecondaryTables().iterator().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());	
	}
	
	public void testUpdateDefaultNameFromJavaTable() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		
		ormEntity.getJavaTypeMapping().addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");

		SecondaryTable ormSecondaryTable = ormEntity.getVirtualSecondaryTables().iterator().next();
		assertEquals("FOO", ormSecondaryTable.getSpecifiedName());
		
		ormEntity.getJavaTypeMapping().getSpecifiedSecondaryTables().iterator().next().setSpecifiedName("BAZ");
		assertEquals("BAZ", ormSecondaryTable.getSpecifiedName());
		
		ormEntity.setSecondaryTablesAreDefinedInXml(true);
		assertNull(ormEntity.getSpecifiedSecondaryTables().iterator().next().getDefaultName());
		assertEquals("BAZ", ormEntity.getSpecifiedSecondaryTables().iterator().next().getSpecifiedName());

	}

	public void testUpdateSpecifiedSchema() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
				
		//set schema in the resource model, verify context model updated
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createXmlSecondaryTable());
		entityResource.getSecondaryTables().get(0).setSchema("FOO");
		OrmSpecifiedSecondaryTable secondaryTable = ormEntity.getSpecifiedSecondaryTables().iterator().next();
		assertEquals("FOO", secondaryTable.getSpecifiedSchema());
		assertEquals("FOO", entityResource.getSecondaryTables().get(0).getSchema());
	
		//set schema to null in the resource model
		entityResource.getSecondaryTables().get(0).setSchema(null);
		assertNull(secondaryTable.getSpecifiedSchema());
		assertNull(entityResource.getSecondaryTables().get(0).getSchema());
		
		entityResource.getSecondaryTables().remove(0);
		assertFalse(ormEntity.getSpecifiedSecondaryTables().iterator().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());
	}

	public void testModifySpecifiedSchema() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		//set schema in the context model, verify resource model modified
		OrmSpecifiedSecondaryTable secondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		secondaryTable.setSpecifiedSchema("foo");
		
		assertEquals("foo", secondaryTable.getSpecifiedSchema());
		assertEquals("foo", entityResource.getSecondaryTables().get(0).getSchema());
		
		//set schema to null in the context model
		secondaryTable.setSpecifiedSchema(null);
		assertNull(secondaryTable.getSpecifiedSchema());
		assertNull(entityResource.getSecondaryTables().get(0).getSchema());
		
		ormEntity.removeSpecifiedSecondaryTable(0);
		assertFalse(ormEntity.getSpecifiedSecondaryTables().iterator().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());	
	}

	public void testUpdateDefaultSchemaFromJavaTable() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
		
		SpecifiedSecondaryTable javaSecondaryTable = ormEntity.getJavaTypeMapping().addSpecifiedSecondaryTable(0);
		javaSecondaryTable.setSpecifiedName("FOO");
		javaSecondaryTable.setSpecifiedSchema("BAR");
		
		OrmVirtualSecondaryTable ormSecondaryTable = ormEntity.getVirtualSecondaryTables().iterator().next();
		assertEquals("BAR", ormSecondaryTable.getSpecifiedSchema());
		
		javaSecondaryTable.setSpecifiedSchema("BAZ");
		assertEquals("BAZ", ormSecondaryTable.getSpecifiedSchema());

		
		ormEntity.setSecondaryTablesAreDefinedInXml(true);
		assertNull(ormEntity.getSpecifiedSecondaryTables().iterator().next().getDefaultSchema());
		assertEquals("BAZ", ormEntity.getSpecifiedSecondaryTables().iterator().next().getSpecifiedSchema());
	}

	public void testUpdateDefaultSchemaFromParent() throws Exception {
		createTestEntity();
		createTestSubType();
		
		OrmPersistentType parentOrmPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType childOrmPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		OrmEntity parentOrmEntity = (OrmEntity) parentOrmPersistentType.getMapping();
		OrmEntity childOrmEntity = (OrmEntity) childOrmPersistentType.getMapping();
		
		assertNull(parentOrmEntity.getTable().getDefaultSchema());
		assertNull(childOrmEntity.getTable().getDefaultSchema());
		
		parentOrmEntity.getTable().setSpecifiedSchema("FOO");
		assertNull(parentOrmEntity.getTable().getDefaultSchema());
		assertEquals("FOO", childOrmEntity.getTable().getDefaultSchema());

		parentOrmEntity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		assertNull(parentOrmEntity.getTable().getDefaultSchema());
		assertNull(childOrmEntity.getTable().getDefaultSchema());
	}
	
	public void testUpdateDefaultSchemaFromPersistenceUnitDefaults() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmSpecifiedSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		ormSecondaryTable.setSpecifiedName("FOO");
		assertNull(ormSecondaryTable.getDefaultSchema());
		
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedSchema("FOO");
		assertEquals("FOO", ormSecondaryTable.getDefaultSchema());
		
		getEntityMappings().setSpecifiedSchema("BAR");
		assertEquals("BAR", ormSecondaryTable.getDefaultSchema());
		
		SpecifiedSecondaryTable javaSecondaryTable = ormEntity.getJavaTypeMapping().addSpecifiedSecondaryTable(0);
		javaSecondaryTable.setSpecifiedName("FOO");
		javaSecondaryTable.setSpecifiedSchema("JAVA_SCHEMA");
		assertEquals("BAR", ormSecondaryTable.getDefaultSchema()); //schema is not defaulted from underlying java
		
		getEntityMappings().setSpecifiedSchema(null);
		assertEquals("FOO", ormSecondaryTable.getDefaultSchema());

		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedSchema(null);
		assertNull(ormSecondaryTable.getDefaultSchema());
	}
	
	public void testUpdateSpecifiedCatalog() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
				
		//set catalog in the resource model, verify context model updated
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createXmlSecondaryTable());
		entityResource.getSecondaryTables().get(0).setCatalog("FOO");
		OrmSpecifiedSecondaryTable secondaryTable = ormEntity.getSpecifiedSecondaryTables().iterator().next();
		assertEquals("FOO", secondaryTable.getSpecifiedCatalog());
		assertEquals("FOO", entityResource.getSecondaryTables().get(0).getCatalog());
	
		//set catalog to null in the resource model
		entityResource.getSecondaryTables().get(0).setCatalog(null);
		assertNull(secondaryTable.getSpecifiedCatalog());
		assertNull(entityResource.getSecondaryTables().get(0).getCatalog());
		
		entityResource.getSecondaryTables().remove(0);
		assertFalse(ormEntity.getSpecifiedSecondaryTables().iterator().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());
	}
	
	public void testModifySpecifiedCatalog() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		//set catalog in the context model, verify resource model modified
		OrmSpecifiedSecondaryTable secondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		secondaryTable.setSpecifiedCatalog("foo");
		
		assertEquals("foo", secondaryTable.getSpecifiedCatalog());
		assertEquals("foo", entityResource.getSecondaryTables().get(0).getCatalog());
		
		//set catalog to null in the context model
		secondaryTable.setSpecifiedCatalog(null);
		assertNull(secondaryTable.getSpecifiedCatalog());
		assertNull(entityResource.getSecondaryTables().get(0).getCatalog());
		
		ormEntity.removeSpecifiedSecondaryTable(0);
		assertFalse(ormEntity.getSpecifiedSecondaryTables().iterator().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());	
	}
	
	public void testUpdateDefaultCatalogFromJavaTable() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
		
		SpecifiedSecondaryTable javaSecondaryTable = ormEntity.getJavaTypeMapping().addSpecifiedSecondaryTable(0);
		javaSecondaryTable.setSpecifiedName("FOO");
		javaSecondaryTable.setSpecifiedCatalog("BAR");
		
		OrmVirtualSecondaryTable ormSecondaryTable = ormEntity.getVirtualSecondaryTables().iterator().next();
		assertEquals("BAR", ormSecondaryTable.getSpecifiedCatalog());
		
		javaSecondaryTable.setSpecifiedCatalog("BAZ");
		assertEquals("BAZ", ormSecondaryTable.getSpecifiedCatalog());
		
		ormEntity.setSecondaryTablesAreDefinedInXml(true);
		assertNull(ormEntity.getSpecifiedSecondaryTables().iterator().next().getDefaultCatalog());
		assertEquals("BAZ", ormEntity.getSpecifiedSecondaryTables().iterator().next().getSpecifiedCatalog());
	}

	public void testUpdateDefaultCatalogFromPersistenceUnitDefaults() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmSpecifiedSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		ormSecondaryTable.setSpecifiedName("FOO");
		assertNull(ormSecondaryTable.getDefaultCatalog());
		
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedCatalog("FOO");
		assertEquals("FOO", ormSecondaryTable.getDefaultCatalog());
		
		getEntityMappings().setSpecifiedCatalog("BAR");
		assertEquals("BAR", ormSecondaryTable.getDefaultCatalog());
		
		SpecifiedSecondaryTable javaSecondaryTable = ormEntity.getJavaTypeMapping().addSpecifiedSecondaryTable(0);
		javaSecondaryTable.setSpecifiedName("FOO");
		javaSecondaryTable.setSpecifiedCatalog("JAVA_CATALOG");
		assertEquals("BAR", ormSecondaryTable.getDefaultCatalog()); //schema is not defaulted from underlying java
		
		getEntityMappings().setSpecifiedCatalog(null);
		assertEquals("FOO", ormSecondaryTable.getDefaultCatalog());

		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedCatalog(null);
		assertNull(ormSecondaryTable.getDefaultCatalog());
	}
	
	public void testAddSpecifiedPrimaryKeyJoinColumn() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmSpecifiedSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);
		
		OrmSpecifiedPrimaryKeyJoinColumn primaryKeyJoinColumn = ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(0);
		primaryKeyJoinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", secondaryTableResource.getPrimaryKeyJoinColumns().get(0).getName());
		
		OrmSpecifiedPrimaryKeyJoinColumn primaryKeyJoinColumn2 = ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(0);
		primaryKeyJoinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", secondaryTableResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("FOO", secondaryTableResource.getPrimaryKeyJoinColumns().get(1).getName());
		
		OrmSpecifiedPrimaryKeyJoinColumn primaryKeyJoinColumn3 = ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(1);
		primaryKeyJoinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", secondaryTableResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", secondaryTableResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", secondaryTableResource.getPrimaryKeyJoinColumns().get(2).getName());
		
		ListIterator<OrmSpecifiedPrimaryKeyJoinColumn> primaryKeyJoinColumns = ormSecondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals(primaryKeyJoinColumn2, primaryKeyJoinColumns.next());
		assertEquals(primaryKeyJoinColumn3, primaryKeyJoinColumns.next());
		assertEquals(primaryKeyJoinColumn, primaryKeyJoinColumns.next());
		
		primaryKeyJoinColumns = ormSecondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmSpecifiedSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);

		ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, secondaryTableResource.getPrimaryKeyJoinColumns().size());
		
		ormSecondaryTable.removeSpecifiedPrimaryKeyJoinColumn(0);
		assertEquals(2, secondaryTableResource.getPrimaryKeyJoinColumns().size());
		assertEquals("BAR", secondaryTableResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", secondaryTableResource.getPrimaryKeyJoinColumns().get(1).getName());

		ormSecondaryTable.removeSpecifiedPrimaryKeyJoinColumn(0);
		assertEquals(1, secondaryTableResource.getPrimaryKeyJoinColumns().size());
		assertEquals("BAZ", secondaryTableResource.getPrimaryKeyJoinColumns().get(0).getName());
		
		ormSecondaryTable.removeSpecifiedPrimaryKeyJoinColumn(0);
		assertEquals(0, secondaryTableResource.getPrimaryKeyJoinColumns().size());
	}
	
	public void testMoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmSpecifiedSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);

		ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, secondaryTableResource.getPrimaryKeyJoinColumns().size());
		
		
		ormSecondaryTable.moveSpecifiedPrimaryKeyJoinColumn(2, 0);
		ListIterator<OrmSpecifiedPrimaryKeyJoinColumn> primaryKeyJoinColumns = ormSecondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());

		assertEquals("BAR", secondaryTableResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", secondaryTableResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", secondaryTableResource.getPrimaryKeyJoinColumns().get(2).getName());


		ormSecondaryTable.moveSpecifiedPrimaryKeyJoinColumn(0, 1);
		primaryKeyJoinColumns = ormSecondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());

		assertEquals("BAZ", secondaryTableResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAR", secondaryTableResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", secondaryTableResource.getPrimaryKeyJoinColumns().get(2).getName());
	}
	
	public void testUpdatePrimaryKeyJoinColumns() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmSpecifiedSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);
		
		secondaryTableResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn());
		secondaryTableResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn());
		secondaryTableResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn());
		
		secondaryTableResource.getPrimaryKeyJoinColumns().get(0).setName("FOO");
		secondaryTableResource.getPrimaryKeyJoinColumns().get(1).setName("BAR");
		secondaryTableResource.getPrimaryKeyJoinColumns().get(2).setName("BAZ");

		ListIterator<OrmSpecifiedPrimaryKeyJoinColumn> primaryKeyJoinColumns = ormSecondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		secondaryTableResource.getPrimaryKeyJoinColumns().move(2, 0);
		primaryKeyJoinColumns = ormSecondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		secondaryTableResource.getPrimaryKeyJoinColumns().move(0, 1);
		primaryKeyJoinColumns = ormSecondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		secondaryTableResource.getPrimaryKeyJoinColumns().remove(1);
		primaryKeyJoinColumns = ormSecondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		secondaryTableResource.getPrimaryKeyJoinColumns().remove(1);
		primaryKeyJoinColumns = ormSecondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		secondaryTableResource.getPrimaryKeyJoinColumns().remove(0);
		assertFalse(ormSecondaryTable.getSpecifiedPrimaryKeyJoinColumns().iterator().hasNext());
	}
	

	public void testUniqueConstraints() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmSpecifiedSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);
		
		ListIterator<OrmUniqueConstraint> uniqueConstraints = ormSecondaryTable.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());
		
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		secondaryTableResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "foo");
		
		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		secondaryTableResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "bar");
		
		uniqueConstraints = ormSecondaryTable.getUniqueConstraints().iterator();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("bar", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("foo", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsSize() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmSpecifiedSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);
		
		assertEquals(0,  ormSecondaryTable.getUniqueConstraintsSize());
		
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		secondaryTableResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "foo");
		
		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		secondaryTableResource.getUniqueConstraints().add(1, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "bar");
		
		assertEquals(2,  ormSecondaryTable.getUniqueConstraintsSize());
	}

	public void testAddUniqueConstraint() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmSpecifiedSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);
		
		ormSecondaryTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		ormSecondaryTable.addUniqueConstraint(0).addColumnName(0, "BAR");
		ormSecondaryTable.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		ListIterator<XmlUniqueConstraint> uniqueConstraints = secondaryTableResource.getUniqueConstraints().listIterator();
		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().get(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testAddUniqueConstraint2() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmSpecifiedSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);
		
		ormSecondaryTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		ormSecondaryTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		ormSecondaryTable.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		ListIterator<XmlUniqueConstraint> uniqueConstraints = secondaryTableResource.getUniqueConstraints().listIterator();
		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().get(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmSpecifiedSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);
		
		ormSecondaryTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		ormSecondaryTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		ormSecondaryTable.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		assertEquals(3, secondaryTableResource.getUniqueConstraints().size());

		ormSecondaryTable.removeUniqueConstraint(1);
		
		ListIterator<XmlUniqueConstraint> uniqueConstraintResources = secondaryTableResource.getUniqueConstraints().listIterator();
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));		
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertFalse(uniqueConstraintResources.hasNext());
		
		Iterator<OrmUniqueConstraint> uniqueConstraints = ormSecondaryTable.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		
		ormSecondaryTable.removeUniqueConstraint(1);
		uniqueConstraintResources = secondaryTableResource.getUniqueConstraints().listIterator();
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));		
		assertFalse(uniqueConstraintResources.hasNext());

		uniqueConstraints = ormSecondaryTable.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());		
		assertFalse(uniqueConstraints.hasNext());

		
		ormSecondaryTable.removeUniqueConstraint(0);
		uniqueConstraintResources = secondaryTableResource.getUniqueConstraints().listIterator();
		assertFalse(uniqueConstraintResources.hasNext());
		uniqueConstraints = ormSecondaryTable.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmSpecifiedSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);
		
		ormSecondaryTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		ormSecondaryTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		ormSecondaryTable.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		assertEquals(3, secondaryTableResource.getUniqueConstraints().size());
		
		
		ormSecondaryTable.moveUniqueConstraint(2, 0);
		ListIterator<OrmUniqueConstraint> uniqueConstraints = ormSecondaryTable.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());

		ListIterator<XmlUniqueConstraint> uniqueConstraintResources = secondaryTableResource.getUniqueConstraints().listIterator();
		assertEquals("BAR", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));


		ormSecondaryTable.moveUniqueConstraint(0, 1);
		uniqueConstraints = ormSecondaryTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());

		uniqueConstraintResources = secondaryTableResource.getUniqueConstraints().listIterator();
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("BAR", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));
	}
	
	public void testUpdateUniqueConstraints() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmSpecifiedSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);
	
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		secondaryTableResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "FOO");

		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		secondaryTableResource.getUniqueConstraints().add(1, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "BAR");

		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		secondaryTableResource.getUniqueConstraints().add(2, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "BAZ");

		
		ListIterator<OrmUniqueConstraint> uniqueConstraints = ormSecondaryTable.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		secondaryTableResource.getUniqueConstraints().move(2, 0);
		uniqueConstraints = ormSecondaryTable.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		secondaryTableResource.getUniqueConstraints().move(0, 1);
		uniqueConstraints = ormSecondaryTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		secondaryTableResource.getUniqueConstraints().remove(1);
		uniqueConstraints = ormSecondaryTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		secondaryTableResource.getUniqueConstraints().remove(1);
		uniqueConstraints = ormSecondaryTable.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		secondaryTableResource.getUniqueConstraints().remove(0);
		uniqueConstraints = ormSecondaryTable.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	
	public void testUniqueConstraintsFromJava() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		
		JavaEntity javaEntity = (JavaEntity) ormPersistentType.getJavaPersistentType().getMapping();
		JavaSpecifiedSecondaryTable javaSecondaryTable = javaEntity.addSpecifiedSecondaryTable();
		javaSecondaryTable.setSpecifiedName("SECONDARY");
		
		SecondaryTable ormSecondaryTable = ormEntity.getSecondaryTables().iterator().next();
		assertTrue(ormSecondaryTable.isVirtual());
		assertFalse(ormSecondaryTable.getUniqueConstraints().iterator().hasNext());

		
		javaSecondaryTable.addUniqueConstraint().addColumnName("FOO");
		javaSecondaryTable.addUniqueConstraint().addColumnName("BAR");
		javaSecondaryTable.addUniqueConstraint().addColumnName("BAZ");

		List<UniqueConstraint> uniqueConstraints = ListTools.list(ormSecondaryTable.getUniqueConstraints());
		assertEquals(3, uniqueConstraints.size());
		assertEquals("FOO", uniqueConstraints.get(0).getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.get(1).getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.get(2).getColumnNames().iterator().next());
		
		ormEntity.setSecondaryTablesAreDefinedInXml(true);
		OrmSpecifiedSecondaryTable ormSecondaryTable2 = ormEntity.getSpecifiedSecondaryTables().iterator().next();
		ormSecondaryTable2.setSpecifiedName("SECONDARY");
		
		assertEquals("SECONDARY", ormSecondaryTable.getSpecifiedName());
		assertFalse(ormSecondaryTable2.isVirtual());
		assertEquals(3, ormSecondaryTable2.getUniqueConstraintsSize());
	}
}
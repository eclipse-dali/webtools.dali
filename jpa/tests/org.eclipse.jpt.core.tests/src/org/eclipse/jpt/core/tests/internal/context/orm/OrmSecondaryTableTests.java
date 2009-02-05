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
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

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
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
				
		//set name in the resource model, verify context model updated
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createXmlSecondaryTableImpl());
		entityResource.getSecondaryTables().get(0).setName("FOO");
		OrmSecondaryTable secondaryTable = ormEntity.specifiedSecondaryTables().next();
		assertEquals("FOO", secondaryTable.getSpecifiedName());
		assertEquals("FOO", entityResource.getSecondaryTables().get(0).getName());
	
		//set name to null in the resource model
		entityResource.getSecondaryTables().get(0).setName(null);
		assertNull(secondaryTable.getSpecifiedName());
		assertNull(entityResource.getSecondaryTables().get(0).getName());
		
		entityResource.getSecondaryTables().remove(0);
		assertFalse(ormEntity.specifiedSecondaryTables().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());
	}
	
	public void testModifySpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		//set name in the context model, verify resource model modified
		OrmSecondaryTable secondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		secondaryTable.setSpecifiedName("foo");
		
		assertEquals("foo", secondaryTable.getSpecifiedName());
		assertEquals("foo", entityResource.getSecondaryTables().get(0).getName());
		
		//set name to null in the context model
		secondaryTable.setSpecifiedName(null);
		assertNull(secondaryTable.getSpecifiedName());
		assertNull(entityResource.getSecondaryTables().get(0).getName());
		
		ormEntity.removeSpecifiedSecondaryTable(0);
		assertFalse(ormEntity.specifiedSecondaryTables().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());	
	}
	
	public void testUpdateDefaultNameFromJavaTable() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		
		ormEntity.getJavaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");

		OrmSecondaryTable ormSecondaryTable = ormEntity.virtualSecondaryTables().next();
		assertEquals("FOO", ormSecondaryTable.getSpecifiedName());
		
		ormEntity.getJavaEntity().specifiedSecondaryTables().next().setSpecifiedName("BAZ");
		assertEquals("BAZ", ormSecondaryTable.getSpecifiedName());
		
		ormEntity.setSecondaryTablesDefinedInXml(true);
		assertNull(ormEntity.specifiedSecondaryTables().next().getDefaultName());
		assertEquals("BAZ", ormEntity.specifiedSecondaryTables().next().getSpecifiedName());

	}

	public void testUpdateSpecifiedSchema() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
				
		//set schema in the resource model, verify context model updated
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createXmlSecondaryTableImpl());
		entityResource.getSecondaryTables().get(0).setSchema("FOO");
		OrmSecondaryTable secondaryTable = ormEntity.specifiedSecondaryTables().next();
		assertEquals("FOO", secondaryTable.getSpecifiedSchema());
		assertEquals("FOO", entityResource.getSecondaryTables().get(0).getSchema());
	
		//set schema to null in the resource model
		entityResource.getSecondaryTables().get(0).setSchema(null);
		assertNull(secondaryTable.getSpecifiedSchema());
		assertNull(entityResource.getSecondaryTables().get(0).getSchema());
		
		entityResource.getSecondaryTables().remove(0);
		assertFalse(ormEntity.specifiedSecondaryTables().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());
	}

	public void testModifySpecifiedSchema() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		//set schema in the context model, verify resource model modified
		OrmSecondaryTable secondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		secondaryTable.setSpecifiedSchema("foo");
		
		assertEquals("foo", secondaryTable.getSpecifiedSchema());
		assertEquals("foo", entityResource.getSecondaryTables().get(0).getSchema());
		
		//set schema to null in the context model
		secondaryTable.setSpecifiedSchema(null);
		assertNull(secondaryTable.getSpecifiedSchema());
		assertNull(entityResource.getSecondaryTables().get(0).getSchema());
		
		ormEntity.removeSpecifiedSecondaryTable(0);
		assertFalse(ormEntity.specifiedSecondaryTables().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());	
	}

	public void testUpdateDefaultSchemaFromJavaTable() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
		
		SecondaryTable javaSecondaryTable = ormEntity.getJavaEntity().addSpecifiedSecondaryTable(0);
		javaSecondaryTable.setSpecifiedName("FOO");
		javaSecondaryTable.setSpecifiedSchema("BAR");
		
		OrmSecondaryTable ormSecondaryTable = ormEntity.virtualSecondaryTables().next();
		assertEquals("BAR", ormSecondaryTable.getSpecifiedSchema());
		
		javaSecondaryTable.setSpecifiedSchema("BAZ");
		assertEquals("BAZ", ormSecondaryTable.getSpecifiedSchema());

		
		ormEntity.setSecondaryTablesDefinedInXml(true);
		assertNull(ormEntity.specifiedSecondaryTables().next().getDefaultSchema());
		assertEquals("BAZ", ormEntity.specifiedSecondaryTables().next().getSpecifiedSchema());
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
		OrmSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		ormSecondaryTable.setSpecifiedName("FOO");
		assertNull(ormSecondaryTable.getDefaultSchema());
		
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedSchema("FOO");
		assertEquals("FOO", ormSecondaryTable.getDefaultSchema());
		
		getEntityMappings().setSpecifiedSchema("BAR");
		assertEquals("BAR", ormSecondaryTable.getDefaultSchema());
		
		SecondaryTable javaSecondaryTable = ormEntity.getJavaEntity().addSpecifiedSecondaryTable(0);
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
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createXmlSecondaryTableImpl());
		entityResource.getSecondaryTables().get(0).setCatalog("FOO");
		OrmSecondaryTable secondaryTable = ormEntity.specifiedSecondaryTables().next();
		assertEquals("FOO", secondaryTable.getSpecifiedCatalog());
		assertEquals("FOO", entityResource.getSecondaryTables().get(0).getCatalog());
	
		//set catalog to null in the resource model
		entityResource.getSecondaryTables().get(0).setCatalog(null);
		assertNull(secondaryTable.getSpecifiedCatalog());
		assertNull(entityResource.getSecondaryTables().get(0).getCatalog());
		
		entityResource.getSecondaryTables().remove(0);
		assertFalse(ormEntity.specifiedSecondaryTables().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());
	}
	
	public void testModifySpecifiedCatalog() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		//set catalog in the context model, verify resource model modified
		OrmSecondaryTable secondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		secondaryTable.setSpecifiedCatalog("foo");
		
		assertEquals("foo", secondaryTable.getSpecifiedCatalog());
		assertEquals("foo", entityResource.getSecondaryTables().get(0).getCatalog());
		
		//set catalog to null in the context model
		secondaryTable.setSpecifiedCatalog(null);
		assertNull(secondaryTable.getSpecifiedCatalog());
		assertNull(entityResource.getSecondaryTables().get(0).getCatalog());
		
		ormEntity.removeSpecifiedSecondaryTable(0);
		assertFalse(ormEntity.specifiedSecondaryTables().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());	
	}
	
	public void testUpdateDefaultCatalogFromJavaTable() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
		
		SecondaryTable javaSecondaryTable = ormEntity.getJavaEntity().addSpecifiedSecondaryTable(0);
		javaSecondaryTable.setSpecifiedName("FOO");
		javaSecondaryTable.setSpecifiedCatalog("BAR");
		
		OrmSecondaryTable ormSecondaryTable = ormEntity.virtualSecondaryTables().next();
		assertEquals("BAR", ormSecondaryTable.getSpecifiedCatalog());
		
		javaSecondaryTable.setSpecifiedCatalog("BAZ");
		assertEquals("BAZ", ormSecondaryTable.getSpecifiedCatalog());
		
		ormEntity.setSecondaryTablesDefinedInXml(true);
		assertNull(ormEntity.specifiedSecondaryTables().next().getDefaultCatalog());
		assertEquals("BAZ", ormEntity.specifiedSecondaryTables().next().getSpecifiedCatalog());
	}

	public void testUpdateDefaultCatalogFromPersistenceUnitDefaults() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		ormSecondaryTable.setSpecifiedName("FOO");
		assertNull(ormSecondaryTable.getDefaultCatalog());
		
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedCatalog("FOO");
		assertEquals("FOO", ormSecondaryTable.getDefaultCatalog());
		
		getEntityMappings().setSpecifiedCatalog("BAR");
		assertEquals("BAR", ormSecondaryTable.getDefaultCatalog());
		
		SecondaryTable javaSecondaryTable = ormEntity.getJavaEntity().addSpecifiedSecondaryTable(0);
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
		OrmSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);
		
		OrmPrimaryKeyJoinColumn primaryKeyJoinColumn = ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(0);
		primaryKeyJoinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", secondaryTableResource.getPrimaryKeyJoinColumns().get(0).getName());
		
		OrmPrimaryKeyJoinColumn primaryKeyJoinColumn2 = ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(0);
		primaryKeyJoinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", secondaryTableResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("FOO", secondaryTableResource.getPrimaryKeyJoinColumns().get(1).getName());
		
		OrmPrimaryKeyJoinColumn primaryKeyJoinColumn3 = ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(1);
		primaryKeyJoinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", secondaryTableResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", secondaryTableResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", secondaryTableResource.getPrimaryKeyJoinColumns().get(2).getName());
		
		ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns = ormSecondaryTable.specifiedPrimaryKeyJoinColumns();
		assertEquals(primaryKeyJoinColumn2, primaryKeyJoinColumns.next());
		assertEquals(primaryKeyJoinColumn3, primaryKeyJoinColumns.next());
		assertEquals(primaryKeyJoinColumn, primaryKeyJoinColumns.next());
		
		primaryKeyJoinColumns = ormSecondaryTable.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
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
		OrmSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);

		ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, secondaryTableResource.getPrimaryKeyJoinColumns().size());
		
		
		ormSecondaryTable.moveSpecifiedPrimaryKeyJoinColumn(2, 0);
		ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns = ormSecondaryTable.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());

		assertEquals("BAR", secondaryTableResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", secondaryTableResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", secondaryTableResource.getPrimaryKeyJoinColumns().get(2).getName());


		ormSecondaryTable.moveSpecifiedPrimaryKeyJoinColumn(0, 1);
		primaryKeyJoinColumns = ormSecondaryTable.specifiedPrimaryKeyJoinColumns();
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
		OrmSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);
		
		secondaryTableResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumnImpl());
		secondaryTableResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumnImpl());
		secondaryTableResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumnImpl());
		
		secondaryTableResource.getPrimaryKeyJoinColumns().get(0).setName("FOO");
		secondaryTableResource.getPrimaryKeyJoinColumns().get(1).setName("BAR");
		secondaryTableResource.getPrimaryKeyJoinColumns().get(2).setName("BAZ");

		ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns = ormSecondaryTable.specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		secondaryTableResource.getPrimaryKeyJoinColumns().move(2, 0);
		primaryKeyJoinColumns = ormSecondaryTable.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		secondaryTableResource.getPrimaryKeyJoinColumns().move(0, 1);
		primaryKeyJoinColumns = ormSecondaryTable.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		secondaryTableResource.getPrimaryKeyJoinColumns().remove(1);
		primaryKeyJoinColumns = ormSecondaryTable.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		secondaryTableResource.getPrimaryKeyJoinColumns().remove(1);
		primaryKeyJoinColumns = ormSecondaryTable.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		secondaryTableResource.getPrimaryKeyJoinColumns().remove(0);
		assertFalse(ormSecondaryTable.specifiedPrimaryKeyJoinColumns().hasNext());
	}
	

	public void testUniqueConstraints() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);
		
		ListIterator<OrmUniqueConstraint> uniqueConstraints = ormSecondaryTable.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
		
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		secondaryTableResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "foo");
		
		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		secondaryTableResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "bar");
		
		uniqueConstraints = ormSecondaryTable.uniqueConstraints();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("bar", uniqueConstraints.next().columnNames().next());
		assertEquals("foo", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsSize() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);
		
		assertEquals(0,  ormSecondaryTable.uniqueConstraintsSize());
		
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		secondaryTableResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "foo");
		
		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		secondaryTableResource.getUniqueConstraints().add(1, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "bar");
		
		assertEquals(2,  ormSecondaryTable.uniqueConstraintsSize());
	}

	public void testAddUniqueConstraint() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
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
		OrmSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
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
		OrmSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
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
		
		Iterator<OrmUniqueConstraint> uniqueConstraints = ormSecondaryTable.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());		
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		
		ormSecondaryTable.removeUniqueConstraint(1);
		uniqueConstraintResources = secondaryTableResource.getUniqueConstraints().listIterator();
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));		
		assertFalse(uniqueConstraintResources.hasNext());

		uniqueConstraints = ormSecondaryTable.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());		
		assertFalse(uniqueConstraints.hasNext());

		
		ormSecondaryTable.removeUniqueConstraint(0);
		uniqueConstraintResources = secondaryTableResource.getUniqueConstraints().listIterator();
		assertFalse(uniqueConstraintResources.hasNext());
		uniqueConstraints = ormSecondaryTable.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);
		
		ormSecondaryTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		ormSecondaryTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		ormSecondaryTable.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		assertEquals(3, secondaryTableResource.getUniqueConstraints().size());
		
		
		ormSecondaryTable.moveUniqueConstraint(2, 0);
		ListIterator<OrmUniqueConstraint> uniqueConstraints = ormSecondaryTable.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());

		ListIterator<XmlUniqueConstraint> uniqueConstraintResources = secondaryTableResource.getUniqueConstraints().listIterator();
		assertEquals("BAR", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));


		ormSecondaryTable.moveUniqueConstraint(0, 1);
		uniqueConstraints = ormSecondaryTable.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());

		uniqueConstraintResources = secondaryTableResource.getUniqueConstraints().listIterator();
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("BAR", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));
	}
	
	public void testUpdateUniqueConstraints() throws Exception {
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		OrmSecondaryTable ormSecondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);
	
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		secondaryTableResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "FOO");

		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		secondaryTableResource.getUniqueConstraints().add(1, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "BAR");

		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraintImpl();
		secondaryTableResource.getUniqueConstraints().add(2, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "BAZ");

		
		ListIterator<OrmUniqueConstraint> uniqueConstraints = ormSecondaryTable.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		secondaryTableResource.getUniqueConstraints().move(2, 0);
		uniqueConstraints = ormSecondaryTable.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		secondaryTableResource.getUniqueConstraints().move(0, 1);
		uniqueConstraints = ormSecondaryTable.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		secondaryTableResource.getUniqueConstraints().remove(1);
		uniqueConstraints = ormSecondaryTable.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		secondaryTableResource.getUniqueConstraints().remove(1);
		uniqueConstraints = ormSecondaryTable.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		secondaryTableResource.getUniqueConstraints().remove(0);
		uniqueConstraints = ormSecondaryTable.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	
	public void testUniqueConstraintsFromJava() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		
		JavaEntity javaEntity = (JavaEntity) ormPersistentType.getJavaPersistentType().getMapping();
		JavaSecondaryTable javaSecondaryTable = javaEntity.addSpecifiedSecondaryTable(0);
		javaSecondaryTable.setSpecifiedName("SECONDARY");
		
		OrmSecondaryTable ormSecondaryTable = ormEntity.secondaryTables().next();
		assertTrue(ormSecondaryTable.isVirtual());
		ListIterator<OrmUniqueConstraint> uniqueConstraints = ormSecondaryTable.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());

		
		javaSecondaryTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		javaSecondaryTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		javaSecondaryTable.addUniqueConstraint(2).addColumnName(0, "BAZ");

		uniqueConstraints = ormSecondaryTable.uniqueConstraints();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		ormEntity.setSecondaryTablesDefinedInXml(true);
		OrmSecondaryTable ormSecondaryTable2 = ormEntity.secondaryTables().next();
		ormSecondaryTable2.setSpecifiedName("SECONDARY");
		
		assertEquals("SECONDARY", ormSecondaryTable.getSpecifiedName());
		assertFalse(ormSecondaryTable2.isVirtual());
		assertEquals(0, ormSecondaryTable2.uniqueConstraintsSize());
	}
}
/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEntity;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

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
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		persistenceResource().save(null);
	}
	
	private void createEntityAnnotation() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createIdAnnotation() throws Exception {
		this.createAnnotationAndMembers("Id", "");		
	}
	
	private IType createTestEntity() throws Exception {
		createEntityAnnotation();
		createIdAnnotation();
	
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
	
	private IType createTestSubType() throws Exception {
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
		return this.javaProject.createType(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
	}
	
	public void testUpdateSpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
				
		//set name in the resource model, verify context model updated
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createSecondaryTable());
		entityResource.getSecondaryTables().get(0).setName("FOO");
		OrmSecondaryTable secondaryTable = xmlEntity.specifiedSecondaryTables().next();
		assertEquals("FOO", secondaryTable.getSpecifiedName());
		assertEquals("FOO", entityResource.getSecondaryTables().get(0).getName());
	
		//set name to null in the resource model
		entityResource.getSecondaryTables().get(0).setName(null);
		assertNull(secondaryTable.getSpecifiedName());
		assertNull(entityResource.getSecondaryTables().get(0).getName());
		
		entityResource.getSecondaryTables().remove(0);
		assertFalse(xmlEntity.specifiedSecondaryTables().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());
	}
	
	public void testModifySpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		//set name in the context model, verify resource model modified
		OrmSecondaryTable secondaryTable = xmlEntity.addSpecifiedSecondaryTable(0);
		secondaryTable.setSpecifiedName("foo");
		
		assertEquals("foo", secondaryTable.getSpecifiedName());
		assertEquals("foo", entityResource.getSecondaryTables().get(0).getName());
		
		//set name to null in the context model
		secondaryTable.setSpecifiedName(null);
		assertNull(secondaryTable.getSpecifiedName());
		assertNull(entityResource.getSecondaryTables().get(0).getName());
		
		xmlEntity.removeSpecifiedSecondaryTable(0);
		assertFalse(xmlEntity.specifiedSecondaryTables().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());	
	}
	
	public void testUpdateDefaultNameFromJavaTable() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		
		xmlEntity.javaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		OrmSecondaryTable secondaryTable = xmlEntity.addSpecifiedSecondaryTable(0);
		secondaryTable.setSpecifiedName("FOO");
		assertNull(secondaryTable.getDefaultName());
	}

	public void testUpdateSpecifiedSchema() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
				
		//set schema in the resource model, verify context model updated
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createSecondaryTable());
		entityResource.getSecondaryTables().get(0).setSchema("FOO");
		OrmSecondaryTable secondaryTable = xmlEntity.specifiedSecondaryTables().next();
		assertEquals("FOO", secondaryTable.getSpecifiedSchema());
		assertEquals("FOO", entityResource.getSecondaryTables().get(0).getSchema());
	
		//set schema to null in the resource model
		entityResource.getSecondaryTables().get(0).setSchema(null);
		assertNull(secondaryTable.getSpecifiedSchema());
		assertNull(entityResource.getSecondaryTables().get(0).getSchema());
		
		entityResource.getSecondaryTables().remove(0);
		assertFalse(xmlEntity.specifiedSecondaryTables().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());
	}

	public void testModifySpecifiedSchema() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		//set schema in the context model, verify resource model modified
		OrmSecondaryTable secondaryTable = xmlEntity.addSpecifiedSecondaryTable(0);
		secondaryTable.setSpecifiedSchema("foo");
		
		assertEquals("foo", secondaryTable.getSpecifiedSchema());
		assertEquals("foo", entityResource.getSecondaryTables().get(0).getSchema());
		
		//set schema to null in the context model
		secondaryTable.setSpecifiedSchema(null);
		assertNull(secondaryTable.getSpecifiedSchema());
		assertNull(entityResource.getSecondaryTables().get(0).getSchema());
		
		xmlEntity.removeSpecifiedSecondaryTable(0);
		assertFalse(xmlEntity.specifiedSecondaryTables().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());	
	}

	public void testUpdateDefaultSchemaFromJavaTable() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
		
		SecondaryTable javaSecondaryTable = xmlEntity.javaEntity().addSpecifiedSecondaryTable(0);
		javaSecondaryTable.setSpecifiedName("FOO");
		javaSecondaryTable.setSpecifiedSchema("BAR");
		OrmSecondaryTable secondaryTable = xmlEntity.addSpecifiedSecondaryTable(0);
		secondaryTable.setSpecifiedName("FOO");
		assertNull(secondaryTable.getDefaultSchema());
	}

//	public void testUpdateDefaultSchemaFromParent() throws Exception {
//		createTestEntity();
//		createTestSubType();
//		
//		OrmPersistentType parentOrmPersistentType = entityMappings().addOrmPersistentType(FULLY_QUALIFIED_TYPE_NAME, IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
//		OrmPersistentType childOrmPersistentType = entityMappings().addOrmPersistentType(PACKAGE_NAME + ".AnnotationTestTypeChild", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
//		XmlEntity parentXmlEntity = (XmlEntity) parentOrmPersistentType.getMapping();
//		XmlEntity childXmlEntity = (XmlEntity) childOrmPersistentType.getMapping();
//		
//		assertNull(parentXmlEntity.getTable().getDefaultSchema());
//		assertNull(childXmlEntity.getTable().getDefaultSchema());
//		
//		parentXmlEntity.getTable().setSpecifiedSchema("FOO");
//		assertNull(parentXmlEntity.getTable().getDefaultSchema());
//		assertEquals("FOO", childXmlEntity.getTable().getDefaultSchema());
//
//		parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
//		assertNull(parentXmlEntity.getTable().getDefaultSchema());
//		assertNull(childXmlEntity.getTable().getDefaultSchema());
//	}
//	
	public void testUpdateDefaultSchemaFromPersistenceUnitDefaults() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		OrmSecondaryTable ormSecondaryTable = xmlEntity.addSpecifiedSecondaryTable(0);
		ormSecondaryTable.setSpecifiedName("FOO");
		assertNull(ormSecondaryTable.getDefaultSchema());
		
		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema("FOO");
		assertEquals("FOO", ormSecondaryTable.getDefaultSchema());
		
		xmlEntity.entityMappings().setSpecifiedSchema("BAR");
		assertEquals("BAR", ormSecondaryTable.getDefaultSchema());
		
		SecondaryTable javaSecondaryTable = xmlEntity.javaEntity().addSpecifiedSecondaryTable(0);
		javaSecondaryTable.setSpecifiedName("FOO");
		javaSecondaryTable.setSpecifiedSchema("JAVA_SCHEMA");
		assertEquals("BAR", ormSecondaryTable.getDefaultSchema()); //schema is not defaulted from underlying java
		
		xmlEntity.entityMappings().setSpecifiedSchema(null);
		assertEquals("FOO", ormSecondaryTable.getDefaultSchema());

		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema(null);
		assertNull(ormSecondaryTable.getDefaultSchema());
	}
	
	public void testUpdateSpecifiedCatalog() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
				
		//set catalog in the resource model, verify context model updated
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createSecondaryTable());
		entityResource.getSecondaryTables().get(0).setCatalog("FOO");
		OrmSecondaryTable secondaryTable = xmlEntity.specifiedSecondaryTables().next();
		assertEquals("FOO", secondaryTable.getSpecifiedCatalog());
		assertEquals("FOO", entityResource.getSecondaryTables().get(0).getCatalog());
	
		//set catalog to null in the resource model
		entityResource.getSecondaryTables().get(0).setCatalog(null);
		assertNull(secondaryTable.getSpecifiedCatalog());
		assertNull(entityResource.getSecondaryTables().get(0).getCatalog());
		
		entityResource.getSecondaryTables().remove(0);
		assertFalse(xmlEntity.specifiedSecondaryTables().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());
	}
	
	public void testModifySpecifiedCatalog() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		//set catalog in the context model, verify resource model modified
		OrmSecondaryTable secondaryTable = xmlEntity.addSpecifiedSecondaryTable(0);
		secondaryTable.setSpecifiedCatalog("foo");
		
		assertEquals("foo", secondaryTable.getSpecifiedCatalog());
		assertEquals("foo", entityResource.getSecondaryTables().get(0).getCatalog());
		
		//set catalog to null in the context model
		secondaryTable.setSpecifiedCatalog(null);
		assertNull(secondaryTable.getSpecifiedCatalog());
		assertNull(entityResource.getSecondaryTables().get(0).getCatalog());
		
		xmlEntity.removeSpecifiedSecondaryTable(0);
		assertFalse(xmlEntity.specifiedSecondaryTables().hasNext());
		assertEquals(0, entityResource.getSecondaryTables().size());	
	}
	
	public void testUpdateDefaultCatalogFromJavaTable() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
		
		SecondaryTable javaSecondaryTable = xmlEntity.javaEntity().addSpecifiedSecondaryTable(0);
		javaSecondaryTable.setSpecifiedName("FOO");
		javaSecondaryTable.setSpecifiedCatalog("BAR");
		OrmSecondaryTable secondaryTable = xmlEntity.addSpecifiedSecondaryTable(0);
		secondaryTable.setSpecifiedName("FOO");
		assertNull(secondaryTable.getDefaultCatalog());
	}

	public void testUpdateDefaultCatalogFromPersistenceUnitDefaults() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		OrmSecondaryTable ormSecondaryTable = xmlEntity.addSpecifiedSecondaryTable(0);
		ormSecondaryTable.setSpecifiedName("FOO");
		assertNull(ormSecondaryTable.getDefaultCatalog());
		
		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog("FOO");
		assertEquals("FOO", ormSecondaryTable.getDefaultCatalog());
		
		xmlEntity.entityMappings().setSpecifiedCatalog("BAR");
		assertEquals("BAR", ormSecondaryTable.getDefaultCatalog());
		
		SecondaryTable javaSecondaryTable = xmlEntity.javaEntity().addSpecifiedSecondaryTable(0);
		javaSecondaryTable.setSpecifiedName("FOO");
		javaSecondaryTable.setSpecifiedCatalog("JAVA_CATALOG");
		assertEquals("BAR", ormSecondaryTable.getDefaultCatalog()); //schema is not defaulted from underlying java
		
		xmlEntity.entityMappings().setSpecifiedCatalog(null);
		assertEquals("FOO", ormSecondaryTable.getDefaultCatalog());

		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog(null);
		assertNull(ormSecondaryTable.getDefaultCatalog());
	}
	
	public void testAddSpecifiedPrimaryKeyJoinColumn() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		GenericOrmEntity xmlEntity = (GenericOrmEntity) persistentType.getMapping();
		OrmSecondaryTable ormSecondaryTable = xmlEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);
		
		OrmPrimaryKeyJoinColumn primaryKeyJoinColumn = ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(0);
		ormResource().save(null);
		primaryKeyJoinColumn.setSpecifiedName("FOO");
		ormResource().save(null);
				
		assertEquals("FOO", secondaryTableResource.getPrimaryKeyJoinColumns().get(0).getName());
		
		OrmPrimaryKeyJoinColumn primaryKeyJoinColumn2 = ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(0);
		ormResource().save(null);
		primaryKeyJoinColumn2.setSpecifiedName("BAR");
		ormResource().save(null);
		
		assertEquals("BAR", secondaryTableResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("FOO", secondaryTableResource.getPrimaryKeyJoinColumns().get(1).getName());
		
		OrmPrimaryKeyJoinColumn primaryKeyJoinColumn3 = ormSecondaryTable.addSpecifiedPrimaryKeyJoinColumn(1);
		ormResource().save(null);
		primaryKeyJoinColumn3.setSpecifiedName("BAZ");
		ormResource().save(null);
		
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
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		GenericOrmEntity xmlEntity = (GenericOrmEntity) persistentType.getMapping();
		OrmSecondaryTable ormSecondaryTable = xmlEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
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
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		GenericOrmEntity xmlEntity = (GenericOrmEntity) persistentType.getMapping();
		OrmSecondaryTable ormSecondaryTable = xmlEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
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
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		GenericOrmEntity xmlEntity = (GenericOrmEntity) persistentType.getMapping();
		OrmSecondaryTable ormSecondaryTable = xmlEntity.addSpecifiedSecondaryTable(0);
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		XmlSecondaryTable secondaryTableResource = entityResource.getSecondaryTables().get(0);
		
		secondaryTableResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createPrimaryKeyJoinColumn());
		secondaryTableResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createPrimaryKeyJoinColumn());
		secondaryTableResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createPrimaryKeyJoinColumn());
		
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

}
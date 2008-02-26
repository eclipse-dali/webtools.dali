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
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTable;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEntity;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
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
		OrmTable ormTable = xmlEntity.getTable();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(ormTable.getSpecifiedName());
		assertNull(entityResource.getTable());
		
		//set name in the resource model, verify context model updated
		entityResource.setTable(OrmFactory.eINSTANCE.createTable());
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmTable ormTable = ((GenericOrmEntity) ormPersistentType.getMapping()).getTable();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
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
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
		
		xmlEntity.javaEntity().getTable().setSpecifiedName("Foo");
		assertEquals("Foo", xmlEntity.getTable().getDefaultName());
		
		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());

		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
	
		xmlEntity.setSpecifiedMetadataComplete(null);
		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
		
		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertEquals("Foo", xmlEntity.getTable().getDefaultName());
		
		xmlEntity.getTable().setSpecifiedName("Bar");
		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
	}
	
	public void testUpdateDefaultNameNoJava() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		assertEquals("Foo", xmlEntity.getTable().getDefaultName());
	}
	
	public void testUpdateDefaultNameFromParent() throws Exception {
		createTestEntity();
		createTestSubType();
		
		OrmPersistentType parentOrmPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType childOrmPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		GenericOrmEntity parentXmlEntity = (GenericOrmEntity) parentOrmPersistentType.getMapping();
		GenericOrmEntity childXmlEntity = (GenericOrmEntity) childOrmPersistentType.getMapping();
		
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		OrmTable ormTable = xmlEntity.getTable();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(ormTable.getSpecifiedSchema());
		assertNull(entityResource.getTable());
		
		//set schema in the resource model, verify context model updated
		entityResource.setTable(OrmFactory.eINSTANCE.createTable());
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
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		assertNull(xmlEntity.getTable().getDefaultSchema());
		
		xmlEntity.javaEntity().getTable().setSpecifiedSchema("Foo");
		assertEquals("Foo", xmlEntity.getTable().getDefaultSchema());
		
		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertNull(xmlEntity.getTable().getDefaultSchema());

		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertNull(xmlEntity.getTable().getDefaultSchema());
	
		xmlEntity.setSpecifiedMetadataComplete(null);
		assertNull(xmlEntity.getTable().getDefaultSchema());
		
		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertEquals("Foo", xmlEntity.getTable().getDefaultSchema());
		
		xmlEntity.getTable().setSpecifiedName("Bar");
		assertNull(xmlEntity.getTable().getDefaultSchema());
	}
	
	public void testUpdateDefaultSchemaNoJava() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		assertNull(xmlEntity.getTable().getDefaultSchema());
	}
	
	public void testUpdateDefaultSchemaFromParent() throws Exception {
		createTestEntity();
		createTestSubType();
		
		OrmPersistentType parentOrmPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType childOrmPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		GenericOrmEntity parentXmlEntity = (GenericOrmEntity) parentOrmPersistentType.getMapping();
		GenericOrmEntity childXmlEntity = (GenericOrmEntity) childOrmPersistentType.getMapping();
		
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
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		assertNull(xmlEntity.getTable().getDefaultSchema());
		
		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema("FOO");
		assertEquals("FOO", xmlEntity.getTable().getDefaultSchema());
		
		xmlEntity.entityMappings().setSpecifiedSchema("BAR");
		assertEquals("BAR", xmlEntity.getTable().getDefaultSchema());
		
		xmlEntity.javaEntity().getTable().setSpecifiedSchema("JAVA_SCHEMA");
		assertEquals("JAVA_SCHEMA", xmlEntity.getTable().getDefaultSchema());
		
		xmlEntity.getTable().setSpecifiedName("BLAH");
		//xml entity now has a table element so default schema is not taken from java
		assertEquals("BAR", xmlEntity.getTable().getDefaultSchema());

		
		xmlEntity.entityMappings().setSpecifiedSchema(null);
		assertEquals("FOO", xmlEntity.getTable().getDefaultSchema());

		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema(null);
		assertNull(xmlEntity.getTable().getDefaultSchema());
		
		xmlEntity.getTable().setSpecifiedName(null);
		assertEquals("JAVA_SCHEMA", xmlEntity.getTable().getDefaultSchema());
	}

	public void testModifySpecifiedSchema() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		OrmTable ormTable = xmlEntity.getTable();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		OrmTable ormTable = xmlEntity.getTable();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(ormTable.getSpecifiedCatalog());
		assertNull(entityResource.getTable());
		
		//set Catalog in the resource model, verify context model updated
		entityResource.setTable(OrmFactory.eINSTANCE.createTable());
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		OrmTable ormTable = xmlEntity.getTable();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
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
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		assertNull(xmlEntity.getTable().getDefaultCatalog());
		
		xmlEntity.javaEntity().getTable().setSpecifiedCatalog("Foo");
		assertEquals("Foo", xmlEntity.getTable().getDefaultCatalog());
		
		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertNull(xmlEntity.getTable().getDefaultCatalog());

		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertNull(xmlEntity.getTable().getDefaultCatalog());
	
		xmlEntity.setSpecifiedMetadataComplete(null);
		assertNull(xmlEntity.getTable().getDefaultCatalog());
		
		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertEquals("Foo", xmlEntity.getTable().getDefaultCatalog());
		
		xmlEntity.getTable().setSpecifiedName("Bar");
		assertNull(xmlEntity.getTable().getDefaultCatalog());
	}
	
	public void testUpdateDefaultCatalogNoJava() throws Exception {
		createTestEntity();
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		assertNull(xmlEntity.getTable().getDefaultCatalog());
	}
	
	public void testUpdateDefaultCatalogFromParent() throws Exception {
		createTestEntity();
		createTestSubType();
		
		OrmPersistentType parentOrmPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType childOrmPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		GenericOrmEntity parentXmlEntity = (GenericOrmEntity) parentOrmPersistentType.getMapping();
		GenericOrmEntity childXmlEntity = (GenericOrmEntity) childOrmPersistentType.getMapping();
		
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
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		assertNull(xmlEntity.getTable().getDefaultCatalog());
		
		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog("FOO");
		assertEquals("FOO", xmlEntity.getTable().getDefaultCatalog());
		
		xmlEntity.entityMappings().setSpecifiedCatalog("BAR");
		assertEquals("BAR", xmlEntity.getTable().getDefaultCatalog());
		
		xmlEntity.javaEntity().getTable().setSpecifiedCatalog("JAVA_CATALOG");
		assertEquals("JAVA_CATALOG", xmlEntity.getTable().getDefaultCatalog());
		
		xmlEntity.getTable().setSpecifiedName("BLAH");
		//xml entity now has a table element so default schema is not taken from java
		assertEquals("BAR", xmlEntity.getTable().getDefaultCatalog());

		
		xmlEntity.entityMappings().setSpecifiedCatalog(null);
		assertEquals("FOO", xmlEntity.getTable().getDefaultCatalog());

		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog(null);
		assertNull(xmlEntity.getTable().getDefaultCatalog());
		
		xmlEntity.getTable().setSpecifiedName(null);
		assertEquals("JAVA_CATALOG", xmlEntity.getTable().getDefaultCatalog());
}

//	
//	public void testUpdateName() throws Exception {
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlEntity xmlEntity = (XmlEntity) ormPersistentType.getMapping();
//		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
//		assertEquals("Foo", xmlEntity.getName());
//		
//		//set class in the resource model, verify context model updated
//		entityResource.setClassName("com.Bar");
//		assertEquals("Bar", xmlEntity.getName());
//		
//		entityResource.setName("Baz");
//		assertEquals("Baz", xmlEntity.getName());
//		
//		//set class to null in the resource model
//		entityResource.setClassName(null);
//		assertEquals("Baz", xmlEntity.getName());
//		
//		entityResource.setName(null);
//		assertNull(xmlEntity.getName());
//	}



}
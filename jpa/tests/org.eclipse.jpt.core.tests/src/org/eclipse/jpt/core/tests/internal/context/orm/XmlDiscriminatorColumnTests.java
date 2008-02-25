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
import org.eclipse.jpt.core.context.DiscriminatorType;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEntity;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class XmlDiscriminatorColumnTests extends ContextModelTestCase
{
	public XmlDiscriminatorColumnTests(String name) {
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
		GenericOrmDiscriminatorColumn xmlColumn = xmlEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		assertNull(xmlColumn.getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the resource model, verify context model updated
		entityResource.setDiscriminatorColumn(OrmFactory.eINSTANCE.createDiscriminatorColumn());
		entityResource.getDiscriminatorColumn().setName("FOO");
		ormResource().save(null);
		assertEquals("FOO", xmlColumn.getSpecifiedName());
		assertEquals("FOO", entityResource.getDiscriminatorColumn().getName());
	
		//set name to null in the resource model
		entityResource.getDiscriminatorColumn().setName(null);
		assertNull(xmlColumn.getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn().getName());
		
		entityResource.getDiscriminatorColumn().setName("FOO");
		assertEquals("FOO", xmlColumn.getSpecifiedName());
		assertEquals("FOO", entityResource.getDiscriminatorColumn().getName());

		entityResource.setDiscriminatorColumn(null);
		assertNull(xmlColumn.getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
	public void testModifySpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		GenericOrmDiscriminatorColumn xmlColumn = xmlEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		assertNull(xmlColumn.getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the context model, verify resource model modified
		xmlColumn.setSpecifiedName("foo");
		assertEquals("foo", xmlColumn.getSpecifiedName());
		assertEquals("foo", entityResource.getDiscriminatorColumn().getName());
		
		//set name to null in the context model
		xmlColumn.setSpecifiedName(null);
		assertNull(xmlColumn.getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
//	public void testUpdateDefaultNameFromJavaTable() throws Exception {
//		createTestEntity();
//		
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlEntity xmlEntity = (XmlEntity) ormPersistentType.getMapping();
//		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
//		
//		xmlEntity.javaEntity().getTable().setSpecifiedName("Foo");
//		assertEquals("Foo", xmlEntity.getTable().getDefaultName());
//		
//		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
//		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
//
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
//		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
//		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
//	
//		xmlEntity.setSpecifiedMetadataComplete(null);
//		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
//		
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
//		assertEquals("Foo", xmlEntity.getTable().getDefaultName());
//		
//		xmlEntity.getTable().setSpecifiedName("Bar");
//		assertEquals(TYPE_NAME, xmlEntity.getTable().getDefaultName());
//	}
//	
//	public void testUpdateDefaultNameNoJava() throws Exception {
//		createTestEntity();
//		
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlEntity xmlEntity = (XmlEntity) ormPersistentType.getMapping();
//		assertEquals("Foo", xmlEntity.getTable().getDefaultName());
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		GenericOrmDiscriminatorColumn xmlColumn = xmlEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the resource model, verify context model updated
		entityResource.setDiscriminatorColumn(OrmFactory.eINSTANCE.createDiscriminatorColumn());
		entityResource.getDiscriminatorColumn().setLength(Integer.valueOf(8));
		assertEquals(Integer.valueOf(8), xmlColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(8), entityResource.getDiscriminatorColumn().getLength());
	
		//set name to null in the resource model
		entityResource.getDiscriminatorColumn().setLength(null);
		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(entityResource.getDiscriminatorColumn().getLength());
		
		entityResource.getDiscriminatorColumn().setLength(Integer.valueOf(11));
		assertEquals(Integer.valueOf(11), xmlColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(11), entityResource.getDiscriminatorColumn().getLength());

		entityResource.setDiscriminatorColumn(null);
		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
	public void testModifySpecifiedLength() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		GenericOrmDiscriminatorColumn xmlColumn = xmlEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the context model, verify resource model modified
		xmlColumn.setSpecifiedLength(Integer.valueOf(7));
		assertEquals(Integer.valueOf(7), xmlColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(7), entityResource.getDiscriminatorColumn().getLength());
		
		//set name to null in the context model
		xmlColumn.setSpecifiedLength(null);
		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(entityResource.getDiscriminatorColumn());
	}

	public void testUpdateSpecifiedColumnDefinition() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		GenericOrmDiscriminatorColumn xmlColumn = xmlEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		assertNull(xmlColumn.getColumnDefinition());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the resource model, verify context model updated
		entityResource.setDiscriminatorColumn(OrmFactory.eINSTANCE.createDiscriminatorColumn());
		entityResource.getDiscriminatorColumn().setColumnDefinition("FOO");
		ormResource().save(null);
		assertEquals("FOO", xmlColumn.getColumnDefinition());
		assertEquals("FOO", entityResource.getDiscriminatorColumn().getColumnDefinition());
	
		//set name to null in the resource model
		entityResource.getDiscriminatorColumn().setColumnDefinition(null);
		assertNull(xmlColumn.getColumnDefinition());
		assertNull(entityResource.getDiscriminatorColumn().getColumnDefinition());
		
		entityResource.getDiscriminatorColumn().setColumnDefinition("FOO");
		assertEquals("FOO", xmlColumn.getColumnDefinition());
		assertEquals("FOO", entityResource.getDiscriminatorColumn().getColumnDefinition());

		entityResource.setDiscriminatorColumn(null);
		assertNull(xmlColumn.getColumnDefinition());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
	public void testModifySpecifiedColumnDefinition() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		GenericOrmDiscriminatorColumn xmlColumn = xmlEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		assertNull(xmlColumn.getColumnDefinition());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the context model, verify resource model modified
		xmlColumn.setColumnDefinition("foo");
		assertEquals("foo", xmlColumn.getColumnDefinition());
		assertEquals("foo", entityResource.getDiscriminatorColumn().getColumnDefinition());
		
		//set name to null in the context model
		xmlColumn.setColumnDefinition(null);
		assertNull(xmlColumn.getColumnDefinition());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
	public void testUpdateSpecifiedDiscriminatorType() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		GenericOrmDiscriminatorColumn xmlColumn = xmlEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		assertNull(xmlColumn.getSpecifiedDiscriminatorType());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set discriminator type in the resource model, verify context model updated
		entityResource.setDiscriminatorColumn(OrmFactory.eINSTANCE.createDiscriminatorColumn());
		entityResource.getDiscriminatorColumn().setDiscriminatorType(org.eclipse.jpt.core.resource.orm.DiscriminatorType.STRING);
		ormResource().save(null);
		assertEquals(DiscriminatorType.STRING, xmlColumn.getSpecifiedDiscriminatorType());
		assertEquals(org.eclipse.jpt.core.resource.orm.DiscriminatorType.STRING, entityResource.getDiscriminatorColumn().getDiscriminatorType());
	
		//set discriminator type to null in the resource model
		entityResource.getDiscriminatorColumn().setDiscriminatorType(null);
		assertNull(xmlColumn.getSpecifiedDiscriminatorType());
		assertNull(entityResource.getDiscriminatorColumn().getDiscriminatorType());
		
		entityResource.getDiscriminatorColumn().setDiscriminatorType(org.eclipse.jpt.core.resource.orm.DiscriminatorType.CHAR);
		assertEquals(DiscriminatorType.CHAR, xmlColumn.getSpecifiedDiscriminatorType());
		assertEquals(org.eclipse.jpt.core.resource.orm.DiscriminatorType.CHAR, entityResource.getDiscriminatorColumn().getDiscriminatorType());

		entityResource.setDiscriminatorColumn(null);
		assertNull(xmlColumn.getSpecifiedDiscriminatorType());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
	public void testModifySpecifiedDiscriminatorType() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		GenericOrmDiscriminatorColumn xmlColumn = xmlEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		assertNull(xmlColumn.getSpecifiedDiscriminatorType());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set discriminator type in the context model, verify resource model modified
		xmlColumn.setSpecifiedDiscriminatorType(DiscriminatorType.STRING);
		assertEquals(DiscriminatorType.STRING, xmlColumn.getSpecifiedDiscriminatorType());
		assertEquals(org.eclipse.jpt.core.resource.orm.DiscriminatorType.STRING, entityResource.getDiscriminatorColumn().getDiscriminatorType());
		
		//set discriminator type to null in the context model
		xmlColumn.setSpecifiedDiscriminatorType(null);
		assertNull(xmlColumn.getSpecifiedDiscriminatorType());
		assertNull(entityResource.getDiscriminatorColumn());
	}
}
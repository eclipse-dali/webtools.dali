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
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.orm.XmlEntity;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.context.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class XmlPrimaryKeyJoinColumnTests extends ContextModelTestCase
{
	public XmlPrimaryKeyJoinColumnTests(String name) {
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlPrimaryKeyJoinColumn xmlPrimaryKeyJoinColumn = xmlEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		PrimaryKeyJoinColumn primaryKeyJoinColumnResource = entityResource.getPrimaryKeyJoinColumns().get(0);
		
		assertNull(xmlPrimaryKeyJoinColumn.getSpecifiedName());
		assertNull(primaryKeyJoinColumnResource.getName());
		
		//set name in the resource model, verify context model updated
		primaryKeyJoinColumnResource.setName("FOO");
		ormResource().save(null);
		assertEquals("FOO", xmlPrimaryKeyJoinColumn.getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumnResource.getName());
	
		//set name to null in the resource model
		primaryKeyJoinColumnResource.setName(null);
		ormResource().save(null);
		assertNull(xmlPrimaryKeyJoinColumn.getSpecifiedName());
		assertNull(primaryKeyJoinColumnResource.getName());
		
		primaryKeyJoinColumnResource.setName("FOO");
		assertEquals("FOO", xmlPrimaryKeyJoinColumn.getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumnResource.getName());

		entityResource.getPrimaryKeyJoinColumns().remove(0);
		ormResource().save(null);
		assertFalse(xmlEntity.primaryKeyJoinColumns().hasNext());
		assertTrue(entityResource.getPrimaryKeyJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlPrimaryKeyJoinColumn xmlPrimaryKeyJoinColumn = xmlEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		PrimaryKeyJoinColumn primaryKeyJoinColumnResource = entityResource.getPrimaryKeyJoinColumns().get(0);

		assertNull(xmlPrimaryKeyJoinColumn.getSpecifiedName());
		assertNull(primaryKeyJoinColumnResource.getName());
		
		//set name in the context model, verify resource model modified
		xmlPrimaryKeyJoinColumn.setSpecifiedName("foo");
		assertEquals("foo", xmlPrimaryKeyJoinColumn.getSpecifiedName());
		assertEquals("foo", primaryKeyJoinColumnResource.getName());
		
		//set name to null in the context model
		xmlPrimaryKeyJoinColumn.setSpecifiedName(null);
		assertNull(xmlPrimaryKeyJoinColumn.getSpecifiedName());
		assertNull(entityResource.getPrimaryKeyJoinColumns().get(0).getName());
	}
	
	public void testUpdateSpecifiedReferencedColumnName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlPrimaryKeyJoinColumn xmlPrimaryKeyJoinColumn = xmlEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		PrimaryKeyJoinColumn primaryKeyJoinColumnResource = entityResource.getPrimaryKeyJoinColumns().get(0);
		
		assertNull(xmlPrimaryKeyJoinColumn.getSpecifiedReferencedColumnName());
		assertNull(primaryKeyJoinColumnResource.getReferencedColumnName());
		
		//set name in the resource model, verify context model updated
		primaryKeyJoinColumnResource.setReferencedColumnName("FOO");
		ormResource().save(null);
		assertEquals("FOO", xmlPrimaryKeyJoinColumn.getSpecifiedReferencedColumnName());
		assertEquals("FOO", primaryKeyJoinColumnResource.getReferencedColumnName());
	
		//set name to null in the resource model
		primaryKeyJoinColumnResource.setReferencedColumnName(null);
		assertNull(xmlPrimaryKeyJoinColumn.getSpecifiedReferencedColumnName());
		assertNull(primaryKeyJoinColumnResource.getReferencedColumnName());
		
		primaryKeyJoinColumnResource.setReferencedColumnName("FOO");
		assertEquals("FOO", xmlPrimaryKeyJoinColumn.getSpecifiedReferencedColumnName());
		assertEquals("FOO", primaryKeyJoinColumnResource.getReferencedColumnName());

		entityResource.getPrimaryKeyJoinColumns().remove(0);
		assertFalse(xmlEntity.primaryKeyJoinColumns().hasNext());
		assertTrue(entityResource.getPrimaryKeyJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedReferencedColumnName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlPrimaryKeyJoinColumn xmlPrimaryKeyJoinColumn = xmlEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		PrimaryKeyJoinColumn primaryKeyJoinColumnResource = entityResource.getPrimaryKeyJoinColumns().get(0);

		assertNull(xmlPrimaryKeyJoinColumn.getSpecifiedReferencedColumnName());
		assertNull(primaryKeyJoinColumnResource.getReferencedColumnName());
		
		//set name in the context model, verify resource model modified
		xmlPrimaryKeyJoinColumn.setSpecifiedReferencedColumnName("foo");
		assertEquals("foo", xmlPrimaryKeyJoinColumn.getSpecifiedReferencedColumnName());
		assertEquals("foo", primaryKeyJoinColumnResource.getReferencedColumnName());
		
		//set name to null in the context model
		xmlPrimaryKeyJoinColumn.setSpecifiedReferencedColumnName(null);
		assertNull(xmlPrimaryKeyJoinColumn.getSpecifiedReferencedColumnName());
		assertNull(entityResource.getPrimaryKeyJoinColumns().get(0).getReferencedColumnName());
	}

//	public void testUpdateDefaultNameFromJavaTable() throws Exception {
//		createTestEntity();
//		
//		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
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
//		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
//		assertEquals("Foo", xmlEntity.getTable().getDefaultName());
//	}
//	
//	public void testUpdateDefaultNameFromParent() throws Exception {
//		createTestEntity();
//		createTestSubType();
//		
//		XmlPersistentType parentXmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlPersistentType childXmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
//		XmlEntity parentXmlEntity = (XmlEntity) parentXmlPersistentType.getMapping();
//		XmlEntity childXmlEntity = (XmlEntity) childXmlPersistentType.getMapping();
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
	

	public void testUpdateSpecifiedColumnDefinition() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlPrimaryKeyJoinColumn xmlPrimaryKeyJoinColumn = xmlEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		PrimaryKeyJoinColumn primaryKeyJoinColumnResource = entityResource.getPrimaryKeyJoinColumns().get(0);
		
		assertNull(xmlPrimaryKeyJoinColumn.getColumnDefinition());
		assertNull(primaryKeyJoinColumnResource.getColumnDefinition());
		
		//set name in the resource model, verify context model updated
		primaryKeyJoinColumnResource.setColumnDefinition("FOO");
		assertEquals("FOO", xmlPrimaryKeyJoinColumn.getColumnDefinition());
		assertEquals("FOO", primaryKeyJoinColumnResource.getColumnDefinition());
	
		//set name to null in the resource model
		primaryKeyJoinColumnResource.setColumnDefinition(null);
		assertNull(xmlPrimaryKeyJoinColumn.getColumnDefinition());
		assertNull(primaryKeyJoinColumnResource.getColumnDefinition());
		
		primaryKeyJoinColumnResource.setColumnDefinition("FOO");
		assertEquals("FOO", xmlPrimaryKeyJoinColumn.getColumnDefinition());
		assertEquals("FOO", primaryKeyJoinColumnResource.getColumnDefinition());

		entityResource.getPrimaryKeyJoinColumns().remove(0);
		assertFalse(xmlEntity.primaryKeyJoinColumns().hasNext());
		assertTrue(entityResource.getPrimaryKeyJoinColumns().isEmpty());
	}
	
	public void testModifySpecifiedColumnDefinition() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlPrimaryKeyJoinColumn xmlPrimaryKeyJoinColumn = xmlEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		PrimaryKeyJoinColumn primaryKeyJoinColumnResource = entityResource.getPrimaryKeyJoinColumns().get(0);

		assertNull(xmlPrimaryKeyJoinColumn.getColumnDefinition());
		assertNull(primaryKeyJoinColumnResource.getColumnDefinition());
		
		//set name in the context model, verify resource model modified
		xmlPrimaryKeyJoinColumn.setColumnDefinition("foo");
		assertEquals("foo", xmlPrimaryKeyJoinColumn.getColumnDefinition());
		assertEquals("foo", primaryKeyJoinColumnResource.getColumnDefinition());
		
		//set name to null in the context model
		xmlPrimaryKeyJoinColumn.setColumnDefinition(null);
		assertNull(xmlPrimaryKeyJoinColumn.getColumnDefinition());
		assertNull(entityResource.getPrimaryKeyJoinColumns().get(0).getColumnDefinition());
	}
	

}
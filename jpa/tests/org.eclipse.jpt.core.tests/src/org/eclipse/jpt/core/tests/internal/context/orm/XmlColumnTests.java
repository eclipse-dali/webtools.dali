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
import org.eclipse.jpt.core.internal.context.orm.XmlBasicMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlColumn;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.orm.Basic;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class XmlColumnTests extends ContextModelTestCase
{
	public XmlColumnTests(String name) {
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
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedName());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createColumn());
		basic.getColumn().setName("FOO");
		ormResource().save(null);
		assertEquals("FOO", xmlColumn.getSpecifiedName());
		assertEquals("FOO", basic.getColumn().getName());
	
		//set name to null in the resource model
		basic.getColumn().setName(null);
		assertNull(xmlColumn.getSpecifiedName());
		assertNull(basic.getColumn().getName());
		
		basic.getColumn().setName("FOO");
		assertEquals("FOO", xmlColumn.getSpecifiedName());
		assertEquals("FOO", basic.getColumn().getName());

		basic.setColumn(null);
		assertNull(xmlColumn.getSpecifiedName());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedName());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		xmlColumn.setSpecifiedName("foo");
		assertEquals("foo", xmlColumn.getSpecifiedName());
		assertEquals("foo", basic.getColumn().getName());
		
		//set name to null in the context model
		xmlColumn.setSpecifiedName(null);
		assertNull(xmlColumn.getSpecifiedName());
		assertNull(basic.getColumn());
	}
	
	public void testUpdateColumnDefinition() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getColumnDefinition());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createColumn());
		basic.getColumn().setColumnDefinition("FOO");
		ormResource().save(null);
		assertEquals("FOO", xmlColumn.getColumnDefinition());
		assertEquals("FOO", basic.getColumn().getColumnDefinition());
	
		//set name to null in the resource model
		basic.getColumn().setColumnDefinition(null);
		assertNull(xmlColumn.getColumnDefinition());
		assertNull(basic.getColumn().getColumnDefinition());
		
		basic.getColumn().setColumnDefinition("FOO");
		assertEquals("FOO", xmlColumn.getColumnDefinition());
		assertEquals("FOO", basic.getColumn().getColumnDefinition());

		basic.setColumn(null);
		assertNull(xmlColumn.getColumnDefinition());
		assertNull(basic.getColumn());
	}
	
	public void testModifyColumnDefinition() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getColumnDefinition());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		xmlColumn.setColumnDefinition("foo");
		assertEquals("foo", xmlColumn.getColumnDefinition());
		assertEquals("foo", basic.getColumn().getColumnDefinition());
		
		//set name to null in the context model
		xmlColumn.setColumnDefinition(null);
		assertNull(xmlColumn.getColumnDefinition());
		assertNull(basic.getColumn());
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
	
	public void testUpdateSpecifiedTable() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedTable());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createColumn());
		basic.getColumn().setTable("FOO");
		assertEquals("FOO", xmlColumn.getSpecifiedTable());
		assertEquals("FOO", basic.getColumn().getTable());
	
		//set name to null in the resource model
		basic.getColumn().setTable(null);
		assertNull(xmlColumn.getSpecifiedTable());
		assertNull(basic.getColumn().getTable());
		
		basic.getColumn().setTable("FOO");
		assertEquals("FOO", xmlColumn.getSpecifiedTable());
		assertEquals("FOO", basic.getColumn().getTable());

		basic.setColumn(null);
		assertNull(xmlColumn.getSpecifiedTable());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedTable() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedTable());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		xmlColumn.setSpecifiedTable("foo");
		assertEquals("foo", xmlColumn.getSpecifiedTable());
		assertEquals("foo", basic.getColumn().getTable());
		
		//set name to null in the context model
		xmlColumn.setSpecifiedTable(null);
		assertNull(xmlColumn.getSpecifiedTable());
		assertNull(basic.getColumn());
	}

	public void testUpdateSpecifiedNullable() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedNullable());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createColumn());
		basic.getColumn().setNullable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, xmlColumn.getSpecifiedNullable());
		assertEquals(Boolean.TRUE, basic.getColumn().getNullable());
	
		//set name to null in the resource model
		basic.getColumn().setNullable(null);
		assertNull(xmlColumn.getSpecifiedNullable());
		assertNull(basic.getColumn().getNullable());
		
		basic.getColumn().setNullable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, basic.getColumn().getNullable());

		basic.setColumn(null);
		assertNull(xmlColumn.getSpecifiedNullable());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedNullable() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedNullable());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		xmlColumn.setSpecifiedNullable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, basic.getColumn().getNullable());
		
		//set name to null in the context model
		xmlColumn.setSpecifiedNullable(null);
		assertNull(xmlColumn.getSpecifiedNullable());
		assertNull(basic.getColumn());
	}

	public void testUpdateSpecifiedUpdatable() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedUpdatable());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createColumn());
		basic.getColumn().setUpdatable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, xmlColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.TRUE, basic.getColumn().getUpdatable());
	
		//set name to null in the resource model
		basic.getColumn().setUpdatable(null);
		assertNull(xmlColumn.getSpecifiedUpdatable());
		assertNull(basic.getColumn().getUpdatable());
		
		basic.getColumn().setUpdatable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.FALSE, basic.getColumn().getUpdatable());

		basic.setColumn(null);
		assertNull(xmlColumn.getSpecifiedUpdatable());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedUpdatable() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedUpdatable());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		xmlColumn.setSpecifiedUpdatable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.FALSE, basic.getColumn().getUpdatable());
		
		//set name to null in the context model
		xmlColumn.setSpecifiedUpdatable(null);
		assertNull(xmlColumn.getSpecifiedUpdatable());
		assertNull(basic.getColumn());
	}

	public void testUpdateSpecifiedInsertable() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedInsertable());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createColumn());
		basic.getColumn().setInsertable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, xmlColumn.getSpecifiedInsertable());
		assertEquals(Boolean.TRUE, basic.getColumn().getInsertable());
	
		//set name to null in the resource model
		basic.getColumn().setInsertable(null);
		assertNull(xmlColumn.getSpecifiedInsertable());
		assertNull(basic.getColumn().getInsertable());
		
		basic.getColumn().setInsertable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, basic.getColumn().getInsertable());

		basic.setColumn(null);
		assertNull(xmlColumn.getSpecifiedInsertable());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedInsertable() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedInsertable());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		xmlColumn.setSpecifiedInsertable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, basic.getColumn().getInsertable());
		
		//set name to null in the context model
		xmlColumn.setSpecifiedInsertable(null);
		assertNull(xmlColumn.getSpecifiedInsertable());
		assertNull(basic.getColumn());
	}
	
	public void testUpdateSpecifiedUnique() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedUnique());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createColumn());
		basic.getColumn().setUnique(Boolean.TRUE);
		assertEquals(Boolean.TRUE, xmlColumn.getSpecifiedUnique());
		assertEquals(Boolean.TRUE, basic.getColumn().getUnique());
	
		//set name to null in the resource model
		basic.getColumn().setUnique(null);
		assertNull(xmlColumn.getSpecifiedUnique());
		assertNull(basic.getColumn().getUnique());
		
		basic.getColumn().setUnique(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, basic.getColumn().getUnique());

		basic.setColumn(null);
		assertNull(xmlColumn.getSpecifiedUnique());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedUnique() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedUnique());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		xmlColumn.setSpecifiedUnique(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, basic.getColumn().getUnique());
		
		//set name to null in the context model
		xmlColumn.setSpecifiedUnique(null);
		assertNull(xmlColumn.getSpecifiedUnique());
		assertNull(basic.getColumn());
	}
	
	public void testUpdateSpecifiedLength() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createColumn());
		basic.getColumn().setLength(Integer.valueOf(8));
		assertEquals(Integer.valueOf(8), xmlColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(8), basic.getColumn().getLength());
	
		//set name to null in the resource model
		basic.getColumn().setLength(null);
		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(basic.getColumn().getLength());
		
		basic.getColumn().setLength(Integer.valueOf(11));
		assertEquals(Integer.valueOf(11), xmlColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(11), basic.getColumn().getLength());

		basic.setColumn(null);
		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedLength() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		xmlColumn.setSpecifiedLength(Integer.valueOf(7));
		assertEquals(Integer.valueOf(7), xmlColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(7), basic.getColumn().getLength());
		
		//set name to null in the context model
		xmlColumn.setSpecifiedLength(null);
		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
	}
	
	public void testUpdateSpecifiedPrecision() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createColumn());
		basic.getColumn().setPrecision(Integer.valueOf(8));
		assertEquals(Integer.valueOf(8), xmlColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(8), basic.getColumn().getPrecision());
	
		//set name to null in the resource model
		basic.getColumn().setPrecision(null);
		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(basic.getColumn().getPrecision());
		
		basic.getColumn().setPrecision(Integer.valueOf(11));
		assertEquals(Integer.valueOf(11), xmlColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(11), basic.getColumn().getPrecision());

		basic.setColumn(null);
		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedPrecision() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		xmlColumn.setSpecifiedPrecision(Integer.valueOf(7));
		assertEquals(Integer.valueOf(7), xmlColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(7), basic.getColumn().getPrecision());
		
		//set name to null in the context model
		xmlColumn.setSpecifiedPrecision(null);
		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
	}
	
	public void testUpdateSpecifiedScale() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createColumn());
		basic.getColumn().setScale(Integer.valueOf(8));
		assertEquals(Integer.valueOf(8), xmlColumn.getSpecifiedScale());
		assertEquals(Integer.valueOf(8), basic.getColumn().getScale());
	
		//set name to null in the resource model
		basic.getColumn().setScale(null);
		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(basic.getColumn().getScale());
		
		basic.getColumn().setScale(Integer.valueOf(11));
		assertEquals(Integer.valueOf(11), xmlColumn.getSpecifiedScale());
		assertEquals(Integer.valueOf(11), basic.getColumn().getScale());

		basic.setColumn(null);
		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedScale() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		XmlBasicMapping xmlBasicMapping = (XmlBasicMapping) xmlPersistentAttribute.getMapping();
		XmlColumn xmlColumn = xmlBasicMapping.getColumn();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		Basic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		xmlColumn.setSpecifiedScale(Integer.valueOf(7));
		assertEquals(Integer.valueOf(7), xmlColumn.getSpecifiedScale());
		assertEquals(Integer.valueOf(7), basic.getColumn().getScale());
		
		//set name to null in the context model
		xmlColumn.setSpecifiedScale(null);
		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
	}
}
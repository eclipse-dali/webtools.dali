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
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.base.InheritanceType;
import org.eclipse.jpt.core.internal.context.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.internal.context.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.internal.context.orm.XmlEmbeddable;
import org.eclipse.jpt.core.internal.context.orm.XmlEntity;
import org.eclipse.jpt.core.internal.context.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.internal.context.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.core.internal.context.orm.XmlNamedQuery;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.context.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.orm.XmlSecondaryTable;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.orm.Embeddable;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class XmlEntityTests extends ContextModelTestCase
{
	public XmlEntityTests(String name) {
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
	
	private IType createTestEntityDefaultFieldAccess() throws Exception {
		createEntityAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}
	
	private IType createTestEntityFieldAccess() throws Exception {
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
	
	private IType createTestEntityPropertyAccess() throws Exception {
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
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
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
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedName());
		assertNull(entityResource.getName());
		
		//set name in the resource model, verify context model updated
		entityResource.setName("foo");
		assertEquals("foo", xmlEntity.getSpecifiedName());
		assertEquals("foo", entityResource.getName());
	
		//set name to null in the resource model
		entityResource.setName(null);
		assertNull(xmlEntity.getSpecifiedName());
		assertNull(entityResource.getName());
	}
	
	public void testModifySpecifiedName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedName());
		assertNull(entityResource.getName());
		
		//set name in the context model, verify resource model modified
		xmlEntity.setSpecifiedName("foo");
		assertEquals("foo", xmlEntity.getSpecifiedName());
		assertEquals("foo", entityResource.getName());
		
		//set name to null in the context model
		xmlEntity.setSpecifiedName(null);
		assertNull(xmlEntity.getSpecifiedName());
		assertNull(entityResource.getName());
	}

	public void testUpdateDefaultName() throws Exception {
		createTestEntityFieldAccess();
		
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(TYPE_NAME, xmlEntity.getDefaultName());
		
		xmlEntity.javaEntity().setSpecifiedName("Foo");
		//xml default name is not affect by what is specified in java
		assertEquals(TYPE_NAME, xmlEntity.getDefaultName());
		
		//set class in the resource model, verify context model updated
		entityResource.setClassName("com.Bar");
		assertEquals("Bar", xmlEntity.getDefaultName());
		
		//set class to null in the resource model
		entityResource.setClassName(null);
		assertNull(xmlEntity.getDefaultName());
	}
	
	public void testUpdateName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals("Foo", xmlEntity.getName());
		
		//set class in the resource model, verify context model updated
		entityResource.setClassName("com.Bar");
		assertEquals("Bar", xmlEntity.getName());
		
		entityResource.setName("Baz");
		assertEquals("Baz", xmlEntity.getName());
		
		//set class to null in the resource model
		entityResource.setClassName(null);
		assertEquals("Baz", xmlEntity.getName());
		
		entityResource.setName(null);
		assertNull(xmlEntity.getName());
	}

	public void testUpdateClass() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals("model.Foo", xmlEntity.getClass_());
		assertEquals("model.Foo", entityResource.getClassName());
		
		//set class in the resource model, verify context model updated
		entityResource.setClassName("com.Bar");
		assertEquals("com.Bar", xmlEntity.getClass_());
		assertEquals("com.Bar", entityResource.getClassName());
	
		//set class to null in the resource model
		entityResource.setClassName(null);
		assertNull(xmlEntity.getClass_());
		assertNull(entityResource.getClassName());
	}
	
	public void testModifyClass() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals("model.Foo", xmlEntity.getClass_());
		assertEquals("model.Foo", entityResource.getClassName());
		
		//set class in the context model, verify resource model modified
		xmlEntity.setClass("com.Bar");
		assertEquals("com.Bar", xmlEntity.getClass_());
		assertEquals("com.Bar", entityResource.getClassName());
		
		//set class to null in the context model
		xmlEntity.setClass(null);
		assertNull(xmlEntity.getClass_());
		assertNull(entityResource.getClassName());
	}
	//TODO add tests for setting the className when the package is set on entity-mappings
	
	public void testUpdateSpecifiedAccess() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
		
		//set access in the resource model, verify context model updated
		entityResource.setAccess(org.eclipse.jpt.core.internal.resource.orm.AccessType.FIELD);
		ormResource().save(null);
		assertEquals(AccessType.FIELD, xmlEntity.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.FIELD, entityResource.getAccess());
	
		//set access to null in the resource model
		entityResource.setAccess(null);
		assertNull(xmlEntity.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
	}
	
	public void testModifySpecifiedAccess() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
		
		//set access in the context model, verify resource model modified
		xmlEntity.setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, xmlEntity.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, entityResource.getAccess());
		
		//set access to null in the context model
		xmlEntity.setSpecifiedAccess(null);
		assertNull(xmlEntity.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
	}
	
	public void testUpdateDefaultAccessFromPersistenceUnitDefaults() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedAccess());
		assertNull(xmlEntity.getDefaultAccess());
		assertNull(entityResource.getAccess());
		
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createPersistenceUnitDefaults());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(org.eclipse.jpt.core.internal.resource.orm.AccessType.FIELD);
		assertEquals(AccessType.FIELD, xmlEntity.getDefaultAccess());
		assertNull(xmlEntity.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, xmlEntity.getDefaultAccess());
		assertNull(xmlEntity.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(null);
		assertNull(xmlEntity.getSpecifiedAccess());
		assertNull(xmlEntity.getDefaultAccess());
		assertNull(entityResource.getAccess());
	}
	
	public void testUpdateDefaultAccessFromJava() throws Exception {
		createTestEntityDefaultFieldAccess();

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		//java has no annotations, so defaultAccess in xml is null
		assertNull(xmlEntity.getDefaultAccess());
		
		entityMappings().setSpecifiedAccess(AccessType.FIELD);
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);		
		//entityMappings access wins over persistence-unit-defaults access
		assertEquals(AccessType.FIELD, xmlEntity.getDefaultAccess());
		
		entityMappings().setSpecifiedAccess(null);		
		//persistence-unit-defaults access used now
		assertEquals(AccessType.PROPERTY, xmlEntity.getDefaultAccess());
		
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(null);
		assertNull(xmlEntity.getDefaultAccess());
		
		xmlPersistentType.javaPersistentType().attributeNamed("id").setSpecifiedMappingKey(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		//java has annotations on fields now, that should win in all cases
		assertEquals(AccessType.FIELD, xmlEntity.getDefaultAccess());
		
		entityMappings().setSpecifiedAccess(AccessType.PROPERTY);
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);
		assertEquals(AccessType.FIELD, xmlEntity.getDefaultAccess());

		xmlPersistentType.javaPersistentType().attributeNamed("id").setSpecifiedMappingKey(IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertEquals(AccessType.PROPERTY, xmlEntity.getDefaultAccess());
	}

	public void testUpdateDefaultAccessFromJavaFieldAccess() throws Exception {
		createTestEntityFieldAccess();

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		assertEquals(AccessType.FIELD, xmlEntity.getDefaultAccess());
		
		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertNull(xmlEntity.getDefaultAccess());
		
		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(AccessType.FIELD, xmlEntity.getDefaultAccess());

		entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertNull(xmlEntity.getDefaultAccess());
		
		xmlEntity.setSpecifiedMetadataComplete(null);
		assertNull(xmlEntity.getDefaultAccess());
		
		entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertEquals(AccessType.FIELD, xmlEntity.getDefaultAccess());
	}
	
	public void testUpdateDefaultAccessFromJavaPropertyAccess() throws Exception {
		createTestEntityPropertyAccess();

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		assertEquals(AccessType.PROPERTY, xmlEntity.getDefaultAccess());
		
		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertNull(xmlEntity.getDefaultAccess());
		
		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(AccessType.PROPERTY, xmlEntity.getDefaultAccess());

		entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertNull(xmlEntity.getDefaultAccess());
		
		xmlEntity.setSpecifiedMetadataComplete(null);
		assertNull(xmlEntity.getDefaultAccess());
		
		entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertEquals(AccessType.PROPERTY, xmlEntity.getDefaultAccess());
	}
	
	public void testUpdateDefaultAccessNoUnderlyingJava() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		assertNull(xmlEntity.getDefaultAccess());
	}
		
	public void testUpdateSpecifiedMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		//set metadata-complete in the resource model, verify context model updated
		entityResource.setMetadataComplete(true);
		ormResource().save(null);
		assertTrue(xmlEntity.getSpecifiedMetadataComplete());
		assertTrue(entityResource.getMetadataComplete());
	
		//set access to false in the resource model
		entityResource.setMetadataComplete(false);
		ormResource().save(null);
		assertFalse(xmlEntity.getSpecifiedMetadataComplete());
		assertFalse(entityResource.getMetadataComplete());
		
		entityResource.setMetadataComplete(null);
		ormResource().save(null);
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
	}
	
	public void testModifySpecifiedMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		//set access in the context model, verify resource model modified
		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertTrue(xmlEntity.getSpecifiedMetadataComplete());
		assertTrue(entityResource.getMetadataComplete());
		
		//set access to null in the context model
		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertFalse(xmlEntity.getSpecifiedMetadataComplete());
		assertFalse(entityResource.getMetadataComplete());
		
		xmlEntity.setSpecifiedMetadataComplete(null);
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
	}
	
	public void testUpdateDefaultMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertFalse(xmlEntity.isDefaultMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(xmlEntity.isDefaultMetadataComplete());
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertFalse(xmlEntity.isDefaultMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		xmlEntity.setSpecifiedMetadataComplete(false);
		assertFalse(xmlEntity.getSpecifiedMetadataComplete());
		assertTrue(xmlEntity.isDefaultMetadataComplete());
		assertTrue(xmlEntity.isMetadataComplete());
	}

	public void testUpdateMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertFalse(xmlEntity.isMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(xmlEntity.isMetadataComplete());
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertFalse(xmlEntity.isMetadataComplete());
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
	}
	
	
	public void testUpdateInheritanceStrategy() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(InheritanceType.SINGLE_TABLE, xmlEntity.getInheritanceStrategy());
		assertNull(entityResource.getInheritance());

		//set inheritance strategy in the resource model, verify context model updated
		entityResource.setInheritance(OrmFactory.eINSTANCE.createInheritance());
		entityResource.getInheritance().setStrategy(org.eclipse.jpt.core.internal.resource.orm.InheritanceType.TABLE_PER_CLASS);
		
		assertEquals(InheritanceType.TABLE_PER_CLASS, xmlEntity.getInheritanceStrategy());		
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.InheritanceType.TABLE_PER_CLASS, entityResource.getInheritance().getStrategy());		
	}
	
	public void testUpdateDefaultInheritanceStrategyFromJava() throws Exception {
		createTestEntityDefaultFieldAccess();

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		//no inheritance strategy specified in java so single-table is default
		assertEquals(InheritanceType.SINGLE_TABLE, xmlEntity.getDefaultInheritanceStrategy());
		
		xmlEntity.javaEntity().setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		assertEquals(InheritanceType.JOINED, xmlEntity.getDefaultInheritanceStrategy());
			
		xmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		ormResource().save(null);
		//inheritance tag exists in xml, so it overrides anything in java
		assertEquals(InheritanceType.SINGLE_TABLE, xmlEntity.getDefaultInheritanceStrategy());

		xmlEntity.setSpecifiedInheritanceStrategy(null);		
		ormResource().save(null);
		assertEquals(InheritanceType.JOINED, xmlEntity.getDefaultInheritanceStrategy());

		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		ormResource().save(null);
		assertEquals(InheritanceType.SINGLE_TABLE, xmlEntity.getDefaultInheritanceStrategy());
		
		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(InheritanceType.JOINED, xmlEntity.getDefaultInheritanceStrategy());
		
		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		//this setting overrides the false meta-data complete found on xmlEntity
		assertEquals(InheritanceType.SINGLE_TABLE, xmlEntity.getDefaultInheritanceStrategy());
	}
	
	public void testUpdateDefaultInheritanceStrategyFromParent() throws Exception {
		createTestEntityDefaultFieldAccess();
		createTestSubType();
	
		XmlPersistentType parentPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentType childPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		XmlEntity parentXmlEntity = (XmlEntity) parentPersistentType.getMapping();
		XmlEntity childXmlEntity = (XmlEntity) childPersistentType.getMapping();
		
		assertEquals(parentXmlEntity, childXmlEntity.parentEntity());
		assertEquals(InheritanceType.SINGLE_TABLE, childXmlEntity.getDefaultInheritanceStrategy());
		
		//change root inheritance strategy, verify default is changed for child entity
		parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		assertEquals(InheritanceType.SINGLE_TABLE, parentXmlEntity.getDefaultInheritanceStrategy());
		assertEquals(InheritanceType.TABLE_PER_CLASS, childXmlEntity.getDefaultInheritanceStrategy());
		assertNull(childXmlEntity.getSpecifiedInheritanceStrategy());

		//set specified inheritance strategy in java and verify defaults in xml are correct
		parentXmlEntity.setSpecifiedInheritanceStrategy(null);
		parentXmlEntity.javaEntity().setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		assertEquals(InheritanceType.JOINED, parentXmlEntity.getDefaultInheritanceStrategy());
		assertEquals(InheritanceType.JOINED, childXmlEntity.getDefaultInheritanceStrategy());
		assertNull(parentXmlEntity.getSpecifiedInheritanceStrategy());
		assertNull(childXmlEntity.getSpecifiedInheritanceStrategy());
		
		parentPersistentType.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertEquals(InheritanceType.SINGLE_TABLE, parentXmlEntity.getDefaultInheritanceStrategy());
		assertEquals(InheritanceType.SINGLE_TABLE, childXmlEntity.getDefaultInheritanceStrategy());
	}

	public void testUpdateSpecifiedInheritanceStrategy() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedInheritanceStrategy());
		assertNull(entityResource.getInheritance());
		
		//set strategy in the resource model, verify context model updated
		entityResource.setInheritance(OrmFactory.eINSTANCE.createInheritance());
		entityResource.getInheritance().setStrategy(org.eclipse.jpt.core.internal.resource.orm.InheritanceType.JOINED);
		assertEquals(InheritanceType.JOINED, xmlEntity.getSpecifiedInheritanceStrategy());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.InheritanceType.JOINED, entityResource.getInheritance().getStrategy());
	
		//set strategy to null in the resource model
		entityResource.getInheritance().setStrategy(null);
		assertNull(xmlEntity.getSpecifiedInheritanceStrategy());
		assertNull(entityResource.getInheritance().getStrategy());
		
		entityResource.getInheritance().setStrategy(org.eclipse.jpt.core.internal.resource.orm.InheritanceType.SINGLE_TABLE);
		assertEquals(InheritanceType.SINGLE_TABLE, xmlEntity.getSpecifiedInheritanceStrategy());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.InheritanceType.SINGLE_TABLE, entityResource.getInheritance().getStrategy());

		entityResource.setInheritance(null);
		assertNull(xmlEntity.getSpecifiedInheritanceStrategy());
		assertNull(entityResource.getInheritance());
	}
	
	public void testModifySpecifiedInheritanceStrategy() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedInheritanceStrategy());
		assertNull(entityResource.getInheritance());
		
		//set strategy in the context model, verify resource model modified
		xmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		assertEquals(InheritanceType.TABLE_PER_CLASS, xmlEntity.getSpecifiedInheritanceStrategy());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.InheritanceType.TABLE_PER_CLASS, entityResource.getInheritance().getStrategy());
		
		//set strategy to null in the context model
		xmlEntity.setSpecifiedInheritanceStrategy(null);
		assertNull(xmlEntity.getSpecifiedInheritanceStrategy());
		assertNull(entityResource.getInheritance());	
	}
	
	public void testAddSpecifiedSecondaryTable() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		XmlSecondaryTable secondaryTable = xmlEntity.addSpecifiedSecondaryTable(0);
		ormResource().save(null);
		secondaryTable.setSpecifiedName("FOO");
		ormResource().save(null);
				
		assertEquals("FOO", entityResource.getSecondaryTables().get(0).getName());
		
		XmlSecondaryTable secondaryTable2 = xmlEntity.addSpecifiedSecondaryTable(0);
		ormResource().save(null);
		secondaryTable2.setSpecifiedName("BAR");
		ormResource().save(null);
		
		assertEquals("BAR", entityResource.getSecondaryTables().get(0).getName());
		assertEquals("FOO", entityResource.getSecondaryTables().get(1).getName());
		
		XmlSecondaryTable secondaryTable3 = xmlEntity.addSpecifiedSecondaryTable(1);
		ormResource().save(null);
		secondaryTable3.setSpecifiedName("BAZ");
		ormResource().save(null);
		
		assertEquals("BAR", entityResource.getSecondaryTables().get(0).getName());
		assertEquals("BAZ", entityResource.getSecondaryTables().get(1).getName());
		assertEquals("FOO", entityResource.getSecondaryTables().get(2).getName());
		
		ListIterator<XmlSecondaryTable> secondaryTables = xmlEntity.specifiedSecondaryTables();
		assertEquals(secondaryTable2, secondaryTables.next());
		assertEquals(secondaryTable3, secondaryTables.next());
		assertEquals(secondaryTable, secondaryTables.next());
		
		secondaryTables = xmlEntity.specifiedSecondaryTables();
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
	}
	
	public void testRemoveSpecifiedSecondaryTable() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();

		xmlEntity.addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		xmlEntity.addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		xmlEntity.addSpecifiedSecondaryTable(2).setSpecifiedName("BAZ");
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getSecondaryTables().size());
		
		xmlEntity.removeSpecifiedSecondaryTable(0);
		assertEquals(2, entityResource.getSecondaryTables().size());
		assertEquals("BAR", entityResource.getSecondaryTables().get(0).getName());
		assertEquals("BAZ", entityResource.getSecondaryTables().get(1).getName());

		xmlEntity.removeSpecifiedSecondaryTable(0);
		assertEquals(1, entityResource.getSecondaryTables().size());
		assertEquals("BAZ", entityResource.getSecondaryTables().get(0).getName());
		
		xmlEntity.removeSpecifiedSecondaryTable(0);
		assertEquals(0, entityResource.getSecondaryTables().size());
	}
	
	public void testMoveSpecifiedSecondaryTable() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();

		xmlEntity.addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		xmlEntity.addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		xmlEntity.addSpecifiedSecondaryTable(2).setSpecifiedName("BAZ");
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getSecondaryTables().size());
		
		
		xmlEntity.moveSpecifiedSecondaryTable(2, 0);
		ListIterator<XmlSecondaryTable> secondaryTables = xmlEntity.specifiedSecondaryTables();
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());

		assertEquals("BAR", entityResource.getSecondaryTables().get(0).getName());
		assertEquals("BAZ", entityResource.getSecondaryTables().get(1).getName());
		assertEquals("FOO", entityResource.getSecondaryTables().get(2).getName());


		xmlEntity.moveSpecifiedSecondaryTable(0, 1);
		secondaryTables = xmlEntity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());

		assertEquals("BAZ", entityResource.getSecondaryTables().get(0).getName());
		assertEquals("BAR", entityResource.getSecondaryTables().get(1).getName());
		assertEquals("FOO", entityResource.getSecondaryTables().get(2).getName());
	}
	
	public void testUpdateSecondaryTables() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createSecondaryTable());
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createSecondaryTable());
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createSecondaryTable());
		
		entityResource.getSecondaryTables().get(0).setName("FOO");
		entityResource.getSecondaryTables().get(1).setName("BAR");
		entityResource.getSecondaryTables().get(2).setName("BAZ");

		ListIterator<XmlSecondaryTable> secondaryTables = xmlEntity.specifiedSecondaryTables();
		assertEquals("FOO", secondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
		
		entityResource.getSecondaryTables().move(2, 0);
		secondaryTables = xmlEntity.specifiedSecondaryTables();
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());

		entityResource.getSecondaryTables().move(0, 1);
		secondaryTables = xmlEntity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());

		entityResource.getSecondaryTables().remove(1);
		secondaryTables = xmlEntity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());

		entityResource.getSecondaryTables().remove(1);
		secondaryTables = xmlEntity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
		
		entityResource.getSecondaryTables().remove(0);
		assertFalse(xmlEntity.specifiedSecondaryTables().hasNext());
	}
	
	//test adding 2 secondary tables to java entity
	//override one in xmlEntity, verify other one still exists as a default
	//change xml-mapping-metadata complete setting in both locations and verify defaults from java are gone
	public void testDefaultSecondaryTables() throws Exception {
		createTestEntityDefaultFieldAccess();
		createTestSubType();
	
		XmlPersistentType parentPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentType childPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		XmlEntity parentXmlEntity = (XmlEntity) parentPersistentType.getMapping();
		XmlEntity childXmlEntity = (XmlEntity) childPersistentType.getMapping();
		
		childXmlEntity.javaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		
		assertEquals("FOO", childXmlEntity.virtualSecondaryTables().next().getName());
		assertEquals("FOO", childXmlEntity.secondaryTables().next().getName());
		assertEquals(0, childXmlEntity.specifiedSecondaryTablesSize());
		assertEquals(1, childXmlEntity.virtualSecondaryTablesSize());
		assertEquals(1, childXmlEntity.secondaryTablesSize());
	
		childXmlEntity.javaEntity().addSpecifiedSecondaryTable(0).setSpecifiedName("BAR");
		ListIterator<XmlSecondaryTable> virtualSecondaryTables = childXmlEntity.virtualSecondaryTables();
		ListIterator<XmlSecondaryTable> secondaryTables = childXmlEntity.secondaryTables();
		assertEquals("BAR", virtualSecondaryTables.next().getName());
		assertEquals("FOO", virtualSecondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertEquals(0, childXmlEntity.specifiedSecondaryTablesSize());
		assertEquals(2, childXmlEntity.virtualSecondaryTablesSize());
		assertEquals(2, childXmlEntity.secondaryTablesSize());
		
		childXmlEntity.addSpecifiedSecondaryTable(0).setSpecifiedName("BAZ");
		virtualSecondaryTables = childXmlEntity.virtualSecondaryTables();
		secondaryTables = childXmlEntity.secondaryTables();
		assertFalse(virtualSecondaryTables.hasNext());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals(1, childXmlEntity.specifiedSecondaryTablesSize());
		assertEquals(0, childXmlEntity.virtualSecondaryTablesSize());
		assertEquals(1, childXmlEntity.secondaryTablesSize());
		
		childXmlEntity.addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		virtualSecondaryTables = childXmlEntity.virtualSecondaryTables();
		secondaryTables = childXmlEntity.secondaryTables();
		assertFalse(virtualSecondaryTables.hasNext());
		assertEquals("FOO", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
		assertEquals(2, childXmlEntity.specifiedSecondaryTablesSize());
		assertEquals(0, childXmlEntity.virtualSecondaryTablesSize());
		assertEquals(2, childXmlEntity.secondaryTablesSize());
		
		//add a specified secondary table to the parent, this will not affect virtual secondaryTables in child
		parentXmlEntity.addSpecifiedSecondaryTable(0).setSpecifiedName("PARENT_TABLE");
		virtualSecondaryTables = childXmlEntity.virtualSecondaryTables();
		secondaryTables = childXmlEntity.secondaryTables();
		assertFalse(virtualSecondaryTables.hasNext());
		assertEquals("FOO", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
		assertEquals(2, childXmlEntity.specifiedSecondaryTablesSize());
		assertEquals(0, childXmlEntity.virtualSecondaryTablesSize());
		assertEquals(2, childXmlEntity.secondaryTablesSize());
		
		childXmlEntity.removeSpecifiedSecondaryTable(0);
		childXmlEntity.removeSpecifiedSecondaryTable(0);
		virtualSecondaryTables = childXmlEntity.virtualSecondaryTables();
		secondaryTables = childXmlEntity.secondaryTables();
		assertEquals("BAR", virtualSecondaryTables.next().getName());
		assertEquals("FOO", virtualSecondaryTables.next().getName());
		assertFalse(virtualSecondaryTables.hasNext());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
		assertEquals(0, childXmlEntity.specifiedSecondaryTablesSize());
		assertEquals(2, childXmlEntity.virtualSecondaryTablesSize());
		assertEquals(2, childXmlEntity.secondaryTablesSize());
	}

	//test that inherited tables don't show up in this list
	public void testAssociatedTables() throws Exception {
		
	}
	
	public void testAssociatedTableNamesIncludingInherited() throws Exception {
		
	}
	
	public void testTableNameIsInvalid() throws Exception {
		
	}
	
	public void testMakeEntityEmbeddable() throws Exception {
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity entity = (XmlEntity) entityPersistentType.getMapping();
		entity.setSpecifiedAccess(AccessType.PROPERTY);
		entity.setSpecifiedDiscriminatorValue("DISC_VALUE");
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedMetadataComplete(Boolean.TRUE);
		entity.setSpecifiedName("ENTITY_NAME");
		ormResource().save(null);
	
		entityPersistentType.setMappingKey(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		ormResource().save(null);
		
		Embeddable embeddable = ormResource().getEntityMappings().getEmbeddables().get(0);
		assertEquals("model.Foo", embeddable.getClassName());
		assertEquals(Boolean.TRUE, embeddable.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, embeddable.getAccess());
		
		XmlEmbeddable xmlEmbeddable = (XmlEmbeddable) entityPersistentType.getMapping();
		assertEquals("model.Foo", xmlEmbeddable.getClass_());
		assertEquals(Boolean.TRUE, xmlEmbeddable.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, xmlEmbeddable.getSpecifiedAccess());
	}
	
	//TODO test that attribute mappings are not removed when changing type mapping.
	public void testMakeEntityEmbeddable2() throws Exception {
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		XmlEntity entity = (XmlEntity) entityPersistentType.getMapping();
		entity.setSpecifiedAccess(AccessType.PROPERTY);
		entity.setSpecifiedDiscriminatorValue("DISC_VALUE");
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedMetadataComplete(Boolean.TRUE);
		entity.setSpecifiedName("ENTITY_NAME");
//		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		ormResource().save(null);
	
		entityPersistentType.setMappingKey(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		ormResource().save(null);
		
		Embeddable embeddable = ormResource().getEntityMappings().getEmbeddables().get(0);
		assertEquals("model.Foo", embeddable.getClassName());
		assertEquals(Boolean.TRUE, embeddable.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, embeddable.getAccess());
//		assertEquals("basicMapping", embeddable.getAttributes().getBasics().get(0).getName());
		
		XmlEmbeddable xmlEmbeddable = (XmlEmbeddable) entityPersistentType.getMapping();
		assertEquals("model.Foo", xmlEmbeddable.getClass_());
		assertEquals(Boolean.TRUE, xmlEmbeddable.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, xmlEmbeddable.getSpecifiedAccess());
//		assertEquals("basicMapping", xmlEmbeddable.persistentType().attributes().next().getName());
	}
	
	public void testMakeEntityMappedSuperclass() throws Exception {
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity entity = (XmlEntity) entityPersistentType.getMapping();
		entity.setSpecifiedAccess(AccessType.PROPERTY);
		entity.setSpecifiedDiscriminatorValue("DISC_VALUE");
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedMetadataComplete(Boolean.TRUE);
		entity.setSpecifiedName("ENTITY_NAME");
		ormResource().save(null);
	
		entityPersistentType.setMappingKey(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		ormResource().save(null);
		
		MappedSuperclass mappedSuperclass = ormResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertEquals("model.Foo", mappedSuperclass.getClassName());
		assertEquals(Boolean.TRUE, mappedSuperclass.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, mappedSuperclass.getAccess());
		
		XmlMappedSuperclass xmlMappedSuperclass = (XmlMappedSuperclass) entityPersistentType.getMapping();
		assertEquals("model.Foo", xmlMappedSuperclass.getClass_());
		assertEquals(Boolean.TRUE, xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, xmlMappedSuperclass.getSpecifiedAccess());
	}
	
	public void testMakeEntityMappedSuperclass2() throws Exception {
		entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity entity = (XmlEntity) entityPersistentType.getMapping();
		entity.setSpecifiedAccess(AccessType.PROPERTY);
		entity.setSpecifiedDiscriminatorValue("DISC_VALUE");
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedMetadataComplete(Boolean.TRUE);
		entity.setSpecifiedName("ENTITY_NAME");
		ormResource().save(null);
	
		entityPersistentType.setMappingKey(IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		ormResource().save(null);
		
		MappedSuperclass mappedSuperclass = ormResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertEquals("model.Foo", mappedSuperclass.getClassName());
		assertEquals(Boolean.TRUE, mappedSuperclass.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, mappedSuperclass.getAccess());
		
		XmlMappedSuperclass xmlMappedSuperclass = (XmlMappedSuperclass) entityPersistentType.getMapping();
		assertEquals("model.Foo", xmlMappedSuperclass.getClass_());
		assertEquals(Boolean.TRUE, xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, xmlMappedSuperclass.getSpecifiedAccess());
	}

	
	public void testAddSequenceGenerator() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNull(xmlEntity.getSequenceGenerator());
		assertNull(entityResource.getSequenceGenerator());
		
		xmlEntity.addSequenceGenerator();
		
		assertNotNull(entityResource.getSequenceGenerator());
		assertNotNull(xmlEntity.getSequenceGenerator());
				
		//try adding another sequence generator, should get an IllegalStateException
		try {
			xmlEntity.addSequenceGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveSequenceGenerator() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNull(xmlEntity.getSequenceGenerator());
		assertNull(entityResource.getSequenceGenerator());

		xmlEntity.addSequenceGenerator();
		assertNotNull(entityResource.getSequenceGenerator());
		assertNotNull(xmlEntity.getSequenceGenerator());

		xmlEntity.removeSequenceGenerator();
		
		assertNull(xmlEntity.getSequenceGenerator());
		assertNull(entityResource.getSequenceGenerator());

		//try removing the sequence generator again, should get an IllegalStateException
		try {
			xmlEntity.removeSequenceGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}

	public void testUpdateSequenceGenerator() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNull(xmlEntity.getSequenceGenerator());
		assertNull(entityResource.getSequenceGenerator());
		
		entityResource.setSequenceGenerator(OrmFactory.eINSTANCE.createSequenceGenerator());
				
		assertNotNull(xmlEntity.getSequenceGenerator());
		assertNotNull(entityResource.getSequenceGenerator());
				
		entityResource.setSequenceGenerator(null);
		assertNull(xmlEntity.getSequenceGenerator());
		assertNull(entityResource.getSequenceGenerator());
	}
	
	public void testAddTableGenerator() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNull(xmlEntity.getTableGenerator());
		assertNull(entityResource.getTableGenerator());
		
		xmlEntity.addTableGenerator();
		
		assertNotNull(entityResource.getTableGenerator());
		assertNotNull(xmlEntity.getTableGenerator());
				
		//try adding another table generator, should get an IllegalStateException
		try {
			xmlEntity.addTableGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveTableGenerator() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNull(xmlEntity.getTableGenerator());
		assertNull(entityResource.getTableGenerator());

		xmlEntity.addTableGenerator();
		assertNotNull(entityResource.getTableGenerator());
		assertNotNull(xmlEntity.getTableGenerator());

		xmlEntity.removeTableGenerator();
		
		assertNull(xmlEntity.getTableGenerator());
		assertNull(entityResource.getTableGenerator());

		//try removing the table generator again, should get an IllegalStateException
		try {
			xmlEntity.removeTableGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testUpdateTableGenerator() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNull(xmlEntity.getTableGenerator());
		assertNull(entityResource.getTableGenerator());
		
		entityResource.setTableGenerator(OrmFactory.eINSTANCE.createTableGenerator());
				
		assertNotNull(xmlEntity.getTableGenerator());
		assertNotNull(entityResource.getTableGenerator());
				
		entityResource.setTableGenerator(null);
		assertNull(xmlEntity.getTableGenerator());
		assertNull(entityResource.getTableGenerator());
	}
	
	public void testUpdateDiscriminatorColumn() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNotNull(xmlEntity.getDiscriminatorColumn());

		entityResource.setDiscriminatorColumn(OrmFactory.eINSTANCE.createDiscriminatorColumn());
		entityResource.getDiscriminatorColumn().setName("FOO");
		
		assertEquals("FOO", xmlEntity.getDiscriminatorColumn().getSpecifiedName());
		assertEquals("FOO", entityResource.getDiscriminatorColumn().getName());
		
		entityResource.getDiscriminatorColumn().setName(null);
		
		assertNull(xmlEntity.getDiscriminatorColumn().getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn().getName());

		entityResource.setDiscriminatorColumn(null);
		
		assertNotNull(xmlEntity.getDiscriminatorColumn());
	}
	
	public void testUpdateDiscriminatorValue() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNull(xmlEntity.getSpecifiedDiscriminatorValue());
		assertNull(entityResource.getDiscriminatorValue());

		entityResource.setDiscriminatorValue("FOO");
		
		assertEquals("FOO", xmlEntity.getSpecifiedDiscriminatorValue());
		assertEquals("FOO", entityResource.getDiscriminatorValue());
		
		entityResource.setDiscriminatorValue(null);
		
		assertNull(xmlEntity.getSpecifiedDiscriminatorValue());
		assertNull(entityResource.getDiscriminatorValue());
	}
	
	public void testModifyDiscriminatorValue() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNull(xmlEntity.getSpecifiedDiscriminatorValue());
		assertNull(entityResource.getDiscriminatorValue());

		xmlEntity.setSpecifiedDiscriminatorValue("FOO");
		
		assertEquals("FOO", xmlEntity.getSpecifiedDiscriminatorValue());
		assertEquals("FOO", entityResource.getDiscriminatorValue());
		
		xmlEntity.setSpecifiedDiscriminatorValue(null);
		
		assertNull(xmlEntity.getSpecifiedDiscriminatorValue());
		assertNull(entityResource.getDiscriminatorValue());
	}
	
	public void testAddSpecifiedPrimaryKeyJoinColumn() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		XmlPrimaryKeyJoinColumn primaryKeyJoinColumn = xmlEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		primaryKeyJoinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		
		XmlPrimaryKeyJoinColumn primaryKeyJoinColumn2 = xmlEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		primaryKeyJoinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("FOO", entityResource.getPrimaryKeyJoinColumns().get(1).getName());
		
		XmlPrimaryKeyJoinColumn primaryKeyJoinColumn3 = xmlEntity.addSpecifiedPrimaryKeyJoinColumn(1);
		primaryKeyJoinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", entityResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", entityResource.getPrimaryKeyJoinColumns().get(2).getName());
		
		ListIterator<XmlPrimaryKeyJoinColumn> primaryKeyJoinColumns = xmlEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals(primaryKeyJoinColumn2, primaryKeyJoinColumns.next());
		assertEquals(primaryKeyJoinColumn3, primaryKeyJoinColumns.next());
		assertEquals(primaryKeyJoinColumn, primaryKeyJoinColumns.next());
		
		primaryKeyJoinColumns = xmlEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();

		xmlEntity.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		xmlEntity.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		xmlEntity.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getPrimaryKeyJoinColumns().size());
		
		xmlEntity.removeSpecifiedPrimaryKeyJoinColumn(0);
		assertEquals(2, entityResource.getPrimaryKeyJoinColumns().size());
		assertEquals("BAR", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", entityResource.getPrimaryKeyJoinColumns().get(1).getName());

		xmlEntity.removeSpecifiedPrimaryKeyJoinColumn(0);
		assertEquals(1, entityResource.getPrimaryKeyJoinColumns().size());
		assertEquals("BAZ", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		
		xmlEntity.removeSpecifiedPrimaryKeyJoinColumn(0);
		assertEquals(0, entityResource.getPrimaryKeyJoinColumns().size());
	}
	
	public void testMoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();

		xmlEntity.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		xmlEntity.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		xmlEntity.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getPrimaryKeyJoinColumns().size());
		
		
		xmlEntity.moveSpecifiedPrimaryKeyJoinColumn(2, 0);
		ListIterator<XmlPrimaryKeyJoinColumn> primaryKeyJoinColumns = xmlEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());

		assertEquals("BAR", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", entityResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", entityResource.getPrimaryKeyJoinColumns().get(2).getName());


		xmlEntity.moveSpecifiedPrimaryKeyJoinColumn(0, 1);
		primaryKeyJoinColumns = xmlEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());

		assertEquals("BAZ", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAR", entityResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", entityResource.getPrimaryKeyJoinColumns().get(2).getName());
	}
	
	public void testUpdatePrimaryKeyJoinColumns() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		entityResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createPrimaryKeyJoinColumn());
		entityResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createPrimaryKeyJoinColumn());
		entityResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createPrimaryKeyJoinColumn());
		
		entityResource.getPrimaryKeyJoinColumns().get(0).setName("FOO");
		entityResource.getPrimaryKeyJoinColumns().get(1).setName("BAR");
		entityResource.getPrimaryKeyJoinColumns().get(2).setName("BAZ");

		ListIterator<XmlPrimaryKeyJoinColumn> primaryKeyJoinColumns = xmlEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		entityResource.getPrimaryKeyJoinColumns().move(2, 0);
		primaryKeyJoinColumns = xmlEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		entityResource.getPrimaryKeyJoinColumns().move(0, 1);
		primaryKeyJoinColumns = xmlEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		entityResource.getPrimaryKeyJoinColumns().remove(1);
		primaryKeyJoinColumns = xmlEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		entityResource.getPrimaryKeyJoinColumns().remove(1);
		primaryKeyJoinColumns = xmlEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		entityResource.getPrimaryKeyJoinColumns().remove(0);
		assertFalse(xmlEntity.specifiedPrimaryKeyJoinColumns().hasNext());
	}
	
	public void testAddSpecifiedAttributeOverride() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		XmlAttributeOverride attributeOverride = xmlEntity.addSpecifiedAttributeOverride(0);
		attributeOverride.setName("FOO");
				
		assertEquals("FOO", entityResource.getAttributeOverrides().get(0).getName());
		
		XmlAttributeOverride attributeOverride2 = xmlEntity.addSpecifiedAttributeOverride(0);
		attributeOverride2.setName("BAR");
		
		assertEquals("BAR", entityResource.getAttributeOverrides().get(0).getName());
		assertEquals("FOO", entityResource.getAttributeOverrides().get(1).getName());
		
		XmlAttributeOverride attributeOverride3 = xmlEntity.addSpecifiedAttributeOverride(1);
		attributeOverride3.setName("BAZ");
		
		assertEquals("BAR", entityResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAZ", entityResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", entityResource.getAttributeOverrides().get(2).getName());
		
		ListIterator<XmlAttributeOverride> attributeOverrides = xmlEntity.specifiedAttributeOverrides();
		assertEquals(attributeOverride2, attributeOverrides.next());
		assertEquals(attributeOverride3, attributeOverrides.next());
		assertEquals(attributeOverride, attributeOverrides.next());
		
		attributeOverrides = xmlEntity.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
	}
	
	public void testRemoveSpecifiedAttributeOverride() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();

		xmlEntity.addSpecifiedAttributeOverride(0).setName("FOO");
		xmlEntity.addSpecifiedAttributeOverride(1).setName("BAR");
		xmlEntity.addSpecifiedAttributeOverride(2).setName("BAZ");
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getAttributeOverrides().size());
		
		xmlEntity.removeSpecifiedAttributeOverride(0);
		assertEquals(2, entityResource.getAttributeOverrides().size());
		assertEquals("BAR", entityResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAZ", entityResource.getAttributeOverrides().get(1).getName());

		xmlEntity.removeSpecifiedAttributeOverride(0);
		assertEquals(1, entityResource.getAttributeOverrides().size());
		assertEquals("BAZ", entityResource.getAttributeOverrides().get(0).getName());
		
		xmlEntity.removeSpecifiedAttributeOverride(0);
		assertEquals(0, entityResource.getAttributeOverrides().size());
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();

		xmlEntity.addSpecifiedAttributeOverride(0).setName("FOO");
		xmlEntity.addSpecifiedAttributeOverride(1).setName("BAR");
		xmlEntity.addSpecifiedAttributeOverride(2).setName("BAZ");
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getAttributeOverrides().size());
		
		
		xmlEntity.moveSpecifiedAttributeOverride(2, 0);
		ListIterator<XmlAttributeOverride> attributeOverrides = xmlEntity.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAR", entityResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAZ", entityResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", entityResource.getAttributeOverrides().get(2).getName());


		xmlEntity.moveSpecifiedAttributeOverride(0, 1);
		attributeOverrides = xmlEntity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAZ", entityResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAR", entityResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", entityResource.getAttributeOverrides().get(2).getName());
	}
	
	public void testUpdateAttributeOverrides() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createAttributeOverride());
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createAttributeOverride());
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createAttributeOverride());
		
		entityResource.getAttributeOverrides().get(0).setName("FOO");
		entityResource.getAttributeOverrides().get(1).setName("BAR");
		entityResource.getAttributeOverrides().get(2).setName("BAZ");

		ListIterator<XmlAttributeOverride> attributeOverrides = xmlEntity.specifiedAttributeOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		entityResource.getAttributeOverrides().move(2, 0);
		attributeOverrides = xmlEntity.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		entityResource.getAttributeOverrides().move(0, 1);
		attributeOverrides = xmlEntity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		entityResource.getAttributeOverrides().remove(1);
		attributeOverrides = xmlEntity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		entityResource.getAttributeOverrides().remove(1);
		attributeOverrides = xmlEntity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		entityResource.getAttributeOverrides().remove(0);
		assertFalse(xmlEntity.specifiedAttributeOverrides().hasNext());
	}
	
	public void testAddSpecifiedAssociationOverride() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		XmlAssociationOverride associationOverride = xmlEntity.addSpecifiedAssociationOverride(0);
		associationOverride.setName("FOO");
				
		assertEquals("FOO", entityResource.getAssociationOverrides().get(0).getName());
		
		XmlAssociationOverride associationOverride2 = xmlEntity.addSpecifiedAssociationOverride(0);
		associationOverride2.setName("BAR");
		
		assertEquals("BAR", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("FOO", entityResource.getAssociationOverrides().get(1).getName());
		
		XmlAssociationOverride associationOverride3 = xmlEntity.addSpecifiedAssociationOverride(1);
		associationOverride3.setName("BAZ");
		
		assertEquals("BAR", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("BAZ", entityResource.getAssociationOverrides().get(1).getName());
		assertEquals("FOO", entityResource.getAssociationOverrides().get(2).getName());
		
		ListIterator<XmlAssociationOverride> associationOverrides = xmlEntity.specifiedAssociationOverrides();
		assertEquals(associationOverride2, associationOverrides.next());
		assertEquals(associationOverride3, associationOverrides.next());
		assertEquals(associationOverride, associationOverrides.next());
		
		associationOverrides = xmlEntity.specifiedAssociationOverrides();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
	}
	
	public void testRemoveSpecifiedAssociationOverride() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();

		xmlEntity.addSpecifiedAssociationOverride(0).setName("FOO");
		xmlEntity.addSpecifiedAssociationOverride(1).setName("BAR");
		xmlEntity.addSpecifiedAssociationOverride(2).setName("BAZ");
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getAssociationOverrides().size());
		
		xmlEntity.removeSpecifiedAssociationOverride(0);
		assertEquals(2, entityResource.getAssociationOverrides().size());
		assertEquals("BAR", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("BAZ", entityResource.getAssociationOverrides().get(1).getName());

		xmlEntity.removeSpecifiedAssociationOverride(0);
		assertEquals(1, entityResource.getAssociationOverrides().size());
		assertEquals("BAZ", entityResource.getAssociationOverrides().get(0).getName());
		
		xmlEntity.removeSpecifiedAssociationOverride(0);
		assertEquals(0, entityResource.getAssociationOverrides().size());
	}
	
	public void testMoveSpecifiedAssociationOverride() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();

		xmlEntity.addSpecifiedAssociationOverride(0).setName("FOO");
		xmlEntity.addSpecifiedAssociationOverride(1).setName("BAR");
		xmlEntity.addSpecifiedAssociationOverride(2).setName("BAZ");
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getAssociationOverrides().size());
		
		
		xmlEntity.moveSpecifiedAssociationOverride(2, 0);
		ListIterator<XmlAssociationOverride> associationOverrides = xmlEntity.specifiedAssociationOverrides();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());

		assertEquals("BAR", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("BAZ", entityResource.getAssociationOverrides().get(1).getName());
		assertEquals("FOO", entityResource.getAssociationOverrides().get(2).getName());


		xmlEntity.moveSpecifiedAssociationOverride(0, 1);
		associationOverrides = xmlEntity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());

		assertEquals("BAZ", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("BAR", entityResource.getAssociationOverrides().get(1).getName());
		assertEquals("FOO", entityResource.getAssociationOverrides().get(2).getName());
	}
	
	public void testUpdateAssociationOverrides() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createAssociationOverride());
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createAssociationOverride());
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createAssociationOverride());
		
		entityResource.getAssociationOverrides().get(0).setName("FOO");
		entityResource.getAssociationOverrides().get(1).setName("BAR");
		entityResource.getAssociationOverrides().get(2).setName("BAZ");

		ListIterator<XmlAssociationOverride> associationOverrides = xmlEntity.specifiedAssociationOverrides();
		assertEquals("FOO", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		entityResource.getAssociationOverrides().move(2, 0);
		associationOverrides = xmlEntity.specifiedAssociationOverrides();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		entityResource.getAssociationOverrides().move(0, 1);
		associationOverrides = xmlEntity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		entityResource.getAssociationOverrides().remove(1);
		associationOverrides = xmlEntity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		entityResource.getAssociationOverrides().remove(1);
		associationOverrides = xmlEntity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		entityResource.getAssociationOverrides().remove(0);
		assertFalse(xmlEntity.specifiedAssociationOverrides().hasNext());
	}

	
	public void testAddNamedQuery() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		XmlNamedQuery namedQuery = xmlEntity.addNamedQuery(0);
		namedQuery.setName("FOO");
				
		assertEquals("FOO", entityResource.getNamedQueries().get(0).getName());
		
		XmlNamedQuery namedQuery2 = xmlEntity.addNamedQuery(0);
		namedQuery2.setName("BAR");
		
		assertEquals("BAR", entityResource.getNamedQueries().get(0).getName());
		assertEquals("FOO", entityResource.getNamedQueries().get(1).getName());
		
		XmlNamedQuery namedQuery3 = xmlEntity.addNamedQuery(1);
		namedQuery3.setName("BAZ");
		
		assertEquals("BAR", entityResource.getNamedQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedQueries().get(2).getName());
		
		ListIterator<XmlNamedQuery> namedQuerys = xmlEntity.namedQueries();
		assertEquals(namedQuery2, namedQuerys.next());
		assertEquals(namedQuery3, namedQuerys.next());
		assertEquals(namedQuery, namedQuerys.next());
		
		namedQuerys = xmlEntity.namedQueries();
		assertEquals("BAR", namedQuerys.next().getName());
		assertEquals("BAZ", namedQuerys.next().getName());
		assertEquals("FOO", namedQuerys.next().getName());
	}
	
	public void testRemoveNamedQuery() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();

		xmlEntity.addNamedQuery(0).setName("FOO");
		xmlEntity.addNamedQuery(1).setName("BAR");
		xmlEntity.addNamedQuery(2).setName("BAZ");
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getNamedQueries().size());
		
		xmlEntity.removeNamedQuery(0);
		assertEquals(2, entityResource.getNamedQueries().size());
		assertEquals("BAR", entityResource.getNamedQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedQueries().get(1).getName());

		xmlEntity.removeNamedQuery(0);
		assertEquals(1, entityResource.getNamedQueries().size());
		assertEquals("BAZ", entityResource.getNamedQueries().get(0).getName());
		
		xmlEntity.removeNamedQuery(0);
		assertEquals(0, entityResource.getNamedQueries().size());
	}
	
	public void testMoveNamedQuery() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();

		xmlEntity.addNamedQuery(0).setName("FOO");
		xmlEntity.addNamedQuery(1).setName("BAR");
		xmlEntity.addNamedQuery(2).setName("BAZ");
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getNamedQueries().size());
		
		
		xmlEntity.moveNamedQuery(2, 0);
		ListIterator<XmlNamedQuery> namedQuerys = xmlEntity.namedQueries();
		assertEquals("BAR", namedQuerys.next().getName());
		assertEquals("BAZ", namedQuerys.next().getName());
		assertEquals("FOO", namedQuerys.next().getName());

		assertEquals("BAR", entityResource.getNamedQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedQueries().get(2).getName());


		xmlEntity.moveNamedQuery(0, 1);
		namedQuerys = xmlEntity.namedQueries();
		assertEquals("BAZ", namedQuerys.next().getName());
		assertEquals("BAR", namedQuerys.next().getName());
		assertEquals("FOO", namedQuerys.next().getName());

		assertEquals("BAZ", entityResource.getNamedQueries().get(0).getName());
		assertEquals("BAR", entityResource.getNamedQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedQueries().get(2).getName());
	}
	
	public void testUpdateNamedQueries() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		entityResource.getNamedQueries().add(OrmFactory.eINSTANCE.createNamedQuery());
		entityResource.getNamedQueries().add(OrmFactory.eINSTANCE.createNamedQuery());
		entityResource.getNamedQueries().add(OrmFactory.eINSTANCE.createNamedQuery());
		
		entityResource.getNamedQueries().get(0).setName("FOO");
		entityResource.getNamedQueries().get(1).setName("BAR");
		entityResource.getNamedQueries().get(2).setName("BAZ");

		ListIterator<XmlNamedQuery> namedQuerys = xmlEntity.namedQueries();
		assertEquals("FOO", namedQuerys.next().getName());
		assertEquals("BAR", namedQuerys.next().getName());
		assertEquals("BAZ", namedQuerys.next().getName());
		assertFalse(namedQuerys.hasNext());
		
		entityResource.getNamedQueries().move(2, 0);
		namedQuerys = xmlEntity.namedQueries();
		assertEquals("BAR", namedQuerys.next().getName());
		assertEquals("BAZ", namedQuerys.next().getName());
		assertEquals("FOO", namedQuerys.next().getName());
		assertFalse(namedQuerys.hasNext());

		entityResource.getNamedQueries().move(0, 1);
		namedQuerys = xmlEntity.namedQueries();
		assertEquals("BAZ", namedQuerys.next().getName());
		assertEquals("BAR", namedQuerys.next().getName());
		assertEquals("FOO", namedQuerys.next().getName());
		assertFalse(namedQuerys.hasNext());

		entityResource.getNamedQueries().remove(1);
		namedQuerys = xmlEntity.namedQueries();
		assertEquals("BAZ", namedQuerys.next().getName());
		assertEquals("FOO", namedQuerys.next().getName());
		assertFalse(namedQuerys.hasNext());

		entityResource.getNamedQueries().remove(1);
		namedQuerys = xmlEntity.namedQueries();
		assertEquals("BAZ", namedQuerys.next().getName());
		assertFalse(namedQuerys.hasNext());
		
		entityResource.getNamedQueries().remove(0);
		assertFalse(xmlEntity.namedQueries().hasNext());
	}
	
	public void testAddNamedNativeQuery() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		XmlNamedNativeQuery namedNativeQuery = xmlEntity.addNamedNativeQuery(0);
		namedNativeQuery.setName("FOO");
				
		assertEquals("FOO", entityResource.getNamedNativeQueries().get(0).getName());
		
		XmlNamedNativeQuery namedNativeQuery2 = xmlEntity.addNamedNativeQuery(0);
		namedNativeQuery2.setName("BAR");
		
		assertEquals("BAR", entityResource.getNamedNativeQueries().get(0).getName());
		assertEquals("FOO", entityResource.getNamedNativeQueries().get(1).getName());
		
		XmlNamedNativeQuery namedNativeQuery3 = xmlEntity.addNamedNativeQuery(1);
		namedNativeQuery3.setName("BAZ");
		
		assertEquals("BAR", entityResource.getNamedNativeQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedNativeQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedNativeQueries().get(2).getName());
		
		ListIterator<XmlNamedNativeQuery> namedNativeQuerys = xmlEntity.namedNativeQueries();
		assertEquals(namedNativeQuery2, namedNativeQuerys.next());
		assertEquals(namedNativeQuery3, namedNativeQuerys.next());
		assertEquals(namedNativeQuery, namedNativeQuerys.next());
		
		namedNativeQuerys = xmlEntity.namedNativeQueries();
		assertEquals("BAR", namedNativeQuerys.next().getName());
		assertEquals("BAZ", namedNativeQuerys.next().getName());
		assertEquals("FOO", namedNativeQuerys.next().getName());
	}
	
	public void testRemoveNamedNativeQuery() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();

		xmlEntity.addNamedNativeQuery(0).setName("FOO");
		xmlEntity.addNamedNativeQuery(1).setName("BAR");
		xmlEntity.addNamedNativeQuery(2).setName("BAZ");
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getNamedNativeQueries().size());
		
		xmlEntity.removeNamedNativeQuery(0);
		assertEquals(2, entityResource.getNamedNativeQueries().size());
		assertEquals("BAR", entityResource.getNamedNativeQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedNativeQueries().get(1).getName());

		xmlEntity.removeNamedNativeQuery(0);
		assertEquals(1, entityResource.getNamedNativeQueries().size());
		assertEquals("BAZ", entityResource.getNamedNativeQueries().get(0).getName());
		
		xmlEntity.removeNamedNativeQuery(0);
		assertEquals(0, entityResource.getNamedNativeQueries().size());
	}
	
	public void testMoveNamedNativeQuery() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();

		xmlEntity.addNamedNativeQuery(0).setName("FOO");
		xmlEntity.addNamedNativeQuery(1).setName("BAR");
		xmlEntity.addNamedNativeQuery(2).setName("BAZ");
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getNamedNativeQueries().size());
		
		
		xmlEntity.moveNamedNativeQuery(2, 0);
		ListIterator<XmlNamedNativeQuery> namedNativeQuerys = xmlEntity.namedNativeQueries();
		assertEquals("BAR", namedNativeQuerys.next().getName());
		assertEquals("BAZ", namedNativeQuerys.next().getName());
		assertEquals("FOO", namedNativeQuerys.next().getName());

		assertEquals("BAR", entityResource.getNamedNativeQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedNativeQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedNativeQueries().get(2).getName());


		xmlEntity.moveNamedNativeQuery(0, 1);
		namedNativeQuerys = xmlEntity.namedNativeQueries();
		assertEquals("BAZ", namedNativeQuerys.next().getName());
		assertEquals("BAR", namedNativeQuerys.next().getName());
		assertEquals("FOO", namedNativeQuerys.next().getName());

		assertEquals("BAZ", entityResource.getNamedNativeQueries().get(0).getName());
		assertEquals("BAR", entityResource.getNamedNativeQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedNativeQueries().get(2).getName());
	}
	
	public void testUpdateNamedNativeQueries() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		entityResource.getNamedNativeQueries().add(OrmFactory.eINSTANCE.createNamedNativeQuery());
		entityResource.getNamedNativeQueries().add(OrmFactory.eINSTANCE.createNamedNativeQuery());
		entityResource.getNamedNativeQueries().add(OrmFactory.eINSTANCE.createNamedNativeQuery());
		
		entityResource.getNamedNativeQueries().get(0).setName("FOO");
		entityResource.getNamedNativeQueries().get(1).setName("BAR");
		entityResource.getNamedNativeQueries().get(2).setName("BAZ");

		ListIterator<XmlNamedNativeQuery> namedNativeQuerys = xmlEntity.namedNativeQueries();
		assertEquals("FOO", namedNativeQuerys.next().getName());
		assertEquals("BAR", namedNativeQuerys.next().getName());
		assertEquals("BAZ", namedNativeQuerys.next().getName());
		assertFalse(namedNativeQuerys.hasNext());
		
		entityResource.getNamedNativeQueries().move(2, 0);
		namedNativeQuerys = xmlEntity.namedNativeQueries();
		assertEquals("BAR", namedNativeQuerys.next().getName());
		assertEquals("BAZ", namedNativeQuerys.next().getName());
		assertEquals("FOO", namedNativeQuerys.next().getName());
		assertFalse(namedNativeQuerys.hasNext());

		entityResource.getNamedNativeQueries().move(0, 1);
		namedNativeQuerys = xmlEntity.namedNativeQueries();
		assertEquals("BAZ", namedNativeQuerys.next().getName());
		assertEquals("BAR", namedNativeQuerys.next().getName());
		assertEquals("FOO", namedNativeQuerys.next().getName());
		assertFalse(namedNativeQuerys.hasNext());

		entityResource.getNamedNativeQueries().remove(1);
		namedNativeQuerys = xmlEntity.namedNativeQueries();
		assertEquals("BAZ", namedNativeQuerys.next().getName());
		assertEquals("FOO", namedNativeQuerys.next().getName());
		assertFalse(namedNativeQuerys.hasNext());

		entityResource.getNamedNativeQueries().remove(1);
		namedNativeQuerys = xmlEntity.namedNativeQueries();
		assertEquals("BAZ", namedNativeQuerys.next().getName());
		assertFalse(namedNativeQuerys.hasNext());
		
		entityResource.getNamedNativeQueries().remove(0);
		assertFalse(xmlEntity.namedNativeQueries().hasNext());
	}
	
	public void testUpdateIdClass() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		assertNull(xmlEntity.getIdClass());
		assertNull(entityResource.getIdClass());
		
		entityResource.setIdClass(OrmFactory.eINSTANCE.createIdClass());
		
		assertNull(xmlEntity.getIdClass());
		assertNotNull(entityResource.getIdClass());
		
		entityResource.getIdClass().setClassName("model.Foo");
		assertEquals("model.Foo", xmlEntity.getIdClass());
		assertEquals("model.Foo", entityResource.getIdClass().getClassName());
		
		//test setting  @IdClass value to null, id-class tag is not removed
		entityResource.getIdClass().setClassName(null);
		assertNull(xmlEntity.getIdClass());
		assertNotNull(entityResource.getIdClass());
		
		//reset @IdClass value and then remove id-class tag
		entityResource.setIdClass(OrmFactory.eINSTANCE.createIdClass());
		entityResource.getIdClass().setClassName("model.Foo");
		entityResource.setIdClass(null);
		
		assertNull(xmlEntity.getIdClass());
		assertNull(entityResource.getIdClass());
	}
	
	public void testModifyIdClass() throws Exception {
		XmlPersistentType persistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) persistentType.getMapping();
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		assertNull(xmlEntity.getIdClass());
		assertNull(entityResource.getIdClass());
			
		xmlEntity.setIdClass("model.Foo");
		assertEquals("model.Foo", entityResource.getIdClass().getClassName());
		assertEquals("model.Foo", xmlEntity.getIdClass());
		
		xmlEntity.setIdClass(null);
		assertNull(xmlEntity.getIdClass());
		assertNull(entityResource.getIdClass());
	}

}
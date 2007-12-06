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
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.orm.XmlEntity;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
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
	
	public void testUpdateSpecifiedName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals("Foo", xmlEntity.getDefaultName());
		
		//set class in the resource model, verify context model updated
		entityResource.setClassName("com.Bar");
		assertEquals("Bar", xmlEntity.getDefaultName());
		
		//set class to null in the resource model
		entityResource.setClassName(null);
		assertNull(xmlEntity.getDefaultName());
	}
	
	public void testUpdateName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
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

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(FULLY_QUALIFIED_TYPE_NAME, IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		//java has no annotations, so defaultAccess in xml is null
		assertNull(xmlEntity.getDefaultAccess());
		
		entityMappings().setSpecifiedAccess(AccessType.FIELD);
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);		
		//entityMappings access wins over perssitence-unit-defaults access
		assertEquals(AccessType.FIELD, xmlEntity.getDefaultAccess());
		
		entityMappings().setSpecifiedAccess(null);		
		//perssitence-unit-defaults access used now
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

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(FULLY_QUALIFIED_TYPE_NAME, IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		assertEquals(AccessType.FIELD, xmlEntity.getDefaultAccess());
		
		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertNull(xmlEntity.getDefaultAccess());
		
		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(AccessType.FIELD, xmlEntity.getDefaultAccess());

		entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertEquals(AccessType.FIELD, xmlEntity.getDefaultAccess());
		
		xmlEntity.setSpecifiedMetadataComplete(null);
		assertNull(xmlEntity.getDefaultAccess());
		
		entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertEquals(AccessType.FIELD, xmlEntity.getDefaultAccess());
	}
	
	public void testUpdateDefaultAccessFromJavaPropertyAccess() throws Exception {
		createTestEntityPropertyAccess();

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(FULLY_QUALIFIED_TYPE_NAME, IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		assertEquals(AccessType.PROPERTY, xmlEntity.getDefaultAccess());
		
		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertNull(xmlEntity.getDefaultAccess());
		
		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(AccessType.PROPERTY, xmlEntity.getDefaultAccess());

		entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertEquals(AccessType.PROPERTY, xmlEntity.getDefaultAccess());
		
		xmlEntity.setSpecifiedMetadataComplete(null);
		assertNull(xmlEntity.getDefaultAccess());
		
		entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertEquals(AccessType.PROPERTY, xmlEntity.getDefaultAccess());
	}
	
	public void testUpdateDefaultAccessNoUnderlyingJava() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		assertNull(xmlEntity.getDefaultAccess());
	}
		
	public void testUpdateSpecifiedMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertFalse(xmlEntity.getDefaultMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(xmlEntity.getDefaultMetadataComplete());
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertFalse(xmlEntity.getDefaultMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
	}

	public void testUpdateMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertFalse(xmlEntity.getMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(xmlEntity.getMetadataComplete());
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertFalse(xmlEntity.getMetadataComplete());
		assertNull(xmlEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
	}
}
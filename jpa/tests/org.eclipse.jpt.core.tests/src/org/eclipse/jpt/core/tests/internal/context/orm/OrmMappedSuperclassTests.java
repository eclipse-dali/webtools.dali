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

import java.util.ListIterator;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.IdClassReference;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;

@SuppressWarnings("nls")
public class OrmMappedSuperclassTests extends ContextModelTestCase
{
	public OrmMappedSuperclassTests(String name) {
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
	
	private void createTestIdClass() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("public class ").append("TestTypeId").append(" ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "TestTypeId.java", sourceWriter);
	}
	
	public void testUpdateClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		OrmMappedSuperclass ormMappedSuperclass = (OrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = getXmlEntityMappings().getMappedSuperclasses().get(0);
		assertEquals("model.Foo", ormMappedSuperclass.getClass_());
		assertEquals("model.Foo", mappedSuperclassResource.getClassName());
		
		//set class in the resource model, verify context model updated
		mappedSuperclassResource.setClassName("com.Bar");
		assertEquals("com.Bar", ormMappedSuperclass.getClass_());
		assertEquals("com.Bar", mappedSuperclassResource.getClassName());
	
		//set class to null in the resource model
		mappedSuperclassResource.setClassName(null);
		assertNull(ormMappedSuperclass.getClass_());
		assertNull(mappedSuperclassResource.getClassName());
	}
	
	public void testModifyClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		OrmMappedSuperclass ormMappedSuperclass = (OrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = getXmlEntityMappings().getMappedSuperclasses().get(0);
		assertEquals("model.Foo", ormMappedSuperclass.getClass_());
		assertEquals("model.Foo", mappedSuperclassResource.getClassName());
		
		//set class in the context model, verify resource model modified
		ormMappedSuperclass.setClass("com.Bar");
		assertEquals("com.Bar", ormMappedSuperclass.getClass_());
		assertEquals("com.Bar", mappedSuperclassResource.getClassName());
		
		//set class to null in the context model
		ormMappedSuperclass.setClass(null);
		assertNull(ormMappedSuperclass.getClass_());
		assertNull(mappedSuperclassResource.getClassName());
	}
	//TODO add tests for setting the className when the package is set on entity-mappings
	
	public void testUpdateSpecifiedAccess() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		XmlMappedSuperclass mappedSuperclassResource = getXmlEntityMappings().getMappedSuperclasses().get(0);
		assertNull(ormPersistentType.getSpecifiedAccess());
		assertNull(mappedSuperclassResource.getAccess());
		
		//set access in the resource model, verify context model updated
		mappedSuperclassResource.setAccess(org.eclipse.jpt.core.resource.orm.AccessType.FIELD);
		assertEquals(AccessType.FIELD, ormPersistentType.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.FIELD, mappedSuperclassResource.getAccess());
	
		//set access to null in the resource model
		mappedSuperclassResource.setAccess(null);
		assertNull(ormPersistentType.getSpecifiedAccess());
		assertNull(mappedSuperclassResource.getAccess());
	}
	
	public void testModifySpecifiedAccess() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		XmlMappedSuperclass mappedSuperclassResource = getXmlEntityMappings().getMappedSuperclasses().get(0);
		assertNull(ormPersistentType.getSpecifiedAccess());
		assertNull(mappedSuperclassResource.getAccess());
		
		//set access in the context model, verify resource model modified
		ormPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, ormPersistentType.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, mappedSuperclassResource.getAccess());
		
		//set access to null in the context model
		ormPersistentType.setSpecifiedAccess(null);
		assertNull(ormPersistentType.getSpecifiedAccess());
		assertNull(mappedSuperclassResource.getAccess());
	}
	//TODO test default access from
		//underlying java
		//persistence-unit-defaults
		//entity-mappings
		//with xml-mapping-metadata-complete set
	
	public void testUpdateSpecifiedMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		OrmMappedSuperclass ormMappedSuperclass = (OrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = getXmlEntityMappings().getMappedSuperclasses().get(0);
		assertNull(ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		//set metadata-complete in the resource model, verify context model updated
		mappedSuperclassResource.setMetadataComplete(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertEquals(Boolean.TRUE, mappedSuperclassResource.getMetadataComplete());
	
		//set access to false in the resource model
		mappedSuperclassResource.setMetadataComplete(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertEquals(Boolean.FALSE, mappedSuperclassResource.getMetadataComplete());
		
		mappedSuperclassResource.setMetadataComplete(null);
		assertNull(ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
	}
	
	public void testModifySpecifiedMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		OrmMappedSuperclass ormMappedSuperclass = (OrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = getXmlEntityMappings().getMappedSuperclasses().get(0);		assertNull(ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		//set access in the context model, verify resource model modified
		ormMappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertEquals(Boolean.TRUE, mappedSuperclassResource.getMetadataComplete());
		
		//set access to null in the context model
		ormMappedSuperclass.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertEquals(Boolean.FALSE, mappedSuperclassResource.getMetadataComplete());
		
		ormMappedSuperclass.setSpecifiedMetadataComplete(null);
		assertNull(ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
	}
	
	public void testUpdateDefaultMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		OrmMappedSuperclass ormMappedSuperclass = (OrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = getXmlEntityMappings().getMappedSuperclasses().get(0);
		assertNull(ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertFalse(ormMappedSuperclass.isDefaultMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		getXmlEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(ormMappedSuperclass.isDefaultMetadataComplete());
		assertNull(ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		getXmlEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertNull(ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertFalse(ormMappedSuperclass.isDefaultMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
	}
	
	public void testUpdateMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		OrmMappedSuperclass ormMappedSuperclass = (OrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = getXmlEntityMappings().getMappedSuperclasses().get(0);
		assertNull(ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertFalse(ormMappedSuperclass.isMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		getXmlEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(ormMappedSuperclass.isMetadataComplete());
		assertNull(ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		getXmlEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertFalse(ormMappedSuperclass.isMetadataComplete());
		assertNull(ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
	}

	
	public void testMakeMappedSuperclassEntity() throws Exception {
		OrmPersistentType mappedSuperclassPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		OrmMappedSuperclass mappedSuperclass = (OrmMappedSuperclass) mappedSuperclassPersistentType.getMapping();
		mappedSuperclassPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
		mappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
	
		mappedSuperclassPersistentType.setMappingKey(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		
		XmlEntity entity = getXmlEntityMappings().getEntities().get(0);
		assertEquals("model.Foo", entity.getClassName());
		assertEquals(Boolean.TRUE, entity.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, entity.getAccess());
		assertNull(entity.getDiscriminatorValue());
		assertNull(entity.getName());
		
		OrmEntity ormEntity = (OrmEntity) mappedSuperclassPersistentType.getMapping();
		assertEquals("model.Foo", ormEntity.getClass_());
		assertEquals(Boolean.TRUE, ormEntity.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, mappedSuperclassPersistentType.getSpecifiedAccess());
	}
		
	//test with 2 MappedSuperclasses, make the first one an Entity so it has to move to the end of the list
	public void testMakeMappedSuperclassEntity2() throws Exception {
		OrmPersistentType mappedSuperclassPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo2");
		OrmMappedSuperclass mappedSuperclass = (OrmMappedSuperclass) mappedSuperclassPersistentType.getMapping();
		mappedSuperclassPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
		mappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
	
		mappedSuperclassPersistentType.setMappingKey(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		
		XmlEntity entity = getXmlEntityMappings().getEntities().get(0);
		assertEquals("model.Foo", entity.getClassName());
		assertEquals(Boolean.TRUE, entity.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, entity.getAccess());
		assertNull(entity.getDiscriminatorValue());
		assertNull(entity.getName());
		
		OrmEntity ormEntity = (OrmEntity) mappedSuperclassPersistentType.getMapping();
		assertEquals("model.Foo", ormEntity.getClass_());
		assertEquals(Boolean.TRUE, ormEntity.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, mappedSuperclassPersistentType.getSpecifiedAccess());
		
		ListIterator<OrmPersistentType> persistentTypes = getEntityMappings().getPersistentTypes().iterator();
		assertEquals(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, persistentTypes.next().getMappingKey());
		assertEquals(MappingKeys.ENTITY_TYPE_MAPPING_KEY, persistentTypes.next().getMappingKey());
	}
	
	public void testMakeMappedSuperclassEmbeddable() throws Exception {
		OrmPersistentType mappedSuperclassPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		OrmMappedSuperclass mappedSuperclass = (OrmMappedSuperclass) mappedSuperclassPersistentType.getMapping();
		mappedSuperclassPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
		mappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
	
		mappedSuperclassPersistentType.setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		
		XmlEmbeddable embeddable = getXmlEntityMappings().getEmbeddables().get(0);
		assertEquals("model.Foo", embeddable.getClassName());
		assertEquals(Boolean.TRUE, embeddable.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, embeddable.getAccess());
		
		OrmEmbeddable ormEmbeddable = (OrmEmbeddable) mappedSuperclassPersistentType.getMapping();
		assertEquals("model.Foo", ormEmbeddable.getClass_());
		assertEquals(Boolean.TRUE, ormEmbeddable.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, mappedSuperclassPersistentType.getSpecifiedAccess());
	}
	//test with 2 MappedSuperclasses, make the first one an Embeddable so it has to move to the end of the list
	public void testMakeMappedSuperclassEmbeddable2() throws Exception {
		OrmPersistentType mappedSuperclassPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo2");
		OrmMappedSuperclass mappedSuperclass = (OrmMappedSuperclass) mappedSuperclassPersistentType.getMapping();
		mappedSuperclassPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
		mappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
	
		mappedSuperclassPersistentType.setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		
		XmlEmbeddable embeddable = getXmlEntityMappings().getEmbeddables().get(0);
		assertEquals("model.Foo", embeddable.getClassName());
		assertEquals(Boolean.TRUE, embeddable.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, embeddable.getAccess());
		
		OrmEmbeddable ormEmbeddable = (OrmEmbeddable) mappedSuperclassPersistentType.getMapping();
		assertEquals("model.Foo", ormEmbeddable.getClass_());
		assertEquals(Boolean.TRUE, ormEmbeddable.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, mappedSuperclassPersistentType.getSpecifiedAccess());
		
		ListIterator<OrmPersistentType> persistentTypes = getEntityMappings().getPersistentTypes().iterator();
		assertEquals(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, persistentTypes.next().getMappingKey());
		assertEquals(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, persistentTypes.next().getMappingKey());
	}
	
	public void testUpdateIdClass() throws Exception {
		createTestIdClass();
		OrmPersistentType ormPersistentType = 
				getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		
		XmlMappedSuperclass mappedSuperclassResource = getXmlEntityMappings().getMappedSuperclasses().get(0);
		OrmMappedSuperclass ormMappedSuperclass = (OrmMappedSuperclass) ormPersistentType.getMapping();
		IdClassReference idClassRef = ormMappedSuperclass.getIdClassReference();
		
		assertNull(mappedSuperclassResource.getIdClass());
		assertNull(idClassRef.getSpecifiedIdClassName());
		assertNull(idClassRef.getIdClass());
		
		mappedSuperclassResource.setIdClass(OrmFactory.eINSTANCE.createXmlClassReference());
		assertNotNull(mappedSuperclassResource.getIdClass());
		assertNull(idClassRef.getSpecifiedIdClassName());
		assertNull(idClassRef.getIdClass());
		
		String nonExistentIdClassName = PACKAGE_NAME + ".Foo";
		mappedSuperclassResource.getIdClass().setClassName(nonExistentIdClassName);
		assertEquals(nonExistentIdClassName, mappedSuperclassResource.getIdClass().getClassName());
		assertEquals(nonExistentIdClassName, idClassRef.getSpecifiedIdClassName());
		assertNull(idClassRef.getIdClass());
		
		String existentIdClassName = PACKAGE_NAME + ".TestTypeId";
		mappedSuperclassResource.getIdClass().setClassName(existentIdClassName);
		assertEquals(existentIdClassName, mappedSuperclassResource.getIdClass().getClassName());
		assertEquals(existentIdClassName, idClassRef.getSpecifiedIdClassName());
		assertNotNull(idClassRef.getIdClass());
		
		//test setting  @IdClass value to null, id-class tag is not removed
		mappedSuperclassResource.getIdClass().setClassName(null);
		assertNotNull(mappedSuperclassResource.getIdClass());
		assertNull(idClassRef.getSpecifiedIdClassName());
		assertNull(idClassRef.getIdClass());
		
		//reset @IdClass value and then remove id-class tag
		mappedSuperclassResource.setIdClass(OrmFactory.eINSTANCE.createXmlClassReference());
		mappedSuperclassResource.getIdClass().setClassName("model.Foo");
		mappedSuperclassResource.setIdClass(null);
		assertNull(mappedSuperclassResource.getIdClass());
		assertNull(idClassRef.getSpecifiedIdClassName());
		assertNull(idClassRef.getIdClass());
	}
	
	public void testModifyIdClass() throws Exception {
		createTestIdClass();
		OrmPersistentType ormPersistentType = 
				getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		
		XmlMappedSuperclass mappedSuperclassResource = getXmlEntityMappings().getMappedSuperclasses().get(0);
		OrmMappedSuperclass ormMappedSuperclass = (OrmMappedSuperclass) ormPersistentType.getMapping();
		IdClassReference idClassRef = ormMappedSuperclass.getIdClassReference();
		
		assertNull(mappedSuperclassResource.getIdClass());
		assertNull(idClassRef.getSpecifiedIdClassName());
		assertNull(idClassRef.getIdClass());
		
		String nonExistentIdClassName = PACKAGE_NAME + ".Foo";
		idClassRef.setSpecifiedIdClassName(nonExistentIdClassName);
		assertEquals(nonExistentIdClassName, mappedSuperclassResource.getIdClass().getClassName());
		assertEquals(nonExistentIdClassName, idClassRef.getSpecifiedIdClassName());
		assertNull(idClassRef.getIdClass());
		
		String existentIdClassName = PACKAGE_NAME + ".TestTypeId";
		idClassRef.setSpecifiedIdClassName(existentIdClassName);
		assertEquals(existentIdClassName, mappedSuperclassResource.getIdClass().getClassName());
		assertEquals(existentIdClassName, idClassRef.getSpecifiedIdClassName());
		assertNotNull(idClassRef.getIdClass());
		
		idClassRef.setSpecifiedIdClassName(null);
		assertNull(mappedSuperclassResource.getIdClass());
		assertNull(idClassRef.getSpecifiedIdClassName());
		assertNull(idClassRef.getIdClass());
	}
}
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
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class OrmEntityTests extends ContextModelTestCase
{
	public OrmEntityTests(String name) {
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
	
	private void createMappedSuperclassAnnotation() throws Exception{
		this.createAnnotationAndMembers("MappedSuperclass", "");		
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


	private IType createTestMappedSuperclass() throws Exception {
		createMappedSuperclassAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS, JPA.ONE_TO_ONE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass");
			}
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("private String foo;").append(CR);
				sb.append(CR);
				sb.append("    @OneToOne");
				sb.append(CR);
				sb.append("    private int address;").append(CR);
				sb.append(CR);
				sb.append("    @OneToOne");
				sb.append(CR);
				sb.append("    private int address2;").append(CR);
				sb.append(CR);
				sb.append("    ");
			}
		});
	}

	public void testUpdateSpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedName());
		assertNull(entityResource.getName());
		
		//set name in the resource model, verify context model updated
		entityResource.setName("foo");
		assertEquals("foo", ormEntity.getSpecifiedName());
		assertEquals("foo", entityResource.getName());
	
		//set name to null in the resource model
		entityResource.setName(null);
		assertNull(ormEntity.getSpecifiedName());
		assertNull(entityResource.getName());
	}
	
	public void testModifySpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedName());
		assertNull(entityResource.getName());
		
		//set name in the context model, verify resource model modified
		ormEntity.setSpecifiedName("foo");
		assertEquals("foo", ormEntity.getSpecifiedName());
		assertEquals("foo", entityResource.getName());
		
		//set name to null in the context model
		ormEntity.setSpecifiedName(null);
		assertNull(ormEntity.getSpecifiedName());
		assertNull(entityResource.getName());
	}

	public void testUpdateDefaultName() throws Exception {
		createTestEntityFieldAccess();
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(TYPE_NAME, ormEntity.getDefaultName());
		
		ormEntity.getJavaEntity().setSpecifiedName("Foo");
		//xml default entity name comes from java
		assertEquals("Foo", ormEntity.getDefaultName());
		
		//set class in the resource model, verify context model updated
		entityResource.setClassName("com.Bar");
		assertEquals("Bar", ormEntity.getDefaultName());
		
		//set class to null in the resource model
		entityResource.setClassName(null);
		assertNull(ormEntity.getDefaultName());
		
		entityResource.setClassName(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals("Foo", ormEntity.getDefaultName());
		entityResource.setMetadataComplete(Boolean.TRUE);
		assertEquals(TYPE_NAME, ormEntity.getDefaultName());
		
		ormEntity.getJavaEntity().setSpecifiedName("Foo1");
		assertEquals(TYPE_NAME, ormEntity.getDefaultName());
		
		entityResource.setMetadataComplete(null);
		assertEquals("Foo1", ormEntity.getDefaultName());
	}
	
	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals("Foo", ormEntity.getName());
		
		//set class in the resource model, verify context model updated
		entityResource.setClassName("com.Bar");
		assertEquals("Bar", ormEntity.getName());
		
		entityResource.setName("Baz");
		assertEquals("Baz", ormEntity.getName());
		
		//set class to null in the resource model
		entityResource.setClassName(null);
		assertEquals("Baz", ormEntity.getName());
		
		entityResource.setName(null);
		assertNull(ormEntity.getName());
	}

	public void testUpdateClass() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals("model.Foo", ormEntity.getClass_());
		assertEquals("model.Foo", entityResource.getClassName());
		
		//set class in the resource model, verify context model updated
		entityResource.setClassName("com.Bar");
		assertEquals("com.Bar", ormEntity.getClass_());
		assertEquals("com.Bar", entityResource.getClassName());
	
		//set class to null in the resource model
		entityResource.setClassName(null);
		assertNull(ormEntity.getClass_());
		assertNull(entityResource.getClassName());
	}
	
	public void testModifyClass() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals("model.Foo", ormEntity.getClass_());
		assertEquals("model.Foo", entityResource.getClassName());
		
		//set class in the context model, verify resource model modified
		ormEntity.setClass("com.Bar");
		assertEquals("com.Bar", ormEntity.getClass_());
		assertEquals("com.Bar", entityResource.getClassName());
		
		//set class to null in the context model
		ormEntity.setClass(null);
		assertNull(ormEntity.getClass_());
		assertNull(entityResource.getClassName());
	}
	//TODO add tests for setting the className when the package is set on entity-mappings
	
	public void testUpdateSpecifiedAccess() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
		
		//set access in the resource model, verify context model updated
		entityResource.setAccess(org.eclipse.jpt.core.resource.orm.AccessType.FIELD);
		assertEquals(AccessType.FIELD, ormEntity.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.FIELD, entityResource.getAccess());
	
		//set access to null in the resource model
		entityResource.setAccess(null);
		assertNull(ormEntity.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
	}
	
	public void testModifySpecifiedAccess() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
		
		//set access in the context model, verify resource model modified
		ormEntity.setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, ormEntity.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, entityResource.getAccess());
		
		//set access to null in the context model
		ormEntity.setSpecifiedAccess(null);
		assertNull(ormEntity.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
	}
	
	public void testUpdateDefaultAccessFromPersistenceUnitDefaults() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedAccess());
		assertNull(ormEntity.getDefaultAccess());
		assertNull(entityResource.getAccess());
		
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(org.eclipse.jpt.core.resource.orm.AccessType.FIELD);
		assertEquals(AccessType.FIELD, ormEntity.getDefaultAccess());
		assertNull(ormEntity.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, ormEntity.getDefaultAccess());
		assertNull(ormEntity.getSpecifiedAccess());
		assertNull(entityResource.getAccess());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(null);
		assertNull(ormEntity.getSpecifiedAccess());
		assertNull(ormEntity.getDefaultAccess());
		assertNull(entityResource.getAccess());
	}
	
	public void testUpdateDefaultAccessFromJava() throws Exception {
		createTestEntityDefaultFieldAccess();

		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		//java has no annotations, so defaultAccess in xml is null
		assertNull(ormEntity.getDefaultAccess());
		
		entityMappings().setSpecifiedAccess(AccessType.FIELD);
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);		
		//entityMappings access wins over persistence-unit-defaults access
		assertEquals(AccessType.FIELD, ormEntity.getDefaultAccess());
		
		entityMappings().setSpecifiedAccess(null);		
		//persistence-unit-defaults access used now
		assertEquals(AccessType.PROPERTY, ormEntity.getDefaultAccess());
		
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(null);
		assertNull(ormEntity.getDefaultAccess());
		
		ormPersistentType.getJavaPersistentType().getAttributeNamed("id").setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		//java has annotations on fields now, that should win in all cases
		assertEquals(AccessType.FIELD, ormEntity.getDefaultAccess());
		
		entityMappings().setSpecifiedAccess(AccessType.PROPERTY);
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);
		assertEquals(AccessType.FIELD, ormEntity.getDefaultAccess());

		ormPersistentType.getJavaPersistentType().getAttributeNamed("id").setSpecifiedMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertEquals(AccessType.PROPERTY, ormEntity.getDefaultAccess());
	}

	public void testUpdateDefaultAccessFromJavaFieldAccess() throws Exception {
		createTestEntityFieldAccess();

		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		assertEquals(AccessType.FIELD, ormEntity.getDefaultAccess());
		
		ormEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertNull(ormEntity.getDefaultAccess());
		
		ormEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(AccessType.FIELD, ormEntity.getDefaultAccess());

		entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertNull(ormEntity.getDefaultAccess());
		
		ormEntity.setSpecifiedMetadataComplete(null);
		assertNull(ormEntity.getDefaultAccess());
		
		entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertEquals(AccessType.FIELD, ormEntity.getDefaultAccess());
	}
	
	public void testUpdateDefaultAccessFromJavaPropertyAccess() throws Exception {
		createTestEntityPropertyAccess();

		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		assertEquals(AccessType.PROPERTY, ormEntity.getDefaultAccess());
		
		ormEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertNull(ormEntity.getDefaultAccess());
		
		ormEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(AccessType.PROPERTY, ormEntity.getDefaultAccess());

		entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertNull(ormEntity.getDefaultAccess());
		
		ormEntity.setSpecifiedMetadataComplete(null);
		assertNull(ormEntity.getDefaultAccess());
		
		entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertEquals(AccessType.PROPERTY, ormEntity.getDefaultAccess());
	}
	
	public void testUpdateDefaultAccessNoUnderlyingJava() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		assertNull(ormEntity.getDefaultAccess());
	}
		
	public void testUpdateSpecifiedMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		//set metadata-complete in the resource model, verify context model updated
		entityResource.setMetadataComplete(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormEntity.getSpecifiedMetadataComplete());
		assertEquals(Boolean.TRUE, entityResource.getMetadataComplete());
	
		//set access to false in the resource model
		entityResource.setMetadataComplete(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormEntity.getSpecifiedMetadataComplete());
		assertEquals(Boolean.FALSE, entityResource.getMetadataComplete());
		
		entityResource.setMetadataComplete(null);
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
	}
	
	public void testModifySpecifiedMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		//set access in the context model, verify resource model modified
		ormEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertTrue(ormEntity.getSpecifiedMetadataComplete().booleanValue());
		assertTrue(entityResource.getMetadataComplete().booleanValue());
		
		//set access to null in the context model
		ormEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertFalse(ormEntity.getSpecifiedMetadataComplete().booleanValue());
		assertFalse(entityResource.getMetadataComplete().booleanValue());
		
		ormEntity.setSpecifiedMetadataComplete(null);
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
	}
	
	public void testUpdateDefaultMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertFalse(ormEntity.isDefaultMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(ormEntity.isDefaultMetadataComplete());
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertFalse(ormEntity.isDefaultMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		ormEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormEntity.getSpecifiedMetadataComplete());
		assertTrue(ormEntity.isDefaultMetadataComplete());
		assertTrue(ormEntity.isMetadataComplete());
	}

	public void testUpdateMetadataComplete() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertFalse(ormEntity.isMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(ormEntity.isMetadataComplete());
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertFalse(ormEntity.isMetadataComplete());
		assertNull(ormEntity.getSpecifiedMetadataComplete());
		assertNull(entityResource.getMetadataComplete());
	}
	
	
	public void testUpdateInheritanceStrategy() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(InheritanceType.SINGLE_TABLE, ormEntity.getInheritanceStrategy());
		assertNull(entityResource.getInheritance());

		//set inheritance strategy in the resource model, verify context model updated
		entityResource.setInheritance(OrmFactory.eINSTANCE.createInheritance());
		entityResource.getInheritance().setStrategy(org.eclipse.jpt.core.resource.orm.InheritanceType.TABLE_PER_CLASS);
		
		assertEquals(InheritanceType.TABLE_PER_CLASS, ormEntity.getInheritanceStrategy());		
		assertEquals(org.eclipse.jpt.core.resource.orm.InheritanceType.TABLE_PER_CLASS, entityResource.getInheritance().getStrategy());		
	}
	
	public void testUpdateDefaultInheritanceStrategyFromJava() throws Exception {
		createTestEntityDefaultFieldAccess();

		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		//no inheritance strategy specified in java so single-table is default
		assertEquals(InheritanceType.SINGLE_TABLE, ormEntity.getDefaultInheritanceStrategy());
		
		ormEntity.getJavaEntity().setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		assertEquals(InheritanceType.JOINED, ormEntity.getDefaultInheritanceStrategy());
			
		ormEntity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		//inheritance tag exists in xml, so it overrides anything in java
		assertEquals(InheritanceType.SINGLE_TABLE, ormEntity.getDefaultInheritanceStrategy());

		ormEntity.setSpecifiedInheritanceStrategy(null);		
		assertEquals(InheritanceType.JOINED, ormEntity.getDefaultInheritanceStrategy());

		ormEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(InheritanceType.SINGLE_TABLE, ormEntity.getDefaultInheritanceStrategy());
		
		ormEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(InheritanceType.JOINED, ormEntity.getDefaultInheritanceStrategy());
		
		ormEntity.getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		//this setting overrides the false meta-data complete found on ormEntity
		assertEquals(InheritanceType.SINGLE_TABLE, ormEntity.getDefaultInheritanceStrategy());
	}
	
	public void testUpdateDefaultInheritanceStrategyFromParent() throws Exception {
		createTestEntityDefaultFieldAccess();
		createTestSubType();
	
		OrmPersistentType parentPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType childPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		OrmEntity parentXmlEntity = (OrmEntity) parentPersistentType.getMapping();
		OrmEntity childXmlEntity = (OrmEntity) childPersistentType.getMapping();
		
		assertEquals(parentXmlEntity, childXmlEntity.getParentEntity());
		assertEquals(InheritanceType.SINGLE_TABLE, childXmlEntity.getDefaultInheritanceStrategy());
		
		//change root inheritance strategy, verify default is changed for child entity
		parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		assertEquals(InheritanceType.SINGLE_TABLE, parentXmlEntity.getDefaultInheritanceStrategy());
		assertEquals(InheritanceType.TABLE_PER_CLASS, childXmlEntity.getDefaultInheritanceStrategy());
		assertNull(childXmlEntity.getSpecifiedInheritanceStrategy());

		//set specified inheritance strategy in java and verify defaults in xml are correct
		parentXmlEntity.setSpecifiedInheritanceStrategy(null);
		parentXmlEntity.getJavaEntity().setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		assertEquals(InheritanceType.JOINED, parentXmlEntity.getDefaultInheritanceStrategy());
		assertEquals(InheritanceType.JOINED, childXmlEntity.getDefaultInheritanceStrategy());
		assertNull(parentXmlEntity.getSpecifiedInheritanceStrategy());
		assertNull(childXmlEntity.getSpecifiedInheritanceStrategy());
		
		parentPersistentType.getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertEquals(InheritanceType.SINGLE_TABLE, parentXmlEntity.getDefaultInheritanceStrategy());
		assertEquals(InheritanceType.SINGLE_TABLE, childXmlEntity.getDefaultInheritanceStrategy());
	}

	public void testUpdateSpecifiedInheritanceStrategy() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedInheritanceStrategy());
		assertNull(entityResource.getInheritance());
		
		//set strategy in the resource model, verify context model updated
		entityResource.setInheritance(OrmFactory.eINSTANCE.createInheritance());
		entityResource.getInheritance().setStrategy(org.eclipse.jpt.core.resource.orm.InheritanceType.JOINED);
		assertEquals(InheritanceType.JOINED, ormEntity.getSpecifiedInheritanceStrategy());
		assertEquals(org.eclipse.jpt.core.resource.orm.InheritanceType.JOINED, entityResource.getInheritance().getStrategy());
	
		//set strategy to null in the resource model
		entityResource.getInheritance().setStrategy(null);
		assertNull(ormEntity.getSpecifiedInheritanceStrategy());
		assertNull(entityResource.getInheritance().getStrategy());
		
		entityResource.getInheritance().setStrategy(org.eclipse.jpt.core.resource.orm.InheritanceType.SINGLE_TABLE);
		assertEquals(InheritanceType.SINGLE_TABLE, ormEntity.getSpecifiedInheritanceStrategy());
		assertEquals(org.eclipse.jpt.core.resource.orm.InheritanceType.SINGLE_TABLE, entityResource.getInheritance().getStrategy());

		entityResource.setInheritance(null);
		assertNull(ormEntity.getSpecifiedInheritanceStrategy());
		assertNull(entityResource.getInheritance());
	}
	
	public void testModifySpecifiedInheritanceStrategy() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertNull(ormEntity.getSpecifiedInheritanceStrategy());
		assertNull(entityResource.getInheritance());
		
		//set strategy in the context model, verify resource model modified
		ormEntity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		assertEquals(InheritanceType.TABLE_PER_CLASS, ormEntity.getSpecifiedInheritanceStrategy());
		assertEquals(org.eclipse.jpt.core.resource.orm.InheritanceType.TABLE_PER_CLASS, entityResource.getInheritance().getStrategy());
		
		//set strategy to null in the context model
		ormEntity.setSpecifiedInheritanceStrategy(null);
		assertNull(ormEntity.getSpecifiedInheritanceStrategy());
		assertNull(entityResource.getInheritance());	
	}
	
	public void testAddSpecifiedSecondaryTable() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		OrmSecondaryTable secondaryTable = ormEntity.addSpecifiedSecondaryTable(0);
		secondaryTable.setSpecifiedName("FOO");
				
		assertEquals("FOO", entityResource.getSecondaryTables().get(0).getName());
		
		OrmSecondaryTable secondaryTable2 = ormEntity.addSpecifiedSecondaryTable(0);
		secondaryTable2.setSpecifiedName("BAR");
		
		assertEquals("BAR", entityResource.getSecondaryTables().get(0).getName());
		assertEquals("FOO", entityResource.getSecondaryTables().get(1).getName());
		
		OrmSecondaryTable secondaryTable3 = ormEntity.addSpecifiedSecondaryTable(1);
		secondaryTable3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", entityResource.getSecondaryTables().get(0).getName());
		assertEquals("BAZ", entityResource.getSecondaryTables().get(1).getName());
		assertEquals("FOO", entityResource.getSecondaryTables().get(2).getName());
		
		ListIterator<OrmSecondaryTable> secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals(secondaryTable2, secondaryTables.next());
		assertEquals(secondaryTable3, secondaryTables.next());
		assertEquals(secondaryTable, secondaryTables.next());
		
		secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
	}
	
	public void testRemoveSpecifiedSecondaryTable() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		ormEntity.addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		ormEntity.addSpecifiedSecondaryTable(2).setSpecifiedName("BAZ");
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getSecondaryTables().size());
		
		ormEntity.removeSpecifiedSecondaryTable(0);
		assertEquals(2, entityResource.getSecondaryTables().size());
		assertEquals("BAR", entityResource.getSecondaryTables().get(0).getName());
		assertEquals("BAZ", entityResource.getSecondaryTables().get(1).getName());

		ormEntity.removeSpecifiedSecondaryTable(0);
		assertEquals(1, entityResource.getSecondaryTables().size());
		assertEquals("BAZ", entityResource.getSecondaryTables().get(0).getName());
		
		ormEntity.removeSpecifiedSecondaryTable(0);
		assertEquals(0, entityResource.getSecondaryTables().size());
	}
	
	public void testMoveSpecifiedSecondaryTable() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.addSpecifiedSecondaryTable(0).setSpecifiedName("FOO");
		ormEntity.addSpecifiedSecondaryTable(1).setSpecifiedName("BAR");
		ormEntity.addSpecifiedSecondaryTable(2).setSpecifiedName("BAZ");
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getSecondaryTables().size());
		
		
		ormEntity.moveSpecifiedSecondaryTable(2, 0);
		ListIterator<OrmSecondaryTable> secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());

		assertEquals("BAR", entityResource.getSecondaryTables().get(0).getName());
		assertEquals("BAZ", entityResource.getSecondaryTables().get(1).getName());
		assertEquals("FOO", entityResource.getSecondaryTables().get(2).getName());


		ormEntity.moveSpecifiedSecondaryTable(0, 1);
		secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());

		assertEquals("BAZ", entityResource.getSecondaryTables().get(0).getName());
		assertEquals("BAR", entityResource.getSecondaryTables().get(1).getName());
		assertEquals("FOO", entityResource.getSecondaryTables().get(2).getName());
	}
	
	public void testUpdateSecondaryTables() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createXmlSecondaryTableImpl());
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createXmlSecondaryTableImpl());
		entityResource.getSecondaryTables().add(OrmFactory.eINSTANCE.createXmlSecondaryTableImpl());
		
		entityResource.getSecondaryTables().get(0).setName("FOO");
		entityResource.getSecondaryTables().get(1).setName("BAR");
		entityResource.getSecondaryTables().get(2).setName("BAZ");

		ListIterator<OrmSecondaryTable> secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals("FOO", secondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
		
		entityResource.getSecondaryTables().move(2, 0);
		secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());

		entityResource.getSecondaryTables().move(0, 1);
		secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());

		entityResource.getSecondaryTables().remove(1);
		secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());

		entityResource.getSecondaryTables().remove(1);
		secondaryTables = ormEntity.specifiedSecondaryTables();
		assertEquals("BAZ", secondaryTables.next().getName());
		assertFalse(secondaryTables.hasNext());
		
		entityResource.getSecondaryTables().remove(0);
		assertFalse(ormEntity.specifiedSecondaryTables().hasNext());
	}
	
	public void testVirtualSecondaryTables() throws Exception {
		createTestEntityFieldAccess();
		createTestSubType();
	
		OrmPersistentType parentPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType childPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		OrmEntity parentOrmEntity = (OrmEntity) parentPersistentType.getMapping();
		OrmEntity childOrmEntity = (OrmEntity) childPersistentType.getMapping();
		JavaEntity javaEntity = childOrmEntity.getJavaEntity();
		
		JavaSecondaryTable javaSecondaryTableFoo = javaEntity.addSpecifiedSecondaryTable(0);
		javaSecondaryTableFoo.setSpecifiedName("FOO");
		OrmSecondaryTable virtualSecondaryTableFoo = childOrmEntity.virtualSecondaryTables().next();
		assertEquals("FOO", childOrmEntity.secondaryTables().next().getName());
		assertEquals("FOO", virtualSecondaryTableFoo.getName());
		assertEquals(0, virtualSecondaryTableFoo.specifiedPrimaryKeyJoinColumnsSize());
		assertEquals("id", virtualSecondaryTableFoo.getDefaultPrimaryKeyJoinColumn().getDefaultName());
		assertEquals("id", virtualSecondaryTableFoo.getDefaultPrimaryKeyJoinColumn().getDefaultReferencedColumnName());
		
		assertEquals(0, childOrmEntity.specifiedSecondaryTablesSize());
		assertEquals(1, childOrmEntity.virtualSecondaryTablesSize());
		assertEquals(1, childOrmEntity.secondaryTablesSize());
		
		javaEntity.addSpecifiedSecondaryTable(0).setSpecifiedName("BAR");
		ListIterator<OrmSecondaryTable> virtualSecondaryTables = childOrmEntity.virtualSecondaryTables();
		ListIterator<OrmSecondaryTable> secondaryTables = childOrmEntity.secondaryTables();
		assertEquals("BAR", virtualSecondaryTables.next().getName());
		assertEquals("FOO", virtualSecondaryTables.next().getName());
		assertEquals("BAR", secondaryTables.next().getName());
		assertEquals("FOO", secondaryTables.next().getName());
		assertEquals(0, childOrmEntity.specifiedSecondaryTablesSize());
		assertEquals(2, childOrmEntity.virtualSecondaryTablesSize());
		assertEquals(2, childOrmEntity.secondaryTablesSize());
		
		childOrmEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(0, childOrmEntity.virtualSecondaryTablesSize());
		
		childOrmEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertEquals(2, childOrmEntity.virtualSecondaryTablesSize());
		
		
		childOrmEntity.setSecondaryTablesDefinedInXml(true);
		assertEquals(0, childOrmEntity.virtualSecondaryTablesSize());
		assertEquals(2, childOrmEntity.specifiedSecondaryTablesSize());
		assertEquals(2, childOrmEntity.secondaryTablesSize());
		ListIterator<OrmSecondaryTable> specifiedSecondaryTables = childOrmEntity.specifiedSecondaryTables();
		assertEquals("BAR", specifiedSecondaryTables.next().getName());
		OrmSecondaryTable specifiedSecondaryTableFoo = specifiedSecondaryTables.next();
		assertEquals("FOO", specifiedSecondaryTableFoo.getName());
		assertEquals(0, specifiedSecondaryTableFoo.specifiedPrimaryKeyJoinColumnsSize());
		assertEquals("id", specifiedSecondaryTableFoo.getDefaultPrimaryKeyJoinColumn().getDefaultName());
		assertEquals("id", specifiedSecondaryTableFoo.getDefaultPrimaryKeyJoinColumn().getDefaultReferencedColumnName());
		
		
		childOrmEntity.removeSpecifiedSecondaryTable(0);
		assertEquals(0, childOrmEntity.virtualSecondaryTablesSize());
		assertEquals(1, childOrmEntity.specifiedSecondaryTablesSize());
		assertEquals(1, childOrmEntity.secondaryTablesSize());
		assertEquals("FOO", childOrmEntity.specifiedSecondaryTables().next().getName());
		
	
		childOrmEntity.removeSpecifiedSecondaryTable(0);
		assertEquals(0, childOrmEntity.specifiedSecondaryTablesSize());
		assertEquals(2, childOrmEntity.virtualSecondaryTablesSize());
		assertEquals(2, childOrmEntity.secondaryTablesSize());
		virtualSecondaryTables = childOrmEntity.virtualSecondaryTables();
		assertEquals("BAR", virtualSecondaryTables.next().getName());
		assertEquals("FOO", virtualSecondaryTables.next().getName());
		
		
		//add a specified secondary table to the parent, this will not affect virtual secondaryTables in child
		parentOrmEntity.addSpecifiedSecondaryTable(0).setSpecifiedName("PARENT_TABLE");
		assertEquals(2, childOrmEntity.virtualSecondaryTablesSize());	
	}

	//test that inherited tables don't show up in this list
	public void testAssociatedTables() throws Exception {
		
	}
	
	public void testAssociatedTableNamesIncludingInherited() throws Exception {
		
	}
	
	public void testTableNameIsInvalid() throws Exception {
		
	}
	
	public void testMakeEntityEmbeddable() throws Exception {
		OrmPersistentType entityPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity entity = (OrmEntity) entityPersistentType.getMapping();
		entity.setSpecifiedAccess(AccessType.PROPERTY);
		entity.setSpecifiedDiscriminatorValue("DISC_VALUE");
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedMetadataComplete(Boolean.TRUE);
		entity.setSpecifiedName("ENTITY_NAME");
	
		entityPersistentType.setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		
		XmlEmbeddable embeddable = ormResource().getEntityMappings().getEmbeddables().get(0);
		assertEquals("model.Foo", embeddable.getClassName());
		assertEquals(Boolean.TRUE, embeddable.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, embeddable.getAccess());
		
		OrmEmbeddable ormEmbeddable = (OrmEmbeddable) entityPersistentType.getMapping();
		assertEquals("model.Foo", ormEmbeddable.getClass_());
		assertEquals(Boolean.TRUE, ormEmbeddable.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, ormEmbeddable.getSpecifiedAccess());
	}
	
	//TODO test that attribute mappings are not removed when changing type mapping.
	public void testMakeEntityEmbeddable2() throws Exception {
		OrmPersistentType entityPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		OrmEntity entity = (OrmEntity) entityPersistentType.getMapping();
		entity.setSpecifiedAccess(AccessType.PROPERTY);
		entity.setSpecifiedDiscriminatorValue("DISC_VALUE");
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedMetadataComplete(Boolean.TRUE);
		entity.setSpecifiedName("ENTITY_NAME");
//		entityPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
	
		entityPersistentType.setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		
		XmlEmbeddable embeddable = ormResource().getEntityMappings().getEmbeddables().get(0);
		assertEquals("model.Foo", embeddable.getClassName());
		assertEquals(Boolean.TRUE, embeddable.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, embeddable.getAccess());
//		assertEquals("basicMapping", embeddable.getAttributes().getBasics().get(0).getName());
		
		OrmEmbeddable ormEmbeddable = (OrmEmbeddable) entityPersistentType.getMapping();
		assertEquals("model.Foo", ormEmbeddable.getClass_());
		assertEquals(Boolean.TRUE, ormEmbeddable.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, ormEmbeddable.getSpecifiedAccess());
//		assertEquals("basicMapping", ormEmbeddable.persistentType().attributes().next().getName());
	}
	
	public void testMakeEntityMappedSuperclass() throws Exception {
		OrmPersistentType entityPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity entity = (OrmEntity) entityPersistentType.getMapping();
		entity.setSpecifiedAccess(AccessType.PROPERTY);
		entity.setSpecifiedDiscriminatorValue("DISC_VALUE");
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedMetadataComplete(Boolean.TRUE);
		entity.setSpecifiedName("ENTITY_NAME");
	
		entityPersistentType.setMappingKey(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		
		XmlMappedSuperclass mappedSuperclass = ormResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertEquals("model.Foo", mappedSuperclass.getClassName());
		assertEquals(Boolean.TRUE, mappedSuperclass.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, mappedSuperclass.getAccess());
		
		OrmMappedSuperclass ormMappedSuperclass = (OrmMappedSuperclass) entityPersistentType.getMapping();
		assertEquals("model.Foo", ormMappedSuperclass.getClass_());
		assertEquals(Boolean.TRUE, ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, ormMappedSuperclass.getSpecifiedAccess());
	}
	
	public void testMakeEntityMappedSuperclass2() throws Exception {
		entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo2");
		OrmPersistentType entityPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity entity = (OrmEntity) entityPersistentType.getMapping();
		entity.setSpecifiedAccess(AccessType.PROPERTY);
		entity.setSpecifiedDiscriminatorValue("DISC_VALUE");
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedMetadataComplete(Boolean.TRUE);
		entity.setSpecifiedName("ENTITY_NAME");
	
		entityPersistentType.setMappingKey(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		
		XmlMappedSuperclass mappedSuperclass = ormResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertEquals("model.Foo", mappedSuperclass.getClassName());
		assertEquals(Boolean.TRUE, mappedSuperclass.getMetadataComplete());
		assertEquals(org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY, mappedSuperclass.getAccess());
		
		OrmMappedSuperclass ormMappedSuperclass = (OrmMappedSuperclass) entityPersistentType.getMapping();
		assertEquals("model.Foo", ormMappedSuperclass.getClass_());
		assertEquals(Boolean.TRUE, ormMappedSuperclass.getSpecifiedMetadataComplete());
		assertEquals(AccessType.PROPERTY, ormMappedSuperclass.getSpecifiedAccess());
	}

	
	public void testAddSequenceGenerator() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNull(ormEntity.getSequenceGenerator());
		assertNull(entityResource.getSequenceGenerator());
		
		ormEntity.addSequenceGenerator();
		
		assertNotNull(entityResource.getSequenceGenerator());
		assertNotNull(ormEntity.getSequenceGenerator());
				
		//try adding another sequence generator, should get an IllegalStateException
		try {
			ormEntity.addSequenceGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveSequenceGenerator() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNull(ormEntity.getSequenceGenerator());
		assertNull(entityResource.getSequenceGenerator());

		ormEntity.addSequenceGenerator();
		assertNotNull(entityResource.getSequenceGenerator());
		assertNotNull(ormEntity.getSequenceGenerator());

		ormEntity.removeSequenceGenerator();
		
		assertNull(ormEntity.getSequenceGenerator());
		assertNull(entityResource.getSequenceGenerator());

		//try removing the sequence generator again, should get an IllegalStateException
		try {
			ormEntity.removeSequenceGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}

	public void testUpdateSequenceGenerator() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNull(ormEntity.getSequenceGenerator());
		assertNull(entityResource.getSequenceGenerator());
		assertEquals(0, CollectionTools.size(ormEntity.getPersistenceUnit().allGenerators()));
		
		entityResource.setSequenceGenerator(OrmFactory.eINSTANCE.createXmlSequenceGeneratorImpl());
				
		assertNotNull(ormEntity.getSequenceGenerator());
		assertNotNull(entityResource.getSequenceGenerator());
		assertEquals(1, CollectionTools.size(ormEntity.getPersistenceUnit().allGenerators()));
		
		ormEntity.getSequenceGenerator().setName("foo");
		assertEquals(1, CollectionTools.size(ormEntity.getPersistenceUnit().allGenerators()));

		entityResource.setSequenceGenerator(null);
		assertNull(ormEntity.getSequenceGenerator());
		assertNull(entityResource.getSequenceGenerator());
		assertEquals(0, CollectionTools.size(ormEntity.getPersistenceUnit().allGenerators()));
	}
	
	public void testAddTableGenerator() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNull(ormEntity.getTableGenerator());
		assertNull(entityResource.getTableGenerator());
		
		ormEntity.addTableGenerator();
		
		assertNotNull(entityResource.getTableGenerator());
		assertNotNull(ormEntity.getTableGenerator());
				
		//try adding another table generator, should get an IllegalStateException
		try {
			ormEntity.addTableGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveTableGenerator() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNull(ormEntity.getTableGenerator());
		assertNull(entityResource.getTableGenerator());

		ormEntity.addTableGenerator();
		assertNotNull(entityResource.getTableGenerator());
		assertNotNull(ormEntity.getTableGenerator());

		ormEntity.removeTableGenerator();
		
		assertNull(ormEntity.getTableGenerator());
		assertNull(entityResource.getTableGenerator());

		//try removing the table generator again, should get an IllegalStateException
		try {
			ormEntity.removeTableGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testUpdateTableGenerator() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNull(ormEntity.getTableGenerator());
		assertNull(entityResource.getTableGenerator());
		assertEquals(0, CollectionTools.size(ormEntity.getPersistenceUnit().allGenerators()));
		
		entityResource.setTableGenerator(OrmFactory.eINSTANCE.createXmlTableGeneratorImpl());
				
		assertNotNull(ormEntity.getTableGenerator());
		assertNotNull(entityResource.getTableGenerator());
		assertEquals(1, CollectionTools.size(ormEntity.getPersistenceUnit().allGenerators()));

		ormEntity.getTableGenerator().setName("foo");
		assertEquals(1, CollectionTools.size(ormEntity.getPersistenceUnit().allGenerators()));
		
		entityResource.setTableGenerator(null);
		assertNull(ormEntity.getTableGenerator());
		assertNull(entityResource.getTableGenerator());
		assertEquals(0, CollectionTools.size(ormEntity.getPersistenceUnit().allGenerators()));
	}
	
	public void testUpdateDiscriminatorColumn() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNotNull(ormEntity.getDiscriminatorColumn());

		entityResource.setDiscriminatorColumn(OrmFactory.eINSTANCE.createXmlDiscriminatorColumn());
		entityResource.getDiscriminatorColumn().setName("FOO");
		
		assertEquals("FOO", ormEntity.getDiscriminatorColumn().getSpecifiedName());
		assertEquals("FOO", entityResource.getDiscriminatorColumn().getName());
		
		entityResource.getDiscriminatorColumn().setName(null);
		
		assertNull(ormEntity.getDiscriminatorColumn().getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn().getName());

		entityResource.setDiscriminatorColumn(null);
		
		assertNotNull(ormEntity.getDiscriminatorColumn());
	}
	
	public void testUpdateDiscriminatorValue() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNull(ormEntity.getSpecifiedDiscriminatorValue());
		assertNull(entityResource.getDiscriminatorValue());

		entityResource.setDiscriminatorValue("FOO");
		
		assertEquals("FOO", ormEntity.getSpecifiedDiscriminatorValue());
		assertEquals("FOO", entityResource.getDiscriminatorValue());
		
		entityResource.setDiscriminatorValue(null);
		
		assertNull(ormEntity.getSpecifiedDiscriminatorValue());
		assertNull(entityResource.getDiscriminatorValue());
	}
	
	public void testModifyDiscriminatorValue() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		
		assertNull(ormEntity.getSpecifiedDiscriminatorValue());
		assertNull(entityResource.getDiscriminatorValue());

		ormEntity.setSpecifiedDiscriminatorValue("FOO");
		
		assertEquals("FOO", ormEntity.getSpecifiedDiscriminatorValue());
		assertEquals("FOO", entityResource.getDiscriminatorValue());
		
		ormEntity.setSpecifiedDiscriminatorValue(null);
		
		assertNull(ormEntity.getSpecifiedDiscriminatorValue());
		assertNull(entityResource.getDiscriminatorValue());
	}
	
	public void testAddSpecifiedPrimaryKeyJoinColumn() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		OrmPrimaryKeyJoinColumn primaryKeyJoinColumn = ormEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		primaryKeyJoinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		
		OrmPrimaryKeyJoinColumn primaryKeyJoinColumn2 = ormEntity.addSpecifiedPrimaryKeyJoinColumn(0);
		primaryKeyJoinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("FOO", entityResource.getPrimaryKeyJoinColumns().get(1).getName());
		
		OrmPrimaryKeyJoinColumn primaryKeyJoinColumn3 = ormEntity.addSpecifiedPrimaryKeyJoinColumn(1);
		primaryKeyJoinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", entityResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", entityResource.getPrimaryKeyJoinColumns().get(2).getName());
		
		ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals(primaryKeyJoinColumn2, primaryKeyJoinColumns.next());
		assertEquals(primaryKeyJoinColumn3, primaryKeyJoinColumns.next());
		assertEquals(primaryKeyJoinColumn, primaryKeyJoinColumns.next());
		
		primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		ormEntity.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		ormEntity.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getPrimaryKeyJoinColumns().size());
		
		ormEntity.removeSpecifiedPrimaryKeyJoinColumn(0);
		assertEquals(2, entityResource.getPrimaryKeyJoinColumns().size());
		assertEquals("BAR", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", entityResource.getPrimaryKeyJoinColumns().get(1).getName());

		ormEntity.removeSpecifiedPrimaryKeyJoinColumn(0);
		assertEquals(1, entityResource.getPrimaryKeyJoinColumns().size());
		assertEquals("BAZ", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		
		ormEntity.removeSpecifiedPrimaryKeyJoinColumn(0);
		assertEquals(0, entityResource.getPrimaryKeyJoinColumns().size());
	}
	
	public void testMoveSpecifiedPrimaryKeyJoinColumn() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.addSpecifiedPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		ormEntity.addSpecifiedPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		ormEntity.addSpecifiedPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getPrimaryKeyJoinColumns().size());
		
		
		ormEntity.moveSpecifiedPrimaryKeyJoinColumn(2, 0);
		ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());

		assertEquals("BAR", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", entityResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", entityResource.getPrimaryKeyJoinColumns().get(2).getName());


		ormEntity.moveSpecifiedPrimaryKeyJoinColumn(0, 1);
		primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());

		assertEquals("BAZ", entityResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAR", entityResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", entityResource.getPrimaryKeyJoinColumns().get(2).getName());
	}
	
	public void testUpdatePrimaryKeyJoinColumns() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		entityResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumnImpl());
		entityResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumnImpl());
		entityResource.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumnImpl());
		
		entityResource.getPrimaryKeyJoinColumns().get(0).setName("FOO");
		entityResource.getPrimaryKeyJoinColumns().get(1).setName("BAR");
		entityResource.getPrimaryKeyJoinColumns().get(2).setName("BAZ");

		ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		entityResource.getPrimaryKeyJoinColumns().move(2, 0);
		primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		entityResource.getPrimaryKeyJoinColumns().move(0, 1);
		primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		entityResource.getPrimaryKeyJoinColumns().remove(1);
		primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());

		entityResource.getPrimaryKeyJoinColumns().remove(1);
		primaryKeyJoinColumns = ormEntity.specifiedPrimaryKeyJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getName());
		assertFalse(primaryKeyJoinColumns.hasNext());
		
		entityResource.getPrimaryKeyJoinColumns().remove(0);
		assertFalse(ormEntity.specifiedPrimaryKeyJoinColumns().hasNext());
	}

//	public void testAddSpecifiedAttributeOverride() throws Exception {
//		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
//		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
//
//		OrmAttributeOverride attributeOverride = ormEntity.addSpecifiedAttributeOverride(0);
//		attributeOverride.setName("FOO");
//				
//		assertEquals("FOO", entityResource.getAttributeOverrides().get(0).getName());
//		
//		OrmAttributeOverride attributeOverride2 = ormEntity.addSpecifiedAttributeOverride(0);
//		attributeOverride2.setName("BAR");
//		
//		assertEquals("BAR", entityResource.getAttributeOverrides().get(0).getName());
//		assertEquals("FOO", entityResource.getAttributeOverrides().get(1).getName());
//		
//		OrmAttributeOverride attributeOverride3 = ormEntity.addSpecifiedAttributeOverride(1);
//		attributeOverride3.setName("BAZ");
//		
//		assertEquals("BAR", entityResource.getAttributeOverrides().get(0).getName());
//		assertEquals("BAZ", entityResource.getAttributeOverrides().get(1).getName());
//		assertEquals("FOO", entityResource.getAttributeOverrides().get(2).getName());
//		
//		ListIterator<OrmAttributeOverride> attributeOverrides = ormEntity.specifiedAttributeOverrides();
//		assertEquals(attributeOverride2, attributeOverrides.next());
//		assertEquals(attributeOverride3, attributeOverrides.next());
//		assertEquals(attributeOverride, attributeOverrides.next());
//		
//		attributeOverrides = ormEntity.specifiedAttributeOverrides();
//		assertEquals("BAR", attributeOverrides.next().getName());
//		assertEquals("BAZ", attributeOverrides.next().getName());
//		assertEquals("FOO", attributeOverrides.next().getName());
//	}
//	
//	public void testRemoveSpecifiedAttributeOverride() throws Exception {
//		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
//
//		ormEntity.addSpecifiedAttributeOverride(0).setName("FOO");
//		ormEntity.addSpecifiedAttributeOverride(1).setName("BAR");
//		ormEntity.addSpecifiedAttributeOverride(2).setName("BAZ");
//		
//		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
//		assertEquals(3, entityResource.getAttributeOverrides().size());
//		
//		ormEntity.removeSpecifiedAttributeOverride(0);
//		assertEquals(2, entityResource.getAttributeOverrides().size());
//		assertEquals("BAR", entityResource.getAttributeOverrides().get(0).getName());
//		assertEquals("BAZ", entityResource.getAttributeOverrides().get(1).getName());
//
//		ormEntity.removeSpecifiedAttributeOverride(0);
//		assertEquals(1, entityResource.getAttributeOverrides().size());
//		assertEquals("BAZ", entityResource.getAttributeOverrides().get(0).getName());
//		
//		ormEntity.removeSpecifiedAttributeOverride(0);
//		assertEquals(0, entityResource.getAttributeOverrides().size());
//	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverrideImpl());
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverrideImpl());
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverrideImpl());
		
		entityResource.getAttributeOverrides().get(0).setName("FOO");
		entityResource.getAttributeOverrides().get(1).setName("BAR");
		entityResource.getAttributeOverrides().get(2).setName("BAZ");
		
		assertEquals(3, entityResource.getAttributeOverrides().size());
		
		
		ormEntity.moveSpecifiedAttributeOverride(2, 0);
		ListIterator<OrmAttributeOverride> attributeOverrides = ormEntity.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAR", entityResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAZ", entityResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", entityResource.getAttributeOverrides().get(2).getName());


		ormEntity.moveSpecifiedAttributeOverride(0, 1);
		attributeOverrides = ormEntity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAZ", entityResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAR", entityResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", entityResource.getAttributeOverrides().get(2).getName());
	}
	
	public void testUpdateAttributeOverrides() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverrideImpl());
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverrideImpl());
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverrideImpl());
		
		entityResource.getAttributeOverrides().get(0).setName("FOO");
		entityResource.getAttributeOverrides().get(1).setName("BAR");
		entityResource.getAttributeOverrides().get(2).setName("BAZ");

		ListIterator<OrmAttributeOverride> attributeOverrides = ormEntity.specifiedAttributeOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		entityResource.getAttributeOverrides().move(2, 0);
		attributeOverrides = ormEntity.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		entityResource.getAttributeOverrides().move(0, 1);
		attributeOverrides = ormEntity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		entityResource.getAttributeOverrides().remove(1);
		attributeOverrides = ormEntity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		entityResource.getAttributeOverrides().remove(1);
		attributeOverrides = ormEntity.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		entityResource.getAttributeOverrides().remove(0);
		assertFalse(ormEntity.specifiedAttributeOverrides().hasNext());
	}
	
//	public void testAddSpecifiedAssociationOverride() throws Exception {
//		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
//		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
//
//		OrmAssociationOverride associationOverride = ormEntity.addSpecifiedAssociationOverride(0);
//		associationOverride.setName("FOO");
//				
//		assertEquals("FOO", entityResource.getAssociationOverrides().get(0).getName());
//		
//		OrmAssociationOverride associationOverride2 = ormEntity.addSpecifiedAssociationOverride(0);
//		associationOverride2.setName("BAR");
//		
//		assertEquals("BAR", entityResource.getAssociationOverrides().get(0).getName());
//		assertEquals("FOO", entityResource.getAssociationOverrides().get(1).getName());
//		
//		OrmAssociationOverride associationOverride3 = ormEntity.addSpecifiedAssociationOverride(1);
//		associationOverride3.setName("BAZ");
//		
//		assertEquals("BAR", entityResource.getAssociationOverrides().get(0).getName());
//		assertEquals("BAZ", entityResource.getAssociationOverrides().get(1).getName());
//		assertEquals("FOO", entityResource.getAssociationOverrides().get(2).getName());
//		
//		ListIterator<OrmAssociationOverride> associationOverrides = ormEntity.specifiedAssociationOverrides();
//		assertEquals(associationOverride2, associationOverrides.next());
//		assertEquals(associationOverride3, associationOverrides.next());
//		assertEquals(associationOverride, associationOverrides.next());
//		
//		associationOverrides = ormEntity.specifiedAssociationOverrides();
//		assertEquals("BAR", associationOverrides.next().getName());
//		assertEquals("BAZ", associationOverrides.next().getName());
//		assertEquals("FOO", associationOverrides.next().getName());
//	}
//	
//	public void testRemoveSpecifiedAssociationOverride() throws Exception {
//		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
//
//		ormEntity.addSpecifiedAssociationOverride(0).setName("FOO");
//		ormEntity.addSpecifiedAssociationOverride(1).setName("BAR");
//		ormEntity.addSpecifiedAssociationOverride(2).setName("BAZ");
//		
//		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
//		assertEquals(3, entityResource.getAssociationOverrides().size());
//		
//		ormEntity.removeSpecifiedAssociationOverride(0);
//		assertEquals(2, entityResource.getAssociationOverrides().size());
//		assertEquals("BAR", entityResource.getAssociationOverrides().get(0).getName());
//		assertEquals("BAZ", entityResource.getAssociationOverrides().get(1).getName());
//
//		ormEntity.removeSpecifiedAssociationOverride(0);
//		assertEquals(1, entityResource.getAssociationOverrides().size());
//		assertEquals("BAZ", entityResource.getAssociationOverrides().get(0).getName());
//		
//		ormEntity.removeSpecifiedAssociationOverride(0);
//		assertEquals(0, entityResource.getAssociationOverrides().size());
//	}
	
	public void testMoveSpecifiedAssociationOverride() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverrideImpl());
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverrideImpl());
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverrideImpl());
		
		entityResource.getAssociationOverrides().get(0).setName("FOO");
		entityResource.getAssociationOverrides().get(1).setName("BAR");
		entityResource.getAssociationOverrides().get(2).setName("BAZ");
		
		assertEquals(3, entityResource.getAssociationOverrides().size());
		
		
		ormEntity.moveSpecifiedAssociationOverride(2, 0);
		ListIterator<OrmAssociationOverride> associationOverrides = ormEntity.specifiedAssociationOverrides();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());

		assertEquals("BAR", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("BAZ", entityResource.getAssociationOverrides().get(1).getName());
		assertEquals("FOO", entityResource.getAssociationOverrides().get(2).getName());


		ormEntity.moveSpecifiedAssociationOverride(0, 1);
		associationOverrides = ormEntity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());

		assertEquals("BAZ", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("BAR", entityResource.getAssociationOverrides().get(1).getName());
		assertEquals("FOO", entityResource.getAssociationOverrides().get(2).getName());
	}
	
	public void testUpdateAssociationOverrides() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverrideImpl());
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverrideImpl());
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverrideImpl());
		
		entityResource.getAssociationOverrides().get(0).setName("FOO");
		entityResource.getAssociationOverrides().get(1).setName("BAR");
		entityResource.getAssociationOverrides().get(2).setName("BAZ");

		ListIterator<OrmAssociationOverride> associationOverrides = ormEntity.specifiedAssociationOverrides();
		assertEquals("FOO", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		entityResource.getAssociationOverrides().move(2, 0);
		associationOverrides = ormEntity.specifiedAssociationOverrides();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		entityResource.getAssociationOverrides().move(0, 1);
		associationOverrides = ormEntity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		entityResource.getAssociationOverrides().remove(1);
		associationOverrides = ormEntity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		entityResource.getAssociationOverrides().remove(1);
		associationOverrides = ormEntity.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		entityResource.getAssociationOverrides().remove(0);
		assertFalse(ormEntity.specifiedAssociationOverrides().hasNext());
	}

	
	public void testAddNamedQuery() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		OrmNamedQuery namedQuery = ormEntity.addNamedQuery(0);
		namedQuery.setName("FOO");
				
		assertEquals("FOO", entityResource.getNamedQueries().get(0).getName());
		
		OrmNamedQuery namedQuery2 = ormEntity.addNamedQuery(0);
		namedQuery2.setName("BAR");
		
		assertEquals("BAR", entityResource.getNamedQueries().get(0).getName());
		assertEquals("FOO", entityResource.getNamedQueries().get(1).getName());
		
		OrmNamedQuery namedQuery3 = ormEntity.addNamedQuery(1);
		namedQuery3.setName("BAZ");
		
		assertEquals("BAR", entityResource.getNamedQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedQueries().get(2).getName());
		
		ListIterator<OrmNamedQuery> namedQueries = ormEntity.namedQueries();
		assertEquals(namedQuery2, namedQueries.next());
		assertEquals(namedQuery3, namedQueries.next());
		assertEquals(namedQuery, namedQueries.next());
		
		namedQueries = ormEntity.namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
	}
	
	public void testRemoveNamedQuery() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.addNamedQuery(0).setName("FOO");
		ormEntity.addNamedQuery(1).setName("BAR");
		ormEntity.addNamedQuery(2).setName("BAZ");
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getNamedQueries().size());
		
		ormEntity.removeNamedQuery(0);
		assertEquals(2, entityResource.getNamedQueries().size());
		assertEquals("BAR", entityResource.getNamedQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedQueries().get(1).getName());

		ormEntity.removeNamedQuery(0);
		assertEquals(1, entityResource.getNamedQueries().size());
		assertEquals("BAZ", entityResource.getNamedQueries().get(0).getName());
		
		ormEntity.removeNamedQuery(0);
		assertEquals(0, entityResource.getNamedQueries().size());
	}
	
	public void testMoveNamedQuery() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.addNamedQuery(0).setName("FOO");
		ormEntity.addNamedQuery(1).setName("BAR");
		ormEntity.addNamedQuery(2).setName("BAZ");
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getNamedQueries().size());
		
		
		ormEntity.moveNamedQuery(2, 0);
		ListIterator<OrmNamedQuery> namedQueries = ormEntity.namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		assertEquals("BAR", entityResource.getNamedQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedQueries().get(2).getName());


		ormEntity.moveNamedQuery(0, 1);
		namedQueries = ormEntity.namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		assertEquals("BAZ", entityResource.getNamedQueries().get(0).getName());
		assertEquals("BAR", entityResource.getNamedQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedQueries().get(2).getName());
	}
	
	public void testUpdateNamedQueries() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		
		assertEquals(0, CollectionTools.size(ormEntity.getPersistenceUnit().allQueries()));
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		entityResource.getNamedQueries().add(OrmFactory.eINSTANCE.createXmlNamedQuery());
		entityResource.getNamedQueries().add(OrmFactory.eINSTANCE.createXmlNamedQuery());
		entityResource.getNamedQueries().add(OrmFactory.eINSTANCE.createXmlNamedQuery());
		entityResource.getNamedQueries().get(0).setName("FOO");
		entityResource.getNamedQueries().get(1).setName("BAR");
		entityResource.getNamedQueries().get(2).setName("BAZ");
		
		ListIterator<OrmNamedQuery> namedQueries = ormEntity.namedQueries();
		assertEquals("FOO", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(3, CollectionTools.size(ormEntity.getPersistenceUnit().allQueries()));
		
		entityResource.getNamedQueries().move(2, 0);
		namedQueries = ormEntity.namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		entityResource.getNamedQueries().move(0, 1);
		namedQueries = ormEntity.namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		entityResource.getNamedQueries().remove(1);
		namedQueries = ormEntity.namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(2, CollectionTools.size(ormEntity.getPersistenceUnit().allQueries()));
		
		entityResource.getNamedQueries().remove(1);
		namedQueries = ormEntity.namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		assertEquals(1, CollectionTools.size(ormEntity.getPersistenceUnit().allQueries()));
		
		entityResource.getNamedQueries().remove(0);
		assertFalse(ormEntity.namedQueries().hasNext());
		assertEquals(0, CollectionTools.size(ormEntity.getPersistenceUnit().allQueries()));
	}
	
	public void testAddNamedNativeQuery() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		OrmNamedNativeQuery namedNativeQuery = ormEntity.addNamedNativeQuery(0);
		namedNativeQuery.setName("FOO");
				
		assertEquals("FOO", entityResource.getNamedNativeQueries().get(0).getName());
		
		OrmNamedNativeQuery namedNativeQuery2 = ormEntity.addNamedNativeQuery(0);
		namedNativeQuery2.setName("BAR");
		
		assertEquals("BAR", entityResource.getNamedNativeQueries().get(0).getName());
		assertEquals("FOO", entityResource.getNamedNativeQueries().get(1).getName());
		
		OrmNamedNativeQuery namedNativeQuery3 = ormEntity.addNamedNativeQuery(1);
		namedNativeQuery3.setName("BAZ");
		
		assertEquals("BAR", entityResource.getNamedNativeQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedNativeQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedNativeQueries().get(2).getName());
		
		ListIterator<OrmNamedNativeQuery> namedNativeQueries = ormEntity.namedNativeQueries();
		assertEquals(namedNativeQuery2, namedNativeQueries.next());
		assertEquals(namedNativeQuery3, namedNativeQueries.next());
		assertEquals(namedNativeQuery, namedNativeQueries.next());
		
		namedNativeQueries = ormEntity.namedNativeQueries();
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());
	}
	
	public void testRemoveNamedNativeQuery() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.addNamedNativeQuery(0).setName("FOO");
		ormEntity.addNamedNativeQuery(1).setName("BAR");
		ormEntity.addNamedNativeQuery(2).setName("BAZ");
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getNamedNativeQueries().size());
		
		ormEntity.removeNamedNativeQuery(0);
		assertEquals(2, entityResource.getNamedNativeQueries().size());
		assertEquals("BAR", entityResource.getNamedNativeQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedNativeQueries().get(1).getName());

		ormEntity.removeNamedNativeQuery(0);
		assertEquals(1, entityResource.getNamedNativeQueries().size());
		assertEquals("BAZ", entityResource.getNamedNativeQueries().get(0).getName());
		
		ormEntity.removeNamedNativeQuery(0);
		assertEquals(0, entityResource.getNamedNativeQueries().size());
	}
	
	public void testMoveNamedNativeQuery() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();

		ormEntity.addNamedNativeQuery(0).setName("FOO");
		ormEntity.addNamedNativeQuery(1).setName("BAR");
		ormEntity.addNamedNativeQuery(2).setName("BAZ");
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getNamedNativeQueries().size());
		
		
		ormEntity.moveNamedNativeQuery(2, 0);
		ListIterator<OrmNamedNativeQuery> namedNativeQueries = ormEntity.namedNativeQueries();
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());

		assertEquals("BAR", entityResource.getNamedNativeQueries().get(0).getName());
		assertEquals("BAZ", entityResource.getNamedNativeQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedNativeQueries().get(2).getName());


		ormEntity.moveNamedNativeQuery(0, 1);
		namedNativeQueries = ormEntity.namedNativeQueries();
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());

		assertEquals("BAZ", entityResource.getNamedNativeQueries().get(0).getName());
		assertEquals("BAR", entityResource.getNamedNativeQueries().get(1).getName());
		assertEquals("FOO", entityResource.getNamedNativeQueries().get(2).getName());
	}
	
	public void testUpdateNamedNativeQueries() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		
		assertEquals(0, CollectionTools.size(ormEntity.getPersistenceUnit().allQueries()));
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		entityResource.getNamedNativeQueries().add(OrmFactory.eINSTANCE.createXmlNamedNativeQuery());
		entityResource.getNamedNativeQueries().add(OrmFactory.eINSTANCE.createXmlNamedNativeQuery());
		entityResource.getNamedNativeQueries().add(OrmFactory.eINSTANCE.createXmlNamedNativeQuery());
		entityResource.getNamedNativeQueries().get(0).setName("FOO");
		entityResource.getNamedNativeQueries().get(1).setName("BAR");
		entityResource.getNamedNativeQueries().get(2).setName("BAZ");
		ListIterator<OrmNamedNativeQuery> namedNativeQueries = ormEntity.namedNativeQueries();
		assertEquals("FOO", namedNativeQueries.next().getName());
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertFalse(namedNativeQueries.hasNext());
		assertEquals(3, CollectionTools.size(ormEntity.getPersistenceUnit().allQueries()));
		
		entityResource.getNamedNativeQueries().move(2, 0);
		namedNativeQueries = ormEntity.namedNativeQueries();
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());
		assertFalse(namedNativeQueries.hasNext());
		
		entityResource.getNamedNativeQueries().move(0, 1);
		namedNativeQueries = ormEntity.namedNativeQueries();
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("BAR", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());
		assertFalse(namedNativeQueries.hasNext());
		
		entityResource.getNamedNativeQueries().remove(1);
		namedNativeQueries = ormEntity.namedNativeQueries();
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertEquals("FOO", namedNativeQueries.next().getName());
		assertFalse(namedNativeQueries.hasNext());
		assertEquals(2, CollectionTools.size(ormEntity.getPersistenceUnit().allQueries()));
		
		entityResource.getNamedNativeQueries().remove(1);
		namedNativeQueries = ormEntity.namedNativeQueries();
		assertEquals("BAZ", namedNativeQueries.next().getName());
		assertFalse(namedNativeQueries.hasNext());
		assertEquals(1, CollectionTools.size(ormEntity.getPersistenceUnit().allQueries()));
		
		entityResource.getNamedNativeQueries().remove(0);
		assertFalse(ormEntity.namedNativeQueries().hasNext());
		assertEquals(0, CollectionTools.size(ormEntity.getPersistenceUnit().allQueries()));
	}
	
	public void testUpdateIdClass() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		assertNull(ormEntity.getIdClass());
		assertNull(entityResource.getIdClass());
		
		entityResource.setIdClass(OrmFactory.eINSTANCE.createXmlIdClass());
		
		assertNull(ormEntity.getIdClass());
		assertNotNull(entityResource.getIdClass());
		
		entityResource.getIdClass().setClassName("model.Foo");
		assertEquals("model.Foo", ormEntity.getIdClass());
		assertEquals("model.Foo", entityResource.getIdClass().getClassName());
		
		//test setting  @IdClass value to null, id-class tag is not removed
		entityResource.getIdClass().setClassName(null);
		assertNull(ormEntity.getIdClass());
		assertNotNull(entityResource.getIdClass());
		
		//reset @IdClass value and then remove id-class tag
		entityResource.setIdClass(OrmFactory.eINSTANCE.createXmlIdClass());
		entityResource.getIdClass().setClassName("model.Foo");
		entityResource.setIdClass(null);
		
		assertNull(ormEntity.getIdClass());
		assertNull(entityResource.getIdClass());
	}
	
	public void testModifyIdClass() throws Exception {
		OrmPersistentType persistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) persistentType.getMapping();
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);

		assertNull(ormEntity.getIdClass());
		assertNull(entityResource.getIdClass());
			
		ormEntity.setIdClass("model.Foo");
		assertEquals("model.Foo", entityResource.getIdClass().getClassName());
		assertEquals("model.Foo", ormEntity.getIdClass());
		
		ormEntity.setIdClass(null);
		assertNull(ormEntity.getIdClass());
		assertNull(entityResource.getIdClass());
	}

	
	public void testGetPrimaryKeyColumnNameWithAttributeOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType parentPersistentType = entityMappings().addOrmPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType childPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		OrmEntity childXmlEntity = (OrmEntity) childPersistentType.getMapping();
		
		assertNull(childXmlEntity.getPrimaryKeyColumnName());

		parentPersistentType.getAttributeNamed("id").makeSpecified(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals("id", childXmlEntity.getPrimaryKeyColumnName());
		
		((OrmIdMapping) parentPersistentType.getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName("MY_ID");
		assertEquals("MY_ID", childXmlEntity.getPrimaryKeyColumnName());

		//TODO once bug 228718 is fixed
//		OrmAttributeOverride ormAttributeOverride = childXmlEntity.virtualAttributeOverrides().next();
//		assertEquals("id", ormAttributeOverride.getName());
//		
//		ormAttributeOverride = (OrmAttributeOverride) ormAttributeOverride.setVirtual(false);
//		ormAttributeOverride.getColumn().setSpecifiedName("ID");
//		assertEquals("ID", childXmlEntity.getPrimaryKeyColumnName());
	}

}
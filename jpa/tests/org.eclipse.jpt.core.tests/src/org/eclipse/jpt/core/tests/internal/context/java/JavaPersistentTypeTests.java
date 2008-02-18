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
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.context.persistence.IClassRef;
import org.eclipse.jpt.core.internal.resource.java.Embeddable;
import org.eclipse.jpt.core.internal.resource.java.Entity;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaPersistentTypeTests extends ContextModelTestCase
{
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		persistenceResource().save(null);
	}

	
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
		
	private IType createTestEntity() throws Exception {
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

	private IType createTestEntityAnnotatedField() throws Exception {
		createEntityAnnotation();
		this.createAnnotationAndMembers("Id", "");
	
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

	private IType createTestEntityAnnotatedMethod() throws Exception {
		createEntityAnnotation();
		this.createAnnotationAndMembers("Id", "");
	
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
	private IType createTestEntityAnnotatedFieldAndMethod() throws Exception {
		createEntityAnnotation();
		this.createAnnotationAndMembers("Id", "");
	
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
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}
	
	private IType createTestSubType() throws Exception {
		return this.createTestType(PACKAGE_NAME, "AnnotationTestTypeChild.java", "AnnotationTestTypeChild", new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendExtendsImplementsTo(StringBuilder sb) {
				sb.append("extends " + TYPE_NAME + " ");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}

		});
	}

	private IType createTestSubTypeWithFieldAnnotation() throws Exception {
		return this.createTestType(PACKAGE_NAME, "AnnotationTestTypeChild.java", "AnnotationTestTypeChild", new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendExtendsImplementsTo(StringBuilder sb) {
				sb.append("extends " + TYPE_NAME + " ");
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
	
	private IType createTestSubTypeWithMethodAnnotation() throws Exception {
		return this.createTestType(PACKAGE_NAME, "AnnotationTestTypeChild.java", "AnnotationTestTypeChild", new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendExtendsImplementsTo(StringBuilder sb) {
				sb.append("extends " + TYPE_NAME + " ");
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
	
	private IType createTestSubTypeNonPersistent() throws Exception {
		return this.createTestType(PACKAGE_NAME, "AnnotationTestTypeChild.java", "AnnotationTestTypeChild", new DefaultAnnotationWriter() {
			@Override
			public void appendExtendsImplementsTo(StringBuilder sb) {
				sb.append("extends " + TYPE_NAME + " ");
			}
		});
	}

	private IType createTestSubTypePersistentExtendsNonPersistent() throws Exception {
		return this.createTestType(PACKAGE_NAME, "AnnotationTestTypeChild2.java", "AnnotationTestTypeChild2", new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendExtendsImplementsTo(StringBuilder sb) {
				sb.append("extends AnnotationTestTypeChild ");
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
		
	public JavaPersistentTypeTests(String name) {
		super(name);
	}
	
	public void testGetName() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, javaPersistentType().getName());
	}
	
	public void testGetAccessNothingAnnotated() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(AccessType.FIELD, javaPersistentType().access());
	}

	public void testAccessField() throws Exception {
		createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(AccessType.FIELD, javaPersistentType().access());
	}
	
	public void testAccessProperty() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(AccessType.PROPERTY, javaPersistentType().access());
	}
	
	public void testAccessFieldAndMethodAnnotated() throws Exception {
		createTestEntityAnnotatedFieldAndMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(AccessType.FIELD, javaPersistentType().access());
	}

	public void testAccessInheritance() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		ListIterator<IClassRef> classRefs = persistenceUnit().specifiedClassRefs();
		classRefs.next();
		IClassRef classRef = classRefs.next();
		
		IJavaPersistentType javaPersistentType = classRef.getJavaPersistentType();
		assertEquals(PACKAGE_NAME + ".AnnotationTestTypeChild", javaPersistentType.getName());
		
		assertEquals(AccessType.PROPERTY, javaPersistentType.access());
	}
		
	public void testAccessInheritance2() throws Exception {
		createTestEntityAnnotatedField();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		ListIterator<IClassRef> classRefs = persistenceUnit().specifiedClassRefs();
		classRefs.next();
		IClassRef classRef = classRefs.next();
		
		IJavaPersistentType javaPersistentType = classRef.getJavaPersistentType();
		assertEquals(PACKAGE_NAME + ".AnnotationTestTypeChild", javaPersistentType.getName());
		
		assertEquals(AccessType.FIELD, javaPersistentType.access());
	}	
		
	public void testAccessInheritance3() throws Exception {
		createTestEntityAnnotatedField();
		createTestSubTypeWithMethodAnnotation();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		ListIterator<IClassRef> classRefs = persistenceUnit().specifiedClassRefs();
		classRefs.next();
		IClassRef classRef = classRefs.next();
		
		IJavaPersistentType javaPersistentType = classRef.getJavaPersistentType();
		assertEquals(PACKAGE_NAME + ".AnnotationTestTypeChild", javaPersistentType.getName());
		
		assertEquals(AccessType.PROPERTY, javaPersistentType.access());
	}	
		
	public void testAccessInheritance4() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubTypeWithFieldAnnotation();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		ListIterator<IClassRef> classRefs = persistenceUnit().specifiedClassRefs();
		classRefs.next();
		IClassRef classRef = classRefs.next();
		IJavaPersistentType javaPersistentType = classRef.getJavaPersistentType();
		
		assertEquals(PACKAGE_NAME + ".AnnotationTestTypeChild", javaPersistentType.getName());
		
		assertEquals(AccessType.FIELD, javaPersistentType.access());
	}
	
	//inherited class having annotations set wins over the default access set on persistence-unit-defaults
	public void testAccessInheritancePersistenceUnitDefaultAccess() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.FIELD);

		ListIterator<IClassRef> classRefs = persistenceUnit().specifiedClassRefs();
		classRefs.next();
		IClassRef classRef = classRefs.next();
		IJavaPersistentType javaPersistentType = classRef.getJavaPersistentType();
		
		assertEquals(PACKAGE_NAME + ".AnnotationTestTypeChild", javaPersistentType.getName());
		
		assertEquals(AccessType.PROPERTY, javaPersistentType.access());
	}

	public void testAccessXmlNoAccessNoAnnotations() throws Exception {
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		createTestEntity();

		IJavaPersistentType javaPersistentType = entityPersistentType.javaPersistentType(); 
		assertEquals(AccessType.FIELD, javaPersistentType.access());
	}
	
	public void testAccessXmlEntityAccessNoAnnotations() throws Exception {
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		createTestEntity();
		IJavaPersistentType javaPersistentType = entityPersistentType.javaPersistentType(); 

		entityPersistentType.getMapping().setSpecifiedAccess(AccessType.FIELD);
		assertEquals(AccessType.FIELD, javaPersistentType.access());

		entityPersistentType.getMapping().setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, javaPersistentType.access());
	}
	
	public void testAccessXmlPersistentUnitDefaultsAccessNoAnnotations()  throws Exception {
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		createTestEntity();
		IJavaPersistentType javaPersistentType = entityPersistentType.javaPersistentType(); 

		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.FIELD);
		assertEquals(AccessType.FIELD, javaPersistentType.access());

		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, javaPersistentType.access());
	}
	
	public void testAccessXmlEntityPropertyAccessAndFieldAnnotations() throws Exception {
		//xml access set to property, field annotations, JavaPersistentType access is property
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		createTestEntityAnnotatedField();
		IJavaPersistentType javaPersistentType = entityPersistentType.javaPersistentType(); 

		entityPersistentType.getMapping().setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, javaPersistentType.access());
	}
	
	public void testAccessXmlEntityFieldAccessAndPropertyAnnotations() throws Exception {
		//xml access set to field, property annotations, JavaPersistentType access is field
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		createTestEntityAnnotatedMethod();
		IJavaPersistentType javaPersistentType = entityPersistentType.javaPersistentType(); 

		entityPersistentType.getMapping().setSpecifiedAccess(AccessType.FIELD);
		assertEquals(AccessType.FIELD, javaPersistentType.access());
	}
	
	public void testAccessXmlPersistentUnitDefaultsAccessFieldAnnotations() throws Exception {
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		createTestEntityAnnotatedField();
		IJavaPersistentType javaPersistentType = entityPersistentType.javaPersistentType(); 

		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);
		assertEquals(AccessType.FIELD, javaPersistentType.access());
	}

	//inheritance wins over entity-mappings specified access
	public void testAccessXmlEntityMappingsAccessWithInheritance() throws Exception {
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentType childEntityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		createTestEntityAnnotatedMethod();
		createTestSubType();
		IJavaPersistentType childJavaPersistentType = childEntityPersistentType.javaPersistentType(); 

		entityMappings().setSpecifiedAccess(AccessType.FIELD);
		assertEquals(AccessType.PROPERTY, entityPersistentType.javaPersistentType().access());
		assertEquals(AccessType.PROPERTY, childJavaPersistentType.access());
	}

	public void testAccessXmlMetadataCompleteFieldAnnotations() throws Exception {
		//xml access set to property, so even though there are field annotations, JavaPersistentType
		//access should be property
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		createTestEntityAnnotatedField();
		IJavaPersistentType javaPersistentType = entityPersistentType.javaPersistentType(); 

		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);
		entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertEquals(AccessType.PROPERTY, javaPersistentType.access());
		
	}
	
	public void testAccessNoXmlAccessXmlMetdataCompletePropertyAnnotations() throws Exception {
		//xml access not set, metadata complete set.  JavaPersistentType access
		//is field??
		XmlPersistentType entityPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		createTestEntityAnnotatedMethod();
		IJavaPersistentType javaPersistentType = entityPersistentType.javaPersistentType(); 

		entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertEquals(AccessType.FIELD, javaPersistentType.access());
	}
	
	public void testParentPersistentType() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubTypeWithFieldAnnotation();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		ListIterator<IClassRef> classRefs = persistenceUnit().specifiedClassRefs();
		IClassRef classRef = classRefs.next();
		IJavaPersistentType rootJavaPersistentType = classRef.getJavaPersistentType();
		
		classRef = classRefs.next();
		IJavaPersistentType childJavaPersistentType = classRef.getJavaPersistentType();
		
		assertEquals(rootJavaPersistentType, childJavaPersistentType.parentPersistentType());
		assertNull(rootJavaPersistentType.parentPersistentType());
	}
	
	public void testParentPersistentType2() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubTypeWithFieldAnnotation();
		
		//parent is not added to the persistenceUnit, so even though it has an Entity
		//annotation it should not be found as the parentPersistentType
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		ListIterator<IClassRef> classRefs = persistenceUnit().specifiedClassRefs();
		IJavaPersistentType javaPersistentType = classRefs.next().getJavaPersistentType();
		
		assertNull(javaPersistentType.parentPersistentType());
	}	
	
	//Entity extends Non-Entity extends Entity 
	public void testParentPersistentType3() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubTypeNonPersistent();
		createTestSubTypePersistentExtendsNonPersistent();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild2");
		
		ListIterator<IClassRef> classRefs = persistenceUnit().specifiedClassRefs();
		IClassRef classRef = classRefs.next();
		IJavaPersistentType rootJavaPersistentType = classRef.getJavaPersistentType();
		
		classRef = classRefs.next();
		IJavaPersistentType childJavaPersistentType = classRef.getJavaPersistentType();
		
		assertEquals(rootJavaPersistentType, childJavaPersistentType.parentPersistentType());
		assertNull(rootJavaPersistentType.parentPersistentType());
	}
	
	public void testInheritanceHierarchy() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubTypeNonPersistent();
		createTestSubTypePersistentExtendsNonPersistent();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild2");
		
		ListIterator<IClassRef> classRefs = persistenceUnit().specifiedClassRefs();
		IJavaPersistentType rootJavaPersistentType = classRefs.next().getJavaPersistentType();
		IJavaPersistentType childJavaPersistentType = classRefs.next().getJavaPersistentType();
		
		Iterator<IPersistentType> inheritanceHierarchy = childJavaPersistentType.inheritanceHierarchy();	
		
		assertEquals(childJavaPersistentType, inheritanceHierarchy.next());
		assertEquals(rootJavaPersistentType, inheritanceHierarchy.next());
	}
	
	public void testInheritanceHierarchy2() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubTypeNonPersistent();
		createTestSubTypePersistentExtendsNonPersistent();
		
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild2");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<IClassRef> classRefs = persistenceUnit().specifiedClassRefs();
		IJavaPersistentType childJavaPersistentType = classRefs.next().getJavaPersistentType();
		IJavaPersistentType rootJavaPersistentType = classRefs.next().getJavaPersistentType();
		
		Iterator<IPersistentType> inheritanceHierarchy = childJavaPersistentType.inheritanceHierarchy();	
		
		assertEquals(childJavaPersistentType, inheritanceHierarchy.next());
		assertEquals(rootJavaPersistentType, inheritanceHierarchy.next());
	}
	
	public void testGetMapping() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, javaPersistentType().getMapping().getKey());
	}
	
	public void testGetMappingNull() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(IMappingKeys.NULL_TYPE_MAPPING_KEY, javaPersistentType().getMapping().getKey());
	}
	
	public void testMappingKey() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, javaPersistentType().mappingKey());
	}
	
	public void testMappingKeyNull() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(IMappingKeys.NULL_TYPE_MAPPING_KEY, javaPersistentType().mappingKey());
	}
	
	public void testSetMappingKey() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(IMappingKeys.NULL_TYPE_MAPPING_KEY, javaPersistentType().mappingKey());

		javaPersistentType().setMappingKey(IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		assertNotNull(typeResource.mappingAnnotation());
		assertTrue(typeResource.mappingAnnotation() instanceof Entity);
		
		assertEquals(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, javaPersistentType().mappingKey());
	}
	
	public void testSetMappingKey2() throws Exception {
		createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, javaPersistentType().mappingKey());

		javaPersistentType().setMappingKey(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		assertNotNull(typeResource.mappingAnnotation());
		assertTrue(typeResource.mappingAnnotation() instanceof Embeddable);
		
		assertEquals(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, javaPersistentType().mappingKey());
	}

	public void testSetMappingKeyNull() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, javaPersistentType().mappingKey());

		javaPersistentType().setMappingKey(IMappingKeys.NULL_TYPE_MAPPING_KEY);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		assertNull(typeResource.mappingAnnotation());
		assertNull(typeResource.mappingAnnotation(Entity.ANNOTATION_NAME));
		
		assertEquals(IMappingKeys.NULL_TYPE_MAPPING_KEY, javaPersistentType().mappingKey());
	}
	
	public void testGetMappingKeyMappingChangeInResourceModel() throws Exception {
		createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, javaPersistentType().mappingKey());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.setMappingAnnotation(Embeddable.ANNOTATION_NAME);
				
		assertEquals(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, javaPersistentType().mappingKey());
	}
	
	public void testGetMappingKeyMappingChangeInResourceModel2() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(IMappingKeys.NULL_TYPE_MAPPING_KEY, javaPersistentType().mappingKey());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.setMappingAnnotation(Entity.ANNOTATION_NAME);
				
		assertEquals(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, javaPersistentType().mappingKey());
	}

	public void testIsMapped() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertTrue(javaPersistentType().isMapped());
		
		javaPersistentType().setMappingKey(IMappingKeys.NULL_TYPE_MAPPING_KEY);	
		assertFalse(javaPersistentType().isMapped());	
	}
	
	public void testAttributes() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		ListIterator<IJavaPersistentAttribute> attributes = javaPersistentType().attributes();
		
		assertEquals("id", attributes.next().getName());
		assertFalse(attributes.hasNext());
	}
	
	public void testAttributes2() throws Exception {
		createTestEntityAnnotatedFieldAndMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<IJavaPersistentAttribute> attributes = javaPersistentType().attributes();
		
		assertEquals("id", attributes.next().getName());
		assertEquals("name", attributes.next().getName());
		assertFalse(attributes.hasNext());
	}
	
	public void testAttributesSize() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(1, javaPersistentType().attributesSize());
	}
	
	public void testAttributesSize2() throws Exception {
		createTestEntityAnnotatedFieldAndMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(2, javaPersistentType().attributesSize());
	}
	
	public void testAttributeNamed() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPersistentAttribute attribute = javaPersistentType().attributeNamed("id");
		
		assertEquals("id", attribute.getName());
		assertNull(javaPersistentType().attributeNamed("name"));
		assertNull(javaPersistentType().attributeNamed("foo"));
	}
	
	public void testAttributeNamed2() throws Exception {
		createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPersistentAttribute attribute = javaPersistentType().attributeNamed("name");
		
		assertEquals("name", attribute.getName());
		
		assertNull(javaPersistentType().attributeNamed("foo"));
	}

}

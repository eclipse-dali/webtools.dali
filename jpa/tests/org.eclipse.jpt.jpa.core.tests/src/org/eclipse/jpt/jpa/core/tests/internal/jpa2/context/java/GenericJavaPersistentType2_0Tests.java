/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.internal.context.java.FieldAccessor;
import org.eclipse.jpt.jpa.core.internal.context.java.PropertyAccessor;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericJavaPersistentType2_0Tests extends Generic2_0ContextModelTestCase
{
		
	private ICompilationUnit createTestEntity() throws Exception {
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

	private ICompilationUnit createTestEntityAnnotatedField() throws Exception {	
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

	private ICompilationUnit createTestEntityAnnotatedMethod() throws Exception {	
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
	
	private ICompilationUnit createTestEntityAnnotatedFieldAndMethod() throws Exception {
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
	
	private ICompilationUnit createTestEntityAnnotatedFieldPropertySpecified() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC, JPA.ID, JPA2_0.ACCESS, JPA2_0.ACCESS_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Access(AccessType.PROPERTY)");
			}
	
			@Override
			public void appendNameFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic").append(CR);
				sb.append("    @Access(AccessType.FIELD)");
			}
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}
	
	private ICompilationUnit createTestEntityAnnotatedPropertyFieldSpecified() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, JPA2_0.ACCESS, JPA2_0.ACCESS_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append("@Access(AccessType.FIELD)");
			}
	
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
				sb.append("@Access(AccessType.PROPERTY)");
			}
		});
	}
	
	private ICompilationUnit createTestSubType() throws Exception {
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

	private ICompilationUnit createTestSubTypeWithFieldAnnotation() throws Exception {
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
	
	private ICompilationUnit createTestSubTypeWithMethodAnnotation() throws Exception {
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
	
	private ICompilationUnit createTestSubTypeNonPersistent() throws Exception {
		return this.createTestType(PACKAGE_NAME, "AnnotationTestTypeChild.java", "AnnotationTestTypeChild", new DefaultAnnotationWriter() {
			@Override
			public void appendExtendsImplementsTo(StringBuilder sb) {
				sb.append("extends " + TYPE_NAME + " ");
			}
		});
	}

	private ICompilationUnit createTestSubTypePersistentExtendsNonPersistent() throws Exception {
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
		
	public GenericJavaPersistentType2_0Tests(String name) {
		super(name);
	}
	
	public void testGetName() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, getJavaPersistentType().getName());
	}
	
	public void testGetAccessNothingAnnotated() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(AccessType.FIELD, getJavaPersistentType().getAccess());
	}

	public void testAccessField() throws Exception {
		createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(AccessType.FIELD, getJavaPersistentType().getAccess());
	}
	
	public void testAccessProperty() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(AccessType.PROPERTY, getJavaPersistentType().getAccess());
	}
	
	public void testAccessFieldAndMethodAnnotated() throws Exception {
		createTestEntityAnnotatedFieldAndMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(AccessType.FIELD, getJavaPersistentType().getAccess());
	}
	
	public void testAccessFieldAnnotatedPropertySpecified() throws Exception {
		createTestEntityAnnotatedFieldPropertySpecified();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(AccessType.PROPERTY, getJavaPersistentType().getAccess());
		
		Iterator<JavaPersistentAttribute> attributes = getJavaPersistentType().getAttributes().iterator();
		JavaPersistentAttribute javaPersistentAttribute = attributes.next();
		assertEquals("name", javaPersistentAttribute.getName());
		assertEquals(AccessType.FIELD, javaPersistentAttribute.getAccess());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, javaPersistentAttribute.getMapping().getKey());

		javaPersistentAttribute = attributes.next();
		assertEquals("id", javaPersistentAttribute.getName());
		assertEquals(AccessType.PROPERTY, javaPersistentAttribute.getAccess());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, javaPersistentAttribute.getMappingKey());
		
		assertFalse(attributes.hasNext());
	}
	
	public void testAccessPropertyAnnotatedFieldSpecified() throws Exception {
		createTestEntityAnnotatedPropertyFieldSpecified();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(AccessType.FIELD, getJavaPersistentType().getAccess());
		
		Iterator<JavaPersistentAttribute> attributes = getJavaPersistentType().getAttributes().iterator();
		JavaPersistentAttribute javaPersistentAttribute = attributes.next();
		assertEquals("id", javaPersistentAttribute.getName());
		assertEquals(AccessType.FIELD, javaPersistentAttribute.getAccess());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, javaPersistentAttribute.getMappingKey());

		javaPersistentAttribute = attributes.next();
		assertEquals("name", javaPersistentAttribute.getName());
		assertEquals(AccessType.FIELD, javaPersistentAttribute.getAccess());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, javaPersistentAttribute.getMappingKey());

		javaPersistentAttribute = attributes.next();
		assertEquals("id", javaPersistentAttribute.getName());
		assertEquals(AccessType.PROPERTY, javaPersistentAttribute.getAccess());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, javaPersistentAttribute.getMappingKey());
		
		assertFalse(attributes.hasNext());
		
		attributes = getJavaPersistentType().getAttributes().iterator();
		attributes.next().getResourceAttribute().setPrimaryAnnotation(TransientAnnotation.ANNOTATION_NAME, EmptyIterable.<String>instance());
		attributes = getJavaPersistentType().getAttributes().iterator();
		javaPersistentAttribute = attributes.next();

		javaPersistentAttribute = attributes.next();
		assertEquals("name", javaPersistentAttribute.getName());
		assertEquals(AccessType.FIELD, javaPersistentAttribute.getAccess());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, javaPersistentAttribute.getMappingKey());

		javaPersistentAttribute = attributes.next();
		assertEquals("id", javaPersistentAttribute.getName());
		assertEquals(AccessType.PROPERTY, javaPersistentAttribute.getAccess());
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, javaPersistentAttribute.getMappingKey());
		assertFalse(attributes.hasNext());
	}

	public void testAccessInheritance() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		ClassRef classRef = classRefs.next();
		
		JavaPersistentType javaPersistentType = classRef.getJavaPersistentType();
		assertEquals(PACKAGE_NAME + ".AnnotationTestTypeChild", javaPersistentType.getName());
		
		assertEquals(AccessType.PROPERTY, javaPersistentType.getAccess());
	}
		
	public void testAccessInheritance2() throws Exception {
		createTestEntityAnnotatedField();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		ClassRef classRef = classRefs.next();
		
		JavaPersistentType javaPersistentType = classRef.getJavaPersistentType();
		assertEquals(PACKAGE_NAME + ".AnnotationTestTypeChild", javaPersistentType.getName());
		
		assertEquals(AccessType.FIELD, javaPersistentType.getAccess());
	}	
		
	public void testAccessInheritance3() throws Exception {
		createTestEntityAnnotatedField();
		createTestSubTypeWithMethodAnnotation();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		ClassRef classRef = classRefs.next();
		
		JavaPersistentType javaPersistentType = classRef.getJavaPersistentType();
		assertEquals(PACKAGE_NAME + ".AnnotationTestTypeChild", javaPersistentType.getName());
		
		assertEquals(AccessType.PROPERTY, javaPersistentType.getAccess());
	}	
		
	public void testAccessInheritance4() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubTypeWithFieldAnnotation();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		ClassRef classRef = classRefs.next();
		JavaPersistentType javaPersistentType = classRef.getJavaPersistentType();
		
		assertEquals(PACKAGE_NAME + ".AnnotationTestTypeChild", javaPersistentType.getName());
		
		assertEquals(AccessType.FIELD, javaPersistentType.getAccess());
	}
	
	//inherited class having annotations set wins over the default access set on persistence-unit-defaults
	public void testAccessInheritancePersistenceUnitDefaultAccess() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.FIELD);

		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		ClassRef classRef = classRefs.next();
		JavaPersistentType javaPersistentType = classRef.getJavaPersistentType();
		
		assertEquals(PACKAGE_NAME + ".AnnotationTestTypeChild", javaPersistentType.getName());
		
		assertEquals(AccessType.PROPERTY, javaPersistentType.getAccess());
	}

	public void testAccessXmlNoAccessNoAnnotations() throws Exception {
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		createTestEntity();

		JavaPersistentType javaPersistentType = entityPersistentType.getJavaPersistentType(); 
		assertEquals(AccessType.FIELD, javaPersistentType.getAccess());
	}
	
	public void testAccessXmlEntityAccessNoAnnotations() throws Exception {
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		createTestEntity();
		JavaPersistentType javaPersistentType = entityPersistentType.getJavaPersistentType(); 

		entityPersistentType.setSpecifiedAccess(AccessType.FIELD);
		assertEquals(AccessType.FIELD, javaPersistentType.getAccess());

		entityPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, javaPersistentType.getAccess());
	}
	
	public void testAccessXmlPersistenceUnitDefaultsAccessNoAnnotations()  throws Exception {
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		createTestEntity();
		JavaPersistentType javaPersistentType = entityPersistentType.getJavaPersistentType(); 

		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.FIELD);
		assertEquals(AccessType.FIELD, javaPersistentType.getAccess());

		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, javaPersistentType.getAccess());
	}
	
	public void testAccessXmlEntityPropertyAccessAndFieldAnnotations() throws Exception {
		//xml access set to property, field annotations, JavaPersistentType access is field
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		createTestEntityAnnotatedField();
		JavaPersistentType javaPersistentType = entityPersistentType.getJavaPersistentType(); 

		entityPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.FIELD, javaPersistentType.getAccess());
	}
	
	public void testAccessXmlEntityFieldAccessAndPropertyAnnotations() throws Exception {
		//xml access set to field, property annotations, JavaPersistentType access is property
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		createTestEntityAnnotatedMethod();
		JavaPersistentType javaPersistentType = entityPersistentType.getJavaPersistentType(); 

		entityPersistentType.setSpecifiedAccess(AccessType.FIELD);
		assertEquals(AccessType.PROPERTY, javaPersistentType.getAccess());
	}
	
	public void testAccessXmlPersistenceUnitDefaultsAccessFieldAnnotations() throws Exception {
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		createTestEntityAnnotatedField();
		JavaPersistentType javaPersistentType = entityPersistentType.getJavaPersistentType(); 

		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);
		assertEquals(AccessType.FIELD, javaPersistentType.getAccess());
	}

	//inheritance wins over entity-mappings specified access
	public void testAccessXmlEntityMappingsAccessWithInheritance() throws Exception {
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType childEntityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		createTestEntityAnnotatedMethod();
		createTestSubType();
		JavaPersistentType childJavaPersistentType = childEntityPersistentType.getJavaPersistentType(); 

		getEntityMappings().setSpecifiedAccess(AccessType.FIELD);
		assertEquals(AccessType.PROPERTY, entityPersistentType.getJavaPersistentType().getAccess());
		assertEquals(AccessType.PROPERTY, childJavaPersistentType.getAccess());
	}

	public void testAccessXmlMetadataCompleteFieldAnnotations() throws Exception {
		//xml access set to property, java has field annotations so the access should be field
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		createTestEntityAnnotatedField();
		JavaPersistentType javaPersistentType = entityPersistentType.getJavaPersistentType(); 

		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertEquals(AccessType.FIELD, javaPersistentType.getAccess());
		
	}
	
	public void testAccessNoXmlAccessXmlMetdataCompletePropertyAnnotations() throws Exception {
		//xml access not set, metadata complete set.  JavaPersistentType access is property because properties are annotated
		OrmPersistentType entityPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		createTestEntityAnnotatedMethod();
		JavaPersistentType javaPersistentType = entityPersistentType.getJavaPersistentType(); 

		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertEquals(AccessType.PROPERTY, javaPersistentType.getAccess());
	}
	
	public void testSuperPersistentType() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubTypeWithFieldAnnotation();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		ClassRef classRef = classRefs.next();
		JavaPersistentType rootJavaPersistentType = classRef.getJavaPersistentType();
		
		classRef = classRefs.next();
		JavaPersistentType childJavaPersistentType = classRef.getJavaPersistentType();
		
		assertEquals(rootJavaPersistentType, childJavaPersistentType.getSuperPersistentType());
		assertNull(rootJavaPersistentType.getSuperPersistentType());
	}
	
	public void testSuperPersistentType2() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubTypeWithFieldAnnotation();
		
		//super is not added to the getPersistenceUnit, but it should still be found
		//as the superPersistentType because of impliedClassRefs and changes for bug 190317
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		JavaPersistentType javaPersistentType = classRefs.next().getJavaPersistentType();
		
		assertNotNull(javaPersistentType.getSuperPersistentType());
	}	
	
	//Entity extends Non-Entity extends Entity 
	public void testSuperPersistentType3() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubTypeNonPersistent();
		createTestSubTypePersistentExtendsNonPersistent();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild2");
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		ClassRef classRef = classRefs.next();
		JavaPersistentType rootJavaPersistentType = classRef.getJavaPersistentType();
		
		classRef = classRefs.next();
		JavaPersistentType childJavaPersistentType = classRef.getJavaPersistentType();
		
		assertEquals(rootJavaPersistentType, childJavaPersistentType.getSuperPersistentType());
		assertNull(rootJavaPersistentType.getSuperPersistentType());
	}
	
	public void testInheritanceHierarchy() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubTypeNonPersistent();
		createTestSubTypePersistentExtendsNonPersistent();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild2");
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		JavaPersistentType rootJavaPersistentType = classRefs.next().getJavaPersistentType();
		JavaPersistentType childJavaPersistentType = classRefs.next().getJavaPersistentType();
		
		Iterator<PersistentType> inheritanceHierarchy = childJavaPersistentType.inheritanceHierarchy();	
		
		assertEquals(childJavaPersistentType, inheritanceHierarchy.next());
		assertEquals(rootJavaPersistentType, inheritanceHierarchy.next());
	}
	
	public void testInheritanceHierarchy2() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubTypeNonPersistent();
		createTestSubTypePersistentExtendsNonPersistent();
		
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild2");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		JavaPersistentType childJavaPersistentType = classRefs.next().getJavaPersistentType();
		JavaPersistentType rootJavaPersistentType = classRefs.next().getJavaPersistentType();
		
		Iterator<PersistentType> inheritanceHierarchy = childJavaPersistentType.inheritanceHierarchy();	
		
		assertEquals(childJavaPersistentType, inheritanceHierarchy.next());
		assertEquals(rootJavaPersistentType, inheritanceHierarchy.next());
	}
	
	public void testGetMapping() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(MappingKeys.ENTITY_TYPE_MAPPING_KEY, getJavaPersistentType().getMapping().getKey());
	}
	
	public void testGetMappingNull() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(MappingKeys.NULL_TYPE_MAPPING_KEY, getJavaPersistentType().getMapping().getKey());
	}
	
	public void testMappingKey() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(MappingKeys.ENTITY_TYPE_MAPPING_KEY, getJavaPersistentType().getMappingKey());
	}
	
	public void testMappingKeyNull() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(MappingKeys.NULL_TYPE_MAPPING_KEY, getJavaPersistentType().getMappingKey());
	}
	
	public void testSetMappingKey() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(MappingKeys.NULL_TYPE_MAPPING_KEY, getJavaPersistentType().getMappingKey());

		getJavaPersistentType().setMappingKey(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		assertNotNull(resourceType.getAnnotation(EntityAnnotation.ANNOTATION_NAME));
		
		assertEquals(MappingKeys.ENTITY_TYPE_MAPPING_KEY, getJavaPersistentType().getMappingKey());
	}
	
	public void testSetMappingKey2() throws Exception {
		createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(MappingKeys.ENTITY_TYPE_MAPPING_KEY, getJavaPersistentType().getMappingKey());

		getJavaPersistentType().setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		assertNotNull(resourceType.getAnnotation(EmbeddableAnnotation.ANNOTATION_NAME));
		
		assertEquals(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, getJavaPersistentType().getMappingKey());
	}

	public void testSetMappingKeyNull() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(MappingKeys.ENTITY_TYPE_MAPPING_KEY, getJavaPersistentType().getMappingKey());

		getJavaPersistentType().setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		assertNull(resourceType.getAnnotation(EntityAnnotation.ANNOTATION_NAME));
		
		assertEquals(MappingKeys.NULL_TYPE_MAPPING_KEY, getJavaPersistentType().getMappingKey());
	}
	
	public void testGetMappingKeyMappingChangeInResourceModel() throws Exception {
		createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(MappingKeys.ENTITY_TYPE_MAPPING_KEY, getJavaPersistentType().getMappingKey());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		resourceType.setPrimaryAnnotation(EmbeddableAnnotation.ANNOTATION_NAME, EmptyIterable.<String>instance());
				this.getJpaProject().synchronizeContextModel();
		assertEquals(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, getJavaPersistentType().getMappingKey());
	}
	
	public void testGetMappingKeyMappingChangeInResourceModel2() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(MappingKeys.NULL_TYPE_MAPPING_KEY, getJavaPersistentType().getMappingKey());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		resourceType.setPrimaryAnnotation(EntityAnnotation.ANNOTATION_NAME, EmptyIterable.<String>instance());
				this.getJpaProject().synchronizeContextModel();
		assertEquals(MappingKeys.ENTITY_TYPE_MAPPING_KEY, getJavaPersistentType().getMappingKey());
	}

	public void testIsMapped() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertTrue(getJavaPersistentType().isMapped());
		
		getJavaPersistentType().setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);	
		assertFalse(getJavaPersistentType().isMapped());	
	}
	
	public void testAttributes() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Iterator<JavaPersistentAttribute> attributes = getJavaPersistentType().getAttributes().iterator();
		
		assertEquals("id", attributes.next().getName());
		assertFalse(attributes.hasNext());
	}
	
	public void testAttributes2() throws Exception {
		createTestEntityAnnotatedFieldAndMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<JavaPersistentAttribute> attributes = getJavaPersistentType().getAttributes().iterator();
		
		JavaPersistentAttribute attribute = attributes.next();
		assertEquals("id", attribute.getName());
		assertTrue(attribute.getAccessor() instanceof FieldAccessor);
		attribute = attributes.next();
		assertEquals("name", attribute.getName());
		assertTrue(attribute.getAccessor() instanceof FieldAccessor);
		attribute = attributes.next();
		assertEquals("id", attribute.getName());
		assertTrue(attribute.getAccessor() instanceof PropertyAccessor);
		assertFalse(attributes.hasNext());
	}
	
	public void testAttributesSize() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(1, getJavaPersistentType().getAttributesSize());
	}
	
	public void testAttributesSize2() throws Exception {
		createTestEntityAnnotatedFieldAndMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(3, getJavaPersistentType().getAttributesSize());
	}
	
	public void testAttributeNamed() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute attribute = getJavaPersistentType().getAttributeNamed("id");
		
		assertEquals("id", attribute.getName());
		assertNull(getJavaPersistentType().getAttributeNamed("name"));
		assertNull(getJavaPersistentType().getAttributeNamed("foo"));
	}
	
	public void testAttributeNamed2() throws Exception {
		createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute attribute = getJavaPersistentType().getAttributeNamed("name");
		
		assertEquals("name", attribute.getName());
		
		assertNull(getJavaPersistentType().getAttributeNamed("foo"));
	}
	
	public void testRenameAttribute() throws Exception {
		ICompilationUnit testType = createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Iterator<JavaPersistentAttribute> attributes = getJavaPersistentType().getAttributes().iterator();
		JavaPersistentAttribute idAttribute = attributes.next();
		JavaPersistentAttribute nameAttribute = attributes.next();
		
		
		assertEquals("id", idAttribute.getName());
		assertEquals("name", nameAttribute.getName());
		
		IField idField = testType.findPrimaryType().getField("id");
		idField.rename("id2", false, null);
		
		attributes = getJavaPersistentType().getAttributes().iterator();
		JavaPersistentAttribute nameAttribute2 = attributes.next();
		JavaPersistentAttribute id2Attribute = attributes.next();

		assertNotSame(idAttribute, id2Attribute);
		assertEquals("id2", id2Attribute.getName());
		assertEquals(nameAttribute, nameAttribute2);
		assertEquals("name", nameAttribute2.getName());
		assertFalse(attributes.hasNext());
	}

	public void testSuperPersistentTypeGeneric() throws Exception {
		createTestGenericEntity();
		createTestGenericMappedSuperclass();
		
		addXmlClassRef(PACKAGE_NAME + ".Entity1");
		addXmlClassRef(PACKAGE_NAME + ".Entity2");
		
		JavaPersistentType javaPersistentType = getJavaPersistentType();
		assertEquals("test.Entity1", javaPersistentType.getName());
		assertNotNull(javaPersistentType.getSuperPersistentType());
		
		assertEquals("test.Entity2", javaPersistentType.getSuperPersistentType().getName());
	}

	private void createTestGenericEntity() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class Entity1 ");
				sb.append("extends Entity2<Integer> {}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Entity1.java", sourceWriter);
	}
	
	private void createTestGenericMappedSuperclass() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.MAPPED_SUPERCLASS);
					sb.append(";");
					sb.append(CR);
				sb.append("@MappedSuperclass");
				sb.append(CR);
				sb.append("public class Entity2<K> {}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Entity2.java", sourceWriter);
	}
}

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
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.base.IClassRef;
import org.eclipse.jpt.core.internal.context.base.IPersistenceUnit;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.java.Entity;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModel;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaPersistentTypeTests extends ContextModelTestCase
{
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
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
	
	protected XmlPersistenceUnit xmlPersistenceUnit() {
		PersistenceResourceModel prm = persistenceResourceModel();
		return prm.getPersistence().getPersistenceUnits().get(0);
	}
	
	protected IPersistenceUnit persistenceUnit() {
		return jpaContent().getPersistenceXml().getPersistence().persistenceUnits().next();
	}
	
	protected IClassRef classRef() {
		return persistenceUnit().classRefs().next();
	}
	
	protected IJavaPersistentType javaPersistentType() {
		return classRef().getJavaPersistentType();
	}
	
	protected void addXmlClassRef(String className) {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass(className);
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
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

	public void testGetAccessField() throws Exception {
		createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(AccessType.FIELD, javaPersistentType().access());
	}
	
	public void testGetAccessProperty() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(AccessType.PROPERTY, javaPersistentType().access());
	}
	
	public void testGetAccessFieldAndMethodAnnotated() throws Exception {
		createTestEntityAnnotatedFieldAndMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(AccessType.FIELD, javaPersistentType().access());
	}

	public void testGetAccessInheritance() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		ListIterator<IClassRef> classRefs = persistenceUnit().classRefs();
		classRefs.next();
		IClassRef classRef = classRefs.next();
		
		IJavaPersistentType javaPersistentType = classRef.getJavaPersistentType();
		assertEquals(PACKAGE_NAME + ".AnnotationTestTypeChild", javaPersistentType.getName());
		
		assertEquals(AccessType.PROPERTY, javaPersistentType.access());
	}
		
	public void testGetAccessInheritance2() throws Exception {
		createTestEntityAnnotatedField();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		ListIterator<IClassRef> classRefs = persistenceUnit().classRefs();
		classRefs.next();
		IClassRef classRef = classRefs.next();
		
		IJavaPersistentType javaPersistentType = classRef.getJavaPersistentType();
		assertEquals(PACKAGE_NAME + ".AnnotationTestTypeChild", javaPersistentType.getName());
		
		assertEquals(AccessType.FIELD, javaPersistentType.access());
	}	
		
	public void testGetAccessInheritance3() throws Exception {
		createTestEntityAnnotatedField();
		createTestSubTypeWithMethodAnnotation();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		ListIterator<IClassRef> classRefs = persistenceUnit().classRefs();
		classRefs.next();
		IClassRef classRef = classRefs.next();
		
		IJavaPersistentType javaPersistentType = classRef.getJavaPersistentType();
		assertEquals(PACKAGE_NAME + ".AnnotationTestTypeChild", javaPersistentType.getName());
		
		assertEquals(AccessType.PROPERTY, javaPersistentType.access());
	}	
		
	public void testGetAccessInheritance4() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubTypeWithFieldAnnotation();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		ListIterator<IClassRef> classRefs = persistenceUnit().classRefs();
		classRefs.next();
		IClassRef classRef = classRefs.next();
		IJavaPersistentType javaPersistentType = classRef.getJavaPersistentType();
		
		assertEquals(PACKAGE_NAME + ".AnnotationTestTypeChild", javaPersistentType.getName());
		
		assertEquals(AccessType.FIELD, javaPersistentType.access());
	}
	
	public void testParentPersistentType() throws Exception {
		createTestEntityAnnotatedMethod();
		createTestSubTypeWithFieldAnnotation();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		
		ListIterator<IClassRef> classRefs = persistenceUnit().classRefs();
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
		
		ListIterator<IClassRef> classRefs = persistenceUnit().classRefs();
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
		
		ListIterator<IClassRef> classRefs = persistenceUnit().classRefs();
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
		
		ListIterator<IClassRef> classRefs = persistenceUnit().classRefs();
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
		
		ListIterator<IClassRef> classRefs = persistenceUnit().classRefs();
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
		ListIterator<IPersistentAttribute> attributes = javaPersistentType().attributes();
		
		assertEquals("id", attributes.next().getName());
		assertFalse(attributes.hasNext());
	}
	
	public void testAttributes2() throws Exception {
		createTestEntityAnnotatedFieldAndMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<IPersistentAttribute> attributes = javaPersistentType().attributes();
		
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

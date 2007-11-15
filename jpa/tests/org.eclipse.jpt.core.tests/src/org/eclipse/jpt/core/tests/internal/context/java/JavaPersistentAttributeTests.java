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
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IClassRef;
import org.eclipse.jpt.core.internal.context.base.IPersistenceUnit;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.IJavaBasicMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaIdMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.java.Basic;
import org.eclipse.jpt.core.internal.resource.java.Embedded;
import org.eclipse.jpt.core.internal.resource.java.Id;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaPersistentAttributeTests extends ContextModelTestCase
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

		
	public JavaPersistentAttributeTests(String name) {
		super(name);
	}
	
	protected XmlPersistenceUnit xmlPersistenceUnit() {
		PersistenceResource prm = persistenceResourceModel();
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
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		
		assertEquals("id", persistentAttribute.getName());
	}
	
	
	public void testGetMapping() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		assertTrue(persistentAttribute.getMapping() instanceof IJavaIdMapping);

		persistentAttribute.setSpecifiedMappingKey(null);
		assertTrue(persistentAttribute.getMapping() instanceof IJavaBasicMapping);
	}
	
	public void testGetSpecifiedMapping() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		assertTrue(persistentAttribute.getSpecifiedMapping() instanceof IJavaIdMapping);

		persistentAttribute.setSpecifiedMappingKey(null);
		assertNull(persistentAttribute.getSpecifiedMapping());
	}
	
	public void testGetSpecifiedMappingNull() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();

		assertNull(persistentAttribute.getSpecifiedMapping());
		assertNotNull(persistentAttribute.getMapping());
	}
	
	public void testMappingKey() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();

		assertEquals(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, persistentAttribute.mappingKey());
		
		persistentAttribute.setSpecifiedMappingKey(null);
		assertEquals(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, persistentAttribute.mappingKey());
	}
	
	public void testDefaultMappingKey() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();

		assertEquals(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, persistentAttribute.mappingKey());
		assertEquals(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, persistentAttribute.defaultMappingKey());
	}
	
	public void testSetSpecifiedMappingKey() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		assertNull(persistentAttribute.getSpecifiedMapping());

		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNotNull(attributeResource.mappingAnnotation());
		assertTrue(attributeResource.mappingAnnotation() instanceof Embedded);
		
		assertEquals(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, persistentAttribute.mappingKey());
		assertTrue(persistentAttribute.getSpecifiedMapping() instanceof IJavaEmbeddedMapping);
	}
	
	public void testSetSpecifiedMappingKey2() throws Exception {
		createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		assertEquals(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, persistentAttribute.mappingKey());

		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNotNull(attributeResource.mappingAnnotation());
		assertTrue(attributeResource.mappingAnnotation() instanceof Embedded);
		
		assertEquals(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, persistentAttribute.mappingKey());
		assertTrue(persistentAttribute.getSpecifiedMapping() instanceof IJavaEmbeddedMapping);
	}

	public void testSetSpecifiedMappingKeyNull() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		assertEquals(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, persistentAttribute.mappingKey());

		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation());
		assertNull(attributeResource.mappingAnnotation(Id.ANNOTATION_NAME));
		
		assertNull(persistentAttribute.getSpecifiedMapping());
	}
	
	public void testGetMappingKeyMappingChangeInResourceModel() throws Exception {
		createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		assertEquals(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, persistentAttribute.mappingKey());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		attributeResource.setMappingAnnotation(Embedded.ANNOTATION_NAME);
				
		assertEquals(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, persistentAttribute.mappingKey());
	}
	
	public void testGetMappingKeyMappingChangeInResourceModel2() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		attributeResource.setMappingAnnotation(Basic.ANNOTATION_NAME);
				
		assertEquals(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getSpecifiedMapping().getKey());
	}
}

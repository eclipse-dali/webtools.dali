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
import org.eclipse.jpt.core.internal.context.base.GenerationType;
import org.eclipse.jpt.core.internal.context.base.IClassRef;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IGeneratedValue;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.IPersistenceUnit;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.java.GeneratedValue;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaGeneratedValueTests extends ContextModelTestCase
{
	private static final String GENERATOR = "MY_GENERATOR";
	
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createGeneratedValueAnnotation() throws Exception{
		this.createAnnotationAndMembers("GeneratedValue", 
			"GenerationType strategy() default;" +
			"String generator() default \"\"; ");		
	}

	private IType createTestEntityWithGeneratedValue() throws Exception {
		createEntityAnnotation();
		createGeneratedValueAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.GENERATED_VALUE, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("@GeneratedValue(generator=\"" + GENERATOR + "\")");
			}
		});
	}
		
	public JavaGeneratedValueTests(String name) {
		super(name);
	}
	
	protected XmlPersistenceUnit xmlPersistenceUnit() {
		PersistenceResource prm = persistenceResource();
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
	
	protected IEntity javaEntity() {
		return (IEntity) javaPersistentType().getMapping();
	}
	
	protected void addXmlClassRef(String className) {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass(className);
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
	}

	public void testGetGenerator() throws Exception {
		createTestEntityWithGeneratedValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		assertEquals(GENERATOR, idMapping.getGeneratedValue().getGenerator());

		//change resource model sequenceGenerator name, verify the context model is updated
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		GeneratedValue generatedValue = (GeneratedValue) attributeResource.annotation(JPA.GENERATED_VALUE);
		
		generatedValue.setGenerator("foo");
		
		assertEquals("foo", idMapping.getGeneratedValue().getGenerator());
	}

	public void testSetSpecifiedGenerator() throws Exception {
		createTestEntityWithGeneratedValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		assertEquals(GENERATOR, idMapping.getGeneratedValue().getGenerator());

		idMapping.getGeneratedValue().setSpecifiedGenerator("foo");
		
		assertEquals("foo", idMapping.getGeneratedValue().getGenerator());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		GeneratedValue generatedValue = (GeneratedValue) attributeResource.annotation(JPA.GENERATED_VALUE);
		
		assertEquals("foo", generatedValue.getGenerator());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithGeneratedValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		assertEquals(GENERATOR, idMapping.getGeneratedValue().getGenerator());

		idMapping.getGeneratedValue().setSpecifiedGenerator(null);
		
		assertNotNull(idMapping.getGeneratedValue());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		GeneratedValue generatedValue = (GeneratedValue) attributeResource.annotation(JPA.GENERATED_VALUE);
		
		assertNotNull(generatedValue);
	}
	
	public void testGetStrategy() throws Exception {
		createTestEntityWithGeneratedValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		assertEquals(IGeneratedValue.DEFAULT_STRATEGY, idMapping.getGeneratedValue().getStrategy());

		//change resource model sequenceGenerator name, verify the context model is updated
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		GeneratedValue generatedValue = (GeneratedValue) attributeResource.annotation(JPA.GENERATED_VALUE);
		
		generatedValue.setStrategy(org.eclipse.jpt.core.internal.resource.java.GenerationType.IDENTITY);
		
		assertEquals(GenerationType.IDENTITY, idMapping.getGeneratedValue().getStrategy());
		assertEquals(IGeneratedValue.DEFAULT_STRATEGY, idMapping.getGeneratedValue().getDefaultStrategy());
	}

	public void testSetSpecifiedStrategy() throws Exception {
		createTestEntityWithGeneratedValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		assertEquals(IGeneratedValue.DEFAULT_STRATEGY, idMapping.getGeneratedValue().getStrategy());

		idMapping.getGeneratedValue().setSpecifiedStrategy(GenerationType.IDENTITY);
		
		assertEquals(GenerationType.IDENTITY, idMapping.getGeneratedValue().getStrategy());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		GeneratedValue generatedValue = (GeneratedValue) attributeResource.annotation(JPA.GENERATED_VALUE);
		
		assertEquals(org.eclipse.jpt.core.internal.resource.java.GenerationType.IDENTITY, generatedValue.getStrategy());
		
		idMapping.getGeneratedValue().setSpecifiedStrategy(null);
		
		assertEquals(IGeneratedValue.DEFAULT_STRATEGY, idMapping.getGeneratedValue().getStrategy());
		generatedValue = (GeneratedValue) attributeResource.annotation(JPA.GENERATED_VALUE);
		assertNotNull(generatedValue);
		assertNull(generatedValue.getStrategy());
	}
}

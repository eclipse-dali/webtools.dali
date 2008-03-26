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
import org.eclipse.jpt.core.context.GeneratedValue;
import org.eclipse.jpt.core.context.GenerationType;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
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
	
	public void testGetGenerator() throws Exception {
		createTestEntityWithGeneratedValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(GENERATOR, idMapping.getGeneratedValue().getGenerator());

		//change resource model sequenceGenerator name, verify the context model is updated
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) attributeResource.getAnnotation(JPA.GENERATED_VALUE);
		
		generatedValue.setGenerator("foo");
		
		assertEquals("foo", idMapping.getGeneratedValue().getGenerator());
	}

	public void testSetSpecifiedGenerator() throws Exception {
		createTestEntityWithGeneratedValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(GENERATOR, idMapping.getGeneratedValue().getGenerator());

		idMapping.getGeneratedValue().setSpecifiedGenerator("foo");
		
		assertEquals("foo", idMapping.getGeneratedValue().getGenerator());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) attributeResource.getAnnotation(JPA.GENERATED_VALUE);
		
		assertEquals("foo", generatedValue.getGenerator());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithGeneratedValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(GENERATOR, idMapping.getGeneratedValue().getGenerator());

		idMapping.getGeneratedValue().setSpecifiedGenerator(null);
		
		assertNotNull(idMapping.getGeneratedValue());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) attributeResource.getAnnotation(JPA.GENERATED_VALUE);
		
		assertNotNull(generatedValue);
	}
	
	public void testGetStrategy() throws Exception {
		createTestEntityWithGeneratedValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(GeneratedValue.DEFAULT_STRATEGY, idMapping.getGeneratedValue().getStrategy());

		//change resource model sequenceGenerator name, verify the context model is updated
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) attributeResource.getAnnotation(JPA.GENERATED_VALUE);
		
		generatedValue.setStrategy(org.eclipse.jpt.core.resource.java.GenerationType.IDENTITY);
		
		assertEquals(GenerationType.IDENTITY, idMapping.getGeneratedValue().getStrategy());
		assertEquals(GeneratedValue.DEFAULT_STRATEGY, idMapping.getGeneratedValue().getDefaultStrategy());
	}

	public void testSetSpecifiedStrategy() throws Exception {
		createTestEntityWithGeneratedValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(GeneratedValue.DEFAULT_STRATEGY, idMapping.getGeneratedValue().getStrategy());

		idMapping.getGeneratedValue().setSpecifiedStrategy(GenerationType.IDENTITY);
		
		assertEquals(GenerationType.IDENTITY, idMapping.getGeneratedValue().getStrategy());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) attributeResource.getAnnotation(JPA.GENERATED_VALUE);
		
		assertEquals(org.eclipse.jpt.core.resource.java.GenerationType.IDENTITY, generatedValue.getStrategy());
		
		idMapping.getGeneratedValue().setSpecifiedStrategy(null);
		
		assertEquals(GeneratedValue.DEFAULT_STRATEGY, idMapping.getGeneratedValue().getStrategy());
		generatedValue = (GeneratedValueAnnotation) attributeResource.getAnnotation(JPA.GENERATED_VALUE);
		assertNotNull(generatedValue);
		assertNull(generatedValue.getStrategy());
	}
}

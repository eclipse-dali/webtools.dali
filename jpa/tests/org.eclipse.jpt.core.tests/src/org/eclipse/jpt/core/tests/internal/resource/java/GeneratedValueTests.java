/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.core.resource.java.GenerationType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class GeneratedValueTests extends JavaResourceModelTestCase {

	private static final String GENERATOR = "MY_GENERATOR";
	public GeneratedValueTests(String name) {
		super(name);
	}

	private void createGenerationTypeEnum() throws Exception {
		this.createEnumAndMembers("GenerationType", "TABLE, SEQUENCE, IDENTITY, AUTO;");	
	}
	
	private void createGeneratedValueAnnotation() throws Exception {
		this.createAnnotationAndMembers("GeneratedValue", "GenerationType strategy() default AUTO; String generator() default \"\"");
		createGenerationTypeEnum();
	}
	
	private IType createTestGeneratedValue() throws Exception {
		createGeneratedValueAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.GENERATED_VALUE, JPA.GENERATION_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@GeneratedValue");
			}
		});
	}
	
	private IType createTestGeneratedValueWithGenerator() throws Exception {
		createGeneratedValueAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.GENERATED_VALUE, JPA.GENERATION_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@GeneratedValue(generator=\"" + GENERATOR + "\")");
			}
		});
	}
	
	private IType createTestGeneratedValueWithStrategy() throws Exception {
		createGeneratedValueAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.GENERATED_VALUE, JPA.GENERATION_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@GeneratedValue(strategy=GenerationType.SEQUENCE)");
			}
		});
	}

	public void testGeneratedValue() throws Exception {
		IType testType = this.createTestGeneratedValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) attributeResource.annotation(JPA.GENERATED_VALUE);
		assertNotNull(generatedValue);
	}

	public void testGetGenerator() throws Exception {
		IType testType = this.createTestGeneratedValueWithGenerator();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) attributeResource.annotation(JPA.GENERATED_VALUE);
		assertEquals(GENERATOR, generatedValue.getGenerator());
	}

	public void testSetGenerator() throws Exception {
		IType testType = this.createTestGeneratedValueWithGenerator();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) attributeResource.annotation(JPA.GENERATED_VALUE);
		assertEquals(GENERATOR, generatedValue.getGenerator());
		
		generatedValue.setGenerator("foo");
		assertEquals("foo", generatedValue.getGenerator());
		
		assertSourceContains("@GeneratedValue(generator=\"foo\")");
		
		generatedValue.setGenerator(null);
		assertNull(generatedValue.getGenerator());
		
		assertSourceDoesNotContain("generator");
		assertSourceContains("@GeneratedValue");
	}
	
	public void testGetStrategy() throws Exception {
		IType testType = this.createTestGeneratedValueWithStrategy();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) attributeResource.annotation(JPA.GENERATED_VALUE);
		assertEquals(GenerationType.SEQUENCE, generatedValue.getStrategy());
	}

	public void testSetStrategy() throws Exception {
		IType testType = this.createTestGeneratedValueWithStrategy();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) attributeResource.annotation(JPA.GENERATED_VALUE);
		assertEquals(GenerationType.SEQUENCE, generatedValue.getStrategy());
		
		generatedValue.setStrategy(GenerationType.TABLE);
		assertEquals(GenerationType.TABLE, generatedValue.getStrategy());
		
		assertSourceContains("@GeneratedValue(strategy=TABLE)");
		
		generatedValue.setStrategy(null);
		assertNull(generatedValue.getStrategy());
		assertSourceDoesNotContain("strategy");
		assertSourceContains("@GeneratedValue");
	}
}

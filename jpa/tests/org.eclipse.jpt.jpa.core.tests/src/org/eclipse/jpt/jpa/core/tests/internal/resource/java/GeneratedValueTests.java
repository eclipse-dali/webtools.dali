/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.GenerationType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

@SuppressWarnings("nls")
public class GeneratedValueTests extends JpaJavaResourceModelTestCase {

	private static final String GENERATOR = "MY_GENERATOR";
	public GeneratedValueTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestGeneratedValue() throws Exception {
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
	
	private ICompilationUnit createTestGeneratedValueWithGenerator() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.GENERATED_VALUE, JPA.GENERATION_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@GeneratedValue(generator = \"" + GENERATOR + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestGeneratedValueWithStrategy() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.GENERATED_VALUE, JPA.GENERATION_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@GeneratedValue(strategy = GenerationType.SEQUENCE)");
			}
		});
	}

	public void testGeneratedValue() throws Exception {
		ICompilationUnit cu = this.createTestGeneratedValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) resourceField.getAnnotation(JPA.GENERATED_VALUE);
		assertNotNull(generatedValue);
	}

	public void testGetGenerator() throws Exception {
		ICompilationUnit cu = this.createTestGeneratedValueWithGenerator();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) resourceField.getAnnotation(JPA.GENERATED_VALUE);
		assertEquals(GENERATOR, generatedValue.getGenerator());
	}

	public void testSetGenerator() throws Exception {
		ICompilationUnit cu = this.createTestGeneratedValueWithGenerator();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) resourceField.getAnnotation(JPA.GENERATED_VALUE);
		assertEquals(GENERATOR, generatedValue.getGenerator());
		
		generatedValue.setGenerator("foo");
		assertEquals("foo", generatedValue.getGenerator());
		
		assertSourceContains("@GeneratedValue(generator = \"foo\")", cu);
		
		generatedValue.setGenerator(null);
		assertNull(generatedValue.getGenerator());
		
		assertSourceDoesNotContain("generator", cu);
		assertSourceContains("@GeneratedValue", cu);
	}
	
	public void testGetStrategy() throws Exception {
		ICompilationUnit cu = this.createTestGeneratedValueWithStrategy();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) resourceField.getAnnotation(JPA.GENERATED_VALUE);
		assertEquals(GenerationType.SEQUENCE, generatedValue.getStrategy());
	}

	public void testSetStrategy() throws Exception {
		ICompilationUnit cu = this.createTestGeneratedValueWithStrategy();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		GeneratedValueAnnotation generatedValue = (GeneratedValueAnnotation) resourceField.getAnnotation(JPA.GENERATED_VALUE);
		assertEquals(GenerationType.SEQUENCE, generatedValue.getStrategy());
		
		generatedValue.setStrategy(GenerationType.TABLE);
		assertEquals(GenerationType.TABLE, generatedValue.getStrategy());
		
		assertSourceContains("@GeneratedValue(strategy = TABLE)", cu);
		
		generatedValue.setStrategy(null);
		assertNull(generatedValue.getStrategy());
		assertSourceDoesNotContain("strategy", cu);
		assertSourceContains("@GeneratedValue", cu);
	}
}

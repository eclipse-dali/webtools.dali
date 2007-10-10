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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.platform.generic.GenericJpaPlatform;
import org.eclipse.jpt.core.internal.resource.java.GeneratedValue;
import org.eclipse.jpt.core.internal.resource.java.GenerationType;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResourceImpl;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class GeneratedValueTests extends AnnotationTestCase {

	private static final String GENERATOR = "MY_GENERATOR";
	public GeneratedValueTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}
	private void createEnum(String enumName, String enumBody) throws Exception {
		this.javaProject.createType("javax.persistence", enumName + ".java", "public enum " + enumName + " { " + enumBody + " }");
	}

	private void createGenerationTypeEnum() throws Exception {
		this.createEnum("GenerationType", "TABLE, SEQUENCE, IDENTITY, AUTO;");	
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
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
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
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
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
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@GeneratedValue(strategy=GenerationType.SEQUENCE)");
			}
		});
	}

	protected JavaResource buildParentResource(final IJpaPlatform jpaPlatform) {
		return new JavaResource() {
			public void updateFromJava(CompilationUnit astRoot) {
			}
			public IJpaPlatform jpaPlatform() {
				return jpaPlatform;
			}
		};
	}
	
	protected IJpaPlatform buildJpaPlatform() {
		return new GenericJpaPlatform();
	}

	protected JavaPersistentTypeResource buildJavaTypeResource(IType testType) {
		JavaPersistentTypeResource typeResource = new JavaPersistentTypeResourceImpl(buildParentResource(buildJpaPlatform()), new Type(testType, MODIFY_SHARED_DOCUMENT_COMMAND_EXECUTOR_PROVIDER));
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		return typeResource;
	}

	public void testGeneratedValue() throws Exception {
		IType testType = this.createTestGeneratedValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		GeneratedValue generatedValue = (GeneratedValue) attributeResource.annotation(JPA.GENERATED_VALUE);
		assertNotNull(generatedValue);
	}

	public void testGetGenerator() throws Exception {
		IType testType = this.createTestGeneratedValueWithGenerator();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		GeneratedValue generatedValue = (GeneratedValue) attributeResource.annotation(JPA.GENERATED_VALUE);
		assertEquals(GENERATOR, generatedValue.getGenerator());
	}

	public void testSetGenerator() throws Exception {
		IType testType = this.createTestGeneratedValueWithGenerator();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		GeneratedValue generatedValue = (GeneratedValue) attributeResource.annotation(JPA.GENERATED_VALUE);
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
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		GeneratedValue generatedValue = (GeneratedValue) attributeResource.annotation(JPA.GENERATED_VALUE);
		assertEquals(GenerationType.SEQUENCE, generatedValue.getStrategy());
	}

	public void testSetStrategy() throws Exception {
		IType testType = this.createTestGeneratedValueWithStrategy();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		GeneratedValue generatedValue = (GeneratedValue) attributeResource.annotation(JPA.GENERATED_VALUE);
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

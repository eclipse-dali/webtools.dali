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
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResourceImpl;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.SequenceGenerator;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class SequenceGeneratorTests extends AnnotationTestCase {

	private static final String GENERATOR_NAME = "MY_GENERATOR";
	private static final String GENERATOR_SEQUENCE_NAME = "MY_SEQUENCE";
	private static final int GENERATOR_ALLOCATION_SIZE = 5;
	private static final int GENERATOR_INITIAL_VALUE = 5;
	
	public SequenceGeneratorTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}

	private void createSequenceGeneratorAnnotation() throws Exception {
		this.createAnnotationAndMembers("SequenceGenerator", "String name(); " +
			"String sequenceName() default \"\"" +
			"int initialValue() default 1" +
			"int allocationSize() default 50");
	}
	
	private IType createTestSequenceGeneratorOnField() throws Exception {
		createSequenceGeneratorAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SEQUENCE_GENERATOR);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@SequenceGenerator");
			}
		});
	}
	
	private IType createTestSequenceGeneratorOnType() throws Exception {
		createSequenceGeneratorAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SEQUENCE_GENERATOR);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@SequenceGenerator");
			}
		});
	}
	
	private IType createTestSequenceGeneratorWithName() throws Exception {
		return createTestSequenceGeneratorWithStringElement("name", GENERATOR_NAME);
	}
	
	private IType createTestSequenceGeneratorWithSequenceName() throws Exception {
		return createTestSequenceGeneratorWithStringElement("sequenceName", GENERATOR_SEQUENCE_NAME);
	}
		
	private IType createTestSequenceGeneratorWithStringElement(final String elementName, final String value) throws Exception {
		createSequenceGeneratorAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SEQUENCE_GENERATOR);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@SequenceGenerator(" + elementName + "=\"" + value + "\")");
			}
		});
	}
	
	private IType createTestSequenceGeneratorWithAllocationSize() throws Exception {
		return createTestSequenceGeneratorWithIntElement("allocationSize", GENERATOR_ALLOCATION_SIZE);
	}
	
	private IType createTestSequenceGeneratorWithInitialValue() throws Exception {
		return createTestSequenceGeneratorWithIntElement("initialValue", GENERATOR_INITIAL_VALUE);
	}
	
	private IType createTestSequenceGeneratorWithIntElement(final String elementName, final int value) throws Exception {
		createSequenceGeneratorAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SEQUENCE_GENERATOR);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@SequenceGenerator(" + elementName + "=" + value + ")");
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

	public void testSequenceGeneratorOnField() throws Exception {
		IType testType = this.createTestSequenceGeneratorOnField();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);
		assertNotNull(sequenceGenerator);
	}
	
	public void testSequenceGeneratorOnType() throws Exception {
		IType testType = this.createTestSequenceGeneratorOnType();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		SequenceGenerator sequenceGenerator = (SequenceGenerator) typeResource.annotation(JPA.SEQUENCE_GENERATOR);
		assertNotNull(sequenceGenerator);
	}

	public void testGetName() throws Exception {
		IType testType = this.createTestSequenceGeneratorWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_NAME, sequenceGenerator.getName());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestSequenceGeneratorWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_NAME, sequenceGenerator.getName());
		
		sequenceGenerator.setName("foo");
		assertEquals("foo", sequenceGenerator.getName());
		
		assertSourceContains("@SequenceGenerator(name=\"foo\")");
		
		sequenceGenerator.setName(null);
		assertNull(sequenceGenerator.getName());
		
		assertSourceDoesNotContain("@SequenceGenerator");
	}


	public void testGetAllocationSize() throws Exception {
		IType testType = this.createTestSequenceGeneratorWithAllocationSize();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_ALLOCATION_SIZE, sequenceGenerator.getAllocationSize());
	}

	public void testSetAllocationSize() throws Exception {
		IType testType = this.createTestSequenceGeneratorWithAllocationSize();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_ALLOCATION_SIZE, sequenceGenerator.getAllocationSize());
		
		sequenceGenerator.setAllocationSize(500);
		assertEquals(500, sequenceGenerator.getAllocationSize());
		
		assertSourceContains("@SequenceGenerator(allocationSize=500)");
		
		sequenceGenerator.setAllocationSize(-1);
		
		assertSourceDoesNotContain("@SequenceGenerator");

		sequenceGenerator.setAllocationSize(0);
		assertSourceContains("@SequenceGenerator(allocationSize=0)");
	}
	
	public void testGetInitialValue() throws Exception {
		IType testType = this.createTestSequenceGeneratorWithInitialValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_INITIAL_VALUE, sequenceGenerator.getInitialValue());
	}

	public void testSetInitialValue() throws Exception {
		IType testType = this.createTestSequenceGeneratorWithInitialValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_INITIAL_VALUE, sequenceGenerator.getInitialValue());
		
		sequenceGenerator.setInitialValue(500);
		assertEquals(500, sequenceGenerator.getInitialValue());
		
		assertSourceContains("@SequenceGenerator(initialValue=500)");
		
		sequenceGenerator.setInitialValue(-1);
		
		assertSourceDoesNotContain("@SequenceGenerator");

		sequenceGenerator.setInitialValue(0);
		assertSourceContains("@SequenceGenerator(initialValue=0)");
	}
}

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
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.SequenceGenerator;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class SequenceGeneratorTests extends JavaResourceModelTestCase {

	private static final String GENERATOR_NAME = "MY_GENERATOR";
	private static final String GENERATOR_SEQUENCE_NAME = "MY_SEQUENCE";
	private static final Integer GENERATOR_ALLOCATION_SIZE = Integer.valueOf(5);
	private static final Integer GENERATOR_INITIAL_VALUE = Integer.valueOf(5);
	
	public SequenceGeneratorTests(String name) {
		super(name);
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
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
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
			public void appendTypeAnnotationTo(StringBuilder sb) {
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
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
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
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@SequenceGenerator(" + elementName + "=" + value + ")");
			}
		});
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

	public void testGetSequenceName() throws Exception {
		IType testType = this.createTestSequenceGeneratorWithSequenceName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_SEQUENCE_NAME, sequenceGenerator.getSequenceName());
	}

	public void testSetSequenceName() throws Exception {
		IType testType = this.createTestSequenceGeneratorWithSequenceName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_SEQUENCE_NAME, sequenceGenerator.getSequenceName());
		
		sequenceGenerator.setSequenceName("foo");
		assertEquals("foo", sequenceGenerator.getSequenceName());
		
		assertSourceContains("@SequenceGenerator(sequenceName=\"foo\")");
		
		sequenceGenerator.setSequenceName(null);
		assertNull(sequenceGenerator.getSequenceName());
		
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
		
		sequenceGenerator.setAllocationSize(Integer.valueOf(500));
		assertEquals(Integer.valueOf(500), sequenceGenerator.getAllocationSize());
		
		assertSourceContains("@SequenceGenerator(allocationSize=500)");
		
		sequenceGenerator.setAllocationSize(null);
		
		assertSourceDoesNotContain("@SequenceGenerator");

		sequenceGenerator.setAllocationSize(Integer.valueOf(0));
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
		
		sequenceGenerator.setInitialValue(Integer.valueOf(500));
		assertEquals(Integer.valueOf(500), sequenceGenerator.getInitialValue());
		
		assertSourceContains("@SequenceGenerator(initialValue=500)");
		
		sequenceGenerator.setInitialValue(null);
		
		assertSourceDoesNotContain("@SequenceGenerator");

		sequenceGenerator.setInitialValue(Integer.valueOf(0));
		assertSourceContains("@SequenceGenerator(initialValue=0)");
	}
}

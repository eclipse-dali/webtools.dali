/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class SequenceGeneratorTests extends JavaResourceModelTestCase {

	private static final String GENERATOR_NAME = "MY_GENERATOR";
	private static final String GENERATOR_SEQUENCE_NAME = "MY_SEQUENCE";
	private static final Integer GENERATOR_ALLOCATION_SIZE = Integer.valueOf(5);
	private static final Integer GENERATOR_INITIAL_VALUE = Integer.valueOf(5);
	
	public SequenceGeneratorTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestSequenceGeneratorOnField() throws Exception {
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
	
	private ICompilationUnit createTestSequenceGeneratorOnType() throws Exception {
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
	
	private ICompilationUnit createTestSequenceGeneratorWithName() throws Exception {
		return createTestSequenceGeneratorWithStringElement("name", GENERATOR_NAME);
	}
	
	private ICompilationUnit createTestSequenceGeneratorWithSequenceName() throws Exception {
		return createTestSequenceGeneratorWithStringElement("sequenceName", GENERATOR_SEQUENCE_NAME);
	}
		
	private ICompilationUnit createTestSequenceGeneratorWithStringElement(final String elementName, final String value) throws Exception {		return this.createTestType(new DefaultAnnotationWriter() {
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
	
	private ICompilationUnit createTestSequenceGeneratorWithAllocationSize() throws Exception {
		return createTestSequenceGeneratorWithIntElement("allocationSize", GENERATOR_ALLOCATION_SIZE);
	}
	
	private ICompilationUnit createTestSequenceGeneratorWithInitialValue() throws Exception {
		return createTestSequenceGeneratorWithIntElement("initialValue", GENERATOR_INITIAL_VALUE);
	}
	
	private ICompilationUnit createTestSequenceGeneratorWithIntElement(final String elementName, final int value) throws Exception {
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
		ICompilationUnit cu = this.createTestSequenceGeneratorOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		assertNotNull(sequenceGenerator);
	}
	
	public void testSequenceGeneratorOnType() throws Exception {
		ICompilationUnit cu = this.createTestSequenceGeneratorOnType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) typeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		assertNotNull(sequenceGenerator);
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestSequenceGeneratorWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_NAME, sequenceGenerator.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestSequenceGeneratorWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_NAME, sequenceGenerator.getName());
		
		sequenceGenerator.setName("foo");
		assertEquals("foo", sequenceGenerator.getName());
		
		assertSourceContains("@SequenceGenerator(name=\"foo\")", cu);
		
		sequenceGenerator.setName(null);
		assertNull(sequenceGenerator.getName());
		
		assertSourceDoesNotContain("@SequenceGenerator", cu);
	}

	public void testGetSequenceName() throws Exception {
		ICompilationUnit cu = this.createTestSequenceGeneratorWithSequenceName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_SEQUENCE_NAME, sequenceGenerator.getSequenceName());
	}

	public void testSetSequenceName() throws Exception {
		ICompilationUnit cu = this.createTestSequenceGeneratorWithSequenceName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_SEQUENCE_NAME, sequenceGenerator.getSequenceName());
		
		sequenceGenerator.setSequenceName("foo");
		assertEquals("foo", sequenceGenerator.getSequenceName());
		
		assertSourceContains("@SequenceGenerator(sequenceName=\"foo\")", cu);
		
		sequenceGenerator.setSequenceName(null);
		assertNull(sequenceGenerator.getSequenceName());
		
		assertSourceDoesNotContain("@SequenceGenerator", cu);
	}

	public void testGetAllocationSize() throws Exception {
		ICompilationUnit cu = this.createTestSequenceGeneratorWithAllocationSize();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_ALLOCATION_SIZE, sequenceGenerator.getAllocationSize());
	}

	public void testSetAllocationSize() throws Exception {
		ICompilationUnit cu = this.createTestSequenceGeneratorWithAllocationSize();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_ALLOCATION_SIZE, sequenceGenerator.getAllocationSize());
		
		sequenceGenerator.setAllocationSize(Integer.valueOf(500));
		assertEquals(Integer.valueOf(500), sequenceGenerator.getAllocationSize());
		
		assertSourceContains("@SequenceGenerator(allocationSize=500)", cu);
		
		sequenceGenerator.setAllocationSize(null);
		
		assertSourceDoesNotContain("@SequenceGenerator", cu);

		sequenceGenerator.setAllocationSize(Integer.valueOf(0));
		assertSourceContains("@SequenceGenerator(allocationSize=0)", cu);
	}
	
	public void testGetInitialValue() throws Exception {
		ICompilationUnit cu = this.createTestSequenceGeneratorWithInitialValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_INITIAL_VALUE, sequenceGenerator.getInitialValue());
	}

	public void testSetInitialValue() throws Exception {
		ICompilationUnit cu = this.createTestSequenceGeneratorWithInitialValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_INITIAL_VALUE, sequenceGenerator.getInitialValue());
		
		sequenceGenerator.setInitialValue(Integer.valueOf(500));
		assertEquals(Integer.valueOf(500), sequenceGenerator.getInitialValue());
		
		assertSourceContains("@SequenceGenerator(initialValue=500)", cu);
		
		sequenceGenerator.setInitialValue(null);
		
		assertSourceDoesNotContain("@SequenceGenerator", cu);

		sequenceGenerator.setInitialValue(Integer.valueOf(0));
		assertSourceContains("@SequenceGenerator(initialValue=0)", cu);
	}
}

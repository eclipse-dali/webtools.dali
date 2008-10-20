/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.SequenceGenerator;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaSequenceGeneratorTests extends ContextModelTestCase
{
	private static final String SEQUENCE_GENERATOR_NAME = "MY_SEQUENCE_GENERATOR";
	

	private ICompilationUnit createTestEntityWithSequenceGenerator() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SEQUENCE_GENERATOR, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("@SequenceGenerator(name=\"" + SEQUENCE_GENERATOR_NAME + "\")");
			}
		});
	}
		
	public JavaSequenceGeneratorTests(String name) {
		super(name);
	}

	public void testGetName() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(SEQUENCE_GENERATOR_NAME, idMapping.getSequenceGenerator().getName());

		//change resource model sequenceGenerator name, verify the context model is updated
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		
		sequenceGenerator.setName("foo");
		
		assertEquals("foo", idMapping.getSequenceGenerator().getName());
	}

	public void testSetName() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(SEQUENCE_GENERATOR_NAME, idMapping.getSequenceGenerator().getName());

		idMapping.getSequenceGenerator().setName("foo");
		
		assertEquals("foo", idMapping.getSequenceGenerator().getName());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		
		assertEquals("foo", sequenceGenerator.getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(SEQUENCE_GENERATOR_NAME, idMapping.getSequenceGenerator().getName());

		idMapping.getSequenceGenerator().setName(null);
		
		assertNull(idMapping.getSequenceGenerator());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		
		assertNull(sequenceGenerator);
	}
	
	public void testGetInitialValue() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		
		assertEquals(SequenceGenerator.DEFAULT_INITIAL_VALUE, idMapping.getSequenceGenerator().getInitialValue());
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		sequenceGenerator.setInitialValue(Integer.valueOf(82));
		
		assertEquals(Integer.valueOf(82), idMapping.getSequenceGenerator().getInitialValue());
		assertEquals(Integer.valueOf(82), idMapping.getSequenceGenerator().getSpecifiedInitialValue());
	}
	
	public void testGetDefaultInitialValue() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();

		assertEquals(SequenceGenerator.DEFAULT_INITIAL_VALUE, idMapping.getSequenceGenerator().getDefaultInitialValue());
		
		idMapping.getSequenceGenerator().setSpecifiedInitialValue(Integer.valueOf(82));
		
		assertEquals(SequenceGenerator.DEFAULT_INITIAL_VALUE, idMapping.getSequenceGenerator().getDefaultInitialValue());
		assertEquals(Integer.valueOf(82), idMapping.getSequenceGenerator().getSpecifiedInitialValue());
	}
	
	public void testSetSpecifiedInitialValue() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getSequenceGenerator().setSpecifiedInitialValue(Integer.valueOf(20));
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		assertEquals(Integer.valueOf(20), sequenceGenerator.getInitialValue());
		
		idMapping.getSequenceGenerator().setName(null);
		idMapping.getSequenceGenerator().setSpecifiedInitialValue(null);
		assertNull(attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR));
	}
	
	public void testGetAllocationSize() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		
		assertEquals(Generator.DEFAULT_ALLOCATION_SIZE, idMapping.getSequenceGenerator().getAllocationSize());

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		sequenceGenerator.setAllocationSize(Integer.valueOf(20));
		
		assertEquals(Integer.valueOf(20), idMapping.getSequenceGenerator().getAllocationSize());
		assertEquals(Integer.valueOf(20), idMapping.getSequenceGenerator().getSpecifiedAllocationSize());
	}
	
	public void testGetDefaultAllocationSize() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();

		assertEquals(Generator.DEFAULT_ALLOCATION_SIZE, idMapping.getSequenceGenerator().getDefaultAllocationSize());
		
		idMapping.getSequenceGenerator().setSpecifiedAllocationSize(Integer.valueOf(20));
		
		assertEquals(Generator.DEFAULT_ALLOCATION_SIZE, idMapping.getSequenceGenerator().getDefaultAllocationSize());
		assertEquals(Integer.valueOf(20), idMapping.getSequenceGenerator().getSpecifiedAllocationSize());
	}
	
	public void testSetSpecifiedAllocationSize() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getSequenceGenerator().setSpecifiedAllocationSize(Integer.valueOf(25));
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		assertEquals(Integer.valueOf(25), sequenceGenerator.getAllocationSize());
		
		idMapping.getSequenceGenerator().setName(null);
		idMapping.getSequenceGenerator().setSpecifiedAllocationSize(null);
		assertNull(attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR));
	}
	
	

	public void testGetSequenceName() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getSequenceGenerator().getSequenceName());
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		sequenceGenerator.setSequenceName("mySequenceName");
		
		assertEquals("mySequenceName", idMapping.getSequenceGenerator().getSequenceName());
		assertEquals("mySequenceName", idMapping.getSequenceGenerator().getSpecifiedSequenceName());
	}
	
	public void testGetDefaultSequenceName() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();

		assertNull(idMapping.getSequenceGenerator().getDefaultSequenceName());
		
		idMapping.getSequenceGenerator().setSpecifiedSequenceName("mySequenceName");
		
		assertNull(idMapping.getSequenceGenerator().getDefaultSequenceName());
		assertEquals("mySequenceName", idMapping.getSequenceGenerator().getSpecifiedSequenceName());
	}
	
	public void testSetSpecifiedSequenceName() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getSequenceGenerator().setSpecifiedSequenceName("mySequenceName");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		assertEquals("mySequenceName", sequenceGenerator.getSequenceName());
		
		idMapping.getSequenceGenerator().setName(null);
		idMapping.getSequenceGenerator().setSpecifiedSequenceName(null);
		assertNull(attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR));
	}

}

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
import org.eclipse.jpt.core.internal.context.base.IGenerator;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.ISequenceGenerator;
import org.eclipse.jpt.core.internal.resource.java.Generator;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.SequenceGenerator;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaSequenceGeneratorTests extends ContextModelTestCase
{
	private static final String SEQUENCE_GENERATOR_NAME = "MY_SEQUENCE_GENERATOR";
	
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createSequenceGeneratorAnnotation() throws Exception{
		this.createAnnotationAndMembers("SequenceGenerator", 
			"String name();" +
			"String sequenceName() default \"\"; " +
			"int initialValue() default 0; " +
			"int allocationSize() default 50;");		
	}

	private IType createTestEntityWithSequenceGenerator() throws Exception {
		createEntityAnnotation();
		createSequenceGeneratorAnnotation();
	
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

		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		assertEquals(SEQUENCE_GENERATOR_NAME, idMapping.getSequenceGenerator().getName());

		//change resource model sequenceGenerator name, verify the context model is updated
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);
		
		sequenceGenerator.setName("foo");
		
		assertEquals("foo", idMapping.getSequenceGenerator().getName());
	}

	public void testSetName() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		assertEquals(SEQUENCE_GENERATOR_NAME, idMapping.getSequenceGenerator().getName());

		idMapping.getSequenceGenerator().setName("foo");
		
		assertEquals("foo", idMapping.getSequenceGenerator().getName());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);
		
		assertEquals("foo", sequenceGenerator.getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		assertEquals(SEQUENCE_GENERATOR_NAME, idMapping.getSequenceGenerator().getName());

		idMapping.getSequenceGenerator().setName(null);
		
		assertNull(idMapping.getSequenceGenerator());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);
		
		assertNull(sequenceGenerator);
	}
	
	public void testGetInitialValue() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		
		assertEquals(ISequenceGenerator.DEFAULT_INITIAL_VALUE, idMapping.getSequenceGenerator().getInitialValue());
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);	
		
		sequenceGenerator.setInitialValue(82);
		
		assertEquals(82, idMapping.getSequenceGenerator().getInitialValue());
		assertEquals(82, idMapping.getSequenceGenerator().getSpecifiedInitialValue());
	}
	
	public void testGetDefaultInitialValue() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();

		assertEquals(ISequenceGenerator.DEFAULT_INITIAL_VALUE, idMapping.getSequenceGenerator().getDefaultInitialValue());
		
		idMapping.getSequenceGenerator().setSpecifiedInitialValue(82);
		
		assertEquals(ISequenceGenerator.DEFAULT_INITIAL_VALUE, idMapping.getSequenceGenerator().getDefaultInitialValue());
		assertEquals(82, idMapping.getSequenceGenerator().getSpecifiedInitialValue());
	}
	
	public void testSetSpecifiedInitialValue() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		idMapping.getSequenceGenerator().setSpecifiedInitialValue(20);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);	
		
		assertEquals(20, sequenceGenerator.getInitialValue());
		
		idMapping.getSequenceGenerator().setName(null);
		idMapping.getSequenceGenerator().setSpecifiedInitialValue(Generator.DEFAULT_INITIAL_VALUE);
		assertNull(attributeResource.annotation(JPA.SEQUENCE_GENERATOR));
	}
	
	public void testGetAllocationSize() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		
		assertEquals(IGenerator.DEFAULT_ALLOCATION_SIZE, idMapping.getSequenceGenerator().getAllocationSize());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);	
		
		sequenceGenerator.setAllocationSize(20);
		
		assertEquals(20, idMapping.getSequenceGenerator().getAllocationSize());
		assertEquals(20, idMapping.getSequenceGenerator().getSpecifiedAllocationSize());
	}
	
	public void testGetDefaultAllocationSize() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();

		assertEquals(IGenerator.DEFAULT_ALLOCATION_SIZE, idMapping.getSequenceGenerator().getDefaultAllocationSize());
		
		idMapping.getSequenceGenerator().setSpecifiedAllocationSize(20);
		
		assertEquals(IGenerator.DEFAULT_ALLOCATION_SIZE, idMapping.getSequenceGenerator().getDefaultAllocationSize());
		assertEquals(20, idMapping.getSequenceGenerator().getSpecifiedAllocationSize());
	}
	
	public void testSetSpecifiedAllocationSize() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		idMapping.getSequenceGenerator().setSpecifiedAllocationSize(25);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);	
		
		assertEquals(25, sequenceGenerator.getAllocationSize());
		
		idMapping.getSequenceGenerator().setName(null);
		idMapping.getSequenceGenerator().setSpecifiedAllocationSize(Generator.DEFAULT_ALLOCATION_SIZE_VALUE);
		assertNull(attributeResource.annotation(JPA.SEQUENCE_GENERATOR));
	}
	
	

	public void testGetSequenceName() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		
		assertNull(idMapping.getSequenceGenerator().getSequenceName());
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);	
		
		sequenceGenerator.setSequenceName("mySequenceName");
		
		assertEquals("mySequenceName", idMapping.getSequenceGenerator().getSequenceName());
		assertEquals("mySequenceName", idMapping.getSequenceGenerator().getSpecifiedSequenceName());
	}
	
	public void testGetDefaultSequenceName() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();

		assertNull(idMapping.getSequenceGenerator().getDefaultSequenceName());
		
		idMapping.getSequenceGenerator().setSpecifiedSequenceName("mySequenceName");
		
		assertNull(idMapping.getSequenceGenerator().getDefaultSequenceName());
		assertEquals("mySequenceName", idMapping.getSequenceGenerator().getSpecifiedSequenceName());
	}
	
	public void testSetSpecifiedSequenceName() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		idMapping.getSequenceGenerator().setSpecifiedSequenceName("mySequenceName");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		SequenceGenerator sequenceGenerator = (SequenceGenerator) attributeResource.annotation(JPA.SEQUENCE_GENERATOR);	
		
		assertEquals("mySequenceName", sequenceGenerator.getSequenceName());
		
		idMapping.getSequenceGenerator().setName(null);
		idMapping.getSequenceGenerator().setSpecifiedSequenceName(null);
		assertNull(attributeResource.annotation(JPA.SEQUENCE_GENERATOR));
	}

}

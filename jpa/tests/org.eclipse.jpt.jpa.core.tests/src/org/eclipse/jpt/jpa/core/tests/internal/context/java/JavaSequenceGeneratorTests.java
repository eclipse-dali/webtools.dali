/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.context.DatabaseGenerator;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.SequenceGenerator;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class JavaSequenceGeneratorTests
	extends ContextModelTestCase
{
	private static final String SEQUENCE_GENERATOR_NAME = "MY_SEQUENCE_GENERATOR";
	

	protected ICompilationUnit createTestEntityWithSequenceGenerator() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.SEQUENCE_GENERATOR, JPA.ID);
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

		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(SEQUENCE_GENERATOR_NAME, idMapping.getGeneratorContainer().getSequenceGenerator().getName());

		//change resource model sequenceGenerator name, verify the context model is updated
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR);
		
		sequenceGenerator.setName("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("foo", idMapping.getGeneratorContainer().getSequenceGenerator().getName());
	}

	public void testSetName() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(SEQUENCE_GENERATOR_NAME, idMapping.getGeneratorContainer().getSequenceGenerator().getName());

		idMapping.getGeneratorContainer().getSequenceGenerator().setName("foo");
		
		assertEquals("foo", idMapping.getGeneratorContainer().getSequenceGenerator().getName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR);
		
		assertEquals("foo", sequenceGenerator.getName());
	}
	
	public void testGetInitialValue() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertEquals(SequenceGenerator.DEFAULT_INITIAL_VALUE, idMapping.getGeneratorContainer().getSequenceGenerator().getInitialValue());
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		sequenceGenerator.setInitialValue(Integer.valueOf(82));
		getJpaProject().synchronizeContextModel();
		
		assertEquals(82, idMapping.getGeneratorContainer().getSequenceGenerator().getInitialValue());
		assertEquals(Integer.valueOf(82), idMapping.getGeneratorContainer().getSequenceGenerator().getSpecifiedInitialValue());
	}
	
	public void testGetDefaultInitialValue() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();

		assertEquals(SequenceGenerator.DEFAULT_INITIAL_VALUE, idMapping.getGeneratorContainer().getSequenceGenerator().getDefaultInitialValue());
		
		idMapping.getGeneratorContainer().getSequenceGenerator().setSpecifiedInitialValue(Integer.valueOf(82));
		
		assertEquals(SequenceGenerator.DEFAULT_INITIAL_VALUE, idMapping.getGeneratorContainer().getSequenceGenerator().getDefaultInitialValue());
		assertEquals(Integer.valueOf(82), idMapping.getGeneratorContainer().getSequenceGenerator().getSpecifiedInitialValue());
	}
	
	public void testSetSpecifiedInitialValue() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getGeneratorContainer().getSequenceGenerator().setSpecifiedInitialValue(Integer.valueOf(20));
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SequenceGeneratorAnnotation generatorAnnotation = (SequenceGeneratorAnnotation) resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		assertEquals(Integer.valueOf(20), generatorAnnotation.getInitialValue());
	}
	
	public void testGetAllocationSize() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertEquals(DatabaseGenerator.DEFAULT_ALLOCATION_SIZE, idMapping.getGeneratorContainer().getSequenceGenerator().getAllocationSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		sequenceGenerator.setAllocationSize(Integer.valueOf(20));
		getJpaProject().synchronizeContextModel();
		
		assertEquals(20, idMapping.getGeneratorContainer().getSequenceGenerator().getAllocationSize());
		assertEquals(Integer.valueOf(20), idMapping.getGeneratorContainer().getSequenceGenerator().getSpecifiedAllocationSize());
	}
	
	public void testGetDefaultAllocationSize() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();

		assertEquals(DatabaseGenerator.DEFAULT_ALLOCATION_SIZE, idMapping.getGeneratorContainer().getSequenceGenerator().getDefaultAllocationSize());
		
		idMapping.getGeneratorContainer().getSequenceGenerator().setSpecifiedAllocationSize(Integer.valueOf(20));
		
		assertEquals(DatabaseGenerator.DEFAULT_ALLOCATION_SIZE, idMapping.getGeneratorContainer().getSequenceGenerator().getDefaultAllocationSize());
		assertEquals(Integer.valueOf(20), idMapping.getGeneratorContainer().getSequenceGenerator().getSpecifiedAllocationSize());
	}
	
	public void testSetSpecifiedAllocationSize() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getGeneratorContainer().getSequenceGenerator().setSpecifiedAllocationSize(Integer.valueOf(25));
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SequenceGeneratorAnnotation generatorAnnotation = (SequenceGeneratorAnnotation) resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		assertEquals(Integer.valueOf(25), generatorAnnotation.getAllocationSize());
	}
	
	

	public void testGetSequenceName() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getGeneratorContainer().getSequenceGenerator().getSequenceName());
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SequenceGeneratorAnnotation sequenceGenerator = (SequenceGeneratorAnnotation) resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		sequenceGenerator.setSequenceName("mySequenceName");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("mySequenceName", idMapping.getGeneratorContainer().getSequenceGenerator().getSequenceName());
		assertEquals("mySequenceName", idMapping.getGeneratorContainer().getSequenceGenerator().getSpecifiedSequenceName());
	}
	
	public void testGetDefaultSequenceName() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();

		assertNull(idMapping.getGeneratorContainer().getSequenceGenerator().getDefaultSequenceName());
		
		idMapping.getGeneratorContainer().getSequenceGenerator().setSpecifiedSequenceName("mySequenceName");
		
		assertNull(idMapping.getGeneratorContainer().getSequenceGenerator().getDefaultSequenceName());
		assertEquals("mySequenceName", idMapping.getGeneratorContainer().getSequenceGenerator().getSpecifiedSequenceName());
	}
	
	public void testSetSpecifiedSequenceName() throws Exception {
		createTestEntityWithSequenceGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getGeneratorContainer().getSequenceGenerator().setSpecifiedSequenceName("mySequenceName");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		SequenceGeneratorAnnotation generatorAnnotation = (SequenceGeneratorAnnotation) resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		assertEquals("mySequenceName", generatorAnnotation.getSequenceName());
		
		idMapping.getGeneratorContainer().getSequenceGenerator().setName(null);
		idMapping.getGeneratorContainer().getSequenceGenerator().setSpecifiedSequenceName(null);
		generatorAnnotation = (SequenceGeneratorAnnotation) resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR);
		assertNull(generatorAnnotation.getName());
	}
}

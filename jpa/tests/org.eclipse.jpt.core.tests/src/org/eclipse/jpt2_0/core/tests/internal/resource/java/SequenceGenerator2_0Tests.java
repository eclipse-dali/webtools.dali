/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt2_0.core.tests.internal.resource.java;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt2_0.core.internal.platform.Generic2_0JpaAnnotationDefinitionProvider;
import org.eclipse.jpt2_0.core.resource.java.SequenceGenerator2_0Annotation;

/**
 *  SequenceGenerator2_0Tests
 */
@SuppressWarnings("nls")
public class SequenceGenerator2_0Tests extends Generic2_0JavaResourceModelTestCase {

	private static final String GENERATOR_CATALOG = "TEST_CATALOG";
	private static final String GENERATOR_SCHEMA = "TEST_SCHEMA";

	public SequenceGenerator2_0Tests(String name) {
		super(name);
	}

	@Override
	protected JpaAnnotationDefinitionProvider annotationDefinitionProvider() {
		return Generic2_0JpaAnnotationDefinitionProvider.instance();
	}
	
	// ********** catalog **********

	public void testGetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestSequenceGeneratorWithCatalog();	
		JavaResourcePersistentType typeResource = this.buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		SequenceGenerator2_0Annotation sequenceGenerator = (SequenceGenerator2_0Annotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_CATALOG, sequenceGenerator.getCatalog());
	}

	public void testSetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestSequenceGeneratorWithCatalog();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		SequenceGenerator2_0Annotation sequenceGenerator = (SequenceGenerator2_0Annotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_CATALOG, sequenceGenerator.getCatalog());
		
		sequenceGenerator.setCatalog("foo");
		assertEquals("foo", sequenceGenerator.getCatalog());
		
		assertSourceContains("@SequenceGenerator(catalog = \"foo\")", cu);
		
		sequenceGenerator.setCatalog(null);
		assertNull(sequenceGenerator.getCatalog());
		
		assertSourceDoesNotContain("@SequenceGenerator", cu);
	}
	
	// ********** schema **********
	
	public void testGetSchema() throws Exception {
		ICompilationUnit cu = this.createTestSequenceGeneratorWithSchema();
		JavaResourcePersistentType typeResource = this.buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		SequenceGenerator2_0Annotation sequenceGenerator = (SequenceGenerator2_0Annotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_SCHEMA, sequenceGenerator.getSchema());
	}

	public void testSetSchema() throws Exception {
		ICompilationUnit cu = this.createTestSequenceGeneratorWithSchema();
		JavaResourcePersistentType typeResource = this.buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		SequenceGenerator2_0Annotation sequenceGenerator = (SequenceGenerator2_0Annotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_SCHEMA, sequenceGenerator.getSchema());
		
		sequenceGenerator.setSchema("foo");
		assertEquals("foo", sequenceGenerator.getSchema());
		
		assertSourceContains("@SequenceGenerator(schema = \"foo\")", cu);
		
		sequenceGenerator.setSchema(null);
		assertNull(sequenceGenerator.getSchema());
		
		assertSourceDoesNotContain("@SequenceGenerator", cu);
	}
	
	// ********** utility **********
		
	protected ICompilationUnit createTestSequenceGeneratorWithStringElement(final String elementName, final String value) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.SEQUENCE_GENERATOR);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@SequenceGenerator(" + elementName + " = \"" + value + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestSequenceGeneratorWithCatalog() throws Exception {
		return this.createTestSequenceGeneratorWithStringElement("catalog", GENERATOR_CATALOG);
	}

	private ICompilationUnit createTestSequenceGeneratorWithSchema() throws Exception {
		return this.createTestSequenceGeneratorWithStringElement("schema", GENERATOR_SCHEMA);
	}
}

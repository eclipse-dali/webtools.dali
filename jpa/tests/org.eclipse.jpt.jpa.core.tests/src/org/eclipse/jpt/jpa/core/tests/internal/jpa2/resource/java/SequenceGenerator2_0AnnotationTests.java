/*******************************************************************************
* Copyright (c) 2009, 2013 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0, which accompanies this distribution
* and is available at https://www.eclipse.org/legal/epl-2.0/.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.resource.java;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.SequenceGeneratorAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 *  SequenceGenerator2_0Tests
 */
@SuppressWarnings("nls")
public class SequenceGenerator2_0AnnotationTests extends JavaResourceModel2_0TestCase {

	private static final String GENERATOR_CATALOG = "TEST_CATALOG";
	private static final String GENERATOR_SCHEMA = "TEST_SCHEMA";

	public SequenceGenerator2_0AnnotationTests(String name) {
		super(name);
	}

	// ********** catalog **********

	public void testGetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestSequenceGeneratorWithCatalog();	
		JavaResourceType resourceType = this.buildJavaResourceType(cu);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		SequenceGeneratorAnnotation2_0 sequenceGenerator = (SequenceGeneratorAnnotation2_0) resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_CATALOG, sequenceGenerator.getCatalog());
	}

	public void testSetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestSequenceGeneratorWithCatalog();
		JavaResourceType resourceType = this.buildJavaResourceType(cu);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		SequenceGeneratorAnnotation2_0 sequenceGenerator = (SequenceGeneratorAnnotation2_0) resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_CATALOG, sequenceGenerator.getCatalog());
		
		sequenceGenerator.setCatalog("foo");
		assertEquals("foo", sequenceGenerator.getCatalog());
		
		assertSourceContains("@SequenceGenerator(catalog = \"foo\")", cu);
		
		sequenceGenerator.setCatalog(null);
		assertNull(sequenceGenerator.getCatalog());
		
		assertSourceDoesNotContain("@SequenceGenerator(", cu);
	}
	
	// ********** schema **********
	
	public void testGetSchema() throws Exception {
		ICompilationUnit cu = this.createTestSequenceGeneratorWithSchema();
		JavaResourceType resourceType = this.buildJavaResourceType(cu);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		SequenceGeneratorAnnotation2_0 sequenceGenerator = (SequenceGeneratorAnnotation2_0) resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_SCHEMA, sequenceGenerator.getSchema());
	}

	public void testSetSchema() throws Exception {
		ICompilationUnit cu = this.createTestSequenceGeneratorWithSchema();
		JavaResourceType resourceType = this.buildJavaResourceType(cu);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		SequenceGeneratorAnnotation2_0 sequenceGenerator = (SequenceGeneratorAnnotation2_0) resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR);
		assertEquals(GENERATOR_SCHEMA, sequenceGenerator.getSchema());
		
		sequenceGenerator.setSchema("foo");
		assertEquals("foo", sequenceGenerator.getSchema());
		
		assertSourceContains("@SequenceGenerator(schema = \"foo\")", cu);
		
		sequenceGenerator.setSchema(null);
		assertNull(sequenceGenerator.getSchema());
		
		assertSourceDoesNotContain("@SequenceGenerator(", cu);
	}
	
	// ********** utility **********
		
	protected ICompilationUnit createTestSequenceGeneratorWithStringElement(final String elementName, final String value) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.SEQUENCE_GENERATOR);
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

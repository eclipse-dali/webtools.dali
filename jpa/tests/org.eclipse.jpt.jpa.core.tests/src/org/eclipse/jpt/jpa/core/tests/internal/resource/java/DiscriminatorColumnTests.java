/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

@SuppressWarnings("nls")
public class DiscriminatorColumnTests extends JpaJavaResourceModelTestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String COLUMN_COLUMN_DEFINITION = "COLUMN_DEFINITION";
	
	public DiscriminatorColumnTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestDiscriminatorColumn() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@DiscriminatorColumn");
			}
		});
	}
	
	private ICompilationUnit createTestDiscriminatorColumnWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@DiscriminatorColumn(name = \"" + COLUMN_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestDiscriminatorColumnWithColumnDefinition() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@DiscriminatorColumn(columnDefinition = \"" + COLUMN_COLUMN_DEFINITION + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestDiscriminatorColumnWithDiscriminatorType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.DISCRIMINATOR_COLUMN, JPA.DISCRIMINATOR_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@DiscriminatorColumn(discriminatorType = DiscriminatorType.CHAR)");
			}
		});
	}
	
	private ICompilationUnit createTestColumnWithIntElement(final String intElement) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@DiscriminatorColumn(" + intElement + " = 5)");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestDiscriminatorColumnWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) resourceType.getAnnotation(JPA.DISCRIMINATOR_COLUMN);
		assertNotNull(column);
		assertEquals(COLUMN_NAME, column.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestDiscriminatorColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) resourceType.getAnnotation(JPA.DISCRIMINATOR_COLUMN);
		assertNotNull(column);
		assertNull(column.getName());
		assertNull(column.getDiscriminatorType());
		assertNull(column.getColumnDefinition());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestDiscriminatorColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) resourceType.getAnnotation(JPA.DISCRIMINATOR_COLUMN);

		assertNotNull(column);
		assertNull(column.getName());

		column.setName("Foo");
		assertEquals("Foo", column.getName());
		
		assertSourceContains("@DiscriminatorColumn(name = \"Foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestDiscriminatorColumnWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) resourceType.getAnnotation(JPA.DISCRIMINATOR_COLUMN);

		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName(null);
		assertNull(column.getName());
		
		assertSourceDoesNotContain("(name", cu);
	}

	public void testGetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestDiscriminatorColumnWithColumnDefinition();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) resourceType.getAnnotation(JPA.DISCRIMINATOR_COLUMN);
		assertEquals(COLUMN_COLUMN_DEFINITION, column.getColumnDefinition());
	}

	public void testSetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestDiscriminatorColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) resourceType.getAnnotation(JPA.DISCRIMINATOR_COLUMN);

		assertNotNull(column);
		assertNull(column.getColumnDefinition());

		column.setColumnDefinition("Foo");
		assertEquals("Foo", column.getColumnDefinition());
		
		assertSourceContains("@DiscriminatorColumn(columnDefinition = \"Foo\")", cu);

		
		column.setColumnDefinition(null);
		assertSourceDoesNotContain("(columnDefinition", cu);
	}

	public void testGetLength() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithIntElement("length");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) resourceType.getAnnotation(JPA.DISCRIMINATOR_COLUMN);

		assertEquals(Integer.valueOf(5), column.getLength());
	}
	
	public void testSetLength() throws Exception {
		ICompilationUnit cu = this.createTestDiscriminatorColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) resourceType.getAnnotation(JPA.DISCRIMINATOR_COLUMN);

		assertNotNull(column);
		assertNull(column.getLength());

		column.setLength(Integer.valueOf(5));
		assertEquals(Integer.valueOf(5), column.getLength());
		
		assertSourceContains("@DiscriminatorColumn(length = 5)", cu);
		
		column.setLength(null);
		assertSourceDoesNotContain("(length", cu);
	}
	
	public void testGetDiscriminatorType() throws Exception {
		ICompilationUnit cu = this.createTestDiscriminatorColumnWithDiscriminatorType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) resourceType.getAnnotation(JPA.DISCRIMINATOR_COLUMN);
		assertEquals(DiscriminatorType.CHAR, column.getDiscriminatorType());
	}
	
	public void testSetDiscriminatorType() throws Exception {
		ICompilationUnit cu = this.createTestDiscriminatorColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) resourceType.getAnnotation(JPA.DISCRIMINATOR_COLUMN);

		assertNull(column.getDiscriminatorType());

		column.setDiscriminatorType(DiscriminatorType.INTEGER);
		assertEquals(DiscriminatorType.INTEGER, column.getDiscriminatorType());
		
		assertSourceContains("@DiscriminatorColumn(discriminatorType = INTEGER)", cu);
		
		column.setDiscriminatorType(null);
		assertSourceDoesNotContain("(discriminatorType", cu);
	}
}

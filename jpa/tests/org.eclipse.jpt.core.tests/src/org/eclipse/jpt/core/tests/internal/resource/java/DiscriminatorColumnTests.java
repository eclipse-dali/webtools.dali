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
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorColumn;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorType;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class DiscriminatorColumnTests extends JavaResourceModelTestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String COLUMN_COLUMN_DEFINITION = "COLUMN_DEFINITION";
	
	public DiscriminatorColumnTests(String name) {
		super(name);
	}
		
	private void createDiscriminatorColumnAnnotation() throws Exception {
		this.createEnumAndMembers("DiscriminatorType", "STRING, CHAR, INTEGER");
		this.createAnnotationAndMembers("DiscriminatorColumn", 
			"String name() default \"DTYPE\"; " +
			"DiscriminatorType discriminatorType() default STRING; " +
			"String columnDefinition() default \"\"; " +
			"int length() default 31;");
	}

	private IType createTestDiscriminatorColumn() throws Exception {
		createDiscriminatorColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@DiscriminatorColumn");
			}
		});
	}
	
	private IType createTestDiscriminatorColumnWithName() throws Exception {
		createDiscriminatorColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@DiscriminatorColumn(name=\"" + COLUMN_NAME + "\")");
			}
		});
	}
	
	private IType createTestDiscriminatorColumnWithColumnDefinition() throws Exception {
		createDiscriminatorColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@DiscriminatorColumn(columnDefinition=\"" + COLUMN_COLUMN_DEFINITION + "\")");
			}
		});
	}
	
	private IType createTestDiscriminatorColumnWithDiscriminatorType() throws Exception {
		createDiscriminatorColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.DISCRIMINATOR_COLUMN, JPA.DISCRIMINATOR_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@DiscriminatorColumn(discriminatorType=DiscriminatorType.CHAR)");
			}
		});
	}
	
	private IType createTestColumnWithIntElement(final String intElement) throws Exception {
		createDiscriminatorColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@DiscriminatorColumn(" + intElement + "=5)");
			}
		});
	}

	public void testGetName() throws Exception {
		IType testType = this.createTestDiscriminatorColumnWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		DiscriminatorColumn column = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);
		assertNotNull(column);
		assertEquals(COLUMN_NAME, column.getName());
	}

	public void testGetNull() throws Exception {
		IType testType = this.createTestDiscriminatorColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		DiscriminatorColumn column = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);
		assertNotNull(column);
		assertNull(column.getName());
		assertNull(column.getDiscriminatorType());
		assertNull(column.getColumnDefinition());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestDiscriminatorColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		DiscriminatorColumn column = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);

		assertNotNull(column);
		assertNull(column.getName());

		column.setName("Foo");
		assertEquals("Foo", column.getName());
		
		assertSourceContains("@DiscriminatorColumn(name=\"Foo\")");
	}
	
	public void testSetNameNull() throws Exception {
		IType testType = this.createTestDiscriminatorColumnWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		DiscriminatorColumn column = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);

		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName(null);
		assertNull(column.getName());
		
		assertSourceDoesNotContain("@DiscriminatorColumn");
	}

	public void testGetColumnDefinition() throws Exception {
		IType testType = this.createTestDiscriminatorColumnWithColumnDefinition();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		DiscriminatorColumn column = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);
		assertEquals(COLUMN_COLUMN_DEFINITION, column.getColumnDefinition());
	}

	public void testSetColumnDefinition() throws Exception {
		IType testType = this.createTestDiscriminatorColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		DiscriminatorColumn column = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);

		assertNotNull(column);
		assertNull(column.getColumnDefinition());

		column.setColumnDefinition("Foo");
		assertEquals("Foo", column.getColumnDefinition());
		
		assertSourceContains("@DiscriminatorColumn(columnDefinition=\"Foo\")");

		
		column.setColumnDefinition(null);
		assertSourceDoesNotContain("@DiscriminatorColumn");
	}

	public void testGetLength() throws Exception {
		IType testType = this.createTestColumnWithIntElement("length");
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		DiscriminatorColumn column = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);

		assertEquals(Integer.valueOf(5), column.getLength());
	}
	
	public void testSetLength() throws Exception {
		IType testType = this.createTestDiscriminatorColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		DiscriminatorColumn column = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);

		assertNotNull(column);
		assertNull(column.getLength());

		column.setLength(Integer.valueOf(5));
		assertEquals(Integer.valueOf(5), column.getLength());
		
		assertSourceContains("@DiscriminatorColumn(length=5)");
		
		column.setLength(null);
		assertSourceDoesNotContain("@DiscriminatorColumn");
	}
	
	public void testGetDiscriminatorType() throws Exception {
		IType testType = this.createTestDiscriminatorColumnWithDiscriminatorType();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		DiscriminatorColumn column = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);
		assertEquals(DiscriminatorType.CHAR, column.getDiscriminatorType());
	}
	
	public void testSetDiscriminatorType() throws Exception {
		IType testType = this.createTestDiscriminatorColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		DiscriminatorColumn column = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);

		assertNull(column.getDiscriminatorType());

		column.setDiscriminatorType(DiscriminatorType.INTEGER);
		assertEquals(DiscriminatorType.INTEGER, column.getDiscriminatorType());
		
		assertSourceContains("@DiscriminatorColumn(discriminatorType=INTEGER)");
		
		column.setDiscriminatorType(null);
		assertSourceDoesNotContain("@DiscriminatorColumn");
	}
}

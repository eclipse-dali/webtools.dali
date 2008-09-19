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
import org.eclipse.jpt.core.context.DiscriminatorColumn;
import org.eclipse.jpt.core.context.DiscriminatorType;
import org.eclipse.jpt.core.resource.java.DiscriminatorColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaDiscriminatorColumnTests extends ContextModelTestCase
{
	private static final String DISCRIMINATOR_COLUMN_NAME = "MY_DISCRIMINATOR_COLUMN";
	private static final String COLUMN_DEFINITION = "MY_COLUMN_DEFINITION";
	

	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}

	private ICompilationUnit createTestEntityWithDiscriminatorColumn() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@DiscriminatorColumn(name=\"" + DISCRIMINATOR_COLUMN_NAME + "\")");
			}
		});
	}

		
	public JavaDiscriminatorColumnTests(String name) {
		super(name);
	}
		
	public void testGetSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedName());
	}

	public void testGetSpecifiedName() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(DISCRIMINATOR_COLUMN_NAME, javaEntity().getDiscriminatorColumn().getSpecifiedName());
	}
	
	public void testGetDefaultNameSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals("DTYPE", javaEntity().getDiscriminatorColumn().getDefaultName());
	}

	public void testGetDefaultName() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals("DTYPE", javaEntity().getDiscriminatorColumn().getDefaultName());
		
		javaEntity().getDiscriminatorColumn().setSpecifiedName("foo");
		assertEquals("DTYPE", javaEntity().getDiscriminatorColumn().getDefaultName());
	}
	
	public void testGetNameSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals("DTYPE", javaEntity().getDiscriminatorColumn().getName());
	}
	
	public void testGetName() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(DISCRIMINATOR_COLUMN_NAME, javaEntity().getDiscriminatorColumn().getName());
	}

	public void testSetSpecifiedName() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		javaEntity().getDiscriminatorColumn().setSpecifiedName("foo");
		
		assertEquals("foo", javaEntity().getDiscriminatorColumn().getSpecifiedName());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation discriminatorColumn = (DiscriminatorColumnAnnotation) typeResource.getAnnotation(JPA.DISCRIMINATOR_COLUMN);
		
		assertEquals("foo", discriminatorColumn.getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		javaEntity().getDiscriminatorColumn().setSpecifiedName(null);
		
		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedName());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation discriminatorColumn = (DiscriminatorColumnAnnotation) typeResource.getAnnotation(JPA.DISCRIMINATOR_COLUMN);
	
		assertNull(discriminatorColumn);
	}
	
	public void testGetDefaultDiscriminatorType() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(DiscriminatorType.STRING, javaEntity().getDiscriminatorColumn().getDefaultDiscriminatorType());
	}
	
	public void testGetDiscriminatorType() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(DiscriminatorType.STRING, javaEntity().getDiscriminatorColumn().getDiscriminatorType());
		
		javaEntity().getDiscriminatorColumn().setSpecifiedDiscriminatorType(DiscriminatorType.CHAR);
		assertEquals(DiscriminatorType.CHAR, javaEntity().getDiscriminatorColumn().getDiscriminatorType());
	}
	
	public void testGetSpecifiedDiscriminatorType() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
		
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation discriminatorColumn = (DiscriminatorColumnAnnotation) typeResource.getAnnotation(JPA.DISCRIMINATOR_COLUMN);
		discriminatorColumn.setDiscriminatorType(org.eclipse.jpt.core.resource.java.DiscriminatorType.CHAR);
		
		assertEquals(DiscriminatorType.CHAR, javaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
	}
	
	public void testSetSpecifiedDiscriminatorType() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
		
		javaEntity().getDiscriminatorColumn().setSpecifiedDiscriminatorType(DiscriminatorType.CHAR);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation discriminatorColumn = (DiscriminatorColumnAnnotation) typeResource.getAnnotation(JPA.DISCRIMINATOR_COLUMN);
		
		assertEquals(org.eclipse.jpt.core.resource.java.DiscriminatorType.CHAR, discriminatorColumn.getDiscriminatorType());
		
		javaEntity().getDiscriminatorColumn().setSpecifiedName(null);
		javaEntity().getDiscriminatorColumn().setSpecifiedDiscriminatorType(null);
		assertNull(typeResource.getAnnotation(JPA.DISCRIMINATOR_COLUMN));
	}
	
	public void testGetDiscriminatorTypeUpdatesFromResourceChange() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) typeResource.addAnnotation(JPA.DISCRIMINATOR_COLUMN);

		column.setDiscriminatorType(org.eclipse.jpt.core.resource.java.DiscriminatorType.INTEGER);
		assertEquals(DiscriminatorType.INTEGER, javaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
		assertEquals(DiscriminatorType.INTEGER, javaEntity().getDiscriminatorColumn().getDiscriminatorType());
		
		column.setDiscriminatorType(null);
		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
		assertEquals(DiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE, javaEntity().getDiscriminatorColumn().getDiscriminatorType());
	}

	public void testGetLength() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(DiscriminatorColumn.DEFAULT_LENGTH, javaEntity().getDiscriminatorColumn().getLength());
		javaEntity().getDiscriminatorColumn().setSpecifiedLength(Integer.valueOf(55));
		assertEquals(Integer.valueOf(55), javaEntity().getDiscriminatorColumn().getLength());
	}
	
	public void testGetDefaultLength() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(DiscriminatorColumn.DEFAULT_LENGTH, javaEntity().getDiscriminatorColumn().getDefaultLength());
		javaEntity().getDiscriminatorColumn().setSpecifiedLength(Integer.valueOf(55));
		
		assertEquals(DiscriminatorColumn.DEFAULT_LENGTH, javaEntity().getDiscriminatorColumn().getDefaultLength());
	}	
	
	public void testGetSpecifiedLength() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedLength());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation discriminatorColumn = (DiscriminatorColumnAnnotation) typeResource.getAnnotation(JPA.DISCRIMINATOR_COLUMN);
		discriminatorColumn.setLength(Integer.valueOf(66));
		
		assertEquals(Integer.valueOf(66), javaEntity().getDiscriminatorColumn().getSpecifiedLength());
		assertEquals(Integer.valueOf(66), javaEntity().getDiscriminatorColumn().getLength());		
		discriminatorColumn.setName(null);
		discriminatorColumn.setLength(null);
		
		assertNull(typeResource.getAnnotation(JPA.DISCRIMINATOR_COLUMN));
		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedLength());	
	}	
	
	public void testSetSpecifiedLength() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedLength());
		
		javaEntity().getDiscriminatorColumn().setSpecifiedLength(Integer.valueOf(100));
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation discriminatorColumn = (DiscriminatorColumnAnnotation) typeResource.getAnnotation(JPA.DISCRIMINATOR_COLUMN);
		
		assertEquals(Integer.valueOf(100), discriminatorColumn.getLength());
		
		javaEntity().getDiscriminatorColumn().setSpecifiedName(null);
		javaEntity().getDiscriminatorColumn().setSpecifiedLength(null);
		assertNull(typeResource.getAnnotation(JPA.DISCRIMINATOR_COLUMN));
	}
	
	public void testGetLengthUpdatesFromResourceChange() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedLength());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) typeResource.addAnnotation(JPA.DISCRIMINATOR_COLUMN);

		column.setLength(Integer.valueOf(78));
		assertEquals(Integer.valueOf(78), javaEntity().getDiscriminatorColumn().getSpecifiedLength());
		assertEquals(Integer.valueOf(78), javaEntity().getDiscriminatorColumn().getLength());
		
		column.setLength(null);
		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedLength());
		assertEquals(DiscriminatorColumn.DEFAULT_LENGTH, javaEntity().getDiscriminatorColumn().getLength());
	}

	
	public void testGetColumnDefinition() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(javaEntity().getDiscriminatorColumn().getColumnDefinition());

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) typeResource.getAnnotation(JPA.DISCRIMINATOR_COLUMN);
		column.setColumnDefinition(COLUMN_DEFINITION);
		
		assertEquals(COLUMN_DEFINITION, javaEntity().getDiscriminatorColumn().getColumnDefinition());
		
		column.setColumnDefinition(null);
		
		assertNull(javaEntity().getDiscriminatorColumn().getColumnDefinition());

		typeResource.removeAnnotation(JPA.DISCRIMINATOR_COLUMN);
	}
	
	public void testSetColumnDefinition() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().getDiscriminatorColumn().setColumnDefinition("foo");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) typeResource.getAnnotation(JPA.DISCRIMINATOR_COLUMN);
		
		assertEquals("foo", column.getColumnDefinition());
		
		javaEntity().getDiscriminatorColumn().setColumnDefinition(null);
		column = (DiscriminatorColumnAnnotation) typeResource.getAnnotation(JPA.DISCRIMINATOR_COLUMN);
		assertNull(column.getColumnDefinition());
	}

}

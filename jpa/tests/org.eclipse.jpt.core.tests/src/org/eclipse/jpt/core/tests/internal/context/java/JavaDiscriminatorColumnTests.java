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

@SuppressWarnings("nls")
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

		assertNull(getJavaEntity().getDiscriminatorColumn().getSpecifiedName());
	}

	public void testGetSpecifiedName() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(DISCRIMINATOR_COLUMN_NAME, getJavaEntity().getDiscriminatorColumn().getSpecifiedName());
	}
	
	public void testGetDefaultNameSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals("DTYPE", getJavaEntity().getDiscriminatorColumn().getDefaultName());
	}

	public void testGetDefaultName() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals("DTYPE", getJavaEntity().getDiscriminatorColumn().getDefaultName());
		
		getJavaEntity().getDiscriminatorColumn().setSpecifiedName("foo");
		assertEquals("DTYPE", getJavaEntity().getDiscriminatorColumn().getDefaultName());
	}
	
	public void testGetNameSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals("DTYPE", getJavaEntity().getDiscriminatorColumn().getName());
	}
	
	public void testGetName() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(DISCRIMINATOR_COLUMN_NAME, getJavaEntity().getDiscriminatorColumn().getName());
	}

	public void testSetSpecifiedName() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		getJavaEntity().getDiscriminatorColumn().setSpecifiedName("foo");
		
		assertEquals("foo", getJavaEntity().getDiscriminatorColumn().getSpecifiedName());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation discriminatorColumn = (DiscriminatorColumnAnnotation) typeResource.getSupportingAnnotation(JPA.DISCRIMINATOR_COLUMN);
		
		assertEquals("foo", discriminatorColumn.getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		getJavaEntity().getDiscriminatorColumn().setSpecifiedName(null);
		
		assertNull(getJavaEntity().getDiscriminatorColumn().getSpecifiedName());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation discriminatorColumn = (DiscriminatorColumnAnnotation) typeResource.getSupportingAnnotation(JPA.DISCRIMINATOR_COLUMN);
	
		assertNull(discriminatorColumn);
	}
	
	public void testGetDefaultDiscriminatorType() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(DiscriminatorType.STRING, getJavaEntity().getDiscriminatorColumn().getDefaultDiscriminatorType());
	}
	
	public void testGetDiscriminatorType() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(DiscriminatorType.STRING, getJavaEntity().getDiscriminatorColumn().getDiscriminatorType());
		
		getJavaEntity().getDiscriminatorColumn().setSpecifiedDiscriminatorType(DiscriminatorType.CHAR);
		assertEquals(DiscriminatorType.CHAR, getJavaEntity().getDiscriminatorColumn().getDiscriminatorType());
	}
	
	public void testGetSpecifiedDiscriminatorType() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(getJavaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation discriminatorColumn = (DiscriminatorColumnAnnotation) typeResource.getSupportingAnnotation(JPA.DISCRIMINATOR_COLUMN);
		discriminatorColumn.setDiscriminatorType(org.eclipse.jpt.core.resource.java.DiscriminatorType.CHAR);
		
		assertEquals(DiscriminatorType.CHAR, getJavaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
	}
	
	public void testSetSpecifiedDiscriminatorType() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(getJavaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
		
		getJavaEntity().getDiscriminatorColumn().setSpecifiedDiscriminatorType(DiscriminatorType.CHAR);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation discriminatorColumn = (DiscriminatorColumnAnnotation) typeResource.getSupportingAnnotation(JPA.DISCRIMINATOR_COLUMN);
		
		assertEquals(org.eclipse.jpt.core.resource.java.DiscriminatorType.CHAR, discriminatorColumn.getDiscriminatorType());
		
		getJavaEntity().getDiscriminatorColumn().setSpecifiedName(null);
		getJavaEntity().getDiscriminatorColumn().setSpecifiedDiscriminatorType(null);
		assertNull(typeResource.getSupportingAnnotation(JPA.DISCRIMINATOR_COLUMN));
	}
	
	public void testGetDiscriminatorTypeUpdatesFromResourceChange() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(getJavaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) typeResource.addSupportingAnnotation(JPA.DISCRIMINATOR_COLUMN);

		column.setDiscriminatorType(org.eclipse.jpt.core.resource.java.DiscriminatorType.INTEGER);
		assertEquals(DiscriminatorType.INTEGER, getJavaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
		assertEquals(DiscriminatorType.INTEGER, getJavaEntity().getDiscriminatorColumn().getDiscriminatorType());
		
		column.setDiscriminatorType(null);
		assertNull(getJavaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
		assertEquals(DiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE, getJavaEntity().getDiscriminatorColumn().getDiscriminatorType());
	}

	public void testGetLength() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(DiscriminatorColumn.DEFAULT_LENGTH, getJavaEntity().getDiscriminatorColumn().getLength());
		getJavaEntity().getDiscriminatorColumn().setSpecifiedLength(Integer.valueOf(55));
		assertEquals(55, getJavaEntity().getDiscriminatorColumn().getLength());
	}
	
	public void testGetDefaultLength() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(DiscriminatorColumn.DEFAULT_LENGTH, getJavaEntity().getDiscriminatorColumn().getDefaultLength());
		getJavaEntity().getDiscriminatorColumn().setSpecifiedLength(Integer.valueOf(55));
		
		assertEquals(DiscriminatorColumn.DEFAULT_LENGTH, getJavaEntity().getDiscriminatorColumn().getDefaultLength());
	}	
	
	public void testGetSpecifiedLength() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(getJavaEntity().getDiscriminatorColumn().getSpecifiedLength());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation discriminatorColumn = (DiscriminatorColumnAnnotation) typeResource.getSupportingAnnotation(JPA.DISCRIMINATOR_COLUMN);
		discriminatorColumn.setLength(Integer.valueOf(66));
		
		assertEquals(Integer.valueOf(66), getJavaEntity().getDiscriminatorColumn().getSpecifiedLength());
		assertEquals(66, getJavaEntity().getDiscriminatorColumn().getLength());		
		discriminatorColumn.setName(null);
		discriminatorColumn.setLength(null);
		
		assertNull(typeResource.getSupportingAnnotation(JPA.DISCRIMINATOR_COLUMN));
		assertNull(getJavaEntity().getDiscriminatorColumn().getSpecifiedLength());	
	}	
	
	public void testSetSpecifiedLength() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(getJavaEntity().getDiscriminatorColumn().getSpecifiedLength());
		
		getJavaEntity().getDiscriminatorColumn().setSpecifiedLength(Integer.valueOf(100));
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation discriminatorColumn = (DiscriminatorColumnAnnotation) typeResource.getSupportingAnnotation(JPA.DISCRIMINATOR_COLUMN);
		
		assertEquals(Integer.valueOf(100), discriminatorColumn.getLength());
		
		getJavaEntity().getDiscriminatorColumn().setSpecifiedName(null);
		getJavaEntity().getDiscriminatorColumn().setSpecifiedLength(null);
		assertNull(typeResource.getSupportingAnnotation(JPA.DISCRIMINATOR_COLUMN));
	}
	
	public void testGetLengthUpdatesFromResourceChange() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(getJavaEntity().getDiscriminatorColumn().getSpecifiedLength());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) typeResource.addSupportingAnnotation(JPA.DISCRIMINATOR_COLUMN);

		column.setLength(Integer.valueOf(78));
		assertEquals(Integer.valueOf(78), getJavaEntity().getDiscriminatorColumn().getSpecifiedLength());
		assertEquals(78, getJavaEntity().getDiscriminatorColumn().getLength());
		
		column.setLength(null);
		assertNull(getJavaEntity().getDiscriminatorColumn().getSpecifiedLength());
		assertEquals(DiscriminatorColumn.DEFAULT_LENGTH, getJavaEntity().getDiscriminatorColumn().getLength());
	}

	
	public void testGetColumnDefinition() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(getJavaEntity().getDiscriminatorColumn().getColumnDefinition());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) typeResource.getSupportingAnnotation(JPA.DISCRIMINATOR_COLUMN);
		column.setColumnDefinition(COLUMN_DEFINITION);
		
		assertEquals(COLUMN_DEFINITION, getJavaEntity().getDiscriminatorColumn().getColumnDefinition());
		
		column.setColumnDefinition(null);
		
		assertNull(getJavaEntity().getDiscriminatorColumn().getColumnDefinition());

		typeResource.removeSupportingAnnotation(JPA.DISCRIMINATOR_COLUMN);
	}
	
	public void testSetColumnDefinition() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		getJavaEntity().getDiscriminatorColumn().setColumnDefinition("foo");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumnAnnotation column = (DiscriminatorColumnAnnotation) typeResource.getSupportingAnnotation(JPA.DISCRIMINATOR_COLUMN);
		
		assertEquals("foo", column.getColumnDefinition());
		
		getJavaEntity().getDiscriminatorColumn().setColumnDefinition(null);
		column = (DiscriminatorColumnAnnotation) typeResource.getSupportingAnnotation(JPA.DISCRIMINATOR_COLUMN);
		assertNull(column.getColumnDefinition());
	}

}

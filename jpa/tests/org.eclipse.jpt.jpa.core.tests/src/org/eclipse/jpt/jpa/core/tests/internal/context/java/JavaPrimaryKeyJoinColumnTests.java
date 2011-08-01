/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class JavaPrimaryKeyJoinColumnTests extends ContextModelTestCase
{
	private static final String PRIMARY_KEY_JOIN_COLUMN_NAME = "MY_PRIMARY_KEY_JOIN_COLUMN";
	private static final String COLUMN_DEFINITION = "MY_COLUMN_DEFINITION";


	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}

	private ICompilationUnit createTestEntityWithPrimaryKeyJoinColumn() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, JPA.PRIMARY_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@PrimaryKeyJoinColumn(name=\"" + PRIMARY_KEY_JOIN_COLUMN_NAME + "\")");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}

		
	public JavaPrimaryKeyJoinColumnTests(String name) {
		super(name);
	}
	
	public void testGetSpecifiedName() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PrimaryKeyJoinColumn specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		assertEquals(PRIMARY_KEY_JOIN_COLUMN_NAME, specifiedPkJoinColumn.getSpecifiedName());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		PrimaryKeyJoinColumnAnnotation pkJoinColumnResource = (PrimaryKeyJoinColumnAnnotation) resourceType.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		
		pkJoinColumnResource.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		assertEquals("FOO", specifiedPkJoinColumn.getName());
	}
	
	public void testGetDefaultNameNoSpecifiedPrimaryKeyJoinColumns() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PrimaryKeyJoinColumn pkJoinColumn = getJavaEntity().getDefaultPrimaryKeyJoinColumn();
		assertEquals("id", pkJoinColumn.getDefaultName());
	}

	public void testGetDefaultName() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(getJavaEntity().getDefaultPrimaryKeyJoinColumn());
		
		PrimaryKeyJoinColumn specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		assertEquals("id", specifiedPkJoinColumn.getDefaultName());
		
		//remove @Id annotation
		PersistentAttribute idAttribute = getJavaPersistentType().getAttributeNamed("id");
		idAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);

		assertNull(specifiedPkJoinColumn.getDefaultName());
	}
	
	public void testGetName() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PrimaryKeyJoinColumn specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		assertEquals(PRIMARY_KEY_JOIN_COLUMN_NAME, specifiedPkJoinColumn.getName());
	}

	public void testSetSpecifiedName() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PrimaryKeyJoinColumn specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		
		specifiedPkJoinColumn.setSpecifiedName("foo");
		assertEquals("foo", specifiedPkJoinColumn.getSpecifiedName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		PrimaryKeyJoinColumnAnnotation columnAnnotation = (PrimaryKeyJoinColumnAnnotation) resourceType.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		
		assertEquals("foo", columnAnnotation.getName());
		
		specifiedPkJoinColumn.setSpecifiedName(null);
		assertNull(specifiedPkJoinColumn.getSpecifiedName());
		columnAnnotation = (PrimaryKeyJoinColumnAnnotation) resourceType.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertNull(columnAnnotation.getName());
	}

	public void testGetColumnDefinition() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PrimaryKeyJoinColumn specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		assertNull(specifiedPkJoinColumn.getColumnDefinition());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) resourceType.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		column.setColumnDefinition(COLUMN_DEFINITION);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(COLUMN_DEFINITION, specifiedPkJoinColumn.getColumnDefinition());
		
		column.setColumnDefinition(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(specifiedPkJoinColumn.getColumnDefinition());

		resourceType.removeAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(0, getJavaEntity().getSpecifiedPrimaryKeyJoinColumnsSize());
	}
	
	public void testSetColumnDefinition() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PrimaryKeyJoinColumn specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		specifiedPkJoinColumn.setColumnDefinition("foo");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) resourceType.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		
		assertEquals("foo", column.getColumnDefinition());
		
		specifiedPkJoinColumn.setColumnDefinition(null);
		column = (PrimaryKeyJoinColumnAnnotation) resourceType.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertNull(column.getColumnDefinition());
	}

	public void testGetSpecifiedReferencedColumnName() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PrimaryKeyJoinColumn specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		assertNull(specifiedPkJoinColumn.getSpecifiedReferencedColumnName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		PrimaryKeyJoinColumnAnnotation pkJoinColumnResource = (PrimaryKeyJoinColumnAnnotation) resourceType.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		
		pkJoinColumnResource.setReferencedColumnName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		assertEquals("FOO", specifiedPkJoinColumn.getSpecifiedReferencedColumnName());
	}
	
	public void testGetDefaultReferencedColumnNameNoSpecifiedPrimaryKeyJoinColumns() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PrimaryKeyJoinColumn pkJoinColumn = getJavaEntity().getDefaultPrimaryKeyJoinColumn();
		assertEquals("id", pkJoinColumn.getDefaultReferencedColumnName());
	}
	
	public void testGetDefaultReferencedColumnName() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(getJavaEntity().getDefaultPrimaryKeyJoinColumn());
		
		PrimaryKeyJoinColumn specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		assertEquals("id", specifiedPkJoinColumn.getDefaultReferencedColumnName());
		
		
		//remove @Id annotation
		PersistentAttribute idAttribute = getJavaPersistentType().getAttributeNamed("id");
		idAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);

		assertNull(specifiedPkJoinColumn.getDefaultReferencedColumnName());
	}
	
	public void testGetReferencedColumnName() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		PrimaryKeyJoinColumnAnnotation pkJoinColumnResource = (PrimaryKeyJoinColumnAnnotation) resourceType.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		
		pkJoinColumnResource.setReferencedColumnName("FOO");
		getJpaProject().synchronizeContextModel();
		
		PrimaryKeyJoinColumn specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		assertEquals("FOO", specifiedPkJoinColumn.getReferencedColumnName());
	}

	public void testSetSpecifiedReferencedColumnName() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PrimaryKeyJoinColumn specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		
		specifiedPkJoinColumn.setSpecifiedReferencedColumnName("foo");
		assertEquals("foo", specifiedPkJoinColumn.getSpecifiedReferencedColumnName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		PrimaryKeyJoinColumnAnnotation columnAnnotation = (PrimaryKeyJoinColumnAnnotation) resourceType.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		
		assertEquals("foo", columnAnnotation.getReferencedColumnName());
		
		specifiedPkJoinColumn.setSpecifiedName(null);
		specifiedPkJoinColumn.setSpecifiedReferencedColumnName(null);
		assertNull(specifiedPkJoinColumn.getSpecifiedReferencedColumnName());
		columnAnnotation = (PrimaryKeyJoinColumnAnnotation) resourceType.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertNull(columnAnnotation.getReferencedColumnName());
	}
	
	public void testIsVirtual() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PrimaryKeyJoinColumn specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		assertFalse(specifiedPkJoinColumn.isDefault());
		
		assertNull(getJavaEntity().getDefaultPrimaryKeyJoinColumn());
		getJavaEntity().removeSpecifiedPrimaryKeyJoinColumn(0);
		assertTrue(getJavaEntity().getDefaultPrimaryKeyJoinColumn().isDefault());
	}
	
	public void testIsReferencedColumnResolved() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PrimaryKeyJoinColumn specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		assertFalse(((Boolean) ReflectionTools.executeMethod(specifiedPkJoinColumn, "referencedColumnIsResolved")).booleanValue());
	}
	
	public void testDbColumn() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PrimaryKeyJoinColumn specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		assertFalse(specifiedPkJoinColumn.isResolved());
	}
	
	public void testDbReferencedColumn() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PrimaryKeyJoinColumn specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		assertFalse(((Boolean) ReflectionTools.executeMethod(specifiedPkJoinColumn, "referencedColumnIsResolved")).booleanValue());
	}
	
	public void testDbTable() throws Exception {
		createTestEntityWithPrimaryKeyJoinColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PrimaryKeyJoinColumn specifiedPkJoinColumn = getJavaEntity().getSpecifiedPrimaryKeyJoinColumns().iterator().next();
		assertNull(specifiedPkJoinColumn.getDbTable());
	}

}

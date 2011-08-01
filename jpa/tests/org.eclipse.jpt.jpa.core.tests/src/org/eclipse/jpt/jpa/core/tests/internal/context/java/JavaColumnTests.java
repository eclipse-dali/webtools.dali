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
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyColumn;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class JavaColumnTests extends ContextModelTestCase
{
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String TABLE_NAME = "MY_TABLE";
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

	private ICompilationUnit createTestEntityWithDefaultBasicColumn() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Column(name=\"" + COLUMN_NAME + "\")");
			}
		});
	}

	private ICompilationUnit createTestEntityWithBasicColumnTableSet() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC, JPA.COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic");
				sb.append("@Column(table=\"" + TABLE_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithBasicColumnColumnDefinitionSet() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC, JPA.COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic");
				sb.append("@Column(columnDefinition=\"" + COLUMN_DEFINITION + "\")");
			}
		});
	}
	
	public JavaColumnTests(String name) {
		super(name);
	}
	
	public void testGetSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedName());
	}

	public void testGetSpecifiedName() throws Exception {
		createTestEntityWithDefaultBasicColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertEquals(COLUMN_NAME, basicMapping.getColumn().getSpecifiedName());
	}
	
	public void testGetDefaultNameSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertEquals(basicMapping.getPersistentAttribute().getName(), basicMapping.getColumn().getDefaultName());
		assertEquals("id", basicMapping.getColumn().getDefaultName());
	}

	public void testGetDefaultName() throws Exception {
		createTestEntityWithDefaultBasicColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		assertEquals("id", basicMapping.getColumn().getDefaultName());
		
		basicMapping.getColumn().setSpecifiedName("foo");
		assertEquals("id", basicMapping.getColumn().getDefaultName());
	}
	
	public void testGetNameSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertEquals("id", basicMapping.getColumn().getName());
	}
	
	public void testGetName() throws Exception {
		createTestEntityWithDefaultBasicColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
	
		assertEquals(COLUMN_NAME, basicMapping.getColumn().getName());
	}

	public void testSetSpecifiedName() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		basicMapping.getColumn().setSpecifiedName("foo");
		
		assertEquals("foo", basicMapping.getColumn().getSpecifiedName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation column = (ColumnAnnotation) resourceField.getAnnotation(JPA.COLUMN);
		
		assertEquals("foo", column.getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithDefaultBasicColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		basicMapping.getColumn().setSpecifiedName(null);
		
		assertNull(basicMapping.getColumn().getSpecifiedName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(JPA.COLUMN));
	}
	
	public void testGetNameUpdatesFromResourceChange() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertNull(basicMapping.getColumn().getSpecifiedName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation columnAnnotation = (ColumnAnnotation) resourceField.addAnnotation(JPA.COLUMN);

		columnAnnotation.setName("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals("foo", basicMapping.getColumn().getSpecifiedName());
		assertEquals("foo", basicMapping.getColumn().getName());
		
		columnAnnotation.setName(null);
		getJpaProject().synchronizeContextModel();
		assertNull(basicMapping.getColumn().getSpecifiedName());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void testGetSpecifiedTableNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedTable());
	}

	public void testGetSpecifiedTable() throws Exception {
		createTestEntityWithBasicColumnTableSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertEquals(TABLE_NAME, basicMapping.getColumn().getSpecifiedTable());
	}
	
	public void testGetDefaultTableSpecifiedTableNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertEquals(((Entity) basicMapping.getTypeMapping()).getName(), basicMapping.getColumn().getDefaultTable());
		assertEquals(TYPE_NAME, basicMapping.getColumn().getDefaultTable());
	}

	public void testGetDefaultTable() throws Exception {
		createTestEntityWithDefaultBasicColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		assertEquals(TYPE_NAME, basicMapping.getColumn().getDefaultTable());
		
		basicMapping.getColumn().setSpecifiedTable("foo");
		assertEquals(TYPE_NAME, basicMapping.getColumn().getDefaultTable());
	}
	
	public void testGetTable() throws Exception {
		createTestEntityWithBasicColumnTableSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
	
		assertEquals(TABLE_NAME, basicMapping.getColumn().getTable());
	}

	public void testSetSpecifiedTable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		basicMapping.getColumn().setSpecifiedTable("foo");
		
		assertEquals("foo", basicMapping.getColumn().getSpecifiedTable());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation column = (ColumnAnnotation) resourceField.getAnnotation(JPA.COLUMN);
		
		assertEquals("foo", column.getTable());
	}
	
	public void testSetSpecifiedTableNull() throws Exception {
		createTestEntityWithBasicColumnTableSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		basicMapping.getColumn().setSpecifiedTable(null);
		
		assertNull(basicMapping.getColumn().getSpecifiedTable());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(JPA.COLUMN));
	}
	
	public void testGetTableUpdatesFromResourceChange() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertNull(basicMapping.getColumn().getSpecifiedTable());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation column = (ColumnAnnotation) resourceField.addAnnotation(JPA.COLUMN);

		column.setTable("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals("foo", basicMapping.getColumn().getSpecifiedTable());
		assertEquals("foo", basicMapping.getColumn().getTable());
		
		column.setTable(null);
		getJpaProject().synchronizeContextModel();
		assertNull(basicMapping.getColumn().getSpecifiedTable());
	}
	
	public void testGetColumnDefinition() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertEquals(COLUMN_DEFINITION, basicMapping.getColumn().getColumnDefinition());
	}
	
	public void testSetColumnDefinition() throws Exception {
		createTestEntityWithBasicColumnTableSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		basicMapping.getColumn().setColumnDefinition("foo");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation column = (ColumnAnnotation) resourceField.getAnnotation(JPA.COLUMN);
		
		assertEquals("foo", column.getColumnDefinition());
		
		basicMapping.getColumn().setColumnDefinition(null);
		assertNull(column.getColumnDefinition());
	}
	
	public void testGetColumnDefinitionUpdatesFromResourceChange() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertNull(basicMapping.getColumn().getColumnDefinition());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation column = (ColumnAnnotation) resourceField.addAnnotation(JPA.COLUMN);

		column.setColumnDefinition("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals("foo", basicMapping.getColumn().getColumnDefinition());
		
		column.setColumnDefinition(null);
		getJpaProject().synchronizeContextModel();
		assertNull(basicMapping.getColumn().getColumnDefinition());

	}
	
	public void testGetLength() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertEquals(ReadOnlyColumn.DEFAULT_LENGTH, basicMapping.getColumn().getLength());
		basicMapping.getColumn().setSpecifiedLength(Integer.valueOf(55));
		assertEquals(55, basicMapping.getColumn().getLength());
	}
	
	public void testGetDefaultLength() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertEquals(ReadOnlyColumn.DEFAULT_LENGTH, basicMapping.getColumn().getDefaultLength());
		basicMapping.getColumn().setSpecifiedLength(Integer.valueOf(55));
		
		assertEquals(ReadOnlyColumn.DEFAULT_LENGTH, basicMapping.getColumn().getDefaultLength());
	}	
	
	public void testGetSpecifiedLength() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedLength());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation columnAnnotation = (ColumnAnnotation) resourceField.addAnnotation(JPA.COLUMN);
		columnAnnotation.setLength(Integer.valueOf(66));
		getJpaProject().synchronizeContextModel();
		
		assertEquals(Integer.valueOf(66), basicMapping.getColumn().getSpecifiedLength());
		assertEquals(66, basicMapping.getColumn().getLength());
		
		columnAnnotation.setLength(null);	
		getJpaProject().synchronizeContextModel();

		assertNull(basicMapping.getColumn().getSpecifiedLength());	
	}	
	
	public void testSetSpecifiedLength() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedLength());
		
		basicMapping.getColumn().setSpecifiedLength(Integer.valueOf(100));
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation columnAnnotation = (ColumnAnnotation) resourceField.getAnnotation(JPA.COLUMN);
		
		assertEquals(Integer.valueOf(100), columnAnnotation.getLength());
		
		basicMapping.getColumn().setColumnDefinition(null);
		basicMapping.getColumn().setSpecifiedLength(null);
		
		assertNull(columnAnnotation.getLength());
		assertNull(resourceField.getAnnotation(JPA.COLUMN));
	}

	public void testGetPrecision() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertEquals(ReadOnlyColumn.DEFAULT_PRECISION, basicMapping.getColumn().getPrecision());
		basicMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(55));
		assertEquals(55, basicMapping.getColumn().getPrecision());
	}
	
	public void testGetDefaultPrecision() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertEquals(ReadOnlyColumn.DEFAULT_PRECISION, basicMapping.getColumn().getDefaultPrecision());
		basicMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(55));
		
		assertEquals(ReadOnlyColumn.DEFAULT_PRECISION, basicMapping.getColumn().getDefaultPrecision());
	}	
	
	public void testGetSpecifiedPrecision() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedPrecision());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation columnAnnotation = (ColumnAnnotation) resourceField.addAnnotation(JPA.COLUMN);
		columnAnnotation.setPrecision(Integer.valueOf(66));
		getJpaProject().synchronizeContextModel();
		
		assertEquals(Integer.valueOf(66), basicMapping.getColumn().getSpecifiedPrecision());
		assertEquals(66, basicMapping.getColumn().getPrecision());
		
		columnAnnotation.setPrecision(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(basicMapping.getColumn().getSpecifiedPrecision());	
	}	
	
	public void testSetSpecifiedPrecision() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedPrecision());
		
		basicMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(100));
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation columnAnnotation = (ColumnAnnotation) resourceField.getAnnotation(JPA.COLUMN);
		
		assertEquals(Integer.valueOf(100), columnAnnotation.getPrecision());
		
		basicMapping.getColumn().setColumnDefinition(null);
		basicMapping.getColumn().setSpecifiedPrecision(null);
		
		assertNull(columnAnnotation.getPrecision());
		assertNull(resourceField.getAnnotation(JPA.COLUMN));
	}
	
	public void testGetScale() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertEquals(ReadOnlyColumn.DEFAULT_SCALE, basicMapping.getColumn().getScale());
		basicMapping.getColumn().setSpecifiedScale(Integer.valueOf(55));
		assertEquals(55, basicMapping.getColumn().getScale());
	}
	
	public void testGetDefaultScale() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertEquals(ReadOnlyColumn.DEFAULT_SCALE, basicMapping.getColumn().getDefaultScale());
		basicMapping.getColumn().setSpecifiedScale(Integer.valueOf(55));
		
		assertEquals(ReadOnlyColumn.DEFAULT_SCALE, basicMapping.getColumn().getDefaultScale());
	}	
	
	public void testGetSpecifiedScale() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedScale());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation columnAnnotation = (ColumnAnnotation) resourceField.addAnnotation(JPA.COLUMN);
		columnAnnotation.setScale(Integer.valueOf(66));
		getJpaProject().synchronizeContextModel();
		
		assertEquals(Integer.valueOf(66), basicMapping.getColumn().getSpecifiedScale());
		assertEquals(66, basicMapping.getColumn().getScale());
		
		columnAnnotation.setScale(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(basicMapping.getColumn().getSpecifiedScale());	
	}	
	
	public void testSetSpecifiedScale() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedScale());
		
		basicMapping.getColumn().setSpecifiedScale(Integer.valueOf(100));
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation columnAnnotation = (ColumnAnnotation) resourceField.getAnnotation(JPA.COLUMN);
		
		assertEquals(Integer.valueOf(100), columnAnnotation.getScale());
		
		basicMapping.getColumn().setColumnDefinition(null);
		basicMapping.getColumn().setSpecifiedScale(null);
		
		assertNull(columnAnnotation.getScale());
		assertNull(resourceField.getAnnotation(JPA.COLUMN));
	}
	
	public void testGetUnique() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertEquals(ReadOnlyBaseColumn.DEFAULT_UNIQUE, basicMapping.getColumn().isUnique());
		basicMapping.getColumn().setSpecifiedUnique(Boolean.TRUE);
		assertEquals(true, basicMapping.getColumn().isUnique());
	}
	
	public void testGetDefaultUnique() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertEquals(ReadOnlyBaseColumn.DEFAULT_UNIQUE, basicMapping.getColumn().isDefaultUnique());
		basicMapping.getColumn().setSpecifiedUnique(Boolean.TRUE);
		
		assertEquals(ReadOnlyBaseColumn.DEFAULT_UNIQUE, basicMapping.getColumn().isDefaultUnique());
	}	
	
	public void testGetSpecifiedUnique() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedUnique());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation columnAnnotation = (ColumnAnnotation) resourceField.addAnnotation(JPA.COLUMN);
		columnAnnotation.setUnique(Boolean.TRUE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(Boolean.TRUE, basicMapping.getColumn().getSpecifiedUnique());
		assertEquals(true, basicMapping.getColumn().isUnique());
		
		columnAnnotation.setUnique(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(basicMapping.getColumn().getSpecifiedUnique());	
	}	
	
	public void testSetSpecifiedUnique() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedUnique());
		
		basicMapping.getColumn().setSpecifiedUnique(Boolean.FALSE);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation columnAnnotation = (ColumnAnnotation) resourceField.getAnnotation(JPA.COLUMN);
		
		assertEquals(Boolean.FALSE, columnAnnotation.getUnique());
		
		basicMapping.getColumn().setColumnDefinition(null);
		basicMapping.getColumn().setSpecifiedUnique(null);
		
		assertNull(columnAnnotation.getUnique());
		assertNull(resourceField.getAnnotation(JPA.COLUMN));
	}
		
	public void testGetInsertable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertEquals(ReadOnlyBaseColumn.DEFAULT_INSERTABLE, basicMapping.getColumn().isInsertable());
		basicMapping.getColumn().setSpecifiedInsertable(Boolean.TRUE);
		assertEquals(true, basicMapping.getColumn().isInsertable());
	}
	
	public void testGetDefaultInsertable() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertEquals(ReadOnlyBaseColumn.DEFAULT_INSERTABLE, basicMapping.getColumn().isDefaultInsertable());
		basicMapping.getColumn().setSpecifiedInsertable(Boolean.TRUE);
		
		assertEquals(ReadOnlyBaseColumn.DEFAULT_INSERTABLE, basicMapping.getColumn().isDefaultInsertable());
	}	
	
	public void testGetSpecifiedInsertable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedInsertable());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation columnAnnotation = (ColumnAnnotation) resourceField.addAnnotation(JPA.COLUMN);
		columnAnnotation.setInsertable(Boolean.TRUE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(Boolean.TRUE, basicMapping.getColumn().getSpecifiedInsertable());
		assertEquals(true, basicMapping.getColumn().isInsertable());
		
		columnAnnotation.setInsertable(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(basicMapping.getColumn().getSpecifiedInsertable());	
	}	
	
	public void testSetSpecifiedInsertable() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedInsertable());
		
		basicMapping.getColumn().setSpecifiedInsertable(Boolean.FALSE);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation columnAnnotation = (ColumnAnnotation) resourceField.getAnnotation(JPA.COLUMN);
		
		assertEquals(Boolean.FALSE, columnAnnotation.getInsertable());
		
		basicMapping.getColumn().setColumnDefinition(null);
		basicMapping.getColumn().setSpecifiedInsertable(null);
		
		assertNull(columnAnnotation.getInsertable());
		assertNull(resourceField.getAnnotation(JPA.COLUMN));
	}
	
	public void testGetNullable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertEquals(ReadOnlyBaseColumn.DEFAULT_NULLABLE, basicMapping.getColumn().isNullable());
		basicMapping.getColumn().setSpecifiedNullable(Boolean.TRUE);
		assertEquals(true, basicMapping.getColumn().isNullable());
	}
	
	public void testGetDefaultNullable() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertEquals(ReadOnlyBaseColumn.DEFAULT_NULLABLE, basicMapping.getColumn().isDefaultNullable());
		basicMapping.getColumn().setSpecifiedNullable(Boolean.TRUE);
		
		assertEquals(ReadOnlyBaseColumn.DEFAULT_NULLABLE, basicMapping.getColumn().isDefaultNullable());
	}	
	
	public void testGetSpecifiedNullable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedNullable());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation columnAnnotation = (ColumnAnnotation) resourceField.addAnnotation(JPA.COLUMN);
		columnAnnotation.setNullable(Boolean.TRUE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(Boolean.TRUE, basicMapping.getColumn().getSpecifiedNullable());
		assertEquals(true, basicMapping.getColumn().isNullable());
		
		columnAnnotation.setNullable(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(basicMapping.getColumn().getSpecifiedNullable());	
	}	
	
	public void testSetSpecifiedNullable() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedNullable());
		
		basicMapping.getColumn().setSpecifiedNullable(Boolean.FALSE);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation columnAnnotation = (ColumnAnnotation) resourceField.getAnnotation(JPA.COLUMN);
		
		assertEquals(Boolean.FALSE, columnAnnotation.getNullable());
		
		basicMapping.getColumn().setColumnDefinition(null);
		basicMapping.getColumn().setSpecifiedNullable(null);
		
		assertNull(columnAnnotation.getNullable());
		assertNull(resourceField.getAnnotation(JPA.COLUMN));
	}
	
	public void testGetUpdatable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertEquals(ReadOnlyBaseColumn.DEFAULT_UPDATABLE, basicMapping.getColumn().isUpdatable());
		basicMapping.getColumn().setSpecifiedUpdatable(Boolean.TRUE);
		assertEquals(true, basicMapping.getColumn().isUpdatable());
	}
	
	public void testGetDefaultUpdatable() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();

		assertEquals(ReadOnlyBaseColumn.DEFAULT_UPDATABLE, basicMapping.getColumn().isDefaultUpdatable());
		basicMapping.getColumn().setSpecifiedUpdatable(Boolean.TRUE);
		
		assertEquals(ReadOnlyBaseColumn.DEFAULT_UPDATABLE, basicMapping.getColumn().isDefaultUpdatable());
	}	
	
	public void testGetSpecifiedUpdatable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedUpdatable());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation columnAnnotation = (ColumnAnnotation) resourceField.addAnnotation(JPA.COLUMN);
		columnAnnotation.setUpdatable(Boolean.TRUE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(Boolean.TRUE, basicMapping.getColumn().getSpecifiedUpdatable());
		assertEquals(true, basicMapping.getColumn().isUpdatable());
		
		columnAnnotation.setUpdatable(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(basicMapping.getColumn().getSpecifiedUpdatable());	
	}	
	
	public void testSetSpecifiedUpdatable() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) getJavaPersistentType().getAttributes().iterator().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedUpdatable());
		
		basicMapping.getColumn().setSpecifiedUpdatable(Boolean.FALSE);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation columnAnnotation = (ColumnAnnotation) resourceField.getAnnotation(JPA.COLUMN);
		
		assertEquals(Boolean.FALSE, columnAnnotation.getUpdatable());
		
		basicMapping.getColumn().setColumnDefinition(null);
		basicMapping.getColumn().setSpecifiedUpdatable(null);
		
		assertNull(columnAnnotation.getUpdatable());
		assertNull(resourceField.getAnnotation(JPA.COLUMN));
	}
}

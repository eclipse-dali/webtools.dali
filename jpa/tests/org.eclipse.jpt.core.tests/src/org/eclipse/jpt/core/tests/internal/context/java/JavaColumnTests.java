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
import org.eclipse.jpt.core.context.AbstractColumn;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaColumnTests extends ContextModelTestCase
{
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String TABLE_NAME = "MY_TABLE";
	private static final String COLUMN_DEFINITION = "MY_COLUMN_DEFINITION";
	
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createBasicAnnotation() throws Exception{
		this.createAnnotationAndMembers("Basic", "String name() default \"\";");		
	}
	
	private void createColumnAnnotation() throws Exception{
		this.createAnnotationAndMembers("Column", 
			"String name() default \"\";" +
			"boolean unique() default false;" +
			"boolean nullable() default true;" +
			"boolean insertable() default true;" +
			"boolean updatable() default true;" +
			"String columnDefinition() default \"\";" +
			"String table() default \"\";" +
			"int length() default 255;" +
			"int precision() default 0;" +
			"int scale() default 0;");		
	}

	private IType createTestEntity() throws Exception {
		createEntityAnnotation();

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

	private IType createTestEntityWithDefaultBasicColumn() throws Exception {
		createEntityAnnotation();
		createColumnAnnotation();
	
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

	private IType createTestEntityWithBasicColumnTableSet() throws Exception {
		createEntityAnnotation();
		createColumnAnnotation();
		createBasicAnnotation();
	
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
	private IType createTestEntityWithBasicColumnColumnDefinitionSet() throws Exception {
		createEntityAnnotation();
		createColumnAnnotation();
		createBasicAnnotation();
	
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

		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedName());
	}

	public void testGetSpecifiedName() throws Exception {
		createTestEntityWithDefaultBasicColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertEquals(COLUMN_NAME, basicMapping.getColumn().getSpecifiedName());
	}
	
	public void testGetDefaultNameSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertEquals(basicMapping.persistentAttribute().getName(), basicMapping.getColumn().getDefaultName());
		assertEquals("id", basicMapping.getColumn().getDefaultName());
	}

	public void testGetDefaultName() throws Exception {
		createTestEntityWithDefaultBasicColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		assertEquals("id", basicMapping.getColumn().getDefaultName());
		
		basicMapping.getColumn().setSpecifiedName("foo");
		assertEquals("id", basicMapping.getColumn().getDefaultName());
	}
	
	public void testGetNameSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertEquals("id", basicMapping.getColumn().getName());
	}
	
	public void testGetName() throws Exception {
		createTestEntityWithDefaultBasicColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
	
		assertEquals(COLUMN_NAME, basicMapping.getColumn().getName());
	}

	public void testSetSpecifiedName() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		basicMapping.getColumn().setSpecifiedName("foo");
		
		assertEquals("foo", basicMapping.getColumn().getSpecifiedName());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.annotation(JPA.COLUMN);
		
		assertEquals("foo", column.getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithDefaultBasicColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		basicMapping.getColumn().setSpecifiedName(null);
		
		assertNull(basicMapping.getColumn().getSpecifiedName());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.annotation(JPA.COLUMN));
	}
	
	public void testGetNameUpdatesFromResourceChange() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertNull(basicMapping.getColumn().getSpecifiedName());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.addAnnotation(JPA.COLUMN);

		column.setName("foo");
		assertEquals("foo", basicMapping.getColumn().getSpecifiedName());
		assertEquals("foo", basicMapping.getColumn().getName());
		
		column.setName(null);
		assertNull(basicMapping.getColumn().getSpecifiedName());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void testGetSpecifiedTableNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedTable());
	}

	public void testGetSpecifiedTable() throws Exception {
		createTestEntityWithBasicColumnTableSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertEquals(TABLE_NAME, basicMapping.getColumn().getSpecifiedTable());
	}
	
	public void testGetDefaultTableSpecifiedTableNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertEquals(((Entity) basicMapping.typeMapping()).getName(), basicMapping.getColumn().getDefaultTable());
		assertEquals(TYPE_NAME, basicMapping.getColumn().getDefaultTable());
	}

	public void testGetDefaultTable() throws Exception {
		createTestEntityWithDefaultBasicColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		assertEquals(TYPE_NAME, basicMapping.getColumn().getDefaultTable());
		
		basicMapping.getColumn().setSpecifiedTable("foo");
		assertEquals(TYPE_NAME, basicMapping.getColumn().getDefaultTable());
	}
	
	public void testGetTable() throws Exception {
		createTestEntityWithBasicColumnTableSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
	
		assertEquals(TABLE_NAME, basicMapping.getColumn().getTable());
	}

	public void testSetSpecifiedTable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		basicMapping.getColumn().setSpecifiedTable("foo");
		
		assertEquals("foo", basicMapping.getColumn().getSpecifiedTable());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.annotation(JPA.COLUMN);
		
		assertEquals("foo", column.getTable());
	}
	
	public void testSetSpecifiedTableNull() throws Exception {
		createTestEntityWithBasicColumnTableSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		basicMapping.getColumn().setSpecifiedTable(null);
		
		assertNull(basicMapping.getColumn().getSpecifiedTable());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.annotation(JPA.COLUMN));
	}
	
	public void testGetTableUpdatesFromResourceChange() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertNull(basicMapping.getColumn().getSpecifiedTable());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.addAnnotation(JPA.COLUMN);

		column.setTable("foo");
		assertEquals("foo", basicMapping.getColumn().getSpecifiedTable());
		assertEquals("foo", basicMapping.getColumn().getTable());
		
		column.setTable(null);
		assertNull(basicMapping.getColumn().getSpecifiedTable());
	}
	
	public void testGetColumnDefinition() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertEquals(COLUMN_DEFINITION, basicMapping.getColumn().getColumnDefinition());
	}
	
	public void testSetColumnDefinition() throws Exception {
		createTestEntityWithBasicColumnTableSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		basicMapping.getColumn().setColumnDefinition("foo");
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.annotation(JPA.COLUMN);
		
		assertEquals("foo", column.getColumnDefinition());
		
		basicMapping.getColumn().setColumnDefinition(null);
		assertNull(column.getColumnDefinition());
	}
	
	public void testGetColumnDefinitionUpdatesFromResourceChange() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertNull(basicMapping.getColumn().getColumnDefinition());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.addAnnotation(JPA.COLUMN);

		column.setColumnDefinition("foo");
		assertEquals("foo", basicMapping.getColumn().getColumnDefinition());
		
		column.setColumnDefinition(null);
		assertNull(basicMapping.getColumn().getColumnDefinition());

	}
	
	public void testGetLength() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertEquals(Column.DEFAULT_LENGTH, basicMapping.getColumn().getLength());
		basicMapping.getColumn().setSpecifiedLength(Integer.valueOf(55));
		assertEquals(Integer.valueOf(55), basicMapping.getColumn().getLength());
	}
	
	public void testGetDefaultLength() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertEquals(Column.DEFAULT_LENGTH, basicMapping.getColumn().getDefaultLength());
		basicMapping.getColumn().setSpecifiedLength(Integer.valueOf(55));
		
		assertEquals(Column.DEFAULT_LENGTH, basicMapping.getColumn().getDefaultLength());
	}	
	
	public void testGetSpecifiedLength() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedLength());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.addAnnotation(JPA.COLUMN);
		column.setLength(Integer.valueOf(66));
		
		assertEquals(Integer.valueOf(66), basicMapping.getColumn().getSpecifiedLength());
		assertEquals(Integer.valueOf(66), basicMapping.getColumn().getLength());
		
		column.setLength(null);
		
		assertNull(attributeResource.annotation(JPA.COLUMN));
		assertNull(basicMapping.getColumn().getSpecifiedLength());	
	}	
	
	public void testSetSpecifiedLength() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedLength());
		
		basicMapping.getColumn().setSpecifiedLength(Integer.valueOf(100));
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.annotation(JPA.COLUMN);
		
		assertEquals(Integer.valueOf(100), column.getLength());
		
		basicMapping.getColumn().setSpecifiedLength(null);
		
		assertNull(column.getLength());
	}

	public void testGetPrecision() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertEquals(Column.DEFAULT_PRECISION, basicMapping.getColumn().getPrecision());
		basicMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(55));
		assertEquals(Integer.valueOf(55), basicMapping.getColumn().getPrecision());
	}
	
	public void testGetDefaultPrecision() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertEquals(Column.DEFAULT_PRECISION, basicMapping.getColumn().getDefaultPrecision());
		basicMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(55));
		
		assertEquals(Column.DEFAULT_PRECISION, basicMapping.getColumn().getDefaultPrecision());
	}	
	
	public void testGetSpecifiedPrecision() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedPrecision());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.addAnnotation(JPA.COLUMN);
		column.setPrecision(Integer.valueOf(66));
		
		assertEquals(Integer.valueOf(66), basicMapping.getColumn().getSpecifiedPrecision());
		assertEquals(Integer.valueOf(66), basicMapping.getColumn().getPrecision());
		
		column.setPrecision(null);
		
		assertNull(attributeResource.annotation(JPA.COLUMN));
		assertNull(basicMapping.getColumn().getSpecifiedPrecision());	
	}	
	
	public void testSetSpecifiedPrecision() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedPrecision());
		
		basicMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(100));
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.annotation(JPA.COLUMN);
		
		assertEquals(Integer.valueOf(100), column.getPrecision());
		
		basicMapping.getColumn().setSpecifiedPrecision(null);
		
		assertNull(column.getPrecision());
	}
	
	public void testGetScale() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertEquals(Column.DEFAULT_SCALE, basicMapping.getColumn().getScale());
		basicMapping.getColumn().setSpecifiedScale(Integer.valueOf(55));
		assertEquals(Integer.valueOf(55), basicMapping.getColumn().getScale());
	}
	
	public void testGetDefaultScale() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertEquals(Column.DEFAULT_SCALE, basicMapping.getColumn().getDefaultScale());
		basicMapping.getColumn().setSpecifiedScale(Integer.valueOf(55));
		
		assertEquals(Column.DEFAULT_SCALE, basicMapping.getColumn().getDefaultScale());
	}	
	
	public void testGetSpecifiedScale() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedScale());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.addAnnotation(JPA.COLUMN);
		column.setScale(Integer.valueOf(66));
		
		assertEquals(Integer.valueOf(66), basicMapping.getColumn().getSpecifiedScale());
		assertEquals(Integer.valueOf(66), basicMapping.getColumn().getScale());
		
		column.setScale(null);
		
		assertNull(attributeResource.annotation(JPA.COLUMN));
		assertNull(basicMapping.getColumn().getSpecifiedScale());	
	}	
	
	public void testSetSpecifiedScale() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedScale());
		
		basicMapping.getColumn().setSpecifiedScale(Integer.valueOf(100));
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.annotation(JPA.COLUMN);
		
		assertEquals(Integer.valueOf(100), column.getScale());
		
		basicMapping.getColumn().setSpecifiedScale(null);
		
		assertNull(column.getScale());
	}
	
	public void testGetUnique() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertEquals(AbstractColumn.DEFAULT_UNIQUE, basicMapping.getColumn().getUnique());
		basicMapping.getColumn().setSpecifiedUnique(Boolean.TRUE);
		assertEquals(Boolean.TRUE, basicMapping.getColumn().getUnique());
	}
	
	public void testGetDefaultUnique() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertEquals(AbstractColumn.DEFAULT_UNIQUE, basicMapping.getColumn().getDefaultUnique());
		basicMapping.getColumn().setSpecifiedUnique(Boolean.TRUE);
		
		assertEquals(AbstractColumn.DEFAULT_UNIQUE, basicMapping.getColumn().getDefaultUnique());
	}	
	
	public void testGetSpecifiedUnique() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedUnique());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.addAnnotation(JPA.COLUMN);
		column.setUnique(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, basicMapping.getColumn().getSpecifiedUnique());
		assertEquals(Boolean.TRUE, basicMapping.getColumn().getUnique());
		
		column.setUnique(null);
		
		assertNull(attributeResource.annotation(JPA.COLUMN));
		assertNull(basicMapping.getColumn().getSpecifiedUnique());	
	}	
	
	public void testSetSpecifiedUnique() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedUnique());
		
		basicMapping.getColumn().setSpecifiedUnique(Boolean.FALSE);
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.annotation(JPA.COLUMN);
		
		assertEquals(Boolean.FALSE, column.getUnique());
		
		basicMapping.getColumn().setSpecifiedUnique(null);
		
		assertNull(column.getUnique());
	}
		
	public void testGetInsertable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertEquals(AbstractColumn.DEFAULT_INSERTABLE, basicMapping.getColumn().getInsertable());
		basicMapping.getColumn().setSpecifiedInsertable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, basicMapping.getColumn().getInsertable());
	}
	
	public void testGetDefaultInsertable() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertEquals(AbstractColumn.DEFAULT_INSERTABLE, basicMapping.getColumn().getDefaultInsertable());
		basicMapping.getColumn().setSpecifiedInsertable(Boolean.TRUE);
		
		assertEquals(AbstractColumn.DEFAULT_INSERTABLE, basicMapping.getColumn().getDefaultInsertable());
	}	
	
	public void testGetSpecifiedInsertable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedInsertable());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.addAnnotation(JPA.COLUMN);
		column.setInsertable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, basicMapping.getColumn().getSpecifiedInsertable());
		assertEquals(Boolean.TRUE, basicMapping.getColumn().getInsertable());
		
		column.setInsertable(null);
		
		assertNull(attributeResource.annotation(JPA.COLUMN));
		assertNull(basicMapping.getColumn().getSpecifiedInsertable());	
	}	
	
	public void testSetSpecifiedInsertable() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedInsertable());
		
		basicMapping.getColumn().setSpecifiedInsertable(Boolean.FALSE);
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.annotation(JPA.COLUMN);
		
		assertEquals(Boolean.FALSE, column.getInsertable());
		
		basicMapping.getColumn().setSpecifiedInsertable(null);
		
		assertNull(column.getInsertable());
	}
	
	public void testGetNullable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertEquals(AbstractColumn.DEFAULT_NULLABLE, basicMapping.getColumn().getNullable());
		basicMapping.getColumn().setSpecifiedNullable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, basicMapping.getColumn().getNullable());
	}
	
	public void testGetDefaultNullable() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertEquals(AbstractColumn.DEFAULT_NULLABLE, basicMapping.getColumn().getDefaultNullable());
		basicMapping.getColumn().setSpecifiedNullable(Boolean.TRUE);
		
		assertEquals(AbstractColumn.DEFAULT_NULLABLE, basicMapping.getColumn().getDefaultNullable());
	}	
	
	public void testGetSpecifiedNullable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedNullable());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.addAnnotation(JPA.COLUMN);
		column.setNullable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, basicMapping.getColumn().getSpecifiedNullable());
		assertEquals(Boolean.TRUE, basicMapping.getColumn().getNullable());
		
		column.setNullable(null);
		
		assertNull(attributeResource.annotation(JPA.COLUMN));
		assertNull(basicMapping.getColumn().getSpecifiedNullable());	
	}	
	
	public void testSetSpecifiedNullable() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedNullable());
		
		basicMapping.getColumn().setSpecifiedNullable(Boolean.FALSE);
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.annotation(JPA.COLUMN);
		
		assertEquals(Boolean.FALSE, column.getNullable());
		
		basicMapping.getColumn().setSpecifiedNullable(null);
		
		assertNull(column.getNullable());
	}
	
	public void testGetUpdatable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertEquals(AbstractColumn.DEFAULT_UPDATABLE, basicMapping.getColumn().getUpdatable());
		basicMapping.getColumn().setSpecifiedUpdatable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, basicMapping.getColumn().getUpdatable());
	}
	
	public void testGetDefaultUpdatable() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();

		assertEquals(AbstractColumn.DEFAULT_UPDATABLE, basicMapping.getColumn().getDefaultUpdatable());
		basicMapping.getColumn().setSpecifiedUpdatable(Boolean.TRUE);
		
		assertEquals(AbstractColumn.DEFAULT_UPDATABLE, basicMapping.getColumn().getDefaultUpdatable());
	}	
	
	public void testGetSpecifiedUpdatable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedUpdatable());
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.addAnnotation(JPA.COLUMN);
		column.setUpdatable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, basicMapping.getColumn().getSpecifiedUpdatable());
		assertEquals(Boolean.TRUE, basicMapping.getColumn().getUpdatable());
		
		column.setUpdatable(null);
		
		assertNull(attributeResource.annotation(JPA.COLUMN));
		assertNull(basicMapping.getColumn().getSpecifiedUpdatable());	
	}	
	
	public void testSetSpecifiedUpdatable() throws Exception {
		createTestEntityWithBasicColumnColumnDefinitionSet();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		BasicMapping basicMapping = (BasicMapping) javaPersistentType().attributes().next().getMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedUpdatable());
		
		basicMapping.getColumn().setSpecifiedUpdatable(Boolean.FALSE);
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.annotation(JPA.COLUMN);
		
		assertEquals(Boolean.FALSE, column.getUpdatable());
		
		basicMapping.getColumn().setSpecifiedUpdatable(null);
		
		assertNull(column.getUpdatable());
	}
}

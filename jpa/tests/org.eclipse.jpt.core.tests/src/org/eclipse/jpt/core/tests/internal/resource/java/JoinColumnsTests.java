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
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JoinColumn;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JoinColumnsTests extends JavaResourceModelTestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String COLUMN_TABLE = "MY_TABLE";
	private static final String COLUMN_COLUMN_DEFINITION = "COLUMN_DEFINITION";
	private static final String COLUMN_REFERENCED_COLUMN_NAME = "MY_REF_COLUMN_NAME";
	
	public JoinColumnsTests(String name) {
		super(name);
	}
	
	private void createJoinColumnAnnotation() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", 
			"String name() default \"\"; " +
			"String referencedColumnName() default \"\"; " +
			"boolean unique() default false; " +
			"boolean nullable() default true; " +
			"boolean insertable() default true; " +
			"boolean updatable() default true; " +
			"String columnDefinition() default \"\"; " +
			"String table() default \"\"; ");
	}
	
	private void createJoinColumnsAnnotation() throws Exception {
		createJoinColumnAnnotation();
		this.createAnnotationAndMembers("JoinColumns", 
			"JoinColumn[] value();");
	}

	private IType createTestJoinColumns() throws Exception {
		createJoinColumnsAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@JoinColumns(@JoinColumn)");
			}
		});
	}
	
	private IType createTestJoinColumnWithName() throws Exception {
		createJoinColumnsAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@JoinColumns(@JoinColumn(name=\"" + COLUMN_NAME + "\"))");
			}
		});
	}
	
	private IType createTestJoinColumnWithTable() throws Exception {
		createJoinColumnsAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@JoinColumns(@JoinColumn(table=\"" + COLUMN_TABLE + "\"))");
			}
		});
	}
	
	private IType createTestJoinColumnWithReferencedColumnName() throws Exception {
		createJoinColumnsAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@JoinColumns(@JoinColumn(referencedColumnName=\"" + COLUMN_REFERENCED_COLUMN_NAME + "\"))");
			}
		});
	}
	
	private IType createTestJoinColumnWithColumnDefinition() throws Exception {
		createJoinColumnsAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@JoinColumns(@JoinColumn(columnDefinition=\"" + COLUMN_COLUMN_DEFINITION + "\"))");
			}
		});
	}
	
	private IType createTestJoinColumnWithBooleanElement(final String booleanElement) throws Exception {
		createJoinColumnsAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@JoinColumns(@JoinColumn(" + booleanElement + "=true))");
			}
		});
	}

	private IType createTestJoinColumn() throws Exception {
		createJoinColumnsAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@JoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\")");
			}
		});
	}
	
	public void testGetName() throws Exception {
		IType testType = this.createTestJoinColumnWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();
		assertNotNull(column);
		assertEquals(COLUMN_NAME, column.getName());
	}

	public void testGetNull() throws Exception {
		IType testType = this.createTestJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();
		assertNotNull(column);
		assertNull(column.getName());
		assertNull(column.isNullable());
		assertNull(column.isInsertable());
		assertNull(column.isUnique());
		assertNull(column.isUpdatable());
		assertNull(column.getTable());
		assertNull(column.getColumnDefinition());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();

		assertNotNull(column);
		assertNull(column.getName());

		column.setName("Foo");
		assertEquals("Foo", column.getName());
		
		assertSourceContains("@JoinColumns(@JoinColumn(name=\"Foo\"))");
	}
	
	public void testSetNameNull() throws Exception {
		IType testType = this.createTestJoinColumnWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();

		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName(null);
		assertNull(column.getName());
		
		assertSourceDoesNotContain("@JoinColumn");
	}
	
	public void testGetTable() throws Exception {
		IType testType = this.createTestJoinColumnWithTable();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();
		assertEquals(COLUMN_TABLE, column.getTable());
	}

	public void testSetTable() throws Exception {
		IType testType = this.createTestJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();

		assertNotNull(column);
		assertNull(column.getTable());

		column.setTable("Foo");
		assertEquals("Foo", column.getTable());
		
		assertSourceContains("@JoinColumns(@JoinColumn(table=\"Foo\"))");

		
		column.setTable(null);
		assertSourceDoesNotContain("@JoinColumn");
	}
	
	public void testGetReferencedColumnName() throws Exception {
		IType testType = this.createTestJoinColumnWithReferencedColumnName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();
		assertEquals(COLUMN_REFERENCED_COLUMN_NAME, column.getReferencedColumnName());
	}

	public void testSetReferencedColumnName() throws Exception {
		IType testType = this.createTestJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();

		assertNotNull(column);
		assertNull(column.getReferencedColumnName());

		column.setReferencedColumnName("Foo");
		assertEquals("Foo", column.getReferencedColumnName());
		
		assertSourceContains("@JoinColumns(@JoinColumn(referencedColumnName=\"Foo\"))");

		
		column.setReferencedColumnName(null);
		assertSourceDoesNotContain("@JoinColumn");
	}

	public void testGetColumnDefinition() throws Exception {
		IType testType = this.createTestJoinColumnWithColumnDefinition();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();
		assertEquals(COLUMN_COLUMN_DEFINITION, column.getColumnDefinition());
	}

	public void testSetColumnDefinition() throws Exception {
		IType testType = this.createTestJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();

		assertNotNull(column);
		assertNull(column.getColumnDefinition());

		column.setColumnDefinition("Foo");
		assertEquals("Foo", column.getColumnDefinition());
		
		assertSourceContains("@JoinColumns(@JoinColumn(columnDefinition=\"Foo\"))");

		
		column.setColumnDefinition(null);
		assertSourceDoesNotContain("@JoinColumn");
	}

	public void testGetUnique() throws Exception {
		IType testType = this.createTestJoinColumnWithBooleanElement("unique");
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();

		assertTrue(column.isUnique());
	}
	
	public void testSetUnique() throws Exception {
		IType testType = this.createTestJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();

		assertNotNull(column);
		assertNull(column.isUnique());

		column.setUnique(false);
		assertFalse(column.isUnique());
		
		assertSourceContains("@JoinColumns(@JoinColumn(unique=false))");
		
		column.setUnique(null);
		assertSourceDoesNotContain("@JoinColumn");
	}
	
	public void testGetNullable() throws Exception {
		IType testType = this.createTestJoinColumnWithBooleanElement("nullable");
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();

		assertTrue(column.isNullable());
	}
	
	public void testSetNullable() throws Exception {
		IType testType = this.createTestJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();

		assertNotNull(column);
		assertNull(column.isNullable());

		column.setNullable(false);
		assertFalse(column.isNullable());
		
		assertSourceContains("@JoinColumns(@JoinColumn(nullable=false))");
		
		column.setNullable(null);
		assertSourceDoesNotContain("@JoinColumn");
	}

	public void testGetInsertable() throws Exception {
		IType testType = this.createTestJoinColumnWithBooleanElement("insertable");
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();

		assertTrue(column.isInsertable());
	}
	
	public void testSetInsertable() throws Exception {
		IType testType = this.createTestJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();

		assertNotNull(column);
		assertNull(column.isInsertable());

		column.setInsertable(false);
		assertFalse(column.isInsertable());
		
		assertSourceContains("@JoinColumns(@JoinColumn(insertable=false))");
		
		column.setInsertable(null);
		assertSourceDoesNotContain("@JoinColumn");
	}
	
	public void testGetUpdatable() throws Exception {
		IType testType = this.createTestJoinColumnWithBooleanElement("updatable");
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();

		assertTrue(column.isUpdatable());
	}
	
	public void testSetUpdatable() throws Exception {
		IType testType = this.createTestJoinColumns();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		JoinColumn column = (JoinColumn) attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS).next();

		assertNotNull(column);
		assertNull(column.isUpdatable());

		column.setUpdatable(false);
		assertFalse(column.isUpdatable());
		
		assertSourceContains("@JoinColumns(@JoinColumn(updatable=false))");
		
		column.setUpdatable(null);
		assertSourceDoesNotContain("@JoinColumn");
		assertSourceDoesNotContain("@JoinColumns");
	}
	
	
	public void testAddJoinColumnCopyExisting() throws Exception {
		IType jdtType = createTestJoinColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinColumn joinColumn = (JoinColumn) attributeResource.addAnnotation(1, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		joinColumn.setName("FOO");
		assertSourceContains("@JoinColumns({@JoinColumn(name=\"BAR\", columnDefinition = \"COLUMN_DEF\", table = \"TABLE\", unique = false, nullable = false, insertable = false, updatable = false, referencedColumnName = \"REF_NAME\"),@JoinColumn(name=\"FOO\")})");
		
		assertNull(attributeResource.annotation(JPA.JOIN_COLUMN));
		assertNotNull(attributeResource.annotation(JPA.JOIN_COLUMNS));
		assertEquals(2, CollectionTools.size(attributeResource.annotations(JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS)));
	}

	public void testRemoveJoinColumnCopyExisting() throws Exception {
		IType jdtType = createTestJoinColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(jdtType);
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		JoinColumn joinColumn = (JoinColumn) attributeResource.addAnnotation(1, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		joinColumn.setName("FOO");
		assertSourceContains("@JoinColumns({@JoinColumn(name=\"BAR\", columnDefinition = \"COLUMN_DEF\", table = \"TABLE\", unique = false, nullable = false, insertable = false, updatable = false, referencedColumnName = \"REF_NAME\"),@JoinColumn(name=\"FOO\")})");
		
		attributeResource.removeAnnotation(1, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		assertSourceContains("@JoinColumn(name=\"BAR\", columnDefinition = \"COLUMN_DEF\", table = \"TABLE\", unique = false, nullable = false, insertable = false, updatable = false, referencedColumnName = \"REF_NAME\")");
	}

}

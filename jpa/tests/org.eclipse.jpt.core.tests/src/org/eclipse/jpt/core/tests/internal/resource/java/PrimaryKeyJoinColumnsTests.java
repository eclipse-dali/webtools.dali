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
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class PrimaryKeyJoinColumnsTests extends JavaResourceModelTestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String COLUMN_COLUMN_DEFINITION = "COLUMN_DEFINITION";
	private static final String COLUMN_REFERENCED_COLUMN_NAME = "MY_REF_COLUMN_NAME";
	
	public PrimaryKeyJoinColumnsTests(String name) {
		super(name);
	}
	
	private void createPrimaryKeyJoinColumnAnnotation() throws Exception {
		this.createAnnotationAndMembers("PrimaryKeyJoinColumn", 
			"String name() default \"\"; " +
			"String referencedColumnName() default \"\"; " +
			"String columnDefinition() default \"\";");
	}
	
	private void createPrimaryKeyJoinColumnsAnnotation() throws Exception {
		createPrimaryKeyJoinColumnAnnotation();
		this.createAnnotationAndMembers("PrimaryKeyJoinColumns", 
			"PrimaryKeyJoinColumn[] value();");
	}

	private IType createTestPrimaryKeyJoinColumns() throws Exception {
		createPrimaryKeyJoinColumnsAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@PrimaryKeyJoinColumns(@PrimaryKeyJoinColumn)");
			}
		});
	}
	
	private IType createTestPrimaryKeyJoinColumnWithName() throws Exception {
		createPrimaryKeyJoinColumnsAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@PrimaryKeyJoinColumns(@PrimaryKeyJoinColumn(name=\"" + COLUMN_NAME + "\"))");
			}
		});
	}
	
	private IType createTestPrimaryKeyJoinColumnWithReferencedColumnName() throws Exception {
		createPrimaryKeyJoinColumnsAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@PrimaryKeyJoinColumns(@PrimaryKeyJoinColumn(referencedColumnName=\"" + COLUMN_REFERENCED_COLUMN_NAME + "\"))");
			}
		});
	}
	
	private IType createTestPrimaryKeyJoinColumnWithColumnDefinition() throws Exception {
		createPrimaryKeyJoinColumnsAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@PrimaryKeyJoinColumns(@PrimaryKeyJoinColumn(columnDefinition=\"" + COLUMN_COLUMN_DEFINITION + "\"))");
			}
		});
	}

	private IType createTestPrimaryKeyJoinColumn() throws Exception {
		createPrimaryKeyJoinColumnsAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@PrimaryKeyJoinColumn(name=\"BAR\", columnDefinition = \"COLUMN_DEF\", referencedColumnName = \"REF_NAME\")");
			}
		});
	}

	public void testGetName() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumnWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS).next();
		assertNotNull(column);
		assertEquals(COLUMN_NAME, column.getName());
	}

	public void testGetNull() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS).next();
		assertNotNull(column);
		assertNull(column.getName());
		assertNull(column.getReferencedColumnName());
		assertNull(column.getColumnDefinition());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS).next();

		assertNotNull(column);
		assertNull(column.getName());

		column.setName("Foo");
		assertEquals("Foo", column.getName());
		
		assertSourceContains("@PrimaryKeyJoinColumns(@PrimaryKeyJoinColumn(name=\"Foo\"))");
	}
	
	public void testSetNameNull() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumnWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS).next();

		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName(null);
		assertNull(column.getName());
		
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn");
	}

	public void testGetReferencedColumnName() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumnWithReferencedColumnName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS).next();
		assertEquals(COLUMN_REFERENCED_COLUMN_NAME, column.getReferencedColumnName());
	}

	public void testSetReferencedColumnName() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS).next();

		assertNotNull(column);
		assertNull(column.getReferencedColumnName());

		column.setReferencedColumnName("Foo");
		assertEquals("Foo", column.getReferencedColumnName());
		
		assertSourceContains("@PrimaryKeyJoinColumns(@PrimaryKeyJoinColumn(referencedColumnName=\"Foo\"))");

		
		column.setReferencedColumnName(null);
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn");
	}

	public void testGetColumnDefinition() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumnWithColumnDefinition();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS).next();
		assertEquals(COLUMN_COLUMN_DEFINITION, column.getColumnDefinition());
	}

	public void testSetColumnDefinition() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS).next();

		assertNotNull(column);
		assertNull(column.getColumnDefinition());

		column.setColumnDefinition("Foo");
		assertEquals("Foo", column.getColumnDefinition());
		
		assertSourceContains("@PrimaryKeyJoinColumns(@PrimaryKeyJoinColumn(columnDefinition=\"Foo\"))");

		
		column.setColumnDefinition(null);
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn");
	}
	
	
	public void testAddPrimaryKeyJoinColumnCopyExisting() throws Exception {
		IType jdtType = createTestPrimaryKeyJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		PrimaryKeyJoinColumnAnnotation joinColumn = (PrimaryKeyJoinColumnAnnotation) attributeResource.addAnnotation(1, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		joinColumn.setName("FOO");
		assertSourceContains("@PrimaryKeyJoinColumns({@PrimaryKeyJoinColumn(name=\"BAR\", columnDefinition = \"COLUMN_DEF\", referencedColumnName = \"REF_NAME\"),@PrimaryKeyJoinColumn(name=\"FOO\")})");
		
		assertNull(attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNotNull(attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMNS));
		assertEquals(2, CollectionTools.size(attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS)));
	}
	public void testAddPrimaryKeyJoinColumnToBeginningOfList() throws Exception {
		IType jdtType = createTestPrimaryKeyJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		PrimaryKeyJoinColumnAnnotation joinColumn = (PrimaryKeyJoinColumnAnnotation) attributeResource.addAnnotation(1, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		joinColumn.setName("FOO");
		assertSourceContains("@PrimaryKeyJoinColumns({@PrimaryKeyJoinColumn(name=\"BAR\", columnDefinition = \"COLUMN_DEF\", referencedColumnName = \"REF_NAME\"),@PrimaryKeyJoinColumn(name=\"FOO\")})");
		
		joinColumn = (PrimaryKeyJoinColumnAnnotation) attributeResource.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		joinColumn.setName("BAZ");
		assertSourceContains("@PrimaryKeyJoinColumns({@PrimaryKeyJoinColumn(name=\"BAZ\"),@PrimaryKeyJoinColumn(name=\"BAR\", columnDefinition = \"COLUMN_DEF\", referencedColumnName = \"REF_NAME\"), @PrimaryKeyJoinColumn(name=\"FOO\")})");

		Iterator<JavaResourceNode> pkJoinColumns = attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());

		assertNull(attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNotNull(attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMNS));
		assertEquals(3, CollectionTools.size(attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS)));
	}

	public void testRemovePrimaryKeyJoinColumnCopyExisting() throws Exception {
		IType jdtType = createTestPrimaryKeyJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		PrimaryKeyJoinColumnAnnotation joinColumn = (PrimaryKeyJoinColumnAnnotation) attributeResource.addAnnotation(1, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		joinColumn.setName("FOO");
		assertSourceContains("@PrimaryKeyJoinColumns({@PrimaryKeyJoinColumn(name=\"BAR\", columnDefinition = \"COLUMN_DEF\", referencedColumnName = \"REF_NAME\"),@PrimaryKeyJoinColumn(name=\"FOO\")})");
		
		attributeResource.removeAnnotation(1, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		assertSourceContains("@PrimaryKeyJoinColumn(name=\"BAR\", columnDefinition = \"COLUMN_DEF\", referencedColumnName = \"REF_NAME\")");
	}

}

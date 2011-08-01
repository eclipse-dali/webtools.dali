/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;

@SuppressWarnings("nls")
public class PrimaryKeyJoinColumnsTests extends JpaJavaResourceModelTestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String COLUMN_COLUMN_DEFINITION = "COLUMN_DEFINITION";
	private static final String COLUMN_REFERENCED_COLUMN_NAME = "MY_REF_COLUMN_NAME";
	
	public PrimaryKeyJoinColumnsTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestPrimaryKeyJoinColumns() throws Exception {
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
	
	private ICompilationUnit createTestPrimaryKeyJoinColumnWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@PrimaryKeyJoinColumns(@PrimaryKeyJoinColumn(name = \"" + COLUMN_NAME + "\"))");
			}
		});
	}
	
	private ICompilationUnit createTestPrimaryKeyJoinColumnWithReferencedColumnName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@PrimaryKeyJoinColumns(@PrimaryKeyJoinColumn(referencedColumnName = \"" + COLUMN_REFERENCED_COLUMN_NAME + "\"))");
			}
		});
	}
	
	private ICompilationUnit createTestPrimaryKeyJoinColumnWithColumnDefinition() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@PrimaryKeyJoinColumns(@PrimaryKeyJoinColumn(columnDefinition = \"" + COLUMN_COLUMN_DEFINITION + "\"))");
			}
		});
	}

	private ICompilationUnit createTestPrimaryKeyJoinColumn() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@PrimaryKeyJoinColumn(name = \"BAR\", columnDefinition = \"COLUMN_DEF\", referencedColumnName = \"REF_NAME\")");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumnWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertNotNull(column);
		assertEquals(COLUMN_NAME, column.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertNotNull(column);
		assertNull(column.getName());
		assertNull(column.getReferencedColumnName());
		assertNull(column.getColumnDefinition());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getName());

		column.setName("Foo");
		assertEquals("Foo", column.getName());
		
		assertSourceContains("@PrimaryKeyJoinColumns(@PrimaryKeyJoinColumn(name = \"Foo\"))", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumnWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);

		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName(null);
		assertNull(column.getName());
		
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn(", cu);
	}

	public void testGetReferencedColumnName() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumnWithReferencedColumnName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertEquals(COLUMN_REFERENCED_COLUMN_NAME, column.getReferencedColumnName());
	}

	public void testSetReferencedColumnName() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getReferencedColumnName());

		column.setReferencedColumnName("Foo");
		assertEquals("Foo", column.getReferencedColumnName());
		
		assertSourceContains("@PrimaryKeyJoinColumns(@PrimaryKeyJoinColumn(referencedColumnName = \"Foo\"))", cu);

		
		column.setReferencedColumnName(null);
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn(", cu);
	}

	public void testGetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumnWithColumnDefinition();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertEquals(COLUMN_COLUMN_DEFINITION, column.getColumnDefinition());
	}

	public void testSetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getColumnDefinition());

		column.setColumnDefinition("Foo");
		assertEquals("Foo", column.getColumnDefinition());
		
		assertSourceContains("@PrimaryKeyJoinColumns(@PrimaryKeyJoinColumn(columnDefinition = \"Foo\"))", cu);

		
		column.setColumnDefinition(null);
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn(", cu);
	}
	
	
	public void testAddPrimaryKeyJoinColumnCopyExisting() throws Exception {
		ICompilationUnit cu = createTestPrimaryKeyJoinColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		String expected1 = "@PrimaryKeyJoinColumns({";
		String expected2 = "@PrimaryKeyJoinColumn(name = \"BAR\", columnDefinition = \"COLUMN_DEF\", referencedColumnName = \"REF_NAME\"),";
		String expected3 = "@PrimaryKeyJoinColumn(name = \"FOO\") })";
		PrimaryKeyJoinColumnAnnotation joinColumn = (PrimaryKeyJoinColumnAnnotation) resourceField.addAnnotation(1, JPA.PRIMARY_KEY_JOIN_COLUMN);
		joinColumn.setName("FOO");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
		
		assertNotNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertEquals(2, resourceField.getAnnotationsSize(JPA.PRIMARY_KEY_JOIN_COLUMN));
	}
	public void testAddPrimaryKeyJoinColumnToBeginningOfList() throws Exception {
		ICompilationUnit cu = createTestPrimaryKeyJoinColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		String expected1 = "@PrimaryKeyJoinColumns({";
		String expected2 = "@PrimaryKeyJoinColumn(name = \"BAR\", columnDefinition = \"COLUMN_DEF\", referencedColumnName = \"REF_NAME\"),";
		String expected3 = "@PrimaryKeyJoinColumn(name = \"FOO\") })";
		PrimaryKeyJoinColumnAnnotation joinColumn = (PrimaryKeyJoinColumnAnnotation) resourceField.addAnnotation(1, JPA.PRIMARY_KEY_JOIN_COLUMN);
		joinColumn.setName("FOO");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
		
		expected2 = "@PrimaryKeyJoinColumn(name = \"BAZ\"),";
		expected3 = "@PrimaryKeyJoinColumn(name = \"BAR\", columnDefinition = \"COLUMN_DEF\", referencedColumnName = \"REF_NAME\"), @PrimaryKeyJoinColumn(name = \"FOO\") })";
		joinColumn = (PrimaryKeyJoinColumnAnnotation) resourceField.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN);
		joinColumn.setName("BAZ");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);

		Iterator<NestableAnnotation> pkJoinColumns = resourceField.getAnnotations(JPA.PRIMARY_KEY_JOIN_COLUMN).iterator();
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());

		assertNotNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertEquals(3, resourceField.getAnnotationsSize(JPA.PRIMARY_KEY_JOIN_COLUMN));
	}

	public void testRemovePrimaryKeyJoinColumnCopyExisting() throws Exception {
		ICompilationUnit cu = createTestPrimaryKeyJoinColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		String expected1 = "@PrimaryKeyJoinColumns({";
		String expected2 = "@PrimaryKeyJoinColumn(name = \"BAR\", columnDefinition = \"COLUMN_DEF\", referencedColumnName = \"REF_NAME\"),";
		String expected3 = "@PrimaryKeyJoinColumn(name = \"FOO\") })";
		PrimaryKeyJoinColumnAnnotation joinColumn = (PrimaryKeyJoinColumnAnnotation) resourceField.addAnnotation(1, JPA.PRIMARY_KEY_JOIN_COLUMN);
		joinColumn.setName("FOO");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
		
		expected1 = "@PrimaryKeyJoinColumn(name = \"BAR\", columnDefinition = \"COLUMN_DEF\", referencedColumnName = \"REF_NAME\")";
		resourceField.removeAnnotation(1, JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertSourceContains(expected1, cu);
		assertSourceDoesNotContain("@PrimaryKeyJoinColumns", cu);
	}

}

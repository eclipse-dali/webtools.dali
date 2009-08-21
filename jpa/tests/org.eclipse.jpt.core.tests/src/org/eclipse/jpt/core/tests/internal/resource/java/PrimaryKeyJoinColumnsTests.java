/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class PrimaryKeyJoinColumnsTests extends JavaResourceModelTestCase {
	
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS).next();
		assertNotNull(column);
		assertEquals(COLUMN_NAME, column.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS).next();
		assertNotNull(column);
		assertNull(column.getName());
		assertNull(column.getReferencedColumnName());
		assertNull(column.getColumnDefinition());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS).next();

		assertNotNull(column);
		assertNull(column.getName());

		column.setName("Foo");
		assertEquals("Foo", column.getName());
		
		assertSourceContains("@PrimaryKeyJoinColumns(@PrimaryKeyJoinColumn(name = \"Foo\"))", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumnWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS).next();

		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName(null);
		assertNull(column.getName());
		
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn", cu);
	}

	public void testGetReferencedColumnName() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumnWithReferencedColumnName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS).next();
		assertEquals(COLUMN_REFERENCED_COLUMN_NAME, column.getReferencedColumnName());
	}

	public void testSetReferencedColumnName() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS).next();

		assertNotNull(column);
		assertNull(column.getReferencedColumnName());

		column.setReferencedColumnName("Foo");
		assertEquals("Foo", column.getReferencedColumnName());
		
		assertSourceContains("@PrimaryKeyJoinColumns(@PrimaryKeyJoinColumn(referencedColumnName = \"Foo\"))", cu);

		
		column.setReferencedColumnName(null);
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn", cu);
	}

	public void testGetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumnWithColumnDefinition();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS).next();
		assertEquals(COLUMN_COLUMN_DEFINITION, column.getColumnDefinition());
	}

	public void testSetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestPrimaryKeyJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumnAnnotation column = (PrimaryKeyJoinColumnAnnotation) attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS).next();

		assertNotNull(column);
		assertNull(column.getColumnDefinition());

		column.setColumnDefinition("Foo");
		assertEquals("Foo", column.getColumnDefinition());
		
		assertSourceContains("@PrimaryKeyJoinColumns(@PrimaryKeyJoinColumn(columnDefinition = \"Foo\"))", cu);

		
		column.setColumnDefinition(null);
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn", cu);
	}
	
	
	public void testAddPrimaryKeyJoinColumnCopyExisting() throws Exception {
		ICompilationUnit cu = createTestPrimaryKeyJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		PrimaryKeyJoinColumnAnnotation joinColumn = (PrimaryKeyJoinColumnAnnotation) attributeResource.addAnnotation(1, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		joinColumn.setName("FOO");
		assertSourceContains("@PrimaryKeyJoinColumns({@PrimaryKeyJoinColumn(name = \"BAR\", columnDefinition = \"COLUMN_DEF\", referencedColumnName = \"REF_NAME\"),@PrimaryKeyJoinColumn(name = \"FOO\")})", cu);
		
		assertNull(attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNotNull(attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMNS));
		assertEquals(2, CollectionTools.size(attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS)));
	}
	public void testAddPrimaryKeyJoinColumnToBeginningOfList() throws Exception {
		ICompilationUnit cu = createTestPrimaryKeyJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		PrimaryKeyJoinColumnAnnotation joinColumn = (PrimaryKeyJoinColumnAnnotation) attributeResource.addAnnotation(1, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		joinColumn.setName("FOO");
		assertSourceContains("@PrimaryKeyJoinColumns({@PrimaryKeyJoinColumn(name = \"BAR\", columnDefinition = \"COLUMN_DEF\", referencedColumnName = \"REF_NAME\"),@PrimaryKeyJoinColumn(name = \"FOO\")})", cu);
		
		joinColumn = (PrimaryKeyJoinColumnAnnotation) attributeResource.addAnnotation(0, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		joinColumn.setName("BAZ");
		assertSourceContains("@PrimaryKeyJoinColumns({@PrimaryKeyJoinColumn(name = \"BAZ\"),@PrimaryKeyJoinColumn(name = \"BAR\", columnDefinition = \"COLUMN_DEF\", referencedColumnName = \"REF_NAME\"), @PrimaryKeyJoinColumn(name = \"FOO\")})", cu);

		Iterator<NestableAnnotation> pkJoinColumns = attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		assertEquals("BAZ", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertEquals("BAR", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());
		assertEquals("FOO", ((PrimaryKeyJoinColumnAnnotation) pkJoinColumns.next()).getName());

		assertNull(attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMN));
		assertNotNull(attributeResource.getAnnotation(JPA.PRIMARY_KEY_JOIN_COLUMNS));
		assertEquals(3, CollectionTools.size(attributeResource.annotations(JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS)));
	}

	public void testRemovePrimaryKeyJoinColumnCopyExisting() throws Exception {
		ICompilationUnit cu = createTestPrimaryKeyJoinColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		PrimaryKeyJoinColumnAnnotation joinColumn = (PrimaryKeyJoinColumnAnnotation) attributeResource.addAnnotation(1, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		joinColumn.setName("FOO");
		assertSourceContains("@PrimaryKeyJoinColumns({@PrimaryKeyJoinColumn(name = \"BAR\", columnDefinition = \"COLUMN_DEF\", referencedColumnName = \"REF_NAME\"),@PrimaryKeyJoinColumn(name = \"FOO\")})", cu);
		
		attributeResource.removeAnnotation(1, JPA.PRIMARY_KEY_JOIN_COLUMN, JPA.PRIMARY_KEY_JOIN_COLUMNS);
		assertSourceContains("@PrimaryKeyJoinColumn(name = \"BAR\", columnDefinition = \"COLUMN_DEF\", referencedColumnName = \"REF_NAME\")", cu);
	}

}

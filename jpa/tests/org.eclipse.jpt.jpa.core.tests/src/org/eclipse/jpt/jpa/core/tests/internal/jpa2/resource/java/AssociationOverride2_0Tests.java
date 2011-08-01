/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.AssociationOverride2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;

@SuppressWarnings("nls")
public class AssociationOverride2_0Tests extends JavaResourceModel2_0TestCase {
	
	private static final String ASSOCIATION_OVERRIDE_NAME = "MY_ASSOCIATION_OVERRIDE";
	private static final String JOIN_TABLE_NAME = "MY_JOIN_TABLE";
	private static final String CATALOG_NAME = "MY_CATALOG";
	private static final String SCHEMA_NAME = "MY_SCHEMA";
	
	public AssociationOverride2_0Tests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestAssociationOverrideOnField() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ASSOCIATION_OVERRIDE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestAssociationOverrideOnType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ASSOCIATION_OVERRIDE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestAssociationOverrideWithJoinColumns() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ASSOCIATION_OVERRIDE, JPA.JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name = \"BAR\"), @JoinColumn})");
			}
		});
	}
	
	private ICompilationUnit createTestAssociationOverrideWithJoinTable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ASSOCIATION_OVERRIDE, JPA.JOIN_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(name = \"" + JOIN_TABLE_NAME + "\"))");
			}
		});
	}
	
	private ICompilationUnit createTestAssociationOverrideJoinTableWithCatalog() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ASSOCIATION_OVERRIDE, JPA.JOIN_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(catalog = \"" + CATALOG_NAME + "\"))");
			}
		});
	}
	
	private ICompilationUnit createTestAssociationOverrideJoinTableWithSchema() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ASSOCIATION_OVERRIDE, JPA.JOIN_TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(schema = \"" + SCHEMA_NAME + "\"))");
			}
		});
	}
	
	private ICompilationUnit createTestAssociationOverrideJoinTableWithUniqueConstraints() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ASSOCIATION_OVERRIDE, JPA.JOIN_TABLE, JPA.UNIQUE_CONSTRAINT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"})}))");
			}
		});
	}
	
	private ICompilationUnit createTestAssociationOverrideJoinTableWithJoinColumns() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ASSOCIATION_OVERRIDE, JPA.JOIN_TABLE, JPA.JOIN_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(joinColumns = {@JoinColumn(name = \"BAR\"), @JoinColumn}))");
			}
		});
	}
	
	private ICompilationUnit createTestAssociationOverrideJoinTableWithInverseJoinColumns() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ASSOCIATION_OVERRIDE, JPA.JOIN_TABLE, JPA.JOIN_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(inverseJoinColumns = {@JoinColumn(name = \"BAR\"), @JoinColumn}))");
			}
		});
	}

	private AssociationOverride2_0Annotation associationOverrideAt(int index, JavaResourceMember resourceMember) {
		return (AssociationOverride2_0Annotation) resourceMember.getAnnotation(index, JPA.ASSOCIATION_OVERRIDE);
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceField);

		assertNotNull(associationOverride);
		assertEquals(ASSOCIATION_OVERRIDE_NAME, associationOverride.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceField);

		assertNotNull(associationOverride);
		assertEquals(ASSOCIATION_OVERRIDE_NAME, associationOverride.getName());

		associationOverride.setName("Foo");
		assertEquals("Foo", associationOverride.getName());
		assertSourceContains("@AssociationOverride(name = \"Foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceField);

		assertEquals(ASSOCIATION_OVERRIDE_NAME, associationOverride.getName());
		
		associationOverride.setName(null);
		assertNull(associationOverride.getName());
		
		assertSourceDoesNotContain("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\")", cu);
		assertSourceContains("@AssociationOverride", cu);
	}
	
	
	public void testJoinColumns() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceField);
		
		assertEquals(0, associationOverride.getJoinColumnsSize());
	}
	
	public void testJoinColumns2() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceField);

		
		associationOverride.addJoinColumn(0);
		associationOverride.addJoinColumn(1);
				
		assertEquals(2, associationOverride.getJoinColumnsSize());
	}
	
	public void testJoinColumns3() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceField);
				
		assertEquals(2, associationOverride.getJoinColumnsSize());
	}
	
	public void testAddJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceField);
		
		associationOverride.addJoinColumn(0).setName("FOO");
		associationOverride.addJoinColumn(1);
		associationOverride.addJoinColumn(0).setName("BAR");

		assertEquals("BAR", associationOverride.joinColumnAt(0).getName());
		assertEquals("FOO", associationOverride.joinColumnAt(1).getName());
		assertNull(associationOverride.joinColumnAt(2).getName());

		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name = \"BAR\"),@JoinColumn(name = \"FOO\"), @JoinColumn})", cu);
	}
	
	public void testRemoveJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceField);
		associationOverride.addJoinColumn(0).setName("FOO");
		
		Iterator<JoinColumnAnnotation> joinColumns = associationOverride.getJoinColumns().iterator();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertNull(joinColumns.next().getName());
		assertEquals(false, joinColumns.hasNext());
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name = \"FOO\"), @JoinColumn(name = \"BAR\"), @JoinColumn})", cu);
		
		associationOverride.removeJoinColumn(1);
		joinColumns = associationOverride.getJoinColumns().iterator();
		assertEquals("FOO", joinColumns.next().getName());
		assertNull(joinColumns.next().getName());
		assertEquals(false, joinColumns.hasNext());
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name = \"FOO\"), @JoinColumn})", cu);

		associationOverride.removeJoinColumn(0);
		joinColumns = associationOverride.getJoinColumns().iterator();
		assertNull(joinColumns.next().getName());
		assertEquals(false, joinColumns.hasNext());
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = @JoinColumn)", cu);

		
		associationOverride.setName(null);
		associationOverride.removeJoinColumn(0);
		assertSourceDoesNotContain("@AssociationOverride(", cu);
	}
	
	public void testMoveJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceField);
		JoinColumnAnnotation joinColumn = associationOverride.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		associationOverride.addJoinColumn(0).setName("FOO");
		
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name = \"FOO\"), @JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn})", cu);

		associationOverride.moveJoinColumn(2, 0);
		assertEquals("BAR", associationOverride.joinColumnAt(0).getName());
		assertNull(associationOverride.joinColumnAt(1).getName());
		assertEquals("FOO", associationOverride.joinColumnAt(2).getName());
		assertEquals(3, associationOverride.getJoinColumnsSize());
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn, @JoinColumn(name = \"FOO\")})", cu);
	}
	
	public void testMoveJoinColumn2() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceField);
		
		JoinColumnAnnotation joinColumn = associationOverride.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		associationOverride.addJoinColumn(0).setName("FOO");
		
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name = \"FOO\"), @JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn})", cu);

		associationOverride.moveJoinColumn(0, 2);
		assertNull(associationOverride.joinColumnAt(0).getName());
		assertEquals("FOO", associationOverride.joinColumnAt(1).getName());
		assertEquals("BAR", associationOverride.joinColumnAt(2).getName());
		assertEquals(3, associationOverride.getJoinColumnsSize());
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn, @JoinColumn(name = \"FOO\"), @JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\")})", cu);
	}
	
	public void testSetJoinColumnName() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceField);
				
		assertEquals(2, associationOverride.getJoinColumnsSize());
		
		JoinColumnAnnotation joinColumn = associationOverride.joinColumnAt(0);
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name = \"foo\"), @JoinColumn})", cu);
	}
		
	public void testGetNullJoinTable() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation joinTable = associationOverride.getJoinTable();
		assertNotNull(associationOverride);
		assertNull(joinTable);
	}
	
	public void testJoinTableGetName() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation joinTable = associationOverride.getJoinTable();
		assertEquals(JOIN_TABLE_NAME, joinTable.getName());
	}
	
	public void testJoinTableSetName() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation joinTable = associationOverride.getJoinTable();
		assertEquals(JOIN_TABLE_NAME, joinTable.getName());
		
		joinTable.setName("Foo");
		
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(name = \"Foo\"))", cu);
		
		joinTable.setName(null);
		assertNull(associationOverride.getJoinTable().getName());
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable)", cu);
	}
	
	public void testAddJoinTable() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation joinTable = associationOverride.getJoinTable();
		assertNull(joinTable);
		
		associationOverride.addJoinTable();
		joinTable = associationOverride.getJoinTable();
		assertNotNull(joinTable);
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable)", cu);
	}
	
	public void testRemoveJoinTable() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation joinTable = associationOverride.getJoinTable();
		assertNull(joinTable);
	}
	
	public void testJoinTableGetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithCatalog();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		assertNotNull(table);
		assertEquals(CATALOG_NAME, table.getCatalog());
	}

	public void testJoinTableSetCatalog() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		assertNotNull(table);
		assertNull(table.getCatalog());

		table.setCatalog("Foo");
		assertEquals("Foo", table.getCatalog());
		
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(name = \"MY_JOIN_TABLE\", catalog = \"Foo\"))", cu);
	}
	
	public void testJoinTableSetCatalogNull() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithCatalog();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		assertEquals(CATALOG_NAME, table.getCatalog());
		
		table.setCatalog(null);
		assertNull(table.getCatalog());
		
		assertSourceDoesNotContain("@JoinTable(", cu);
	}
	
	public void testJoinTableGetSchema() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithSchema();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		assertNotNull(table);
		assertEquals(SCHEMA_NAME, table.getSchema());
	}

	public void testJoinTableSetSchema() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		assertNotNull(table);
		assertNull(table.getSchema());

		table.setSchema("Foo");
		assertEquals("Foo", table.getSchema());
		
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(name = \"MY_JOIN_TABLE\", schema = \"Foo\"))", cu);
	}
	
	public void testJoinTableSetSchemaNull() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithSchema();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		assertEquals(SCHEMA_NAME, table.getSchema());
		
		table.setSchema(null);
		assertNull(table.getSchema());
		
		assertSourceDoesNotContain("@JoinTable(", cu);
	}
	
	public void testJoinTableUniqueConstraints() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		
		assertEquals(0, table.getUniqueConstraintsSize());
	}
	
	public void testJoinTableUniqueConstraints2() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();

		
		table.addUniqueConstraint(0);
		table.addUniqueConstraint(1);
		
		assertEquals(2, table.getUniqueConstraintsSize());
	}
	
	public void testJoinTableUniqueConstraints3() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
				
		assertEquals(3, table.getUniqueConstraintsSize());
	}
	
	public void testJoinTableAddUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		
		table.addUniqueConstraint(0).addColumnName("FOO");
		table.addUniqueConstraint(1);
		table.addUniqueConstraint(0).addColumnName("BAR");

		assertEquals("BAR", table.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("FOO", table.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals(0, table.uniqueConstraintAt(2).getColumnNamesSize());

		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(name = \"MY_JOIN_TABLE\", uniqueConstraints = {@UniqueConstraint(columnNames = \"BAR\"),@UniqueConstraint(columnNames = \"FOO\"), @UniqueConstraint}))", cu);
	}
	
	public void testJoinTableRemoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		assertEquals("BAR", table.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("FOO", table.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals("BAZ", table.uniqueConstraintAt(2).columnNameAt(0));
		assertEquals(3, table.getUniqueConstraintsSize());
		
		table.removeUniqueConstraint(1);
		assertEquals("BAR", table.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals("BAZ", table.uniqueConstraintAt(1).columnNameAt(0));
		assertEquals(2, table.getUniqueConstraintsSize());		
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"BAZ\"})}))", cu);
		
		table.removeUniqueConstraint(0);
		assertEquals("BAZ", table.uniqueConstraintAt(0).columnNameAt(0));
		assertEquals(1, table.getUniqueConstraintsSize());		
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = {\"BAZ\"})))", cu);
		
		table.removeUniqueConstraint(0);
		assertEquals(0, table.getUniqueConstraintsSize());		
		assertSourceDoesNotContain("@JoinTable(", cu);
	}
	
	public void testJoinTableMoveUniqueConstraint() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"})}))", cu);
		
		table.moveUniqueConstraint(2, 0);
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"}), @UniqueConstraint(columnNames = {\"BAR\"})}))", cu);
	}
	
	public void testJoinTableMoveUniqueConstraint2() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithUniqueConstraints();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"}), @UniqueConstraint(columnNames = {\"BAZ\"})}))", cu);
		
		table.moveUniqueConstraint(0, 2);
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(uniqueConstraints = {@UniqueConstraint(columnNames = {\"BAZ\"}), @UniqueConstraint(columnNames = {\"BAR\"}), @UniqueConstraint(columnNames = {\"FOO\"})}))", cu);
	}
	
	public void testJoinTableJoinColumns() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
				
		assertEquals(0, table.getJoinColumnsSize());
	}
	
	public void testJoinTableJoinColumns2() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();

		
		table.addJoinColumn(0);
		table.addJoinColumn(1);
		
		assertEquals(2, table.getJoinColumnsSize());
	}
	
	public void testJoinTableJoinColumns3() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
				
		assertEquals(2, table.getJoinColumnsSize());
	}
	
	public void testJoinTableAddJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		
		table.addJoinColumn(0).setName("FOO");
		table.addJoinColumn(1);
		table.addJoinColumn(0).setName("BAR");

		assertEquals("BAR", table.joinColumnAt(0).getName());
		assertEquals("FOO", table.joinColumnAt(1).getName());
		assertNull(table.joinColumnAt(2).getName());
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(name = \"MY_JOIN_TABLE\", joinColumns = {@JoinColumn(name = \"BAR\"),@JoinColumn(name = \"FOO\"), @JoinColumn}))", cu);
	}
	
	public void testJoinTableRemoveJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		table.addJoinColumn(0).setName("FOO");
		
		assertEquals("FOO", table.joinColumnAt(0).getName());
		assertEquals("BAR", table.joinColumnAt(1).getName());
		assertNull(table.joinColumnAt(2).getName());
		assertEquals(3, table.getJoinColumnsSize());
		
		table.removeJoinColumn(1);
		assertEquals("FOO", table.joinColumnAt(0).getName());
		assertNull(table.joinColumnAt(1).getName());
		assertEquals(2, table.getJoinColumnsSize());
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(joinColumns = {@JoinColumn(name = \"FOO\"), @JoinColumn}))", cu);

		table.removeJoinColumn(0);
		assertNull(table.joinColumnAt(0).getName());
		assertEquals(1, table.getJoinColumnsSize());
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(joinColumns = @JoinColumn))", cu);

		
		table.removeJoinColumn(0);
		assertEquals(0, table.getJoinColumnsSize());
		assertSourceDoesNotContain("@JoinTable(", cu);
	}
	
	public void testJoinTableMoveJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		JoinColumnAnnotation joinColumn = table.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		table.addJoinColumn(0).setName("FOO");
		
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(joinColumns = {@JoinColumn(name = \"FOO\"), @JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn}))", cu);

		table.moveJoinColumn(2, 0);
		assertEquals("BAR", table.joinColumnAt(0).getName());
		assertNull(table.joinColumnAt(1).getName());
		assertEquals("FOO", table.joinColumnAt(2).getName());
		assertEquals(3, table.getJoinColumnsSize());
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(joinColumns = {@JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn, @JoinColumn(name = \"FOO\")}))", cu);
	}
	
	public void testJoinTableMoveJoinColumn2() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		
		JoinColumnAnnotation joinColumn = table.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		
		table.addJoinColumn(0).setName("FOO");
		
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(joinColumns = {@JoinColumn(name = \"FOO\"), @JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn}))", cu);


		table.moveJoinColumn(0, 2);
		assertNull(table.joinColumnAt(0).getName());
		assertEquals("FOO", table.joinColumnAt(1).getName());
		assertEquals("BAR", table.joinColumnAt(2).getName());
		assertEquals(3, table.getJoinColumnsSize());
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(joinColumns = {@JoinColumn, @JoinColumn(name = \"FOO\"), @JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\")}))", cu);
	}
	
	public void testJoinTableSetJoinColumnName() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
				
		assertEquals(2, table.getJoinColumnsSize());
		
		JoinColumnAnnotation joinColumn = table.joinColumnAt(0);
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(joinColumns = {@JoinColumn(name = \"foo\"), @JoinColumn}))", cu);
	}

	public void testJoinTableInverseJoinColumns() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		
		assertEquals(0, table.getInverseJoinColumnsSize());
	}
	
	public void testInverseJoinColumns2() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();

		
		table.addInverseJoinColumn(0);
		table.addInverseJoinColumn(1);
		
		assertEquals(2, table.getInverseJoinColumnsSize());
	}
	
	public void testJoinTableInverseJoinColumns3() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithInverseJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
				
		assertEquals(2, table.getInverseJoinColumnsSize());
	}
	
	public void testAddInverseJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		
		table.addInverseJoinColumn(0).setName("FOO");
		table.addInverseJoinColumn(1);
		table.addInverseJoinColumn(0).setName("BAR");

		assertEquals("BAR", table.inverseJoinColumnAt(0).getName());
		assertEquals("FOO", table.inverseJoinColumnAt(1).getName());
		assertNull(table.inverseJoinColumnAt(2).getName());
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(name = \"MY_JOIN_TABLE\", inverseJoinColumns = {@JoinColumn(name = \"BAR\"),@JoinColumn(name = \"FOO\"), @JoinColumn}))", cu);
	}
	
	public void testJoinTableRemoveInverseJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithInverseJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		table.addInverseJoinColumn(2).setName("FOO");
		
		Iterator<JoinColumnAnnotation> inverseJoinColumns = table.getInverseJoinColumns().iterator();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertNull(inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
		
		table.removeInverseJoinColumn(1);
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(inverseJoinColumns = {@JoinColumn(name = \"BAR\"), @JoinColumn(name = \"FOO\")}))", cu);
		inverseJoinColumns = table.getInverseJoinColumns().iterator();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());

		table.removeInverseJoinColumn(0);
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(inverseJoinColumns = @JoinColumn(name = \"FOO\")))", cu);
		inverseJoinColumns = table.getInverseJoinColumns().iterator();
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
		
		table.removeInverseJoinColumn(0);
		assertSourceDoesNotContain("@JoinTable(", cu);
	}
	
	public void testJoinTableMoveInverseJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithInverseJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		table.addInverseJoinColumn(0).setName("FOO");
		
		Iterator<JoinColumnAnnotation> inverseJoinColumns = table.getInverseJoinColumns().iterator();
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertNull(inverseJoinColumns.next().getName());
		
		table.moveInverseJoinColumn(2, 0);
		inverseJoinColumns = table.getInverseJoinColumns().iterator();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertNull(inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(inverseJoinColumns = {@JoinColumn(name = \"BAR\"), @JoinColumn, @JoinColumn(name = \"FOO\")}))", cu);
	}
	
	public void testJoinTableMoveInverseJoinColumn2() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithInverseJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
		table.addInverseJoinColumn(1).setName("FOO");
		
		Iterator<JoinColumnAnnotation> inverseJoinColumns = table.getInverseJoinColumns().iterator();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertNull(inverseJoinColumns.next().getName());
		
		table.moveInverseJoinColumn(0, 2);
		inverseJoinColumns = table.getInverseJoinColumns().iterator();
		assertNull(inverseJoinColumns.next().getName());
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());

		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(inverseJoinColumns = {@JoinColumn, @JoinColumn(name = \"BAR\"), @JoinColumn(name = \"FOO\")}))", cu);
	}
	
	public void testJoinTableSetInverseJoinColumnName() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideJoinTableWithInverseJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		AssociationOverride2_0Annotation associationOverride = this.associationOverrideAt(0, resourceType);
		JoinTableAnnotation table = associationOverride.getJoinTable();
				
		assertEquals(2, table.getInverseJoinColumnsSize());
		
		JoinColumnAnnotation joinColumn = table.inverseJoinColumnAt(0);
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinTable = @JoinTable(inverseJoinColumns = {@JoinColumn(name = \"foo\"), @JoinColumn}))", cu);
	}

}

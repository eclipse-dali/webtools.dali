/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;

@SuppressWarnings("nls")
public class AssociationOverridesTests extends JpaJavaResourceModelTestCase {
	
	private static final String ASSOCIATION_OVERRIDE_NAME = "MY_ASSOCIATION_OVERRIDE";
	
	public AssociationOverridesTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestAssociationOverrideOnField() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@AssociationOverrides(@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\"))");
			}
		});
	}
	
	private ICompilationUnit createTestAssociationOverrideWithJoinColumns() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES, JPA.JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@AssociationOverrides(@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name = \"BAR\"), @JoinColumn}))");
			}
		});
	}
	
	private ICompilationUnit createTestAssociationOverride() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ASSOCIATION_OVERRIDE, JPA.JOIN_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@AssociationOverride(name = \"FOO\", joinColumns = @JoinColumn(name = \"FOO\", columnDefinition = \"BAR\", referencedColumnName = \"BAZ\"))");
			}
		});
	}

	private AssociationOverrideAnnotation getAssociationOverrideAnnotationAt(JavaResourceMember resourceMember, int index) {
		return (AssociationOverrideAnnotation) resourceMember.getAnnotation(index, JPA.ASSOCIATION_OVERRIDE);
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		AssociationOverrideAnnotation associationOverride = this.getAssociationOverrideAnnotationAt(resourceField, 0);

		assertNotNull(associationOverride);
		assertEquals(ASSOCIATION_OVERRIDE_NAME, associationOverride.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		AssociationOverrideAnnotation associationOverride = this.getAssociationOverrideAnnotationAt(resourceField, 0);

		assertNotNull(associationOverride);
		assertEquals(ASSOCIATION_OVERRIDE_NAME, associationOverride.getName());

		associationOverride.setName("Foo");
		assertEquals("Foo", associationOverride.getName());
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name = \"Foo\"))", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		AssociationOverrideAnnotation associationOverride = this.getAssociationOverrideAnnotationAt(resourceField, 0);
		assertEquals(ASSOCIATION_OVERRIDE_NAME, associationOverride.getName());
		
		associationOverride.setName(null);
		assertNull(associationOverride.getName());
		
		assertSourceDoesNotContain("@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\")", cu);
		assertSourceContains("@AssociationOverride", cu);
		assertSourceContains("@AssociationOverrides", cu);
	}
	
	public void testAddAssociationOverrideCopyExisting() throws Exception {
		ICompilationUnit cu = createTestAssociationOverride();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		String expected1 = "@AssociationOverrides({";
		String expected2 = "@AssociationOverride(name = \"FOO\", joinColumns = @JoinColumn(name = \"FOO\", columnDefinition = \"BAR\", referencedColumnName = \"BAZ\")),";
		String expected3 = "@AssociationOverride(name = \"BAR\") })";
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) resourceType.addAnnotation(1, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("BAR");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
		
		assertNull(resourceType.getAnnotation(JPA.ASSOCIATION_OVERRIDE));
		assertNotNull(resourceType.getContainerAnnotation(JPA.ASSOCIATION_OVERRIDES));
		assertNotNull(resourceType.getAnnotation(0, JPA.ASSOCIATION_OVERRIDE));
		assertEquals(2, resourceType.getAnnotationsSize(JPA.ASSOCIATION_OVERRIDE));
	}
	
	public void testAddAssociationOverrideToBeginningOfList() throws Exception {
		ICompilationUnit cu = createTestAssociationOverride();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		String expected1 = "@AssociationOverrides({";
		String expected2 = "@AssociationOverride(name = \"FOO\", joinColumns = @JoinColumn(name = \"FOO\", columnDefinition = \"BAR\", referencedColumnName = \"BAZ\")),";
		String expected3 = "@AssociationOverride(name = \"BAR\") })";

		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) resourceType.addAnnotation(1, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("BAR");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
		
		expected2 = "@AssociationOverride(name = \"BAZ\"),";
		expected3 = "@AssociationOverride(name = \"FOO\", joinColumns = @JoinColumn(name = \"FOO\", columnDefinition = \"BAR\", referencedColumnName = \"BAZ\")), @AssociationOverride(name = \"BAR\") })";
		associationOverride = (AssociationOverrideAnnotation) resourceType.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("BAZ");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);

		Iterator<NestableAnnotation> associationOverrides = resourceType.getAnnotations(JPA.ASSOCIATION_OVERRIDE).iterator();
		assertEquals("BAZ", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertEquals("FOO", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertEquals("BAR", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());

		assertNull(resourceType.getAnnotation(JPA.ASSOCIATION_OVERRIDE));
		assertNotNull(resourceType.getContainerAnnotation(JPA.ASSOCIATION_OVERRIDES));
		assertNotNull(resourceType.getAnnotation(0, JPA.ASSOCIATION_OVERRIDE));
		assertEquals(3, resourceType.getAnnotationsSize(JPA.ASSOCIATION_OVERRIDE));
	}

	public void testRemoveAssociationOverrideCopyExisting() throws Exception {
		ICompilationUnit cu = createTestAssociationOverride();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		String expected1 = "@AssociationOverrides({";
		String expected2 = "@AssociationOverride(name = \"FOO\", joinColumns = @JoinColumn(name = \"FOO\", columnDefinition = \"BAR\", referencedColumnName = \"BAZ\")),";
		String expected3 = "@AssociationOverride(name = \"BAR\") })";
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) resourceType.addAnnotation(1, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("BAR");
		assertSourceContains(expected1, cu);
		assertSourceContains(expected2, cu);
		assertSourceContains(expected3, cu);
		
		expected2 = "@AssociationOverride(name = \"FOO\", joinColumns = @JoinColumn(name = \"FOO\", columnDefinition = \"BAR\", referencedColumnName = \"BAZ\"))";
		resourceType.removeAnnotation(1, JPA.ASSOCIATION_OVERRIDE);
		assertSourceContains(expected2, cu);
		assertSourceDoesNotContain("@AssociationOverrides", cu);
	}
	
	public void testJoinColumns() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		AssociationOverrideAnnotation associationOverride = this.getAssociationOverrideAnnotationAt(resourceField, 0);
		
		assertEquals(0, associationOverride.getJoinColumnsSize());
	}
	
	public void testJoinColumns2() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		AssociationOverrideAnnotation associationOverride = this.getAssociationOverrideAnnotationAt(resourceField, 0);
		
		associationOverride.addJoinColumn(0);
		associationOverride.addJoinColumn(1);
		
		assertEquals(2, associationOverride.getJoinColumnsSize());
	}
	
	public void testJoinColumns3() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		AssociationOverrideAnnotation associationOverride = this.getAssociationOverrideAnnotationAt(resourceField, 0);
				
		assertEquals(2, associationOverride.getJoinColumnsSize());
	}
	
	public void testAddJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		AssociationOverrideAnnotation associationOverride = this.getAssociationOverrideAnnotationAt(resourceField, 0);
		
		associationOverride.addJoinColumn(0).setName("FOO");
		associationOverride.addJoinColumn(1);
		associationOverride.addJoinColumn(0).setName("BAR");


		Iterator<JoinColumnAnnotation> joinColumns = associationOverride.getJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertNull(joinColumns.next().getName());
	
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name = \"BAR\"),@JoinColumn(name = \"FOO\"), @JoinColumn}))", cu);
	}
	
	public void testRemoveJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		AssociationOverrideAnnotation associationOverride = this.getAssociationOverrideAnnotationAt(resourceField, 0);
		associationOverride.addJoinColumn(0).setName("FOO");
		
		Iterator<JoinColumnAnnotation> joinColumns = associationOverride.getJoinColumns().iterator();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertNull(joinColumns.next().getName());
		assertEquals(false, joinColumns.hasNext());
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name = \"FOO\"), @JoinColumn(name = \"BAR\"), @JoinColumn}))", cu);
		
		associationOverride.removeJoinColumn(1);
		joinColumns = associationOverride.getJoinColumns().iterator();
		assertEquals("FOO", joinColumns.next().getName());
		assertNull(joinColumns.next().getName());
		assertEquals(false, joinColumns.hasNext());
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name = \"FOO\"), @JoinColumn}))", cu);

		associationOverride.removeJoinColumn(0);
		joinColumns = associationOverride.getJoinColumns().iterator();
		assertNull(joinColumns.next().getName());
		assertEquals(false, joinColumns.hasNext());
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = @JoinColumn))", cu);

		
		associationOverride.setName(null);
		associationOverride.removeJoinColumn(0);
		assertSourceDoesNotContain("@JoinColumn", cu);
	}
	
	public void testMoveJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		AssociationOverrideAnnotation associationOverride = this.getAssociationOverrideAnnotationAt(resourceField, 0);
		JoinColumnAnnotation joinColumn = associationOverride.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		associationOverride.addJoinColumn(0).setName("FOO");
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name = \"FOO\"), @JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn}))", cu);

		associationOverride.moveJoinColumn(2, 0);
		assertEquals("BAR", associationOverride.joinColumnAt(0).getName());
		assertNull(associationOverride.joinColumnAt(1).getName());
		assertEquals("FOO", associationOverride.joinColumnAt(2).getName());
		assertEquals(3, associationOverride.getJoinColumnsSize());
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn, @JoinColumn(name = \"FOO\")}))", cu);
	}
	
	public void testMoveJoinColumn2() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		AssociationOverrideAnnotation associationOverride = this.getAssociationOverrideAnnotationAt(resourceField, 0);
		
		JoinColumnAnnotation joinColumn = associationOverride.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		associationOverride.addJoinColumn(0).setName("FOO");
		
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name = \"FOO\"), @JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn}))", cu);
		associationOverride.moveJoinColumn(0, 2);
		assertNull(associationOverride.joinColumnAt(0).getName());
		assertEquals("FOO", associationOverride.joinColumnAt(1).getName());
		assertEquals("BAR", associationOverride.joinColumnAt(2).getName());
		assertEquals(3, associationOverride.getJoinColumnsSize());
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn, @JoinColumn(name = \"FOO\"), @JoinColumn(name = \"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\")}))", cu);
	}
	
	public void testSetJoinColumnName() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		AssociationOverrideAnnotation associationOverride = this.getAssociationOverrideAnnotationAt(resourceField, 0);
				
		assertEquals(2, associationOverride.getJoinColumnsSize());
		
		JoinColumnAnnotation joinColumn = associationOverride.joinColumnAt(0);
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name = \"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name = \"foo\"), @JoinColumn}))", cu);
	}
}

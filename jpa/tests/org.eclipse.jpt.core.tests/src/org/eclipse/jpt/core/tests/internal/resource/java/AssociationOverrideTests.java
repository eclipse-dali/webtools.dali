/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class AssociationOverrideTests extends JavaResourceModelTestCase {
	
	private static final String ASSOCIATION_OVERRIDE_NAME = "MY_ASSOCIATION_OVERRIDE";
	
	public AssociationOverrideTests(String name) {
		super(name);
	}
	
	private void createAssociationOverrideAnnotation() throws Exception {
		createJoinColumnAnnotation();
		this.createAnnotationAndMembers("AssociationOverride", 
			"String name(); " +
			"JoinColumn[] joinColumns(); ");
	}

	private void createJoinColumnAnnotation() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name() default \"\";" +
				"String referencedColumnName() default \"\";" +
				"boolean unique() default false;" +
				"boolean nullable() default true;" +
				"boolean insertable() default true;" +
				"boolean updatable() default true;" +
				"String columnDefinition() default \"\";" +
				"String table() default \"\";");	
	}

	private ICompilationUnit createTestAssociationOverrideOnField() throws Exception {
		createAssociationOverrideAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ASSOCIATION_OVERRIDE, JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestAssociationOverrideWithJoinColumns() throws Exception {
		createAssociationOverrideAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ASSOCIATION_OVERRIDE, JPA.JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns={@JoinColumn(name=\"BAR\"), @JoinColumn})");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);

		assertNotNull(associationOverride);
		assertEquals(ASSOCIATION_OVERRIDE_NAME, associationOverride.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);

		assertNotNull(associationOverride);
		assertEquals(ASSOCIATION_OVERRIDE_NAME, associationOverride.getName());

		associationOverride.setName("Foo");
		assertEquals("Foo", associationOverride.getName());
		assertSourceContains("@AssociationOverride(name=\"Foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);

		assertEquals(ASSOCIATION_OVERRIDE_NAME, associationOverride.getName());
		
		associationOverride.setName(null);
		assertNull(associationOverride.getName());
		
		assertSourceDoesNotContain("@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\")", cu);
		assertSourceContains("@AssociationOverride", cu);
	}
	
	
	public void testJoinColumns() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);
		
		assertEquals(0, associationOverride.joinColumnsSize());
	}
	
	public void testJoinColumns2() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);

		
		associationOverride.addJoinColumn(0);
		associationOverride.addJoinColumn(1);
				
		assertEquals(2, associationOverride.joinColumnsSize());
	}
	
	public void testJoinColumns3() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);
				
		assertEquals(2, associationOverride.joinColumnsSize());
	}
	
	public void testAddJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);
		
		associationOverride.addJoinColumn(0).setName("FOO");
		associationOverride.addJoinColumn(1);
		associationOverride.addJoinColumn(0).setName("BAR");

		assertEquals("BAR", associationOverride.joinColumnAt(0).getName());
		assertEquals("FOO", associationOverride.joinColumnAt(1).getName());
		assertNull(associationOverride.joinColumnAt(2).getName());

		assertSourceContains("@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name=\"BAR\"),@JoinColumn(name=\"FOO\"), @JoinColumn})", cu);
	}
	
	public void testRemoveJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);
		associationOverride.addJoinColumn(0).setName("FOO");
		
		Iterator<JoinColumnAnnotation> joinColumns = associationOverride.joinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertNull(joinColumns.next().getName());
		assertEquals(false, joinColumns.hasNext());
		assertSourceContains("@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns={@JoinColumn(name=\"FOO\"), @JoinColumn(name=\"BAR\"), @JoinColumn})", cu);
		
		associationOverride.removeJoinColumn(1);
		joinColumns = associationOverride.joinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertNull(joinColumns.next().getName());
		assertEquals(false, joinColumns.hasNext());
		assertSourceContains("@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns={@JoinColumn(name=\"FOO\"), @JoinColumn})", cu);

		associationOverride.removeJoinColumn(0);
		joinColumns = associationOverride.joinColumns();
		assertNull(joinColumns.next().getName());
		assertEquals(false, joinColumns.hasNext());
		assertSourceContains("@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns=@JoinColumn)", cu);

		
		associationOverride.setName(null);
		associationOverride.removeJoinColumn(0);
		assertSourceDoesNotContain("@AssociationOverride", cu);
	}
	
	public void testMoveJoinColumn() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);
		JoinColumnAnnotation joinColumn = associationOverride.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		associationOverride.addJoinColumn(0).setName("FOO");
		
		assertSourceContains("@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns={@JoinColumn(name=\"FOO\"), @JoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn})", cu);

		associationOverride.moveJoinColumn(2, 0);
		assertEquals("BAR", associationOverride.joinColumnAt(0).getName());
		assertNull(associationOverride.joinColumnAt(1).getName());
		assertEquals("FOO", associationOverride.joinColumnAt(2).getName());
		assertEquals(3, associationOverride.joinColumnsSize());
		assertSourceContains("@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns={@JoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn, @JoinColumn(name=\"FOO\")})", cu);
	}
	
	public void testMoveJoinColumn2() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);
		
		JoinColumnAnnotation joinColumn = associationOverride.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		associationOverride.addJoinColumn(0).setName("FOO");
		
		assertSourceContains("@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns={@JoinColumn(name=\"FOO\"), @JoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn})", cu);

		associationOverride.moveJoinColumn(0, 2);
		assertNull(associationOverride.joinColumnAt(0).getName());
		assertEquals("FOO", associationOverride.joinColumnAt(1).getName());
		assertEquals("BAR", associationOverride.joinColumnAt(2).getName());
		assertEquals(3, associationOverride.joinColumnsSize());
		assertSourceContains("@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns={@JoinColumn, @JoinColumn(name=\"FOO\"), @JoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\")})", cu);
	}
	
	public void testSetJoinColumnName() throws Exception {
		ICompilationUnit cu = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);
				
		assertEquals(2, associationOverride.joinColumnsSize());
		
		JoinColumnAnnotation joinColumn = associationOverride.joinColumns().next();
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns={@JoinColumn(name=\"foo\"), @JoinColumn})", cu);
	}
}

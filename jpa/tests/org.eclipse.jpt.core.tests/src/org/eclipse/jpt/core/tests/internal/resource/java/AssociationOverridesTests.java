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
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AssociationOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class AssociationOverridesTests extends JavaResourceModelTestCase {
	
	private static final String ASSOCIATION_OVERRIDE_NAME = "MY_ASSOCIATION_OVERRIDE";
	
	public AssociationOverridesTests(String name) {
		super(name);
	}
	
	private void createAssociationOverrideAnnotation() throws Exception {
		createJoinColumnAnnotation();
		this.createAnnotationAndMembers("AssociationOverride", 
			"String name(); " +
			"Column column(); ");
	}
	
	private void createAssociationOverridesAnnotation() throws Exception {
		createAssociationOverrideAnnotation();
		this.createAnnotationAndMembers("AssociationOverrides", 
			"AssociationOverride[] value();");
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

	private IType createTestAssociationOverrideOnField() throws Exception {
		createAssociationOverridesAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@AssociationOverrides(@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\"))");
			}
		});
	}
	private IType createTestAssociationOverrideWithJoinColumns() throws Exception {
		createAssociationOverridesAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES, JPA.JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@AssociationOverrides(@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns={@JoinColumn(name=\"BAR\"), @JoinColumn}))");
			}
		});
	}
	
	private IType createTestAssociationOverride() throws Exception {
		createAssociationOverridesAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ASSOCIATION_OVERRIDE, JPA.JOIN_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@AssociationOverride(name=\"FOO\", joinColumns=@JoinColumn(name=\"FOO\", columnDefinition=\"BAR\", referencedColumnName=\"BAZ\"))");
			}
		});
	}

	public void testGetName() throws Exception {
		IType testType = this.createTestAssociationOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AssociationOverridesAnnotation associationOverrides = (AssociationOverridesAnnotation) attributeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDES);
		AssociationOverrideAnnotation associationOverride = associationOverrides.nestedAnnotations().next();

		assertNotNull(associationOverride);
		assertEquals(ASSOCIATION_OVERRIDE_NAME, associationOverride.getName());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestAssociationOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AssociationOverridesAnnotation associationOverrides = (AssociationOverridesAnnotation) attributeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDES);
		AssociationOverrideAnnotation associationOverride = associationOverrides.nestedAnnotations().next();

		assertNotNull(associationOverride);
		assertEquals(ASSOCIATION_OVERRIDE_NAME, associationOverride.getName());

		associationOverride.setName("Foo");
		assertEquals("Foo", associationOverride.getName());
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name=\"Foo\"))");
	}
	
	public void testSetNameNull() throws Exception {
		IType testType = this.createTestAssociationOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AssociationOverridesAnnotation associationOverrides = (AssociationOverridesAnnotation) attributeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDES);
		AssociationOverrideAnnotation associationOverride = associationOverrides.nestedAnnotations().next();
		assertEquals(ASSOCIATION_OVERRIDE_NAME, associationOverride.getName());
		
		associationOverride.setName(null);
		assertNull(associationOverride.getName());
		
		assertSourceDoesNotContain("@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\")");
		assertSourceContains("@AssociationOverride");
		assertSourceContains("@AssociationOverrides");
	}
	
	public void testAddAssociationOverrideCopyExisting() throws Exception {
		IType jdtType = createTestAssociationOverride();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(1, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAR");
		assertSourceContains("@AssociationOverrides({@AssociationOverride(name=\"FOO\", joinColumns = @JoinColumn(name=\"FOO\", columnDefinition = \"BAR\", referencedColumnName = \"BAZ\")),@AssociationOverride(name=\"BAR\")})");
		
		assertNull(typeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE));
		assertNotNull(typeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDES));
		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES)));
	}
	
	public void testAddAssociationOverrideToBeginningOfList() throws Exception {
		IType jdtType = createTestAssociationOverride();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(1, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAR");
		assertSourceContains("@AssociationOverrides({@AssociationOverride(name=\"FOO\", joinColumns = @JoinColumn(name=\"FOO\", columnDefinition = \"BAR\", referencedColumnName = \"BAZ\")),@AssociationOverride(name=\"BAR\")})");
		
		associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAZ");
		assertSourceContains("@AssociationOverrides({@AssociationOverride(name=\"BAZ\"),@AssociationOverride(name=\"FOO\", joinColumns = @JoinColumn(name=\"FOO\", columnDefinition = \"BAR\", referencedColumnName = \"BAZ\")), @AssociationOverride(name=\"BAR\")})");

		Iterator<JavaResourceNode> associationOverrides = typeResource.annotations(JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		assertEquals("BAZ", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertEquals("FOO", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertEquals("BAR", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());

		assertNull(typeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE));
		assertNotNull(typeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDES));
		assertEquals(3, CollectionTools.size(typeResource.annotations(JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES)));
	}

	public void testRemoveAssociationOverrideCopyExisting() throws Exception {
		IType jdtType = createTestAssociationOverride();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(1, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAR");
		assertSourceContains("@AssociationOverrides({@AssociationOverride(name=\"FOO\", joinColumns = @JoinColumn(name=\"FOO\", columnDefinition = \"BAR\", referencedColumnName = \"BAZ\")),@AssociationOverride(name=\"BAR\")})");
		
		typeResource.removeAnnotation(1, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		assertSourceContains("@AssociationOverride(name=\"FOO\", joinColumns = @JoinColumn(name=\"FOO\", columnDefinition = \"BAR\", referencedColumnName = \"BAZ\"))");
	}
	
	public void testJoinColumns() throws Exception {
		IType testType = this.createTestAssociationOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.annotations(JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES).next();
		
		assertEquals(0, associationOverride.joinColumnsSize());
	}
	
	public void testJoinColumns2() throws Exception {
		IType testType = this.createTestAssociationOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.annotations(JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES).next();
		
		associationOverride.addJoinColumn(0);
		associationOverride.addJoinColumn(1);
		
		assertEquals(2, associationOverride.joinColumnsSize());
	}
	
	public void testJoinColumns3() throws Exception {
		IType testType = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.annotations(JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES).next();
				
		assertEquals(2, associationOverride.joinColumnsSize());
	}
	
	public void testAddJoinColumn() throws Exception {
		IType testType = this.createTestAssociationOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.annotations(JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES).next();
		
		associationOverride.addJoinColumn(0).setName("FOO");
		associationOverride.addJoinColumn(1);
		associationOverride.addJoinColumn(0).setName("BAR");


		Iterator<JoinColumnAnnotation> joinColumns = associationOverride.joinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertNull(joinColumns.next().getName());
	
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns = {@JoinColumn(name=\"BAR\"),@JoinColumn(name=\"FOO\"), @JoinColumn}))");
	}
	
	public void testRemoveJoinColumn() throws Exception {
		IType testType = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.annotations(JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES).next();
		associationOverride.addJoinColumn(0).setName("FOO");
		
		Iterator<JoinColumnAnnotation> joinColumns = associationOverride.joinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertNull(joinColumns.next().getName());
		assertEquals(false, joinColumns.hasNext());
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns={@JoinColumn(name=\"FOO\"), @JoinColumn(name=\"BAR\"), @JoinColumn}))");	
		
		associationOverride.removeJoinColumn(1);
		joinColumns = associationOverride.joinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertNull(joinColumns.next().getName());
		assertEquals(false, joinColumns.hasNext());
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns={@JoinColumn(name=\"FOO\"), @JoinColumn}))");	

		associationOverride.removeJoinColumn(0);
		joinColumns = associationOverride.joinColumns();
		assertNull(joinColumns.next().getName());
		assertEquals(false, joinColumns.hasNext());
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns=@JoinColumn))");	

		
		associationOverride.setName(null);
		associationOverride.removeJoinColumn(0);
		assertSourceDoesNotContain("@AssociationOverride");
	}
	
	public void testMoveJoinColumn() throws Exception {
		IType testType = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.annotations(JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES).next();
		JoinColumnAnnotation joinColumn = associationOverride.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		associationOverride.addJoinColumn(0).setName("FOO");
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns={@JoinColumn(name=\"FOO\"), @JoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn}))");

		associationOverride.moveJoinColumn(2, 0);
		assertEquals("BAR", associationOverride.joinColumnAt(0).getName());
		assertNull(associationOverride.joinColumnAt(1).getName());
		assertEquals("FOO", associationOverride.joinColumnAt(2).getName());
		assertEquals(3, associationOverride.joinColumnsSize());
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns={@JoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn, @JoinColumn(name=\"FOO\")}))");
	}
	
	public void testMoveJoinColumn2() throws Exception {
		IType testType = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.annotations(JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES).next();
		
		JoinColumnAnnotation joinColumn = associationOverride.joinColumnAt(0);
		joinColumn.setReferencedColumnName("REF_NAME");
		joinColumn.setUnique(Boolean.FALSE);
		joinColumn.setNullable(Boolean.FALSE);
		joinColumn.setInsertable(Boolean.FALSE);
		joinColumn.setUpdatable(Boolean.FALSE);
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setTable("TABLE");
		associationOverride.addJoinColumn(0).setName("FOO");
		
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns={@JoinColumn(name=\"FOO\"), @JoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\"), @JoinColumn}))");
		associationOverride.moveJoinColumn(0, 2);
		assertNull(associationOverride.joinColumnAt(0).getName());
		assertEquals("FOO", associationOverride.joinColumnAt(1).getName());
		assertEquals("BAR", associationOverride.joinColumnAt(2).getName());
		assertEquals(3, associationOverride.joinColumnsSize());
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns={@JoinColumn, @JoinColumn(name=\"FOO\"), @JoinColumn(name=\"BAR\", referencedColumnName = \"REF_NAME\", unique = false, nullable = false, insertable = false, updatable = false, columnDefinition = \"COLUMN_DEF\", table = \"TABLE\")}))");
	}
	
	public void testSetJoinColumnName() throws Exception {
		IType testType = this.createTestAssociationOverrideWithJoinColumns();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) attributeResource.annotations(JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES).next();
				
		assertEquals(2, associationOverride.joinColumnsSize());
		
		JoinColumnAnnotation joinColumn = associationOverride.joinColumns().next();
		
		assertEquals("BAR", joinColumn.getName());
		
		joinColumn.setName("foo");
		assertEquals("foo", joinColumn.getName());
		
		assertSourceContains("@AssociationOverrides(@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\", joinColumns={@JoinColumn(name=\"foo\"), @JoinColumn}))");
	}
}

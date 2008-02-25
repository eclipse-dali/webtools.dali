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
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrides;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class AttributeOverridesTests extends JavaResourceModelTestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String ATTRIBUTE_OVERRIDE_NAME = "MY_ATTRIBUTE_OVERRIDE";
	
	public AttributeOverridesTests(String name) {
		super(name);
	}
	
	private void createAttributeOverrideAnnotation() throws Exception {
		createColumnAnnotation();
		this.createAnnotationAndMembers("AttributeOverride", 
			"String name(); " +
			"Column column(); ");
	}
	
	private void createAttributeOverridesAnnotation() throws Exception {
		createAttributeOverrideAnnotation();
		this.createAnnotationAndMembers("AttributeOverrides", 
			"AttributeOverride[] value();");
	}

	private void createColumnAnnotation() throws Exception {
		this.createAnnotationAndMembers("Column", 
			"String name() default \"\"; " +
			"boolean unique() default false; " +
			"boolean nullable() default true; " +
			"boolean insertable() default true; " +
			"boolean updatable() default true; " +
			"String columnDefinition() default \"\"; " +
			"String table() default \"\"; " +
			"int length() default 255; " +
			"int precision() default 0; " +
			"int scale() default 0;");
	}

	private IType createTestAttributeOverrideOnField() throws Exception {
		createAttributeOverridesAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES, JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@AttributeOverrides(@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\"))");
			}
		});
	}
	
	private IType createTestAttributeOverrideWithColumnOnField() throws Exception {
		createAttributeOverridesAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES, JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@AttributeOverrides(@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\", column = @Column(name=\"" + COLUMN_NAME + "\")))");
			}
		});
	}
	
	private IType createTestAttributeOverride() throws Exception {
		createAttributeOverrideAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ATTRIBUTE_OVERRIDE, JPA.COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
				sb.append("@AttributeOverride(name=\"FOO\", column=@Column(name=\"FOO\", columnDefinition=\"BAR\", table=\"BAZ\", unique=false, nullable=false, insertable=false, updatable=false, length=1, precision=1, scale=1))");
			}
		});
	}

	public void testGetName() throws Exception {
		IType testType = this.createTestAttributeOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AttributeOverrides attributeOverrides = (AttributeOverrides) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES);
		AttributeOverrideAnnotation attributeOverride = attributeOverrides.nestedAnnotations().next();

		assertNotNull(attributeOverride);
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, attributeOverride.getName());
	}

	public void testGetNullColumn() throws Exception {
		IType testType = this.createTestAttributeOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AttributeOverrides attributeOverrides = (AttributeOverrides) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES);
		AttributeOverrideAnnotation attributeOverride = attributeOverrides.nestedAnnotations().next();
		ColumnAnnotation column = attributeOverride.getColumn();
		assertNotNull(attributeOverride);
		assertNull(column);
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestAttributeOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AttributeOverrides attributeOverrides = (AttributeOverrides) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES);
		AttributeOverrideAnnotation attributeOverride = attributeOverrides.nestedAnnotations().next();

		assertNotNull(attributeOverride);
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, attributeOverride.getName());

		attributeOverride.setName("Foo");
		assertEquals("Foo", attributeOverride.getName());
		assertSourceContains("@AttributeOverrides(@AttributeOverride(name=\"Foo\"))");
	}
	
	public void testSetNameNull() throws Exception {
		IType testType = this.createTestAttributeOverrideOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AttributeOverrides attributeOverrides = (AttributeOverrides) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES);
		AttributeOverrideAnnotation attributeOverride = attributeOverrides.nestedAnnotations().next();
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, attributeOverride.getName());
		
		attributeOverride.setName(null);
		assertNull(attributeOverride.getName());
		
		assertSourceDoesNotContain("@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\")");
		assertSourceContains("@AttributeOverride");
		assertSourceContains("@AttributeOverrides");
	}
	
	public void testColumnGetName() throws Exception {
		IType testType = this.createTestAttributeOverrideWithColumnOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AttributeOverrides attributeOverrides = (AttributeOverrides) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES);
		AttributeOverrideAnnotation attributeOverride = attributeOverrides.nestedAnnotations().next();

		ColumnAnnotation column = attributeOverride.getColumn();
		assertEquals(COLUMN_NAME, column.getName());
	}
	
	public void testColumnSetName() throws Exception {
		IType testType = this.createTestAttributeOverrideWithColumnOnField();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES).next();

		ColumnAnnotation column = attributeOverride.getColumn();
		
		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName("Foo");
		
		assertSourceContains("@AttributeOverrides(@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\", column = @Column(name=\"Foo\")))");
		
		column.setName(null);
		//TODO should I have to update from java before column is set to null??
		attributeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		assertNull(attributeOverride.getColumn());
		assertSourceContains("@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\")");
	}
	
	public void testAddAttributeOverrideCopyExisting() throws Exception {
		IType jdtType = createTestAttributeOverride();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"FOO\", column = @Column(name=\"FOO\", columnDefinition = \"BAR\", table = \"BAZ\", unique = false, nullable = false, insertable = false, updatable = false, length = 1, precision = 1, scale = 1)),@AttributeOverride(name=\"BAR\")})");
		
		assertNull(typeResource.annotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(typeResource.annotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(2, CollectionTools.size(typeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
	}
	
	public void testAddAttributeOverrideToBeginningOfList() throws Exception {
		IType jdtType = createTestAttributeOverride();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"FOO\", column = @Column(name=\"FOO\", columnDefinition = \"BAR\", table = \"BAZ\", unique = false, nullable = false, insertable = false, updatable = false, length = 1, precision = 1, scale = 1)),@AttributeOverride(name=\"BAR\")})");
		
		attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAZ");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"BAZ\"),@AttributeOverride(name=\"FOO\", column = @Column(name=\"FOO\", columnDefinition = \"BAR\", table = \"BAZ\", unique = false, nullable = false, insertable = false, updatable = false, length = 1, precision = 1, scale = 1)), @AttributeOverride(name=\"BAR\")})");

		Iterator<JavaResourceNode> attributeOverrides = typeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertEquals("BAZ", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("FOO", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("BAR", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());

		assertNull(typeResource.annotation(JPA.ATTRIBUTE_OVERRIDE));
		assertNotNull(typeResource.annotation(JPA.ATTRIBUTE_OVERRIDES));
		assertEquals(3, CollectionTools.size(typeResource.annotations(JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES)));
	}

	public void testRemoveAttributeOverrideCopyExisting() throws Exception {
		IType jdtType = createTestAttributeOverride();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(jdtType);
		
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"FOO\", column = @Column(name=\"FOO\", columnDefinition = \"BAR\", table = \"BAZ\", unique = false, nullable = false, insertable = false, updatable = false, length = 1, precision = 1, scale = 1)),@AttributeOverride(name=\"BAR\")})");
		
		typeResource.removeAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertSourceContains("@AttributeOverride(name=\"FOO\", column = @Column(name=\"FOO\", columnDefinition = \"BAR\", table = \"BAZ\", unique = false, nullable = false, insertable = false, updatable = false, length = 1, precision = 1, scale = 1))");
	}
	//not sure i want to test this api, how can we keep ContainerAnnotation.add, move, remove from being public?
	//users should go throught AbstractJavapersistenceResource. this gets confusing because you would handle it differently
	//for non top-level annotations
//	public void testAdd() throws Exception {
//		IType testType = this.createTestType();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
//		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
//		AttributeOverrides attributeOverrides = (AttributeOverrides) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES);
//		assertNull(attributeOverrides);
//		
//		attributeResource.addAnnotation(JPA.ATTRIBUTE_OVERRIDES);
//		attributeOverrides = (AttributeOverrides) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES);
//		assertNotNull(attributeOverrides);
//		
//		assertSourceContains("@AttributeOverrides");
//		
//		AttributeOverride fooAttributeOverride = attributeOverrides.add(0);
//		fooAttributeOverride.setName("Foo");
//	
//		assertSourceContains("@AttributeOverrides(@AttributeOverride(name=\"Foo\"))");
//
//		AttributeOverride barAttributeOverride = attributeOverrides.add(0);
//		barAttributeOverride.setName("Bar");
//	
//		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"Bar\"), @AttributeOverride(name=\"Foo\")})");
//
//	}
//	
//	public void testMove() throws Exception {
//		IType testType = this.createTestAttributeOverrideWithColumnOnField();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
//		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
//		AttributeOverrides attributeOverrides = (AttributeOverrides) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES);
//		
//		AttributeOverride fooAttributeOverride = attributeOverrides.add(1);
//		fooAttributeOverride.setName("Foo");
//		
//		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\"), @AttributeOverride(name=\"Foo\")})");
//		
//		attributeOverrides.move(0, 1);
//		
//		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"Foo\"), @AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\")})");
//	}
//	
//	public void testRemove() throws Exception {
//		IType testType = this.createTestAttributeOverrideWithColumnOnField();
//		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
//		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
//		AttributeOverrides attributeOverrides = (AttributeOverrides) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDES);
//		
//		AttributeOverride fooAttributeOverride = attributeOverrides.add(1);
//		fooAttributeOverride.setName("Foo");
//		
//		assertSourceContains("@AttributeOverrides({@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\"), @AttributeOverride(name=\"Foo\")})");
//		
//		attributeOverrides.remove(0);
//
//		assertSourceContains("@AttributeOverrides(@AttributeOverride(name=\"Foo\"))");
//
//		attributeOverrides.remove(0);
//		
//		assertSourceContains("@AttributeOverrides()");
//
//	}
	


}

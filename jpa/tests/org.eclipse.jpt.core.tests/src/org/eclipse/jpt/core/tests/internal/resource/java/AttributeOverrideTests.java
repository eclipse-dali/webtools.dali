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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.platform.generic.GenericJpaPlatform;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverride;
import org.eclipse.jpt.core.internal.resource.java.Column;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResourceImpl;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class AttributeOverrideTests extends AnnotationTestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String ATTRIBUTE_OVERRIDE_NAME = "MY_ATTRIBUTE_OVERRIDE";
	
	public AttributeOverrideTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}
	
	private void createAttributeOverrideAnnotation() throws Exception {
		this.createAnnotationAndMembers("AttributeOverride", 
			"String name(); " +
			"Column column(); ");
	}
	
	private void createAttributeOverridesAnnotation() throws Exception {
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
		createAttributeOverrideAnnotation();
		createColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ATTRIBUTE_OVERRIDE, JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\")");
			}
		});
	}
	
	private IType createTestAttributeOverrideWithColumnOnField() throws Exception {
		createAttributeOverrideAnnotation();
		createColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ATTRIBUTE_OVERRIDE, JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\", column = @Column(name=\"" + COLUMN_NAME + "\"))");
			}
		});
	}
		
	protected JavaResource buildParentResource(final IJpaPlatform jpaPlatform) {
		return new JavaResource() {
			public void updateFromJava(CompilationUnit astRoot) {
			}
			public IJpaPlatform jpaPlatform() {
				return jpaPlatform;
			}
		};
	}
	
	protected IJpaPlatform buildJpaPlatform() {
		return new GenericJpaPlatform();
	}

	protected JavaPersistentTypeResource buildJavaTypeResource(IType testType) {
		JavaPersistentTypeResource typeResource = new JavaPersistentTypeResourceImpl(buildParentResource(buildJpaPlatform()), new Type(testType, MODIFY_SHARED_DOCUMENT_COMMAND_EXECUTOR_PROVIDER));
		typeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		return typeResource;
	}

	public void testGetName() throws Exception {
		IType testType = this.createTestAttributeOverrideOnField();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDE);

		assertNotNull(attributeOverride);
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, attributeOverride.getName());
	}

	public void testGetNullColumn() throws Exception {
		IType testType = this.createTestAttributeOverrideOnField();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDE);
		Column column = attributeOverride.getColumn();
		assertNotNull(attributeOverride);
		assertNull(column);
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestAttributeOverrideOnField();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDE);

		assertNotNull(attributeOverride);
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, attributeOverride.getName());

		attributeOverride.setName("Foo");
		assertEquals("Foo", attributeOverride.getName());
		assertSourceContains("@AttributeOverride(name=\"Foo\")");
	}
	
	public void testSetNameNull() throws Exception {
		IType testType = this.createTestAttributeOverrideOnField();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDE);

		assertEquals(ATTRIBUTE_OVERRIDE_NAME, attributeOverride.getName());
		
		attributeOverride.setName(null);
		assertNull(attributeOverride.getName());
		
		assertSourceDoesNotContain("@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\")");
		assertSourceContains("@AttributeOverride");
	}
	
	public void testColumnGetName() throws Exception {
		IType testType = this.createTestAttributeOverrideWithColumnOnField();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDE);
		Column column = attributeOverride.getColumn();
		assertEquals(COLUMN_NAME, column.getName());
	}
	
	public void testColumnSetName() throws Exception {
		IType testType = this.createTestAttributeOverrideWithColumnOnField();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDE);
		Column column = attributeOverride.getColumn();
		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName("Foo");
		
		assertSourceContains("@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\", column = @Column(name=\"Foo\"))");
		
		column.setName(null);
		//TODO should I have to update from java before column is set to null??
		attributeResource.updateFromJava(JDTTools.buildASTRoot(testType));
		assertNull(attributeOverride.getColumn());
		assertSourceContains("@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\")");
	}
	
	public void testAddColumn() throws Exception {
		IType testType = this.createTestAttributeOverrideOnField();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDE);
		Column column = attributeOverride.getColumn();
		assertNull(column);
	}
	
	public void testRemoveColumn() throws Exception {
		IType testType = this.createTestAttributeOverrideOnField();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		AttributeOverride attributeOverride = (AttributeOverride) attributeResource.annotation(JPA.ATTRIBUTE_OVERRIDE);
		Column column = attributeOverride.getColumn();
		assertNull(column);
	}

}

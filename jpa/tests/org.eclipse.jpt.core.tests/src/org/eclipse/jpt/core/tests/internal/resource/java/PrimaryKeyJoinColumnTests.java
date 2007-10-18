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
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResourceImpl;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.tests.internal.jdtutility.AnnotationTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class PrimaryKeyJoinColumnTests extends AnnotationTestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String COLUMN_COLUMN_DEFINITION = "COLUMN_DEFINITION";
	private static final String COLUMN_REFERENCED_COLUMN_NAME = "MY_REF_COLUMN_NAME";
	
	public PrimaryKeyJoinColumnTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("javax.persistence", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}
	
	private void createPrimaryKeyJoinColumnAnnotation() throws Exception {
		this.createAnnotationAndMembers("PrimaryKeyJoinColumn", 
			"String name() default \"\"; " +
			"String referencedColumnName() default \"\"; " +
			"String columnDefinition() default \"\"; ");
	}

	private IType createTestPrimaryKeyJoinColumn() throws Exception {
		createPrimaryKeyJoinColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.PRIMARY_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@PrimaryKeyJoinColumn");
			}
		});
	}
	
	private IType createTestPrimaryKeyJoinColumnWithName() throws Exception {
		createPrimaryKeyJoinColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.PRIMARY_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@PrimaryKeyJoinColumn(name=\"" + COLUMN_NAME + "\")");
			}
		});
	}
	
	private IType createTestPrimaryKeyJoinColumnWithReferencedColumnName() throws Exception {
		createPrimaryKeyJoinColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.PRIMARY_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@PrimaryKeyJoinColumn(referencedColumnName=\"" + COLUMN_REFERENCED_COLUMN_NAME + "\")");
			}
		});
	}
	
	private IType createTestPrimaryKeyJoinColumnWithColumnDefinition() throws Exception {
		createPrimaryKeyJoinColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.PRIMARY_KEY_JOIN_COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@PrimaryKeyJoinColumn(columnDefinition=\"" + COLUMN_COLUMN_DEFINITION + "\")");
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
		IType testType = this.createTestPrimaryKeyJoinColumnWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumn column = (PrimaryKeyJoinColumn) attributeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertNotNull(column);
		assertEquals(COLUMN_NAME, column.getName());
	}

	public void testGetNull() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumn column = (PrimaryKeyJoinColumn) attributeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertNotNull(column);
		assertNull(column.getReferencedColumnName());
		assertNull(column.getColumnDefinition());
	}

	public void testSetName() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumn column = (PrimaryKeyJoinColumn) attributeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getName());

		column.setName("Foo");
		assertEquals("Foo", column.getName());
		
		assertSourceContains("@PrimaryKeyJoinColumn(name=\"Foo\")");
	}
	
	public void testSetNameNull() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumnWithName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumn column = (PrimaryKeyJoinColumn) attributeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);

		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName(null);
		assertNull(column.getName());
		
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn");
	}


	
	public void testGetReferencedColumnName() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumnWithReferencedColumnName();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumn column = (PrimaryKeyJoinColumn) attributeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertEquals(COLUMN_REFERENCED_COLUMN_NAME, column.getReferencedColumnName());
	}

	public void testSetReferencedColumnName() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumn column = (PrimaryKeyJoinColumn) attributeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getReferencedColumnName());

		column.setReferencedColumnName("Foo");
		assertEquals("Foo", column.getReferencedColumnName());
		
		assertSourceContains("@PrimaryKeyJoinColumn(referencedColumnName=\"Foo\")");

		
		column.setReferencedColumnName(null);
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn");
	}

	public void testGetColumnDefinition() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumnWithColumnDefinition();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumn column = (PrimaryKeyJoinColumn) attributeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);
		assertEquals(COLUMN_COLUMN_DEFINITION, column.getColumnDefinition());
	}

	public void testSetColumnDefinition() throws Exception {
		IType testType = this.createTestPrimaryKeyJoinColumn();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		PrimaryKeyJoinColumn column = (PrimaryKeyJoinColumn) attributeResource.annotation(JPA.PRIMARY_KEY_JOIN_COLUMN);

		assertNotNull(column);
		assertNull(column.getColumnDefinition());

		column.setColumnDefinition("Foo");
		assertEquals("Foo", column.getColumnDefinition());
		
		assertSourceContains("@PrimaryKeyJoinColumn(columnDefinition=\"Foo\")");

		
		column.setColumnDefinition(null);
		assertSourceDoesNotContain("@PrimaryKeyJoinColumn");
	}

}

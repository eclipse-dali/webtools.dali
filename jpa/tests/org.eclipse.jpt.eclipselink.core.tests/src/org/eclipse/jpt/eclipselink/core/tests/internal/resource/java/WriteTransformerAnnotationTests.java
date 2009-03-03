/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.WriteTransformerAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class WriteTransformerAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public WriteTransformerAnnotationTests(String name) {
		super(name);
	}

	private void createWriteTransformerAnnotation() throws Exception {
		this.createAnnotationAndMembers("WriteTransformer", "Class transformerClass() default void.class; String method() default \"\";Column column() default @Column");
		this.createColumnAnnotation();
	}
	
	private void createColumnAnnotation() throws Exception {
		this.createAnnotationAndMembers(JPA.PACKAGE, "Column", 
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

	private ICompilationUnit createTestWriteTransformer() throws Exception {
		createWriteTransformerAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.WRITE_TRANSFORMER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@WriteTransformer");
			}
		});
	}
	
	private ICompilationUnit createTestWriteTransformerWithTransformerClass() throws Exception {
		createWriteTransformerAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.WRITE_TRANSFORMER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@WriteTransformer(transformerClass = Foo.class)");
			}
		});
	}
	
	private ICompilationUnit createTestWriteTransformerWithMethod() throws Exception {
		createWriteTransformerAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.WRITE_TRANSFORMER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@WriteTransformer(method = \"transformerMethod\")");
			}
		});
	}
	
	private ICompilationUnit createTestWriteTransformerWithColumn() throws Exception {
		createWriteTransformerAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.WRITE_TRANSFORMER, JPA.COLUMN);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@WriteTransformer(column = @Column(name = \"FOO\"))");
			}
		});
	}


	public void testWriteTransformerAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestWriteTransformer();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertNotNull(attributeResource.getSupportingAnnotation(EclipseLinkJPA.WRITE_TRANSFORMER));
		
		attributeResource.removeSupportingAnnotation(EclipseLinkJPA.WRITE_TRANSFORMER)	;
		assertNull(attributeResource.getSupportingAnnotation(EclipseLinkJPA.WRITE_TRANSFORMER));
		
		attributeResource.addSupportingAnnotation(EclipseLinkJPA.WRITE_TRANSFORMER);
		assertNotNull(attributeResource.getSupportingAnnotation(EclipseLinkJPA.WRITE_TRANSFORMER));
	}

	public void testGetTransformerClass() throws Exception {
		ICompilationUnit cu = this.createTestWriteTransformerWithTransformerClass();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		WriteTransformerAnnotation writeTransformer = (WriteTransformerAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.WRITE_TRANSFORMER);
		assertEquals("Foo", writeTransformer.getTransformerClass());
	}

	public void testSetTransformerClass() throws Exception {
		ICompilationUnit cu = this.createTestWriteTransformerWithTransformerClass();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		WriteTransformerAnnotation writeTransformer = (WriteTransformerAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.WRITE_TRANSFORMER);
		assertEquals("Foo", writeTransformer.getTransformerClass());
		
		writeTransformer.setTransformerClass("Bar");
		assertEquals("Bar", writeTransformer.getTransformerClass());
		
		assertSourceContains("@WriteTransformer(transformerClass = Bar.class)", cu);
	}
	
	public void testSetTransformerClassNull() throws Exception {
		ICompilationUnit cu = this.createTestWriteTransformerWithTransformerClass();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		WriteTransformerAnnotation writeTransformer = (WriteTransformerAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.WRITE_TRANSFORMER);
		assertEquals("Foo", writeTransformer.getTransformerClass());
		
		writeTransformer.setTransformerClass(null);
		assertNull(writeTransformer.getTransformerClass());
		
		assertSourceContains("@WriteTransformer", cu);
		assertSourceDoesNotContain("transformerClass", cu);
	}
	
	public void testGetMethod() throws Exception {
		ICompilationUnit cu = this.createTestWriteTransformerWithMethod();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		WriteTransformerAnnotation writeTransformer = (WriteTransformerAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.WRITE_TRANSFORMER);
		assertEquals("transformerMethod", writeTransformer.getMethod());
	}

	public void testSetMethod() throws Exception {
		ICompilationUnit cu = this.createTestWriteTransformerWithMethod();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		WriteTransformerAnnotation writeTransformer = (WriteTransformerAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.WRITE_TRANSFORMER);
		assertEquals("transformerMethod", writeTransformer.getMethod());
		
		writeTransformer.setMethod("foo");
		assertEquals("foo", writeTransformer.getMethod());
		
		assertSourceContains("@WriteTransformer(method = \"foo\")", cu);
	}
	
	public void testSetMethodNull() throws Exception {
		ICompilationUnit cu = this.createTestWriteTransformerWithMethod();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		WriteTransformerAnnotation writeTransformer = (WriteTransformerAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.WRITE_TRANSFORMER);
		assertEquals("transformerMethod", writeTransformer.getMethod());
		
		writeTransformer.setMethod(null);
		assertNull(writeTransformer.getMethod());
		
		assertSourceContains("@WriteTransformer", cu);
		assertSourceDoesNotContain("method", cu);
	}
	
	
	public void testGetColumn() throws Exception {
		ICompilationUnit cu = this.createTestWriteTransformerWithColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		WriteTransformerAnnotation writeTransformer = (WriteTransformerAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.WRITE_TRANSFORMER);
		assertNotNull(writeTransformer.getColumn());
		assertEquals("FOO", writeTransformer.getColumn().getName());
	}

	public void testAddColumn() throws Exception {
		ICompilationUnit cu = this.createTestWriteTransformerWithMethod();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		WriteTransformerAnnotation writeTransformer = (WriteTransformerAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.WRITE_TRANSFORMER);
		assertNull(writeTransformer.getColumn());
		
		writeTransformer.addColumn();
		
		assertNotNull(writeTransformer.getColumn());		
		assertSourceContains("@WriteTransformer(method = \"transformerMethod\", column = @Column)", cu);
		
		writeTransformer.getColumn().setName("BAR");
		assertSourceContains("@WriteTransformer(method = \"transformerMethod\", column = @Column(name = \"BAR\"))", cu);
		
	}
	
	public void testRemoveColumn() throws Exception {
		ICompilationUnit cu = this.createTestWriteTransformerWithColumn();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		WriteTransformerAnnotation writeTransformer = (WriteTransformerAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.WRITE_TRANSFORMER);
		assertNotNull(writeTransformer.getColumn());
		assertEquals("FOO", writeTransformer.getColumn().getName());
		
		writeTransformer.removeColumn();
		assertNull(writeTransformer.getColumn());
		
		assertSourceContains("@WriteTransformer", cu);
		assertSourceDoesNotContain("column", cu);
	}
	
	public void getNonNullColumn() throws Exception {

	}
}

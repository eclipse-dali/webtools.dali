/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkWriteTransformerAnnotation;

@SuppressWarnings("nls")
public class WriteTransformerAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public WriteTransformerAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestWriteTransformer() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.WRITE_TRANSFORMER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@WriteTransformer");
			}
		});
	}
	
	private ICompilationUnit createTestWriteTransformerWithTransformerClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.WRITE_TRANSFORMER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@WriteTransformer(transformerClass = Foo.class)");
			}
		});
	}
	
	private ICompilationUnit createTestWriteTransformerWithMethod() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.WRITE_TRANSFORMER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@WriteTransformer(method = \"transformerMethod\")");
			}
		});
	}
	
	private ICompilationUnit createTestWriteTransformerWithColumn() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.WRITE_TRANSFORMER, JPA.COLUMN);
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
		
		assertNotNull(attributeResource.getAnnotation(EclipseLink.WRITE_TRANSFORMER));
		
		attributeResource.removeAnnotation(EclipseLink.WRITE_TRANSFORMER)	;
		assertNull(attributeResource.getAnnotation(EclipseLink.WRITE_TRANSFORMER));
		
		attributeResource.addAnnotation(EclipseLink.WRITE_TRANSFORMER);
		assertNotNull(attributeResource.getAnnotation(EclipseLink.WRITE_TRANSFORMER));
	}

	public void testGetTransformerClass() throws Exception {
		ICompilationUnit cu = this.createTestWriteTransformerWithTransformerClass();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkWriteTransformerAnnotation writeTransformer = (EclipseLinkWriteTransformerAnnotation) attributeResource.getAnnotation(EclipseLink.WRITE_TRANSFORMER);
		assertEquals("Foo", writeTransformer.getTransformerClass());
	}

	public void testSetTransformerClass() throws Exception {
		ICompilationUnit cu = this.createTestWriteTransformerWithTransformerClass();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkWriteTransformerAnnotation writeTransformer = (EclipseLinkWriteTransformerAnnotation) attributeResource.getAnnotation(EclipseLink.WRITE_TRANSFORMER);
		assertEquals("Foo", writeTransformer.getTransformerClass());
		
		writeTransformer.setTransformerClass("Bar");
		assertEquals("Bar", writeTransformer.getTransformerClass());
		
		assertSourceContains("@WriteTransformer(transformerClass = Bar.class)", cu);
	}
	
	public void testSetTransformerClassNull() throws Exception {
		ICompilationUnit cu = this.createTestWriteTransformerWithTransformerClass();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkWriteTransformerAnnotation writeTransformer = (EclipseLinkWriteTransformerAnnotation) attributeResource.getAnnotation(EclipseLink.WRITE_TRANSFORMER);
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
		
		EclipseLinkWriteTransformerAnnotation writeTransformer = (EclipseLinkWriteTransformerAnnotation) attributeResource.getAnnotation(EclipseLink.WRITE_TRANSFORMER);
		assertEquals("transformerMethod", writeTransformer.getMethod());
	}

	public void testSetMethod() throws Exception {
		ICompilationUnit cu = this.createTestWriteTransformerWithMethod();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkWriteTransformerAnnotation writeTransformer = (EclipseLinkWriteTransformerAnnotation) attributeResource.getAnnotation(EclipseLink.WRITE_TRANSFORMER);
		assertEquals("transformerMethod", writeTransformer.getMethod());
		
		writeTransformer.setMethod("foo");
		assertEquals("foo", writeTransformer.getMethod());
		
		assertSourceContains("@WriteTransformer(method = \"foo\")", cu);
	}
	
	public void testSetMethodNull() throws Exception {
		ICompilationUnit cu = this.createTestWriteTransformerWithMethod();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkWriteTransformerAnnotation writeTransformer = (EclipseLinkWriteTransformerAnnotation) attributeResource.getAnnotation(EclipseLink.WRITE_TRANSFORMER);
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
		
		EclipseLinkWriteTransformerAnnotation writeTransformer = (EclipseLinkWriteTransformerAnnotation) attributeResource.getAnnotation(EclipseLink.WRITE_TRANSFORMER);
		assertNotNull(writeTransformer.getColumn());
		assertEquals("FOO", writeTransformer.getColumn().getName());
	}

	public void testAddColumn() throws Exception {
		ICompilationUnit cu = this.createTestWriteTransformerWithMethod();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkWriteTransformerAnnotation writeTransformer = (EclipseLinkWriteTransformerAnnotation) attributeResource.getAnnotation(EclipseLink.WRITE_TRANSFORMER);
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
		
		EclipseLinkWriteTransformerAnnotation writeTransformer = (EclipseLinkWriteTransformerAnnotation) attributeResource.getAnnotation(EclipseLink.WRITE_TRANSFORMER);
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

/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkReadTransformerAnnotation;

@SuppressWarnings("nls")
public class ReadTransformerAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public ReadTransformerAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestReadTransformer() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.READ_TRANSFORMER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ReadTransformer");
			}
		});
	}
	
	private ICompilationUnit createTestReadTransformerWithTransformerClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.READ_TRANSFORMER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ReadTransformer(transformerClass=Foo.class)");
			}
		});
	}
	
	private ICompilationUnit createTestReadTransformerWithMethod() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.READ_TRANSFORMER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ReadTransformer(method=\"transformerMethod\")");
			}
		});
	}

	public void testReadTransformerAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestReadTransformer();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		assertNotNull(resourceField.getAnnotation(EclipseLink.READ_TRANSFORMER));
		
		resourceField.removeAnnotation(EclipseLink.READ_TRANSFORMER)	;
		assertNull(resourceField.getAnnotation(EclipseLink.READ_TRANSFORMER));
		
		resourceField.addAnnotation(EclipseLink.READ_TRANSFORMER);
		assertNotNull(resourceField.getAnnotation(EclipseLink.READ_TRANSFORMER));
	}

	public void testGetTransformerClass() throws Exception {
		ICompilationUnit cu = this.createTestReadTransformerWithTransformerClass();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkReadTransformerAnnotation readTransformer = (EclipseLinkReadTransformerAnnotation) resourceField.getAnnotation(EclipseLink.READ_TRANSFORMER);
		assertEquals("Foo", readTransformer.getTransformerClass());
	}

	public void testSetTransformerClass() throws Exception {
		ICompilationUnit cu = this.createTestReadTransformerWithTransformerClass();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkReadTransformerAnnotation readTransformer = (EclipseLinkReadTransformerAnnotation) resourceField.getAnnotation(EclipseLink.READ_TRANSFORMER);
		assertEquals("Foo", readTransformer.getTransformerClass());
		
		readTransformer.setTransformerClass("Bar");
		assertEquals("Bar", readTransformer.getTransformerClass());
		
		assertSourceContains("@ReadTransformer(transformerClass=Bar.class)", cu);
	}
	
	public void testSetTransformerClassNull() throws Exception {
		ICompilationUnit cu = this.createTestReadTransformerWithTransformerClass();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkReadTransformerAnnotation readTransformer = (EclipseLinkReadTransformerAnnotation) resourceField.getAnnotation(EclipseLink.READ_TRANSFORMER);
		assertEquals("Foo", readTransformer.getTransformerClass());
		
		readTransformer.setTransformerClass(null);
		assertNull(readTransformer.getTransformerClass());
		
		assertSourceContains("@ReadTransformer", cu);
		assertSourceDoesNotContain("transformerClass", cu);
	}
	
	public void testGetMethod() throws Exception {
		ICompilationUnit cu = this.createTestReadTransformerWithMethod();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkReadTransformerAnnotation readTransformer = (EclipseLinkReadTransformerAnnotation) resourceField.getAnnotation(EclipseLink.READ_TRANSFORMER);
		assertEquals("transformerMethod", readTransformer.getMethod());
	}

	public void testSetMethod() throws Exception {
		ICompilationUnit cu = this.createTestReadTransformerWithMethod();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkReadTransformerAnnotation readTransformer = (EclipseLinkReadTransformerAnnotation) resourceField.getAnnotation(EclipseLink.READ_TRANSFORMER);
		assertEquals("transformerMethod", readTransformer.getMethod());
		
		readTransformer.setMethod("foo");
		assertEquals("foo", readTransformer.getMethod());
		
		assertSourceContains("@ReadTransformer(method=\"foo\")", cu);
	}
	
	public void testSetMethodNull() throws Exception {
		ICompilationUnit cu = this.createTestReadTransformerWithMethod();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkReadTransformerAnnotation readTransformer = (EclipseLinkReadTransformerAnnotation) resourceField.getAnnotation(EclipseLink.READ_TRANSFORMER);
		assertEquals("transformerMethod", readTransformer.getMethod());
		
		readTransformer.setMethod(null);
		assertNull(readTransformer.getMethod());
		
		assertSourceContains("@ReadTransformer", cu);
		assertSourceDoesNotContain("method", cu);
	}
}

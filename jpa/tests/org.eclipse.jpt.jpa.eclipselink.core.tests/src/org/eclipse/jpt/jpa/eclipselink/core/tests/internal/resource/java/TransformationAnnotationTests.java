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
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.resource.java.FetchType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkTransformationAnnotation;

@SuppressWarnings("nls")
public class TransformationAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public TransformationAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestTransformation() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.TRANSFORMATION);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Transformation");
			}
		});
	}
	
	private ICompilationUnit createTestTransformationWithOptional() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.TRANSFORMATION);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Transformation(optional = true)");
			}
		});
	}
	
	private ICompilationUnit createTestTransformationWithFetch() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.TRANSFORMATION, JPA.FETCH_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Transformation(fetch = FetchType.EAGER)");
			}
		});
	}

	public void testTransformationAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestTransformation();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		assertNotNull(resourceField.getAnnotation(EclipseLink.TRANSFORMATION));
		
		resourceField.setPrimaryAnnotation(null, EmptyIterable.<String>instance());
		assertNull(resourceField.getAnnotation(EclipseLink.TRANSFORMATION));
		
		resourceField.setPrimaryAnnotation(EclipseLink.TRANSFORMATION, EmptyIterable.<String>instance());
		assertNotNull(resourceField.getAnnotation(EclipseLink.TRANSFORMATION));
	}

	public void testGetOptional() throws Exception {
		ICompilationUnit cu = this.createTestTransformationWithOptional();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkTransformationAnnotation transformation = (EclipseLinkTransformationAnnotation) resourceField.getAnnotation(EclipseLink.TRANSFORMATION);
		assertEquals(Boolean.TRUE, transformation.getOptional());
	}

	public void testSetOptional() throws Exception {
		ICompilationUnit cu = this.createTestTransformationWithOptional();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkTransformationAnnotation transformation = (EclipseLinkTransformationAnnotation) resourceField.getAnnotation(EclipseLink.TRANSFORMATION);
		assertEquals(Boolean.TRUE, transformation.getOptional());
		
		transformation.setOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, transformation.getOptional());
		
		assertSourceContains("@Transformation(optional = false)", cu);
	}
	
	public void testSetOptionalNull() throws Exception {
		ICompilationUnit cu = this.createTestTransformationWithOptional();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkTransformationAnnotation transformation = (EclipseLinkTransformationAnnotation) resourceField.getAnnotation(EclipseLink.TRANSFORMATION);
		assertEquals(Boolean.TRUE, transformation.getOptional());
		
		transformation.setOptional(null);
		assertNull(transformation.getOptional());
		
		assertSourceContains("@Transformation", cu);
		assertSourceDoesNotContain("optional", cu);
	}
	
	public void testGetFetch() throws Exception {
		ICompilationUnit cu = this.createTestTransformationWithFetch();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkTransformationAnnotation transformation = (EclipseLinkTransformationAnnotation) resourceField.getAnnotation(EclipseLink.TRANSFORMATION);
		assertEquals(FetchType.EAGER, transformation.getFetch());
	}

	public void testSetFetch() throws Exception {
		ICompilationUnit cu = this.createTestTransformationWithFetch();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkTransformationAnnotation transformation = (EclipseLinkTransformationAnnotation) resourceField.getAnnotation(EclipseLink.TRANSFORMATION);
		assertEquals(FetchType.EAGER, transformation.getFetch());
		
		transformation.setFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, transformation.getFetch());
		
		assertSourceContains("@Transformation(fetch = LAZY)", cu);
	}
	
	public void testSetFetchNull() throws Exception {
		ICompilationUnit cu = this.createTestTransformationWithFetch();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkTransformationAnnotation transformation = (EclipseLinkTransformationAnnotation) resourceField.getAnnotation(EclipseLink.TRANSFORMATION);
		assertEquals(FetchType.EAGER, transformation.getFetch());
		
		transformation.setFetch(null);
		assertNull(transformation.getFetch());
		
		assertSourceContains("@Transformation", cu);
		assertSourceDoesNotContain("fetch", cu);
	}
}

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
import org.eclipse.jpt.core.resource.java.FetchType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkTransformationAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class TransformationAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public TransformationAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestTransformation() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.TRANSFORMATION);
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
				return new ArrayIterator<String>(EclipseLinkJPA.TRANSFORMATION);
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
				return new ArrayIterator<String>(EclipseLinkJPA.TRANSFORMATION, JPA.FETCH_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Transformation(fetch = FetchType.EAGER)");
			}
		});
	}

	public void testTransformationAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestTransformation();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertNotNull(attributeResource.getAnnotation(EclipseLinkJPA.TRANSFORMATION));
		
		attributeResource.setPrimaryAnnotation(null, new String[0]);		
		assertNull(attributeResource.getAnnotation(EclipseLinkJPA.TRANSFORMATION));
		
		attributeResource.setPrimaryAnnotation(EclipseLinkJPA.TRANSFORMATION, new String[0]);
		assertNotNull(attributeResource.getAnnotation(EclipseLinkJPA.TRANSFORMATION));
	}

	public void testGetOptional() throws Exception {
		ICompilationUnit cu = this.createTestTransformationWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkTransformationAnnotation transformation = (EclipseLinkTransformationAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.TRANSFORMATION);
		assertEquals(Boolean.TRUE, transformation.getOptional());
	}

	public void testSetOptional() throws Exception {
		ICompilationUnit cu = this.createTestTransformationWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkTransformationAnnotation transformation = (EclipseLinkTransformationAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.TRANSFORMATION);
		assertEquals(Boolean.TRUE, transformation.getOptional());
		
		transformation.setOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, transformation.getOptional());
		
		assertSourceContains("@Transformation(optional = false)", cu);
	}
	
	public void testSetOptionalNull() throws Exception {
		ICompilationUnit cu = this.createTestTransformationWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkTransformationAnnotation transformation = (EclipseLinkTransformationAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.TRANSFORMATION);
		assertEquals(Boolean.TRUE, transformation.getOptional());
		
		transformation.setOptional(null);
		assertNull(transformation.getOptional());
		
		assertSourceContains("@Transformation", cu);
		assertSourceDoesNotContain("optional", cu);
	}
	
	public void testGetFetch() throws Exception {
		ICompilationUnit cu = this.createTestTransformationWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkTransformationAnnotation transformation = (EclipseLinkTransformationAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.TRANSFORMATION);
		assertEquals(FetchType.EAGER, transformation.getFetch());
	}

	public void testSetFetch() throws Exception {
		ICompilationUnit cu = this.createTestTransformationWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkTransformationAnnotation transformation = (EclipseLinkTransformationAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.TRANSFORMATION);
		assertEquals(FetchType.EAGER, transformation.getFetch());
		
		transformation.setFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, transformation.getFetch());
		
		assertSourceContains("@Transformation(fetch = LAZY)", cu);
	}
	
	public void testSetFetchNull() throws Exception {
		ICompilationUnit cu = this.createTestTransformationWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkTransformationAnnotation transformation = (EclipseLinkTransformationAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.TRANSFORMATION);
		assertEquals(FetchType.EAGER, transformation.getFetch());
		
		transformation.setFetch(null);
		assertNull(transformation.getFetch());
		
		assertSourceContains("@Transformation", cu);
		assertSourceDoesNotContain("fetch", cu);
	}
}

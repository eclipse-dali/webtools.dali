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
import org.eclipse.jpt.eclipselink.core.resource.java.TransformationAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class TransformationAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public TransformationAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestTransformation() throws Exception {
		this.createAnnotationAndMembers("Transformation", "boolean optional() default true;");
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
		this.createAnnotationAndMembers("Transformation", "boolean optional() default true;");
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
		this.createAnnotationAndMembers("Transformation", "boolean optional() default true; FetchType fetch() default FetchType.EAGER;");
		this.createEnumAndMembers(JPA.PACKAGE, "FetchType", "EAGER, LAZY");
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
		
		assertNotNull(attributeResource.getMappingAnnotation(EclipseLinkJPA.TRANSFORMATION));
		
		attributeResource.setMappingAnnotation(null);		
		assertNull(attributeResource.getMappingAnnotation(EclipseLinkJPA.TRANSFORMATION));
		
		attributeResource.setMappingAnnotation(EclipseLinkJPA.TRANSFORMATION);
		assertNotNull(attributeResource.getMappingAnnotation(EclipseLinkJPA.TRANSFORMATION));
	}

	public void testGetOptional() throws Exception {
		ICompilationUnit cu = this.createTestTransformationWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TransformationAnnotation transformation = (TransformationAnnotation) attributeResource.getMappingAnnotation(EclipseLinkJPA.TRANSFORMATION);
		assertEquals(Boolean.TRUE, transformation.getOptional());
	}

	public void testSetOptional() throws Exception {
		ICompilationUnit cu = this.createTestTransformationWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TransformationAnnotation transformation = (TransformationAnnotation) attributeResource.getMappingAnnotation(EclipseLinkJPA.TRANSFORMATION);
		assertEquals(Boolean.TRUE, transformation.getOptional());
		
		transformation.setOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, transformation.getOptional());
		
		assertSourceContains("@Transformation(optional = false)", cu);
	}
	
	public void testSetOptionalNull() throws Exception {
		ICompilationUnit cu = this.createTestTransformationWithOptional();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TransformationAnnotation transformation = (TransformationAnnotation) attributeResource.getMappingAnnotation(EclipseLinkJPA.TRANSFORMATION);
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
		
		TransformationAnnotation transformation = (TransformationAnnotation) attributeResource.getMappingAnnotation(EclipseLinkJPA.TRANSFORMATION);
		assertEquals(FetchType.EAGER, transformation.getFetch());
	}

	public void testSetFetch() throws Exception {
		ICompilationUnit cu = this.createTestTransformationWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TransformationAnnotation transformation = (TransformationAnnotation) attributeResource.getMappingAnnotation(EclipseLinkJPA.TRANSFORMATION);
		assertEquals(FetchType.EAGER, transformation.getFetch());
		
		transformation.setFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, transformation.getFetch());
		
		assertSourceContains("@Transformation(fetch = LAZY)", cu);
	}
	
	public void testSetFetchNull() throws Exception {
		ICompilationUnit cu = this.createTestTransformationWithFetch();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TransformationAnnotation transformation = (TransformationAnnotation) attributeResource.getMappingAnnotation(EclipseLinkJPA.TRANSFORMATION);
		assertEquals(FetchType.EAGER, transformation.getFetch());
		
		transformation.setFetch(null);
		assertNull(transformation.getFetch());
		
		assertSourceContains("@Transformation", cu);
		assertSourceDoesNotContain("fetch", cu);
	}
}

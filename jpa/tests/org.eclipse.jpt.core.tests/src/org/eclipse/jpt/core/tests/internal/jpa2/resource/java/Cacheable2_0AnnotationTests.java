/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.core.jpa2.resource.java.Cacheable2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

@SuppressWarnings("nls")
public class Cacheable2_0AnnotationTests extends JavaResourceModel2_0TestCase {
	
	public Cacheable2_0AnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestCacheable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.CACHEABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cacheable");
			}
		});
	}
	
	private ICompilationUnit createTestCacheableWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA2_0.CACHEABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cacheable(value=true)");
			}
		});
	}
	

	
	public void testCacheableAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestCacheable();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		assertNotNull(typeResource.getAnnotation(JPA2_0.CACHEABLE));
		
		typeResource.removeAnnotation(JPA2_0.CACHEABLE);		
		assertNull(typeResource.getAnnotation(JPA2_0.CACHEABLE));
		
		typeResource.addAnnotation(JPA2_0.CACHEABLE);
		assertNotNull(typeResource.getAnnotation(JPA2_0.CACHEABLE));
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestCacheableWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		Cacheable2_0Annotation cacheableAnnotation = (Cacheable2_0Annotation) typeResource.getAnnotation(JPA2_0.CACHEABLE);
		assertEquals(Boolean.TRUE, cacheableAnnotation.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestCacheableWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		Cacheable2_0Annotation cacheableAnnotation = (Cacheable2_0Annotation) typeResource.getAnnotation(JPA2_0.CACHEABLE);
		assertEquals(Boolean.TRUE, cacheableAnnotation.getValue());
		
		cacheableAnnotation.setValue(Boolean.FALSE);
		assertEquals(Boolean.FALSE, cacheableAnnotation.getValue());
		
		assertSourceContains("@Cacheable(value=false)", cu);
		
		cacheableAnnotation.setValue(null);
		cacheableAnnotation.setValue(Boolean.FALSE);
		assertSourceContains("@Cacheable(false)", cu);
	}
	
	public void testSetValueNull() throws Exception {
		ICompilationUnit cu = this.createTestCacheableWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		Cacheable2_0Annotation cacheableAnnotation = (Cacheable2_0Annotation) typeResource.getAnnotation(JPA2_0.CACHEABLE);
		assertEquals(Boolean.TRUE, cacheableAnnotation.getValue());
		
		cacheableAnnotation.setValue(null);
		assertNull(cacheableAnnotation.getValue());
		
		assertSourceContains("@Cacheable", cu);
		assertSourceDoesNotContain("value", cu);
	}

}

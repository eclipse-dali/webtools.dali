/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.CacheableAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;

@SuppressWarnings("nls")
public class Cacheable2_0AnnotationTests extends JavaResourceModel2_0TestCase {
	
	public Cacheable2_0AnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestCacheable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_0.CACHEABLE);
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
				return IteratorTools.iterator(JPA2_0.CACHEABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cacheable(value=true)");
			}
		});
	}
	

	
	public void testCacheableAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestCacheable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		assertNotNull(resourceType.getAnnotation(JPA2_0.CACHEABLE));
		
		resourceType.removeAnnotation(JPA2_0.CACHEABLE);		
		assertNull(resourceType.getAnnotation(JPA2_0.CACHEABLE));
		
		resourceType.addAnnotation(JPA2_0.CACHEABLE);
		assertNotNull(resourceType.getAnnotation(JPA2_0.CACHEABLE));
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestCacheableWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		CacheableAnnotation2_0 cacheableAnnotation = (CacheableAnnotation2_0) resourceType.getAnnotation(JPA2_0.CACHEABLE);
		assertEquals(Boolean.TRUE, cacheableAnnotation.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestCacheableWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		CacheableAnnotation2_0 cacheableAnnotation = (CacheableAnnotation2_0) resourceType.getAnnotation(JPA2_0.CACHEABLE);
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
		JavaResourceType resourceType = buildJavaResourceType(cu); 
	
		CacheableAnnotation2_0 cacheableAnnotation = (CacheableAnnotation2_0) resourceType.getAnnotation(JPA2_0.CACHEABLE);
		assertEquals(Boolean.TRUE, cacheableAnnotation.getValue());
		
		cacheableAnnotation.setValue(null);
		assertNull(cacheableAnnotation.getValue());
		
		assertSourceContains("@Cacheable", cu);
		assertSourceDoesNotContain("value", cu);
	}

}

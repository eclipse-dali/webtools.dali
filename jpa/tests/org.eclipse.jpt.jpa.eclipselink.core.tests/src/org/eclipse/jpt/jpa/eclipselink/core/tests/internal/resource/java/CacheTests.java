/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheCoordinationType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkCacheAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkTimeOfDayAnnotation;

@SuppressWarnings("nls")
public class CacheTests extends EclipseLinkJavaResourceModelTestCase {

	public CacheTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestCache() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CACHE, EclipseLink.CACHE_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache");
			}
		});
	}
	
	private ICompilationUnit createTestCacheWithCacheType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CACHE, EclipseLink.CACHE_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(type = CacheType.SOFT)");
			}
		});
	}
	
	private ICompilationUnit createTestCacheWithSize() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CACHE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(size = 50)");
			}
		});
	}
	
	private ICompilationUnit createTestCacheWithExpiry() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CACHE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(expiry = 50)");
			}
		});
	}
	
	private ICompilationUnit createTestCacheWithExpiryTimeOfDay() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CACHE, EclipseLink.TIME_OF_DAY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(expiryTimeOfDay = @TimeOfDay)");
			}
		});
	}

	private ICompilationUnit createTestCacheWithShared() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CACHE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(shared=true)");
			}
		});
	}

	private ICompilationUnit createTestCacheWithAlwaysRefresh() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CACHE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(alwaysRefresh=true)");
			}
		});
	}
	
	private ICompilationUnit createTestCacheWithRefreshOnlyIfNewer() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CACHE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(refreshOnlyIfNewer=true)");
			}
		});
	}
	
	private ICompilationUnit createTestCacheWithDisableHits() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CACHE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(disableHits=true)");
			}
		});
	}
	
	private ICompilationUnit createTestExistenceCheckingWithCoordinationType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CACHE, EclipseLink.CACHE_COORDINATION_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(coordinationType = CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS)");
			}
		});
	}
	
	public void testCache() throws Exception {
		ICompilationUnit cu = this.createTestCache();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertNotNull(cache);
	}

	public void testGetType() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithCacheType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(CacheType.SOFT, cache.getType());
	}

	public void testSetType() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithCacheType();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(CacheType.SOFT, cache.getType());
		
		cache.setType(CacheType.WEAK);
		assertEquals(CacheType.WEAK, cache.getType());
		
		assertSourceContains("@Cache(type = WEAK)", cu);
		
		cache.setType(null);
		assertNull(cache.getType());
		
		assertSourceDoesNotContain("(type = WEAK)", cu);
		assertSourceDoesNotContain("@Cache(", cu);
	}
	
	public void testGetSize() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithSize();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Integer.valueOf(50), cache.getSize());
	}

	public void testSetSize() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithSize();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Integer.valueOf(50), cache.getSize());
		
		cache.setSize(Integer.valueOf(80));
		assertEquals(Integer.valueOf(80), cache.getSize());
		
		assertSourceContains("@Cache(size = 80)", cu);
		
		cache.setSize(null);
		assertNull(cache.getSize());
		
		assertSourceDoesNotContain("(size = 80)", cu);
		assertSourceDoesNotContain("@Cache(", cu);
	}
	
	public void testGetShared() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithShared();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Boolean.TRUE, cache.getShared());
	}

	public void testSetShared() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithShared();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Boolean.TRUE, cache.getShared());
		
		cache.setShared(Boolean.FALSE);
		assertEquals(Boolean.FALSE, cache.getShared());
		
		assertSourceContains("@Cache(shared=false)", cu);
	}
	
	public void testSetSharedNull() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithShared();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Boolean.TRUE, cache.getShared());
		
		cache.setShared(null);
	
		assertSourceDoesNotContain("@Cache(", cu);
	}
	
	public void testGetExpiry() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithExpiry();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Integer.valueOf(50), cache.getExpiry());
	}

	public void testSetExpiry() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithExpiry();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Integer.valueOf(50), cache.getExpiry());
		
		cache.setExpiry(Integer.valueOf(80));
		assertEquals(Integer.valueOf(80), cache.getExpiry());
		
		assertSourceContains("@Cache(expiry = 80)", cu);
		
		cache.setExpiry(null);
		assertNull(cache.getExpiry());
		
		assertSourceDoesNotContain("(expiry = 80)", cu);
		assertSourceDoesNotContain("@Cache(", cu);
	}
	
	public void testGetExpiryTimeOfDay() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithExpiryTimeOfDay();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		EclipseLinkTimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
		assertNotNull(timeOfDay);
	}

	public void testAddExpiryTimeOfDay() throws Exception {
		ICompilationUnit cu = this.createTestCache();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		cache.addExpiryTimeOfDay();
		
		assertNotNull(cache.getExpiryTimeOfDay());
		
		assertSourceContains("@Cache(expiryTimeOfDay = @TimeOfDay)", cu);
	}
	
	public void testRemoveExpiryTimeOfDay() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithExpiryTimeOfDay();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		
		assertNotNull(cache.getExpiryTimeOfDay());

		cache.removeExpiryTimeOfDay();
		assertNull(cache.getExpiryTimeOfDay());
		assertSourceDoesNotContain("@Cache(expiryTimeOfDay = @TimeOfDay)", cu);
	}
	
	public void testGetAlwaysRefresh() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithAlwaysRefresh();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Boolean.TRUE, cache.getAlwaysRefresh());
	}
	
	public void testSetAlwaysRefresh() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithAlwaysRefresh();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Boolean.TRUE, cache.getAlwaysRefresh());
		
		cache.setAlwaysRefresh(Boolean.FALSE);
		assertEquals(Boolean.FALSE, cache.getAlwaysRefresh());
		
		assertSourceContains("@Cache(alwaysRefresh=false)", cu);		
	}
	
	public void testSetAlwaysRefreshNull() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithAlwaysRefresh();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Boolean.TRUE, cache.getAlwaysRefresh());
		
		cache.setAlwaysRefresh(null);
		cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertNull(cache.getAlwaysRefresh());
		
		assertSourceContains("@Cache", cu);
		assertSourceDoesNotContain("@Cache(", cu);
	}

	public void testGetRefreshOnlyIfNewer() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithRefreshOnlyIfNewer();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Boolean.TRUE, cache.getRefreshOnlyIfNewer());		
	}
	
	public void testSetRefreshOnlyIfNewer() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithRefreshOnlyIfNewer();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Boolean.TRUE, cache.getRefreshOnlyIfNewer());
		
		cache.setRefreshOnlyIfNewer(Boolean.FALSE);
		assertEquals(Boolean.FALSE, cache.getRefreshOnlyIfNewer());
		
		assertSourceContains("@Cache(refreshOnlyIfNewer=false)", cu);			
	}
	
	public void testSetRefreshOnlyIfNewerNull() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithRefreshOnlyIfNewer();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Boolean.TRUE, cache.getRefreshOnlyIfNewer());
		
		cache.setRefreshOnlyIfNewer(null);
		cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertNull(cache.getRefreshOnlyIfNewer());
		assertSourceContains("@Cache", cu);
		assertSourceDoesNotContain("@Cache(", cu);
	}

	public void testGetDisableHits() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithDisableHits();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Boolean.TRUE, cache.getDisableHits());				
	}
	
	public void testSetDisableHits() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithDisableHits();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Boolean.TRUE, cache.getDisableHits());
		
		cache.setDisableHits(Boolean.FALSE);
		assertEquals(Boolean.FALSE, cache.getDisableHits());
		
		assertSourceContains("@Cache(disableHits=false)", cu);			
	}
	
	public void testSetDisableHitsNull() throws Exception {
		ICompilationUnit cu = this.createTestCacheWithDisableHits();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Boolean.TRUE, cache.getDisableHits());
		
		cache.setDisableHits(null);
		cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertNull(cache.getDisableHits());
		assertSourceContains("@Cache", cu);
		assertSourceDoesNotContain("@Cache(", cu);
	}

	
	public void testGetCoordinationType() throws Exception {
		ICompilationUnit cu = this.createTestExistenceCheckingWithCoordinationType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, cache.getCoordinationType());
	}

	public void testSetCoordinationType() throws Exception {
		ICompilationUnit cu = this.createTestExistenceCheckingWithCoordinationType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, cache.getCoordinationType());
		
		cache.setCoordinationType(CacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES);
		assertEquals(CacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, cache.getCoordinationType());
		
		assertSourceContains("@Cache(coordinationType = SEND_NEW_OBJECTS_WITH_CHANGES)", cu);
		
		cache.setCoordinationType(null);
		assertNull(cache.getCoordinationType());
		
		assertSourceDoesNotContain("(coordinationType = SEND_NEW_OBJECTS_WITH_CHANGES)", cu);
		assertSourceDoesNotContain("@Cache(", cu);
	}
}

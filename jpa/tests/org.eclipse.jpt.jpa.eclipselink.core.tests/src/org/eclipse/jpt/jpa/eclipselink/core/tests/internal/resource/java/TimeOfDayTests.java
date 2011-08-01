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
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkCacheAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkTimeOfDayAnnotation;

@SuppressWarnings("nls")
public class TimeOfDayTests extends EclipseLinkJavaResourceModelTestCase {

	public TimeOfDayTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestTimeOfDay() throws Exception {
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

	private ICompilationUnit createTestTimeOfDayWithHour() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CACHE, EclipseLink.TIME_OF_DAY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(expiryTimeOfDay = @TimeOfDay(hour=5))");
			}
		});
	}
	
	private ICompilationUnit createTestTimeOfDayWithMinute() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CACHE, EclipseLink.TIME_OF_DAY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(expiryTimeOfDay = @TimeOfDay(minute=5))");
			}
		});
	}
	
	private ICompilationUnit createTestTimeOfDayWithSecond() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CACHE, EclipseLink.TIME_OF_DAY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(expiryTimeOfDay = @TimeOfDay(second=5))");
			}
		});
	}
	
	private ICompilationUnit createTestTimeOfDayWithMillisecond() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CACHE, EclipseLink.TIME_OF_DAY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(expiryTimeOfDay = @TimeOfDay(millisecond=5))");
			}
		});
	}
	
	public void testExpiryTimeOfDay() throws Exception {
		ICompilationUnit cu = this.createTestTimeOfDay();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		
		EclipseLinkTimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
		
		assertNotNull(timeOfDay);
	}
	
	public void testGetHour() throws Exception {
		ICompilationUnit cu = this.createTestTimeOfDayWithHour();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		EclipseLinkTimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
		assertEquals(Integer.valueOf(5), timeOfDay.getHour());
	}

	public void testSetHour() throws Exception {
		ICompilationUnit cu = this.createTestTimeOfDayWithHour();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		EclipseLinkTimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
		assertEquals(Integer.valueOf(5), timeOfDay.getHour());
		
		timeOfDay.setHour(Integer.valueOf(80));
		assertEquals(Integer.valueOf(80), timeOfDay.getHour());
		
		assertSourceContains("@TimeOfDay(hour=80)", cu);
		
		timeOfDay.setHour(null);
		assertNull(timeOfDay.getHour());
		
		assertSourceDoesNotContain("(hour=80)", cu);
		assertSourceContains("@TimeOfDay", cu);
	}
	
	public void testGetMinute() throws Exception {
		ICompilationUnit cu = this.createTestTimeOfDayWithMinute();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		EclipseLinkTimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
		assertEquals(Integer.valueOf(5), timeOfDay.getMinute());
	}

	public void testSetMinute() throws Exception {
		ICompilationUnit cu = this.createTestTimeOfDayWithMinute();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		EclipseLinkTimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
		assertEquals(Integer.valueOf(5), timeOfDay.getMinute());
		
		timeOfDay.setMinute(Integer.valueOf(80));
		assertEquals(Integer.valueOf(80), timeOfDay.getMinute());
		
		assertSourceContains("@TimeOfDay(minute=80)", cu);
		
		timeOfDay.setMinute(null);
		assertNull(timeOfDay.getMinute());
		
		assertSourceDoesNotContain("(minute=80)", cu);
		assertSourceContains("@TimeOfDay", cu);
	}

	public void testGetSecond() throws Exception {
		ICompilationUnit cu = this.createTestTimeOfDayWithSecond();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		EclipseLinkTimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
		assertEquals(Integer.valueOf(5), timeOfDay.getSecond());
	}

	public void testSetSecond() throws Exception {
		ICompilationUnit cu = this.createTestTimeOfDayWithSecond();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		EclipseLinkTimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
		assertEquals(Integer.valueOf(5), timeOfDay.getSecond());
		
		timeOfDay.setSecond(Integer.valueOf(80));
		assertEquals(Integer.valueOf(80), timeOfDay.getSecond());
		
		assertSourceContains("@TimeOfDay(second=80)", cu);
		
		timeOfDay.setSecond(null);
		assertNull(timeOfDay.getSecond());
		
		assertSourceDoesNotContain("(second=80)", cu);
		assertSourceContains("@TimeOfDay", cu);
	}
	
	public void testGetMillisecond() throws Exception {
		ICompilationUnit cu = this.createTestTimeOfDayWithMillisecond();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		EclipseLinkTimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
		assertEquals(Integer.valueOf(5), timeOfDay.getMillisecond());
	}

	public void testSetMillisecond() throws Exception {
		ICompilationUnit cu = this.createTestTimeOfDayWithMillisecond();
		JavaResourceType resourceType = buildJavaResourceType(cu);
		
		EclipseLinkCacheAnnotation cache = (EclipseLinkCacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		EclipseLinkTimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
		assertEquals(Integer.valueOf(5), timeOfDay.getMillisecond());
		
		timeOfDay.setMillisecond(Integer.valueOf(80));
		assertEquals(Integer.valueOf(80), timeOfDay.getMillisecond());
		
		assertSourceContains("@TimeOfDay(millisecond=80)", cu);
		
		timeOfDay.setMillisecond(null);
		assertNull(timeOfDay.getMillisecond());
		
		assertSourceDoesNotContain("(millisecond=80)", cu);
		assertSourceContains("@TimeOfDay", cu);
	}


}

/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.resource.java.CacheAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.TimeOfDayAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class TimeOfDayTests extends EclipseLinkJavaResourceModelTestCase {

	public TimeOfDayTests(String name) {
		super(name);
	}

	private void createTimeOfDayAnnotation() throws Exception {

		this.createAnnotationAndMembers("TimeOfDay", 
			"int hour() default 0; " +
			"int minute() default 0; " +
			"int second() default 0; " +
			"int millisecond() default 0;");
	}

	private void createCacheAnnotation() throws Exception {
		this.createAnnotationAndMembers("Cache", 
			"CacheType type() default SOFT_WEAK; " +
			"int size() default 100; " +
			"boolean shared() default true; " +
			"int expiry() default -1; " +
			"TimeOfDay expiryTimeOfDay() default @TimeOfDay(specified=false); " +
			"boolean alwaysRefresh() default false; " +
			"boolean refreshOnlyIfNewer() default false; " +
			"boolean disableHits() default false; " +
			"CacheCoordinationType coordinationType() default SEND_OBJECT_CHANGES;");
	}
	
	private ICompilationUnit createTestTimeOfDay() throws Exception {
		createCacheAnnotation();
		createTimeOfDayAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.CACHE, EclipseLinkJPA.TIME_OF_DAY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(expiryTimeOfDay = @TimeOfDay)");
			}
		});
	}

	private ICompilationUnit createTestTimeOfDayWithHour() throws Exception {
		createCacheAnnotation();
		createTimeOfDayAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.CACHE, EclipseLinkJPA.TIME_OF_DAY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(expiryTimeOfDay = @TimeOfDay(hour=5))");
			}
		});
	}
	
	private ICompilationUnit createTestTimeOfDayWithMinute() throws Exception {
		createCacheAnnotation();
		createTimeOfDayAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.CACHE, EclipseLinkJPA.TIME_OF_DAY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(expiryTimeOfDay = @TimeOfDay(minute=5))");
			}
		});
	}
	
	private ICompilationUnit createTestTimeOfDayWithSecond() throws Exception {
		createCacheAnnotation();
		createTimeOfDayAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.CACHE, EclipseLinkJPA.TIME_OF_DAY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(expiryTimeOfDay = @TimeOfDay(second=5))");
			}
		});
	}
	
	private ICompilationUnit createTestTimeOfDayWithMillisecond() throws Exception {
		createCacheAnnotation();
		createTimeOfDayAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.CACHE, EclipseLinkJPA.TIME_OF_DAY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Cache(expiryTimeOfDay = @TimeOfDay(millisecond=5))");
			}
		});
	}
	
	public void testExpiryTimeOfDay() throws Exception {
		ICompilationUnit cu = this.createTestTimeOfDay();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		
		CacheAnnotation cache = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		
		TimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
		
		assertNotNull(timeOfDay);
	}
	
	public void testGetHour() throws Exception {
		ICompilationUnit cu = this.createTestTimeOfDayWithHour();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		CacheAnnotation cache = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		TimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
		assertEquals(Integer.valueOf(5), timeOfDay.getHour());
	}

	public void testSetHour() throws Exception {
		ICompilationUnit cu = this.createTestTimeOfDayWithHour();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		CacheAnnotation cache = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		TimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		CacheAnnotation cache = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		TimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
		assertEquals(Integer.valueOf(5), timeOfDay.getMinute());
	}

	public void testSetMinute() throws Exception {
		ICompilationUnit cu = this.createTestTimeOfDayWithMinute();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		CacheAnnotation cache = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		TimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		CacheAnnotation cache = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		TimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
		assertEquals(Integer.valueOf(5), timeOfDay.getSecond());
	}

	public void testSetSecond() throws Exception {
		ICompilationUnit cu = this.createTestTimeOfDayWithSecond();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		CacheAnnotation cache = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		TimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
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
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		CacheAnnotation cache = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		TimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
		assertEquals(Integer.valueOf(5), timeOfDay.getMillisecond());
	}

	public void testSetMillisecond() throws Exception {
		ICompilationUnit cu = this.createTestTimeOfDayWithMillisecond();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		
		CacheAnnotation cache = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		TimeOfDayAnnotation timeOfDay = cache.getExpiryTimeOfDay();
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

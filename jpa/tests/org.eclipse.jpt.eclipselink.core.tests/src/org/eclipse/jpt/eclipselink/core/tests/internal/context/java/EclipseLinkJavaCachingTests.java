/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.CacheCoordinationType;
import org.eclipse.jpt.eclipselink.core.context.CacheType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkExpiryTimeOfDay;
import org.eclipse.jpt.eclipselink.core.context.ExistenceType;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaCaching;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaExpiryTimeOfDay;
import org.eclipse.jpt.eclipselink.core.resource.java.CacheAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.ExistenceCheckingAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class EclipseLinkJavaCachingTests extends EclipseLinkJavaContextModelTestCase
{	
	public EclipseLinkJavaCachingTests(String name) {
		super(name);
	}
	
	private void createCacheTypeEnum() throws Exception {
		this.createEnumAndMembers(ECLIPSELINK_ANNOTATIONS_PACKAGE_NAME, "CacheType", "SOFT_WEAK, HARD_WEAK, WEAK, SOFT, FULL, CACHE, NONE;");	
	}
	
	private void createCacheCoordinationTypeEnum() throws Exception {
		this.createEnumAndMembers(ECLIPSELINK_ANNOTATIONS_PACKAGE_NAME, "CacheCoordinationType", "SEND_OBJECT_CHANGES, INVALIDATE_CHANGED_OBJECTS, SEND_NEW_OBJECTS_WITH_CHANGES, NONE;");	
	}
	
	private void createExistenceTypeEnum() throws Exception {
		this.createEnumAndMembers(ECLIPSELINK_ANNOTATIONS_PACKAGE_NAME, "ExistenceType", "CHECK_CACHE, CHECK_DATABASE, ASSUME_EXISTENCE, ASSUME_NON_EXISTENCE;");	
	}
	
	private void createCacheAnnotation() throws Exception {
		createCacheTypeEnum();
		createCacheCoordinationTypeEnum();
		createTimeOfDayAnnotation();
		this.createAnnotationAndMembers(ECLIPSELINK_ANNOTATIONS_PACKAGE_NAME, "Cache", 
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
	
	private void createTimeOfDayAnnotation() throws Exception {
		this.createAnnotationAndMembers(ECLIPSELINK_ANNOTATIONS_PACKAGE_NAME, "TimeOfDay", 
			"int hour() default 0; " +
			"int minute() default 0; " +
			"int second() default 0; " +
			"int millisecond() default 0;");
	}

	private void createExistenceCheckingAnnotation() throws Exception {
		createExistenceTypeEnum();

		this.createAnnotationAndMembers(ECLIPSELINK_ANNOTATIONS_PACKAGE_NAME, "ExistenceChecking", 
			"ExistenceType value() default CHECK_CACHE;; " +
			"int size() default 100; " +
			"boolean shared() default true; " +
			"int expiry() default -1; " +
			"TimeOfDay expiryTimeOfDay() default @TimeOfDay(specified=false); " +
			"boolean alwaysRefresh() default false; " +
			"boolean refreshOnlyIfNewer() default false; " +
			"boolean disableHits() default false; " +
			"CacheCoordinationType coordinationType() default SEND_OBJECT_CHANGES;");
	}
	

	private ICompilationUnit createTestEntity() throws Exception {
		createCacheAnnotation();
		createExistenceCheckingAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}
	
	public void testSetSpecifiedShared() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(Boolean.TRUE, caching.getShared());
		
		caching.setSpecifiedShared(Boolean.FALSE);
		
		
		assertEquals(Boolean.FALSE, entity.getCaching().getSpecifiedShared());
		assertEquals(Boolean.FALSE, entity.getCaching().getShared());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		
		assertEquals(Boolean.FALSE, cacheAnnotation.getShared());		
	}
	
	public void testSetSpecifiedSharedFalseUnsetsOtherCacheSettings() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		caching.setSpecifiedType(CacheType.HARD_WEAK);
		caching.setSpecifiedSize(Integer.valueOf(500));
		caching.setSpecifiedAlwaysRefresh(Boolean.FALSE);
		caching.setSpecifiedRefreshOnlyIfNewer(Boolean.FALSE);
		caching.setSpecifiedDisableHits(Boolean.FALSE);
		caching.setSpecifiedCoordinationType(CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS);
		caching.setExistenceChecking(true);
		caching.setSpecifiedExistenceType(ExistenceType.CHECK_CACHE);
		caching.setExpiry(Integer.valueOf(8000));
		
		caching.setSpecifiedShared(Boolean.FALSE);
		
		assertEquals(null, caching.getSpecifiedType());
		assertEquals(null, caching.getSpecifiedSize());
		assertEquals(null, caching.getSpecifiedAlwaysRefresh());
		assertEquals(null, caching.getSpecifiedRefreshOnlyIfNewer());
		assertEquals(null, caching.getSpecifiedDisableHits());
		assertEquals(null, caching.getSpecifiedCoordinationType());
		assertEquals(null, caching.getExpiry());
		
		
		//existence checking is the only thing that isn't unset when shared is set to false
		assertTrue(caching.hasExistenceChecking());
		assertEquals(ExistenceType.CHECK_CACHE, caching.getSpecifiedExistenceType());
		
		caching.setSpecifiedShared(null);
		EclipseLinkExpiryTimeOfDay timeOfDayExpiry = caching.addExpiryTimeOfDay();
		timeOfDayExpiry.setHour(Integer.valueOf(5));
		
		caching.setSpecifiedShared(Boolean.FALSE);
		assertNull(caching.getExpiryTimeOfDay());
		
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		
		assertEquals(Boolean.FALSE, cacheAnnotation.getShared());
		assertNull(cacheAnnotation.getExpiryTimeOfDay());
	}
	
	public void testGetSpecifiedShared() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(Boolean.TRUE, caching.getShared());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.addAnnotation(EclipseLinkJPA.CACHE);
		cacheAnnotation.setShared(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, cacheAnnotation.getShared());				
		assertEquals(Boolean.FALSE, entity.getCaching().getSpecifiedShared());
		assertEquals(Boolean.FALSE, entity.getCaching().getShared());
	}
	
	public void testSetSpecifiedType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(CacheType.SOFT_WEAK, caching.getType());
		
		caching.setSpecifiedType(CacheType.HARD_WEAK);
		
		
		assertEquals(CacheType.HARD_WEAK, entity.getCaching().getSpecifiedType());
		assertEquals(CacheType.HARD_WEAK, entity.getCaching().getType());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.CacheType.HARD_WEAK, cacheAnnotation.getType());		

	
		//set specified type to the same as the default, verify it is not set to default
		caching.setSpecifiedType(CacheType.SOFT_WEAK);
		assertEquals(CacheType.SOFT_WEAK, caching.getSpecifiedType());
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.CacheType.SOFT_WEAK, cacheAnnotation.getType());		
		
		caching.setSpecifiedType(null);
		assertNull(caching.getSpecifiedType());
		assertEquals(CacheType.SOFT_WEAK, caching.getType());
	}
	
	public void testGetSpecifiedType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(CacheType.SOFT_WEAK, caching.getType());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.addAnnotation(EclipseLinkJPA.CACHE);
		cacheAnnotation.setType(org.eclipse.jpt.eclipselink.core.resource.java.CacheType.HARD_WEAK);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.CacheType.HARD_WEAK, cacheAnnotation.getType());				
		assertEquals(CacheType.HARD_WEAK, entity.getCaching().getSpecifiedType());
		assertEquals(CacheType.HARD_WEAK, entity.getCaching().getType());
	}

	public void testSetSpecifiedAlwaysRefresh() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(Boolean.FALSE, caching.getAlwaysRefresh());
		
		caching.setSpecifiedAlwaysRefresh(Boolean.FALSE);
		
		
		assertEquals(Boolean.FALSE, entity.getCaching().getSpecifiedAlwaysRefresh());
		assertEquals(Boolean.FALSE, entity.getCaching().getAlwaysRefresh());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		
		assertEquals(Boolean.FALSE, cacheAnnotation.getAlwaysRefresh());		
	}
	
	public void testGetSpecifiedAlwaysRefresh() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(Boolean.FALSE, caching.getAlwaysRefresh());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.addAnnotation(EclipseLinkJPA.CACHE);
		cacheAnnotation.setAlwaysRefresh(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, cacheAnnotation.getAlwaysRefresh());				
		assertEquals(Boolean.TRUE, entity.getCaching().getSpecifiedAlwaysRefresh());
		assertEquals(Boolean.TRUE, entity.getCaching().getAlwaysRefresh());
	}
	
	public void testSetSpecifiedRefreshOnlyIfNewer() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(Boolean.FALSE, caching.getRefreshOnlyIfNewer());
		
		caching.setSpecifiedRefreshOnlyIfNewer(Boolean.TRUE);
		
		
		assertEquals(Boolean.TRUE, entity.getCaching().getSpecifiedRefreshOnlyIfNewer());
		assertEquals(Boolean.TRUE, entity.getCaching().getRefreshOnlyIfNewer());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		
		assertEquals(Boolean.TRUE, cacheAnnotation.getRefreshOnlyIfNewer());		
	}
	
	public void testGetSpecifiedRefreshOnlyIfNewer() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(Boolean.FALSE, caching.getRefreshOnlyIfNewer());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.addAnnotation(EclipseLinkJPA.CACHE);
		cacheAnnotation.setRefreshOnlyIfNewer(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, cacheAnnotation.getRefreshOnlyIfNewer());				
		assertEquals(Boolean.TRUE, entity.getCaching().getSpecifiedRefreshOnlyIfNewer());
		assertEquals(Boolean.TRUE, entity.getCaching().getRefreshOnlyIfNewer());
	}

	public void testSetSpecifiedDisableHits() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(Boolean.FALSE, caching.getDisableHits());
		
		caching.setSpecifiedDisableHits(Boolean.TRUE);
		
		
		assertEquals(Boolean.TRUE, entity.getCaching().getSpecifiedDisableHits());
		assertEquals(Boolean.TRUE, entity.getCaching().getDisableHits());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		
		assertEquals(Boolean.TRUE, cacheAnnotation.getDisableHits());		
	}
	
	public void testGetSpecifiedDisableHits() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(Boolean.FALSE, caching.getDisableHits());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.addAnnotation(EclipseLinkJPA.CACHE);
		cacheAnnotation.setDisableHits(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, cacheAnnotation.getDisableHits());				
		assertEquals(Boolean.TRUE, entity.getCaching().getSpecifiedDisableHits());
		assertEquals(Boolean.TRUE, entity.getCaching().getDisableHits());
	}

	public void testSetSpecifiedCoordinationType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(CacheCoordinationType.SEND_OBJECT_CHANGES, caching.getCoordinationType());
		
		caching.setSpecifiedCoordinationType(CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS);
		
		
		assertEquals(CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, entity.getCaching().getSpecifiedCoordinationType());
		assertEquals(CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, entity.getCaching().getCoordinationType());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, cacheAnnotation.getCoordinationType());		

	
		//set specified coordination type to the same as the default, verify it is not set to default
		caching.setSpecifiedCoordinationType(CacheCoordinationType.SEND_OBJECT_CHANGES);
		assertEquals(CacheCoordinationType.SEND_OBJECT_CHANGES, caching.getSpecifiedCoordinationType());
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.CacheCoordinationType.SEND_OBJECT_CHANGES, cacheAnnotation.getCoordinationType());		
		
		caching.setSpecifiedCoordinationType(null);
		assertNull(caching.getSpecifiedCoordinationType());
		assertEquals(CacheCoordinationType.SEND_OBJECT_CHANGES, caching.getCoordinationType());
	}
	
	public void testGetSpecifiedCoordinationType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(CacheCoordinationType.SEND_OBJECT_CHANGES, caching.getCoordinationType());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.addAnnotation(EclipseLinkJPA.CACHE);
		cacheAnnotation.setCoordinationType(org.eclipse.jpt.eclipselink.core.resource.java.CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, cacheAnnotation.getCoordinationType());				
		assertEquals(CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, entity.getCaching().getSpecifiedCoordinationType());
		assertEquals(CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, entity.getCaching().getCoordinationType());
	}
	
	public void testHasExistenceChecking() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(false, caching.hasExistenceChecking());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addAnnotation(EclipseLinkJPA.EXISTENCE_CHECKING);	
		assertEquals(true, caching.hasExistenceChecking());
	
		typeResource.removeAnnotation(EclipseLinkJPA.EXISTENCE_CHECKING);
		assertEquals(false, caching.hasExistenceChecking());
	}
	
	public void testSetExistenceChecking() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(false, caching.hasExistenceChecking());
		assertNull(typeResource.getAnnotation(EclipseLinkJPA.EXISTENCE_CHECKING));
		
		caching.setExistenceChecking(true);
		
		assertEquals(true, caching.hasExistenceChecking());
		assertNotNull(typeResource.getAnnotation(EclipseLinkJPA.EXISTENCE_CHECKING));
	}
	
	public void testGetDefaultExistenceType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(ExistenceType.CHECK_DATABASE, caching.getDefaultExistenceType());

		caching.setExistenceChecking(true);
		assertEquals(ExistenceType.CHECK_CACHE, caching.getDefaultExistenceType());
	}
	
	public void testGetSpecifiedExistenceType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(ExistenceType.CHECK_DATABASE, caching.getExistenceType());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		ExistenceCheckingAnnotation existenceCheckingAnnotation = (ExistenceCheckingAnnotation) typeResource.addAnnotation(EclipseLinkJPA.EXISTENCE_CHECKING);
		existenceCheckingAnnotation.setValue(org.eclipse.jpt.eclipselink.core.resource.java.ExistenceType.ASSUME_NON_EXISTENCE);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ExistenceType.ASSUME_NON_EXISTENCE, existenceCheckingAnnotation.getValue());				
		assertEquals(ExistenceType.ASSUME_NON_EXISTENCE, entity.getCaching().getSpecifiedExistenceType());
		assertEquals(ExistenceType.ASSUME_NON_EXISTENCE, entity.getCaching().getExistenceType());
	}
	
	public void testSetSpecifiedExistenceType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(ExistenceType.CHECK_DATABASE, caching.getExistenceType());
		
		caching.setExistenceChecking(true);
		caching.setSpecifiedExistenceType(ExistenceType.ASSUME_NON_EXISTENCE);
		
		
		assertEquals(ExistenceType.ASSUME_NON_EXISTENCE, entity.getCaching().getSpecifiedExistenceType());
		assertEquals(ExistenceType.ASSUME_NON_EXISTENCE, entity.getCaching().getExistenceType());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		ExistenceCheckingAnnotation existenceCheckingAnnotation = (ExistenceCheckingAnnotation) typeResource.getAnnotation(EclipseLinkJPA.EXISTENCE_CHECKING);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ExistenceType.ASSUME_NON_EXISTENCE, existenceCheckingAnnotation.getValue());		

	
		//set specified coordination type to the same as the default, verify it is not set to default
		caching.setSpecifiedExistenceType(ExistenceType.CHECK_DATABASE);
		assertEquals(ExistenceType.CHECK_DATABASE, caching.getSpecifiedExistenceType());
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ExistenceType.CHECK_DATABASE, existenceCheckingAnnotation.getValue());		
		
		caching.setSpecifiedExistenceType(null);
		assertNull(caching.getSpecifiedExistenceType());
		assertEquals(ExistenceType.CHECK_CACHE, caching.getExistenceType());
	}

	public void testGetExpiry() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		
		assertNull(entity.getCaching().getExpiry());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.addAnnotation(EclipseLinkJPA.CACHE);
		assertNull(entity.getCaching().getExpiry());
		
		cacheAnnotation.setExpiry(Integer.valueOf(57));
		
		assertEquals(Integer.valueOf(57), entity.getCaching().getExpiry());
		
		typeResource.removeAnnotation(EclipseLinkJPA.CACHE);
		assertNull(entity.getCaching().getExpiry());	
	}
	
	public void testSetExpiry() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		caching.setExpiry(Integer.valueOf(58));
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		assertEquals(Integer.valueOf(58), cacheAnnotation.getExpiry());
		
		
		caching.setExpiry(null);
		cacheAnnotation = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		assertNull(cacheAnnotation);
	}
	
	public void testSetExpiryUnsetsExpiryTimeOfDay() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		caching.addExpiryTimeOfDay();
		caching.getExpiryTimeOfDay().setHour(Integer.valueOf(5));
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		assertEquals(Integer.valueOf(5), cacheAnnotation.getExpiryTimeOfDay().getHour());
		
		caching.setExpiry(Integer.valueOf(900));
		
		assertNull(caching.getExpiryTimeOfDay());
		assertNull(cacheAnnotation.getExpiryTimeOfDay());
		assertEquals(Integer.valueOf(900), cacheAnnotation.getExpiry());	
		assertEquals(Integer.valueOf(900), caching.getExpiry());	
	}
	
	public void testGetTimeOfDayExpiry() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertNull(caching.getExpiryTimeOfDay());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.addAnnotation(EclipseLinkJPA.CACHE);

		cacheAnnotation.addExpiryTimeOfDay();
		
		assertNotNull(caching.getExpiryTimeOfDay());
	}
	
	public void testAddTimeOfDayExpiry() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertNull(caching.getExpiryTimeOfDay());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		EclipseLinkJavaExpiryTimeOfDay timeOfDayExpiry = caching.addExpiryTimeOfDay();
		
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		assertNotNull(cacheAnnotation.getExpiryTimeOfDay());
		assertNotNull(caching.getExpiryTimeOfDay());
		assertEquals(timeOfDayExpiry, caching.getExpiryTimeOfDay());
	}
	
	public void testRemoveTimeOfDayExpiry() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertNull(caching.getExpiryTimeOfDay());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.addAnnotation(EclipseLinkJPA.CACHE);
		cacheAnnotation.addExpiryTimeOfDay();

		assertNotNull(caching.getExpiryTimeOfDay());
		
		caching.removeExpiryTimeOfDay();
		assertNull(caching.getExpiryTimeOfDay());
		assertNull(typeResource.getAnnotation(EclipseLinkJPA.CACHE));
	}
	
	public void testAddTimeOfDayExpiryUnsetsExpiry() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) javaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		caching.setExpiry(Integer.valueOf(800));
		CacheAnnotation cacheAnnotation = (CacheAnnotation) typeResource.getAnnotation(EclipseLinkJPA.CACHE);
		assertEquals(Integer.valueOf(800), cacheAnnotation.getExpiry());	
		
		
		caching.addExpiryTimeOfDay();

		
		assertNull(caching.getExpiry());
		assertNull(cacheAnnotation.getExpiry());
		assertNotNull(cacheAnnotation.getExpiryTimeOfDay());
	}

}

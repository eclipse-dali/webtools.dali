/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCacheCoordinationType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCacheType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkExpiryTimeOfDay;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkExistenceType;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkEntity;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkCacheAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkExistenceCheckingAnnotation;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkJavaCachingTests extends EclipseLinkContextModelTestCase
{	
	public EclipseLinkJavaCachingTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEntity() throws Exception {
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
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(true, caching.isShared());
		
		caching.setSpecifiedShared(Boolean.FALSE);
		
		
		assertEquals(Boolean.FALSE, entity.getCaching().getSpecifiedShared());
		assertEquals(false, entity.getCaching().isShared());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.getAnnotation(EclipseLink.CACHE);
		
		assertEquals(Boolean.FALSE, cacheAnnotation.getShared());		
	}
	
	public void testSetSpecifiedSharedFalseUnsetsOtherCacheSettings() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		caching.setSpecifiedType(EclipseLinkCacheType.HARD_WEAK);
		caching.setSpecifiedSize(Integer.valueOf(500));
		caching.setSpecifiedAlwaysRefresh(Boolean.FALSE);
		caching.setSpecifiedRefreshOnlyIfNewer(Boolean.FALSE);
		caching.setSpecifiedDisableHits(Boolean.FALSE);
		caching.setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS);
		caching.setExistenceChecking(true);
		caching.setSpecifiedExistenceType(EclipseLinkExistenceType.CHECK_CACHE);
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
		assertEquals(EclipseLinkExistenceType.CHECK_CACHE, caching.getSpecifiedExistenceType());
		
		caching.setSpecifiedShared(null);
		EclipseLinkExpiryTimeOfDay timeOfDayExpiry = caching.addExpiryTimeOfDay();
		timeOfDayExpiry.setHour(Integer.valueOf(5));
		
		caching.setSpecifiedShared(Boolean.FALSE);
		assertNull(caching.getExpiryTimeOfDay());
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.getAnnotation(EclipseLink.CACHE);
		
		assertEquals(Boolean.FALSE, cacheAnnotation.getShared());
		assertNull(cacheAnnotation.getExpiryTimeOfDay());
	}
	
	public void testGetSpecifiedShared() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(true, caching.isShared());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.addAnnotation(EclipseLink.CACHE);
		cacheAnnotation.setShared(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, cacheAnnotation.getShared());				
		assertEquals(Boolean.FALSE, entity.getCaching().getSpecifiedShared());
		assertEquals(false, entity.getCaching().isShared());
	}
	
	public void testSetSpecifiedType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, caching.getType());
		
		caching.setSpecifiedType(EclipseLinkCacheType.HARD_WEAK);
		
		
		assertEquals(EclipseLinkCacheType.HARD_WEAK, entity.getCaching().getSpecifiedType());
		assertEquals(EclipseLinkCacheType.HARD_WEAK, entity.getCaching().getType());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.getAnnotation(EclipseLink.CACHE);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.CacheType.HARD_WEAK, cacheAnnotation.getType());		

	
		//set specified type to the same as the default, verify it is not set to default
		caching.setSpecifiedType(EclipseLinkCacheType.SOFT_WEAK);
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, caching.getSpecifiedType());
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.CacheType.SOFT_WEAK, cacheAnnotation.getType());		
		
		caching.setSpecifiedType(null);
		assertNull(caching.getSpecifiedType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, caching.getType());
	}
	
	public void testGetSpecifiedType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, caching.getType());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.addAnnotation(EclipseLink.CACHE);
		cacheAnnotation.setType(org.eclipse.jpt.eclipselink.core.resource.java.CacheType.HARD_WEAK);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.CacheType.HARD_WEAK, cacheAnnotation.getType());				
		assertEquals(EclipseLinkCacheType.HARD_WEAK, entity.getCaching().getSpecifiedType());
		assertEquals(EclipseLinkCacheType.HARD_WEAK, entity.getCaching().getType());
	}

	public void testSetSpecifiedAlwaysRefresh() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isAlwaysRefresh());
		
		caching.setSpecifiedAlwaysRefresh(Boolean.FALSE);
		
		
		assertEquals(Boolean.FALSE, entity.getCaching().getSpecifiedAlwaysRefresh());
		assertEquals(false, entity.getCaching().isAlwaysRefresh());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.getAnnotation(EclipseLink.CACHE);
		
		assertEquals(Boolean.FALSE, cacheAnnotation.getAlwaysRefresh());		
	}
	
	public void testGetSpecifiedAlwaysRefresh() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isAlwaysRefresh());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.addAnnotation(EclipseLink.CACHE);
		cacheAnnotation.setAlwaysRefresh(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, cacheAnnotation.getAlwaysRefresh());				
		assertEquals(Boolean.TRUE, entity.getCaching().getSpecifiedAlwaysRefresh());
		assertEquals(true, entity.getCaching().isAlwaysRefresh());
	}
	
	public void testSetSpecifiedRefreshOnlyIfNewer() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isRefreshOnlyIfNewer());
		
		caching.setSpecifiedRefreshOnlyIfNewer(Boolean.TRUE);
		
		
		assertEquals(Boolean.TRUE, entity.getCaching().getSpecifiedRefreshOnlyIfNewer());
		assertEquals(true, entity.getCaching().isRefreshOnlyIfNewer());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.getAnnotation(EclipseLink.CACHE);
		
		assertEquals(Boolean.TRUE, cacheAnnotation.getRefreshOnlyIfNewer());		
	}
	
	public void testGetSpecifiedRefreshOnlyIfNewer() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isRefreshOnlyIfNewer());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.addAnnotation(EclipseLink.CACHE);
		cacheAnnotation.setRefreshOnlyIfNewer(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, cacheAnnotation.getRefreshOnlyIfNewer());				
		assertEquals(Boolean.TRUE, entity.getCaching().getSpecifiedRefreshOnlyIfNewer());
		assertEquals(true, entity.getCaching().isRefreshOnlyIfNewer());
	}

	public void testSetSpecifiedDisableHits() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isDisableHits());
		
		caching.setSpecifiedDisableHits(Boolean.TRUE);
		
		
		assertEquals(Boolean.TRUE, entity.getCaching().getSpecifiedDisableHits());
		assertEquals(true, entity.getCaching().isDisableHits());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.getAnnotation(EclipseLink.CACHE);
		
		assertEquals(Boolean.TRUE, cacheAnnotation.getDisableHits());		
	}
	
	public void testGetSpecifiedDisableHits() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isDisableHits());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.addAnnotation(EclipseLink.CACHE);
		cacheAnnotation.setDisableHits(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, cacheAnnotation.getDisableHits());				
		assertEquals(Boolean.TRUE, entity.getCaching().getSpecifiedDisableHits());
		assertEquals(true, entity.getCaching().isDisableHits());
	}

	public void testSetSpecifiedCoordinationType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, caching.getCoordinationType());
		
		caching.setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS);
		
		
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, entity.getCaching().getSpecifiedCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, entity.getCaching().getCoordinationType());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.getAnnotation(EclipseLink.CACHE);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, cacheAnnotation.getCoordinationType());		

	
		//set specified coordination type to the same as the default, verify it is not set to default
		caching.setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES);
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, caching.getSpecifiedCoordinationType());
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.CacheCoordinationType.SEND_OBJECT_CHANGES, cacheAnnotation.getCoordinationType());		
		
		caching.setSpecifiedCoordinationType(null);
		assertNull(caching.getSpecifiedCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, caching.getCoordinationType());
	}
	
	public void testGetSpecifiedCoordinationType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, caching.getCoordinationType());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.addAnnotation(EclipseLink.CACHE);
		cacheAnnotation.setCoordinationType(org.eclipse.jpt.eclipselink.core.resource.java.CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, cacheAnnotation.getCoordinationType());				
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, entity.getCaching().getSpecifiedCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, entity.getCaching().getCoordinationType());
	}
	
	public void testHasExistenceChecking() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(false, caching.hasExistenceChecking());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.addAnnotation(EclipseLink.EXISTENCE_CHECKING);	
		assertEquals(true, caching.hasExistenceChecking());
	
		typeResource.removeAnnotation(EclipseLink.EXISTENCE_CHECKING);
		assertEquals(false, caching.hasExistenceChecking());
	}
	
	public void testSetExistenceChecking() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(false, caching.hasExistenceChecking());
		assertNull(typeResource.getAnnotation(EclipseLink.EXISTENCE_CHECKING));
		
		caching.setExistenceChecking(true);
		
		assertEquals(true, caching.hasExistenceChecking());
		assertNotNull(typeResource.getAnnotation(EclipseLink.EXISTENCE_CHECKING));
	}
	
	public void testGetDefaultExistenceType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, caching.getDefaultExistenceType());

		caching.setExistenceChecking(true);
		assertEquals(EclipseLinkExistenceType.CHECK_CACHE, caching.getDefaultExistenceType());
	}
	
	public void testGetSpecifiedExistenceType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, caching.getExistenceType());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkExistenceCheckingAnnotation existenceCheckingAnnotation = (EclipseLinkExistenceCheckingAnnotation) typeResource.addAnnotation(EclipseLink.EXISTENCE_CHECKING);
		existenceCheckingAnnotation.setValue(org.eclipse.jpt.eclipselink.core.resource.java.ExistenceType.ASSUME_NON_EXISTENCE);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ExistenceType.ASSUME_NON_EXISTENCE, existenceCheckingAnnotation.getValue());				
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, entity.getCaching().getSpecifiedExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, entity.getCaching().getExistenceType());
	}
	
	public void testSetSpecifiedExistenceType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, caching.getExistenceType());
		
		caching.setExistenceChecking(true);
		caching.setSpecifiedExistenceType(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE);
		
		
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, entity.getCaching().getSpecifiedExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, entity.getCaching().getExistenceType());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkExistenceCheckingAnnotation existenceCheckingAnnotation = (EclipseLinkExistenceCheckingAnnotation) typeResource.getAnnotation(EclipseLink.EXISTENCE_CHECKING);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ExistenceType.ASSUME_NON_EXISTENCE, existenceCheckingAnnotation.getValue());		

	
		//set specified coordination type to the same as the default, verify it is not set to default
		caching.setSpecifiedExistenceType(EclipseLinkExistenceType.CHECK_DATABASE);
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, caching.getSpecifiedExistenceType());
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ExistenceType.CHECK_DATABASE, existenceCheckingAnnotation.getValue());		
		
		caching.setSpecifiedExistenceType(null);
		assertNull(caching.getSpecifiedExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_CACHE, caching.getExistenceType());
	}

	public void testGetExpiry() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		
		assertNull(entity.getCaching().getExpiry());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.addAnnotation(EclipseLink.CACHE);
		assertNull(entity.getCaching().getExpiry());
		
		cacheAnnotation.setExpiry(Integer.valueOf(57));
		
		assertEquals(Integer.valueOf(57), entity.getCaching().getExpiry());
		
		typeResource.removeAnnotation(EclipseLink.CACHE);
		assertNull(entity.getCaching().getExpiry());	
	}
	
	public void testSetExpiry() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		caching.setExpiry(Integer.valueOf(58));
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.getAnnotation(EclipseLink.CACHE);
		assertEquals(Integer.valueOf(58), cacheAnnotation.getExpiry());
		
		
		caching.setExpiry(null);
		cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.getAnnotation(EclipseLink.CACHE);
		assertNull(cacheAnnotation);
	}
	
	public void testSetExpiryUnsetsExpiryTimeOfDay() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		caching.addExpiryTimeOfDay();
		caching.getExpiryTimeOfDay().setHour(Integer.valueOf(5));
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.getAnnotation(EclipseLink.CACHE);
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
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertNull(caching.getExpiryTimeOfDay());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.addAnnotation(EclipseLink.CACHE);

		cacheAnnotation.addExpiryTimeOfDay();
		
		assertNotNull(caching.getExpiryTimeOfDay());
	}
	
	public void testAddTimeOfDayExpiry() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertNull(caching.getExpiryTimeOfDay());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		EclipseLinkExpiryTimeOfDay timeOfDayExpiry = caching.addExpiryTimeOfDay();
		
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.getAnnotation(EclipseLink.CACHE);
		assertNotNull(cacheAnnotation.getExpiryTimeOfDay());
		assertNotNull(caching.getExpiryTimeOfDay());
		assertEquals(timeOfDayExpiry, caching.getExpiryTimeOfDay());
	}
	
	public void testRemoveTimeOfDayExpiry() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertNull(caching.getExpiryTimeOfDay());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.addAnnotation(EclipseLink.CACHE);
		cacheAnnotation.addExpiryTimeOfDay();

		assertNotNull(caching.getExpiryTimeOfDay());
		
		caching.removeExpiryTimeOfDay();
		assertNull(caching.getExpiryTimeOfDay());
		assertNull(typeResource.getAnnotation(EclipseLink.CACHE));
	}
	
	public void testAddTimeOfDayExpiryUnsetsExpiry() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		caching.setExpiry(Integer.valueOf(800));
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.getAnnotation(EclipseLink.CACHE);
		assertEquals(Integer.valueOf(800), cacheAnnotation.getExpiry());	
		
		
		caching.addExpiryTimeOfDay();

		
		assertNull(caching.getExpiry());
		assertNull(cacheAnnotation.getExpiry());
		assertNotNull(cacheAnnotation.getExpiryTimeOfDay());
	}

	
	public void testSetSpecifiedSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(100, caching.getSize());
		
		caching.setSpecifiedSize(new Integer(50));
		
		
		assertEquals(new Integer(50), entity.getCaching().getSpecifiedSize());
		assertEquals(50, entity.getCaching().getSize());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.getAnnotation(EclipseLink.CACHE);
		
		assertEquals(new Integer(50), cacheAnnotation.getSize());		
	}
	
	public void testGetSpecifiedSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEclipseLinkEntity entity = (JavaEclipseLinkEntity) getJavaPersistentType().getMapping();
		JavaEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(100, caching.getSize());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkCacheAnnotation cacheAnnotation = (EclipseLinkCacheAnnotation) typeResource.addAnnotation(EclipseLink.CACHE);
		cacheAnnotation.setSize(new Integer(50));
		
		assertEquals(new Integer(50), cacheAnnotation.getSize());				
		assertEquals(new Integer(50), entity.getCaching().getSpecifiedSize());
		assertEquals(50, entity.getCaching().getSize());
	}

}

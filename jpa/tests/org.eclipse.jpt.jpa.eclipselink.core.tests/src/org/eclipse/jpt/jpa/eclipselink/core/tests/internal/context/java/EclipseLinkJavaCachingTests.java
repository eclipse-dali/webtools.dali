/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCacheCoordinationType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCacheIsolationType2_2;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCacheType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkExistenceType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTimeOfDay;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ExistenceCheckingAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;

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
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID);
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
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(true, caching.isShared());
		
		caching.setSpecifiedShared(Boolean.FALSE);
		
		
		assertEquals(Boolean.FALSE, entity.getCaching().getSpecifiedShared());
		assertEquals(false, entity.getCaching().isShared());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		
		assertEquals(Boolean.FALSE, cacheAnnotation.getShared());		
	}
	
	public void testSetSpecifiedSharedFalseUnsetsOtherCacheSettings() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
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
		assertTrue(caching.isExistenceChecking());
		assertEquals(EclipseLinkExistenceType.CHECK_CACHE, caching.getSpecifiedExistenceType());
		
		caching.setSpecifiedShared(null);
		EclipseLinkTimeOfDay timeOfDayExpiry = caching.addExpiryTimeOfDay();
		timeOfDayExpiry.setHour(Integer.valueOf(5));
		
		caching.setSpecifiedShared(Boolean.FALSE);
		assertNull(caching.getExpiryTimeOfDay());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		
		assertEquals(Boolean.FALSE, cacheAnnotation.getShared());
		assertNull(cacheAnnotation.getExpiryTimeOfDay());
	}
	
	public void testGetDefaultShared() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertTrue(caching.isDefaultShared());
		
		getPersistenceUnit().setProperty(EclipseLinkCaching.ECLIPSELINK_CACHE_SHARED_DEFAULT, "false");
		
		assertFalse(caching.isDefaultShared());
	}
	
	public void testGetSpecifiedShared() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(true, caching.isShared());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.addAnnotation(EclipseLink.CACHE);
		cacheAnnotation.setShared(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(Boolean.FALSE, cacheAnnotation.getShared());				
		assertEquals(Boolean.FALSE, entity.getCaching().getSpecifiedShared());
		assertEquals(false, entity.getCaching().isShared());
	}
	
	public void testSetSpecifiedType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, caching.getType());
		
		caching.setSpecifiedType(EclipseLinkCacheType.HARD_WEAK);
		
		
		assertEquals(EclipseLinkCacheType.HARD_WEAK, entity.getCaching().getSpecifiedType());
		assertEquals(EclipseLinkCacheType.HARD_WEAK, entity.getCaching().getType());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheType.HARD_WEAK, cacheAnnotation.getType());		

	
		//set specified type to the same as the default, verify it is not set to default
		caching.setSpecifiedType(EclipseLinkCacheType.SOFT_WEAK);
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, caching.getSpecifiedType());
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheType.SOFT_WEAK, cacheAnnotation.getType());		
		
		caching.setSpecifiedType(null);
		assertNull(caching.getSpecifiedType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, caching.getType());
	}
	
	public void testGetDefaultType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, caching.getDefaultType());
		
		getPersistenceUnit().setProperty(EclipseLinkCaching.ECLIPSELINK_CACHE_TYPE_DEFAULT, EclipseLinkCacheType.FULL.toString());
		
		assertEquals(EclipseLinkCacheType.FULL, caching.getDefaultType());
	}
	
	public void testGetSpecifiedType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, caching.getType());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.addAnnotation(EclipseLink.CACHE);
		cacheAnnotation.setType(org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheType.HARD_WEAK);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheType.HARD_WEAK, cacheAnnotation.getType());				
		assertEquals(EclipseLinkCacheType.HARD_WEAK, entity.getCaching().getSpecifiedType());
		assertEquals(EclipseLinkCacheType.HARD_WEAK, entity.getCaching().getType());
	}

	public void testSetSpecifiedAlwaysRefresh() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isAlwaysRefresh());
		
		caching.setSpecifiedAlwaysRefresh(Boolean.FALSE);
		
		
		assertEquals(Boolean.FALSE, entity.getCaching().getSpecifiedAlwaysRefresh());
		assertEquals(false, entity.getCaching().isAlwaysRefresh());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		
		assertEquals(Boolean.FALSE, cacheAnnotation.getAlwaysRefresh());		
	}
	
	public void testGetSpecifiedAlwaysRefresh() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isAlwaysRefresh());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.addAnnotation(EclipseLink.CACHE);
		cacheAnnotation.setAlwaysRefresh(Boolean.TRUE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(Boolean.TRUE, cacheAnnotation.getAlwaysRefresh());				
		assertEquals(Boolean.TRUE, entity.getCaching().getSpecifiedAlwaysRefresh());
		assertEquals(true, entity.getCaching().isAlwaysRefresh());
	}
	
	public void testSetSpecifiedRefreshOnlyIfNewer() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isRefreshOnlyIfNewer());
		
		caching.setSpecifiedRefreshOnlyIfNewer(Boolean.TRUE);
		
		
		assertEquals(Boolean.TRUE, entity.getCaching().getSpecifiedRefreshOnlyIfNewer());
		assertEquals(true, entity.getCaching().isRefreshOnlyIfNewer());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		
		assertEquals(Boolean.TRUE, cacheAnnotation.getRefreshOnlyIfNewer());		
	}
	
	public void testGetSpecifiedRefreshOnlyIfNewer() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isRefreshOnlyIfNewer());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.addAnnotation(EclipseLink.CACHE);
		cacheAnnotation.setRefreshOnlyIfNewer(Boolean.TRUE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(Boolean.TRUE, cacheAnnotation.getRefreshOnlyIfNewer());				
		assertEquals(Boolean.TRUE, entity.getCaching().getSpecifiedRefreshOnlyIfNewer());
		assertEquals(true, entity.getCaching().isRefreshOnlyIfNewer());
	}

	public void testSetSpecifiedDisableHits() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isDisableHits());
		
		caching.setSpecifiedDisableHits(Boolean.TRUE);
		
		
		assertEquals(Boolean.TRUE, entity.getCaching().getSpecifiedDisableHits());
		assertEquals(true, entity.getCaching().isDisableHits());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		
		assertEquals(Boolean.TRUE, cacheAnnotation.getDisableHits());		
	}
	
	public void testGetSpecifiedDisableHits() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isDisableHits());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.addAnnotation(EclipseLink.CACHE);
		cacheAnnotation.setDisableHits(Boolean.TRUE);
		getJpaProject().synchronizeContextModel();
	
		assertEquals(Boolean.TRUE, cacheAnnotation.getDisableHits());				
		assertEquals(Boolean.TRUE, entity.getCaching().getSpecifiedDisableHits());
		assertEquals(true, entity.getCaching().isDisableHits());
	}

	public void testSetSpecifiedCoordinationType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, caching.getCoordinationType());
		
		caching.setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS);
		
		
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, entity.getCaching().getSpecifiedCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, entity.getCaching().getCoordinationType());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, cacheAnnotation.getCoordinationType());		

	
		//set specified coordination type to the same as the default, verify it is not set to default
		caching.setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES);
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, caching.getSpecifiedCoordinationType());
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheCoordinationType.SEND_OBJECT_CHANGES, cacheAnnotation.getCoordinationType());		
		
		caching.setSpecifiedCoordinationType(null);
		assertNull(caching.getSpecifiedCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, caching.getCoordinationType());
	}
	
	public void testGetSpecifiedCoordinationType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, caching.getCoordinationType());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.addAnnotation(EclipseLink.CACHE);
		cacheAnnotation.setCoordinationType(org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, cacheAnnotation.getCoordinationType());				
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, entity.getCaching().getSpecifiedCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, entity.getCaching().getCoordinationType());
	}
	
	public void testHasExistenceChecking() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isExistenceChecking());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		resourceType.addAnnotation(EclipseLink.EXISTENCE_CHECKING);	
		getJpaProject().synchronizeContextModel();
		assertEquals(true, caching.isExistenceChecking());
	
		resourceType.removeAnnotation(EclipseLink.EXISTENCE_CHECKING);
		getJpaProject().synchronizeContextModel();
		assertEquals(false, caching.isExistenceChecking());
	}
	
	public void testSetExistenceChecking() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		
		assertEquals(false, caching.isExistenceChecking());
		assertNull(resourceType.getAnnotation(EclipseLink.EXISTENCE_CHECKING));
		
		caching.setExistenceChecking(true);
		
		assertEquals(true, caching.isExistenceChecking());
		assertNotNull(resourceType.getAnnotation(EclipseLink.EXISTENCE_CHECKING));
	}
	
	public void testGetDefaultExistenceType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, caching.getDefaultExistenceType());

		caching.setExistenceChecking(true);
		assertEquals(EclipseLinkExistenceType.CHECK_CACHE, caching.getDefaultExistenceType());
	}
	
	public void testGetSpecifiedExistenceType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, caching.getExistenceType());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		ExistenceCheckingAnnotation existenceCheckingAnnotation = (ExistenceCheckingAnnotation) resourceType.addAnnotation(EclipseLink.EXISTENCE_CHECKING);
		existenceCheckingAnnotation.setValue(org.eclipse.jpt.jpa.eclipselink.core.resource.java.ExistenceType.ASSUME_NON_EXISTENCE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.ExistenceType.ASSUME_NON_EXISTENCE, existenceCheckingAnnotation.getValue());				
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, entity.getCaching().getSpecifiedExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, entity.getCaching().getExistenceType());
	}
	
	public void testSetSpecifiedExistenceType() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, caching.getExistenceType());
		
		caching.setExistenceChecking(true);
		caching.setSpecifiedExistenceType(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE);
		
		
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, entity.getCaching().getSpecifiedExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, entity.getCaching().getExistenceType());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		
		ExistenceCheckingAnnotation existenceCheckingAnnotation = (ExistenceCheckingAnnotation) resourceType.getAnnotation(EclipseLink.EXISTENCE_CHECKING);
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.ExistenceType.ASSUME_NON_EXISTENCE, existenceCheckingAnnotation.getValue());		

	
		//set specified coordination type to the same as the default, verify it is not set to default
		caching.setSpecifiedExistenceType(EclipseLinkExistenceType.CHECK_DATABASE);
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, caching.getSpecifiedExistenceType());
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.ExistenceType.CHECK_DATABASE, existenceCheckingAnnotation.getValue());		
		
		caching.setSpecifiedExistenceType(null);
		assertNull(caching.getSpecifiedExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_CACHE, caching.getExistenceType());
	}

	public void testGetExpiry() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		
		assertNull(entity.getCaching().getExpiry());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.addAnnotation(EclipseLink.CACHE);
		assertNull(entity.getCaching().getExpiry());
		
		cacheAnnotation.setExpiry(Integer.valueOf(57));
		getJpaProject().synchronizeContextModel();
		
		assertEquals(Integer.valueOf(57), entity.getCaching().getExpiry());
		
		resourceType.removeAnnotation(EclipseLink.CACHE);
		getJpaProject().synchronizeContextModel();
		assertNull(entity.getCaching().getExpiry());	
	}
	
	public void testSetExpiry() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		
		caching.setExpiry(Integer.valueOf(58));
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Integer.valueOf(58), cacheAnnotation.getExpiry());
		
		
		caching.setExpiry(null);
		cacheAnnotation = (CacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertNull(cacheAnnotation.getExpiry());
	}
	
	public void testSetExpiryUnsetsExpiryTimeOfDay() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		
		caching.addExpiryTimeOfDay();
		caching.getExpiryTimeOfDay().setHour(Integer.valueOf(5));
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
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
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertNull(caching.getExpiryTimeOfDay());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.addAnnotation(EclipseLink.CACHE);

		cacheAnnotation.addExpiryTimeOfDay();
		getJpaProject().synchronizeContextModel();
		
		assertNotNull(caching.getExpiryTimeOfDay());
	}
	
	public void testAddTimeOfDayExpiry() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertNull(caching.getExpiryTimeOfDay());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);

		EclipseLinkTimeOfDay timeOfDayExpiry = caching.addExpiryTimeOfDay();
		
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertNotNull(cacheAnnotation.getExpiryTimeOfDay());
		assertNotNull(caching.getExpiryTimeOfDay());
		assertEquals(timeOfDayExpiry, caching.getExpiryTimeOfDay());
	}
	
	public void testRemoveTimeOfDayExpiry() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertNull(caching.getExpiryTimeOfDay());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.addAnnotation(EclipseLink.CACHE);
		cacheAnnotation.addExpiryTimeOfDay();
		getJpaProject().synchronizeContextModel();

		assertNotNull(caching.getExpiryTimeOfDay());
		
		caching.removeExpiryTimeOfDay();
		getJpaProject().synchronizeContextModel();
		assertNull(caching.getExpiryTimeOfDay());
	}
	
	public void testAddTimeOfDayExpiryUnsetsExpiry() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		caching.setExpiry(Integer.valueOf(800));
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		assertEquals(Integer.valueOf(800), cacheAnnotation.getExpiry());	
		
		
		caching.addExpiryTimeOfDay();

		
		assertNull(caching.getExpiry());
		assertNull(cacheAnnotation.getExpiry());
		assertNotNull(cacheAnnotation.getExpiryTimeOfDay());
	}

	
	public void testSetSpecifiedSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(100, caching.getSize());
		
		caching.setSpecifiedSize(new Integer(50));
		
		
		assertEquals(new Integer(50), entity.getCaching().getSpecifiedSize());
		assertEquals(50, entity.getCaching().getSize());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);
		
		assertEquals(new Integer(50), cacheAnnotation.getSize());		
	}
	
	public void testGetDefaultSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(100, caching.getDefaultSize());
		
		getPersistenceUnit().setProperty(EclipseLinkCaching.ECLIPSELINK_CACHE_SIZE_DEFAULT, "333");
		
		assertEquals(333, caching.getDefaultSize());
	}
	
	public void testGetSpecifiedSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();
		
		assertEquals(100, caching.getSize());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.addAnnotation(EclipseLink.CACHE);
		cacheAnnotation.setSize(new Integer(50));
		getJpaProject().synchronizeContextModel();
		
		assertEquals(new Integer(50), cacheAnnotation.getSize());				
		assertEquals(new Integer(50), entity.getCaching().getSpecifiedSize());
		assertEquals(50, entity.getCaching().getSize());
	}

	public void testSetSpecifiedIsolation() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();

		assertEquals(EclipseLinkCacheIsolationType2_2.SHARED, caching.getIsolation());

		caching.setSpecifiedIsolation(EclipseLinkCacheIsolationType2_2.PROTECTED);


		assertEquals(EclipseLinkCacheIsolationType2_2.PROTECTED, entity.getCaching().getSpecifiedIsolation());
		assertEquals(EclipseLinkCacheIsolationType2_2.PROTECTED, entity.getCaching().getIsolation());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);

		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.getAnnotation(EclipseLink.CACHE);

		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheIsolationType2_2.PROTECTED, cacheAnnotation.getIsolation());		


		//set specified coordination type to the same as the default, verify it is not set to default
		caching.setSpecifiedIsolation(EclipseLinkCacheIsolationType2_2.SHARED);
		assertEquals(EclipseLinkCacheIsolationType2_2.SHARED, caching.getSpecifiedIsolation());
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheIsolationType2_2.SHARED, cacheAnnotation.getIsolation());		

		caching.setSpecifiedIsolation(null);
		assertNull(caching.getSpecifiedIsolation());
		assertEquals(EclipseLinkCacheIsolationType2_2.SHARED, caching.getIsolation());
	}

	public void testGetSpecifiedIsolation() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		EclipseLinkJavaCaching caching = entity.getCaching();

		assertEquals(EclipseLinkCacheIsolationType2_2.SHARED, caching.getIsolation());
		assertNull(caching.getSpecifiedIsolation());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		CacheAnnotation cacheAnnotation = (CacheAnnotation) resourceType.addAnnotation(EclipseLink.CACHE);
		cacheAnnotation.setIsolation(org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheIsolationType2_2.PROTECTED);
		getJpaProject().synchronizeContextModel();

		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheIsolationType2_2.PROTECTED, cacheAnnotation.getIsolation());				
		assertEquals(EclipseLinkCacheIsolationType2_2.PROTECTED, entity.getCaching().getSpecifiedIsolation());
		assertEquals(EclipseLinkCacheIsolationType2_2.PROTECTED, entity.getCaching().getIsolation());
	}
}

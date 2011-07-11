package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCacheCoordinationType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCacheType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkExistenceType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTimeOfDay;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.caching.Caching;

public class EclipseLinkOrmCachingTests extends EclipseLinkOrmContextModelTestCase {

	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
		});
	}
	
	public EclipseLinkOrmCachingTests(String name) {
		super(name);
	}
	
	public void testSetSpecifiedShared() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(true, caching.isShared());
		
		caching.setSpecifiedShared(Boolean.FALSE);
		
		
		assertEquals(Boolean.FALSE, entity.getCaching().getSpecifiedShared());
		assertEquals(false, entity.getCaching().isShared());
		
		
	}

	public void testSetSpecifiedSharedFalseUnsetsOtherCacheSettings() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		caching.setSpecifiedType(EclipseLinkCacheType.HARD_WEAK);
		caching.setSpecifiedSize(Integer.valueOf(500));
		caching.setSpecifiedAlwaysRefresh(Boolean.FALSE);
		caching.setSpecifiedRefreshOnlyIfNewer(Boolean.FALSE);
		caching.setSpecifiedDisableHits(Boolean.FALSE);
		caching.setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS);
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
		assertEquals(EclipseLinkExistenceType.CHECK_CACHE, caching.getSpecifiedExistenceType());
		
		caching.setSpecifiedShared(null);
		EclipseLinkTimeOfDay timeOfDayExpiry = caching.addExpiryTimeOfDay();
		timeOfDayExpiry.setHour(Integer.valueOf(5));
		
		caching.setSpecifiedShared(Boolean.FALSE);
		assertNull(caching.getExpiryTimeOfDay());
		
	}

	public void testGetDefaultShared() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertTrue(caching.isDefaultShared());
		
		getPersistenceUnit().setProperty(Caching.ECLIPSELINK_CACHE_SHARED_DEFAULT, "false");
		
		assertFalse(caching.isDefaultShared());
	}
	
	public void testGetSpecifiedShared() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(true, caching.isShared());
		
	}
	
	public void testSetSpecifiedType() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, caching.getType());
		
		caching.setSpecifiedType(EclipseLinkCacheType.HARD_WEAK);
		
		
		assertEquals(EclipseLinkCacheType.HARD_WEAK, entity.getCaching().getSpecifiedType());
		assertEquals(EclipseLinkCacheType.HARD_WEAK, entity.getCaching().getType());
		
		caching.setSpecifiedType(EclipseLinkCacheType.SOFT_WEAK);
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, caching.getSpecifiedType());
		
		caching.setSpecifiedType(null);
		assertNull(caching.getSpecifiedType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, caching.getType());
	}
	
	public void testGetDefaultType() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, caching.getDefaultType());
		
		getPersistenceUnit().setProperty(Caching.ECLIPSELINK_CACHE_TYPE_DEFAULT, EclipseLinkCacheType.FULL.toString());
		
		assertEquals(EclipseLinkCacheType.FULL, caching.getDefaultType());
	}

	public void testGetSpecifiedType() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, caching.getType());
		
	}

	public void testSetSpecifiedAlwaysRefresh() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isAlwaysRefresh());
		
		caching.setSpecifiedAlwaysRefresh(Boolean.FALSE);
		
		
		assertEquals(Boolean.FALSE, entity.getCaching().getSpecifiedAlwaysRefresh());
		assertEquals(false, entity.getCaching().isAlwaysRefresh());
		
	}
	
	public void testGetSpecifiedAlwaysRefresh() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isAlwaysRefresh());
		
	}
	
	public void testSetSpecifiedRefreshOnlyIfNewer() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isRefreshOnlyIfNewer());
		
		caching.setSpecifiedRefreshOnlyIfNewer(Boolean.TRUE);
		
		
		assertEquals(Boolean.TRUE, entity.getCaching().getSpecifiedRefreshOnlyIfNewer());
		assertEquals(true, entity.getCaching().isRefreshOnlyIfNewer());
		
	}
	
	public void testGetSpecifiedRefreshOnlyIfNewer() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isRefreshOnlyIfNewer());
		
	}

	public void testSetSpecifiedDisableHits() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isDisableHits());
		
		caching.setSpecifiedDisableHits(Boolean.TRUE);
		
		
		assertEquals(Boolean.TRUE, entity.getCaching().getSpecifiedDisableHits());
		assertEquals(true, entity.getCaching().isDisableHits());
		
	}
	
	public void testGetSpecifiedDisableHits() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(false, caching.isDisableHits());
		
	}

	public void testSetSpecifiedCoordinationType() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, caching.getCoordinationType());
		
		caching.setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS);
		
		
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, entity.getCaching().getSpecifiedCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, entity.getCaching().getCoordinationType());
			
		//set specified coordination type to the same as the default, verify it is not set to default
		caching.setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES);
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, caching.getSpecifiedCoordinationType());
		
		caching.setSpecifiedCoordinationType(null);
		assertNull(caching.getSpecifiedCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, caching.getCoordinationType());
	}
	
	public void testGetSpecifiedCoordinationType() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, caching.getCoordinationType());
	}
	
	public void testGetExpiry() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		
		assertNull(entity.getCaching().getExpiry());
		
	}
	
	public void testSetExpiry() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		caching.setExpiry(Integer.valueOf(58));
		assertEquals(Integer.valueOf(58), caching.getExpiry());
		
		
		caching.setExpiry(null);
		assertNull(caching.getExpiry());
	}
	
	public void testSetExpiryUnsetsExpiryTimeOfDay() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		caching.addExpiryTimeOfDay();
		caching.getExpiryTimeOfDay().setHour(Integer.valueOf(5));
		assertEquals(Integer.valueOf(5), caching.getExpiryTimeOfDay().getHour());
		
		caching.setExpiry(Integer.valueOf(900));
		
		assertNull(caching.getExpiryTimeOfDay());
		assertEquals(Integer.valueOf(900), caching.getExpiry());	
	}
	
	public void testGetTimeOfDayExpiry() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertNull(caching.getExpiryTimeOfDay());
	}
	
	public void testAddTimeOfDayExpiry() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertNull(caching.getExpiryTimeOfDay());
		
		EclipseLinkTimeOfDay timeOfDayExpiry = caching.addExpiryTimeOfDay();
		
		assertNotNull(caching.getExpiryTimeOfDay());
		assertEquals(timeOfDayExpiry, caching.getExpiryTimeOfDay());
	}
	
	public void testRemoveTimeOfDayExpiry() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertNull(caching.getExpiryTimeOfDay());
	}
	
	public void testAddTimeOfDayExpiryUnsetsExpiry() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		caching.setExpiry(Integer.valueOf(800));
		assertEquals(Integer.valueOf(800), caching.getExpiry());	
		
		caching.addExpiryTimeOfDay();
		
		assertNull(caching.getExpiry());
		assertNotNull(caching.getExpiryTimeOfDay());
	}

	
	public void testSetSpecifiedSize() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(100, caching.getSize());
		
		caching.setSpecifiedSize(new Integer(50));
		
		
		assertEquals(new Integer(50), entity.getCaching().getSpecifiedSize());
		assertEquals(50, entity.getCaching().getSize());
	}
	
	public void testGetDefaultSize() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(100, caching.getDefaultSize());
		
		getPersistenceUnit().setProperty(Caching.ECLIPSELINK_CACHE_SIZE_DEFAULT, "333");
		
		assertEquals(333, caching.getDefaultSize());
	}
	
	public void testGetSpecifiedSize() throws Exception {
		createTestEntity();
		
		OrmPersistentType type = (OrmPersistentType)getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity entity = (OrmEclipseLinkEntity)type.getMapping();
		OrmEclipseLinkCaching caching = entity.getCaching();
		
		assertEquals(100, caching.getSize());
		
		caching.setSpecifiedSize(new Integer(50));
		getJpaProject().synchronizeContextModel();
		
		assertEquals(new Integer(50), entity.getCaching().getSpecifiedSize());
		assertEquals(50, entity.getCaching().getSize());
	}
}

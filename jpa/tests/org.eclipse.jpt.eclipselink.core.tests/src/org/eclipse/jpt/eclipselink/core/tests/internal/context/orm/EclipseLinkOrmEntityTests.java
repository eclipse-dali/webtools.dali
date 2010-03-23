/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCacheCoordinationType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCacheType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTrackingType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkExistenceType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkExpiryTimeOfDay;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkEntity;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkEntity;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkOrmEntityTests extends EclipseLinkOrmContextModelTestCase
{

	
	public EclipseLinkOrmEntityTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestEntityForReadOnly() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLink.READ_ONLY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityForCustomizer() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLink.CUSTOMIZER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
		});
	}

	private ICompilationUnit createTestEntityForChangeTracking() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLink.CHANGE_TRACKING);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
		});
	}

	private ICompilationUnit createTestEntityForCaching() throws Exception {
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

	private ICompilationUnit createTestEntityForConverters() throws Exception {
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

	private ICompilationUnit createTestEntityForTypeConverters() throws Exception {
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

	private ICompilationUnit createTestEntityForObjectTypeConverters() throws Exception {
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

	private ICompilationUnit createTestEntityForStructConverters() throws Exception {
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
	
	public void testUpdateReadOnly() throws Exception {
		createTestEntityForReadOnly();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaEclipseLinkEntity javaContextEntity = (JavaEclipseLinkEntity) ormPersistentType.getJavaPersistentType().getMapping();
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		// check defaults
		
		assertNull(resourceEntity.getReadOnly());
		assertFalse(javaContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertNull(ormContextEntity.getReadOnly().getSpecifiedReadOnly());
		
		// set xml read only to false, check override
		
		resourceEntity.setReadOnly(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceEntity.getReadOnly());
		assertFalse(javaContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertEquals(Boolean.FALSE, ormContextEntity.getReadOnly().getSpecifiedReadOnly());
		
		// set xml read only to true, check override
		
		resourceEntity.setReadOnly(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceEntity.getReadOnly());
		assertFalse(javaContextEntity.getReadOnly().isReadOnly());
		assertTrue(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertEquals(Boolean.TRUE, ormContextEntity.getReadOnly().getSpecifiedReadOnly());
		
		// clear xml read only, set java read only to true, check defaults
		
		resourceEntity.setReadOnly(null);
		javaContextEntity.getReadOnly().setSpecifiedReadOnly(Boolean.TRUE);
		
		assertNull(resourceEntity.getReadOnly());
		assertTrue(javaContextEntity.getReadOnly().isReadOnly());
		assertTrue(ormContextEntity.getReadOnly().isReadOnly());
		assertTrue(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertNull(ormContextEntity.getReadOnly().getSpecifiedReadOnly());

		// set metadataComplete to True, check defaults not from java

		ormContextEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertNull(resourceEntity.getReadOnly());
		assertTrue(javaContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertNull(ormContextEntity.getReadOnly().getSpecifiedReadOnly());

		ormContextEntity.setSpecifiedMetadataComplete(null);
		
		// set xml read only to false, check override
		
		resourceEntity.setReadOnly(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceEntity.getReadOnly());
		assertTrue(javaContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isReadOnly());
		assertTrue(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertEquals(Boolean.FALSE, ormContextEntity.getReadOnly().getSpecifiedReadOnly());
		
		// set xml read only to true, check override
		
		resourceEntity.setReadOnly(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceEntity.getReadOnly());
		assertTrue(javaContextEntity.getReadOnly().isReadOnly());
		assertTrue(ormContextEntity.getReadOnly().isReadOnly());
		assertTrue(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertEquals(Boolean.TRUE, ormContextEntity.getReadOnly().getSpecifiedReadOnly());
				
		// clear xml read only, set java read only to false, check defaults
		
		resourceEntity.setReadOnly(null);
		javaContextEntity.getReadOnly().setSpecifiedReadOnly(Boolean.FALSE);
		
		assertNull(resourceEntity.getReadOnly());
		assertFalse(javaContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertNull(ormContextEntity.getReadOnly().getSpecifiedReadOnly());
		
		
		
		// set xml read only to false, check override
		
		resourceEntity.setReadOnly(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceEntity.getReadOnly());
		assertFalse(javaContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertEquals(Boolean.FALSE, ormContextEntity.getReadOnly().getSpecifiedReadOnly());
		
		// set xml read only to true, check override
		
		resourceEntity.setReadOnly(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceEntity.getReadOnly());
		assertFalse(javaContextEntity.getReadOnly().isReadOnly());
		assertTrue(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertEquals(Boolean.TRUE, ormContextEntity.getReadOnly().getSpecifiedReadOnly());
	}
	
	public void testModifyReadOnly() throws Exception {
		createTestEntityForReadOnly();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		// check defaults
		
		assertNull(resourceEntity.getReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertNull(ormContextEntity.getReadOnly().getSpecifiedReadOnly());
		
		// set context read only to true, check resource
		
		ormContextEntity.getReadOnly().setSpecifiedReadOnly(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceEntity.getReadOnly());
		assertTrue(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertEquals(Boolean.TRUE, ormContextEntity.getReadOnly().getSpecifiedReadOnly());
		
		// set context read only to false, check resource
		
		ormContextEntity.getReadOnly().setSpecifiedReadOnly(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceEntity.getReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertEquals(Boolean.FALSE, ormContextEntity.getReadOnly().getSpecifiedReadOnly());
		
		// set context read only to null, check resource
		
		ormContextEntity.getReadOnly().setSpecifiedReadOnly(null);
		
		assertNull(resourceEntity.getReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertNull(ormContextEntity.getReadOnly().getSpecifiedReadOnly());	
	}
	
	
	public void testUpdateCustomizerClass() throws Exception {
		createTestEntityForCustomizer();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaEclipseLinkEntity javaContextEntity = (JavaEclipseLinkEntity) ormPersistentType.getJavaPersistentType().getMapping();
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);


		// check defaults
		
		assertNull(resourceEntity.getCustomizer());
		assertNull(javaContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getSpecifiedCustomizerClass());
		
		// set xml customizer, check defaults
		resourceEntity.setCustomizer(OrmFactory.eINSTANCE.createXmlClassReference());
		assertNull(resourceEntity.getCustomizer().getClassName());
		assertNull(javaContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getSpecifiedCustomizerClass());

		
		// set xml customizer class, check override
		
		resourceEntity.getCustomizer().setClassName("foo");
		
		assertEquals("foo", resourceEntity.getCustomizer().getClassName());
		assertNull(javaContextEntity.getCustomizer().getCustomizerClass());
		assertEquals("foo", ormContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getDefaultCustomizerClass());
		assertEquals("foo", ormContextEntity.getCustomizer().getSpecifiedCustomizerClass());
		
		// clear xml customizer class, set java customizer class, check defaults
		
		resourceEntity.getCustomizer().setClassName(null);
		javaContextEntity.getCustomizer().setSpecifiedCustomizerClass("bar");
		
		assertNull(resourceEntity.getCustomizer().getClassName());
		assertEquals("bar", javaContextEntity.getCustomizer().getCustomizerClass());
		assertEquals("bar", ormContextEntity.getCustomizer().getCustomizerClass());
		assertEquals("bar", ormContextEntity.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getSpecifiedCustomizerClass());

		// set metadataComplete to True, check defaults not from java

		ormContextEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertNull(resourceEntity.getCustomizer().getClassName());
		assertEquals("bar", javaContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getSpecifiedCustomizerClass());

		ormContextEntity.setSpecifiedMetadataComplete(null);
		
		// set xml customizer class, check override
		
		resourceEntity.getCustomizer().setClassName("foo");
		
		assertEquals("foo", resourceEntity.getCustomizer().getClassName());
		assertEquals("bar", javaContextEntity.getCustomizer().getCustomizerClass());
		assertEquals("foo", ormContextEntity.getCustomizer().getCustomizerClass());
		assertEquals("bar", ormContextEntity.getCustomizer().getDefaultCustomizerClass());
		assertEquals("foo", ormContextEntity.getCustomizer().getSpecifiedCustomizerClass());

		//set xml customizer null
		javaContextEntity.getCustomizer().setSpecifiedCustomizerClass(null);
		resourceEntity.setCustomizer(null);
		assertNull(resourceEntity.getCustomizer());
		assertNull(javaContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getSpecifiedCustomizerClass());

	}
	
	public void testModifyCustomizerClass() throws Exception {
		createTestEntityForCustomizer();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		// check defaults
		
		assertNull(resourceEntity.getCustomizer());
		assertNull(ormContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getSpecifiedCustomizerClass());
		
		// set context customizer, check resource
		
		ormContextEntity.getCustomizer().setSpecifiedCustomizerClass("foo");
		
		assertEquals("foo", resourceEntity.getCustomizer().getClassName());
		assertEquals("foo", ormContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getDefaultCustomizerClass());
		assertEquals("foo", ormContextEntity.getCustomizer().getSpecifiedCustomizerClass());
				
		// set context customizer to null, check resource
		
		ormContextEntity.getCustomizer().setSpecifiedCustomizerClass(null);
		
		assertNull(resourceEntity.getCustomizer());
		assertNull(ormContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getSpecifiedCustomizerClass());
	}
	
	public void testUpdateChangeTracking() throws Exception {
		createTestEntityForChangeTracking();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaEclipseLinkEntity javaContextEntity = (JavaEclipseLinkEntity) ormPersistentType.getJavaPersistentType().getMapping();
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		// check defaults
		
		assertNull(resourceEntity.getChangeTracking());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, javaContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getDefaultType());
		assertNull(ormContextEntity.getChangeTracking().getSpecifiedType());
		
		// set xml type to ATTRIBUTE, check context
		
		resourceEntity.setChangeTracking(EclipseLinkOrmFactory.eINSTANCE.createXmlChangeTracking());
		resourceEntity.getChangeTracking().setType(XmlChangeTrackingType.ATTRIBUTE);
		
		assertEquals(XmlChangeTrackingType.ATTRIBUTE, resourceEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, javaContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextEntity.getChangeTracking().getSpecifiedType());
		
		// set xml type to OBJECT, check context
		
		resourceEntity.getChangeTracking().setType(XmlChangeTrackingType.OBJECT);
		
		assertEquals(XmlChangeTrackingType.OBJECT, resourceEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, javaContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextEntity.getChangeTracking().getSpecifiedType());
		
		// set xml type to DEFERRED, check context
		
		resourceEntity.getChangeTracking().setType(XmlChangeTrackingType.DEFERRED);
		
		assertEquals(XmlChangeTrackingType.DEFERRED, resourceEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, javaContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, ormContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, ormContextEntity.getChangeTracking().getSpecifiedType());
		
		// set xml type to AUTO, check context
		
		resourceEntity.getChangeTracking().setType(XmlChangeTrackingType.AUTO);
		
		assertEquals(XmlChangeTrackingType.AUTO, resourceEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, javaContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getSpecifiedType());
		
		// clear xml change tracking, set java change tracking, check defaults
		
		resourceEntity.setChangeTracking(null);
		javaContextEntity.getChangeTracking().setSpecifiedType(EclipseLinkChangeTrackingType.ATTRIBUTE);
		
		assertNull(resourceEntity.getChangeTracking());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, javaContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextEntity.getChangeTracking().getDefaultType());
		assertNull(ormContextEntity.getChangeTracking().getSpecifiedType());
		
		// set metadataComplete to True, check defaults not from java

		ormContextEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertNull(resourceEntity.getChangeTracking());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, javaContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getDefaultType());
		assertNull(ormContextEntity.getChangeTracking().getSpecifiedType());
		
		// unset metadataComplete, set xml change tracking to OBJECT, check context
		
		ormContextEntity.setSpecifiedMetadataComplete(null);
		resourceEntity.setChangeTracking(EclipseLinkOrmFactory.eINSTANCE.createXmlChangeTracking());
		resourceEntity.getChangeTracking().setType(XmlChangeTrackingType.OBJECT);
		
		assertEquals(XmlChangeTrackingType.OBJECT, resourceEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, javaContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextEntity.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextEntity.getChangeTracking().getSpecifiedType());
	}
	
	public void testModifyChangeTracking() throws Exception  {
		createTestEntityForChangeTracking();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		// check defaults
		
		assertNull(resourceEntity.getChangeTracking());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getDefaultType());
		assertNull(ormContextEntity.getChangeTracking().getSpecifiedType());
		
		// set context change tracking to ATTRIBUTE, check resource
		
		ormContextEntity.getChangeTracking().setSpecifiedType(EclipseLinkChangeTrackingType.ATTRIBUTE);
		
		assertEquals(XmlChangeTrackingType.ATTRIBUTE, resourceEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextEntity.getChangeTracking().getSpecifiedType());
				
		// set context change tracking to OBJECT, check resource
		
		ormContextEntity.getChangeTracking().setSpecifiedType(EclipseLinkChangeTrackingType.OBJECT);
		
		assertEquals(XmlChangeTrackingType.OBJECT, resourceEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextEntity.getChangeTracking().getSpecifiedType());
				
		// set context change tracking to DEFERRED, check resource
		
		ormContextEntity.getChangeTracking().setSpecifiedType(EclipseLinkChangeTrackingType.DEFERRED);
		
		assertEquals(XmlChangeTrackingType.DEFERRED, resourceEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, ormContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, ormContextEntity.getChangeTracking().getSpecifiedType());
				
		// set context change tracking to AUTO, check resource
		
		ormContextEntity.getChangeTracking().setSpecifiedType(EclipseLinkChangeTrackingType.AUTO);
		
		assertEquals(XmlChangeTrackingType.AUTO, resourceEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getSpecifiedType());
				
		// set context change tracking to null, check resource
		
		ormContextEntity.getChangeTracking().setSpecifiedType(null);
		
		assertNull(resourceEntity.getChangeTracking());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEntity.getChangeTracking().getDefaultType());
		assertNull(ormContextEntity.getChangeTracking().getSpecifiedType());
	}
	
	public void testUpdateCacheType() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaEclipseLinkCaching javaContextCaching = ((JavaEclipseLinkEntity) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);


		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, javaContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(null, ormContextCaching.getSpecifiedType());
		
		// set xml cache, check defaults
		resourceEntity.setCache(EclipseLinkOrmFactory.eINSTANCE.createXmlCache());
		assertEquals(null, resourceEntity.getCache().getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, javaContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(null, ormContextCaching.getSpecifiedType());

		
		// set xml cache type, check settings
		resourceEntity.getCache().setType(org.eclipse.jpt.eclipselink.core.resource.orm.CacheType.FULL);
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.orm.CacheType.FULL, resourceEntity.getCache().getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, javaContextCaching.getType());
		assertEquals(EclipseLinkCacheType.FULL, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(EclipseLinkCacheType.FULL, ormContextCaching.getSpecifiedType());

			
		// set java cache type, check defaults
		
		javaContextCaching.setSpecifiedType(EclipseLinkCacheType.WEAK);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.orm.CacheType.FULL, resourceEntity.getCache().getType());
		assertEquals(EclipseLinkCacheType.WEAK, javaContextCaching.getType());
		assertEquals(EclipseLinkCacheType.FULL, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(EclipseLinkCacheType.FULL, ormContextCaching.getSpecifiedType());

		// clear xml cache type, check defaults
		resourceEntity.getCache().setType(null);

		assertEquals(null, resourceEntity.getCache().getType());
		assertEquals(EclipseLinkCacheType.WEAK, javaContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(null, ormContextCaching.getSpecifiedType());
	
		
		// clear xml cache, check defaults
		resourceEntity.setCache(null);

		assertEquals(null, resourceEntity.getCache());
		assertEquals(EclipseLinkCacheType.WEAK, javaContextCaching.getType());
		assertEquals(EclipseLinkCacheType.WEAK, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.WEAK, ormContextCaching.getDefaultType());
		assertEquals(null, ormContextCaching.getSpecifiedType());
	
		
		// set metadataComplete to True, check defaults not from java

		ormContextEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(EclipseLinkCacheType.WEAK, javaContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(null, ormContextCaching.getSpecifiedType());

		ormContextEntity.setSpecifiedMetadataComplete(null);
	}
	
	public void testModifyCacheType() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(null, ormContextCaching.getSpecifiedType());
		
		// set context cache type, check resource
		
		ormContextEntity.getCaching().setSpecifiedType(EclipseLinkCacheType.HARD_WEAK);
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.orm.CacheType.HARD_WEAK, resourceEntity.getCache().getType());
		assertEquals(EclipseLinkCacheType.HARD_WEAK, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(EclipseLinkCacheType.HARD_WEAK, ormContextCaching.getSpecifiedType());
				
		// set context customizer to null, check resource
		
		ormContextEntity.getCaching().setSpecifiedType(null);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(null, ormContextCaching.getSpecifiedType());
	}

	public void testUpdateCacheCoordinationType() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaEclipseLinkCaching javaContextCaching = ((JavaEclipseLinkEntity) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);


		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, javaContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(null, ormContextCaching.getSpecifiedCoordinationType());
		
		// set xml cache, check defaults
		resourceEntity.setCache(EclipseLinkOrmFactory.eINSTANCE.createXmlCache());
		assertEquals(null, resourceEntity.getCache().getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, javaContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(null, ormContextCaching.getSpecifiedCoordinationType());

		
		// set xml cache type, check settings
		resourceEntity.getCache().setCoordinationType(org.eclipse.jpt.eclipselink.core.resource.orm.CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS);
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.orm.CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, resourceEntity.getCache().getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, javaContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, ormContextCaching.getSpecifiedCoordinationType());

			
		// set java cache type, check defaults
		
		javaContextCaching.setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.orm.CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, resourceEntity.getCache().getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, javaContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, ormContextCaching.getSpecifiedCoordinationType());

		// clear xml cache type, check defaults
		resourceEntity.getCache().setCoordinationType(null);

		assertEquals(null, resourceEntity.getCache().getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, javaContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(null, ormContextCaching.getSpecifiedCoordinationType());
	
		
		// clear xml cache, check defaults
		resourceEntity.setCache(null);

		assertEquals(null, resourceEntity.getCache());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, javaContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(null, ormContextCaching.getSpecifiedCoordinationType());
	
		
		// set metadataComplete to True, check defaults not from java

		ormContextEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, javaContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(null, ormContextCaching.getSpecifiedCoordinationType());

		ormContextEntity.setSpecifiedMetadataComplete(null);
	}
	
	public void testModifyCacheCoordinationType() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(null, ormContextCaching.getSpecifiedCoordinationType());
		
		// set context cache coordination type, check resource
		
		ormContextEntity.getCaching().setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES);
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.orm.CacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, resourceEntity.getCache().getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, ormContextCaching.getSpecifiedCoordinationType());
				
		// set context coordination type to null, check resource
		
		ormContextEntity.getCaching().setSpecifiedCoordinationType(null);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(null, ormContextCaching.getSpecifiedCoordinationType());
	}

	
	public void testUpdateCacheSize() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaEclipseLinkCaching javaContextCaching = ((JavaEclipseLinkEntity) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);


		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(100, javaContextCaching.getSize());
		assertEquals(100, ormContextCaching.getSize());
		assertEquals(100, ormContextCaching.getDefaultSize());
		assertEquals(null, ormContextCaching.getSpecifiedSize());
		
		// set xml cache, check defaults
		resourceEntity.setCache(EclipseLinkOrmFactory.eINSTANCE.createXmlCache());
		assertEquals(null, resourceEntity.getCache().getSize());
		assertEquals(100, javaContextCaching.getSize());
		assertEquals(100, ormContextCaching.getSize());
		assertEquals(100, ormContextCaching.getDefaultSize());
		assertEquals(null, ormContextCaching.getSpecifiedSize());

		
		// set xml cache size, check settings
		resourceEntity.getCache().setSize(new Integer(105));
		assertEquals(new Integer(105), resourceEntity.getCache().getSize());
		assertEquals(100, javaContextCaching.getSize());
		assertEquals(105, ormContextCaching.getSize());
		assertEquals(100, ormContextCaching.getDefaultSize());
		assertEquals(new Integer(105), ormContextCaching.getSpecifiedSize());

			
		// set java cache size, check defaults
		
		javaContextCaching.setSpecifiedSize(new Integer(50));
		
		assertEquals(new Integer(105), resourceEntity.getCache().getSize());
		assertEquals(50, javaContextCaching.getSize());
		assertEquals(105, ormContextCaching.getSize());
		assertEquals(50, ormContextCaching.getDefaultSize());
		assertEquals(new Integer(105), ormContextCaching.getSpecifiedSize());

		// clear xml cache size, check defaults
		resourceEntity.getCache().setSize(null);

		assertEquals(null, resourceEntity.getCache().getSize());
		assertEquals(50, javaContextCaching.getSize());
		assertEquals(50, ormContextCaching.getSize());
		assertEquals(50, ormContextCaching.getDefaultSize());
		assertEquals(null, ormContextCaching.getSpecifiedSize());
	
		
		// clear xml cache, check defaults
		resourceEntity.setCache(null);

		assertEquals(null, resourceEntity.getCache());
		assertEquals(50, javaContextCaching.getSize());
		assertEquals(50, ormContextCaching.getSize());
		assertEquals(50, ormContextCaching.getDefaultSize());
		assertEquals(null, ormContextCaching.getSpecifiedSize());
	
		
		// set metadataComplete to True, check defaults not from java

		ormContextEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(50, javaContextCaching.getSize());
		assertEquals(100, ormContextCaching.getSize());
		assertEquals(100, ormContextCaching.getDefaultSize());
		assertEquals(null, ormContextCaching.getSpecifiedSize());

		ormContextEntity.setSpecifiedMetadataComplete(null);
	}
	
	public void testModifyCacheSize() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(100, ormContextCaching.getSize());
		assertEquals(100, ormContextCaching.getDefaultSize());
		assertEquals(null, ormContextCaching.getSpecifiedSize());
		
		// set context cache size, check resource
		
		ormContextEntity.getCaching().setSpecifiedSize(new Integer(50));
		assertEquals(new Integer(50), resourceEntity.getCache().getSize());
		assertEquals(50, ormContextCaching.getSize());
		assertEquals(100, ormContextCaching.getDefaultSize());
		assertEquals(new Integer(50), ormContextCaching.getSpecifiedSize());
				
		// set context cache size to null, check resource
		
		ormContextEntity.getCaching().setSpecifiedSize(null);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(100, ormContextCaching.getSize());
		assertEquals(100, ormContextCaching.getDefaultSize());
		assertEquals(null, ormContextCaching.getSpecifiedSize());
	}

	public void testUpdateCacheAlwaysRefresh() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaEclipseLinkCaching javaContextCaching = ((JavaEclipseLinkEntity) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);


		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(false, javaContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(null, ormContextCaching.getSpecifiedAlwaysRefresh());
		
		// set xml cache, check defaults
		resourceEntity.setCache(EclipseLinkOrmFactory.eINSTANCE.createXmlCache());
		assertEquals(null, resourceEntity.getCache().getAlwaysRefresh());
		assertEquals(false, javaContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(null, ormContextCaching.getSpecifiedAlwaysRefresh());

		
		// set xml cache always refresh, check settings
		resourceEntity.getCache().setAlwaysRefresh(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceEntity.getCache().getAlwaysRefresh());
		assertEquals(false, javaContextCaching.isAlwaysRefresh());
		assertEquals(true, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedAlwaysRefresh());

			
		// set java cache always refresh, check defaults
		
		javaContextCaching.setSpecifiedAlwaysRefresh(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceEntity.getCache().getAlwaysRefresh());
		assertEquals(true, javaContextCaching.isAlwaysRefresh());
		assertEquals(true, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedAlwaysRefresh());

		// set xml cache always refresh to false
		resourceEntity.getCache().setAlwaysRefresh(Boolean.FALSE);
		assertEquals(Boolean.FALSE, resourceEntity.getCache().getAlwaysRefresh());
		assertEquals(true, javaContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(Boolean.FALSE, ormContextCaching.getSpecifiedAlwaysRefresh());

		// clear xml cache always refresh, check defaults
		resourceEntity.getCache().setAlwaysRefresh(null);

		assertEquals(null, resourceEntity.getCache().getAlwaysRefresh());
		assertEquals(true, javaContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(null, ormContextCaching.getSpecifiedAlwaysRefresh());
	
		
		// clear xml cache, check defaults
		resourceEntity.setCache(null);

		assertEquals(null, resourceEntity.getCache());
		assertEquals(true, javaContextCaching.isAlwaysRefresh());
		assertEquals(true, ormContextCaching.isAlwaysRefresh());
		assertEquals(true, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(null, ormContextCaching.getSpecifiedAlwaysRefresh());
	
		
		// set metadataComplete to True, check defaults not from java

		ormContextEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(true, javaContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(null, ormContextCaching.getSpecifiedAlwaysRefresh());

		
		// set metadataComplete back to null, check defaults from java
		ormContextEntity.setSpecifiedMetadataComplete(null);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(true, javaContextCaching.isAlwaysRefresh());
		assertEquals(true, ormContextCaching.isAlwaysRefresh());
		assertEquals(true, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(null, ormContextCaching.getSpecifiedAlwaysRefresh());
	}
	
	public void testModifyCacheAlwaysRefresh() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(false, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(null, ormContextCaching.getSpecifiedAlwaysRefresh());
		
		// set context cache size, check resource
		
		ormContextEntity.getCaching().setSpecifiedAlwaysRefresh(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceEntity.getCache().getAlwaysRefresh());
		assertEquals(true, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedAlwaysRefresh());
				
		// set context cache size to null, check resource
		
		ormContextEntity.getCaching().setSpecifiedAlwaysRefresh(null);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(false, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(null, ormContextCaching.getSpecifiedAlwaysRefresh());
	}
	
	public void testUpdateCacheRefreshOnlyIfNewer() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaEclipseLinkCaching javaContextCaching = ((JavaEclipseLinkEntity) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);


		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(false, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(null, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());
		
		// set xml cache, check defaults
		resourceEntity.setCache(EclipseLinkOrmFactory.eINSTANCE.createXmlCache());
		assertEquals(null, resourceEntity.getCache().getRefreshOnlyIfNewer());
		assertEquals(false, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(null, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());

		
		// set xml cache size, check settings
		resourceEntity.getCache().setRefreshOnlyIfNewer(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceEntity.getCache().getRefreshOnlyIfNewer());
		assertEquals(false, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(true, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());

			
		// set java cache size, check defaults
		
		javaContextCaching.setSpecifiedRefreshOnlyIfNewer(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceEntity.getCache().getRefreshOnlyIfNewer());
		assertEquals(true, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(true, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());

		// set xml cache always refresh to false
		resourceEntity.getCache().setRefreshOnlyIfNewer(Boolean.FALSE);
		assertEquals(Boolean.FALSE, resourceEntity.getCache().getRefreshOnlyIfNewer());
		assertEquals(true, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(Boolean.FALSE, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());

		// clear xml cache always refresh, check defaults
		resourceEntity.getCache().setRefreshOnlyIfNewer(null);

		assertEquals(null, resourceEntity.getCache().getRefreshOnlyIfNewer());
		assertEquals(true, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(null, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());
	
		
		// clear xml cache, check defaults
		resourceEntity.setCache(null);

		assertEquals(null, resourceEntity.getCache());
		assertEquals(true, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(true, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(true, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(null, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());
	
		
		// set metadataComplete to True, check defaults not from java

		ormContextEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(true, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(null, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());

		
		// set metadataComplete back to null, check defaults from java
		ormContextEntity.setSpecifiedMetadataComplete(null);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(true, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(true, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(true, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(null, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());
	}
	
	public void testModifyCacheRefreshOnlyIfNewer() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(false, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(null, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());
		
		// set context cache size, check resource
		
		ormContextEntity.getCaching().setSpecifiedRefreshOnlyIfNewer(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceEntity.getCache().getRefreshOnlyIfNewer());
		assertEquals(true, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());
				
		// set context cache size to null, check resource
		
		ormContextEntity.getCaching().setSpecifiedRefreshOnlyIfNewer(null);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(false, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(null, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());
	}
	
	public void testUpdateCacheDisableHits() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaEclipseLinkCaching javaContextCaching = ((JavaEclipseLinkEntity) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);


		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(false, javaContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(null, ormContextCaching.getSpecifiedDisableHits());
		
		// set xml cache, check defaults
		resourceEntity.setCache(EclipseLinkOrmFactory.eINSTANCE.createXmlCache());
		assertEquals(null, resourceEntity.getCache().getDisableHits());
		assertEquals(false, javaContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(null, ormContextCaching.getSpecifiedDisableHits());

		
		// set xml cache size, check settings
		resourceEntity.getCache().setDisableHits(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceEntity.getCache().getDisableHits());
		assertEquals(false, javaContextCaching.isDisableHits());
		assertEquals(true, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedDisableHits());

			
		// set java cache size, check defaults
		
		javaContextCaching.setSpecifiedDisableHits(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceEntity.getCache().getDisableHits());
		assertEquals(true, javaContextCaching.isDisableHits());
		assertEquals(true, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedDisableHits());

		// set xml cache always refresh to false
		resourceEntity.getCache().setDisableHits(Boolean.FALSE);
		assertEquals(Boolean.FALSE, resourceEntity.getCache().getDisableHits());
		assertEquals(true, javaContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(Boolean.FALSE, ormContextCaching.getSpecifiedDisableHits());

		// clear xml cache always refresh, check defaults
		resourceEntity.getCache().setDisableHits(null);

		assertEquals(null, resourceEntity.getCache().getDisableHits());
		assertEquals(true, javaContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(null, ormContextCaching.getSpecifiedDisableHits());
	
		
		// clear xml cache, check defaults
		resourceEntity.setCache(null);

		assertEquals(null, resourceEntity.getCache());
		assertEquals(true, javaContextCaching.isDisableHits());
		assertEquals(true, ormContextCaching.isDisableHits());
		assertEquals(true, ormContextCaching.isDefaultDisableHits());
		assertEquals(null, ormContextCaching.getSpecifiedDisableHits());
	
		
		// set metadataComplete to True, check defaults not from java

		ormContextEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(true, javaContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(null, ormContextCaching.getSpecifiedDisableHits());

		
		// set metadataComplete back to null, check defaults from java
		ormContextEntity.setSpecifiedMetadataComplete(null);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(true, javaContextCaching.isDisableHits());
		assertEquals(true, ormContextCaching.isDisableHits());
		assertEquals(true, ormContextCaching.isDefaultDisableHits());
		assertEquals(null, ormContextCaching.getSpecifiedDisableHits());
	}
	
	public void testModifyCacheDisableHits() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(false, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(null, ormContextCaching.getSpecifiedDisableHits());
		
		// set context cache size, check resource
		
		ormContextEntity.getCaching().setSpecifiedDisableHits(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceEntity.getCache().getDisableHits());
		assertEquals(true, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedDisableHits());
				
		// set context cache size to null, check resource
		
		ormContextEntity.getCaching().setSpecifiedDisableHits(null);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(false, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(null, ormContextCaching.getSpecifiedDisableHits());
	}
	
	public void testUpdateCacheShared() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaEclipseLinkCaching javaContextCaching = ((JavaEclipseLinkEntity) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);


		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(true, javaContextCaching.isShared());
		assertEquals(true, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(null, ormContextCaching.getSpecifiedShared());
		
		// set xml cache, check defaults
		resourceEntity.setCache(EclipseLinkOrmFactory.eINSTANCE.createXmlCache());
		assertEquals(null, resourceEntity.getCache().getShared());
		assertEquals(true, javaContextCaching.isShared());
		assertEquals(true, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(null, ormContextCaching.getSpecifiedShared());

		
		// set xml cache size, check settings
		resourceEntity.getCache().setShared(Boolean.FALSE);
		assertEquals(Boolean.FALSE, resourceEntity.getCache().getShared());
		assertEquals(true, javaContextCaching.isShared());
		assertEquals(false, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(Boolean.FALSE, ormContextCaching.getSpecifiedShared());

			
		// set java cache size, check defaults
		
		javaContextCaching.setSpecifiedShared(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceEntity.getCache().getShared());
		assertEquals(false, javaContextCaching.isShared());
		assertEquals(false, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(Boolean.FALSE, ormContextCaching.getSpecifiedShared());

		// set xml cache always refresh to false
		resourceEntity.getCache().setShared(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceEntity.getCache().getShared());
		assertEquals(false, javaContextCaching.isShared());
		assertEquals(true, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedShared());

		// clear xml cache always refresh, check defaults
		resourceEntity.getCache().setShared(null);

		assertEquals(null, resourceEntity.getCache().getShared());
		assertEquals(false, javaContextCaching.isShared());
		assertEquals(true, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(null, ormContextCaching.getSpecifiedShared());
	
		
		// clear xml cache, check defaults
		resourceEntity.setCache(null);

		assertEquals(null, resourceEntity.getCache());
		assertEquals(false, javaContextCaching.isShared());
		assertEquals(false, ormContextCaching.isShared());
		assertEquals(false, ormContextCaching.isDefaultShared());
		assertEquals(null, ormContextCaching.getSpecifiedShared());
	
		
		// set metadataComplete to True, check defaults not from java

		ormContextEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(false, javaContextCaching.isShared());
		assertEquals(true, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(null, ormContextCaching.getSpecifiedShared());

		
		// set metadataComplete back to null, check defaults from java
		ormContextEntity.setSpecifiedMetadataComplete(null);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(false, javaContextCaching.isShared());
		assertEquals(false, ormContextCaching.isShared());
		assertEquals(false, ormContextCaching.isDefaultShared());
		assertEquals(null, ormContextCaching.getSpecifiedShared());
	}
	
	public void testModifyCacheShared() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(true, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(null, ormContextCaching.getSpecifiedShared());
		
		// set context cache size, check resource
		
		ormContextEntity.getCaching().setSpecifiedShared(Boolean.FALSE);
		assertEquals(Boolean.FALSE, resourceEntity.getCache().getShared());
		assertEquals(false, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(Boolean.FALSE, ormContextCaching.getSpecifiedShared());
				
		// set context cache size to null, check resource
		
		ormContextEntity.getCaching().setSpecifiedShared(null);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(true, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(null, ormContextCaching.getSpecifiedShared());
	}
	public void testSetSpecifiedSharedFalseUnsetsOtherCacheSettings() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		ormContextCaching.setSpecifiedType(EclipseLinkCacheType.HARD_WEAK);
		ormContextCaching.setSpecifiedSize(Integer.valueOf(500));
		ormContextCaching.setSpecifiedAlwaysRefresh(Boolean.FALSE);
		ormContextCaching.setSpecifiedRefreshOnlyIfNewer(Boolean.FALSE);
		ormContextCaching.setSpecifiedDisableHits(Boolean.FALSE);
		ormContextCaching.setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS);
		ormContextCaching.setSpecifiedExistenceType(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE);
		ormContextCaching.setExpiry(Integer.valueOf(8000));
		
		ormContextCaching.setSpecifiedShared(Boolean.FALSE);
		
		assertEquals(null, ormContextCaching.getSpecifiedType());
		assertEquals(null, ormContextCaching.getSpecifiedSize());
		assertEquals(null, ormContextCaching.getSpecifiedAlwaysRefresh());
		assertEquals(null, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());
		assertEquals(null, ormContextCaching.getSpecifiedDisableHits());
		assertEquals(null, ormContextCaching.getSpecifiedCoordinationType());
		assertEquals(null, ormContextCaching.getExpiry());
		
		
		//existence checking is the only thing that isn't unset when shared is set to false
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, ormContextCaching.getSpecifiedExistenceType());
		
		ormContextCaching.setSpecifiedShared(null);
		EclipseLinkExpiryTimeOfDay timeOfDayExpiry = ormContextCaching.addExpiryTimeOfDay();
		timeOfDayExpiry.setHour(Integer.valueOf(5));
		
		ormContextCaching.setSpecifiedShared(Boolean.FALSE);
		assertNull(ormContextCaching.getExpiryTimeOfDay());		
		assertEquals(Boolean.FALSE, resourceEntity.getCache().getShared());
		assertNull(resourceEntity.getCache().getExpiryTimeOfDay());
	}

	public void testUpdateExistenceChecking() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaEclipseLinkCaching javaContextCaching = ((JavaEclipseLinkEntity) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);


		// check defaults
		
		assertEquals(null, resourceEntity.getExistenceChecking());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, javaContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getDefaultExistenceType());
		assertEquals(null, ormContextCaching.getSpecifiedExistenceType());
		
		// set xml existence checking, check settings
		resourceEntity.setExistenceChecking(org.eclipse.jpt.eclipselink.core.resource.orm.ExistenceType.ASSUME_EXISTENCE);
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.orm.ExistenceType.ASSUME_EXISTENCE, resourceEntity.getExistenceChecking());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, javaContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_EXISTENCE, ormContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getDefaultExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_EXISTENCE, ormContextCaching.getSpecifiedExistenceType());

			
		// set java cache existence checking, check defaults
		
		javaContextCaching.setSpecifiedExistenceType(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.orm.ExistenceType.ASSUME_EXISTENCE, resourceEntity.getExistenceChecking());
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, javaContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_EXISTENCE, ormContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, ormContextCaching.getDefaultExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_EXISTENCE, ormContextCaching.getSpecifiedExistenceType());

		// clear xml existence checking, check defaults
		resourceEntity.setExistenceChecking(null);

		assertEquals(null, resourceEntity.getExistenceChecking());
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, javaContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, ormContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, ormContextCaching.getDefaultExistenceType());
		assertEquals(null, ormContextCaching.getSpecifiedExistenceType());	
		
		// set metadataComplete to True, check defaults not from java

		ormContextEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceEntity.getExistenceChecking());
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, javaContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getDefaultExistenceType());
		assertEquals(null, ormContextCaching.getSpecifiedExistenceType());

		ormContextEntity.setSpecifiedMetadataComplete(null);
	}
	
	public void testModifyExistenceChecking() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		// check defaults
		
		assertEquals(null, resourceEntity.getExistenceChecking());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getDefaultExistenceType());
		assertEquals(null, ormContextCaching.getSpecifiedExistenceType());
		
		// set context cache existence checking, check resource
		
		ormContextEntity.getCaching().setSpecifiedExistenceType(EclipseLinkExistenceType.ASSUME_EXISTENCE);
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.orm.ExistenceType.ASSUME_EXISTENCE, resourceEntity.getExistenceChecking());
		assertEquals(EclipseLinkExistenceType.ASSUME_EXISTENCE, ormContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getDefaultExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_EXISTENCE, ormContextCaching.getSpecifiedExistenceType());
				
		// set context existence checking to null, check resource
		
		ormContextEntity.getCaching().setSpecifiedExistenceType(null);
		
		assertEquals(null, resourceEntity.getExistenceChecking());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getDefaultExistenceType());
		assertEquals(null, ormContextCaching.getSpecifiedExistenceType());
	}
	
	public void testUpdateCacheExpiry() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaEclipseLinkCaching javaContextCaching = ((JavaEclipseLinkEntity) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);


		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(null, javaContextCaching.getExpiry());
		assertEquals(null, ormContextCaching.getExpiry());
		
		// set xml cache, check defaults
		resourceEntity.setCache(EclipseLinkOrmFactory.eINSTANCE.createXmlCache());
		assertEquals(null, resourceEntity.getCache().getExpiry());
		assertEquals(null, javaContextCaching.getExpiry());
		assertEquals(null, ormContextCaching.getExpiry());

		
		// set xml cache expiry, check settings
		resourceEntity.getCache().setExpiry(new Integer(45));
		assertEquals(new Integer(45), resourceEntity.getCache().getExpiry());
		assertEquals(null, javaContextCaching.getExpiry());
		assertEquals(new Integer(45), ormContextCaching.getExpiry());

			
		// set java cache expiry, check defaults
		
		javaContextCaching.setExpiry(new Integer(55));
		
		assertEquals(new Integer(45), resourceEntity.getCache().getExpiry());
		assertEquals(new Integer(55), javaContextCaching.getExpiry());
		assertEquals(new Integer(45), ormContextCaching.getExpiry());

		// clear xml cache expiry to null, check defaults
		resourceEntity.getCache().setExpiry(null);

		assertEquals(null, resourceEntity.getCache().getExpiry());
		assertEquals(new Integer(55), javaContextCaching.getExpiry());
		assertEquals(null, ormContextCaching.getExpiry());
	
		
		// clear xml cache, check defaults
		resourceEntity.setCache(null);

		assertEquals(null, resourceEntity.getCache());
		assertEquals(new Integer(55), javaContextCaching.getExpiry());
		assertEquals(null, ormContextCaching.getExpiry());
	
		
		// set metadataComplete to True, check defaults not from java

		ormContextEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(new Integer(55), javaContextCaching.getExpiry());
		assertEquals(null, ormContextCaching.getExpiry());

		
		// set metadataComplete back to null, check defaults from java
		ormContextEntity.setSpecifiedMetadataComplete(null);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(new Integer(55), javaContextCaching.getExpiry());
		assertEquals(null, ormContextCaching.getExpiry());
	}
	
	public void testModifyCacheExpiry() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(null, ormContextCaching.getExpiry());
		
		// set context cache expiry, check resource
		
		ormContextEntity.getCaching().setExpiry(new Integer(60));
		assertEquals(new Integer(60), resourceEntity.getCache().getExpiry());
		assertEquals(new Integer(60), ormContextCaching.getExpiry());
				
		// set context cache size to null, check resource
		
		ormContextEntity.getCaching().setExpiry(null);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(null, ormContextCaching.getExpiry());
	}
	
	public void testUpdateCacheExpiryTimeOfDay() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaEclipseLinkCaching javaContextCaching = ((JavaEclipseLinkEntity) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);


		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(null, javaContextCaching.getExpiryTimeOfDay());
		assertEquals(null, ormContextCaching.getExpiryTimeOfDay());
		
		// set xml cache, check defaults
		resourceEntity.setCache(EclipseLinkOrmFactory.eINSTANCE.createXmlCache());
		assertEquals(null, resourceEntity.getCache().getExpiryTimeOfDay());
		assertEquals(null, javaContextCaching.getExpiryTimeOfDay());
		assertEquals(null, ormContextCaching.getExpiryTimeOfDay());

		
		// set xml cache expiry, check settings
		resourceEntity.getCache().setExpiryTimeOfDay(EclipseLinkOrmFactory.eINSTANCE.createXmlTimeOfDay());
		resourceEntity.getCache().getExpiryTimeOfDay().setHour(new Integer(10));
		assertEquals(new Integer(10), resourceEntity.getCache().getExpiryTimeOfDay().getHour());
		assertEquals(null, javaContextCaching.getExpiryTimeOfDay());
		assertEquals(new Integer(10), ormContextCaching.getExpiryTimeOfDay().getHour());

			
		// set java cache expiry, check defaults
		
		javaContextCaching.addExpiryTimeOfDay();
		javaContextCaching.getExpiryTimeOfDay().setHour(new Integer(12));
		
		assertEquals(new Integer(10), resourceEntity.getCache().getExpiryTimeOfDay().getHour());
		assertEquals(new Integer(12), javaContextCaching.getExpiryTimeOfDay().getHour());
		assertEquals(new Integer(10), ormContextCaching.getExpiryTimeOfDay().getHour());

		// clear xml cache expiry to null, check defaults
		resourceEntity.getCache().setExpiryTimeOfDay(null);

		assertEquals(null, resourceEntity.getCache().getExpiryTimeOfDay());
		assertEquals(new Integer(12), javaContextCaching.getExpiryTimeOfDay().getHour());
		assertEquals(null, ormContextCaching.getExpiryTimeOfDay());
	
		
		// clear xml cache, check defaults
		resourceEntity.setCache(null);

		assertEquals(null, resourceEntity.getCache());
		assertEquals(new Integer(12), javaContextCaching.getExpiryTimeOfDay().getHour());
		assertEquals(null, ormContextCaching.getExpiryTimeOfDay());
	
		
		// set metadataComplete to True, check defaults not from java

		ormContextEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(new Integer(12), javaContextCaching.getExpiryTimeOfDay().getHour());
		assertEquals(null, ormContextCaching.getExpiryTimeOfDay());

		
		// set metadataComplete back to null, check defaults from java
		ormContextEntity.setSpecifiedMetadataComplete(null);
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(new Integer(12), javaContextCaching.getExpiryTimeOfDay().getHour());
		assertEquals(null, ormContextCaching.getExpiryTimeOfDay());
	}
	
	public void testModifyCacheExpiryTimeOfDay() throws Exception {
		createTestEntityForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextEntity.getCaching();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		// check defaults
		
		assertEquals(null, resourceEntity.getCache());
		assertEquals(null, ormContextCaching.getExpiryTimeOfDay());
		
		// set context cache expiry, check resource
		
		ormContextEntity.getCaching().addExpiryTimeOfDay().setHour(new Integer(12));
		assertEquals(new Integer(12), resourceEntity.getCache().getExpiryTimeOfDay().getHour());
		assertEquals(new Integer(12), ormContextCaching.getExpiryTimeOfDay().getHour());
				
		// set context expiry time of day minute, check resource
		
		ormContextEntity.getCaching().getExpiryTimeOfDay().setMinute(new Integer(35));
		
		assertEquals(new Integer(12), resourceEntity.getCache().getExpiryTimeOfDay().getHour());
		assertEquals(new Integer(12), ormContextCaching.getExpiryTimeOfDay().getHour());
		assertEquals(new Integer(35), resourceEntity.getCache().getExpiryTimeOfDay().getMinute());
		assertEquals(new Integer(35), ormContextCaching.getExpiryTimeOfDay().getMinute());
		
		// set context expiry time of day null, check resource
		
		ormContextEntity.getCaching().removeExpiryTimeOfDay();
		assertEquals(null, resourceEntity.getCache());
		assertEquals(null, ormContextCaching.getExpiryTimeOfDay());
	}

	public void testUpdateCustomConverters() throws Exception {
		createTestEntityForConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkConverterHolder ormContextConverterHolder = ormContextEntity.getConverterHolder();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		assertEquals(0, ormContextConverterHolder.customConvertersSize());
		assertEquals(0, resourceEntity.getConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add a converter to the resource model, check context model
		XmlConverter resourceConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlConverter();
		resourceEntity.getConverters().add(resourceConverter);
		resourceConverter.setClassName("Foo");
		resourceConverter.setName("myConverter");
		
		assertEquals(1, ormContextConverterHolder.customConvertersSize());
		ListIterator<EclipseLinkCustomConverter> ormContextConverters = ormContextConverterHolder.customConverters();
		EclipseLinkCustomConverter ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(1, resourceEntity.getConverters().size());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add another converter to the resource model, check context model
		XmlConverter resourceConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlConverter();
		resourceEntity.getConverters().add(0, resourceConverter2);
		resourceConverter2.setClassName("Foo2");
		resourceConverter2.setName("myConverter2");
		
		assertEquals(2, ormContextConverterHolder.customConvertersSize());
		ormContextConverters = ormContextConverterHolder.customConverters();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(2, resourceEntity.getConverters().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//move a converter in the resource model, check context model
		resourceEntity.getConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.customConvertersSize());
		ormContextConverters = ormContextConverterHolder.customConverters();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(2, resourceEntity.getConverters().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the resource model, check context model
		resourceEntity.getConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.customConvertersSize());
		ormContextConverters = ormContextConverterHolder.customConverters();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(1, resourceEntity.getConverters().size());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the resource model, check context model
		resourceEntity.getConverters().remove(resourceConverter2);
		
		assertEquals(0, ormContextConverterHolder.customConvertersSize());
		assertFalse(ormContextConverterHolder.customConverters().hasNext());
		assertEquals(0, resourceEntity.getConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
	}

	public void testModifyCustomConverters() throws Exception {
		createTestEntityForConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkConverterHolder ormContextConverterHolder = ormContextEntity.getConverterHolder();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		assertEquals(0, ormContextConverterHolder.customConvertersSize());
		assertEquals(0, resourceEntity.getConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add a converter to the context model, check resource model
		EclipseLinkCustomConverter contextConverter = ormContextConverterHolder.addCustomConverter(0);
		contextConverter.setConverterClass("Foo");
		contextConverter.setName("myConverter");
		
		assertEquals(1, resourceEntity.getConverters().size());
		assertEquals("Foo", resourceEntity.getConverters().get(0).getClassName());
		assertEquals("myConverter", resourceEntity.getConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.customConvertersSize());
		ListIterator<EclipseLinkCustomConverter> ormContextConverters = ormContextConverterHolder.customConverters();
		EclipseLinkCustomConverter ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add another converter to the context model, check resource model
		EclipseLinkCustomConverter contextConverter2 = ormContextConverterHolder.addCustomConverter(0);
		contextConverter2.setConverterClass("Foo2");
		contextConverter2.setName("myConverter2");
		
		assertEquals(2, resourceEntity.getConverters().size());
		assertEquals("Foo2", resourceEntity.getConverters().get(0).getClassName());
		assertEquals("myConverter2", resourceEntity.getConverters().get(0).getName());
		assertEquals("Foo", resourceEntity.getConverters().get(1).getClassName());
		assertEquals("myConverter", resourceEntity.getConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.customConvertersSize());
		ormContextConverters = ormContextConverterHolder.customConverters();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//move a converter in the context model, check resource model
		ormContextConverterHolder.moveCustomConverter(0, 1);
		
		assertEquals(2, resourceEntity.getConverters().size());
		assertEquals("Foo", resourceEntity.getConverters().get(0).getClassName());
		assertEquals("myConverter", resourceEntity.getConverters().get(0).getName());
		assertEquals("Foo2", resourceEntity.getConverters().get(1).getClassName());
		assertEquals("myConverter2", resourceEntity.getConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.customConvertersSize());
		ormContextConverters = ormContextConverterHolder.customConverters();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeCustomConverter(0);
		
		assertEquals(1, resourceEntity.getConverters().size());
		assertEquals("Foo2", resourceEntity.getConverters().get(0).getClassName());
		assertEquals("myConverter2", resourceEntity.getConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.customConvertersSize());
		ormContextConverters = ormContextConverterHolder.customConverters();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeCustomConverter(contextConverter2);
		
		assertEquals(0, ormContextConverterHolder.customConvertersSize());
		assertFalse(ormContextConverterHolder.customConverters().hasNext());
		assertEquals(0, resourceEntity.getConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
	}
	
	public void testUpdateTypeConverters() throws Exception {
		createTestEntityForTypeConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkConverterHolder ormContextConverterHolder = ormContextEntity.getConverterHolder();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		assertEquals(0, ormContextConverterHolder.typeConvertersSize());
		assertEquals(0, resourceEntity.getTypeConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add a converter to the resource model, check context model
		XmlTypeConverter resourceTypeConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverter();
		resourceEntity.getTypeConverters().add(resourceTypeConverter);
		resourceTypeConverter.setDataType("Foo");
		resourceTypeConverter.setName("myTypeConverter");
		
		assertEquals(1, ormContextConverterHolder.typeConvertersSize());
		ListIterator<EclipseLinkTypeConverter> ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		EclipseLinkTypeConverter ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		assertEquals(1, resourceEntity.getTypeConverters().size());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add another converter to the resource model, check context model
		XmlTypeConverter resourceTypeConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverter();
		resourceEntity.getTypeConverters().add(0, resourceTypeConverter2);
		resourceTypeConverter2.setDataType("Foo2");
		resourceTypeConverter2.setName("myTypeConverter2");
		
		assertEquals(2, ormContextConverterHolder.typeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		assertEquals(2, resourceEntity.getTypeConverters().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//move a converter in the resource model, check context model
		resourceEntity.getTypeConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.typeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		assertEquals(2, resourceEntity.getTypeConverters().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the resource model, check context model
		resourceEntity.getTypeConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.typeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		assertEquals(1, resourceEntity.getTypeConverters().size());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the resource model, check context model
		resourceEntity.getTypeConverters().remove(resourceTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.typeConvertersSize());
		assertFalse(ormContextConverterHolder.typeConverters().hasNext());
		assertEquals(0, resourceEntity.getTypeConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
	}

	public void testModifyTypeConverters() throws Exception {
		createTestEntityForTypeConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkConverterHolder ormContextConverterHolder = ormContextEntity.getConverterHolder();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		assertEquals(0, ormContextConverterHolder.typeConvertersSize());
		assertEquals(0, resourceEntity.getTypeConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add a converter to the context model, check resource model
		EclipseLinkTypeConverter contextTypeConverter = ormContextConverterHolder.addTypeConverter(0);
		contextTypeConverter.setDataType("Foo");
		contextTypeConverter.setName("myTypeConverter");
		
		assertEquals(1, resourceEntity.getTypeConverters().size());
		assertEquals("Foo", resourceEntity.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter", resourceEntity.getTypeConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.typeConvertersSize());
		ListIterator<EclipseLinkTypeConverter> ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		EclipseLinkTypeConverter ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add another converter to the context model, check resource model
		EclipseLinkTypeConverter contextTypeConverter2 = ormContextConverterHolder.addTypeConverter(0);
		contextTypeConverter2.setDataType("Foo2");
		contextTypeConverter2.setName("myTypeConverter2");
		
		assertEquals(2, resourceEntity.getTypeConverters().size());
		assertEquals("Foo2", resourceEntity.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter2", resourceEntity.getTypeConverters().get(0).getName());
		assertEquals("Foo", resourceEntity.getTypeConverters().get(1).getDataType());
		assertEquals("myTypeConverter", resourceEntity.getTypeConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.typeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//move a converter in the context model, check resource model
		ormContextConverterHolder.moveTypeConverter(0, 1);
		
		assertEquals(2, resourceEntity.getTypeConverters().size());
		assertEquals("Foo", resourceEntity.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter", resourceEntity.getTypeConverters().get(0).getName());
		assertEquals("Foo2", resourceEntity.getTypeConverters().get(1).getDataType());
		assertEquals("myTypeConverter2", resourceEntity.getTypeConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.typeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeTypeConverter(0);
		
		assertEquals(1, resourceEntity.getTypeConverters().size());
		assertEquals("Foo2", resourceEntity.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter2", resourceEntity.getTypeConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.typeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeTypeConverter(contextTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.typeConvertersSize());
		assertFalse(ormContextConverterHolder.typeConverters().hasNext());
		assertEquals(0, resourceEntity.getTypeConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
	}
	
	public void testUpdateObjectTypeConverters() throws Exception {
		createTestEntityForObjectTypeConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkConverterHolder ormContextConverterHolder = ormContextEntity.getConverterHolder();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		assertEquals(0, ormContextConverterHolder.objectTypeConvertersSize());
		assertEquals(0, resourceEntity.getObjectTypeConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add a converter to the resource model, check context model
		XmlObjectTypeConverter resourceObjectTypeConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverter();
		resourceEntity.getObjectTypeConverters().add(resourceObjectTypeConverter);
		resourceObjectTypeConverter.setDataType("Foo");
		resourceObjectTypeConverter.setName("myObjectTypeConverter");
		
		assertEquals(1, ormContextConverterHolder.objectTypeConvertersSize());
		ListIterator<EclipseLinkObjectTypeConverter> ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		EclipseLinkObjectTypeConverter ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		assertEquals(1, resourceEntity.getObjectTypeConverters().size());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add another converter to the resource model, check context model
		XmlObjectTypeConverter resourceObjectTypeConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverter();
		resourceEntity.getObjectTypeConverters().add(0, resourceObjectTypeConverter2);
		resourceObjectTypeConverter2.setDataType("Foo2");
		resourceObjectTypeConverter2.setName("myObjectTypeConverter2");
		
		assertEquals(2, ormContextConverterHolder.objectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		assertEquals(2, resourceEntity.getObjectTypeConverters().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//move a converter in the resource model, check context model
		resourceEntity.getObjectTypeConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.objectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		assertEquals(2, resourceEntity.getObjectTypeConverters().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the resource model, check context model
		resourceEntity.getObjectTypeConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.objectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		assertEquals(1, resourceEntity.getObjectTypeConverters().size());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the resource model, check context model
		resourceEntity.getObjectTypeConverters().remove(resourceObjectTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.objectTypeConvertersSize());
		assertFalse(ormContextConverterHolder.objectTypeConverters().hasNext());
		assertEquals(0, resourceEntity.getObjectTypeConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
	}

	public void testModifyObjectTypeConverters() throws Exception {
		createTestEntityForObjectTypeConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkConverterHolder ormContextConverterHolder = ormContextEntity.getConverterHolder();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		assertEquals(0, ormContextConverterHolder.objectTypeConvertersSize());
		assertEquals(0, resourceEntity.getObjectTypeConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add a converter to the context model, check resource model
		EclipseLinkObjectTypeConverter contextObjectTypeConverter = ormContextConverterHolder.addObjectTypeConverter(0);
		contextObjectTypeConverter.setDataType("Foo");
		contextObjectTypeConverter.setName("myObjectTypeConverter");
		
		assertEquals(1, resourceEntity.getObjectTypeConverters().size());
		assertEquals("Foo", resourceEntity.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter", resourceEntity.getObjectTypeConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.objectTypeConvertersSize());
		ListIterator<EclipseLinkObjectTypeConverter> ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		EclipseLinkObjectTypeConverter ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add another converter to the context model, check resource model
		EclipseLinkObjectTypeConverter contextObjectTypeConverter2 = ormContextConverterHolder.addObjectTypeConverter(0);
		contextObjectTypeConverter2.setDataType("Foo2");
		contextObjectTypeConverter2.setName("myObjectTypeConverter2");
		
		assertEquals(2, resourceEntity.getObjectTypeConverters().size());
		assertEquals("Foo2", resourceEntity.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter2", resourceEntity.getObjectTypeConverters().get(0).getName());
		assertEquals("Foo", resourceEntity.getObjectTypeConverters().get(1).getDataType());
		assertEquals("myObjectTypeConverter", resourceEntity.getObjectTypeConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.objectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//move a converter in the context model, check resource model
		ormContextConverterHolder.moveObjectTypeConverter(0, 1);
		
		assertEquals(2, resourceEntity.getObjectTypeConverters().size());
		assertEquals("Foo", resourceEntity.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter", resourceEntity.getObjectTypeConverters().get(0).getName());
		assertEquals("Foo2", resourceEntity.getObjectTypeConverters().get(1).getDataType());
		assertEquals("myObjectTypeConverter2", resourceEntity.getObjectTypeConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.objectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeObjectTypeConverter(0);
		
		assertEquals(1, resourceEntity.getObjectTypeConverters().size());
		assertEquals("Foo2", resourceEntity.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter2", resourceEntity.getObjectTypeConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.objectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeObjectTypeConverter(contextObjectTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.objectTypeConvertersSize());
		assertFalse(ormContextConverterHolder.objectTypeConverters().hasNext());
		assertEquals(0, resourceEntity.getObjectTypeConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
	}
	
	public void testUpdateStructConverters() throws Exception {
		createTestEntityForStructConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkConverterHolder ormContextConverterHolder = ormContextEntity.getConverterHolder();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		assertEquals(0, ormContextConverterHolder.structConvertersSize());
		assertEquals(0, resourceEntity.getStructConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add a converter to the resource model, check context model
		XmlStructConverter resourceStructConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverter();
		resourceEntity.getStructConverters().add(resourceStructConverter);
		resourceStructConverter.setConverter("Foo");
		resourceStructConverter.setName("myStructConverter");
		
		assertEquals(1, ormContextConverterHolder.structConvertersSize());
		ListIterator<EclipseLinkStructConverter> ormContextStructConverters = ormContextConverterHolder.structConverters();
		EclipseLinkStructConverter ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		assertEquals(1, resourceEntity.getStructConverters().size());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add another converter to the resource model, check context model
		XmlStructConverter resourceStructConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverter();
		resourceEntity.getStructConverters().add(0, resourceStructConverter2);
		resourceStructConverter2.setConverter("Foo2");
		resourceStructConverter2.setName("myStructConverter2");
		
		assertEquals(2, ormContextConverterHolder.structConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.structConverters();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		assertEquals(2, resourceEntity.getStructConverters().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//move a converter in the resource model, check context model
		resourceEntity.getStructConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.structConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.structConverters();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		assertEquals(2, resourceEntity.getStructConverters().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the resource model, check context model
		resourceEntity.getStructConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.structConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.structConverters();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		assertEquals(1, resourceEntity.getStructConverters().size());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the resource model, check context model
		resourceEntity.getStructConverters().remove(resourceStructConverter2);
		
		assertEquals(0, ormContextConverterHolder.structConvertersSize());
		assertFalse(ormContextConverterHolder.structConverters().hasNext());
		assertEquals(0, resourceEntity.getStructConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
	}

	public void testModifyStructConverters() throws Exception {
		createTestEntityForStructConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEntity ormContextEntity = (OrmEclipseLinkEntity) ormPersistentType.getMapping();
		EclipseLinkConverterHolder ormContextConverterHolder = ormContextEntity.getConverterHolder();
		XmlEntity resourceEntity = (XmlEntity) getXmlEntityMappings().getEntities().get(0);
		
		assertEquals(0, ormContextConverterHolder.structConvertersSize());
		assertEquals(0, resourceEntity.getStructConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add a converter to the context model, check resource model
		EclipseLinkStructConverter contextStructConverter = ormContextConverterHolder.addStructConverter(0);
		contextStructConverter.setConverterClass("Foo");
		contextStructConverter.setName("myStructConverter");
		
		assertEquals(1, resourceEntity.getStructConverters().size());
		assertEquals("Foo", resourceEntity.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter", resourceEntity.getStructConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.structConvertersSize());
		ListIterator<EclipseLinkStructConverter> ormContextStructConverters = ormContextConverterHolder.structConverters();
		EclipseLinkStructConverter ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add another converter to the context model, check resource model
		EclipseLinkStructConverter contextStructConverter2 = ormContextConverterHolder.addStructConverter(0);
		contextStructConverter2.setConverterClass("Foo2");
		contextStructConverter2.setName("myStructConverter2");
		
		assertEquals(2, resourceEntity.getStructConverters().size());
		assertEquals("Foo2", resourceEntity.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter2", resourceEntity.getStructConverters().get(0).getName());
		assertEquals("Foo", resourceEntity.getStructConverters().get(1).getConverter());
		assertEquals("myStructConverter", resourceEntity.getStructConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.structConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.structConverters();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//move a converter in the context model, check resource model
		ormContextConverterHolder.moveStructConverter(0, 1);
		
		assertEquals(2, resourceEntity.getStructConverters().size());
		assertEquals("Foo", resourceEntity.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter", resourceEntity.getStructConverters().get(0).getName());
		assertEquals("Foo2", resourceEntity.getStructConverters().get(1).getConverter());
		assertEquals("myStructConverter2", resourceEntity.getStructConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.structConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.structConverters();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeStructConverter(0);
		
		assertEquals(1, resourceEntity.getStructConverters().size());
		assertEquals("Foo2", resourceEntity.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter2", resourceEntity.getStructConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.structConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.structConverters();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeStructConverter(contextStructConverter2);
		
		assertEquals(0, ormContextConverterHolder.structConvertersSize());
		assertFalse(ormContextConverterHolder.structConverters().hasNext());
		assertEquals(0, resourceEntity.getStructConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));	
	}
}

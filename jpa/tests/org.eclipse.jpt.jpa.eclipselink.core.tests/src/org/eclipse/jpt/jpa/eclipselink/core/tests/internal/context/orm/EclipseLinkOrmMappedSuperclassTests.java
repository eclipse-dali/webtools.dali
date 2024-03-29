/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCacheCoordinationType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCacheType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTrackingType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkExistenceType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTrackingType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLinkOrmMappedSuperclassTests
	extends EclipseLinkContextModelTestCase
{
	public EclipseLinkOrmMappedSuperclassTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createTestMappedSuperclassForReadOnly() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS, EclipseLink.READ_ONLY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestMappedSuperclassForCustomizer() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS, EclipseLink.CUSTOMIZER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestMappedSuperclassForChangeTracking() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS, EclipseLink.CHANGE_TRACKING);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestMappedSuperclassForCaching() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
			}
		});
	}

	private ICompilationUnit createTestMappedSuperclassForConverters() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
			}
		});
	}

	private ICompilationUnit createTestMappedSuperclassForTypeConverters() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
			}
		});
	}

	private ICompilationUnit createTestMappedSuperclassForObjectTypeConverters() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
			}
		});
	}

	private ICompilationUnit createTestMappedSuperclassForStructConverters() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
			}
		});
	}
	
	public void testUpdateReadOnly() throws Exception {
		createTestMappedSuperclassForReadOnly();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkJavaMappedSuperclass javaContextMappedSuperclass = (EclipseLinkJavaMappedSuperclass) ormPersistentType.getJavaPersistentType().getMapping();
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		// check defaults
		
		assertNull(resourceMappedSuperclass.getReadOnly());
		assertFalse(javaContextMappedSuperclass.getReadOnly().isReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isDefaultReadOnly());
		assertNull(ormContextMappedSuperclass.getReadOnly().getSpecifiedReadOnly());
		
		// set xml read only to false, check override
		
		resourceMappedSuperclass.setReadOnly(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceMappedSuperclass.getReadOnly());
		assertFalse(javaContextMappedSuperclass.getReadOnly().isReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isDefaultReadOnly());
		assertEquals(Boolean.FALSE, ormContextMappedSuperclass.getReadOnly().getSpecifiedReadOnly());
		
		// set xml read only to true, check override
		
		resourceMappedSuperclass.setReadOnly(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceMappedSuperclass.getReadOnly());
		assertFalse(javaContextMappedSuperclass.getReadOnly().isReadOnly());
		assertTrue(ormContextMappedSuperclass.getReadOnly().isReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isDefaultReadOnly());
		assertEquals(Boolean.TRUE, ormContextMappedSuperclass.getReadOnly().getSpecifiedReadOnly());
		
		// clear xml read only, set java read only to true, check defaults
		
		resourceMappedSuperclass.setReadOnly(null);
		javaContextMappedSuperclass.getReadOnly().setSpecifiedReadOnly(Boolean.TRUE);
		
		assertNull(resourceMappedSuperclass.getReadOnly());
		assertTrue(javaContextMappedSuperclass.getReadOnly().isReadOnly());
		assertTrue(ormContextMappedSuperclass.getReadOnly().isReadOnly());
		assertTrue(ormContextMappedSuperclass.getReadOnly().isDefaultReadOnly());
		assertNull(ormContextMappedSuperclass.getReadOnly().getSpecifiedReadOnly());
		
		// set metadataComplete to True, check defaults not from java

		ormContextMappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertNull(resourceMappedSuperclass.getReadOnly());
		assertTrue(javaContextMappedSuperclass.getReadOnly().isReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isDefaultReadOnly());
		assertNull(ormContextMappedSuperclass.getReadOnly().getSpecifiedReadOnly());

		ormContextMappedSuperclass.setSpecifiedMetadataComplete(null);

		// set xml read only to false, check override
		
		resourceMappedSuperclass.setReadOnly(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceMappedSuperclass.getReadOnly());
		assertTrue(javaContextMappedSuperclass.getReadOnly().isReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isReadOnly());
		assertTrue(ormContextMappedSuperclass.getReadOnly().isDefaultReadOnly());
		assertEquals(Boolean.FALSE, ormContextMappedSuperclass.getReadOnly().getSpecifiedReadOnly());
		
		// set xml read only to true, check override
		
		resourceMappedSuperclass.setReadOnly(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceMappedSuperclass.getReadOnly());
		assertTrue(javaContextMappedSuperclass.getReadOnly().isReadOnly());
		assertTrue(ormContextMappedSuperclass.getReadOnly().isReadOnly());
		assertTrue(ormContextMappedSuperclass.getReadOnly().isDefaultReadOnly());
		assertEquals(Boolean.TRUE, ormContextMappedSuperclass.getReadOnly().getSpecifiedReadOnly());
		
		// clear xml read only, set java read only to false, check defaults
		
		resourceMappedSuperclass.setReadOnly(null);
		javaContextMappedSuperclass.getReadOnly().setSpecifiedReadOnly(Boolean.FALSE);
		
		assertNull(resourceMappedSuperclass.getReadOnly());
		assertFalse(javaContextMappedSuperclass.getReadOnly().isReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isDefaultReadOnly());
		assertNull(ormContextMappedSuperclass.getReadOnly().getSpecifiedReadOnly());
		
		// set xml read only to false, check override
		
		resourceMappedSuperclass.setReadOnly(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceMappedSuperclass.getReadOnly());
		assertFalse(javaContextMappedSuperclass.getReadOnly().isReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isDefaultReadOnly());
		assertEquals(Boolean.FALSE, ormContextMappedSuperclass.getReadOnly().getSpecifiedReadOnly());
		
		// set xml read only to true, check override
		
		resourceMappedSuperclass.setReadOnly(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceMappedSuperclass.getReadOnly());
		assertFalse(javaContextMappedSuperclass.getReadOnly().isReadOnly());
		assertTrue(ormContextMappedSuperclass.getReadOnly().isReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isDefaultReadOnly());
		assertEquals(Boolean.TRUE, ormContextMappedSuperclass.getReadOnly().getSpecifiedReadOnly());
	}
	
	public void testModifyReadOnly() throws Exception {
		createTestMappedSuperclassForReadOnly();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		// check defaults
		
		assertNull(resourceMappedSuperclass.getReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isDefaultReadOnly());
		assertNull(ormContextMappedSuperclass.getReadOnly().getSpecifiedReadOnly());
		
		// set context read only to true, check resource
		
		ormContextMappedSuperclass.getReadOnly().setSpecifiedReadOnly(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceMappedSuperclass.getReadOnly());
		assertTrue(ormContextMappedSuperclass.getReadOnly().isReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isDefaultReadOnly());
		assertEquals(Boolean.TRUE, ormContextMappedSuperclass.getReadOnly().getSpecifiedReadOnly());
		
		// set context read only to false, check resource
		
		ormContextMappedSuperclass.getReadOnly().setSpecifiedReadOnly(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceMappedSuperclass.getReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isDefaultReadOnly());
		assertEquals(Boolean.FALSE, ormContextMappedSuperclass.getReadOnly().getSpecifiedReadOnly());
		
		// set context read only to null, check resource
		
		ormContextMappedSuperclass.getReadOnly().setSpecifiedReadOnly(null);
		
		assertNull(resourceMappedSuperclass.getReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isReadOnly());
		assertFalse(ormContextMappedSuperclass.getReadOnly().isDefaultReadOnly());
		assertNull(ormContextMappedSuperclass.getReadOnly().getSpecifiedReadOnly());	
	}
	
	public void testUpdateCustomizerClass() throws Exception {
		createTestMappedSuperclassForCustomizer();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkJavaMappedSuperclass javaContextMappedSuperclass = (EclipseLinkJavaMappedSuperclass) ormPersistentType.getJavaPersistentType().getMapping();
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);


		// check defaults
		
		assertNull(resourceMappedSuperclass.getCustomizer());
		assertNull(javaContextMappedSuperclass.getCustomizer().getCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getSpecifiedCustomizerClass());
		
		// set xml customizer, check defaults
		resourceMappedSuperclass.setCustomizer(OrmFactory.eINSTANCE.createXmlClassReference());
		assertNull(resourceMappedSuperclass.getCustomizer().getClassName());
		assertNull(javaContextMappedSuperclass.getCustomizer().getCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getSpecifiedCustomizerClass());

		
		// set xml customizer class, check override
		
		resourceMappedSuperclass.getCustomizer().setClassName("foo");
		
		assertEquals("foo", resourceMappedSuperclass.getCustomizer().getClassName());
		assertNull(javaContextMappedSuperclass.getCustomizer().getCustomizerClass());
		assertEquals("foo", ormContextMappedSuperclass.getCustomizer().getCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getDefaultCustomizerClass());
		assertEquals("foo", ormContextMappedSuperclass.getCustomizer().getSpecifiedCustomizerClass());
		
		// clear xml customizer class, set java customizer class, check defaults
		
		resourceMappedSuperclass.getCustomizer().setClassName(null);
		javaContextMappedSuperclass.getCustomizer().setSpecifiedCustomizerClass("bar");
		
		assertNull(resourceMappedSuperclass.getCustomizer().getClassName());
		assertEquals("bar", javaContextMappedSuperclass.getCustomizer().getCustomizerClass());
		assertEquals("bar", ormContextMappedSuperclass.getCustomizer().getCustomizerClass());
		assertEquals("bar", ormContextMappedSuperclass.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getSpecifiedCustomizerClass());

		// set metadataComplete to True, check defaults not from java

		ormContextMappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertNull(resourceMappedSuperclass.getCustomizer().getClassName());
		assertEquals("bar", javaContextMappedSuperclass.getCustomizer().getCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getSpecifiedCustomizerClass());

		ormContextMappedSuperclass.setSpecifiedMetadataComplete(null);
		
		// set xml customizer class, check override
		
		resourceMappedSuperclass.getCustomizer().setClassName("foo");
		
		assertEquals("foo", resourceMappedSuperclass.getCustomizer().getClassName());
		assertEquals("bar", javaContextMappedSuperclass.getCustomizer().getCustomizerClass());
		assertEquals("foo", ormContextMappedSuperclass.getCustomizer().getCustomizerClass());
		assertEquals("bar", ormContextMappedSuperclass.getCustomizer().getDefaultCustomizerClass());
		assertEquals("foo", ormContextMappedSuperclass.getCustomizer().getSpecifiedCustomizerClass());

		//set xml customizer null
		javaContextMappedSuperclass.getCustomizer().setSpecifiedCustomizerClass(null);
		resourceMappedSuperclass.setCustomizer(null);
		assertNull(resourceMappedSuperclass.getCustomizer());
		assertNull(javaContextMappedSuperclass.getCustomizer().getCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getSpecifiedCustomizerClass());

	}
	
	public void testModifyCustomizerClass() throws Exception {
		createTestMappedSuperclassForCustomizer();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		// check defaults
		
		assertNull(resourceMappedSuperclass.getCustomizer());
		assertNull(ormContextMappedSuperclass.getCustomizer().getCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getSpecifiedCustomizerClass());
		
		// set context customizer, check resource
		
		ormContextMappedSuperclass.getCustomizer().setSpecifiedCustomizerClass("foo");
		
		assertEquals("foo", resourceMappedSuperclass.getCustomizer().getClassName());
		assertEquals("foo", ormContextMappedSuperclass.getCustomizer().getCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getDefaultCustomizerClass());
		assertEquals("foo", ormContextMappedSuperclass.getCustomizer().getSpecifiedCustomizerClass());
				
		// set context customizer to null, check resource
		
		ormContextMappedSuperclass.getCustomizer().setSpecifiedCustomizerClass(null);
		
		assertNull(resourceMappedSuperclass.getCustomizer());
		assertNull(ormContextMappedSuperclass.getCustomizer().getCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextMappedSuperclass.getCustomizer().getSpecifiedCustomizerClass());
	}
	
	public void testUpdateChangeTracking() throws Exception {
		createTestMappedSuperclassForChangeTracking();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkJavaMappedSuperclass javaContextMappedSuperclass = (EclipseLinkJavaMappedSuperclass) ormPersistentType.getJavaPersistentType().getMapping();
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		// check defaults
		
		assertNull(resourceMappedSuperclass.getChangeTracking());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, javaContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getDefaultType());
		assertNull(ormContextMappedSuperclass.getChangeTracking().getSpecifiedType());
		
		// set xml type to ATTRIBUTE, check context
		
		resourceMappedSuperclass.setChangeTracking(EclipseLinkOrmFactory.eINSTANCE.createXmlChangeTracking());
		resourceMappedSuperclass.getChangeTracking().setType(XmlChangeTrackingType.ATTRIBUTE);
		
		assertEquals(XmlChangeTrackingType.ATTRIBUTE, resourceMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, javaContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextMappedSuperclass.getChangeTracking().getSpecifiedType());
		
		// set xml type to OBJECT, check context
		
		resourceMappedSuperclass.getChangeTracking().setType(XmlChangeTrackingType.OBJECT);
		
		assertEquals(XmlChangeTrackingType.OBJECT, resourceMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, javaContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextMappedSuperclass.getChangeTracking().getSpecifiedType());
		
		// set xml type to DEFERRED, check context
		
		resourceMappedSuperclass.getChangeTracking().setType(XmlChangeTrackingType.DEFERRED);
		
		assertEquals(XmlChangeTrackingType.DEFERRED, resourceMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, javaContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, ormContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, ormContextMappedSuperclass.getChangeTracking().getSpecifiedType());
		
		// set xml type to AUTO, check context
		
		resourceMappedSuperclass.getChangeTracking().setType(XmlChangeTrackingType.AUTO);
		
		assertEquals(XmlChangeTrackingType.AUTO, resourceMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, javaContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getSpecifiedType());
		
		// clear xml change tracking, set java change tracking, check defaults
		
		resourceMappedSuperclass.setChangeTracking(null);
		javaContextMappedSuperclass.getChangeTracking().setSpecifiedType(EclipseLinkChangeTrackingType.ATTRIBUTE);
		
		assertNull(resourceMappedSuperclass.getChangeTracking());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, javaContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextMappedSuperclass.getChangeTracking().getDefaultType());
		assertNull(ormContextMappedSuperclass.getChangeTracking().getSpecifiedType());
		
		// set metadataComplete to True, check defaults not from java

		ormContextMappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertNull(resourceMappedSuperclass.getChangeTracking());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, javaContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getDefaultType());
		assertNull(ormContextMappedSuperclass.getChangeTracking().getSpecifiedType());
		
		// unset metadataComplete, set xml change tracking to OBJECT, check context
		
		ormContextMappedSuperclass.setSpecifiedMetadataComplete(null);
		resourceMappedSuperclass.setChangeTracking(EclipseLinkOrmFactory.eINSTANCE.createXmlChangeTracking());
		resourceMappedSuperclass.getChangeTracking().setType(XmlChangeTrackingType.OBJECT);
		
		assertEquals(XmlChangeTrackingType.OBJECT, resourceMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, javaContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextMappedSuperclass.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextMappedSuperclass.getChangeTracking().getSpecifiedType());
	}
	
	public void testModifyChangeTracking() throws Exception  {
		createTestMappedSuperclassForChangeTracking();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		// check defaults
		
		assertNull(resourceMappedSuperclass.getChangeTracking());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getDefaultType());
		assertNull(ormContextMappedSuperclass.getChangeTracking().getSpecifiedType());
		
		// set context change tracking to ATTRIBUTE, check resource
		
		ormContextMappedSuperclass.getChangeTracking().setSpecifiedType(EclipseLinkChangeTrackingType.ATTRIBUTE);
		
		assertEquals(XmlChangeTrackingType.ATTRIBUTE, resourceMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextMappedSuperclass.getChangeTracking().getSpecifiedType());
				
		// set context change tracking to OBJECT, check resource
		
		ormContextMappedSuperclass.getChangeTracking().setSpecifiedType(EclipseLinkChangeTrackingType.OBJECT);
		
		assertEquals(XmlChangeTrackingType.OBJECT, resourceMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextMappedSuperclass.getChangeTracking().getSpecifiedType());
				
		// set context change tracking to DEFERRED, check resource
		
		ormContextMappedSuperclass.getChangeTracking().setSpecifiedType(EclipseLinkChangeTrackingType.DEFERRED);
		
		assertEquals(XmlChangeTrackingType.DEFERRED, resourceMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, ormContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, ormContextMappedSuperclass.getChangeTracking().getSpecifiedType());
				
		// set context change tracking to AUTO, check resource
		
		ormContextMappedSuperclass.getChangeTracking().setSpecifiedType(EclipseLinkChangeTrackingType.AUTO);
		
		assertEquals(XmlChangeTrackingType.AUTO, resourceMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getSpecifiedType());
				
		// set context change tracking to null, check resource
		
		ormContextMappedSuperclass.getChangeTracking().setSpecifiedType(null);
		
		assertNull(resourceMappedSuperclass.getChangeTracking());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextMappedSuperclass.getChangeTracking().getDefaultType());
		assertNull(ormContextMappedSuperclass.getChangeTracking().getSpecifiedType());
	}
	
	public void testUpdateCacheType() throws Exception {
		createTestMappedSuperclassForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkJavaCaching javaContextCaching = ((EclipseLinkJavaMappedSuperclass) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextMappedSuperclass.getCaching();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);


		// check defaults
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, javaContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(null, ormContextCaching.getSpecifiedType());
		
		// set xml cache, check defaults
		resourceMappedSuperclass.setCache(EclipseLinkOrmFactory.eINSTANCE.createXmlCache());
		assertEquals(null, resourceMappedSuperclass.getCache().getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, javaContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(null, ormContextCaching.getSpecifiedType());

		
		// set xml cache type, check settings
		resourceMappedSuperclass.getCache().setType(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheType.FULL);
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheType.FULL, resourceMappedSuperclass.getCache().getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, javaContextCaching.getType());
		assertEquals(EclipseLinkCacheType.FULL, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(EclipseLinkCacheType.FULL, ormContextCaching.getSpecifiedType());

			
		// set java cache type, check defaults
		
		javaContextCaching.setSpecifiedType(EclipseLinkCacheType.WEAK);
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheType.FULL, resourceMappedSuperclass.getCache().getType());
		assertEquals(EclipseLinkCacheType.WEAK, javaContextCaching.getType());
		assertEquals(EclipseLinkCacheType.FULL, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(EclipseLinkCacheType.FULL, ormContextCaching.getSpecifiedType());

		// clear xml cache type, check defaults
		resourceMappedSuperclass.getCache().setType(null);

		assertEquals(null, resourceMappedSuperclass.getCache().getType());
		assertEquals(EclipseLinkCacheType.WEAK, javaContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(null, ormContextCaching.getSpecifiedType());
	
		
		// clear xml cache, check defaults
		resourceMappedSuperclass.setCache(null);

		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(EclipseLinkCacheType.WEAK, javaContextCaching.getType());
		assertEquals(EclipseLinkCacheType.WEAK, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.WEAK, ormContextCaching.getDefaultType());
		assertEquals(null, ormContextCaching.getSpecifiedType());
	
		
		// set metadataComplete to True, check defaults not from java

		ormContextMappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(EclipseLinkCacheType.WEAK, javaContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(null, ormContextCaching.getSpecifiedType());

		ormContextMappedSuperclass.setSpecifiedMetadataComplete(null);
	}
	
	public void testModifyCacheType() throws Exception {
		createTestMappedSuperclassForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextMappedSuperclass.getCaching();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		// check defaults
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(null, ormContextCaching.getSpecifiedType());
		
		// set context cache type, check resource
		
		ormContextMappedSuperclass.getCaching().setSpecifiedType(EclipseLinkCacheType.HARD_WEAK);
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheType.HARD_WEAK, resourceMappedSuperclass.getCache().getType());
		assertEquals(EclipseLinkCacheType.HARD_WEAK, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(EclipseLinkCacheType.HARD_WEAK, ormContextCaching.getSpecifiedType());
				
		// set context customizer to null, check resource
		
		ormContextMappedSuperclass.getCaching().setSpecifiedType(null);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getType());
		assertEquals(EclipseLinkCacheType.SOFT_WEAK, ormContextCaching.getDefaultType());
		assertEquals(null, ormContextCaching.getSpecifiedType());
	}

	public void testUpdateCacheCoordinationType() throws Exception {
		createTestMappedSuperclassForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkJavaCaching javaContextCaching = ((EclipseLinkJavaMappedSuperclass) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextMappedSuperclass.getCaching();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);


		// check defaults
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, javaContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(null, ormContextCaching.getSpecifiedCoordinationType());
		
		// set xml cache, check defaults
		resourceMappedSuperclass.setCache(EclipseLinkOrmFactory.eINSTANCE.createXmlCache());
		assertEquals(null, resourceMappedSuperclass.getCache().getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, javaContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(null, ormContextCaching.getSpecifiedCoordinationType());

		
		// set xml cache type, check settings
		resourceMappedSuperclass.getCache().setCoordinationType(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS);
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, resourceMappedSuperclass.getCache().getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, javaContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, ormContextCaching.getSpecifiedCoordinationType());

			
		// set java cache type, check defaults
		
		javaContextCaching.setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES);
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, resourceMappedSuperclass.getCache().getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, javaContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.INVALIDATE_CHANGED_OBJECTS, ormContextCaching.getSpecifiedCoordinationType());

		// clear xml cache type, check defaults
		resourceMappedSuperclass.getCache().setCoordinationType(null);

		assertEquals(null, resourceMappedSuperclass.getCache().getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, javaContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(null, ormContextCaching.getSpecifiedCoordinationType());
	
		
		// clear xml cache, check defaults
		resourceMappedSuperclass.setCache(null);

		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, javaContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(null, ormContextCaching.getSpecifiedCoordinationType());
	
		
		// set metadataComplete to True, check defaults not from java

		ormContextMappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, javaContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(null, ormContextCaching.getSpecifiedCoordinationType());

		ormContextMappedSuperclass.setSpecifiedMetadataComplete(null);
	}
	
	public void testModifyCacheCoordinationType() throws Exception {
		createTestMappedSuperclassForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextMappedSuperclass.getCaching();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		// check defaults
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(null, ormContextCaching.getSpecifiedCoordinationType());
		
		// set context cache coordination type, check resource
		
		ormContextMappedSuperclass.getCaching().setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES);
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, resourceMappedSuperclass.getCache().getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES, ormContextCaching.getSpecifiedCoordinationType());
				
		// set context coordination type to null, check resource
		
		ormContextMappedSuperclass.getCaching().setSpecifiedCoordinationType(null);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getCoordinationType());
		assertEquals(EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES, ormContextCaching.getDefaultCoordinationType());
		assertEquals(null, ormContextCaching.getSpecifiedCoordinationType());
	}

	
	public void testUpdateCacheSize() throws Exception {
		createTestMappedSuperclassForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkJavaCaching javaContextCaching = ((EclipseLinkJavaMappedSuperclass) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextMappedSuperclass.getCaching();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);


		// check defaults
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(100, javaContextCaching.getSize());
		assertEquals(100, ormContextCaching.getSize());
		assertEquals(100, ormContextCaching.getDefaultSize());
		assertEquals(null, ormContextCaching.getSpecifiedSize());
		
		// set xml cache, check defaults
		resourceMappedSuperclass.setCache(EclipseLinkOrmFactory.eINSTANCE.createXmlCache());
		assertEquals(null, resourceMappedSuperclass.getCache().getSize());
		assertEquals(100, javaContextCaching.getSize());
		assertEquals(100, ormContextCaching.getSize());
		assertEquals(100, ormContextCaching.getDefaultSize());
		assertEquals(null, ormContextCaching.getSpecifiedSize());

		
		// set xml cache size, check settings
		resourceMappedSuperclass.getCache().setSize(new Integer(105));
		assertEquals(new Integer(105), resourceMappedSuperclass.getCache().getSize());
		assertEquals(100, javaContextCaching.getSize());
		assertEquals(105, ormContextCaching.getSize());
		assertEquals(100, ormContextCaching.getDefaultSize());
		assertEquals(new Integer(105), ormContextCaching.getSpecifiedSize());

			
		// set java cache size, check defaults
		
		javaContextCaching.setSpecifiedSize(new Integer(50));
		
		assertEquals(new Integer(105), resourceMappedSuperclass.getCache().getSize());
		assertEquals(50, javaContextCaching.getSize());
		assertEquals(105, ormContextCaching.getSize());
		assertEquals(100, ormContextCaching.getDefaultSize());
		assertEquals(new Integer(105), ormContextCaching.getSpecifiedSize());

		// clear xml cache size, check defaults
		resourceMappedSuperclass.getCache().setSize(null);

		assertEquals(null, resourceMappedSuperclass.getCache().getSize());
		assertEquals(50, javaContextCaching.getSize());
		assertEquals(100, ormContextCaching.getSize());
		assertEquals(100, ormContextCaching.getDefaultSize());
		assertEquals(null, ormContextCaching.getSpecifiedSize());
	
		
		// clear xml cache, check defaults
		resourceMappedSuperclass.setCache(null);

		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(50, javaContextCaching.getSize());
		assertEquals(50, ormContextCaching.getSize());
		assertEquals(50, ormContextCaching.getDefaultSize());
		assertEquals(null, ormContextCaching.getSpecifiedSize());
	
		
		// set metadataComplete to True, check defaults not from java

		ormContextMappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(50, javaContextCaching.getSize());
		assertEquals(100, ormContextCaching.getSize());
		assertEquals(100, ormContextCaching.getDefaultSize());
		assertEquals(null, ormContextCaching.getSpecifiedSize());

		ormContextMappedSuperclass.setSpecifiedMetadataComplete(null);
	}
	
	public void testModifyCacheSize() throws Exception {
		createTestMappedSuperclassForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextMappedSuperclass.getCaching();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		// check defaults
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(100, ormContextCaching.getSize());
		assertEquals(100, ormContextCaching.getDefaultSize());
		assertEquals(null, ormContextCaching.getSpecifiedSize());
		
		// set context cache size, check resource
		
		ormContextMappedSuperclass.getCaching().setSpecifiedSize(new Integer(50));
		assertEquals(new Integer(50), resourceMappedSuperclass.getCache().getSize());
		assertEquals(50, ormContextCaching.getSize());
		assertEquals(100, ormContextCaching.getDefaultSize());
		assertEquals(new Integer(50), ormContextCaching.getSpecifiedSize());
				
		// set context cache size to null, check resource
		
		ormContextMappedSuperclass.getCaching().setSpecifiedSize(null);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(100, ormContextCaching.getSize());
		assertEquals(100, ormContextCaching.getDefaultSize());
		assertEquals(null, ormContextCaching.getSpecifiedSize());
	}

	public void testUpdateCacheAlwaysRefresh() throws Exception {
		createTestMappedSuperclassForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkJavaCaching javaContextCaching = ((EclipseLinkJavaMappedSuperclass) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextMappedSuperclass.getCaching();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);


		// check defaults
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(false, javaContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(null, ormContextCaching.getSpecifiedAlwaysRefresh());
		
		// set xml cache, check defaults
		resourceMappedSuperclass.setCache(EclipseLinkOrmFactory.eINSTANCE.createXmlCache());
		assertEquals(null, resourceMappedSuperclass.getCache().getAlwaysRefresh());
		assertEquals(false, javaContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(null, ormContextCaching.getSpecifiedAlwaysRefresh());

		
		// set xml cache always refresh, check settings
		resourceMappedSuperclass.getCache().setAlwaysRefresh(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceMappedSuperclass.getCache().getAlwaysRefresh());
		assertEquals(false, javaContextCaching.isAlwaysRefresh());
		assertEquals(true, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedAlwaysRefresh());

			
		// set java cache always refresh, check defaults
		
		javaContextCaching.setSpecifiedAlwaysRefresh(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceMappedSuperclass.getCache().getAlwaysRefresh());
		assertEquals(true, javaContextCaching.isAlwaysRefresh());
		assertEquals(true, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedAlwaysRefresh());

		// set xml cache always refresh to false
		resourceMappedSuperclass.getCache().setAlwaysRefresh(Boolean.FALSE);
		assertEquals(Boolean.FALSE, resourceMappedSuperclass.getCache().getAlwaysRefresh());
		assertEquals(true, javaContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(Boolean.FALSE, ormContextCaching.getSpecifiedAlwaysRefresh());

		// clear xml cache always refresh, check defaults
		resourceMappedSuperclass.getCache().setAlwaysRefresh(null);

		assertEquals(null, resourceMappedSuperclass.getCache().getAlwaysRefresh());
		assertEquals(true, javaContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(null, ormContextCaching.getSpecifiedAlwaysRefresh());
	
		
		// clear xml cache, check defaults
		resourceMappedSuperclass.setCache(null);

		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(true, javaContextCaching.isAlwaysRefresh());
		assertEquals(true, ormContextCaching.isAlwaysRefresh());
		assertEquals(true, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(null, ormContextCaching.getSpecifiedAlwaysRefresh());
	
		
		// set metadataComplete to True, check defaults not from java

		ormContextMappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(true, javaContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(null, ormContextCaching.getSpecifiedAlwaysRefresh());

		
		// set metadataComplete back to null, check defaults from java
		ormContextMappedSuperclass.setSpecifiedMetadataComplete(null);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(true, javaContextCaching.isAlwaysRefresh());
		assertEquals(true, ormContextCaching.isAlwaysRefresh());
		assertEquals(true, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(null, ormContextCaching.getSpecifiedAlwaysRefresh());
	}
	
	public void testModifyCacheAlwaysRefresh() throws Exception {
		createTestMappedSuperclassForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextMappedSuperclass.getCaching();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		// check defaults
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(false, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(null, ormContextCaching.getSpecifiedAlwaysRefresh());
		
		// set context cache size, check resource
		
		ormContextMappedSuperclass.getCaching().setSpecifiedAlwaysRefresh(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceMappedSuperclass.getCache().getAlwaysRefresh());
		assertEquals(true, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedAlwaysRefresh());
				
		// set context cache size to null, check resource
		
		ormContextMappedSuperclass.getCaching().setSpecifiedAlwaysRefresh(null);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(false, ormContextCaching.isAlwaysRefresh());
		assertEquals(false, ormContextCaching.isDefaultAlwaysRefresh());
		assertEquals(null, ormContextCaching.getSpecifiedAlwaysRefresh());
	}
	
	public void testUpdateCacheRefreshOnlyIfNewer() throws Exception {
		createTestMappedSuperclassForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkJavaCaching javaContextCaching = ((EclipseLinkJavaMappedSuperclass) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextMappedSuperclass.getCaching();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);


		// check defaults
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(false, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(null, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());
		
		// set xml cache, check defaults
		resourceMappedSuperclass.setCache(EclipseLinkOrmFactory.eINSTANCE.createXmlCache());
		assertEquals(null, resourceMappedSuperclass.getCache().getRefreshOnlyIfNewer());
		assertEquals(false, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(null, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());

		
		// set xml cache size, check settings
		resourceMappedSuperclass.getCache().setRefreshOnlyIfNewer(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceMappedSuperclass.getCache().getRefreshOnlyIfNewer());
		assertEquals(false, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(true, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());

			
		// set java cache size, check defaults
		
		javaContextCaching.setSpecifiedRefreshOnlyIfNewer(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceMappedSuperclass.getCache().getRefreshOnlyIfNewer());
		assertEquals(true, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(true, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());

		// set xml cache always refresh to false
		resourceMappedSuperclass.getCache().setRefreshOnlyIfNewer(Boolean.FALSE);
		assertEquals(Boolean.FALSE, resourceMappedSuperclass.getCache().getRefreshOnlyIfNewer());
		assertEquals(true, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(Boolean.FALSE, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());

		// clear xml cache always refresh, check defaults
		resourceMappedSuperclass.getCache().setRefreshOnlyIfNewer(null);

		assertEquals(null, resourceMappedSuperclass.getCache().getRefreshOnlyIfNewer());
		assertEquals(true, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(null, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());
	
		
		// clear xml cache, check defaults
		resourceMappedSuperclass.setCache(null);

		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(true, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(true, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(true, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(null, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());
	
		
		// set metadataComplete to True, check defaults not from java

		ormContextMappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(true, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(null, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());

		
		// set metadataComplete back to null, check defaults from java
		ormContextMappedSuperclass.setSpecifiedMetadataComplete(null);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(true, javaContextCaching.isRefreshOnlyIfNewer());
		assertEquals(true, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(true, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(null, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());
	}
	
	public void testModifyCacheRefreshOnlyIfNewer() throws Exception {
		createTestMappedSuperclassForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextMappedSuperclass.getCaching();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		// check defaults
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(false, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(null, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());
		
		// set context cache size, check resource
		
		ormContextMappedSuperclass.getCaching().setSpecifiedRefreshOnlyIfNewer(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceMappedSuperclass.getCache().getRefreshOnlyIfNewer());
		assertEquals(true, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());
				
		// set context cache size to null, check resource
		
		ormContextMappedSuperclass.getCaching().setSpecifiedRefreshOnlyIfNewer(null);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(false, ormContextCaching.isRefreshOnlyIfNewer());
		assertEquals(false, ormContextCaching.isDefaultRefreshOnlyIfNewer());
		assertEquals(null, ormContextCaching.getSpecifiedRefreshOnlyIfNewer());
	}
	
	public void testUpdateCacheDisableHits() throws Exception {
		createTestMappedSuperclassForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkJavaCaching javaContextCaching = ((EclipseLinkJavaMappedSuperclass) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextMappedSuperclass.getCaching();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);


		// check defaults
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(false, javaContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(null, ormContextCaching.getSpecifiedDisableHits());
		
		// set xml cache, check defaults
		resourceMappedSuperclass.setCache(EclipseLinkOrmFactory.eINSTANCE.createXmlCache());
		assertEquals(null, resourceMappedSuperclass.getCache().getDisableHits());
		assertEquals(false, javaContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(null, ormContextCaching.getSpecifiedDisableHits());

		
		// set xml cache size, check settings
		resourceMappedSuperclass.getCache().setDisableHits(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceMappedSuperclass.getCache().getDisableHits());
		assertEquals(false, javaContextCaching.isDisableHits());
		assertEquals(true, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedDisableHits());

			
		// set java cache size, check defaults
		
		javaContextCaching.setSpecifiedDisableHits(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceMappedSuperclass.getCache().getDisableHits());
		assertEquals(true, javaContextCaching.isDisableHits());
		assertEquals(true, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedDisableHits());

		// set xml cache always refresh to false
		resourceMappedSuperclass.getCache().setDisableHits(Boolean.FALSE);
		assertEquals(Boolean.FALSE, resourceMappedSuperclass.getCache().getDisableHits());
		assertEquals(true, javaContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(Boolean.FALSE, ormContextCaching.getSpecifiedDisableHits());

		// clear xml cache always refresh, check defaults
		resourceMappedSuperclass.getCache().setDisableHits(null);

		assertEquals(null, resourceMappedSuperclass.getCache().getDisableHits());
		assertEquals(true, javaContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(null, ormContextCaching.getSpecifiedDisableHits());
	
		
		// clear xml cache, check defaults
		resourceMappedSuperclass.setCache(null);

		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(true, javaContextCaching.isDisableHits());
		assertEquals(true, ormContextCaching.isDisableHits());
		assertEquals(true, ormContextCaching.isDefaultDisableHits());
		assertEquals(null, ormContextCaching.getSpecifiedDisableHits());
	
		
		// set metadataComplete to True, check defaults not from java

		ormContextMappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(true, javaContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(null, ormContextCaching.getSpecifiedDisableHits());

		
		// set metadataComplete back to null, check defaults from java
		ormContextMappedSuperclass.setSpecifiedMetadataComplete(null);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(true, javaContextCaching.isDisableHits());
		assertEquals(true, ormContextCaching.isDisableHits());
		assertEquals(true, ormContextCaching.isDefaultDisableHits());
		assertEquals(null, ormContextCaching.getSpecifiedDisableHits());
	}
	
	public void testModifyCacheDisableHits() throws Exception {
		createTestMappedSuperclassForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextMappedSuperclass.getCaching();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		// check defaults
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(false, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(null, ormContextCaching.getSpecifiedDisableHits());
		
		// set context cache size, check resource
		
		ormContextMappedSuperclass.getCaching().setSpecifiedDisableHits(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceMappedSuperclass.getCache().getDisableHits());
		assertEquals(true, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedDisableHits());
				
		// set context cache size to null, check resource
		
		ormContextMappedSuperclass.getCaching().setSpecifiedDisableHits(null);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(false, ormContextCaching.isDisableHits());
		assertEquals(false, ormContextCaching.isDefaultDisableHits());
		assertEquals(null, ormContextCaching.getSpecifiedDisableHits());
	}
	
	public void testUpdateCacheShared() throws Exception {
		createTestMappedSuperclassForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkJavaCaching javaContextCaching = ((EclipseLinkJavaMappedSuperclass) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextMappedSuperclass.getCaching();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);


		// check defaults
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(true, javaContextCaching.isShared());
		assertEquals(true, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(null, ormContextCaching.getSpecifiedShared());
		
		// set xml cache, check defaults
		resourceMappedSuperclass.setCache(EclipseLinkOrmFactory.eINSTANCE.createXmlCache());
		assertEquals(null, resourceMappedSuperclass.getCache().getShared());
		assertEquals(true, javaContextCaching.isShared());
		assertEquals(true, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(null, ormContextCaching.getSpecifiedShared());

		
		// set xml cache size, check settings
		resourceMappedSuperclass.getCache().setShared(Boolean.FALSE);
		assertEquals(Boolean.FALSE, resourceMappedSuperclass.getCache().getShared());
		assertEquals(true, javaContextCaching.isShared());
		assertEquals(false, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(Boolean.FALSE, ormContextCaching.getSpecifiedShared());

			
		// set java cache size, check defaults
		
		javaContextCaching.setSpecifiedShared(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceMappedSuperclass.getCache().getShared());
		assertEquals(false, javaContextCaching.isShared());
		assertEquals(false, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(Boolean.FALSE, ormContextCaching.getSpecifiedShared());

		// set xml cache always refresh to false
		resourceMappedSuperclass.getCache().setShared(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceMappedSuperclass.getCache().getShared());
		assertEquals(false, javaContextCaching.isShared());
		assertEquals(true, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(Boolean.TRUE, ormContextCaching.getSpecifiedShared());

		// clear xml cache always refresh, check defaults
		resourceMappedSuperclass.getCache().setShared(null);

		assertEquals(null, resourceMappedSuperclass.getCache().getShared());
		assertEquals(false, javaContextCaching.isShared());
		assertEquals(true, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(null, ormContextCaching.getSpecifiedShared());
	
		
		// clear xml cache, check defaults
		resourceMappedSuperclass.setCache(null);

		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(false, javaContextCaching.isShared());
		assertEquals(false, ormContextCaching.isShared());
		assertEquals(false, ormContextCaching.isDefaultShared());
		assertEquals(null, ormContextCaching.getSpecifiedShared());
	
		
		// set metadataComplete to True, check defaults not from java

		ormContextMappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(false, javaContextCaching.isShared());
		assertEquals(true, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(null, ormContextCaching.getSpecifiedShared());

		
		// set metadataComplete back to null, check defaults from java
		ormContextMappedSuperclass.setSpecifiedMetadataComplete(null);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(false, javaContextCaching.isShared());
		assertEquals(false, ormContextCaching.isShared());
		assertEquals(false, ormContextCaching.isDefaultShared());
		assertEquals(null, ormContextCaching.getSpecifiedShared());
	}
	
	public void testModifyCacheShared() throws Exception {
		createTestMappedSuperclassForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextMappedSuperclass.getCaching();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		// check defaults
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(true, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(null, ormContextCaching.getSpecifiedShared());
		
		// set context cache size, check resource
		
		ormContextMappedSuperclass.getCaching().setSpecifiedShared(Boolean.FALSE);
		assertEquals(Boolean.FALSE, resourceMappedSuperclass.getCache().getShared());
		assertEquals(false, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(Boolean.FALSE, ormContextCaching.getSpecifiedShared());
				
		// set context cache size to null, check resource
		
		ormContextMappedSuperclass.getCaching().setSpecifiedShared(null);
		
		assertEquals(null, resourceMappedSuperclass.getCache());
		assertEquals(true, ormContextCaching.isShared());
		assertEquals(true, ormContextCaching.isDefaultShared());
		assertEquals(null, ormContextCaching.getSpecifiedShared());
	}
	
	public void testUpdateExistenceChecking() throws Exception {
		createTestMappedSuperclassForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkJavaCaching javaContextCaching = ((EclipseLinkJavaMappedSuperclass) ormPersistentType.getJavaPersistentType().getMapping()).getCaching();
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextMappedSuperclass.getCaching();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);


		// check defaults
		
		assertEquals(null, resourceMappedSuperclass.getExistenceChecking());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, javaContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getDefaultExistenceType());
		assertEquals(null, ormContextCaching.getSpecifiedExistenceType());
		
		// set xml existence checking, check settings
		resourceMappedSuperclass.setExistenceChecking(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.ExistenceType.ASSUME_EXISTENCE);
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.ExistenceType.ASSUME_EXISTENCE, resourceMappedSuperclass.getExistenceChecking());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, javaContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_EXISTENCE, ormContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getDefaultExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_EXISTENCE, ormContextCaching.getSpecifiedExistenceType());

			
		// set java cache existence checking, check defaults
		
		javaContextCaching.setSpecifiedExistenceType(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE);
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.ExistenceType.ASSUME_EXISTENCE, resourceMappedSuperclass.getExistenceChecking());
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, javaContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_EXISTENCE, ormContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, ormContextCaching.getDefaultExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_EXISTENCE, ormContextCaching.getSpecifiedExistenceType());

		// clear xml existence checking, check defaults
		resourceMappedSuperclass.setExistenceChecking(null);

		assertEquals(null, resourceMappedSuperclass.getExistenceChecking());
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, javaContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, ormContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, ormContextCaching.getDefaultExistenceType());
		assertEquals(null, ormContextCaching.getSpecifiedExistenceType());	
		
		// set metadataComplete to True, check defaults not from java

		ormContextMappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertEquals(null, resourceMappedSuperclass.getExistenceChecking());
		assertEquals(EclipseLinkExistenceType.ASSUME_NON_EXISTENCE, javaContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getDefaultExistenceType());
		assertEquals(null, ormContextCaching.getSpecifiedExistenceType());

		ormContextMappedSuperclass.setSpecifiedMetadataComplete(null);
	}
	
	public void testModifyExistenceChecking() throws Exception {
		createTestMappedSuperclassForCaching();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkCaching ormContextCaching = ormContextMappedSuperclass.getCaching();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		// check defaults
		
		assertEquals(null, resourceMappedSuperclass.getExistenceChecking());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getDefaultExistenceType());
		assertEquals(null, ormContextCaching.getSpecifiedExistenceType());
		
		// set context cache existence checking, check resource
		
		ormContextMappedSuperclass.getCaching().setSpecifiedExistenceType(EclipseLinkExistenceType.ASSUME_EXISTENCE);
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.ExistenceType.ASSUME_EXISTENCE, resourceMappedSuperclass.getExistenceChecking());
		assertEquals(EclipseLinkExistenceType.ASSUME_EXISTENCE, ormContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getDefaultExistenceType());
		assertEquals(EclipseLinkExistenceType.ASSUME_EXISTENCE, ormContextCaching.getSpecifiedExistenceType());
				
		// set context existence checking to null, check resource
		
		ormContextMappedSuperclass.getCaching().setSpecifiedExistenceType(null);
		
		assertEquals(null, resourceMappedSuperclass.getExistenceChecking());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getExistenceType());
		assertEquals(EclipseLinkExistenceType.CHECK_DATABASE, ormContextCaching.getDefaultExistenceType());
		assertEquals(null, ormContextCaching.getSpecifiedExistenceType());
	}
	public void testUpdateCustomConverters() throws Exception {
		createTestMappedSuperclassForConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = ormContextMappedSuperclass.getConverterContainer();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		assertEquals(0, ormContextConverterHolder.getCustomConvertersSize());
		assertEquals(0, resourceMappedSuperclass.getConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add a converter to the resource model, check context model
		XmlConverter resourceConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlConverter();
		resourceMappedSuperclass.getConverters().add(resourceConverter);
		resourceConverter.setClassName("Foo");
		resourceConverter.setName("myConverter");
		
		assertEquals(1, ormContextConverterHolder.getCustomConvertersSize());
		ListIterator<? extends EclipseLinkCustomConverter> ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		EclipseLinkCustomConverter ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(1, resourceMappedSuperclass.getConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add another converter to the resource model, check context model
		XmlConverter resourceConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlConverter();
		resourceMappedSuperclass.getConverters().add(0, resourceConverter2);
		resourceConverter2.setClassName("Foo2");
		resourceConverter2.setName("myConverter2");
		
		assertEquals(2, ormContextConverterHolder.getCustomConvertersSize());
		ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(2, resourceMappedSuperclass.getConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//move a converter in the resource model, check context model
		resourceMappedSuperclass.getConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.getCustomConvertersSize());
		ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(2, resourceMappedSuperclass.getConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the resource model, check context model
		resourceMappedSuperclass.getConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.getCustomConvertersSize());
		ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(1, resourceMappedSuperclass.getConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the resource model, check context model
		resourceMappedSuperclass.getConverters().remove(resourceConverter2);
		
		assertEquals(0, ormContextConverterHolder.getCustomConvertersSize());
		assertFalse(ormContextConverterHolder.getCustomConverters().iterator().hasNext());
		assertEquals(0, resourceMappedSuperclass.getConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
	}

	public void testModifyCustomConverters() throws Exception {
		createTestMappedSuperclassForConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = ormContextMappedSuperclass.getConverterContainer();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		assertEquals(0, ormContextConverterHolder.getCustomConvertersSize());
		assertEquals(0, resourceMappedSuperclass.getConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add a converter to the context model, check resource model
		EclipseLinkCustomConverter contextConverter = ormContextConverterHolder.addCustomConverter("myConverter", 0);
		contextConverter.setConverterClass("Foo");
		
		assertEquals(1, resourceMappedSuperclass.getConverters().size());
		assertEquals("Foo", resourceMappedSuperclass.getConverters().get(0).getClassName());
		assertEquals("myConverter", ((XmlConverter) resourceMappedSuperclass.getConverters().get(0)).getName());
		assertEquals(1, ormContextConverterHolder.getCustomConvertersSize());
		ListIterator<? extends EclipseLinkCustomConverter> ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		EclipseLinkCustomConverter ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add another converter to the context model, check resource model
		EclipseLinkCustomConverter contextConverter2 = ormContextConverterHolder.addCustomConverter("myConverter2", 0);
		contextConverter2.setConverterClass("Foo2");
		
		assertEquals(2, resourceMappedSuperclass.getConverters().size());
		assertEquals("Foo2", resourceMappedSuperclass.getConverters().get(0).getClassName());
		assertEquals("myConverter2", ((XmlConverter) resourceMappedSuperclass.getConverters().get(0)).getName());
		assertEquals("Foo", resourceMappedSuperclass.getConverters().get(1).getClassName());
		assertEquals("myConverter", ((XmlConverter) resourceMappedSuperclass.getConverters().get(1)).getName());
		assertEquals(2, ormContextConverterHolder.getCustomConvertersSize());
		ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//move a converter in the context model, check resource model
		ormContextConverterHolder.moveCustomConverter(0, 1);
		
		assertEquals(2, resourceMappedSuperclass.getConverters().size());
		assertEquals("Foo", resourceMappedSuperclass.getConverters().get(0).getClassName());
		assertEquals("myConverter", ((XmlConverter) resourceMappedSuperclass.getConverters().get(0)).getName());
		assertEquals("Foo2", resourceMappedSuperclass.getConverters().get(1).getClassName());
		assertEquals("myConverter2", ((XmlConverter) resourceMappedSuperclass.getConverters().get(1)).getName());
		assertEquals(2, ormContextConverterHolder.getCustomConvertersSize());
		ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeCustomConverter(0);
		
		assertEquals(1, resourceMappedSuperclass.getConverters().size());
		assertEquals("Foo2", resourceMappedSuperclass.getConverters().get(0).getClassName());
		assertEquals("myConverter2", ((XmlConverter) resourceMappedSuperclass.getConverters().get(0)).getName());
		assertEquals(1, ormContextConverterHolder.getCustomConvertersSize());
		ormContextConverters = ormContextConverterHolder.getCustomConverters().iterator();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeCustomConverter(contextConverter2);
		
		assertEquals(0, ormContextConverterHolder.getCustomConvertersSize());
		assertFalse(ormContextConverterHolder.getCustomConverters().iterator().hasNext());
		assertEquals(0, resourceMappedSuperclass.getConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
	}
	
	public void testUpdateTypeConverters() throws Exception {
		createTestMappedSuperclassForTypeConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = ormContextMappedSuperclass.getConverterContainer();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		assertEquals(0, ormContextConverterHolder.getTypeConvertersSize());
		assertEquals(0, resourceMappedSuperclass.getTypeConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add a converter to the resource model, check context model
		XmlTypeConverter resourceTypeConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverter();
		resourceMappedSuperclass.getTypeConverters().add(resourceTypeConverter);
		resourceTypeConverter.setDataType("Foo");
		resourceTypeConverter.setName("myTypeConverter");
		
		assertEquals(1, ormContextConverterHolder.getTypeConvertersSize());
		ListIterator<? extends EclipseLinkTypeConverter> ormContextTypeConverters = ormContextConverterHolder.getTypeConverters().iterator();
		EclipseLinkTypeConverter ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		assertEquals(1, resourceMappedSuperclass.getTypeConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add another converter to the resource model, check context model
		XmlTypeConverter resourceTypeConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverter();
		resourceMappedSuperclass.getTypeConverters().add(0, resourceTypeConverter2);
		resourceTypeConverter2.setDataType("Foo2");
		resourceTypeConverter2.setName("myTypeConverter2");
		
		assertEquals(2, ormContextConverterHolder.getTypeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.getTypeConverters().iterator();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		assertEquals(2, resourceMappedSuperclass.getTypeConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//move a converter in the resource model, check context model
		resourceMappedSuperclass.getTypeConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.getTypeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.getTypeConverters().iterator();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		assertEquals(2, resourceMappedSuperclass.getTypeConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the resource model, check context model
		resourceMappedSuperclass.getTypeConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.getTypeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.getTypeConverters().iterator();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		assertEquals(1, resourceMappedSuperclass.getTypeConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the resource model, check context model
		resourceMappedSuperclass.getTypeConverters().remove(resourceTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.getTypeConvertersSize());
		assertFalse(ormContextConverterHolder.getTypeConverters().iterator().hasNext());
		assertEquals(0, resourceMappedSuperclass.getTypeConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
	}

	public void testModifyTypeConverters() throws Exception {
		createTestMappedSuperclassForTypeConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = ormContextMappedSuperclass.getConverterContainer();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		assertEquals(0, ormContextConverterHolder.getTypeConvertersSize());
		assertEquals(0, resourceMappedSuperclass.getTypeConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add a converter to the context model, check resource model
		EclipseLinkTypeConverter contextTypeConverter = ormContextConverterHolder.addTypeConverter("myTypeConverter", 0);
		contextTypeConverter.setDataType("Foo");
		
		assertEquals(1, resourceMappedSuperclass.getTypeConverters().size());
		assertEquals("Foo", resourceMappedSuperclass.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter", resourceMappedSuperclass.getTypeConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.getTypeConvertersSize());
		ListIterator<? extends EclipseLinkTypeConverter> ormContextTypeConverters = ormContextConverterHolder.getTypeConverters().iterator();
		EclipseLinkTypeConverter ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add another converter to the context model, check resource model
		EclipseLinkTypeConverter contextTypeConverter2 = ormContextConverterHolder.addTypeConverter("myTypeConverter2", 0);
		contextTypeConverter2.setDataType("Foo2");
		
		assertEquals(2, resourceMappedSuperclass.getTypeConverters().size());
		assertEquals("Foo2", resourceMappedSuperclass.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter2", resourceMappedSuperclass.getTypeConverters().get(0).getName());
		assertEquals("Foo", resourceMappedSuperclass.getTypeConverters().get(1).getDataType());
		assertEquals("myTypeConverter", resourceMappedSuperclass.getTypeConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.getTypeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.getTypeConverters().iterator();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//move a converter in the context model, check resource model
		ormContextConverterHolder.moveTypeConverter(0, 1);
		
		assertEquals(2, resourceMappedSuperclass.getTypeConverters().size());
		assertEquals("Foo", resourceMappedSuperclass.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter", resourceMappedSuperclass.getTypeConverters().get(0).getName());
		assertEquals("Foo2", resourceMappedSuperclass.getTypeConverters().get(1).getDataType());
		assertEquals("myTypeConverter2", resourceMappedSuperclass.getTypeConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.getTypeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.getTypeConverters().iterator();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeTypeConverter(0);
		
		assertEquals(1, resourceMappedSuperclass.getTypeConverters().size());
		assertEquals("Foo2", resourceMappedSuperclass.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter2", resourceMappedSuperclass.getTypeConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.getTypeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.getTypeConverters().iterator();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeTypeConverter(contextTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.getTypeConvertersSize());
		assertFalse(ormContextConverterHolder.getTypeConverters().iterator().hasNext());
		assertEquals(0, resourceMappedSuperclass.getTypeConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
	}
	
	public void testUpdateObjectTypeConverters() throws Exception {
		createTestMappedSuperclassForObjectTypeConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = ormContextMappedSuperclass.getConverterContainer();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		assertEquals(0, ormContextConverterHolder.getObjectTypeConvertersSize());
		assertEquals(0, resourceMappedSuperclass.getObjectTypeConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add a converter to the resource model, check context model
		XmlObjectTypeConverter resourceObjectTypeConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverter();
		resourceMappedSuperclass.getObjectTypeConverters().add(resourceObjectTypeConverter);
		resourceObjectTypeConverter.setDataType("Foo");
		resourceObjectTypeConverter.setName("myObjectTypeConverter");
		
		assertEquals(1, ormContextConverterHolder.getObjectTypeConvertersSize());
		ListIterator<? extends EclipseLinkObjectTypeConverter> ormContextObjectTypeConverters = ormContextConverterHolder.getObjectTypeConverters().iterator();
		EclipseLinkObjectTypeConverter ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		assertEquals(1, resourceMappedSuperclass.getObjectTypeConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add another converter to the resource model, check context model
		XmlObjectTypeConverter resourceObjectTypeConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverter();
		resourceMappedSuperclass.getObjectTypeConverters().add(0, resourceObjectTypeConverter2);
		resourceObjectTypeConverter2.setDataType("Foo2");
		resourceObjectTypeConverter2.setName("myObjectTypeConverter2");
		
		assertEquals(2, ormContextConverterHolder.getObjectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.getObjectTypeConverters().iterator();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		assertEquals(2, resourceMappedSuperclass.getObjectTypeConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//move a converter in the resource model, check context model
		resourceMappedSuperclass.getObjectTypeConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.getObjectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.getObjectTypeConverters().iterator();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		assertEquals(2, resourceMappedSuperclass.getObjectTypeConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the resource model, check context model
		resourceMappedSuperclass.getObjectTypeConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.getObjectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.getObjectTypeConverters().iterator();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		assertEquals(1, resourceMappedSuperclass.getObjectTypeConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the resource model, check context model
		resourceMappedSuperclass.getObjectTypeConverters().remove(resourceObjectTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.getObjectTypeConvertersSize());
		assertFalse(ormContextConverterHolder.getObjectTypeConverters().iterator().hasNext());
		assertEquals(0, resourceMappedSuperclass.getObjectTypeConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
	}

	public void testModifyObjectTypeConverters() throws Exception {
		createTestMappedSuperclassForObjectTypeConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = ormContextMappedSuperclass.getConverterContainer();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		assertEquals(0, ormContextConverterHolder.getObjectTypeConvertersSize());
		assertEquals(0, resourceMappedSuperclass.getObjectTypeConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add a converter to the context model, check resource model
		EclipseLinkObjectTypeConverter contextObjectTypeConverter = ormContextConverterHolder.addObjectTypeConverter("myObjectTypeConverter", 0);
		contextObjectTypeConverter.setDataType("Foo");
		
		assertEquals(1, resourceMappedSuperclass.getObjectTypeConverters().size());
		assertEquals("Foo", resourceMappedSuperclass.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter", resourceMappedSuperclass.getObjectTypeConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.getObjectTypeConvertersSize());
		ListIterator<? extends EclipseLinkObjectTypeConverter> ormContextObjectTypeConverters = ormContextConverterHolder.getObjectTypeConverters().iterator();
		EclipseLinkObjectTypeConverter ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add another converter to the context model, check resource model
		EclipseLinkObjectTypeConverter contextObjectTypeConverter2 = ormContextConverterHolder.addObjectTypeConverter("myObjectTypeConverter2", 0);
		contextObjectTypeConverter2.setDataType("Foo2");
		
		assertEquals(2, resourceMappedSuperclass.getObjectTypeConverters().size());
		assertEquals("Foo2", resourceMappedSuperclass.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter2", resourceMappedSuperclass.getObjectTypeConverters().get(0).getName());
		assertEquals("Foo", resourceMappedSuperclass.getObjectTypeConverters().get(1).getDataType());
		assertEquals("myObjectTypeConverter", resourceMappedSuperclass.getObjectTypeConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.getObjectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.getObjectTypeConverters().iterator();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//move a converter in the context model, check resource model
		ormContextConverterHolder.moveObjectTypeConverter(0, 1);
		
		assertEquals(2, resourceMappedSuperclass.getObjectTypeConverters().size());
		assertEquals("Foo", resourceMappedSuperclass.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter", resourceMappedSuperclass.getObjectTypeConverters().get(0).getName());
		assertEquals("Foo2", resourceMappedSuperclass.getObjectTypeConverters().get(1).getDataType());
		assertEquals("myObjectTypeConverter2", resourceMappedSuperclass.getObjectTypeConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.getObjectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.getObjectTypeConverters().iterator();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeObjectTypeConverter(0);
		
		assertEquals(1, resourceMappedSuperclass.getObjectTypeConverters().size());
		assertEquals("Foo2", resourceMappedSuperclass.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter2", resourceMappedSuperclass.getObjectTypeConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.getObjectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.getObjectTypeConverters().iterator();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeObjectTypeConverter(contextObjectTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.getObjectTypeConvertersSize());
		assertFalse(ormContextConverterHolder.getObjectTypeConverters().iterator().hasNext());
		assertEquals(0, resourceMappedSuperclass.getObjectTypeConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
	}
	
	public void testUpdateStructConverters() throws Exception {
		createTestMappedSuperclassForStructConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = ormContextMappedSuperclass.getConverterContainer();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		assertEquals(0, ormContextConverterHolder.getStructConvertersSize());
		assertEquals(0, resourceMappedSuperclass.getStructConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add a converter to the resource model, check context model
		XmlStructConverter resourceStructConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverter();
		resourceMappedSuperclass.getStructConverters().add(resourceStructConverter);
		resourceStructConverter.setConverter("Foo");
		resourceStructConverter.setName("myStructConverter");
		
		assertEquals(1, ormContextConverterHolder.getStructConvertersSize());
		ListIterator<? extends EclipseLinkStructConverter> ormContextStructConverters = ormContextConverterHolder.getStructConverters().iterator();
		EclipseLinkStructConverter ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		assertEquals(1, resourceMappedSuperclass.getStructConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add another converter to the resource model, check context model
		XmlStructConverter resourceStructConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverter();
		resourceMappedSuperclass.getStructConverters().add(0, resourceStructConverter2);
		resourceStructConverter2.setConverter("Foo2");
		resourceStructConverter2.setName("myStructConverter2");
		
		assertEquals(2, ormContextConverterHolder.getStructConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.getStructConverters().iterator();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		assertEquals(2, resourceMappedSuperclass.getStructConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//move a converter in the resource model, check context model
		resourceMappedSuperclass.getStructConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.getStructConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.getStructConverters().iterator();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		assertEquals(2, resourceMappedSuperclass.getStructConverters().size());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the resource model, check context model
		resourceMappedSuperclass.getStructConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.getStructConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.getStructConverters().iterator();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		assertEquals(1, resourceMappedSuperclass.getStructConverters().size());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the resource model, check context model
		resourceMappedSuperclass.getStructConverters().remove(resourceStructConverter2);
		
		assertEquals(0, ormContextConverterHolder.getStructConvertersSize());
		assertFalse(ormContextConverterHolder.getStructConverters().iterator().hasNext());
		assertEquals(0, resourceMappedSuperclass.getStructConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
	}

	public void testModifyStructConverters() throws Exception {
		createTestMappedSuperclassForStructConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmMappedSuperclass ormContextMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		EclipseLinkOrmConverterContainer ormContextConverterHolder = ormContextMappedSuperclass.getConverterContainer();
		XmlMappedSuperclass resourceMappedSuperclass = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		assertEquals(0, ormContextConverterHolder.getStructConvertersSize());
		assertEquals(0, resourceMappedSuperclass.getStructConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add a converter to the context model, check resource model
		EclipseLinkStructConverter contextStructConverter = ormContextConverterHolder.addStructConverter("myStructConverter", 0);
		contextStructConverter.setConverterClass("Foo");
		
		assertEquals(1, resourceMappedSuperclass.getStructConverters().size());
		assertEquals("Foo", resourceMappedSuperclass.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter", resourceMappedSuperclass.getStructConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.getStructConvertersSize());
		ListIterator<? extends EclipseLinkStructConverter> ormContextStructConverters = ormContextConverterHolder.getStructConverters().iterator();
		EclipseLinkStructConverter ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//add another converter to the context model, check resource model
		EclipseLinkStructConverter contextStructConverter2 = ormContextConverterHolder.addStructConverter("myStructConverter2", 0);
		contextStructConverter2.setConverterClass("Foo2");
		
		assertEquals(2, resourceMappedSuperclass.getStructConverters().size());
		assertEquals("Foo2", resourceMappedSuperclass.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter2", resourceMappedSuperclass.getStructConverters().get(0).getName());
		assertEquals("Foo", resourceMappedSuperclass.getStructConverters().get(1).getConverter());
		assertEquals("myStructConverter", resourceMappedSuperclass.getStructConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.getStructConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.getStructConverters().iterator();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//move a converter in the context model, check resource model
		ormContextConverterHolder.moveStructConverter(0, 1);
		
		assertEquals(2, resourceMappedSuperclass.getStructConverters().size());
		assertEquals("Foo", resourceMappedSuperclass.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter", resourceMappedSuperclass.getStructConverters().get(0).getName());
		assertEquals("Foo2", resourceMappedSuperclass.getStructConverters().get(1).getConverter());
		assertEquals("myStructConverter2", resourceMappedSuperclass.getStructConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.getStructConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.getStructConverters().iterator();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		assertEquals(2, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeStructConverter(0);
		
		assertEquals(1, resourceMappedSuperclass.getStructConverters().size());
		assertEquals("Foo2", resourceMappedSuperclass.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter2", resourceMappedSuperclass.getStructConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.getStructConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.getStructConverters().iterator();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		assertEquals(1, IterableTools.size(persistenceUnit.getAllConverters()));
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeStructConverter(contextStructConverter2);
		
		assertEquals(0, ormContextConverterHolder.getStructConvertersSize());
		assertFalse(ormContextConverterHolder.getStructConverters().iterator().hasNext());
		assertEquals(0, resourceMappedSuperclass.getStructConverters().size());
		assertEquals(0, IterableTools.size(persistenceUnit.getAllConverters()));
	}
}

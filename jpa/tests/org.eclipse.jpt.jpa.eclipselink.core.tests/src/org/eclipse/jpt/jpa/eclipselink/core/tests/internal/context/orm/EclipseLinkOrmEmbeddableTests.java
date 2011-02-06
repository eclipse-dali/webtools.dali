/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTrackingType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkEmbeddable;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkEmbeddable;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlChangeTrackingType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter;

@SuppressWarnings("nls")
public class EclipseLinkOrmEmbeddableTests extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkOrmEmbeddableTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEmbeddableForCustomizer() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDABLE, EclipseLink.CUSTOMIZER);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable").append(CR);
			}
		});
	}
		
	private ICompilationUnit createTestEmbeddableForChangeTracking() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDABLE, EclipseLink.CHANGE_TRACKING);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable").append(CR);
			}
		});
	}	
	
	private ICompilationUnit createTestEmbeddableForCustomConverters() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable").append(CR);
			}
		});
	}

	private ICompilationUnit createTestEmbeddableForTypeConverters() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable").append(CR);
			}
		});
	}

	private ICompilationUnit createTestEmbeddableForObjectTypeConverters() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable").append(CR);
			}
		});
	}

	private ICompilationUnit createTestEmbeddableForStructConverters() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable").append(CR);
			}
		});
	}
	
	public void testUpdateCustomizerClass() throws Exception {
		createTestEmbeddableForCustomizer();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaEclipseLinkEmbeddable javaContextEmbeddable = (JavaEclipseLinkEmbeddable) ormPersistentType.getJavaPersistentType().getMapping();
		OrmEclipseLinkEmbeddable ormContextEmbeddable = (OrmEclipseLinkEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) getXmlEntityMappings().getEmbeddables().get(0);


		// check defaults
		
		assertNull(resourceEmbeddable.getCustomizer());
		assertNull(javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());
		
		// set xml customizer, check defaults
		resourceEmbeddable.setCustomizer(OrmFactory.eINSTANCE.createXmlClassReference());
		assertNull(resourceEmbeddable.getCustomizer().getClassName());
		assertNull(javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());

		
		// set xml customizer class, check override
		
		resourceEmbeddable.getCustomizer().setClassName("foo");
		
		assertEquals("foo", resourceEmbeddable.getCustomizer().getClassName());
		assertNull(javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertEquals("foo", ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertEquals("foo", ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());
		
		// clear xml customizer class, set java customizer class, check defaults
		
		resourceEmbeddable.getCustomizer().setClassName(null);
		javaContextEmbeddable.getCustomizer().setSpecifiedCustomizerClass("bar");
		
		assertNull(resourceEmbeddable.getCustomizer().getClassName());
		assertEquals("bar", javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertEquals("bar", ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertEquals("bar", ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());

		// set metadataComplete to True, check defaults not from java

		ormContextEmbeddable.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertNull(resourceEmbeddable.getCustomizer().getClassName());
		assertEquals("bar", javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());

		ormContextEmbeddable.setSpecifiedMetadataComplete(null);
		
		// set xml customizer class, check override
		
		resourceEmbeddable.getCustomizer().setClassName("foo");
		
		assertEquals("foo", resourceEmbeddable.getCustomizer().getClassName());
		assertEquals("bar", javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertEquals("foo", ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertEquals("bar", ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertEquals("foo", ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());

		//set xml customizer null
		javaContextEmbeddable.getCustomizer().setSpecifiedCustomizerClass(null);
		resourceEmbeddable.setCustomizer(null);
		assertNull(resourceEmbeddable.getCustomizer());
		assertNull(javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());

	}
	
	public void testModifyCustomizerClass() throws Exception {
		createTestEmbeddableForCustomizer();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEmbeddable ormContextEmbeddable = (OrmEclipseLinkEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) getXmlEntityMappings().getEmbeddables().get(0);
		
		// check defaults
		
		assertNull(resourceEmbeddable.getCustomizer());
		assertNull(ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());
		
		// set context customizer, check resource
		
		ormContextEmbeddable.getCustomizer().setSpecifiedCustomizerClass("foo");
		
		assertEquals("foo", resourceEmbeddable.getCustomizer().getClassName());
		assertEquals("foo", ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertEquals("foo", ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());
				
		// set context customizer to null, check resource
		
		ormContextEmbeddable.getCustomizer().setSpecifiedCustomizerClass(null);
		
		assertNull(resourceEmbeddable.getCustomizer());
		assertNull(ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());
	}
	
	public void testUpdateChangeTracking() throws Exception {
		createTestEmbeddableForChangeTracking();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaEclipseLinkEmbeddable javaContextEmbeddable = (JavaEclipseLinkEmbeddable) ormPersistentType.getJavaPersistentType().getMapping();
		OrmEclipseLinkEmbeddable ormContextEmbeddable = (OrmEclipseLinkEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) getXmlEntityMappings().getEmbeddables().get(0);
		
		// check defaults
		
		assertNull(resourceEmbeddable.getChangeTracking());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, javaContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertNull(ormContextEmbeddable.getChangeTracking().getSpecifiedType());
		
		// set xml type to ATTRIBUTE, check context
		
		resourceEmbeddable.setChangeTracking(EclipseLinkOrmFactory.eINSTANCE.createXmlChangeTracking());
		resourceEmbeddable.getChangeTracking().setType(XmlChangeTrackingType.ATTRIBUTE);
		
		assertEquals(XmlChangeTrackingType.ATTRIBUTE, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, javaContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
		
		// set xml type to OBJECT, check context
		
		resourceEmbeddable.getChangeTracking().setType(XmlChangeTrackingType.OBJECT);
		
		assertEquals(XmlChangeTrackingType.OBJECT, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, javaContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
		
		// set xml type to DEFERRED, check context
		
		resourceEmbeddable.getChangeTracking().setType(XmlChangeTrackingType.DEFERRED);
		
		assertEquals(XmlChangeTrackingType.DEFERRED, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, javaContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
		
		// set xml type to AUTO, check context
		
		resourceEmbeddable.getChangeTracking().setType(XmlChangeTrackingType.AUTO);
		
		assertEquals(XmlChangeTrackingType.AUTO, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, javaContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
		
		// clear xml change tracking, set java change tracking, check defaults
		
		resourceEmbeddable.setChangeTracking(null);
		javaContextEmbeddable.getChangeTracking().setSpecifiedType(EclipseLinkChangeTrackingType.ATTRIBUTE);
		
		assertNull(resourceEmbeddable.getChangeTracking());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, javaContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertNull(ormContextEmbeddable.getChangeTracking().getSpecifiedType());
		
		// set metadataComplete to True, check defaults not from java

		ormContextEmbeddable.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertNull(resourceEmbeddable.getChangeTracking());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, javaContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertNull(ormContextEmbeddable.getChangeTracking().getSpecifiedType());
		
		// unset metadataComplete, set xml change tracking to OBJECT, check context
		
		ormContextEmbeddable.setSpecifiedMetadataComplete(null);
		resourceEmbeddable.setChangeTracking(EclipseLinkOrmFactory.eINSTANCE.createXmlChangeTracking());
		resourceEmbeddable.getChangeTracking().setType(XmlChangeTrackingType.OBJECT);
		
		assertEquals(XmlChangeTrackingType.OBJECT, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, javaContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
	}
	
	public void testModifyChangeTracking() throws Exception  {
		createTestEmbeddableForChangeTracking();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEmbeddable ormContextEmbeddable = (OrmEclipseLinkEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) getXmlEntityMappings().getEmbeddables().get(0);
		
		// check defaults
		
		assertNull(resourceEmbeddable.getChangeTracking());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertNull(ormContextEmbeddable.getChangeTracking().getSpecifiedType());
		
		// set context change tracking to ATTRIBUTE, check resource
		
		ormContextEmbeddable.getChangeTracking().setSpecifiedType(EclipseLinkChangeTrackingType.ATTRIBUTE);
		
		assertEquals(XmlChangeTrackingType.ATTRIBUTE, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
				
		// set context change tracking to OBJECT, check resource
		
		ormContextEmbeddable.getChangeTracking().setSpecifiedType(EclipseLinkChangeTrackingType.OBJECT);
		
		assertEquals(XmlChangeTrackingType.OBJECT, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
				
		// set context change tracking to DEFERRED, check resource
		
		ormContextEmbeddable.getChangeTracking().setSpecifiedType(EclipseLinkChangeTrackingType.DEFERRED);
		
		assertEquals(XmlChangeTrackingType.DEFERRED, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
				
		// set context change tracking to AUTO, check resource
		
		ormContextEmbeddable.getChangeTracking().setSpecifiedType(EclipseLinkChangeTrackingType.AUTO);
		
		assertEquals(XmlChangeTrackingType.AUTO, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
				
		// set context change tracking to null, check resource
		
		ormContextEmbeddable.getChangeTracking().setSpecifiedType(null);
		
		assertNull(resourceEmbeddable.getChangeTracking());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertNull(ormContextEmbeddable.getChangeTracking().getSpecifiedType());
	}
	
	public void testUpdateCustomConverters() throws Exception {
		createTestEmbeddableForCustomConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEmbeddable ormContextEmbeddable = (OrmEclipseLinkEmbeddable) ormPersistentType.getMapping();
		OrmEclipseLinkConverterContainer ormContextConverterHolder = ormContextEmbeddable.getConverterContainer();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) getXmlEntityMappings().getEmbeddables().get(0);
		
		assertEquals(0, ormContextConverterHolder.customConvertersSize());
		assertEquals(0, resourceEmbeddable.getConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add a converter to the resource model, check context model
		XmlConverter resourceConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlConverter();
		resourceEmbeddable.getConverters().add(resourceConverter);
		resourceConverter.setClassName("Foo");
		resourceConverter.setName("myConverter");
		
		assertEquals(1, ormContextConverterHolder.customConvertersSize());
		ListIterator<EclipseLinkCustomConverter> ormContextConverters = ormContextConverterHolder.customConverters();
		EclipseLinkCustomConverter ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		assertEquals(1, resourceEmbeddable.getConverters().size());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add another converter to the resource model, check context model
		XmlConverter resourceConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlConverter();
		resourceEmbeddable.getConverters().add(0, resourceConverter2);
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
		assertEquals(2, resourceEmbeddable.getConverters().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//move a converter in the resource model, check context model
		resourceEmbeddable.getConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.customConvertersSize());
		ormContextConverters = ormContextConverterHolder.customConverters();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(2, resourceEmbeddable.getConverters().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the resource model, check context model
		resourceEmbeddable.getConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.customConvertersSize());
		ormContextConverters = ormContextConverterHolder.customConverters();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		assertEquals(1, resourceEmbeddable.getConverters().size());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the resource model, check context model
		resourceEmbeddable.getConverters().remove(resourceConverter2);
		
		assertEquals(0, ormContextConverterHolder.customConvertersSize());
		assertFalse(ormContextConverterHolder.customConverters().hasNext());
		assertEquals(0, resourceEmbeddable.getConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
	}

	public void testModifyCustomConverters() throws Exception {
		createTestEmbeddableForCustomConverters();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEmbeddable ormContextEmbeddable = (OrmEclipseLinkEmbeddable) ormPersistentType.getMapping();
		OrmEclipseLinkConverterContainer ormContextConverterHolder = ormContextEmbeddable.getConverterContainer();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) getXmlEntityMappings().getEmbeddables().get(0);
		
		assertEquals(0, ormContextConverterHolder.customConvertersSize());
		assertEquals(0, resourceEmbeddable.getConverters().size());
		
		//add a converter to the context model, check resource model
		EclipseLinkCustomConverter contextConverter = ormContextConverterHolder.addCustomConverter(0);
		contextConverter.setConverterClass("Foo");
		contextConverter.setName("myConverter");
		
		assertEquals(1, resourceEmbeddable.getConverters().size());
		assertEquals("Foo", resourceEmbeddable.getConverters().get(0).getClassName());
		assertEquals("myConverter", resourceEmbeddable.getConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.customConvertersSize());
		ListIterator<EclipseLinkCustomConverter> ormContextConverters = ormContextConverterHolder.customConverters();
		EclipseLinkCustomConverter ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
	
		//add another converter to the context model, check resource model
		EclipseLinkCustomConverter contextConverter2 = ormContextConverterHolder.addCustomConverter(0);
		contextConverter2.setConverterClass("Foo2");
		contextConverter2.setName("myConverter2");
		
		assertEquals(2, resourceEmbeddable.getConverters().size());
		assertEquals("Foo2", resourceEmbeddable.getConverters().get(0).getClassName());
		assertEquals("myConverter2", resourceEmbeddable.getConverters().get(0).getName());
		assertEquals("Foo", resourceEmbeddable.getConverters().get(1).getClassName());
		assertEquals("myConverter", resourceEmbeddable.getConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.customConvertersSize());
		ormContextConverters = ormContextConverterHolder.customConverters();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
	
		//move a converter in the context model, check resource model
		ormContextConverterHolder.moveCustomConverter(0, 1);
		
		assertEquals(2, resourceEmbeddable.getConverters().size());
		assertEquals("Foo", resourceEmbeddable.getConverters().get(0).getClassName());
		assertEquals("myConverter", resourceEmbeddable.getConverters().get(0).getName());
		assertEquals("Foo2", resourceEmbeddable.getConverters().get(1).getClassName());
		assertEquals("myConverter2", resourceEmbeddable.getConverters().get(1).getName());
		assertEquals(2, ormContextConverterHolder.customConvertersSize());
		ormContextConverters = ormContextConverterHolder.customConverters();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo", ormContextConverter.getConverterClass());
		assertEquals("myConverter", ormContextConverter.getName());
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeCustomConverter(0);
		
		assertEquals(1, resourceEmbeddable.getConverters().size());
		assertEquals("Foo2", resourceEmbeddable.getConverters().get(0).getClassName());
		assertEquals("myConverter2", resourceEmbeddable.getConverters().get(0).getName());
		assertEquals(1, ormContextConverterHolder.customConvertersSize());
		ormContextConverters = ormContextConverterHolder.customConverters();
		ormContextConverter = ormContextConverters.next();
		assertEquals("Foo2", ormContextConverter.getConverterClass());
		assertEquals("myConverter2", ormContextConverter.getName());
		
		
		//remove a converter from the context model, check resource model
		ormContextConverterHolder.removeCustomConverter(contextConverter2);
		
		assertEquals(0, ormContextConverterHolder.customConvertersSize());
		assertFalse(ormContextConverterHolder.customConverters().hasNext());
		assertEquals(0, resourceEmbeddable.getConverters().size());
	}
	
	public void testUpdateTypeConverters() throws Exception {
		createTestEmbeddableForTypeConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEmbeddable ormContextEmbeddable = (OrmEclipseLinkEmbeddable) ormPersistentType.getMapping();
		OrmEclipseLinkConverterContainer ormContextConverterHolder = ormContextEmbeddable.getConverterContainer();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) getXmlEntityMappings().getEmbeddables().get(0);
		
		assertEquals(0, ormContextConverterHolder.typeConvertersSize());
		assertEquals(0, resourceEmbeddable.getTypeConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add a converter to the resource model, check context model
		XmlTypeConverter resourceTypeConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverter();
		resourceEmbeddable.getTypeConverters().add(resourceTypeConverter);
		resourceTypeConverter.setDataType("Foo");
		resourceTypeConverter.setName("myTypeConverter");
		
		assertEquals(1, ormContextConverterHolder.typeConvertersSize());
		ListIterator<EclipseLinkTypeConverter> ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		EclipseLinkTypeConverter ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		assertEquals(1, resourceEmbeddable.getTypeConverters().size());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add another converter to the resource model, check context model
		XmlTypeConverter resourceTypeConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverter();
		resourceEmbeddable.getTypeConverters().add(0, resourceTypeConverter2);
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
		assertEquals(2, resourceEmbeddable.getTypeConverters().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//move a converter in the resource model, check context model
		resourceEmbeddable.getTypeConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.typeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter", ormContextTypeConverter.getName());
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		assertEquals(2, resourceEmbeddable.getTypeConverters().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the resource model, check context model
		resourceEmbeddable.getTypeConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.typeConvertersSize());
		ormContextTypeConverters = ormContextConverterHolder.typeConverters();
		ormContextTypeConverter = ormContextTypeConverters.next();
		assertEquals("Foo2", ormContextTypeConverter.getDataType());
		assertEquals("myTypeConverter2", ormContextTypeConverter.getName());
		assertEquals(1, resourceEmbeddable.getTypeConverters().size());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the resource model, check context model
		resourceEmbeddable.getTypeConverters().remove(resourceTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.typeConvertersSize());
		assertFalse(ormContextConverterHolder.typeConverters().hasNext());
		assertEquals(0, resourceEmbeddable.getTypeConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));	
	}

	public void testModifyTypeConverters() throws Exception {
		createTestEmbeddableForTypeConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEmbeddable ormContextEmbeddable = (OrmEclipseLinkEmbeddable) ormPersistentType.getMapping();
		OrmEclipseLinkConverterContainer ormContextConverterHolder = ormContextEmbeddable.getConverterContainer();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) getXmlEntityMappings().getEmbeddables().get(0);
		
		assertEquals(0, ormContextConverterHolder.typeConvertersSize());
		assertEquals(0, resourceEmbeddable.getTypeConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add a converter to the context model, check resource model
		EclipseLinkTypeConverter contextTypeConverter = ormContextConverterHolder.addTypeConverter(0);
		contextTypeConverter.setDataType("Foo");
		contextTypeConverter.setName("myTypeConverter");
		
		assertEquals(1, resourceEmbeddable.getTypeConverters().size());
		assertEquals("Foo", resourceEmbeddable.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter", resourceEmbeddable.getTypeConverters().get(0).getName());
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
		
		assertEquals(2, resourceEmbeddable.getTypeConverters().size());
		assertEquals("Foo2", resourceEmbeddable.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter2", resourceEmbeddable.getTypeConverters().get(0).getName());
		assertEquals("Foo", resourceEmbeddable.getTypeConverters().get(1).getDataType());
		assertEquals("myTypeConverter", resourceEmbeddable.getTypeConverters().get(1).getName());
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
		
		assertEquals(2, resourceEmbeddable.getTypeConverters().size());
		assertEquals("Foo", resourceEmbeddable.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter", resourceEmbeddable.getTypeConverters().get(0).getName());
		assertEquals("Foo2", resourceEmbeddable.getTypeConverters().get(1).getDataType());
		assertEquals("myTypeConverter2", resourceEmbeddable.getTypeConverters().get(1).getName());
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
		
		assertEquals(1, resourceEmbeddable.getTypeConverters().size());
		assertEquals("Foo2", resourceEmbeddable.getTypeConverters().get(0).getDataType());
		assertEquals("myTypeConverter2", resourceEmbeddable.getTypeConverters().get(0).getName());
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
		assertEquals(0, resourceEmbeddable.getTypeConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
	}
	
	public void testUpdateObjectTypeConverters() throws Exception {
		createTestEmbeddableForObjectTypeConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEmbeddable ormContextEmbeddable = (OrmEclipseLinkEmbeddable) ormPersistentType.getMapping();
		OrmEclipseLinkConverterContainer ormContextConverterHolder = ormContextEmbeddable.getConverterContainer();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) getXmlEntityMappings().getEmbeddables().get(0);
		
		assertEquals(0, ormContextConverterHolder.objectTypeConvertersSize());
		assertEquals(0, resourceEmbeddable.getObjectTypeConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add a converter to the resource model, check context model
		XmlObjectTypeConverter resourceObjectTypeConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverter();
		resourceEmbeddable.getObjectTypeConverters().add(resourceObjectTypeConverter);
		resourceObjectTypeConverter.setDataType("Foo");
		resourceObjectTypeConverter.setName("myObjectTypeConverter");
		
		assertEquals(1, ormContextConverterHolder.objectTypeConvertersSize());
		ListIterator<EclipseLinkObjectTypeConverter> ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		EclipseLinkObjectTypeConverter ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		assertEquals(1, resourceEmbeddable.getObjectTypeConverters().size());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add another converter to the resource model, check context model
		XmlObjectTypeConverter resourceObjectTypeConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverter();
		resourceEmbeddable.getObjectTypeConverters().add(0, resourceObjectTypeConverter2);
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
		assertEquals(2, resourceEmbeddable.getObjectTypeConverters().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//move a converter in the resource model, check context model
		resourceEmbeddable.getObjectTypeConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.objectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter", ormContextObjectTypeConverter.getName());
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		assertEquals(2, resourceEmbeddable.getObjectTypeConverters().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the resource model, check context model
		resourceEmbeddable.getObjectTypeConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.objectTypeConvertersSize());
		ormContextObjectTypeConverters = ormContextConverterHolder.objectTypeConverters();
		ormContextObjectTypeConverter = ormContextObjectTypeConverters.next();
		assertEquals("Foo2", ormContextObjectTypeConverter.getDataType());
		assertEquals("myObjectTypeConverter2", ormContextObjectTypeConverter.getName());
		assertEquals(1, resourceEmbeddable.getObjectTypeConverters().size());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the resource model, check context model
		resourceEmbeddable.getObjectTypeConverters().remove(resourceObjectTypeConverter2);
		
		assertEquals(0, ormContextConverterHolder.objectTypeConvertersSize());
		assertFalse(ormContextConverterHolder.objectTypeConverters().hasNext());
		assertEquals(0, resourceEmbeddable.getObjectTypeConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
	}

	public void testModifyObjectTypeConverters() throws Exception {
		createTestEmbeddableForObjectTypeConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEmbeddable ormContextEmbeddable = (OrmEclipseLinkEmbeddable) ormPersistentType.getMapping();
		OrmEclipseLinkConverterContainer ormContextConverterHolder = ormContextEmbeddable.getConverterContainer();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) getXmlEntityMappings().getEmbeddables().get(0);
		
		assertEquals(0, ormContextConverterHolder.objectTypeConvertersSize());
		assertEquals(0, resourceEmbeddable.getObjectTypeConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add a converter to the context model, check resource model
		EclipseLinkObjectTypeConverter contextObjectTypeConverter = ormContextConverterHolder.addObjectTypeConverter(0);
		contextObjectTypeConverter.setDataType("Foo");
		contextObjectTypeConverter.setName("myObjectTypeConverter");
		
		assertEquals(1, resourceEmbeddable.getObjectTypeConverters().size());
		assertEquals("Foo", resourceEmbeddable.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter", resourceEmbeddable.getObjectTypeConverters().get(0).getName());
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
		
		assertEquals(2, resourceEmbeddable.getObjectTypeConverters().size());
		assertEquals("Foo2", resourceEmbeddable.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter2", resourceEmbeddable.getObjectTypeConverters().get(0).getName());
		assertEquals("Foo", resourceEmbeddable.getObjectTypeConverters().get(1).getDataType());
		assertEquals("myObjectTypeConverter", resourceEmbeddable.getObjectTypeConverters().get(1).getName());
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
		
		assertEquals(2, resourceEmbeddable.getObjectTypeConverters().size());
		assertEquals("Foo", resourceEmbeddable.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter", resourceEmbeddable.getObjectTypeConverters().get(0).getName());
		assertEquals("Foo2", resourceEmbeddable.getObjectTypeConverters().get(1).getDataType());
		assertEquals("myObjectTypeConverter2", resourceEmbeddable.getObjectTypeConverters().get(1).getName());
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
		
		assertEquals(1, resourceEmbeddable.getObjectTypeConverters().size());
		assertEquals("Foo2", resourceEmbeddable.getObjectTypeConverters().get(0).getDataType());
		assertEquals("myObjectTypeConverter2", resourceEmbeddable.getObjectTypeConverters().get(0).getName());
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
		assertEquals(0, resourceEmbeddable.getObjectTypeConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
	}
	
	public void testUpdateStructConverters() throws Exception {
		createTestEmbeddableForStructConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEmbeddable ormContextEmbeddable = (OrmEclipseLinkEmbeddable) ormPersistentType.getMapping();
		OrmEclipseLinkConverterContainer ormContextConverterHolder = ormContextEmbeddable.getConverterContainer();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) getXmlEntityMappings().getEmbeddables().get(0);
		
		assertEquals(0, ormContextConverterHolder.structConvertersSize());
		assertEquals(0, resourceEmbeddable.getStructConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add a converter to the resource model, check context model
		XmlStructConverter resourceStructConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverter();
		resourceEmbeddable.getStructConverters().add(resourceStructConverter);
		resourceStructConverter.setConverter("Foo");
		resourceStructConverter.setName("myStructConverter");
		
		assertEquals(1, ormContextConverterHolder.structConvertersSize());
		ListIterator<EclipseLinkStructConverter> ormContextStructConverters = ormContextConverterHolder.structConverters();
		EclipseLinkStructConverter ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		assertEquals(1, resourceEmbeddable.getStructConverters().size());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add another converter to the resource model, check context model
		XmlStructConverter resourceStructConverter2 = EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverter();
		resourceEmbeddable.getStructConverters().add(0, resourceStructConverter2);
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
		assertEquals(2, resourceEmbeddable.getStructConverters().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//move a converter in the resource model, check context model
		resourceEmbeddable.getStructConverters().move(0, 1);
		
		assertEquals(2, ormContextConverterHolder.structConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.structConverters();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter", ormContextStructConverter.getName());
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		assertEquals(2, resourceEmbeddable.getStructConverters().size());
		assertEquals(2, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the resource model, check context model
		resourceEmbeddable.getStructConverters().remove(0);
		
		assertEquals(1, ormContextConverterHolder.structConvertersSize());
		ormContextStructConverters = ormContextConverterHolder.structConverters();
		ormContextStructConverter = ormContextStructConverters.next();
		assertEquals("Foo2", ormContextStructConverter.getConverterClass());
		assertEquals("myStructConverter2", ormContextStructConverter.getName());
		assertEquals(1, resourceEmbeddable.getStructConverters().size());
		assertEquals(1, CollectionTools.size(persistenceUnit.allConverters()));
		
		//remove a converter from the resource model, check context model
		resourceEmbeddable.getStructConverters().remove(resourceStructConverter2);
		
		assertEquals(0, ormContextConverterHolder.structConvertersSize());
		assertFalse(ormContextConverterHolder.structConverters().hasNext());
		assertEquals(0, resourceEmbeddable.getStructConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
	}

	public void testModifyStructConverters() throws Exception {
		createTestEmbeddableForStructConverters();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEclipseLinkEmbeddable ormContextEmbeddable = (OrmEclipseLinkEmbeddable) ormPersistentType.getMapping();
		OrmEclipseLinkConverterContainer ormContextConverterHolder = ormContextEmbeddable.getConverterContainer();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) getXmlEntityMappings().getEmbeddables().get(0);
		
		assertEquals(0, ormContextConverterHolder.structConvertersSize());
		assertEquals(0, resourceEmbeddable.getStructConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
		
		//add a converter to the context model, check resource model
		EclipseLinkStructConverter contextStructConverter = ormContextConverterHolder.addStructConverter(0);
		contextStructConverter.setConverterClass("Foo");
		contextStructConverter.setName("myStructConverter");
		
		assertEquals(1, resourceEmbeddable.getStructConverters().size());
		assertEquals("Foo", resourceEmbeddable.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter", resourceEmbeddable.getStructConverters().get(0).getName());
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
		
		assertEquals(2, resourceEmbeddable.getStructConverters().size());
		assertEquals("Foo2", resourceEmbeddable.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter2", resourceEmbeddable.getStructConverters().get(0).getName());
		assertEquals("Foo", resourceEmbeddable.getStructConverters().get(1).getConverter());
		assertEquals("myStructConverter", resourceEmbeddable.getStructConverters().get(1).getName());
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
		
		assertEquals(2, resourceEmbeddable.getStructConverters().size());
		assertEquals("Foo", resourceEmbeddable.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter", resourceEmbeddable.getStructConverters().get(0).getName());
		assertEquals("Foo2", resourceEmbeddable.getStructConverters().get(1).getConverter());
		assertEquals("myStructConverter2", resourceEmbeddable.getStructConverters().get(1).getName());
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
		
		assertEquals(1, resourceEmbeddable.getStructConverters().size());
		assertEquals("Foo2", resourceEmbeddable.getStructConverters().get(0).getConverter());
		assertEquals("myStructConverter2", resourceEmbeddable.getStructConverters().get(0).getName());
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
		assertEquals(0, resourceEmbeddable.getStructConverters().size());
		assertEquals(0, CollectionTools.size(persistenceUnit.allConverters()));
	}
}
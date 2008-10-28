/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.eclipselink.core.context.ChangeTrackingType;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEmbeddable;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEmbeddable;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkOrmEmbeddableTests extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkOrmEmbeddableTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEmbeddableForCustomizer() throws Exception {
		createCustomizerAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDABLE, EclipseLinkJPA.CUSTOMIZER);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable").append(CR);
			}
		});
	}
	
	private void createCustomizerAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "Customizer", "Class value()");		
	}
		
	private ICompilationUnit createTestEmbeddableForChangeTracking() throws Exception {
		createChangeTrackingAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDABLE, EclipseLinkJPA.CHANGE_TRACKING);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable").append(CR);
			}
		});
	}
	
	private void createChangeTrackingAnnotation() throws Exception{
		createChangeTrackingTypeEnum();
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "ChangeTracking", "ChangeTrackingType value() default ChangeTrackingType.AUTO");		
	}
	
	private void createChangeTrackingTypeEnum() throws Exception {
		this.createEnumAndMembers(EclipseLinkJPA.PACKAGE, "ChangeTrackingType", "ATTRIBUTE, OBJECT, DEFERRED, AUTO;");	
	}
	
	public void testUpdateCustomizerClass() throws Exception {
		createTestEmbeddableForCustomizer();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkJavaEmbeddable javaContextEmbeddable = (EclipseLinkJavaEmbeddable) ormPersistentType.getJavaPersistentType().getMapping();
		EclipseLinkOrmEmbeddable ormContextEmbeddable = (EclipseLinkOrmEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) ormResource().getEntityMappings().getEmbeddables().get(0);


		// check defaults
		
		assertNull(resourceEmbeddable.getCustomizer());
		assertNull(javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());
		
		// set xml customizer, check defaults
		resourceEmbeddable.setCustomizer(EclipseLinkOrmFactory.eINSTANCE.createXmlCustomizer());
		assertNull(resourceEmbeddable.getCustomizer().getCustomizerClassName());
		assertNull(javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());

		
		// set xml customizer class, check override
		
		resourceEmbeddable.getCustomizer().setCustomizerClassName("foo");
		
		assertEquals("foo", resourceEmbeddable.getCustomizer().getCustomizerClassName());
		assertNull(javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertEquals("foo", ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertEquals("foo", ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());
		
		// clear xml customizer class, set java customizer class, check defaults
		
		resourceEmbeddable.getCustomizer().setCustomizerClassName(null);
		javaContextEmbeddable.getCustomizer().setSpecifiedCustomizerClass("bar");
		
		assertNull(resourceEmbeddable.getCustomizer().getCustomizerClassName());
		assertEquals("bar", javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertEquals("bar", ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertEquals("bar", ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());

		// set metadataComplete to True, check defaults not from java

		ormContextEmbeddable.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertNull(resourceEmbeddable.getCustomizer().getCustomizerClassName());
		assertEquals("bar", javaContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());

		ormContextEmbeddable.setSpecifiedMetadataComplete(null);
		
		// set xml customizer class, check override
		
		resourceEmbeddable.getCustomizer().setCustomizerClassName("foo");
		
		assertEquals("foo", resourceEmbeddable.getCustomizer().getCustomizerClassName());
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmEmbeddable ormContextEmbeddable = (EclipseLinkOrmEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) ormResource().getEntityMappings().getEmbeddables().get(0);
		
		// check defaults
		
		assertNull(resourceEmbeddable.getCustomizer());
		assertNull(ormContextEmbeddable.getCustomizer().getCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEmbeddable.getCustomizer().getSpecifiedCustomizerClass());
		
		// set context customizer, check resource
		
		ormContextEmbeddable.getCustomizer().setSpecifiedCustomizerClass("foo");
		
		assertEquals("foo", resourceEmbeddable.getCustomizer().getCustomizerClassName());
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkJavaEmbeddable javaContextEmbeddable = (EclipseLinkJavaEmbeddable) ormPersistentType.getJavaPersistentType().getMapping();
		EclipseLinkOrmEmbeddable ormContextEmbeddable = (EclipseLinkOrmEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) ormResource().getEntityMappings().getEmbeddables().get(0);
		
		// check defaults
		
		assertNull(resourceEmbeddable.getChangeTracking());
		assertEquals(ChangeTrackingType.AUTO, javaContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertNull(ormContextEmbeddable.getChangeTracking().getSpecifiedType());
		
		// set xml type to ATTRIBUTE, check context
		
		resourceEmbeddable.setChangeTracking(EclipseLinkOrmFactory.eINSTANCE.createXmlChangeTracking());
		resourceEmbeddable.getChangeTracking().setType(XmlChangeTrackingType.ATTRIBUTE);
		
		assertEquals(XmlChangeTrackingType.ATTRIBUTE, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, javaContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.ATTRIBUTE, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(ChangeTrackingType.ATTRIBUTE, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
		
		// set xml type to OBJECT, check context
		
		resourceEmbeddable.getChangeTracking().setType(XmlChangeTrackingType.OBJECT);
		
		assertEquals(XmlChangeTrackingType.OBJECT, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, javaContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.OBJECT, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(ChangeTrackingType.OBJECT, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
		
		// set xml type to DEFERRED, check context
		
		resourceEmbeddable.getChangeTracking().setType(XmlChangeTrackingType.DEFERRED);
		
		assertEquals(XmlChangeTrackingType.DEFERRED, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, javaContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.DEFERRED, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(ChangeTrackingType.DEFERRED, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
		
		// set xml type to AUTO, check context
		
		resourceEmbeddable.getChangeTracking().setType(XmlChangeTrackingType.AUTO);
		
		assertEquals(XmlChangeTrackingType.AUTO, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, javaContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
		
		// clear xml change tracking, set java change tracking, check defaults
		
		resourceEmbeddable.setChangeTracking(null);
		javaContextEmbeddable.getChangeTracking().setSpecifiedType(ChangeTrackingType.ATTRIBUTE);
		
		assertNull(resourceEmbeddable.getChangeTracking());
		assertEquals(ChangeTrackingType.ATTRIBUTE, javaContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.ATTRIBUTE, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.ATTRIBUTE, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertNull(ormContextEmbeddable.getChangeTracking().getSpecifiedType());
		
		// set metadataComplete to True, check defaults not from java

		ormContextEmbeddable.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertNull(resourceEmbeddable.getChangeTracking());
		assertEquals(ChangeTrackingType.ATTRIBUTE, javaContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertNull(ormContextEmbeddable.getChangeTracking().getSpecifiedType());
		
		// unset metadataComplete, set xml change tracking to OBJECT, check context
		
		ormContextEmbeddable.setSpecifiedMetadataComplete(null);
		resourceEmbeddable.setChangeTracking(EclipseLinkOrmFactory.eINSTANCE.createXmlChangeTracking());
		resourceEmbeddable.getChangeTracking().setType(XmlChangeTrackingType.OBJECT);
		
		assertEquals(XmlChangeTrackingType.OBJECT, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.ATTRIBUTE, javaContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.OBJECT, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.ATTRIBUTE, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(ChangeTrackingType.OBJECT, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
	}
	
	public void testModifyChangeTracking() throws Exception  {
		createTestEmbeddableForChangeTracking();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmEmbeddable ormContextEmbeddable = (EclipseLinkOrmEmbeddable) ormPersistentType.getMapping();
		XmlEmbeddable resourceEmbeddable = (XmlEmbeddable) ormResource().getEntityMappings().getEmbeddables().get(0);
		
		// check defaults
		
		assertNull(resourceEmbeddable.getChangeTracking());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertNull(ormContextEmbeddable.getChangeTracking().getSpecifiedType());
		
		// set context change tracking to ATTRIBUTE, check resource
		
		ormContextEmbeddable.getChangeTracking().setSpecifiedType(ChangeTrackingType.ATTRIBUTE);
		
		assertEquals(XmlChangeTrackingType.ATTRIBUTE, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.ATTRIBUTE, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(ChangeTrackingType.ATTRIBUTE, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
				
		// set context change tracking to OBJECT, check resource
		
		ormContextEmbeddable.getChangeTracking().setSpecifiedType(ChangeTrackingType.OBJECT);
		
		assertEquals(XmlChangeTrackingType.OBJECT, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.OBJECT, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(ChangeTrackingType.OBJECT, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
				
		// set context change tracking to DEFERRED, check resource
		
		ormContextEmbeddable.getChangeTracking().setSpecifiedType(ChangeTrackingType.DEFERRED);
		
		assertEquals(XmlChangeTrackingType.DEFERRED, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.DEFERRED, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(ChangeTrackingType.DEFERRED, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
				
		// set context change tracking to AUTO, check resource
		
		ormContextEmbeddable.getChangeTracking().setSpecifiedType(ChangeTrackingType.AUTO);
		
		assertEquals(XmlChangeTrackingType.AUTO, resourceEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getSpecifiedType());
				
		// set context change tracking to null, check resource
		
		ormContextEmbeddable.getChangeTracking().setSpecifiedType(null);
		
		assertNull(resourceEmbeddable.getChangeTracking());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getType());
		assertEquals(ChangeTrackingType.AUTO, ormContextEmbeddable.getChangeTracking().getDefaultType());
		assertNull(ormContextEmbeddable.getChangeTracking().getSpecifiedType());
	}
}
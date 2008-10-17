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
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmEntity;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


public class EclipseLinkOrmEntityTests extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkOrmEntityTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createTestEntityForReadOnly() throws Exception {
		createReadOnlyAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLinkJPA.READ_ONLY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
		});
	}
	
	private void createReadOnlyAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "ReadOnly", "");		
	}
	
	private ICompilationUnit createTestEntityForCustomizer() throws Exception {
		createCustomizerAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLinkJPA.CUSTOMIZER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
		});
	}
	
	private void createCustomizerAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "Customizer", "Class value()");		
	}
	
	public void testUpdateReadOnly() throws Exception {
		createTestEntityForReadOnly();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkJavaEntity javaContextEntity = (EclipseLinkJavaEntity) ormPersistentType.getJavaPersistentType().getMapping();
		EclipseLinkOrmEntity ormContextEntity = (EclipseLinkOrmEntity) ormPersistentType.getMapping();
		XmlEntity resourceEntity = (XmlEntity) ormResource().getEntityMappings().getEntities().get(0);
		
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmEntity ormContextEntity = (EclipseLinkOrmEntity) ormPersistentType.getMapping();
		XmlEntity resourceEntity = (XmlEntity) ormResource().getEntityMappings().getEntities().get(0);
		
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkJavaEntity javaContextEntity = (EclipseLinkJavaEntity) ormPersistentType.getJavaPersistentType().getMapping();
		EclipseLinkOrmEntity ormContextEntity = (EclipseLinkOrmEntity) ormPersistentType.getMapping();
		XmlEntity resourceEntity = (XmlEntity) ormResource().getEntityMappings().getEntities().get(0);


		// check defaults
		
		assertNull(resourceEntity.getCustomizer());
		assertNull(javaContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getSpecifiedCustomizerClass());
		
		// set xml customizer, check defaults
		resourceEntity.setCustomizer(EclipseLinkOrmFactory.eINSTANCE.createXmlCustomizer());
		assertNull(resourceEntity.getCustomizer().getCustomizerClassName());
		assertNull(javaContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getSpecifiedCustomizerClass());

		
		// set xml customizer class, check override
		
		resourceEntity.getCustomizer().setCustomizerClassName("foo");
		
		assertEquals("foo", resourceEntity.getCustomizer().getCustomizerClassName());
		assertNull(javaContextEntity.getCustomizer().getCustomizerClass());
		assertEquals("foo", ormContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getDefaultCustomizerClass());
		assertEquals("foo", ormContextEntity.getCustomizer().getSpecifiedCustomizerClass());
		
		// clear xml customizer class, set java customizer class, check defaults
		
		resourceEntity.getCustomizer().setCustomizerClassName(null);
		javaContextEntity.getCustomizer().setSpecifiedCustomizerClass("bar");
		
		assertNull(resourceEntity.getCustomizer().getCustomizerClassName());
		assertEquals("bar", javaContextEntity.getCustomizer().getCustomizerClass());
		assertEquals("bar", ormContextEntity.getCustomizer().getCustomizerClass());
		assertEquals("bar", ormContextEntity.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getSpecifiedCustomizerClass());

		// set metadataComplete to True, check defaults not from java

		ormContextEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
		
		assertNull(resourceEntity.getCustomizer().getCustomizerClassName());
		assertEquals("bar", javaContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getSpecifiedCustomizerClass());

		ormContextEntity.setSpecifiedMetadataComplete(null);
		
		// set xml customizer class, check override
		
		resourceEntity.getCustomizer().setCustomizerClassName("foo");
		
		assertEquals("foo", resourceEntity.getCustomizer().getCustomizerClassName());
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkOrmEntity ormContextEntity = (EclipseLinkOrmEntity) ormPersistentType.getMapping();
		XmlEntity resourceEntity = (XmlEntity) ormResource().getEntityMappings().getEntities().get(0);
		
		// check defaults
		
		assertNull(resourceEntity.getCustomizer());
		assertNull(ormContextEntity.getCustomizer().getCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getDefaultCustomizerClass());
		assertNull(ormContextEntity.getCustomizer().getSpecifiedCustomizerClass());
		
		// set context customizer, check resource
		
		ormContextEntity.getCustomizer().setSpecifiedCustomizerClass("foo");
		
		assertEquals("foo", resourceEntity.getCustomizer().getCustomizerClassName());
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

}

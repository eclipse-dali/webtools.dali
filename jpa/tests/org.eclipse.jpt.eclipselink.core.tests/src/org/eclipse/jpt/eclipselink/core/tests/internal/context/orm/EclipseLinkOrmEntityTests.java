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
		
		assertFalse(resourceEntity.getReadOnly());
		assertFalse(javaContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertFalse(ormContextEntity.getReadOnly().getSpecifiedReadOnly());
		
		// set xml read only to true, check override
		
		resourceEntity.setReadOnly(Boolean.TRUE);
		
		assertTrue(resourceEntity.getReadOnly());
		assertFalse(javaContextEntity.getReadOnly().isReadOnly());
		assertTrue(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertTrue(ormContextEntity.getReadOnly().getSpecifiedReadOnly());
		
		// clear xml read only, set java read only to true, check defaults
		
		resourceEntity.setReadOnly(null);
		javaContextEntity.getReadOnly().setSpecifiedReadOnly(Boolean.TRUE);
		
		assertNull(resourceEntity.getReadOnly());
		assertTrue(javaContextEntity.getReadOnly().isReadOnly());
		assertTrue(ormContextEntity.getReadOnly().isReadOnly());
		assertTrue(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertNull(ormContextEntity.getReadOnly().getSpecifiedReadOnly());
		
		// set xml read only to false, check override
		
		resourceEntity.setReadOnly(Boolean.FALSE);
		
		assertFalse(resourceEntity.getReadOnly());
		assertTrue(javaContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isReadOnly());
		assertTrue(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertFalse(ormContextEntity.getReadOnly().getSpecifiedReadOnly());
		
		// set xml read only to true, check override
		
		resourceEntity.setReadOnly(Boolean.TRUE);
		
		assertTrue(resourceEntity.getReadOnly());
		assertTrue(javaContextEntity.getReadOnly().isReadOnly());
		assertTrue(ormContextEntity.getReadOnly().isReadOnly());
		assertTrue(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertTrue(ormContextEntity.getReadOnly().getSpecifiedReadOnly());
		
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
		
		assertFalse(resourceEntity.getReadOnly());
		assertFalse(javaContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertFalse(ormContextEntity.getReadOnly().getSpecifiedReadOnly());
		
		// set xml read only to true, check override
		
		resourceEntity.setReadOnly(Boolean.TRUE);
		
		assertTrue(resourceEntity.getReadOnly());
		assertFalse(javaContextEntity.getReadOnly().isReadOnly());
		assertTrue(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertTrue(ormContextEntity.getReadOnly().getSpecifiedReadOnly());
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
		
		assertTrue(resourceEntity.getReadOnly());
		assertTrue(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertTrue(ormContextEntity.getReadOnly().getSpecifiedReadOnly());
		
		// set context read only to false, check resource
		
		ormContextEntity.getReadOnly().setSpecifiedReadOnly(Boolean.FALSE);
		
		assertFalse(resourceEntity.getReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertFalse(ormContextEntity.getReadOnly().getSpecifiedReadOnly());
		
		// set context read only to null, check resource
		
		ormContextEntity.getReadOnly().setSpecifiedReadOnly(null);
		
		assertNull(resourceEntity.getReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isReadOnly());
		assertFalse(ormContextEntity.getReadOnly().isDefaultReadOnly());
		assertNull(ormContextEntity.getReadOnly().getSpecifiedReadOnly());	
	}
}

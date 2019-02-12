/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkAccessType;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLink2_1ContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLink2_1EntityMappingsTests
	extends EclipseLink2_1ContextModelTestCase
{
	public EclipseLink2_1EntityMappingsTests(String name) {
		super(name);
	}

	public void testUpdateSpecifiedAccess() throws Exception {
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
		
		//set access in the resource model, verify context model updated
		getXmlEntityMappings().setAccess(org.eclipse.jpt.jpa.core.resource.orm.AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, getEntityMappings().getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.AccessType.PROPERTY, getXmlEntityMappings().getAccess());

		//set access to VIRTUAL in the resource model, verify context model updated
		getXmlEntityMappings().setAccess(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkAccessType.VIRTUAL);
		assertEquals(EclipseLinkAccessType.VIRTUAL, getEntityMappings().getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkAccessType.VIRTUAL, getXmlEntityMappings().getAccess());
		
		//set access to null in the resource model
		getXmlEntityMappings().setAccess(null);
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
	}
	
	public void testModifySpecifiedAccess() throws Exception {
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
		
		//set access in the context model, verify resource model modified
		getEntityMappings().setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, getEntityMappings().getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.AccessType.PROPERTY, getXmlEntityMappings().getAccess());

		//set access in the context model, verify resource model modified
		getEntityMappings().setSpecifiedAccess(EclipseLinkAccessType.VIRTUAL);
		assertEquals(EclipseLinkAccessType.VIRTUAL, getEntityMappings().getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkAccessType.VIRTUAL, getXmlEntityMappings().getAccess());
		
		//set access to null in the context model
		getEntityMappings().setSpecifiedAccess(null);
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
	}
	
	public void testUpdateDefaultAccess() throws Exception {
		assertNull(getEntityMappings().getDefaultAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
	
		getXmlEntityMappings().setPersistenceUnitMetadata(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitDefaults persistenceUnitDefaults = EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults();
		getXmlEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(persistenceUnitDefaults);
		persistenceUnitDefaults.setAccess(org.eclipse.jpt.jpa.core.resource.orm.AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, getEntityMappings().getDefaultAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
		
		persistenceUnitDefaults.setAccess(org.eclipse.jpt.jpa.core.resource.orm.AccessType.FIELD);
		assertEquals(AccessType.FIELD, getEntityMappings().getDefaultAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
		
		persistenceUnitDefaults.setAccess(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkAccessType.VIRTUAL);
		assertEquals(EclipseLinkAccessType.VIRTUAL, getEntityMappings().getDefaultAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
		
		persistenceUnitDefaults.setAccess(null);
		assertNull(getEntityMappings().getDefaultAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
	}

	public void testUpdateAccess() throws Exception {
		assertNull(getEntityMappings().getAccess());
		assertNull(getEntityMappings().getDefaultAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
	
		getXmlEntityMappings().setAccess(org.eclipse.jpt.jpa.core.resource.orm.AccessType.FIELD);
		assertNull(getEntityMappings().getDefaultAccess());
		assertEquals(AccessType.FIELD, getEntityMappings().getAccess());
		assertEquals(AccessType.FIELD, getEntityMappings().getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.AccessType.FIELD, getXmlEntityMappings().getAccess());
		
		getXmlEntityMappings().setAccess(null);
		assertNull(getEntityMappings().getAccess());
		assertNull(getEntityMappings().getDefaultAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());

		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedAccess(AccessType.FIELD);
		assertEquals(AccessType.FIELD, getEntityMappings().getDefaultAccess());
		assertEquals(AccessType.FIELD, getEntityMappings().getAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());

		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedAccess(EclipseLinkAccessType.VIRTUAL);
		assertEquals(EclipseLinkAccessType.VIRTUAL, getEntityMappings().getDefaultAccess());
		assertEquals(EclipseLinkAccessType.VIRTUAL, getEntityMappings().getAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
		
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedAccess(null);
		assertNull(getEntityMappings().getDefaultAccess());
		assertNull(getEntityMappings().getAccess());
		assertNull(getEntityMappings().getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getAccess());
	}	

	public void testUpdateSpecifiedGetMethod() throws Exception {
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		assertNull(entityMappings.getSpecifiedGetMethod());
		assertNull(getXmlEntityMappings().getAccessMethods());
		
		//set getMethod in the resource model, verify context model updated
		getXmlEntityMappings().setAccessMethods(EclipseLinkOrmFactory.eINSTANCE.createXmlAccessMethods());
		getXmlEntityMappings().getAccessMethods().setGetMethod("getFoo");
		assertEquals("getFoo", entityMappings.getSpecifiedGetMethod());
		assertEquals("getFoo", getXmlEntityMappings().getAccessMethods().getGetMethod());

		//set getMethod to null in the resource model
		getXmlEntityMappings().getAccessMethods().setGetMethod(null);
		assertNull(entityMappings.getSpecifiedGetMethod());
		assertNull(getXmlEntityMappings().getAccessMethods().getGetMethod());
	}

	public void testModifySpecifiedGetMethod() throws Exception {		
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		assertNull(entityMappings.getSpecifiedGetMethod());
		assertNull(getXmlEntityMappings().getAccessMethods());

		//set getMethod in the context model, verify resource model modified
		entityMappings.setSpecifiedGetMethod("getMe");
		assertEquals("getMe", entityMappings.getSpecifiedGetMethod());
		assertEquals("getMe", getXmlEntityMappings().getAccessMethods().getGetMethod());

		//set getMethod to null in the context model
		entityMappings.setSpecifiedGetMethod(null);
		assertNull(entityMappings.getSpecifiedGetMethod());
		assertNull(getXmlEntityMappings().getAccessMethods());
	}

	public void testModifySpecifiedGetMethod2() throws Exception {
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		assertNull(entityMappings.getSpecifiedGetMethod());
		assertNull(getXmlEntityMappings().getAccessMethods());

		//set getMethod in the context model, verify resource model modified
		entityMappings.setSpecifiedGetMethod("getMe");
		assertEquals("getMe", entityMappings.getGetMethod());
		assertEquals("getMe", getXmlEntityMappings().getAccessMethods().getGetMethod());

		//set another element on the access-methods element so it doesn't get removed
		getXmlEntityMappings().getAccessMethods().setSetMethod("setMe");
		//set getMethod to null in the context model
		entityMappings.setSpecifiedGetMethod(null);
		assertNull(entityMappings.getGetMethod());
		assertNull(getXmlEntityMappings().getAccessMethods().getGetMethod());
	}

	public void testGetDefaultGetMethod() throws Exception {
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		assertNull(entityMappings.getDefaultGetMethod());
		assertNull(entityMappings.getGetMethod());

		getPersistenceUnitDefaults().setSpecifiedGetMethod("getFoo");
		assertEquals("getFoo", entityMappings.getDefaultGetMethod());
		assertEquals("getFoo", entityMappings.getGetMethod());

		entityMappings.setSpecifiedGetMethod("getBar");
		assertEquals("getFoo", entityMappings.getDefaultGetMethod());
		assertEquals("getBar", entityMappings.getGetMethod());

		getPersistenceUnitDefaults().setSpecifiedGetMethod(null);
		assertNull(entityMappings.getDefaultGetMethod());
		assertEquals("getBar", entityMappings.getGetMethod());		
	}

	public void testGetDefaultSetMethod() throws Exception {
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		assertNull(entityMappings.getDefaultSetMethod());
		assertNull(entityMappings.getSetMethod());

		getPersistenceUnitDefaults().setSpecifiedSetMethod("setFoo");
		assertEquals("setFoo", entityMappings.getDefaultSetMethod());
		assertEquals("setFoo", entityMappings.getSetMethod());

		entityMappings.setSpecifiedSetMethod("setBar");
		assertEquals("setFoo", entityMappings.getDefaultSetMethod());
		assertEquals("setBar", entityMappings.getSetMethod());

		getPersistenceUnitDefaults().setSpecifiedSetMethod(null);
		assertNull(entityMappings.getDefaultSetMethod());
		assertEquals("setBar", entityMappings.getSetMethod());		
	}

	public void testUpdateSpecifiedSetMethod() throws Exception {
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		assertNull(entityMappings.getSpecifiedSetMethod());
		assertNull(getXmlEntityMappings().getAccessMethods());

		//set setMethod in the resource model, verify context model updated
		getXmlEntityMappings().setAccessMethods(EclipseLinkOrmFactory.eINSTANCE.createXmlAccessMethods());

		getXmlEntityMappings().getAccessMethods().setSetMethod("setFoo");
		assertEquals("setFoo", entityMappings.getSetMethod());
		assertEquals("setFoo", getXmlEntityMappings().getAccessMethods().getSetMethod());

		//set setMethod to null in the resource model
		getXmlEntityMappings().getAccessMethods().setSetMethod(null);
		assertNull(entityMappings.getSetMethod());
		assertNull(getXmlEntityMappings().getAccessMethods().getSetMethod());
	}

	public void testModifySpecifiedSetMethod() throws Exception {		
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		assertNull(entityMappings.getSpecifiedSetMethod());
		assertNull(getXmlEntityMappings().getAccessMethods());

		//set setMethod in the context model, verify resource model modified
		entityMappings.setSpecifiedSetMethod("setMe");
		assertEquals("setMe", entityMappings.getSetMethod());
		assertEquals("setMe", getXmlEntityMappings().getAccessMethods().getSetMethod());

		//set setMethod to null in the context model
		entityMappings.setSpecifiedSetMethod(null);
		assertNull(entityMappings.getSetMethod());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
	}

	public void testModifySpecifiedSetMethod2() throws Exception {
		EclipseLinkEntityMappings entityMappings = getEntityMappings();
		assertNull(entityMappings.getSpecifiedSetMethod());
		assertNull(getXmlEntityMappings().getAccessMethods());

		//set setMethod in the context model, verify resource model modified
		entityMappings.setSpecifiedSetMethod("setMe");
		assertEquals("setMe", entityMappings.getSetMethod());
		assertEquals("setMe", getXmlEntityMappings().getAccessMethods().getSetMethod());

		//set another element on the access-methods element so it doesn't get removed
		getXmlEntityMappings().getAccessMethods().setGetMethod("getMe");
		//set getMethod to null in the context model
		entityMappings.setSpecifiedSetMethod(null);
		assertNull(entityMappings.getSetMethod());
		assertNull(getXmlEntityMappings().getAccessMethods().getSetMethod());
	}

	protected EclipseLinkPersistenceUnitDefaults getPersistenceUnitDefaults() {
		return (EclipseLinkPersistenceUnitDefaults) getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults();
	}
}
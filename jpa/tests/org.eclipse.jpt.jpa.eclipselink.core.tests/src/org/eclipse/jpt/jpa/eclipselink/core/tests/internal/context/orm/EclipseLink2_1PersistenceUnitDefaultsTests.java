/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkAccessType;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLink2_1ContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLink2_1PersistenceUnitDefaultsTests
	extends EclipseLink2_1ContextModelTestCase
{
	public EclipseLink2_1PersistenceUnitDefaultsTests(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptJpaEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_RUNTIME_PATH.toString());
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	protected EclipseLinkPersistenceUnitDefaults getPersistenceUnitDefaults() {
		return(EclipseLinkPersistenceUnitDefaults) getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults();
	}
	
	protected XmlPersistenceUnitDefaults getXmlPersistenceUnitDefaults() {
		return (XmlPersistenceUnitDefaults) getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults();
	}
	
	public void testIsAllFeaturesUnset() throws Exception {
		OrmPersistenceUnitDefaults persistenceUnitDefaults = getPersistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		XmlPersistenceUnitMetadata persistenceUnitMetadata = EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata();
		getXmlEntityMappings().setPersistenceUnitMetadata(persistenceUnitMetadata);
		assertTrue(persistenceUnitMetadata.isUnset());
		
		XmlPersistenceUnitDefaults persistenceUnitDefaultsResource = EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults();
		persistenceUnitMetadata.setPersistenceUnitDefaults(persistenceUnitDefaultsResource);
		assertTrue(persistenceUnitDefaultsResource.isUnset());
		
		persistenceUnitDefaultsResource.setCascadePersist(true);
		assertFalse(persistenceUnitDefaultsResource.isUnset());
		
		persistenceUnitDefaultsResource.setCascadePersist(false);
		assertTrue(persistenceUnitDefaultsResource.isUnset());
		
		persistenceUnitDefaultsResource.setSchema("asdf");
		assertFalse(persistenceUnitDefaultsResource.isUnset());
		
		persistenceUnitDefaultsResource.setSchema(null);
		assertTrue(persistenceUnitDefaultsResource.isUnset());
		
		persistenceUnitDefaultsResource.setCatalog("asdf");
		assertFalse(persistenceUnitDefaultsResource.isUnset());
		
		persistenceUnitDefaultsResource.setCatalog(null);
		assertTrue(persistenceUnitDefaultsResource.isUnset());
		
		
		persistenceUnitDefaultsResource.setAccess(org.eclipse.jpt.jpa.core.resource.orm.AccessType.PROPERTY);
		assertFalse(persistenceUnitDefaultsResource.isUnset());
		
		persistenceUnitDefaultsResource.setAccess(org.eclipse.jpt.jpa.core.resource.orm.AccessType.FIELD);
		assertFalse(persistenceUnitDefaultsResource.isUnset());
		
		persistenceUnitDefaultsResource.setAccess(null);
		assertTrue(persistenceUnitDefaultsResource.isUnset());
	}

	
	public void testUpdateAccess() throws Exception {
		OrmPersistenceUnitDefaults persistenceUnitDefaults = getPersistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set access in the resource model, verify context model updated
		getXmlEntityMappings().setPersistenceUnitMetadata(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(org.eclipse.jpt.jpa.core.resource.orm.AccessType.FIELD);
		assertEquals(AccessType.FIELD, persistenceUnitDefaults.getAccess());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.AccessType.FIELD, getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());

		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkAccessType.VIRTUAL);
		assertEquals(EclipseLinkAccessType.VIRTUAL, persistenceUnitDefaults.getAccess());
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkAccessType.VIRTUAL, getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());

		//set access to null in the resource model
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(null);
		assertNull(persistenceUnitDefaults.getAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
	}
	
	public void testModifyAccess() throws Exception {		
		OrmPersistenceUnitDefaults persistenceUnitDefaults = getPersistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set access in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, persistenceUnitDefaults.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.AccessType.PROPERTY, getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());

		persistenceUnitDefaults.setSpecifiedAccess(EclipseLinkAccessType.VIRTUAL);
		assertEquals(EclipseLinkAccessType.VIRTUAL, persistenceUnitDefaults.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkAccessType.VIRTUAL, getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
		
		//set access to null in the context model
		persistenceUnitDefaults.setSpecifiedAccess(null);
		assertNull(persistenceUnitDefaults.getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
	}
	
	public void testModifyAccess2() throws Exception {
		OrmPersistenceUnitDefaults persistenceUnitDefaults = getPersistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set access in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedAccess(AccessType.FIELD);
		assertEquals(AccessType.FIELD, persistenceUnitDefaults.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.AccessType.FIELD, getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
		
		//set access in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedAccess(EclipseLinkAccessType.VIRTUAL);
		assertEquals(EclipseLinkAccessType.VIRTUAL, persistenceUnitDefaults.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkAccessType.VIRTUAL, getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
		
		//set another element on the persistence-unit-defaults element so it doesn't get removed
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCascadePersist(true);
		//set access to null in the context model
		persistenceUnitDefaults.setSpecifiedAccess(null);
		assertNull(persistenceUnitDefaults.getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
	}
	
	public void testModifyAccess3() throws Exception {
		OrmPersistenceUnitDefaults persistenceUnitDefaults = getPersistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		//set another element on the persistence-unit-metadata element so only persistence-unit-defaults element gets removed
		getXmlEntityMappings().setPersistenceUnitMetadata(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		
		//set access in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedAccess(AccessType.FIELD);
		assertEquals(AccessType.FIELD, persistenceUnitDefaults.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.AccessType.FIELD, getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
		
		//set access in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedAccess(EclipseLinkAccessType.VIRTUAL);
		assertEquals(EclipseLinkAccessType.VIRTUAL, persistenceUnitDefaults.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkAccessType.VIRTUAL, getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
		
		//set access to null in the context model
		persistenceUnitDefaults.setSpecifiedAccess(null);
		assertNull(persistenceUnitDefaults.getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults());
	}
	
	public void testUpdateGetMethod() throws Exception {
		EclipseLinkPersistenceUnitDefaults persistenceUnitDefaults = getPersistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getGetMethod());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set getMethod in the resource model, verify context model updated
		getXmlEntityMappings().setPersistenceUnitMetadata(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
		getXmlPersistenceUnitDefaults().setAccessMethods(EclipseLinkOrmFactory.eINSTANCE.createXmlAccessMethods());

		getXmlPersistenceUnitDefaults().getAccessMethods().setGetMethod("getFoo");
		assertEquals("getFoo", persistenceUnitDefaults.getGetMethod());
		assertEquals("getFoo", getXmlPersistenceUnitDefaults().getAccessMethods().getGetMethod());
	
		//set getMethod to null in the resource model
		getXmlPersistenceUnitDefaults().getAccessMethods().setGetMethod(null);
		assertNull(persistenceUnitDefaults.getGetMethod());
		assertNull(getXmlPersistenceUnitDefaults().getAccessMethods().getGetMethod());
	}
	
	public void testModifyGetMethod() throws Exception {		
		EclipseLinkPersistenceUnitDefaults persistenceUnitDefaults = getPersistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getGetMethod());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set getMethod in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedGetMethod("getMe");
		assertEquals("getMe", persistenceUnitDefaults.getGetMethod());
		assertEquals("getMe", getXmlPersistenceUnitDefaults().getAccessMethods().getGetMethod());
		
		//set getMethod to null in the context model
		persistenceUnitDefaults.setSpecifiedGetMethod(null);
		assertNull(persistenceUnitDefaults.getGetMethod());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
	}
	
	public void testModifyGetMethod2() throws Exception {
		EclipseLinkPersistenceUnitDefaults persistenceUnitDefaults = getPersistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getGetMethod());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set getMethod in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedGetMethod("getMe");
		assertEquals("getMe", persistenceUnitDefaults.getGetMethod());
		assertEquals("getMe", getXmlPersistenceUnitDefaults().getAccessMethods().getGetMethod());
		
		//set another element on the access-methods element so it doesn't get removed
		getXmlPersistenceUnitDefaults().getAccessMethods().setSetMethod("setMe");
		//set getMethod to null in the context model
		persistenceUnitDefaults.setSpecifiedGetMethod(null);
		assertNull(persistenceUnitDefaults.getGetMethod());
		assertNull(getXmlPersistenceUnitDefaults().getAccessMethods().getGetMethod());
	}
	
	public void testModifyGetMethod3() throws Exception {
		EclipseLinkPersistenceUnitDefaults persistenceUnitDefaults = getPersistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getGetMethod());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		//set another element on the ersistence-unit-defaults element so only access-methods element gets removed
		getXmlEntityMappings().setPersistenceUnitMetadata(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCascadePersist(true);
		
		//set getMethod in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedGetMethod("getMe");
		assertEquals("getMe", persistenceUnitDefaults.getGetMethod());
		assertEquals("getMe", getXmlPersistenceUnitDefaults().getAccessMethods().getGetMethod());
		
		//set getMethod to null in the context model
		persistenceUnitDefaults.setSpecifiedGetMethod(null);
		assertNull(persistenceUnitDefaults.getGetMethod());
		assertNull(getXmlPersistenceUnitDefaults().getAccessMethods());
	}
	
	public void testUpdateSetMethod() throws Exception {
		EclipseLinkPersistenceUnitDefaults persistenceUnitDefaults = getPersistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSetMethod());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set getMethod in the resource model, verify context model updated
		getXmlEntityMappings().setPersistenceUnitMetadata(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
		getXmlPersistenceUnitDefaults().setAccessMethods(EclipseLinkOrmFactory.eINSTANCE.createXmlAccessMethods());

		getXmlPersistenceUnitDefaults().getAccessMethods().setSetMethod("setFoo");
		assertEquals("setFoo", persistenceUnitDefaults.getSetMethod());
		assertEquals("setFoo", getXmlPersistenceUnitDefaults().getAccessMethods().getSetMethod());
	
		//set getMethod to null in the resource model
		getXmlPersistenceUnitDefaults().getAccessMethods().setSetMethod(null);
		assertNull(persistenceUnitDefaults.getSetMethod());
		assertNull(getXmlPersistenceUnitDefaults().getAccessMethods().getSetMethod());
	}
	
	public void testModifySetMethod() throws Exception {		
		EclipseLinkPersistenceUnitDefaults persistenceUnitDefaults = getPersistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSetMethod());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set getMethod in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedSetMethod("setMe");
		assertEquals("setMe", persistenceUnitDefaults.getSetMethod());
		assertEquals("setMe", getXmlPersistenceUnitDefaults().getAccessMethods().getSetMethod());
		
		//set getMethod to null in the context model
		persistenceUnitDefaults.setSpecifiedSetMethod(null);
		assertNull(persistenceUnitDefaults.getSetMethod());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
	}
	
	public void testModifySetMethod2() throws Exception {
		EclipseLinkPersistenceUnitDefaults persistenceUnitDefaults = getPersistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSetMethod());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set getMethod in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedSetMethod("setMe");
		assertEquals("setMe", persistenceUnitDefaults.getSetMethod());
		assertEquals("setMe", getXmlPersistenceUnitDefaults().getAccessMethods().getSetMethod());
		
		//set another element on the access-methods element so it doesn't get removed
		getXmlPersistenceUnitDefaults().getAccessMethods().setGetMethod("getMe");
		//set getMethod to null in the context model
		persistenceUnitDefaults.setSpecifiedSetMethod(null);
		assertNull(persistenceUnitDefaults.getSetMethod());
		assertNull(getXmlPersistenceUnitDefaults().getAccessMethods().getSetMethod());
	}
	
	public void testModifySetMethod3() throws Exception {
		EclipseLinkPersistenceUnitDefaults persistenceUnitDefaults = getPersistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSetMethod());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		//set another element on the ersistence-unit-defaults element so only access-methods element gets removed
		getXmlEntityMappings().setPersistenceUnitMetadata(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCascadePersist(true);
		
		//set getMethod in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedSetMethod("setMe");
		assertEquals("setMe", persistenceUnitDefaults.getSetMethod());
		assertEquals("setMe", getXmlPersistenceUnitDefaults().getAccessMethods().getSetMethod());
		
		//set getMethod to null in the context model
		persistenceUnitDefaults.setSpecifiedSetMethod(null);
		assertNull(persistenceUnitDefaults.getSetMethod());
		assertNull(getXmlPersistenceUnitDefaults().getAccessMethods());
	}

}
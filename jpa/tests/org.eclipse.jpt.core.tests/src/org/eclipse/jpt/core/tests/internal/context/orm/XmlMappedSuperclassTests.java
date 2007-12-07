/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlMappedSuperclassTests extends ContextModelTestCase
{
	public XmlMappedSuperclassTests(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		persistenceResource().save(null);
	}
		
	protected XmlPersistence xmlPersistence() {
		return persistenceResource().getPersistence();
	}
	
	protected EntityMappings entityMappings() {
		return persistenceUnit().mappingFileRefs().next().getOrmXml().getEntityMappings();
	}
	
	public void testUpdateClass() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		XmlMappedSuperclass xmlMappedSuperclass = (XmlMappedSuperclass) xmlPersistentType.getMapping();
		MappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertEquals("model.Foo", xmlMappedSuperclass.getClass_());
		assertEquals("model.Foo", mappedSuperclassResource.getClassName());
		
		//set class in the resource model, verify context model updated
		mappedSuperclassResource.setClassName("com.Bar");
		ormResource().save(null);
		assertEquals("com.Bar", xmlMappedSuperclass.getClass_());
		assertEquals("com.Bar", mappedSuperclassResource.getClassName());
	
		//set class to null in the resource model
		mappedSuperclassResource.setClassName(null);
		assertNull(xmlMappedSuperclass.getClass_());
		assertNull(mappedSuperclassResource.getClassName());
	}
	
	public void testModifyClass() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		XmlMappedSuperclass xmlMappedSuperclass = (XmlMappedSuperclass) xmlPersistentType.getMapping();
		MappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertEquals("model.Foo", xmlMappedSuperclass.getClass_());
		assertEquals("model.Foo", mappedSuperclassResource.getClassName());
		
		//set class in the context model, verify resource model modified
		xmlMappedSuperclass.setClass("com.Bar");
		assertEquals("com.Bar", xmlMappedSuperclass.getClass_());
		assertEquals("com.Bar", mappedSuperclassResource.getClassName());
		
		//set class to null in the context model
		xmlMappedSuperclass.setClass(null);
		assertNull(xmlMappedSuperclass.getClass_());
		assertNull(mappedSuperclassResource.getClassName());
	}
	//TODO add tests for setting the className when the package is set on entity-mappings
	
	public void testUpdateSpecifiedAccess() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		XmlMappedSuperclass xmlMappedSuperclass = (XmlMappedSuperclass) xmlPersistentType.getMapping();
		MappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertNull(xmlMappedSuperclass.getSpecifiedAccess());
		assertNull(mappedSuperclassResource.getAccess());
		
		//set access in the resource model, verify context model updated
		mappedSuperclassResource.setAccess(org.eclipse.jpt.core.internal.resource.orm.AccessType.FIELD);
		assertEquals(AccessType.FIELD, xmlMappedSuperclass.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.FIELD, mappedSuperclassResource.getAccess());
	
		//set access to null in the resource model
		mappedSuperclassResource.setAccess(null);
		assertNull(xmlMappedSuperclass.getSpecifiedAccess());
		assertNull(mappedSuperclassResource.getAccess());
	}
	
	public void testModifySpecifiedAccess() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		XmlMappedSuperclass xmlMappedSuperclass = (XmlMappedSuperclass) xmlPersistentType.getMapping();
		MappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);
		assertNull(xmlMappedSuperclass.getSpecifiedAccess());
		assertNull(mappedSuperclassResource.getAccess());
		
		//set access in the context model, verify resource model modified
		xmlMappedSuperclass.setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, xmlMappedSuperclass.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, mappedSuperclassResource.getAccess());
		
		//set access to null in the context model
		xmlMappedSuperclass.setSpecifiedAccess(null);
		assertNull(xmlMappedSuperclass.getSpecifiedAccess());
		assertNull(mappedSuperclassResource.getAccess());
	}
	//TODO test default access from
		//underlying java
		//persistence-unit-defaults
		//entity-mappings
		//with xml-mapping-metadata-complete set
	
	public void testUpdateSpecifiedMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		XmlMappedSuperclass xmlMappedSuperclass = (XmlMappedSuperclass) xmlPersistentType.getMapping();
		MappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		//set metadata-complete in the resource model, verify context model updated
		mappedSuperclassResource.setMetadataComplete(true);
		assertTrue(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertTrue(mappedSuperclassResource.getMetadataComplete());
	
		//set access to false in the resource model
		mappedSuperclassResource.setMetadataComplete(false);
		assertFalse(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertFalse(mappedSuperclassResource.getMetadataComplete());
		
		mappedSuperclassResource.setMetadataComplete(null);
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
	}
	
	public void testModifySpecifiedMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		XmlMappedSuperclass xmlMappedSuperclass = (XmlMappedSuperclass) xmlPersistentType.getMapping();
		MappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		//set access in the context model, verify resource model modified
		xmlMappedSuperclass.setSpecifiedMetadataComplete(Boolean.TRUE);
		assertTrue(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertTrue(mappedSuperclassResource.getMetadataComplete());
		
		//set access to null in the context model
		xmlMappedSuperclass.setSpecifiedMetadataComplete(Boolean.FALSE);
		assertFalse(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertFalse(mappedSuperclassResource.getMetadataComplete());
		
		xmlMappedSuperclass.setSpecifiedMetadataComplete(null);
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
	}
	
	public void testUpdateDefaultMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		XmlMappedSuperclass xmlMappedSuperclass = (XmlMappedSuperclass) xmlPersistentType.getMapping();
		MappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertFalse(xmlMappedSuperclass.isDefaultMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(xmlMappedSuperclass.isDefaultMetadataComplete());
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertFalse(xmlMappedSuperclass.isDefaultMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
	}
	
	public void testUpdateMetadataComplete() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType("model.Foo", IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		XmlMappedSuperclass xmlMappedSuperclass = (XmlMappedSuperclass) xmlPersistentType.getMapping();
		MappedSuperclass mappedSuperclassResource = ormResource().getEntityMappings().getMappedSuperclasses().get(0);		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertFalse(xmlMappedSuperclass.isMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertTrue(xmlMappedSuperclass.isMetadataComplete());
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
		
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertFalse(xmlMappedSuperclass.isMetadataComplete());
		assertNull(xmlMappedSuperclass.getSpecifiedMetadataComplete());
		assertNull(mappedSuperclassResource.getMetadataComplete());
	}

}
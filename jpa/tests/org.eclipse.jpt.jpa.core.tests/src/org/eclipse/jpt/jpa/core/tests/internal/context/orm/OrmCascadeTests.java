/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.orm;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.Cascade;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmCascadeTests extends ContextModelTestCase
{
	public OrmCascadeTests(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	public void testUpdateCascadeAll() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		Cascade cascade = ormOneToOneMapping.getCascade();
		
		assertEquals(false, cascade.isAll());
		assertNull(oneToOne.getCascade());
		
		//set cascade in the resource model, verify context model does not change
		oneToOne.setCascade(OrmFactory.eINSTANCE.createCascadeType());
		assertEquals(false, cascade.isAll());
		assertNotNull(oneToOne.getCascade());
				
		//set all in the resource model, verify context model updated
		oneToOne.getCascade().setCascadeAll(true);
		assertEquals(true, cascade.isAll());
		assertEquals(true, oneToOne.getCascade().isCascadeAll());
		
		//set all to false in the resource model
		oneToOne.getCascade().setCascadeAll(false);
		assertEquals(false, cascade.isAll());
		assertEquals(false, oneToOne.getCascade().isCascadeAll());
	}
	
	public void testModifyCascadeAll() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		Cascade cascade = ormOneToOneMapping.getCascade();
		
		assertEquals(false, cascade.isAll());
		assertNull(oneToOne.getCascade());
					
		//set all in the context model, verify resource model updated
		cascade.setAll(true);
		assertEquals(true, cascade.isAll());
		assertEquals(true, oneToOne.getCascade().isCascadeAll());
	
		//set all to false in the context model
		cascade.setAll(false);
		assertEquals(false, cascade.isAll());
		assertNull(oneToOne.getCascade());
	}
	
	public void testUpdateCascadePersist() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		Cascade cascade = ormOneToOneMapping.getCascade();
		
		assertEquals(false, cascade.isPersist());
		assertNull(oneToOne.getCascade());
		
		//set cascade in the resource model, verify context model does not change
		oneToOne.setCascade(OrmFactory.eINSTANCE.createCascadeType());
		assertEquals(false, cascade.isPersist());
		assertNotNull(oneToOne.getCascade());
				
		//set Persist in the resource model, verify context model updated
		oneToOne.getCascade().setCascadePersist(true);
		assertEquals(true, cascade.isPersist());
		assertEquals(true, oneToOne.getCascade().isCascadePersist());
		
		//set Persist to false in the resource model
		oneToOne.getCascade().setCascadePersist(false);
		assertEquals(false, cascade.isPersist());
		assertEquals(false, oneToOne.getCascade().isCascadePersist());
	}
	
	public void testModifyCascadePersist() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		Cascade cascade = ormOneToOneMapping.getCascade();
		
		assertEquals(false, cascade.isPersist());
		assertNull(oneToOne.getCascade());
					
		//set Persist in the context model, verify resource model updated
		cascade.setPersist(true);
		assertEquals(true, cascade.isPersist());
		assertEquals(true, oneToOne.getCascade().isCascadePersist());
	
		//set Persist to false in the context model
		cascade.setPersist(false);
		assertEquals(false, cascade.isPersist());
		assertNull(oneToOne.getCascade());
	}
	
	public void testUpdateCascadeMerge() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		Cascade cascade = ormOneToOneMapping.getCascade();
		
		assertEquals(false, cascade.isMerge());
		assertNull(oneToOne.getCascade());
		
		//set cascade in the resource model, verify context model does not change
		oneToOne.setCascade(OrmFactory.eINSTANCE.createCascadeType());
		assertEquals(false, cascade.isMerge());
		assertNotNull(oneToOne.getCascade());
				
		//set Merge in the resource model, verify context model updated
		oneToOne.getCascade().setCascadeMerge(true);
		assertEquals(true, cascade.isMerge());
		assertEquals(true, oneToOne.getCascade().isCascadeMerge());
		
		//set Merge to false in the resource model
		oneToOne.getCascade().setCascadeMerge(false);
		assertEquals(false, cascade.isMerge());
		assertEquals(false, oneToOne.getCascade().isCascadeMerge());
	}
	
	public void testModifyCascadeMerge() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		Cascade cascade = ormOneToOneMapping.getCascade();
		
		assertEquals(false, cascade.isMerge());
		assertNull(oneToOne.getCascade());
					
		//set Merge in the context model, verify resource model updated
		cascade.setMerge(true);
		assertEquals(true, cascade.isMerge());
		assertEquals(true, oneToOne.getCascade().isCascadeMerge());
	
		//set Merge to false in the context model
		cascade.setMerge(false);
		assertEquals(false, cascade.isMerge());
		assertNull(oneToOne.getCascade());
	}
	
	public void testUpdateCascadeRemove() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		Cascade cascade = ormOneToOneMapping.getCascade();
		
		assertEquals(false, cascade.isRemove());
		assertNull(oneToOne.getCascade());
		
		//set cascade in the resource model, verify context model does not change
		oneToOne.setCascade(OrmFactory.eINSTANCE.createCascadeType());
		assertEquals(false, cascade.isRemove());
		assertNotNull(oneToOne.getCascade());
				
		//set Remove in the resource model, verify context model updated
		oneToOne.getCascade().setCascadeRemove(true);
		assertEquals(true, cascade.isRemove());
		assertEquals(true, oneToOne.getCascade().isCascadeRemove());
		
		//set Remove to false in the resource model
		oneToOne.getCascade().setCascadeRemove(false);
		assertEquals(false, cascade.isRemove());
		assertEquals(false, oneToOne.getCascade().isCascadeRemove());
	}
	
	public void testModifyCascadeRemove() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		Cascade cascade = ormOneToOneMapping.getCascade();
		
		assertEquals(false, cascade.isRemove());
		assertNull(oneToOne.getCascade());
					
		//set Remove in the context model, verify resource model updated
		cascade.setRemove(true);
		assertEquals(true, cascade.isRemove());
		assertEquals(true, oneToOne.getCascade().isCascadeRemove());
	
		//set Remove to false in the context model
		cascade.setRemove(false);
		assertEquals(false, cascade.isRemove());
		assertNull(oneToOne.getCascade());
	}
	
	public void testUpdateCascadeRefresh() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		Cascade cascade = ormOneToOneMapping.getCascade();
		
		assertEquals(false, cascade.isRefresh());
		assertNull(oneToOne.getCascade());
		
		//set cascade in the resource model, verify context model does not change
		oneToOne.setCascade(OrmFactory.eINSTANCE.createCascadeType());
		assertEquals(false, cascade.isRefresh());
		assertNotNull(oneToOne.getCascade());
				
		//set Refresh in the resource model, verify context model updated
		oneToOne.getCascade().setCascadeRefresh(true);
		assertEquals(true, cascade.isRefresh());
		assertEquals(true, oneToOne.getCascade().isCascadeRefresh());
		
		//set Refresh to false in the resource model
		oneToOne.getCascade().setCascadeRefresh(false);
		assertEquals(false, cascade.isRefresh());
		assertEquals(false, oneToOne.getCascade().isCascadeRefresh());
	}
	
	public void testModifyCascadeRefresh() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		Cascade cascade = ormOneToOneMapping.getCascade();
		
		assertEquals(false, cascade.isRefresh());
		assertNull(oneToOne.getCascade());
					
		//set Refresh in the context model, verify resource model updated
		cascade.setRefresh(true);
		assertEquals(true, cascade.isRefresh());
		assertEquals(true, oneToOne.getCascade().isCascadeRefresh());
	
		//set Refresh to false in the context model
		cascade.setRefresh(false);
		assertEquals(false, cascade.isRefresh());
		assertNull(oneToOne.getCascade());
	}
}
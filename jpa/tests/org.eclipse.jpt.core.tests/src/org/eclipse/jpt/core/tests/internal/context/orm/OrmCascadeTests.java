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

import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmOneToOneMapping;
import org.eclipse.jpt.core.internal.context.orm.OrmCascade;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class OrmCascadeTests extends ContextModelTestCase
{
	public OrmCascadeTests(String name) {
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
	
	public void testUpdateCascadeAll() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		GenericOrmOneToOneMapping xmlOneToOneMapping = (GenericOrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		OrmCascade xmlCascade = xmlOneToOneMapping.getCascade();
		
		assertEquals(false, xmlCascade.isAll());
		assertNull(oneToOne.getCascade());
		
		//set cascade in the resource model, verify context model does not change
		oneToOne.setCascade(OrmFactory.eINSTANCE.createCascadeTypeImpl());
		assertEquals(false, xmlCascade.isAll());
		assertNotNull(oneToOne.getCascade());
				
		//set all in the resource model, verify context model updated
		oneToOne.getCascade().setCascadeAll(true);
		assertEquals(true, xmlCascade.isAll());
		assertEquals(true, oneToOne.getCascade().isCascadeAll());
		
		//set all to false in the resource model
		oneToOne.getCascade().setCascadeAll(false);
		assertEquals(false, xmlCascade.isAll());
		assertEquals(false, oneToOne.getCascade().isCascadeAll());
	}
	
	public void testModifyCascadeAll() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		GenericOrmOneToOneMapping xmlOneToOneMapping = (GenericOrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		OrmCascade xmlCascade = xmlOneToOneMapping.getCascade();
		
		assertEquals(false, xmlCascade.isAll());
		assertNull(oneToOne.getCascade());
					
		//set all in the context model, verify resource model updated
		xmlCascade.setAll(true);		ormResource().save(null);
		assertEquals(true, xmlCascade.isAll());
		assertEquals(true, oneToOne.getCascade().isCascadeAll());
	
		//set all to false in the context model
		xmlCascade.setAll(false);
		assertEquals(false, xmlCascade.isAll());
		assertNull(oneToOne.getCascade());
	}
	
	public void testUpdateCascadePersist() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		GenericOrmOneToOneMapping xmlOneToOneMapping = (GenericOrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		OrmCascade xmlCascade = xmlOneToOneMapping.getCascade();
		
		assertEquals(false, xmlCascade.isPersist());
		assertNull(oneToOne.getCascade());
		
		//set cascade in the resource model, verify context model does not change
		oneToOne.setCascade(OrmFactory.eINSTANCE.createCascadeTypeImpl());
		assertEquals(false, xmlCascade.isPersist());
		assertNotNull(oneToOne.getCascade());
				
		//set Persist in the resource model, verify context model updated
		oneToOne.getCascade().setCascadePersist(true);
		assertEquals(true, xmlCascade.isPersist());
		assertEquals(true, oneToOne.getCascade().isCascadePersist());
		
		//set Persist to false in the resource model
		oneToOne.getCascade().setCascadePersist(false);
		assertEquals(false, xmlCascade.isPersist());
		assertEquals(false, oneToOne.getCascade().isCascadePersist());
	}
	
	public void testModifyCascadePersist() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		GenericOrmOneToOneMapping xmlOneToOneMapping = (GenericOrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		OrmCascade xmlCascade = xmlOneToOneMapping.getCascade();
		
		assertEquals(false, xmlCascade.isPersist());
		assertNull(oneToOne.getCascade());
					
		//set Persist in the context model, verify resource model updated
		xmlCascade.setPersist(true);		ormResource().save(null);
		assertEquals(true, xmlCascade.isPersist());
		assertEquals(true, oneToOne.getCascade().isCascadePersist());
	
		//set Persist to false in the context model
		xmlCascade.setPersist(false);
		assertEquals(false, xmlCascade.isPersist());
		assertNull(oneToOne.getCascade());
	}
	
	public void testUpdateCascadeMerge() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		GenericOrmOneToOneMapping xmlOneToOneMapping = (GenericOrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		OrmCascade xmlCascade = xmlOneToOneMapping.getCascade();
		
		assertEquals(false, xmlCascade.isMerge());
		assertNull(oneToOne.getCascade());
		
		//set cascade in the resource model, verify context model does not change
		oneToOne.setCascade(OrmFactory.eINSTANCE.createCascadeTypeImpl());
		assertEquals(false, xmlCascade.isMerge());
		assertNotNull(oneToOne.getCascade());
				
		//set Merge in the resource model, verify context model updated
		oneToOne.getCascade().setCascadeMerge(true);
		assertEquals(true, xmlCascade.isMerge());
		assertEquals(true, oneToOne.getCascade().isCascadeMerge());
		
		//set Merge to false in the resource model
		oneToOne.getCascade().setCascadeMerge(false);
		assertEquals(false, xmlCascade.isMerge());
		assertEquals(false, oneToOne.getCascade().isCascadeMerge());
	}
	
	public void testModifyCascadeMerge() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		GenericOrmOneToOneMapping xmlOneToOneMapping = (GenericOrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		OrmCascade xmlCascade = xmlOneToOneMapping.getCascade();
		
		assertEquals(false, xmlCascade.isMerge());
		assertNull(oneToOne.getCascade());
					
		//set Merge in the context model, verify resource model updated
		xmlCascade.setMerge(true);		ormResource().save(null);
		assertEquals(true, xmlCascade.isMerge());
		assertEquals(true, oneToOne.getCascade().isCascadeMerge());
	
		//set Merge to false in the context model
		xmlCascade.setMerge(false);
		assertEquals(false, xmlCascade.isMerge());
		assertNull(oneToOne.getCascade());
	}
	
	public void testUpdateCascadeRemove() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		GenericOrmOneToOneMapping xmlOneToOneMapping = (GenericOrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		OrmCascade xmlCascade = xmlOneToOneMapping.getCascade();
		
		assertEquals(false, xmlCascade.isRemove());
		assertNull(oneToOne.getCascade());
		
		//set cascade in the resource model, verify context model does not change
		oneToOne.setCascade(OrmFactory.eINSTANCE.createCascadeTypeImpl());
		assertEquals(false, xmlCascade.isRemove());
		assertNotNull(oneToOne.getCascade());
				
		//set Remove in the resource model, verify context model updated
		oneToOne.getCascade().setCascadeRemove(true);
		assertEquals(true, xmlCascade.isRemove());
		assertEquals(true, oneToOne.getCascade().isCascadeRemove());
		
		//set Remove to false in the resource model
		oneToOne.getCascade().setCascadeRemove(false);
		assertEquals(false, xmlCascade.isRemove());
		assertEquals(false, oneToOne.getCascade().isCascadeRemove());
	}
	
	public void testModifyCascadeRemove() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		GenericOrmOneToOneMapping xmlOneToOneMapping = (GenericOrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		OrmCascade xmlCascade = xmlOneToOneMapping.getCascade();
		
		assertEquals(false, xmlCascade.isRemove());
		assertNull(oneToOne.getCascade());
					
		//set Remove in the context model, verify resource model updated
		xmlCascade.setRemove(true);		ormResource().save(null);
		assertEquals(true, xmlCascade.isRemove());
		assertEquals(true, oneToOne.getCascade().isCascadeRemove());
	
		//set Remove to false in the context model
		xmlCascade.setRemove(false);
		assertEquals(false, xmlCascade.isRemove());
		assertNull(oneToOne.getCascade());
	}
	
	public void testUpdateCascadeRefresh() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		GenericOrmOneToOneMapping xmlOneToOneMapping = (GenericOrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		OrmCascade xmlCascade = xmlOneToOneMapping.getCascade();
		
		assertEquals(false, xmlCascade.isRefresh());
		assertNull(oneToOne.getCascade());
		
		//set cascade in the resource model, verify context model does not change
		oneToOne.setCascade(OrmFactory.eINSTANCE.createCascadeTypeImpl());
		assertEquals(false, xmlCascade.isRefresh());
		assertNotNull(oneToOne.getCascade());
				
		//set Refresh in the resource model, verify context model updated
		oneToOne.getCascade().setCascadeRefresh(true);
		assertEquals(true, xmlCascade.isRefresh());
		assertEquals(true, oneToOne.getCascade().isCascadeRefresh());
		
		//set Refresh to false in the resource model
		oneToOne.getCascade().setCascadeRefresh(false);
		assertEquals(false, xmlCascade.isRefresh());
		assertEquals(false, oneToOne.getCascade().isCascadeRefresh());
	}
	
	public void testModifyCascadeRefresh() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		GenericOrmOneToOneMapping xmlOneToOneMapping = (GenericOrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		OrmCascade xmlCascade = xmlOneToOneMapping.getCascade();
		
		assertEquals(false, xmlCascade.isRefresh());
		assertNull(oneToOne.getCascade());
					
		//set Refresh in the context model, verify resource model updated
		xmlCascade.setRefresh(true);		ormResource().save(null);
		assertEquals(true, xmlCascade.isRefresh());
		assertEquals(true, oneToOne.getCascade().isCascadeRefresh());
	
		//set Refresh to false in the context model
		xmlCascade.setRefresh(false);
		assertEquals(false, xmlCascade.isRefresh());
		assertNull(oneToOne.getCascade());
	}
}
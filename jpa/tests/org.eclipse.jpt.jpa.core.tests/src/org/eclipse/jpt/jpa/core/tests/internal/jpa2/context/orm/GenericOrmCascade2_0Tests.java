/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.orm;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.jpa2.context.Cascade2_0;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericOrmCascade2_0Tests extends Generic2_0ContextModelTestCase
{
	public GenericOrmCascade2_0Tests(String name) {
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
	
	public void testUpdateCascadeDetach() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		Cascade2_0 cascade = (Cascade2_0) ormOneToOneMapping.getCascade();
		
		assertEquals(false, cascade.isDetach());
		assertNull(oneToOne.getCascade());
		
		//set cascade in the resource model, verify context model does not change
		oneToOne.setCascade(OrmFactory.eINSTANCE.createCascadeType());
		assertEquals(false, cascade.isDetach());
		assertNotNull(oneToOne.getCascade());
		
		//set detach in the resource model, verify context model updated
		oneToOne.getCascade().setCascadeDetach(true);
		assertEquals(true, cascade.isDetach());
		assertEquals(true, oneToOne.getCascade().isCascadeDetach());
		
		//set detach to false in the resource model
		oneToOne.getCascade().setCascadeDetach(false);
		assertEquals(false, cascade.isDetach());
		assertEquals(false, oneToOne.getCascade().isCascadeDetach());
	}
	
	public void testModifyCascadeDetach() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		Cascade2_0 cascade = (Cascade2_0) ormOneToOneMapping.getCascade();
		
		assertEquals(false, cascade.isDetach());
		assertNull(oneToOne.getCascade());
		
		//set detach in the context model, verify resource model updated
		cascade.setDetach(true);
		assertEquals(true, cascade.isDetach());
		assertEquals(true, oneToOne.getCascade().isCascadeDetach());
		
		//set detach to false in the context model
		cascade.setDetach(false);
		assertEquals(false, cascade.isDetach());
		assertNull(oneToOne.getCascade());
	}
}

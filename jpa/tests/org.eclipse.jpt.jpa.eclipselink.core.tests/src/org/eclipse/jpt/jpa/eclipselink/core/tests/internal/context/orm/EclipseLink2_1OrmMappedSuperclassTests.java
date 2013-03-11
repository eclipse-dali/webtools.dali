/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLink2_1ContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLink2_1OrmMappedSuperclassTests
	extends EclipseLink2_1ContextModelTestCase
{
	public EclipseLink2_1OrmMappedSuperclassTests(String name) {
		super(name);
	}

	public void testUpdateSpecifiedParentClass() throws Exception {
		EclipseLinkOrmPersistentType persistentType = (EclipseLinkOrmPersistentType) getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Employee");
		EclipseLinkOrmTypeMapping ormTypeMapping = persistentType.getMapping();
		XmlTypeMapping xmlTypeMapping = (XmlTypeMapping) ormTypeMapping.getXmlTypeMapping();
		assertNull(ormTypeMapping.getSpecifiedParentClass());
		assertNull(xmlTypeMapping.getParentClass());

		//set parentClass in the resource model, verify context model updated
		xmlTypeMapping.setParentClass("model.Parent");
		assertEquals("model.Parent", ormTypeMapping.getSpecifiedParentClass());
		assertEquals("model.Parent", xmlTypeMapping.getParentClass());
		
		//set parentClass to null in the resource model
		xmlTypeMapping.setParentClass(null);
		assertNull(ormTypeMapping.getSpecifiedParentClass());
		assertNull(xmlTypeMapping.getParentClass());
	}

	public void testModifySpecifiedParentClass() throws Exception {		
		EclipseLinkOrmPersistentType persistentType = (EclipseLinkOrmPersistentType) getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Employee");
		EclipseLinkOrmTypeMapping ormTypeMapping = persistentType.getMapping();
		XmlTypeMapping xmlTypeMapping = (XmlTypeMapping) ormTypeMapping.getXmlTypeMapping();
		assertNull(ormTypeMapping.getSpecifiedParentClass());
		assertNull(xmlTypeMapping.getParentClass());
		
		//set parentClass in the context model, verify resource model modified
		ormTypeMapping.setSpecifiedParentClass("model.Parent");
		assertEquals("model.Parent", ormTypeMapping.getSpecifiedParentClass());
		assertEquals("model.Parent", xmlTypeMapping.getParentClass());
		
		//set parentClass to null in the context model
		ormTypeMapping.setSpecifiedParentClass(null);
		assertNull(ormTypeMapping.getSpecifiedParentClass());
		assertNull(xmlTypeMapping.getParentClass());
	}

	
	public void testAddSequenceGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		EclipseLinkOrmMappedSuperclass ormMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		assertNull(ormMappedSuperclass.getGeneratorContainer().getSequenceGenerator());
		assertNull(mappedSuperclassResource.getSequenceGenerator());
		
		ormMappedSuperclass.getGeneratorContainer().addSequenceGenerator();
		
		assertNotNull(mappedSuperclassResource.getSequenceGenerator());
		assertNotNull(ormMappedSuperclass.getGeneratorContainer().getSequenceGenerator());
				
		//try adding another sequence generator, should get an IllegalStateException
		try {
			ormMappedSuperclass.getGeneratorContainer().addSequenceGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveSequenceGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		EclipseLinkOrmMappedSuperclass ormMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		assertNull(ormMappedSuperclass.getGeneratorContainer().getSequenceGenerator());
		assertNull(mappedSuperclassResource.getSequenceGenerator());

		ormMappedSuperclass.getGeneratorContainer().addSequenceGenerator();
		assertNotNull(mappedSuperclassResource.getSequenceGenerator());
		assertNotNull(ormMappedSuperclass.getGeneratorContainer().getSequenceGenerator());

		ormMappedSuperclass.getGeneratorContainer().removeSequenceGenerator();
		
		assertNull(ormMappedSuperclass.getGeneratorContainer().getSequenceGenerator());
		assertNull(mappedSuperclassResource.getSequenceGenerator());

		//try removing the sequence generator again, should get an IllegalStateException
		try {
			ormMappedSuperclass.getGeneratorContainer().removeSequenceGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}

	public void testUpdateSequenceGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		EclipseLinkOrmMappedSuperclass ormMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		assertNull(ormMappedSuperclass.getGeneratorContainer().getSequenceGenerator());
		assertNull(mappedSuperclassResource.getSequenceGenerator());
		assertEquals(0, ormMappedSuperclass.getPersistenceUnit().getGeneratorsSize());
		
		mappedSuperclassResource.setSequenceGenerator(OrmFactory.eINSTANCE.createXmlSequenceGenerator());
				
		assertNotNull(ormMappedSuperclass.getGeneratorContainer().getSequenceGenerator());
		assertNotNull(mappedSuperclassResource.getSequenceGenerator());
		assertEquals(1, ormMappedSuperclass.getPersistenceUnit().getGeneratorsSize());
		
		ormMappedSuperclass.getGeneratorContainer().getSequenceGenerator().setName("foo");
		assertEquals(1, ormMappedSuperclass.getPersistenceUnit().getGeneratorsSize());

		mappedSuperclassResource.setSequenceGenerator(null);
		assertNull(ormMappedSuperclass.getGeneratorContainer().getSequenceGenerator());
		assertNull(mappedSuperclassResource.getSequenceGenerator());
		assertEquals(0, ormMappedSuperclass.getPersistenceUnit().getGeneratorsSize());
	}
	
	public void testAddTableGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		EclipseLinkOrmMappedSuperclass ormMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		assertNull(ormMappedSuperclass.getGeneratorContainer().getTableGenerator());
		assertNull(mappedSuperclassResource.getTableGenerator());
		
		ormMappedSuperclass.getGeneratorContainer().addTableGenerator();
		
		assertNotNull(mappedSuperclassResource.getTableGenerator());
		assertNotNull(ormMappedSuperclass.getGeneratorContainer().getTableGenerator());
				
		//try adding another table generator, should get an IllegalStateException
		try {
			ormMappedSuperclass.getGeneratorContainer().addTableGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveTableGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		EclipseLinkOrmMappedSuperclass ormMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		assertNull(ormMappedSuperclass.getGeneratorContainer().getTableGenerator());
		assertNull(mappedSuperclassResource.getTableGenerator());

		ormMappedSuperclass.getGeneratorContainer().addTableGenerator();
		assertNotNull(mappedSuperclassResource.getTableGenerator());
		assertNotNull(ormMappedSuperclass.getGeneratorContainer().getTableGenerator());

		ormMappedSuperclass.getGeneratorContainer().removeTableGenerator();
		
		assertNull(ormMappedSuperclass.getGeneratorContainer().getTableGenerator());
		assertNull(mappedSuperclassResource.getTableGenerator());

		//try removing the table generator again, should get an IllegalStateException
		try {
			ormMappedSuperclass.getGeneratorContainer().removeTableGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testUpdateTableGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, "model.Foo");
		EclipseLinkOrmMappedSuperclass ormMappedSuperclass = (EclipseLinkOrmMappedSuperclass) ormPersistentType.getMapping();
		XmlMappedSuperclass mappedSuperclassResource = (XmlMappedSuperclass) getXmlEntityMappings().getMappedSuperclasses().get(0);
		
		assertNull(ormMappedSuperclass.getGeneratorContainer().getTableGenerator());
		assertNull(mappedSuperclassResource.getTableGenerator());
		assertEquals(0, ormMappedSuperclass.getPersistenceUnit().getGeneratorsSize());
		
		mappedSuperclassResource.setTableGenerator(OrmFactory.eINSTANCE.createXmlTableGenerator());
				
		assertNotNull(ormMappedSuperclass.getGeneratorContainer().getTableGenerator());
		assertNotNull(mappedSuperclassResource.getTableGenerator());
		assertEquals(1, ormMappedSuperclass.getPersistenceUnit().getGeneratorsSize());

		ormMappedSuperclass.getGeneratorContainer().getTableGenerator().setName("foo");
		assertEquals(1, ormMappedSuperclass.getPersistenceUnit().getGeneratorsSize());
		
		mappedSuperclassResource.setTableGenerator(null);
		assertNull(ormMappedSuperclass.getGeneratorContainer().getTableGenerator());
		assertNull(mappedSuperclassResource.getTableGenerator());
		assertEquals(0, ormMappedSuperclass.getPersistenceUnit().getGeneratorsSize());
	}
}
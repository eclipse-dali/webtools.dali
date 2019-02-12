/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context.orm;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.jpa2_1.ParameterMode2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmQueryContainer2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.orm.OrmStoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlStoredProcedureParameter;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.ParameterMode_2_1;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context.Generic2_1ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericOrmStoredProcedureParameter2_1Tests
	extends Generic2_1ContextModelTestCase
{
	public GenericOrmStoredProcedureParameter2_1Tests(String name) {
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

	// ******* name ***********
	
	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmStoredProcedureParameter2_1 ormParameter = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0).addParameter(0);
		
		XmlStoredProcedureParameter parameterResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0).getParameters().get(0);
		
		assertNull(ormParameter.getName());
		assertNull(parameterResource.getName());
				
		//set name in the resource model, verify context model updated
		parameterResource.setName("foo");
		assertEquals("foo", ormParameter.getName());
		assertEquals("foo", parameterResource.getName());
	
		//set name to null in the resource model
		parameterResource.setName(null);
		assertNull(ormParameter.getName());
		assertNull(parameterResource.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmStoredProcedureParameter2_1 ormParameter = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0).addParameter(0);
		
		XmlStoredProcedureParameter parameterResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0).getParameters().get(0);
		
		assertNull(ormParameter.getName());
		assertNull(parameterResource.getName());
				
		//set name in the context model, verify resource model updated
		ormParameter.setName("foo");
		assertEquals("foo", ormParameter.getName());
		assertEquals("foo", parameterResource.getName());
		
		//set new name in the context model, verify resource model updated
		ormParameter.setName("newFoo");
		assertEquals("newFoo", ormParameter.getName());
		assertEquals("newFoo", parameterResource.getName());
	
		//set name to null in the context model
		ormParameter.setName(null);
		assertNull(ormParameter.getName());
		assertNull(parameterResource.getName());
	}
	
	// ********* mode *********
	
	public void testUpdateMode() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmStoredProcedureParameter2_1 ormParameter = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0).addParameter(0);
		
		XmlStoredProcedureParameter parameterResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0).getParameters().get(0);
		
		assertNull(ormParameter.getSpecifiedMode());
		// test default mode
		assertEquals(ParameterMode2_1.IN, ormParameter.getMode());
		assertNull(parameterResource.getMode());
				
		//set mode in the resource model, verify context model updated
		parameterResource.setMode(ParameterMode_2_1.INOUT);
		assertEquals(ParameterMode2_1.INOUT, ormParameter.getMode());
		assertEquals(ParameterMode_2_1.INOUT, parameterResource.getMode());
	
		//set mode to null in the resource model
		parameterResource.setMode(null);
		assertNull(ormParameter.getSpecifiedMode());
		// test default mode
		assertEquals(ParameterMode2_1.IN, ormParameter.getMode());
		assertNull(parameterResource.getMode());
	}
	
	public void testModifyMode() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmStoredProcedureParameter2_1 ormParameter = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0).addParameter(0);
		
		XmlStoredProcedureParameter parameterResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0).getParameters().get(0);
		
		assertNull(ormParameter.getSpecifiedMode());
		// test default mode
		assertEquals(ParameterMode2_1.IN, ormParameter.getMode());
		assertNull(parameterResource.getMode());
				
		//set mode in the context model, verify resource model updated
		ormParameter.setSpecifiedMode(ParameterMode2_1.INOUT);
		assertEquals(ParameterMode2_1.INOUT, ormParameter.getMode());
		assertEquals(ParameterMode_2_1.INOUT, parameterResource.getMode());
		
		//set new mode in the context model, verify resource model updated
		ormParameter.setSpecifiedMode(ParameterMode2_1.OUT);
		assertEquals(ParameterMode2_1.OUT, ormParameter.getMode());
		assertEquals(ParameterMode_2_1.OUT, parameterResource.getMode());

	
		//set mode to null in the context model
		ormParameter.setSpecifiedMode(null);
		assertNull(ormParameter.getSpecifiedMode());
		// test default mode
		assertEquals(ParameterMode2_1.IN, ormParameter.getMode());
		assertNull(parameterResource.getMode());
	}
	
	// *********** type name *********
	
	public void testUpdateTypeName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmStoredProcedureParameter2_1 ormParameter = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0).addParameter(0);
		
		XmlStoredProcedureParameter parameterResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0).getParameters().get(0);
		
		assertNull(ormParameter.getTypeName());
		assertNull(parameterResource.getClassName());
		
		//set type name in the resource model, verify context model updated
		parameterResource.setClassName("MyType");
		assertEquals("MyType", ormParameter.getTypeName());
		assertEquals("MyType", parameterResource.getClassName());
	
		//set type name to null in the resource model
		parameterResource.setClassName(null);
		assertNull(ormParameter.getTypeName());
		assertNull(parameterResource.getClassName());
	}
	
	public void testModifyTypeName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmStoredProcedureParameter2_1 ormParameter = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0).addParameter(0);
		
		XmlStoredProcedureParameter parameterResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0).getParameters().get(0);
		
		assertNull(ormParameter.getTypeName());
		assertNull(parameterResource.getClassName());
				
		//set type name in the resource model, verify context model updated
		parameterResource.setClassName("MyType");
		assertEquals("MyType", ormParameter.getTypeName());
		assertEquals("MyType", parameterResource.getClassName());
	
		//set type name in the resource model, verify context model updated
		parameterResource.setClassName("MyNewType");
		assertEquals("MyNewType", ormParameter.getTypeName());
		assertEquals("MyNewType", parameterResource.getClassName());
	
		//set type name to null in the resource model
		parameterResource.setClassName(null);
		assertNull(ormParameter.getTypeName());
		assertNull(parameterResource.getClassName());
	}
}

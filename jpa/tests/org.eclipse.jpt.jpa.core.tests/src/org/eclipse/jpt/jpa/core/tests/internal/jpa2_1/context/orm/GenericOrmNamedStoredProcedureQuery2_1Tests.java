/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryHint;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.OrmNamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.OrmQueryContainer2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.orm.OrmStoredProcedureParameter2_1;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedStoredProcedureQuery;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context.Generic2_1ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericOrmNamedStoredProcedureQuery2_1Tests
	extends Generic2_1ContextModelTestCase
{
	public GenericOrmNamedStoredProcedureQuery2_1Tests(String name) {
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

	// *********** name *********
	
	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);
		
		assertNull(ormProcedureQuery.getName());
		assertNull(procedureQueryResource.getName());
				
		//set name in the resource model, verify context model updated
		procedureQueryResource.setName("foo");
		assertEquals("foo", ormProcedureQuery.getName());
		assertEquals("foo", procedureQueryResource.getName());
	
		//set name to null in the resource model
		procedureQueryResource.setName(null);
		assertNull(ormProcedureQuery.getName());
		assertNull(procedureQueryResource.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);
		
		assertNull(ormProcedureQuery.getName());
		assertNull(procedureQueryResource.getName());
				
		//set name in the context model, verify resource model updated
		ormProcedureQuery.setName("foo");
		assertEquals("foo", ormProcedureQuery.getName());
		assertEquals("foo", procedureQueryResource.getName());
	
		//set new name in the context model, verify resource model updated
		ormProcedureQuery.setName("newFoo");
		assertEquals("newFoo", ormProcedureQuery.getName());
		assertEquals("newFoo", procedureQueryResource.getName());

		//set name to null in the context model
		ormProcedureQuery.setName(null);
		assertNull(ormProcedureQuery.getName());
		assertNull(procedureQueryResource.getName());
	}
	
	// ********** procedure name ***********
	
	public void testUpdateProcedureName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);
		
		assertNull(ormProcedureQuery.getProcedureName());
		assertNull(procedureQueryResource.getProcedureName());
				
		//set procedure name in the resource model, verify context model updated
		procedureQueryResource.setProcedureName("foo");
		assertEquals("foo", ormProcedureQuery.getProcedureName());
		assertEquals("foo", procedureQueryResource.getProcedureName());
	
		//set procedure name to null in the resource model
		procedureQueryResource.setProcedureName(null);
		assertNull(ormProcedureQuery.getProcedureName());
		assertNull(procedureQueryResource.getProcedureName());
	}
	
	public void testModifyProcedureName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);
		
		assertNull(ormProcedureQuery.getProcedureName());
		assertNull(procedureQueryResource.getName());
				
		//set procedure name in the context model, verify resource model updated
		ormProcedureQuery.setProcedureName("foo");
		assertEquals("foo", ormProcedureQuery.getProcedureName());
		assertEquals("foo", procedureQueryResource.getProcedureName());
	
		//set new procedure name in the context model, verify resource model updated
		ormProcedureQuery.setProcedureName("newFoo");
		assertEquals("newFoo", ormProcedureQuery.getProcedureName());
		assertEquals("newFoo", procedureQueryResource.getProcedureName());

		//set procedure name to null in the context model
		ormProcedureQuery.setProcedureName(null);
		assertNull(ormProcedureQuery.getProcedureName());
		assertNull(procedureQueryResource.getProcedureName());
	}
	
	
	// ************ parameters ************
	
	public void testAddParameter() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);

		OrmStoredProcedureParameter2_1 parameter1 = ormProcedureQuery.addParameter(0);
		parameter1.setName("FOO");
				
		assertEquals("FOO", procedureQueryResource.getParameters().get(0).getName());
		
		OrmStoredProcedureParameter2_1 parameter2 = ormProcedureQuery.addParameter(0);
		parameter2.setName("BAR");
		
		assertEquals("BAR", procedureQueryResource.getParameters().get(0).getName());
		assertEquals("FOO", procedureQueryResource.getParameters().get(1).getName());
		
		OrmStoredProcedureParameter2_1 parameter3 = ormProcedureQuery.addParameter(1);
		parameter3.setName("BAZ");
		
		assertEquals("BAR", procedureQueryResource.getParameters().get(0).getName());
		assertEquals("BAZ", procedureQueryResource.getParameters().get(1).getName());
		assertEquals("FOO", procedureQueryResource.getParameters().get(2).getName());
		
		ListIterator<OrmStoredProcedureParameter2_1> parameters = ormProcedureQuery.getParameters().iterator();
		assertEquals(parameter2, parameters.next());
		assertEquals(parameter3, parameters.next());
		assertEquals(parameter1, parameters.next());
		
		parameters = ormProcedureQuery.getParameters().iterator();
		assertEquals("BAR", parameters.next().getName());
		assertEquals("BAZ", parameters.next().getName());
		assertEquals("FOO", parameters.next().getName());
	}
	
	public void testRemoveParameter() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);

		ormProcedureQuery.addParameter(0).setName("FOO");
		ormProcedureQuery.addParameter(1).setName("BAR");
		ormProcedureQuery.addParameter(2).setName("BAZ");
		
		assertEquals(3, procedureQueryResource.getParameters().size());
		
		ormProcedureQuery.removeParameter(0);
		assertEquals(2, procedureQueryResource.getParameters().size());
		assertEquals("BAR", procedureQueryResource.getParameters().get(0).getName());
		assertEquals("BAZ", procedureQueryResource.getParameters().get(1).getName());

		ormProcedureQuery.removeParameter(0);
		assertEquals(1, procedureQueryResource.getParameters().size());
		assertEquals("BAZ", procedureQueryResource.getParameters().get(0).getName());
		
		ormProcedureQuery.removeParameter(0);
		assertEquals(0, procedureQueryResource.getParameters().size());
	}
	
	public void testMoveParameter() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);

		ormProcedureQuery.addParameter(0).setName("FOO");
		ormProcedureQuery.addParameter(1).setName("BAR");
		ormProcedureQuery.addParameter(2).setName("BAZ");
		
		assertEquals(3, procedureQueryResource.getParameters().size());
		
		ormProcedureQuery.moveParameter(2, 0);
		ListIterator<OrmStoredProcedureParameter2_1> parameters = ormProcedureQuery.getParameters().iterator();
		assertEquals("BAR", parameters.next().getName());
		assertEquals("BAZ", parameters.next().getName());
		assertEquals("FOO", parameters.next().getName());

		assertEquals("BAR", procedureQueryResource.getParameters().get(0).getName());
		assertEquals("BAZ", procedureQueryResource.getParameters().get(1).getName());
		assertEquals("FOO", procedureQueryResource.getParameters().get(2).getName());


		ormProcedureQuery.moveParameter(0, 1);
		parameters = ormProcedureQuery.getParameters().iterator();
		assertEquals("BAZ", parameters.next().getName());
		assertEquals("BAR", parameters.next().getName());
		assertEquals("FOO", parameters.next().getName());

		assertEquals("BAZ", procedureQueryResource.getParameters().get(0).getName());
		assertEquals("BAR", procedureQueryResource.getParameters().get(1).getName());
		assertEquals("FOO", procedureQueryResource.getParameters().get(2).getName());
	}
	
	public void testUpdateParameters() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);

		procedureQueryResource.getParameters().add(OrmFactory.eINSTANCE.createXmlStoredProcedureParameter());
		procedureQueryResource.getParameters().add(OrmFactory.eINSTANCE.createXmlStoredProcedureParameter());
		procedureQueryResource.getParameters().add(OrmFactory.eINSTANCE.createXmlStoredProcedureParameter());
		
		procedureQueryResource.getParameters().get(0).setName("FOO");
		procedureQueryResource.getParameters().get(1).setName("BAR");
		procedureQueryResource.getParameters().get(2).setName("BAZ");

		ListIterator<OrmStoredProcedureParameter2_1> parameters = ormProcedureQuery.getParameters().iterator();
		assertEquals("FOO", parameters.next().getName());
		assertEquals("BAR", parameters.next().getName());
		assertEquals("BAZ", parameters.next().getName());
		assertFalse(parameters.hasNext());
		
		procedureQueryResource.getParameters().move(2, 0);
		parameters = ormProcedureQuery.getParameters().iterator();
		assertEquals("BAR", parameters.next().getName());
		assertEquals("BAZ", parameters.next().getName());
		assertEquals("FOO", parameters.next().getName());
		assertFalse(parameters.hasNext());

		procedureQueryResource.getParameters().move(0, 1);
		parameters = ormProcedureQuery.getParameters().iterator();
		assertEquals("BAZ", parameters.next().getName());
		assertEquals("BAR", parameters.next().getName());
		assertEquals("FOO", parameters.next().getName());
		assertFalse(parameters.hasNext());

		procedureQueryResource.getParameters().remove(1);
		parameters = ormProcedureQuery.getParameters().iterator();
		assertEquals("BAZ", parameters.next().getName());
		assertEquals("FOO", parameters.next().getName());
		assertFalse(parameters.hasNext());

		procedureQueryResource.getParameters().remove(1);
		parameters = ormProcedureQuery.getParameters().iterator();
		assertEquals("BAZ", parameters.next().getName());
		assertFalse(parameters.hasNext());
		
		procedureQueryResource.getParameters().remove(0);
		assertFalse(ormProcedureQuery.getParameters().iterator().hasNext());
	}
	
	
	// ************ result classes ********
	
	public void testAddResultClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);

		ormProcedureQuery.addResultClass("Employee");
		assertEquals("Employee", procedureQueryResource.getResultClasses().get(0));
		
		ormProcedureQuery.addResultClass("Address");
		assertEquals("Employee", procedureQueryResource.getResultClasses().get(0));
		assertEquals("Address", procedureQueryResource.getResultClasses().get(1));
		
		ormProcedureQuery.addResultClass(1, "Project");
		
		assertEquals("Employee", procedureQueryResource.getResultClasses().get(0));
		assertEquals("Project", procedureQueryResource.getResultClasses().get(1));
		assertEquals("Address", procedureQueryResource.getResultClasses().get(2));
		
		ListIterator<String> resultSetClasses = ormProcedureQuery.getResultClasses().iterator();
		assertEquals("Employee", resultSetClasses.next());
		assertEquals("Project", resultSetClasses.next());
		assertEquals("Address", resultSetClasses.next());
	}
	
	public void testRemoveResultClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);

		ormProcedureQuery.addResultClass("Employee");
		ormProcedureQuery.addResultClass("Address");
		ormProcedureQuery.addResultClass("Project");
		
		assertEquals(3, procedureQueryResource.getResultClasses().size());
		
		ormProcedureQuery.removeResultClass(0);
		assertEquals(2, procedureQueryResource.getResultClasses().size());
		assertEquals("Address", procedureQueryResource.getResultClasses().get(0));
		assertEquals("Project", procedureQueryResource.getResultClasses().get(1));

		ormProcedureQuery.removeResultClass("Project");
		assertEquals(1, procedureQueryResource.getResultClasses().size());
		assertEquals("Address", procedureQueryResource.getResultClasses().get(0));
		
		ormProcedureQuery.removeResultClass(0);
		assertEquals(0, procedureQueryResource.getResultClasses().size());
	}
	
	public void testMoveResultClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);

		ormProcedureQuery.addResultClass("Employee");
		ormProcedureQuery.addResultClass("Address");
		ormProcedureQuery.addResultClass("Project");
		
		assertEquals(3, procedureQueryResource.getResultClasses().size());
		
		ormProcedureQuery.moveResultClass(2, 0);
		ListIterator<String> resultClasses = ormProcedureQuery.getResultClasses().iterator();
		assertEquals("Address", resultClasses.next());
		assertEquals("Project", resultClasses.next());
		assertEquals("Employee", resultClasses.next());

		assertEquals("Address", procedureQueryResource.getResultClasses().get(0));
		assertEquals("Project", procedureQueryResource.getResultClasses().get(1));
		assertEquals("Employee", procedureQueryResource.getResultClasses().get(2));

		ormProcedureQuery.moveResultClass(0, 1);
		resultClasses = ormProcedureQuery.getResultClasses().iterator();
		assertEquals("Project", resultClasses.next());
		assertEquals("Address", resultClasses.next());
		assertEquals("Employee", resultClasses.next());

		assertEquals("Project", procedureQueryResource.getResultClasses().get(0));
		assertEquals("Address", procedureQueryResource.getResultClasses().get(1));
		assertEquals("Employee", procedureQueryResource.getResultClasses().get(2));
	}
	
	public void testUpdateResultClasses() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);

		procedureQueryResource.getResultClasses().add("Employee");
		procedureQueryResource.getResultClasses().add("Address");
		procedureQueryResource.getResultClasses().add("Project");

		ListIterator<String> resultClasses = ormProcedureQuery.getResultClasses().iterator();
		assertEquals("Employee", resultClasses.next());
		assertEquals("Address", resultClasses.next());
		assertEquals("Project", resultClasses.next());
		assertFalse(resultClasses.hasNext());
		
		procedureQueryResource.getResultClasses().move(2, 0);
		resultClasses = ormProcedureQuery.getResultClasses().iterator();
		assertEquals("Address", resultClasses.next());
		assertEquals("Project", resultClasses.next());
		assertEquals("Employee", resultClasses.next());
		assertFalse(resultClasses.hasNext());

		procedureQueryResource.getResultClasses().move(0, 1);
		resultClasses = ormProcedureQuery.getResultClasses().iterator();
		assertEquals("Project", resultClasses.next());
		assertEquals("Address", resultClasses.next());
		assertEquals("Employee", resultClasses.next());
		assertFalse(resultClasses.hasNext());

		procedureQueryResource.getResultClasses().remove(1);
		resultClasses = ormProcedureQuery.getResultClasses().iterator();
		assertEquals("Project", resultClasses.next());
		assertEquals("Employee", resultClasses.next());
		assertFalse(resultClasses.hasNext());

		procedureQueryResource.getResultClasses().remove(1);
		resultClasses = ormProcedureQuery.getResultClasses().iterator();
		assertEquals("Project", resultClasses.next());
		assertFalse(resultClasses.hasNext());
		
		procedureQueryResource.getResultClasses().remove(0);
		assertFalse(ormProcedureQuery.getResultClasses().iterator().hasNext());
	}


	// ************ result set mappings ********
	
	public void testAddResultSetMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);

		ormProcedureQuery.addResultSetMapping("Employee");
		assertEquals("Employee", procedureQueryResource.getResultSetMappings().get(0));
		
		ormProcedureQuery.addResultSetMapping("Address");
		assertEquals("Employee", procedureQueryResource.getResultSetMappings().get(0));
		assertEquals("Address", procedureQueryResource.getResultSetMappings().get(1));
		
		ormProcedureQuery.addResultSetMapping(1, "Project");
		
		assertEquals("Employee", procedureQueryResource.getResultSetMappings().get(0));
		assertEquals("Project", procedureQueryResource.getResultSetMappings().get(1));
		assertEquals("Address", procedureQueryResource.getResultSetMappings().get(2));
		
		ListIterator<String> resultSetMappings = ormProcedureQuery.getResultSetMappings().iterator();
		assertEquals("Employee", resultSetMappings.next());
		assertEquals("Project", resultSetMappings.next());
		assertEquals("Address", resultSetMappings.next());
	}
	
	public void testRemoveResultSetMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);

		ormProcedureQuery.addResultSetMapping("Employee");
		ormProcedureQuery.addResultSetMapping("Address");
		ormProcedureQuery.addResultSetMapping("Project");
		
		assertEquals(3, procedureQueryResource.getResultSetMappings().size());
		
		ormProcedureQuery.removeResultSetMapping(0);
		assertEquals(2, procedureQueryResource.getResultSetMappings().size());
		assertEquals("Address", procedureQueryResource.getResultSetMappings().get(0));
		assertEquals("Project", procedureQueryResource.getResultSetMappings().get(1));

		ormProcedureQuery.removeResultSetMapping("Project");
		assertEquals(1, procedureQueryResource.getResultSetMappings().size());
		assertEquals("Address", procedureQueryResource.getResultSetMappings().get(0));
		
		ormProcedureQuery.removeResultSetMapping(0);
		assertEquals(0, procedureQueryResource.getResultSetMappings().size());
	}
	
	public void testMoveResultSetMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);

		ormProcedureQuery.addResultSetMapping("Employee");
		ormProcedureQuery.addResultSetMapping("Address");
		ormProcedureQuery.addResultSetMapping("Project");
		
		assertEquals(3, procedureQueryResource.getResultSetMappings().size());
		
		ormProcedureQuery.moveResultSetMapping(2, 0);
		ListIterator<String> resultSetMappings = ormProcedureQuery.getResultSetMappings().iterator();
		assertEquals("Address", resultSetMappings.next());
		assertEquals("Project", resultSetMappings.next());
		assertEquals("Employee", resultSetMappings.next());

		assertEquals("Address", procedureQueryResource.getResultSetMappings().get(0));
		assertEquals("Project", procedureQueryResource.getResultSetMappings().get(1));
		assertEquals("Employee", procedureQueryResource.getResultSetMappings().get(2));

		ormProcedureQuery.moveResultSetMapping(0, 1);
		resultSetMappings = ormProcedureQuery.getResultSetMappings().iterator();
		assertEquals("Project", resultSetMappings.next());
		assertEquals("Address", resultSetMappings.next());
		assertEquals("Employee", resultSetMappings.next());

		assertEquals("Project", procedureQueryResource.getResultSetMappings().get(0));
		assertEquals("Address", procedureQueryResource.getResultSetMappings().get(1));
		assertEquals("Employee", procedureQueryResource.getResultSetMappings().get(2));
	}
	
	public void testUpdateResultSetMappings() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);

		procedureQueryResource.getResultSetMappings().add("Employee");
		procedureQueryResource.getResultSetMappings().add("Address");
		procedureQueryResource.getResultSetMappings().add("Project");

		ListIterator<String> resultSetMappings = ormProcedureQuery.getResultSetMappings().iterator();
		assertEquals("Employee", resultSetMappings.next());
		assertEquals("Address", resultSetMappings.next());
		assertEquals("Project", resultSetMappings.next());
		assertFalse(resultSetMappings.hasNext());
		
		procedureQueryResource.getResultSetMappings().move(2, 0);
		resultSetMappings = ormProcedureQuery.getResultSetMappings().iterator();
		assertEquals("Address", resultSetMappings.next());
		assertEquals("Project", resultSetMappings.next());
		assertEquals("Employee", resultSetMappings.next());
		assertFalse(resultSetMappings.hasNext());

		procedureQueryResource.getResultSetMappings().move(0, 1);
		resultSetMappings = ormProcedureQuery.getResultSetMappings().iterator();
		assertEquals("Project", resultSetMappings.next());
		assertEquals("Address", resultSetMappings.next());
		assertEquals("Employee", resultSetMappings.next());
		assertFalse(resultSetMappings.hasNext());

		procedureQueryResource.getResultSetMappings().remove(1);
		resultSetMappings = ormProcedureQuery.getResultSetMappings().iterator();
		assertEquals("Project", resultSetMappings.next());
		assertEquals("Employee", resultSetMappings.next());
		assertFalse(resultSetMappings.hasNext());

		procedureQueryResource.getResultSetMappings().remove(1);
		resultSetMappings= ormProcedureQuery.getResultSetMappings().iterator();
		assertEquals("Project", resultSetMappings.next());
		assertFalse(resultSetMappings.hasNext());
		
		procedureQueryResource.getResultSetMappings().remove(0);
		assertFalse(ormProcedureQuery.getResultSetMappings().iterator().hasNext());
	}
	
	
	// ************ hints **********
	
	public void testAddHint() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);

		OrmQueryHint queryHint = ormProcedureQuery.addHint(0);
		queryHint.setName("FOO");
				
		assertEquals("FOO", procedureQueryResource.getHints().get(0).getName());
		
		OrmQueryHint queryHint2 = ormProcedureQuery.addHint(0);
		queryHint2.setName("BAR");
		
		assertEquals("BAR", procedureQueryResource.getHints().get(0).getName());
		assertEquals("FOO", procedureQueryResource.getHints().get(1).getName());
		
		OrmQueryHint queryHint3 = ormProcedureQuery.addHint(1);
		queryHint3.setName("BAZ");
		
		assertEquals("BAR", procedureQueryResource.getHints().get(0).getName());
		assertEquals("BAZ", procedureQueryResource.getHints().get(1).getName());
		assertEquals("FOO", procedureQueryResource.getHints().get(2).getName());
		
		ListIterator<OrmQueryHint> queryHints = ormProcedureQuery.getHints().iterator();
		assertEquals(queryHint2, queryHints.next());
		assertEquals(queryHint3, queryHints.next());
		assertEquals(queryHint, queryHints.next());
		
		queryHints = ormProcedureQuery.getHints().iterator();
		assertEquals("BAR", queryHints.next().getName());
		assertEquals("BAZ", queryHints.next().getName());
		assertEquals("FOO", queryHints.next().getName());
	}
	
	public void testRemoveHint() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);

		ormProcedureQuery.addHint(0).setName("FOO");
		ormProcedureQuery.addHint(1).setName("BAR");
		ormProcedureQuery.addHint(2).setName("BAZ");
		
		assertEquals(3, procedureQueryResource.getHints().size());
		
		ormProcedureQuery.removeHint(0);
		assertEquals(2, procedureQueryResource.getHints().size());
		assertEquals("BAR", procedureQueryResource.getHints().get(0).getName());
		assertEquals("BAZ", procedureQueryResource.getHints().get(1).getName());

		ormProcedureQuery.removeHint(0);
		assertEquals(1, procedureQueryResource.getHints().size());
		assertEquals("BAZ", procedureQueryResource.getHints().get(0).getName());
		
		ormProcedureQuery.removeHint(0);
		assertEquals(0, procedureQueryResource.getHints().size());
	}
	
	public void testMoveHint() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);

		ormProcedureQuery.addHint(0).setName("FOO");
		ormProcedureQuery.addHint(1).setName("BAR");
		ormProcedureQuery.addHint(2).setName("BAZ");
		
		assertEquals(3, procedureQueryResource.getHints().size());
		
		
		ormProcedureQuery.moveHint(2, 0);
		ListIterator<OrmQueryHint> hints = ormProcedureQuery.getHints().iterator();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());

		assertEquals("BAR", procedureQueryResource.getHints().get(0).getName());
		assertEquals("BAZ", procedureQueryResource.getHints().get(1).getName());
		assertEquals("FOO", procedureQueryResource.getHints().get(2).getName());


		ormProcedureQuery.moveHint(0, 1);
		hints = ormProcedureQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("FOO", hints.next().getName());

		assertEquals("BAZ", procedureQueryResource.getHints().get(0).getName());
		assertEquals("BAR", procedureQueryResource.getHints().get(1).getName());
		assertEquals("FOO", procedureQueryResource.getHints().get(2).getName());
	}
	
	public void testUpdateHints() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmNamedStoredProcedureQuery2_1 ormProcedureQuery = ((OrmQueryContainer2_1) ormEntity.getQueryContainer()).addNamedStoredProcedureQuery(0);
		
		XmlNamedStoredProcedureQuery procedureQueryResource = getXmlEntityMappings().getEntities().get(0).getNamedStoredProcedureQueries().get(0);

		procedureQueryResource.getHints().add(OrmFactory.eINSTANCE.createXmlQueryHint());
		procedureQueryResource.getHints().add(OrmFactory.eINSTANCE.createXmlQueryHint());
		procedureQueryResource.getHints().add(OrmFactory.eINSTANCE.createXmlQueryHint());
		
		procedureQueryResource.getHints().get(0).setName("FOO");
		procedureQueryResource.getHints().get(1).setName("BAR");
		procedureQueryResource.getHints().get(2).setName("BAZ");

		ListIterator<OrmQueryHint> hints = ormProcedureQuery.getHints().iterator();
		assertEquals("FOO", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertFalse(hints.hasNext());
		
		procedureQueryResource.getHints().move(2, 0);
		hints = ormProcedureQuery.getHints().iterator();
		assertEquals("BAR", hints.next().getName());
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());

		procedureQueryResource.getHints().move(0, 1);
		hints = ormProcedureQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("BAR", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());

		procedureQueryResource.getHints().remove(1);
		hints = ormProcedureQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertEquals("FOO", hints.next().getName());
		assertFalse(hints.hasNext());

		procedureQueryResource.getHints().remove(1);
		hints = ormProcedureQuery.getHints().iterator();
		assertEquals("BAZ", hints.next().getName());
		assertFalse(hints.hasNext());
		
		procedureQueryResource.getHints().remove(0);
		assertFalse(ormProcedureQuery.getHints().iterator().hasNext());
	}

}

/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkAccessType;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethodsHolder;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlId;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLink2_1ContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLink2_1OrmPersistentTypeTests
	extends EclipseLink2_1ContextModelTestCase
{
	public EclipseLink2_1OrmPersistentTypeTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, JPA.COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}

	public void testUpdateSpecifiedGetMethod() throws Exception {
		createTestEntity();
		EclipseLinkOrmPersistentType ormPersistentType = (EclipseLinkOrmPersistentType) getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlAccessMethodsHolder xmlAccessMethodsHolder = (XmlAccessMethodsHolder) ormPersistentType.getMapping().getXmlTypeMapping();
		assertNull(ormPersistentType.getSpecifiedGetMethod());
		assertNull(xmlAccessMethodsHolder.getAccessMethods());

		//set getMethod in the resource model, verify context model updated
		xmlAccessMethodsHolder.setAccessMethods(EclipseLinkOrmFactory.eINSTANCE.createXmlAccessMethods());
		xmlAccessMethodsHolder.getAccessMethods().setGetMethod("getFoo");
		assertEquals("getFoo", ormPersistentType.getSpecifiedGetMethod());
		assertEquals("getFoo", xmlAccessMethodsHolder.getAccessMethods().getGetMethod());
		
		//set getMethod to null in the resource model
		xmlAccessMethodsHolder.getAccessMethods().setGetMethod(null);
		assertNull(ormPersistentType.getSpecifiedGetMethod());
		assertNull(xmlAccessMethodsHolder.getAccessMethods().getGetMethod());
	}

	public void testModifySpecifiedGetMethod() throws Exception {		
		createTestEntity();
		EclipseLinkOrmPersistentType ormPersistentType = (EclipseLinkOrmPersistentType) getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlAccessMethodsHolder xmlAccessMethodsHolder = (XmlAccessMethodsHolder) ormPersistentType.getMapping().getXmlTypeMapping();
		assertNull(ormPersistentType.getSpecifiedGetMethod());
		assertNull(xmlAccessMethodsHolder.getAccessMethods());
		
		//set getMethod in the context model, verify resource model modified
		ormPersistentType.setSpecifiedGetMethod("getMe");
		assertEquals("getMe", ormPersistentType.getSpecifiedGetMethod());
		assertEquals("getMe", xmlAccessMethodsHolder.getAccessMethods().getGetMethod());
		
		//set getMethod to null in the context model
		ormPersistentType.setSpecifiedGetMethod(null);
		assertNull(ormPersistentType.getSpecifiedGetMethod());
		assertNull(xmlAccessMethodsHolder.getAccessMethods());
	}
	
	public void testModifySpecifiedGetMethod2() throws Exception {
		createTestEntity();
		EclipseLinkOrmPersistentType ormPersistentType = (EclipseLinkOrmPersistentType) getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlAccessMethodsHolder xmlAccessMethodsHolder = (XmlAccessMethodsHolder) ormPersistentType.getMapping().getXmlTypeMapping();
		assertNull(ormPersistentType.getSpecifiedGetMethod());
		assertNull(xmlAccessMethodsHolder.getAccessMethods());
		
		//set getMethod in the context model, verify resource model modified
		ormPersistentType.setSpecifiedGetMethod("getMe");
		assertEquals("getMe", ormPersistentType.getGetMethod());
		assertEquals("getMe", xmlAccessMethodsHolder.getAccessMethods().getGetMethod());
		
		//set another element on the access-methods element so it doesn't get removed
		xmlAccessMethodsHolder.getAccessMethods().setSetMethod("setMe");
		//set getMethod to null in the context model
		ormPersistentType.setSpecifiedGetMethod(null);
		assertNull(ormPersistentType.getSpecifiedGetMethod());
		assertNull(xmlAccessMethodsHolder.getAccessMethods().getGetMethod());
	}

	//TODO default from super class, default from mapped super class, what about a default on an embeddable?
	//default from persistence-unit-defaults, default from entity-mappings, default from java entity
	public void testGetDefaultGetMethod() throws Exception {
		createTestEntity();
		EclipseLinkOrmPersistentType ormPersistentType = (EclipseLinkOrmPersistentType) getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlAccessMethodsHolder xmlAccessMethodsHolder = (XmlAccessMethodsHolder) ormPersistentType.getMapping().getXmlTypeMapping();
		assertNull(ormPersistentType.getSpecifiedGetMethod());
		assertNull(ormPersistentType.getGetMethod());
		assertNull(ormPersistentType.getDefaultGetMethod());
		assertNull(xmlAccessMethodsHolder.getAccessMethods());

		ormPersistentType.setSpecifiedAccess(EclipseLinkAccessType.VIRTUAL);
		assertNull(ormPersistentType.getSpecifiedGetMethod());
		assertEquals("get", ormPersistentType.getGetMethod());
		assertEquals("get", ormPersistentType.getDefaultGetMethod());
		assertNull(xmlAccessMethodsHolder.getAccessMethods());

		getPersistenceUnitDefaults().setSpecifiedGetMethod("getFoo");
		assertEquals("getFoo", ormPersistentType.getDefaultGetMethod());
		assertEquals("getFoo", ormPersistentType.getGetMethod());

		ormPersistentType.setSpecifiedGetMethod("getBar");
		assertEquals("getFoo", ormPersistentType.getDefaultGetMethod());
		assertEquals("getBar", ormPersistentType.getGetMethod());
		
		getPersistenceUnitDefaults().setSpecifiedGetMethod(null);
		assertEquals("get", ormPersistentType.getDefaultGetMethod());
		assertEquals("getBar", ormPersistentType.getGetMethod());

		ormPersistentType.setSpecifiedAccess(null);
		assertNull(ormPersistentType.getDefaultGetMethod());
	}

	public void testUpdateSpecifiedSetMethod() throws Exception {
		createTestEntity();
		EclipseLinkOrmPersistentType ormPersistentType = (EclipseLinkOrmPersistentType) getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlAccessMethodsHolder xmlAccessMethodsHolder = (XmlAccessMethodsHolder) ormPersistentType.getMapping().getXmlTypeMapping();
		assertNull(ormPersistentType.getSpecifiedSetMethod());
		assertNull(xmlAccessMethodsHolder.getAccessMethods());

		//set setMethod in the resource model, verify context model updated
		xmlAccessMethodsHolder.setAccessMethods(EclipseLinkOrmFactory.eINSTANCE.createXmlAccessMethods());
		xmlAccessMethodsHolder.getAccessMethods().setSetMethod("setFoo");
		assertEquals("setFoo", ormPersistentType.getSpecifiedSetMethod());
		assertEquals("setFoo", xmlAccessMethodsHolder.getAccessMethods().getSetMethod());
		
		//set setMethod to null in the resource model
		xmlAccessMethodsHolder.getAccessMethods().setSetMethod(null);
		assertNull(ormPersistentType.getSpecifiedSetMethod());
		assertNull(xmlAccessMethodsHolder.getAccessMethods().getSetMethod());
	}

	public void testModifySpecifiedSetMethod() throws Exception {		
		createTestEntity();
		EclipseLinkOrmPersistentType ormPersistentType = (EclipseLinkOrmPersistentType) getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlAccessMethodsHolder xmlAccessMethodsHolder = (XmlAccessMethodsHolder) ormPersistentType.getMapping().getXmlTypeMapping();
		assertNull(ormPersistentType.getSpecifiedSetMethod());
		assertNull(xmlAccessMethodsHolder.getAccessMethods());
		
		//set setMethod in the context model, verify resource model modified
		ormPersistentType.setSpecifiedSetMethod("setMe");
		assertEquals("setMe", ormPersistentType.getSpecifiedSetMethod());
		assertEquals("setMe", xmlAccessMethodsHolder.getAccessMethods().getSetMethod());
		
		//set setMethod to null in the context model
		ormPersistentType.setSpecifiedSetMethod(null);
		assertNull(ormPersistentType.getSpecifiedSetMethod());
		assertNull(xmlAccessMethodsHolder.getAccessMethods());
	}
	
	public void testModifySpecifiedSetMethod2() throws Exception {
		createTestEntity();
		EclipseLinkOrmPersistentType ormPersistentType = (EclipseLinkOrmPersistentType) getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlAccessMethodsHolder xmlAccessMethodsHolder = (XmlAccessMethodsHolder) ormPersistentType.getMapping().getXmlTypeMapping();
		assertNull(ormPersistentType.getSpecifiedSetMethod());
		assertNull(xmlAccessMethodsHolder.getAccessMethods());
		
		//set setMethod in the context model, verify resource model modified
		ormPersistentType.setSpecifiedSetMethod("setMe");
		assertEquals("setMe", ormPersistentType.getSetMethod());
		assertEquals("setMe", xmlAccessMethodsHolder.getAccessMethods().getSetMethod());
		
		//set another element on the access-methods element so it doesn't get removed
		xmlAccessMethodsHolder.getAccessMethods().setGetMethod("getMe");
		//set setMethod to null in the context model
		ormPersistentType.setSpecifiedSetMethod(null);
		assertNull(ormPersistentType.getSpecifiedSetMethod());
		assertNull(xmlAccessMethodsHolder.getAccessMethods().getSetMethod());
	}

	//TODO default from super class, default from mapped super class, what about a default on an embeddable?
	//default from persistence-unit-defaults, default from entity-mappings, default from java entity
	public void testGetDefaultSetMethod() throws Exception {
		createTestEntity();
		EclipseLinkOrmPersistentType ormPersistentType = (EclipseLinkOrmPersistentType) getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlAccessMethodsHolder xmlAccessMethodsHolder = (XmlAccessMethodsHolder) ormPersistentType.getMapping().getXmlTypeMapping();
		assertNull(ormPersistentType.getSpecifiedSetMethod());
		assertNull(ormPersistentType.getSetMethod());
		assertNull(ormPersistentType.getDefaultSetMethod());
		assertNull(xmlAccessMethodsHolder.getAccessMethods());

		ormPersistentType.setSpecifiedAccess(EclipseLinkAccessType.VIRTUAL);
		assertNull(ormPersistentType.getSpecifiedSetMethod());
		assertEquals("set", ormPersistentType.getSetMethod());
		assertEquals("set", ormPersistentType.getDefaultSetMethod());
		assertNull(xmlAccessMethodsHolder.getAccessMethods());

		getPersistenceUnitDefaults().setSpecifiedSetMethod("setFoo");
		assertEquals("setFoo", ormPersistentType.getDefaultSetMethod());
		assertEquals("setFoo", ormPersistentType.getSetMethod());

		ormPersistentType.setSpecifiedSetMethod("setBar");
		assertEquals("setFoo", ormPersistentType.getDefaultSetMethod());
		assertEquals("setBar", ormPersistentType.getSetMethod());
		
		getPersistenceUnitDefaults().setSpecifiedSetMethod(null);
		assertEquals("set", ormPersistentType.getDefaultSetMethod());
		assertEquals("setBar", ormPersistentType.getSetMethod());

		ormPersistentType.setSpecifiedAccess(null);
		assertNull(ormPersistentType.getDefaultSetMethod());
	}
	
	protected EclipseLinkPersistenceUnitDefaults getPersistenceUnitDefaults() {
		return(EclipseLinkPersistenceUnitDefaults) getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults();
	}

	public void testAddVirtualAttribute() throws Exception {
		EclipseLinkOrmPersistentType persistentType = (EclipseLinkOrmPersistentType) getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Employee");
		XmlEntityMappings xmlEntityMappings = getXmlEntityMappings();
		XmlEntity xmlEntity = (XmlEntity) xmlEntityMappings.getEntities().get(0);
		
		// test virtual attribute mapping that only needs to set attribute type
		OrmPersistentAttribute persistentAttribute = persistentType.addVirtualAttribute("id", MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "int", null);

		assertEquals("id", persistentAttribute.getName());
		assertEquals(EclipseLinkAccessType.VIRTUAL, persistentAttribute.getSpecifiedAccess());
		OrmIdMapping idMapping = (OrmIdMapping) persistentAttribute.getMapping();
		assertEquals("int", idMapping.getAttributeType());
		
		XmlId xmlId = (XmlId) xmlEntity.getAttributes().getIds().get(0);
		assertEquals("id", xmlId.getName());
		assertEquals("int", xmlId.getAttributeType());
		assertEquals("VIRTUAL", xmlId.getAccess());
		
		// test virtual attribute mapping that only needs to set target type
		persistentAttribute = persistentType.addVirtualAttribute("many2one", MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, null, "model.Project");

		assertEquals("many2one", persistentAttribute.getName());
		assertEquals(EclipseLinkAccessType.VIRTUAL, persistentAttribute.getSpecifiedAccess());
		OrmManyToOneMapping many2oneMapping = (OrmManyToOneMapping) persistentAttribute.getMapping();
		assertNull(many2oneMapping.getAttributeType());
		assertEquals("model.Project", many2oneMapping.getTargetEntity());
		
		XmlManyToOne xmlManyToOne = (XmlManyToOne) xmlEntity.getAttributes().getManyToOnes().get(0);
		assertEquals("many2one", xmlManyToOne.getName());
		assertNull(xmlManyToOne.getAttributeType());
		assertEquals("model.Project", xmlManyToOne.getTargetEntity());
		assertEquals("VIRTUAL", xmlManyToOne.getAccess());

		// test virtual attribute mapping that needs to set both attribute type and target type
		persistentAttribute = persistentType.addVirtualAttribute("many2many", MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "java.util.List", "model.Project");

		assertEquals("many2many", persistentAttribute.getName());
		assertEquals(EclipseLinkAccessType.VIRTUAL, persistentAttribute.getSpecifiedAccess());
		OrmManyToManyMapping many2manyMapping = (OrmManyToManyMapping) persistentAttribute.getMapping();
		assertEquals("java.util.List", many2manyMapping.getAttributeType());
		assertEquals("model.Project", many2manyMapping.getTargetEntity());
		
		XmlManyToMany xmlManyToMany = (XmlManyToMany) xmlEntity.getAttributes().getManyToManys().get(0);
		assertEquals("many2many", xmlManyToMany.getName());
		assertEquals("java.util.List", xmlManyToMany.getAttributeType());
		assertEquals("model.Project", xmlManyToMany.getTargetEntity());
		assertEquals("VIRTUAL", xmlManyToMany.getAccess());

		//if the entity access is set to VIRTUAL, test that the added virtual attribute mapping has no specified access
		persistentType.setSpecifiedAccess(EclipseLinkAccessType.VIRTUAL);
		persistentAttribute = persistentType.addVirtualAttribute("name", MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "String", null);

		assertEquals("name", persistentAttribute.getName());
		assertNull(persistentAttribute.getSpecifiedAccess());
		assertEquals(EclipseLinkAccessType.VIRTUAL, persistentAttribute.getDefaultAccess());
		OrmBasicMapping basicMapping = (OrmBasicMapping) persistentAttribute.getMapping();
		assertEquals("String", basicMapping.getAttributeType());
		
		XmlBasic xmlBasic = (XmlBasic) xmlEntity.getAttributes().getBasics().get(0);
		assertEquals("name", xmlBasic.getName());
		assertEquals("String", xmlBasic.getAttributeType());
		assertNull(xmlBasic.getAccess());
	}
}
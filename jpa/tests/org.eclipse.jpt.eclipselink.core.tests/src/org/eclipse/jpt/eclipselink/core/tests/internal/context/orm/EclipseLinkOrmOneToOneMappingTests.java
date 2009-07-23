/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne;

@SuppressWarnings("nls")
public class EclipseLinkOrmOneToOneMappingTests
	extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkOrmOneToOneMappingTests(String name) {
		super(name);
	}
	
	private void createTestDepartment() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ").append(JPA.ENTITY).append(";");
					sb.append(CR);
					sb.append("import ").append(JPA.ID).append(";");
					sb.append(CR);
					sb.append(CR);
					sb.append("import ").append(JPA.ONE_TO_ONE).append(";");
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("Department").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int id;").append(CR);
				sb.append(CR);
				sb.append("    @OneToOne").append(CR);
				sb.append("    private Employee employee;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Department.java", sourceWriter);
	}

	private void createTestEmployee() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ").append(JPA.ENTITY).append(";");
					sb.append(CR);
					sb.append("import ").append(JPA.ID).append(";");
					sb.append(CR);
			sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("Employee").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int empId;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Employee.java", sourceWriter);
	}	
	
	public void testUpdatePrivateOwned() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		EclipseLinkOneToOneMapping contextOneToOne = 
			(EclipseLinkOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlOneToOne resourceOneToOne = 
			(XmlOneToOne) resourceEntity.getAttributes().getOneToOnes().get(0);
		
		// check defaults
		
		assertFalse(resourceOneToOne.isPrivateOwned());
		assertFalse(contextOneToOne.getPrivateOwned().isPrivateOwned());
		
		// set xml private owned to true, check context
		
		resourceOneToOne.setPrivateOwned(true);
		
		assertTrue(resourceOneToOne.isPrivateOwned());
		assertTrue(contextOneToOne.getPrivateOwned().isPrivateOwned());
		
		// set xml private owned back to false, check context
		
		resourceOneToOne.setPrivateOwned(false);
		
		assertFalse(resourceOneToOne.isPrivateOwned());
		assertFalse(contextOneToOne.getPrivateOwned().isPrivateOwned());
	}
	
	public void testModifyPrivateOwned() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		EclipseLinkOneToOneMapping contextOneToOne = 
			(EclipseLinkOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlOneToOne resourceOneToOne = 
			(XmlOneToOne) resourceEntity.getAttributes().getOneToOnes().get(0);
		
		// check defaults
		
		assertFalse(resourceOneToOne.isPrivateOwned());
		assertFalse(contextOneToOne.getPrivateOwned().isPrivateOwned());
		
		// set context private owned  to true, check resource
		
		contextOneToOne.getPrivateOwned().setPrivateOwned(true);
		
		assertTrue(resourceOneToOne.isPrivateOwned());
		assertTrue(contextOneToOne.getPrivateOwned().isPrivateOwned());
		
		// set context private owned back to false, check resource
		
		contextOneToOne.getPrivateOwned().setPrivateOwned(false);
	}
	
	public void testUpdateJoinFetch() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		EclipseLinkOneToOneMapping contextOneToOne = 
			(EclipseLinkOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity) getXmlEntityMappings().getEntities().get(0);
		XmlOneToOne resourceOneToOne = 
			(XmlOneToOne) resourceEntity.getAttributes().getOneToOnes().get(0);
		
		// check defaults
		
		assertNull(resourceOneToOne.getJoinFetch());
		assertNull(contextOneToOne.getJoinFetch().getValue());
		
		// set xml join fetch to INNER, check context
		
		resourceOneToOne.setJoinFetch(XmlJoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceOneToOne.getJoinFetch());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextOneToOne.getJoinFetch().getValue());
		
		// set xml join fetch to OUTER, check context
		
		resourceOneToOne.setJoinFetch(XmlJoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceOneToOne.getJoinFetch());
		assertEquals(EclipseLinkJoinFetchType.OUTER, contextOneToOne.getJoinFetch().getValue());
		
		// set xml join fetch to null, check context
		
		resourceOneToOne.setJoinFetch(null);
		
		assertNull(resourceOneToOne.getJoinFetch());
		assertNull(contextOneToOne.getJoinFetch().getValue());
	}
	
	public void testModifyJoinFetch() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		EclipseLinkOneToOneMapping contextOneToOne = 
			(EclipseLinkOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlOneToOne resourceOneToOne = 
			(XmlOneToOne) resourceEntity.getAttributes().getOneToOnes().get(0);
		
		// check defaults
		
		assertNull(resourceOneToOne.getJoinFetch());
		assertNull(contextOneToOne.getJoinFetch().getValue());
		
		// set context join fetch to INNER, check resource
		
		contextOneToOne.getJoinFetch().setValue(EclipseLinkJoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceOneToOne.getJoinFetch());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextOneToOne.getJoinFetch().getValue());
		
		// set context join fetch to OUTER, check resource
		
		contextOneToOne.getJoinFetch().setValue(EclipseLinkJoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceOneToOne.getJoinFetch());
		assertEquals(EclipseLinkJoinFetchType.OUTER, contextOneToOne.getJoinFetch().getValue());
		
		// set context join fetch to null, check resource
		
		contextOneToOne.getJoinFetch().setValue(null);
		
		assertNull(resourceOneToOne.getJoinFetch());
		assertNull(contextOneToOne.getJoinFetch().getValue());
	}
	
	public void testJoinFetchDefaultFromJava() throws Exception {
		createTestEmployee();
		createTestDepartment();
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Department");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Employee");
		
		OrmPersistentType departmentPersistentType = getEntityMappings().persistentTypes().next();
		EclipseLinkOrmOneToOneMapping oneToOne = (EclipseLinkOrmOneToOneMapping) departmentPersistentType.getAttributeNamed("employee").getMapping();

		assertNull(oneToOne.getJoinFetch().getValue());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		oneToOne = (EclipseLinkOrmOneToOneMapping) departmentPersistentType.getAttributeNamed("employee").getMapping();
		assertNull(oneToOne.getJoinFetch().getValue());		
		
		EclipseLinkRelationshipMapping javaRelationshipMapping = (EclipseLinkRelationshipMapping) departmentPersistentType.getJavaPersistentType().getAttributeNamed("employee").getMapping();
		javaRelationshipMapping.getJoinFetch().setValue(EclipseLinkJoinFetchType.OUTER);
		assertNull(oneToOne.getJoinFetch().getValue());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		oneToOne = (EclipseLinkOrmOneToOneMapping) departmentPersistentType.getAttributeNamed("employee").getMapping();
		assertEquals(EclipseLinkJoinFetchType.OUTER, oneToOne.getJoinFetch().getValue());
	}
}
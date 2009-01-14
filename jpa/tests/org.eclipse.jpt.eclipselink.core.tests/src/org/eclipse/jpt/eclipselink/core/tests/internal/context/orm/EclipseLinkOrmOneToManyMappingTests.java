/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany;

@SuppressWarnings("nls")
public class EclipseLinkOrmOneToManyMappingTests
	extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkOrmOneToManyMappingTests(String name) {
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
					sb.append("import java.util.Collection;");
					sb.append(CR);
					sb.append("import ").append(JPA.JOIN_TABLE).append(";");
					sb.append(CR);
					sb.append("import ").append(JPA.JOIN_COLUMN).append(";");
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("Department").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int id;").append(CR);
				sb.append(CR);
				sb.append("    @JoinTable(name=\"DEP_EMP\", joinColumns=@JoinColumn(name=\"DEPT_ID\"), inverseJoinColumns=@JoinColumn(name=\"EMP_ID\"))").append(CR);
				sb.append("    private Collection<Employee> employees;").append(CR);
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
			getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		EclipseLinkOneToManyMapping contextOneToMany = 
			(EclipseLinkOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity) getOrmResource().getEntityMappings().getEntities().get(0);
		XmlOneToMany resourceOneToMany = 
			(XmlOneToMany) resourceEntity.getAttributes().getOneToManys().get(0);
		
		// check defaults
		
		assertFalse(resourceOneToMany.isPrivateOwned());
		assertFalse(contextOneToMany.getPrivateOwned().isPrivateOwned());
		
		// set xml private owned to true, check context
		
		resourceOneToMany.setPrivateOwned(true);
		
		assertTrue(resourceOneToMany.isPrivateOwned());
		assertTrue(contextOneToMany.getPrivateOwned().isPrivateOwned());
		
		// set xml private owned back to false, check context
		
		resourceOneToMany.setPrivateOwned(false);
		
		assertFalse(resourceOneToMany.isPrivateOwned());
		assertFalse(contextOneToMany.getPrivateOwned().isPrivateOwned());
	}
	
	public void testModifyPrivateOwned() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		EclipseLinkOneToManyMapping contextOneToMany = 
			(EclipseLinkOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getOrmResource().getEntityMappings().getEntities().get(0);
		XmlOneToMany resourceOneToMany = 
			(XmlOneToMany) resourceEntity.getAttributes().getOneToManys().get(0);
		
		// check defaults
		
		assertFalse(resourceOneToMany.isPrivateOwned());
		assertFalse(contextOneToMany.getPrivateOwned().isPrivateOwned());
		
		// set context private owned  to true, check resource
		
		contextOneToMany.getPrivateOwned().setPrivateOwned(true);
		
		assertTrue(resourceOneToMany.isPrivateOwned());
		assertTrue(contextOneToMany.getPrivateOwned().isPrivateOwned());
		
		// set context private owned back to false, check resource
		
		contextOneToMany.getPrivateOwned().setPrivateOwned(false);
		
		assertFalse(resourceOneToMany.isPrivateOwned());
		assertFalse(contextOneToMany.getPrivateOwned().isPrivateOwned());
	}
	
	public void testUpdateJoinFetch() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		EclipseLinkOneToManyMapping contextOneToMany = 
			(EclipseLinkOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity) getOrmResource().getEntityMappings().getEntities().get(0);
		XmlOneToMany resourceOneToMany = 
			(XmlOneToMany) resourceEntity.getAttributes().getOneToManys().get(0);
		
		// check defaults
		
		assertNull(resourceOneToMany.getJoinFetch());
		assertNull(contextOneToMany.getJoinFetch().getValue());
		
		// set xml join fetch to INNER, check context
		
		resourceOneToMany.setJoinFetch(XmlJoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceOneToMany.getJoinFetch());
		assertEquals(JoinFetchType.INNER, contextOneToMany.getJoinFetch().getValue());
		
		// set xml join fetch to OUTER, check context
		
		resourceOneToMany.setJoinFetch(XmlJoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceOneToMany.getJoinFetch());
		assertEquals(JoinFetchType.OUTER, contextOneToMany.getJoinFetch().getValue());
		
		// set xml join fetch to null, check context
		
		resourceOneToMany.setJoinFetch(null);
		
		assertNull(resourceOneToMany.getJoinFetch());
		assertNull(contextOneToMany.getJoinFetch().getValue());
	}
	
	public void testModifyJoinFetch() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		EclipseLinkOneToManyMapping contextOneToMany = 
			(EclipseLinkOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getOrmResource().getEntityMappings().getEntities().get(0);
		XmlOneToMany resourceOneToMany = 
			(XmlOneToMany) resourceEntity.getAttributes().getOneToManys().get(0);
		
		// check defaults
		
		assertNull(resourceOneToMany.getJoinFetch());
		assertNull(contextOneToMany.getJoinFetch().getValue());
		
		// set context join fetch to INNER, check resource
		
		contextOneToMany.getJoinFetch().setValue(JoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceOneToMany.getJoinFetch());
		assertEquals(JoinFetchType.INNER, contextOneToMany.getJoinFetch().getValue());
		
		// set context join fetch to OUTER, check resource
		
		contextOneToMany.getJoinFetch().setValue(JoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceOneToMany.getJoinFetch());
		assertEquals(JoinFetchType.OUTER, contextOneToMany.getJoinFetch().getValue());
		
		// set context join fetch to null, check resource
		
		contextOneToMany.getJoinFetch().setValue(null);
		
		assertNull(resourceOneToMany.getJoinFetch());
		assertNull(contextOneToMany.getJoinFetch().getValue());
	}
	
	public void testDefaultJoinTable() throws Exception {
		createTestEmployee();
		createTestDepartment();
		getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Department");
		getEntityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Employee");
		
		OrmPersistentType departmentPersistentType = getEntityMappings().ormPersistentTypes().next();
		OrmOneToManyMapping oneToMany = (OrmOneToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		
		assertEquals(true, oneToMany.getPersistentAttribute().isVirtual());
		OrmJoinTable ormJoinTable = oneToMany.getJoinTable();
		assertEquals("DEP_EMP", ormJoinTable.getName());
		assertEquals("DEPT_ID", ormJoinTable.specifiedJoinColumns().next().getName());
		assertEquals("id", ormJoinTable.specifiedJoinColumns().next().getReferencedColumnName());
		assertEquals("EMP_ID", ormJoinTable.specifiedInverseJoinColumns().next().getName());
		assertEquals("empId", ormJoinTable.specifiedInverseJoinColumns().next().getReferencedColumnName());

		//set metadata-complete and verify JoinTable info is not taken from the java
		departmentPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		
		oneToMany = (OrmOneToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		
		assertEquals(true, oneToMany.getPersistentAttribute().isVirtual());
		ormJoinTable = oneToMany.getJoinTable();
		assertEquals("Department_Employee", ormJoinTable.getName());
		assertEquals("Department_id", ormJoinTable.specifiedJoinColumns().next().getName());
		assertEquals("id", ormJoinTable.specifiedJoinColumns().next().getReferencedColumnName());
		assertEquals("employees_empId", ormJoinTable.specifiedInverseJoinColumns().next().getName());
		assertEquals("empId", ormJoinTable.specifiedInverseJoinColumns().next().getReferencedColumnName());
		
		//set metadata-complete to false, add mapping to orm.xml verify JoinTable info is not taken from the java
		departmentPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.FALSE);
		oneToMany = (OrmOneToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		assertEquals(true, oneToMany.getPersistentAttribute().isVirtual());
		ormJoinTable = oneToMany.getJoinTable();
		assertEquals("DEP_EMP", ormJoinTable.getName());
		assertEquals("DEPT_ID", ormJoinTable.specifiedJoinColumns().next().getName());
		assertEquals("id", ormJoinTable.specifiedJoinColumns().next().getReferencedColumnName());
		assertEquals("EMP_ID", ormJoinTable.specifiedInverseJoinColumns().next().getName());
		assertEquals("empId", ormJoinTable.specifiedInverseJoinColumns().next().getReferencedColumnName());

		
		departmentPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "employees");
		oneToMany = (OrmOneToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		assertEquals(false, oneToMany.getPersistentAttribute().isVirtual());
		ormJoinTable = oneToMany.getJoinTable();
		assertEquals("Department_Employee", ormJoinTable.getName());
		assertEquals(0, ormJoinTable.specifiedJoinColumnsSize());
		assertEquals("Department_id", ormJoinTable.getDefaultJoinColumn().getName());
		assertEquals("id", ormJoinTable.getDefaultJoinColumn().getReferencedColumnName());
		assertEquals(0, ormJoinTable.specifiedInverseJoinColumnsSize());
		assertEquals("employees_empId", ormJoinTable.getDefaultInverseJoinColumn().getName());
		assertEquals("empId", ormJoinTable.getDefaultInverseJoinColumn().getReferencedColumnName());
	}
}

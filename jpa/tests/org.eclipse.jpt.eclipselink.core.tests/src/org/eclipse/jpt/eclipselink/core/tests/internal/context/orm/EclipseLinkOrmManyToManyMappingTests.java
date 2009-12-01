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
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkManyToManyMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany;

@SuppressWarnings("nls")
public class EclipseLinkOrmManyToManyMappingTests
	extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkOrmManyToManyMappingTests(String name) {
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
					sb.append("import ").append(JPA.MANY_TO_MANY).append(";");
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
				sb.append("    @ManyToMany").append(CR);
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
	public void testUpdateJoinFetch() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		EclipseLinkRelationshipMapping contextManyToMany = 
			(EclipseLinkRelationshipMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity) getXmlEntityMappings().getEntities().get(0);
		XmlManyToMany resourceManyToMany = 
			(XmlManyToMany) resourceEntity.getAttributes().getManyToManys().get(0);
		
		// check defaults
		
		assertNull(resourceManyToMany.getJoinFetch());
		assertNull(contextManyToMany.getJoinFetch().getValue());
		
		// set xml join fetch to INNER, check context
		
		resourceManyToMany.setJoinFetch(XmlJoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceManyToMany.getJoinFetch());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextManyToMany.getJoinFetch().getValue());
		
		// set xml join fetch to OUTER, check context
		
		resourceManyToMany.setJoinFetch(XmlJoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceManyToMany.getJoinFetch());
		assertEquals(EclipseLinkJoinFetchType.OUTER, contextManyToMany.getJoinFetch().getValue());
		
		// set xml join fetch to null, check context
		
		resourceManyToMany.setJoinFetch(null);
		
		assertNull(resourceManyToMany.getJoinFetch());
		assertNull(contextManyToMany.getJoinFetch().getValue());
	}
	
	public void testModifyJoinFetch() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		EclipseLinkRelationshipMapping contextManyToMany = 
			(EclipseLinkRelationshipMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlManyToMany resourceManyToMany = 
			(XmlManyToMany) resourceEntity.getAttributes().getManyToManys().get(0);
		
		// check defaults
		
		assertNull(resourceManyToMany.getJoinFetch());
		assertNull(contextManyToMany.getJoinFetch().getValue());
		
		// set context join fetch to INNER, check resource
		
		contextManyToMany.getJoinFetch().setValue(EclipseLinkJoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceManyToMany.getJoinFetch());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextManyToMany.getJoinFetch().getValue());
		
		// set context join fetch to OUTER, check resource
		
		contextManyToMany.getJoinFetch().setValue(EclipseLinkJoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceManyToMany.getJoinFetch());
		assertEquals(EclipseLinkJoinFetchType.OUTER, contextManyToMany.getJoinFetch().getValue());
		
		// set context join fetch to null, check resource
		
		contextManyToMany.getJoinFetch().setValue(null);
		
		assertNull(resourceManyToMany.getJoinFetch());
		assertNull(contextManyToMany.getJoinFetch().getValue());
	}
	
	public void testJoinFetchDefault() throws Exception {
		createTestEmployee();
		createTestDepartment();
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Department");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Employee");
		
		OrmPersistentType departmentPersistentType = getEntityMappings().persistentTypes().next();
		OrmEclipseLinkManyToManyMapping manyToMany = (OrmEclipseLinkManyToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();

		assertNull(manyToMany.getJoinFetch().getValue());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		OrmEclipseLinkOneToManyMapping oneToMany = (OrmEclipseLinkOneToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		assertNull(oneToMany.getJoinFetch().getValue());
		
		
		EclipseLinkRelationshipMapping javaRelationshipMapping = (EclipseLinkRelationshipMapping) departmentPersistentType.getJavaPersistentType().getAttributeNamed("employees").getMapping();
		javaRelationshipMapping.getJoinFetch().setValue(EclipseLinkJoinFetchType.OUTER);
		assertNull(oneToMany.getJoinFetch().getValue());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		manyToMany = (OrmEclipseLinkManyToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		assertEquals(EclipseLinkJoinFetchType.OUTER, manyToMany.getJoinFetch().getValue());
	}
	
	public void testDefaultJoinTable() throws Exception {
		createTestEmployee();
		createTestDepartment();
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Department");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Employee");
		
		OrmPersistentType departmentPersistentType = getEntityMappings().persistentTypes().next();
		OrmManyToManyMapping manyToMany = (OrmManyToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		
		assertEquals(true, manyToMany.getPersistentAttribute().isVirtual());
		OrmJoinTable ormJoinTable = manyToMany.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable();
		assertEquals("DEP_EMP", ormJoinTable.getName());
		assertEquals("DEPT_ID", ormJoinTable.specifiedJoinColumns().next().getName());
		assertEquals("id", ormJoinTable.specifiedJoinColumns().next().getReferencedColumnName());
		assertEquals("EMP_ID", ormJoinTable.specifiedInverseJoinColumns().next().getName());
		assertEquals("empId", ormJoinTable.specifiedInverseJoinColumns().next().getReferencedColumnName());

		//set metadata-complete and verify JoinTable info is not taken from the java
		departmentPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		departmentPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		OrmOneToManyMapping oneToMany = (OrmOneToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		
		assertEquals(true, oneToMany.getPersistentAttribute().isVirtual());
		ormJoinTable = oneToMany.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable();
		assertEquals("Department_Employee", ormJoinTable.getName());
		assertEquals("Department_id", ormJoinTable.specifiedJoinColumns().next().getName());
		assertEquals("id", ormJoinTable.specifiedJoinColumns().next().getReferencedColumnName());
		assertEquals("employees_empId", ormJoinTable.specifiedInverseJoinColumns().next().getName());
		assertEquals("empId", ormJoinTable.specifiedInverseJoinColumns().next().getReferencedColumnName());
		
		//set metadata-complete to false, add mapping to orm.xml verify JoinTable info is not taken from the java
		departmentPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.FALSE);
		manyToMany = (OrmManyToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		assertEquals(true, manyToMany.getPersistentAttribute().isVirtual());
		ormJoinTable = manyToMany.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable();
		assertEquals("DEP_EMP", ormJoinTable.getName());
		assertEquals("DEPT_ID", ormJoinTable.specifiedJoinColumns().next().getName());
		assertEquals("id", ormJoinTable.specifiedJoinColumns().next().getReferencedColumnName());
		assertEquals("EMP_ID", ormJoinTable.specifiedInverseJoinColumns().next().getName());
		assertEquals("empId", ormJoinTable.specifiedInverseJoinColumns().next().getReferencedColumnName());

		
		departmentPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "employees");
		manyToMany = (OrmManyToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		assertEquals(false, manyToMany.getPersistentAttribute().isVirtual());
		ormJoinTable = manyToMany.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable();
		assertEquals("Department_Employee", ormJoinTable.getName());
		assertEquals(0, ormJoinTable.specifiedJoinColumnsSize());
		assertEquals("Department_id", ormJoinTable.getDefaultJoinColumn().getName());
		assertEquals("id", ormJoinTable.getDefaultJoinColumn().getReferencedColumnName());
		assertEquals(0, ormJoinTable.specifiedInverseJoinColumnsSize());
		assertEquals("employees_empId", ormJoinTable.getDefaultInverseJoinColumn().getName());
		assertEquals("empId", ormJoinTable.getDefaultInverseJoinColumn().getReferencedColumnName());
	
	}
}

/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.Cascade;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetchMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkManyToManyMapping;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

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

	private ICompilationUnit createTestEntityManyToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.FETCH_TYPE, JPA.CASCADE_TYPE, JPA.ORDER_BY, EclipseLink.JOIN_FETCH, EclipseLink.JOIN_FETCH_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}

			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToMany(fetch=FetchType.EAGER, targetEntity=Address.class, cascade={CascadeType.ALL, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})");
				sb.append(CR);
				sb.append("    @OrderBy(\"city\"");
				sb.append(CR);
				sb.append("    @JoinFetch(JoinFetchType.INNER)");
				sb.append(CR);
				sb.append("    private java.util.Collection<Address> address;").append(CR);
				sb.append(CR);
				sb.append("    @Id");				
			}
		});
	}

	private void createTestTargetEntityAddress() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ID);
					sb.append(";");
					sb.append(CR);
				sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("Address").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int id;").append(CR);
				sb.append(CR);
				sb.append("    private String city;").append(CR);
				sb.append(CR);
				sb.append("    private State state;").append(CR);
				sb.append(CR);
				sb.append("    private int zip;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
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
		
		OrmPersistentType departmentPersistentType = getEntityMappings().getPersistentTypes().iterator().next();
		EclipseLinkRelationshipMapping manyToMany = (EclipseLinkRelationshipMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();

		assertNull(manyToMany.getJoinFetch().getValue());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		EclipseLinkOneToManyMapping oneToMany = (EclipseLinkOneToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		assertNull(oneToMany.getJoinFetch().getValue());
		
		
		EclipseLinkRelationshipMapping javaRelationshipMapping = (EclipseLinkRelationshipMapping) departmentPersistentType.getJavaPersistentType().getAttributeNamed("employees").getMapping();
		javaRelationshipMapping.getJoinFetch().setValue(EclipseLinkJoinFetchType.OUTER);
		assertNull(oneToMany.getJoinFetch().getValue());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		manyToMany = (EclipseLinkRelationshipMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		assertEquals(EclipseLinkJoinFetchType.OUTER, manyToMany.getJoinFetch().getValue());
	}
	
	public void testDefaultJoinTable() throws Exception {
		createTestEmployee();
		createTestDepartment();
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Department");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Employee");
		
		OrmPersistentType departmentPersistentType = getEntityMappings().getPersistentTypes().iterator().next();
		OrmReadOnlyPersistentAttribute attribute = departmentPersistentType.getAttributeNamed("employees");
		ManyToManyMapping manyToMany = (ManyToManyMapping) attribute.getMapping();
		
		assertEquals(true, attribute.isVirtual());
		JoinTable ormJoinTable = manyToMany.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable();
		assertEquals("DEP_EMP", ormJoinTable.getName());
		assertEquals("DEPT_ID", ormJoinTable.specifiedJoinColumns().next().getName());
		assertEquals("id", ormJoinTable.specifiedJoinColumns().next().getReferencedColumnName());
		assertEquals("EMP_ID", ormJoinTable.specifiedInverseJoinColumns().next().getName());
		assertEquals("empId", ormJoinTable.specifiedInverseJoinColumns().next().getReferencedColumnName());

		//set metadata-complete and verify JoinTable info is not taken from the java
		departmentPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		departmentPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		attribute = departmentPersistentType.getAttributeNamed("employees");
		OneToManyMapping oneToMany = (OneToManyMapping) attribute.getMapping();
		
		assertEquals(true, attribute.isVirtual());
		ormJoinTable = oneToMany.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable();
		assertEquals("Department_Employee", ormJoinTable.getName());
		assertEquals("Department_id", ormJoinTable.getDefaultJoinColumn().getName());
		assertEquals("id", ormJoinTable.getDefaultJoinColumn().getReferencedColumnName());
		assertEquals("employees_empId", ormJoinTable.getDefaultInverseJoinColumn().getName());
		assertEquals("empId", ormJoinTable.getDefaultInverseJoinColumn().getReferencedColumnName());
		
		//set metadata-complete to false, add mapping to orm.xml verify JoinTable info is not taken from the java
		departmentPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.FALSE);
		attribute = departmentPersistentType.getAttributeNamed("employees");
		manyToMany = (ManyToManyMapping) attribute.getMapping();
		assertEquals(true, attribute.isVirtual());
		ormJoinTable = manyToMany.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable();
		assertEquals("DEP_EMP", ormJoinTable.getName());
		assertEquals("DEPT_ID", ormJoinTable.specifiedJoinColumns().next().getName());
		assertEquals("id", ormJoinTable.specifiedJoinColumns().next().getReferencedColumnName());
		assertEquals("EMP_ID", ormJoinTable.specifiedInverseJoinColumns().next().getName());
		assertEquals("empId", ormJoinTable.specifiedInverseJoinColumns().next().getReferencedColumnName());

		
		departmentPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "employees");
		attribute = departmentPersistentType.getAttributeNamed("employees");
		manyToMany = (ManyToManyMapping) attribute.getMapping();
		assertEquals(false, attribute.isVirtual());
		ormJoinTable = manyToMany.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable();
		assertEquals("Department_Employee", ormJoinTable.getName());
		assertEquals(0, ormJoinTable.specifiedJoinColumnsSize());
		assertEquals("Department_id", ormJoinTable.getDefaultJoinColumn().getName());
		assertEquals("id", ormJoinTable.getDefaultJoinColumn().getReferencedColumnName());
		assertEquals(0, ormJoinTable.specifiedInverseJoinColumnsSize());
		assertEquals("employees_empId", ormJoinTable.getDefaultInverseJoinColumn().getName());
		assertEquals("empId", ormJoinTable.getDefaultInverseJoinColumn().getReferencedColumnName());
	}

	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityManyToManyMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, ormPersistentType.virtualAttributesSize());		
		OrmReadOnlyPersistentAttribute virtualPersistentAttribute = ormPersistentType.virtualAttributes().next();

		ManyToManyMapping virtualManyToManyMapping = (ManyToManyMapping) virtualPersistentAttribute.getMapping();	
		assertEquals("address", virtualManyToManyMapping.getName());
		assertEquals(FetchType.EAGER, virtualManyToManyMapping.getSpecifiedFetch());
		assertEquals("Address", virtualManyToManyMapping.getSpecifiedTargetEntity());
		assertNull(virtualManyToManyMapping.getRelationshipReference().
			getMappedByJoiningStrategy().getMappedByAttribute());

		Cascade cascade = virtualManyToManyMapping.getCascade();
		assertTrue(cascade.isAll());
		assertTrue(cascade.isMerge());
		assertTrue(cascade.isPersist());
		assertTrue(cascade.isRemove());
		assertTrue(cascade.isRefresh());

		assertTrue(virtualManyToManyMapping.getOrderable().isCustomOrdering());
		assertEquals("city", virtualManyToManyMapping.getOrderable().getSpecifiedOrderBy());

		assertEquals(EclipseLinkJoinFetchType.INNER, ((EclipseLinkJoinFetchMapping) virtualManyToManyMapping).getJoinFetch().getValue());
	}

	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityManyToManyMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(3, ormPersistentType.virtualAttributesSize());		
		OrmReadOnlyPersistentAttribute virtualPersistentAttribute = ormPersistentType.getAttributeNamed("address");

		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, virtualPersistentAttribute.getMappingKey());
		assertTrue(virtualPersistentAttribute.isVirtual());

		OrmPersistentAttribute ormPersistentAttribute = virtualPersistentAttribute.convertToSpecified(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);

		OrmEclipseLinkManyToManyMapping ormManyToManyMapping = (OrmEclipseLinkManyToManyMapping) ormPersistentAttribute.getMapping();	
		assertEquals("address", ormManyToManyMapping.getName());
		assertEquals(FetchType.LAZY, ormManyToManyMapping.getFetch());
		assertEquals("test.Address", ormManyToManyMapping.getTargetEntity());
		assertNull(ormManyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().getMappedByAttribute());

		Cascade cascade = ormManyToManyMapping.getCascade();
		assertFalse(cascade.isAll());
		assertFalse(cascade.isMerge());
		assertFalse(cascade.isPersist());
		assertFalse(cascade.isRemove());
		assertFalse(cascade.isRefresh());

		assertTrue(ormManyToManyMapping.getOrderable().isNoOrdering());
		assertEquals(null, ormManyToManyMapping.getOrderable().getSpecifiedOrderBy());

		assertEquals(null, ormManyToManyMapping.getJoinFetch().getValue());
	}
}

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

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

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
	
	private ICompilationUnit createTestEntityWithValidMapOneToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany").append(CR);				
				sb.append("    private java.util.Map<String, Address> addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}

	private ICompilationUnit createTestEntityWithValidNonGenericMapOneToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany").append(CR);				
				sb.append("    private java.util.Map addresses;").append(CR);			
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	private void createTestEmbeddableState() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.EMBEDDABLE);
					sb.append(";");
					sb.append(CR);
				sb.append("@Embeddable");
				sb.append(CR);
				sb.append("public class ").append("State").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    private String foo;").append(CR);
				sb.append(CR);
				sb.append("    private Address address;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "State.java", sourceWriter);
	}

	public void testUpdatePrivateOwned() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		EclipseLinkOneToManyMapping contextOneToMany = 
			(EclipseLinkOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity) getXmlEntityMappings().getEntities().get(0);
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
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		EclipseLinkOneToManyMapping contextOneToMany = 
			(EclipseLinkOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
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
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		EclipseLinkOneToManyMapping contextOneToMany = 
			(EclipseLinkOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity) getXmlEntityMappings().getEntities().get(0);
		XmlOneToMany resourceOneToMany = 
			(XmlOneToMany) resourceEntity.getAttributes().getOneToManys().get(0);
		
		// check defaults
		
		assertNull(resourceOneToMany.getJoinFetch());
		assertNull(contextOneToMany.getJoinFetch().getValue());
		
		// set xml join fetch to INNER, check context
		
		resourceOneToMany.setJoinFetch(XmlJoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceOneToMany.getJoinFetch());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextOneToMany.getJoinFetch().getValue());
		
		// set xml join fetch to OUTER, check context
		
		resourceOneToMany.setJoinFetch(XmlJoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceOneToMany.getJoinFetch());
		assertEquals(EclipseLinkJoinFetchType.OUTER, contextOneToMany.getJoinFetch().getValue());
		
		// set xml join fetch to null, check context
		
		resourceOneToMany.setJoinFetch(null);
		
		assertNull(resourceOneToMany.getJoinFetch());
		assertNull(contextOneToMany.getJoinFetch().getValue());
	}
	
	public void testModifyJoinFetch() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		EclipseLinkOneToManyMapping contextOneToMany = 
			(EclipseLinkOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlOneToMany resourceOneToMany = 
			(XmlOneToMany) resourceEntity.getAttributes().getOneToManys().get(0);
		
		// check defaults
		
		assertNull(resourceOneToMany.getJoinFetch());
		assertNull(contextOneToMany.getJoinFetch().getValue());
		
		// set context join fetch to INNER, check resource
		
		contextOneToMany.getJoinFetch().setValue(EclipseLinkJoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceOneToMany.getJoinFetch());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextOneToMany.getJoinFetch().getValue());
		
		// set context join fetch to OUTER, check resource
		
		contextOneToMany.getJoinFetch().setValue(EclipseLinkJoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceOneToMany.getJoinFetch());
		assertEquals(EclipseLinkJoinFetchType.OUTER, contextOneToMany.getJoinFetch().getValue());
		
		// set context join fetch to null, check resource
		
		contextOneToMany.getJoinFetch().setValue(null);
		
		assertNull(resourceOneToMany.getJoinFetch());
		assertNull(contextOneToMany.getJoinFetch().getValue());
	}
	
	public void testJoinFetchDefault() throws Exception {
		createTestEmployee();
		createTestDepartment();
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Department");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Employee");
		
		OrmPersistentType departmentPersistentType = getEntityMappings().getPersistentTypes().iterator().next();
		OrmEclipseLinkOneToManyMapping oneToMany = (OrmEclipseLinkOneToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();

		assertNull(oneToMany.getJoinFetch().getValue());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		oneToMany = (OrmEclipseLinkOneToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		assertNull(oneToMany.getJoinFetch().getValue());		
		
		EclipseLinkRelationshipMapping javaRelationshipMapping = (EclipseLinkRelationshipMapping) departmentPersistentType.getJavaPersistentType().getAttributeNamed("employees").getMapping();
		javaRelationshipMapping.getJoinFetch().setValue(EclipseLinkJoinFetchType.OUTER);
		assertNull(oneToMany.getJoinFetch().getValue());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		oneToMany = (OrmEclipseLinkOneToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		assertEquals(EclipseLinkJoinFetchType.OUTER, oneToMany.getJoinFetch().getValue());
	}
	
	public void testDefaultJoinTable() throws Exception {
		createTestEmployee();
		createTestDepartment();
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Department");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Employee");
		
		OrmPersistentType departmentPersistentType = getEntityMappings().getPersistentTypes().iterator().next();
		OrmOneToManyMapping oneToMany = (OrmOneToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		
		assertEquals(true, oneToMany.getPersistentAttribute().isVirtual());
		OrmJoinTable ormJoinTable = oneToMany.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable();
		assertEquals("DEP_EMP", ormJoinTable.getName());
		assertEquals("DEPT_ID", ormJoinTable.specifiedJoinColumns().next().getName());
		assertEquals("id", ormJoinTable.specifiedJoinColumns().next().getReferencedColumnName());
		assertEquals("EMP_ID", ormJoinTable.specifiedInverseJoinColumns().next().getName());
		assertEquals("empId", ormJoinTable.specifiedInverseJoinColumns().next().getReferencedColumnName());

		//set metadata-complete and verify JoinTable info is not taken from the java
		departmentPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		departmentPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");	
		oneToMany = (OrmOneToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		
		assertEquals(true, oneToMany.getPersistentAttribute().isVirtual());
		ormJoinTable = oneToMany.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable();
		assertEquals("Department_Employee", ormJoinTable.getName());
		assertEquals("Department_id", ormJoinTable.specifiedJoinColumns().next().getName());
		assertEquals("id", ormJoinTable.specifiedJoinColumns().next().getReferencedColumnName());
		assertEquals("employees_empId", ormJoinTable.specifiedInverseJoinColumns().next().getName());
		assertEquals("empId", ormJoinTable.specifiedInverseJoinColumns().next().getReferencedColumnName());
		
		//set metadata-complete to false, add mapping to orm.xml verify JoinTable info is not taken from the java
		departmentPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.FALSE);
		oneToMany = (OrmOneToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		assertEquals(true, oneToMany.getPersistentAttribute().isVirtual());
		ormJoinTable = oneToMany.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable();
		assertEquals("DEP_EMP", ormJoinTable.getName());
		assertEquals("DEPT_ID", ormJoinTable.specifiedJoinColumns().next().getName());
		assertEquals("id", ormJoinTable.specifiedJoinColumns().next().getReferencedColumnName());
		assertEquals("EMP_ID", ormJoinTable.specifiedInverseJoinColumns().next().getName());
		assertEquals("empId", ormJoinTable.specifiedInverseJoinColumns().next().getReferencedColumnName());

		
		departmentPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "employees");
		oneToMany = (OrmOneToManyMapping) departmentPersistentType.getAttributeNamed("employees").getMapping();
		assertEquals(false, oneToMany.getPersistentAttribute().isVirtual());
		ormJoinTable = oneToMany.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable();
		assertEquals("Department_Employee", ormJoinTable.getName());
		assertEquals(0, ormJoinTable.specifiedJoinColumnsSize());
		assertEquals("Department_id", ormJoinTable.getDefaultJoinColumn().getName());
		assertEquals("id", ormJoinTable.getDefaultJoinColumn().getReferencedColumnName());
		assertEquals(0, ormJoinTable.specifiedInverseJoinColumnsSize());
		assertEquals("employees_empId", ormJoinTable.getDefaultInverseJoinColumn().getName());
		assertEquals("empId", ormJoinTable.getDefaultInverseJoinColumn().getReferencedColumnName());
	}
	
	public void testUpdateVirtualMapKey() throws Exception {
		createTestEntityWithValidMapOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");

		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaOneToManyMapping javaOneToManyMapping = (JavaOneToManyMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		assertNull(ormOneToManyMapping.getSpecifiedMapKey());
		assertNull(ormOneToManyMapping.getMapKey());
		assertFalse(ormOneToManyMapping.isPkMapKey());
		assertFalse(ormOneToManyMapping.isCustomMapKey());
		assertTrue(ormOneToManyMapping.isNoMapKey());
		
		//set pk mapKey in the java, verify virtual orm mapping updates
		javaOneToManyMapping.setPkMapKey(true);
		assertEquals("id", ormOneToManyMapping.getMapKey());
		assertTrue(ormOneToManyMapping.isPkMapKey());
		assertFalse(ormOneToManyMapping.isCustomMapKey());
		assertFalse(ormOneToManyMapping.isNoMapKey());
		
		
		//set custom specified mapKey in the java, verify virtual orm mapping updates
		javaOneToManyMapping.setCustomMapKey(true);
		javaOneToManyMapping.setSpecifiedMapKey("city");
		assertEquals("city", ormOneToManyMapping.getSpecifiedMapKey());
		assertEquals("city", ormOneToManyMapping.getMapKey());
		assertFalse(ormOneToManyMapping.isPkMapKey());
		assertTrue(ormOneToManyMapping.isCustomMapKey());
		assertFalse(ormOneToManyMapping.isNoMapKey());
		
		//set metadata complete and verify that the orm model ignores the java annotations
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		assertEquals(null, ormOneToManyMapping.getSpecifiedMapKey());
		assertEquals(null, ormOneToManyMapping.getMapKey());
		assertFalse(ormOneToManyMapping.isPkMapKey());
		assertFalse(ormOneToManyMapping.isCustomMapKey());
		assertTrue(ormOneToManyMapping.isNoMapKey());
	}

	public void testCandidateMapKeyNames() throws Exception {
		createTestEntityWithValidMapOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		
		OrmOneToManyMapping oneToManyMapping = (OrmOneToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();

		Iterator<String> mapKeyNames = 
			oneToManyMapping.candidateMapKeyNames();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
	}
	
	public void testCandidateMapKeyNames2() throws Exception {
		createTestEntityWithValidNonGenericMapOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaOneToManyMapping javaOneToManyMapping = (JavaOneToManyMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();

		Iterator<String> mapKeyNames = ormOneToManyMapping.candidateMapKeyNames();
		assertEquals(false, mapKeyNames.hasNext());
		
		javaOneToManyMapping.setSpecifiedTargetEntity("test.Address");
		mapKeyNames = ormOneToManyMapping.candidateMapKeyNames();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		ormOneToManyMapping.getPersistentAttribute().makeSpecified();
		ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		mapKeyNames = ormOneToManyMapping.candidateMapKeyNames();
		assertEquals(false, mapKeyNames.hasNext());
		
		ormOneToManyMapping.setSpecifiedTargetEntity("test.Address");
		mapKeyNames = ormOneToManyMapping.candidateMapKeyNames();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		ormOneToManyMapping.setSpecifiedTargetEntity("String");
		mapKeyNames = ormOneToManyMapping.candidateMapKeyNames();
		assertEquals(false, mapKeyNames.hasNext());
	}
}

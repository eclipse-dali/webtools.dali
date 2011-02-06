/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.Cascade;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetchMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkOneToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkOneToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlOneToOne;

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

	private ICompilationUnit createTestEntityOneToOneMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE, JPA.JOIN_COLUMN, JPA.FETCH_TYPE, JPA.CASCADE_TYPE, EclipseLink.JOIN_FETCH, EclipseLink.JOIN_FETCH_TYPE, EclipseLink.PRIVATE_OWNED);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}

			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToOne(fetch=FetchType.LAZY, optional=false, targetEntity=Address.class, cascade={CascadeType.ALL, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})");
				sb.append(CR);
				sb.append("    @JoinColumn(name=\"MY_COLUMN\", referencedColumnName=\"MY_REFERENCED_COLUMN\", unique=true, nullable=false, insertable=false, updatable=false, columnDefinition=\"COLUMN_DEFINITION\", table=\"MY_TABLE\")");
				sb.append(CR);
				sb.append("    @JoinFetch(JoinFetchType.INNER)");
				sb.append(CR);
				sb.append("    @PrivateOwned)");
				sb.append(CR);
				sb.append("    private Address address;").append(CR);
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

	public void testUpdatePrivateOwned() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
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
			ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
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
			ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
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
			ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOne");
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
		
		OrmPersistentType departmentPersistentType = getEntityMappings().getPersistentTypes().iterator().next();
		EclipseLinkOneToOneMapping oneToOne = (EclipseLinkOneToOneMapping) departmentPersistentType.getAttributeNamed("employee").getMapping();

		assertNull(oneToOne.getJoinFetch().getValue());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		oneToOne = (EclipseLinkOneToOneMapping) departmentPersistentType.getAttributeNamed("employee").getMapping();
		assertNull(oneToOne.getJoinFetch().getValue());		
		
		EclipseLinkRelationshipMapping javaRelationshipMapping = (EclipseLinkRelationshipMapping) departmentPersistentType.getJavaPersistentType().getAttributeNamed("employee").getMapping();
		javaRelationshipMapping.getJoinFetch().setValue(EclipseLinkJoinFetchType.OUTER);
		assertNull(oneToOne.getJoinFetch().getValue());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		oneToOne = (EclipseLinkOneToOneMapping) departmentPersistentType.getAttributeNamed("employee").getMapping();
		assertEquals(EclipseLinkJoinFetchType.OUTER, oneToOne.getJoinFetch().getValue());
	}

	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityOneToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, ormPersistentType.virtualAttributesSize());		
		OrmReadOnlyPersistentAttribute virtualPersistentAttribute = ormPersistentType.virtualAttributes().next();

		OneToOneMapping virtualOneToOneMapping = (OneToOneMapping) virtualPersistentAttribute.getMapping();	
		assertEquals("address", virtualOneToOneMapping.getName());
		assertEquals(FetchType.LAZY, virtualOneToOneMapping.getSpecifiedFetch());
		assertEquals(Boolean.FALSE, virtualOneToOneMapping.getSpecifiedOptional());
		assertEquals("Address", virtualOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(virtualOneToOneMapping.getRelationship().
			getMappedByStrategy().getMappedByAttribute());

		JoinColumn virtualJoinColumn = 
			virtualOneToOneMapping.getRelationship().getJoinColumnStrategy().specifiedJoinColumns().next();
		assertEquals("MY_COLUMN", virtualJoinColumn.getSpecifiedName());
		assertEquals("MY_REFERENCED_COLUMN", virtualJoinColumn.getSpecifiedReferencedColumnName());
		assertEquals(Boolean.TRUE, virtualJoinColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, virtualJoinColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, virtualJoinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, virtualJoinColumn.getSpecifiedUpdatable());
		assertEquals("COLUMN_DEFINITION", virtualJoinColumn.getColumnDefinition());
		assertEquals("MY_TABLE", virtualJoinColumn.getSpecifiedTable());

		Cascade cascade = virtualOneToOneMapping.getCascade();
		assertTrue(cascade.isAll());
		assertTrue(cascade.isMerge());
		assertTrue(cascade.isPersist());
		assertTrue(cascade.isRemove());
		assertTrue(cascade.isRefresh());

		assertEquals(EclipseLinkJoinFetchType.INNER, ((EclipseLinkJoinFetchMapping) virtualOneToOneMapping).getJoinFetch().getValue());
		assertTrue(((EclipseLinkOneToOneMapping) virtualOneToOneMapping).getPrivateOwned().isPrivateOwned());
	}

	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityOneToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(3, ormPersistentType.virtualAttributesSize());		
		OrmReadOnlyPersistentAttribute virtualPersistentAttribute = ormPersistentType.getAttributeNamed("address");

		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, virtualPersistentAttribute.getMappingKey());
		assertTrue(virtualPersistentAttribute.isVirtual());

		EclipseLinkOneToOneMapping ormOneToOneMapping = (EclipseLinkOneToOneMapping) virtualPersistentAttribute.getMapping();	
		assertEquals("address", ormOneToOneMapping.getName());
		assertEquals(FetchType.EAGER, ormOneToOneMapping.getFetch());
		assertEquals(true, ormOneToOneMapping.isOptional());
		assertEquals("test.Address", ormOneToOneMapping.getTargetEntity());
		assertNull(ormOneToOneMapping.getRelationship().getMappedByStrategy().getMappedByAttribute());

		//TODO default join columns in xml one-to-one
//		XmlJoinColumn ormJoinColumn = ormOneToOneMapping.specifiedJoinColumns().next();
//		//TODO java default columns name in JavaSingleRelationshipMapping.JoinColumnOwner
//		//assertEquals("address", ormJoinColumn.getSpecifiedName());
//		//assertEquals("address", ormJoinColumn.getSpecifiedReferencedColumnName());
//		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedUnique());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedNullable());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedInsertable());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedUpdatable());
//		assertNull(ormJoinColumn.getColumnDefinition());
//		assertEquals(TYPE_NAME, ormJoinColumn.getSpecifiedTable());

		Cascade cascade = ormOneToOneMapping.getCascade();
		assertFalse(cascade.isAll());
		assertFalse(cascade.isMerge());
		assertFalse(cascade.isPersist());
		assertFalse(cascade.isRemove());
		assertFalse(cascade.isRefresh());

		assertEquals(null, ormOneToOneMapping.getJoinFetch().getValue());
		assertFalse(ormOneToOneMapping.getPrivateOwned().isPrivateOwned());
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityOneToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "address");
		assertEquals(2, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		OrmEclipseLinkOneToOneMapping ormOneToOneMapping = (OrmEclipseLinkOneToOneMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("address", ormOneToOneMapping.getName());
		assertNull(ormOneToOneMapping.getSpecifiedFetch());
		assertNull(ormOneToOneMapping.getSpecifiedOptional());
		assertNull(ormOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(ormOneToOneMapping.getRelationship().getMappedByStrategy().getMappedByAttribute());
		assertEquals(FetchType.EAGER, ormOneToOneMapping.getFetch());
		assertEquals(true, ormOneToOneMapping.isOptional());
		//TODO default target entity in xml
		//assertEquals("test.Address", ormOneToOneMapping.getDefaultTargetEntity());
		
		assertTrue(ormOneToOneMapping.getRelationship().getJoinColumnStrategy().joinColumnsSize() > 0);
		
		//TODO default join columns for specified xmlOneToOne mapping
//		XmlJoinColumn ormJoinColumn = ormOneToOneMapping.defaultJoinColumns().next();
//		assertNull(ormJoinColumn.getSpecifiedName());
//		assertNull(ormJoinColumn.getSpecifiedReferencedColumnName());
//		assertNull(ormJoinColumn.getSpecifiedUnique());
//		assertNull(ormJoinColumn.getSpecifiedNullable());
//		assertNull(ormJoinColumn.getSpecifiedInsertable());
//		assertNull(ormJoinColumn.getSpecifiedUpdatable());
//		assertNull(ormJoinColumn.getColumnDefinition());
//		assertNull(ormJoinColumn.getSpecifiedTable());
//		
//		assertEquals("address", ormJoinColumn.getDefaultName());
//		assertEquals("address", ormJoinColumn.getDefaultReferencedColumnName());
//		assertEquals(Boolean.FALSE, ormJoinColumn.getDefaultUnique());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getDefaultNullable());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getDefaultInsertable());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getDefaultUpdatable());
//		assertEquals(null, ormJoinColumn.getColumnDefinition());
//		assertEquals(TYPE_NAME, ormJoinColumn.getDefaultTable());

		Cascade cascade = ormOneToOneMapping.getCascade();
		assertFalse(cascade.isAll());
		assertFalse(cascade.isMerge());
		assertFalse(cascade.isPersist());
		assertFalse(cascade.isRemove());
		assertFalse(cascade.isRefresh());
		
		assertEquals(null, ormOneToOneMapping.getJoinFetch().getValue());
		assertFalse(ormOneToOneMapping.getPrivateOwned().isPrivateOwned());
	}

}
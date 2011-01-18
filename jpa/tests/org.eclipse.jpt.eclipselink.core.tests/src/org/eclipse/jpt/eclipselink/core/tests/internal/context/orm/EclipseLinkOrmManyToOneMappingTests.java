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
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetchMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkManyToOneMapping;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkOrmManyToOneMappingTests
	extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkOrmManyToOneMappingTests(String name) {
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
					sb.append("import ").append(JPA.MANY_TO_ONE).append(";");
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("Department").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int id;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToOne").append(CR);
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

	private ICompilationUnit createTestEntityManyToOneMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE, JPA.JOIN_COLUMN, JPA.FETCH_TYPE, JPA.CASCADE_TYPE, EclipseLink.JOIN_FETCH, EclipseLink.JOIN_FETCH_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}

			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToOne(fetch=FetchType.LAZY, optional=false, targetEntity=Address.class, cascade={CascadeType.ALL, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})");
				sb.append(CR);
				sb.append("    @JoinColumn(name=\"MY_COLUMN\", referencedColumnName=\"MY_REFERENCED_COLUMN\", unique=true, nullable=false, insertable=false, updatable=false, columnDefinition=\"COLUMN_DEFINITION\", table=\"MY_TABLE\")");
				sb.append(CR);
				sb.append("    @JoinFetch(JoinFetchType.INNER)");
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
				sb.append("    private String state;").append(CR);
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
			ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOne");
		EclipseLinkRelationshipMapping contextManyToOne = 
			(EclipseLinkRelationshipMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity) getXmlEntityMappings().getEntities().get(0);
		XmlManyToOne resourceManyToOne = 
			(XmlManyToOne) resourceEntity.getAttributes().getManyToOnes().get(0);
		
		// check defaults
		
		assertNull(resourceManyToOne.getJoinFetch());
		assertNull(contextManyToOne.getJoinFetch().getValue());
		
		// set xml join fetch to INNER, check context
		
		resourceManyToOne.setJoinFetch(XmlJoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceManyToOne.getJoinFetch());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextManyToOne.getJoinFetch().getValue());
		
		// set xml join fetch to OUTER, check context
		
		resourceManyToOne.setJoinFetch(XmlJoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceManyToOne.getJoinFetch());
		assertEquals(EclipseLinkJoinFetchType.OUTER, contextManyToOne.getJoinFetch().getValue());
		
		// set xml join fetch to null, check context
		
		resourceManyToOne.setJoinFetch(null);
		
		assertNull(resourceManyToOne.getJoinFetch());
		assertNull(contextManyToOne.getJoinFetch().getValue());
	}
	
	public void testModifyJoinFetch() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOne");
		EclipseLinkRelationshipMapping contextManyToOne = 
			(EclipseLinkRelationshipMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlManyToOne resourceManyToOne = 
			(XmlManyToOne) resourceEntity.getAttributes().getManyToOnes().get(0);
		
		// check defaults
		
		assertNull(resourceManyToOne.getJoinFetch());
		assertNull(contextManyToOne.getJoinFetch().getValue());
		
		// set context join fetch to INNER, check resource
		
		contextManyToOne.getJoinFetch().setValue(EclipseLinkJoinFetchType.INNER);
		
		assertEquals(XmlJoinFetchType.INNER, resourceManyToOne.getJoinFetch());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextManyToOne.getJoinFetch().getValue());
		
		// set context join fetch to OUTER, check resource
		
		contextManyToOne.getJoinFetch().setValue(EclipseLinkJoinFetchType.OUTER);
		
		assertEquals(XmlJoinFetchType.OUTER, resourceManyToOne.getJoinFetch());
		assertEquals(EclipseLinkJoinFetchType.OUTER, contextManyToOne.getJoinFetch().getValue());
		
		// set context join fetch to null, check resource
		
		contextManyToOne.getJoinFetch().setValue(null);
		
		assertNull(resourceManyToOne.getJoinFetch());
		assertNull(contextManyToOne.getJoinFetch().getValue());
	}
	
	public void testJoinFetchDefaultFromJava() throws Exception {
		createTestEmployee();
		createTestDepartment();
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Department");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Employee");
		
		OrmPersistentType departmentPersistentType = getEntityMappings().getPersistentTypes().iterator().next();
		EclipseLinkRelationshipMapping manyToOneMapping = (EclipseLinkRelationshipMapping) departmentPersistentType.getAttributeNamed("employee").getMapping();

		assertNull(manyToOneMapping.getJoinFetch().getValue());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		EclipseLinkOneToOneMapping oneToOne = (EclipseLinkOneToOneMapping) departmentPersistentType.getAttributeNamed("employee").getMapping();
		assertNull(oneToOne.getJoinFetch().getValue());
		
		
		EclipseLinkRelationshipMapping javaRelationshipMapping = (EclipseLinkRelationshipMapping) departmentPersistentType.getJavaPersistentType().getAttributeNamed("employee").getMapping();
		javaRelationshipMapping.getJoinFetch().setValue(EclipseLinkJoinFetchType.OUTER);
		manyToOneMapping = (EclipseLinkRelationshipMapping) departmentPersistentType.getAttributeNamed("employee").getMapping();
		assertNull(manyToOneMapping.getJoinFetch().getValue());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		manyToOneMapping = (EclipseLinkRelationshipMapping) departmentPersistentType.getAttributeNamed("employee").getMapping();
		assertEquals(EclipseLinkJoinFetchType.OUTER, manyToOneMapping.getJoinFetch().getValue());
	}

	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityManyToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, ormPersistentType.virtualAttributesSize());		
		OrmReadOnlyPersistentAttribute virtualPersistentAttribute = ormPersistentType.virtualAttributes().next();

		ManyToOneMapping virtualManyToOneMapping = (ManyToOneMapping) virtualPersistentAttribute.getMapping();	
		assertEquals("address", virtualManyToOneMapping.getName());
		assertEquals(FetchType.LAZY, virtualManyToOneMapping.getSpecifiedFetch());
		assertEquals(Boolean.FALSE, virtualManyToOneMapping.getSpecifiedOptional());
		assertEquals("Address", virtualManyToOneMapping.getSpecifiedTargetEntity());

		JoinColumn virtualJoinColumn = virtualManyToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy().specifiedJoinColumns().next();
		assertEquals("MY_COLUMN", virtualJoinColumn.getSpecifiedName());
		assertEquals("MY_REFERENCED_COLUMN", virtualJoinColumn.getSpecifiedReferencedColumnName());
		assertEquals(Boolean.TRUE, virtualJoinColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, virtualJoinColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, virtualJoinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, virtualJoinColumn.getSpecifiedUpdatable());
		assertEquals("COLUMN_DEFINITION", virtualJoinColumn.getColumnDefinition());
		assertEquals("MY_TABLE", virtualJoinColumn.getSpecifiedTable());

		Cascade cascade = virtualManyToOneMapping.getCascade();
		assertTrue(cascade.isAll());
		assertTrue(cascade.isMerge());
		assertTrue(cascade.isPersist());
		assertTrue(cascade.isRemove());
		assertTrue(cascade.isRefresh());

		assertEquals(EclipseLinkJoinFetchType.INNER, ((EclipseLinkJoinFetchMapping) virtualManyToOneMapping).getJoinFetch().getValue());
	}

	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityManyToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(3, ormPersistentType.virtualAttributesSize());		
		OrmReadOnlyPersistentAttribute virtualPersistentAttribute = ormPersistentType.getAttributeNamed("address");

		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, virtualPersistentAttribute.getMappingKey());
		assertTrue(virtualPersistentAttribute.isVirtual());

		OrmReadOnlyPersistentAttribute ormPersistentAttribute = virtualPersistentAttribute.convertToSpecified(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);

		OrmEclipseLinkManyToOneMapping ormManyToOneMapping = (OrmEclipseLinkManyToOneMapping) ormPersistentAttribute.getMapping();	
		assertEquals("address", ormManyToOneMapping.getName());
		assertEquals(FetchType.EAGER, ormManyToOneMapping.getDefaultFetch());
		assertEquals(true, ormManyToOneMapping.isDefaultOptional());
		assertEquals("test.Address", ormManyToOneMapping.getDefaultTargetEntity());

		Cascade cascade = ormManyToOneMapping.getCascade();
		assertFalse(cascade.isAll());
		assertFalse(cascade.isMerge());
		assertFalse(cascade.isPersist());
		assertFalse(cascade.isRemove());
		assertFalse(cascade.isRefresh());

		assertEquals(null, ormManyToOneMapping.getJoinFetch().getValue());
	}
}

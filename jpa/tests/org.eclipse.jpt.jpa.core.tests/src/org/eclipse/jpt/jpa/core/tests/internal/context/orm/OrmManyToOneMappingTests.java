/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.Cascade;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToOneRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmManyToOneMappingTests extends ContextModelTestCase
{
	public OrmManyToOneMappingTests(String name) {
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
	
	private ICompilationUnit createTestEntityManyToOneMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.MANY_TO_ONE, JPA.JOIN_COLUMN, JPA.FETCH_TYPE, JPA.CASCADE_TYPE);
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
				sb.append("    private Address address;").append(CR);
				sb.append(CR);
				sb.append("    @Id");				
			}
		});
	}	
	
	private void createTestEntityWithManyToOneMapping() throws Exception {
		createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.MANY_TO_ONE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToOne").append(CR);
			}
		});
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		for (OrmPersistentAttribute each : ormPersistentType.getAttributes()) {
			each.addToXml();
		}
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
	
	public void testUpdateName() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmManyToOneMapping ormManyToOneMapping = (OrmManyToOneMapping) ormPersistentAttribute.getMapping();
		XmlManyToOne manyToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertEquals("id", ormManyToOneMapping.getName());
		assertEquals("id", manyToOne.getName());
				
		//set name in the resource model, verify context model updated
		manyToOne.setName("newName");
		assertEquals("newName", ormManyToOneMapping.getName());
		assertEquals("newName", manyToOne.getName());
	
		//set name to null in the resource model
		manyToOne.setName(null);
		assertNull(ormManyToOneMapping.getName());
		assertNull(manyToOne.getName());
	}
	
	public void testModifyName() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmManyToOneMapping ormManyToOneMapping = (OrmManyToOneMapping) ormPersistentAttribute.getMapping();
		XmlManyToOne manyToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertEquals("id", ormManyToOneMapping.getName());
		assertEquals("id", manyToOne.getName());
				
		//set name in the context model, verify resource model updated
		ormManyToOneMapping.setName("newName");
		assertEquals("newName", ormManyToOneMapping.getName());
		assertEquals("newName", manyToOne.getName());
	
		//set name to null in the context model
		ormManyToOneMapping.setName(null);
		assertNull(ormManyToOneMapping.getName());
		assertNull(manyToOne.getName());
	}
	
	public void testUpdateTargetEntity() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmManyToOneMapping ormManyToOneMapping = (OrmManyToOneMapping) ormPersistentAttribute.getMapping();
		XmlManyToOne manyToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertNull(ormManyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
				
		//set target entity in the resource model, verify context model updated
		manyToOne.setTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", ormManyToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", manyToOne.getTargetEntity());
	
		//set target entity to null in the resource model
		manyToOne.setTargetEntity(null);
		assertNull(ormManyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
	}
	
	public void testModifyTargetEntity() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmManyToOneMapping ormManyToOneMapping = (OrmManyToOneMapping) ormPersistentAttribute.getMapping();
		XmlManyToOne manyToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertNull(ormManyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
				
		//set target entity in the context model, verify resource model updated
		ormManyToOneMapping.setSpecifiedTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", ormManyToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", manyToOne.getTargetEntity());
	
		//set target entity to null in the context model
		ormManyToOneMapping.setSpecifiedTargetEntity(null);
		assertNull(ormManyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmManyToOneMapping ormManyToOneMapping = (OrmManyToOneMapping) ormPersistentAttribute.getMapping();
		XmlManyToOne manyToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertNull(ormManyToOneMapping.getSpecifiedFetch());
		assertNull(manyToOneResource.getFetch());
				
		//set fetch in the resource model, verify context model updated
		manyToOneResource.setFetch(org.eclipse.jpt.jpa.core.resource.orm.FetchType.EAGER);
		assertEquals(FetchType.EAGER, ormManyToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.FetchType.EAGER, manyToOneResource.getFetch());
	
		manyToOneResource.setFetch(org.eclipse.jpt.jpa.core.resource.orm.FetchType.LAZY);
		assertEquals(FetchType.LAZY, ormManyToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.FetchType.LAZY, manyToOneResource.getFetch());

		//set fetch to null in the resource model
		manyToOneResource.setFetch(null);
		assertNull(ormManyToOneMapping.getSpecifiedFetch());
		assertNull(manyToOneResource.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmManyToOneMapping ormManyToOneMapping = (OrmManyToOneMapping) ormPersistentAttribute.getMapping();
		XmlManyToOne manyToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertNull(ormManyToOneMapping.getSpecifiedFetch());
		assertNull(manyToOneResource.getFetch());
				
		//set fetch in the context model, verify resource model updated
		ormManyToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.FetchType.EAGER, manyToOneResource.getFetch());
		assertEquals(FetchType.EAGER, ormManyToOneMapping.getSpecifiedFetch());
	
		ormManyToOneMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.FetchType.LAZY, manyToOneResource.getFetch());
		assertEquals(FetchType.LAZY, ormManyToOneMapping.getSpecifiedFetch());

		//set fetch to null in the context model
		ormManyToOneMapping.setSpecifiedFetch(null);
		assertNull(manyToOneResource.getFetch());
		assertNull(ormManyToOneMapping.getSpecifiedFetch());
	}
	
	public void testUpdateSpecifiedOptional() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmManyToOneMapping ormManyToOneMapping = (OrmManyToOneMapping) ormPersistentAttribute.getMapping();
		XmlManyToOne manyToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertNull(ormManyToOneMapping.getSpecifiedOptional());
		assertNull(manyToOneResource.getOptional());
				
		//set optional in the resource model, verify context model updated
		manyToOneResource.setOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormManyToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.TRUE, manyToOneResource.getOptional());
	
		manyToOneResource.setOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormManyToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.FALSE, manyToOneResource.getOptional());

		//set optional to null in the resource model
		manyToOneResource.setOptional(null);
		assertNull(ormManyToOneMapping.getSpecifiedOptional());
		assertNull(manyToOneResource.getOptional());
	}
	
	public void testModifySpecifiedOptional() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmManyToOneMapping ormManyToOneMapping = (OrmManyToOneMapping) ormPersistentAttribute.getMapping();
		XmlManyToOne manyToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertNull(ormManyToOneMapping.getSpecifiedOptional());
		assertNull(manyToOneResource.getOptional());
				
		//set optional in the context model, verify resource model updated
		ormManyToOneMapping.setSpecifiedOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, manyToOneResource.getOptional());
		assertEquals(Boolean.TRUE, ormManyToOneMapping.getSpecifiedOptional());
	
		ormManyToOneMapping.setSpecifiedOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, manyToOneResource.getOptional());
		assertEquals(Boolean.FALSE, ormManyToOneMapping.getSpecifiedOptional());

		//set optional to null in the context model
		ormManyToOneMapping.setSpecifiedOptional(null);
		assertNull(manyToOneResource.getOptional());
		assertNull(ormManyToOneMapping.getSpecifiedOptional());
	}
	
	public void testAddSpecifiedJoinColumn() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmManyToOneMapping ormManyToOneMapping = (OrmManyToOneMapping) ormPersistentAttribute.getMapping();
		OrmJoinColumnRelationshipStrategy strategy = ormManyToOneMapping.getRelationship().getJoinColumnStrategy();
		XmlManyToOne manyToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		OrmSpecifiedJoinColumn joinColumn = strategy.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", manyToOneResource.getJoinColumns().get(0).getName());
		
		OrmSpecifiedJoinColumn joinColumn2 = strategy.addSpecifiedJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", manyToOneResource.getJoinColumns().get(0).getName());
		assertEquals("FOO", manyToOneResource.getJoinColumns().get(1).getName());
		
		OrmSpecifiedJoinColumn joinColumn3 = strategy.addSpecifiedJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", manyToOneResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", manyToOneResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", manyToOneResource.getJoinColumns().get(2).getName());
		
		ListIterator<OrmSpecifiedJoinColumn> joinColumns = strategy.getSpecifiedJoinColumns().iterator();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = strategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmManyToOneMapping ormManyToOneMapping = (OrmManyToOneMapping) ormPersistentAttribute.getMapping();
		OrmJoinColumnRelationshipStrategy strategy = ormManyToOneMapping.getRelationship().getJoinColumnStrategy();
		XmlManyToOne manyToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);

		strategy.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		strategy.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		strategy.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, manyToOneResource.getJoinColumns().size());
		
		strategy.removeSpecifiedJoinColumn(0);
		assertEquals(2, manyToOneResource.getJoinColumns().size());
		assertEquals("BAR", manyToOneResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", manyToOneResource.getJoinColumns().get(1).getName());

		strategy.removeSpecifiedJoinColumn(0);
		assertEquals(1, manyToOneResource.getJoinColumns().size());
		assertEquals("BAZ", manyToOneResource.getJoinColumns().get(0).getName());
		
		strategy.removeSpecifiedJoinColumn(0);
		assertEquals(0, manyToOneResource.getJoinColumns().size());
	}
	
	public void testModifyPredominantJoiningStrategy() throws Exception {
		createTestEntityWithManyToOneMapping();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("id");
		OrmManyToOneMapping contextMapping = (OrmManyToOneMapping) contextAttribute.getMapping();
		OrmManyToOneRelationship rel = contextMapping.getRelationship();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getXmlTypeMapping();
		XmlManyToOne resourceMapping = resourceEntity.getAttributes().getManyToOnes().get(0);
		
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(rel.strategyIsJoinColumn());
		
		rel.setStrategyToJoinColumn();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(rel.strategyIsJoinColumn());
	}
	
	public void testUpdatePredominantJoiningStrategy() throws Exception {
		createTestEntityWithManyToOneMapping();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("id");
		OrmManyToOneMapping contextMapping = (OrmManyToOneMapping) contextAttribute.getMapping();
		OrmManyToOneRelationship rel = contextMapping.getRelationship();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getXmlTypeMapping();
		XmlManyToOne resourceMapping = resourceEntity.getAttributes().getManyToOnes().get(0);
		
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(rel.strategyIsJoinColumn());
		
		resourceMapping.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		getJpaProject().synchronizeContextModel();
		assertFalse(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(rel.strategyIsJoinColumn());
		
		resourceMapping.getJoinColumns().clear();
		getJpaProject().synchronizeContextModel();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(rel.strategyIsJoinColumn());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmManyToOneMapping ormManyToOneMapping = (OrmManyToOneMapping) ormPersistentAttribute.getMapping();
		OrmJoinColumnRelationshipStrategy strategy = ormManyToOneMapping.getRelationship().getJoinColumnStrategy();
		XmlManyToOne manyToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);

		strategy.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		strategy.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		strategy.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, manyToOneResource.getJoinColumns().size());
		
		
		strategy.moveSpecifiedJoinColumn(2, 0);
		ListIterator<OrmSpecifiedJoinColumn> joinColumns = strategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", manyToOneResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", manyToOneResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", manyToOneResource.getJoinColumns().get(2).getName());


		strategy.moveSpecifiedJoinColumn(0, 1);
		joinColumns = strategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", manyToOneResource.getJoinColumns().get(0).getName());
		assertEquals("BAR", manyToOneResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", manyToOneResource.getJoinColumns().get(2).getName());
	}
	
	
	public void testManyToOneMappingNoUnderylingJavaAttribute() throws Exception {
		createTestEntityManyToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("address"), MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());
		
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		OrmManyToOneMapping ormManyToOneMapping = (OrmManyToOneMapping) ormPersistentAttribute.getMapping();
		ormManyToOneMapping.setName("foo");
		OrmJoinColumnRelationshipStrategy strategy = ormManyToOneMapping.getRelationship().getJoinColumnStrategy();
		
		assertEquals("foo", ormManyToOneMapping.getName());

		assertNull(ormManyToOneMapping.getSpecifiedFetch());
		assertNull(ormManyToOneMapping.getSpecifiedOptional());
		assertNull(ormManyToOneMapping.getSpecifiedTargetEntity());
		assertEquals(FetchType.EAGER, ormManyToOneMapping.getFetch());
		assertEquals(true, ormManyToOneMapping.isOptional());
		assertNull(ormManyToOneMapping.getTargetEntity());

		
		assertFalse(strategy.getSpecifiedJoinColumns().iterator().hasNext());
		//TODO default joinColumns
		//assertTrue(ormManyToOneMapping.defaultJoinColumns().hasNext());
	
	
		Cascade cascade = ormManyToOneMapping.getCascade();
		assertFalse(cascade.isAll());
		assertFalse(cascade.isMerge());
		assertFalse(cascade.isPersist());
		assertFalse(cascade.isRemove());
		assertFalse(cascade.isRefresh());
	}
	
	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityManyToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		
		ManyToOneMapping manyToOneMapping = (ManyToOneMapping) ormPersistentAttribute.getMapping();	
		assertEquals("address", manyToOneMapping.getName());
		assertEquals(FetchType.LAZY, manyToOneMapping.getSpecifiedFetch());
		assertEquals(Boolean.FALSE, manyToOneMapping.getSpecifiedOptional());
		assertEquals("Address", manyToOneMapping.getSpecifiedTargetEntity());

		SpecifiedJoinColumn joinColumn = manyToOneMapping.getRelationship().getJoinColumnStrategy().getSpecifiedJoinColumns().iterator().next();
		assertEquals("MY_COLUMN", joinColumn.getSpecifiedName());
		assertEquals("MY_REFERENCED_COLUMN", joinColumn.getSpecifiedReferencedColumnName());
		assertEquals(Boolean.TRUE, joinColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedUpdatable());
		assertEquals("COLUMN_DEFINITION", joinColumn.getColumnDefinition());
		assertEquals("MY_TABLE", joinColumn.getSpecifiedTableName());

		Cascade cascade = manyToOneMapping.getCascade();
		assertTrue(cascade.isAll());
		assertTrue(cascade.isMerge());
		assertTrue(cascade.isPersist());
		assertTrue(cascade.isRemove());
		assertTrue(cascade.isRefresh());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityManyToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("address");
		
		assertEquals(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
		assertTrue(ormPersistentAttribute.isVirtual());
		
		ormPersistentAttribute.addToXml(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		ormPersistentAttribute= ormPersistentType.getSpecifiedAttributes().iterator().next();
		
		OrmManyToOneMapping ormManyToOneMapping = (OrmManyToOneMapping) ormPersistentAttribute.getMapping();	
		assertEquals("address", ormManyToOneMapping.getName());
		assertEquals(FetchType.EAGER, ormManyToOneMapping.getDefaultFetch());
		assertEquals(true, ormManyToOneMapping.isDefaultOptional());
		//TODO hmm, is this correct?
		assertEquals("test.Address", ormManyToOneMapping.getDefaultTargetEntity());
		
		//TODO default join columns in xml one-to-one
//		XmlJoinColumn ormJoinColumn = ormManyToOneMapping.specifiedJoinColumns().next();
//		//TODO java default columns name in JavaSingleRelationshipMapping.JoinColumnOwner
//		//assertEquals("address", ormJoinColumn.getSpecifiedName());
//		//assertEquals("address", ormJoinColumn.getSpecifiedReferencedColumnName());
//		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedUnique());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedNullable());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedInsertable());
//		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedUpdatable());
//		assertNull(ormJoinColumn.getColumnDefinition());
//		assertEquals(TYPE_NAME, ormJoinColumn.getSpecifiedTable());

		Cascade cascade = ormManyToOneMapping.getCascade();
		assertFalse(cascade.isAll());
		assertFalse(cascade.isMerge());
		assertFalse(cascade.isPersist());
		assertFalse(cascade.isRemove());
		assertFalse(cascade.isRefresh());
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityManyToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("address"), MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());
		
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		OrmManyToOneMapping ormManyToOneMapping = (OrmManyToOneMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("address", ormManyToOneMapping.getName());
		assertNull(ormManyToOneMapping.getSpecifiedFetch());
		assertNull(ormManyToOneMapping.getSpecifiedOptional());
		assertNull(ormManyToOneMapping.getSpecifiedTargetEntity());
		assertEquals(FetchType.EAGER, ormManyToOneMapping.getFetch());
		assertEquals(true, ormManyToOneMapping.isOptional());
		assertEquals("test.Address", ormManyToOneMapping.getDefaultTargetEntity());
		
		assertFalse(ormManyToOneMapping.getRelationship().getJoinColumnStrategy().getSpecifiedJoinColumns().iterator().hasNext());
		
		//TODO default join columns for specified xmlManyToOne mapping
//		XmlJoinColumn ormJoinColumn = ormManyToOneMapping.defaultJoinColumns().next();
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

		Cascade cascade = ormManyToOneMapping.getCascade();
		assertFalse(cascade.isAll());
		assertFalse(cascade.isMerge());
		assertFalse(cascade.isPersist());
		assertFalse(cascade.isRemove());
		assertFalse(cascade.isRefresh());
	}
	
	//3 things tested above
	//1. virtual mapping metadata complete=false - defaults are taken from the java annotations
	//2. virtual mapping metadata complete=true - defaults are taken from java defaults,annotations ignored
	//3. specified mapping (metadata complete=true/false - defaults are taken from java annotations

}
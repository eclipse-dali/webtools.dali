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
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.Cascade;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.MappedByRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmPrimaryKeyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmOneToOneMappingTests extends ContextModelTestCase
{
	public OrmOneToOneMappingTests(String name) {
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
	
	private ICompilationUnit createTestEntityOneToOneMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ONE_TO_ONE, JPA.JOIN_COLUMN, JPA.FETCH_TYPE, JPA.CASCADE_TYPE);
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
				sb.append("    private Address address;").append(CR);
				sb.append(CR);
				sb.append("    @Id");				
			}
		});
	}	
	
	private void createTestEntityWithOneToOneMapping() throws Exception {
		createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ONE_TO_ONE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne").append(CR);
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
				sb.append("    private State state;").append(CR);
				sb.append(CR);
				sb.append("    private int zip;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
	}
	
	private ICompilationUnit createTestEntityWithValidOneToOneMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ONE_TO_ONE, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToOne").append(CR);				
				sb.append("    private Address address;").append(CR);
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
	
	public void testUpdateName() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertEquals("id", ormOneToOneMapping.getName());
		assertEquals("id", oneToOne.getName());
				
		//set name in the resource model, verify context model updated
		oneToOne.setName("newName");
		assertEquals("newName", ormOneToOneMapping.getName());
		assertEquals("newName", oneToOne.getName());
	
		//set name to null in the resource model
		oneToOne.setName(null);
		assertNull(ormOneToOneMapping.getName());
		assertNull(oneToOne.getName());
	}
	
	public void testModifyName() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertEquals("id", ormOneToOneMapping.getName());
		assertEquals("id", oneToOne.getName());
				
		//set name in the context model, verify resource model updated
		ormOneToOneMapping.setName("newName");
		assertEquals("newName", ormOneToOneMapping.getName());
		assertEquals("newName", oneToOne.getName());
	
		//set name to null in the context model
		ormOneToOneMapping.setName(null);
		assertNull(ormOneToOneMapping.getName());
		assertNull(oneToOne.getName());
	}
	
	public void testUpdateSpecifiedTargetEntity() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(ormOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOne.getTargetEntity());
				
		//set target entity in the resource model, verify context model updated
		oneToOne.setTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", ormOneToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", oneToOne.getTargetEntity());
	
		//set target entity to null in the resource model
		oneToOne.setTargetEntity(null);
		assertNull(ormOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOne.getTargetEntity());
	}
	
	public void testModifySpecifiedTargetEntity() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(ormOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOne.getTargetEntity());
				
		//set target entity in the context model, verify resource model updated
		ormOneToOneMapping.setSpecifiedTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", ormOneToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", oneToOne.getTargetEntity());
	
		//set target entity to null in the context model
		ormOneToOneMapping.setSpecifiedTargetEntity(null);
		assertNull(ormOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOne.getTargetEntity());
	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(ormOneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOneResource.getFetch());
				
		//set fetch in the resource model, verify context model updated
		oneToOneResource.setFetch(org.eclipse.jpt.jpa.core.resource.orm.FetchType.EAGER);
		assertEquals(FetchType.EAGER, ormOneToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.FetchType.EAGER, oneToOneResource.getFetch());
	
		oneToOneResource.setFetch(org.eclipse.jpt.jpa.core.resource.orm.FetchType.LAZY);
		assertEquals(FetchType.LAZY, ormOneToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.FetchType.LAZY, oneToOneResource.getFetch());

		//set fetch to null in the resource model
		oneToOneResource.setFetch(null);
		assertNull(ormOneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOneResource.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(ormOneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOneResource.getFetch());
				
		//set fetch in the context model, verify resource model updated
		ormOneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.FetchType.EAGER, oneToOneResource.getFetch());
		assertEquals(FetchType.EAGER, ormOneToOneMapping.getSpecifiedFetch());
	
		ormOneToOneMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.FetchType.LAZY, oneToOneResource.getFetch());
		assertEquals(FetchType.LAZY, ormOneToOneMapping.getSpecifiedFetch());

		//set fetch to null in the context model
		ormOneToOneMapping.setSpecifiedFetch(null);
		assertNull(oneToOneResource.getFetch());
		assertNull(ormOneToOneMapping.getSpecifiedFetch());
	}

	public void testUpdateMappedBy() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		MappedByRelationshipStrategy strategy = ormOneToOneMapping.getRelationship().getMappedByStrategy();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(strategy.getMappedByAttribute());
		assertNull(oneToOne.getMappedBy());
				
		//set mappedBy in the resource model, verify context model updated
		oneToOne.setMappedBy("newMappedBy");
		assertEquals("newMappedBy", strategy.getMappedByAttribute());
		assertEquals("newMappedBy", oneToOne.getMappedBy());
	
		//set mappedBy to null in the resource model
		oneToOne.setMappedBy(null);
		assertNull(strategy.getMappedByAttribute());
		assertNull(oneToOne.getMappedBy());
	}
	
	public void testModifyMappedBy() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		MappedByRelationshipStrategy strategy = ormOneToOneMapping.getRelationship().getMappedByStrategy();
		XmlOneToOne oneToOne = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(strategy.getMappedByAttribute());
		assertNull(oneToOne.getMappedBy());
				
		//set mappedBy in the context model, verify resource model updated
		strategy.setMappedByAttribute("newMappedBy");
		assertEquals("newMappedBy", strategy.getMappedByAttribute());
		assertEquals("newMappedBy", oneToOne.getMappedBy());
	
		//set mappedBy to null in the context model
		strategy.setMappedByAttribute(null);
		assertNull(strategy.getMappedByAttribute());
		assertNull(oneToOne.getMappedBy());
	}
	
	public void testModifyPredominantJoiningStrategy() throws Exception {
		createTestEntityWithOneToOneMapping();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("id");
		OrmOneToOneMapping contextMapping = (OrmOneToOneMapping) contextAttribute.getMapping();
		OrmOneToOneRelationship rel = contextMapping.getRelationship();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getXmlTypeMapping();
		XmlOneToOne resourceMapping = resourceEntity.getAttributes().getOneToOnes().get(0);
		
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNull(resourceMapping.getMappedBy());
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		
		rel.setStrategyToMappedBy();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNotNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertTrue(rel.strategyIsMappedBy());
		
		rel.setStrategyToPrimaryKeyJoinColumn();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertFalse(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		
		rel.setStrategyToJoinColumn();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNull(resourceMapping.getMappedBy());
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());	
	}
	
	public void testUpdatePredominantJoiningStrategy() throws Exception {
		createTestEntityWithOneToOneMapping();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("id");
		OrmOneToOneMapping contextMapping = (OrmOneToOneMapping) contextAttribute.getMapping();
		OrmOneToOneRelationship rel = contextMapping.getRelationship();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getXmlTypeMapping();
		XmlOneToOne resourceMapping = resourceEntity.getAttributes().getOneToOnes().get(0);
		
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNull(resourceMapping.getMappedBy());
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		
		resourceMapping.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn());
		getJpaProject().synchronizeContextModel();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertFalse(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		
		resourceMapping.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		getJpaProject().synchronizeContextModel();
		assertFalse(resourceMapping.getJoinColumns().isEmpty());
		assertFalse(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
		
		resourceMapping.setMappedBy("foo");
		getJpaProject().synchronizeContextModel();
		assertFalse(resourceMapping.getJoinColumns().isEmpty());
		assertFalse(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNotNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertTrue(rel.strategyIsMappedBy());
		
		resourceMapping.getPrimaryKeyJoinColumns().clear();
		getJpaProject().synchronizeContextModel();
		assertFalse(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNotNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertTrue(rel.strategyIsMappedBy());
		
		resourceMapping.getJoinColumns().clear();
		getJpaProject().synchronizeContextModel();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNotNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertTrue(rel.strategyIsMappedBy());
		
		resourceMapping.setMappedBy(null);
		getJpaProject().synchronizeContextModel();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNull(resourceMapping.getMappedBy());
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsPrimaryKeyJoinColumn());
		assertFalse(rel.strategyIsMappedBy());
	}
	
	public void testUpdateSpecifiedOptional() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(ormOneToOneMapping.getSpecifiedOptional());
		assertNull(oneToOneResource.getOptional());
				
		//set optional in the resource model, verify context model updated
		oneToOneResource.setOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormOneToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.TRUE, oneToOneResource.getOptional());
	
		oneToOneResource.setOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormOneToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.FALSE, oneToOneResource.getOptional());

		//set optional to null in the resource model
		oneToOneResource.setOptional(null);
		assertNull(ormOneToOneMapping.getSpecifiedOptional());
		assertNull(oneToOneResource.getOptional());
	}
	
	public void testModifySpecifiedOptional() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(ormOneToOneMapping.getSpecifiedOptional());
		assertNull(oneToOneResource.getOptional());
				
		//set optional in the context model, verify resource model updated
		ormOneToOneMapping.setSpecifiedOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, oneToOneResource.getOptional());
		assertEquals(Boolean.TRUE, ormOneToOneMapping.getSpecifiedOptional());
	
		ormOneToOneMapping.setSpecifiedOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, oneToOneResource.getOptional());
		assertEquals(Boolean.FALSE, ormOneToOneMapping.getSpecifiedOptional());

		//set optional to null in the context model
		ormOneToOneMapping.setSpecifiedOptional(null);
		assertNull(oneToOneResource.getOptional());
		assertNull(ormOneToOneMapping.getSpecifiedOptional());
	}
	
	public void testAddSpecifiedJoinColumn() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		OrmJoinColumnRelationshipStrategy strategy = ormOneToOneMapping.getRelationship().getJoinColumnStrategy();
		XmlOneToOne oneToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		OrmJoinColumn joinColumn = strategy.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", oneToOneResource.getJoinColumns().get(0).getName());
		
		OrmJoinColumn joinColumn2 = strategy.addSpecifiedJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", oneToOneResource.getJoinColumns().get(0).getName());
		assertEquals("FOO", oneToOneResource.getJoinColumns().get(1).getName());
		
		OrmJoinColumn joinColumn3 = strategy.addSpecifiedJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", oneToOneResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", oneToOneResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", oneToOneResource.getJoinColumns().get(2).getName());
		
		ListIterator<OrmJoinColumn> joinColumns = strategy.getSpecifiedJoinColumns().iterator();
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
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		OrmJoinColumnRelationshipStrategy strategy = ormOneToOneMapping.getRelationship().getJoinColumnStrategy();
		XmlOneToOne oneToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);

		strategy.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		strategy.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		strategy.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, oneToOneResource.getJoinColumns().size());
		
		strategy.removeSpecifiedJoinColumn(0);
		assertEquals(2, oneToOneResource.getJoinColumns().size());
		assertEquals("BAR", oneToOneResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", oneToOneResource.getJoinColumns().get(1).getName());

		strategy.removeSpecifiedJoinColumn(0);
		assertEquals(1, oneToOneResource.getJoinColumns().size());
		assertEquals("BAZ", oneToOneResource.getJoinColumns().get(0).getName());
		
		strategy.removeSpecifiedJoinColumn(0);
		assertEquals(0, oneToOneResource.getJoinColumns().size());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		OrmJoinColumnRelationshipStrategy strategy = ormOneToOneMapping.getRelationship().getJoinColumnStrategy();
		XmlOneToOne oneToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);

		strategy.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		strategy.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		strategy.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, oneToOneResource.getJoinColumns().size());
		
		
		strategy.moveSpecifiedJoinColumn(2, 0);
		ListIterator<OrmJoinColumn> joinColumns = strategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", oneToOneResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", oneToOneResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", oneToOneResource.getJoinColumns().get(2).getName());


		strategy.moveSpecifiedJoinColumn(0, 1);
		joinColumns = strategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", oneToOneResource.getJoinColumns().get(0).getName());
		assertEquals("BAR", oneToOneResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", oneToOneResource.getJoinColumns().get(2).getName());
	}
	
	public void testOneToOneMappingNoUnderylingJavaAttribute() throws Exception {
		createTestEntityOneToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("address"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());
		
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		ormOneToOneMapping.setName("foo");
		assertEquals("foo", ormOneToOneMapping.getName());

		assertNull(ormOneToOneMapping.getSpecifiedFetch());
		assertNull(ormOneToOneMapping.getSpecifiedOptional());
		assertNull(ormOneToOneMapping.getSpecifiedTargetEntity());
		assertEquals(FetchType.EAGER, ormOneToOneMapping.getFetch());
		assertEquals(true, ormOneToOneMapping.isOptional());
		assertNull(ormOneToOneMapping.getTargetEntity());

		
		assertTrue(ormOneToOneMapping.getRelationship().getJoinColumnStrategy().getJoinColumnsSize() > 0);
		//TODO default joinColumns
		//assertTrue(ormOneToOneMapping.defaultJoinColumns().hasNext());
	
	
		Cascade cascade = ormOneToOneMapping.getCascade();
		assertFalse(cascade.isAll());
		assertFalse(cascade.isMerge());
		assertFalse(cascade.isPersist());
		assertFalse(cascade.isRemove());
		assertFalse(cascade.isRefresh());
	}
	
	
	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityOneToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		
		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();	
		assertEquals("address", oneToOneMapping.getName());
		assertEquals(FetchType.LAZY, oneToOneMapping.getSpecifiedFetch());
		assertEquals(Boolean.FALSE, oneToOneMapping.getSpecifiedOptional());
		assertEquals("Address", oneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOneMapping.getRelationship().
			getMappedByStrategy().getMappedByAttribute());

		JoinColumn joinColumn = 
			oneToOneMapping.getRelationship().getJoinColumnStrategy().getSpecifiedJoinColumns().iterator().next();
		assertEquals("MY_COLUMN", joinColumn.getSpecifiedName());
		assertEquals("MY_REFERENCED_COLUMN", joinColumn.getSpecifiedReferencedColumnName());
		assertEquals(Boolean.TRUE, joinColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedUpdatable());
		assertEquals("COLUMN_DEFINITION", joinColumn.getColumnDefinition());
		assertEquals("MY_TABLE", joinColumn.getSpecifiedTableName());

		Cascade cascade = oneToOneMapping.getCascade();
		assertTrue(cascade.isAll());
		assertTrue(cascade.isMerge());
		assertTrue(cascade.isPersist());
		assertTrue(cascade.isRemove());
		assertTrue(cascade.isRefresh());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityOneToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("address");
		
		assertEquals(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
		assertTrue(ormPersistentAttribute.isVirtual());
			
		ormPersistentAttribute.addToXml(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		ormPersistentAttribute= ormPersistentType.getSpecifiedAttributes().iterator().next();

		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();	
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
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityOneToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("address"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());
		
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("address", ormOneToOneMapping.getName());
		assertNull(ormOneToOneMapping.getSpecifiedFetch());
		assertNull(ormOneToOneMapping.getSpecifiedOptional());
		assertNull(ormOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(ormOneToOneMapping.getRelationship().getMappedByStrategy().getMappedByAttribute());
		assertEquals(FetchType.EAGER, ormOneToOneMapping.getFetch());
		assertEquals(true, ormOneToOneMapping.isOptional());
		//TODO default target entity in xml
		//assertEquals("test.Address", ormOneToOneMapping.getDefaultTargetEntity());
		
		assertTrue(ormOneToOneMapping.getRelationship().getJoinColumnStrategy().getJoinColumnsSize() > 0);
		
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
	}
	
	
	public void testOneToOneMorphToIdMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.getRelationship().getJoinColumnStrategy().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToOneMorphToVersionMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.getRelationship().getJoinColumnStrategy().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToOneMorphToTransientMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);

		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.getRelationship().getJoinColumnStrategy().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToOneMorphToEmbeddedMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);

		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.getRelationship().getJoinColumnStrategy().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToOneMorphToEmbeddedIdMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.getRelationship().getJoinColumnStrategy().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToOneMorphToManyToManyMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.getRelationship().getJoinColumnStrategy().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((ManyToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((ManyToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertEquals("mappedBy", ((ManyToManyMapping) ormPersistentAttribute.getMapping()).getRelationship().getMappedByStrategy().getMappedByAttribute());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isRemove());
	}
	
	public void testOneToOneMorphToOneToManyMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.getRelationship().getJoinColumnStrategy().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((OneToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((OneToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertEquals("mappedBy", ((OneToManyMapping) ormPersistentAttribute.getMapping()).getRelationship().getMappedByStrategy().getMappedByAttribute());
		assertTrue(((OneToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((OneToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((OneToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((OneToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((OneToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isRemove());
	}
	
	public void testOneToOneMorphToManyToOneMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.getRelationship().getJoinColumnStrategy().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((ManyToOneMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((ManyToOneMapping) ormPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isRemove());
		
		joinColumn = ((ManyToOneMapping) ormPersistentAttribute.getMapping()).getRelationship().getJoinColumnStrategy().getSpecifiedJoinColumns().iterator().next();
		assertEquals("name", joinColumn.getName());		
		assertEquals("referenceName", joinColumn.getReferencedColumnName());		
	}
	
	public void testOneToOneMorphToBasicMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		
		OneToOneMapping oneToOneMapping = (OneToOneMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToOneMapping.isDefault());
		oneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToOneMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToOneMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("mappedBy");
		oneToOneMapping.getCascade().setAll(true);
		oneToOneMapping.getCascade().setMerge(true);
		oneToOneMapping.getCascade().setPersist(true);
		oneToOneMapping.getCascade().setRefresh(true);
		oneToOneMapping.getCascade().setRemove(true);
		JoinColumn joinColumn = oneToOneMapping.getRelationship().getJoinColumnStrategy().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		assertFalse(oneToOneMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
//TODO	assertEquals(FetchType.EAGER, ((IBasicMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
	
	
	
	
	
	public void testAddPrimaryKeyJoinColumn() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		OrmPrimaryKeyJoinColumnRelationshipStrategy strategy = ormOneToOneMapping.getRelationship().getPrimaryKeyJoinColumnStrategy();
		XmlOneToOne oneToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		OrmPrimaryKeyJoinColumn joinColumn = strategy.addPrimaryKeyJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", oneToOneResource.getPrimaryKeyJoinColumns().get(0).getName());
		
		OrmPrimaryKeyJoinColumn joinColumn2 = strategy.addPrimaryKeyJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", oneToOneResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("FOO", oneToOneResource.getPrimaryKeyJoinColumns().get(1).getName());
		
		OrmPrimaryKeyJoinColumn joinColumn3 = strategy.addPrimaryKeyJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", oneToOneResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", oneToOneResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", oneToOneResource.getPrimaryKeyJoinColumns().get(2).getName());
		
		ListIterator<OrmPrimaryKeyJoinColumn> joinColumns = strategy.getPrimaryKeyJoinColumns().iterator();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = strategy.getPrimaryKeyJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemovePrimaryKeyJoinColumn() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		OrmPrimaryKeyJoinColumnRelationshipStrategy strategy = ormOneToOneMapping.getRelationship().getPrimaryKeyJoinColumnStrategy();
		XmlOneToOne oneToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);

		strategy.addPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		strategy.addPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		strategy.addPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, oneToOneResource.getPrimaryKeyJoinColumns().size());
		
		strategy.removePrimaryKeyJoinColumn(0);
		assertEquals(2, oneToOneResource.getPrimaryKeyJoinColumns().size());
		assertEquals("BAR", oneToOneResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", oneToOneResource.getPrimaryKeyJoinColumns().get(1).getName());

		strategy.removePrimaryKeyJoinColumn(0);
		assertEquals(1, oneToOneResource.getPrimaryKeyJoinColumns().size());
		assertEquals("BAZ", oneToOneResource.getPrimaryKeyJoinColumns().get(0).getName());
		
		strategy.removePrimaryKeyJoinColumn(0);
		assertEquals(0, oneToOneResource.getPrimaryKeyJoinColumns().size());
	}
	
	public void testMovePrimaryKeyJoinColumn() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		OrmOneToOneMapping ormOneToOneMapping = (OrmOneToOneMapping) ormPersistentAttribute.getMapping();
		OrmPrimaryKeyJoinColumnRelationshipStrategy strategy = ormOneToOneMapping.getRelationship().getPrimaryKeyJoinColumnStrategy();
		XmlOneToOne oneToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);

		strategy.addPrimaryKeyJoinColumn(0).setSpecifiedName("FOO");
		strategy.addPrimaryKeyJoinColumn(1).setSpecifiedName("BAR");
		strategy.addPrimaryKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, oneToOneResource.getPrimaryKeyJoinColumns().size());
		
		
		strategy.movePrimaryKeyJoinColumn(2, 0);
		ListIterator<OrmPrimaryKeyJoinColumn> joinColumns = strategy.getPrimaryKeyJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", oneToOneResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", oneToOneResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", oneToOneResource.getPrimaryKeyJoinColumns().get(2).getName());


		strategy.movePrimaryKeyJoinColumn(0, 1);
		joinColumns = strategy.getPrimaryKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", oneToOneResource.getPrimaryKeyJoinColumns().get(0).getName());
		assertEquals("BAR", oneToOneResource.getPrimaryKeyJoinColumns().get(1).getName());
		assertEquals("FOO", oneToOneResource.getPrimaryKeyJoinColumns().get(2).getName());
	}

	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("address"), MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);

		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = ormPersistentType.getAttributes().iterator().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			oneToOneMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		oneToOneMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			oneToOneMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertFalse(attributeNames.hasNext());
		
		oneToOneMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			oneToOneMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		AttributeMapping stateFooMapping = oneToOneMapping.getResolvedTargetEntity().resolveAttributeMapping("state.foo");
		assertNull(stateFooMapping);
	}
}
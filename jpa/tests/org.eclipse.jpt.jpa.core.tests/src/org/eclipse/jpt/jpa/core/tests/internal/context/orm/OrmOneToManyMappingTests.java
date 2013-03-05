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
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.MappedByRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToManyRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmOneToManyMappingTests extends ContextModelTestCase
{
	public OrmOneToManyMappingTests(String name) {
		super(name);
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
	
	private void createTestEntityWithOneToManyMapping() throws Exception {
		createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ONE_TO_MANY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToMany").append(CR);
			}
		});
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		for (OrmPersistentAttribute each :ormPersistentType.getAttributes()) {
			each.addToXml();
		}
	}
	
	private ICompilationUnit createTestEntityWithValidOneToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany").append(CR);				
				sb.append("    private java.util.Collection<Address> addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithValidMapOneToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID);
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
				sb.append("    private String name;").append(CR);
				sb.append(CR);
				sb.append("    private String abbr;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "State.java", sourceWriter);
	}	

	private ICompilationUnit createTestEntityOneToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.FETCH_TYPE, JPA.CASCADE_TYPE, JPA.ORDER_BY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}

			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany(fetch=FetchType.EAGER, targetEntity=Address.class, cascade={CascadeType.ALL, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})");
				sb.append(CR);
				sb.append("    @OrderBy(\"city\"");
				sb.append(CR);
				sb.append("    private java.util.Collection<Address> address;").append(CR);
				sb.append(CR);
				sb.append("    @Id");				
			}
		});
	}	

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	public void testUpdateName() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertEquals("id", ormOneToManyMapping.getName());
		assertEquals("id", oneToMany.getName());
				
		//set name in the resource model, verify context model updated
		oneToMany.setName("newName");
		assertEquals("newName", ormOneToManyMapping.getName());
		assertEquals("newName", oneToMany.getName());
	
		//set name to null in the resource model
		oneToMany.setName(null);
		assertNull(ormOneToManyMapping.getName());
		assertNull(oneToMany.getName());
	}
	
	public void testModifyName() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertEquals("id", ormOneToManyMapping.getName());
		assertEquals("id", oneToMany.getName());
				
		//set name in the context model, verify resource model updated
		ormOneToManyMapping.setName("newName");
		assertEquals("newName", ormOneToManyMapping.getName());
		assertEquals("newName", oneToMany.getName());
	
		//set name to null in the context model
		ormOneToManyMapping.setName(null);
		assertNull(ormOneToManyMapping.getName());
		assertNull(oneToMany.getName());
	}
	
	public void testUpdateTargetEntity() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getSpecifiedTargetEntity());
		assertNull(oneToMany.getTargetEntity());
				
		//set target entity in the resource model, verify context model updated
		oneToMany.setTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", ormOneToManyMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", oneToMany.getTargetEntity());
	
		//set target entity to null in the resource model
		oneToMany.setTargetEntity(null);
		assertNull(ormOneToManyMapping.getSpecifiedTargetEntity());
		assertNull(oneToMany.getTargetEntity());
	}
	
	public void testModifyTargetEntity() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getSpecifiedTargetEntity());
		assertNull(oneToMany.getTargetEntity());
				
		//set target entity in the context model, verify resource model updated
		ormOneToManyMapping.setSpecifiedTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", ormOneToManyMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", oneToMany.getTargetEntity());
	
		//set target entity to null in the context model
		ormOneToManyMapping.setSpecifiedTargetEntity(null);
		assertNull(ormOneToManyMapping.getSpecifiedTargetEntity());
		assertNull(oneToMany.getTargetEntity());
	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getSpecifiedFetch());
		assertNull(oneToManyResource.getFetch());
				
		//set fetch in the resource model, verify context model updated
		oneToManyResource.setFetch(org.eclipse.jpt.jpa.core.resource.orm.FetchType.EAGER);
		assertEquals(FetchType.EAGER, ormOneToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.FetchType.EAGER, oneToManyResource.getFetch());
	
		oneToManyResource.setFetch(org.eclipse.jpt.jpa.core.resource.orm.FetchType.LAZY);
		assertEquals(FetchType.LAZY, ormOneToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.FetchType.LAZY, oneToManyResource.getFetch());

		//set fetch to null in the resource model
		oneToManyResource.setFetch(null);
		assertNull(ormOneToManyMapping.getSpecifiedFetch());
		assertNull(oneToManyResource.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getSpecifiedFetch());
		assertNull(oneToManyResource.getFetch());
				
		//set fetch in the context model, verify resource model updated
		ormOneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.FetchType.EAGER, oneToManyResource.getFetch());
		assertEquals(FetchType.EAGER, ormOneToManyMapping.getSpecifiedFetch());
	
		ormOneToManyMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.FetchType.LAZY, oneToManyResource.getFetch());
		assertEquals(FetchType.LAZY, ormOneToManyMapping.getSpecifiedFetch());

		//set fetch to null in the context model
		ormOneToManyMapping.setSpecifiedFetch(null);
		assertNull(oneToManyResource.getFetch());
		assertNull(ormOneToManyMapping.getSpecifiedFetch());
	}
	
	public void testUpdateMappedBy() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		MappedByRelationshipStrategy strategy = ormOneToManyMapping.getRelationship().getMappedByStrategy();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(strategy.getMappedByAttribute());
		assertNull(oneToMany.getMappedBy());
				
		//set mappedBy in the resource model, verify context model updated
		oneToMany.setMappedBy("newMappedBy");
		assertEquals("newMappedBy", strategy.getMappedByAttribute());
		assertEquals("newMappedBy", oneToMany.getMappedBy());
	
		//setmappedBy to null in the resource model
		oneToMany.setMappedBy(null);
		assertNull(strategy.getMappedByAttribute());
		assertNull(oneToMany.getMappedBy());
	}
	
	public void testModifyMappedBy() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		MappedByRelationshipStrategy strategy = ormOneToManyMapping.getRelationship().getMappedByStrategy();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(strategy.getMappedByAttribute());
		assertNull(oneToMany.getMappedBy());
				
		//set mappedBy in the context model, verify resource model updated
		strategy.setMappedByAttribute("newMappedBy");
		assertEquals("newMappedBy", strategy.getMappedByAttribute());
		assertEquals("newMappedBy", oneToMany.getMappedBy());
	
		//set mappedBy to null in the context model
		strategy.setMappedByAttribute(null);
		assertNull(strategy.getMappedByAttribute());
		assertNull(oneToMany.getMappedBy());
	}
	
	public void testModifyPredominantJoiningStrategy() throws Exception {
		createTestEntityWithOneToManyMapping();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("id");
		OrmOneToManyMapping contextMapping = (OrmOneToManyMapping) contextAttribute.getMapping();
		OrmOneToManyRelationship rel = contextMapping.getRelationship();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getXmlTypeMapping();
		XmlOneToMany resourceMapping = resourceEntity.getAttributes().getOneToManys().get(0);
		
		assertNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertTrue(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());
		
		rel.setStrategyToMappedBy();
		assertNull(resourceMapping.getJoinTable());
		assertNotNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinTable());
		assertTrue(rel.strategyIsMappedBy());
		
		rel.setStrategyToJoinTable();
		assertNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertTrue(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());
	}
	
	public void testUpdatePredominantJoiningStrategy() throws Exception {
		createTestEntityWithOneToManyMapping();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("id");
		OrmOneToManyMapping contextMapping = (OrmOneToManyMapping) contextAttribute.getMapping();
		OrmOneToManyRelationship rel = contextMapping.getRelationship();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getXmlTypeMapping();
		XmlOneToMany resourceMapping = resourceEntity.getAttributes().getOneToManys().get(0);
		
		assertNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertTrue(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());
		
		resourceMapping.setMappedBy("foo");
		getJpaProject().synchronizeContextModel();
		assertNull(resourceMapping.getJoinTable());
		assertNotNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinTable());
		assertTrue(rel.strategyIsMappedBy());
		
		resourceMapping.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceMapping.getJoinTable());
		assertNotNull(resourceMapping.getMappedBy());
		assertFalse(rel.strategyIsJoinTable());
		assertTrue(rel.strategyIsMappedBy());
		
		resourceMapping.setMappedBy(null);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertTrue(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());
		
		resourceMapping.setJoinTable(null);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceMapping.getJoinTable());
		assertNull(resourceMapping.getMappedBy());
		assertTrue(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());
	}
	
	public void testUpdateMapKey() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getSpecifiedMapKey());
		assertNull(oneToMany.getMapKey());
		
		//set mapKey in the resource model, verify context model does not change
		oneToMany.setMapKey(OrmFactory.eINSTANCE.createMapKey());
		assertNull(ormOneToManyMapping.getSpecifiedMapKey());
		assertNotNull(oneToMany.getMapKey());
				
		//set mapKey name in the resource model, verify context model updated
		oneToMany.getMapKey().setName("myMapKey");
		assertEquals("myMapKey", ormOneToManyMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", oneToMany.getMapKey().getName());
		
		//set mapKey name to null in the resource model
		oneToMany.getMapKey().setName(null);
		assertNull(ormOneToManyMapping.getSpecifiedMapKey());
		assertNull(oneToMany.getMapKey().getName());
		
		oneToMany.getMapKey().setName("myMapKey");
		oneToMany.setMapKey(null);
		assertNull(ormOneToManyMapping.getSpecifiedMapKey());
		assertNull(oneToMany.getMapKey());
	}
	
	public void testUpdateVirtualMapKey() throws Exception {
		createTestEntityWithValidMapOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");

		OneToManyMapping virtualOneToManyMapping = (OneToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaOneToManyMapping javaOneToManyMapping = (JavaOneToManyMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		assertNull(virtualOneToManyMapping.getSpecifiedMapKey());
		assertNull(virtualOneToManyMapping.getMapKey());
		assertFalse(virtualOneToManyMapping.isPkMapKey());
		assertFalse(virtualOneToManyMapping.isCustomMapKey());
		assertTrue(virtualOneToManyMapping.isNoMapKey());
		
		//set pk mapKey in the java, verify virtual orm mapping updates
		javaOneToManyMapping.setPkMapKey(true);
		assertEquals("id", virtualOneToManyMapping.getMapKey());
		assertTrue(virtualOneToManyMapping.isPkMapKey());
		assertFalse(virtualOneToManyMapping.isCustomMapKey());
		assertFalse(virtualOneToManyMapping.isNoMapKey());
		
		
		//set custom specified mapKey in the java, verify virtual orm mapping updates
		javaOneToManyMapping.setCustomMapKey(true);
		javaOneToManyMapping.setSpecifiedMapKey("city");
		assertEquals("city", virtualOneToManyMapping.getSpecifiedMapKey());
		assertEquals("city", virtualOneToManyMapping.getMapKey());
		assertFalse(virtualOneToManyMapping.isPkMapKey());
		assertTrue(virtualOneToManyMapping.isCustomMapKey());
		assertFalse(virtualOneToManyMapping.isNoMapKey());
	}
	
	public void testModifyMapKey() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getSpecifiedMapKey());
		assertNull(oneToMany.getMapKey());
					
		//set mapKey  in the context model, verify resource model updated
		ormOneToManyMapping.setSpecifiedMapKey("myMapKey");
		assertEquals("myMapKey", ormOneToManyMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", oneToMany.getMapKey().getName());
	
		//set mapKey to null in the context model
		ormOneToManyMapping.setNoMapKey(true);
		assertNull(ormOneToManyMapping.getSpecifiedMapKey());
		assertNull(oneToMany.getMapKey());
	}

	public void testUpdateOrderBy() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getOrderable().getSpecifiedOrderBy());
		assertNull(oneToMany.getOrderBy());
				
		//set orderBy in the resource model, verify context model updated
		oneToMany.setOrderBy("newOrderBy");
		assertEquals("newOrderBy", ormOneToManyMapping.getOrderable().getSpecifiedOrderBy());
		assertEquals("newOrderBy", oneToMany.getOrderBy());
	
		//set orderBy to null in the resource model
		oneToMany.setOrderBy(null);
		assertNull(ormOneToManyMapping.getOrderable().getSpecifiedOrderBy());
		assertNull(oneToMany.getOrderBy());
	}
	
	public void testModifyOrderBy() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getOrderable().getSpecifiedOrderBy());
		assertNull(oneToMany.getOrderBy());
				
		//set mappedBy in the context model, verify resource model updated
		ormOneToManyMapping.getOrderable().setSpecifiedOrderBy("newOrderBy");
		assertEquals("newOrderBy", ormOneToManyMapping.getOrderable().getSpecifiedOrderBy());
		assertEquals("newOrderBy", oneToMany.getOrderBy());
	
		//set mappedBy to null in the context model
		ormOneToManyMapping.getOrderable().setSpecifiedOrderBy(null);
		assertNull(ormOneToManyMapping.getOrderable().getSpecifiedOrderBy());
		assertNull(oneToMany.getOrderBy());
	}
	
	public void testIsNoOrdering() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		
		assertTrue(ormOneToManyMapping.getOrderable().isNoOrdering());

		ormOneToManyMapping.getOrderable().setSpecifiedOrderBy("foo");
		assertFalse(ormOneToManyMapping.getOrderable().isNoOrdering());
		
		ormOneToManyMapping.getOrderable().setSpecifiedOrderBy(null);
		assertTrue(ormOneToManyMapping.getOrderable().isNoOrdering());
	}
	
	public void testSetNoOrdering() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		
		assertTrue(ormOneToManyMapping.getOrderable().isNoOrdering());

		ormOneToManyMapping.getOrderable().setSpecifiedOrderBy("foo");
		assertFalse(ormOneToManyMapping.getOrderable().isNoOrdering());
		
		ormOneToManyMapping.getOrderable().setNoOrdering(true);
		assertTrue(ormOneToManyMapping.getOrderable().isNoOrdering());
		assertNull(ormOneToManyMapping.getOrderable().getSpecifiedOrderBy());
	}
//TODO
//	public boolean isOrderByPk() {
//		return "".equals(getOrderBy());
//	}
//
//	public void setOrderByPk() {
//		setOrderBy("");
//	}

	public void testIsCustomOrdering() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		
		assertFalse(ormOneToManyMapping.getOrderable().isCustomOrdering());

		ormOneToManyMapping.getOrderable().setSpecifiedOrderBy("foo");
		assertTrue(ormOneToManyMapping.getOrderable().isCustomOrdering());
		
		ormOneToManyMapping.getOrderable().setSpecifiedOrderBy(null);
		assertFalse(ormOneToManyMapping.getOrderable().isCustomOrdering());
	}
	
	public void testOneToManyMorphToIdMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("customOrder");
		oneToManyMapping.setSpecifiedMapKey("mapKey");
		oneToManyMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("mappedBy");
		assertFalse(oneToManyMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToVersionMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("customOrder");
		oneToManyMapping.setSpecifiedMapKey("mapKey");
		oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		SpecifiedJoinColumn joinColumn = oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		SpecifiedJoinColumn inverseJoinColumn = oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToTransientMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("customOrder");
		oneToManyMapping.setSpecifiedMapKey("mapKey");
		oneToManyMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("mappedBy");
		assertFalse(oneToManyMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToEmbeddedMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("customOrder");
		oneToManyMapping.setSpecifiedMapKey("mapKey");
		oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		SpecifiedJoinColumn joinColumn = oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		SpecifiedJoinColumn inverseJoinColumn = oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToEmbeddedIdMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("customOrder");
		oneToManyMapping.setSpecifiedMapKey("mapKey");
		oneToManyMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("mappedBy");
		assertFalse(oneToManyMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToOneToOneMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("customOrder");
		oneToManyMapping.setSpecifiedMapKey("mapKey");
		oneToManyMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("mappedBy");
		oneToManyMapping.getCascade().setAll(true);
		oneToManyMapping.getCascade().setMerge(true);
		oneToManyMapping.getCascade().setPersist(true);
		oneToManyMapping.getCascade().setRefresh(true);
		oneToManyMapping.getCascade().setRemove(true);
		assertFalse(oneToManyMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToOneMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((OneToOneMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((OneToOneMapping) ormPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertEquals("mappedBy", ((OneToOneMapping) ormPersistentAttribute.getMapping()).getRelationship().getMappedByStrategy().getMappedByAttribute());
		assertTrue(((OneToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((OneToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((OneToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((OneToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((OneToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isRemove());
	}
	
	public void testOneToManyMorphToManyToManyMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("customOrder");
		oneToManyMapping.setSpecifiedMapKey("mapKey");
		oneToManyMapping.getCascade().setAll(true);
		oneToManyMapping.getCascade().setMerge(true);
		oneToManyMapping.getCascade().setPersist(true);
		oneToManyMapping.getCascade().setRefresh(true);
		oneToManyMapping.getCascade().setRemove(true);
		oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		SpecifiedJoinColumn joinColumn = oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		SpecifiedJoinColumn inverseJoinColumn = oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((ManyToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((ManyToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isRemove());
		JoinTable joinTable = ((ManyToManyMapping) ormPersistentAttribute.getMapping()).getRelationship().getJoinTableStrategy().getJoinTable();
		assertEquals("MY_JOIN_TABLE", joinTable.getName());
		assertEquals("name", joinTable.getJoinColumns().iterator().next().getSpecifiedName());
		assertEquals("referenceName", joinTable.getJoinColumns().iterator().next().getSpecifiedReferencedColumnName());
		assertEquals("inverseName", joinTable.getInverseJoinColumns().iterator().next().getSpecifiedName());
		assertEquals("inverseReferenceName", joinTable.getInverseJoinColumns().iterator().next().getSpecifiedReferencedColumnName());
	}
	
	public void testOneToManyMorphToManyToOneMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("customOrder");
		oneToManyMapping.setSpecifiedMapKey("mapKey");
		oneToManyMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("mappedBy");
		oneToManyMapping.getCascade().setAll(true);
		oneToManyMapping.getCascade().setMerge(true);
		oneToManyMapping.getCascade().setPersist(true);
		oneToManyMapping.getCascade().setRefresh(true);
		oneToManyMapping.getCascade().setRemove(true);
		assertFalse(oneToManyMapping.isDefault());	
		
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
	}
	
	public void testOneToManyMorphToBasicMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("customOrder");
		oneToManyMapping.setSpecifiedMapKey("mapKey");
		oneToManyMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("mappedBy");
		oneToManyMapping.getCascade().setAll(true);
		oneToManyMapping.getCascade().setMerge(true);
		oneToManyMapping.getCascade().setPersist(true);
		oneToManyMapping.getCascade().setRefresh(true);
		oneToManyMapping.getCascade().setRemove(true);
		assertFalse(oneToManyMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof BasicMapping);
//TODO	assertEquals(FetchType.EAGER, ((IBasicMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
	
	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);

		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = ormPersistentType.getAttributes().iterator().next();
		OneToManyMapping oneToManyMapping = (OneToManyMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			oneToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		oneToManyMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			oneToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertFalse(attributeNames.hasNext());
		
		oneToManyMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			oneToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());

		AttributeMapping stateFooMapping = oneToManyMapping.getResolvedTargetEntity().resolveAttributeMapping("state.foo");
		assertNull(stateFooMapping);
	}

	public void testCandidateMapKeyNames() throws Exception {
		createTestEntityWithValidMapOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		
		OneToManyMapping virtualOneToManyMapping = (OneToManyMapping) ormPersistentType.getAttributeNamed("addresses").getMapping();

		Iterator<String> mapKeyNames = virtualOneToManyMapping.getCandidateMapKeyNames().iterator();
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
		
		OrmPersistentAttribute virtualAttribute = ormPersistentType.getAttributeNamed("addresses");
		OneToManyMapping virtualOneToManyMapping = (OneToManyMapping) virtualAttribute.getMapping();
		JavaOneToManyMapping javaOneToManyMapping = (JavaOneToManyMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();

		Iterator<String> mapKeyNames = virtualOneToManyMapping.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
		
		javaOneToManyMapping.setSpecifiedTargetEntity("test.Address");
		mapKeyNames = virtualOneToManyMapping.getCandidateMapKeyNames().iterator();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		OrmOneToManyMapping specifiedOneToManyMapping = (OrmOneToManyMapping) virtualAttribute.addToXml().getMapping();
		mapKeyNames = specifiedOneToManyMapping.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
		
		specifiedOneToManyMapping.setSpecifiedTargetEntity("test.Address");
		mapKeyNames = specifiedOneToManyMapping.getCandidateMapKeyNames().iterator();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		specifiedOneToManyMapping.setSpecifiedTargetEntity("String");
		mapKeyNames = specifiedOneToManyMapping.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
	}

	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityOneToManyMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmPersistentAttribute virtualPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();

		OneToManyMapping virtualOneToManyMapping = (OneToManyMapping) virtualPersistentAttribute.getMapping();	
		assertEquals("address", virtualOneToManyMapping.getName());
		assertEquals(FetchType.EAGER, virtualOneToManyMapping.getSpecifiedFetch());
		assertEquals("Address", virtualOneToManyMapping.getSpecifiedTargetEntity());
		assertNull(virtualOneToManyMapping.getRelationship().
			getMappedByStrategy().getMappedByAttribute());

		Cascade cascade = virtualOneToManyMapping.getCascade();
		assertTrue(cascade.isAll());
		assertTrue(cascade.isMerge());
		assertTrue(cascade.isPersist());
		assertTrue(cascade.isRemove());
		assertTrue(cascade.isRefresh());

		assertTrue(virtualOneToManyMapping.getOrderable().isCustomOrdering());
		assertEquals("city", virtualOneToManyMapping.getOrderable().getSpecifiedOrderBy());
	}

	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityOneToManyMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmPersistentAttribute virtualPersistentAttribute = ormPersistentType.getAttributeNamed("address");

		assertEquals(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY, virtualPersistentAttribute.getMappingKey());
		assertTrue(virtualPersistentAttribute.isVirtual());

		OrmPersistentAttribute ormPersistentAttribute = virtualPersistentAttribute.addToXml(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);

		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();	
		assertEquals("address", ormOneToManyMapping.getName());
		assertEquals(FetchType.LAZY, ormOneToManyMapping.getFetch());
		assertEquals("test.Address", ormOneToManyMapping.getTargetEntity());
		assertNull(ormOneToManyMapping.getRelationship().getMappedByStrategy().getMappedByAttribute());

		Cascade cascade = ormOneToManyMapping.getCascade();
		assertFalse(cascade.isAll());
		assertFalse(cascade.isMerge());
		assertFalse(cascade.isPersist());
		assertFalse(cascade.isRemove());
		assertFalse(cascade.isRefresh());

		assertTrue(ormOneToManyMapping.getOrderable().isNoOrdering());
		assertEquals(null, ormOneToManyMapping.getOrderable().getSpecifiedOrderBy());
	}
}
/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.jpa2.context.Cascade2_0;
import org.eclipse.jpt.core.jpa2.context.OneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.OneToOneRelationshipReference2_0;
import org.eclipse.jpt.core.jpa2.context.OrphanRemovalHolder2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToOneRelationshipReference2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmManyToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOneToOneRelationshipReference2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlDerivedId_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlMapsId_2_0;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.v2_0.context.EclipseLinkOneToOneMapping2_0;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLink2_0OrmOneToOneMappingTests
	extends EclipseLink2_0OrmContextModelTestCase
{
	public EclipseLink2_0OrmOneToOneMappingTests(String name) {
		super(name);
	}
	
	
	private void createTestEntityWithIdDerivedIdentity() throws Exception {
		createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToOne @Id").append(CR);				
				sb.append("    private " + TYPE_NAME + " oneToOne;").append(CR);
				sb.append(CR);				
			}
		});
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		for (OrmReadOnlyPersistentAttribute each : CollectionTools.iterable(ormPersistentType.attributes())) {
			each.convertToSpecified();
		}
	}
	
	private void createTestEntityWithMapsIdDerivedIdentity() throws Exception {
		createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE, JPA2_0.MAPS_ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToOne @MapsId").append(CR);				
				sb.append("    private " + TYPE_NAME + " oneToOne;").append(CR);
				sb.append(CR);				
			}
		});
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		for (OrmReadOnlyPersistentAttribute each : CollectionTools.iterable(ormPersistentType.attributes())) {
			each.convertToSpecified();
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

	private void createTestTargetEntityAddressWithElementCollection() throws Exception {
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
					sb.append("import ");
					sb.append(JPA2_0.ELEMENT_COLLECTION);
					sb.append(";");
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
				sb.append("    @ElementCollection").append(CR);
				sb.append("    private java.util.Collection<State> state;").append(CR);
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
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE, JPA.ID);
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

	private void createTestEntityWithOneToOneMapping() throws Exception {
		createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE);
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
		for (OrmReadOnlyPersistentAttribute each : CollectionTools.iterable(ormPersistentType.attributes())) {
			each.convertToSpecified();
		}
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
				sb.append("    @OneToOne(fetch=FetchType.LAZY, optional=false, targetEntity=Address.class, orphanRemoval = true, cascade={CascadeType.ALL, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH})");
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
	
	public void testUpdateDerivedId() throws Exception {
		createTestEntityWithIdDerivedIdentity();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmReadOnlyPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		OrmOneToOneMapping2_0 contextMapping = (OrmOneToOneMapping2_0) contextAttribute.getMapping();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getXmlTypeMapping();
		XmlDerivedId_2_0 resourceMapping = resourceEntity.getAttributes().getOneToOnes().get(0);
		
		assertNull(resourceMapping.getId());
		assertFalse(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		
		resourceMapping.setId(Boolean.FALSE);
		assertEquals(Boolean.FALSE, resourceMapping.getId());
		assertFalse(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		
		resourceMapping.setId(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceMapping.getId());
		assertTrue(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		
		resourceMapping.setId(null);
		assertNull(resourceMapping.getId());
		assertFalse(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
	}
	
	public void testSetDerivedId() throws Exception {
		createTestEntityWithIdDerivedIdentity();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmReadOnlyPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		OrmOneToOneMapping2_0 contextMapping = (OrmOneToOneMapping2_0) contextAttribute.getMapping();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getXmlTypeMapping();
		XmlDerivedId_2_0 resourceMapping = resourceEntity.getAttributes().getOneToOnes().get(0);
		
		assertNull(resourceMapping.getId());
		assertFalse(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		
		contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().setValue(true);
		assertEquals(Boolean.TRUE, resourceMapping.getId());
		assertTrue(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		
		contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().setValue(false);
		assertNull(resourceMapping.getId());
		assertFalse(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
	}
	
	public void testUpdateMapsId() throws Exception {
		createTestEntityWithMapsIdDerivedIdentity();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmReadOnlyPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		OrmOneToOneMapping2_0 contextMapping = (OrmOneToOneMapping2_0) contextAttribute.getMapping();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getXmlTypeMapping();
		XmlMapsId_2_0 resourceMapping = resourceEntity.getAttributes().getOneToOnes().get(0);
		
		assertNull(resourceMapping.getMapsId());
		assertNull(contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		resourceMapping.setMapsId("foo");
		assertEquals("foo", resourceMapping.getMapsId());
		assertEquals("foo", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		resourceMapping.setMapsId("bar");
		assertEquals("bar", resourceMapping.getMapsId());
		assertEquals("bar", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		resourceMapping.setMapsId(null);
		assertNull(resourceMapping.getMapsId());
		assertNull(contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
	}
	
	public void testSetMapsId() throws Exception {
		createTestEntityWithMapsIdDerivedIdentity();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmReadOnlyPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		OrmOneToOneMapping2_0 contextMapping = (OrmOneToOneMapping2_0) contextAttribute.getMapping();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getXmlTypeMapping();
		XmlMapsId_2_0 resourceMapping = resourceEntity.getAttributes().getOneToOnes().get(0);
		
		assertNull(resourceMapping.getMapsId());
		assertNull(contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().setSpecifiedValue("foo");
		assertEquals("foo", resourceMapping.getMapsId());
		assertEquals("foo", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().setSpecifiedValue("bar");
		assertEquals("bar", resourceMapping.getMapsId());
		assertEquals("bar", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().setSpecifiedValue(null);
		assertNull(resourceMapping.getMapsId());
		assertNull(contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
	}
	
	public void testMorphMapping() throws Exception {
		createTestEntityWithMapsIdDerivedIdentity();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = (OrmPersistentAttribute) contextType.getAttributeNamed("oneToOne");
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getXmlTypeMapping();
		
		XmlOneToOne resourceOneToOne = resourceEntity.getAttributes().getOneToOnes().get(0);
		resourceOneToOne.setMapsId("foo");
		assertNull(resourceOneToOne.getId());
		assertFalse(((OrmOneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertEquals("foo", resourceOneToOne.getMapsId());
		assertEquals("foo", ((OrmOneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		XmlManyToOne resourceManyToOne = resourceEntity.getAttributes().getManyToOnes().get(0);
		assertNull(resourceManyToOne.getId());
		assertFalse(((OrmManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertEquals("foo", resourceManyToOne.getMapsId());
		assertEquals("foo", ((OrmManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		resourceOneToOne = resourceEntity.getAttributes().getOneToOnes().get(0);
		assertNull(resourceOneToOne.getId());
		assertFalse(((OrmOneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertEquals("foo", resourceOneToOne.getMapsId());
		assertEquals("foo", ((OrmOneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
	}
	
	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "address");

		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		ReadOnlyPersistentAttribute persistentAttribute = ormPersistentType.attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			oneToOneMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		oneToOneMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			oneToOneMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertFalse(attributeNames.hasNext());
		
		oneToOneMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			oneToOneMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		AttributeMapping stateFooMapping = oneToOneMapping.getResolvedTargetEntity().resolveAttributeMapping("state.foo");
		assertEquals("foo", stateFooMapping.getName());
	}

	public void testCandidateMappedByAttributeNamesElementCollection() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		createTestTargetEntityAddressWithElementCollection();
		createTestEmbeddableState();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "address");

		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		ReadOnlyPersistentAttribute persistentAttribute = ormPersistentType.attributes().next();
		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			oneToOneMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		oneToOneMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			oneToOneMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertFalse(attributeNames.hasNext());
		
		oneToOneMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			oneToOneMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		AttributeMapping stateFooMapping = oneToOneMapping.getResolvedTargetEntity().resolveAttributeMapping("state.foo");
		assertEquals("foo", stateFooMapping.getName());
	}

	private OrmOrphanRemovable2_0 getOrphanRemovalOf(OneToOneMapping2_0 oneToOneMapping) {
		return ((OrmOrphanRemovalHolder2_0) oneToOneMapping).getOrphanRemoval();
	}
	
	public void testUpdateSpecifiedOrphanRemoval() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping2_0 ormOneToOneMapping = (OrmOneToOneMapping2_0) ormPersistentAttribute.getMapping();
		OrmOrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(ormOneToOneMapping);
		XmlOneToOne oneToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertEquals(null, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		assertEquals(null, oneToOneResource.getOrphanRemoval());
				
		//set enumerated in the resource model, verify context model updated
		oneToOneResource.setOrphanRemoval(Boolean.TRUE);
		assertEquals(Boolean.TRUE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		assertEquals(Boolean.TRUE, oneToOneResource.getOrphanRemoval());
	
		oneToOneResource.setOrphanRemoval(Boolean.FALSE);
		assertEquals(Boolean.FALSE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		assertEquals(Boolean.FALSE, oneToOneResource.getOrphanRemoval());
	}
	
	public void testModifySpecifiedOrphanRemoval() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping2_0 ormOneToOneMapping = (OrmOneToOneMapping2_0) ormPersistentAttribute.getMapping();
		OrmOrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(ormOneToOneMapping);
		XmlOneToOne oneToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertEquals(null, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		assertEquals(null, oneToOneResource.getOrphanRemoval());

		//set enumerated in the context model, verify resource model updated
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(Boolean.TRUE);
		assertEquals(Boolean.TRUE, oneToOneResource.getOrphanRemoval());
		assertEquals(Boolean.TRUE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
	
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(Boolean.FALSE);
		assertEquals(Boolean.FALSE, oneToOneResource.getOrphanRemoval());
		assertEquals(Boolean.FALSE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
	}

	public void testVirtualJoinTable() throws Exception {
		createTestEntityWithValidOneToOneMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);

		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("address");
		JavaOneToOneMapping2_0 javaOneToOneMapping = ((JavaOneToOneMapping2_0) ormPersistentAttribute.getJavaPersistentAttribute().getMapping());
		OneToOneMapping2_0 virtualOneToOneMapping = (OneToOneMapping2_0) ormPersistentAttribute.getMapping();
		((OneToOneRelationshipReference2_0) javaOneToOneMapping.getRelationshipReference()).setJoinTableJoiningStrategy();
		JoinTable virtualJoinTable = ((OneToOneRelationshipReference2_0) virtualOneToOneMapping.getRelationshipReference()).getJoinTableJoiningStrategy().getJoinTable();

		assertTrue(ormPersistentAttribute.isVirtual());
		assertEquals(null, virtualJoinTable.getSpecifiedName());

		createTestTargetEntityAddress();
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		assertEquals(TYPE_NAME + "_Address", virtualJoinTable.getName());
		assertNull(virtualJoinTable.getSpecifiedCatalog());
		assertNull(virtualJoinTable.getSpecifiedSchema());
		assertEquals(0, virtualJoinTable.specifiedJoinColumnsSize());
		assertEquals(0, virtualJoinTable.specifiedInverseJoinColumnsSize());
		JoinColumn virtualJoinColumn = virtualJoinTable.getDefaultJoinColumn();
		assertEquals(TYPE_NAME + "_Address", virtualJoinColumn.getDefaultTable());
		assertEquals(TYPE_NAME + "_id", virtualJoinColumn.getDefaultName());
		assertEquals("id", virtualJoinColumn.getDefaultReferencedColumnName());
		JoinColumn virtualInverseOrmJoinColumn = virtualJoinTable.getDefaultInverseJoinColumn();
		assertEquals(TYPE_NAME + "_Address", virtualInverseOrmJoinColumn.getDefaultTable());
		assertEquals("address_id", virtualInverseOrmJoinColumn.getDefaultName());
		assertEquals("id", virtualInverseOrmJoinColumn.getDefaultReferencedColumnName());

		JavaJoinTable javaJoinTable = ((JavaOneToOneRelationshipReference2_0) javaOneToOneMapping.getRelationshipReference()).getJoinTableJoiningStrategy().getJoinTable();
		javaJoinTable.setSpecifiedName("FOO");
		javaJoinTable.setSpecifiedCatalog("CATALOG");
		javaJoinTable.setSpecifiedSchema("SCHEMA");
		JavaJoinColumn javaJoinColumn = javaJoinTable.addSpecifiedJoinColumn(0);
		javaJoinColumn.setSpecifiedName("NAME");
		javaJoinColumn.setSpecifiedReferencedColumnName("REFERENCED_NAME");
		JavaJoinColumn inverseJavaJoinColumn = javaJoinTable.addSpecifiedInverseJoinColumn(0);
		inverseJavaJoinColumn.setSpecifiedName("INVERSE_NAME");
		inverseJavaJoinColumn.setSpecifiedReferencedColumnName("INVERSE_REFERENCED_NAME");

		assertEquals("FOO", virtualJoinTable.getSpecifiedName());
		assertEquals("CATALOG", virtualJoinTable.getSpecifiedCatalog());
		assertEquals("SCHEMA", virtualJoinTable.getSpecifiedSchema());
		assertEquals(1, virtualJoinTable.specifiedJoinColumnsSize());
		assertEquals(1, virtualJoinTable.specifiedInverseJoinColumnsSize());
		virtualJoinColumn = virtualJoinTable.specifiedJoinColumns().next();
		assertEquals("NAME", virtualJoinColumn.getSpecifiedName());
		assertEquals("REFERENCED_NAME", virtualJoinColumn.getSpecifiedReferencedColumnName());
		virtualInverseOrmJoinColumn = virtualJoinTable.specifiedInverseJoinColumns().next();
		assertEquals("INVERSE_NAME", virtualInverseOrmJoinColumn.getSpecifiedName());
		assertEquals("INVERSE_REFERENCED_NAME", virtualInverseOrmJoinColumn.getSpecifiedReferencedColumnName());
	}

	public void testUpdateDefaultNameFromJavaTable() throws Exception {
		createTestEntityWithValidOneToOneMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);

		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "address");
		OrmOneToOneMapping2_0 ormOneToOneMapping = (OrmOneToOneMapping2_0) ormPersistentAttribute.getMapping();
		((OneToOneRelationshipReference2_0) ormOneToOneMapping.getRelationshipReference()).setJoinTableJoiningStrategy();
		OrmJoinTable ormJoinTable = ((OrmOneToOneRelationshipReference2_0) ormOneToOneMapping.getRelationshipReference()).getJoinTableJoiningStrategy().getJoinTable();
		assertEquals(null, ormJoinTable.getDefaultName());

		createTestTargetEntityAddress();
		OrmPersistentType targetPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(TYPE_NAME + "_Address", ormJoinTable.getDefaultName());


		((JavaEntity) targetPersistentType.getJavaPersistentType().getMapping()).getTable().setSpecifiedName("FOO");
		assertEquals(TYPE_NAME + "_FOO", ormJoinTable.getDefaultName());

		((JavaEntity) ormPersistentType.getJavaPersistentType().getMapping()).getTable().setSpecifiedName("BAR");
		assertEquals("BAR_FOO", ormJoinTable.getDefaultName());

		JavaOneToOneMapping2_0 javaOneToOneMapping = (JavaOneToOneMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("address").getMapping();
		((OneToOneRelationshipReference2_0) javaOneToOneMapping.getRelationshipReference()).setJoinTableJoiningStrategy();
		((OneToOneRelationshipReference2_0) javaOneToOneMapping.getRelationshipReference()).getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("JAVA_JOIN_TABLE");

		assertEquals("BAR_FOO", ormJoinTable.getDefaultName());


		//set metadata-complete to true, will ignore java annotation settings
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		//ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(TYPE_NAME + "_Address", ormJoinTable.getDefaultName());


		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		//remove m-m mapping from the orm.xml file
		ormPersistentAttribute.convertToVirtual();
		//ormPersistentType.getMapping().setSpecifiedMetadataComplete(null);
		OrmReadOnlyPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("address");
		OneToOneMapping2_0 virtualOneToOneMapping = (OneToOneMapping2_0) ormPersistentAttribute2.getMapping();
		JoinTable virtualJoinTable = ((OneToOneRelationshipReference2_0) virtualOneToOneMapping.getRelationshipReference()).getJoinTableJoiningStrategy().getJoinTable();
		assertTrue(ormPersistentAttribute2.isVirtual());
		assertEquals("JAVA_JOIN_TABLE", virtualJoinTable.getSpecifiedName());//specifiedName since this is a virtual mapping now

		((OneToOneRelationshipReference2_0) javaOneToOneMapping.getRelationshipReference()).getJoinTableJoiningStrategy().removeStrategy();
		virtualJoinTable = ((OneToOneRelationshipReference2_0) virtualOneToOneMapping.getRelationshipReference()).getJoinTableJoiningStrategy().getJoinTable();
		assertNull(virtualJoinTable);
		((OneToOneRelationshipReference2_0) javaOneToOneMapping.getRelationshipReference()).setJoinTableJoiningStrategy();
		virtualJoinTable = ((OneToOneRelationshipReference2_0) virtualOneToOneMapping.getRelationshipReference()).getJoinTableJoiningStrategy().getJoinTable();
		assertEquals("BAR_FOO", virtualJoinTable.getName());
		assertEquals("BAR_FOO", virtualJoinTable.getDefaultName());

		((OrmEntity) ormPersistentType.getMapping()).getTable().setSpecifiedName("ORM_TABLE_NAME");
		assertEquals("ORM_TABLE_NAME_FOO", virtualJoinTable.getDefaultName());

		((OrmEntity) targetPersistentType.getMapping()).getTable().setSpecifiedName("ORM_TARGET");
		assertEquals("ORM_TABLE_NAME_ORM_TARGET", virtualJoinTable.getDefaultName());
	}

	public void testModifyPredominantJoiningStrategy() throws Exception {
		createTestEntityWithOneToOneMapping();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmReadOnlyPersistentAttribute contextAttribute = contextType.getAttributeNamed("id");
		OrmOneToOneMapping2_0 contextMapping = (OrmOneToOneMapping2_0) contextAttribute.getMapping();
		OrmOneToOneRelationshipReference2_0 relationshipReference = (OrmOneToOneRelationshipReference2_0) contextMapping.getRelationshipReference();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getXmlTypeMapping();
		XmlOneToOne resourceMapping = resourceEntity.getAttributes().getOneToOnes().get(0);

		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNull(resourceMapping.getMappedBy());
		assertNull(resourceMapping.getJoinTable());
		assertTrue(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesPrimaryKeyJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesMappedByJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());

		relationshipReference.setMappedByJoiningStrategy();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNotNull(resourceMapping.getMappedBy());
		assertNull(resourceMapping.getJoinTable());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesPrimaryKeyJoinColumnJoiningStrategy());
		assertTrue(relationshipReference.usesMappedByJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());

		relationshipReference.setPrimaryKeyJoinColumnJoiningStrategy();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertFalse(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNull(resourceMapping.getMappedBy());
		assertNull(resourceMapping.getJoinTable());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertTrue(relationshipReference.usesPrimaryKeyJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesMappedByJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());

		relationshipReference.setJoinTableJoiningStrategy();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNull(resourceMapping.getMappedBy());
		assertNotNull(resourceMapping.getJoinTable());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesPrimaryKeyJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesMappedByJoiningStrategy());
		assertTrue(relationshipReference.usesJoinTableJoiningStrategy());

		relationshipReference.setJoinColumnJoiningStrategy();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNull(resourceMapping.getMappedBy());
		assertNull(resourceMapping.getJoinTable());
		assertTrue(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesPrimaryKeyJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesMappedByJoiningStrategy());	
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());
	}

	public void testUpdatePredominantJoiningStrategy() throws Exception {
		createTestEntityWithOneToOneMapping();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmReadOnlyPersistentAttribute contextAttribute = contextType.getAttributeNamed("id");
		OrmOneToOneMapping2_0 contextMapping = (OrmOneToOneMapping2_0) contextAttribute.getMapping();
		OrmOneToOneRelationshipReference2_0 relationshipReference = (OrmOneToOneRelationshipReference2_0) contextMapping.getRelationshipReference();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getXmlTypeMapping();
		XmlOneToOne resourceMapping = resourceEntity.getAttributes().getOneToOnes().get(0);

		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNull(resourceMapping.getMappedBy());
		assertNull(resourceMapping.getJoinTable());
		assertTrue(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesPrimaryKeyJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesMappedByJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());

		resourceMapping.getPrimaryKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn());
		getJpaProject().synchronizeContextModel();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertFalse(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNull(resourceMapping.getMappedBy());
		assertNull(resourceMapping.getJoinTable());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertTrue(relationshipReference.usesPrimaryKeyJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesMappedByJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());

		resourceMapping.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		getJpaProject().synchronizeContextModel();
		assertFalse(resourceMapping.getJoinColumns().isEmpty());
		assertFalse(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNull(resourceMapping.getMappedBy());
		assertNull(resourceMapping.getJoinTable());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertTrue(relationshipReference.usesPrimaryKeyJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesMappedByJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());

		resourceMapping.setMappedBy("foo");
		getJpaProject().synchronizeContextModel();
		assertFalse(resourceMapping.getJoinColumns().isEmpty());
		assertFalse(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNotNull(resourceMapping.getMappedBy());
		assertNull(resourceMapping.getJoinTable());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesPrimaryKeyJoinColumnJoiningStrategy());
		assertTrue(relationshipReference.usesMappedByJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());

		resourceMapping.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		getJpaProject().synchronizeContextModel();
		assertFalse(resourceMapping.getJoinColumns().isEmpty());
		assertFalse(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNotNull(resourceMapping.getMappedBy());
		assertNotNull(resourceMapping.getJoinTable());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesPrimaryKeyJoinColumnJoiningStrategy());
		assertTrue(relationshipReference.usesMappedByJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());

		resourceMapping.getPrimaryKeyJoinColumns().clear();
		getJpaProject().synchronizeContextModel();
		assertFalse(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNotNull(resourceMapping.getMappedBy());
		assertNotNull(resourceMapping.getJoinTable());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesPrimaryKeyJoinColumnJoiningStrategy());
		assertTrue(relationshipReference.usesMappedByJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());

		resourceMapping.getJoinColumns().clear();
		getJpaProject().synchronizeContextModel();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNotNull(resourceMapping.getMappedBy());
		assertNotNull(resourceMapping.getJoinTable());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesPrimaryKeyJoinColumnJoiningStrategy());
		assertTrue(relationshipReference.usesMappedByJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());

		resourceMapping.setMappedBy(null);
		getJpaProject().synchronizeContextModel();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNull(resourceMapping.getMappedBy());
		assertNotNull(resourceMapping.getJoinTable());
		assertFalse(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesPrimaryKeyJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesMappedByJoiningStrategy());
		assertTrue(relationshipReference.usesJoinTableJoiningStrategy());

		resourceMapping.setJoinTable(null);
		getJpaProject().synchronizeContextModel();
		assertTrue(resourceMapping.getJoinColumns().isEmpty());
		assertTrue(resourceMapping.getPrimaryKeyJoinColumns().isEmpty());
		assertNull(resourceMapping.getMappedBy());
		assertNull(resourceMapping.getJoinTable());
		assertTrue(relationshipReference.usesJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesPrimaryKeyJoinColumnJoiningStrategy());
		assertFalse(relationshipReference.usesMappedByJoiningStrategy());
		assertFalse(relationshipReference.usesJoinTableJoiningStrategy());
	}

	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityOneToOneMapping();
		createTestTargetEntityAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, ormPersistentType.virtualAttributesSize());		
		OrmReadOnlyPersistentAttribute virtualPersistentAttribute = ormPersistentType.virtualAttributes().next();

		EclipseLinkOneToOneMapping2_0 virtualOneToOneMapping = (EclipseLinkOneToOneMapping2_0) virtualPersistentAttribute.getMapping();	
		assertEquals("address", virtualOneToOneMapping.getName());
		assertEquals(FetchType.LAZY, virtualOneToOneMapping.getSpecifiedFetch());
		assertEquals(Boolean.FALSE, virtualOneToOneMapping.getSpecifiedOptional());
		assertEquals("Address", virtualOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(virtualOneToOneMapping.getRelationshipReference().
			getMappedByJoiningStrategy().getMappedByAttribute());

		JoinColumn ormJoinColumn = 
			virtualOneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy().specifiedJoinColumns().next();
		assertEquals("MY_COLUMN", ormJoinColumn.getSpecifiedName());
		assertEquals("MY_REFERENCED_COLUMN", ormJoinColumn.getSpecifiedReferencedColumnName());
		assertEquals(Boolean.TRUE, ormJoinColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, ormJoinColumn.getSpecifiedUpdatable());
		assertEquals("COLUMN_DEFINITION", ormJoinColumn.getColumnDefinition());
		assertEquals("MY_TABLE", ormJoinColumn.getSpecifiedTable());

		Cascade2_0 cascade = (Cascade2_0) virtualOneToOneMapping.getCascade();
		assertTrue(cascade.isAll());
		assertTrue(cascade.isMerge());
		assertTrue(cascade.isPersist());
		assertTrue(cascade.isRemove());
		assertTrue(cascade.isRefresh());
		assertTrue(cascade.isDetach());

		assertEquals(EclipseLinkJoinFetchType.INNER, virtualOneToOneMapping.getJoinFetch().getValue());
		assertTrue(virtualOneToOneMapping.getPrivateOwned().isPrivateOwned());

		assertTrue(((OrphanRemovalHolder2_0) virtualOneToOneMapping).getOrphanRemoval().isOrphanRemoval());
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

		EclipseLinkOneToOneMapping2_0 ormOneToOneMapping = (EclipseLinkOneToOneMapping2_0) virtualPersistentAttribute.getMapping();	
		assertEquals("address", ormOneToOneMapping.getName());
		assertEquals(FetchType.EAGER, ormOneToOneMapping.getFetch());
		assertEquals(true, ormOneToOneMapping.isOptional());
		assertEquals("test.Address", ormOneToOneMapping.getTargetEntity());
		assertNull(ormOneToOneMapping.getRelationshipReference().getMappedByJoiningStrategy().getMappedByAttribute());

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

		Cascade2_0 cascade = (Cascade2_0) ormOneToOneMapping.getCascade();
		assertFalse(cascade.isAll());
		assertFalse(cascade.isMerge());
		assertFalse(cascade.isPersist());
		assertFalse(cascade.isRemove());
		assertFalse(cascade.isRefresh());
		assertFalse(cascade.isDetach());

		assertEquals(null, ormOneToOneMapping.getJoinFetch().getValue());
		assertFalse(ormOneToOneMapping.getPrivateOwned().isPrivateOwned());

		assertFalse(((OrphanRemovalHolder2_0) ormOneToOneMapping).getOrphanRemoval().isOrphanRemoval());
	}
}

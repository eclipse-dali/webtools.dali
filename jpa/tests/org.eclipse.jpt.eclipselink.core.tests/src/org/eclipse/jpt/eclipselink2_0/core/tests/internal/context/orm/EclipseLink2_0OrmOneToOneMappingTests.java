/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.jpa2.context.orm.OrmManyToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOneToOneMapping2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlDerivedId_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlMapsId_2_0;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLink2_0OrmOneToOneMappingTests
	extends EclipseLink2_0OrmContextModelTestCase
{
	public EclipseLink2_0OrmOneToOneMappingTests(String name) {
		super(name);
	}
	
	
	private void createTestEntity() throws Exception {
		createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToOne");
				sb.append(CR);
				sb.append("    private Address address;");
				sb.append(CR);
				sb.append(CR);
				sb.append("    @Id");			
			}
		});
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		for (OrmPersistentAttribute each : CollectionTools.iterable(ormPersistentType.attributes())) {
			each.makeSpecified();
		}
	}
	
	private void createTestEntityWithDerivedId() throws Exception {
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
		for (OrmPersistentAttribute each : CollectionTools.iterable(ormPersistentType.attributes())) {
			each.makeSpecified();
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
	
	public void testUpdateDerivedId() throws Exception {
		createTestEntityWithDerivedId();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		OrmOneToOneMapping2_0 contextMapping = (OrmOneToOneMapping2_0) contextAttribute.getMapping();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		XmlDerivedId_2_0 resourceMapping = resourceEntity.getAttributes().getOneToOnes().get(0);
		
		assertNull(resourceMapping.getId());
		assertFalse(contextMapping.getDerivedId().getValue());
		
		resourceMapping.setId(Boolean.FALSE);
		assertEquals(Boolean.FALSE, resourceMapping.getId());
		assertFalse(contextMapping.getDerivedId().getValue());
		
		resourceMapping.setId(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceMapping.getId());
		assertTrue(contextMapping.getDerivedId().getValue());
		
		resourceMapping.setId(null);
		assertNull(resourceMapping.getId());
		assertFalse(contextMapping.getDerivedId().getValue());
	}
	
	public void testSetDerivedId() throws Exception {
		createTestEntityWithDerivedId();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		OrmOneToOneMapping2_0 contextMapping = (OrmOneToOneMapping2_0) contextAttribute.getMapping();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		XmlDerivedId_2_0 resourceMapping = resourceEntity.getAttributes().getOneToOnes().get(0);
		
		assertNull(resourceMapping.getId());
		assertFalse(contextMapping.getDerivedId().getValue());
		
		contextMapping.getDerivedId().setValue(true);
		assertEquals(Boolean.TRUE, resourceMapping.getId());
		assertTrue(contextMapping.getDerivedId().getValue());
		
		contextMapping.getDerivedId().setValue(false);
		assertNull(resourceMapping.getId());
		assertFalse(contextMapping.getDerivedId().getValue());
	}
	
	public void testUpdateMapsId() throws Exception {
		createTestEntity();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("address");
		OrmOneToOneMapping2_0 contextMapping = (OrmOneToOneMapping2_0) contextAttribute.getMapping();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		XmlMapsId_2_0 resourceMapping = resourceEntity.getAttributes().getOneToOnes().get(0);
		
		assertNull(resourceMapping.getMapsId());
		assertNull(contextMapping.getMapsId().getValue());
		
		resourceMapping.setMapsId("foo");
		assertEquals("foo", resourceMapping.getMapsId());
		assertEquals("foo", contextMapping.getMapsId().getValue());
		
		resourceMapping.setMapsId("bar");
		assertEquals("bar", resourceMapping.getMapsId());
		assertEquals("bar", contextMapping.getMapsId().getValue());
		
		resourceMapping.setMapsId(null);
		assertNull(resourceMapping.getMapsId());
		assertNull(contextMapping.getMapsId().getValue());
	}
	
	public void testSetMapsId() throws Exception {
		createTestEntity();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("address");
		OrmOneToOneMapping2_0 contextMapping = (OrmOneToOneMapping2_0) contextAttribute.getMapping();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		XmlMapsId_2_0 resourceMapping = resourceEntity.getAttributes().getOneToOnes().get(0);
		
		assertNull(resourceMapping.getMapsId());
		assertNull(contextMapping.getMapsId().getValue());
		
		contextMapping.getMapsId().setValue("foo");
		assertEquals("foo", resourceMapping.getMapsId());
		assertEquals("foo", contextMapping.getMapsId().getValue());
		
		contextMapping.getMapsId().setValue("bar");
		assertEquals("bar", resourceMapping.getMapsId());
		assertEquals("bar", contextMapping.getMapsId().getValue());
		
		contextMapping.getMapsId().setValue(null);
		assertNull(resourceMapping.getMapsId());
		assertNull(contextMapping.getMapsId().getValue());
	}
	
	public void testMorphMapping() throws Exception {
		createTestEntityWithDerivedId();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("oneToOne");
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		
		XmlOneToOne resourceOneToOne = resourceEntity.getAttributes().getOneToOnes().get(0);
		resourceOneToOne.setId(Boolean.TRUE);
		resourceOneToOne.setMapsId("foo");
		assertEquals(Boolean.TRUE, resourceOneToOne.getId());
		assertEquals("foo", resourceOneToOne.getMapsId());
		assertTrue(((OrmOneToOneMapping2_0) contextAttribute.getMapping()).getDerivedId().getValue());
		
		contextAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		XmlManyToOne resourceManyToOne = resourceEntity.getAttributes().getManyToOnes().get(0);
		assertEquals(Boolean.TRUE, resourceManyToOne.getId());
		assertEquals("foo", resourceManyToOne.getMapsId());
		assertTrue(((OrmManyToOneMapping2_0) contextAttribute.getMapping()).getDerivedId().getValue());
		
		contextAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		resourceOneToOne = resourceEntity.getAttributes().getOneToOnes().get(0);
		assertEquals(Boolean.TRUE, resourceOneToOne.getId());
		assertEquals("foo", resourceOneToOne.getMapsId());
		assertTrue(((OrmOneToOneMapping2_0) contextAttribute.getMapping()).getDerivedId().getValue());
	}
	
	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidOneToOneMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "address");

		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = ormPersistentType.attributes().next();
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
	
	public void testUpdateSpecifiedOrphanRemoval() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping2_0 ormOneToOneMapping = (OrmOneToOneMapping2_0) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertEquals(OrmOneToOneMapping2_0.DEFAULT_ORPHAN_REMOVAL, ormOneToOneMapping.getSpecifiedOrphanRemoval().booleanValue());
		assertEquals(OrmOneToOneMapping2_0.DEFAULT_ORPHAN_REMOVAL, oneToOneResource.isOrphanRemoval());
				
		//set enumerated in the resource model, verify context model updated
		oneToOneResource.setOrphanRemoval(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormOneToOneMapping.getSpecifiedOrphanRemoval());
		assertEquals(true, oneToOneResource.isOrphanRemoval());
	
		oneToOneResource.setOrphanRemoval(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormOneToOneMapping.getSpecifiedOrphanRemoval());
		assertEquals(false, oneToOneResource.isOrphanRemoval());
	}
	
	public void testModifySpecifiedOrphanRemoval() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmOneToOneMapping2_0 ormOneToOneMapping = (OrmOneToOneMapping2_0) ormPersistentAttribute.getMapping();
		XmlOneToOne oneToOneResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertEquals(OrmOneToOneMapping2_0.DEFAULT_ORPHAN_REMOVAL, ormOneToOneMapping.getSpecifiedOrphanRemoval().booleanValue());
		assertEquals(OrmOneToOneMapping2_0.DEFAULT_ORPHAN_REMOVAL, oneToOneResource.isOrphanRemoval());

		//set enumerated in the context model, verify resource model updated
		ormOneToOneMapping.setSpecifiedOrphanRemoval(Boolean.TRUE);
		assertEquals(true, oneToOneResource.isOrphanRemoval());
		assertEquals(Boolean.TRUE, ormOneToOneMapping.getSpecifiedOrphanRemoval());
	
		ormOneToOneMapping.setSpecifiedOrphanRemoval(Boolean.FALSE);
		assertEquals(false, oneToOneResource.isOrphanRemoval());
		assertEquals(Boolean.FALSE, ormOneToOneMapping.getSpecifiedOrphanRemoval());
	}
}

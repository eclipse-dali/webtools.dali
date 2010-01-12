/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class GenericOrmElementCollectionMapping2_0Tests extends Generic2_0ContextModelTestCase
{
	public GenericOrmElementCollectionMapping2_0Tests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	private ICompilationUnit createTestEntityWithElementCollectionMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA2_0.ELEMENT_COLLECTION, JPA.FETCH_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ElementCollection(targetClass=String.class, fetch=FetchType.EAGER)");
				sb.append(CR);
				sb.append("    private java.util.Collection<Address> address;").append(CR);
				sb.append(CR);
				sb.append("    @Id");				
			}
		});
	}	
	
	private void createTestTargetEmbeddableAddress() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.EMBEDDABLE);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ID);
					sb.append(";");
					sb.append(CR);
				sb.append(CR);
				sb.append("@Embeddable");
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
	
	private ICompilationUnit createTestEntityWithEmbeddableElementCollectionMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA2_0.ELEMENT_COLLECTION, JPA.FETCH_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ElementCollection");
				sb.append(CR);
				sb.append("    private java.util.Collection<Address> addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id");				
			}
		});
	}	
	
	private ICompilationUnit createTestEntityWithNonGenericElementCollectionMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA2_0.ELEMENT_COLLECTION, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ElementCollection").append(CR);				
				sb.append("    private java.util.Collection addresses;").append(CR);			
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithGenericBasicElementCollectionMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA2_0.ELEMENT_COLLECTION, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ElementCollection").append(CR);				
				sb.append("    private java.util.Collection<String> addresses;").append(CR);			
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection elementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertEquals("oneToOneMapping", ormElementCollectionMapping.getName());
		assertEquals("oneToOneMapping", elementCollection.getName());
				
		//set name in the resource model, verify context model updated
		elementCollection.setName("newName");
		assertEquals("newName", ormElementCollectionMapping.getName());
		assertEquals("newName", elementCollection.getName());
	
		//set name to null in the resource model
		elementCollection.setName(null);
		assertNull(ormElementCollectionMapping.getName());
		assertNull(elementCollection.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection elementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertEquals("oneToOneMapping", ormElementCollectionMapping.getName());
		assertEquals("oneToOneMapping", elementCollection.getName());
				
		//set name in the context model, verify resource model updated
		ormElementCollectionMapping.setName("newName");
		assertEquals("newName", ormElementCollectionMapping.getName());
		assertEquals("newName", elementCollection.getName());
	
		//set name to null in the context model
		ormElementCollectionMapping.setName(null);
		assertNull(ormElementCollectionMapping.getName());
		assertNull(elementCollection.getName());
	}

	public void testMappingNoUnderylingJavaAttribute() throws Exception {
		createTestEntityWithElementCollectionMapping();
		createTestTargetEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "foo");
		assertEquals(3, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		
		assertEquals("foo", ormElementCollectionMapping.getName());

		assertNull(ormElementCollectionMapping.getSpecifiedFetch());
		assertEquals(FetchType.LAZY, ormElementCollectionMapping.getFetch());
	}
	
	
	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityWithElementCollectionMapping();
		createTestTargetEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();	
		assertEquals("address", ormElementCollectionMapping.getName());
		assertEquals(FetchType.EAGER, ormElementCollectionMapping.getSpecifiedFetch());
		assertEquals("String", ormElementCollectionMapping.getSpecifiedTargetClass());
		
		ormPersistentAttribute.makeSpecified();
		ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();	
		assertEquals("address", ormElementCollectionMapping.getName());
		assertEquals(null, ormElementCollectionMapping.getSpecifiedFetch());
		assertEquals(FetchType.LAZY, ormElementCollectionMapping.getDefaultFetch());
		assertEquals(null, ormElementCollectionMapping.getSpecifiedTargetClass());
		assertEquals(PACKAGE_NAME + ".Address", ormElementCollectionMapping.getDefaultTargetClass());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityWithElementCollectionMapping();
		createTestTargetEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(3, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		assertEquals(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
		
		ormPersistentAttribute.makeSpecified(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		ormPersistentAttribute= ormPersistentType.specifiedAttributes().next();

		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();	
		assertEquals("address", ormElementCollectionMapping.getName());
		assertEquals(FetchType.LAZY, ormElementCollectionMapping.getFetch());
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		createTestTargetEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "address");
		assertEquals(2, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		
		assertEquals("address", ormElementCollectionMapping.getName());
		assertNull(ormElementCollectionMapping.getSpecifiedFetch());
		assertEquals(FetchType.LAZY, ormElementCollectionMapping.getFetch());
	}
	
	
	public void testMorphToIdMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testMorphToVersionMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testMorphToTransientMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "oneToOne");

		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "oneToOne");

		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testMorphToManyToManyMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
//		assertEquals(FetchType.EAGER, ((ManyToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
//		assertEquals(FetchType.EAGER, ((OneToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
//		assertEquals(FetchType.EAGER, ((ManyToOneMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
	
	public void testMorphToBasicMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "oneToOne");
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("oneToOne", ormPersistentAttribute.getMapping().getName());
//TODO	assertEquals(FetchType.EAGER, ((IBasicMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection elementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getSpecifiedFetch());
		assertNull(elementCollection.getFetch());
				
		//set fetch in the resource model, verify context model updated
		elementCollection.setFetch(org.eclipse.jpt.core.resource.orm.FetchType.EAGER);
		assertEquals(FetchType.EAGER, ormElementCollectionMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.EAGER, elementCollection.getFetch());
	
		elementCollection.setFetch(org.eclipse.jpt.core.resource.orm.FetchType.LAZY);
		assertEquals(FetchType.LAZY, ormElementCollectionMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.LAZY, elementCollection.getFetch());

		//set fetch to null in the resource model
		elementCollection.setFetch(null);
		assertNull(ormElementCollectionMapping.getSpecifiedFetch());
		assertNull(elementCollection.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection elementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getSpecifiedFetch());
		assertNull(elementCollection.getFetch());
				
		//set fetch in the context model, verify resource model updated
		ormElementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.EAGER, elementCollection.getFetch());
		assertEquals(FetchType.EAGER, ormElementCollectionMapping.getSpecifiedFetch());
	
		ormElementCollectionMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.LAZY, elementCollection.getFetch());
		assertEquals(FetchType.LAZY, ormElementCollectionMapping.getSpecifiedFetch());

		//set fetch to null in the context model
		ormElementCollectionMapping.setSpecifiedFetch(null);
		assertNull(elementCollection.getFetch());
		assertNull(ormElementCollectionMapping.getSpecifiedFetch());
	}	
	
	public void testUpdateSpecifiedTargetClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 elementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getSpecifiedTargetClass());
		assertNull(elementCollection.getTargetClass());
				
		//set target class in the resource model, verify context model updated
		elementCollection.setTargetClass("newTargetClass");
		assertEquals("newTargetClass", ormElementCollectionMapping.getSpecifiedTargetClass());
		assertEquals("newTargetClass", elementCollection.getTargetClass());
	
		//set target class to null in the resource model
		elementCollection.setTargetClass(null);
		assertNull(ormElementCollectionMapping.getSpecifiedTargetClass());
		assertNull(elementCollection.getTargetClass());
	}
	
	public void testModifySpecifiedTargetClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 elementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getSpecifiedTargetClass());
		assertNull(elementCollection.getTargetClass());
				
		//set target class in the context model, verify resource model updated
		ormElementCollectionMapping.setSpecifiedTargetClass("newTargetClass");
		assertEquals("newTargetClass", ormElementCollectionMapping.getSpecifiedTargetClass());
		assertEquals("newTargetClass", elementCollection.getTargetClass());
	
		//set target class to null in the context model
		ormElementCollectionMapping.setSpecifiedTargetClass(null);
		assertNull(ormElementCollectionMapping.getSpecifiedTargetClass());
		assertNull(elementCollection.getTargetClass());
	}

	
	public void testGetValueTypeEmbeddable() throws Exception {
		createTestEntityWithEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "test.Address");

		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "addresses");	
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();

		assertEquals(ElementCollectionMapping2_0.Type.EMBEDDABLE_TYPE, elementCollectionMapping.getValueType());
	}
	
	public void testGetValueTypeEntity() throws Exception {
		createTestEntityWithEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.Address");

		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "addresses");	
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();

		assertEquals(ElementCollectionMapping2_0.Type.BASIC_TYPE, elementCollectionMapping.getValueType());
	}
	
	public void testGetValueTypeNone() throws Exception {
		createTestEntityWithNonGenericElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "test.Address");
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "addresses");
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();

		assertEquals(ElementCollectionMapping2_0.Type.NO_TYPE, elementCollectionMapping.getValueType());

		elementCollectionMapping.setSpecifiedTargetClass("test.Address");
		assertEquals(ElementCollectionMapping2_0.Type.EMBEDDABLE_TYPE, elementCollectionMapping.getValueType());
	}
	
	public void testGetValueTypeBasic() throws Exception {
		createTestEntityWithGenericBasicElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.Address");

		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "addresses");	
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();

		assertEquals(ElementCollectionMapping2_0.Type.BASIC_TYPE, elementCollectionMapping.getValueType());
	}
	
}
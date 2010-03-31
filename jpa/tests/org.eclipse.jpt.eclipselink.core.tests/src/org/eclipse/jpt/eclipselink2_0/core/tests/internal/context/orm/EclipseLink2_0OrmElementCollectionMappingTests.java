/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.OrderColumn2_0;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLink2_0OrmElementCollectionMappingTests extends EclipseLink2_0OrmContextModelTestCase
{
	private static final String ATTRIBUTE_OVERRIDE_NAME = "city";
	private static final String ATTRIBUTE_OVERRIDE_COLUMN_NAME = "E_CITY";

	public EclipseLink2_0OrmElementCollectionMappingTests(String name) {
		super(name);
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
				return new ArrayIterator<String>(JPA.ENTITY, JPA2_0.ELEMENT_COLLECTION, JPA.FETCH_TYPE, JPA.ATTRIBUTE_OVERRIDE, JPA.COLUMN);
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
				sb.append("    @AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\", column=@Column(name=\"" + ATTRIBUTE_OVERRIDE_COLUMN_NAME + "\"))");
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
	
	private ICompilationUnit createTestEntityWithValidMapElementCollectionMapping() throws Exception {
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
				sb.append("    private java.util.Map<String, Address> addresses;").append(CR);
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
	
	private ICompilationUnit createTestEntityWithValidNonGenericMapElementCollectionMapping() throws Exception {
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
				sb.append("    private java.util.Map addresses;").append(CR);			
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
		assertEquals("java.lang.String", ormElementCollectionMapping.getSpecifiedTargetClass());
		
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
	
	
	public void testUpdateMapKey() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
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
		createTestEntityWithValidMapElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");

		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		assertNull(ormElementCollectionMapping.getSpecifiedMapKey());
		assertNull(ormElementCollectionMapping.getMapKey());
		assertFalse(ormElementCollectionMapping.isPkMapKey());
		assertFalse(ormElementCollectionMapping.isCustomMapKey());
		assertTrue(ormElementCollectionMapping.isNoMapKey());
		
		//set pk mapKey in the java, verify virtual orm mapping updates
		javaElementCollectionMapping.setPkMapKey(true);
		assertEquals(null, ormElementCollectionMapping.getMapKey());//no primary key on an embeddable
		assertTrue(ormElementCollectionMapping.isPkMapKey());
		assertFalse(ormElementCollectionMapping.isCustomMapKey());
		assertFalse(ormElementCollectionMapping.isNoMapKey());
		
		
		//set custom specified mapKey in the java, verify virtual orm mapping updates
		javaElementCollectionMapping.setCustomMapKey(true);
		javaElementCollectionMapping.setSpecifiedMapKey("city");
		assertEquals("city", ormElementCollectionMapping.getSpecifiedMapKey());
		assertEquals("city", ormElementCollectionMapping.getMapKey());
		assertFalse(ormElementCollectionMapping.isPkMapKey());
		assertTrue(ormElementCollectionMapping.isCustomMapKey());
		assertFalse(ormElementCollectionMapping.isNoMapKey());
	}
	
	public void testModifyMapKey() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getSpecifiedMapKey());
		assertNull(oneToMany.getMapKey());
					
		//set mapKey  in the context model, verify resource model updated
		ormOneToManyMapping.setSpecifiedMapKey("myMapKey");
		assertEquals("myMapKey", ormOneToManyMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", oneToMany.getMapKey().getName());
	
		//set mapKey to null in the context model
		ormOneToManyMapping.setSpecifiedMapKey(null);
		assertNull(ormOneToManyMapping.getSpecifiedMapKey());
		assertNull(oneToMany.getMapKey());
	}
	
	public void testCandidateMapKeyNames() throws Exception {
		createTestEntityWithValidMapElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentType.getAttributeNamed("addresses").getMapping();

		Iterator<String> mapKeyNames = 
			ormElementCollectionMapping.candidateMapKeyNames();
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.name", mapKeyNames.next());
		assertEquals("state.abbr", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
	}
	
	public void testCandidateMapKeyNames2() throws Exception {
		createTestEntityWithValidNonGenericMapElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();

		Iterator<String> mapKeyNames = ormElementCollectionMapping.candidateMapKeyNames();
		assertEquals(false, mapKeyNames.hasNext());
		
		javaElementCollectionMapping.setSpecifiedTargetClass("test.Address");
		mapKeyNames = ormElementCollectionMapping.candidateMapKeyNames();
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.name", mapKeyNames.next());
		assertEquals("state.abbr", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		ormElementCollectionMapping.getPersistentAttribute().makeSpecified();
		ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentType.getAttributeNamed("addresses").getMapping();
		mapKeyNames = ormElementCollectionMapping.candidateMapKeyNames();
		assertEquals(false, mapKeyNames.hasNext());
		
		ormElementCollectionMapping.setSpecifiedTargetClass("test.Address");
		mapKeyNames = ormElementCollectionMapping.candidateMapKeyNames();
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.name", mapKeyNames.next());
		assertEquals("state.abbr", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		ormElementCollectionMapping.setSpecifiedTargetClass("String");
		mapKeyNames = ormElementCollectionMapping.candidateMapKeyNames();
		assertEquals(false, mapKeyNames.hasNext());
	}
	
	public void testUpdateMapKeyClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection elementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getSpecifiedMapKeyClass());
		assertNull(elementCollection.getMapKeyClass());
		
		//set mapKey in the resource model, verify context model does not change
		elementCollection.setMapKeyClass(OrmFactory.eINSTANCE.createXmlClassReference());
		assertNull(ormElementCollectionMapping.getSpecifiedMapKeyClass());
		assertNotNull(elementCollection.getMapKeyClass());
				
		//set mapKey name in the resource model, verify context model updated
		elementCollection.getMapKeyClass().setClassName("String");
		assertEquals("String", ormElementCollectionMapping.getSpecifiedMapKeyClass());
		assertEquals("String", elementCollection.getMapKeyClass().getClassName());
		
		//set mapKey name to null in the resource model
		elementCollection.getMapKeyClass().setClassName(null);
		assertNull(ormElementCollectionMapping.getSpecifiedMapKeyClass());
		assertNull(elementCollection.getMapKeyClass().getClassName());
		
		elementCollection.getMapKeyClass().setClassName("String");
		elementCollection.setMapKeyClass(null);
		assertNull(ormElementCollectionMapping.getSpecifiedMapKeyClass());
		assertNull(elementCollection.getMapKeyClass());
	}
	
	public void testUpdateVirtualMapKeyClass() throws Exception {
		createTestEntityWithValidMapElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");

		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		assertEquals("java.lang.String", ormElementCollectionMapping.getSpecifiedMapKeyClass());
		assertEquals("java.lang.String", ormElementCollectionMapping.getMapKeyClass());
		assertEquals("java.lang.String", ormElementCollectionMapping.getDefaultMapKeyClass());
		
		//set pk mapKey in the java, verify virtual orm mapping updates
		javaElementCollectionMapping.setSpecifiedMapKeyClass("Integer");
		assertEquals("java.lang.Integer", ormElementCollectionMapping.getMapKeyClass());
		assertEquals("java.lang.Integer", ormElementCollectionMapping.getSpecifiedMapKeyClass());
		assertEquals("java.lang.String", ormElementCollectionMapping.getDefaultMapKeyClass());
	}
	
	public void testModifyMapKeyClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection elementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getSpecifiedMapKeyClass());
		assertNull(elementCollection.getMapKeyClass());
					
		//set mapKey  in the context model, verify resource model updated
		ormElementCollectionMapping.setSpecifiedMapKeyClass("String");
		assertEquals("String", ormElementCollectionMapping.getSpecifiedMapKeyClass());
		assertEquals("String", elementCollection.getMapKeyClass().getClassName());
	
		//set mapKey to null in the context model
		ormElementCollectionMapping.setSpecifiedMapKeyClass(null);
		assertNull(ormElementCollectionMapping.getSpecifiedMapKeyClass());
		assertNull(elementCollection.getMapKeyClass());
	}
	public void testOrderColumnDefaults() throws Exception {
		createTestEntityWithGenericBasicElementCollectionMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "addresses");
		OrmElementCollectionMapping2_0 elementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();

		Orderable2_0 orderable = elementCollectionMapping.getOrderable();
		assertEquals(false, orderable.isOrderColumnOrdering());
		assertEquals(true, orderable.isNoOrdering());
		
		orderable.setOrderColumnOrdering(true);
		OrderColumn2_0 orderColumn = orderable.getOrderColumn();
		assertEquals(true, orderable.isOrderColumnOrdering());
		assertEquals(null, orderColumn.getSpecifiedName());
		assertEquals("addresses_ORDER", orderColumn.getDefaultName());
		assertEquals(TYPE_NAME + "_addresses", orderColumn.getTable());
		
		orderColumn.setSpecifiedName("FOO");
		assertEquals("FOO", orderColumn.getSpecifiedName());
		assertEquals("addresses_ORDER", orderColumn.getDefaultName());
		assertEquals(TYPE_NAME + "_addresses", orderColumn.getTable());
	}
	
	public void testVirtualOrderColumn() throws Exception {
		createTestEntityWithGenericBasicElementCollectionMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "addresses");
		OrmElementCollectionMapping2_0 elementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();

		Orderable2_0 orderable = elementCollectionMapping.getOrderable();
		assertEquals(false, orderable.isOrderColumnOrdering());
		assertEquals(true, orderable.isNoOrdering());
		
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentAttribute.getJavaPersistentAttribute().getMapping();
		((Orderable2_0) javaElementCollectionMapping.getOrderable()).setOrderColumnOrdering(true);
				
		assertEquals(false, orderable.isOrderColumnOrdering());
		assertEquals(true, orderable.isNoOrdering());

		ormPersistentAttribute.makeVirtual();		
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		elementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		orderable = elementCollectionMapping.getOrderable();
		assertEquals(true, orderable.isOrderColumnOrdering());
		assertEquals(false, orderable.isNoOrdering());
		assertEquals(TYPE_NAME + "_addresses", orderable.getOrderColumn().getTable());
		assertEquals("addresses_ORDER", orderable.getOrderColumn().getName());
		
		((Orderable2_0) javaElementCollectionMapping.getOrderable()).getOrderColumn().setSpecifiedName("FOO");
		assertEquals(TYPE_NAME + "_addresses", orderable.getOrderColumn().getTable());
		assertEquals("FOO", orderable.getOrderColumn().getName());
	}

	public void testVirtualValueColumnDefaults() throws Exception {
		createTestEntityWithGenericBasicElementCollectionMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		//virtual attrubte in orm.xml, java attribute has no value Column annotation
		OrmPersistentAttribute addressesPersistentAttribute = ormPersistentType.virtualAttributes().next();
		OrmElementCollectionMapping2_0 addressesVirtualMapping = (OrmElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();		
		OrmColumn ormColumn = addressesVirtualMapping.getValueColumn();
		assertEquals("addresses", ormColumn.getSpecifiedName());
		assertEquals(TYPE_NAME + "_addresses", ormColumn.getSpecifiedTable());
		assertEquals(null, ormColumn.getColumnDefinition());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedInsertable());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedUnique());
		assertEquals(Column.DEFAULT_LENGTH, ormColumn.getSpecifiedLength().intValue());
		assertEquals(Column.DEFAULT_PRECISION, ormColumn.getSpecifiedPrecision().intValue());
		assertEquals(Column.DEFAULT_SCALE, ormColumn.getSpecifiedScale().intValue());
	
		//set Column annotation in Java
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaElementCollectionMapping.getValueColumn().setSpecifiedName("FOO");		
		javaElementCollectionMapping.getValueColumn().setSpecifiedTable("FOO_TABLE");
		javaElementCollectionMapping.getValueColumn().setColumnDefinition("COLUMN_DEFINITION");
		javaElementCollectionMapping.getValueColumn().setSpecifiedInsertable(Boolean.FALSE);	
		javaElementCollectionMapping.getValueColumn().setSpecifiedUpdatable(Boolean.FALSE);	
		javaElementCollectionMapping.getValueColumn().setSpecifiedNullable(Boolean.FALSE);	
		javaElementCollectionMapping.getValueColumn().setSpecifiedUnique(Boolean.TRUE);	
		javaElementCollectionMapping.getValueColumn().setSpecifiedLength(Integer.valueOf(45));
		javaElementCollectionMapping.getValueColumn().setSpecifiedPrecision(Integer.valueOf(46));
		javaElementCollectionMapping.getValueColumn().setSpecifiedScale(Integer.valueOf(47));

		assertEquals("FOO", ormColumn.getSpecifiedName());
		assertEquals("FOO_TABLE", ormColumn.getSpecifiedTable());
		assertEquals("COLUMN_DEFINITION", ormColumn.getColumnDefinition());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedNullable());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedUnique());
		assertEquals(Integer.valueOf(45), ormColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(46), ormColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(47), ormColumn.getSpecifiedScale());

	
		//set metadata-complete, orm.xml virtual column ignores java column annotation
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		addressesPersistentAttribute = ormPersistentType.virtualAttributes().next();
		//no longer an element collection mapping
		assertEquals(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY, addressesPersistentAttribute.getMappingKey());
	}
	
	public void testNullColumnDefaults() throws Exception {
		createTestEntityWithGenericBasicElementCollectionMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute addressesPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "addresses");

		OrmElementCollectionMapping2_0 addressesVirtualMapping = (OrmElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();		
		OrmColumn ormColumn = addressesVirtualMapping.getValueColumn();
	
		//set Column annotation in Java
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaElementCollectionMapping.getValueColumn().setSpecifiedName("FOO");		
		javaElementCollectionMapping.getValueColumn().setSpecifiedTable("FOO_TABLE");
		javaElementCollectionMapping.getValueColumn().setColumnDefinition("COLUMN_DEFINITION");
		javaElementCollectionMapping.getValueColumn().setSpecifiedInsertable(Boolean.FALSE);	
		javaElementCollectionMapping.getValueColumn().setSpecifiedUpdatable(Boolean.FALSE);	
		javaElementCollectionMapping.getValueColumn().setSpecifiedNullable(Boolean.FALSE);	
		javaElementCollectionMapping.getValueColumn().setSpecifiedUnique(Boolean.TRUE);	
		javaElementCollectionMapping.getValueColumn().setSpecifiedLength(Integer.valueOf(45));
		javaElementCollectionMapping.getValueColumn().setSpecifiedPrecision(Integer.valueOf(46));
		javaElementCollectionMapping.getValueColumn().setSpecifiedScale(Integer.valueOf(47));

	
		assertEquals("addresses", ormColumn.getDefaultName());
		assertEquals(TYPE_NAME + "_addresses", ormColumn.getDefaultTable());
		assertEquals(true, ormColumn.isDefaultInsertable());
		assertEquals(true, ormColumn.isDefaultUpdatable());
		assertEquals(true, ormColumn.isDefaultNullable());
		assertEquals(false, ormColumn.isDefaultUnique());
		assertEquals(Column.DEFAULT_LENGTH, ormColumn.getDefaultLength());
		assertEquals(Column.DEFAULT_PRECISION, ormColumn.getDefaultPrecision());
		assertEquals(Column.DEFAULT_SCALE, ormColumn.getDefaultScale());
		assertNull(ormColumn.getSpecifiedName());
		assertNull(ormColumn.getSpecifiedTable());
		assertNull(ormColumn.getColumnDefinition());
		assertNull(ormColumn.getSpecifiedInsertable());
		assertNull(ormColumn.getSpecifiedUpdatable());
		assertNull(ormColumn.getSpecifiedNullable());
		assertNull(ormColumn.getSpecifiedUnique());
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(ormColumn.getSpecifiedPrecision());
		assertNull(ormColumn.getSpecifiedScale());
	}

	public void testVirtualValueColumnTable() throws Exception {
		createTestEntityWithGenericBasicElementCollectionMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		//virtual attribute in orm.xml, java attribute has no Column annotation
		OrmPersistentAttribute addressesPersistentAttribute = ormPersistentType.virtualAttributes().next();
		OrmElementCollectionMapping2_0 addressesVirtualMapping = (OrmElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();	
		OrmColumn ormColumn = addressesVirtualMapping.getValueColumn();
		
		assertEquals(TYPE_NAME + "_addresses", ormColumn.getSpecifiedTable());
	
		//entity table should have no affect on the collection table default name
		((OrmEntity) ormPersistentType.getMapping()).getTable().setSpecifiedName("ORM_TABLE");
		assertEquals(TYPE_NAME + "_addresses", ormColumn.getSpecifiedTable());
		
		//set Column table element in Java
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaElementCollectionMapping.getCollectionTable().setSpecifiedName("JAVA_COLLECTION_TABLE");
		assertEquals("JAVA_COLLECTION_TABLE", ormColumn.getSpecifiedTable());
		javaElementCollectionMapping.getValueColumn().setSpecifiedTable("JAVA_TABLE");	
		assertEquals("JAVA_TABLE", ormColumn.getSpecifiedTable());
		
		//make name persistent attribute not virtual
		addressesPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "addresses");
		addressesVirtualMapping = (OrmElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();	
		ormColumn = addressesVirtualMapping.getValueColumn();
		assertNull(ormColumn.getSpecifiedTable());
		assertEquals(TYPE_NAME + "_addresses", ormColumn.getDefaultTable());
	}
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		OrmAttributeOverrideContainer attributeOverrideContainer = ormElementCollectionMapping.getValueAttributeOverrideContainer();
		XmlElementCollection_2_0 elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		elementCollectionResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		elementCollectionResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		elementCollectionResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		
		elementCollectionResource.getAttributeOverrides().get(0).setName("FOO");
		elementCollectionResource.getAttributeOverrides().get(1).setName("BAR");
		elementCollectionResource.getAttributeOverrides().get(2).setName("BAZ");
		
		assertEquals(3, elementCollectionResource.getAttributeOverrides().size());		
		
		attributeOverrideContainer.moveSpecifiedAttributeOverride(2, 0);
		ListIterator<OrmAttributeOverride> attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAR", elementCollectionResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAZ", elementCollectionResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", elementCollectionResource.getAttributeOverrides().get(2).getName());


		attributeOverrideContainer.moveSpecifiedAttributeOverride(0, 1);
		attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAZ", elementCollectionResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAR", elementCollectionResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", elementCollectionResource.getAttributeOverrides().get(2).getName());
	}
	
	public void testUpdateAttributeOverrides() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "elementCollectionMapping");
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		OrmAttributeOverrideContainer attributeOverrideContainer = ormElementCollectionMapping.getValueAttributeOverrideContainer();
		XmlElementCollection_2_0 elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		elementCollectionResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		elementCollectionResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		elementCollectionResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		
		elementCollectionResource.getAttributeOverrides().get(0).setName("FOO");
		elementCollectionResource.getAttributeOverrides().get(1).setName("BAR");
		elementCollectionResource.getAttributeOverrides().get(2).setName("BAZ");

		ListIterator<OrmAttributeOverride> attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		elementCollectionResource.getAttributeOverrides().move(2, 0);
		attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		elementCollectionResource.getAttributeOverrides().move(0, 1);
		attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		elementCollectionResource.getAttributeOverrides().remove(1);
		attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		elementCollectionResource.getAttributeOverrides().remove(1);
		attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		elementCollectionResource.getAttributeOverrides().remove(0);
		assertFalse(attributeOverrideContainer.specifiedAttributeOverrides().hasNext());
	}

	public void testElementCollectionMappingNoUnderylingJavaAttribute() throws Exception {
		createTestEntityWithEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "foo");
		assertEquals(3, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		OrmAttributeOverrideContainer attributeOverrideContainer = ormElementCollectionMapping.getValueAttributeOverrideContainer();
		
		assertEquals("foo", ormElementCollectionMapping.getName());

		
		assertFalse(attributeOverrideContainer.specifiedAttributeOverrides().hasNext());
		assertFalse(attributeOverrideContainer.virtualAttributeOverrides().hasNext());
	}
	
	public void testVirtualAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType persistentType2 = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		OrmPersistentType persistentType3 = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		
		//embedded mapping is virtual, specified attribute overrides should exist
		OrmPersistentAttribute ormPersistentAttribute = persistentType.getAttributeNamed("addresses");
		OrmElementCollectionMapping2_0 elementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		OrmAttributeOverrideContainer attributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		assertEquals(4, attributeOverrideContainer.attributeOverridesSize());
		assertEquals(0, attributeOverrideContainer.virtualAttributeOverridesSize());
		assertEquals(4, attributeOverrideContainer.specifiedAttributeOverridesSize());
		ListIterator<OrmAttributeOverride> specifiedAttributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		OrmAttributeOverride attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("city", attributeOverride.getName());
		attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("zip", attributeOverride.getName());
		attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("state.name", attributeOverride.getName());
		attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("state.abbr", attributeOverride.getName());
		
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentAttribute.getJavaPersistentAttribute().getMapping();
		Column javaAttributeOverrideColumn = javaElementCollectionMapping.getValueAttributeOverrideContainer().specifiedAttributeOverrides().next().getColumn();
		
		javaAttributeOverrideColumn.setSpecifiedName("FOO_COLUMN");
		javaAttributeOverrideColumn.setSpecifiedTable("FOO_TABLE");
		javaAttributeOverrideColumn.setColumnDefinition("COLUMN_DEF");
		javaAttributeOverrideColumn.setSpecifiedInsertable(Boolean.FALSE);
		javaAttributeOverrideColumn.setSpecifiedUpdatable(Boolean.FALSE);
		javaAttributeOverrideColumn.setSpecifiedUnique(Boolean.TRUE);
		javaAttributeOverrideColumn.setSpecifiedNullable(Boolean.FALSE);
		javaAttributeOverrideColumn.setSpecifiedLength(Integer.valueOf(5));
		javaAttributeOverrideColumn.setSpecifiedPrecision(Integer.valueOf(6));
		javaAttributeOverrideColumn.setSpecifiedScale(Integer.valueOf(7));

		JavaBasicMapping javaBasicMapping = (JavaBasicMapping) persistentType3.getJavaPersistentType().getAttributeNamed("name").getMapping();
		javaBasicMapping.getColumn().setSpecifiedName("MY_STATE_COLUMN");
		assertEquals(4, attributeOverrideContainer.attributeOverridesSize());
		assertEquals(0, attributeOverrideContainer.virtualAttributeOverridesSize());
		assertEquals(4, attributeOverrideContainer.specifiedAttributeOverridesSize());
		specifiedAttributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("city", attributeOverride.getName());
		assertEquals("FOO_COLUMN", attributeOverride.getColumn().getSpecifiedName());
		assertEquals("FOO_TABLE", attributeOverride.getColumn().getSpecifiedTable());
		assertEquals("COLUMN_DEF", attributeOverride.getColumn().getColumnDefinition());
		assertEquals(false, attributeOverride.getColumn().isInsertable());
		assertEquals(false, attributeOverride.getColumn().isUpdatable());
		assertEquals(true, attributeOverride.getColumn().isUnique());
		assertEquals(false, attributeOverride.getColumn().isNullable());
		assertEquals(5, attributeOverride.getColumn().getLength());
		assertEquals(6, attributeOverride.getColumn().getPrecision());
		assertEquals(7, attributeOverride.getColumn().getScale());
		
		attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("zip", attributeOverride.getName());
		attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("state.name", attributeOverride.getName());
		assertEquals("MY_STATE_COLUMN", attributeOverride.getColumn().getSpecifiedName());
		attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("state.abbr", attributeOverride.getName());
		
		
		
		//embedded mapping is specified, virtual attribute overrides should exist
		persistentType.getAttributeNamed("addresses").makeSpecified();
		elementCollectionMapping = (OrmElementCollectionMapping2_0) persistentType.getAttributeNamed("addresses").getMapping();
		attributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		assertEquals(4, attributeOverrideContainer.attributeOverridesSize());
		assertEquals(4, attributeOverrideContainer.virtualAttributeOverridesSize());
		assertEquals(0, attributeOverrideContainer.specifiedAttributeOverridesSize());
		ListIterator<OrmAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.virtualAttributeOverrides();
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("city", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("state.name", attributeOverride.getName());
		assertEquals("MY_STATE_COLUMN", attributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME + "_addresses", attributeOverride.getColumn().getTable());
		assertEquals(null, attributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, attributeOverride.getColumn().isInsertable());
		assertEquals(true, attributeOverride.getColumn().isUpdatable());
		assertEquals(false, attributeOverride.getColumn().isUnique());
		assertEquals(true, attributeOverride.getColumn().isNullable());
		assertEquals(255, attributeOverride.getColumn().getLength());
		assertEquals(0, attributeOverride.getColumn().getPrecision());
		assertEquals(0, attributeOverride.getColumn().getScale());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("state.abbr", attributeOverride.getName());
		assertEquals(TYPE_NAME + "_addresses", attributeOverride.getColumn().getDefaultTable());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("zip", attributeOverride.getName());
		
		//set one of the virtual attribute overrides to specified, verify others are still virtual
		attributeOverrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		
		assertEquals(4, attributeOverrideContainer.attributeOverridesSize());
		assertEquals(1, attributeOverrideContainer.specifiedAttributeOverridesSize());
		assertEquals(3, attributeOverrideContainer.virtualAttributeOverridesSize());
		assertEquals("city", attributeOverrideContainer.specifiedAttributeOverrides().next().getName());
		virtualAttributeOverrides = attributeOverrideContainer.virtualAttributeOverrides();
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("state.name", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("state.abbr", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("zip", attributeOverride.getName());
	}
	public void testVirtualMapKeyColumnDefaults() throws Exception {
		createTestEntityWithValidMapElementCollectionMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);

		//virtual attribute in orm.xml, java attribute has no value Column annotation
		OrmPersistentAttribute addressesPersistentAttribute = ormPersistentType.virtualAttributes().next();
		OrmElementCollectionMapping2_0 addressesVirtualMapping = (OrmElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();		
		Column ormColumn = addressesVirtualMapping.getMapKeyColumn();
		assertEquals("addresses_KEY", ormColumn.getSpecifiedName());
		assertEquals(TYPE_NAME + "_addresses", ormColumn.getSpecifiedTable());
		assertEquals(null, ormColumn.getColumnDefinition());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedInsertable());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedUnique());
		assertEquals(Column.DEFAULT_LENGTH, ormColumn.getSpecifiedLength().intValue());
		assertEquals(Column.DEFAULT_PRECISION, ormColumn.getSpecifiedPrecision().intValue());
		assertEquals(Column.DEFAULT_SCALE, ormColumn.getSpecifiedScale().intValue());

		//set Column annotation in Java
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedName("FOO");		
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedTable("FOO_TABLE");
		javaElementCollectionMapping.getMapKeyColumn().setColumnDefinition("COLUMN_DEFINITION");
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedInsertable(Boolean.FALSE);	
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedUpdatable(Boolean.FALSE);	
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedNullable(Boolean.FALSE);	
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedUnique(Boolean.TRUE);	
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedLength(Integer.valueOf(45));
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedPrecision(Integer.valueOf(46));
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedScale(Integer.valueOf(47));

		assertEquals("FOO", ormColumn.getSpecifiedName());
		assertEquals("FOO_TABLE", ormColumn.getSpecifiedTable());
		assertEquals("COLUMN_DEFINITION", ormColumn.getColumnDefinition());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedNullable());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedUnique());
		assertEquals(Integer.valueOf(45), ormColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(46), ormColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(47), ormColumn.getSpecifiedScale());


		//set metadata-complete, orm.xml virtual column ignores java column annotation
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		addressesPersistentAttribute = ormPersistentType.virtualAttributes().next();
		//no longer an element collection mapping
		assertEquals(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY, addressesPersistentAttribute.getMappingKey());
	}
	
	public void testNullMapKeyColumnDefaults() throws Exception {
		createTestEntityWithValidMapElementCollectionMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute addressesPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "addresses");

		OrmElementCollectionMapping2_0 addressesVirtualMapping = (OrmElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();		
		Column ormColumn = addressesVirtualMapping.getMapKeyColumn();

		//set Column annotation in Java
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedName("FOO");		
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedTable("FOO_TABLE");
		javaElementCollectionMapping.getMapKeyColumn().setColumnDefinition("COLUMN_DEFINITION");
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedInsertable(Boolean.FALSE);	
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedUpdatable(Boolean.FALSE);	
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedNullable(Boolean.FALSE);	
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedUnique(Boolean.TRUE);	
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedLength(Integer.valueOf(45));
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedPrecision(Integer.valueOf(46));
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedScale(Integer.valueOf(47));


		assertEquals("addresses_KEY", ormColumn.getDefaultName());
		assertEquals(TYPE_NAME + "_addresses", ormColumn.getDefaultTable());
		assertEquals(true, ormColumn.isDefaultInsertable());
		assertEquals(true, ormColumn.isDefaultUpdatable());
		assertEquals(true, ormColumn.isDefaultNullable());
		assertEquals(false, ormColumn.isDefaultUnique());
		assertEquals(Column.DEFAULT_LENGTH, ormColumn.getDefaultLength());
		assertEquals(Column.DEFAULT_PRECISION, ormColumn.getDefaultPrecision());
		assertEquals(Column.DEFAULT_SCALE, ormColumn.getDefaultScale());
		assertNull(ormColumn.getSpecifiedName());
		assertNull(ormColumn.getSpecifiedTable());
		assertNull(ormColumn.getColumnDefinition());
		assertNull(ormColumn.getSpecifiedInsertable());
		assertNull(ormColumn.getSpecifiedUpdatable());
		assertNull(ormColumn.getSpecifiedNullable());
		assertNull(ormColumn.getSpecifiedUnique());
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(ormColumn.getSpecifiedPrecision());
		assertNull(ormColumn.getSpecifiedScale());
	}

	public void testVirtualMapKeyColumnTable() throws Exception {
		createTestEntityWithValidMapElementCollectionMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);

		//virtual attribute in orm.xml, java attribute has no Column annotation
		OrmPersistentAttribute addressesPersistentAttribute = ormPersistentType.virtualAttributes().next();
		OrmElementCollectionMapping2_0 addressesVirtualMapping = (OrmElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();	
		Column ormColumn = addressesVirtualMapping.getMapKeyColumn();

		assertEquals(TYPE_NAME + "_addresses", ormColumn.getSpecifiedTable());

		//entity table should have no affect on the collection table default name
		((OrmEntity) ormPersistentType.getMapping()).getTable().setSpecifiedName("ORM_TABLE");
		assertEquals(TYPE_NAME + "_addresses", ormColumn.getSpecifiedTable());

		//set Column table element in Java
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaElementCollectionMapping.getCollectionTable().setSpecifiedName("JAVA_COLLECTION_TABLE");
		assertEquals("JAVA_COLLECTION_TABLE", ormColumn.getSpecifiedTable());
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedTable("JAVA_TABLE");	
		assertEquals("JAVA_TABLE", ormColumn.getSpecifiedTable());

		//make name persistent attribute not virtual
		addressesPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY, "addresses");
		addressesVirtualMapping = (OrmElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();	
		ormColumn = addressesVirtualMapping.getMapKeyColumn();
		assertNull(ormColumn.getSpecifiedTable());
		assertEquals(TYPE_NAME + "_addresses", ormColumn.getDefaultTable());
	}
}
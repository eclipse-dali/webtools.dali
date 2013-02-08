/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.EnumType;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyColumn;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.context.VirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlElementCollection_2_0;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericOrmElementCollectionMapping2_0Tests extends Generic2_0ContextModelTestCase
{
	private static final String ATTRIBUTE_OVERRIDE_NAME = "city";
	private static final String ATTRIBUTE_OVERRIDE_COLUMN_NAME = "E_CITY";
	
	public GenericOrmElementCollectionMapping2_0Tests(String name) {
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
	
	private ICompilationUnit createTestEntityWithElementCollectionMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA2_0.ELEMENT_COLLECTION, JPA.FETCH_TYPE);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA2_0.ELEMENT_COLLECTION, JPA.FETCH_TYPE, JPA.ATTRIBUTE_OVERRIDE, JPA.COLUMN);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA2_0.ELEMENT_COLLECTION, JPA.ID);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA2_0.ELEMENT_COLLECTION, JPA.ID);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA2_0.ELEMENT_COLLECTION, JPA.ID);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA2_0.ELEMENT_COLLECTION, JPA.ID);
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

	private void createSelfReferentialElementCollection() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
					sb.append("import ");
					sb.append(JPA.EMBEDDABLE);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA2_0.ELEMENT_COLLECTION);
					sb.append(";");
					sb.append(CR).append(CR);
				sb.append("@Embeddable");
				sb.append(CR);
				sb.append("public class ").append("Foo").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @ElementCollection").append(CR);
				sb.append("    private java.util.List<Foo> elementCollection;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Foo.java", sourceWriter);
	}
	
	public void testUpdateName() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection elementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertEquals("id", ormElementCollectionMapping.getName());
		assertEquals("id", elementCollection.getName());
				
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection elementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertEquals("id", ormElementCollectionMapping.getName());
		assertEquals("id", elementCollection.getName());
				
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
		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("address"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		ormElementCollectionMapping.setName("foo");
		assertEquals("foo", ormElementCollectionMapping.getName());

		assertNull(ormElementCollectionMapping.getSpecifiedFetch());
		assertEquals(FetchType.LAZY, ormElementCollectionMapping.getFetch());
	}
	
	
	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityWithElementCollectionMapping();
		createTestTargetEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		
		ElementCollectionMapping2_0 virtualElementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertEquals("address", virtualElementCollectionMapping.getName());
		assertEquals(FetchType.EAGER, virtualElementCollectionMapping.getSpecifiedFetch());
		assertEquals("String", virtualElementCollectionMapping.getSpecifiedTargetClass());
		
		ormPersistentAttribute = ormPersistentAttribute.addToXml();
		OrmElementCollectionMapping2_0 specifiedElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();	
		assertEquals("address", specifiedElementCollectionMapping.getName());
		assertEquals(null, specifiedElementCollectionMapping.getSpecifiedFetch());
		assertEquals(FetchType.LAZY, specifiedElementCollectionMapping.getDefaultFetch());
		assertEquals(null, specifiedElementCollectionMapping.getSpecifiedTargetClass());
		assertEquals(PACKAGE_NAME + ".Address", specifiedElementCollectionMapping.getDefaultTargetClass());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityWithElementCollectionMapping();
		createTestTargetEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("address");
		
		assertEquals(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
		
		ormPersistentAttribute.addToXml(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		ormPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();

		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();	
		assertEquals("address", ormElementCollectionMapping.getName());
		assertEquals(FetchType.LAZY, ormElementCollectionMapping.getFetch());
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		createTestTargetEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("address"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		
		assertEquals("address", ormElementCollectionMapping.getName());
		assertNull(ormElementCollectionMapping.getSpecifiedFetch());
		assertEquals(FetchType.LAZY, ormElementCollectionMapping.getFetch());
	}
	
	
	public void testMorphToIdMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);

		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);

		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testMorphToManyToManyMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
//		assertEquals(FetchType.EAGER, ((ManyToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
//		assertEquals(FetchType.EAGER, ((OneToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
//		assertEquals(FetchType.EAGER, ((ManyToOneMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
//TODO	assertEquals(FetchType.EAGER, ((IBasicMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection elementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getSpecifiedFetch());
		assertNull(elementCollection.getFetch());
				
		//set fetch in the resource model, verify context model updated
		elementCollection.setFetch(org.eclipse.jpt.jpa.core.resource.orm.FetchType.EAGER);
		assertEquals(FetchType.EAGER, ormElementCollectionMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.FetchType.EAGER, elementCollection.getFetch());
	
		elementCollection.setFetch(org.eclipse.jpt.jpa.core.resource.orm.FetchType.LAZY);
		assertEquals(FetchType.LAZY, ormElementCollectionMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.FetchType.LAZY, elementCollection.getFetch());

		//set fetch to null in the resource model
		elementCollection.setFetch(null);
		assertNull(ormElementCollectionMapping.getSpecifiedFetch());
		assertNull(elementCollection.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection elementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getSpecifiedFetch());
		assertNull(elementCollection.getFetch());
				
		//set fetch in the context model, verify resource model updated
		ormElementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.FetchType.EAGER, elementCollection.getFetch());
		assertEquals(FetchType.EAGER, ormElementCollectionMapping.getSpecifiedFetch());
	
		ormElementCollectionMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.FetchType.LAZY, elementCollection.getFetch());
		assertEquals(FetchType.LAZY, ormElementCollectionMapping.getSpecifiedFetch());

		//set fetch to null in the context model
		ormElementCollectionMapping.setSpecifiedFetch(null);
		assertNull(elementCollection.getFetch());
		assertNull(ormElementCollectionMapping.getSpecifiedFetch());
	}	
	
	public void testUpdateSpecifiedTargetClass() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
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

		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();

		assertEquals(ElementCollectionMapping2_0.Type.EMBEDDABLE_TYPE, elementCollectionMapping.getValueType());
	}
	
	public void testGetValueTypeEntity() throws Exception {
		createTestEntityWithEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.Address");

		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();

		assertEquals(ElementCollectionMapping2_0.Type.BASIC_TYPE, elementCollectionMapping.getValueType());
	}
	
	public void testGetValueTypeNone() throws Exception {
		createTestEntityWithNonGenericElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "test.Address");
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
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

		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();

		assertEquals(ElementCollectionMapping2_0.Type.BASIC_TYPE, elementCollectionMapping.getValueType());
	}
	
	
	public void testUpdateMapKey() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		ElementCollectionMapping2_0 ormElementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 elementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getSpecifiedMapKey());
		assertNull(elementCollection.getMapKey());
		
		//set mapKey in the resource model, verify context model does not change
		elementCollection.setMapKey(OrmFactory.eINSTANCE.createMapKey());
		assertNull(ormElementCollectionMapping.getSpecifiedMapKey());
		assertNotNull(elementCollection.getMapKey());
				
		//set mapKey name in the resource model, verify context model updated
		elementCollection.getMapKey().setName("myMapKey");
		assertEquals("myMapKey", ormElementCollectionMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", elementCollection.getMapKey().getName());
		
		//set mapKey name to null in the resource model
		elementCollection.getMapKey().setName(null);
		assertNull(ormElementCollectionMapping.getSpecifiedMapKey());
		assertNull(elementCollection.getMapKey().getName());
		
		elementCollection.getMapKey().setName("myMapKey");
		elementCollection.setMapKey(null);
		assertNull(ormElementCollectionMapping.getSpecifiedMapKey());
		assertNull(elementCollection.getMapKey());
	}
	
	public void testUpdateVirtualMapKey() throws Exception {
		createTestEntityWithValidMapElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");

		ElementCollectionMapping2_0 virtualElementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		assertNull(virtualElementCollectionMapping.getSpecifiedMapKey());
		assertNull(virtualElementCollectionMapping.getMapKey());
		assertFalse(virtualElementCollectionMapping.isPkMapKey());
		assertFalse(virtualElementCollectionMapping.isCustomMapKey());
		assertTrue(virtualElementCollectionMapping.isNoMapKey());
		
		//set pk mapKey in the java, verify virtual orm mapping updates
		javaElementCollectionMapping.setPkMapKey(true);
		assertEquals(null, virtualElementCollectionMapping.getMapKey());//no primary key on an embeddable
		assertTrue(virtualElementCollectionMapping.isPkMapKey());
		assertFalse(virtualElementCollectionMapping.isCustomMapKey());
		assertFalse(virtualElementCollectionMapping.isNoMapKey());
		
		
		//set custom specified mapKey in the java, verify virtual orm mapping updates
		javaElementCollectionMapping.setCustomMapKey(true);
		javaElementCollectionMapping.setSpecifiedMapKey("city");
		assertEquals("city", virtualElementCollectionMapping.getSpecifiedMapKey());
		assertEquals("city", virtualElementCollectionMapping.getMapKey());
		assertFalse(virtualElementCollectionMapping.isPkMapKey());
		assertTrue(virtualElementCollectionMapping.isCustomMapKey());
		assertFalse(virtualElementCollectionMapping.isNoMapKey());
	}
	
	public void testModifyMapKey() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		ElementCollectionMapping2_0 ormElementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 xmlElementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getSpecifiedMapKey());
		assertNull(xmlElementCollection.getMapKey());
					
		//set mapKey  in the context model, verify resource model updated
		ormElementCollectionMapping.setSpecifiedMapKey("myMapKey");
		assertEquals("myMapKey", ormElementCollectionMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", xmlElementCollection.getMapKey().getName());
	
		//set mapKey to null in the context model
		ormElementCollectionMapping.setSpecifiedMapKey(null);
		assertNull(ormElementCollectionMapping.getSpecifiedMapKey());
		assertTrue(ormElementCollectionMapping.isPkMapKey());
	}
	
	public void testCandidateMapKeyNames() throws Exception {
		createTestEntityWithValidMapElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		
		ElementCollectionMapping2_0 virtualElementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentType.getAttributeNamed("addresses").getMapping();

		Iterator<String> mapKeyNames = virtualElementCollectionMapping.getCandidateMapKeyNames().iterator();
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
		
		ElementCollectionMapping2_0 virtualElementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();

		Iterator<String> mapKeyNames = virtualElementCollectionMapping.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
		
		javaElementCollectionMapping.setSpecifiedTargetClass("test.Address");
		mapKeyNames = virtualElementCollectionMapping.getCandidateMapKeyNames().iterator();
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.name", mapKeyNames.next());
		assertEquals("state.abbr", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		ormPersistentType.getAttributeNamed("addresses").addToXml();
		virtualElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentType.getAttributeNamed("addresses").getMapping();
		mapKeyNames = virtualElementCollectionMapping.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
		
		virtualElementCollectionMapping.setSpecifiedTargetClass("test.Address");
		mapKeyNames = virtualElementCollectionMapping.getCandidateMapKeyNames().iterator();
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.name", mapKeyNames.next());
		assertEquals("state.abbr", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		virtualElementCollectionMapping.setSpecifiedTargetClass("String");
		mapKeyNames = virtualElementCollectionMapping.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
	}
	
	public void testUpdateMapKeyClass() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
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

		ElementCollectionMapping2_0 virtualElementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentType.getAttributeNamed("addresses").getMapping();
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		assertEquals("java.lang.String", virtualElementCollectionMapping.getMapKeyClass());
		assertNull(virtualElementCollectionMapping.getSpecifiedMapKeyClass());
		assertEquals("java.lang.String", virtualElementCollectionMapping.getDefaultMapKeyClass());
		
		//set pk mapKey in the java, verify virtual orm mapping updates
		javaElementCollectionMapping.setSpecifiedMapKeyClass("Integer");
		assertEquals("Integer", virtualElementCollectionMapping.getMapKeyClass());
		assertEquals("Integer", virtualElementCollectionMapping.getSpecifiedMapKeyClass());
		assertEquals("java.lang.String", virtualElementCollectionMapping.getDefaultMapKeyClass());
	}
	
	public void testModifyMapKeyClass() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
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
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 elementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();

		Orderable2_0 orderable = (Orderable2_0) elementCollectionMapping.getOrderable();
		assertEquals(false, orderable.isOrderColumnOrdering());
		assertEquals(true, orderable.isNoOrdering());
		
		orderable.setOrderColumnOrdering(true);
		OrderColumn2_0 orderColumn = orderable.getOrderColumn();
		assertEquals(true, orderable.isOrderColumnOrdering());
		assertEquals(null, orderColumn.getSpecifiedName());
		assertEquals("addresses_ORDER", orderColumn.getDefaultName());
		assertEquals(TYPE_NAME + "_addresses", orderColumn.getTableName());
		
		orderColumn.setSpecifiedName("FOO");
		assertEquals("FOO", orderColumn.getSpecifiedName());
		assertEquals("addresses_ORDER", orderColumn.getDefaultName());
		assertEquals(TYPE_NAME + "_addresses", orderColumn.getTableName());
	}
	
	public void testVirtualOrderColumn() throws Exception {
		createTestEntityWithGenericBasicElementCollectionMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 elementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();

		Orderable2_0 orderable = (Orderable2_0) elementCollectionMapping.getOrderable();
		assertEquals(false, orderable.isOrderColumnOrdering());
		assertEquals(true, orderable.isNoOrdering());
		
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentAttribute.getJavaPersistentAttribute().getMapping();
		((Orderable2_0) javaElementCollectionMapping.getOrderable()).setOrderColumnOrdering(true);
				
		assertEquals(false, orderable.isOrderColumnOrdering());
		assertEquals(true, orderable.isNoOrdering());

		ormPersistentAttribute.removeFromXml();		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("addresses");
		ElementCollectionMapping2_0 virtualCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute2.getMapping();
		orderable = (Orderable2_0) virtualCollectionMapping.getOrderable();
		assertEquals(true, orderable.isOrderColumnOrdering());
		assertEquals(false, orderable.isNoOrdering());
		assertEquals(TYPE_NAME + "_addresses", orderable.getOrderColumn().getTableName());
		assertEquals("addresses_ORDER", orderable.getOrderColumn().getName());
		
		((Orderable2_0) javaElementCollectionMapping.getOrderable()).getOrderColumn().setSpecifiedName("FOO");
		assertEquals(TYPE_NAME + "_addresses", orderable.getOrderColumn().getTableName());
		assertEquals("FOO", orderable.getOrderColumn().getName());
	}

	public void testVirtualValueColumnDefaults() throws Exception {
		createTestEntityWithGenericBasicElementCollectionMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		//virtual attrubte in orm.xml, java attribute has no value Column annotation
		OrmReadOnlyPersistentAttribute addressesPersistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		ElementCollectionMapping2_0 addressesVirtualMapping = (ElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();		
		Column virtualColumn = addressesVirtualMapping.getValueColumn();
		assertEquals("addresses", virtualColumn.getName());
		assertEquals(TYPE_NAME + "_addresses", virtualColumn.getTableName());
		assertEquals(null, virtualColumn.getColumnDefinition());
		assertTrue(virtualColumn.isInsertable());
		assertTrue(virtualColumn.isUpdatable());
		assertTrue(virtualColumn.isNullable());
		assertFalse(virtualColumn.isUnique());
		assertEquals(ReadOnlyColumn.DEFAULT_LENGTH, virtualColumn.getLength());
		assertEquals(ReadOnlyColumn.DEFAULT_PRECISION, virtualColumn.getPrecision());
		assertEquals(ReadOnlyColumn.DEFAULT_SCALE, virtualColumn.getScale());
	
		//set Column annotation in Java
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaElementCollectionMapping.getValueColumn().setSpecifiedName("FOO");		
		javaElementCollectionMapping.getValueColumn().setSpecifiedTableName("FOO_TABLE");
		javaElementCollectionMapping.getValueColumn().setColumnDefinition("COLUMN_DEFINITION");
		javaElementCollectionMapping.getValueColumn().setSpecifiedInsertable(Boolean.FALSE);	
		javaElementCollectionMapping.getValueColumn().setSpecifiedUpdatable(Boolean.FALSE);	
		javaElementCollectionMapping.getValueColumn().setSpecifiedNullable(Boolean.FALSE);	
		javaElementCollectionMapping.getValueColumn().setSpecifiedUnique(Boolean.TRUE);	
		javaElementCollectionMapping.getValueColumn().setSpecifiedLength(Integer.valueOf(45));
		javaElementCollectionMapping.getValueColumn().setSpecifiedPrecision(Integer.valueOf(46));
		javaElementCollectionMapping.getValueColumn().setSpecifiedScale(Integer.valueOf(47));

		assertEquals("FOO", virtualColumn.getSpecifiedName());
		assertEquals("FOO_TABLE", virtualColumn.getSpecifiedTableName());
		assertEquals("COLUMN_DEFINITION", virtualColumn.getColumnDefinition());
		assertEquals(Boolean.FALSE, virtualColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, virtualColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.FALSE, virtualColumn.getSpecifiedNullable());
		assertEquals(Boolean.TRUE, virtualColumn.getSpecifiedUnique());
		assertEquals(Integer.valueOf(45), virtualColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(46), virtualColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(47), virtualColumn.getSpecifiedScale());

	
		//set metadata-complete, orm.xml virtual column ignores java column annotation
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		addressesPersistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		//no longer an element collection mapping
		assertEquals(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY, addressesPersistentAttribute.getMappingKey());
	}
	
	public void testNullColumnDefaults() throws Exception {
		createTestEntityWithGenericBasicElementCollectionMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute addressesPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);

		OrmElementCollectionMapping2_0 addressesVirtualMapping = (OrmElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();		
		OrmColumn ormColumn = addressesVirtualMapping.getValueColumn();
	
		//set Column annotation in Java
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaElementCollectionMapping.getValueColumn().setSpecifiedName("FOO");		
		javaElementCollectionMapping.getValueColumn().setSpecifiedTableName("FOO_TABLE");
		javaElementCollectionMapping.getValueColumn().setColumnDefinition("COLUMN_DEFINITION");
		javaElementCollectionMapping.getValueColumn().setSpecifiedInsertable(Boolean.FALSE);	
		javaElementCollectionMapping.getValueColumn().setSpecifiedUpdatable(Boolean.FALSE);	
		javaElementCollectionMapping.getValueColumn().setSpecifiedNullable(Boolean.FALSE);	
		javaElementCollectionMapping.getValueColumn().setSpecifiedUnique(Boolean.TRUE);	
		javaElementCollectionMapping.getValueColumn().setSpecifiedLength(Integer.valueOf(45));
		javaElementCollectionMapping.getValueColumn().setSpecifiedPrecision(Integer.valueOf(46));
		javaElementCollectionMapping.getValueColumn().setSpecifiedScale(Integer.valueOf(47));

	
		assertEquals("addresses", ormColumn.getDefaultName());
		assertEquals(TYPE_NAME + "_addresses", ormColumn.getDefaultTableName());
		assertEquals(true, ormColumn.isDefaultInsertable());
		assertEquals(true, ormColumn.isDefaultUpdatable());
		assertEquals(true, ormColumn.isDefaultNullable());
		assertEquals(false, ormColumn.isDefaultUnique());
		assertEquals(ReadOnlyColumn.DEFAULT_LENGTH, ormColumn.getDefaultLength());
		assertEquals(ReadOnlyColumn.DEFAULT_PRECISION, ormColumn.getDefaultPrecision());
		assertEquals(ReadOnlyColumn.DEFAULT_SCALE, ormColumn.getDefaultScale());
		assertNull(ormColumn.getSpecifiedName());
		assertNull(ormColumn.getSpecifiedTableName());
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
		OrmReadOnlyPersistentAttribute addressesPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		ElementCollectionMapping2_0 addressesVirtualMapping = (ElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();	
		Column virtualColumn = addressesVirtualMapping.getValueColumn();
		
		assertEquals(TYPE_NAME + "_addresses", virtualColumn.getTableName());
	
		//entity table should have no affect on the collection table default name
		((OrmEntity) ormPersistentType.getMapping()).getTable().setSpecifiedName("ORM_TABLE");
		assertEquals(TYPE_NAME + "_addresses", virtualColumn.getTableName());
		
		//set Column table element in Java
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaElementCollectionMapping.getCollectionTable().setSpecifiedName("JAVA_COLLECTION_TABLE");
		assertEquals("JAVA_COLLECTION_TABLE", virtualColumn.getTableName());
		javaElementCollectionMapping.getValueColumn().setSpecifiedTableName("JAVA_TABLE");	
		assertEquals("JAVA_TABLE", virtualColumn.getTableName());
		
		//make name persistent attribute not default
		addressesPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		addressesVirtualMapping = (OrmElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();	
		virtualColumn = addressesVirtualMapping.getValueColumn();
		assertNull(virtualColumn.getSpecifiedTableName());
		assertEquals(TYPE_NAME + "_addresses", virtualColumn.getDefaultTableName());
	}

	public void testMoveSpecifiedAttributeOverride() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
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
		
		attributeOverrideContainer.moveSpecifiedOverride(2, 0);
		ListIterator<OrmAttributeOverride> attributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAR", elementCollectionResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAZ", elementCollectionResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", elementCollectionResource.getAttributeOverrides().get(2).getName());


		attributeOverrideContainer.moveSpecifiedOverride(0, 1);
		attributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAZ", elementCollectionResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAR", elementCollectionResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", elementCollectionResource.getAttributeOverrides().get(2).getName());
	}
	
	public void testUpdateAttributeOverrides() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		OrmAttributeOverrideContainer attributeOverrideContainer = ormElementCollectionMapping.getValueAttributeOverrideContainer();
		XmlElementCollection_2_0 elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		elementCollectionResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		elementCollectionResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		elementCollectionResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		
		elementCollectionResource.getAttributeOverrides().get(0).setName("FOO");
		elementCollectionResource.getAttributeOverrides().get(1).setName("BAR");
		elementCollectionResource.getAttributeOverrides().get(2).setName("BAZ");

		ListIterator<OrmAttributeOverride> attributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		elementCollectionResource.getAttributeOverrides().move(2, 0);
		attributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		elementCollectionResource.getAttributeOverrides().move(0, 1);
		attributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		elementCollectionResource.getAttributeOverrides().remove(1);
		attributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		elementCollectionResource.getAttributeOverrides().remove(1);
		attributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		elementCollectionResource.getAttributeOverrides().remove(0);
		assertFalse(attributeOverrideContainer.getSpecifiedOverrides().iterator().hasNext());
	}

	public void testElementCollectionMappingNoUnderylingJavaAttribute() throws Exception {
		createTestEntityWithEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		ormElementCollectionMapping.setName("foo");
		OrmAttributeOverrideContainer attributeOverrideContainer = ormElementCollectionMapping.getValueAttributeOverrideContainer();
		
		assertEquals("foo", ormElementCollectionMapping.getName());

		
		assertNull(ormElementCollectionMapping.getSpecifiedFetch());
		assertEquals(FetchType.LAZY, ormElementCollectionMapping.getFetch());
		assertFalse(attributeOverrideContainer.getSpecifiedOverrides().iterator().hasNext());
		assertFalse(attributeOverrideContainer.getVirtualOverrides().iterator().hasNext());
	}
	
	public void testVirtualAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType persistentType2 = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		OrmPersistentType persistentType3 = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		
		//embedded mapping is virtual, specified attribute overrides should exist
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = persistentType.getAttributeNamed("addresses");
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		AttributeOverrideContainer attributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		assertEquals(4, attributeOverrideContainer.getOverridesSize());
		assertEquals(3, attributeOverrideContainer.getVirtualOverridesSize());
		assertEquals(1, attributeOverrideContainer.getSpecifiedOverridesSize());
		ListIterator<? extends ReadOnlyAttributeOverride> specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		ReadOnlyAttributeOverride attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("city", attributeOverride.getName());
		ListIterator<? extends ReadOnlyAttributeOverride> virtualOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		attributeOverride = virtualOverrides.next();
		assertEquals("state.name", attributeOverride.getName());
		attributeOverride = virtualOverrides.next();
		assertEquals("state.abbr", attributeOverride.getName());
		attributeOverride = virtualOverrides.next();
		assertEquals("zip", attributeOverride.getName());
		
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentAttribute.getJavaPersistentAttribute().getMapping();
		Column javaAttributeOverrideColumn = javaElementCollectionMapping.getValueAttributeOverrideContainer().getSpecifiedOverrides().iterator().next().getColumn();
		
		javaAttributeOverrideColumn.setSpecifiedName("FOO_COLUMN");
		javaAttributeOverrideColumn.setSpecifiedTableName("FOO_TABLE");
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
		assertEquals(4, attributeOverrideContainer.getOverridesSize());
		assertEquals(3, attributeOverrideContainer.getVirtualOverridesSize());
		assertEquals(1, attributeOverrideContainer.getSpecifiedOverridesSize());
		specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("city", attributeOverride.getName());
		assertEquals("FOO_COLUMN", attributeOverride.getColumn().getSpecifiedName());
		assertEquals("FOO_TABLE", attributeOverride.getColumn().getSpecifiedTableName());
		assertEquals("COLUMN_DEF", attributeOverride.getColumn().getColumnDefinition());
		assertEquals(false, attributeOverride.getColumn().isInsertable());
		assertEquals(false, attributeOverride.getColumn().isUpdatable());
		assertEquals(true, attributeOverride.getColumn().isUnique());
		assertEquals(false, attributeOverride.getColumn().isNullable());
		assertEquals(5, attributeOverride.getColumn().getLength());
		assertEquals(6, attributeOverride.getColumn().getPrecision());
		assertEquals(7, attributeOverride.getColumn().getScale());

		virtualOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		attributeOverride = virtualOverrides.next();
		assertEquals("state.name", attributeOverride.getName());
		assertEquals("MY_STATE_COLUMN", attributeOverride.getColumn().getSpecifiedName());
		attributeOverride = virtualOverrides.next();
		assertEquals("state.abbr", attributeOverride.getName());
		attributeOverride = virtualOverrides.next();
		assertEquals("zip", attributeOverride.getName());
		
		
		
		//embedded mapping is specified, virtual attribute overrides should exist
		persistentType.getAttributeNamed("addresses").addToXml();
		elementCollectionMapping = (OrmElementCollectionMapping2_0) persistentType.getAttributeNamed("addresses").getMapping();
		attributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		assertEquals(4, attributeOverrideContainer.getOverridesSize());
		assertEquals(4, attributeOverrideContainer.getVirtualOverridesSize());
		assertEquals(0, attributeOverrideContainer.getSpecifiedOverridesSize());
		ListIterator<? extends VirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		VirtualAttributeOverride virtualOverride = virtualAttributeOverrides.next();
		assertEquals("city", virtualOverride.getName());
		virtualOverride = virtualAttributeOverrides.next();
		assertEquals("state.name", virtualOverride.getName());
		assertEquals("MY_STATE_COLUMN", virtualOverride.getColumn().getName());
		assertEquals(TYPE_NAME + "_addresses", virtualOverride.getColumn().getTableName());
		assertEquals(null, virtualOverride.getColumn().getColumnDefinition());
		assertEquals(true, virtualOverride.getColumn().isInsertable());
		assertEquals(true, virtualOverride.getColumn().isUpdatable());
		assertEquals(false, virtualOverride.getColumn().isUnique());
		assertEquals(true, virtualOverride.getColumn().isNullable());
		assertEquals(255, virtualOverride.getColumn().getLength());
		assertEquals(0, virtualOverride.getColumn().getPrecision());
		assertEquals(0, virtualOverride.getColumn().getScale());
		virtualOverride = virtualAttributeOverrides.next();
		assertEquals("state.abbr", virtualOverride.getName());
		assertEquals(TYPE_NAME + "_addresses", virtualOverride.getColumn().getDefaultTableName());
		virtualOverride = virtualAttributeOverrides.next();
		assertEquals("zip", virtualOverride.getName());
		
		//set one of the virtual attribute overrides to specified, verify others are still virtual
		attributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		assertEquals(4, attributeOverrideContainer.getOverridesSize());
		assertEquals(1, attributeOverrideContainer.getSpecifiedOverridesSize());
		assertEquals(3, attributeOverrideContainer.getVirtualOverridesSize());
		assertEquals("city", attributeOverrideContainer.getSpecifiedOverrides().iterator().next().getName());
		virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		virtualOverride = virtualAttributeOverrides.next();
		assertEquals("state.name", virtualOverride.getName());
		virtualOverride = virtualAttributeOverrides.next();
		assertEquals("state.abbr", virtualOverride.getName());
		virtualOverride = virtualAttributeOverrides.next();
		assertEquals("zip", virtualOverride.getName());
	}

	public void testVirtualMapKeyColumnDefaults() throws Exception {
		createTestEntityWithValidMapElementCollectionMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		//virtual attribute in orm.xml, java attribute has no value Column annotation
		OrmReadOnlyPersistentAttribute addressesPersistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		ElementCollectionMapping2_0 addressesVirtualMapping = (ElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();		
		Column virtualColumn = addressesVirtualMapping.getMapKeyColumn();
		assertEquals("addresses_KEY", virtualColumn.getName());
		assertEquals(TYPE_NAME + "_addresses", virtualColumn.getTableName());
		assertEquals(null, virtualColumn.getColumnDefinition());
		assertTrue(virtualColumn.isInsertable());
		assertTrue(virtualColumn.isUpdatable());
		assertTrue(virtualColumn.isNullable());
		assertFalse(virtualColumn.isUnique());
		assertEquals(ReadOnlyColumn.DEFAULT_LENGTH, virtualColumn.getLength());
		assertEquals(ReadOnlyColumn.DEFAULT_PRECISION, virtualColumn.getPrecision());
		assertEquals(ReadOnlyColumn.DEFAULT_SCALE, virtualColumn.getScale());
	
		//set Column annotation in Java
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedName("FOO");		
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedTableName("FOO_TABLE");
		javaElementCollectionMapping.getMapKeyColumn().setColumnDefinition("COLUMN_DEFINITION");
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedInsertable(Boolean.FALSE);	
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedUpdatable(Boolean.FALSE);	
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedNullable(Boolean.FALSE);	
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedUnique(Boolean.TRUE);	
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedLength(Integer.valueOf(45));
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedPrecision(Integer.valueOf(46));
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedScale(Integer.valueOf(47));

		assertEquals("FOO", virtualColumn.getSpecifiedName());
		assertEquals("FOO_TABLE", virtualColumn.getSpecifiedTableName());
		assertEquals("COLUMN_DEFINITION", virtualColumn.getColumnDefinition());
		assertEquals(Boolean.FALSE, virtualColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, virtualColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.FALSE, virtualColumn.getSpecifiedNullable());
		assertEquals(Boolean.TRUE, virtualColumn.getSpecifiedUnique());
		assertEquals(Integer.valueOf(45), virtualColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(46), virtualColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(47), virtualColumn.getSpecifiedScale());

	
		//set metadata-complete, orm.xml virtual column ignores java column annotation
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		addressesPersistentAttribute = ormPersistentType.getAttributeNamed("addresses");
		//no longer an element collection mapping
		assertEquals(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY, addressesPersistentAttribute.getMappingKey());
	}
	
	public void testNullMapKeyColumnDefaults() throws Exception {
		createTestEntityWithValidMapElementCollectionMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute addressesPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);

		OrmElementCollectionMapping2_0 addressesVirtualMapping = (OrmElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();		
		Column ormColumn = addressesVirtualMapping.getMapKeyColumn();

		//set Column annotation in Java
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedName("FOO");		
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedTableName("FOO_TABLE");
		javaElementCollectionMapping.getMapKeyColumn().setColumnDefinition("COLUMN_DEFINITION");
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedInsertable(Boolean.FALSE);	
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedUpdatable(Boolean.FALSE);	
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedNullable(Boolean.FALSE);	
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedUnique(Boolean.TRUE);	
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedLength(Integer.valueOf(45));
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedPrecision(Integer.valueOf(46));
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedScale(Integer.valueOf(47));


		assertEquals("addresses_KEY", ormColumn.getDefaultName());
		assertEquals(TYPE_NAME + "_addresses", ormColumn.getDefaultTableName());
		assertEquals(true, ormColumn.isDefaultInsertable());
		assertEquals(true, ormColumn.isDefaultUpdatable());
		assertEquals(true, ormColumn.isDefaultNullable());
		assertEquals(false, ormColumn.isDefaultUnique());
		assertEquals(ReadOnlyColumn.DEFAULT_LENGTH, ormColumn.getDefaultLength());
		assertEquals(ReadOnlyColumn.DEFAULT_PRECISION, ormColumn.getDefaultPrecision());
		assertEquals(ReadOnlyColumn.DEFAULT_SCALE, ormColumn.getDefaultScale());
		assertNull(ormColumn.getSpecifiedName());
		assertNull(ormColumn.getSpecifiedTableName());
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
		OrmReadOnlyPersistentAttribute addressesPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		ElementCollectionMapping2_0 addressesVirtualMapping = (ElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();	
		Column virtualColumn = addressesVirtualMapping.getMapKeyColumn();
		
		assertEquals(TYPE_NAME + "_addresses", virtualColumn.getTableName());

		//entity table should have no affect on the collection table default name
		((OrmEntity) ormPersistentType.getMapping()).getTable().setSpecifiedName("ORM_TABLE");
		assertEquals(TYPE_NAME + "_addresses", virtualColumn.getTableName());

		//set Column table element in Java
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		javaElementCollectionMapping.getCollectionTable().setSpecifiedName("JAVA_COLLECTION_TABLE");
		assertEquals("JAVA_COLLECTION_TABLE", virtualColumn.getTableName());
		javaElementCollectionMapping.getMapKeyColumn().setSpecifiedTableName("JAVA_TABLE");	
		assertEquals("JAVA_TABLE", virtualColumn.getTableName());

		//make name persistent attribute not default
		addressesPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		addressesVirtualMapping = (OrmElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();	
		virtualColumn = addressesVirtualMapping.getMapKeyColumn();
		assertNull(virtualColumn.getSpecifiedTableName());
		assertEquals(TYPE_NAME + "_addresses", virtualColumn.getDefaultTableName());
	}

	public void testSelfReferentialElementCollectionMapping() throws Exception {
		createSelfReferentialElementCollection();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Foo");

		ElementCollectionMapping2_0 mapping = (ElementCollectionMapping2_0) persistentType.getAttributeNamed("elementCollection").getMapping();
		assertFalse(mapping.getAllOverridableAttributeMappingNames().iterator().hasNext());
	}
	
	public void testUpdateSpecifiedEnumerated() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getMapKeyConverter().getType());
		assertNull(elementCollectionResource.getMapKeyEnumerated());
				
		//set enumerated in the resource model, verify context model updated
		elementCollectionResource.setMapKeyEnumerated(org.eclipse.jpt.jpa.core.resource.orm.EnumType.ORDINAL);
		assertEquals(EnumType.ORDINAL, ((BaseEnumeratedConverter) ormElementCollectionMapping.getMapKeyConverter()).getSpecifiedEnumType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.EnumType.ORDINAL, elementCollectionResource.getMapKeyEnumerated());
	
		elementCollectionResource.setMapKeyEnumerated(org.eclipse.jpt.jpa.core.resource.orm.EnumType.STRING);
		assertEquals(EnumType.STRING, ((BaseEnumeratedConverter) ormElementCollectionMapping.getMapKeyConverter()).getSpecifiedEnumType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.EnumType.STRING, elementCollectionResource.getMapKeyEnumerated());

		//set enumerated to null in the resource model
		elementCollectionResource.setMapKeyEnumerated(null);
		assertNull(ormElementCollectionMapping.getMapKeyConverter().getType());
		assertNull(elementCollectionResource.getMapKeyEnumerated());
	}
	
	public void testModifySpecifiedEnumerated() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getMapKeyConverter().getType());
		assertNull(elementCollectionResource.getMapKeyEnumerated());
				
		//set enumerated in the context model, verify resource model updated
		ormElementCollectionMapping.setMapKeyConverter(BaseEnumeratedConverter.class);
		((BaseEnumeratedConverter) ormElementCollectionMapping.getMapKeyConverter()).setSpecifiedEnumType(EnumType.ORDINAL);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.EnumType.ORDINAL, elementCollectionResource.getMapKeyEnumerated());
		assertEquals(EnumType.ORDINAL, ((BaseEnumeratedConverter) ormElementCollectionMapping.getMapKeyConverter()).getSpecifiedEnumType());
	
		((BaseEnumeratedConverter) ormElementCollectionMapping.getMapKeyConverter()).setSpecifiedEnumType(EnumType.STRING);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.EnumType.STRING, elementCollectionResource.getMapKeyEnumerated());
		assertEquals(EnumType.STRING, ((BaseEnumeratedConverter) ormElementCollectionMapping.getMapKeyConverter()).getSpecifiedEnumType());

		//set enumerated to null in the context model
		ormElementCollectionMapping.setMapKeyConverter(null);
		assertNull(elementCollectionResource.getMapKeyEnumerated());
		assertNull(ormElementCollectionMapping.getMapKeyConverter().getType());
	}
	
	public void testUpdateSpecifiedTemporal() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getMapKeyConverter().getType());
		assertNull(elementCollectionResource.getMapKeyTemporal());
				
		//set temporal in the resource model, verify context model updated
		elementCollectionResource.setMapKeyTemporal(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.DATE);
		assertEquals(TemporalType.DATE, ((BaseTemporalConverter) ormElementCollectionMapping.getMapKeyConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.DATE, elementCollectionResource.getMapKeyTemporal());
	
		elementCollectionResource.setMapKeyTemporal(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIME);
		assertEquals(TemporalType.TIME, ((BaseTemporalConverter) ormElementCollectionMapping.getMapKeyConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIME, elementCollectionResource.getMapKeyTemporal());
		
		elementCollectionResource.setMapKeyTemporal(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIMESTAMP);
		assertEquals(TemporalType.TIMESTAMP, ((BaseTemporalConverter) ormElementCollectionMapping.getMapKeyConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIMESTAMP, elementCollectionResource.getMapKeyTemporal());

		//set temporal to null in the resource model
		elementCollectionResource.setMapKeyTemporal(null);
		assertNull(ormElementCollectionMapping.getMapKeyConverter().getType());
		assertNull(elementCollectionResource.getMapKeyTemporal());
	}
	
	public void testModifySpecifiedTemporal() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getMapKeyConverter().getType());
		assertNull(elementCollectionResource.getMapKeyTemporal());
				
		//set temporal in the context model, verify resource model updated
		ormElementCollectionMapping.setMapKeyConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) ormElementCollectionMapping.getMapKeyConverter()).setTemporalType(TemporalType.DATE);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.DATE, elementCollectionResource.getMapKeyTemporal());
		assertEquals(TemporalType.DATE, ((BaseTemporalConverter) ormElementCollectionMapping.getMapKeyConverter()).getTemporalType());
	
		((BaseTemporalConverter) ormElementCollectionMapping.getMapKeyConverter()).setTemporalType(TemporalType.TIME);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIME, elementCollectionResource.getMapKeyTemporal());
		assertEquals(TemporalType.TIME, ((BaseTemporalConverter) ormElementCollectionMapping.getMapKeyConverter()).getTemporalType());

		//set temporal to null in the context model
		ormElementCollectionMapping.setMapKeyConverter(null);
		assertNull(elementCollectionResource.getMapKeyTemporal());
		assertNull(ormElementCollectionMapping.getMapKeyConverter().getType());
	}

	public void testAddSpecifiedMapKeyJoinColumn() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		ElementCollectionMapping2_0 ormElementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		JoinColumn joinColumn = ormElementCollectionMapping.addSpecifiedMapKeyJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", elementCollectionResource.getMapKeyJoinColumns().get(0).getName());
		
		JoinColumn joinColumn2 = ormElementCollectionMapping.addSpecifiedMapKeyJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", elementCollectionResource.getMapKeyJoinColumns().get(0).getName());
		assertEquals("FOO", elementCollectionResource.getMapKeyJoinColumns().get(1).getName());
		
		JoinColumn joinColumn3 = ormElementCollectionMapping.addSpecifiedMapKeyJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", elementCollectionResource.getMapKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", elementCollectionResource.getMapKeyJoinColumns().get(1).getName());
		assertEquals("FOO", elementCollectionResource.getMapKeyJoinColumns().get(2).getName());
		
		ListIterator<? extends JoinColumn> joinColumns = ormElementCollectionMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = ormElementCollectionMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedMapKeyJoinColumn() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		ElementCollectionMapping2_0 ormElementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		ormElementCollectionMapping.addSpecifiedMapKeyJoinColumn(0).setSpecifiedName("FOO");
		ormElementCollectionMapping.addSpecifiedMapKeyJoinColumn(1).setSpecifiedName("BAR");
		ormElementCollectionMapping.addSpecifiedMapKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, elementCollectionResource.getMapKeyJoinColumns().size());
		
		ormElementCollectionMapping.removeSpecifiedMapKeyJoinColumn(0);
		assertEquals(2, elementCollectionResource.getMapKeyJoinColumns().size());
		assertEquals("BAR", elementCollectionResource.getMapKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", elementCollectionResource.getMapKeyJoinColumns().get(1).getName());

		ormElementCollectionMapping.removeSpecifiedMapKeyJoinColumn(0);
		assertEquals(1, elementCollectionResource.getMapKeyJoinColumns().size());
		assertEquals("BAZ", elementCollectionResource.getMapKeyJoinColumns().get(0).getName());
		
		ormElementCollectionMapping.removeSpecifiedMapKeyJoinColumn(0);
		assertEquals(0, elementCollectionResource.getMapKeyJoinColumns().size());
	}
	
	public void testMoveSpecifiedMapKeyJoinColumn() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		ElementCollectionMapping2_0 ormElementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);

		ormElementCollectionMapping.addSpecifiedMapKeyJoinColumn(0).setSpecifiedName("FOO");
		ormElementCollectionMapping.addSpecifiedMapKeyJoinColumn(1).setSpecifiedName("BAR");
		ormElementCollectionMapping.addSpecifiedMapKeyJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, elementCollectionResource.getMapKeyJoinColumns().size());
		
		
		ormElementCollectionMapping.moveSpecifiedMapKeyJoinColumn(2, 0);
		ListIterator<? extends JoinColumn> joinColumns = ormElementCollectionMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", elementCollectionResource.getMapKeyJoinColumns().get(0).getName());
		assertEquals("BAZ", elementCollectionResource.getMapKeyJoinColumns().get(1).getName());
		assertEquals("FOO", elementCollectionResource.getMapKeyJoinColumns().get(2).getName());


		ormElementCollectionMapping.moveSpecifiedMapKeyJoinColumn(0, 1);
		joinColumns = ormElementCollectionMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", elementCollectionResource.getMapKeyJoinColumns().get(0).getName());
		assertEquals("BAR", elementCollectionResource.getMapKeyJoinColumns().get(1).getName());
		assertEquals("FOO", elementCollectionResource.getMapKeyJoinColumns().get(2).getName());
	}

	public void testUpdateMapKeyJoinColumns() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		ElementCollectionMapping2_0 ormElementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
	
		elementCollectionResource.getMapKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		elementCollectionResource.getMapKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		elementCollectionResource.getMapKeyJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		
		elementCollectionResource.getMapKeyJoinColumns().get(0).setName("FOO");
		elementCollectionResource.getMapKeyJoinColumns().get(1).setName("BAR");
		elementCollectionResource.getMapKeyJoinColumns().get(2).setName("BAZ");

		ListIterator<? extends JoinColumn> joinColumns = ormElementCollectionMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		elementCollectionResource.getMapKeyJoinColumns().move(2, 0);
		joinColumns = ormElementCollectionMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		elementCollectionResource.getMapKeyJoinColumns().move(0, 1);
		joinColumns = ormElementCollectionMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		elementCollectionResource.getMapKeyJoinColumns().remove(1);
		joinColumns = ormElementCollectionMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		elementCollectionResource.getMapKeyJoinColumns().remove(1);
		joinColumns = ormElementCollectionMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		elementCollectionResource.getMapKeyJoinColumns().remove(0);
		assertFalse(ormElementCollectionMapping.getSpecifiedMapKeyJoinColumns().iterator().hasNext());
	}

}
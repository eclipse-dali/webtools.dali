/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.tests.internal.projects.JavaProjectTestHarness.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.EnumType;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.SpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.context.VirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedOrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlElementCollection_2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvertibleMapping_2_1;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvert;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLink2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLink2_0OrmElementCollectionMappingTests
	extends EclipseLink2_0ContextModelTestCase
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
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
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
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "State.java", sourceWriter);
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
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "Foo.java", sourceWriter);
	}

	private ICompilationUnit createTestTypeWithCollection() throws Exception {		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator("java.util.Collection");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("    private Collection<String> elementCollection;").append(CR);
			}
		});
	}

	public void testUpdateName() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection elementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertEquals("elementCollection", ormElementCollectionMapping.getName());
		assertEquals("elementCollection", elementCollection.getName());
				
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
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection elementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertEquals("elementCollection", ormElementCollectionMapping.getName());
		assertEquals("elementCollection", elementCollection.getName());
				
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
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("address"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		ormPersistentAttribute.getMapping().setName("foo");

		assertEquals(3, ormPersistentType.getDefaultAttributesSize());
		
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
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		
		ElementCollectionMapping2_0 virtualElementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();	
		assertEquals("address", virtualElementCollectionMapping.getName());
		assertEquals(FetchType.EAGER, virtualElementCollectionMapping.getSpecifiedFetch());
		assertEquals("String", virtualElementCollectionMapping.getSpecifiedTargetClass());
		
		ormPersistentAttribute.addToXml();
		ormPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();	
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
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("address");
		
		assertEquals(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
		
		ormPersistentAttribute.addToXml(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		ormPersistentAttribute= ormPersistentType.getSpecifiedAttributes().iterator().next();

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
		
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		
		assertEquals("address", ormElementCollectionMapping.getName());
		assertNull(ormElementCollectionMapping.getSpecifiedFetch());
		assertEquals(FetchType.LAZY, ormElementCollectionMapping.getFetch());
	}
	
	
	public void testMorphToIdMapping() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("elementCollection", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("elementCollection", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);

		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("elementCollection", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);

		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("elementCollection", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("elementCollection", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testMorphToManyToManyMapping() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("elementCollection", ormPersistentAttribute.getMapping().getName());
//		assertEquals(FetchType.EAGER, ((ManyToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("elementCollection", ormPersistentAttribute.getMapping().getName());
//		assertEquals(FetchType.EAGER, ((OneToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("elementCollection", ormPersistentAttribute.getMapping().getName());
//		assertEquals(FetchType.EAGER, ((ManyToOneMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertFalse(elementCollectionMapping.isDefault());	
		
		ormPersistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("elementCollection", ormPersistentAttribute.getMapping().getName());
//TODO	assertEquals(FetchType.EAGER, ((IBasicMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
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
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
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
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
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
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
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

		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);	
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();

		assertEquals(ElementCollectionMapping2_0.Type.EMBEDDABLE_TYPE, elementCollectionMapping.getValueType());
	}
	
	public void testGetValueTypeEntity() throws Exception {
		createTestEntityWithEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.Address");

		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);	
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();

		assertEquals(ElementCollectionMapping2_0.Type.BASIC_TYPE, elementCollectionMapping.getValueType());
	}
	
	public void testGetValueTypeNone() throws Exception {
		createTestEntityWithNonGenericElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, "test.Address");
		
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);	
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

		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);	
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();

		assertEquals(ElementCollectionMapping2_0.Type.BASIC_TYPE, elementCollectionMapping.getValueType());
	}
	
	
	public void testUpdateMapKey() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection elementCollection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
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
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection xmlElementColection = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getSpecifiedMapKey());
		assertNull(xmlElementColection.getMapKey());
					
		//set mapKey  in the context model, verify resource model updated
		ormElementCollectionMapping.setSpecifiedMapKey("myMapKey");
		assertEquals("myMapKey", ormElementCollectionMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", xmlElementColection.getMapKey().getName());
	
		//set mapKey to null in the context model
		ormElementCollectionMapping.setSpecifiedMapKey(null);
		assertNull(ormElementCollectionMapping.getSpecifiedMapKey());
		assertNull(xmlElementColection.getMapKey().getName());
	}
	
	public void testCandidateMapKeyNames() throws Exception {
		createTestEntityWithValidMapElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		
		ElementCollectionMapping2_0 virtualElementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentType.getAttributeNamed("addresses").getMapping();

		Iterator<String> mapKeyNames = 
			virtualElementCollectionMapping.getCandidateMapKeyNames().iterator();
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
		OrmPersistentAttribute attribute = ormPersistentType.getAttributeNamed("addresses");
		ElementCollectionMapping2_0 virtualElementCollectionMapping = (ElementCollectionMapping2_0) attribute.getMapping();
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
		
		attribute.addToXml();
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentType.getAttributeNamed("addresses").getMapping();
		mapKeyNames = ormElementCollectionMapping.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
		
		ormElementCollectionMapping.setSpecifiedTargetClass("test.Address");
		mapKeyNames = ormElementCollectionMapping.getCandidateMapKeyNames().iterator();
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.name", mapKeyNames.next());
		assertEquals("state.abbr", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		ormElementCollectionMapping.setSpecifiedTargetClass("String");
		mapKeyNames = ormElementCollectionMapping.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
	}
	
	public void testUpdateMapKeyClass() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
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
		assertNull(virtualElementCollectionMapping.getSpecifiedMapKeyClass());
		assertEquals("java.lang.String", virtualElementCollectionMapping.getMapKeyClass());
		assertEquals("java.lang.String", virtualElementCollectionMapping.getDefaultMapKeyClass());
		
		//set pk mapKey in the java, verify virtual orm mapping updates
		javaElementCollectionMapping.setSpecifiedMapKeyClass("Integer");
		assertEquals("Integer", virtualElementCollectionMapping.getMapKeyClass());
		assertEquals("Integer", virtualElementCollectionMapping.getSpecifiedMapKeyClass());
		assertEquals("java.lang.String", virtualElementCollectionMapping.getDefaultMapKeyClass());
	}
	
	public void testModifyMapKeyClass() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
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
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 elementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();

		Orderable2_0 orderable = (Orderable2_0) elementCollectionMapping.getOrderable();
		assertEquals(false, orderable.isOrderColumnOrdering());
		assertEquals(true, orderable.isNoOrdering());
		
		orderable.setOrderColumnOrdering();
		SpecifiedOrderColumn2_0 orderColumn = orderable.getOrderColumn();
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
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 elementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();

		Orderable2_0 orderable = (Orderable2_0) elementCollectionMapping.getOrderable();
		assertEquals(false, orderable.isOrderColumnOrdering());
		assertEquals(true, orderable.isNoOrdering());
		
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentAttribute.getJavaPersistentAttribute().getMapping();
		((Orderable2_0) javaElementCollectionMapping.getOrderable()).setOrderColumnOrdering();
				
		assertEquals(false, orderable.isOrderColumnOrdering());
		assertEquals(true, orderable.isNoOrdering());

		ormPersistentAttribute.removeFromXml();		
		OrmPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("addresses");
		ElementCollectionMapping2_0 virtualElementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute2.getMapping();
		orderable = (Orderable2_0) virtualElementCollectionMapping.getOrderable();
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
		OrmPersistentAttribute addressesPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		ElementCollectionMapping2_0 addressesVirtualMapping = (ElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();		
		SpecifiedColumn virtualColumn = addressesVirtualMapping.getValueColumn();
		assertEquals("addresses", virtualColumn.getName());
		assertEquals(TYPE_NAME + "_addresses", virtualColumn.getTableName());
		assertEquals(null, virtualColumn.getColumnDefinition());
		assertTrue(virtualColumn.isInsertable());
		assertTrue(virtualColumn.isUpdatable());
		assertTrue(virtualColumn.isNullable());
		assertFalse(virtualColumn.isUnique());
		assertEquals(Column.DEFAULT_LENGTH, virtualColumn.getLength());
		assertEquals(Column.DEFAULT_PRECISION, virtualColumn.getPrecision());
		assertEquals(Column.DEFAULT_SCALE, virtualColumn.getScale());
	
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
		OrmSpecifiedPersistentAttribute addressesPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);

		OrmElementCollectionMapping2_0 addressesVirtualMapping = (OrmElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();		
		OrmSpecifiedColumn ormColumn = addressesVirtualMapping.getValueColumn();
	
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
		assertEquals(Column.DEFAULT_LENGTH, ormColumn.getDefaultLength());
		assertEquals(Column.DEFAULT_PRECISION, ormColumn.getDefaultPrecision());
		assertEquals(Column.DEFAULT_SCALE, ormColumn.getDefaultScale());
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
		
		//default attribute in orm.xml, java attribute has no Column annotation
		OrmPersistentAttribute addressesPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		ElementCollectionMapping2_0 addressesVirtualMapping = (ElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();	
		SpecifiedColumn virtualColumn = addressesVirtualMapping.getValueColumn();
		
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
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
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
		ListIterator<OrmSpecifiedAttributeOverride> attributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
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
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		OrmAttributeOverrideContainer attributeOverrideContainer = ormElementCollectionMapping.getValueAttributeOverrideContainer();
		XmlElementCollection_2_0 elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		elementCollectionResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		elementCollectionResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		elementCollectionResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		
		elementCollectionResource.getAttributeOverrides().get(0).setName("FOO");
		elementCollectionResource.getAttributeOverrides().get(1).setName("BAR");
		elementCollectionResource.getAttributeOverrides().get(2).setName("BAZ");

		ListIterator<OrmSpecifiedAttributeOverride> attributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
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
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"));
		ormPersistentAttribute.getMapping().setName("foo");
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());
		
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		OrmAttributeOverrideContainer attributeOverrideContainer = ormElementCollectionMapping.getValueAttributeOverrideContainer();
		
		assertEquals("foo", ormElementCollectionMapping.getName());

		
		assertFalse(attributeOverrideContainer.getSpecifiedOverrides().iterator().hasNext());
		assertFalse(attributeOverrideContainer.getVirtualOverrides().iterator().hasNext());
	}
	
	public void testVirtualAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		OrmPersistentType persistentType3 = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".State");
		
		//embedded mapping is virtual, specified attribute overrides should exist
		OrmPersistentAttribute ormPersistentAttribute = persistentType.getAttributeNamed("addresses");
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		AttributeOverrideContainer attributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		assertEquals(4, attributeOverrideContainer.getOverridesSize());
		assertEquals(3, attributeOverrideContainer.getVirtualOverridesSize());
		assertEquals(1, attributeOverrideContainer.getSpecifiedOverridesSize());
		ListIterator<? extends SpecifiedAttributeOverride> specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		AttributeOverride attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("city", attributeOverride.getName());
		ListIterator<? extends VirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("state.name", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("state.abbr", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("zip", attributeOverride.getName());
		
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentAttribute.getJavaPersistentAttribute().getMapping();
		SpecifiedColumn javaAttributeOverrideColumn = javaElementCollectionMapping.getValueAttributeOverrideContainer().getSpecifiedOverrides().iterator().next().getColumn();
		
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
		
		virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("state.name", attributeOverride.getName());
		assertEquals("MY_STATE_COLUMN", attributeOverride.getColumn().getSpecifiedName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("state.abbr", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("zip", attributeOverride.getName());
		
		
		
		//embedded mapping is specified, virtual attribute overrides should exist
		persistentType.getAttributeNamed("addresses").addToXml();
		elementCollectionMapping = (OrmElementCollectionMapping2_0) persistentType.getAttributeNamed("addresses").getMapping();
		attributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		assertEquals(4, attributeOverrideContainer.getOverridesSize());
		assertEquals(4, attributeOverrideContainer.getVirtualOverridesSize());
		assertEquals(0, attributeOverrideContainer.getSpecifiedOverridesSize());
		virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		AttributeOverride virtualOverride = virtualAttributeOverrides.next();
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
		OrmPersistentAttribute addressesPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		ElementCollectionMapping2_0 addressesVirtualMapping = (ElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();		
		SpecifiedColumn virtualColumn = addressesVirtualMapping.getMapKeyColumn();
		assertEquals("addresses_KEY", virtualColumn.getName());
		assertEquals(TYPE_NAME + "_addresses", virtualColumn.getTableName());
		assertEquals(null, virtualColumn.getColumnDefinition());
		assertTrue(virtualColumn.isInsertable());
		assertTrue(virtualColumn.isUpdatable());
		assertTrue(virtualColumn.isNullable());
		assertFalse(virtualColumn.isUnique());
		assertEquals(Column.DEFAULT_LENGTH, virtualColumn.getLength());
		assertEquals(Column.DEFAULT_PRECISION, virtualColumn.getPrecision());
		assertEquals(Column.DEFAULT_SCALE, virtualColumn.getScale());

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
		OrmSpecifiedPersistentAttribute addressesPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("addresses"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);

		OrmElementCollectionMapping2_0 addressesVirtualMapping = (OrmElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();		
		SpecifiedColumn ormColumn = addressesVirtualMapping.getMapKeyColumn();

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
		assertEquals(Column.DEFAULT_LENGTH, ormColumn.getDefaultLength());
		assertEquals(Column.DEFAULT_PRECISION, ormColumn.getDefaultPrecision());
		assertEquals(Column.DEFAULT_SCALE, ormColumn.getDefaultScale());
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
		OrmPersistentAttribute addressesPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		ElementCollectionMapping2_0 addressesVirtualMapping = (ElementCollectionMapping2_0) addressesPersistentAttribute.getMapping();	
		SpecifiedColumn virtualColumn = addressesVirtualMapping.getMapKeyColumn();

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

		//make addresses persistent attribute not default
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
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getMapKeyConverter().getConverterType());
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
		assertNull(ormElementCollectionMapping.getMapKeyConverter().getConverterType());
		assertNull(elementCollectionResource.getMapKeyEnumerated());
	}
	
	public void testModifySpecifiedEnumerated() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getMapKeyConverter().getConverterType());
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
		assertNull(ormElementCollectionMapping.getMapKeyConverter().getConverterType());
	}
	
	public void testUpdateSpecifiedTemporal() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getMapKeyConverter().getConverterType());
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
		assertNull(ormElementCollectionMapping.getMapKeyConverter().getConverterType());
		assertNull(elementCollectionResource.getMapKeyTemporal());
	}
	
	public void testModifySpecifiedTemporal() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlElementCollection_2_0 elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		
		assertNull(ormElementCollectionMapping.getMapKeyConverter().getConverterType());
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
		assertNull(ormElementCollectionMapping.getMapKeyConverter().getConverterType());
	}

	public void testUpdateConvert() throws Exception {
		createTestEntityWithElementCollectionMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("address"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlConvertibleMapping_2_1 elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		XmlConvert xmlConvert = (XmlConvert) elementCollectionResource.getConvert();
		JavaElementCollectionMapping2_0 javaElementCollectionMapping = (JavaElementCollectionMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("address").getMapping();
		
		assertNull(ormElementCollectionMapping.getConverter().getConverterType());
		assertEquals(null, xmlConvert);
				
		//set lob in the resource model, verify context model updated
		xmlConvert = EclipseLinkOrmFactory.eINSTANCE.createXmlConvert();
		xmlConvert.setConvert("myConvert");
		elementCollectionResource.setConvert(xmlConvert);
		assertEquals(EclipseLinkConvert.class, ormElementCollectionMapping.getConverter().getConverterType());
		assertEquals("myConvert", ((EclipseLinkConvert) ormElementCollectionMapping.getConverter()).getConverterName());

		//set lob to null in the resource model
		elementCollectionResource.setConvert(null);
		assertNull(ormElementCollectionMapping.getConverter().getConverterType());
		assertEquals(null, elementCollectionResource.getConvert());
		
		
		javaElementCollectionMapping.setConverter(EclipseLinkConvert.class);
		((EclipseLinkConvert) javaElementCollectionMapping.getConverter()).setSpecifiedConverterName("foo");
		
		assertNull(ormElementCollectionMapping.getConverter().getConverterType());
		assertEquals(null, elementCollectionResource.getConvert());
		assertEquals("foo", ((EclipseLinkConvert) javaElementCollectionMapping.getConverter()).getSpecifiedConverterName());
		
		
		ormPersistentAttribute.removeFromXml();
		OrmPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("address");
		EclipseLinkElementCollectionMapping2_0 virtualElementCollectionMapping = (EclipseLinkElementCollectionMapping2_0) ormPersistentAttribute2.getMapping();
		
		assertEquals(EclipseLinkConvert.class, virtualElementCollectionMapping.getConverter().getConverterType());
		assertEquals("foo", ((EclipseLinkConvert) virtualElementCollectionMapping.getConverter()).getSpecifiedConverterName());
		assertEquals(null, elementCollectionResource.getConvert());
		assertEquals("foo", ((EclipseLinkConvert) javaElementCollectionMapping.getConverter()).getSpecifiedConverterName());
		
		((EclipseLinkConvert) javaElementCollectionMapping.getConverter()).setSpecifiedConverterName("bar");
		assertEquals(EclipseLinkConvert.class, virtualElementCollectionMapping.getConverter().getConverterType());
		assertEquals("bar", ((EclipseLinkConvert) virtualElementCollectionMapping.getConverter()).getSpecifiedConverterName());
		assertEquals(null, elementCollectionResource.getConvert());
		assertEquals("bar", ((EclipseLinkConvert) javaElementCollectionMapping.getConverter()).getSpecifiedConverterName());

		javaElementCollectionMapping.setConverter(null);
		assertNull(virtualElementCollectionMapping.getConverter().getConverterType());
		assertEquals(null, elementCollectionResource.getConvert());
		assertNull(javaElementCollectionMapping.getConverter().getConverterType());
	}
	
	public void testModifyConvert() throws Exception {
		createTestTypeWithCollection();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("elementCollection"), MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmElementCollectionMapping2_0 ormElementCollectionMapping = (OrmElementCollectionMapping2_0) ormPersistentAttribute.getMapping();
		XmlConvertibleMapping elementCollectionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getElementCollections().get(0);
		XmlConvert xmlConvert = (XmlConvert) elementCollectionResource.getConvert();
		assertNull(ormElementCollectionMapping.getConverter().getConverterType());
		assertEquals(null, xmlConvert);
				
		//set lob in the context model, verify resource model updated
		ormElementCollectionMapping.setConverter(EclipseLinkConvert.class);
		xmlConvert = (XmlConvert) elementCollectionResource.getConvert();
		assertEquals("none", xmlConvert.getConvert());
		assertEquals(EclipseLinkConvert.class, ormElementCollectionMapping.getConverter().getConverterType());
	
		((EclipseLinkConvert) ormElementCollectionMapping.getConverter()).setSpecifiedConverterName("bar");
		assertEquals("bar", xmlConvert.getConvert());
		assertEquals(EclipseLinkConvert.class, ormElementCollectionMapping.getConverter().getConverterType());
		assertEquals("bar", ((EclipseLinkConvert) ormElementCollectionMapping.getConverter()).getSpecifiedConverterName());

		((EclipseLinkConvert) ormElementCollectionMapping.getConverter()).setSpecifiedConverterName(null);

		assertNull(ormElementCollectionMapping.getConverter().getConverterType());
		assertEquals(null, elementCollectionResource.getConvert());

		//set lob to false in the context model
		ormElementCollectionMapping.setConverter(null);
		assertNull(ormElementCollectionMapping.getConverter().getConverterType());
		assertEquals(null, elementCollectionResource.getConvert());
	}
}

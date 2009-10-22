/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.JoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.jpa2.context.AssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaAssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaEmbeddedMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmEmbeddedMapping2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class GenericOrmEmbeddedMapping2_0Tests extends Generic2_0OrmContextModelTestCase
{
	private static final String ATTRIBUTE_OVERRIDE_NAME = "city";
	private static final String ATTRIBUTE_OVERRIDE_COLUMN_NAME = "E_CITY";
	
	private static final String EMBEDDABLE_TYPE_NAME = "MyEmbeddable";
	private static final String FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME = PACKAGE_NAME + "." + EMBEDDABLE_TYPE_NAME;

	public GenericOrmEmbeddedMapping2_0Tests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestEntityEmbeddedMappingAttributeOverrides() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.EMBEDDED, JPA.ATTRIBUTE_OVERRIDE, JPA.COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @Embedded");
				sb.append(CR);
				sb.append("    @AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\", column=@Column(name=\"" + ATTRIBUTE_OVERRIDE_COLUMN_NAME + "\"))");
				sb.append(CR);
				sb.append("    private Address address;").append(CR);
				sb.append(CR);
				sb.append("    @Id");				
			}
		});
	}	
	private ICompilationUnit createTestEntityWithEmbeddedMapping() throws Exception {
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.EMBEDDED, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Embedded").append(CR);
				sb.append("    private " + EMBEDDABLE_TYPE_NAME + " myEmbedded;").append(CR);
				sb.append(CR);
				sb.append("    @Id");
			}
		});
	}

	private void createEmbeddableType() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.EMBEDDABLE);
					sb.append(";");
					sb.append("import ");
					sb.append(JPA.ONE_TO_ONE);
					sb.append(";");
					sb.append("import ");
					sb.append(JPA.ONE_TO_MANY);
					sb.append(";");
					sb.append(CR);
				sb.append("@Embeddable");
				sb.append(CR);
				sb.append("public class ").append(EMBEDDABLE_TYPE_NAME).append(" {");
				sb.append(CR);
				sb.append("    private String city;").append(CR);
				sb.append(CR);
				sb.append("    private String state;").append(CR);
				sb.append(CR);
				sb.append("    @OneToOne").append(CR);
				sb.append("    private Address address;").append(CR);
				sb.append(CR);
				sb.append("    @OneToMany").append(CR);
				sb.append("    private java.util.Collection<Address> addresses;").append(CR);
				sb.append(CR);
				sb.append("    ");
				sb.append("}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, EMBEDDABLE_TYPE_NAME + ".java", sourceWriter);
	}
	
	private void createAddressEntity() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append("import ");
					sb.append(JPA.ID);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class Address {");
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private String id;").append(CR);
				sb.append(CR);
				sb.append("    private String city;").append(CR);
				sb.append(CR);
				sb.append("    private String state;").append(CR);
				sb.append(CR);
				sb.append("    ");
				sb.append("}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
	}
	
	private void createTestEmbeddableAddress() throws Exception {
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
					sb.append("import ");
					sb.append(JPA.COLUMN);
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
				sb.append("    @Column(name=\"A_STATE\")").append(CR);
				sb.append("    private String state;").append(CR);
				sb.append(CR);
				sb.append("    private int zip;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
	}
	
	private void createTestEntityCustomer() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ID);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.EMBEDDED);
					sb.append(";");
					sb.append(CR);
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("Customer").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private String id;").append(CR);
				sb.append(CR);
				sb.append("    private String name;").append(CR);
				sb.append(CR);
				sb.append("    @Embedded").append(CR);
				sb.append("    private Address address;").append(CR);
				sb.append(CR);
			sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Customer.java", sourceWriter);
	}

	private void createTestEmbeddableAddress2() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
					sb.append("import ");
					sb.append(JPA.EMBEDDABLE);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.EMBEDDED);
					sb.append(";");
					sb.append(CR);
					sb.append(CR);
				sb.append("@Embeddable");
				sb.append(CR);
				sb.append("public class ").append("Address").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    private String street;").append(CR);
				sb.append(CR);
				sb.append("    private String city;").append(CR);
				sb.append(CR);
				sb.append("    private String state;").append(CR);
				sb.append(CR);
				sb.append("    @Embedded").append(CR);
				sb.append("    private ZipCode zipCode;").append(CR);
				sb.append(CR);
			sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
	}
	
	private void createTestEmbeddableZipCode() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
					sb.append("import ");
					sb.append(JPA.EMBEDDABLE);
					sb.append(";");
					sb.append(CR);
					sb.append(CR);
				sb.append("@Embeddable");
				sb.append(CR);
				sb.append("public class ").append("ZipCode").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    private String zip;").append(CR);
				sb.append(CR);
				sb.append("    private String plusfour;").append(CR);
				sb.append(CR);
			sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "ZipCode.java", sourceWriter);
	}

	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedMapping");
		OrmEmbeddedMapping ormEmbeddedMapping = (OrmEmbeddedMapping) ormPersistentAttribute.getMapping();
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		
		assertEquals("embeddedMapping", ormEmbeddedMapping.getName());
		assertEquals("embeddedMapping", embeddedResource.getName());
				
		//set name in the resource model, verify context model updated
		embeddedResource.setName("newName");
		assertEquals("newName", ormEmbeddedMapping.getName());
		assertEquals("newName", embeddedResource.getName());
	
		//set name to null in the resource model
		embeddedResource.setName(null);
		assertNull(ormEmbeddedMapping.getName());
		assertNull(embeddedResource.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedMapping");
		OrmEmbeddedMapping ormEmbeddedMapping = (OrmEmbeddedMapping) ormPersistentAttribute.getMapping();
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		
		assertEquals("embeddedMapping", ormEmbeddedMapping.getName());
		assertEquals("embeddedMapping", embeddedResource.getName());
				
		//set name in the context model, verify resource model updated
		ormEmbeddedMapping.setName("newName");
		assertEquals("newName", ormEmbeddedMapping.getName());
		assertEquals("newName", embeddedResource.getName());
	
		//set name to null in the context model
		ormEmbeddedMapping.setName(null);
		assertNull(ormEmbeddedMapping.getName());
		assertNull(embeddedResource.getName());
	}
	
//	public void testAddSpecifiedAttributeOverride() throws Exception {
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedMapping");
//		OrmEmbeddedMapping ormEmbeddedMapping = (OrmEmbeddedMapping) ormPersistentAttribute.getMapping();
//		XmlEmbedded embeddedResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
//		
//		OrmAttributeOverride attributeOverride = ormEmbeddedMapping.addSpecifiedAttributeOverride(0);
//		attributeOverride.setName("FOO");
//				
//		assertEquals("FOO", embeddedResource.getAttributeOverrides().get(0).getName());
//		
//		OrmAttributeOverride attributeOverride2 = ormEmbeddedMapping.addSpecifiedAttributeOverride(0);
//		attributeOverride2.setName("BAR");
//		
//		assertEquals("BAR", embeddedResource.getAttributeOverrides().get(0).getName());
//		assertEquals("FOO", embeddedResource.getAttributeOverrides().get(1).getName());
//		
//		OrmAttributeOverride attributeOverride3 = ormEmbeddedMapping.addSpecifiedAttributeOverride(1);
//		attributeOverride3.setName("BAZ");
//		
//		assertEquals("BAR", embeddedResource.getAttributeOverrides().get(0).getName());
//		assertEquals("BAZ", embeddedResource.getAttributeOverrides().get(1).getName());
//		assertEquals("FOO", embeddedResource.getAttributeOverrides().get(2).getName());
//		
//		ListIterator<OrmAttributeOverride> attributeOverrides = ormEmbeddedMapping.specifiedAttributeOverrides();
//		assertEquals(attributeOverride2, attributeOverrides.next());
//		assertEquals(attributeOverride3, attributeOverrides.next());
//		assertEquals(attributeOverride, attributeOverrides.next());
//		
//		attributeOverrides = ormEmbeddedMapping.specifiedAttributeOverrides();
//		assertEquals("BAR", attributeOverrides.next().getName());
//		assertEquals("BAZ", attributeOverrides.next().getName());
//		assertEquals("FOO", attributeOverrides.next().getName());
//	}
//	
//	public void testRemoveSpecifiedAttributeOverride() throws Exception {
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedMapping");
//		OrmEmbeddedMapping ormEmbeddedMapping = (OrmEmbeddedMapping) ormPersistentAttribute.getMapping();
//		XmlEmbedded embeddedResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
//
//		ormEmbeddedMapping.addSpecifiedAttributeOverride(0).setName("FOO");
//		ormEmbeddedMapping.addSpecifiedAttributeOverride(1).setName("BAR");
//		ormEmbeddedMapping.addSpecifiedAttributeOverride(2).setName("BAZ");
//		
//		assertEquals(3, embeddedResource.getAttributeOverrides().size());
//		
//		ormEmbeddedMapping.removeSpecifiedAttributeOverride(0);
//		assertEquals(2, embeddedResource.getAttributeOverrides().size());
//		assertEquals("BAR", embeddedResource.getAttributeOverrides().get(0).getName());
//		assertEquals("BAZ", embeddedResource.getAttributeOverrides().get(1).getName());
//
//		ormEmbeddedMapping.removeSpecifiedAttributeOverride(0);
//		assertEquals(1, embeddedResource.getAttributeOverrides().size());
//		assertEquals("BAZ", embeddedResource.getAttributeOverrides().get(0).getName());
//		
//		ormEmbeddedMapping.removeSpecifiedAttributeOverride(0);
//		assertEquals(0, embeddedResource.getAttributeOverrides().size());
//	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedMapping");
		OrmEmbeddedMapping ormEmbeddedMapping = (OrmEmbeddedMapping) ormPersistentAttribute.getMapping();
		OrmAttributeOverrideContainer attributeOverrideContainer = ormEmbeddedMapping.getAttributeOverrideContainer();
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);

		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		
		embeddedResource.getAttributeOverrides().get(0).setName("FOO");
		embeddedResource.getAttributeOverrides().get(1).setName("BAR");
		embeddedResource.getAttributeOverrides().get(2).setName("BAZ");
		
		assertEquals(3, embeddedResource.getAttributeOverrides().size());		
		
		attributeOverrideContainer.moveSpecifiedAttributeOverride(2, 0);
		ListIterator<OrmAttributeOverride> attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAR", embeddedResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAZ", embeddedResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", embeddedResource.getAttributeOverrides().get(2).getName());


		attributeOverrideContainer.moveSpecifiedAttributeOverride(0, 1);
		attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAZ", embeddedResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAR", embeddedResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", embeddedResource.getAttributeOverrides().get(2).getName());
	}
	
	public void testUpdateAttributeOverrides() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embeddedMapping");
		OrmEmbeddedMapping ormEmbeddedMapping = (OrmEmbeddedMapping) ormPersistentAttribute.getMapping();
		OrmAttributeOverrideContainer attributeOverrideContainer = ormEmbeddedMapping.getAttributeOverrideContainer();
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		
		embeddedResource.getAttributeOverrides().get(0).setName("FOO");
		embeddedResource.getAttributeOverrides().get(1).setName("BAR");
		embeddedResource.getAttributeOverrides().get(2).setName("BAZ");

		ListIterator<OrmAttributeOverride> attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		embeddedResource.getAttributeOverrides().move(2, 0);
		attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		embeddedResource.getAttributeOverrides().move(0, 1);
		attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		embeddedResource.getAttributeOverrides().remove(1);
		attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		embeddedResource.getAttributeOverrides().remove(1);
		attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		embeddedResource.getAttributeOverrides().remove(0);
		assertFalse(attributeOverrideContainer.specifiedAttributeOverrides().hasNext());
	}
	
	
	public void testEmbeddedMappingNoUnderylingJavaAttribute() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		createTestEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "foo");
		assertEquals(3, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		OrmEmbeddedMapping ormEmbeddedMapping = (OrmEmbeddedMapping) ormPersistentAttribute.getMapping();
		OrmAttributeOverrideContainer attributeOverrideContainer = ormEmbeddedMapping.getAttributeOverrideContainer();
		
		assertEquals("foo", ormEmbeddedMapping.getName());

		
		assertFalse(attributeOverrideContainer.specifiedAttributeOverrides().hasNext());
		assertFalse(attributeOverrideContainer.virtualAttributeOverrides().hasNext());
	}
	
	public void testVirtualAttributeOverrides() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		createTestEmbeddableAddress();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType persistentType2 = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		
		//embedded mapping is virtual, specified attribute overrides should exist
		OrmPersistentAttribute ormPersistentAttribute = persistentType.getAttributeNamed("address");
		OrmEmbeddedMapping embeddedMapping = (OrmEmbeddedMapping) ormPersistentAttribute.getMapping();
		OrmAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		assertEquals(4, attributeOverrideContainer.attributeOverridesSize());
		assertEquals(0, attributeOverrideContainer.virtualAttributeOverridesSize());
		assertEquals(4, attributeOverrideContainer.specifiedAttributeOverridesSize());
		ListIterator<OrmAttributeOverride> specifiedAttributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		OrmAttributeOverride attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("city", attributeOverride.getName());
		attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("id", attributeOverride.getName());
		attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("state", attributeOverride.getName());
		attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("zip", attributeOverride.getName());
		
		JavaEmbeddedMapping javaEmbeddedMapping = (JavaEmbeddedMapping) ormPersistentAttribute.getJavaPersistentAttribute().getMapping();
		Column javaAttributeOverrideColumn = javaEmbeddedMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next().getColumn();
		
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

		JavaBasicMapping javaBasicMapping = (JavaBasicMapping) persistentType2.getJavaPersistentType().getAttributeNamed("state").getMapping();
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
		assertEquals("id", attributeOverride.getName());
		attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("state", attributeOverride.getName());
		assertEquals("MY_STATE_COLUMN", attributeOverride.getColumn().getSpecifiedName());
		attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("zip", attributeOverride.getName());
		
		
		
		//embedded mapping is specified, virtual attribute overrides should exist
		persistentType.getAttributeNamed("address").makeSpecified();
		getOrmXmlResource().save(null);
		embeddedMapping = (OrmEmbeddedMapping) persistentType.getAttributeNamed("address").getMapping();
		attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		assertEquals(4, attributeOverrideContainer.attributeOverridesSize());
		assertEquals(4, attributeOverrideContainer.virtualAttributeOverridesSize());
		assertEquals(0, attributeOverrideContainer.specifiedAttributeOverridesSize());
		ListIterator<OrmAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.virtualAttributeOverrides();
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("city", attributeOverride.getName());
		assertEquals("city", attributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME, attributeOverride.getColumn().getTable());
		assertEquals(null, attributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, attributeOverride.getColumn().isInsertable());
		assertEquals(true, attributeOverride.getColumn().isUpdatable());
		assertEquals(false, attributeOverride.getColumn().isUnique());
		assertEquals(true, attributeOverride.getColumn().isNullable());
		assertEquals(255, attributeOverride.getColumn().getLength());
		assertEquals(0, attributeOverride.getColumn().getPrecision());
		assertEquals(0, attributeOverride.getColumn().getScale());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("state", attributeOverride.getName());
		assertEquals("MY_STATE_COLUMN", attributeOverride.getColumn().getDefaultName());
		assertEquals(TYPE_NAME, attributeOverride.getColumn().getDefaultTable());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("zip", attributeOverride.getName());
		
		//set one of the virtual attribute overrides to specified, verify others are still virtual
		attributeOverrideContainer.virtualAttributeOverrides().next().setVirtual(false);
		
		assertEquals(4, attributeOverrideContainer.attributeOverridesSize());
		assertEquals(1, attributeOverrideContainer.specifiedAttributeOverridesSize());
		assertEquals(3, attributeOverrideContainer.virtualAttributeOverridesSize());
		assertEquals("id", attributeOverrideContainer.specifiedAttributeOverrides().next().getName());
		virtualAttributeOverrides = attributeOverrideContainer.virtualAttributeOverrides();
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("city", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("state", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("zip", attributeOverride.getName());
	}

	
	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		createTestEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		OrmEmbeddedMapping ormEmbeddedMapping = (OrmEmbeddedMapping) ormPersistentAttribute.getMapping();	
		OrmAttributeOverrideContainer attributeOverrideContainer = ormEmbeddedMapping.getAttributeOverrideContainer();
		assertEquals("address", ormEmbeddedMapping.getName());

		assertEquals(4, attributeOverrideContainer.specifiedAttributeOverridesSize());
		assertEquals(0, attributeOverrideContainer.virtualAttributeOverridesSize());
		ListIterator<OrmAttributeOverride> ormAttributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();

		OrmAttributeOverride ormAttributeOverride = ormAttributeOverrides.next();
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, ormAttributeOverride.getName());
		OrmColumn ormColumn = ormAttributeOverride.getColumn();
		assertEquals(ATTRIBUTE_OVERRIDE_COLUMN_NAME, ormColumn.getSpecifiedName());

	
		ormAttributeOverride = ormAttributeOverrides.next();
		assertEquals("id", ormAttributeOverride.getName());
		ormColumn = ormAttributeOverride.getColumn();
		assertEquals("id", ormColumn.getSpecifiedName());

		ormAttributeOverride = ormAttributeOverrides.next();
		assertEquals("state", ormAttributeOverride.getName());
		ormColumn = ormAttributeOverride.getColumn();
		assertEquals("A_STATE", ormColumn.getSpecifiedName());

		ormAttributeOverride = ormAttributeOverrides.next();
		assertEquals("zip", ormAttributeOverride.getName());
		ormColumn = ormAttributeOverride.getColumn();
		assertEquals("zip", ormColumn.getSpecifiedName());

	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		createTestEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(3, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		OrmEmbeddedMapping ormEmbeddedMapping = (OrmEmbeddedMapping) ormPersistentAttribute.getMapping();	
		assertEquals("address", ormEmbeddedMapping.getName());

		//TODO
//		assertEquals(4, ormEmbeddedMapping.specifiedAttributeOverridesSize());
//		assertEquals(0, CollectionTools.size(ormEmbeddedMapping.defaultAttributeOverrides()));
//		ListIterator<XmlAttributeOverride> ormAttributeOverrides = ormEmbeddedMapping.specifiedAttributeOverrides();
//
//		XmlAttributeOverride ormAttributeOverride = ormAttributeOverrides.next();
//		assertEquals(ATTRIBUTE_OVERRIDE_NAME, ormAttributeOverride.getName());
//		XmlColumn xmlColumn = ormAttributeOverride.getColumn();
//		assertEquals("city", xmlColumn.getSpecifiedName());
////		assertEquals(Boolean.TRUE, xmlColumn.getSpecifiedUnique());
////		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedNullable());
////		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedInsertable());
////		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedUpdatable());
////		assertEquals("COLUMN_DEFINITION", xmlColumn.getColumnDefinition());
////		assertEquals("MY_TABLE", xmlColumn.getSpecifiedTable());
////		assertEquals(Integer.valueOf(5), xmlColumn.getSpecifiedLength());
////		assertEquals(Integer.valueOf(6), xmlColumn.getSpecifiedPrecision());
////		assertEquals(Integer.valueOf(7), xmlColumn.getSpecifiedScale());
//		
//		ormAttributeOverride = ormAttributeOverrides.next();
//		assertEquals("id", ormAttributeOverride.getName());
//		xmlColumn = ormAttributeOverride.getColumn();
//		assertEquals("id", xmlColumn.getSpecifiedName());
//
//		ormAttributeOverride = ormAttributeOverrides.next();
//		assertEquals("state", ormAttributeOverride.getName());
//		xmlColumn = ormAttributeOverride.getColumn();
//		assertEquals("state", xmlColumn.getSpecifiedName());
//
//		ormAttributeOverride = ormAttributeOverrides.next();
//		assertEquals("zip", ormAttributeOverride.getName());
//		xmlColumn = ormAttributeOverride.getColumn();
//		assertEquals("zip", xmlColumn.getSpecifiedName());
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		createTestEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "address");
		assertEquals(2, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		OrmEmbeddedMapping ormEmbeddedMapping = (OrmEmbeddedMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("address", ormEmbeddedMapping.getName());

		assertEquals(0, ormEmbeddedMapping.getAttributeOverrideContainer().specifiedAttributeOverridesSize());
		//TODO
//		assertEquals(4, CollectionTools.size(ormEmbeddedMapping.defaultAttributeOverrides()));
//		ListIterator<XmlAttributeOverride> ormAttributeOverrides = ormEmbeddedMapping.defaultAttributeOverrides();
//
//		XmlAttributeOverride ormAttributeOverride = ormAttributeOverrides.next();
//		assertEquals(ATTRIBUTE_OVERRIDE_NAME, ormAttributeOverride.getName());
//		XmlColumn xmlColumn = ormAttributeOverride.getColumn();
//		assertEquals("city", xmlColumn.getDefaultName());
////		assertEquals(Boolean.TRUE, xmlColumn.getSpecifiedUnique());
////		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedNullable());
////		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedInsertable());
////		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedUpdatable());
////		assertEquals("COLUMN_DEFINITION", xmlColumn.getColumnDefinition());
////		assertEquals("MY_TABLE", xmlColumn.getSpecifiedTable());
////		assertEquals(Integer.valueOf(5), xmlColumn.getSpecifiedLength());
////		assertEquals(Integer.valueOf(6), xmlColumn.getSpecifiedPrecision());
////		assertEquals(Integer.valueOf(7), xmlColumn.getSpecifiedScale());
//		
//		ormAttributeOverride = ormAttributeOverrides.next();
//		assertEquals("id", ormAttributeOverride.getName());
//		xmlColumn = ormAttributeOverride.getColumn();
//		assertEquals("id", xmlColumn.getDefaultName());
//
//		ormAttributeOverride = ormAttributeOverrides.next();
//		assertEquals("state", ormAttributeOverride.getName());
//		xmlColumn = ormAttributeOverride.getColumn();
//		assertEquals("state", xmlColumn.getDefaultName());
//
//		ormAttributeOverride = ormAttributeOverrides.next();
//		assertEquals("zip", ormAttributeOverride.getName());
//		xmlColumn = ormAttributeOverride.getColumn();
//		assertEquals("zip", xmlColumn.getDefaultName());
	}
	
	
	public void testEmbeddedMorphToIdMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embedded");
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("embedded", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedMorphToVersionMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embedded");
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("embedded", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedMorphToTransientMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embedded");
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("embedded", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedMorphToBasicMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embedded");
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("embedded", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embedded");
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertEquals("embedded", ormPersistentAttribute.getMapping().getName());
		attributeOverride = ((EmbeddedIdMapping) ormPersistentAttribute.getMapping()).getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		assertEquals("override", attributeOverride.getName());
		assertEquals("OVERRIDE_COLUMN", attributeOverride.getColumn().getSpecifiedName());
	}
	
	public void testEmbeddedMorphToOneToOneMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embedded");
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToOneMapping);
		assertEquals("embedded", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedMorphToOneToManyMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embedded");
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("embedded", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedMorphToManyToOneMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embedded");
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("embedded", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedMorphToManyToManyMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "embedded");
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("embedded", ormPersistentAttribute.getMapping().getName());
	}
	
	
	public void testSpecifiedAssociationOverrides() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "myEmbedded");
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();

		OrmAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();
		ListIterator<OrmAssociationOverride> specifiedAssociationOverrides = overrideContainer.specifiedAssociationOverrides();
		
		assertFalse(specifiedAssociationOverrides.hasNext());

		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		
		//add an annotation to the resource model and verify the context model is updated
		XmlAssociationOverride associationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		embeddedResource.getAssociationOverrides().add(associationOverride);
		associationOverride.setName("FOO");
		specifiedAssociationOverrides = overrideContainer.specifiedAssociationOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		associationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		embeddedResource.getAssociationOverrides().add(1, associationOverride);
		associationOverride.setName("BAR");
		specifiedAssociationOverrides = overrideContainer.specifiedAssociationOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());


		associationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		embeddedResource.getAssociationOverrides().add(0, associationOverride);
		associationOverride.setName("BAZ");
		specifiedAssociationOverrides = overrideContainer.specifiedAssociationOverrides();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		embeddedResource.getAssociationOverrides().move(1, 0);
		specifiedAssociationOverrides = overrideContainer.specifiedAssociationOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		embeddedResource.getAssociationOverrides().remove(0);
		specifiedAssociationOverrides = overrideContainer.specifiedAssociationOverrides();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		embeddedResource.getAssociationOverrides().remove(0);
		specifiedAssociationOverrides = overrideContainer.specifiedAssociationOverrides();		
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		
		embeddedResource.getAssociationOverrides().remove(0);
		specifiedAssociationOverrides = overrideContainer.specifiedAssociationOverrides();		
		assertFalse(specifiedAssociationOverrides.hasNext());
	}

	public void testVirtualAssociationOverrideDefaults() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "myEmbedded");
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();
		
		assertEquals(2, overrideContainer.virtualAssociationOverridesSize());
		AssociationOverride virtualAssociationOverride = overrideContainer.virtualAssociationOverrides().next();
		JoinColumnJoiningStrategy joiningStrategy = virtualAssociationOverride.getRelationshipReference().getJoinColumnJoiningStrategy();
		assertEquals("address", virtualAssociationOverride.getName());
		assertEquals(1, joiningStrategy.joinColumnsSize());
		JoinColumn virtualJoinColumn = joiningStrategy.joinColumns().next();
		assertEquals("address_id", virtualJoinColumn.getName());
		assertEquals("id", virtualJoinColumn.getReferencedColumnName());
		assertEquals(TYPE_NAME, virtualJoinColumn.getTable());
		assertEquals(null, virtualJoinColumn.getColumnDefinition());
		assertEquals(true, virtualJoinColumn.isInsertable());
		assertEquals(true, virtualJoinColumn.isUpdatable());
		assertEquals(false, virtualJoinColumn.isUnique());
		assertEquals(true, virtualJoinColumn.isNullable());
		

		JavaPersistentType javaEmbeddable = getPersistenceUnit().specifiedClassRefs().next().getJavaPersistentType(); 
		OneToOneMapping oneToOneMapping = (OneToOneMapping) javaEmbeddable.getAttributeNamed("address").getMapping();
		JoinColumn joinColumn = oneToOneMapping.getRelationshipReference().getJoinColumnJoiningStrategy().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("MY_JOIN_COLUMN");
		joinColumn.setSpecifiedReferencedColumnName("MY_REFERENCE_COLUMN");
		joinColumn.setSpecifiedTable("BAR");
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setSpecifiedInsertable(Boolean.FALSE);
		joinColumn.setSpecifiedUpdatable(Boolean.FALSE);
		joinColumn.setSpecifiedUnique(Boolean.TRUE);
		joinColumn.setSpecifiedNullable(Boolean.FALSE);

		assertEquals(2, overrideContainer.virtualAssociationOverridesSize());
		virtualAssociationOverride = overrideContainer.virtualAssociationOverrides().next();
		joiningStrategy = virtualAssociationOverride.getRelationshipReference().getJoinColumnJoiningStrategy();
		assertEquals("address", virtualAssociationOverride.getName());
		assertEquals(1, joiningStrategy.joinColumnsSize());
		virtualAssociationOverride = overrideContainer.virtualAssociationOverrides().next();
		virtualJoinColumn = joiningStrategy.joinColumns().next();
		assertEquals("MY_JOIN_COLUMN", virtualJoinColumn.getName());
		assertEquals("MY_REFERENCE_COLUMN", virtualJoinColumn.getReferencedColumnName());
		assertEquals("BAR", virtualJoinColumn.getTable());
		assertEquals("COLUMN_DEF", virtualJoinColumn.getColumnDefinition());
		assertEquals(false, virtualJoinColumn.isInsertable());
		assertEquals(false, virtualJoinColumn.isUpdatable());
		assertEquals(true, virtualJoinColumn.isUnique());
		assertEquals(false, virtualJoinColumn.isNullable());

		assertEquals("MY_JOIN_COLUMN", joiningStrategy.joinColumns().next().getName());


		virtualAssociationOverride = overrideContainer.virtualAssociationOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());
		
		virtualAssociationOverride = virtualAssociationOverride.setVirtual(false);
		assertEquals(1, overrideContainer.virtualAssociationOverridesSize());
	}
	
	public void testSpecifiedAssociationOverridesSize() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "myEmbedded");
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		
		assertEquals(0, overrideContainer.specifiedAssociationOverridesSize());

		//add an annotation to the resource model and verify the context model is updated
		XmlAssociationOverride associationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		embeddedResource.getAssociationOverrides().add(associationOverride);
		associationOverride.setName("FOO");
		associationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		embeddedResource.getAssociationOverrides().add(0, associationOverride);
		associationOverride.setName("BAR");

		assertEquals(2, overrideContainer.specifiedAssociationOverridesSize());
	}
	
	public void testVirtualAssociationOverridesSize() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "myEmbedded");
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();
		
		assertEquals(2, overrideContainer.virtualAssociationOverridesSize());
		
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(1, overrideContainer.virtualAssociationOverridesSize());
		
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(0, overrideContainer.virtualAssociationOverridesSize());
	}
	
	public void testAssociationOverridesSize() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "myEmbedded");
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		assertEquals(2, overrideContainer.associationOverridesSize());

		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(2, overrideContainer.associationOverridesSize());
		
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		assertEquals(2, overrideContainer.associationOverridesSize());
		
		
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);

		XmlAssociationOverride associationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		embeddedResource.getAssociationOverrides().add(associationOverride);
		associationOverride.setName("bar");	
		assertEquals(3, overrideContainer.associationOverridesSize());
	}

	public void testAssociationOverrideSetVirtual() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "myEmbedded");
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		
		assertEquals("address", embeddedResource.getAssociationOverrides().get(0).getName());
		assertEquals("addresses", embeddedResource.getAssociationOverrides().get(1).getName());
		assertEquals(2, embeddedResource.getAssociationOverrides().size());
	}
	
	public void testAssociationOverrideSetVirtual2() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "myEmbedded");
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		ListIterator<OrmAssociationOverride> virtualAssociationOverrides = overrideContainer.virtualAssociationOverrides();
		virtualAssociationOverrides.next();
		virtualAssociationOverrides.next().setVirtual(false);
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		
		assertEquals("addresses", embeddedResource.getAssociationOverrides().get(0).getName());
		assertEquals("address", embeddedResource.getAssociationOverrides().get(1).getName());
		assertEquals(2, embeddedResource.getAssociationOverrides().size());
	}
	
	public void testAssociationOverrideSetVirtualTrue() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "myEmbedded");
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		assertEquals(2, embeddedResource.getAssociationOverrides().size());

		overrideContainer.specifiedAssociationOverrides().next().setVirtual(true);
		
		assertEquals("addresses", embeddedResource.getAssociationOverrides().get(0).getName());
		assertEquals(1, embeddedResource.getAssociationOverrides().size());

		Iterator<OrmAssociationOverride> associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("addresses", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		
		overrideContainer.specifiedAssociationOverrides().next().setVirtual(true);
		assertEquals(0, embeddedResource.getAssociationOverrides().size());
		associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertFalse(associationOverrides.hasNext());
	}
	
	public void testMoveSpecifiedAssociationOverride() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "myEmbedded");
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);

		
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		assertEquals(2, embeddedResource.getAssociationOverrides().size());
		
		
		overrideContainer.moveSpecifiedAssociationOverride(1, 0);
		ListIterator<AssociationOverride> associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("addresses", associationOverrides.next().getName());
		assertEquals("address", associationOverrides.next().getName());

		assertEquals("addresses", embeddedResource.getAssociationOverrides().get(0).getName());
		assertEquals("address", embeddedResource.getAssociationOverrides().get(1).getName());


		overrideContainer.moveSpecifiedAssociationOverride(0, 1);
		associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("address", associationOverrides.next().getName());
		assertEquals("addresses", associationOverrides.next().getName());

		assertEquals("address", embeddedResource.getAssociationOverrides().get(0).getName());
		assertEquals("addresses", embeddedResource.getAssociationOverrides().get(1).getName());
	}

	public void testUpdateSpecifiedAssociationOverrides() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "myEmbedded");
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
	
		XmlAssociationOverride associationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		embeddedResource.getAssociationOverrides().add(0, associationOverride);
		associationOverride.setName("FOO");
		associationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		embeddedResource.getAssociationOverrides().add(1, associationOverride);
		associationOverride.setName("BAR");
		associationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		embeddedResource.getAssociationOverrides().add(2, associationOverride);
		associationOverride.setName("BAZ");
			
		ListIterator<AssociationOverride> associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("FOO", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		embeddedResource.getAssociationOverrides().move(2, 0);
		associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		embeddedResource.getAssociationOverrides().move(0, 1);
		associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		embeddedResource.getAssociationOverrides().remove(1);
		associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		embeddedResource.getAssociationOverrides().remove(1);
		associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		embeddedResource.getAssociationOverrides().remove(0);
		associationOverrides = overrideContainer.specifiedAssociationOverrides();
		assertFalse(associationOverrides.hasNext());
	}

	public void testAssociationOverrideIsVirtual() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "myEmbedded");
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		ListIterator<OrmAssociationOverride> virtualAssociationOverrides = overrideContainer.virtualAssociationOverrides();	
		AssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());

		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("addresses", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		assertFalse(virtualAssociationOverrides.hasNext());

		overrideContainer.virtualAssociationOverrides().next().setVirtual(false);
		AssociationOverride specifiedAssociationOverride = overrideContainer.specifiedAssociationOverrides().next();
		assertFalse(specifiedAssociationOverride.isVirtual());
		
		
		virtualAssociationOverrides = overrideContainer.virtualAssociationOverrides();	
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("addresses", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		assertFalse(virtualAssociationOverrides.hasNext());
	}
	
	public void testVirtualAssociationOverrideJoinTableDefaults() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		getPersistenceXmlResource().save(null);
		getOrmXmlResource().save(null);
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, "myEmbedded");
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();
		
		assertEquals(2, overrideContainer.virtualAssociationOverridesSize());
		AssociationOverride virtualAssociationOverride = CollectionTools.get(overrideContainer.virtualAssociationOverrides(), 1);
		JoinTableJoiningStrategy joiningStrategy = ((AssociationOverrideRelationshipReference2_0) virtualAssociationOverride.getRelationshipReference()).getJoinTableJoiningStrategy();
		JoinTable joinTable = joiningStrategy.getJoinTable();
		assertEquals("addresses", virtualAssociationOverride.getName());
		assertEquals("AnnotationTestType_Address", joinTable.getName());
		assertEquals(1, joinTable.joinColumnsSize());
		JoinColumn virtualJoinColumn = joinTable.joinColumns().next();
		assertEquals("AnnotationTestType_id", virtualJoinColumn.getName());
		assertEquals("id", virtualJoinColumn.getReferencedColumnName());
		assertEquals("AnnotationTestType_Address", virtualJoinColumn.getTable());
		assertEquals(null, virtualJoinColumn.getColumnDefinition());
		assertEquals(true, virtualJoinColumn.isInsertable());
		assertEquals(true, virtualJoinColumn.isUpdatable());
		assertEquals(false, virtualJoinColumn.isUnique());
		assertEquals(true, virtualJoinColumn.isNullable());
		
		assertEquals(1, joinTable.inverseJoinColumnsSize());
		JoinColumn virtualInverseJoinColumn = joinTable.inverseJoinColumns().next();
		assertEquals("addresses_id", virtualInverseJoinColumn.getName());
		assertEquals("id", virtualInverseJoinColumn.getReferencedColumnName());
		assertEquals("AnnotationTestType_Address", virtualInverseJoinColumn.getTable());
		assertEquals(null, virtualInverseJoinColumn.getColumnDefinition());
		assertEquals(true, virtualInverseJoinColumn.isInsertable());
		assertEquals(true, virtualInverseJoinColumn.isUpdatable());
		assertEquals(false, virtualInverseJoinColumn.isUnique());
		assertEquals(true, virtualInverseJoinColumn.isNullable());
		
		JavaPersistentType javaEmbeddable = getPersistenceUnit().specifiedClassRefs().next().getJavaPersistentType(); 
		OneToManyMapping oneToManyMapping = (OneToManyMapping) javaEmbeddable.getAttributeNamed("addresses").getMapping();
		JoinTableJoiningStrategy joinTableStrategy = oneToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy();
		joinTableStrategy.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = joinTableStrategy.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("MY_JOIN_COLUMN");
		joinColumn.setSpecifiedReferencedColumnName("MY_REFERENCE_COLUMN");
		joinColumn.setSpecifiedTable("BAR");
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setSpecifiedInsertable(Boolean.FALSE);
		joinColumn.setSpecifiedUpdatable(Boolean.FALSE);
		joinColumn.setSpecifiedUnique(Boolean.TRUE);
		joinColumn.setSpecifiedNullable(Boolean.FALSE);
		
		JoinColumn inverseJoinColumn = joinTableStrategy.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("MY_INVERSE_JOIN_COLUMN");
		inverseJoinColumn.setSpecifiedReferencedColumnName("MY_INVERSE_REFERENCE_COLUMN");
		inverseJoinColumn.setSpecifiedTable("INVERSE_BAR");
		inverseJoinColumn.setColumnDefinition("INVERSE_COLUMN_DEF");
		inverseJoinColumn.setSpecifiedInsertable(Boolean.FALSE);
		inverseJoinColumn.setSpecifiedUpdatable(Boolean.FALSE);
		inverseJoinColumn.setSpecifiedUnique(Boolean.TRUE);
		inverseJoinColumn.setSpecifiedNullable(Boolean.FALSE);
		
		assertEquals(2, overrideContainer.virtualAssociationOverridesSize());
		virtualAssociationOverride = CollectionTools.get(overrideContainer.virtualAssociationOverrides(), 1);
		joiningStrategy = ((AssociationOverrideRelationshipReference2_0) virtualAssociationOverride.getRelationshipReference()).getJoinTableJoiningStrategy();
		joinTable = joiningStrategy.getJoinTable();
		assertEquals("addresses", virtualAssociationOverride.getName());
		assertEquals(1, joinTable.joinColumnsSize());
		virtualJoinColumn = joinTable.joinColumns().next();
		assertEquals("MY_JOIN_COLUMN", virtualJoinColumn.getName());
		assertEquals("MY_REFERENCE_COLUMN", virtualJoinColumn.getReferencedColumnName());
		assertEquals("BAR", virtualJoinColumn.getTable());
		assertEquals("COLUMN_DEF", virtualJoinColumn.getColumnDefinition());
		assertEquals(false, virtualJoinColumn.isInsertable());
		assertEquals(false, virtualJoinColumn.isUpdatable());
		assertEquals(true, virtualJoinColumn.isUnique());
		assertEquals(false, virtualJoinColumn.isNullable());

		assertEquals(1, joinTable.inverseJoinColumnsSize());
		virtualInverseJoinColumn = joinTable.inverseJoinColumns().next();
		assertEquals("MY_INVERSE_JOIN_COLUMN", virtualInverseJoinColumn.getName());
		assertEquals("MY_INVERSE_REFERENCE_COLUMN", virtualInverseJoinColumn.getReferencedColumnName());
		assertEquals("INVERSE_BAR", virtualInverseJoinColumn.getTable());
		assertEquals("INVERSE_COLUMN_DEF", virtualInverseJoinColumn.getColumnDefinition());
		assertEquals(false, virtualInverseJoinColumn.isInsertable());
		assertEquals(false, virtualInverseJoinColumn.isUpdatable());
		assertEquals(true, virtualInverseJoinColumn.isUnique());
		assertEquals(false, virtualInverseJoinColumn.isNullable());

		
		//add the java association override and make sure the settings are not used in determining the association override defaults in the specified orm embedded mapping
		JavaEmbeddedMapping2_0 javaEmbeddedMapping = (JavaEmbeddedMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		ListIterator<JavaAssociationOverride> javaAssociationOverrides = javaEmbeddedMapping.getAssociationOverrideContainer().associationOverrides();
		javaAssociationOverrides.next();
		JavaAssociationOverride javaAssociationOverride = javaAssociationOverrides.next().setVirtual(false);
		assertEquals("addresses", javaAssociationOverride.getName());
		JavaJoinTable javaJoinTable = ((JavaAssociationOverrideRelationshipReference2_0) javaAssociationOverride.getRelationshipReference()).getJoinTableJoiningStrategy().getJoinTable();
		javaJoinTable.setSpecifiedName("JAVA_FOO");
		JavaJoinColumn javaJoinColumn = javaJoinTable.addSpecifiedJoinColumn(0);
		javaJoinColumn.setSpecifiedName("JAVA_JOIN_COLUMN_NAME");
		javaJoinColumn.setSpecifiedReferencedColumnName("JAVA_JOIN_COLUMN_REFERENCED_NAME");
		JavaJoinColumn javaInverseJoinColumn = javaJoinTable.addSpecifiedInverseJoinColumn(0);
		javaInverseJoinColumn.setSpecifiedName("JAVA_INVERSE_JOIN_COLUMN_NAME");
		javaInverseJoinColumn.setSpecifiedReferencedColumnName("JAVA_INVERSE_JOIN_COLUMN_REFERENCED_NAME");
		
		getOrmXmlResource().save(null);
		
		assertEquals(2, overrideContainer.virtualAssociationOverridesSize());
		virtualAssociationOverride = CollectionTools.get(overrideContainer.virtualAssociationOverrides(), 1);
		joiningStrategy = ((AssociationOverrideRelationshipReference2_0) virtualAssociationOverride.getRelationshipReference()).getJoinTableJoiningStrategy();
		joinTable = joiningStrategy.getJoinTable();
		assertEquals("addresses", virtualAssociationOverride.getName());
		assertEquals("MY_JOIN_TABLE", joinTable.getName());
		assertEquals(1, joinTable.joinColumnsSize());
		virtualJoinColumn = joinTable.joinColumns().next();
		
		getOrmXmlResource().save(null);

		assertEquals("MY_JOIN_COLUMN", virtualJoinColumn.getName());
		assertEquals("MY_REFERENCE_COLUMN", virtualJoinColumn.getReferencedColumnName());
		assertEquals("BAR", virtualJoinColumn.getTable());
		assertEquals("COLUMN_DEF", virtualJoinColumn.getColumnDefinition());
		assertEquals(false, virtualJoinColumn.isInsertable());
		assertEquals(false, virtualJoinColumn.isUpdatable());
		assertEquals(true, virtualJoinColumn.isUnique());
		assertEquals(false, virtualJoinColumn.isNullable());

		assertEquals(1, joinTable.inverseJoinColumnsSize());
		virtualInverseJoinColumn = joinTable.inverseJoinColumns().next();
		assertEquals("MY_INVERSE_JOIN_COLUMN", virtualInverseJoinColumn.getName());
		assertEquals("MY_INVERSE_REFERENCE_COLUMN", virtualInverseJoinColumn.getReferencedColumnName());
		assertEquals("INVERSE_BAR", virtualInverseJoinColumn.getTable());
		assertEquals("INVERSE_COLUMN_DEF", virtualInverseJoinColumn.getColumnDefinition());
		assertEquals(false, virtualInverseJoinColumn.isInsertable());
		assertEquals(false, virtualInverseJoinColumn.isUpdatable());
		assertEquals(true, virtualInverseJoinColumn.isUnique());
		assertEquals(false, virtualInverseJoinColumn.isNullable());
		
		
		virtualAssociationOverride = overrideContainer.virtualAssociationOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());
		
		virtualAssociationOverride = virtualAssociationOverride.setVirtual(false);
		assertEquals(1, overrideContainer.virtualAssociationOverridesSize());
		
		
		ormPersistentType.removeSpecifiedPersistentAttribute(ormPersistentAttribute);
		getOrmXmlResource().save(null);
		embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentType.getAttributeNamed("myEmbedded").getMapping();
		overrideContainer = embeddedMapping.getAssociationOverrideContainer();
		virtualAssociationOverride = CollectionTools.get(overrideContainer.virtualAssociationOverrides(), 1);
		assertEquals("addresses", virtualAssociationOverride.getName());
		joiningStrategy = ((AssociationOverrideRelationshipReference2_0) virtualAssociationOverride.getRelationshipReference()).getJoinTableJoiningStrategy();
		joinTable = joiningStrategy.getJoinTable();
		assertEquals("JAVA_FOO", joinTable.getName());
		assertEquals(1, joinTable.joinColumnsSize());
		virtualJoinColumn = joinTable.joinColumns().next();
		assertEquals("JAVA_JOIN_COLUMN_NAME", virtualJoinColumn.getName());
		assertEquals("JAVA_JOIN_COLUMN_REFERENCED_NAME", virtualJoinColumn.getReferencedColumnName());
		assertEquals("JAVA_FOO", virtualJoinColumn.getTable());

		assertEquals(1, joinTable.inverseJoinColumnsSize());
		virtualInverseJoinColumn = joinTable.inverseJoinColumns().next();
		assertEquals("JAVA_INVERSE_JOIN_COLUMN_NAME", virtualInverseJoinColumn.getName());
		assertEquals("JAVA_INVERSE_JOIN_COLUMN_REFERENCED_NAME", virtualInverseJoinColumn.getReferencedColumnName());
		assertEquals("JAVA_FOO", virtualInverseJoinColumn.getTable());
	}

	public void testNestedVirtualAttributeOverrides() throws Exception {
		createTestEntityCustomer();
		createTestEmbeddableAddress2();
		createTestEmbeddableZipCode();
		
		OrmPersistentType customerPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Customer");
		OrmPersistentType addressPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		OrmPersistentType zipCodePersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".ZipCode");

		customerPersistentType.getAttributeNamed("address").makeSpecified();
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) customerPersistentType.getAttributeNamed("address").getMapping();
		AttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		
		assertEquals(5, attributeOverrideContainer.virtualAttributeOverridesSize());
		ListIterator<AttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.virtualAttributeOverrides();
		AttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("street", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("city", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("state", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("zipCode.zip", virtualAttributeOverride.getName());
		assertEquals("zip", virtualAttributeOverride.getColumn().getName());
		assertEquals("Customer", virtualAttributeOverride.getColumn().getTable());		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("zipCode.plusfour", virtualAttributeOverride.getName());
		assertEquals("plusfour", virtualAttributeOverride.getColumn().getName());
		assertEquals("Customer", virtualAttributeOverride.getColumn().getTable());	
		assertEquals(null, virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(true, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(255, virtualAttributeOverride.getColumn().getLength());
		assertEquals(0, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(0, virtualAttributeOverride.getColumn().getScale());

		
		addressPersistentType.getAttributeNamed("zipCode").makeSpecified();
		EmbeddedMapping nestedEmbeddedMapping = (EmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping();
		AttributeOverrideContainer nestedAttributeOverrideContainer = nestedEmbeddedMapping.getAttributeOverrideContainer();
		assertEquals(2, nestedAttributeOverrideContainer.virtualAttributeOverridesSize());
		virtualAttributeOverrides = nestedAttributeOverrideContainer.virtualAttributeOverrides();
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("zip", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("plusfour", virtualAttributeOverride.getName());
		
		zipCodePersistentType.getAttributeNamed("plusfour").makeSpecified();
		BasicMapping plusFourMapping = (BasicMapping) zipCodePersistentType.getAttributeNamed("plusfour").getMapping();
		plusFourMapping.getColumn().setSpecifiedName("BLAH");
		plusFourMapping.getColumn().setSpecifiedTable("BLAH_TABLE");
		plusFourMapping.getColumn().setColumnDefinition("COLUMN_DEFINITION");
		plusFourMapping.getColumn().setSpecifiedInsertable(Boolean.FALSE);
		plusFourMapping.getColumn().setSpecifiedUpdatable(Boolean.FALSE);
		plusFourMapping.getColumn().setSpecifiedUnique(Boolean.TRUE);
		plusFourMapping.getColumn().setSpecifiedNullable(Boolean.FALSE);
		plusFourMapping.getColumn().setSpecifiedLength(Integer.valueOf(5));
		plusFourMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(6));
		plusFourMapping.getColumn().setSpecifiedScale(Integer.valueOf(7));

		//check the nested embedded (Address.zipCode) attribute override to verify it is getting settings from the specified column on Zipcode.plusfour
		virtualAttributeOverride = ((EmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping()).getAttributeOverrideContainer().getAttributeOverrideNamed("plusfour");
		assertEquals("plusfour", virtualAttributeOverride.getName());
		assertEquals("BLAH", virtualAttributeOverride.getColumn().getName());
		assertEquals("BLAH_TABLE", virtualAttributeOverride.getColumn().getTable());	
		assertEquals("COLUMN_DEFINITION", virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(false, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(false, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(5, virtualAttributeOverride.getColumn().getLength());
		assertEquals(6, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(7, virtualAttributeOverride.getColumn().getScale());

		//check the top-level embedded (Customer.address) attribute override to verify it is getting settings from the specified column on Zipcode.plusfour
		virtualAttributeOverride = ((EmbeddedMapping) customerPersistentType.getAttributeNamed("address").getMapping()).getAttributeOverrideContainer().getAttributeOverrideNamed("zipCode.plusfour");
		assertEquals("zipCode.plusfour", virtualAttributeOverride.getName());
		assertEquals("BLAH", virtualAttributeOverride.getColumn().getName());
		assertEquals("BLAH_TABLE", virtualAttributeOverride.getColumn().getTable());	
		assertEquals("COLUMN_DEFINITION", virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(false, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(false, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(5, virtualAttributeOverride.getColumn().getLength());
		assertEquals(6, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(7, virtualAttributeOverride.getColumn().getScale());
		
		//set an attribute override on Address.zipCode embedded mapping
		AttributeOverride specifiedAttributeOverride = ((EmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping()).getAttributeOverrideContainer().getAttributeOverrideNamed("plusfour").setVirtual(false);
		specifiedAttributeOverride.getColumn().setSpecifiedName("BLAH_OVERRIDE");
		specifiedAttributeOverride.getColumn().setSpecifiedTable("BLAH_TABLE_OVERRIDE");
		specifiedAttributeOverride.getColumn().setColumnDefinition("COLUMN_DEFINITION_OVERRIDE");
	
		virtualAttributeOverride = ((EmbeddedMapping) customerPersistentType.getAttributeNamed("address").getMapping()).getAttributeOverrideContainer().getAttributeOverrideNamed("zipCode.plusfour");
		assertEquals("zipCode.plusfour", virtualAttributeOverride.getName());
		assertEquals("BLAH_OVERRIDE", virtualAttributeOverride.getColumn().getName());
		assertEquals("BLAH_TABLE_OVERRIDE", virtualAttributeOverride.getColumn().getTable());	
		assertEquals("COLUMN_DEFINITION_OVERRIDE", virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(true, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(255, virtualAttributeOverride.getColumn().getLength());
		assertEquals(0, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(0, virtualAttributeOverride.getColumn().getScale());
		
		specifiedAttributeOverride = virtualAttributeOverride.setVirtual(false);
		assertEquals(false, specifiedAttributeOverride.isVirtual());
		assertEquals("zipCode.plusfour", specifiedAttributeOverride.getName());
		//TODO I have the default wrong in this case, but this was wrong before as well.  Need to fix this later
//		assertEquals("plusfour", specifiedAttributeOverride.getColumn().getDefaultName());
		assertEquals("BLAH_OVERRIDE", specifiedAttributeOverride.getColumn().getSpecifiedName());
//		assertEquals("Customer", specifiedAttributeOverride.getColumn().getDefaultTable());	
		assertEquals(null, specifiedAttributeOverride.getColumn().getSpecifiedTable());	
		assertEquals(null, specifiedAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, specifiedAttributeOverride.getColumn().isInsertable());
		assertEquals(true, specifiedAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, specifiedAttributeOverride.getColumn().isUnique());
		assertEquals(true, specifiedAttributeOverride.getColumn().isNullable());
		assertEquals(255, specifiedAttributeOverride.getColumn().getLength());
		assertEquals(0, specifiedAttributeOverride.getColumn().getPrecision());
		assertEquals(0, specifiedAttributeOverride.getColumn().getScale());
	}
}
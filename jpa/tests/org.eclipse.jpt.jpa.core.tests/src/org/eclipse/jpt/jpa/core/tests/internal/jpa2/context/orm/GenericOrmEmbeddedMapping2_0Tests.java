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
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.context.VirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.VirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.jpa2.context.EmbeddedMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ReadOnlyOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.VirtualOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaEmbeddedMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmEmbeddedMapping2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericOrmEmbeddedMapping2_0Tests extends Generic2_0ContextModelTestCase
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.EMBEDDED, JPA.ATTRIBUTE_OVERRIDE, JPA.COLUMN);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.EMBEDDED, JPA.ID);
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

	private void createSelfReferentialEmbedded() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
					sb.append("import ");
					sb.append(JPA.EMBEDDABLE);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.EMBEDDED);
					sb.append(";");
					sb.append(CR).append(CR);
				sb.append("@Embeddable");
				sb.append(CR);
				sb.append("public class ").append("Foo").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Embedded").append(CR);
				sb.append("    private Foo embedded;").append(CR);
				sb.append(CR);
				sb.append("    private String name;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Foo.java", sourceWriter);
	}

	public void testUpdateName() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		OrmEmbeddedMapping ormEmbeddedMapping = (OrmEmbeddedMapping) ormPersistentAttribute.getMapping();
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		
		assertEquals("id", ormEmbeddedMapping.getName());
		assertEquals("id", embeddedResource.getName());
				
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		OrmEmbeddedMapping ormEmbeddedMapping = (OrmEmbeddedMapping) ormPersistentAttribute.getMapping();
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		
		assertEquals("id", ormEmbeddedMapping.getName());
		assertEquals("id", embeddedResource.getName());
				
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
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
		
		attributeOverrideContainer.moveSpecifiedOverride(2, 0);
		ListIterator<OrmAttributeOverride> attributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAR", embeddedResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAZ", embeddedResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", embeddedResource.getAttributeOverrides().get(2).getName());


		attributeOverrideContainer.moveSpecifiedOverride(0, 1);
		attributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAZ", embeddedResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAR", embeddedResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", embeddedResource.getAttributeOverrides().get(2).getName());
	}
	
	public void testUpdateAttributeOverrides() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		OrmEmbeddedMapping ormEmbeddedMapping = (OrmEmbeddedMapping) ormPersistentAttribute.getMapping();
		OrmAttributeOverrideContainer attributeOverrideContainer = ormEmbeddedMapping.getAttributeOverrideContainer();
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		
		embeddedResource.getAttributeOverrides().get(0).setName("FOO");
		embeddedResource.getAttributeOverrides().get(1).setName("BAR");
		embeddedResource.getAttributeOverrides().get(2).setName("BAZ");

		ListIterator<OrmAttributeOverride> attributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		embeddedResource.getAttributeOverrides().move(2, 0);
		attributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		embeddedResource.getAttributeOverrides().move(0, 1);
		attributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		embeddedResource.getAttributeOverrides().remove(1);
		attributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		embeddedResource.getAttributeOverrides().remove(1);
		attributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		embeddedResource.getAttributeOverrides().remove(0);
		assertFalse(attributeOverrideContainer.getSpecifiedOverrides().iterator().hasNext());
	}
	
	
	public void testEmbeddedMappingNoUnderylingJavaAttribute() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		createTestEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("address"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());
		
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		OrmEmbeddedMapping ormEmbeddedMapping = (OrmEmbeddedMapping) ormPersistentAttribute.getMapping();
		ormEmbeddedMapping.setName("foo");
		OrmAttributeOverrideContainer attributeOverrideContainer = ormEmbeddedMapping.getAttributeOverrideContainer();
		
		assertEquals("foo", ormEmbeddedMapping.getName());

		
		assertFalse(attributeOverrideContainer.getSpecifiedOverrides().iterator().hasNext());
		assertFalse(attributeOverrideContainer.getVirtualOverrides().iterator().hasNext());
	}
	
	public void testVirtualAttributeOverrides() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		createTestEmbeddableAddress();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType persistentType2 = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		
		//embedded mapping is virtual, specified attribute overrides should exist
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = persistentType.getAttributeNamed("address");
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		AttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		assertEquals(4, attributeOverrideContainer.getOverridesSize());
		assertEquals(3, attributeOverrideContainer.getVirtualOverridesSize());
		assertEquals(1, attributeOverrideContainer.getSpecifiedOverridesSize());
		ListIterator<? extends ReadOnlyAttributeOverride> specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		ReadOnlyAttributeOverride attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("city", attributeOverride.getName());
		ListIterator<? extends ReadOnlyAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("state", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("zip", attributeOverride.getName());
		
		JavaEmbeddedMapping javaEmbeddedMapping = (JavaEmbeddedMapping) ormPersistentAttribute.getJavaPersistentAttribute().getMapping();
		Column javaAttributeOverrideColumn = javaEmbeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next().getColumn();
		
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

		JavaBasicMapping javaBasicMapping = (JavaBasicMapping) persistentType2.getJavaPersistentType().getAttributeNamed("state").getMapping();
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
		assertEquals("id", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("state", attributeOverride.getName());
		assertEquals("MY_STATE_COLUMN", attributeOverride.getColumn().getSpecifiedName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("zip", attributeOverride.getName());
		
		
		
		//embedded mapping is specified, virtual attribute overrides should exist
		persistentType.getAttributeNamed("address").addToXml();
		embeddedMapping = (OrmEmbeddedMapping) persistentType.getAttributeNamed("address").getMapping();
		attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		assertEquals(4, attributeOverrideContainer.getOverridesSize());
		assertEquals(4, attributeOverrideContainer.getVirtualOverridesSize());
		assertEquals(0, attributeOverrideContainer.getSpecifiedOverridesSize());
		virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		ReadOnlyAttributeOverride virtualOverride = virtualAttributeOverrides.next();
		assertEquals("id", virtualOverride.getName());
		virtualOverride = virtualAttributeOverrides.next();
		assertEquals("city", virtualOverride.getName());
		assertEquals("city", virtualOverride.getColumn().getName());
		assertEquals(TYPE_NAME, virtualOverride.getColumn().getTableName());
		assertEquals(null, virtualOverride.getColumn().getColumnDefinition());
		assertEquals(true, virtualOverride.getColumn().isInsertable());
		assertEquals(true, virtualOverride.getColumn().isUpdatable());
		assertEquals(false, virtualOverride.getColumn().isUnique());
		assertEquals(true, virtualOverride.getColumn().isNullable());
		assertEquals(255, virtualOverride.getColumn().getLength());
		assertEquals(0, virtualOverride.getColumn().getPrecision());
		assertEquals(0, virtualOverride.getColumn().getScale());
		virtualOverride = virtualAttributeOverrides.next();
		assertEquals("state", virtualOverride.getName());
		assertEquals("MY_STATE_COLUMN", virtualOverride.getColumn().getDefaultName());
		assertEquals(TYPE_NAME, virtualOverride.getColumn().getDefaultTableName());
		virtualOverride = virtualAttributeOverrides.next();
		assertEquals("zip", virtualOverride.getName());
		
		//set one of the virtual attribute overrides to specified, verify others are still virtual
		attributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		assertEquals(4, attributeOverrideContainer.getOverridesSize());
		assertEquals(1, attributeOverrideContainer.getSpecifiedOverridesSize());
		assertEquals(3, attributeOverrideContainer.getVirtualOverridesSize());
		assertEquals("id", attributeOverrideContainer.getSpecifiedOverrides().iterator().next().getName());
		virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		virtualOverride = virtualAttributeOverrides.next();
		assertEquals("city", virtualOverride.getName());
		virtualOverride = virtualAttributeOverrides.next();
		assertEquals("state", virtualOverride.getName());
		virtualOverride = virtualAttributeOverrides.next();
		assertEquals("zip", virtualOverride.getName());
	}

	
	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		createTestEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		
		EmbeddedMapping virtualEmbeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();	
		AttributeOverrideContainer attributeOverrideContainer = virtualEmbeddedMapping.getAttributeOverrideContainer();
		assertEquals("address", virtualEmbeddedMapping.getName());

		assertEquals(1, attributeOverrideContainer.getSpecifiedOverridesSize());
		assertEquals(3, attributeOverrideContainer.getVirtualOverridesSize());
		ListIterator<? extends ReadOnlyAttributeOverride> specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();

		ReadOnlyAttributeOverride override = specifiedAttributeOverrides.next();
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, override.getName());
		ReadOnlyColumn column = override.getColumn();
		assertEquals(ATTRIBUTE_OVERRIDE_COLUMN_NAME, column.getSpecifiedName());

		ListIterator<? extends ReadOnlyAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		override = virtualAttributeOverrides.next();
		assertEquals("id", override.getName());
		column = override.getColumn();
		assertEquals("id", column.getName());

		override = virtualAttributeOverrides.next();
		assertEquals("state", override.getName());
		column = override.getColumn();
		assertEquals("A_STATE", column.getName());

		override = virtualAttributeOverrides.next();
		assertEquals("zip", override.getName());
		column = override.getColumn();
		assertEquals("zip", column.getName());

	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		createTestEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		
		EmbeddedMapping virtualEmbeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();	
		assertEquals("address", virtualEmbeddedMapping.getName());

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

		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("address"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());
		
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		OrmEmbeddedMapping ormEmbeddedMapping = (OrmEmbeddedMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("address", ormEmbeddedMapping.getName());

		assertEquals(0, ormEmbeddedMapping.getAttributeOverrideContainer().getSpecifiedOverridesSize());
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
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedMorphToVersionMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedMorphToTransientMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedMorphToBasicMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
		attributeOverride = ((EmbeddedIdMapping) ormPersistentAttribute.getMapping()).getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
		assertEquals("override", attributeOverride.getName());
		assertEquals("OVERRIDE_COLUMN", attributeOverride.getColumn().getSpecifiedName());
	}
	
	public void testEmbeddedMorphToOneToOneMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToOneMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedMorphToOneToManyMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedMorphToManyToOneMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedMorphToManyToManyMapping() throws Exception {
		createTestEntityEmbeddedMappingAttributeOverrides();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	
	public void testSpecifiedAssociationOverrides() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("myEmbedded"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();

		OrmAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();
		ListIterator<OrmAssociationOverride> specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		
		assertFalse(specifiedAssociationOverrides.hasNext());

		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		
		//add an annotation to the resource model and verify the context model is updated
		XmlAssociationOverride associationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		embeddedResource.getAssociationOverrides().add(associationOverride);
		associationOverride.setName("FOO");
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		associationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		embeddedResource.getAssociationOverrides().add(1, associationOverride);
		associationOverride.setName("BAR");
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());


		associationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		embeddedResource.getAssociationOverrides().add(0, associationOverride);
		associationOverride.setName("BAZ");
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		embeddedResource.getAssociationOverrides().move(1, 0);
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		embeddedResource.getAssociationOverrides().remove(0);
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		embeddedResource.getAssociationOverrides().remove(0);
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		
		embeddedResource.getAssociationOverrides().remove(0);
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertFalse(specifiedAssociationOverrides.hasNext());
	}

	public void testVirtualAssociationOverrideDefaults() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("myEmbedded"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();
		
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		VirtualAssociationOverride virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		VirtualJoinColumnRelationshipStrategy joiningStrategy = ((VirtualJoinColumnRelationship) virtualAssociationOverride.getRelationship()).getJoinColumnStrategy();
		assertEquals("address", virtualAssociationOverride.getName());
		assertEquals(1, joiningStrategy.getJoinColumnsSize());
		VirtualJoinColumn virtualJoinColumn = joiningStrategy.getJoinColumns().iterator().next();
		assertEquals("address_id", virtualJoinColumn.getName());
		assertEquals("id", virtualJoinColumn.getReferencedColumnName());
		assertEquals(TYPE_NAME, virtualJoinColumn.getTableName());
		assertEquals(null, virtualJoinColumn.getColumnDefinition());
		assertEquals(true, virtualJoinColumn.isInsertable());
		assertEquals(true, virtualJoinColumn.isUpdatable());
		assertEquals(false, virtualJoinColumn.isUnique());
		assertEquals(true, virtualJoinColumn.isNullable());
		

		JavaPersistentType javaEmbeddable = getPersistenceUnit().getSpecifiedClassRefs().iterator().next().getJavaPersistentType(); 
		OneToOneMapping oneToOneMapping = (OneToOneMapping) javaEmbeddable.getAttributeNamed("address").getMapping();
		JoinColumn joinColumn = oneToOneMapping.getRelationship().getJoinColumnStrategy().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("MY_JOIN_COLUMN");
		joinColumn.setSpecifiedReferencedColumnName("MY_REFERENCE_COLUMN");
		joinColumn.setSpecifiedTableName("BAR");
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setSpecifiedInsertable(Boolean.FALSE);
		joinColumn.setSpecifiedUpdatable(Boolean.FALSE);
		joinColumn.setSpecifiedUnique(Boolean.TRUE);
		joinColumn.setSpecifiedNullable(Boolean.FALSE);

		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		joiningStrategy = ((VirtualJoinColumnRelationship) virtualAssociationOverride.getRelationship()).getJoinColumnStrategy();
		assertEquals("address", virtualAssociationOverride.getName());
		assertEquals(1, joiningStrategy.getJoinColumnsSize());
		virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		virtualJoinColumn = joiningStrategy.getJoinColumns().iterator().next();
		assertEquals("MY_JOIN_COLUMN", virtualJoinColumn.getName());
		assertEquals("MY_REFERENCE_COLUMN", virtualJoinColumn.getReferencedColumnName());
		assertEquals("BAR", virtualJoinColumn.getTableName());
		assertEquals("COLUMN_DEF", virtualJoinColumn.getColumnDefinition());
		assertEquals(false, virtualJoinColumn.isInsertable());
		assertEquals(false, virtualJoinColumn.isUpdatable());
		assertEquals(true, virtualJoinColumn.isUnique());
		assertEquals(false, virtualJoinColumn.isNullable());

		assertEquals("MY_JOIN_COLUMN", joiningStrategy.getJoinColumns().iterator().next().getName());


		virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("address", virtualAssociationOverride.getName());
		
		virtualAssociationOverride.convertToSpecified();
		assertEquals(1, overrideContainer.getVirtualOverridesSize());
	}
	
	public void testSpecifiedAssociationOverridesSize() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("myEmbedded"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		
		assertEquals(0, overrideContainer.getSpecifiedOverridesSize());

		//add an annotation to the resource model and verify the context model is updated
		XmlAssociationOverride associationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		embeddedResource.getAssociationOverrides().add(associationOverride);
		associationOverride.setName("FOO");
		associationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		embeddedResource.getAssociationOverrides().add(0, associationOverride);
		associationOverride.setName("BAR");

		assertEquals(2, overrideContainer.getSpecifiedOverridesSize());
	}
	
	public void testVirtualAssociationOverridesSize() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("myEmbedded"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();
		
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(1, overrideContainer.getVirtualOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(0, overrideContainer.getVirtualOverridesSize());
	}
	
	public void testAssociationOverridesSize() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("myEmbedded"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		assertEquals(2, overrideContainer.getOverridesSize());

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(2, overrideContainer.getOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(2, overrideContainer.getOverridesSize());
		
		
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);

		XmlAssociationOverride associationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		embeddedResource.getAssociationOverrides().add(associationOverride);
		associationOverride.setName("bar");	
		assertEquals(3, overrideContainer.getOverridesSize());
	}

	public void testAssociationOverrideSetVirtual() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("myEmbedded"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
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
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("myEmbedded"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		OrmAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		ListIterator<OrmVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.getVirtualOverrides().iterator();
		virtualAssociationOverrides.next();
		virtualAssociationOverrides.next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
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
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("myEmbedded"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		OrmAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		assertEquals(2, embeddedResource.getAssociationOverrides().size());

		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		
		assertEquals("addresses", embeddedResource.getAssociationOverrides().get(0).getName());
		assertEquals(1, embeddedResource.getAssociationOverrides().size());

		Iterator<OrmAssociationOverride> associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("addresses", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		
		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		assertEquals(0, embeddedResource.getAssociationOverrides().size());
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
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
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("myEmbedded"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();

		
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		assertEquals(2, embeddedResource.getAssociationOverrides().size());
		
		
		overrideContainer.moveSpecifiedOverride(1, 0);
		ListIterator<? extends AssociationOverride> associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("addresses", associationOverrides.next().getName());
		assertEquals("address", associationOverrides.next().getName());

		assertEquals("addresses", embeddedResource.getAssociationOverrides().get(0).getName());
		assertEquals("address", embeddedResource.getAssociationOverrides().get(1).getName());


		overrideContainer.moveSpecifiedOverride(0, 1);
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
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
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("myEmbedded"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
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
			
		ListIterator<? extends AssociationOverride> associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("FOO", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		embeddedResource.getAssociationOverrides().move(2, 0);
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		embeddedResource.getAssociationOverrides().move(0, 1);
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		embeddedResource.getAssociationOverrides().remove(1);
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		embeddedResource.getAssociationOverrides().remove(1);
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		embeddedResource.getAssociationOverrides().remove(0);
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertFalse(associationOverrides.hasNext());
	}

	public void testAssociationOverrideIsVirtual() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("myEmbedded"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		OrmAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		ListIterator<OrmVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.getVirtualOverrides().iterator();
		VirtualAssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());

		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("addresses", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		assertFalse(virtualAssociationOverrides.hasNext());

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		AssociationOverride specifiedAssociationOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		assertFalse(specifiedAssociationOverride.isVirtual());
		
		
		virtualAssociationOverrides = overrideContainer.getVirtualOverrides().iterator();
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
		addXmlClassRef(PACKAGE_NAME_ + "Address");
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("myEmbedded"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		OrmEmbeddedMapping2_0 embeddedMapping = (OrmEmbeddedMapping2_0) ormPersistentAttribute.getMapping();
		AssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		VirtualAssociationOverride virtualAssociationOverride = IterableTools.get(overrideContainer.getVirtualOverrides(), 1);
		VirtualJoinTableRelationshipStrategy joiningStrategy = ((VirtualOverrideRelationship2_0) virtualAssociationOverride.getRelationship()).getJoinTableStrategy();
		ReadOnlyJoinTable joinTable = joiningStrategy.getJoinTable();
		assertEquals("addresses", virtualAssociationOverride.getName());
		assertEquals("AnnotationTestType_Address", joinTable.getName());
		assertEquals(1, joinTable.getJoinColumnsSize());
		ReadOnlyJoinColumn virtualJoinColumn = joinTable.getJoinColumns().iterator().next();
		assertEquals("AnnotationTestType_id", virtualJoinColumn.getName());
		assertEquals("id", virtualJoinColumn.getReferencedColumnName());
		assertEquals("AnnotationTestType_Address", virtualJoinColumn.getTableName());
		assertEquals(null, virtualJoinColumn.getColumnDefinition());
		assertEquals(true, virtualJoinColumn.isInsertable());
		assertEquals(true, virtualJoinColumn.isUpdatable());
		assertEquals(false, virtualJoinColumn.isUnique());
		assertEquals(true, virtualJoinColumn.isNullable());
		
		assertEquals(1, joinTable.getInverseJoinColumnsSize());
		ReadOnlyJoinColumn virtualInverseJoinColumn = joinTable.getInverseJoinColumns().iterator().next();
		assertEquals("addresses_id", virtualInverseJoinColumn.getName());
		assertEquals("id", virtualInverseJoinColumn.getReferencedColumnName());
		assertEquals("AnnotationTestType_Address", virtualInverseJoinColumn.getTableName());
		assertEquals(null, virtualInverseJoinColumn.getColumnDefinition());
		assertEquals(true, virtualInverseJoinColumn.isInsertable());
		assertEquals(true, virtualInverseJoinColumn.isUpdatable());
		assertEquals(false, virtualInverseJoinColumn.isUnique());
		assertEquals(true, virtualInverseJoinColumn.isNullable());
		
		JavaPersistentType javaEmbeddable = getPersistenceUnit().getSpecifiedClassRefs().iterator().next().getJavaPersistentType(); 
		OneToManyMapping oneToManyMapping = (OneToManyMapping) javaEmbeddable.getAttributeNamed("addresses").getMapping();
		JoinTableRelationshipStrategy joinTableStrategy = oneToManyMapping.getRelationship().getJoinTableStrategy();
		joinTableStrategy.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = joinTableStrategy.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("MY_JOIN_COLUMN");
		joinColumn.setSpecifiedReferencedColumnName("MY_REFERENCE_COLUMN");
		joinColumn.setSpecifiedTableName("BAR");
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setSpecifiedInsertable(Boolean.FALSE);
		joinColumn.setSpecifiedUpdatable(Boolean.FALSE);
		joinColumn.setSpecifiedUnique(Boolean.TRUE);
		joinColumn.setSpecifiedNullable(Boolean.FALSE);
		
		JoinColumn inverseJoinColumn = joinTableStrategy.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("MY_INVERSE_JOIN_COLUMN");
		inverseJoinColumn.setSpecifiedReferencedColumnName("MY_INVERSE_REFERENCE_COLUMN");
		inverseJoinColumn.setSpecifiedTableName("INVERSE_BAR");
		inverseJoinColumn.setColumnDefinition("INVERSE_COLUMN_DEF");
		inverseJoinColumn.setSpecifiedInsertable(Boolean.FALSE);
		inverseJoinColumn.setSpecifiedUpdatable(Boolean.FALSE);
		inverseJoinColumn.setSpecifiedUnique(Boolean.TRUE);
		inverseJoinColumn.setSpecifiedNullable(Boolean.FALSE);
		
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		virtualAssociationOverride = IterableTools.get(overrideContainer.getVirtualOverrides(), 1);
		joiningStrategy = ((VirtualOverrideRelationship2_0) virtualAssociationOverride.getRelationship()).getJoinTableStrategy();
		joinTable = joiningStrategy.getJoinTable();
		assertEquals("addresses", virtualAssociationOverride.getName());
		assertEquals(1, joinTable.getJoinColumnsSize());
		virtualJoinColumn = joinTable.getJoinColumns().iterator().next();
		assertEquals("MY_JOIN_COLUMN", virtualJoinColumn.getName());
		assertEquals("MY_REFERENCE_COLUMN", virtualJoinColumn.getReferencedColumnName());
		assertEquals("BAR", virtualJoinColumn.getTableName());
		assertEquals("COLUMN_DEF", virtualJoinColumn.getColumnDefinition());
		assertEquals(false, virtualJoinColumn.isInsertable());
		assertEquals(false, virtualJoinColumn.isUpdatable());
		assertEquals(true, virtualJoinColumn.isUnique());
		assertEquals(false, virtualJoinColumn.isNullable());

		assertEquals(1, joinTable.getInverseJoinColumnsSize());
		virtualInverseJoinColumn = joinTable.getInverseJoinColumns().iterator().next();
		assertEquals("MY_INVERSE_JOIN_COLUMN", virtualInverseJoinColumn.getName());
		assertEquals("MY_INVERSE_REFERENCE_COLUMN", virtualInverseJoinColumn.getReferencedColumnName());
		assertEquals("INVERSE_BAR", virtualInverseJoinColumn.getTableName());
		assertEquals("INVERSE_COLUMN_DEF", virtualInverseJoinColumn.getColumnDefinition());
		assertEquals(false, virtualInverseJoinColumn.isInsertable());
		assertEquals(false, virtualInverseJoinColumn.isUpdatable());
		assertEquals(true, virtualInverseJoinColumn.isUnique());
		assertEquals(false, virtualInverseJoinColumn.isNullable());

		
		//add the java association override and make sure the settings are not used in determining the association override defaults in the specified orm embedded mapping
		JavaEmbeddedMapping2_0 javaEmbeddedMapping = (JavaEmbeddedMapping2_0) ormPersistentType.getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		ListIterator<? extends ReadOnlyAssociationOverride> javaAssociationOverrides = javaEmbeddedMapping.getAssociationOverrideContainer().getOverrides().iterator();
		javaAssociationOverrides.next();
		JavaAssociationOverride javaAssociationOverride = ((JavaVirtualAssociationOverride) javaAssociationOverrides.next()).convertToSpecified();
		assertEquals("addresses", javaAssociationOverride.getName());
		JavaJoinTable javaJoinTable = ((JavaOverrideRelationship2_0) javaAssociationOverride.getRelationship()).getJoinTableStrategy().getJoinTable();
		javaJoinTable.setSpecifiedName("JAVA_FOO");
		JavaJoinColumn javaJoinColumn = javaJoinTable.addSpecifiedJoinColumn(0);
		javaJoinColumn.setSpecifiedName("JAVA_JOIN_COLUMN_NAME");
		javaJoinColumn.setSpecifiedReferencedColumnName("JAVA_JOIN_COLUMN_REFERENCED_NAME");
		JavaJoinColumn javaInverseJoinColumn = javaJoinTable.addSpecifiedInverseJoinColumn(0);
		javaInverseJoinColumn.setSpecifiedName("JAVA_INVERSE_JOIN_COLUMN_NAME");
		javaInverseJoinColumn.setSpecifiedReferencedColumnName("JAVA_INVERSE_JOIN_COLUMN_REFERENCED_NAME");
		

		
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		virtualAssociationOverride = IterableTools.get(overrideContainer.getVirtualOverrides(), 1);
		joiningStrategy = ((VirtualOverrideRelationship2_0) virtualAssociationOverride.getRelationship()).getJoinTableStrategy();
		joinTable = joiningStrategy.getJoinTable();
		assertEquals("addresses", virtualAssociationOverride.getName());
		assertEquals("MY_JOIN_TABLE", joinTable.getName());
		assertEquals(1, joinTable.getJoinColumnsSize());
		virtualJoinColumn = joinTable.getJoinColumns().iterator().next();
		
		assertEquals("MY_JOIN_COLUMN", virtualJoinColumn.getName());
		assertEquals("MY_REFERENCE_COLUMN", virtualJoinColumn.getReferencedColumnName());
		assertEquals("BAR", virtualJoinColumn.getTableName());
		assertEquals("COLUMN_DEF", virtualJoinColumn.getColumnDefinition());
		assertEquals(false, virtualJoinColumn.isInsertable());
		assertEquals(false, virtualJoinColumn.isUpdatable());
		assertEquals(true, virtualJoinColumn.isUnique());
		assertEquals(false, virtualJoinColumn.isNullable());

		assertEquals(1, joinTable.getInverseJoinColumnsSize());
		virtualInverseJoinColumn = joinTable.getInverseJoinColumns().iterator().next();
		assertEquals("MY_INVERSE_JOIN_COLUMN", virtualInverseJoinColumn.getName());
		assertEquals("MY_INVERSE_REFERENCE_COLUMN", virtualInverseJoinColumn.getReferencedColumnName());
		assertEquals("INVERSE_BAR", virtualInverseJoinColumn.getTableName());
		assertEquals("INVERSE_COLUMN_DEF", virtualInverseJoinColumn.getColumnDefinition());
		assertEquals(false, virtualInverseJoinColumn.isInsertable());
		assertEquals(false, virtualInverseJoinColumn.isUpdatable());
		assertEquals(true, virtualInverseJoinColumn.isUnique());
		assertEquals(false, virtualInverseJoinColumn.isNullable());
		
		
		virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("address", virtualAssociationOverride.getName());
		
		virtualAssociationOverride.convertToSpecified();
		assertEquals(1, overrideContainer.getVirtualOverridesSize());
		
		
		ormPersistentAttribute.removeFromXml();
		EmbeddedMapping2_0 virtualEmbeddedMapping = (EmbeddedMapping2_0) ormPersistentType.getAttributeNamed("myEmbedded").getMapping();
		overrideContainer = virtualEmbeddedMapping.getAssociationOverrideContainer();
		AssociationOverride associationOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		assertEquals("addresses", associationOverride.getName());
		ReadOnlyJoinTableRelationshipStrategy strategy = ((ReadOnlyOverrideRelationship2_0) associationOverride.getRelationship()).getJoinTableStrategy();
		joinTable = strategy.getJoinTable();
		assertEquals("JAVA_FOO", joinTable.getName());
		assertEquals(2, joinTable.getJoinColumnsSize());
		ListIterator<? extends ReadOnlyJoinColumn> joinColumns = joinTable.getJoinColumns().iterator();
		virtualJoinColumn = joinColumns.next();
		assertEquals("JAVA_JOIN_COLUMN_NAME", virtualJoinColumn.getName());
		assertEquals("JAVA_JOIN_COLUMN_REFERENCED_NAME", virtualJoinColumn.getReferencedColumnName());
		assertEquals("JAVA_FOO", virtualJoinColumn.getTableName());
		virtualJoinColumn = joinColumns.next();
		assertEquals("MY_JOIN_COLUMN", virtualJoinColumn.getName());
		assertEquals("MY_REFERENCE_COLUMN", virtualJoinColumn.getReferencedColumnName());
		assertEquals("BAR", virtualJoinColumn.getTableName());

		assertEquals(2, joinTable.getInverseJoinColumnsSize());
		ListIterator<? extends ReadOnlyJoinColumn> inverseJoinColumns = joinTable.getInverseJoinColumns().iterator();
		virtualInverseJoinColumn = inverseJoinColumns.next();
		assertEquals("JAVA_INVERSE_JOIN_COLUMN_NAME", virtualInverseJoinColumn.getName());
		assertEquals("JAVA_INVERSE_JOIN_COLUMN_REFERENCED_NAME", virtualInverseJoinColumn.getReferencedColumnName());
		assertEquals("JAVA_FOO", virtualInverseJoinColumn.getTableName());
		virtualInverseJoinColumn = inverseJoinColumns.next();
		assertEquals("MY_INVERSE_JOIN_COLUMN", virtualInverseJoinColumn.getName());
		assertEquals("MY_INVERSE_REFERENCE_COLUMN", virtualInverseJoinColumn.getReferencedColumnName());
		assertEquals("INVERSE_BAR", virtualInverseJoinColumn.getTableName());
	}

	public void testNestedVirtualAttributeOverrides() throws Exception {
		createTestEntityCustomer();
		createTestEmbeddableAddress2();
		createTestEmbeddableZipCode();
		
		OrmPersistentType customerPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Customer");
		OrmPersistentType addressPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		OrmPersistentType zipCodePersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".ZipCode");

		customerPersistentType.getAttributeNamed("address").addToXml();
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) customerPersistentType.getAttributeNamed("address").getMapping();
		AttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		
		assertEquals(5, attributeOverrideContainer.getVirtualOverridesSize());
		ListIterator<? extends VirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		VirtualAttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("street", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("city", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("state", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("zipCode.zip", virtualAttributeOverride.getName());
		assertEquals("zip", virtualAttributeOverride.getColumn().getName());
		assertEquals("Customer", virtualAttributeOverride.getColumn().getTableName());		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("zipCode.plusfour", virtualAttributeOverride.getName());
		assertEquals("plusfour", virtualAttributeOverride.getColumn().getName());
		assertEquals("Customer", virtualAttributeOverride.getColumn().getTableName());	
		assertEquals(null, virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(true, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(255, virtualAttributeOverride.getColumn().getLength());
		assertEquals(0, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(0, virtualAttributeOverride.getColumn().getScale());

		
		addressPersistentType.getAttributeNamed("zipCode").addToXml();
		EmbeddedMapping nestedEmbeddedMapping = (EmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping();
		AttributeOverrideContainer nestedAttributeOverrideContainer = nestedEmbeddedMapping.getAttributeOverrideContainer();
		assertEquals(2, nestedAttributeOverrideContainer.getVirtualOverridesSize());
		virtualAttributeOverrides = nestedAttributeOverrideContainer.getVirtualOverrides().iterator();
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("zip", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("plusfour", virtualAttributeOverride.getName());
		
		zipCodePersistentType.getAttributeNamed("plusfour").addToXml();
		BasicMapping plusFourMapping = (BasicMapping) zipCodePersistentType.getAttributeNamed("plusfour").getMapping();
		plusFourMapping.getColumn().setSpecifiedName("BLAH");
		plusFourMapping.getColumn().setSpecifiedTableName("BLAH_TABLE");
		plusFourMapping.getColumn().setColumnDefinition("COLUMN_DEFINITION");
		plusFourMapping.getColumn().setSpecifiedInsertable(Boolean.FALSE);
		plusFourMapping.getColumn().setSpecifiedUpdatable(Boolean.FALSE);
		plusFourMapping.getColumn().setSpecifiedUnique(Boolean.TRUE);
		plusFourMapping.getColumn().setSpecifiedNullable(Boolean.FALSE);
		plusFourMapping.getColumn().setSpecifiedLength(Integer.valueOf(5));
		plusFourMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(6));
		plusFourMapping.getColumn().setSpecifiedScale(Integer.valueOf(7));

		//check the nested embedded (Address.zipCode) attribute override to verify it is getting settings from the specified column on Zipcode.plusfour
		virtualAttributeOverride = (VirtualAttributeOverride) ((EmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping()).getAttributeOverrideContainer().getOverrideNamed("plusfour");
		assertEquals("plusfour", virtualAttributeOverride.getName());
		assertEquals("BLAH", virtualAttributeOverride.getColumn().getName());
		assertEquals("BLAH_TABLE", virtualAttributeOverride.getColumn().getTableName());	
		assertEquals("COLUMN_DEFINITION", virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(false, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(false, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(5, virtualAttributeOverride.getColumn().getLength());
		assertEquals(6, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(7, virtualAttributeOverride.getColumn().getScale());

		//check the top-level embedded (Customer.address) attribute override to verify it is getting settings from the specified column on Zipcode.plusfour
		virtualAttributeOverride = (VirtualAttributeOverride) ((EmbeddedMapping) customerPersistentType.getAttributeNamed("address").getMapping()).getAttributeOverrideContainer().getOverrideNamed("zipCode.plusfour");
		assertEquals("zipCode.plusfour", virtualAttributeOverride.getName());
		assertEquals("BLAH", virtualAttributeOverride.getColumn().getName());
		assertEquals("BLAH_TABLE", virtualAttributeOverride.getColumn().getTableName());	
		assertEquals("COLUMN_DEFINITION", virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(false, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(false, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(5, virtualAttributeOverride.getColumn().getLength());
		assertEquals(6, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(7, virtualAttributeOverride.getColumn().getScale());
		
		//set an attribute override on Address.zipCode embedded mapping
		AttributeOverride specifiedAttributeOverride = ((VirtualAttributeOverride) ((EmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping()).getAttributeOverrideContainer().getOverrideNamed("plusfour")).convertToSpecified();
		specifiedAttributeOverride.getColumn().setSpecifiedName("BLAH_OVERRIDE");
		specifiedAttributeOverride.getColumn().setSpecifiedTableName("BLAH_TABLE_OVERRIDE");
		specifiedAttributeOverride.getColumn().setColumnDefinition("COLUMN_DEFINITION_OVERRIDE");
	
		virtualAttributeOverride = (VirtualAttributeOverride) ((EmbeddedMapping) customerPersistentType.getAttributeNamed("address").getMapping()).getAttributeOverrideContainer().getOverrideNamed("zipCode.plusfour");
		assertEquals("zipCode.plusfour", virtualAttributeOverride.getName());
		assertEquals("BLAH_OVERRIDE", virtualAttributeOverride.getColumn().getName());
		assertEquals("BLAH_TABLE_OVERRIDE", virtualAttributeOverride.getColumn().getTableName());	
		assertEquals("COLUMN_DEFINITION_OVERRIDE", virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(true, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(255, virtualAttributeOverride.getColumn().getLength());
		assertEquals(0, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(0, virtualAttributeOverride.getColumn().getScale());
		
		specifiedAttributeOverride = virtualAttributeOverride.convertToSpecified();
		assertEquals(false, specifiedAttributeOverride.isVirtual());
		assertEquals("zipCode.plusfour", specifiedAttributeOverride.getName());
		//TODO I have the default wrong in this case, but this was wrong before as well.  Need to fix this later
//		assertEquals("plusfour", specifiedAttributeOverride.getColumn().getDefaultName());
		assertEquals("BLAH_OVERRIDE", specifiedAttributeOverride.getColumn().getSpecifiedName());
//		assertEquals("Customer", specifiedAttributeOverride.getColumn().getDefaultTable());	
		assertEquals("BLAH_TABLE_OVERRIDE", specifiedAttributeOverride.getColumn().getSpecifiedTableName());	
		assertEquals("COLUMN_DEFINITION_OVERRIDE", specifiedAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, specifiedAttributeOverride.getColumn().isInsertable());
		assertEquals(true, specifiedAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, specifiedAttributeOverride.getColumn().isUnique());
		assertEquals(true, specifiedAttributeOverride.getColumn().isNullable());
		assertEquals(255, specifiedAttributeOverride.getColumn().getLength());
		assertEquals(0, specifiedAttributeOverride.getColumn().getPrecision());
		assertEquals(0, specifiedAttributeOverride.getColumn().getScale());
	}

	public void testSelfReferentialEmbeddedMapping() throws Exception {
		createSelfReferentialEmbedded();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Foo");

		EmbeddedMapping2_0 embeddedMapping = (EmbeddedMapping2_0) persistentType.getAttributeNamed("embedded").getMapping();
		assertFalse(embeddedMapping.getAllOverridableAttributeMappingNames().iterator().hasNext());
	}
}
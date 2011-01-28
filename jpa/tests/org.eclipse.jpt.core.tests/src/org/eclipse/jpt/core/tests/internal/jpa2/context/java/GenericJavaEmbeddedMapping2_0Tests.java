/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTableRelationshipStrategy;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.VirtualAssociationOverride;
import org.eclipse.jpt.core.context.VirtualAttributeOverride;
import org.eclipse.jpt.core.context.VirtualJoinColumn;
import org.eclipse.jpt.core.context.VirtualJoinColumnRelationship;
import org.eclipse.jpt.core.context.VirtualJoinColumnRelationshipStrategy;
import org.eclipse.jpt.core.context.VirtualJoinTable;
import org.eclipse.jpt.core.context.VirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaNullAttributeMapping;
import org.eclipse.jpt.core.jpa2.context.OverrideRelationship2_0;
import org.eclipse.jpt.core.jpa2.context.VirtualOverrideRelationship2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaEmbeddedMapping2_0;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AssociationOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.ManyToManyAnnotation;
import org.eclipse.jpt.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class GenericJavaEmbeddedMapping2_0Tests extends Generic2_0ContextModelTestCase
{

	public static final String EMBEDDABLE_TYPE_NAME = "MyEmbeddable";
	public static final String FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME = PACKAGE_NAME + "." + EMBEDDABLE_TYPE_NAME;

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

	private void createTestEmbeddableAddress() throws Exception {
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

	public GenericJavaEmbeddedMapping2_0Tests(String name) {
		super(name);
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof BasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(((JavaEmbeddedMapping) persistentAttribute.getMapping()).getAttributeOverrideContainer().overrides().hasNext());
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		assertNull(attributeResource.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testDefaultEmbeddedMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof GenericJavaNullAttributeMapping);
		assertTrue(persistentAttribute.getMapping().isDefault());
		
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		assertTrue(persistentAttribute.getMapping() instanceof JavaEmbeddedMapping);
		assertTrue(persistentAttribute.getMapping().isDefault());
	}
	
	public void testDefaultEmbeddedMappingGenericEmbeddable() throws Exception {
		createTestEntityWithDefaultEmbeddedMapping();
		createTestGenericEmbeddable();
		addXmlClassRef(PACKAGE_NAME + ".Entity1");
		addXmlClassRef(PACKAGE_NAME + ".Embeddable1");
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbeddable");
		assertNotNull(persistentAttribute.getMapping());
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
		assertTrue(persistentAttribute.getMapping().isDefault());
	}
	
	private void createTestEntityWithDefaultEmbeddedMapping() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class Entity1 { ").append(CR);
				sb.append("private Embeddable1<Integer> myEmbeddable;").append(CR);
				sb.append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Entity1.java", sourceWriter);
	}
	
	private void createTestGenericEmbeddable() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.EMBEDDABLE);
					sb.append(";");
					sb.append(CR);
				sb.append("@Embeddable");
				sb.append(CR);
				sb.append("public class Embeddable1<T> {}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Embeddable1.java", sourceWriter);
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
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Foo.java", sourceWriter);
	}

	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof VersionMapping);
	
		assertNull(attributeResource.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		
		assertNull(attributeResource.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(TransientAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IdMapping);
		
		assertNull(attributeResource.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		
		assertNull(attributeResource.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToOneMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToOneMapping);
		
		assertNull(attributeResource.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		
		assertNull(attributeResource.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		
		assertNull(attributeResource.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		
		assertNull(attributeResource.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
	}

	public void testSpecifiedAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		
		ListIterator<JavaAttributeOverride> specifiedAttributeOverrides = attributeOverrideContainer.specifiedOverrides();
		
		assertFalse(specifiedAttributeOverrides.hasNext());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		attributeOverride = (AttributeOverrideAnnotation) attributeResource.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());


		attributeOverride = (AttributeOverrideAnnotation) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.specifiedOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		attributeResource.moveAnnotation(1, 0, JPA.ATTRIBUTE_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		attributeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.specifiedOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		attributeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.specifiedOverrides();		
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		
		attributeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.specifiedOverrides();		
		assertFalse(specifiedAttributeOverrides.hasNext());
	}

	public void testVirtualAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertEquals("myEmbedded", attributeResource.getName());
		assertNull(attributeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));
		
		assertEquals(2, attributeOverrideContainer.virtualOverridesSize());
		ReadOnlyAttributeOverride defaultAttributeOverride = attributeOverrideContainer.virtualOverrides().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("city", defaultAttributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME, defaultAttributeOverride.getColumn().getTable());
		assertEquals(null, defaultAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, defaultAttributeOverride.getColumn().isInsertable());
		assertEquals(true, defaultAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, defaultAttributeOverride.getColumn().isUnique());
		assertEquals(true, defaultAttributeOverride.getColumn().isNullable());
		assertEquals(255, defaultAttributeOverride.getColumn().getLength());
		assertEquals(0, defaultAttributeOverride.getColumn().getPrecision());
		assertEquals(0, defaultAttributeOverride.getColumn().getScale());
		
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		Embeddable embeddable = (Embeddable) classRefs.next().getJavaPersistentType().getMapping();
		
		BasicMapping cityMapping = (BasicMapping) embeddable.getPersistentType().getAttributeNamed("city").getMapping();
		cityMapping.getColumn().setSpecifiedName("FOO");
		cityMapping.getColumn().setSpecifiedTable("BAR");
		cityMapping.getColumn().setColumnDefinition("COLUMN_DEF");
		cityMapping.getColumn().setSpecifiedInsertable(Boolean.FALSE);
		cityMapping.getColumn().setSpecifiedUpdatable(Boolean.FALSE);
		cityMapping.getColumn().setSpecifiedUnique(Boolean.TRUE);
		cityMapping.getColumn().setSpecifiedNullable(Boolean.FALSE);
		cityMapping.getColumn().setSpecifiedLength(Integer.valueOf(5));
		cityMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(6));
		cityMapping.getColumn().setSpecifiedScale(Integer.valueOf(7));
		
		assertEquals("myEmbedded", attributeResource.getName());
		assertNull(attributeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));

		assertEquals(2, attributeOverrideContainer.virtualOverridesSize());
		defaultAttributeOverride = attributeOverrideContainer.virtualOverrides().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("FOO", defaultAttributeOverride.getColumn().getName());
		assertEquals("BAR", defaultAttributeOverride.getColumn().getTable());
		assertEquals("COLUMN_DEF", defaultAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(false, defaultAttributeOverride.getColumn().isInsertable());
		assertEquals(false, defaultAttributeOverride.getColumn().isUpdatable());
		assertEquals(true, defaultAttributeOverride.getColumn().isUnique());
		assertEquals(false, defaultAttributeOverride.getColumn().isNullable());
		assertEquals(5, defaultAttributeOverride.getColumn().getLength());
		assertEquals(6, defaultAttributeOverride.getColumn().getPrecision());
		assertEquals(7, defaultAttributeOverride.getColumn().getScale());

		cityMapping.getColumn().setSpecifiedName(null);
		cityMapping.getColumn().setSpecifiedTable(null);
		cityMapping.getColumn().setColumnDefinition(null);
		cityMapping.getColumn().setSpecifiedInsertable(null);
		cityMapping.getColumn().setSpecifiedUpdatable(null);
		cityMapping.getColumn().setSpecifiedUnique(null);
		cityMapping.getColumn().setSpecifiedNullable(null);
		cityMapping.getColumn().setSpecifiedLength(null);
		cityMapping.getColumn().setSpecifiedPrecision(null);
		cityMapping.getColumn().setSpecifiedScale(null);
		defaultAttributeOverride = attributeOverrideContainer.virtualOverrides().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("city", defaultAttributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME, defaultAttributeOverride.getColumn().getTable());
		assertEquals(null, defaultAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, defaultAttributeOverride.getColumn().isInsertable());
		assertEquals(true, defaultAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, defaultAttributeOverride.getColumn().isUnique());
		assertEquals(true, defaultAttributeOverride.getColumn().isNullable());
		assertEquals(255, defaultAttributeOverride.getColumn().getLength());
		assertEquals(0, defaultAttributeOverride.getColumn().getPrecision());
		assertEquals(0, defaultAttributeOverride.getColumn().getScale());
		
		AttributeOverrideAnnotation annotation = (AttributeOverrideAnnotation) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		annotation.setName("city");
		getJpaProject().synchronizeContextModel();
		assertEquals(1, attributeOverrideContainer.virtualOverridesSize());
	}
	
	public void testSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		assertEquals(0, attributeOverrideContainer.specifiedOverridesSize());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(2, attributeOverrideContainer.specifiedOverridesSize());
	}
	
	public void testAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		assertEquals(2, attributeOverrideContainer.overridesSize());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(4, attributeOverrideContainer.overridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("city");
		getJpaProject().synchronizeContextModel();
		assertEquals(4, attributeOverrideContainer.overridesSize());	
	}
	
	public void testVirtualAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		assertEquals(2, attributeOverrideContainer.virtualOverridesSize());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		getJpaProject().synchronizeContextModel();

		assertEquals(2, attributeOverrideContainer.virtualOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("city");
		getJpaProject().synchronizeContextModel();
		assertEquals(1, attributeOverrideContainer.virtualOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) attributeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("state");
		getJpaProject().synchronizeContextModel();
		assertEquals(0, attributeOverrideContainer.virtualOverridesSize());
	}

	public void testAttributeOverrideSetVirtual() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
				
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		attributeOverrideContainer.virtualOverrides().next().convertToSpecified();
		attributeOverrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		Iterator<NestableAnnotation> attributeOverrides = attributeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("city", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("state", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		attributeOverrideContainer.specifiedOverrides().next().convertToVirtual();
		attributeOverrides = attributeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("state", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		assertEquals("city", attributeOverrideContainer.virtualOverrides().next().getName());
		assertEquals(1, attributeOverrideContainer.virtualOverridesSize());
		
		attributeOverrideContainer.specifiedOverrides().next().convertToVirtual();
		attributeOverrides = attributeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertFalse(attributeOverrides.hasNext());
		
		Iterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.virtualOverrides();
		assertEquals("city", virtualAttributeOverrides.next().getName());
		assertEquals("state", virtualAttributeOverrides.next().getName());
		assertEquals(2, attributeOverrideContainer.virtualOverridesSize());
	}
	
	public void testAttributeOverrideSetVirtual2() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		ListIterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.virtualOverrides();
		virtualAttributeOverrides.next();	
		virtualAttributeOverrides.next().convertToSpecified();
		attributeOverrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		Iterator<NestableAnnotation> attributeOverrides = attributeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("state", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("city", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		attributeOverrideContainer.virtualOverrides().next().convertToSpecified();
		attributeOverrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		attributeResource.moveAnnotation(1, 0, AttributeOverridesAnnotation.ANNOTATION_NAME);
		
		Iterator<NestableAnnotation> attributeOverrides = attributeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);

		assertEquals("state", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("city", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	
	public void testSpecifiedAssociationOverrides() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		JavaEmbeddedMapping2_0 embeddedMapping = (JavaEmbeddedMapping2_0) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();
		ListIterator<JavaAssociationOverride> specifiedAssociationOverrides = overrideContainer.specifiedOverrides();
		
		assertFalse(specifiedAssociationOverrides.hasNext());

		JavaResourcePersistentAttribute resourceAttribute = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
		
		//add an annotation to the resource model and verify the context model is updated
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) resourceAttribute.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		associationOverride = (AssociationOverrideAnnotation) resourceAttribute.addAnnotation(1, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());


		associationOverride = (AssociationOverrideAnnotation) resourceAttribute.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		resourceAttribute.moveAnnotation(1, 0, JPA.ASSOCIATION_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		resourceAttribute.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		resourceAttribute.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		
		resourceAttribute.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertFalse(specifiedAssociationOverrides.hasNext());
	}

	public void testVirtualAssociationOverrideDefaults() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		JavaEmbeddedMapping2_0 embeddedMapping = (JavaEmbeddedMapping2_0) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		JavaResourcePersistentAttribute resourceAttribute = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
		assertNull(resourceAttribute.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceAttribute.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));
		
		assertEquals(2, overrideContainer.virtualOverridesSize());
		VirtualAssociationOverride virtualAssociationOverride = overrideContainer.virtualOverrides().next();
		VirtualJoinColumnRelationshipStrategy joiningStrategy = ((VirtualJoinColumnRelationship) virtualAssociationOverride.getRelationship()).getJoinColumnStrategy();
		assertEquals("address", virtualAssociationOverride.getName());
		assertEquals(1, joiningStrategy.joinColumnsSize());
		VirtualJoinColumn virtualJoinColumn = joiningStrategy.joinColumns().next();
		assertEquals("address_id", virtualJoinColumn.getName());
		assertEquals("id", virtualJoinColumn.getReferencedColumnName());
		assertEquals(TYPE_NAME, virtualJoinColumn.getTable());
		assertEquals(null, virtualJoinColumn.getColumnDefinition());
		assertEquals(true, virtualJoinColumn.isInsertable());
		assertEquals(true, virtualJoinColumn.isUpdatable());
		assertEquals(false, virtualJoinColumn.isUnique());
		assertEquals(true, virtualJoinColumn.isNullable());
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaPersistentType javaEmbeddable = classRefs.next().getJavaPersistentType(); 
		OneToOneMapping oneToOneMapping = (OneToOneMapping) javaEmbeddable.getAttributeNamed("address").getMapping();
		JoinColumn joinColumn = oneToOneMapping.getRelationship().getJoinColumnStrategy().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("MY_JOIN_COLUMN");
		joinColumn.setSpecifiedReferencedColumnName("MY_REFERENCE_COLUMN");
		joinColumn.setSpecifiedTable("BAR");
		joinColumn.setColumnDefinition("COLUMN_DEF");
		joinColumn.setSpecifiedInsertable(Boolean.FALSE);
		joinColumn.setSpecifiedUpdatable(Boolean.FALSE);
		joinColumn.setSpecifiedUnique(Boolean.TRUE);
		joinColumn.setSpecifiedNullable(Boolean.FALSE);
		
		assertNull(resourceAttribute.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceAttribute.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));

		assertEquals(2, overrideContainer.virtualOverridesSize());
		virtualAssociationOverride = overrideContainer.virtualOverrides().next();
		joiningStrategy = ((VirtualJoinColumnRelationship) virtualAssociationOverride.getRelationship()).getJoinColumnStrategy();
		assertEquals("address", virtualAssociationOverride.getName());
		assertEquals(1, joiningStrategy.joinColumnsSize());
		virtualAssociationOverride = overrideContainer.virtualOverrides().next();
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

		
		assertNull(resourceAttribute.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceAttribute.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));

		virtualAssociationOverride = overrideContainer.virtualOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());
		
		virtualAssociationOverride.convertToSpecified();
		assertEquals(1, overrideContainer.virtualOverridesSize());
	}
	
	public void testSpecifiedAssociationOverridesSize() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		JavaEmbeddedMapping2_0 embeddedMapping = (JavaEmbeddedMapping2_0) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();
		JavaResourcePersistentAttribute resourceAttribute = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
		
		assertEquals(0, overrideContainer.specifiedOverridesSize());

		//add an annotation to the resource model and verify the context model is updated
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) resourceAttribute.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("FOO");
		associationOverride = (AssociationOverrideAnnotation) resourceAttribute.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(2, overrideContainer.specifiedOverridesSize());
	}
	
	public void testVirtualAssociationOverridesSize() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		JavaEmbeddedMapping2_0 embeddedMapping = (JavaEmbeddedMapping2_0) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();
		
		assertEquals(2, overrideContainer.virtualOverridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(1, overrideContainer.virtualOverridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(0, overrideContainer.virtualOverridesSize());
	}
	
	public void testAssociationOverridesSize() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		JavaEmbeddedMapping2_0 embeddedMapping = (JavaEmbeddedMapping2_0) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		assertEquals(2, overrideContainer.overridesSize());

		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(2, overrideContainer.overridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(2, overrideContainer.overridesSize());
		
		
		JavaResourcePersistentAttribute resourceAttribute = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
		AssociationOverrideAnnotation annotation = (AssociationOverrideAnnotation) resourceAttribute.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		annotation.setName("bar");
		getJpaProject().synchronizeContextModel();
		assertEquals(3, overrideContainer.overridesSize());
	}

	public void testAssociationOverrideSetVirtual() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		JavaEmbeddedMapping2_0 embeddedMapping = (JavaEmbeddedMapping2_0) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentAttribute resourceAttribute = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
		Iterator<NestableAnnotation> associationOverrides = resourceAttribute.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("address", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertEquals("addresses", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertFalse(associationOverrides.hasNext());
	}
	
	public void testAssociationOverrideSetVirtual2() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		JavaEmbeddedMapping2_0 embeddedMapping = (JavaEmbeddedMapping2_0) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		ListIterator<JavaVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.virtualOverrides();
		virtualAssociationOverrides.next();
		virtualAssociationOverrides.next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentAttribute resourceAttribute = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
		Iterator<NestableAnnotation> associationOverrides = resourceAttribute.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("addresses", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertEquals("address", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertFalse(associationOverrides.hasNext());
	}
	
	public void testAssociationOverrideSetVirtualTrue() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		JavaEmbeddedMapping2_0 embeddedMapping = (JavaEmbeddedMapping2_0) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentAttribute resourceAttribute = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
		assertEquals(2, CollectionTools.size(resourceAttribute.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)));

		overrideContainer.specifiedOverrides().next().convertToVirtual();
		
		Iterator<NestableAnnotation> associationOverrideResources = resourceAttribute.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("addresses", ((AssociationOverrideAnnotation) associationOverrideResources.next()).getName());		
		assertFalse(associationOverrideResources.hasNext());

		Iterator<JavaAssociationOverride> associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("addresses", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		
		overrideContainer.specifiedOverrides().next().convertToVirtual();
		associationOverrideResources = resourceAttribute.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertFalse(associationOverrideResources.hasNext());
		associationOverrides = overrideContainer.specifiedOverrides();
		assertFalse(associationOverrides.hasNext());

		assertNull(resourceAttribute.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedAssociationOverride() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		JavaEmbeddedMapping2_0 embeddedMapping = (JavaEmbeddedMapping2_0) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();

		
		JavaResourcePersistentAttribute resourceAttribute = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
		
		Iterator<NestableAnnotation> javaAssociationOverrides = resourceAttribute.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals(2, CollectionTools.size(javaAssociationOverrides));
		
		
		overrideContainer.moveSpecifiedOverride(1, 0);
		ListIterator<JavaAssociationOverride> associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("addresses", associationOverrides.next().getName());
		assertEquals("address", associationOverrides.next().getName());

		javaAssociationOverrides = resourceAttribute.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("addresses", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
		assertEquals("address", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());


		overrideContainer.moveSpecifiedOverride(0, 1);
		associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("address", associationOverrides.next().getName());
		assertEquals("addresses", associationOverrides.next().getName());

		javaAssociationOverrides = resourceAttribute.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("address", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
		assertEquals("addresses", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
	}

	public void testUpdateSpecifiedAssociationOverrides() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		JavaEmbeddedMapping2_0 embeddedMapping = (JavaEmbeddedMapping2_0) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		JavaResourcePersistentAttribute resourceAttribute = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
	
		((AssociationOverrideAnnotation) resourceAttribute.addAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((AssociationOverrideAnnotation) resourceAttribute.addAnnotation(1, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((AssociationOverrideAnnotation) resourceAttribute.addAnnotation(2, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		ListIterator<JavaAssociationOverride> associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("FOO", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		resourceAttribute.moveAnnotation(2, 0, AssociationOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		resourceAttribute.moveAnnotation(0, 1, AssociationOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		resourceAttribute.removeAnnotation(1,  AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		resourceAttribute.removeAnnotation(1,  AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		resourceAttribute.removeAnnotation(0,  AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.specifiedOverrides();
		assertFalse(associationOverrides.hasNext());
	}

	public void testAssociationOverrideIsVirtual() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		JavaEmbeddedMapping2_0 embeddedMapping = (JavaEmbeddedMapping2_0) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		ListIterator<JavaVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.virtualOverrides();	
		ReadOnlyAssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());

		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("addresses", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		assertFalse(virtualAssociationOverrides.hasNext());

		overrideContainer.virtualOverrides().next().convertToSpecified();
		AssociationOverride specifiedAssociationOverride = overrideContainer.specifiedOverrides().next();
		assertFalse(specifiedAssociationOverride.isVirtual());
		
		
		virtualAssociationOverrides = overrideContainer.virtualOverrides();	
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("addresses", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		assertFalse(virtualAssociationOverrides.hasNext());
	}

	
	public void testVirtualAssociationOverrideJoinTableDefaults() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		createAddressEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		JavaEmbeddedMapping2_0 embeddedMapping = (JavaEmbeddedMapping2_0) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		JavaResourcePersistentAttribute resourceAttribute = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME).persistableAttributes().next();
		assertNull(resourceAttribute.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceAttribute.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));
		
		assertEquals(2, overrideContainer.virtualOverridesSize());
		VirtualAssociationOverride virtualAssociationOverride = CollectionTools.get(overrideContainer.virtualOverrides(), 1);
		VirtualJoinTableRelationshipStrategy joiningStrategy = ((VirtualOverrideRelationship2_0) virtualAssociationOverride.getRelationship()).getJoinTableStrategy();
		VirtualJoinTable joinTable = joiningStrategy.getJoinTable();
		assertEquals("addresses", virtualAssociationOverride.getName());
		assertEquals("AnnotationTestType_Address", joinTable.getName());
		assertEquals(1, joinTable.joinColumnsSize());
		VirtualJoinColumn virtualJoinColumn = joinTable.joinColumns().next();
		assertEquals("AnnotationTestType_id", virtualJoinColumn.getName());
		assertEquals("id", virtualJoinColumn.getReferencedColumnName());
		assertEquals("AnnotationTestType_Address", virtualJoinColumn.getTable());
		assertEquals(null, virtualJoinColumn.getColumnDefinition());
		assertEquals(true, virtualJoinColumn.isInsertable());
		assertEquals(true, virtualJoinColumn.isUpdatable());
		assertEquals(false, virtualJoinColumn.isUnique());
		assertEquals(true, virtualJoinColumn.isNullable());
		
		assertEquals(1, joinTable.inverseJoinColumnsSize());
		VirtualJoinColumn virtualInverseJoinColumn = joinTable.inverseJoinColumns().next();
		assertEquals("addresses_id", virtualInverseJoinColumn.getName());
		assertEquals("id", virtualInverseJoinColumn.getReferencedColumnName());
		assertEquals("AnnotationTestType_Address", virtualInverseJoinColumn.getTable());
		assertEquals(null, virtualInverseJoinColumn.getColumnDefinition());
		assertEquals(true, virtualInverseJoinColumn.isInsertable());
		assertEquals(true, virtualInverseJoinColumn.isUpdatable());
		assertEquals(false, virtualInverseJoinColumn.isUnique());
		assertEquals(true, virtualInverseJoinColumn.isNullable());
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaPersistentType javaEmbeddable = classRefs.next().getJavaPersistentType(); 
		OneToManyMapping oneToManyMapping = (OneToManyMapping) javaEmbeddable.getAttributeNamed("addresses").getMapping();
		JoinTableRelationshipStrategy joinTableStrategy = oneToManyMapping.getRelationship().getJoinTableStrategy();
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
		
		assertNull(resourceAttribute.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceAttribute.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));

		assertEquals(2, overrideContainer.virtualOverridesSize());
		virtualAssociationOverride = CollectionTools.get(overrideContainer.virtualOverrides(), 1);
		joiningStrategy = ((VirtualOverrideRelationship2_0) virtualAssociationOverride.getRelationship()).getJoinTableStrategy();
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

		
		assertNull(resourceAttribute.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceAttribute.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));

		virtualAssociationOverride = overrideContainer.virtualOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());
		
		virtualAssociationOverride.convertToSpecified();
		assertEquals(1, overrideContainer.virtualOverridesSize());
	}

	public void testNestedVirtualAttributeOverrides() throws Exception {
		createTestEntityCustomer();
		createTestEmbeddableAddress();
		createTestEmbeddableZipCode();
		
		addXmlClassRef(PACKAGE_NAME + ".Customer");
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".ZipCode");
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().specifiedClassRefs();
		PersistentType customerPersistentType = specifiedClassRefs.next().getJavaPersistentType();
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) customerPersistentType.getAttributeNamed("address").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		
		assertEquals(5, attributeOverrideContainer.virtualOverridesSize());
		ListIterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.virtualOverrides();
		VirtualAttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
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

		
		PersistentType addressPersistentType = specifiedClassRefs.next().getJavaPersistentType();
		JavaEmbeddedMapping nestedEmbeddedMapping = (JavaEmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping();
		JavaAttributeOverrideContainer nestedAttributeOverrideContainer = nestedEmbeddedMapping.getAttributeOverrideContainer();
		assertEquals(2, nestedAttributeOverrideContainer.virtualOverridesSize());
		virtualAttributeOverrides = nestedAttributeOverrideContainer.virtualOverrides();
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("zip", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("plusfour", virtualAttributeOverride.getName());
		
		PersistentType zipCodePersistentType = specifiedClassRefs.next().getJavaPersistentType();
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
		virtualAttributeOverride = (VirtualAttributeOverride) ((JavaEmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping()).getAttributeOverrideContainer().getOverrideNamed("plusfour");
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
		virtualAttributeOverride = (VirtualAttributeOverride) ((JavaEmbeddedMapping) customerPersistentType.getAttributeNamed("address").getMapping()).getAttributeOverrideContainer().getOverrideNamed("zipCode.plusfour");
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
		AttributeOverride specifiedAttributeOverride = ((VirtualAttributeOverride) ((JavaEmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping()).getAttributeOverrideContainer().getOverrideNamed("plusfour")).convertToSpecified();
		specifiedAttributeOverride.getColumn().setSpecifiedName("BLAH_OVERRIDE");
		specifiedAttributeOverride.getColumn().setSpecifiedTable("BLAH_TABLE_OVERRIDE");
		specifiedAttributeOverride.getColumn().setColumnDefinition("COLUMN_DEFINITION_OVERRIDE");
	
		virtualAttributeOverride = (VirtualAttributeOverride) ((JavaEmbeddedMapping) customerPersistentType.getAttributeNamed("address").getMapping()).getAttributeOverrideContainer().getOverrideNamed("zipCode.plusfour");
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
		
		specifiedAttributeOverride = virtualAttributeOverride.convertToSpecified();
		assertEquals(false, specifiedAttributeOverride.isVirtual());
		assertEquals("zipCode.plusfour", specifiedAttributeOverride.getName());
		//TODO I have the default wrong in this case, but this was wrong before as well.  Need to fix this later
//		assertEquals("plusfour", specifiedAttributeOverride.getColumn().getDefaultName());
		assertEquals("BLAH_OVERRIDE", specifiedAttributeOverride.getColumn().getSpecifiedName());
//		assertEquals("Customer", specifiedAttributeOverride.getColumn().getDefaultTable());	
		assertEquals("BLAH_TABLE_OVERRIDE", specifiedAttributeOverride.getColumn().getSpecifiedTable());	
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
		addXmlClassRef(PACKAGE_NAME + ".Foo");
		
		//If there is a StackOverflowError you will not be able to get the mapping
		JavaEmbeddedMapping2_0 embeddedMapping = (JavaEmbeddedMapping2_0) getJavaPersistentType().getAttributeNamed("embedded").getMapping();
		assertFalse(embeddedMapping.allOverridableAttributeMappingNames().hasNext());
	}
}

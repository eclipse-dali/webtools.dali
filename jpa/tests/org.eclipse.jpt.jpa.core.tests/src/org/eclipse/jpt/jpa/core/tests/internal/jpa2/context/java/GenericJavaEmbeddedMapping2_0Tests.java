/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.context.VirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.VirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaNullAttributeMapping;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.VirtualOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaEmbeddedMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.Access2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ElementCollection2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.ManyToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

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
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof BasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(((JavaEmbeddedMapping) persistentAttribute.getMapping()).getAttributeOverrideContainer().getOverrides().iterator().hasNext());
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		assertNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
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
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof VersionMapping);
	
		assertNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		
		assertNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TransientAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IdMapping);
		
		assertNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		
		assertNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToOneMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToOneMapping);
		
		assertNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		
		assertNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		
		assertNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		
		assertNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToElementCollectionMapping() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributeNamed("myEmbedded");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ElementCollectionMapping2_0);
		
		assertNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
	}

	public void testSpecifiedAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		
		ListIterator<JavaAttributeOverride> specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		
		assertFalse(specifiedAttributeOverrides.hasNext());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());


		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		resourceField.moveAnnotation(1, 0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		resourceField.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		resourceField.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		
		resourceField.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertFalse(specifiedAttributeOverrides.hasNext());
	}

	public void testVirtualAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertEquals("myEmbedded", resourceField.getName());
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		
		assertEquals(2, attributeOverrideContainer.getVirtualOverridesSize());
		ReadOnlyAttributeOverride defaultAttributeOverride = attributeOverrideContainer.getVirtualOverrides().iterator().next();
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
		
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
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
		
		assertEquals("myEmbedded", resourceField.getName());
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));

		assertEquals(2, attributeOverrideContainer.getVirtualOverridesSize());
		defaultAttributeOverride = attributeOverrideContainer.getVirtualOverrides().iterator().next();
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
		defaultAttributeOverride = attributeOverrideContainer.getVirtualOverrides().iterator().next();
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
		
		AttributeOverrideAnnotation annotation = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		annotation.setName("city");
		getJpaProject().synchronizeContextModel();
		assertEquals(1, attributeOverrideContainer.getVirtualOverridesSize());
	}
	
	public void testSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		assertEquals(0, attributeOverrideContainer.getSpecifiedOverridesSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(2, attributeOverrideContainer.getSpecifiedOverridesSize());
	}
	
	public void testAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		assertEquals(2, attributeOverrideContainer.getOverridesSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(4, attributeOverrideContainer.getOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("city");
		getJpaProject().synchronizeContextModel();
		assertEquals(4, attributeOverrideContainer.getOverridesSize());	
	}
	
	public void testVirtualAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		assertEquals(2, attributeOverrideContainer.getVirtualOverridesSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		getJpaProject().synchronizeContextModel();

		assertEquals(2, attributeOverrideContainer.getVirtualOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("city");
		getJpaProject().synchronizeContextModel();
		assertEquals(1, attributeOverrideContainer.getVirtualOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("state");
		getJpaProject().synchronizeContextModel();
		assertEquals(0, attributeOverrideContainer.getVirtualOverridesSize());
	}

	public void testAttributeOverrideSetVirtual() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
				
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		attributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		attributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		Iterator<NestableAnnotation> attributeOverrides = resourceField.getAnnotations(JPA.ATTRIBUTE_OVERRIDE).iterator();
		
		assertEquals("city", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("state", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		attributeOverrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		attributeOverrides = resourceField.getAnnotations(JPA.ATTRIBUTE_OVERRIDE).iterator();
		assertEquals("state", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		assertEquals("city", attributeOverrideContainer.getVirtualOverrides().iterator().next().getName());
		assertEquals(1, attributeOverrideContainer.getVirtualOverridesSize());
		
		attributeOverrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		attributeOverrides = resourceField.getAnnotations(JPA.ATTRIBUTE_OVERRIDE).iterator();
		assertFalse(attributeOverrides.hasNext());
		
		Iterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		assertEquals("city", virtualAttributeOverrides.next().getName());
		assertEquals("state", virtualAttributeOverrides.next().getName());
		assertEquals(2, attributeOverrideContainer.getVirtualOverridesSize());
	}
	
	public void testAttributeOverrideSetVirtual2() throws Exception {
		createTestEntityWithEmbeddedMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		ListIterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		virtualAttributeOverrides.next();	
		virtualAttributeOverrides.next().convertToSpecified();
		attributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		Iterator<NestableAnnotation> attributeOverrides = resourceField.getAnnotations(JPA.ATTRIBUTE_OVERRIDE).iterator();
		
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
		attributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		attributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		resourceField.moveAnnotation(1, 0, JPA.ATTRIBUTE_OVERRIDE);
		
		Iterator<NestableAnnotation> attributeOverrides = resourceField.getAnnotations(JPA.ATTRIBUTE_OVERRIDE).iterator();

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
		ListIterator<JavaAssociationOverride> specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		
		assertFalse(specifiedAssociationOverrides.hasNext());

		JavaResourceField resourceField = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		
		//add an annotation to the resource model and verify the context model is updated
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) resourceField.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		associationOverride = (AssociationOverrideAnnotation) resourceField.addAnnotation(1, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());


		associationOverride = (AssociationOverrideAnnotation) resourceField.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		resourceField.moveAnnotation(1, 0, JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		resourceField.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		resourceField.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		
		resourceField.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
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

		JavaResourceField resourceField = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		assertNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
		
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		VirtualAssociationOverride virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		VirtualJoinColumnRelationshipStrategy joiningStrategy = ((VirtualJoinColumnRelationship) virtualAssociationOverride.getRelationship()).getJoinColumnStrategy();
		assertEquals("address", virtualAssociationOverride.getName());
		assertEquals(1, joiningStrategy.getJoinColumnsSize());
		VirtualJoinColumn virtualJoinColumn = joiningStrategy.getJoinColumns().iterator().next();
		assertEquals("address_id", virtualJoinColumn.getName());
		assertEquals("id", virtualJoinColumn.getReferencedColumnName());
		assertEquals(TYPE_NAME, virtualJoinColumn.getTable());
		assertEquals(null, virtualJoinColumn.getColumnDefinition());
		assertEquals(true, virtualJoinColumn.isInsertable());
		assertEquals(true, virtualJoinColumn.isUpdatable());
		assertEquals(false, virtualJoinColumn.isUnique());
		assertEquals(true, virtualJoinColumn.isNullable());
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
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
		
		assertNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));

		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		joiningStrategy = ((VirtualJoinColumnRelationship) virtualAssociationOverride.getRelationship()).getJoinColumnStrategy();
		assertEquals("address", virtualAssociationOverride.getName());
		assertEquals(1, joiningStrategy.getJoinColumnsSize());
		virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		virtualJoinColumn = joiningStrategy.getJoinColumns().iterator().next();
		assertEquals("MY_JOIN_COLUMN", virtualJoinColumn.getName());
		assertEquals("MY_REFERENCE_COLUMN", virtualJoinColumn.getReferencedColumnName());
		assertEquals("BAR", virtualJoinColumn.getTable());
		assertEquals("COLUMN_DEF", virtualJoinColumn.getColumnDefinition());
		assertEquals(false, virtualJoinColumn.isInsertable());
		assertEquals(false, virtualJoinColumn.isUpdatable());
		assertEquals(true, virtualJoinColumn.isUnique());
		assertEquals(false, virtualJoinColumn.isNullable());

		assertEquals("MY_JOIN_COLUMN", joiningStrategy.getJoinColumns().iterator().next().getName());

		
		assertNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));

		virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("address", virtualAssociationOverride.getName());
		
		virtualAssociationOverride.convertToSpecified();
		assertEquals(1, overrideContainer.getVirtualOverridesSize());
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
		JavaResourceField resourceField = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		
		assertEquals(0, overrideContainer.getSpecifiedOverridesSize());

		//add an annotation to the resource model and verify the context model is updated
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) resourceField.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("FOO");
		associationOverride = (AssociationOverrideAnnotation) resourceField.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(2, overrideContainer.getSpecifiedOverridesSize());
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
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		JavaEmbeddedMapping2_0 embeddedMapping = (JavaEmbeddedMapping2_0) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		assertEquals(2, overrideContainer.getOverridesSize());

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(2, overrideContainer.getOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(2, overrideContainer.getOverridesSize());
		
		
		JavaResourceField resourceField = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		AssociationOverrideAnnotation annotation = (AssociationOverrideAnnotation) resourceField.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		annotation.setName("bar");
		getJpaProject().synchronizeContextModel();
		assertEquals(3, overrideContainer.getOverridesSize());
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

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceField resourceField = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		Iterator<NestableAnnotation> associationOverrides = resourceField.getAnnotations(JPA.ASSOCIATION_OVERRIDE).iterator();
		
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

		ListIterator<JavaVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.getVirtualOverrides().iterator();
		virtualAssociationOverrides.next();
		virtualAssociationOverrides.next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceField resourceField = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		Iterator<NestableAnnotation> associationOverrides = resourceField.getAnnotations(JPA.ASSOCIATION_OVERRIDE).iterator();
		
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

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceField resourceField = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		assertEquals(2, CollectionTools.size(resourceField.getAnnotations(JPA.ASSOCIATION_OVERRIDE).iterator()));

		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		
		Iterator<NestableAnnotation> associationOverrideResources = resourceField.getAnnotations(JPA.ASSOCIATION_OVERRIDE).iterator();
		assertEquals("addresses", ((AssociationOverrideAnnotation) associationOverrideResources.next()).getName());		
		assertFalse(associationOverrideResources.hasNext());

		Iterator<JavaAssociationOverride> associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("addresses", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		
		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		associationOverrideResources = resourceField.getAnnotations(JPA.ASSOCIATION_OVERRIDE).iterator();
		assertFalse(associationOverrideResources.hasNext());
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertFalse(associationOverrides.hasNext());

		assertNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
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

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();

		
		JavaResourceField resourceField = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		
		Iterator<NestableAnnotation> javaAssociationOverrides = resourceField.getAnnotations(JPA.ASSOCIATION_OVERRIDE).iterator();
		assertEquals(2, CollectionTools.size(javaAssociationOverrides));
		
		
		overrideContainer.moveSpecifiedOverride(1, 0);
		ListIterator<JavaAssociationOverride> associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("addresses", associationOverrides.next().getName());
		assertEquals("address", associationOverrides.next().getName());

		javaAssociationOverrides = resourceField.getAnnotations(JPA.ASSOCIATION_OVERRIDE).iterator();
		assertEquals("addresses", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
		assertEquals("address", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());


		overrideContainer.moveSpecifiedOverride(0, 1);
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("address", associationOverrides.next().getName());
		assertEquals("addresses", associationOverrides.next().getName());

		javaAssociationOverrides = resourceField.getAnnotations(JPA.ASSOCIATION_OVERRIDE).iterator();
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

		JavaResourceField resourceField = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
	
		((AssociationOverrideAnnotation) resourceField.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE)).setName("FOO");
		((AssociationOverrideAnnotation) resourceField.addAnnotation(1, JPA.ASSOCIATION_OVERRIDE)).setName("BAR");
		((AssociationOverrideAnnotation) resourceField.addAnnotation(2, JPA.ASSOCIATION_OVERRIDE)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		ListIterator<JavaAssociationOverride> associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("FOO", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		resourceField.moveAnnotation(2, 0, JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		resourceField.moveAnnotation(0, 1, JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		resourceField.removeAnnotation(1,  JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		resourceField.removeAnnotation(1,  JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		resourceField.removeAnnotation(0,  JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
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

		ListIterator<JavaVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.getVirtualOverrides().iterator();	
		ReadOnlyAssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
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
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		JavaEmbeddedMapping2_0 embeddedMapping = (JavaEmbeddedMapping2_0) getJavaPersistentType().getAttributeNamed("myEmbedded").getMapping();
		JavaAssociationOverrideContainer overrideContainer = embeddedMapping.getAssociationOverrideContainer();

		JavaResourceField resourceField = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		assertNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
		
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		VirtualAssociationOverride virtualAssociationOverride = CollectionTools.get(overrideContainer.getVirtualOverrides(), 1);
		VirtualJoinTableRelationshipStrategy joiningStrategy = ((VirtualOverrideRelationship2_0) virtualAssociationOverride.getRelationship()).getJoinTableStrategy();
		VirtualJoinTable joinTable = joiningStrategy.getJoinTable();
		assertEquals("addresses", virtualAssociationOverride.getName());
		assertEquals("AnnotationTestType_Address", joinTable.getName());
		assertEquals(1, joinTable.getJoinColumnsSize());
		VirtualJoinColumn virtualJoinColumn = joinTable.getJoinColumns().iterator().next();
		assertEquals("AnnotationTestType_id", virtualJoinColumn.getName());
		assertEquals("id", virtualJoinColumn.getReferencedColumnName());
		assertEquals("AnnotationTestType_Address", virtualJoinColumn.getTable());
		assertEquals(null, virtualJoinColumn.getColumnDefinition());
		assertEquals(true, virtualJoinColumn.isInsertable());
		assertEquals(true, virtualJoinColumn.isUpdatable());
		assertEquals(false, virtualJoinColumn.isUnique());
		assertEquals(true, virtualJoinColumn.isNullable());
		
		assertEquals(1, joinTable.getInverseJoinColumnsSize());
		VirtualJoinColumn virtualInverseJoinColumn = joinTable.getInverseJoinColumns().iterator().next();
		assertEquals("addresses_id", virtualInverseJoinColumn.getName());
		assertEquals("id", virtualInverseJoinColumn.getReferencedColumnName());
		assertEquals("AnnotationTestType_Address", virtualInverseJoinColumn.getTable());
		assertEquals(null, virtualInverseJoinColumn.getColumnDefinition());
		assertEquals(true, virtualInverseJoinColumn.isInsertable());
		assertEquals(true, virtualInverseJoinColumn.isUpdatable());
		assertEquals(false, virtualInverseJoinColumn.isUnique());
		assertEquals(true, virtualInverseJoinColumn.isNullable());
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
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
		
		assertNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));

		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		virtualAssociationOverride = CollectionTools.get(overrideContainer.getVirtualOverrides(), 1);
		joiningStrategy = ((VirtualOverrideRelationship2_0) virtualAssociationOverride.getRelationship()).getJoinTableStrategy();
		joinTable = joiningStrategy.getJoinTable();
		assertEquals("addresses", virtualAssociationOverride.getName());
		assertEquals(1, joinTable.getJoinColumnsSize());
		virtualJoinColumn = joinTable.getJoinColumns().iterator().next();
		assertEquals("MY_JOIN_COLUMN", virtualJoinColumn.getName());
		assertEquals("MY_REFERENCE_COLUMN", virtualJoinColumn.getReferencedColumnName());
		assertEquals("BAR", virtualJoinColumn.getTable());
		assertEquals("COLUMN_DEF", virtualJoinColumn.getColumnDefinition());
		assertEquals(false, virtualJoinColumn.isInsertable());
		assertEquals(false, virtualJoinColumn.isUpdatable());
		assertEquals(true, virtualJoinColumn.isUnique());
		assertEquals(false, virtualJoinColumn.isNullable());

		assertEquals(1, joinTable.getInverseJoinColumnsSize());
		virtualInverseJoinColumn = joinTable.getInverseJoinColumns().iterator().next();
		assertEquals("MY_INVERSE_JOIN_COLUMN", virtualInverseJoinColumn.getName());
		assertEquals("MY_INVERSE_REFERENCE_COLUMN", virtualInverseJoinColumn.getReferencedColumnName());
		assertEquals("INVERSE_BAR", virtualInverseJoinColumn.getTable());
		assertEquals("INVERSE_COLUMN_DEF", virtualInverseJoinColumn.getColumnDefinition());
		assertEquals(false, virtualInverseJoinColumn.isInsertable());
		assertEquals(false, virtualInverseJoinColumn.isUpdatable());
		assertEquals(true, virtualInverseJoinColumn.isUnique());
		assertEquals(false, virtualInverseJoinColumn.isNullable());

		
		assertNull(resourceField.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));

		virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("address", virtualAssociationOverride.getName());
		
		virtualAssociationOverride.convertToSpecified();
		assertEquals(1, overrideContainer.getVirtualOverridesSize());
	}

	public void testNestedVirtualAttributeOverrides() throws Exception {
		createTestEntityCustomer();
		createTestEmbeddableAddress();
		createTestEmbeddableZipCode();
		
		addXmlClassRef(PACKAGE_NAME + ".Customer");
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".ZipCode");
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		PersistentType customerPersistentType = specifiedClassRefs.next().getJavaPersistentType();
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) customerPersistentType.getAttributeNamed("address").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		
		assertEquals(5, attributeOverrideContainer.getVirtualOverridesSize());
		ListIterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
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
		assertEquals(2, nestedAttributeOverrideContainer.getVirtualOverridesSize());
		virtualAttributeOverrides = nestedAttributeOverrideContainer.getVirtualOverrides().iterator();
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
		assertFalse(embeddedMapping.getAllOverridableAttributeMappingNames().iterator().hasNext());
	}
}

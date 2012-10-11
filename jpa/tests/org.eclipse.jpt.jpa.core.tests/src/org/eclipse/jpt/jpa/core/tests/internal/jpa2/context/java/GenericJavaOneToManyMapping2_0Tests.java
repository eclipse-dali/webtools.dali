/*******************************************************************************
* Copyright (c) 2009, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.EnumType;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.context.VirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToManyMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToManyRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovalHolder2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.Access2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyClass2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyColumn2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyEnumerated2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyJoinColumn2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyTemporal2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.OneToMany2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ManyToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OrderByAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericJavaOneToManyMapping2_0Tests
	extends Generic2_0ContextModelTestCase
{
	public GenericJavaOneToManyMapping2_0Tests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEntityWithValidOneToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany").append(CR);				
				sb.append("    private Collection<Address> addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
		
	private ICompilationUnit createTestEntityWithValidOneToManyMappingOrphanRemovalSpecified() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany(orphanRemoval=false)").append(CR);
				sb.append("    private Collection<Address> addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}

	private ICompilationUnit createTestEntityWithValidGenericMapOneToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany").append(CR);				
				sb.append("    private java.util.Map<Integer, Address> addresses;").append(CR);			
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithValidNonGenericMapOneToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany").append(CR);				
				sb.append("    private java.util.Map addresses;").append(CR);			
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
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
					sb.append("import ");
					sb.append(JPA.EMBEDDED);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.MANY_TO_ONE);
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
				sb.append("    @Embedded").append(CR);
				sb.append("    private State state;").append(CR);
				sb.append(CR);
				sb.append("    private int zip;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToOne").append(CR);
				sb.append("    private AnnotationTestType employee;").append(CR);
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
				sb.append("    private String address;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "State.java", sourceWriter);
	}

	private ICompilationUnit createTestEntityWithEmbeddableKeyOneToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany").append(CR);				
				sb.append("    private java.util.Map<Address, PropertyInfo> parcels;").append(CR);			
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}

	private ICompilationUnit createTestEntityWithEntityKeyOneToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany").append(CR);				
				sb.append("    private java.util.Map<Address, PropertyInfo> parcels;").append(CR);			
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
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
					sb.append(JPA.EMBEDDED);
					sb.append(";");
					sb.append(CR);
				sb.append("@Embeddable");
				sb.append(CR);
				sb.append("public class ").append("Address").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    private String city;").append(CR);
				sb.append(CR);
				sb.append("    @Embedded").append(CR);
				sb.append("    private State state;").append(CR);
				sb.append(CR);
				sb.append("    private int zip;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
	}

	private void createTestEntityPropertyInfo() throws Exception {
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
					sb.append(JPA.EMBEDDED);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("PropertyInfo").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    private Integer parcelNumber;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
				sb.append("    private Integer size;").append(CR);
				sb.append(CR);
				sb.append("    private java.math.BigDecimal tax;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "PropertyInfo.java", sourceWriter);
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping oneToManyMapping = (OneToManyMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("FOO");
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof BasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping oneToManyMapping = (OneToManyMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("FOO");
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		assertNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping oneToManyMapping = (OneToManyMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("FOO");
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof VersionMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping oneToManyMapping = (OneToManyMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("FOO");
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping oneToManyMapping = (OneToManyMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("FOO");
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping oneToManyMapping = (OneToManyMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("FOO");
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping oneToManyMapping = (OneToManyMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("FOO");
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TransientAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToOneMapping() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping oneToManyMapping = (OneToManyMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("FOO");
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToOneMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping oneToManyMapping = (OneToManyMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("FOO");
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping oneToManyMapping = (OneToManyMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		oneToManyMapping.getOrderable().setSpecifiedOrderBy("asdf");
		oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("FOO");
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(oneToManyMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(OrderByAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = (getJavaPersistentType()).getAttributes().iterator().next();
		OneToManyMapping oneToManyMapping = (OneToManyMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			oneToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertEquals("employee", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		oneToManyMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			oneToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertFalse(attributeNames.hasNext());
		
		oneToManyMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			oneToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertEquals("employee", attributeNames.next());
		assertFalse(attributeNames.hasNext());

		AttributeMapping stateFooMapping = oneToManyMapping.getResolvedTargetEntity().resolveAttributeMapping("state.foo");
		assertEquals("foo", stateFooMapping.getName());
	}
	
	public void testCandidateMappedByAttributeNamesElementCollection() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		createTestTargetEntityAddressWithElementCollection();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = (getJavaPersistentType()).getAttributes().iterator().next();
		OneToManyMapping oneToManyMapping = (OneToManyMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			oneToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		oneToManyMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			oneToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertFalse(attributeNames.hasNext());
		
		oneToManyMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			oneToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());

		AttributeMapping stateFooMapping = oneToManyMapping.getResolvedTargetEntity().resolveAttributeMapping("state.foo");
		assertEquals("foo", stateFooMapping.getName());
	}
	
	private OrphanRemovable2_0 getOrphanRemovalOf(OneToManyMapping2_0 oneToManyMapping) {
		return ((OrphanRemovalHolder2_0) oneToManyMapping).getOrphanRemoval();
	}

	public void testDefaultOneToManyGetDefaultOrphanRemoval() throws Exception {
		this.createTestEntityWithValidOneToManyMapping();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		assertEquals(false, this.getOrphanRemovalOf(oneToManyMapping).isDefaultOrphanRemoval());
	}
	
	public void testSpecifiedOneToManyGetDefaultOrphanRemoval() throws Exception {
		this.createTestEntityWithValidOneToManyMapping();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		assertEquals(false, this.getOrphanRemovalOf(oneToManyMapping).isDefaultOrphanRemoval());
	}
	
	public void testGetOrphanRemoval() throws Exception {
		this.createTestEntityWithValidOneToManyMapping();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		OrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToManyMapping);

		assertEquals(false, mappingsOrphanRemoval.isOrphanRemoval());
		
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(Boolean.TRUE);
		assertEquals(true, mappingsOrphanRemoval.isOrphanRemoval());
	}
	
	public void testGetSpecifiedOrphanRemoval() throws Exception {
		this.createTestEntityWithValidOneToManyMapping();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		OrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToManyMapping);

		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		OneToMany2_0Annotation oneToMany = (OneToMany2_0Annotation) resourceField.getAnnotation(JPA.ONE_TO_MANY);
		oneToMany.setOrphanRemoval(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(Boolean.FALSE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
	}
	
	public void testGetSpecifiedOrphanRemoval2() throws Exception {
		this.createTestEntityWithValidOneToManyMappingOrphanRemovalSpecified();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		OrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToManyMapping);

		assertEquals(Boolean.FALSE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
	}

	public void testSetSpecifiedOrphanRemoval() throws Exception {
		this.createTestEntityWithValidOneToManyMapping();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		OrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToManyMapping);
		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(Boolean.TRUE);
		
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		OneToMany2_0Annotation oneToMany = (OneToMany2_0Annotation) resourceField.getAnnotation(JPA.ONE_TO_MANY);
		
		assertEquals(Boolean.TRUE, oneToMany.getOrphanRemoval());
		
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(null);
		assertNotNull(resourceField.getAnnotation(JPA.ONE_TO_MANY)); 	// .getElement);
	}
	
	public void testSetSpecifiedOrphanRemoval2() throws Exception {
		this.createTestEntityWithValidOneToManyMapping();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		OrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToManyMapping);
		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(Boolean.TRUE);
		
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		OneToMany2_0Annotation oneToMany = (OneToMany2_0Annotation) resourceField.getAnnotation(JPA.ONE_TO_MANY);
		
		assertEquals(Boolean.TRUE, oneToMany.getOrphanRemoval());
		
		oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		assertEquals(Boolean.TRUE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());

		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(null);
		assertNotNull(resourceField.getAnnotation(JPA.ONE_TO_MANY));
		
		oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
	}

	public void testGetSpecifiedOrphanRemovalUpdatesFromResourceModelChange() throws Exception {
		this.createTestEntityWithValidOneToManyMapping();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		OrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToManyMapping);

		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());

		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		OneToMany2_0Annotation oneToMany = (OneToMany2_0Annotation) resourceField.getAnnotation(JPA.ONE_TO_MANY);
		oneToMany.setOrphanRemoval(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();

		assertEquals(Boolean.FALSE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		
		oneToMany.setOrphanRemoval(null);
		getJpaProject().synchronizeContextModel();
		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		assertSame(oneToManyMapping, persistentAttribute.getMapping());
		
		oneToMany.setOrphanRemoval(Boolean.FALSE);
		resourceField.setPrimaryAnnotation(null, EmptyIterable.<String>instance());
		getJpaProject().synchronizeContextModel();
		
		assertTrue(persistentAttribute.getMapping().isDefault());
	}
	
	public void testUpdateMapKey() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		
		assertNull(oneToManyMapping.getSpecifiedMapKey());
		assertNull(resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME));
		
		//set mapKey in the resource model, verify context model does not change
		resourceField.addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertNull(oneToManyMapping.getSpecifiedMapKey());
		MapKeyAnnotation mapKey = (MapKeyAnnotation) resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertNotNull(mapKey);
				
		//set mapKey name in the resource model, verify context model updated
		mapKey.setName("myMapKey");
		getJpaProject().synchronizeContextModel();
		assertEquals("myMapKey", oneToManyMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", mapKey.getName());
		
		//set mapKey name to null in the resource model
		mapKey.setName(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(oneToManyMapping.getSpecifiedMapKey());
		assertNull(mapKey.getName());
		
		mapKey.setName("myMapKey");
		resourceField.removeAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();

		assertNull(oneToManyMapping.getSpecifiedMapKey());
		assertNull(resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME));
	}
	
	public void testModifyMapKey() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		
		assertNull(oneToManyMapping.getSpecifiedMapKey());
		assertNull(resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME));
					
		//set mapKey  in the context model, verify resource model updated
		oneToManyMapping.setSpecifiedMapKey("myMapKey");
		MapKeyAnnotation mapKeyAnnotation = (MapKeyAnnotation) resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertEquals("myMapKey", oneToManyMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", mapKeyAnnotation.getName());
	
		//set mapKey to null in the context model
		oneToManyMapping.setSpecifiedMapKey(null);
		assertNull(oneToManyMapping.getSpecifiedMapKey());
		mapKeyAnnotation = (MapKeyAnnotation) resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertNull(mapKeyAnnotation.getName());
	}
	
	public void testCandidateMapKeyNames() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping2_0 = (OneToManyMapping2_0) persistentAttribute.getMapping();

		Iterator<String> mapKeyNames = 
			oneToManyMapping2_0.getCandidateMapKeyNames().iterator();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.foo", mapKeyNames.next());
		assertEquals("state.address", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertEquals("employee", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
	}
	
	public void testCandidateMapKeyNames2() throws Exception {
		createTestEntityWithValidNonGenericMapOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping2_0 = (OneToManyMapping2_0) persistentAttribute.getMapping();

		Iterator<String> mapKeyNames = oneToManyMapping2_0.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
		
		oneToManyMapping2_0.setSpecifiedTargetEntity("Address");
		mapKeyNames = oneToManyMapping2_0.getCandidateMapKeyNames().iterator();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.foo", mapKeyNames.next());
		assertEquals("state.address", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertEquals("employee", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		oneToManyMapping2_0.setSpecifiedTargetEntity("String");
		mapKeyNames = oneToManyMapping2_0.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
	}
	
	public void testUpdateMapKeyClass() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		
		assertNull(oneToManyMapping.getSpecifiedMapKeyClass());
		assertNull(resourceField.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME));
		
		//set mapKey in the resource model, verify context model does not change
		resourceField.addAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
		assertNull(oneToManyMapping.getSpecifiedMapKeyClass());
		MapKeyClass2_0Annotation mapKeyClassAnnotation = (MapKeyClass2_0Annotation) resourceField.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
		assertNotNull(mapKeyClassAnnotation);
				
		//set mapKey name in the resource model, verify context model updated
		mapKeyClassAnnotation.setValue("myMapKeyClass");
		this.getJpaProject().synchronizeContextModel();
		assertEquals("myMapKeyClass", oneToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("myMapKeyClass", mapKeyClassAnnotation.getValue());
		
		//set mapKey name to null in the resource model
		mapKeyClassAnnotation.setValue(null);
		this.getJpaProject().synchronizeContextModel();
		assertNull(oneToManyMapping.getSpecifiedMapKeyClass());
		assertNull(mapKeyClassAnnotation.getValue());
		
		mapKeyClassAnnotation.setValue("myMapKeyClass");
		resourceField.removeAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertNull(oneToManyMapping.getSpecifiedMapKeyClass());
		assertNull(resourceField.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testModifyMapKeyClass() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		
		assertNull(oneToManyMapping.getSpecifiedMapKeyClass());
		assertNull(resourceField.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME));
					
		//set mapKey  in the context model, verify resource model updated
		oneToManyMapping.setSpecifiedMapKeyClass("String");
		MapKeyClass2_0Annotation mapKeyClass = (MapKeyClass2_0Annotation) resourceField.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
		assertEquals("String", oneToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("String", mapKeyClass.getValue());
	
		//set mapKey to null in the context model
		oneToManyMapping.setSpecifiedMapKeyClass(null);
		assertNull(oneToManyMapping.getSpecifiedMapKeyClass());
		assertNull(resourceField.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME));
	}

	public void testDefaultMapKeyClass() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();

		assertEquals("java.lang.Integer", oneToManyMapping.getDefaultMapKeyClass());

		//test default still the same when specified target entity it set
		oneToManyMapping.setSpecifiedMapKeyClass("foo");
		assertEquals("java.lang.Integer", oneToManyMapping.getDefaultMapKeyClass());
	}
	
	public void testDefaultMapKeyClassCollectionType() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();

		assertNull(oneToManyMapping.getDefaultMapKeyClass());
	}
	
	public void testMapKeyClass() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();

		assertEquals("java.lang.Integer", oneToManyMapping.getMapKeyClass());

		oneToManyMapping.setSpecifiedMapKeyClass("foo");
		assertEquals("foo", oneToManyMapping.getMapKeyClass());
		
		oneToManyMapping.setSpecifiedMapKeyClass(null);
		assertEquals("java.lang.Integer", oneToManyMapping.getMapKeyClass());
	}

	public void testOrderColumnDefaults() throws Exception {
		createTestEntityPrintQueue();
		createTestEntityPrintJob();
		
		addXmlClassRef(PACKAGE_NAME + ".PrintQueue");
		addXmlClassRef(PACKAGE_NAME + ".PrintJob");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) getJavaPersistentType().getAttributeNamed("jobs").getMapping();

		Orderable2_0 orderable = ((Orderable2_0) oneToManyMapping.getOrderable());
		OrderColumn2_0 orderColumn = orderable.getOrderColumn();
		assertEquals(true, orderable.isOrderColumnOrdering());
		assertEquals(null, orderColumn.getSpecifiedName());
		assertEquals("jobs_ORDER", orderColumn.getDefaultName());
		assertEquals("PrintJob", orderColumn.getTable());
		
		orderColumn.setSpecifiedName("FOO");
		assertEquals("FOO", orderColumn.getSpecifiedName());
		assertEquals("jobs_ORDER", orderColumn.getDefaultName());
		assertEquals("PrintJob", orderColumn.getTable());
		
		JavaPersistentType printJobPersistentType = (JavaPersistentType) getPersistenceUnit().getPersistentType("test.PrintJob");
		((Entity) printJobPersistentType.getMapping()).getTable().setSpecifiedName("MY_TABLE");

		assertEquals("MY_TABLE", orderColumn.getTable());
	}
	
	private void createTestEntityPrintQueue() throws Exception {
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
					sb.append(JPA.ONE_TO_MANY);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA2_0.ORDER_COLUMN);
					sb.append(";");
					sb.append(CR);
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("PrintQueue").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private String name;").append(CR);
				sb.append(CR);
				sb.append("    @OneToMany(mappedBy=\"queue\")").append(CR);
				sb.append("    @OrderColumn").append(CR);
				sb.append("    private java.util.List<PrintJob> jobs;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "PrintQueue.java", sourceWriter);
	}
	
	private void createTestEntityPrintJob() throws Exception {
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
					sb.append(JPA.MANY_TO_ONE);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("PrintJob").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int id;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToOne").append(CR);
				sb.append("    private PrintQueue queue;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "PrintJob.java", sourceWriter);
	}

	public void testGetMapKeyColumnMappedByStrategy() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		oneToManyMapping.getRelationship().setStrategyToMappedBy();
		oneToManyMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("employee");
		
		assertNull(oneToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("addresses_KEY", oneToManyMapping.getMapKeyColumn().getName());
		assertEquals("Address", oneToManyMapping.getMapKeyColumn().getTable());//owing entity table name
		
		Entity addressEntity = getPersistenceUnit().getEntity("test.Address");
		addressEntity.getTable().setSpecifiedName("MY_PRIMARY_TABLE");
		assertEquals("MY_PRIMARY_TABLE", oneToManyMapping.getMapKeyColumn().getTable());
		
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.addAnnotation(JPA2_0.MAP_KEY_COLUMN);
		column.setName("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("foo", oneToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("foo", oneToManyMapping.getMapKeyColumn().getName());
		assertEquals("addresses_KEY", oneToManyMapping.getMapKeyColumn().getDefaultName());
	}
	
	public void testGetMapKeyColumnJoinTableStrategy() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		
		assertNull(oneToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("addresses_KEY", oneToManyMapping.getMapKeyColumn().getName());
		assertEquals(TYPE_NAME + "_Address", oneToManyMapping.getMapKeyColumn().getTable());//join table name
		
		oneToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("MY_PRIMARY_TABLE");
		assertEquals("MY_PRIMARY_TABLE", oneToManyMapping.getMapKeyColumn().getTable());
		
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.addAnnotation(JPA2_0.MAP_KEY_COLUMN);
		column.setName("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("foo", oneToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("foo", oneToManyMapping.getMapKeyColumn().getName());
		assertEquals("addresses_KEY", oneToManyMapping.getMapKeyColumn().getDefaultName());
	}

	public void testTargetForeignKeyJoinColumnStrategy() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");

		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		((JoinColumnRelationship) oneToManyMapping.getRelationship()).setStrategyToJoinColumn();

		JoinColumn joinColumn = ((JoinColumnRelationship) oneToManyMapping.getRelationship()).getJoinColumnStrategy().getSpecifiedJoinColumns().iterator().next();

		assertEquals("addresses_id", joinColumn.getDefaultName());
		assertEquals("Address", joinColumn.getDefaultTable());//target table name

		Entity addressEntity = getPersistenceUnit().getEntity("test.Address");
		addressEntity.getTable().setSpecifiedName("ADDRESS_PRIMARY_TABLE");
		assertEquals("ADDRESS_PRIMARY_TABLE", joinColumn.getDefaultTable());

		joinColumn.setSpecifiedName("FOO");
		assertEquals("addresses_id", joinColumn.getDefaultName());
		assertEquals("FOO", joinColumn.getSpecifiedName());
		assertEquals("ADDRESS_PRIMARY_TABLE", joinColumn.getDefaultTable());
	}

	//target foreign key case
	public void testGetMapKeyColumnJoinColumnStrategy() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");

		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		((JoinColumnRelationship) oneToManyMapping.getRelationship()).setStrategyToJoinColumn();

		assertNull(oneToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("addresses_KEY", oneToManyMapping.getMapKeyColumn().getName());
		assertEquals("Address", oneToManyMapping.getMapKeyColumn().getTable());//target table name

		Entity addressEntity = getPersistenceUnit().getEntity("test.Address");
		addressEntity.getTable().setSpecifiedName("ADDRESS_PRIMARY_TABLE");
		assertEquals("ADDRESS_PRIMARY_TABLE", oneToManyMapping.getMapKeyColumn().getTable());

		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.addAnnotation(JPA2_0.MAP_KEY_COLUMN);
		column.setName("foo");
		getJpaProject().synchronizeContextModel();

		assertEquals("foo", oneToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("foo", oneToManyMapping.getMapKeyColumn().getName());
		assertEquals("addresses_KEY", oneToManyMapping.getMapKeyColumn().getDefaultName());
		assertEquals("ADDRESS_PRIMARY_TABLE", oneToManyMapping.getMapKeyColumn().getDefaultTable());
	}

	//target foreign key case
	public void testOrderColumnDefaultsJoinColumnStrategy() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");

		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		((JoinColumnRelationship) oneToManyMapping.getRelationship()).setStrategyToJoinColumn();
		((Orderable2_0) oneToManyMapping.getOrderable()).setOrderColumnOrdering(true);
		OrderColumn2_0 orderColumn = ((Orderable2_0) oneToManyMapping.getOrderable()).getOrderColumn();


		assertNull(orderColumn.getSpecifiedName());
		assertEquals("addresses_ORDER", orderColumn.getName());
		assertEquals("Address", orderColumn.getTable());//target table name

		Entity addressEntity = getPersistenceUnit().getEntity("test.Address");
		addressEntity.getTable().setSpecifiedName("ADDRESS_PRIMARY_TABLE");
		assertEquals("ADDRESS_PRIMARY_TABLE", orderColumn.getTable());
	}

	public void testModifyPredominantJoiningStrategy() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		OneToManyAnnotation annotation = (OneToManyAnnotation) resourceField.getAnnotation(JPA.ONE_TO_MANY);
		PersistentAttribute contextAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 mapping = (OneToManyMapping2_0) contextAttribute.getMapping();
		OneToManyRelationship2_0 rel = (OneToManyRelationship2_0) mapping.getRelationship();

		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertNull(annotation.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());

		rel.setStrategyToJoinColumn();
		assertNotNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertNull(annotation.getMappedBy());
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());

		rel.setStrategyToMappedBy();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertNotNull(annotation.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());
		assertTrue(rel.strategyIsMappedBy());
		
		rel.setStrategyToJoinTable();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertNull(annotation.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());
	}
	
	public void testUpdatePredominantJoiningStrategy() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		OneToManyAnnotation annotation = (OneToManyAnnotation) resourceField.getAnnotation(JPA.ONE_TO_MANY);
		PersistentAttribute contextAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 mapping = (OneToManyMapping2_0) contextAttribute.getMapping();
		OneToManyRelationship2_0 rel = (OneToManyRelationship2_0) mapping.getRelationship();
		
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertNull(annotation.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());
		
		annotation.setMappedBy("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertNotNull(annotation.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());
		assertTrue(rel.strategyIsMappedBy());
		
		resourceField.addAnnotation(JPA.JOIN_TABLE);
		getJpaProject().synchronizeContextModel();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertNotNull(annotation.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());
		assertTrue(rel.strategyIsMappedBy());
		
		resourceField.addAnnotation(0, JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertNotNull(annotation.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());
		assertTrue(rel.strategyIsMappedBy());
		
		annotation.setMappedBy(null);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNotNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertNull(annotation.getMappedBy());
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());
		
		resourceField.removeAnnotation(JPA.JOIN_TABLE);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(0, JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertNull(annotation.getMappedBy());
		assertTrue(rel.strategyIsJoinColumn());
		assertFalse(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());
		
		resourceField.removeAnnotation(0, JPA.JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		assertEquals(0, resourceField.getAnnotationsSize(JPA.JOIN_COLUMN));
		assertNull(resourceField.getAnnotation(JPA.JOIN_TABLE));
		assertNull(annotation.getMappedBy());
		assertFalse(rel.strategyIsJoinColumn());
		assertTrue(rel.strategyIsJoinTable());
		assertFalse(rel.strategyIsMappedBy());
	}
	
	public void testMapKeySpecifiedAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddableKeyOneToManyMapping();
		createTestEmbeddableAddress();
		createTestEmbeddableState();
		createTestEntityPropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		AttributeOverrideContainer mapKeyAttributeOverrideContainer = oneToManyMapping.getMapKeyAttributeOverrideContainer();
		
		ListIterator<? extends AttributeOverride> specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());

		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		
		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());

		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("value.BAR");
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("value.BAR", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());


		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("value.BAR", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());
	
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.BLAH");
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BLAH", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("value.BAR", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());

		//move an annotation to the resource model and verify the context model is updated
		resourceField.moveAnnotation(1, 0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("BLAH", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("value.BAR", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());

		resourceField.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BLAH", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("value.BAR", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());
	
		resourceField.removeAnnotation(1, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BLAH", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("value.BAR", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());

		
		resourceField.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("value.BAR", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());

		resourceField.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());
	}

	public void testMapKeyValueVirtualAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddableKeyOneToManyMapping();
		createTestEmbeddableAddress();
		createTestEmbeddableState();
		createTestEntityPropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		AttributeOverrideContainer mapKeyAttributeOverrideContainer = oneToManyMapping.getMapKeyAttributeOverrideContainer();

		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		assertEquals("parcels", resourceField.getName());
		assertEquals(0, resourceField.getAnnotationsSize(AttributeOverrideAnnotation.ANNOTATION_NAME));
		
		assertEquals(4, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
		ReadOnlyAttributeOverride defaultAttributeOverride = mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("city", defaultAttributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME +"_PropertyInfo", defaultAttributeOverride.getColumn().getTable());
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
		Embeddable addressEmbeddable = (Embeddable) classRefs.next().getJavaPersistentType().getMapping();
		
		BasicMapping cityMapping = (BasicMapping) addressEmbeddable.getPersistentType().getAttributeNamed("city").getMapping();
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
		
		assertEquals("parcels", resourceField.getName());
		assertEquals(0, resourceField.getAnnotationsSize(AttributeOverrideAnnotation.ANNOTATION_NAME));
		
		assertEquals(4, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
		defaultAttributeOverride = mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next();
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
		defaultAttributeOverride = mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("city", defaultAttributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME +"_PropertyInfo", defaultAttributeOverride.getColumn().getTable());
		assertEquals(null, defaultAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, defaultAttributeOverride.getColumn().isInsertable());
		assertEquals(true, defaultAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, defaultAttributeOverride.getColumn().isUnique());
		assertEquals(true, defaultAttributeOverride.getColumn().isNullable());
		assertEquals(255, defaultAttributeOverride.getColumn().getLength());
		assertEquals(0, defaultAttributeOverride.getColumn().getPrecision());
		assertEquals(0, defaultAttributeOverride.getColumn().getScale());
		
		AttributeOverrideAnnotation annotation = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		annotation.setName("key.city");
		getJpaProject().synchronizeContextModel();
		assertEquals(3, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
	}
	
	public void testMapKeyValueSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddableKeyOneToManyMapping();
		createTestEmbeddableAddress();
		createTestEmbeddableState();
		createTestEntityPropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		AttributeOverrideContainer mapKeyAttributeOverrideContainer = oneToManyMapping.getMapKeyAttributeOverrideContainer();
		assertEquals(0, mapKeyAttributeOverrideContainer.getSpecifiedOverridesSize());

		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.BAR");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("value.FOO2");
		getJpaProject().synchronizeContextModel();

		assertEquals(3, mapKeyAttributeOverrideContainer.getSpecifiedOverridesSize());
	}
	
	public void testMapKeyValueAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddableKeyOneToManyMapping();
		createTestEmbeddableAddress();
		createTestEmbeddableState();
		createTestEntityPropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");

		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		AttributeOverrideContainer mapKeyAttributeOverrideContainer = oneToManyMapping.getMapKeyAttributeOverrideContainer();
		assertEquals(4, mapKeyAttributeOverrideContainer.getOverridesSize());

		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.BAR");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("value.FOO2");
		getJpaProject().synchronizeContextModel();

		assertEquals(7, mapKeyAttributeOverrideContainer.getOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("city");
		getJpaProject().synchronizeContextModel();
		assertEquals(7, mapKeyAttributeOverrideContainer.getOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.state.foo");
		getJpaProject().synchronizeContextModel();
		assertEquals(7, mapKeyAttributeOverrideContainer.getOverridesSize());
	}
	
	public void testMapKeyValueVirtualAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddableKeyOneToManyMapping();
		createTestEmbeddableAddress();
		createTestEmbeddableState();
		createTestEntityPropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		AttributeOverrideContainer mapKeyAttributeOverrideContainer = oneToManyMapping.getMapKeyAttributeOverrideContainer();
		assertEquals(4, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());

		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(4, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());

		
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.city");
		getJpaProject().synchronizeContextModel();
		assertEquals(3, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.state.foo");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("size");
		getJpaProject().synchronizeContextModel();
		assertEquals(2, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
	}

	public void testMapKeyValueAttributeOverrideSetVirtual() throws Exception {
		createTestEntityWithEmbeddableKeyOneToManyMapping();
		createTestEmbeddableAddress();
		createTestEmbeddableState();
		createTestEntityPropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");
				
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		AttributeOverrideContainer mapKeyAttributeOverrideContainer = oneToManyMapping.getMapKeyAttributeOverrideContainer();
		mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		Iterator<NestableAnnotation> attributeOverrides = resourceField.getAnnotations(JPA.ATTRIBUTE_OVERRIDE).iterator();
		
		assertEquals("key.city", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("key.state.foo", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		attributeOverrides = resourceField.getAnnotations(JPA.ATTRIBUTE_OVERRIDE).iterator();
		assertEquals("key.state.foo", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		attributeOverrides = resourceField.getAnnotations(JPA.ATTRIBUTE_OVERRIDE).iterator();
		assertFalse(attributeOverrides.hasNext());
		
		Iterator<? extends VirtualAttributeOverride> virtualAttributeOverrides = mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator();
		assertEquals("city", virtualAttributeOverrides.next().getName());
		assertEquals("state.foo", virtualAttributeOverrides.next().getName());
		assertEquals("state.address", virtualAttributeOverrides.next().getName());
		assertEquals("zip", virtualAttributeOverrides.next().getName());
		assertEquals(4, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
	}
	
	
	public void testMapKeyValueMoveSpecifiedAttributeOverride() throws Exception {
		createTestEntityWithEmbeddableKeyOneToManyMapping();
		createTestEmbeddableAddress();
		createTestEmbeddableState();
		createTestEntityPropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		AttributeOverrideContainer mapKeyAttributeOverrideContainer = oneToManyMapping.getMapKeyAttributeOverrideContainer();
		mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		ListIterator<? extends AttributeOverride> specifiedOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("city", specifiedOverrides.next().getName());
		assertEquals("state.foo", specifiedOverrides.next().getName());
		assertFalse(specifiedOverrides.hasNext());

		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE)).getFields().iterator().next();
		
		resourceField.moveAnnotation(1, 0, AttributeOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		Iterator<NestableAnnotation> attributeOverrides = resourceField.getAnnotations(JPA.ATTRIBUTE_OVERRIDE).iterator();

		assertEquals("key.state.foo", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("key.city", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		specifiedOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("state.foo", specifiedOverrides.next().getName());
		assertEquals("city", specifiedOverrides.next().getName());
		assertFalse(specifiedOverrides.hasNext());
	}

	public void testSetSpecifiedMapKeyEnumerated() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		assertNull(oneToManyMapping.getMapKeyConverter().getType());
		
		oneToManyMapping.setMapKeyConverter(BaseEnumeratedConverter.class);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		MapKeyEnumerated2_0Annotation enumerated = (MapKeyEnumerated2_0Annotation) resourceField.getAnnotation(MapKeyEnumerated2_0Annotation.ANNOTATION_NAME);
		
		assertNotNull(enumerated);
		assertEquals(null, enumerated.getValue());
		
		((BaseEnumeratedConverter) oneToManyMapping.getMapKeyConverter()).setSpecifiedEnumType(EnumType.STRING);
		assertEquals(org.eclipse.jpt.jpa.core.resource.java.EnumType.STRING, enumerated.getValue());
		
		((BaseEnumeratedConverter) oneToManyMapping.getMapKeyConverter()).setSpecifiedEnumType(null);
		assertNotNull(resourceField.getAnnotation(MapKeyEnumerated2_0Annotation.ANNOTATION_NAME));
		assertNull(enumerated.getValue());
		
		oneToManyMapping.setMapKeyConverter(null);
		assertNull(resourceField.getAnnotation(MapKeyEnumerated2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testGetSpecifiedMapKeyEnumeratedUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();

		assertNull(oneToManyMapping.getMapKeyConverter().getType());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		MapKeyEnumerated2_0Annotation enumerated = (MapKeyEnumerated2_0Annotation) resourceField.addAnnotation(MapKeyEnumerated2_0Annotation.ANNOTATION_NAME);
		enumerated.setValue(org.eclipse.jpt.jpa.core.resource.java.EnumType.STRING);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(EnumType.STRING, ((BaseEnumeratedConverter) oneToManyMapping.getMapKeyConverter()).getSpecifiedEnumType());
		
		enumerated.setValue(null);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(MapKeyEnumerated2_0Annotation.ANNOTATION_NAME));
		assertNull(((BaseEnumeratedConverter) oneToManyMapping.getMapKeyConverter()).getSpecifiedEnumType());
		assertFalse(oneToManyMapping.isDefault());
		assertSame(oneToManyMapping, persistentAttribute.getMapping());
	}

	public void testSetMapKeyTemporal() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		assertNull(oneToManyMapping.getMapKeyConverter().getType());
		
		oneToManyMapping.setMapKeyConverter(BaseTemporalConverter.class);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		MapKeyTemporal2_0Annotation temporal = (MapKeyTemporal2_0Annotation) resourceField.getAnnotation(MapKeyTemporal2_0Annotation.ANNOTATION_NAME);
		
		assertNotNull(temporal);
		assertEquals(null, temporal.getValue());
		
		((BaseTemporalConverter) oneToManyMapping.getMapKeyConverter()).setTemporalType(TemporalType.TIME);
		assertEquals(org.eclipse.jpt.jpa.core.resource.java.TemporalType.TIME, temporal.getValue());
		
		((BaseTemporalConverter) oneToManyMapping.getMapKeyConverter()).setTemporalType(null);
		assertNull(resourceField.getAnnotation(MapKeyTemporal2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testGetMapKeyTemporalUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithValidGenericMapOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();

		assertNull(oneToManyMapping.getMapKeyConverter().getType());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		MapKeyTemporal2_0Annotation temporal = (MapKeyTemporal2_0Annotation) resourceField.addAnnotation(MapKeyTemporal2_0Annotation.ANNOTATION_NAME);
		temporal.setValue(org.eclipse.jpt.jpa.core.resource.java.TemporalType.TIME);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(TemporalType.TIME, ((BaseTemporalConverter) oneToManyMapping.getMapKeyConverter()).getTemporalType());
		
		temporal.setValue(null);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(MapKeyTemporal2_0Annotation.ANNOTATION_NAME));
		assertNull(((BaseTemporalConverter) oneToManyMapping.getMapKeyConverter()).getTemporalType());
		assertFalse(oneToManyMapping.isDefault());
		assertSame(oneToManyMapping, persistentAttribute.getMapping());
	}

	public void testSpecifiedMapKeyJoinColumns() throws Exception {
		createTestEntityWithEntityKeyOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();

		ListIterator<? extends JoinColumn> specifiedMapKeyJoinColumns = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();

		assertFalse(specifiedMapKeyJoinColumns.hasNext());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		//add an annotation to the resource model and verify the context model is updated
		MapKeyJoinColumn2_0Annotation joinColumn = (MapKeyJoinColumn2_0Annotation) resourceField.addAnnotation(0, JPA2_0.MAP_KEY_JOIN_COLUMN);
		joinColumn.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyJoinColumns = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();	
		assertEquals("FOO", specifiedMapKeyJoinColumns.next().getName());
		assertFalse(specifiedMapKeyJoinColumns.hasNext());

		joinColumn = (MapKeyJoinColumn2_0Annotation) resourceField.addAnnotation(0, JPA2_0.MAP_KEY_JOIN_COLUMN);
		joinColumn.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyJoinColumns = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();		
		assertEquals("BAR", specifiedMapKeyJoinColumns.next().getName());
		assertEquals("FOO", specifiedMapKeyJoinColumns.next().getName());
		assertFalse(specifiedMapKeyJoinColumns.hasNext());


		joinColumn = (MapKeyJoinColumn2_0Annotation) resourceField.addAnnotation(0, JPA2_0.MAP_KEY_JOIN_COLUMN);
		joinColumn.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyJoinColumns = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();		
		assertEquals("BAZ", specifiedMapKeyJoinColumns.next().getName());
		assertEquals("BAR", specifiedMapKeyJoinColumns.next().getName());
		assertEquals("FOO", specifiedMapKeyJoinColumns.next().getName());
		assertFalse(specifiedMapKeyJoinColumns.hasNext());

		//move an annotation to the resource model and verify the context model is updated
		resourceField.moveAnnotation(1, 0, JPA2_0.MAP_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyJoinColumns = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();		
		assertEquals("BAR", specifiedMapKeyJoinColumns.next().getName());
		assertEquals("BAZ", specifiedMapKeyJoinColumns.next().getName());
		assertEquals("FOO", specifiedMapKeyJoinColumns.next().getName());
		assertFalse(specifiedMapKeyJoinColumns.hasNext());

		resourceField.removeAnnotation(0, JPA2_0.MAP_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyJoinColumns = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();		
		assertEquals("BAZ", specifiedMapKeyJoinColumns.next().getName());
		assertEquals("FOO", specifiedMapKeyJoinColumns.next().getName());
		assertFalse(specifiedMapKeyJoinColumns.hasNext());

		resourceField.removeAnnotation(0, JPA2_0.MAP_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyJoinColumns = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();		
		assertEquals("FOO", specifiedMapKeyJoinColumns.next().getName());
		assertFalse(specifiedMapKeyJoinColumns.hasNext());


		resourceField.removeAnnotation(0, JPA2_0.MAP_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyJoinColumns = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();		
		assertFalse(specifiedMapKeyJoinColumns.hasNext());
	}

	public void testSpecifiedMapKeyJoinColumnsSize() throws Exception {
		createTestEntityWithEntityKeyOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();

		assertEquals(0, oneToManyMapping.getSpecifiedMapKeyJoinColumnsSize());

		oneToManyMapping.addSpecifiedMapKeyJoinColumn(0);
		assertEquals(1, oneToManyMapping.getSpecifiedMapKeyJoinColumnsSize());

		oneToManyMapping.removeSpecifiedMapKeyJoinColumn(0);
		assertEquals(0, oneToManyMapping.getSpecifiedMapKeyJoinColumnsSize());
	}

	public void testMapKeyJoinColumnsSize() throws Exception {
		createTestEntityWithEntityKeyOneToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");

		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();

		assertEquals(1, oneToManyMapping.getMapKeyJoinColumnsSize());

		oneToManyMapping.addSpecifiedMapKeyJoinColumn(0);
		assertEquals(1, oneToManyMapping.getMapKeyJoinColumnsSize());

		oneToManyMapping.addSpecifiedMapKeyJoinColumn(0);
		assertEquals(2, oneToManyMapping.getMapKeyJoinColumnsSize());

		oneToManyMapping.removeSpecifiedMapKeyJoinColumn(0);
		oneToManyMapping.removeSpecifiedMapKeyJoinColumn(0);
		assertEquals(1, oneToManyMapping.getMapKeyJoinColumnsSize());
	}

	public void testAddSpecifiedMapKeyJoinColumn() throws Exception {
		createTestEntityWithEntityKeyOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();

		oneToManyMapping.addSpecifiedMapKeyJoinColumn(0).setSpecifiedName("FOO");
		oneToManyMapping.addSpecifiedMapKeyJoinColumn(0).setSpecifiedName("BAR");
		oneToManyMapping.addSpecifiedMapKeyJoinColumn(0).setSpecifiedName("BAZ");

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		Iterator<NestableAnnotation> joinColumnsIterator = 
			resourceField.getAnnotations(JPA2_0.MAP_KEY_JOIN_COLUMN).iterator();

		assertEquals("BAZ", ((MapKeyJoinColumn2_0Annotation) joinColumnsIterator.next()).getName());
		assertEquals("BAR", ((MapKeyJoinColumn2_0Annotation) joinColumnsIterator.next()).getName());
		assertEquals("FOO", ((MapKeyJoinColumn2_0Annotation) joinColumnsIterator.next()).getName());
		assertFalse(joinColumnsIterator.hasNext());
	}

	public void testAddSpecifiedMapKeyJoinColumn2() throws Exception {
		createTestEntityWithEntityKeyOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();

		oneToManyMapping.addSpecifiedMapKeyJoinColumn(0).setSpecifiedName("FOO");
		oneToManyMapping.addSpecifiedMapKeyJoinColumn(1).setSpecifiedName("BAR");
		oneToManyMapping.addSpecifiedMapKeyJoinColumn(2).setSpecifiedName("BAZ");

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		Iterator<NestableAnnotation> joinColumnsIterator = 
			resourceField.getAnnotations(JPA2_0.MAP_KEY_JOIN_COLUMN).iterator();

		assertEquals("FOO", ((MapKeyJoinColumn2_0Annotation) joinColumnsIterator.next()).getName());
		assertEquals("BAR", ((MapKeyJoinColumn2_0Annotation) joinColumnsIterator.next()).getName());
		assertEquals("BAZ", ((MapKeyJoinColumn2_0Annotation) joinColumnsIterator.next()).getName());
		assertFalse(joinColumnsIterator.hasNext());
	}

	public void testRemoveSpecifiedMapKeyJoinColumn() throws Exception {
		createTestEntityWithEntityKeyOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();

		oneToManyMapping.addSpecifiedMapKeyJoinColumn(0).setSpecifiedName("FOO");
		oneToManyMapping.addSpecifiedMapKeyJoinColumn(1).setSpecifiedName("BAR");
		oneToManyMapping.addSpecifiedMapKeyJoinColumn(2).setSpecifiedName("BAZ");

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		assertEquals(3, resourceField.getAnnotationsSize(JPA2_0.MAP_KEY_JOIN_COLUMN));

		oneToManyMapping.removeSpecifiedMapKeyJoinColumn(1);

		Iterator<NestableAnnotation> joinColumnResources = resourceField.getAnnotations(JPA2_0.MAP_KEY_JOIN_COLUMN).iterator();
		assertEquals("FOO", ((MapKeyJoinColumn2_0Annotation) joinColumnResources.next()).getName());		
		assertEquals("BAZ", ((MapKeyJoinColumn2_0Annotation) joinColumnResources.next()).getName());
		assertFalse(joinColumnResources.hasNext());

		Iterator<? extends JoinColumn> joinColumnsIterator = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("FOO", joinColumnsIterator.next().getName());		
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());


		oneToManyMapping.removeSpecifiedMapKeyJoinColumn(1);
		joinColumnResources = resourceField.getAnnotations(JPA2_0.MAP_KEY_JOIN_COLUMN).iterator();
		assertEquals("FOO", ((MapKeyJoinColumn2_0Annotation) joinColumnResources.next()).getName());		
		assertFalse(joinColumnResources.hasNext());

		joinColumnsIterator = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("FOO", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());


		oneToManyMapping.removeSpecifiedMapKeyJoinColumn(0);
		joinColumnResources = resourceField.getAnnotations(JPA2_0.MAP_KEY_JOIN_COLUMN).iterator();
		assertFalse(joinColumnResources.hasNext());
		joinColumnsIterator = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertFalse(joinColumnsIterator.hasNext());
		assertEquals(0, resourceField.getAnnotationsSize(MapKeyJoinColumn2_0Annotation.ANNOTATION_NAME));
	}

	public void testMoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithEntityKeyOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();

		oneToManyMapping.addSpecifiedMapKeyJoinColumn(0).setSpecifiedName("FOO");
		oneToManyMapping.addSpecifiedMapKeyJoinColumn(1).setSpecifiedName("BAR");
		oneToManyMapping.addSpecifiedMapKeyJoinColumn(2).setSpecifiedName("BAZ");

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		Iterator<NestableAnnotation> javaJoinColumns = resourceField.getAnnotations(JPA2_0.MAP_KEY_JOIN_COLUMN).iterator();
		assertEquals(3, IteratorTools.size(javaJoinColumns));


		oneToManyMapping.moveSpecifiedMapKeyJoinColumn(2, 0);
		ListIterator<? extends JoinColumn> joinColumns = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getSpecifiedName());
		assertEquals("BAZ", joinColumns.next().getSpecifiedName());
		assertEquals("FOO", joinColumns.next().getSpecifiedName());

		javaJoinColumns = resourceField.getAnnotations(JPA2_0.MAP_KEY_JOIN_COLUMN).iterator();
		assertEquals("BAR", ((MapKeyJoinColumn2_0Annotation) javaJoinColumns.next()).getName());
		assertEquals("BAZ", ((MapKeyJoinColumn2_0Annotation) javaJoinColumns.next()).getName());
		assertEquals("FOO", ((MapKeyJoinColumn2_0Annotation) javaJoinColumns.next()).getName());


		oneToManyMapping.moveSpecifiedMapKeyJoinColumn(0, 1);
		joinColumns = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getSpecifiedName());
		assertEquals("BAR", joinColumns.next().getSpecifiedName());
		assertEquals("FOO", joinColumns.next().getSpecifiedName());

		javaJoinColumns = resourceField.getAnnotations(JPA2_0.MAP_KEY_JOIN_COLUMN).iterator();
		assertEquals("BAZ", ((MapKeyJoinColumn2_0Annotation) javaJoinColumns.next()).getName());
		assertEquals("BAR", ((MapKeyJoinColumn2_0Annotation) javaJoinColumns.next()).getName());
		assertEquals("FOO", ((MapKeyJoinColumn2_0Annotation) javaJoinColumns.next()).getName());
	}

	public void testUpdateSpecifiedMapKeyJoinColumns() throws Exception {
		createTestEntityWithEntityKeyOneToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		((MapKeyJoinColumn2_0Annotation) resourceField.addAnnotation(0, JPA2_0.MAP_KEY_JOIN_COLUMN)).setName("FOO");
		((MapKeyJoinColumn2_0Annotation) resourceField.addAnnotation(1, JPA2_0.MAP_KEY_JOIN_COLUMN)).setName("BAR");
		((MapKeyJoinColumn2_0Annotation) resourceField.addAnnotation(2, JPA2_0.MAP_KEY_JOIN_COLUMN)).setName("BAZ");
		getJpaProject().synchronizeContextModel();

		ListIterator<? extends JoinColumn> joinColumnsIterator = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("FOO", joinColumnsIterator.next().getName());
		assertEquals("BAR", joinColumnsIterator.next().getName());
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());

		resourceField.moveAnnotation(2, 0, MapKeyJoinColumn2_0Annotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		joinColumnsIterator = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAR", joinColumnsIterator.next().getName());
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertEquals("FOO", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());

		resourceField.moveAnnotation(0, 1, MapKeyJoinColumn2_0Annotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		joinColumnsIterator = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertEquals("BAR", joinColumnsIterator.next().getName());
		assertEquals("FOO", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());

		resourceField.removeAnnotation(1, JPA2_0.MAP_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		joinColumnsIterator = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertEquals("FOO", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());

		resourceField.removeAnnotation(1, JPA2_0.MAP_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		joinColumnsIterator = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertEquals("BAZ", joinColumnsIterator.next().getName());
		assertFalse(joinColumnsIterator.hasNext());

		resourceField.removeAnnotation(0, JPA2_0.MAP_KEY_JOIN_COLUMN);
		getJpaProject().synchronizeContextModel();
		joinColumnsIterator = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator();
		assertFalse(joinColumnsIterator.hasNext());
	}

	public void testMapKeyJoinColumnIsDefault() throws Exception {
		createTestEntityWithEntityKeyOneToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");

		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();

		assertTrue(oneToManyMapping.getDefaultMapKeyJoinColumn().isVirtual());

		oneToManyMapping.addSpecifiedMapKeyJoinColumn(0);
		JoinColumn specifiedJoinColumn = oneToManyMapping.getSpecifiedMapKeyJoinColumns().iterator().next();
		assertFalse(specifiedJoinColumn.isVirtual());

		assertNull(oneToManyMapping.getDefaultMapKeyJoinColumn());
	}

}

/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_0.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.context.VirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ElementCollection2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyClass2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyColumn2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.ManyToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_0.context.EclipseLink2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLink2_0JavaElementCollectionMappingTests
	extends EclipseLink2_0ContextModelTestCase
{
	public static final String EMBEDDABLE_TYPE_NAME = "Address";
	public static final String FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME = PACKAGE_NAME + "." + EMBEDDABLE_TYPE_NAME;

	private ICompilationUnit createTestEntityWithElementCollectionMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA2_0.ELEMENT_COLLECTION);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ElementCollection").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithGenericEmbeddableElementCollectionMapping() throws Exception {
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
				sb.append("    private java.util.Collection<Address> addresses;").append(CR);			
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

	private ICompilationUnit createTestEntityWithValidGenericMapElementCollectionMapping() throws Exception {
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
				sb.append("    private java.util.Map<Integer, Address> addresses;").append(CR);			
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
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

	private ICompilationUnit createTestEntityWithEmbeddableKeyAndValueElementCollectionMapping() throws Exception {
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
				sb.append("    private java.util.Map<Address, PropertyInfo> parcels;").append(CR);			
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}

	private void createTestEmbeddablePropertyInfo() throws Exception {
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
				sb.append("public class ").append("PropertyInfo").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    private Integer parcelNumber;").append(CR);
				sb.append(CR);
				sb.append("    private Integer size;").append(CR);
				sb.append(CR);
				sb.append("    private java.math.BigDecimal tax;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "PropertyInfo.java", sourceWriter);
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

	public EclipseLink2_0JavaElementCollectionMappingTests(String name) {
		super(name);
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof BasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof VersionMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(VersionAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TransientAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
	}
	
	public void testUpdateSpecifiedTargetEntity() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ElementCollection2_0Annotation elementCollectionAnnotation = (ElementCollection2_0Annotation) resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME);
		
		assertNull(elementCollectionMapping.getSpecifiedTargetClass());
		assertNull(elementCollectionAnnotation.getTargetClass());
				
		//set target class in the resource model, verify context model updated
		elementCollectionAnnotation.setTargetClass("newTargetClass");
		this.getJpaProject().synchronizeContextModel();
		assertEquals("newTargetClass", elementCollectionMapping.getSpecifiedTargetClass());
		assertEquals("newTargetClass", elementCollectionAnnotation.getTargetClass());
	
		//set target class to null in the resource model
		elementCollectionAnnotation.setTargetClass(null);
		this.getJpaProject().synchronizeContextModel();
		assertNull(elementCollectionMapping.getSpecifiedTargetClass());
		assertNull(elementCollectionAnnotation.getTargetClass());
	}
	
	public void testModifySpecifiedTargetClass() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ElementCollection2_0Annotation elementCollection = (ElementCollection2_0Annotation) resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME);
		
		assertNull(elementCollectionMapping.getSpecifiedTargetClass());
		assertNull(elementCollection.getTargetClass());
				
		//set target class in the context model, verify resource model updated
		elementCollectionMapping.setSpecifiedTargetClass("newTargetClass");
		assertEquals("newTargetClass", elementCollectionMapping.getSpecifiedTargetClass());
		assertEquals("newTargetClass", elementCollection.getTargetClass());
	
		//set target class to null in the context model
		elementCollectionMapping.setSpecifiedTargetClass(null);
		assertNull(elementCollectionMapping.getSpecifiedTargetClass());
		assertNull(elementCollection.getTargetClass());
	}
	
	public void testDefaultTargetClass() throws Exception {
		createTestEntityWithGenericEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		//targetEntity not in the persistence unit, default still set, handled by validation
		assertEquals(PACKAGE_NAME + ".Address", elementCollectionMapping.getDefaultTargetClass());
		
		//add targetEntity to the persistence unit
		addXmlClassRef(PACKAGE_NAME + ".Address");
		assertEquals(PACKAGE_NAME + ".Address", elementCollectionMapping.getDefaultTargetClass());

		//test default still the same when specified target entity it set
		elementCollectionMapping.setSpecifiedTargetClass("foo");
		assertEquals(PACKAGE_NAME + ".Address", elementCollectionMapping.getDefaultTargetClass());
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		ClassRef addressClassRef = classRefs.next();
		JavaPersistentType addressPersistentType = addressClassRef.getJavaPersistentType();

		//test target is not an Embeddable, default target entity still exists, this case handled with validation
		addressPersistentType.setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
		assertEquals(PACKAGE_NAME + ".Address", elementCollectionMapping.getDefaultTargetClass());
	}
	
	public void testDefaultTargetClassNonGenericCollection() throws Exception {
		createTestEntityWithNonGenericElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		assertNull(elementCollectionMapping.getDefaultTargetClass());
	}
	
	public void testDefaultTargetClassGenericCollection() throws Exception {
		createTestEntityWithGenericEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		assertEquals(PACKAGE_NAME + ".Address", elementCollectionMapping.getDefaultTargetClass());
	}

	public void testDefaultTargetClassNonGenericMap() throws Exception {
		createTestEntityWithValidNonGenericMapElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		assertNull(elementCollectionMapping.getDefaultTargetClass());
	}
	
	public void testDefaultTargetClassGenericMap() throws Exception {
		createTestEntityWithValidGenericMapElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		assertEquals(PACKAGE_NAME + ".Address", elementCollectionMapping.getDefaultTargetClass());
	}
	
	public void testTargetClass() throws Exception {
		createTestEntityWithGenericEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		assertEquals(PACKAGE_NAME + ".Address", elementCollectionMapping.getTargetClass());

		elementCollectionMapping.setSpecifiedTargetClass("foo");
		assertEquals("foo", elementCollectionMapping.getTargetClass());
		
		elementCollectionMapping.setSpecifiedTargetClass(null);
		assertEquals(PACKAGE_NAME + ".Address", elementCollectionMapping.getTargetClass());
	}

	protected Embeddable getResolvedTargetEmbeddable(ElementCollectionMapping2_0 mapping) {
		return (Embeddable) ReflectionTools.executeMethod(mapping, "getResolvedTargetEmbeddable");
	}
	
	public void testResolvedTargetEmbeddable() throws Exception {
		createTestEntityWithGenericEmbeddableElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		//target embeddable not in the persistence unit
		assertNull(this.getResolvedTargetEmbeddable(elementCollectionMapping));
		
		//add target embeddable to the persistence unit, now target embeddable should resolve
		createTestTargetEmbeddableAddress();
		addXmlClassRef(PACKAGE_NAME + ".Address");
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		ClassRef addressClassRef = classRefs.next();
		TypeMapping addressTypeMapping = addressClassRef.getJavaPersistentType().getMapping();
		assertEquals(addressTypeMapping, this.getResolvedTargetEmbeddable(elementCollectionMapping));

		//test default still the same when specified target entity it set
		elementCollectionMapping.setSpecifiedTargetClass("foo");
		assertNull(this.getResolvedTargetEmbeddable(elementCollectionMapping));
		
		
		elementCollectionMapping.setSpecifiedTargetClass(PACKAGE_NAME + ".Address");
		assertEquals(addressTypeMapping, this.getResolvedTargetEmbeddable(elementCollectionMapping));
		

		elementCollectionMapping.setSpecifiedTargetClass(null);
		assertEquals(addressTypeMapping, this.getResolvedTargetEmbeddable(elementCollectionMapping));
	}
	
	public void testResolvedTargetEmbeddableWithBasicType() throws Exception {
		createTestEntityWithGenericBasicElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		//target is a basic type, so resolved target embeddable is null
		assertNull(this.getResolvedTargetEmbeddable(elementCollectionMapping));
	}

	public void testUpdateSpecifiedFetch() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ElementCollection2_0Annotation elementCollection = (ElementCollection2_0Annotation) resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME);
		
		assertNull(elementCollectionMapping.getSpecifiedFetch());
		assertNull(elementCollection.getFetch());
				
		//set fetch in the resource model, verify context model updated
		elementCollection.setFetch(org.eclipse.jpt.jpa.core.resource.java.FetchType.EAGER);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(FetchType.EAGER, elementCollectionMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.jpa.core.resource.java.FetchType.EAGER, elementCollection.getFetch());
		
		elementCollection.setFetch(org.eclipse.jpt.jpa.core.resource.java.FetchType.LAZY);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(FetchType.LAZY, elementCollectionMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.jpa.core.resource.java.FetchType.LAZY, elementCollection.getFetch());
		
		//set fetch to null in the resource model
		elementCollection.setFetch(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(elementCollectionMapping.getSpecifiedFetch());
		assertNull(elementCollection.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ElementCollection2_0Annotation elementCollection = (ElementCollection2_0Annotation) resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME);
		
		assertNull(elementCollectionMapping.getSpecifiedFetch());
		assertNull(elementCollection.getFetch());
				
		//set fetch in the context model, verify resource model updated
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(FetchType.EAGER, elementCollectionMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.jpa.core.resource.java.FetchType.EAGER, elementCollection.getFetch());
	
		elementCollectionMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, elementCollectionMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.jpa.core.resource.java.FetchType.LAZY, elementCollection.getFetch());
		
		//set fetch to null in the context model
		elementCollectionMapping.setSpecifiedFetch(null);
		assertNull(elementCollectionMapping.getSpecifiedFetch());
		assertNull(elementCollection.getFetch());
	}
	
	public void testGetValueTypeEmbeddable() throws Exception {
		createTestEntityWithGenericEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		assertEquals(ElementCollectionMapping2_0.Type.EMBEDDABLE_TYPE, elementCollectionMapping.getValueType());
	}
	
	public void testGetValueTypeNone() throws Exception {
		createTestEntityWithNonGenericElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		assertEquals(ElementCollectionMapping2_0.Type.NO_TYPE, elementCollectionMapping.getValueType());
		
		elementCollectionMapping.setSpecifiedTargetClass("Address");
		assertEquals(ElementCollectionMapping2_0.Type.EMBEDDABLE_TYPE, elementCollectionMapping.getValueType());
	}
	
	public void testGetValueTypeBasic() throws Exception {
		createTestEntityWithGenericBasicElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		assertEquals(ElementCollectionMapping2_0.Type.BASIC_TYPE, elementCollectionMapping.getValueType());
	}
	
	public void testUpdateMapKey() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		assertNull(elementCollectionMapping.getSpecifiedMapKey());
		assertNull(resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME));
		
		//set mapKey in the resource model, verify context model does not change
		resourceField.addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		assertNull(elementCollectionMapping.getSpecifiedMapKey());
		MapKeyAnnotation mapKey = (MapKeyAnnotation) resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertNotNull(mapKey);
				
		//set mapKey name in the resource model, verify context model updated
		mapKey.setName("myMapKey");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("myMapKey", elementCollectionMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", mapKey.getName());
		
		//set mapKey name to null in the resource model
		mapKey.setName(null);
		getJpaProject().synchronizeContextModel();
		assertNull(elementCollectionMapping.getSpecifiedMapKey());
		assertNull(mapKey.getName());
		
		mapKey.setName("myMapKey");
		resourceField.removeAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertNull(elementCollectionMapping.getSpecifiedMapKey());
		assertNull(resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME));
	}
	
	public void testModifyMapKey() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		assertNull(elementCollectionMapping.getSpecifiedMapKey());
		assertNull(resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME));
					
		//set mapKey  in the context model, verify resource model updated
		elementCollectionMapping.setSpecifiedMapKey("myMapKey");
		MapKeyAnnotation mapKeyAnnotation = (MapKeyAnnotation) resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertEquals("myMapKey", elementCollectionMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", mapKeyAnnotation.getName());
	
		//set mapKey to null in the context model
		elementCollectionMapping.setSpecifiedMapKey(null);
		assertNull(elementCollectionMapping.getSpecifiedMapKey());
		mapKeyAnnotation = (MapKeyAnnotation) resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertNull(mapKeyAnnotation.getName());
	}
	
	public void testCandidateMapKeyNames() throws Exception {
		createTestEntityWithValidGenericMapElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping2_0 = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		Iterator<String> mapKeyNames = 
			elementCollectionMapping2_0.getCandidateMapKeyNames().iterator();
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
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping2_0 = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		Iterator<String> mapKeyNames = elementCollectionMapping2_0.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
		
		elementCollectionMapping2_0.setSpecifiedTargetClass("Address");
		mapKeyNames = elementCollectionMapping2_0.getCandidateMapKeyNames().iterator();
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.name", mapKeyNames.next());
		assertEquals("state.abbr", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		elementCollectionMapping2_0.setSpecifiedTargetClass("String");
		mapKeyNames = elementCollectionMapping2_0.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
	}
	
	public void testUpdateMapKeyClass() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		assertNull(elementCollectionMapping.getSpecifiedMapKeyClass());
		assertNull(resourceField.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME));
		
		//set mapKey in the resource model, verify context model does not change
		resourceField.addAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
		assertNull(elementCollectionMapping.getSpecifiedMapKeyClass());
		MapKeyClass2_0Annotation mapKeyClassAnnotation = (MapKeyClass2_0Annotation) resourceField.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
		assertNotNull(mapKeyClassAnnotation);
				
		//set mapKey name in the resource model, verify context model updated
		mapKeyClassAnnotation.setValue("myMapKeyClass");
		this.getJpaProject().synchronizeContextModel();
		assertEquals("myMapKeyClass", elementCollectionMapping.getSpecifiedMapKeyClass());
		assertEquals("myMapKeyClass", mapKeyClassAnnotation.getValue());
		
		//set mapKey name to null in the resource model
		mapKeyClassAnnotation.setValue(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(elementCollectionMapping.getSpecifiedMapKeyClass());
		assertNull(mapKeyClassAnnotation.getValue());
		
		mapKeyClassAnnotation.setValue("myMapKeyClass");
		resourceField.removeAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		assertNull(elementCollectionMapping.getSpecifiedMapKeyClass());
		assertNull(resourceField.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testModifyMapKeyClass() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		assertNull(elementCollectionMapping.getSpecifiedMapKeyClass());
		assertNull(resourceField.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME));
					
		//set mapKey  in the context model, verify resource model updated
		elementCollectionMapping.setSpecifiedMapKeyClass("String");
		MapKeyClass2_0Annotation mapKeyClass = (MapKeyClass2_0Annotation) resourceField.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
		assertEquals("String", elementCollectionMapping.getSpecifiedMapKeyClass());
		assertEquals("String", mapKeyClass.getValue());
	
		//set mapKey to null in the context model
		elementCollectionMapping.setSpecifiedMapKeyClass(null);
		assertNull(elementCollectionMapping.getSpecifiedMapKeyClass());
		assertNull(resourceField.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME));
	}

	public void testDefaultMapKeyClass() throws Exception {
		createTestEntityWithValidGenericMapElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		assertEquals("java.lang.Integer", elementCollectionMapping.getDefaultMapKeyClass());

		//test default still the same when specified target entity it set
		elementCollectionMapping.setSpecifiedMapKeyClass("foo");
		assertEquals("java.lang.Integer", elementCollectionMapping.getDefaultMapKeyClass());
	}
	
	public void testDefaultMapKeyClassCollectionType() throws Exception {
		createTestEntityWithGenericBasicElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		assertNull(elementCollectionMapping.getDefaultMapKeyClass());
	}
	
	public void testMapKeyClass() throws Exception {
		createTestEntityWithValidGenericMapElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		assertEquals("java.lang.Integer", elementCollectionMapping.getMapKeyClass());

		elementCollectionMapping.setSpecifiedMapKeyClass("foo");
		assertEquals("foo", elementCollectionMapping.getMapKeyClass());
		
		elementCollectionMapping.setSpecifiedMapKeyClass(null);
		assertEquals("java.lang.Integer", elementCollectionMapping.getMapKeyClass());
	}

	public void testOrderColumnDefaults() throws Exception {
		createTestEntityWithGenericEmbeddableElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		Orderable2_0 orderable = ((Orderable2_0) elementCollectionMapping.getOrderable());
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
	
	public void testGetValueColumn() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		
		assertNull(elementCollectionMapping.getValueColumn().getSpecifiedName());
		assertEquals("id", elementCollectionMapping.getValueColumn().getName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation column = (ColumnAnnotation) resourceField.addAnnotation(JPA.COLUMN);
		column.setName("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("foo", elementCollectionMapping.getValueColumn().getSpecifiedName());
		assertEquals("foo", elementCollectionMapping.getValueColumn().getName());
		assertEquals("id", elementCollectionMapping.getValueColumn().getDefaultName());
	}
	
	public void testSpecifiedAttributeOverrides() throws Exception {
		createTestEntityWithGenericEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		
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
		createTestEntityWithGenericEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		AttributeOverrideContainer attributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertEquals("addresses", resourceField.getName());
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		
		assertEquals(4, attributeOverrideContainer.getVirtualOverridesSize());
		ReadOnlyAttributeOverride defaultAttributeOverride = attributeOverrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("city", defaultAttributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME +"_addresses", defaultAttributeOverride.getColumn().getTable());
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
		
		assertEquals("addresses", resourceField.getName());
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));

		assertEquals(4, attributeOverrideContainer.getVirtualOverridesSize());
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
		assertEquals(TYPE_NAME +"_addresses", defaultAttributeOverride.getColumn().getTable());
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
		assertEquals(3, attributeOverrideContainer.getVirtualOverridesSize());
	}
	
	public void testSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntityWithGenericEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		AttributeOverrideContainer attributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
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
		createTestEntityWithGenericEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		AttributeOverrideContainer attributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		assertEquals(4, attributeOverrideContainer.getOverridesSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(6, attributeOverrideContainer.getOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("city");
		getJpaProject().synchronizeContextModel();
		assertEquals(6, attributeOverrideContainer.getOverridesSize());	
	}
	
	public void testVirtualAttributeOverridesSize() throws Exception {
		createTestEntityWithGenericEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		AttributeOverrideContainer attributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		assertEquals(4, attributeOverrideContainer.getVirtualOverridesSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		getJpaProject().synchronizeContextModel();

		assertEquals(4, attributeOverrideContainer.getVirtualOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("city");
		getJpaProject().synchronizeContextModel();
		assertEquals(3, attributeOverrideContainer.getVirtualOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("state.name");
		getJpaProject().synchronizeContextModel();
		assertEquals(2, attributeOverrideContainer.getVirtualOverridesSize());
	}

	public void testAttributeOverrideSetVirtual() throws Exception {
		createTestEntityWithGenericEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".State");
				
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		AttributeOverrideContainer attributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		attributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		attributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		Iterator<NestableAnnotation> attributeOverrides = resourceField.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		
		assertEquals("city", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("state.name", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		attributeOverrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		attributeOverrides = resourceField.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("state.name", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		assertEquals("city", attributeOverrideContainer.getVirtualOverrides().iterator().next().getName());
		assertEquals(3, attributeOverrideContainer.getVirtualOverridesSize());
		
		attributeOverrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		attributeOverrides = resourceField.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertFalse(attributeOverrides.hasNext());
		
		Iterator<? extends VirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		assertEquals("city", virtualAttributeOverrides.next().getName());
		assertEquals("state.name", virtualAttributeOverrides.next().getName());
		assertEquals("state.abbr", virtualAttributeOverrides.next().getName());
		assertEquals("zip", virtualAttributeOverrides.next().getName());
		assertEquals(4, attributeOverrideContainer.getVirtualOverridesSize());
	}
	
	public void testAttributeOverrideSetVirtual2() throws Exception {
		createTestEntityWithGenericEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		AttributeOverrideContainer attributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		ListIterator<? extends VirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		virtualAttributeOverrides.next();	
		virtualAttributeOverrides.next().convertToSpecified();
		attributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		Iterator<NestableAnnotation> attributeOverrides = resourceField.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		
		assertEquals("state.name", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("city", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		createTestEntityWithGenericEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("addresses").getMapping();
		AttributeOverrideContainer attributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		attributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		attributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		resourceField.moveAnnotation(1, 0, AttributeOverrideAnnotation.ANNOTATION_NAME);
		
		Iterator<NestableAnnotation> attributeOverrides = resourceField.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();

		assertEquals("state.name", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("city", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testNestedVirtualAttributeOverrides() throws Exception {
		createTestEntityWithGenericEmbeddableElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".State");

		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		PersistentType persistentType = specifiedClassRefs.next().getJavaPersistentType();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentType.getAttributeNamed("addresses").getMapping();
		AttributeOverrideContainer attributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		
		assertEquals(4, attributeOverrideContainer.getVirtualOverridesSize());
		ListIterator<? extends VirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		ReadOnlyAttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("city", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("state.name", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("state.abbr", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("zip", virtualAttributeOverride.getName());
		assertEquals(false, virtualAttributeOverrides.hasNext());

		
		PersistentType addressPersistentType = specifiedClassRefs.next().getJavaPersistentType();
		EmbeddedMapping nestedEmbeddedMapping = (EmbeddedMapping) addressPersistentType.getAttributeNamed("state").getMapping();
		AttributeOverrideContainer nestedAttributeOverrideContainer = nestedEmbeddedMapping.getAttributeOverrideContainer();
		assertEquals(2, nestedAttributeOverrideContainer.getVirtualOverridesSize());
		virtualAttributeOverrides = nestedAttributeOverrideContainer.getVirtualOverrides().iterator();
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("abbr", virtualAttributeOverride.getName());
		
		PersistentType statePersistentType = specifiedClassRefs.next().getJavaPersistentType();
		BasicMapping abbrMapping = (BasicMapping) statePersistentType.getAttributeNamed("abbr").getMapping();
		abbrMapping.getColumn().setSpecifiedName("BLAH");
		abbrMapping.getColumn().setSpecifiedTable("BLAH_TABLE");
		abbrMapping.getColumn().setColumnDefinition("COLUMN_DEFINITION");
		abbrMapping.getColumn().setSpecifiedInsertable(Boolean.FALSE);
		abbrMapping.getColumn().setSpecifiedUpdatable(Boolean.FALSE);
		abbrMapping.getColumn().setSpecifiedUnique(Boolean.TRUE);
		abbrMapping.getColumn().setSpecifiedNullable(Boolean.FALSE);
		abbrMapping.getColumn().setSpecifiedLength(Integer.valueOf(5));
		abbrMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(6));
		abbrMapping.getColumn().setSpecifiedScale(Integer.valueOf(7));

		//check the nested embedded (Address.state) attribute override to verify it is getting settings from the specified column on State.abbr
		virtualAttributeOverride = ((EmbeddedMapping) addressPersistentType.getAttributeNamed("state").getMapping()).getAttributeOverrideContainer().getOverrideNamed("abbr");
		assertEquals("abbr", virtualAttributeOverride.getName());
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
	}

	public void testGetMapKeyColumn() throws Exception {
		createTestEntityWithValidGenericMapElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();

		assertNull(elementCollectionMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("addresses_KEY", elementCollectionMapping.getMapKeyColumn().getName());
		assertEquals(TYPE_NAME + "_addresses", elementCollectionMapping.getMapKeyColumn().getTable());//collection table name

		elementCollectionMapping.getCollectionTable().setSpecifiedName("MY_COLLECTION_TABLE");
		assertEquals("MY_COLLECTION_TABLE", elementCollectionMapping.getMapKeyColumn().getTable());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) resourceField.addAnnotation(JPA2_0.MAP_KEY_COLUMN);
		column.setName("foo");
		getJpaProject().synchronizeContextModel();

		assertEquals("foo", elementCollectionMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("foo", elementCollectionMapping.getMapKeyColumn().getName());
		assertEquals("addresses_KEY", elementCollectionMapping.getMapKeyColumn().getDefaultName());
	}

	public void testMapKeyValueSpecifiedAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddableKeyAndValueElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddablePropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		JavaAttributeOverrideContainer mapKeyAttributeOverrideContainer = elementCollectionMapping.getMapKeyAttributeOverrideContainer();
		
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
		attributeOverride.setName("value.BAR");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());


		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
		ListIterator<JavaAttributeOverride> specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());
	
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.BLAH");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BLAH", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());

		//move an annotation to the resource model and verify the context model is updated
		resourceField.moveAnnotation(1, 0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("BLAH", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());

		resourceField.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BLAH", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());
	
		resourceField.removeAnnotation(1, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BLAH", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());

		
		resourceField.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());

		resourceField.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertFalse(specifiedAttributeOverrides.hasNext());
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());
	}

	public void testMapKeyValueVirtualAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddableKeyAndValueElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		createTestEmbeddablePropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		AttributeOverrideContainer attributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		AttributeOverrideContainer mapKeyAttributeOverrideContainer = elementCollectionMapping.getMapKeyAttributeOverrideContainer();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertEquals("parcels", resourceField.getName());
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		
		assertEquals(4, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
		ReadOnlyAttributeOverride defaultAttributeOverride = mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("city", defaultAttributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME +"_parcels", defaultAttributeOverride.getColumn().getTable());
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
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));

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
		assertEquals(TYPE_NAME +"_parcels", defaultAttributeOverride.getColumn().getTable());
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
	
		
		
		assertEquals(3, attributeOverrideContainer.getVirtualOverridesSize());
		defaultAttributeOverride = attributeOverrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("parcelNumber", defaultAttributeOverride.getName());
		assertEquals("parcelNumber", defaultAttributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME +"_parcels", defaultAttributeOverride.getColumn().getTable());
		assertEquals(null, defaultAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, defaultAttributeOverride.getColumn().isInsertable());
		assertEquals(true, defaultAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, defaultAttributeOverride.getColumn().isUnique());
		assertEquals(true, defaultAttributeOverride.getColumn().isNullable());
		assertEquals(255, defaultAttributeOverride.getColumn().getLength());
		assertEquals(0, defaultAttributeOverride.getColumn().getPrecision());
		assertEquals(0, defaultAttributeOverride.getColumn().getScale());
		
		
		classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		classRefs.next();
		Embeddable propertyInfoEmbeddable = (Embeddable) classRefs.next().getJavaPersistentType().getMapping();
		
		BasicMapping parcelNumberMapping = (BasicMapping) propertyInfoEmbeddable.getPersistentType().getAttributeNamed("parcelNumber").getMapping();
		parcelNumberMapping.getColumn().setSpecifiedName("FOO1");
		parcelNumberMapping.getColumn().setSpecifiedTable("BAR1");
		parcelNumberMapping.getColumn().setColumnDefinition("COLUMN_DEF1");
		parcelNumberMapping.getColumn().setSpecifiedInsertable(Boolean.FALSE);
		parcelNumberMapping.getColumn().setSpecifiedUpdatable(Boolean.FALSE);
		parcelNumberMapping.getColumn().setSpecifiedUnique(Boolean.TRUE);
		parcelNumberMapping.getColumn().setSpecifiedNullable(Boolean.FALSE);
		parcelNumberMapping.getColumn().setSpecifiedLength(Integer.valueOf(5));
		parcelNumberMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(6));
		parcelNumberMapping.getColumn().setSpecifiedScale(Integer.valueOf(7));
		
		assertEquals("parcels", resourceField.getName());

		assertEquals(3, attributeOverrideContainer.getVirtualOverridesSize());
		defaultAttributeOverride = attributeOverrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("parcelNumber", defaultAttributeOverride.getName());
		assertEquals("FOO1", defaultAttributeOverride.getColumn().getName());
		assertEquals("BAR1", defaultAttributeOverride.getColumn().getTable());
		assertEquals("COLUMN_DEF1", defaultAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(false, defaultAttributeOverride.getColumn().isInsertable());
		assertEquals(false, defaultAttributeOverride.getColumn().isUpdatable());
		assertEquals(true, defaultAttributeOverride.getColumn().isUnique());
		assertEquals(false, defaultAttributeOverride.getColumn().isNullable());
		assertEquals(5, defaultAttributeOverride.getColumn().getLength());
		assertEquals(6, defaultAttributeOverride.getColumn().getPrecision());
		assertEquals(7, defaultAttributeOverride.getColumn().getScale());

		parcelNumberMapping.getColumn().setSpecifiedName(null);
		parcelNumberMapping.getColumn().setSpecifiedTable(null);
		parcelNumberMapping.getColumn().setColumnDefinition(null);
		parcelNumberMapping.getColumn().setSpecifiedInsertable(null);
		parcelNumberMapping.getColumn().setSpecifiedUpdatable(null);
		parcelNumberMapping.getColumn().setSpecifiedUnique(null);
		parcelNumberMapping.getColumn().setSpecifiedNullable(null);
		parcelNumberMapping.getColumn().setSpecifiedLength(null);
		parcelNumberMapping.getColumn().setSpecifiedPrecision(null);
		parcelNumberMapping.getColumn().setSpecifiedScale(null);
		defaultAttributeOverride = attributeOverrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("parcelNumber", defaultAttributeOverride.getName());
		assertEquals("parcelNumber", defaultAttributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME +"_parcels", defaultAttributeOverride.getColumn().getTable());
		assertEquals(null, defaultAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, defaultAttributeOverride.getColumn().isInsertable());
		assertEquals(true, defaultAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, defaultAttributeOverride.getColumn().isUnique());
		assertEquals(true, defaultAttributeOverride.getColumn().isNullable());
		assertEquals(255, defaultAttributeOverride.getColumn().getLength());
		assertEquals(0, defaultAttributeOverride.getColumn().getPrecision());
		assertEquals(0, defaultAttributeOverride.getColumn().getScale());
		
		annotation = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		annotation.setName("value.parcelNumber");
		getJpaProject().synchronizeContextModel();
		assertEquals(2, attributeOverrideContainer.getVirtualOverridesSize());
	}
	
	public void testMapKeyValueSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddableKeyAndValueElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		createTestEmbeddablePropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		AttributeOverrideContainer valueAttributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		AttributeOverrideContainer mapKeyAttributeOverrideContainer = elementCollectionMapping.getMapKeyAttributeOverrideContainer();
		assertEquals(0, valueAttributeOverrideContainer.getSpecifiedOverridesSize());
		assertEquals(0, mapKeyAttributeOverrideContainer.getSpecifiedOverridesSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.BAR");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("value.FOO2");
		getJpaProject().synchronizeContextModel();

		assertEquals(2, valueAttributeOverrideContainer.getSpecifiedOverridesSize());
		assertEquals(1, mapKeyAttributeOverrideContainer.getSpecifiedOverridesSize());
	}
	
	public void testMapKeyValueAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddableKeyAndValueElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		createTestEmbeddablePropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");

		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		AttributeOverrideContainer valueAttributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		AttributeOverrideContainer mapKeyAttributeOverrideContainer = elementCollectionMapping.getMapKeyAttributeOverrideContainer();
		assertEquals(4, mapKeyAttributeOverrideContainer.getOverridesSize());
		assertEquals(3, valueAttributeOverrideContainer.getOverridesSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.BAR");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("value.FOO2");
		getJpaProject().synchronizeContextModel();

		assertEquals(5, mapKeyAttributeOverrideContainer.getOverridesSize());
		assertEquals(5, valueAttributeOverrideContainer.getOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("city");
		getJpaProject().synchronizeContextModel();
		assertEquals(5, mapKeyAttributeOverrideContainer.getOverridesSize());
		assertEquals(6, valueAttributeOverrideContainer.getOverridesSize());
	}
	
	public void testMapKeyValueVirtualAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddableKeyAndValueElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		createTestEmbeddablePropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		AttributeOverrideContainer valueAttributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		AttributeOverrideContainer mapKeyAttributeOverrideContainer = elementCollectionMapping.getMapKeyAttributeOverrideContainer();
		assertEquals(4, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
		assertEquals(3, valueAttributeOverrideContainer.getVirtualOverridesSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(4, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
		assertEquals(3, valueAttributeOverrideContainer.getVirtualOverridesSize());

		
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.city");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("value.parcelNumber");
		getJpaProject().synchronizeContextModel();
		assertEquals(3, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
		assertEquals(2, valueAttributeOverrideContainer.getVirtualOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.state.name");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("size");
		getJpaProject().synchronizeContextModel();
		assertEquals(2, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
		assertEquals(1, valueAttributeOverrideContainer.getVirtualOverridesSize());
	}

	public void testMapKeyValueAttributeOverrideSetVirtual() throws Exception {
		createTestEntityWithEmbeddableKeyAndValueElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		createTestEmbeddablePropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");
				
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		AttributeOverrideContainer valueAttributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		AttributeOverrideContainer mapKeyAttributeOverrideContainer = elementCollectionMapping.getMapKeyAttributeOverrideContainer();
		valueAttributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		valueAttributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		Iterator<NestableAnnotation> attributeOverrides = resourceField.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		
		assertEquals("value.parcelNumber", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("key.city", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("value.size", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("key.state.name", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		valueAttributeOverrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		attributeOverrides = resourceField.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("value.size", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("key.state.name", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		assertEquals("parcelNumber", valueAttributeOverrideContainer.getVirtualOverrides().iterator().next().getName());
		assertEquals(2, valueAttributeOverrideContainer.getVirtualOverridesSize());
		assertEquals("city", mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next().getName());
		assertEquals(3, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
		
		valueAttributeOverrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		attributeOverrides = resourceField.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertFalse(attributeOverrides.hasNext());
		
		Iterator<? extends VirtualAttributeOverride> virtualAttributeOverrides = valueAttributeOverrideContainer.getVirtualOverrides().iterator();
		assertEquals("parcelNumber", virtualAttributeOverrides.next().getName());
		assertEquals("size", virtualAttributeOverrides.next().getName());
		assertEquals("tax", virtualAttributeOverrides.next().getName());
		assertEquals(3, valueAttributeOverrideContainer.getVirtualOverridesSize());
		
		virtualAttributeOverrides = mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator();
		assertEquals("city", virtualAttributeOverrides.next().getName());
		assertEquals("state.name", virtualAttributeOverrides.next().getName());
		assertEquals("state.abbr", virtualAttributeOverrides.next().getName());
		assertEquals("zip", virtualAttributeOverrides.next().getName());
		assertEquals(4, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
	}
	
	
	public void testMapKeyValueMoveSpecifiedAttributeOverride() throws Exception {
		createTestEntityWithEmbeddableKeyAndValueElementCollectionMapping();
		createTestTargetEmbeddableAddress();
		createTestEmbeddableState();
		createTestEmbeddablePropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		AttributeOverrideContainer valueAttributeOverrideContainer = elementCollectionMapping.getValueAttributeOverrideContainer();
		AttributeOverrideContainer mapKeyAttributeOverrideContainer = elementCollectionMapping.getMapKeyAttributeOverrideContainer();
		valueAttributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		valueAttributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		ListIterator<? extends AttributeOverride> specifiedOverrides = valueAttributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("parcelNumber", specifiedOverrides.next().getName());
		assertEquals("size", specifiedOverrides.next().getName());
		assertFalse(specifiedOverrides.hasNext());
		
		specifiedOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("city", specifiedOverrides.next().getName());
		assertEquals("state.name", specifiedOverrides.next().getName());
		assertFalse(specifiedOverrides.hasNext());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		resourceField.moveAnnotation(1, 0, AttributeOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();

		Iterator<NestableAnnotation> attributeOverrides = resourceField.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();

		assertEquals("value.size", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("value.parcelNumber", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("key.city", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("key.state.name", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		specifiedOverrides = valueAttributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("size", specifiedOverrides.next().getName());
		assertEquals("parcelNumber", specifiedOverrides.next().getName());
		assertFalse(specifiedOverrides.hasNext());
		
		specifiedOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("city", specifiedOverrides.next().getName());
		assertEquals("state.name", specifiedOverrides.next().getName());
		assertFalse(specifiedOverrides.hasNext());
		
		
		resourceField.moveAnnotation(3, 2, AttributeOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		attributeOverrides = resourceField.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();

		assertEquals("value.size", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("value.parcelNumber", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("key.state.name", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("key.city", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		specifiedOverrides = valueAttributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("size", specifiedOverrides.next().getName());
		assertEquals("parcelNumber", specifiedOverrides.next().getName());
		assertFalse(specifiedOverrides.hasNext());
		
		specifiedOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("state.name", specifiedOverrides.next().getName());
		assertEquals("city", specifiedOverrides.next().getName());
		assertFalse(specifiedOverrides.hasNext());
	}
	
	public void testSelfReferentialElementCollection() throws Exception {
		createSelfReferentialElementCollection();
		addXmlClassRef(PACKAGE_NAME + ".Foo");
		
		//If there is a StackOverflowError you will not be able to get the mapping
		JavaElementCollectionMapping2_0 elementCollectionMapping = (JavaElementCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("elementCollection").getMapping();
		assertFalse(elementCollectionMapping.getAllOverridableAttributeMappingNames().iterator().hasNext());
	}
}

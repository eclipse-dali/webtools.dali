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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.VirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.VirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.Cacheable2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_0.context.EclipseLink2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLink2_0JavaEntityTests extends EclipseLink2_0ContextModelTestCase
{
	protected static final String SUB_TYPE_NAME = "AnnotationTestTypeChild";
	protected static final String FULLY_QUALIFIED_SUB_TYPE_NAME = PACKAGE_NAME + "." + SUB_TYPE_NAME;
	
	
	public EclipseLink2_0JavaEntityTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}

	private ICompilationUnit createTestMappedSuperclass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS, JPA.ONE_TO_ONE, JPA.MANY_TO_ONE, JPA.ONE_TO_MANY, JPA.MANY_TO_MANY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass");
			}
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("private String foo;").append(CR);
				sb.append(CR);
				sb.append("    @OneToOne");
				sb.append(CR);
				sb.append("    private int address;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToOne");
				sb.append(CR);
				sb.append("    private int address2;").append(CR);
				sb.append(CR);
				sb.append("    @OneToMany");
				sb.append(CR);
				sb.append("    private int address3;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToMany");
				sb.append(CR);
				sb.append("    private int address4;").append(CR);
				sb.append(CR);
				sb.append("    ");
			}
		});
	}
	
	private ICompilationUnit createTestAbstractEntityTablePerClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.INHERITANCE, JPA.INHERITANCE_TYPE, JPA.ONE_TO_ONE, JPA.MANY_TO_ONE, JPA.ONE_TO_MANY, JPA.MANY_TO_MANY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)");
				sb.append("abstract");
			}
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("private String foo;").append(CR);
				sb.append(CR);
				sb.append("    @OneToOne");
				sb.append(CR);
				sb.append("    private int address;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToOne");
				sb.append(CR);
				sb.append("    private int address2;").append(CR);
				sb.append(CR);
				sb.append("    @OneToMany");
				sb.append(CR);
				sb.append("    private int address3;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToMany");
				sb.append(CR);
				sb.append("    private int address4;").append(CR);
				sb.append(CR);
				sb.append("    ");
			}
		});
	}

	private void createTestSubType() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("AnnotationTestTypeChild").append(" ");
				sb.append("extends " + TYPE_NAME + " ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
	}
	
	private void createTestMappedSuperclassCustomer() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
					sb.append("import ");
					sb.append(JPA.MAPPED_SUPERCLASS);
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
				sb.append("@MappedSuperclass");
				sb.append(CR);
				sb.append("public class ").append("Customer ");
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
	private void createTestMappedSuperclassCustomerWithElementCollection() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
					sb.append("import ");
					sb.append(JPA.MAPPED_SUPERCLASS);
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
					sb.append(CR);
				sb.append("@MappedSuperclass");
				sb.append(CR);
				sb.append("public class ").append("Customer ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private String id;").append(CR);
				sb.append(CR);
				sb.append("    private String name;").append(CR);
				sb.append(CR);
				sb.append("    @ElementCollection").append(CR);
				sb.append("    private java.util.Collection<Address> address;").append(CR);
				sb.append(CR);
			sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Customer.java", sourceWriter);
	}
	
	private void createTestEntityLongTimeCustomer() throws Exception {
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
				sb.append("public class ").append("LongTimeCustomer extends Customer ");
				sb.append("{}");
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "LongTimeCustomer.java", sourceWriter);
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
					sb.append("import ");
					sb.append(JPA.ONE_TO_ONE);
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
				sb.append("    @OneToOne").append(CR);
				sb.append("    private State state;").append(CR);
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

	public void testAttributeMappingKeyAllowed() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = (Entity) getJavaPersistentType().getMapping();
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(EclipseLinkMappingKeys.BASIC_COLLECTION_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(EclipseLinkMappingKeys.BASIC_MAP_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(EclipseLinkMappingKeys.TRANSFORMATION_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(EclipseLinkMappingKeys.VARIABLE_ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY));
	}
	
	public void testOverridableAttributes() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributes = getJavaEntity().getOverridableAttributeNames().iterator();
		assertFalse(overridableAttributes.hasNext());
		
		
		getJavaEntity().setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		
		overridableAttributes = getJavaEntity().getOverridableAttributeNames().iterator();		
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}

	public void testOverridableAttributeNames() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributeNames = getJavaEntity().getOverridableAttributeNames().iterator();
		assertFalse(overridableAttributeNames.hasNext());
		
		
		getJavaEntity().setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		
		overridableAttributeNames = getJavaEntity().getOverridableAttributeNames().iterator();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}

	public void testAllOverridableAttributes() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributes = getJavaEntity().getAllOverridableAttributeNames().iterator();
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertEquals("foo", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}
	
	public void testAllOverridableAttributesTablePerClass() throws Exception {
		createTestAbstractEntityTablePerClass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributes = getJavaEntity().getAllOverridableAttributeNames().iterator();
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertEquals("foo", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
		
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		JavaEntity abstractEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		overridableAttributes = abstractEntity.getAllOverridableAttributeNames().iterator();
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertEquals("foo", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}
	
	public void testAllOverridableAssociationsTablePerClass() throws Exception {
		createTestAbstractEntityTablePerClass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAssociations = getJavaEntity().getAllOverridableAssociationNames().iterator();
		assertEquals("address", overridableAssociations.next());
		assertEquals("address2", overridableAssociations.next());
		assertEquals("address3", overridableAssociations.next());
		assertEquals("address4", overridableAssociations.next());
		assertFalse(overridableAssociations.hasNext());
		
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		JavaEntity abstractEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		overridableAssociations = abstractEntity.getAllOverridableAssociationNames().iterator();
		assertEquals("address", overridableAssociations.next());
		assertEquals("address2", overridableAssociations.next());
		assertEquals("address3", overridableAssociations.next());
		assertEquals("address4", overridableAssociations.next());
		assertFalse(overridableAssociations.hasNext());
	}

	public void testAllOverridableAttributesMappedSuperclassInOrmXml() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Iterator<String> overridableAttributes = getJavaEntity().getAllOverridableAttributeNames().iterator();
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertEquals("foo", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}

	public void testAllOverridableAttributeNames() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributeNames = getJavaEntity().getAllOverridableAttributeNames().iterator();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertEquals("foo", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}
		
	public void testSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		ListIterator<JavaAttributeOverride> specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		
		assertFalse(specifiedAttributeOverrides.hasNext());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceType.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		attributeOverride = (AttributeOverrideAnnotation) resourceType.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());


		attributeOverride = (AttributeOverrideAnnotation) resourceType.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		resourceType.moveAnnotation(1, 0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		resourceType.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		resourceType.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		
		resourceType.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertFalse(specifiedAttributeOverrides.hasNext());
	}

	public void testDefaultAttributeOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		JavaEntity javaEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		AttributeOverrideContainer overrideContainer = javaEntity.getAttributeOverrideContainer();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		
		assertEquals(3, overrideContainer.getVirtualOverridesSize());
		VirtualAttributeOverride virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		

		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();
		
		BasicMapping idMapping = (BasicMapping) mappedSuperclass.getPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));

		assertEquals(3, overrideContainer.getVirtualOverridesSize());
		virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("FOO", virtualAttributeOverride.getColumn().getName());
		assertEquals("BAR", virtualAttributeOverride.getColumn().getTable());

		idMapping.getColumn().setSpecifiedName(null);
		idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));

		virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		
		virtualAttributeOverride.convertToSpecified();
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
	}
	
	public void testDefaultAttributeOverridesEntityHierachy() throws Exception {
		createTestAbstractEntityTablePerClass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		JavaEntity javaEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		AttributeOverrideContainer overrideContainer = javaEntity.getAttributeOverrideContainer();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
		
		assertEquals(3, overrideContainer.getVirtualOverridesSize());
		VirtualAttributeOverride virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		

		JavaEntity superclass = (JavaEntity) getJavaPersistentType().getMapping();
		
		BasicMapping idMapping = (BasicMapping) superclass.getPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));

		assertEquals(3, overrideContainer.getVirtualOverridesSize());
		virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("FOO", virtualAttributeOverride.getColumn().getName());
		assertEquals("BAR", virtualAttributeOverride.getColumn().getTable());

		idMapping.getColumn().setSpecifiedName(null);
		idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));

		virtualAttributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		
		virtualAttributeOverride.convertToSpecified();
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
	}
	
	public void testSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		assertEquals(0, overrideContainer.getSpecifiedOverridesSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceType.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) resourceType.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(2, overrideContainer.getSpecifiedOverridesSize());
	}
	
	public void testDefaultAttributeOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		
		assertEquals(3, overrideContainer.getVirtualOverridesSize());

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(1, overrideContainer.getVirtualOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(0, overrideContainer.getVirtualOverridesSize());
	}
	
	public void testAttributeOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		
		assertEquals(3, overrideContainer.getOverridesSize());

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(3, overrideContainer.getOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(3, overrideContainer.getOverridesSize());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		AttributeOverrideAnnotation annotation = (AttributeOverrideAnnotation) resourceType.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		annotation.setName("bar");
		getJpaProject().synchronizeContextModel();
		assertEquals(4, overrideContainer.getOverridesSize());
	}

	public void testAttributeOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
				
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		Iterator<NestableAnnotation> attributeOverrides = resourceType.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		
		assertEquals("id", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("name", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testAttributeOverrideSetVirtual2() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaAttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		
		ListIterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = overrideContainer.getVirtualOverrides().iterator();
		virtualAttributeOverrides.next();
		virtualAttributeOverrides.next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		Iterator<NestableAnnotation> attributeOverrides = resourceType.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		
		assertEquals("name", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("id", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testAttributeOverrideSetVirtualTrue() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaAttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
				
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		assertEquals(3, resourceType.getAnnotationsSize(AttributeOverrideAnnotation.ANNOTATION_NAME));

		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		
		Iterator<NestableAnnotation> attributeOverrideResources = resourceType.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("name", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());		
		assertEquals("foo", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());
		assertFalse(attributeOverrideResources.hasNext());
		
		Iterator<JavaAttributeOverride> attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("name", attributeOverrides.next().getName());		
		assertEquals("foo", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		
		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		attributeOverrideResources = resourceType.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("foo", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());		
		assertFalse(attributeOverrideResources.hasNext());

		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("foo", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		
		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		attributeOverrideResources = resourceType.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertFalse(attributeOverrideResources.hasNext());
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertFalse(attributeOverrides.hasNext());

		assertNull(resourceType.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();

		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		
		Iterator<NestableAnnotation> javaAttributeOverrides = resourceType.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals(3, CollectionTools.size(javaAttributeOverrides));
		
		
		overrideContainer.moveSpecifiedOverride(2, 0);
		ListIterator<? extends AttributeOverride> attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("name", attributeOverrides.next().getName());
		assertEquals("foo", attributeOverrides.next().getName());
		assertEquals("id", attributeOverrides.next().getName());

		javaAttributeOverrides = resourceType.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("name", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("foo", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("id", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());


		overrideContainer.moveSpecifiedOverride(0, 1);
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("foo", attributeOverrides.next().getName());
		assertEquals("name", attributeOverrides.next().getName());
		assertEquals("id", attributeOverrides.next().getName());

		javaAttributeOverrides = resourceType.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("foo", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("name", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("id", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
	}
//	
	public void testUpdateSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
	
		((AttributeOverrideAnnotation) resourceType.addAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME)).setName("FOO");
		((AttributeOverrideAnnotation) resourceType.addAnnotation(1, AttributeOverrideAnnotation.ANNOTATION_NAME)).setName("BAR");
		((AttributeOverrideAnnotation) resourceType.addAnnotation(2, AttributeOverrideAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		ListIterator<? extends AttributeOverride> attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		resourceType.moveAnnotation(2, 0, AttributeOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		resourceType.moveAnnotation(0, 1, AttributeOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		resourceType.removeAnnotation(1,  AttributeOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		resourceType.removeAnnotation(1,  AttributeOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		resourceType.removeAnnotation(0,  AttributeOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertFalse(attributeOverrides.hasNext());
	}

	public void testAttributeOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaAttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		
		ListIterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = overrideContainer.getVirtualOverrides().iterator();	
		JavaVirtualAttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());

		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("foo", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		assertFalse(virtualAttributeOverrides.hasNext());

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		AttributeOverride specifiedAttributeOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		assertFalse(specifiedAttributeOverride.isVirtual());
		
		
		virtualAttributeOverrides = overrideContainer.getVirtualOverrides().iterator();	
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("foo", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		assertFalse(virtualAttributeOverrides.hasNext());
	}
	
	
	public void testOverridableAssociations() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<String> overridableAssociations = getJavaEntity().getOverridableAssociationNames().iterator();
		assertFalse(overridableAssociations.hasNext());
	}

	public void testOverridableAssociationNames() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<String> overridableAssociationNames = getJavaEntity().getOverridableAssociationNames().iterator();
		assertFalse(overridableAssociationNames.hasNext());
	}
	
//	//TODO add all mapping types to the mapped superclass to test which ones are overridable
	public void testAllOverridableAssociationNames() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<String> overridableAssociationNames = getJavaEntity().getAllOverridableAssociationNames().iterator();
		assertEquals("address", overridableAssociationNames.next());
		assertEquals("address2", overridableAssociationNames.next());
		assertEquals("address3", overridableAssociationNames.next());
		assertEquals("address4", overridableAssociationNames.next());
		assertFalse(overridableAssociationNames.hasNext());
	}
		
	public void testAllOverridableAssociationsMappedSuperclassInOrmXml() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Iterator<String> overridableAssociations = getJavaEntity().getAllOverridableAssociationNames().iterator();
		assertEquals("address", overridableAssociations.next());
		assertEquals("address2", overridableAssociations.next());
		assertEquals("address3", overridableAssociations.next());
		assertEquals("address4", overridableAssociations.next());
		assertFalse(overridableAssociations.hasNext());
	}

	public void testSpecifiedAssociationOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		ListIterator<JavaAssociationOverride> specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		
		assertFalse(specifiedAssociationOverrides.hasNext());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		//add an annotation to the resource model and verify the context model is updated
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) resourceType.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		associationOverride = (AssociationOverrideAnnotation) resourceType.addAnnotation(1, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());


		associationOverride = (AssociationOverrideAnnotation) resourceType.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		resourceType.moveAnnotation(1, 0, JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		resourceType.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		resourceType.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		
		resourceType.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = overrideContainer.getSpecifiedOverrides().iterator();		
		assertFalse(specifiedAssociationOverrides.hasNext());
	}

	public void testVirtualAssociationOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		JavaEntity javaEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		AssociationOverrideContainer overrideContainer = javaEntity.getAssociationOverrideContainer();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
		
		assertEquals(4, overrideContainer.getVirtualOverridesSize());
		VirtualAssociationOverride virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("address", virtualAssociationOverride.getName());
		

		//MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();
		//BasicMapping idMapping = (BasicMapping) mappedSuperclass.persistentType().attributeNamed("id").getMapping();
		//idMapping.getColumn().setSpecifiedName("FOO");
		//idMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));

		assertEquals(4, overrideContainer.getVirtualOverridesSize());
		virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("address", virtualAssociationOverride.getName());

		//idMapping.getColumn().setSpecifiedName(null);
		//idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(SUB_TYPE_NAME, resourceType.getName());
		assertNull(resourceType.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));

		virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("address", virtualAssociationOverride.getName());
		
		virtualAssociationOverride.convertToSpecified();
		assertEquals(3, overrideContainer.getVirtualOverridesSize());
		
		
		
//		//TODO joinColumns for default association overrides
////	IJoinColumn defaultJoinColumn = defaultAssociationOverride.joinColumns().next();
////	assertEquals("address", defaultJoinColumn.getName());
////	assertEquals("address", defaultJoinColumn.getReferencedColumnName());
////	assertEquals(SUB_TYPE_NAME, defaultJoinColumn.getTable());
////	
////
////	IMappedSuperclass mappedSuperclass = (IMappedSuperclass) javaPersistentType().getMapping();
////	
////	IOneToOneMapping addressMapping = (IOneToOneMapping) mappedSuperclass.persistentType().attributeNamed("address").getMapping();
////	IJoinColumn joinColumn = addressMapping.addSpecifiedJoinColumn(0);
////	joinColumn.setSpecifiedName("FOO");
////	joinColumn.setSpecifiedReferencedColumnName("BAR");
////	joinColumn.setSpecifiedTable("BAZ");
////	
////	assertEquals(SUB_TYPE_NAME, resourceType.getName());
////	assertNull(resourceType.annotation(AssociationOverride.ANNOTATION_NAME));
////	assertNull(resourceType.annotation(AssociationOverrides.ANNOTATION_NAME));
////
////	assertEquals(1, CollectionTools.size(javaEntity.defaultAssociationOverrides()));
////	defaultAssociationOverride = javaEntity.defaultAssociationOverrides().next();
////	assertEquals("address", defaultAssociationOverride.getName());
////	assertEquals("FOO", defaultJoinColumn.getName());
////	assertEquals("BAR", defaultJoinColumn.getReferencedColumnName());
////	assertEquals("BAZ", defaultJoinColumn.getTable());
////
////	joinColumn.setSpecifiedName(null);
////	joinColumn.setSpecifiedReferencedColumnName(null);
////	joinColumn.setSpecifiedTable(null);
////	assertEquals(SUB_TYPE_NAME, resourceType.getName());
////	assertNull(resourceType.annotation(AssociationOverride.ANNOTATION_NAME));
////	assertNull(resourceType.annotation(AssociationOverrides.ANNOTATION_NAME));
////
////	defaultAssociationOverride = javaEntity.defaultAssociationOverrides().next();
////	assertEquals("address", defaultJoinColumn.getName());
////	assertEquals("address", defaultJoinColumn.getReferencedColumnName());
////	assertEquals(SUB_TYPE_NAME, defaultJoinColumn.getTable());
////	
////	javaEntity.addSpecifiedAssociationOverride(0).setName("address");
////	assertEquals(0, CollectionTools.size(javaEntity.defaultAssociationOverrides()));

	}
	
	public void testSpecifiedAssociationOverridesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		
		assertEquals(0, overrideContainer.getSpecifiedOverridesSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		//add an annotation to the resource model and verify the context model is updated
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) resourceType.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("FOO");
		associationOverride = (AssociationOverrideAnnotation) resourceType.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		associationOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(2, overrideContainer.getSpecifiedOverridesSize());
	}
	
	public void testVirtualAssociationOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		
		assertEquals(4, overrideContainer.getVirtualOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(3, overrideContainer.getVirtualOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(1, overrideContainer.getVirtualOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(0, overrideContainer.getVirtualOverridesSize());
	}
	
	public void testAssociationOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		
		assertEquals(4, overrideContainer.getOverridesSize());

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(4, overrideContainer.getOverridesSize());
		
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		assertEquals(4, overrideContainer.getOverridesSize());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		AssociationOverrideAnnotation annotation = (AssociationOverrideAnnotation) resourceType.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		annotation.setName("bar");	
		getJpaProject().synchronizeContextModel();
		assertEquals(5, overrideContainer.getOverridesSize());
	}

	public void testAssociationOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
				
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		Iterator<NestableAnnotation> associationOverrides = resourceType.getAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME).iterator();
		
		assertEquals("address", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertEquals("address2", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertFalse(associationOverrides.hasNext());
	}
	
	public void testAssociationOverrideSetVirtual2() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		
		ListIterator<JavaVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.getVirtualOverrides().iterator();
		virtualAssociationOverrides.next();
		virtualAssociationOverrides.next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		Iterator<NestableAnnotation> associationOverrides = resourceType.getAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME).iterator();
		
		assertEquals("address2", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertEquals("address", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertFalse(associationOverrides.hasNext());
	}
	
	public void testAssociationOverrideSetVirtualTrue() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
				
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		assertEquals(2, resourceType.getAnnotationsSize(AssociationOverrideAnnotation.ANNOTATION_NAME));

		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		
		Iterator<NestableAnnotation> associationOverrideResources = resourceType.getAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("address2", ((AssociationOverrideAnnotation) associationOverrideResources.next()).getName());		
		assertFalse(associationOverrideResources.hasNext());

		Iterator<JavaAssociationOverride> associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("address2", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		
		overrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		associationOverrideResources = resourceType.getAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertFalse(associationOverrideResources.hasNext());
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertFalse(associationOverrides.hasNext());

		assertNull(resourceType.getAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedAssociationOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();

		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_SUB_TYPE_NAME, Kind.TYPE);
		
		Iterator<NestableAnnotation> javaAssociationOverrides = resourceType.getAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals(2, CollectionTools.size(javaAssociationOverrides));
		
		
		overrideContainer.moveSpecifiedOverride(1, 0);
		Iterator<JavaAssociationOverride> associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("address2", associationOverrides.next().getName());
		assertEquals("address", associationOverrides.next().getName());

		javaAssociationOverrides = resourceType.getAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("address2", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
		assertEquals("address", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());


		overrideContainer.moveSpecifiedOverride(0, 1);
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("address", associationOverrides.next().getName());
		assertEquals("address2", associationOverrides.next().getName());

		javaAssociationOverrides = resourceType.getAnnotations(AssociationOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("address", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
		assertEquals("address2", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
	}

	public void testUpdateSpecifiedAssociationOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
	
		((AssociationOverrideAnnotation) resourceType.addAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME)).setName("FOO");
		((AssociationOverrideAnnotation) resourceType.addAnnotation(1, AssociationOverrideAnnotation.ANNOTATION_NAME)).setName("BAR");
		((AssociationOverrideAnnotation) resourceType.addAnnotation(2, AssociationOverrideAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		Iterator<JavaAssociationOverride> associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("FOO", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		resourceType.moveAnnotation(2, 0, AssociationOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		resourceType.moveAnnotation(0, 1, AssociationOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		resourceType.removeAnnotation(1,  AssociationOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		resourceType.removeAnnotation(1,  AssociationOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = overrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		resourceType.removeAnnotation(0,  AssociationOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides =  overrideContainer.getSpecifiedOverrides().iterator();
		assertFalse(associationOverrides.hasNext());
	}

	public void testAssociationOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		
		ListIterator<JavaVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.getVirtualOverrides().iterator();
		VirtualAssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());

		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address2", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address3", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address4", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		assertFalse(virtualAssociationOverrides.hasNext());

		overrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		AssociationOverride specifiedAssociationOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		assertFalse(specifiedAssociationOverride.isVirtual());
		
		
		virtualAssociationOverrides = overrideContainer.getVirtualOverrides().iterator();	
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address2", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address3", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		
		virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address4", virtualAssociationOverride.getName());
		assertTrue(virtualAssociationOverride.isVirtual());
		assertFalse(virtualAssociationOverrides.hasNext());
	}
	
	public void testNestedVirtualAttributeOverrides() throws Exception {
		createTestMappedSuperclassCustomer();
		createTestEntityLongTimeCustomer();
		createTestEmbeddableAddress();
		createTestEmbeddableZipCode();
		
		addXmlClassRef(PACKAGE_NAME + ".Customer");
		addXmlClassRef(PACKAGE_NAME + ".LongTimeCustomer");
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".ZipCode");
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		PersistentType customerPersistentType = specifiedClassRefs.next().getJavaPersistentType();
		PersistentType longTimeCustomerPersistentType = specifiedClassRefs.next().getJavaPersistentType();
		PersistentType addressPersistentType = specifiedClassRefs.next().getJavaPersistentType();		
		PersistentType zipCodePersistentType = specifiedClassRefs.next().getJavaPersistentType();

		AttributeOverrideContainer attributeOverrideContainer = ((Entity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		
		assertEquals(6, attributeOverrideContainer.getVirtualOverridesSize());
		ListIterator<? extends VirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		VirtualAttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.street", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.city", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.zipCode.zip", virtualAttributeOverride.getName());
		assertEquals("zip", virtualAttributeOverride.getColumn().getName());
		assertEquals("LongTimeCustomer", virtualAttributeOverride.getColumn().getTable());		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.zipCode.plusfour", virtualAttributeOverride.getName());
		assertEquals("plusfour", virtualAttributeOverride.getColumn().getName());
		assertEquals("LongTimeCustomer", virtualAttributeOverride.getColumn().getTable());	
		assertEquals(null, virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(true, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(255, virtualAttributeOverride.getColumn().getLength());
		assertEquals(0, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(0, virtualAttributeOverride.getColumn().getScale());

		
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

		attributeOverrideContainer = ((Entity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		//check the top-level embedded (Customer.address) attribute override to verify it is getting settings from the specified column on Zipcode.plusfour
		virtualAttributeOverride = (VirtualAttributeOverride) attributeOverrideContainer.getOverrideNamed("address.zipCode.plusfour");
		assertEquals("address.zipCode.plusfour", virtualAttributeOverride.getName());
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
		AttributeOverride specifiedAttributeOverride = ((VirtualAttributeOverride) ((EmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping()).getAttributeOverrideContainer().getOverrideNamed("plusfour")).convertToSpecified();
		specifiedAttributeOverride.getColumn().setSpecifiedName("BLAH_OVERRIDE");
		specifiedAttributeOverride.getColumn().setSpecifiedTable("BLAH_TABLE_OVERRIDE");
		specifiedAttributeOverride.getColumn().setColumnDefinition("COLUMN_DEFINITION_OVERRIDE");


		attributeOverrideContainer = ((Entity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		virtualAttributeOverride = (VirtualAttributeOverride) attributeOverrideContainer.getOverrideNamed("address.zipCode.plusfour");
		assertEquals("address.zipCode.plusfour", virtualAttributeOverride.getName());
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
		assertEquals("address.zipCode.plusfour", specifiedAttributeOverride.getName());
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

	public void testNestedVirtualAttributeOverridesElementCollection() throws Exception {
		createTestMappedSuperclassCustomerWithElementCollection();
		createTestEntityLongTimeCustomer();
		createTestEmbeddableAddress();
		createTestEmbeddableZipCode();
		
		addXmlClassRef(PACKAGE_NAME + ".Customer");
		addXmlClassRef(PACKAGE_NAME + ".LongTimeCustomer");
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".ZipCode");
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		PersistentType customerPersistentType = specifiedClassRefs.next().getJavaPersistentType();
		PersistentType longTimeCustomerPersistentType = specifiedClassRefs.next().getJavaPersistentType();
		PersistentType addressPersistentType = specifiedClassRefs.next().getJavaPersistentType();		
		PersistentType zipCodePersistentType = specifiedClassRefs.next().getJavaPersistentType();
		
		AttributeOverrideContainer attributeOverrideContainer = ((Entity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		
		assertEquals(6, attributeOverrideContainer.getVirtualOverridesSize());
		ListIterator<? extends VirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		VirtualAttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.street", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.city", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.zipCode.zip", virtualAttributeOverride.getName());
		assertEquals("zip", virtualAttributeOverride.getColumn().getName());
		assertEquals("LongTimeCustomer", virtualAttributeOverride.getColumn().getTable());		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("address.zipCode.plusfour", virtualAttributeOverride.getName());
		assertEquals("plusfour", virtualAttributeOverride.getColumn().getName());
		assertEquals("LongTimeCustomer", virtualAttributeOverride.getColumn().getTable());	
		assertEquals(null, virtualAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, virtualAttributeOverride.getColumn().isInsertable());
		assertEquals(true, virtualAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, virtualAttributeOverride.getColumn().isUnique());
		assertEquals(true, virtualAttributeOverride.getColumn().isNullable());
		assertEquals(255, virtualAttributeOverride.getColumn().getLength());
		assertEquals(0, virtualAttributeOverride.getColumn().getPrecision());
		assertEquals(0, virtualAttributeOverride.getColumn().getScale());

		
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

		attributeOverrideContainer = ((Entity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		//check the top-level embedded (Customer.address) attribute override to verify it is getting settings from the specified column on Zipcode.plusfour
		virtualAttributeOverride = (VirtualAttributeOverride) attributeOverrideContainer.getOverrideNamed("address.zipCode.plusfour");
		assertEquals("address.zipCode.plusfour", virtualAttributeOverride.getName());
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
		AttributeOverride specifiedAttributeOverride = ((VirtualAttributeOverride) ((EmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping()).getAttributeOverrideContainer().getOverrideNamed("plusfour")).convertToSpecified();
		specifiedAttributeOverride.getColumn().setSpecifiedName("BLAH_OVERRIDE");
		specifiedAttributeOverride.getColumn().setSpecifiedTable("BLAH_TABLE_OVERRIDE");
		specifiedAttributeOverride.getColumn().setColumnDefinition("COLUMN_DEFINITION_OVERRIDE");


		attributeOverrideContainer = ((Entity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		virtualAttributeOverride = (VirtualAttributeOverride) attributeOverrideContainer.getOverrideNamed("address.zipCode.plusfour");
		assertEquals("address.zipCode.plusfour", virtualAttributeOverride.getName());
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
		assertEquals("address.zipCode.plusfour", specifiedAttributeOverride.getName());
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

	public void testNestedVirtualAssociationOverrides() throws Exception {
		createTestMappedSuperclassCustomer();
		createTestEntityLongTimeCustomer();
		createTestEmbeddableAddress();
		createTestEmbeddableZipCode();
		
		addXmlClassRef(PACKAGE_NAME + ".Customer");
		addXmlClassRef(PACKAGE_NAME + ".LongTimeCustomer");
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".ZipCode");
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		specifiedClassRefs.next();
		PersistentType longTimeCustomerPersistentType = specifiedClassRefs.next().getJavaPersistentType();

		AssociationOverrideContainer attributeOverrideContainer = ((Entity) longTimeCustomerPersistentType.getMapping()).getAssociationOverrideContainer();
		
		assertEquals(1, attributeOverrideContainer.getVirtualOverridesSize());
		ListIterator<? extends VirtualAssociationOverride> virtualAssociationOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		VirtualAssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address.state", virtualAssociationOverride.getName());
	}

	public void testNestedVirtualAssociationOverridesElementCollection() throws Exception {
		createTestMappedSuperclassCustomerWithElementCollection();
		createTestEntityLongTimeCustomer();
		createTestEmbeddableAddress();
		createTestEmbeddableZipCode();
		
		addXmlClassRef(PACKAGE_NAME + ".Customer");
		addXmlClassRef(PACKAGE_NAME + ".LongTimeCustomer");
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".ZipCode");
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		specifiedClassRefs.next();
		PersistentType longTimeCustomerPersistentType = specifiedClassRefs.next().getJavaPersistentType();

		AssociationOverrideContainer attributeOverrideContainer = ((Entity) longTimeCustomerPersistentType.getMapping()).getAssociationOverrideContainer();
		
		assertEquals(1, attributeOverrideContainer.getVirtualOverridesSize());
		ListIterator<? extends VirtualAssociationOverride> virtualAssociationOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		VirtualAssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address.state", virtualAssociationOverride.getName());
	}
	
	public void testSetSpecifiedCacheable() throws Exception {
		ICompilationUnit cu = createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 cacheable = ((CacheableHolder2_0) getJavaEntity()).getCacheable();
		Cacheable2_0Annotation cacheableAnnotation = (Cacheable2_0Annotation) getJavaPersistentType().getJavaResourceType().getAnnotation(JPA2_0.CACHEABLE);
		assertEquals(null, cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation);
		
		cacheable.setSpecifiedCacheable(Boolean.FALSE);
		cacheableAnnotation = (Cacheable2_0Annotation) getJavaPersistentType().getJavaResourceType().getAnnotation(JPA2_0.CACHEABLE);		
		assertEquals(Boolean.FALSE, cacheable.getSpecifiedCacheable());
		assertEquals(Boolean.FALSE, cacheableAnnotation.getValue());
		assertSourceContains("@Cacheable(false)", cu);
		
		cacheable.setSpecifiedCacheable(Boolean.TRUE);
		cacheableAnnotation = (Cacheable2_0Annotation) getJavaPersistentType().getJavaResourceType().getAnnotation(JPA2_0.CACHEABLE);		
		assertEquals(Boolean.TRUE, cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation.getValue());
		assertSourceContains("@Cacheable", cu);
		
		cacheable.setSpecifiedCacheable(null);
		cacheableAnnotation = (Cacheable2_0Annotation) getJavaPersistentType().getJavaResourceType().getAnnotation(JPA2_0.CACHEABLE);		
		assertEquals(null, cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation);
		assertSourceDoesNotContain("@Cacheable", cu);
	}
	
	public void testGetSpecifiedCacheable() throws Exception {
		ICompilationUnit cu = createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 cacheable = ((CacheableHolder2_0) getJavaEntity()).getCacheable();
		Cacheable2_0Annotation cacheableAnnotation = (Cacheable2_0Annotation) getJavaPersistentType().getJavaResourceType().getAnnotation(JPA2_0.CACHEABLE);
		assertEquals(null, cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation);
		
		getJavaPersistentType().getJavaResourceType().addAnnotation(JPA2_0.CACHEABLE);
		getJpaProject().synchronizeContextModel();
		cacheableAnnotation = (Cacheable2_0Annotation) getJavaPersistentType().getJavaResourceType().getAnnotation(JPA2_0.CACHEABLE);
		assertEquals(Boolean.TRUE, cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation.getValue());
		assertSourceContains("@Cacheable", cu);

		cacheableAnnotation.setValue(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();
		assertEquals(Boolean.FALSE, cacheable.getSpecifiedCacheable());
		assertEquals(Boolean.FALSE, cacheableAnnotation.getValue());
		assertSourceContains("@Cacheable(false)", cu);
		
		cacheableAnnotation.setValue(Boolean.TRUE);
		getJpaProject().synchronizeContextModel();
		assertEquals(Boolean.TRUE, cacheable.getSpecifiedCacheable());
		assertEquals(Boolean.TRUE, cacheableAnnotation.getValue());
		assertSourceContains("@Cacheable(true)", cu);
		
		cacheableAnnotation.setValue(null);
		getJpaProject().synchronizeContextModel();
		assertEquals(Boolean.TRUE, cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation.getValue());
		assertSourceContains("@Cacheable", cu);

		getJavaPersistentType().getJavaResourceType().removeAnnotation(JPA2_0.CACHEABLE);
		cacheableAnnotation = (Cacheable2_0Annotation) getJavaPersistentType().getJavaResourceType().getAnnotation(JPA2_0.CACHEABLE);		
		getJpaProject().synchronizeContextModel();
		assertEquals(null,  cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation);
		assertSourceDoesNotContain("@Cacheable", cu);
	}
	
	public void testIsDefaultCacheable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 cacheable = ((CacheableHolder2_0) getJavaEntity()).getCacheable();
		PersistenceUnit2_0 persistenceUnit = getPersistenceUnit();
		assertEquals(SharedCacheMode.DISABLE_SELECTIVE, persistenceUnit.getSharedCacheMode());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		persistenceUnit.setSpecifiedSharedCacheMode(SharedCacheMode.ALL);
		assertEquals(true, cacheable.isDefaultCacheable());
		
		persistenceUnit.setSpecifiedSharedCacheMode(SharedCacheMode.NONE);
		assertEquals(false, cacheable.isDefaultCacheable());

		persistenceUnit.setSpecifiedSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
		assertEquals(false, cacheable.isDefaultCacheable());

		persistenceUnit.setSpecifiedSharedCacheMode(SharedCacheMode.DISABLE_SELECTIVE);
		assertEquals(true, cacheable.isDefaultCacheable());
		
		persistenceUnit.setSpecifiedSharedCacheMode(SharedCacheMode.UNSPECIFIED);
		assertEquals(true, cacheable.isDefaultCacheable());
	}
	
	public void testInheritedIsDefaultCacheable() throws Exception {
		createTestEntity();
		createTestSubType();
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 subCacheable = ((CacheableHolder2_0) getJavaEntity()).getCacheable();
		Cacheable2_0 cacheable = ((CacheableHolder2_0) getJavaEntity().getParentEntity()).getCacheable();
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());

		PersistenceUnit2_0 persistenceUnit2_0 = getPersistenceUnit();
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.NONE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());

		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(null);
		cacheable.setSpecifiedCacheable(Boolean.FALSE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.DISABLE_SELECTIVE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
				
		cacheable.setSpecifiedCacheable(Boolean.FALSE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		cacheable.setSpecifiedCacheable(Boolean.TRUE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.NONE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
	}
}

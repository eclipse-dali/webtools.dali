/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.MappedSuperclass;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.VirtualAssociationOverride;
import org.eclipse.jpt.core.context.VirtualAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.core.jpa2.resource.java.Cacheable2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AssociationOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.AttributeOverridesAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.EclipseLink2_0ContextModelTestCase;

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
	
		Iterator<String> overridableAttributes = getJavaEntity().overridableAttributeNames();
		assertFalse(overridableAttributes.hasNext());
		
		
		getJavaEntity().setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		
		overridableAttributes = getJavaEntity().overridableAttributeNames();		
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}

	public void testOverridableAttributeNames() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributeNames = getJavaEntity().overridableAttributeNames();
		assertFalse(overridableAttributeNames.hasNext());
		
		
		getJavaEntity().setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		
		overridableAttributeNames = getJavaEntity().overridableAttributeNames();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}

	public void testAllOverridableAttributes() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		Iterator<String> overridableAttributes = getJavaEntity().allOverridableAttributeNames();
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
	
		Iterator<String> overridableAttributes = getJavaEntity().allOverridableAttributeNames();
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertEquals("foo", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
		
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaEntity abstractEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		overridableAttributes = abstractEntity.allOverridableAttributeNames();
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
	
		Iterator<String> overridableAssociations = getJavaEntity().allOverridableAssociationNames();
		assertEquals("address", overridableAssociations.next());
		assertEquals("address2", overridableAssociations.next());
		assertEquals("address3", overridableAssociations.next());
		assertEquals("address4", overridableAssociations.next());
		assertFalse(overridableAssociations.hasNext());
		
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaEntity abstractEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		overridableAssociations = abstractEntity.allOverridableAssociationNames();
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
		
		Iterator<String> overridableAttributes = getJavaEntity().allOverridableAttributeNames();
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
	
		Iterator<String> overridableAttributeNames = getJavaEntity().allOverridableAttributeNames();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertEquals("foo", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}
		
	public void testSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		ListIterator<JavaAttributeOverride> specifiedAttributeOverrides = (ListIterator<JavaAttributeOverride>) overrideContainer.specifiedOverrides();
		
		assertFalse(specifiedAttributeOverrides.hasNext());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = (ListIterator<JavaAttributeOverride>) overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = (ListIterator<JavaAttributeOverride>) overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());


		attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = (ListIterator<JavaAttributeOverride>) overrideContainer.specifiedOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		typeResource.moveAnnotation(1, 0, JPA.ATTRIBUTE_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = (ListIterator<JavaAttributeOverride>) overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		typeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = (ListIterator<JavaAttributeOverride>) overrideContainer.specifiedOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		typeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = (ListIterator<JavaAttributeOverride>) overrideContainer.specifiedOverrides();		
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		
		typeResource.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverrides = (ListIterator<JavaAttributeOverride>) overrideContainer.specifiedOverrides();		
		assertFalse(specifiedAttributeOverrides.hasNext());
	}

	public void testDefaultAttributeOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaEntity javaEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		AttributeOverrideContainer overrideContainer = javaEntity.getAttributeOverrideContainer();

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));
		
		assertEquals(3, overrideContainer.virtualOverridesSize());
		VirtualAttributeOverride virtualAttributeOverride = overrideContainer.virtualOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		

		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();
		
		BasicMapping idMapping = (BasicMapping) mappedSuperclass.getPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));

		assertEquals(3, overrideContainer.virtualOverridesSize());
		virtualAttributeOverride = overrideContainer.virtualOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("FOO", virtualAttributeOverride.getColumn().getName());
		assertEquals("BAR", virtualAttributeOverride.getColumn().getTable());

		idMapping.getColumn().setSpecifiedName(null);
		idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));

		virtualAttributeOverride = overrideContainer.virtualOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		
		virtualAttributeOverride.convertToSpecified();
		assertEquals(2, overrideContainer.virtualOverridesSize());
	}
	
	public void testDefaultAttributeOverridesEntityHierachy() throws Exception {
		createTestAbstractEntityTablePerClass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaEntity javaEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		AttributeOverrideContainer overrideContainer = javaEntity.getAttributeOverrideContainer();

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));
		
		assertEquals(3, overrideContainer.virtualOverridesSize());
		VirtualAttributeOverride virtualAttributeOverride = overrideContainer.virtualOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		

		JavaEntity superclass = (JavaEntity) getJavaPersistentType().getMapping();
		
		BasicMapping idMapping = (BasicMapping) superclass.getPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));

		assertEquals(3, overrideContainer.virtualOverridesSize());
		virtualAttributeOverride = overrideContainer.virtualOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("FOO", virtualAttributeOverride.getColumn().getName());
		assertEquals("BAR", virtualAttributeOverride.getColumn().getTable());

		idMapping.getColumn().setSpecifiedName(null);
		idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));

		virtualAttributeOverride = overrideContainer.virtualOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		
		virtualAttributeOverride.convertToSpecified();
		assertEquals(2, overrideContainer.virtualOverridesSize());
	}
	
	public void testSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		assertEquals(0, overrideContainer.specifiedOverridesSize());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		attributeOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(2, overrideContainer.specifiedOverridesSize());
	}
	
	public void testDefaultAttributeOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		
		assertEquals(3, overrideContainer.virtualOverridesSize());

		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(2, overrideContainer.virtualOverridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(1, overrideContainer.virtualOverridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(0, overrideContainer.virtualOverridesSize());
	}
	
	public void testAttributeOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		
		assertEquals(3, overrideContainer.overridesSize());

		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(3, overrideContainer.overridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(3, overrideContainer.overridesSize());
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		AttributeOverrideAnnotation annotation = (AttributeOverrideAnnotation) typeResource.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE, JPA.ATTRIBUTE_OVERRIDES);
		annotation.setName("bar");
		getJpaProject().synchronizeContextModel();
		assertEquals(4, overrideContainer.overridesSize());
	}

	public void testAttributeOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
				
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		Iterator<NestableAnnotation> attributeOverrides = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("id", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("name", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testAttributeOverrideSetVirtual2() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		
		ListIterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = (ListIterator<JavaVirtualAttributeOverride>) overrideContainer.virtualOverrides();
		virtualAttributeOverrides.next();
		virtualAttributeOverrides.next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		Iterator<NestableAnnotation> attributeOverrides = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("name", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("id", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testAttributeOverrideSetVirtualTrue() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
				
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(3, CollectionTools.size(typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME)));

		overrideContainer.specifiedOverrides().next().convertToVirtual();
		
		Iterator<NestableAnnotation> attributeOverrideResources = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("name", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());		
		assertEquals("foo", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());
		assertFalse(attributeOverrideResources.hasNext());
		
		Iterator<JavaAttributeOverride> attributeOverrides = (Iterator<JavaAttributeOverride>) overrideContainer.specifiedOverrides();
		assertEquals("name", attributeOverrides.next().getName());		
		assertEquals("foo", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		
		overrideContainer.specifiedOverrides().next().convertToVirtual();
		attributeOverrideResources = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("foo", ((AttributeOverrideAnnotation) attributeOverrideResources.next()).getName());		
		assertFalse(attributeOverrideResources.hasNext());

		attributeOverrides = (Iterator<JavaAttributeOverride>) overrideContainer.specifiedOverrides();
		assertEquals("foo", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		
		overrideContainer.specifiedOverrides().next().convertToVirtual();
		attributeOverrideResources = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertFalse(attributeOverrideResources.hasNext());
		attributeOverrides = (Iterator<JavaAttributeOverride>) overrideContainer.specifiedOverrides();
		assertFalse(attributeOverrides.hasNext());

		assertNull(typeResource.getAnnotation(AttributeOverridesAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();

		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		Iterator<NestableAnnotation> javaAttributeOverrides = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaAttributeOverrides));
		
		
		overrideContainer.moveSpecifiedOverride(2, 0);
		ListIterator<AttributeOverride> attributeOverrides = (ListIterator<AttributeOverride>) overrideContainer.specifiedOverrides();
		assertEquals("name", attributeOverrides.next().getName());
		assertEquals("foo", attributeOverrides.next().getName());
		assertEquals("id", attributeOverrides.next().getName());

		javaAttributeOverrides = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("name", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("foo", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("id", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());


		overrideContainer.moveSpecifiedOverride(0, 1);
		attributeOverrides = (ListIterator<AttributeOverride>) overrideContainer.specifiedOverrides();
		assertEquals("foo", attributeOverrides.next().getName());
		assertEquals("name", attributeOverrides.next().getName());
		assertEquals("id", attributeOverrides.next().getName());

		javaAttributeOverrides = typeResource.annotations(AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("foo", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("name", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
		assertEquals("id", ((AttributeOverrideAnnotation) javaAttributeOverrides.next()).getName());
	}
//	
	public void testUpdateSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		((AttributeOverrideAnnotation) typeResource.addAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((AttributeOverrideAnnotation) typeResource.addAnnotation(1, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((AttributeOverrideAnnotation) typeResource.addAnnotation(2, AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		ListIterator<AttributeOverride> attributeOverrides = (ListIterator<AttributeOverride>) overrideContainer.specifiedOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		typeResource.moveAnnotation(2, 0, AttributeOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = (ListIterator<AttributeOverride>) overrideContainer.specifiedOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		typeResource.moveAnnotation(0, 1, AttributeOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = (ListIterator<AttributeOverride>) overrideContainer.specifiedOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		typeResource.removeAnnotation(1,  AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = (ListIterator<AttributeOverride>) overrideContainer.specifiedOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		typeResource.removeAnnotation(1,  AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = (ListIterator<AttributeOverride>) overrideContainer.specifiedOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		typeResource.removeAnnotation(0,  AttributeOverrideAnnotation.ANNOTATION_NAME, AttributeOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		attributeOverrides = (ListIterator<AttributeOverride>) overrideContainer.specifiedOverrides();
		assertFalse(attributeOverrides.hasNext());
	}

	public void testAttributeOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		
		ListIterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = (ListIterator<JavaVirtualAttributeOverride>) overrideContainer.virtualOverrides();	
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

		overrideContainer.virtualOverrides().next().convertToSpecified();
		AttributeOverride specifiedAttributeOverride = overrideContainer.specifiedOverrides().next();
		assertFalse(specifiedAttributeOverride.isVirtual());
		
		
		virtualAttributeOverrides = (ListIterator<JavaVirtualAttributeOverride>) overrideContainer.virtualOverrides();	
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

		Iterator<String> overridableAssociations = getJavaEntity().overridableAssociationNames();
		assertFalse(overridableAssociations.hasNext());
	}

	public void testOverridableAssociationNames() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<String> overridableAssociationNames = getJavaEntity().overridableAssociationNames();
		assertFalse(overridableAssociationNames.hasNext());
	}
	
//	//TODO add all mapping types to the mapped superclass to test which ones are overridable
	public void testAllOverridableAssociationNames() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Iterator<String> overridableAssociationNames = getJavaEntity().allOverridableAssociationNames();
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
		
		Iterator<String> overridableAssociations = getJavaEntity().allOverridableAssociationNames();
		assertEquals("address", overridableAssociations.next());
		assertEquals("address2", overridableAssociations.next());
		assertEquals("address3", overridableAssociations.next());
		assertEquals("address4", overridableAssociations.next());
		assertFalse(overridableAssociations.hasNext());
	}

	public void testSpecifiedAssociationOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		ListIterator<JavaAssociationOverride> specifiedAssociationOverrides = (ListIterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();
		
		assertFalse(specifiedAssociationOverrides.hasNext());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = (ListIterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(1, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = (ListIterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());


		associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = (ListIterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		typeResource.moveAnnotation(1, 0, JPA.ASSOCIATION_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = (ListIterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		typeResource.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = (ListIterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		typeResource.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = (ListIterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();		
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		
		typeResource.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverrides = (ListIterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();		
		assertFalse(specifiedAssociationOverrides.hasNext());
	}

	public void testVirtualAssociationOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().specifiedClassRefs();
		classRefs.next();
		JavaEntity javaEntity = (JavaEntity) classRefs.next().getJavaPersistentType().getMapping();
		AssociationOverrideContainer overrideContainer = javaEntity.getAssociationOverrideContainer();

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));
		
		assertEquals(4, overrideContainer.virtualOverridesSize());
		VirtualAssociationOverride virtualAssociationOverride = overrideContainer.virtualOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());
		

		//MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();
		//BasicMapping idMapping = (BasicMapping) mappedSuperclass.persistentType().attributeNamed("id").getMapping();
		//idMapping.getColumn().setSpecifiedName("FOO");
		//idMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));

		assertEquals(4, overrideContainer.virtualOverridesSize());
		virtualAssociationOverride = overrideContainer.virtualOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());

		//idMapping.getColumn().setSpecifiedName(null);
		//idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(SUB_TYPE_NAME, typeResource.getName());
		assertNull(typeResource.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));

		virtualAssociationOverride = overrideContainer.virtualOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());
		
		virtualAssociationOverride.convertToSpecified();
		assertEquals(3, overrideContainer.virtualOverridesSize());
		
		
		
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
////	assertEquals(SUB_TYPE_NAME, typeResource.getName());
////	assertNull(typeResource.annotation(AssociationOverride.ANNOTATION_NAME));
////	assertNull(typeResource.annotation(AssociationOverrides.ANNOTATION_NAME));
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
////	assertEquals(SUB_TYPE_NAME, typeResource.getName());
////	assertNull(typeResource.annotation(AssociationOverride.ANNOTATION_NAME));
////	assertNull(typeResource.annotation(AssociationOverrides.ANNOTATION_NAME));
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
		
		assertEquals(0, overrideContainer.specifiedOverridesSize());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);

		//add an annotation to the resource model and verify the context model is updated
		AssociationOverrideAnnotation associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("FOO");
		associationOverride = (AssociationOverrideAnnotation) typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		associationOverride.setName("BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(2, overrideContainer.specifiedOverridesSize());
	}
	
	public void testVirtualAssociationOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		
		assertEquals(4, overrideContainer.virtualOverridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(3, overrideContainer.virtualOverridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(2, overrideContainer.virtualOverridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(1, overrideContainer.virtualOverridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(0, overrideContainer.virtualOverridesSize());
	}
	
	public void testAssociationOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		
		assertEquals(4, overrideContainer.overridesSize());

		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(4, overrideContainer.overridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(4, overrideContainer.overridesSize());
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		AssociationOverrideAnnotation annotation = (AssociationOverrideAnnotation) typeResource.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		annotation.setName("bar");	
		getJpaProject().synchronizeContextModel();
		assertEquals(5, overrideContainer.overridesSize());
	}

	public void testAssociationOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
				
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		Iterator<NestableAnnotation> associationOverrides = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("address", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertEquals("address2", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertFalse(associationOverrides.hasNext());
	}
	
	public void testAssociationOverrideSetVirtual2() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		
		ListIterator<JavaVirtualAssociationOverride> virtualAssociationOverrides = (ListIterator<JavaVirtualAssociationOverride>) overrideContainer.virtualOverrides();
		virtualAssociationOverrides.next();
		virtualAssociationOverrides.next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		Iterator<NestableAnnotation> associationOverrides = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		
		assertEquals("address2", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertEquals("address", ((AssociationOverrideAnnotation) associationOverrides.next()).getName());
		assertFalse(associationOverrides.hasNext());
	}
	
	public void testAssociationOverrideSetVirtualTrue() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
				
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		assertEquals(2, CollectionTools.size(typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)));

		overrideContainer.specifiedOverrides().next().convertToVirtual();
		
		Iterator<NestableAnnotation> associationOverrideResources = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("address2", ((AssociationOverrideAnnotation) associationOverrideResources.next()).getName());		
		assertFalse(associationOverrideResources.hasNext());

		Iterator<JavaAssociationOverride> associationOverrides = (Iterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();
		assertEquals("address2", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		
		overrideContainer.specifiedOverrides().next().convertToVirtual();
		associationOverrideResources = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertFalse(associationOverrideResources.hasNext());
		associationOverrides = (Iterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();
		assertFalse(associationOverrides.hasNext());

		assertNull(typeResource.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedAssociationOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();

		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_SUB_TYPE_NAME);
		
		Iterator<NestableAnnotation> javaAssociationOverrides = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals(2, CollectionTools.size(javaAssociationOverrides));
		
		
		overrideContainer.moveSpecifiedOverride(1, 0);
		Iterator<JavaAssociationOverride> associationOverrides = (Iterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();
		assertEquals("address2", associationOverrides.next().getName());
		assertEquals("address", associationOverrides.next().getName());

		javaAssociationOverrides = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("address2", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
		assertEquals("address", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());


		overrideContainer.moveSpecifiedOverride(0, 1);
		associationOverrides = (Iterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();
		assertEquals("address", associationOverrides.next().getName());
		assertEquals("address2", associationOverrides.next().getName());

		javaAssociationOverrides = typeResource.annotations(AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		assertEquals("address", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
		assertEquals("address2", ((AssociationOverrideAnnotation) javaAssociationOverrides.next()).getName());
	}

	public void testUpdateSpecifiedAssociationOverrides() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
	
		((AssociationOverrideAnnotation) typeResource.addAnnotation(0, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)).setName("FOO");
		((AssociationOverrideAnnotation) typeResource.addAnnotation(1, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)).setName("BAR");
		((AssociationOverrideAnnotation) typeResource.addAnnotation(2, AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();
			
		Iterator<JavaAssociationOverride> associationOverrides = (Iterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();
		assertEquals("FOO", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		typeResource.moveAnnotation(2, 0, AssociationOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = (Iterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		typeResource.moveAnnotation(0, 1, AssociationOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = (Iterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		typeResource.removeAnnotation(1,  AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = (Iterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		typeResource.removeAnnotation(1,  AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = (Iterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		typeResource.removeAnnotation(0,  AssociationOverrideAnnotation.ANNOTATION_NAME, AssociationOverridesAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		associationOverrides = (Iterator<JavaAssociationOverride>) overrideContainer.specifiedOverrides();
		assertFalse(associationOverrides.hasNext());
	}

	public void testAssociationOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		
		ListIterator<JavaVirtualAssociationOverride> virtualAssociationOverrides = (ListIterator<JavaVirtualAssociationOverride>) overrideContainer.virtualOverrides();
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

		overrideContainer.virtualOverrides().next().convertToSpecified();
		AssociationOverride specifiedAssociationOverride = overrideContainer.specifiedOverrides().next();
		assertFalse(specifiedAssociationOverride.isVirtual());
		
		
		virtualAssociationOverrides = (ListIterator<JavaVirtualAssociationOverride>) overrideContainer.virtualOverrides();	
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
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().specifiedClassRefs();
		PersistentType customerPersistentType = specifiedClassRefs.next().getJavaPersistentType();
		PersistentType longTimeCustomerPersistentType = specifiedClassRefs.next().getJavaPersistentType();
		PersistentType addressPersistentType = specifiedClassRefs.next().getJavaPersistentType();		
		PersistentType zipCodePersistentType = specifiedClassRefs.next().getJavaPersistentType();

		AttributeOverrideContainer attributeOverrideContainer = ((Entity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		
		assertEquals(6, attributeOverrideContainer.virtualOverridesSize());
		ListIterator<VirtualAttributeOverride> virtualAttributeOverrides = (ListIterator<VirtualAttributeOverride>) attributeOverrideContainer.virtualOverrides();
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
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().specifiedClassRefs();
		PersistentType customerPersistentType = specifiedClassRefs.next().getJavaPersistentType();
		PersistentType longTimeCustomerPersistentType = specifiedClassRefs.next().getJavaPersistentType();
		PersistentType addressPersistentType = specifiedClassRefs.next().getJavaPersistentType();		
		PersistentType zipCodePersistentType = specifiedClassRefs.next().getJavaPersistentType();
		
		AttributeOverrideContainer attributeOverrideContainer = ((Entity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		
		assertEquals(6, attributeOverrideContainer.virtualOverridesSize());
		ListIterator<VirtualAttributeOverride> virtualAttributeOverrides = (ListIterator<VirtualAttributeOverride>) attributeOverrideContainer.virtualOverrides();
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
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().specifiedClassRefs();
		specifiedClassRefs.next();
		PersistentType longTimeCustomerPersistentType = specifiedClassRefs.next().getJavaPersistentType();

		AssociationOverrideContainer attributeOverrideContainer = ((Entity) longTimeCustomerPersistentType.getMapping()).getAssociationOverrideContainer();
		
		assertEquals(1, attributeOverrideContainer.virtualOverridesSize());
		ListIterator<VirtualAssociationOverride> virtualAssociationOverrides = (ListIterator<VirtualAssociationOverride>) attributeOverrideContainer.virtualOverrides();
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
		ListIterator<ClassRef> specifiedClassRefs = getPersistenceUnit().specifiedClassRefs();
		specifiedClassRefs.next();
		PersistentType longTimeCustomerPersistentType = specifiedClassRefs.next().getJavaPersistentType();

		AssociationOverrideContainer attributeOverrideContainer = ((Entity) longTimeCustomerPersistentType.getMapping()).getAssociationOverrideContainer();
		
		assertEquals(1, attributeOverrideContainer.virtualOverridesSize());
		ListIterator<VirtualAssociationOverride> virtualAssociationOverrides = (ListIterator<VirtualAssociationOverride>) attributeOverrideContainer.virtualOverrides();
		VirtualAssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address.state", virtualAssociationOverride.getName());
	}
	
	public void testSetSpecifiedCacheable() throws Exception {
		ICompilationUnit cu = createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 cacheable = ((CacheableHolder2_0) getJavaEntity()).getCacheable();
		Cacheable2_0Annotation cacheableAnnotation = (Cacheable2_0Annotation) getJavaPersistentType().getResourcePersistentType().getAnnotation(JPA2_0.CACHEABLE);
		assertEquals(null, cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation);
		
		cacheable.setSpecifiedCacheable(Boolean.FALSE);
		cacheableAnnotation = (Cacheable2_0Annotation) getJavaPersistentType().getResourcePersistentType().getAnnotation(JPA2_0.CACHEABLE);		
		assertEquals(Boolean.FALSE, cacheable.getSpecifiedCacheable());
		assertEquals(Boolean.FALSE, cacheableAnnotation.getValue());
		assertSourceContains("@Cacheable(false)", cu);
		
		cacheable.setSpecifiedCacheable(Boolean.TRUE);
		cacheableAnnotation = (Cacheable2_0Annotation) getJavaPersistentType().getResourcePersistentType().getAnnotation(JPA2_0.CACHEABLE);		
		assertEquals(Boolean.TRUE, cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation.getValue());
		assertSourceContains("@Cacheable", cu);
		
		cacheable.setSpecifiedCacheable(null);
		cacheableAnnotation = (Cacheable2_0Annotation) getJavaPersistentType().getResourcePersistentType().getAnnotation(JPA2_0.CACHEABLE);		
		assertEquals(null, cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation);
		assertSourceDoesNotContain("@Cacheable", cu);
	}
	
	public void testGetSpecifiedCacheable() throws Exception {
		ICompilationUnit cu = createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 cacheable = ((CacheableHolder2_0) getJavaEntity()).getCacheable();
		Cacheable2_0Annotation cacheableAnnotation = (Cacheable2_0Annotation) getJavaPersistentType().getResourcePersistentType().getAnnotation(JPA2_0.CACHEABLE);
		assertEquals(null, cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation);
		
		getJavaPersistentType().getResourcePersistentType().addAnnotation(JPA2_0.CACHEABLE);
		getJpaProject().synchronizeContextModel();
		cacheableAnnotation = (Cacheable2_0Annotation) getJavaPersistentType().getResourcePersistentType().getAnnotation(JPA2_0.CACHEABLE);
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

		getJavaPersistentType().getResourcePersistentType().removeAnnotation(JPA2_0.CACHEABLE);
		cacheableAnnotation = (Cacheable2_0Annotation) getJavaPersistentType().getResourcePersistentType().getAnnotation(JPA2_0.CACHEABLE);		
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

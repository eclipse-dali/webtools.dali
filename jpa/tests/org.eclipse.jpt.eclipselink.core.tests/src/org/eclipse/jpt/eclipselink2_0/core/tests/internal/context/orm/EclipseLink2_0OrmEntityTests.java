/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.InheritanceType;
import org.eclipse.jpt.core.context.VirtualAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmVirtualAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmVirtualAttributeOverride;
import org.eclipse.jpt.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkEntity;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkMappedSuperclass;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLink2_0OrmEntityTests extends EclipseLink2_0OrmContextModelTestCase
{
	protected static final String SUB_TYPE_NAME = "AnnotationTestTypeChild";
	protected static final String FULLY_QUALIFIED_SUB_TYPE_NAME = PACKAGE_NAME + "." + SUB_TYPE_NAME;
	
	
	public EclipseLink2_0OrmEntityTests(String name) {
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
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		OrmEntity entity = (OrmEntity) ormPersistentType.getMapping();

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
	}
	
	public void testOverridableAttributes() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity entity = (OrmEntity) ormPersistentType.getMapping();
	
		Iterator<String> overridableAttributes = entity.overridableAttributeNames();
		assertFalse(overridableAttributes.hasNext());
		
		
		entity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		
		overridableAttributes = entity.overridableAttributeNames();		
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}

	public void testOverridableAttributeNames() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity entity = (OrmEntity) ormPersistentType.getMapping();
	
		Iterator<String> overridableAttributeNames = entity.overridableAttributeNames();
		assertFalse(overridableAttributeNames.hasNext());
		
		
		entity.setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		
		overridableAttributeNames = entity.overridableAttributeNames();
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
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
	
		Iterator<String> overridableAttributes = ormEntity.allOverridableAttributeNames();
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertEquals("foo", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
		
		
		OrmEntity abstractEntity = (OrmEntity) ormEntity.getParentEntity();
		overridableAttributes = abstractEntity.allOverridableAttributeNames();
		assertEquals("id", overridableAttributes.next());
		assertEquals("name", overridableAttributes.next());
		assertEquals("foo", overridableAttributes.next());
		assertFalse(overridableAttributes.hasNext());
	}
	
	public void testAllOverridableAssociationsTablePerClass() throws Exception {
		createTestAbstractEntityTablePerClass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
	
		Iterator<String> overridableAssociations = ormEntity.allOverridableAssociationNames();
		assertEquals("address", overridableAssociations.next());
		assertEquals("address2", overridableAssociations.next());
		assertEquals("address3", overridableAssociations.next());
		assertEquals("address4", overridableAssociations.next());
		assertFalse(overridableAssociations.hasNext());
		
		
		OrmEntity abstractEntity = (OrmEntity) ormEntity.getParentEntity();
		overridableAssociations = abstractEntity.allOverridableAssociationNames();
		assertEquals("address", overridableAssociations.next());
		assertEquals("address2", overridableAssociations.next());
		assertEquals("address3", overridableAssociations.next());
		assertEquals("address4", overridableAssociations.next());
		assertFalse(overridableAssociations.hasNext());
	}
//TODO
//	public void testAllOverridableAttributesMappedSuperclassInOrmXml() throws Exception {
//		createTestMappedSuperclass();
//		createTestSubType();
//		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
//		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		
//		Iterator<PersistentAttribute> overridableAttributes = getJavaEntity().allOverridableAttributes();
//		assertEquals("id", overridableAttributes.next().getName());
//		assertEquals("name", overridableAttributes.next().getName());
//		assertEquals("foo", overridableAttributes.next().getName());
//		assertFalse(overridableAttributes.hasNext());
//	}

	public void testAllOverridableAttributeNames() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
	
		Iterator<String> overridableAttributeNames = ormEntity.allOverridableAttributeNames();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertEquals("foo", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}
		
	public void testSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity entity = (OrmEntity) ormPersistentType.getMapping();
		OrmAttributeOverrideContainer overrideContainer = entity.getAttributeOverrideContainer();
		
		ListIterator<OrmAttributeOverride> specifiedAttributeOverrides = overrideContainer.specifiedOverrides();
		assertFalse(specifiedAttributeOverrides.hasNext());

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		//add an annotation to the resource model and verify the context model is updated
		entityResource.getAttributeOverrides().add(0, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("FOO");
		specifiedAttributeOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		entityResource.getAttributeOverrides().add(1, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(1).setName("BAR");
		specifiedAttributeOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());


		entityResource.getAttributeOverrides().add(0, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("BAZ");
		specifiedAttributeOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		entityResource.getAttributeOverrides().move(1, 0);
		specifiedAttributeOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());

		entityResource.getAttributeOverrides().remove(0);
		specifiedAttributeOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("BAZ", specifiedAttributeOverrides.next().getName());
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
	
		entityResource.getAttributeOverrides().remove(0);
		specifiedAttributeOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("BAR", specifiedAttributeOverrides.next().getName());
		assertFalse(specifiedAttributeOverrides.hasNext());
		
		entityResource.getAttributeOverrides().remove(0);
		specifiedAttributeOverrides = overrideContainer.specifiedOverrides();		
		assertFalse(specifiedAttributeOverrides.hasNext());
	}

	public void testDefaultAttributeOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(0, entityResource.getAttributeOverrides().size());
		
		assertEquals(3, overrideContainer.virtualOverridesSize());
		VirtualAttributeOverride virtualAttributeOverride = overrideContainer.virtualOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		

		OrmMappedSuperclass mappedSuperclass = (OrmMappedSuperclass) ormEntity.getPersistentType().getSuperPersistentType().getMapping();
		
		mappedSuperclass.getPersistentType().getAttributeNamed("id").convertToSpecified();
		BasicMapping idMapping = (BasicMapping) mappedSuperclass.getPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(0, entityResource.getAttributeOverrides().size());

		assertEquals(3, overrideContainer.virtualOverridesSize());
		virtualAttributeOverride = overrideContainer.virtualOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("FOO", virtualAttributeOverride.getColumn().getName());
		assertEquals("BAR", virtualAttributeOverride.getColumn().getTable());

		idMapping.getColumn().setSpecifiedName(null);
		idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(0, entityResource.getAttributeOverrides().size());

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
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(0, entityResource.getAttributeOverrides().size());

		assertEquals(3, overrideContainer.virtualOverridesSize());
		VirtualAttributeOverride virtualAttributeOverride = overrideContainer.virtualOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		

		OrmEntity superclass = (OrmEntity) ormEntity.getParentEntity();
		
		superclass.getPersistentType().getAttributeNamed("id").convertToSpecified();
		BasicMapping idMapping = (BasicMapping) superclass.getPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.getColumn().setSpecifiedTable("BAR");
		
		assertEquals(0, entityResource.getAttributeOverrides().size());

		assertEquals(3, overrideContainer.virtualOverridesSize());
		virtualAttributeOverride = overrideContainer.virtualOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("FOO", virtualAttributeOverride.getColumn().getName());
		assertEquals("BAR", virtualAttributeOverride.getColumn().getTable());

		idMapping.getColumn().setSpecifiedName(null);
		idMapping.getColumn().setSpecifiedTable(null);
		assertEquals(0, entityResource.getAttributeOverrides().size());

		virtualAttributeOverride = overrideContainer.virtualOverrides().next();
		assertEquals("id", virtualAttributeOverride.getName());
		assertEquals("id", virtualAttributeOverride.getColumn().getName());
		assertEquals(SUB_TYPE_NAME, virtualAttributeOverride.getColumn().getTable());
		
		virtualAttributeOverride.convertToSpecified();
		assertEquals(2, overrideContainer.virtualOverridesSize());
	}
	
	public void testSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
		assertEquals(0, overrideContainer.specifiedOverridesSize());

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		//add an annotation to the resource model and verify the context model is updated
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("FOO");
		entityResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("BAR");

		assertEquals(2, overrideContainer.specifiedOverridesSize());
	}
	
	public void testDefaultAttributeOverridesSize() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
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
			
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
		assertEquals(3, overrideContainer.overridesSize());

		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(3, overrideContainer.overridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(3, overrideContainer.overridesSize());
		
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAttributeOverrides().add(0, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("bar");
		assertEquals(4, overrideContainer.overridesSize());
	}

	public void testAttributeOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
			
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
				
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals("id", entityResource.getAttributeOverrides().get(0).getName());		
		assertEquals("name", entityResource.getAttributeOverrides().get(1).getName());		
		assertEquals(2, entityResource.getAttributeOverrides().size());
	}
	
	public void testAttributeOverrideSetVirtual2() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
		ListIterator<OrmVirtualAttributeOverride> virtualAttributeOverrides = overrideContainer.virtualOverrides();
		virtualAttributeOverrides.next();
		virtualAttributeOverrides.next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals("name", entityResource.getAttributeOverrides().get(0).getName());
		assertEquals("id", entityResource.getAttributeOverrides().get(1).getName());		
		assertEquals(2, entityResource.getAttributeOverrides().size());
	}
	
	public void testAttributeOverrideSetVirtualTrue() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
				
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(3, entityResource.getAttributeOverrides().size());

		overrideContainer.specifiedOverrides().next().convertToVirtual();
		
		assertEquals("name", entityResource.getAttributeOverrides().get(0).getName());		
		assertEquals("foo", entityResource.getAttributeOverrides().get(1).getName());
		assertEquals(2, entityResource.getAttributeOverrides().size());
		
		Iterator<OrmAttributeOverride> attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("name", attributeOverrides.next().getName());		
		assertEquals("foo", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		
		overrideContainer.specifiedOverrides().next().convertToVirtual();
		assertEquals("foo", entityResource.getAttributeOverrides().get(0).getName());		
		assertEquals(1, entityResource.getAttributeOverrides().size());

		attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("foo", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		
		overrideContainer.specifiedOverrides().next().convertToVirtual();
		assertEquals(0, entityResource.getAttributeOverrides().size());
		attributeOverrides = overrideContainer.specifiedOverrides();
		assertFalse(attributeOverrides.hasNext());
	}
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();

		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals(3, entityResource.getAttributeOverrides().size());
		
		
		overrideContainer.moveSpecifiedOverride(2, 0);
		ListIterator<OrmAttributeOverride> attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("name", attributeOverrides.next().getName());
		assertEquals("foo", attributeOverrides.next().getName());
		assertEquals("id", attributeOverrides.next().getName());

		assertEquals("name", entityResource.getAttributeOverrides().get(0).getName());
		assertEquals("foo", entityResource.getAttributeOverrides().get(1).getName());
		assertEquals("id", entityResource.getAttributeOverrides().get(2).getName());


		overrideContainer.moveSpecifiedOverride(0, 1);
		attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("foo", attributeOverrides.next().getName());
		assertEquals("name", attributeOverrides.next().getName());
		assertEquals("id", attributeOverrides.next().getName());

		assertEquals("foo", entityResource.getAttributeOverrides().get(0).getName());
		assertEquals("name", entityResource.getAttributeOverrides().get(1).getName());
		assertEquals("id", entityResource.getAttributeOverrides().get(2).getName());
	}
	
	public void testUpdateSpecifiedAttributeOverrides() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
	
		entityResource.getAttributeOverrides().add(0, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(0).setName("FOO");
		entityResource.getAttributeOverrides().add(1, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(1).setName("BAR");
		entityResource.getAttributeOverrides().add(2, OrmFactory.eINSTANCE.createXmlAttributeOverride());
		entityResource.getAttributeOverrides().get(2).setName("BAZ");
			
		ListIterator<OrmAttributeOverride> attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		entityResource.getAttributeOverrides().move(2, 0);
		attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		entityResource.getAttributeOverrides().move(0, 1);
		attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		entityResource.getAttributeOverrides().remove(1);
		attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
	
		entityResource.getAttributeOverrides().remove(1);
		attributeOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		entityResource.getAttributeOverrides().remove(0);
		attributeOverrides = overrideContainer.specifiedOverrides();
		assertFalse(attributeOverrides.hasNext());
	}

	public void testAttributeOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAttributeOverrideContainer overrideContainer = ormEntity.getAttributeOverrideContainer();
		
		ListIterator<OrmVirtualAttributeOverride> virtualAttributeOverrides = overrideContainer.virtualOverrides();	
		OrmVirtualAttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
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
		
		
		virtualAttributeOverrides = overrideContainer.virtualOverrides();	
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("name", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("foo", virtualAttributeOverride.getName());
		assertTrue(virtualAttributeOverride.isVirtual());
		assertFalse(virtualAttributeOverrides.hasNext());
	}

	public void testOverridableAssociationNames() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		OrmEntity entity = (OrmEntity) ormPersistentType.getMapping();

		Iterator<String> overridableAssociationNames = entity.overridableAssociationNames();
		assertFalse(overridableAssociationNames.hasNext());
	}
	
//	//TODO add all mapping types to the mapped superclass to test which ones are overridable
	public void testAllOverridableAssociationNames() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();

		Iterator<String> overridableAssociationNames = ormEntity.allOverridableAssociationNames();
		assertEquals("address", overridableAssociationNames.next());
		assertEquals("address2", overridableAssociationNames.next());
		assertEquals("address3", overridableAssociationNames.next());
		assertEquals("address4", overridableAssociationNames.next());
		assertFalse(overridableAssociationNames.hasNext());
	}
	
	public void testAllOverridableAssociations() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
	
		Iterator<String> overridableAssociations = ormEntity.allOverridableAssociationNames();
		assertEquals("address", overridableAssociations.next());
		assertEquals("address2", overridableAssociations.next());
		assertEquals("address3", overridableAssociations.next());
		assertEquals("address4", overridableAssociations.next());
		assertFalse(overridableAssociations.hasNext());
	}

	public void testSpecifiedAssociationOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		ListIterator<OrmAssociationOverride> specifiedAssociationOverrides = overrideContainer.specifiedOverrides();
		
		assertFalse(specifiedAssociationOverrides.hasNext());

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		//add an annotation to the resource model and verify the context model is updated
		XmlAssociationOverride xmlAssociationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		entityResource.getAssociationOverrides().add(0, xmlAssociationOverride);
		xmlAssociationOverride.setName("FOO");
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		xmlAssociationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		entityResource.getAssociationOverrides().add(1, xmlAssociationOverride);
		xmlAssociationOverride.setName("BAR");
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());


		xmlAssociationOverride = OrmFactory.eINSTANCE.createXmlAssociationOverride();
		entityResource.getAssociationOverrides().add(0, xmlAssociationOverride);
		xmlAssociationOverride.setName("BAZ");
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		entityResource.getAssociationOverrides().move(1, 0);
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("FOO", specifiedAssociationOverrides.next().getName());
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		entityResource.getAssociationOverrides().remove(0);
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("BAZ", specifiedAssociationOverrides.next().getName());
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());
	
		entityResource.getAssociationOverrides().remove(0);
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertEquals("BAR", specifiedAssociationOverrides.next().getName());
		assertFalse(specifiedAssociationOverrides.hasNext());

		
		entityResource.getAssociationOverrides().remove(0);
		specifiedAssociationOverrides = overrideContainer.specifiedOverrides();		
		assertFalse(specifiedAssociationOverrides.hasNext());
	}

	public void testDefaultAssociationOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(FULLY_QUALIFIED_SUB_TYPE_NAME, entityResource.getClassName());
		assertTrue(entityResource.getAssociationOverrides().isEmpty());
		
		assertEquals(4, overrideContainer.virtualOverridesSize());
		OrmVirtualAssociationOverride virtualAssociationOverride = overrideContainer.virtualOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());
		
//
//		//MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();
//		//BasicMapping idMapping = (BasicMapping) mappedSuperclass.persistentType().attributeNamed("id").getMapping();
//		//idMapping.getColumn().setSpecifiedName("FOO");
//		//idMapping.getColumn().setSpecifiedTable("BAR");
//		
//		assertEquals(SUB_TYPE_NAME, entityResource.getName());
//		assertNull(typeResource.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
//		assertNull(typeResource.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));

		assertEquals(4, overrideContainer.virtualOverridesSize());
		virtualAssociationOverride = overrideContainer.virtualOverrides().next();
		assertEquals("address", virtualAssociationOverride.getName());

//		//idMapping.getColumn().setSpecifiedName(null);
//		//idMapping.getColumn().setSpecifiedTable(null);
//		assertEquals(SUB_TYPE_NAME, typeResource.getName());
//		assertNull(typeResource.getAnnotation(AssociationOverrideAnnotation.ANNOTATION_NAME));
//		assertNull(typeResource.getAnnotation(AssociationOverridesAnnotation.ANNOTATION_NAME));

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
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();

		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals(0, overrideContainer.specifiedOverridesSize());

		//add to the resource model and verify the context model is updated
		entityResource.getAssociationOverrides().add(OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().get(0).setName("FOO");
		entityResource.getAssociationOverrides().add(0, OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().get(0).setName("BAR");

		assertEquals(2, overrideContainer.specifiedOverridesSize());
	}
	
	public void testDefaultAssociationOverridesSize() throws Exception {		
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
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

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		assertEquals(4, overrideContainer.overridesSize());

		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(4, overrideContainer.overridesSize());
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		assertEquals(4, overrideContainer.overridesSize());
		
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		entityResource.getAssociationOverrides().add(0, OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().get(0).setName("bar");
		assertEquals(5, overrideContainer.overridesSize());
	}

	public void testAssociationOverrideSetVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		AssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
				
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals("address", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("address2", entityResource.getAssociationOverrides().get(1).getName());
		assertEquals(2, entityResource.getAssociationOverrides().size());
	}
	
	public void testAssociationOverrideSetVirtual2() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		ListIterator<OrmVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.virtualOverrides();
		virtualAssociationOverrides.next();
		virtualAssociationOverrides.next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		assertEquals("address2", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("address", entityResource.getAssociationOverrides().get(1).getName());
		assertEquals(2, entityResource.getAssociationOverrides().size());
	}
	
	public void testAssociationOverrideSetVirtualTrue() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
				
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(2, entityResource.getAssociationOverrides().size());

		overrideContainer.specifiedOverrides().next().convertToVirtual();
		
		assertEquals("address2", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals(1, entityResource.getAssociationOverrides().size());

		Iterator<OrmAssociationOverride> associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("address2", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());

		
		overrideContainer.specifiedOverrides().next().convertToVirtual();
		assertEquals(0, entityResource.getAssociationOverrides().size());
		associationOverrides = overrideContainer.specifiedOverrides();
		assertFalse(associationOverrides.hasNext());
	}
	
	public void testMoveSpecifiedAssociationOverride() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		overrideContainer.virtualOverrides().next().convertToSpecified();
		overrideContainer.virtualOverrides().next().convertToSpecified();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(2, entityResource.getAssociationOverrides().size());
		
		
		overrideContainer.moveSpecifiedOverride(1, 0);
		ListIterator<OrmAssociationOverride> associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("address2", associationOverrides.next().getName());
		assertEquals("address", associationOverrides.next().getName());

		assertEquals("address2", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("address", entityResource.getAssociationOverrides().get(1).getName());


		overrideContainer.moveSpecifiedOverride(0, 1);
		associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("address", associationOverrides.next().getName());
		assertEquals("address2", associationOverrides.next().getName());

		assertEquals("address", entityResource.getAssociationOverrides().get(0).getName());
		assertEquals("address2", entityResource.getAssociationOverrides().get(1).getName());
	}

	public void testUpdateSpecifiedAssociationOverrides() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		
		entityResource.getAssociationOverrides().add(0, OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().get(0).setName("FOO");
		entityResource.getAssociationOverrides().add(1, OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().get(1).setName("BAR");
		entityResource.getAssociationOverrides().add(2, OrmFactory.eINSTANCE.createXmlAssociationOverride());
		entityResource.getAssociationOverrides().get(2).setName("BAZ");
			
		ListIterator<OrmAssociationOverride> associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("FOO", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		entityResource.getAssociationOverrides().move(2, 0);
		associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		entityResource.getAssociationOverrides().move(0, 1);
		associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("BAR", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		entityResource.getAssociationOverrides().remove(1);
		associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertEquals("FOO", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
	
		entityResource.getAssociationOverrides().remove(1);
		associationOverrides = overrideContainer.specifiedOverrides();
		assertEquals("BAZ", associationOverrides.next().getName());
		assertFalse(associationOverrides.hasNext());
		
		entityResource.getAssociationOverrides().remove(0);
		associationOverrides = overrideContainer.specifiedOverrides();
		assertFalse(associationOverrides.hasNext());
	}

	public void testAssociationOverrideIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "test.AnnotationTestTypeChild");
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmAssociationOverrideContainer overrideContainer = ormEntity.getAssociationOverrideContainer();
		
		ListIterator<OrmVirtualAssociationOverride> virtualAssociationOverrides = overrideContainer.virtualOverrides();	
		OrmVirtualAssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
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
		
		
		virtualAssociationOverrides = overrideContainer.virtualOverrides();	
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
		
		OrmPersistentType customerPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Customer");
		OrmPersistentType longTimeCustomerPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".LongTimeCustomer");
		OrmPersistentType addressPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		OrmPersistentType zipCodePersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".ZipCode");

		OrmAttributeOverrideContainer attributeOverrideContainer = ((OrmEntity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		
		assertEquals(6, attributeOverrideContainer.virtualOverridesSize());
		ListIterator<OrmVirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.virtualOverrides();
		OrmVirtualAttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
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

		
		zipCodePersistentType.getAttributeNamed("plusfour").convertToSpecified();
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

		attributeOverrideContainer = ((OrmEntity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		//check the top-level embedded (Customer.address) attribute override to verify it is getting settings from the specified column on Zipcode.plusfour
		virtualAttributeOverride = (OrmVirtualAttributeOverride) attributeOverrideContainer.getOverrideNamed("address.zipCode.plusfour");
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
		addressPersistentType.getAttributeNamed("zipCode").convertToSpecified();
		AttributeOverride specifiedAttributeOverride = ((VirtualAttributeOverride) ((EmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping()).getAttributeOverrideContainer().getOverrideNamed("plusfour")).convertToSpecified();
		specifiedAttributeOverride.getColumn().setSpecifiedName("BLAH_OVERRIDE");
		specifiedAttributeOverride.getColumn().setSpecifiedTable("BLAH_TABLE_OVERRIDE");
		specifiedAttributeOverride.getColumn().setColumnDefinition("COLUMN_DEFINITION_OVERRIDE");


		attributeOverrideContainer = ((OrmEntity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		virtualAttributeOverride = (OrmVirtualAttributeOverride) attributeOverrideContainer.getOverrideNamed("address.zipCode.plusfour");
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
		
		OrmPersistentType customerPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Customer");
		OrmPersistentType longTimeCustomerPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".LongTimeCustomer");
		OrmPersistentType addressPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		OrmPersistentType zipCodePersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".ZipCode");

		customerPersistentType.getJavaPersistentType().getAttributeNamed("address").setMappingKey(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		OrmAttributeOverrideContainer attributeOverrideContainer = ((OrmEntity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		
		assertEquals(6, attributeOverrideContainer.virtualOverridesSize());
		ListIterator<OrmVirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.virtualOverrides();
		OrmVirtualAttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
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

		
		zipCodePersistentType.getAttributeNamed("plusfour").convertToSpecified();
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

		attributeOverrideContainer = ((OrmEntity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		//check the top-level embedded (Customer.address) attribute override to verify it is getting settings from the specified column on Zipcode.plusfour
		virtualAttributeOverride = (OrmVirtualAttributeOverride) attributeOverrideContainer.getOverrideNamed("address.zipCode.plusfour");
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
		addressPersistentType.getAttributeNamed("zipCode").convertToSpecified();
		AttributeOverride specifiedAttributeOverride = ((VirtualAttributeOverride) ((EmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping()).getAttributeOverrideContainer().getOverrideNamed("plusfour")).convertToSpecified();
		specifiedAttributeOverride.getColumn().setSpecifiedName("BLAH_OVERRIDE");
		specifiedAttributeOverride.getColumn().setSpecifiedTable("BLAH_TABLE_OVERRIDE");
		specifiedAttributeOverride.getColumn().setColumnDefinition("COLUMN_DEFINITION_OVERRIDE");


		attributeOverrideContainer = ((OrmEntity) longTimeCustomerPersistentType.getMapping()).getAttributeOverrideContainer();
		virtualAttributeOverride = (OrmVirtualAttributeOverride) attributeOverrideContainer.getOverrideNamed("address.zipCode.plusfour");
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
		
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Customer");
		OrmPersistentType longTimeCustomerPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".LongTimeCustomer");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".ZipCode");

		OrmAssociationOverrideContainer attributeOverrideContainer = ((OrmEntity) longTimeCustomerPersistentType.getMapping()).getAssociationOverrideContainer();
		
		assertEquals(1, attributeOverrideContainer.virtualOverridesSize());
		ListIterator<OrmVirtualAssociationOverride> virtualAssociationOverrides = attributeOverrideContainer.virtualOverrides();
		OrmVirtualAssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address.state", virtualAssociationOverride.getName());
	}

	public void testNestedVirtualAssociationOverridesElementCollection() throws Exception {
		createTestMappedSuperclassCustomerWithElementCollection();
		createTestEntityLongTimeCustomer();
		createTestEmbeddableAddress();
		createTestEmbeddableZipCode();
		
		getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Customer");
		OrmPersistentType longTimeCustomerPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".LongTimeCustomer");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".ZipCode");

		OrmAssociationOverrideContainer attributeOverrideContainer = ((OrmEntity) longTimeCustomerPersistentType.getMapping()).getAssociationOverrideContainer();
		
		assertEquals(1, attributeOverrideContainer.virtualOverridesSize());
		ListIterator<OrmVirtualAssociationOverride> virtualAssociationOverrides = attributeOverrideContainer.virtualOverrides();
		OrmVirtualAssociationOverride virtualAssociationOverride = virtualAssociationOverrides.next();
		assertEquals("address.state", virtualAssociationOverride.getName());
	}
	
	public void testSetSpecifiedCacheable() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 cacheable2_0 = ((CacheableHolder2_0) ormPersistentType.getMapping()).getCacheable();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(null, cacheable2_0.getSpecifiedCacheable());
		assertEquals(null, entityResource.getCacheable());
		
		cacheable2_0.setSpecifiedCacheable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, cacheable2_0.getSpecifiedCacheable());
		assertEquals(Boolean.FALSE, entityResource.getCacheable());
		
		cacheable2_0.setSpecifiedCacheable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, cacheable2_0.getSpecifiedCacheable());
		assertEquals(Boolean.TRUE, entityResource.getCacheable());
		
		cacheable2_0.setSpecifiedCacheable(null);
		assertEquals(null, cacheable2_0.getSpecifiedCacheable());
		assertEquals(null, entityResource.getCacheable());
	}
	
	public void testGetSpecifiedCacheable() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 cacheable = ((CacheableHolder2_0) ormPersistentType.getMapping()).getCacheable();
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		assertEquals(null, cacheable.getSpecifiedCacheable());
		assertEquals(null, entityResource.getCacheable());
		
		entityResource.setCacheable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, cacheable.getSpecifiedCacheable());
		assertEquals(Boolean.TRUE, entityResource.getCacheable());

		entityResource.setCacheable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, cacheable.getSpecifiedCacheable());
		assertEquals(Boolean.FALSE, entityResource.getCacheable());
		
		entityResource.setCacheable(null);
		assertEquals(null, cacheable.getSpecifiedCacheable());
		assertEquals(null, entityResource.getCacheable());
	}
	
	public void testIsDefaultCacheable() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 cacheable = ((CacheableHolder2_0) ormPersistentType.getMapping()).getCacheable();
		PersistenceUnit2_0 persistenceUnit2_0 = getPersistenceUnit();
		assertEquals(SharedCacheMode.DISABLE_SELECTIVE, persistenceUnit2_0.getSharedCacheMode());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.ALL);
		assertEquals(true, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.NONE);
		assertEquals(false, cacheable.isDefaultCacheable());

		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
		assertEquals(false, cacheable.isDefaultCacheable());

		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.DISABLE_SELECTIVE);
		assertEquals(true, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.UNSPECIFIED);
		assertEquals(true, cacheable.isDefaultCacheable());
	}
	
	public void testIsDefaultCacheableFromSuperType() throws Exception {
		createTestEntity();
		createTestSubType();
		OrmPersistentType subOrmPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_SUB_TYPE_NAME);
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEntity subEntity = (EclipseLinkEntity) subOrmPersistentType.getMapping();
		EclipseLinkEntity entity = (EclipseLinkEntity) ormPersistentType.getMapping();
		Cacheable2_0 subCacheable = ((CacheableHolder2_0) subEntity).getCacheable();
		Cacheable2_0 cacheable = ((CacheableHolder2_0) entity).getCacheable();
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
	
	public void testIsDefaultCacheableFromJava() throws Exception {
		createTestEntity();
		createTestSubType();
		OrmPersistentType subOrmPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_SUB_TYPE_NAME);
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 subCacheable = ((CacheableHolder2_0) subOrmPersistentType.getMapping()).getCacheable();
		Cacheable2_0 cacheable = ((CacheableHolder2_0) ormPersistentType.getMapping()).getCacheable();
		
		Cacheable2_0 javaCacheable = ((CacheableHolder2_0) ormPersistentType.getJavaPersistentType().getMapping()).getCacheable();
		javaCacheable.setSpecifiedCacheable(Boolean.TRUE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		PersistenceUnit2_0 persistenceUnit2_0 = getPersistenceUnit();
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.DISABLE_SELECTIVE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
				
		javaCacheable.setSpecifiedCacheable(Boolean.FALSE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		javaCacheable.setSpecifiedCacheable(Boolean.TRUE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode.DISABLE_SELECTIVE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		javaCacheable.setSpecifiedCacheable(Boolean.FALSE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
	}
	
	public void testIsDefaultCacheableFromMappedSuperClass() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		OrmPersistentType subOrmPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_SUB_TYPE_NAME);
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEntity subEntity = (EclipseLinkEntity) subOrmPersistentType.getMapping();
		EclipseLinkMappedSuperclass mappedSuperclass = (EclipseLinkMappedSuperclass) ormPersistentType.getMapping();
		Cacheable2_0 subCacheable = ((CacheableHolder2_0) subEntity).getCacheable();
		Cacheable2_0 cacheable = ((CacheableHolder2_0) mappedSuperclass).getCacheable();
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

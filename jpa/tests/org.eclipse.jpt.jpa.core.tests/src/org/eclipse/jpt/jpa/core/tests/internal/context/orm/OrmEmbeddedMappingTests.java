/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.SpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.context.VirtualColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmEmbeddedMappingTests extends ContextModelTestCase
{
	private static final String ATTRIBUTE_OVERRIDE_NAME = "city";
	private static final String ATTRIBUTE_OVERRIDE_COLUMN_NAME = "E_CITY";

	public OrmEmbeddedMappingTests(String name) {
		super(name);
	}
		
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	private ICompilationUnit createTestEntityEmbeddedMapping() throws Exception {
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
				sb.append("    @AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\", column=@Column(name=\"" + ATTRIBUTE_OVERRIDE_COLUMN_NAME + "\"))").append(CR);
				sb.append(CR);
				sb.append("    private Address address;").append(CR);
				sb.append(CR);
				sb.append("    @Id");				
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
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.EMBEDDABLE);
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
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.EMBEDDABLE);
					sb.append(";");
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
		createTestEntityEmbeddedMapping();
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
		createTestEntityEmbeddedMapping();
		createTestEmbeddableAddress();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType persistentType2 = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME_ + "Address");
		
		//embedded mapping is virtual, Java attribute overrides should exist
		OrmPersistentAttribute ormPersistentAttribute = persistentType.getAttributeNamed("address");
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) ormPersistentAttribute.getMapping();
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		assertEquals(4, attributeOverrideContainer.getOverridesSize());
		assertEquals(3, attributeOverrideContainer.getVirtualOverridesSize());
		assertEquals(1, attributeOverrideContainer.getSpecifiedOverridesSize());
		ListIterator<JavaSpecifiedAttributeOverride> specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		SpecifiedAttributeOverride attributeOverride = specifiedAttributeOverrides.next();
		assertEquals("city", attributeOverride.getName());
		ListIterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		JavaVirtualAttributeOverride virtualOverride = virtualAttributeOverrides.next();
		assertEquals("id", virtualOverride.getName());
		virtualOverride = virtualAttributeOverrides.next();
		assertEquals("state", virtualOverride.getName());
		virtualOverride = virtualAttributeOverrides.next();
		assertEquals("zip", virtualOverride.getName());
		
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
		embeddedMapping = (JavaEmbeddedMapping) ormPersistentAttribute.getMapping();
		attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
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
		virtualOverride = virtualAttributeOverrides.next();
		assertEquals("id", virtualOverride.getName());
		virtualOverride = virtualAttributeOverrides.next();
		assertEquals("state", virtualOverride.getName());
		assertEquals("MY_STATE_COLUMN", virtualOverride.getColumn().getName());
		virtualOverride = virtualAttributeOverrides.next();
		assertEquals("zip", virtualOverride.getName());
		
		
		//embedded mapping is specified, virtual attribute overrides should exist
		persistentType.getAttributeNamed("address").addToXml();
		OrmEmbeddedMapping ormMapping = (OrmEmbeddedMapping) persistentType.getAttributeNamed("address").getMapping();
		OrmAttributeOverrideContainer ormOverrideContainer = ormMapping.getAttributeOverrideContainer();
		assertEquals(4, ormOverrideContainer.getOverridesSize());
		assertEquals(4, ormOverrideContainer.getVirtualOverridesSize());
		assertEquals(0, ormOverrideContainer.getSpecifiedOverridesSize());
		ListIterator<OrmVirtualAttributeOverride> ormVirtualAttributeOverrides = ormOverrideContainer.getVirtualOverrides().iterator();
		OrmVirtualAttributeOverride ormVirtualOverride = ormVirtualAttributeOverrides.next();
		assertEquals("id", ormVirtualOverride.getName());
		ormVirtualOverride = ormVirtualAttributeOverrides.next();
		assertEquals("city", ormVirtualOverride.getName());
		assertEquals("city", ormVirtualOverride.getColumn().getName());
		assertEquals(TYPE_NAME, ormVirtualOverride.getColumn().getTableName());
		assertEquals(null, ormVirtualOverride.getColumn().getColumnDefinition());
		assertEquals(true, ormVirtualOverride.getColumn().isInsertable());
		assertEquals(true, ormVirtualOverride.getColumn().isUpdatable());
		assertEquals(false, ormVirtualOverride.getColumn().isUnique());
		assertEquals(true, ormVirtualOverride.getColumn().isNullable());
		assertEquals(255, ormVirtualOverride.getColumn().getLength());
		assertEquals(0, ormVirtualOverride.getColumn().getPrecision());
		assertEquals(0, ormVirtualOverride.getColumn().getScale());
		ormVirtualOverride = ormVirtualAttributeOverrides.next();
		assertEquals("state", ormVirtualOverride.getName());
		assertEquals("MY_STATE_COLUMN", ormVirtualOverride.getColumn().getDefaultName());
		assertEquals(TYPE_NAME, ormVirtualOverride.getColumn().getDefaultTableName());
		ormVirtualOverride = ormVirtualAttributeOverrides.next();
		assertEquals("zip", ormVirtualOverride.getName());
		
		//set one of the virtual attribute overrides to specified, verify others are still virtual
		ormOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		assertEquals(4, ormOverrideContainer.getOverridesSize());
		assertEquals(1, ormOverrideContainer.getSpecifiedOverridesSize());
		assertEquals(3, ormOverrideContainer.getVirtualOverridesSize());
		assertEquals("id", ormOverrideContainer.getSpecifiedOverrides().iterator().next().getName());
		ormVirtualAttributeOverrides = ormOverrideContainer.getVirtualOverrides().iterator();
		ormVirtualOverride = ormVirtualAttributeOverrides.next();
		assertEquals("city", ormVirtualOverride.getName());
		ormVirtualOverride = ormVirtualAttributeOverrides.next();
		assertEquals("state", ormVirtualOverride.getName());
		ormVirtualOverride = ormVirtualAttributeOverrides.next();
		assertEquals("zip", ormVirtualOverride.getName());
	}

	
	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityEmbeddedMapping();
		createTestEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		
		JavaEmbeddedMapping embeddedMapping = (JavaEmbeddedMapping) ormPersistentAttribute.getMapping();	
		JavaAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		assertEquals("address", embeddedMapping.getName());

		assertEquals(4, attributeOverrideContainer.getOverridesSize());
		assertEquals(1, attributeOverrideContainer.getSpecifiedOverridesSize());
		assertEquals(3, attributeOverrideContainer.getVirtualOverridesSize());

		ListIterator<JavaSpecifiedAttributeOverride> specifiedAttributeOverrides = attributeOverrideContainer.getSpecifiedOverrides().iterator();
		JavaSpecifiedAttributeOverride specifiedAttributeOverride = specifiedAttributeOverrides.next();
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, specifiedAttributeOverride.getName());
		Column column = specifiedAttributeOverride.getColumn();
		assertEquals(ATTRIBUTE_OVERRIDE_COLUMN_NAME, column.getSpecifiedName());

	
		ListIterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		JavaVirtualAttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", virtualAttributeOverride.getName());
		VirtualColumn virtualColumn = virtualAttributeOverride.getColumn();
		assertEquals("id", virtualColumn.getName());

		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("state", virtualAttributeOverride.getName());
		virtualColumn = virtualAttributeOverride.getColumn();
		assertEquals("A_STATE", virtualColumn.getName());

		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("zip", virtualAttributeOverride.getName());
		virtualColumn = virtualAttributeOverride.getColumn();
		assertEquals("zip", virtualColumn.getName());

	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityEmbeddedMapping();
		createTestEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(3, ormPersistentType.getDefaultAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();	
		assertEquals("address", embeddedMapping.getName());

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
		createTestEntityEmbeddedMapping();
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
		createTestEntityEmbeddedMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		SpecifiedAttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
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
		createTestEntityEmbeddedMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		SpecifiedAttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
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
		createTestEntityEmbeddedMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		SpecifiedAttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
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
		createTestEntityEmbeddedMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		SpecifiedAttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
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
		createTestEntityEmbeddedMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		SpecifiedAttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
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
		createTestEntityEmbeddedMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		SpecifiedAttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
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
		createTestEntityEmbeddedMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		SpecifiedAttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
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
		createTestEntityEmbeddedMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		SpecifiedAttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
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
		createTestEntityEmbeddedMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		EmbeddedMapping embeddedMapping = (EmbeddedMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedMapping.isDefault());
		XmlEmbedded embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddeds().get(0);
		embeddedResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		SpecifiedAttributeOverride attributeOverride = embeddedMapping.getAttributeOverrideContainer().getSpecifiedOverrides().iterator().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}

	//1.0 projects do not support dot-notation, this tests to make sure that support does not exist
	public void testNestedVirtualAttributeOverrides() throws Exception {
		createTestEntityCustomer();
		createTestEmbeddableAddress2();
		createTestEmbeddableZipCode();
		
		OrmPersistentType customerPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Customer");
		OrmPersistentType addressPersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		OrmPersistentType zipCodePersistentType = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".ZipCode");

		customerPersistentType.getAttributeNamed("address").addToXml();
		OrmEmbeddedMapping embeddedMapping = (OrmEmbeddedMapping) customerPersistentType.getAttributeNamed("address").getMapping();
		OrmAttributeOverrideContainer attributeOverrideContainer = embeddedMapping.getAttributeOverrideContainer();
		
		assertEquals(3, attributeOverrideContainer.getVirtualOverridesSize());
		ListIterator<OrmVirtualAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.getVirtualOverrides().iterator();
		OrmVirtualAttributeOverride virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("street", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("city", virtualAttributeOverride.getName());
		virtualAttributeOverride = virtualAttributeOverrides.next();
		assertEquals("state", virtualAttributeOverride.getName());
		assertEquals(false, virtualAttributeOverrides.hasNext());

		addressPersistentType.getAttributeNamed("zipCode").addToXml();
		OrmEmbeddedMapping nestedEmbeddedMapping = (OrmEmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping();
		OrmAttributeOverrideContainer nestedAttributeOverrideContainer = nestedEmbeddedMapping.getAttributeOverrideContainer();
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
		virtualAttributeOverride = (OrmVirtualAttributeOverride) ((OrmEmbeddedMapping) addressPersistentType.getAttributeNamed("zipCode").getMapping()).getAttributeOverrideContainer().getOverrideNamed("plusfour");
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

		//check the top-level embedded (Customer.address) attribute override to verify there is no attribute override named zipCode.plusfour
		virtualAttributeOverride = (OrmVirtualAttributeOverride) ((OrmEmbeddedMapping) customerPersistentType.getAttributeNamed("address").getMapping()).getAttributeOverrideContainer().getOverrideNamed("zipCode.plusfour");
	}

}
/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class OrmEmbeddedIdMappingTests extends ContextModelTestCase
{
	private static final String ATTRIBUTE_OVERRIDE_NAME = "city";
	private static final String ATTRIBUTE_OVERRIDE_COLUMN_NAME = "E_CITY";

	public OrmEmbeddedIdMappingTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	private ICompilationUnit createTestEntityEmbeddedIdMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.EMBEDDED_ID, JPA.ATTRIBUTE_OVERRIDE, JPA.COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @EmbeddedId");
				sb.append(CR);
				sb.append("    @AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\", column=@Column(name=\"" + ATTRIBUTE_OVERRIDE_COLUMN_NAME + "\"))");
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

//	public void testUpdateName() throws Exception {
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
//		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) ormPersistentAttribute.getMapping();
//		EmbeddedId embeddedIdResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
//		
//		assertEquals("embeddedIdMapping", xmlEmbeddedIdMapping.getName());
//		assertEquals("embeddedIdMapping", embeddedIdResource.getName());
//				
//		//set name in the resource model, verify context model updated
//		embeddedIdResource.setName("newName");
//		assertEquals("newName", xmlEmbeddedIdMapping.getName());
//		assertEquals("newName", embeddedIdResource.getName());
//	
//		//set name to null in the resource model
//		embeddedIdResource.setName(null);
//		assertNull(xmlEmbeddedIdMapping.getName());
//		assertNull(embeddedIdResource.getName());
//	}
//	
//	public void testModifyName() throws Exception {
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
//		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) ormPersistentAttribute.getMapping();
//		EmbeddedId embeddedIdResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
//		
//		assertEquals("embeddedIdMapping", xmlEmbeddedIdMapping.getName());
//		assertEquals("embeddedIdMapping", embeddedIdResource.getName());
//				
//		//set name in the context model, verify resource model updated
//		xmlEmbeddedIdMapping.setName("newName");
//		assertEquals("newName", xmlEmbeddedIdMapping.getName());
//		assertEquals("newName", embeddedIdResource.getName());
//	
//		//set name to null in the context model
//		xmlEmbeddedIdMapping.setName(null);
//		assertNull(xmlEmbeddedIdMapping.getName());
//		assertNull(embeddedIdResource.getName());
//	}
//	
//	public void testAddSpecifiedAttributeOverride() throws Exception {
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
//		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) ormPersistentAttribute.getMapping();
//		EmbeddedId embeddedIdResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
//		
//		XmlAttributeOverride attributeOverride = xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(0);
//		ormResource().save(null);
//		attributeOverride.setName("FOO");
//		ormResource().save(null);
//				
//		assertEquals("FOO", embeddedIdResource.getAttributeOverrides().get(0).getName());
//		
//		XmlAttributeOverride attributeOverride2 = xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(0);
//		ormResource().save(null);
//		attributeOverride2.setName("BAR");
//		ormResource().save(null);
//		
//		assertEquals("BAR", embeddedIdResource.getAttributeOverrides().get(0).getName());
//		assertEquals("FOO", embeddedIdResource.getAttributeOverrides().get(1).getName());
//		
//		XmlAttributeOverride attributeOverride3 = xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(1);
//		ormResource().save(null);
//		attributeOverride3.setName("BAZ");
//		ormResource().save(null);
//		
//		assertEquals("BAR", embeddedIdResource.getAttributeOverrides().get(0).getName());
//		assertEquals("BAZ", embeddedIdResource.getAttributeOverrides().get(1).getName());
//		assertEquals("FOO", embeddedIdResource.getAttributeOverrides().get(2).getName());
//		
//		ListIterator<XmlAttributeOverride> attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
//		assertEquals(attributeOverride2, attributeOverrides.next());
//		assertEquals(attributeOverride3, attributeOverrides.next());
//		assertEquals(attributeOverride, attributeOverrides.next());
//		
//		attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
//		assertEquals("BAR", attributeOverrides.next().getName());
//		assertEquals("BAZ", attributeOverrides.next().getName());
//		assertEquals("FOO", attributeOverrides.next().getName());
//	}
//	
//	public void testRemoveSpecifiedAttributeOverride() throws Exception {
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
//		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) ormPersistentAttribute.getMapping();
//		EmbeddedId embeddedIdResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
//
//		xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(0).setName("FOO");
//		xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(1).setName("BAR");
//		xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(2).setName("BAZ");
//		
//		assertEquals(3, embeddedIdResource.getAttributeOverrides().size());
//		
//		xmlEmbeddedIdMapping.removeSpecifiedAttributeOverride(0);
//		assertEquals(2, embeddedIdResource.getAttributeOverrides().size());
//		assertEquals("BAR", embeddedIdResource.getAttributeOverrides().get(0).getName());
//		assertEquals("BAZ", embeddedIdResource.getAttributeOverrides().get(1).getName());
//
//		xmlEmbeddedIdMapping.removeSpecifiedAttributeOverride(0);
//		assertEquals(1, embeddedIdResource.getAttributeOverrides().size());
//		assertEquals("BAZ", embeddedIdResource.getAttributeOverrides().get(0).getName());
//		
//		xmlEmbeddedIdMapping.removeSpecifiedAttributeOverride(0);
//		assertEquals(0, embeddedIdResource.getAttributeOverrides().size());
//	}
//	
//	public void testMoveSpecifiedAttributeOverride() throws Exception {
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
//		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) ormPersistentAttribute.getMapping();
//		EmbeddedId embeddedIdResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
//
//		xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(0).setName("FOO");
//		xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(1).setName("BAR");
//		xmlEmbeddedIdMapping.addSpecifiedAttributeOverride(2).setName("BAZ");
//		
//		assertEquals(3, embeddedIdResource.getAttributeOverrides().size());
//		
//		
//		xmlEmbeddedIdMapping.moveSpecifiedAttributeOverride(2, 0);
//		ListIterator<XmlAttributeOverride> attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
//		assertEquals("BAR", attributeOverrides.next().getName());
//		assertEquals("BAZ", attributeOverrides.next().getName());
//		assertEquals("FOO", attributeOverrides.next().getName());
//
//		assertEquals("BAR", embeddedIdResource.getAttributeOverrides().get(0).getName());
//		assertEquals("BAZ", embeddedIdResource.getAttributeOverrides().get(1).getName());
//		assertEquals("FOO", embeddedIdResource.getAttributeOverrides().get(2).getName());
//
//
//		xmlEmbeddedIdMapping.moveSpecifiedAttributeOverride(0, 1);
//		attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
//		assertEquals("BAZ", attributeOverrides.next().getName());
//		assertEquals("BAR", attributeOverrides.next().getName());
//		assertEquals("FOO", attributeOverrides.next().getName());
//
//		assertEquals("BAZ", embeddedIdResource.getAttributeOverrides().get(0).getName());
//		assertEquals("BAR", embeddedIdResource.getAttributeOverrides().get(1).getName());
//		assertEquals("FOO", embeddedIdResource.getAttributeOverrides().get(2).getName());
//	}
//	
//	public void testUpdateAttributeOverrides() throws Exception {
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
//		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) ormPersistentAttribute.getMapping();
//		EmbeddedId embeddedIdResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
//		
//		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createAttributeOverride());
//		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createAttributeOverride());
//		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createAttributeOverride());
//		
//		embeddedIdResource.getAttributeOverrides().get(0).setName("FOO");
//		embeddedIdResource.getAttributeOverrides().get(1).setName("BAR");
//		embeddedIdResource.getAttributeOverrides().get(2).setName("BAZ");
//
//		ListIterator<XmlAttributeOverride> attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
//		assertEquals("FOO", attributeOverrides.next().getName());
//		assertEquals("BAR", attributeOverrides.next().getName());
//		assertEquals("BAZ", attributeOverrides.next().getName());
//		assertFalse(attributeOverrides.hasNext());
//		
//		embeddedIdResource.getAttributeOverrides().move(2, 0);
//		attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
//		assertEquals("BAR", attributeOverrides.next().getName());
//		assertEquals("BAZ", attributeOverrides.next().getName());
//		assertEquals("FOO", attributeOverrides.next().getName());
//		assertFalse(attributeOverrides.hasNext());
//
//		embeddedIdResource.getAttributeOverrides().move(0, 1);
//		attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
//		assertEquals("BAZ", attributeOverrides.next().getName());
//		assertEquals("BAR", attributeOverrides.next().getName());
//		assertEquals("FOO", attributeOverrides.next().getName());
//		assertFalse(attributeOverrides.hasNext());
//
//		embeddedIdResource.getAttributeOverrides().remove(1);
//		attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
//		assertEquals("BAZ", attributeOverrides.next().getName());
//		assertEquals("FOO", attributeOverrides.next().getName());
//		assertFalse(attributeOverrides.hasNext());
//
//		embeddedIdResource.getAttributeOverrides().remove(1);
//		attributeOverrides = xmlEmbeddedIdMapping.specifiedAttributeOverrides();
//		assertEquals("BAZ", attributeOverrides.next().getName());
//		assertFalse(attributeOverrides.hasNext());
//		
//		embeddedIdResource.getAttributeOverrides().remove(0);
//		assertFalse(xmlEmbeddedIdMapping.specifiedAttributeOverrides().hasNext());
//	}
	
	public void testEmbeddedIdMorphToIdMapping() throws Exception {
		createTestEntityEmbeddedIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		XmlEmbeddedId embeddedIdResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedIdMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("embeddedId", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedIdMorphToVersionMapping() throws Exception {
		createTestEntityEmbeddedIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		XmlEmbeddedId embeddedIdResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedIdMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("embeddedId", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedIdMorphToTransientMapping() throws Exception {
		createTestEntityEmbeddedIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		XmlEmbeddedId embeddedIdResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedIdMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("embeddedId", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedIdMorphToBasicMapping() throws Exception {
		createTestEntityEmbeddedIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		XmlEmbeddedId embeddedIdResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedIdMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("embeddedId", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedIdMorphToEmbeddedMapping() throws Exception {
		createTestEntityEmbeddedIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		XmlEmbeddedId embeddedIdResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedIdMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("embeddedId", ormPersistentAttribute.getMapping().getName());
		attributeOverride = ((EmbeddedMapping) ormPersistentAttribute.getMapping()).getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		assertEquals("override", attributeOverride.getName());
		assertEquals("OVERRIDE_COLUMN", attributeOverride.getColumn().getSpecifiedName());
	}
	
	public void testEmbeddedIdMorphToOneToOneMapping() throws Exception {
		createTestEntityEmbeddedIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		XmlEmbeddedId embeddedIdResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedIdMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToOneMapping);
		assertEquals("embeddedId", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedIdMorphToOneToManyMapping() throws Exception {
		createTestEntityEmbeddedIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		XmlEmbeddedId embeddedIdResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedIdMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("embeddedId", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedIdMorphToManyToOneMapping() throws Exception {
		createTestEntityEmbeddedIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		XmlEmbeddedId embeddedIdResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedIdMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("embeddedId", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedIdMorphToManyToManyMapping() throws Exception {
		createTestEntityEmbeddedIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) ormPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		XmlEmbeddedId embeddedIdResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		AttributeOverride attributeOverride = embeddedIdMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next();
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("embeddedId", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedMapping");
		OrmEmbeddedIdMapping ormEmbeddedMapping = (OrmEmbeddedIdMapping) ormPersistentAttribute.getMapping();
		XmlEmbeddedId embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
		
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
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedMapping");
		OrmEmbeddedIdMapping ormEmbeddedMapping = (OrmEmbeddedIdMapping) ormPersistentAttribute.getMapping();
		XmlEmbeddedId embeddedResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
		
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
	
	public void testMoveSpecifiedAttributeOverride() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedMapping");
		OrmEmbeddedIdMapping ormEmbeddedIdMapping = (OrmEmbeddedIdMapping) ormPersistentAttribute.getMapping();
		OrmAttributeOverrideContainer attributeOverrideContainer = ormEmbeddedIdMapping.getAttributeOverrideContainer();
		XmlEmbeddedId embeddedIdResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);

		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		
		embeddedIdResource.getAttributeOverrides().get(0).setName("FOO");
		embeddedIdResource.getAttributeOverrides().get(1).setName("BAR");
		embeddedIdResource.getAttributeOverrides().get(2).setName("BAZ");
		
		assertEquals(3, embeddedIdResource.getAttributeOverrides().size());		
		
		attributeOverrideContainer.moveSpecifiedAttributeOverride(2, 0);
		ListIterator<OrmAttributeOverride> attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAR", embeddedIdResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAZ", embeddedIdResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", embeddedIdResource.getAttributeOverrides().get(2).getName());


		attributeOverrideContainer.moveSpecifiedAttributeOverride(0, 1);
		attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());

		assertEquals("BAZ", embeddedIdResource.getAttributeOverrides().get(0).getName());
		assertEquals("BAR", embeddedIdResource.getAttributeOverrides().get(1).getName());
		assertEquals("FOO", embeddedIdResource.getAttributeOverrides().get(2).getName());
	}
	
	public void testUpdateAttributeOverrides() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedMapping");
		OrmEmbeddedIdMapping ormEmbeddedIdMapping = (OrmEmbeddedIdMapping) ormPersistentAttribute.getMapping();
		OrmAttributeOverrideContainer attributeOverrideContainer = ormEmbeddedIdMapping.getAttributeOverrideContainer();
		XmlEmbeddedId embeddedIdResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
		
		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createXmlAttributeOverride());
		
		embeddedIdResource.getAttributeOverrides().get(0).setName("FOO");
		embeddedIdResource.getAttributeOverrides().get(1).setName("BAR");
		embeddedIdResource.getAttributeOverrides().get(2).setName("BAZ");

		ListIterator<OrmAttributeOverride> attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("FOO", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		embeddedIdResource.getAttributeOverrides().move(2, 0);
		attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		embeddedIdResource.getAttributeOverrides().move(0, 1);
		attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("BAR", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		embeddedIdResource.getAttributeOverrides().remove(1);
		attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertEquals("FOO", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());

		embeddedIdResource.getAttributeOverrides().remove(1);
		attributeOverrides = attributeOverrideContainer.specifiedAttributeOverrides();
		assertEquals("BAZ", attributeOverrides.next().getName());
		assertFalse(attributeOverrides.hasNext());
		
		embeddedIdResource.getAttributeOverrides().remove(0);
		assertFalse(attributeOverrideContainer.specifiedAttributeOverrides().hasNext());
	}
	
	
	public void testEmbeddedMappingNoUnderylingJavaAttribute() throws Exception {
		createTestEntityEmbeddedIdMapping();
		createTestEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "foo");
		assertEquals(3, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		OrmEmbeddedIdMapping ormEmbeddedIdMapping = (OrmEmbeddedIdMapping) ormPersistentAttribute.getMapping();
		OrmAttributeOverrideContainer attributeOverrideContainer = ormEmbeddedIdMapping.getAttributeOverrideContainer();
		
		assertEquals("foo", ormEmbeddedIdMapping.getName());

		
		assertFalse(attributeOverrideContainer.specifiedAttributeOverrides().hasNext());
		assertFalse(attributeOverrideContainer.virtualAttributeOverrides().hasNext());
	}
	
	public void testVirtualAttributeOverrides() throws Exception {
		createTestEntityEmbeddedIdMapping();
		createTestEmbeddableAddress();
		OrmPersistentType persistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentType persistentType2 = getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		
		//embedded mapping is virtual, specified attribute overrides should exist
		OrmPersistentAttribute ormPersistentAttribute = persistentType.getAttributeNamed("address");
		OrmEmbeddedIdMapping embeddedIdMapping = (OrmEmbeddedIdMapping) ormPersistentAttribute.getMapping();
		OrmAttributeOverrideContainer attributeOverrideContainer = embeddedIdMapping.getAttributeOverrideContainer();
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
		
		JavaEmbeddedIdMapping javaEmbeddedIdMapping = (JavaEmbeddedIdMapping) ormPersistentAttribute.getJavaPersistentAttribute().getMapping();
		Column javaAttributeOverrideColumn = javaEmbeddedIdMapping.getAttributeOverrideContainer().specifiedAttributeOverrides().next().getColumn();
		
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
		embeddedIdMapping = (OrmEmbeddedIdMapping) persistentType.getAttributeNamed("address").getMapping();
		attributeOverrideContainer = embeddedIdMapping.getAttributeOverrideContainer();
		assertEquals(4, attributeOverrideContainer.attributeOverridesSize());
		assertEquals(4, attributeOverrideContainer.virtualAttributeOverridesSize());
		assertEquals(0, attributeOverrideContainer.specifiedAttributeOverridesSize());
		ListIterator<OrmAttributeOverride> virtualAttributeOverrides = attributeOverrideContainer.virtualAttributeOverrides();
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("id", attributeOverride.getName());
		attributeOverride = virtualAttributeOverrides.next();
		assertEquals("city", attributeOverride.getName());
		assertEquals("city", attributeOverride.getColumn().getDefaultName());
		assertEquals(TYPE_NAME, attributeOverride.getColumn().getDefaultTable());
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
		createTestEntityEmbeddedIdMapping();
		createTestEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		OrmEmbeddedIdMapping ormEmbeddedIdMapping = (OrmEmbeddedIdMapping) ormPersistentAttribute.getMapping();	
		OrmAttributeOverrideContainer attributeOverrideContainer = ormEmbeddedIdMapping.getAttributeOverrideContainer();
		assertEquals("address", ormEmbeddedIdMapping.getName());

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
		createTestEntityEmbeddedIdMapping();
		createTestEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(3, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		//will be an OrmEmbeddedMapping instead of OrmEmbeddedIdMapping since that is the default
		OrmEmbeddedMapping ormEmbeddedMapping = (OrmEmbeddedMapping) ormPersistentAttribute.getMapping();	
		assertEquals("address", ormEmbeddedMapping.getName());

		//TODO
//		assertEquals(4, ormEmbeddedIdMapping.specifiedAttributeOverridesSize());
//		assertEquals(0, CollectionTools.size(ormEmbeddedIdMapping.defaultAttributeOverrides()));
//		ListIterator<XmlAttributeOverride> ormAttributeOverrides = ormEmbeddedIdMapping.specifiedAttributeOverrides();
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
		createTestEntityEmbeddedIdMapping();
		createTestEmbeddableAddress();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		getEntityMappings().addPersistentType(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "address");
		assertEquals(2, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		OrmEmbeddedIdMapping ormEmbeddedIdMapping = (OrmEmbeddedIdMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("address", ormEmbeddedIdMapping.getName());

		assertEquals(0, ormEmbeddedIdMapping.getAttributeOverrideContainer().specifiedAttributeOverridesSize());
		//TODO
//		assertEquals(4, CollectionTools.size(ormEmbeddedIdMapping.defaultAttributeOverrides()));
//		ListIterator<XmlAttributeOverride> ormAttributeOverrides = ormEmbeddedIdMapping.defaultAttributeOverrides();
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

}
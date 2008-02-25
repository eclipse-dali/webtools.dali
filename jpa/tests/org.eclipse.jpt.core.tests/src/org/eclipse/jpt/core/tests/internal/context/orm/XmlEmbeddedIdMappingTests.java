/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class XmlEmbeddedIdMappingTests extends ContextModelTestCase
{
	private static final String ATTRIBUTE_OVERRIDE_NAME = "city";
	private static final String ATTRIBUTE_OVERRIDE_COLUMN_NAME = "E_CITY";

	public XmlEmbeddedIdMappingTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		persistenceResource().save(null);
	}
	
	private void createEntityAnnotation() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createEmbeddedIdAnnotation() throws Exception{
		this.createAnnotationAndMembers("EmbeddedId", "");		
	}
	
	private void createColumnAnnotation() throws Exception {
		this.createAnnotationAndMembers("Column", 
			"String name() default \"\";" +
			"boolean unique() default false;" +
			"boolean nullable() default true;" +
			"boolean insertable() default true;" +
			"boolean updatable() default true;" +
			"String columnDefinition() default \"\";" +
			"String table() default \"\";" +
			"int length() default 255;" +
			"int precision() default 0;" +
			"int scale() default 0;");		
	}
	
	private void createAttributeOverrideAnnotation() throws Exception {
		createColumnAnnotation();
		this.createAnnotationAndMembers("AttributeOverride", 
			"String name();" +
			"Column column();");		
	}
	private IType createTestEntityEmbeddedIdMapping() throws Exception {
		createEntityAnnotation();
		createEmbeddedIdAnnotation();
		createAttributeOverrideAnnotation();
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
	

//	public void testUpdateName() throws Exception {
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
//		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) xmlPersistentAttribute.getMapping();
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
//		XmlPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
//		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) xmlPersistentAttribute.getMapping();
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
//		XmlPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
//		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) xmlPersistentAttribute.getMapping();
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
//		XmlPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
//		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) xmlPersistentAttribute.getMapping();
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
//		XmlPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
//		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) xmlPersistentAttribute.getMapping();
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
//		XmlPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedIdMapping");
//		XmlEmbeddedIdMapping xmlEmbeddedIdMapping = (XmlEmbeddedIdMapping) xmlPersistentAttribute.getMapping();
//		EmbeddedId embeddedIdResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getEmbeddedIds().get(0);
//		
//		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createAttributeOverrideImpl());
//		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createAttributeOverrideImpl());
//		embeddedIdResource.getAttributeOverrides().add(OrmFactory.eINSTANCE.createAttributeOverrideImpl());
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) xmlPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		AttributeOverride attributeOverride = embeddedIdMapping.addSpecifiedAttributeOverride(0);
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("embeddedId", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedIdMorphToVersionMapping() throws Exception {
		createTestEntityEmbeddedIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) xmlPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		AttributeOverride attributeOverride = embeddedIdMapping.addSpecifiedAttributeOverride(0);
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("embeddedId", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedIdMorphToTransientMapping() throws Exception {
		createTestEntityEmbeddedIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) xmlPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		AttributeOverride attributeOverride = embeddedIdMapping.addSpecifiedAttributeOverride(0);
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("embeddedId", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedIdMorphToBasicMapping() throws Exception {
		createTestEntityEmbeddedIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) xmlPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		AttributeOverride attributeOverride = embeddedIdMapping.addSpecifiedAttributeOverride(0);
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("embeddedId", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedIdMorphToEmbeddedMapping() throws Exception {
		createTestEntityEmbeddedIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) xmlPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		AttributeOverride attributeOverride = embeddedIdMapping.addSpecifiedAttributeOverride(0);
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("embeddedId", xmlPersistentAttribute.getMapping().getName());
		attributeOverride = ((EmbeddedMapping) xmlPersistentAttribute.getMapping()).specifiedAttributeOverrides().next();
		assertEquals("override", attributeOverride.getName());
		assertEquals("OVERRIDE_COLUMN", attributeOverride.getColumn().getSpecifiedName());
	}
	
	public void testEmbeddedIdMorphToOneToOneMapping() throws Exception {
		createTestEntityEmbeddedIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) xmlPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		AttributeOverride attributeOverride = embeddedIdMapping.addSpecifiedAttributeOverride(0);
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof OneToOneMapping);
		assertEquals("embeddedId", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedIdMorphToOneToManyMapping() throws Exception {
		createTestEntityEmbeddedIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) xmlPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		AttributeOverride attributeOverride = embeddedIdMapping.addSpecifiedAttributeOverride(0);
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("embeddedId", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedIdMorphToManyToOneMapping() throws Exception {
		createTestEntityEmbeddedIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) xmlPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		AttributeOverride attributeOverride = embeddedIdMapping.addSpecifiedAttributeOverride(0);
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(xmlPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("embeddedId", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testEmbeddedIdMorphToManyToManyMapping() throws Exception {
		createTestEntityEmbeddedIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY, "embeddedId");
		
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) xmlPersistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		AttributeOverride attributeOverride = embeddedIdMapping.addSpecifiedAttributeOverride(0);
		attributeOverride.setName("override");
		attributeOverride.getColumn().setSpecifiedName("OVERRIDE_COLUMN");
		assertFalse(embeddedIdMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("embeddedId", xmlPersistentAttribute.getMapping().getName());
	}

}
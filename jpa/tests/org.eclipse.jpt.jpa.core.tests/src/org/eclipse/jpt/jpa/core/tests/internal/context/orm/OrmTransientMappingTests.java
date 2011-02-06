/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTransient;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmTransientMappingTests extends ContextModelTestCase
{
	public OrmTransientMappingTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptJpaCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString());
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}

	private ICompilationUnit createTestEntityTransientMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.TRANSIENT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Transient");
			}
		});
	}
	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transientMapping");
		OrmTransientMapping xmlTransientnMapping = (OrmTransientMapping) ormPersistentAttribute.getMapping();
		XmlTransient transientResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getTransients().get(0);
		
		assertEquals("transientMapping", xmlTransientnMapping.getName());
		assertEquals("transientMapping", transientResource.getName());
				
		//set name in the resource model, verify context model updated
		transientResource.setName("newName");
		assertEquals("newName", xmlTransientnMapping.getName());
		assertEquals("newName", transientResource.getName());
	
		//set name to null in the resource model
		transientResource.setName(null);
		assertNull(xmlTransientnMapping.getName());
		assertNull(transientResource.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transientMapping");
		OrmTransientMapping xmlTransientnMapping = (OrmTransientMapping) ormPersistentAttribute.getMapping();
		XmlTransient transientResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getTransients().get(0);
		
		assertEquals("transientMapping", xmlTransientnMapping.getName());
		assertEquals("transientMapping", transientResource.getName());
				
		//set name in the context model, verify resource model updated
		xmlTransientnMapping.setName("newName");
		assertEquals("newName", xmlTransientnMapping.getName());
		assertEquals("newName", transientResource.getName());
	
		//set name to null in the context model
		xmlTransientnMapping.setName(null);
		assertNull(xmlTransientnMapping.getName());
		assertNull(transientResource.getName());
	}
	
	
	public void testTransientMappingNoUnderylingJavaAttribute() throws Exception {
		createTestEntityTransientMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.addSpecifiedAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "foo");
		assertEquals(2, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		OrmTransientMapping ormTransientMapping = (OrmTransientMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("foo", ormTransientMapping.getName());
	}
	
	//@Basic(fetch=FetchType.LAZY, optional=false)
	//@Column(name="MY_COLUMN", unique=true, nullable=false, insertable=false, updatable=false, 
	//    columnDefinition="COLUMN_DEFINITION", table="MY_TABLE", length=5, precision=6, scale=7)");
	//@Column(
	//@Lob
	//@Temporal(TemporalType.TIMESTAMP)
	//@Enumerated(EnumType.STRING)
	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(2, ormPersistentType.virtualAttributesSize());		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		TransientMapping transientMapping = (TransientMapping) ormPersistentAttribute.getMapping();	
		assertEquals("id", transientMapping.getName());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(2, ormPersistentType.virtualAttributesSize());		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
		assertTrue(ormPersistentAttribute.isVirtual());
		
		ormPersistentAttribute.convertToSpecified(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");

		OrmTransientMapping ormTransientMapping = (OrmTransientMapping) ormPersistentAttribute.getMapping();	
		assertEquals("id", ormTransientMapping.getName());
		assertFalse(ormPersistentAttribute.isVirtual());
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityTransientMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.addSpecifiedAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "id");
		assertEquals(1, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		OrmTransientMapping ormTransientMapping = (OrmTransientMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("id", ormTransientMapping.getName());
	}
	
	public void testTransientMorphToIdMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) ormPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("transient", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToVersionMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) ormPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("transient", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToTransientMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) ormPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("transient", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToEmbeddedMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) ormPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("transient", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) ormPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
	
		ormPersistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("transient", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToOneToOneMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) ormPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToOneMapping);
		assertEquals("transient", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToOneToManyMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) ormPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("transient", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToManyToOneMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) ormPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("transient", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToManyToManyMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) ormPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("transient", ormPersistentAttribute.getMapping().getName());
	}
}
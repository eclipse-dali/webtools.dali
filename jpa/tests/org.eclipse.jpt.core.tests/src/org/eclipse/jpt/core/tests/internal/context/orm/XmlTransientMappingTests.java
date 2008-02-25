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
import org.eclipse.jpt.core.internal.context.orm.GenericOrmTransientMapping;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.XmlTransient;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class XmlTransientMappingTests extends ContextModelTestCase
{
	public XmlTransientMappingTests(String name) {
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
	
	private void createTransientAnnotation() throws Exception{
		this.createAnnotationAndMembers("Transient", "");		
	}


	private IType createTestEntityTransientMapping() throws Exception {
		createEntityAnnotation();
		createTransientAnnotation();
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transientMapping");
		GenericOrmTransientMapping xmlTransientnMapping = (GenericOrmTransientMapping) xmlPersistentAttribute.getMapping();
		XmlTransient transientResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getTransients().get(0);
		
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transientMapping");
		GenericOrmTransientMapping xmlTransientnMapping = (GenericOrmTransientMapping) xmlPersistentAttribute.getMapping();
		XmlTransient transientResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getTransients().get(0);
		
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

		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "foo");
		assertEquals(2, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		GenericOrmTransientMapping xmlTransientMapping = (GenericOrmTransientMapping) xmlPersistentAttribute.getMapping();
		
		assertEquals("foo", xmlTransientMapping.getName());
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(2, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		GenericOrmTransientMapping xmlTransientMapping = (GenericOrmTransientMapping) xmlPersistentAttribute.getMapping();	
		assertEquals("id", xmlTransientMapping.getName());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(2, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		GenericOrmTransientMapping xmlTransientMapping = (GenericOrmTransientMapping) xmlPersistentAttribute.getMapping();	
		assertEquals("id", xmlTransientMapping.getName());
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityTransientMapping();

		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "id");
		assertEquals(1, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		GenericOrmTransientMapping xmlTransientMapping = (GenericOrmTransientMapping) xmlPersistentAttribute.getMapping();
		
		assertEquals("id", xmlTransientMapping.getName());
	}
	
	public void testTransientMorphToIdMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToVersionMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToTransientMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToEmbeddedMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
	
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToOneToOneMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof OneToOneMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToOneToManyMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToManyToOneMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToManyToManyMapping() throws Exception {
		createTestEntityTransientMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		TransientMapping transientMapping = (TransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
}
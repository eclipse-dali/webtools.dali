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
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.IManyToManyMapping;
import org.eclipse.jpt.core.internal.context.base.IManyToOneMapping;
import org.eclipse.jpt.core.internal.context.base.IOneToManyMapping;
import org.eclipse.jpt.core.internal.context.base.IOneToOneMapping;
import org.eclipse.jpt.core.internal.context.base.ITransientMapping;
import org.eclipse.jpt.core.internal.context.base.IVersionMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.context.orm.XmlTransientMapping;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.orm.Transient;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transientMapping");
		XmlTransientMapping xmlTransientnMapping = (XmlTransientMapping) xmlPersistentAttribute.getMapping();
		Transient transientResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getTransients().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transientMapping");
		XmlTransientMapping xmlTransientnMapping = (XmlTransientMapping) xmlPersistentAttribute.getMapping();
		Transient transientResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getTransients().get(0);
		
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

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "foo");
		assertEquals(2, xmlPersistentType.virtualAttributesSize());
		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.specifiedAttributes().next();
		XmlTransientMapping xmlTransientMapping = (XmlTransientMapping) xmlPersistentAttribute.getMapping();
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(2, xmlPersistentType.virtualAttributesSize());		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.virtualAttributes().next();
		
		XmlTransientMapping xmlTransientMapping = (XmlTransientMapping) xmlPersistentAttribute.getMapping();	
		assertEquals("id", xmlTransientMapping.getName());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityTransientMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		xmlPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(2, xmlPersistentType.virtualAttributesSize());		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.virtualAttributes().next();
		
		XmlTransientMapping xmlTransientMapping = (XmlTransientMapping) xmlPersistentAttribute.getMapping();	
		assertEquals("id", xmlTransientMapping.getName());
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityTransientMapping();

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "id");
		assertEquals(1, xmlPersistentType.virtualAttributesSize());
		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.specifiedAttributes().next();
		XmlTransientMapping xmlTransientMapping = (XmlTransientMapping) xmlPersistentAttribute.getMapping();
		
		assertEquals("id", xmlTransientMapping.getName());
	}
	
	public void testTransientMorphToIdMapping() throws Exception {
		createTestEntityTransientMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		ITransientMapping transientMapping = (ITransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IIdMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToVersionMapping() throws Exception {
		createTestEntityTransientMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		ITransientMapping transientMapping = (ITransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IVersionMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToTransientMapping() throws Exception {
		createTestEntityTransientMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		ITransientMapping transientMapping = (ITransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof ITransientMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToEmbeddedMapping() throws Exception {
		createTestEntityTransientMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		ITransientMapping transientMapping = (ITransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IEmbeddedMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityTransientMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		ITransientMapping transientMapping = (ITransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
	
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IEmbeddedIdMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToOneToOneMapping() throws Exception {
		createTestEntityTransientMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		ITransientMapping transientMapping = (ITransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IOneToOneMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToOneToManyMapping() throws Exception {
		createTestEntityTransientMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		ITransientMapping transientMapping = (ITransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IOneToManyMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToManyToOneMapping() throws Exception {
		createTestEntityTransientMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		ITransientMapping transientMapping = (ITransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IManyToOneMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testTransientMorphToManyToManyMapping() throws Exception {
		createTestEntityTransientMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY, "transient");
		
		ITransientMapping transientMapping = (ITransientMapping) xmlPersistentAttribute.getMapping();
		assertFalse(transientMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IManyToManyMapping);
		assertEquals("transient", xmlPersistentAttribute.getMapping().getName());
	}
}
/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConversionValue;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLinkOrmObjectTypeConverterTests
	extends EclipseLinkContextModelTestCase
{
	
	private ICompilationUnit createTestEntityWithBasicMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.BASIC, EclipseLink.CONVERT, EclipseLink.OBJECT_TYPE_CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic").append(CR);
				sb.append("    @Convert(name=\"foo\")").append(CR);
				sb.append("    @ObjectTypeConverter");
			}
		});
	}
	
	
	public EclipseLinkOrmObjectTypeConverterTests(String name) {
		super(name);
	}
	
	
	public void testUpdateDataType() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmEclipseLinkBasicMapping ormBasicMapping = (OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping(); 
		EclipseLinkObjectTypeConverter ormConverter = ormBasicMapping.getConverterContainer().addObjectTypeConverter("objectTypeConverter", 0);
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverters().get(0);
		
		assertEquals(null, ormConverter.getDataType());
		assertEquals(null, converterResource.getDataType());
				
		//set converter class name in the resource model, verify context model updated
		converterResource.setDataType("myConvert");
		assertEquals("myConvert", ormConverter.getDataType());
		assertEquals("myConvert", converterResource.getDataType());

		//set converter class name to null in the resource model
		converterResource.setDataType(null);
		assertEquals(null, ormConverter.getDataType());
		assertEquals(null, converterResource.getDataType());
	}
	
	public void testModifyDataType() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmEclipseLinkBasicMapping ormBasicMapping = (OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping(); 
		EclipseLinkObjectTypeConverter ormConverter = ormBasicMapping.getConverterContainer().addObjectTypeConverter("objectTypeConverter", 0);
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverters().get(0);
	
		assertEquals(null, ormConverter.getDataType());
		assertEquals(null, converterResource.getDataType());
				
		//set converter class in the context model, verify resource model updated
		ormConverter.setDataType("foo");
		assertEquals("foo", ormConverter.getDataType());
		assertEquals("foo", converterResource.getDataType());
	
		ormConverter.setDataType(null);
		assertEquals(null, ormConverter.getDataType());
		assertEquals(null, converterResource.getDataType());
	}
	
	public void testUpdateObjectType() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmEclipseLinkBasicMapping ormBasicMapping = (OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping(); 
		EclipseLinkObjectTypeConverter ormConverter = ormBasicMapping.getConverterContainer().addObjectTypeConverter("objectTypeConverter", 0);
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverters().get(0);
		
		assertEquals(null, ormConverter.getObjectType());
		assertEquals(null, converterResource.getObjectType());
				
		//set converter class name in the resource model, verify context model updated
		converterResource.setObjectType("myConvert");
		assertEquals("myConvert", ormConverter.getObjectType());
		assertEquals("myConvert", converterResource.getObjectType());

		//set converter class name to null in the resource model
		converterResource.setObjectType(null);
		assertEquals(null, ormConverter.getObjectType());
		assertEquals(null, converterResource.getObjectType());
	}
	
	public void testModifyObjectType() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmEclipseLinkBasicMapping ormBasicMapping = (OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping(); 
		EclipseLinkObjectTypeConverter ormConverter = ormBasicMapping.getConverterContainer().addObjectTypeConverter("objectTypeConverter", 0);
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverters().get(0);
	
		assertEquals(null, ormConverter.getObjectType());
		assertEquals(null, converterResource.getObjectType());
				
		//set converter class in the context model, verify resource model updated
		ormConverter.setObjectType("foo");
		assertEquals("foo", ormConverter.getObjectType());
		assertEquals("foo", converterResource.getObjectType());
	
		ormConverter.setObjectType(null);
		assertEquals(null, ormConverter.getObjectType());
		assertEquals(null, converterResource.getObjectType());
	}
	
	public void testUpdateName() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmEclipseLinkBasicMapping ormBasicMapping = (OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping(); 
		EclipseLinkObjectTypeConverter ormConverter = ormBasicMapping.getConverterContainer().addObjectTypeConverter("objectTypeConverter", 0);
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverters().get(0);
		
		assertEquals("objectTypeConverter", ormConverter.getName());
		assertEquals("objectTypeConverter", converterResource.getName());
				
		//set converter class name in the resource model, verify context model updated
		converterResource.setName("myConvert");
		assertEquals("myConvert", ormConverter.getName());
		assertEquals("myConvert", converterResource.getName());

		//set converter class name to null in the resource model
		converterResource.setName(null);
		assertEquals(null, ormConverter.getName());
		assertEquals(null, converterResource.getName());
	}
	
	public void testModifyName() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmEclipseLinkBasicMapping ormBasicMapping = (OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping(); 
		EclipseLinkObjectTypeConverter ormConverter = ormBasicMapping.getConverterContainer().addObjectTypeConverter("objectTypeConverter", 0);
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverters().get(0);
	
		assertEquals("objectTypeConverter", ormConverter.getName());
		assertEquals("objectTypeConverter", converterResource.getName());
				
		//set converter class in the context model, verify resource model updated
		ormConverter.setName("foo");
		assertEquals("foo", ormConverter.getName());
		assertEquals("foo", converterResource.getName());
	
		ormConverter.setName(null);
		assertEquals(null, ormConverter.getName());
		assertEquals(null, converterResource.getName());
	}
	
	public void testUpdateDefaultObjectValue() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmEclipseLinkBasicMapping ormBasicMapping = (OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping(); 
		EclipseLinkObjectTypeConverter ormConverter = ormBasicMapping.getConverterContainer().addObjectTypeConverter("objectTypeConverter", 0);
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverters().get(0);
		
		assertEquals(null, ormConverter.getDefaultObjectValue());
		assertEquals(null, converterResource.getDefaultObjectValue());
				
		//set converter class name in the resource model, verify context model updated
		converterResource.setDefaultObjectValue("myConvert");
		assertEquals("myConvert", ormConverter.getDefaultObjectValue());
		assertEquals("myConvert", converterResource.getDefaultObjectValue());

		//set converter class name to null in the resource model
		converterResource.setDefaultObjectValue(null);
		assertEquals(null, ormConverter.getDefaultObjectValue());
		assertEquals(null, converterResource.getDefaultObjectValue());
	}
	
	public void testModifyDefaultObjectValue() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmEclipseLinkBasicMapping ormBasicMapping = (OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping(); 
		EclipseLinkObjectTypeConverter ormConverter = ormBasicMapping.getConverterContainer().addObjectTypeConverter("objectTypeConverter", 0);
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverters().get(0);
	
		assertEquals(null, ormConverter.getDefaultObjectValue());
		assertEquals(null, converterResource.getDefaultObjectValue());
				
		//set converter class in the context model, verify resource model updated
		ormConverter.setDefaultObjectValue("foo");
		assertEquals("foo", ormConverter.getDefaultObjectValue());
		assertEquals("foo", converterResource.getDefaultObjectValue());
	
		ormConverter.setDefaultObjectValue(null);
		assertEquals(null, ormConverter.getDefaultObjectValue());
		assertEquals(null, converterResource.getDefaultObjectValue());
	}

	
	public void testUpdateConversionValues() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmEclipseLinkBasicMapping ormBasicMapping = (OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping(); 
		EclipseLinkObjectTypeConverter ormConverter = ormBasicMapping.getConverterContainer().addObjectTypeConverter("objectTypeConverter", 0);
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverters().get(0);

		assertEquals(0, ormConverter.getConversionValuesSize());
		assertEquals(0, converterResource.getConversionValues().size());
		
		//add conversion value to resource model, check context model
		XmlConversionValue resourceConversionValue = EclipseLinkOrmFactory.eINSTANCE.createXmlConversionValue();
		converterResource.getConversionValues().add(resourceConversionValue);
		resourceConversionValue.setDataValue("foo");
		resourceConversionValue.setObjectValue("bar");
		
		assertEquals(1, ormConverter.getConversionValuesSize());
		ListIterator<? extends EclipseLinkConversionValue> contextConversionValues = ormConverter.getConversionValues().iterator();
		EclipseLinkConversionValue contextConversionValue = contextConversionValues.next();
		assertEquals("foo", contextConversionValue.getDataValue());
		assertEquals("bar", contextConversionValue.getObjectValue());
		assertEquals(1, converterResource.getConversionValues().size());
		assertEquals("foo", converterResource.getConversionValues().get(0).getDataValue());
		assertEquals("bar", converterResource.getConversionValues().get(0).getObjectValue());
		
		//add a conversion to the beginning of the resource model list
		XmlConversionValue xmlConversionValue2 = EclipseLinkOrmFactory.eINSTANCE.createXmlConversionValue();
		converterResource.getConversionValues().add(0, xmlConversionValue2);
		xmlConversionValue2.setDataValue("foo2");
		xmlConversionValue2.setObjectValue("bar2");

		assertEquals(2, ormConverter.getConversionValuesSize());
		contextConversionValues = ormConverter.getConversionValues().iterator();
		contextConversionValue = contextConversionValues.next();
		assertEquals("foo2", contextConversionValue.getDataValue());
		assertEquals("bar2", contextConversionValue.getObjectValue());
		contextConversionValue = contextConversionValues.next();
		assertEquals("foo", contextConversionValue.getDataValue());
		assertEquals("bar", contextConversionValue.getObjectValue());
		assertEquals(2, converterResource.getConversionValues().size());
		assertEquals("foo2", converterResource.getConversionValues().get(0).getDataValue());
		assertEquals("bar2", converterResource.getConversionValues().get(0).getObjectValue());
		assertEquals("foo", converterResource.getConversionValues().get(1).getDataValue());
		assertEquals("bar", converterResource.getConversionValues().get(1).getObjectValue());

		//move a conversion value in the resource model list
		
		converterResource.getConversionValues().move(0, 1);
		assertEquals(2, ormConverter.getConversionValuesSize());
		contextConversionValues = ormConverter.getConversionValues().iterator();
		contextConversionValue = contextConversionValues.next();
		assertEquals("foo", contextConversionValue.getDataValue());
		assertEquals("bar", contextConversionValue.getObjectValue());
		contextConversionValue = contextConversionValues.next();
		assertEquals("foo2", contextConversionValue.getDataValue());
		assertEquals("bar2", contextConversionValue.getObjectValue());
		assertEquals(2, converterResource.getConversionValues().size());
		assertEquals("foo", converterResource.getConversionValues().get(0).getDataValue());
		assertEquals("bar", converterResource.getConversionValues().get(0).getObjectValue());
		assertEquals("foo2", converterResource.getConversionValues().get(1).getDataValue());
		assertEquals("bar2", converterResource.getConversionValues().get(1).getObjectValue());

		//remove a conversion value from the resource model list

		converterResource.getConversionValues().remove(0);
		assertEquals(1, ormConverter.getConversionValuesSize());
		contextConversionValues = ormConverter.getConversionValues().iterator();
		contextConversionValue = contextConversionValues.next();
		assertEquals("foo2", contextConversionValue.getDataValue());
		assertEquals("bar2", contextConversionValue.getObjectValue());
		assertEquals(1, converterResource.getConversionValues().size());
		assertEquals("foo2", converterResource.getConversionValues().get(0).getDataValue());
		assertEquals("bar2", converterResource.getConversionValues().get(0).getObjectValue());

		//clear the conversion value resource model list
		converterResource.getConversionValues().clear();
		assertEquals(0, ormConverter.getConversionValuesSize());
		assertEquals(0, converterResource.getConversionValues().size());
	}
	
	public void testModifyConversionValues() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmEclipseLinkBasicMapping ormBasicMapping = (OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping(); 
		EclipseLinkObjectTypeConverter ormConverter = ormBasicMapping.getConverterContainer().addObjectTypeConverter("objectTypeConverter", 0);
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverters().get(0);
		
		assertEquals(0, ormConverter.getConversionValuesSize());
		assertEquals(0, converterResource.getConversionValues().size());
		
		//add conversion value to context model, check resource model
		EclipseLinkConversionValue contextConversionValue = ormConverter.addConversionValue();
		contextConversionValue.setDataValue("foo");
		contextConversionValue.setObjectValue("bar");

		assertEquals(1, ormConverter.getConversionValuesSize());
		ListIterator<? extends EclipseLinkConversionValue> contextConversionValues = ormConverter.getConversionValues().iterator();
		contextConversionValue = contextConversionValues.next();
		assertEquals("foo", contextConversionValue.getDataValue());
		assertEquals("bar", contextConversionValue.getObjectValue());
		assertEquals(1, converterResource.getConversionValues().size());
		assertEquals("foo", converterResource.getConversionValues().get(0).getDataValue());
		assertEquals("bar", converterResource.getConversionValues().get(0).getObjectValue());

		//add a conversion to the beginning of the context model list
		EclipseLinkConversionValue contextConversionValue2 = ormConverter.addConversionValue(0);
		contextConversionValue2.setDataValue("foo2");
		contextConversionValue2.setObjectValue("bar2");

		assertEquals(2, ormConverter.getConversionValuesSize());
		contextConversionValues = ormConverter.getConversionValues().iterator();
		contextConversionValue = contextConversionValues.next();
		assertEquals("foo2", contextConversionValue.getDataValue());
		assertEquals("bar2", contextConversionValue.getObjectValue());
		contextConversionValue = contextConversionValues.next();
		assertEquals("foo", contextConversionValue.getDataValue());
		assertEquals("bar", contextConversionValue.getObjectValue());
		assertEquals(2, converterResource.getConversionValues().size());
		assertEquals("foo2", converterResource.getConversionValues().get(0).getDataValue());
		assertEquals("bar2", converterResource.getConversionValues().get(0).getObjectValue());
		assertEquals("foo", converterResource.getConversionValues().get(1).getDataValue());
		assertEquals("bar", converterResource.getConversionValues().get(1).getObjectValue());

		//move a conversion value in the context model list
		
		ormConverter.moveConversionValue(0, 1);
		assertEquals(2, ormConverter.getConversionValuesSize());
		contextConversionValues = ormConverter.getConversionValues().iterator();
		contextConversionValue = contextConversionValues.next();
		assertEquals("foo", contextConversionValue.getDataValue());
		assertEquals("bar", contextConversionValue.getObjectValue());
		contextConversionValue = contextConversionValues.next();
		assertEquals("foo2", contextConversionValue.getDataValue());
		assertEquals("bar2", contextConversionValue.getObjectValue());
		assertEquals(2, converterResource.getConversionValues().size());
		assertEquals("foo", converterResource.getConversionValues().get(0).getDataValue());
		assertEquals("bar", converterResource.getConversionValues().get(0).getObjectValue());
		assertEquals("foo2", converterResource.getConversionValues().get(1).getDataValue());
		assertEquals("bar2", converterResource.getConversionValues().get(1).getObjectValue());

		//remove a conversion value from the context model list

		ormConverter.removeConversionValue(0);
		assertEquals(1, ormConverter.getConversionValuesSize());
		contextConversionValues = ormConverter.getConversionValues().iterator();
		contextConversionValue = contextConversionValues.next();
		assertEquals("foo2", contextConversionValue.getDataValue());
		assertEquals("bar2", contextConversionValue.getObjectValue());
		assertEquals(1, converterResource.getConversionValues().size());
		assertEquals("foo2", converterResource.getConversionValues().get(0).getDataValue());
		assertEquals("bar2", converterResource.getConversionValues().get(0).getObjectValue());

		//clear the conversion value resource model list
		ormConverter.removeConversionValue(0);
		assertEquals(0, ormConverter.getConversionValuesSize());
		assertEquals(0, converterResource.getConversionValues().size());
	}
}

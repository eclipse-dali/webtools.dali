/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkOrmObjectTypeConverterTests
	extends EclipseLinkOrmContextModelTestCase
{
	
	private ICompilationUnit createTestEntityWithBasicMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC, EclipseLinkJPA.CONVERT, EclipseLinkJPA.OBJECT_TYPE_CONVERTER);
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

	
	private ICompilationUnit createTestEntityWithConvertAndObjectTypeConverterConversionValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLinkJPA.CONVERT, EclipseLinkJPA.OBJECT_TYPE_CONVERTER, EclipseLinkJPA.CONVERSION_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert(\"foo\")").append(CR);
				sb.append("    @ObjectTypeConverter(name=\"foo\", defaultObjectValue=\"bar\", conversionValues = @ConversionValue(dataValue=\"f\", objectValue=\"female\"))");
			}
		});
	}

	public EclipseLinkOrmObjectTypeConverterTests(String name) {
		super(name);
	}
	
	
	public void testUpdateDataType() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping(); 
		ormBasicMapping.setSpecifiedConverter(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER);
		((EclipseLinkConvert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		EclipseLinkObjectTypeConverter ormConverter = (EclipseLinkObjectTypeConverter) ((EclipseLinkConvert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
		JavaBasicMapping javaBasicMapping = (JavaBasicMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
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
		
				
		//remove the specified persistent attribute, test virtual mapping	
		ormPersistentType.removeSpecifiedPersistentAttribute(ormPersistentAttribute);
		
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		ormConverter = (EclipseLinkObjectTypeConverter) ((EclipseLinkConvert) ormBasicMapping.getSpecifiedConverter()).getConverter();
		
		EclipseLinkObjectTypeConverter javaConverter = ((EclipseLinkObjectTypeConverter) ((EclipseLinkConvert) javaBasicMapping.getSpecifiedConverter()).getConverter());
		javaConverter.setDataType("bar");
		assertEquals("bar", ormConverter.getDataType());
		assertEquals("bar", javaConverter.getDataType());
		
		//set metadata-complete, test virtual mapping	
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		assertEquals(null,  ormBasicMapping.getSpecifiedConverter());
		assertEquals(org.eclipse.jpt.core.context.Converter.NO_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals("bar", javaConverter.getDataType());
	}
	
	public void testModifyDataType() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = ((OrmBasicMapping) ormPersistentAttribute.getMapping()); 
		ormBasicMapping.setSpecifiedConverter(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER);
		((EclipseLinkConvert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		EclipseLinkObjectTypeConverter ormConverter = (EclipseLinkObjectTypeConverter) ((EclipseLinkConvert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
	
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
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping(); 
		ormBasicMapping.setSpecifiedConverter(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER);
		((EclipseLinkConvert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		EclipseLinkObjectTypeConverter ormConverter = (EclipseLinkObjectTypeConverter) ((EclipseLinkConvert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
		JavaBasicMapping javaBasicMapping = (JavaBasicMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
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
		
				
		//remove the specified persistent attribute, test virtual mapping	
		ormPersistentType.removeSpecifiedPersistentAttribute(ormPersistentAttribute);
		
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		ormConverter = (EclipseLinkObjectTypeConverter) ((EclipseLinkConvert) ormBasicMapping.getSpecifiedConverter()).getConverter();
		
		EclipseLinkObjectTypeConverter javaConverter = ((EclipseLinkObjectTypeConverter) ((EclipseLinkConvert) javaBasicMapping.getSpecifiedConverter()).getConverter());
		javaConverter.setObjectType("bar");
		assertEquals("bar", ormConverter.getObjectType());
		assertEquals("bar", javaConverter.getObjectType());
		
		//set metadata-complete, test virtual mapping	
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		assertEquals(null,  ormBasicMapping.getSpecifiedConverter());
		assertEquals(org.eclipse.jpt.core.context.Converter.NO_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals("bar", javaConverter.getObjectType());
	}
	
	public void testModifyObjectType() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = ((OrmBasicMapping) ormPersistentAttribute.getMapping()); 
		ormBasicMapping.setSpecifiedConverter(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER);
		((EclipseLinkConvert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		EclipseLinkObjectTypeConverter ormConverter = (EclipseLinkObjectTypeConverter) ((EclipseLinkConvert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
	
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
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		OrmBasicMapping ormBasicMapping = ((OrmBasicMapping) ormPersistentAttribute.getMapping()); 
		ormBasicMapping.setSpecifiedConverter(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER);
		((EclipseLinkConvert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		OrmEclipseLinkObjectTypeConverter ormConverter = (OrmEclipseLinkObjectTypeConverter) ((EclipseLinkConvert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
		JavaBasicMapping javaBasicMapping = (JavaBasicMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertEquals(null, ormConverter.getName());
		assertEquals(null, converterResource.getName());
				
		//set converter class name in the resource model, verify context model updated
		converterResource.setName("myConvert");
		assertEquals("myConvert", ormConverter.getName());
		assertEquals("myConvert", converterResource.getName());

		//set converter class name to null in the resource model
		converterResource.setName(null);
		assertEquals(null, ormConverter.getName());
		assertEquals(null, converterResource.getName());
		

		//remove the specified persistent attribute, test virtual mapping	
				
		ormPersistentType.removeSpecifiedPersistentAttribute(ormPersistentAttribute);
		
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		ormConverter = (OrmEclipseLinkObjectTypeConverter) ((EclipseLinkConvert) ormBasicMapping.getSpecifiedConverter()).getConverter();
		
		EclipseLinkObjectTypeConverter javaConverter = ((EclipseLinkObjectTypeConverter) ((EclipseLinkConvert) javaBasicMapping.getSpecifiedConverter()).getConverter());
		javaConverter.setName("bar");
		assertEquals("bar", ormConverter.getName());
		assertEquals("bar", javaConverter.getName());
		
		
		//set metadata-complete, test virtual mapping
		
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		assertEquals(null,  ormBasicMapping.getSpecifiedConverter());
		assertEquals(org.eclipse.jpt.core.context.Converter.NO_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals("bar", javaConverter.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = ((OrmBasicMapping) ormPersistentAttribute.getMapping()); 
		ormBasicMapping.setSpecifiedConverter(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER);
		((EclipseLinkConvert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		OrmEclipseLinkObjectTypeConverter ormConverter = (OrmEclipseLinkObjectTypeConverter) ((EclipseLinkConvert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
	
		assertEquals(null, ormConverter.getName());
		assertEquals(null, converterResource.getName());
				
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
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping(); 
		ormBasicMapping.setSpecifiedConverter(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER);
		((EclipseLinkConvert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		EclipseLinkObjectTypeConverter ormConverter = (EclipseLinkObjectTypeConverter) ((EclipseLinkConvert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
		JavaBasicMapping javaBasicMapping = (JavaBasicMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
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
		
				
		//remove the specified persistent attribute, test virtual mapping	
		ormPersistentType.removeSpecifiedPersistentAttribute(ormPersistentAttribute);
		
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		ormConverter = (EclipseLinkObjectTypeConverter) ((EclipseLinkConvert) ormBasicMapping.getSpecifiedConverter()).getConverter();
		
		EclipseLinkObjectTypeConverter javaConverter = ((EclipseLinkObjectTypeConverter) ((EclipseLinkConvert) javaBasicMapping.getSpecifiedConverter()).getConverter());
		javaConverter.setDefaultObjectValue("bar");
		assertEquals("bar", ormConverter.getDefaultObjectValue());
		assertEquals("bar", javaConverter.getDefaultObjectValue());
		
		//set metadata-complete, test virtual mapping	
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		assertEquals(null,  ormBasicMapping.getSpecifiedConverter());
		assertEquals(org.eclipse.jpt.core.context.Converter.NO_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals("bar", javaConverter.getDefaultObjectValue());
	}
	
	public void testModifyDefaultObjectValue() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = ((OrmBasicMapping) ormPersistentAttribute.getMapping()); 
		ormBasicMapping.setSpecifiedConverter(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER);
		((EclipseLinkConvert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		EclipseLinkObjectTypeConverter ormConverter = (EclipseLinkObjectTypeConverter) ((EclipseLinkConvert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
	
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
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping(); 
		ormBasicMapping.setSpecifiedConverter(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER);
		((EclipseLinkConvert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		EclipseLinkObjectTypeConverter ormConverter = (EclipseLinkObjectTypeConverter) ((EclipseLinkConvert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
		JavaBasicMapping javaBasicMapping = (JavaBasicMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();

		assertEquals(0, ormConverter.conversionValuesSize());
		assertEquals(0, converterResource.getConversionValues().size());
		
		//add conversion value to resource model, check context model
		XmlConversionValue resourceConversionValue = EclipseLinkOrmFactory.eINSTANCE.createXmlConversionValue();
		converterResource.getConversionValues().add(resourceConversionValue);
		resourceConversionValue.setDataValue("foo");
		resourceConversionValue.setObjectValue("bar");
		
		assertEquals(1, ormConverter.conversionValuesSize());
		ListIterator<EclipseLinkConversionValue> contextConversionValues = ormConverter.conversionValues();
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

		assertEquals(2, ormConverter.conversionValuesSize());
		contextConversionValues = ormConverter.conversionValues();
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
		assertEquals(2, ormConverter.conversionValuesSize());
		contextConversionValues = ormConverter.conversionValues();
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
		assertEquals(1, ormConverter.conversionValuesSize());
		contextConversionValues = ormConverter.conversionValues();
		contextConversionValue = contextConversionValues.next();
		assertEquals("foo2", contextConversionValue.getDataValue());
		assertEquals("bar2", contextConversionValue.getObjectValue());
		assertEquals(1, converterResource.getConversionValues().size());
		assertEquals("foo2", converterResource.getConversionValues().get(0).getDataValue());
		assertEquals("bar2", converterResource.getConversionValues().get(0).getObjectValue());

		//clear the conversion value resource model list
		converterResource.getConversionValues().clear();
		assertEquals(0, ormConverter.conversionValuesSize());
		assertEquals(0, converterResource.getConversionValues().size());
		
		//add conversion value to java context model, verify does not affect orm context model
	
		EclipseLinkObjectTypeConverter javaConverter = (EclipseLinkObjectTypeConverter) ((JavaEclipseLinkConvert) javaBasicMapping.getConverter()).getConverter();
		EclipseLinkConversionValue javaConversionValue = javaConverter.addConversionValue();
		javaConversionValue.setDataValue("baz");
		
		assertEquals(0, ormConverter.conversionValuesSize());
		assertEquals(0, converterResource.getConversionValues().size());
		assertEquals(1, javaConverter.conversionValuesSize());
		
		//remove orm attribute mapping, verify virtual mapping has conversion values from java
		
		ormPersistentType.removeSpecifiedPersistentAttribute(ormPersistentAttribute);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		ormConverter = (EclipseLinkObjectTypeConverter) ((EclipseLinkConvert) ormBasicMapping.getConverter()).getConverter();
		
		assertEquals(1, ormConverter.conversionValuesSize());
		contextConversionValues = ormConverter.conversionValues();
		contextConversionValue = contextConversionValues.next();
		assertEquals("baz", contextConversionValue.getDataValue());
		assertEquals(0, converterResource.getConversionValues().size());
		assertEquals(1, javaConverter.conversionValuesSize());
		
		//set metadata-complete to true, verify virtual mapping ignores the conversion values from java
		
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		assertEquals(EclipseLinkConverter.NO_CONVERTER, ormBasicMapping.getConverter().getType());
	}
	
	public void testModifyConversionValues() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping(); 
		ormBasicMapping.setSpecifiedConverter(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER);
		((EclipseLinkConvert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		EclipseLinkObjectTypeConverter ormConverter = (EclipseLinkObjectTypeConverter) ((EclipseLinkConvert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
		
		assertEquals(0, ormConverter.conversionValuesSize());
		assertEquals(0, converterResource.getConversionValues().size());
		
		//add conversion value to context model, check resource model
		EclipseLinkConversionValue contextConversionValue = ormConverter.addConversionValue();
		contextConversionValue.setDataValue("foo");
		contextConversionValue.setObjectValue("bar");

		assertEquals(1, ormConverter.conversionValuesSize());
		ListIterator<EclipseLinkConversionValue> contextConversionValues = ormConverter.conversionValues();
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

		assertEquals(2, ormConverter.conversionValuesSize());
		contextConversionValues = ormConverter.conversionValues();
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
		assertEquals(2, ormConverter.conversionValuesSize());
		contextConversionValues = ormConverter.conversionValues();
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
		assertEquals(1, ormConverter.conversionValuesSize());
		contextConversionValues = ormConverter.conversionValues();
		contextConversionValue = contextConversionValues.next();
		assertEquals("foo2", contextConversionValue.getDataValue());
		assertEquals("bar2", contextConversionValue.getObjectValue());
		assertEquals(1, converterResource.getConversionValues().size());
		assertEquals("foo2", converterResource.getConversionValues().get(0).getDataValue());
		assertEquals("bar2", converterResource.getConversionValues().get(0).getObjectValue());

		//clear the conversion value resource model list
		ormConverter.removeConversionValue(0);
		assertEquals(0, ormConverter.conversionValuesSize());
		assertEquals(0, converterResource.getConversionValues().size());
	}

	public void testInitializeConversionValues() throws Exception {
		createTestEntityWithConvertAndObjectTypeConverterConversionValue();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping(); 
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) ormBasicMapping.getConverter();
		EclipseLinkObjectTypeConverter converter = (EclipseLinkObjectTypeConverter) eclipseLinkConvert.getConverter();

		assertEquals(1, converter.conversionValuesSize());
		assertEquals("f", converter.conversionValues().next().getDataValue());
		assertEquals("female", converter.conversionValues().next().getObjectValue());
	}
}

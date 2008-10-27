/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.ObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkOrmObjectTypeConverterTests
	extends EclipseLinkOrmContextModelTestCase
{
	private void createConvertAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "Convert", "String value() default \"none\";");		
	}

	private void createObjectTypeConverterAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "ObjectTypeConverter", "String name(); String converterClass();");		
	}
	
	private ICompilationUnit createTestEntityWithBasicMapping() throws Exception {
		createConvertAnnotation();
		createObjectTypeConverterAnnotation();
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

	
	public EclipseLinkOrmObjectTypeConverterTests(String name) {
		super(name);
	}
	
	
	public void testUpdateDataType() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping(); 
		ormBasicMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		((Convert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		ObjectTypeConverter ormConverter = (ObjectTypeConverter) ((Convert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
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
		ormConverter = (ObjectTypeConverter) ((Convert) ormBasicMapping.getSpecifiedConverter()).getConverter();
		
		ObjectTypeConverter javaConverter = ((ObjectTypeConverter) ((Convert) javaBasicMapping.getSpecifiedConverter()).getConverter());
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = ((OrmBasicMapping) ormPersistentAttribute.getMapping()); 
		ormBasicMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		((Convert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		ObjectTypeConverter ormConverter = (ObjectTypeConverter) ((Convert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
	
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
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping(); 
		ormBasicMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		((Convert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		ObjectTypeConverter ormConverter = (ObjectTypeConverter) ((Convert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
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
		ormConverter = (ObjectTypeConverter) ((Convert) ormBasicMapping.getSpecifiedConverter()).getConverter();
		
		ObjectTypeConverter javaConverter = ((ObjectTypeConverter) ((Convert) javaBasicMapping.getSpecifiedConverter()).getConverter());
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = ((OrmBasicMapping) ormPersistentAttribute.getMapping()); 
		ormBasicMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		((Convert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		ObjectTypeConverter ormConverter = (ObjectTypeConverter) ((Convert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
	
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
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		OrmBasicMapping ormBasicMapping = ((OrmBasicMapping) ormPersistentAttribute.getMapping()); 
		ormBasicMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		((Convert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		EclipseLinkOrmObjectTypeConverter ormConverter = (EclipseLinkOrmObjectTypeConverter) ((Convert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
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
		ormConverter = (EclipseLinkOrmObjectTypeConverter) ((Convert) ormBasicMapping.getSpecifiedConverter()).getConverter();
		
		ObjectTypeConverter javaConverter = ((ObjectTypeConverter) ((Convert) javaBasicMapping.getSpecifiedConverter()).getConverter());
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = ((OrmBasicMapping) ormPersistentAttribute.getMapping()); 
		ormBasicMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		((Convert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		EclipseLinkOrmObjectTypeConverter ormConverter = (EclipseLinkOrmObjectTypeConverter) ((Convert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
	
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
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping(); 
		ormBasicMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		((Convert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		ObjectTypeConverter ormConverter = (ObjectTypeConverter) ((Convert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
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
		ormConverter = (ObjectTypeConverter) ((Convert) ormBasicMapping.getSpecifiedConverter()).getConverter();
		
		ObjectTypeConverter javaConverter = ((ObjectTypeConverter) ((Convert) javaBasicMapping.getSpecifiedConverter()).getConverter());
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = ((OrmBasicMapping) ormPersistentAttribute.getMapping()); 
		ormBasicMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		((Convert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
		ObjectTypeConverter ormConverter = (ObjectTypeConverter) ((Convert) ormBasicMapping.getConverter()).getConverter();
		XmlObjectTypeConverter converterResource = ((XmlBasic) ormResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getObjectTypeConverter();
	
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

}

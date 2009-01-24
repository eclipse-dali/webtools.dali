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
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.StructConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkOrmStructConverterTests
	extends EclipseLinkOrmContextModelTestCase
{
	private void createConvertAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "Convert", "String value() default \"none\";");		
	}

	private void createStructConverterAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "StructConverter", "String name(); String converterClass();");		
	}
	
	private ICompilationUnit createTestEntityWithBasicMapping() throws Exception {
		createConvertAnnotation();
		createStructConverterAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC, EclipseLinkJPA.CONVERT, EclipseLinkJPA.STRUCT_CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic").append(CR);
				sb.append("    @Convert(name=\"foo\")").append(CR);
				sb.append("    @StructConverter");
			}
		});
	}

	
	public EclipseLinkOrmStructConverterTests(String name) {
		super(name);
	}
	
	
	public void testUpdateConverter() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping(); 
		ormBasicMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		((Convert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.STRUCT_CONVERTER);
		StructConverter ormConverter = (StructConverter) ((Convert) ormBasicMapping.getConverter()).getConverter();
		XmlStructConverter converterResource = ((XmlBasic) getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getStructConverter();
		JavaBasicMapping javaBasicMapping = (JavaBasicMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertEquals(null, ormConverter.getConverterClass());
		assertEquals(null, converterResource.getConverter());
				
		//set converter class name in the resource model, verify context model updated
		converterResource.setConverter("myConvert");
		assertEquals("myConvert", ormConverter.getConverterClass());
		assertEquals("myConvert", converterResource.getConverter());

		//set converter class name to null in the resource model
		converterResource.setConverter(null);
		assertEquals(null, ormConverter.getConverterClass());
		assertEquals(null, converterResource.getConverter());
		
				
		//remove the specified persistent attribute, test virtual mapping	
		ormPersistentType.removeSpecifiedPersistentAttribute(ormPersistentAttribute);
		
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		ormConverter = (StructConverter) ((Convert) ormBasicMapping.getSpecifiedConverter()).getConverter();
		
		StructConverter javaConverter = ((StructConverter) ((Convert) javaBasicMapping.getSpecifiedConverter()).getConverter());
		javaConverter.setConverterClass("bar");
		assertEquals("bar", ormConverter.getConverterClass());
		assertEquals("bar", javaConverter.getConverterClass());
		
		//set metadata-complete, test virtual mapping	
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		assertEquals(null,  ormBasicMapping.getSpecifiedConverter());
		assertEquals(org.eclipse.jpt.core.context.Converter.NO_CONVERTER, ormBasicMapping.getConverter().getType());
		assertEquals("bar", javaConverter.getConverterClass());
	}
	
	public void testModifyConverter() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = ((OrmBasicMapping) ormPersistentAttribute.getMapping()); 
		ormBasicMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		((Convert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.STRUCT_CONVERTER);
		StructConverter ormConverter = (StructConverter) ((Convert) ormBasicMapping.getConverter()).getConverter();
		XmlStructConverter converterResource = ((XmlBasic) getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getStructConverter();
	
		assertEquals(null, ormConverter.getConverterClass());
		assertEquals(null, converterResource.getConverter());
				
		//set converter class in the context model, verify resource model updated
		ormConverter.setConverterClass("foo");
		assertEquals("foo", ormConverter.getConverterClass());
		assertEquals("foo", converterResource.getConverter());
	
		ormConverter.setConverterClass(null);
		assertEquals(null, ormConverter.getConverterClass());
		assertEquals(null, converterResource.getConverter());
	}
	
	public void testUpdateName() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		OrmBasicMapping ormBasicMapping = ((OrmBasicMapping) ormPersistentAttribute.getMapping()); 
		ormBasicMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		((Convert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.STRUCT_CONVERTER);
		EclipseLinkOrmStructConverter ormConverter = (EclipseLinkOrmStructConverter) ((Convert) ormBasicMapping.getConverter()).getConverter();
		XmlStructConverter converterResource = ((XmlBasic) getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getStructConverter();
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
		ormConverter = (EclipseLinkOrmStructConverter) ((Convert) ormBasicMapping.getSpecifiedConverter()).getConverter();
		
		StructConverter javaConverter = ((StructConverter) ((Convert) javaBasicMapping.getSpecifiedConverter()).getConverter());
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
		ormBasicMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		((Convert) ormBasicMapping.getConverter()).setConverter(EclipseLinkConverter.STRUCT_CONVERTER);
		EclipseLinkOrmStructConverter ormConverter = (EclipseLinkOrmStructConverter) ((Convert) ormBasicMapping.getConverter()).getConverter();
		XmlStructConverter converterResource = ((XmlBasic) getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getStructConverter();
	
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
}

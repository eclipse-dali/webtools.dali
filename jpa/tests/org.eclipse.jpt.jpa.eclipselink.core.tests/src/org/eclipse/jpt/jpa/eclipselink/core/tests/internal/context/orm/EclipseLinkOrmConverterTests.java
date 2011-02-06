/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter;

@SuppressWarnings("nls")
public class EclipseLinkOrmConverterTests
	extends EclipseLinkOrmContextModelTestCase
{
	
	private ICompilationUnit createTestEntityWithBasicMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC, EclipseLink.CONVERT, EclipseLink.CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic").append(CR);
				sb.append("    @Convert(name=\"foo\")").append(CR);
				sb.append("    @Converter");
			}
		});
	}

	
	public EclipseLinkOrmConverterTests(String name) {
		super(name);
	}
	
	
	public void testUpdateConverterClass() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping(); 
		ormBasicMapping.setConverter(EclipseLinkConvert.class);
		((EclipseLinkConvert) ormBasicMapping.getConverter()).setConverter(EclipseLinkCustomConverter.class);
		OrmEclipseLinkCustomConverter ormConverter = (OrmEclipseLinkCustomConverter) ((EclipseLinkConvert) ormBasicMapping.getConverter()).getConverter();
		XmlConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getConverter();
		JavaBasicMapping javaBasicMapping = (JavaBasicMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertEquals(null, ormConverter.getConverterClass());
		assertEquals(null, converterResource.getClassName());
				
		//set converter class name in the resource model, verify context model updated
		converterResource.setClassName("myConvert");
		assertEquals("myConvert", ormConverter.getConverterClass());
		assertEquals("myConvert", converterResource.getClassName());

		//set converter class name to null in the resource model
		converterResource.setClassName(null);
		assertEquals(null, ormConverter.getConverterClass());
		assertEquals(null, converterResource.getClassName());
		
		//remove the specified persistent attribute, test virtual mapping	
		ormPersistentAttribute.convertToVirtual();
		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("id");
		BasicMapping virtualBasicMapping = (BasicMapping) ormPersistentAttribute2.getMapping();
		EclipseLinkCustomConverter virtualConverter = (EclipseLinkCustomConverter) ((EclipseLinkConvert) virtualBasicMapping.getConverter()).getConverter();
		
		EclipseLinkCustomConverter javaConverter = ((EclipseLinkCustomConverter) ((EclipseLinkConvert) javaBasicMapping.getConverter()).getConverter());
		javaConverter.setConverterClass("bar");
		assertEquals("bar", virtualConverter.getConverterClass());
		assertEquals("bar", javaConverter.getConverterClass());
		
		//set metadata-complete, test virtual mapping	
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("id");
		virtualBasicMapping = (BasicMapping) ormPersistentAttribute2.getMapping();
		assertNull(virtualBasicMapping.getConverter().getType());
		assertEquals("bar", javaConverter.getConverterClass());
	}
	
	public void testModifyConverterClass() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = ((OrmBasicMapping) ormPersistentAttribute.getMapping()); 
		ormBasicMapping.setConverter(EclipseLinkConvert.class);
		((EclipseLinkConvert) ormBasicMapping.getConverter()).setConverter(EclipseLinkCustomConverter.class);
		OrmEclipseLinkCustomConverter ormConverter = (OrmEclipseLinkCustomConverter) ((EclipseLinkConvert) ormBasicMapping.getConverter()).getConverter();
		XmlConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getConverter();
	
		assertEquals(null, ormConverter.getConverterClass());
		assertEquals(null, converterResource.getClassName());
				
		//set converter class in the context model, verify resource model updated
		ormConverter.setConverterClass("foo");
		assertEquals("foo", ormConverter.getConverterClass());
		assertEquals("foo", converterResource.getClassName());
	
		ormConverter.setConverterClass(null);
		assertEquals(null, ormConverter.getConverterClass());
		assertEquals(null, converterResource.getClassName());
	}
	
	public void testUpdateName() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "id");
		OrmBasicMapping ormBasicMapping = ((OrmBasicMapping) ormPersistentAttribute.getMapping()); 
		ormBasicMapping.setConverter(EclipseLinkConvert.class);
		((EclipseLinkConvert) ormBasicMapping.getConverter()).setConverter(EclipseLinkCustomConverter.class);
		OrmEclipseLinkCustomConverter ormConverter = (OrmEclipseLinkCustomConverter) ((EclipseLinkConvert) ormBasicMapping.getConverter()).getConverter();
		XmlConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getConverter();
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
		ormPersistentAttribute.convertToVirtual();
		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("id");
		BasicMapping virtualBasicMapping = (BasicMapping) ormPersistentAttribute2.getMapping();
		EclipseLinkCustomConverter virtualConverter = (EclipseLinkCustomConverter) ((EclipseLinkConvert) virtualBasicMapping.getConverter()).getConverter();
		
		EclipseLinkCustomConverter javaConverter = ((EclipseLinkCustomConverter) ((EclipseLinkConvert) javaBasicMapping.getConverter()).getConverter());
		javaConverter.setName("bar");
		assertEquals("bar", virtualConverter.getName());
		assertEquals("bar", javaConverter.getName());
		
		//set metadata-complete, test virtual mapping	
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("id");
		virtualBasicMapping = (BasicMapping) ormPersistentAttribute2.getMapping();
		assertNull(virtualBasicMapping.getConverter().getType());
		assertEquals("bar", javaConverter.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = ((OrmBasicMapping) ormPersistentAttribute.getMapping()); 
		ormBasicMapping.setConverter(EclipseLinkConvert.class);
		((EclipseLinkConvert) ormBasicMapping.getConverter()).setConverter(EclipseLinkCustomConverter.class);
		OrmEclipseLinkCustomConverter ormConverter = (OrmEclipseLinkCustomConverter) ((EclipseLinkConvert) ormBasicMapping.getConverter()).getConverter();
		XmlConverter converterResource = ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getConverter();
	
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

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
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLinkOrmConverterTests
	extends EclipseLinkContextModelTestCase
{
	
	private ICompilationUnit createTestEntityWithBasicMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.BASIC, EclipseLink.CONVERT, EclipseLink.CONVERTER);
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
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmEclipseLinkBasicMapping ormBasicMapping = (OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping(); 
		OrmEclipseLinkCustomConverter ormConverter = ormBasicMapping.getConverterContainer().addCustomConverter("customConverter", 0);
		XmlConverter converterResource = (XmlConverter) ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getConverters().get(0);
		
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
	}
	
	public void testModifyConverterClass() throws Exception {
		createTestEntityWithBasicMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmEclipseLinkBasicMapping ormBasicMapping = (OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping(); 
		OrmEclipseLinkCustomConverter ormConverter = ormBasicMapping.getConverterContainer().addCustomConverter("customConverter", 0);
		XmlConverter converterResource = (XmlConverter) ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getConverters().get(0);
		
	
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
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		OrmEclipseLinkBasicMapping ormBasicMapping = (OrmEclipseLinkBasicMapping) ormPersistentAttribute.getMapping(); 
		OrmEclipseLinkCustomConverter ormConverter = ormBasicMapping.getConverterContainer().addCustomConverter("customConverter", 0);
		XmlConverter converterResource = (XmlConverter) ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getConverters().get(0);
		
		assertEquals("customConverter", ormConverter.getName());
		assertEquals("customConverter", converterResource.getName());
				
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
		OrmEclipseLinkCustomConverter ormConverter = ormBasicMapping.getConverterContainer().addCustomConverter("customConverter", 0);
		XmlConverter converterResource = (XmlConverter) ((XmlBasic) getXmlEntityMappings().getEntities().get(0).getAttributes().getBasics().get(0)).getConverters().get(0);
	
		assertEquals("customConverter", ormConverter.getName());
		assertEquals("customConverter", converterResource.getName());
				
		//set converter class in the context model, verify resource model updated
		ormConverter.setName("foo");
		assertEquals("foo", ormConverter.getName());
		assertEquals("foo", converterResource.getName());
	
		ormConverter.setName(null);
		assertEquals(null, ormConverter.getName());
		assertEquals(null, converterResource.getName());
	}
}

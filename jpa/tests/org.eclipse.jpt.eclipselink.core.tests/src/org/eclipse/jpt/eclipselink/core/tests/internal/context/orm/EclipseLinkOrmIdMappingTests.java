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
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkIdMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmBasicMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmIdMapping;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlId;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkOrmIdMappingTests
	extends EclipseLinkOrmContextModelTestCase
{
	public EclipseLinkOrmIdMappingTests(String name) {
		super(name);
	}
	
	private void createConvertAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "Convert", "String value() default \"none\";");		
	}

	private void createMutableAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "Mutable", "boolean value() default true");		
	}
	
	private ICompilationUnit createTestEntityWithIdMapping() throws Exception {
		createConvertAnnotation();
		createMutableAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithMutableIdDate() throws Exception {
		createMutableAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, EclipseLinkJPA.MUTABLE, "java.util.Date");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("    @Mutable").append(CR);
				sb.append("    private Date myDate;").append(CR);
				sb.append(CR);
				sb.append("    ");
			}
		});
	}

	public void testUpdateMutable() throws Exception {
		createTestEntityWithIdMapping();
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		EclipseLinkOrmIdMapping contextId = 
			(EclipseLinkOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlId resourceId = 
			(XmlId) resourceEntity.getAttributes().getIds().get(0);
		EclipseLinkIdMapping javaIdMapping = (EclipseLinkIdMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		// check defaults
		
		assertNull(resourceId.getMutable());
		assertTrue(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertTrue(contextId.getMutable().isMutable());
		
		// set xml mutable to false, check context
		
		resourceId.setMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceId.getMutable());
		assertTrue(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		// set xml mutable to true, check context
		
		resourceId.setMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceId.getMutable());
		assertTrue(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextId.getMutable().getSpecifiedMutable());
		assertTrue(contextId.getMutable().isMutable());
		
		// clear xml mutable, check context
		
		resourceId.setMutable(null);
		
		assertNull(resourceId.getMutable());
		assertTrue(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertTrue(contextId.getMutable().isMutable());
		
		// TODO - test defaults for java serializable and date/time types, 
		// with and without persistence property
		
		// set mutable on java basic mapping
		
		javaIdMapping.getMutable().setSpecifiedMutable(Boolean.FALSE);
		assertNull(resourceId.getMutable());
		assertTrue(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertTrue(contextId.getMutable().isMutable());
		assertFalse(javaIdMapping.getMutable().isMutable());
		
		// remove attribute from xml, test default mutable from java
		
		ormPersistentType.removeSpecifiedPersistentAttribute(ormPersistentAttribute);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		contextId = (EclipseLinkOrmIdMapping) ormPersistentAttribute.getMapping();
		
		assertNull(resourceId.getMutable());
		assertTrue(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		assertFalse(javaIdMapping.getMutable().isMutable());
		
		// set metadata complete
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		EclipseLinkOrmBasicMapping contextBasic = (EclipseLinkOrmBasicMapping) ormPersistentAttribute.getMapping();
		assertNull(resourceId.getMutable());
		assertTrue(contextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextBasic.getMutable().getSpecifiedMutable());
		assertTrue(contextBasic.getMutable().isMutable());
		assertFalse(javaIdMapping.getMutable().isMutable());
	}
	
	public void testUpdateMutableDate() throws Exception {
		createTestEntityWithMutableIdDate();
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "myDate");
		EclipseLinkOrmIdMapping contextId = 
			(EclipseLinkOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlId resourceId = 
			(XmlId) resourceEntity.getAttributes().getIds().get(0);
		EclipseLinkIdMapping javaIdMapping = (EclipseLinkIdMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("myDate").getMapping();
		
		// check defaults
		
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		// set xml mutable to false, check context
		
		resourceId.setMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		// set xml mutable to true, check context
		
		resourceId.setMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextId.getMutable().getSpecifiedMutable());
		assertTrue(contextId.getMutable().isMutable());
		
		// clear xml mutable, check context
		
		resourceId.setMutable(null);
		
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		
		getPersistenceUnit().getOptions().setTemporalMutable(Boolean.TRUE);
		assertNull(resourceId.getMutable());
		assertTrue(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertTrue(contextId.getMutable().isMutable());
		
		getPersistenceUnit().getOptions().setTemporalMutable(Boolean.FALSE);
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		getPersistenceUnit().getOptions().setTemporalMutable(null);
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		// set mutable on java id mapping
		
		javaIdMapping.getMutable().setSpecifiedMutable(Boolean.TRUE);
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		assertTrue(javaIdMapping.getMutable().isMutable());
		
		// remove attribute from xml, test default mutable from java
		
		ormPersistentType.removeSpecifiedPersistentAttribute(ormPersistentAttribute);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		contextId = (EclipseLinkOrmIdMapping) ormPersistentAttribute.getMapping();
		
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextId.getMutable().getSpecifiedMutable());
		assertTrue(contextId.getMutable().isMutable());
		assertTrue(javaIdMapping.getMutable().isMutable());
		
		// set metadata complete
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		EclipseLinkOrmBasicMapping contextBasic = (EclipseLinkOrmBasicMapping) ormPersistentAttribute.getMapping();
		assertNull(resourceId.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
		assertTrue(javaIdMapping.getMutable().isMutable());
	}

	public void testModifyMutable() throws Exception {
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute =
			ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "basic");
		EclipseLinkOrmIdMapping contextId = 
			(EclipseLinkOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlId resourceId = 
			(XmlId) resourceEntity.getAttributes().getIds().get(0);
		
		// check defaults
		
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		// set context mutable to true, check resource
		
		contextId.getMutable().setSpecifiedMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextId.getMutable().getSpecifiedMutable());
		assertTrue(contextId.getMutable().isMutable());
		
		// set context mutable to false, check resource
		
		contextId.getMutable().setSpecifiedMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
		
		// set context read only to null, check resource
		
		contextId.getMutable().setSpecifiedMutable(null);
		
		assertNull(resourceId.getMutable());
		assertFalse(contextId.getMutable().isDefaultMutable());
		assertNull(contextId.getMutable().getSpecifiedMutable());
		assertFalse(contextId.getMutable().isMutable());
	}
	
	public void testUpdateConvert() throws Exception {
		createTestEntityWithIdMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId basicResource = (XmlId) getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		JavaIdMapping javaIdMapping = (JavaIdMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertEquals(null, ormIdMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
				
		//set lob in the resource model, verify context model updated
		basicResource.setConvert("myConvert");
		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, ormIdMapping.getConverter().getType());
		assertEquals("myConvert", basicResource.getConvert());

		//set lob to null in the resource model
		basicResource.setConvert(null);
		assertEquals(null, ormIdMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
		
		
		javaIdMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		((Convert) javaIdMapping.getSpecifiedConverter()).setSpecifiedConverterName("foo");
		
		assertEquals(null, ormIdMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
		assertEquals("foo", ((Convert) javaIdMapping.getSpecifiedConverter()).getSpecifiedConverterName());
		
		
		ormPersistentType.removeSpecifiedPersistentAttribute(ormPersistentAttribute);
		ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		
		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, ormIdMapping.getSpecifiedConverter().getType());
		assertEquals("foo", ((Convert) ormIdMapping.getSpecifiedConverter()).getSpecifiedConverterName());
		assertEquals(null, basicResource.getConvert());
		assertEquals("foo", ((Convert) javaIdMapping.getSpecifiedConverter()).getSpecifiedConverterName());
		
		((Convert) javaIdMapping.getSpecifiedConverter()).setSpecifiedConverterName("bar");
		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, ormIdMapping.getSpecifiedConverter().getType());
		assertEquals("bar", ((Convert) ormIdMapping.getSpecifiedConverter()).getSpecifiedConverterName());
		assertEquals(null, basicResource.getConvert());
		assertEquals("bar", ((Convert) javaIdMapping.getSpecifiedConverter()).getSpecifiedConverterName());

		javaIdMapping.setSpecifiedConverter(Converter.NO_CONVERTER);
		assertEquals(null, ormIdMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
		assertEquals(null, javaIdMapping.getSpecifiedConverter());
	}
	
	public void testModifyConvert() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId basicResource = (XmlId) getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
	
		assertEquals(null, ormIdMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
				
		//set lob in the context model, verify resource model updated
		ormIdMapping.setSpecifiedConverter(Convert.ECLIPSE_LINK_CONVERTER);
		assertEquals("", basicResource.getConvert());
		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, ormIdMapping.getConverter().getType());
	
		((Convert) ormIdMapping.getSpecifiedConverter()).setSpecifiedConverterName("bar");
		assertEquals("bar", basicResource.getConvert());
		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, ormIdMapping.getConverter().getType());
		assertEquals("bar", ((Convert) ormIdMapping.getConverter()).getSpecifiedConverterName());

		((Convert) ormIdMapping.getSpecifiedConverter()).setSpecifiedConverterName(null);

		assertEquals(null, ormIdMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());

		//set lob to false in the context model
		ormIdMapping.setSpecifiedConverter(null);
		assertEquals(null, ormIdMapping.getSpecifiedConverter());
		assertEquals(null, basicResource.getConvert());
	}
}

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
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkVersionMapping;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmVersionMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvert;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlVersion;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLinkOrmVersionMappingTests
	extends EclipseLinkContextModelTestCase
{
	public EclipseLinkOrmVersionMappingTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestEntityWithVersionMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.VERSION);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Version").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithMutableVersionDate() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.VERSION, EclipseLink.MUTABLE, "java.util.Date");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Version").append(CR);
				sb.append("    @Mutable").append(CR);
				sb.append("    private Date myDate;").append(CR);
				sb.append(CR);
				sb.append("    ");
			}
		});
	}
	
	public void testUpdateMutable() throws Exception {
		createTestEntityWithVersionMapping();
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = 
			ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		EclipseLinkOrmVersionMapping contextVersion = 
			(EclipseLinkOrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlVersion resourceVersion = 
			(XmlVersion) resourceEntity.getAttributes().getVersions().get(0);
		EclipseLinkVersionMapping javaVersionMapping = (EclipseLinkVersionMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		// check defaults
		
		assertNull(resourceVersion.getMutable());
		assertTrue(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertTrue(contextVersion.getMutable().isMutable());
		
		// set xml mutable to false, check context
		
		resourceVersion.setMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceVersion.getMutable());
		assertTrue(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		// set xml mutable to true, check context
		
		resourceVersion.setMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceVersion.getMutable());
		assertTrue(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextVersion.getMutable().getSpecifiedMutable());
		assertTrue(contextVersion.getMutable().isMutable());
		
		// clear xml mutable, check context
		
		resourceVersion.setMutable(null);
		
		assertNull(resourceVersion.getMutable());
		assertTrue(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertTrue(contextVersion.getMutable().isMutable());
		
		// TODO - test defaults for java serializable and date/time types, 
		// with and without persistence property
		
		// set mutable on java basic mapping
		
		javaVersionMapping.getMutable().setSpecifiedMutable(Boolean.FALSE);
		assertNull(resourceVersion.getMutable());
		assertTrue(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertTrue(contextVersion.getMutable().isMutable());
		assertFalse(javaVersionMapping.getMutable().isMutable());
		
		// remove attribute from xml, test default mutable from java
		ormPersistentAttribute.removeFromXml();
		OrmPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("id");
		EclipseLinkVersionMapping virtualContextVersion = (EclipseLinkVersionMapping) ormPersistentAttribute2.getMapping();
		
		assertNull(resourceVersion.getMutable());
		assertTrue(virtualContextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, virtualContextVersion.getMutable().getSpecifiedMutable());
		assertFalse(virtualContextVersion.getMutable().isMutable());
		assertFalse(javaVersionMapping.getMutable().isMutable());
		
		// set metadata complete
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("id");
		EclipseLinkBasicMapping contextBasic = (EclipseLinkBasicMapping) ormPersistentAttribute2.getMapping();
		assertNull(resourceVersion.getMutable());
		assertTrue(contextBasic.getMutable().isDefaultMutable());
		assertNull(contextBasic.getMutable().getSpecifiedMutable());
		assertTrue(contextBasic.getMutable().isMutable());
		assertFalse(javaVersionMapping.getMutable().isMutable());
	}
	
	public void testUpdateMutableDate() throws Exception {
		createTestEntityWithMutableVersionDate();
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = 
			ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("myDate"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		EclipseLinkOrmVersionMapping contextVersion = 
			(EclipseLinkOrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlVersion resourceVersion = 
			(XmlVersion) resourceEntity.getAttributes().getVersions().get(0);
		EclipseLinkVersionMapping javaVersionMapping = (EclipseLinkVersionMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("myDate").getMapping();
		
		// check defaults
		
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		// set xml mutable to false, check context
		
		resourceVersion.setMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		// set xml mutable to true, check context
		
		resourceVersion.setMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextVersion.getMutable().getSpecifiedMutable());
		assertTrue(contextVersion.getMutable().isMutable());
		
		// clear xml mutable, check context
		
		resourceVersion.setMutable(null);
		
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		
		getPersistenceUnit().getEclipseLinkOptions().setTemporalMutable(Boolean.TRUE);
		assertNull(resourceVersion.getMutable());
		assertTrue(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertTrue(contextVersion.getMutable().isMutable());
		
		getPersistenceUnit().getEclipseLinkOptions().setTemporalMutable(Boolean.FALSE);
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		getPersistenceUnit().getEclipseLinkOptions().setTemporalMutable(null);
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		// set mutable on java version mapping
		
		javaVersionMapping.getMutable().setSpecifiedMutable(Boolean.TRUE);
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		assertTrue(javaVersionMapping.getMutable().isMutable());
		
		// remove attribute from xml, test default mutable from java
		ormPersistentAttribute.removeFromXml();
		OrmPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("myDate");
		EclipseLinkVersionMapping virtualContextVersion = (EclipseLinkVersionMapping) ormPersistentAttribute2.getMapping();
		
		assertNull(resourceVersion.getMutable());
		assertFalse(virtualContextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, virtualContextVersion.getMutable().getSpecifiedMutable());
		assertTrue(virtualContextVersion.getMutable().isMutable());
		assertTrue(javaVersionMapping.getMutable().isMutable());
		
		// set metadata complete
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("myDate");
		EclipseLinkBasicMapping contextBasic = (EclipseLinkBasicMapping) ormPersistentAttribute2.getMapping();
		assertNull(resourceVersion.getMutable());
		assertFalse(contextBasic.getMutable().isDefaultMutable());
		assertNull(contextBasic.getMutable().getSpecifiedMutable());
		assertFalse(contextBasic.getMutable().isMutable());
		assertTrue(javaVersionMapping.getMutable().isMutable());
	}
	
	public void testModifyMutable() throws Exception {
		createTestEntityWithMutableVersionDate();
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = 
			ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("myDate"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		EclipseLinkOrmVersionMapping contextVersion = 
			(EclipseLinkOrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlEntity resourceEntity = 
			(XmlEntity)getXmlEntityMappings().getEntities().get(0);
		XmlVersion resourceVersion = 
			(XmlVersion) resourceEntity.getAttributes().getVersions().get(0);
		
		// check defaults
		
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		// set context mutable to true, check resource
		
		contextVersion.getMutable().setSpecifiedMutable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.TRUE, contextVersion.getMutable().getSpecifiedMutable());
		assertTrue(contextVersion.getMutable().isMutable());
		
		// set context mutable to false, check resource
		
		contextVersion.getMutable().setSpecifiedMutable(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertEquals(Boolean.FALSE, contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
		
		// set context read only to null, check resource
		
		contextVersion.getMutable().setSpecifiedMutable(null);
		
		assertNull(resourceVersion.getMutable());
		assertFalse(contextVersion.getMutable().isDefaultMutable());
		assertNull(contextVersion.getMutable().getSpecifiedMutable());
		assertFalse(contextVersion.getMutable().isMutable());
	}
	
	public void testUpdateConvert() throws Exception {
		createTestEntityWithVersionMapping();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		OrmVersionMapping ormVersionMapping = (OrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlVersion versionResource = (XmlVersion) getXmlEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		XmlConvert xmlConvert = (XmlConvert) versionResource.getConvert();
		JavaVersionMapping javaVersionMapping = (JavaVersionMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(ormVersionMapping.getConverter().getConverterType());
		assertEquals(null, xmlConvert);
				
		//set lob in the resource model, verify context model updated
		xmlConvert = EclipseLinkOrmFactory.eINSTANCE.createXmlConvert();
		xmlConvert.setConvert("myConvert");
		versionResource.setConvert(xmlConvert);
		assertEquals(EclipseLinkConvert.class, ormVersionMapping.getConverter().getConverterType());
		assertEquals("myConvert", ((EclipseLinkConvert) ormVersionMapping.getConverter()).getConverterName());

		//set lob to null in the resource model
		versionResource.setConvert(null);
		assertNull(ormVersionMapping.getConverter().getConverterType());
		assertEquals(null, versionResource.getConvert());
		
		
		javaVersionMapping.setConverter(EclipseLinkConvert.class);
		((EclipseLinkConvert) javaVersionMapping.getConverter()).setSpecifiedConverterName("foo");
		
		assertNull(ormVersionMapping.getConverter().getConverterType());
		assertEquals(null, versionResource.getConvert());
		assertEquals("foo", ((EclipseLinkConvert) javaVersionMapping.getConverter()).getSpecifiedConverterName());
		
		
		ormPersistentAttribute.removeFromXml();
		OrmPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("id");
		VersionMapping virtualVersionMapping = (VersionMapping) ormPersistentAttribute2.getMapping();
		
		assertEquals(EclipseLinkConvert.class, virtualVersionMapping.getConverter().getConverterType());
		assertEquals("foo", ((EclipseLinkConvert) virtualVersionMapping.getConverter()).getSpecifiedConverterName());
		assertEquals(null, versionResource.getConvert());
		assertEquals("foo", ((EclipseLinkConvert) javaVersionMapping.getConverter()).getSpecifiedConverterName());
		
		((EclipseLinkConvert) javaVersionMapping.getConverter()).setSpecifiedConverterName("bar");
		assertEquals(EclipseLinkConvert.class, virtualVersionMapping.getConverter().getConverterType());
		assertEquals("bar", ((EclipseLinkConvert) virtualVersionMapping.getConverter()).getSpecifiedConverterName());
		assertEquals(null, versionResource.getConvert());
		assertEquals("bar", ((EclipseLinkConvert) javaVersionMapping.getConverter()).getSpecifiedConverterName());

		javaVersionMapping.setConverter(null);
		assertNull(virtualVersionMapping.getConverter().getConverterType());
		assertNull(versionResource.getConvert());
		assertNull(javaVersionMapping.getConverter().getConverterType());
	}
	
	public void testModifyConvert() throws Exception {
		createTestEntityWithVersionMapping();
		OrmPersistentType ormPersistentType = 
			getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmSpecifiedPersistentAttribute ormPersistentAttribute = 
			ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		OrmVersionMapping ormVersionMapping = (OrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlVersion versionResource = (XmlVersion) getXmlEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		XmlConvert xmlConvert = (XmlConvert) versionResource.getConvert();
	
		assertNull(ormVersionMapping.getConverter().getConverterType());
		assertNull(xmlConvert);
				
		//set lob in the context model, verify resource model updated
		ormVersionMapping.setConverter(EclipseLinkConvert.class);
		xmlConvert = (XmlConvert) versionResource.getConvert();
		assertEquals("none", xmlConvert.getConvert());
		assertEquals(EclipseLinkConvert.class, ormVersionMapping.getConverter().getConverterType());
	
		((EclipseLinkConvert) ormVersionMapping.getConverter()).setSpecifiedConverterName("bar");
		assertEquals("bar", xmlConvert.getConvert());
		assertEquals(EclipseLinkConvert.class, ormVersionMapping.getConverter().getConverterType());
		assertEquals("bar", ((EclipseLinkConvert) ormVersionMapping.getConverter()).getSpecifiedConverterName());

		((EclipseLinkConvert) ormVersionMapping.getConverter()).setSpecifiedConverterName(null);

		assertNull(ormVersionMapping.getConverter().getConverterType());
		assertEquals(null, versionResource.getConvert());

		//set lob to false in the context model
		ormVersionMapping.setConverter(null);
		assertNull(ormVersionMapping.getConverter().getConverterType());
		assertNull(versionResource.getConvert());
	}
}

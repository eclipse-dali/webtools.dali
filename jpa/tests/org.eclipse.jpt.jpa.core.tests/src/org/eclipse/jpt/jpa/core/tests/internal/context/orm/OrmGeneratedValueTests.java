/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.GeneratedValue;
import org.eclipse.jpt.jpa.core.context.GenerationType;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.jpa.core.resource.orm.XmlId;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;


@SuppressWarnings("nls")public class OrmGeneratedValueTests extends ContextModelTestCase
{
	public OrmGeneratedValueTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}
	
	public void testUpdateSpecifiedName() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		OrmGeneratedValue ormGeneratedValue = ormIdMapping.addGeneratedValue();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		XmlGeneratedValue generatedValueResource = idResource.getGeneratedValue();
		
		//set generator in the resource model, verify context model updated
		generatedValueResource.setGenerator("FOO");
		assertEquals("FOO", ormGeneratedValue.getSpecifiedGenerator());
		assertEquals("FOO", generatedValueResource.getGenerator());
	
		//set name to null in the resource model
		generatedValueResource.setGenerator(null);
		assertNull(ormGeneratedValue.getSpecifiedGenerator());
		assertNull(generatedValueResource.getGenerator());
	}
	
	public void testModifySpecifiedName() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmModifiablePersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		OrmGeneratedValue ormGeneratedValue = ormIdMapping.addGeneratedValue();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		XmlGeneratedValue generatedValueResource = idResource.getGeneratedValue();
		
		//set name in the context model, verify resource model modified
		ormGeneratedValue.setSpecifiedGenerator("FOO");
		assertEquals("FOO", generatedValueResource.getGenerator());
		assertEquals("FOO", ormGeneratedValue.getSpecifiedGenerator());
		
		//set name to null in the context model
		ormGeneratedValue.setSpecifiedGenerator(null);
		assertNull(generatedValueResource.getGenerator());
		assertNull(ormGeneratedValue.getSpecifiedGenerator());
	}

	public void testDefaultsFromJava() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		OrmReadOnlyPersistentAttribute ormIdAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		IdMapping ormIdMapping = (IdMapping) ormIdAttribute.getMapping();
		assertEquals(null, ormIdMapping.getGeneratedValue());
		
		IdMapping javaIdMapping = (IdMapping) ormPersistentType.getJavaPersistentType().getAttributes().iterator().next().getMapping();
		GeneratedValue javaGeneratedValue = javaIdMapping.addGeneratedValue();
		javaGeneratedValue.setSpecifiedGenerator("Foo");
		javaGeneratedValue.setSpecifiedStrategy(GenerationType.SEQUENCE);
		
		assertEquals("Foo", ormIdMapping.getGeneratedValue().getSpecifiedGenerator());
		assertEquals(GenerationType.SEQUENCE, ormIdMapping.getGeneratedValue().getSpecifiedStrategy());
		assertEquals("Foo", javaGeneratedValue.getSpecifiedGenerator());
		assertEquals(GenerationType.SEQUENCE, javaGeneratedValue.getSpecifiedStrategy());
		
		ormIdAttribute.addToXml();
		ormIdAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		ormIdMapping = (IdMapping) ormIdAttribute.getMapping();
		assertEquals(null, ormIdMapping.getGeneratedValue());
		assertEquals("Foo", javaGeneratedValue.getSpecifiedGenerator());
		assertEquals(GenerationType.SEQUENCE, javaGeneratedValue.getSpecifiedStrategy());
		
		((OrmModifiablePersistentAttribute) ormIdAttribute).removeFromXml();
		ormIdAttribute = ormPersistentType.getAttributeNamed("id");
		ormIdMapping = (IdMapping) ormIdAttribute.getMapping();
		assertEquals("Foo", ormIdMapping.getGeneratedValue().getSpecifiedGenerator());
		assertEquals(GenerationType.SEQUENCE, ormIdMapping.getGeneratedValue().getSpecifiedStrategy());
		assertEquals("Foo", javaGeneratedValue.getSpecifiedGenerator());
		assertEquals(GenerationType.SEQUENCE, javaGeneratedValue.getSpecifiedStrategy());
	}
}
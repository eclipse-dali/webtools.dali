/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.GeneratedValue;
import org.eclipse.jpt.core.context.GenerationType;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


@SuppressWarnings("nls")public class OrmGeneratedValueTests extends ContextModelTestCase
{
	public OrmGeneratedValueTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
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
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
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
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
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
		
		OrmPersistentAttribute ormIdAttribute = ormPersistentType.virtualAttributes().next();
		IdMapping ormIdMapping = (IdMapping) ormIdAttribute.getMapping();
		assertEquals(null, ormIdMapping.getGeneratedValue());
		
		IdMapping javaIdMapping = (IdMapping) ormPersistentType.getJavaPersistentType().attributes().next().getMapping();
		GeneratedValue javaGeneratedValue = javaIdMapping.addGeneratedValue();
		javaGeneratedValue.setSpecifiedGenerator("Foo");
		javaGeneratedValue.setSpecifiedStrategy(GenerationType.SEQUENCE);
		
		assertEquals("Foo", ormIdMapping.getGeneratedValue().getSpecifiedGenerator());
		assertEquals(GenerationType.SEQUENCE, ormIdMapping.getGeneratedValue().getSpecifiedStrategy());
		assertEquals("Foo", javaGeneratedValue.getSpecifiedGenerator());
		assertEquals(GenerationType.SEQUENCE, javaGeneratedValue.getSpecifiedStrategy());
		
		ormIdAttribute.makeSpecified();
		ormIdAttribute = ormPersistentType.specifiedAttributes().next();
		ormIdMapping = (IdMapping) ormIdAttribute.getMapping();
		assertEquals(null, ormIdMapping.getGeneratedValue());
		assertEquals("Foo", javaGeneratedValue.getSpecifiedGenerator());
		assertEquals(GenerationType.SEQUENCE, javaGeneratedValue.getSpecifiedStrategy());
		
		ormIdAttribute.makeVirtual();
		ormIdAttribute = ormPersistentType.virtualAttributes().next();
		ormIdMapping = (IdMapping) ormIdAttribute.getMapping();
		assertEquals("Foo", ormIdMapping.getGeneratedValue().getSpecifiedGenerator());
		assertEquals(GenerationType.SEQUENCE, ormIdMapping.getGeneratedValue().getSpecifiedStrategy());
		assertEquals("Foo", javaGeneratedValue.getSpecifiedGenerator());
		assertEquals(GenerationType.SEQUENCE, javaGeneratedValue.getSpecifiedStrategy());
	}
}
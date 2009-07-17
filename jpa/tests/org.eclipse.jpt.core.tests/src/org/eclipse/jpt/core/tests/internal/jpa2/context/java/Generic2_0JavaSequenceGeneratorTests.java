/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.java;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.GeneratorContainer;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.SequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.jpa2.context.SequenceGenerator2_0;
import org.eclipse.jpt.core.jpa2.resource.java.SequenceGenerator2_0Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

/**
 *  Generic2_0JavaSequenceGeneratorTests
 */
@SuppressWarnings("nls")
public class Generic2_0JavaSequenceGeneratorTests extends Generic2_0ContextModelTestCase
{
	private static final String SEQUENCE_GENERATOR_NAME = "TEST_SEQUENCE_GENERATOR";

	public Generic2_0JavaSequenceGeneratorTests(String name) {
		super(name);
	}
	
	// ********** catalog **********

	public void testGetCatalog() throws Exception {
		this.createTestEntityWithSequenceGenerator();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();

		GeneratorContainer cc = idMapping.getGeneratorContainer();
		SequenceGenerator sg = cc.getSequenceGenerator();
		
		SequenceGenerator2_0 sequenceGenerator = (SequenceGenerator2_0) idMapping.getGeneratorContainer().getSequenceGenerator();
		
		assertNull(sequenceGenerator.getCatalog());
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		SequenceGenerator2_0Annotation annotation = (SequenceGenerator2_0Annotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		annotation.setCatalog("testCatalog");
		
		assertEquals("testCatalog", sequenceGenerator.getCatalog());
		assertEquals("testCatalog", sequenceGenerator.getSpecifiedCatalog());
	}
	
	public void testGetDefaultCatalog() throws Exception {
		this.createTestEntityWithSequenceGenerator();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		SequenceGenerator2_0 sequenceGenerator = (SequenceGenerator2_0) idMapping.getGeneratorContainer().getSequenceGenerator();

		assertNull(sequenceGenerator.getDefaultCatalog());
		
		sequenceGenerator.setSpecifiedCatalog("testCatalog");
		
		assertNull(sequenceGenerator.getDefaultCatalog());
		assertEquals("testCatalog", sequenceGenerator.getSpecifiedCatalog());
	}
	
	public void testSetSpecifiedCatalog() throws Exception {
		this.createTestEntityWithSequenceGenerator();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		SequenceGenerator2_0 sequenceGenerator = (SequenceGenerator2_0) idMapping.getGeneratorContainer().getSequenceGenerator();

		sequenceGenerator.setSpecifiedCatalog("testCatalog");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		SequenceGenerator2_0Annotation annotation = (SequenceGenerator2_0Annotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		assertEquals("testCatalog", annotation.getCatalog());
		
		sequenceGenerator.setName(null);
		sequenceGenerator.setSpecifiedCatalog(null);
		assertNull(attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR));
	}
	
	// ********** schema **********

	public void testGetSchema() throws Exception {
		this.createTestEntityWithSequenceGenerator();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) this.getJavaPersistentType().getAttributeNamed("id").getMapping();
		SequenceGenerator2_0 sequenceGenerator = (SequenceGenerator2_0) idMapping.getGeneratorContainer().getSequenceGenerator();

		assertNull(sequenceGenerator.getSchema());
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		SequenceGenerator2_0Annotation annotation = (SequenceGenerator2_0Annotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		annotation.setSchema("testSchema");
		
		assertEquals("testSchema", sequenceGenerator.getSchema());
		assertEquals("testSchema", sequenceGenerator.getSpecifiedSchema());
	}

	public void testGetDefaultSchema() throws Exception {
		this.createTestEntityWithSequenceGenerator();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		SequenceGenerator2_0 sequenceGenerator = (SequenceGenerator2_0) idMapping.getGeneratorContainer().getSequenceGenerator();

		assertNull(sequenceGenerator.getDefaultSchema());
		
		sequenceGenerator.setSpecifiedSchema("testSchema");
		
		assertNull(sequenceGenerator.getDefaultSchema());
		assertEquals("testSchema", sequenceGenerator.getSpecifiedSchema());
	}
	
	public void testUpdateDefaultSchemaFromPersistenceUnitDefaults() throws Exception {
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);

		createTestEntityWithSequenceGenerator();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		IdMapping idMapping = (IdMapping)  ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		SequenceGenerator2_0 sequenceGenerator = (SequenceGenerator2_0) idMapping.getGeneratorContainer().getSequenceGenerator();
		
		assertNull(sequenceGenerator.getDefaultSchema());
		
		this.getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedSchema("FOO");
		assertEquals("FOO", sequenceGenerator.getDefaultSchema());
		
		this.getEntityMappings().setSpecifiedSchema("BAR");
		assertEquals("BAR", sequenceGenerator.getDefaultSchema());
		
		ormEntity.getTable().setSpecifiedSchema("XML_SCHEMA");
		assertEquals("BAR", sequenceGenerator.getDefaultSchema());

		this.getEntityMappings().removePersistentType(0);
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		//default schema taken from persistence-unit-defaults not entity-mappings since the entity is not in an orm.xml file
		idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		sequenceGenerator = (SequenceGenerator2_0) idMapping.getGeneratorContainer().getSequenceGenerator();
		assertEquals("FOO", sequenceGenerator.getDefaultSchema());
	}

	public void testSetSpecifiedSchema() throws Exception {
		this.createTestEntityWithSequenceGenerator();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		SequenceGenerator2_0 sequenceGenerator = (SequenceGenerator2_0) idMapping.getGeneratorContainer().getSequenceGenerator();
		
		sequenceGenerator.setSpecifiedSchema("testSchema");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		SequenceGenerator2_0Annotation annotation = (SequenceGenerator2_0Annotation) attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		assertEquals("testSchema", annotation.getSchema());
		
		sequenceGenerator.setName(null);
		sequenceGenerator.setSpecifiedSchema(null);
		assertNull(attributeResource.getSupportingAnnotation(JPA.SEQUENCE_GENERATOR));
	}
	
	// ********** utility **********

	protected ICompilationUnit createTestEntityWithSequenceGenerator() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.SEQUENCE_GENERATOR, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("@SequenceGenerator(name=\"" + SEQUENCE_GENERATOR_NAME + "\")");
			}
		});
	}
}

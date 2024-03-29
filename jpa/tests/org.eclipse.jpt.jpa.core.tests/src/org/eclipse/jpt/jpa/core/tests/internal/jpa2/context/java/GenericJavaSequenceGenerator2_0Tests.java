/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.jpa2.context.SequenceGenerator2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.SequenceGeneratorAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

/**
 *  GenericJavaSequenceGenerator2_0Tests
 */
@SuppressWarnings("nls")
public class GenericJavaSequenceGenerator2_0Tests extends Generic2_0ContextModelTestCase
{
	private static final String SEQUENCE_GENERATOR_NAME = "TEST_SEQUENCE_GENERATOR";

	public GenericJavaSequenceGenerator2_0Tests(String name) {
		super(name);
	}
	
	// ********** catalog **********

	public void testGetCatalog() throws Exception {
		this.createTestEntityWithSequenceGenerator();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		SequenceGenerator2_0 sequenceGenerator = (SequenceGenerator2_0) idMapping.getGeneratorContainer().getSequenceGenerator();
		
		assertNull(sequenceGenerator.getCatalog());
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		SequenceGeneratorAnnotation2_0 annotation = (SequenceGeneratorAnnotation2_0) resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		annotation.setCatalog("testCatalog");
		getJpaProject().synchronizeContextModel();
		
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
		
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		SequenceGeneratorAnnotation2_0 annotation = (SequenceGeneratorAnnotation2_0) resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR);	
		assertEquals("testCatalog", annotation.getCatalog());
		
		sequenceGenerator.setSpecifiedCatalog(null);
		assertNull(annotation.getCatalog());
	}
	
	// ********** schema **********

	public void testGetSchema() throws Exception {
		this.createTestEntityWithSequenceGenerator();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) this.getJavaPersistentType().getAttributeNamed("id").getMapping();
		SequenceGenerator2_0 sequenceGenerator = (SequenceGenerator2_0) idMapping.getGeneratorContainer().getSequenceGenerator();

		assertNull(sequenceGenerator.getSchema());
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		SequenceGeneratorAnnotation2_0 annotation = (SequenceGeneratorAnnotation2_0) resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR);	
		
		annotation.setSchema("testSchema");
		getJpaProject().synchronizeContextModel();
		
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
		mappingFileRef.setFileName(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
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

		this.getEntityMappings().removeManagedType(0);
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
		
		JavaResourceField resourceField  = ((JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE)).getFields().iterator().next();
		SequenceGeneratorAnnotation2_0 annotation = (SequenceGeneratorAnnotation2_0) resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR);	
		assertEquals("testSchema", annotation.getSchema());
		
		sequenceGenerator.setSpecifiedSchema(null);
		assertNull(annotation.getSchema());
	}
	
	// ********** utility **********

	protected ICompilationUnit createTestEntityWithSequenceGenerator() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.SEQUENCE_GENERATOR, JPA.ID);
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

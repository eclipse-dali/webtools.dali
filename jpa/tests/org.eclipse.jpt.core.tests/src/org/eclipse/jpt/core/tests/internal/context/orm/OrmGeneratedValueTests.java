/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmIdMapping;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class OrmGeneratedValueTests extends ContextModelTestCase
{
	public OrmGeneratedValueTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		persistenceResource().save(null);
	}
	
	private void createEntityAnnotation() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createIdAnnotation() throws Exception {
		this.createAnnotationAndMembers("Id", "");		
	}
	
	private IType createTestEntity() throws Exception {
		createEntityAnnotation();
		createIdAnnotation();
	
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
	
	private IType createTestSubType() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("AnnotationTestTypeChild").append(" ");
				sb.append("extends " + TYPE_NAME + " ");
				sb.append("{}").append(CR);
			}
		};
		return this.javaProject.createType(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
	}
	
	public void testUpdateSpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();
		OrmGeneratedValue ormGeneratedValue = xmlIdMapping.addGeneratedValue();
		XmlId idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();
		OrmGeneratedValue ormGeneratedValue = xmlIdMapping.addGeneratedValue();
		XmlId idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
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

}
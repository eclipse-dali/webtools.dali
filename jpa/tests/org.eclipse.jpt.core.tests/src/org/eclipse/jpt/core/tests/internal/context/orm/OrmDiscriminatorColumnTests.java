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
import org.eclipse.jpt.core.context.DiscriminatorType;
import org.eclipse.jpt.core.context.orm.OrmDiscriminatorColumn;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class OrmDiscriminatorColumnTests extends ContextModelTestCase
{
	public OrmDiscriminatorColumnTests(String name) {
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
	
	private void createTestSubType() throws Exception {
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
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
	}
	
	public void testUpdateSpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmDiscriminatorColumn ormColumn = ormEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormColumn.getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the resource model, verify context model updated
		entityResource.setDiscriminatorColumn(OrmFactory.eINSTANCE.createXmlDiscriminatorColumn());
		entityResource.getDiscriminatorColumn().setName("FOO");
		assertEquals("FOO", ormColumn.getSpecifiedName());
		assertEquals("FOO", entityResource.getDiscriminatorColumn().getName());
	
		//set name to null in the resource model
		entityResource.getDiscriminatorColumn().setName(null);
		assertNull(ormColumn.getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn().getName());
		
		entityResource.getDiscriminatorColumn().setName("FOO");
		assertEquals("FOO", ormColumn.getSpecifiedName());
		assertEquals("FOO", entityResource.getDiscriminatorColumn().getName());

		entityResource.setDiscriminatorColumn(null);
		assertNull(ormColumn.getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
	public void testModifySpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmDiscriminatorColumn ormColumn = ormEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormColumn.getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the context model, verify resource model modified
		ormColumn.setSpecifiedName("foo");
		assertEquals("foo", ormColumn.getSpecifiedName());
		assertEquals("foo", entityResource.getDiscriminatorColumn().getName());
		
		//set name to null in the context model
		ormColumn.setSpecifiedName(null);
		assertNull(ormColumn.getSpecifiedName());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
//	public void testUpdateDefaultNameFromJavaTable() throws Exception {
//		createTestEntity();
//		
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlEntity ormEntity = (XmlEntity) ormPersistentType.getMapping();
//		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
//		
//		ormEntity.javaEntity().getTable().setSpecifiedName("Foo");
//		assertEquals("Foo", ormEntity.getTable().getDefaultName());
//		
//		ormEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
//		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
//
//		ormEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
//		ormEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
//		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
//	
//		ormEntity.setSpecifiedMetadataComplete(null);
//		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
//		
//		ormEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
//		assertEquals("Foo", ormEntity.getTable().getDefaultName());
//		
//		ormEntity.getTable().setSpecifiedName("Bar");
//		assertEquals(TYPE_NAME, ormEntity.getTable().getDefaultName());
//	}
//	
//	public void testUpdateDefaultNameNoJava() throws Exception {
//		createTestEntity();
//		
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlEntity ormEntity = (XmlEntity) ormPersistentType.getMapping();
//		assertEquals("Foo", ormEntity.getTable().getDefaultName());
//	}
//	
//	public void testUpdateDefaultNameFromParent() throws Exception {
//		createTestEntity();
//		createTestSubType();
//		
//		OrmPersistentType parentOrmPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		OrmPersistentType childOrmPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
//		XmlEntity parentXmlEntity = (XmlEntity) parentOrmPersistentType.getMapping();
//		XmlEntity childXmlEntity = (XmlEntity) childOrmPersistentType.getMapping();
//		
//		assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
//		assertEquals(TYPE_NAME, childXmlEntity.getTable().getDefaultName());
//		
//		parentXmlEntity.getTable().setSpecifiedName("FOO");
//		assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
//		assertEquals("FOO", childXmlEntity.getTable().getDefaultName());
//
//		parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
//		assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
//		assertEquals("AnnotationTestTypeChild", childXmlEntity.getTable().getDefaultName());
//	}
	
	public void testUpdateSpecifiedLength() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmDiscriminatorColumn ormColumn = ormEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormColumn.getSpecifiedLength());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the resource model, verify context model updated
		entityResource.setDiscriminatorColumn(OrmFactory.eINSTANCE.createXmlDiscriminatorColumn());
		entityResource.getDiscriminatorColumn().setLength(Integer.valueOf(8));
		assertEquals(Integer.valueOf(8), ormColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(8), entityResource.getDiscriminatorColumn().getLength());
	
		//set name to null in the resource model
		entityResource.getDiscriminatorColumn().setLength(null);
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(entityResource.getDiscriminatorColumn().getLength());
		
		entityResource.getDiscriminatorColumn().setLength(Integer.valueOf(11));
		assertEquals(Integer.valueOf(11), ormColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(11), entityResource.getDiscriminatorColumn().getLength());

		entityResource.setDiscriminatorColumn(null);
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
	public void testModifySpecifiedLength() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmDiscriminatorColumn ormColumn = ormEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormColumn.getSpecifiedLength());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the context model, verify resource model modified
		ormColumn.setSpecifiedLength(Integer.valueOf(7));
		assertEquals(Integer.valueOf(7), ormColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(7), entityResource.getDiscriminatorColumn().getLength());
		
		//set name to null in the context model
		ormColumn.setSpecifiedLength(null);
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(entityResource.getDiscriminatorColumn());
	}

	public void testUpdateSpecifiedColumnDefinition() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmDiscriminatorColumn ormColumn = ormEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormColumn.getColumnDefinition());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the resource model, verify context model updated
		entityResource.setDiscriminatorColumn(OrmFactory.eINSTANCE.createXmlDiscriminatorColumn());
		entityResource.getDiscriminatorColumn().setColumnDefinition("FOO");
		assertEquals("FOO", ormColumn.getColumnDefinition());
		assertEquals("FOO", entityResource.getDiscriminatorColumn().getColumnDefinition());
	
		//set name to null in the resource model
		entityResource.getDiscriminatorColumn().setColumnDefinition(null);
		assertNull(ormColumn.getColumnDefinition());
		assertNull(entityResource.getDiscriminatorColumn().getColumnDefinition());
		
		entityResource.getDiscriminatorColumn().setColumnDefinition("FOO");
		assertEquals("FOO", ormColumn.getColumnDefinition());
		assertEquals("FOO", entityResource.getDiscriminatorColumn().getColumnDefinition());

		entityResource.setDiscriminatorColumn(null);
		assertNull(ormColumn.getColumnDefinition());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
	public void testModifySpecifiedColumnDefinition() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmDiscriminatorColumn ormColumn = ormEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormColumn.getColumnDefinition());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set name in the context model, verify resource model modified
		ormColumn.setColumnDefinition("foo");
		assertEquals("foo", ormColumn.getColumnDefinition());
		assertEquals("foo", entityResource.getDiscriminatorColumn().getColumnDefinition());
		
		//set name to null in the context model
		ormColumn.setColumnDefinition(null);
		assertNull(ormColumn.getColumnDefinition());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
	public void testUpdateSpecifiedDiscriminatorType() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmDiscriminatorColumn ormColumn = ormEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormColumn.getSpecifiedDiscriminatorType());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set discriminator type in the resource model, verify context model updated
		entityResource.setDiscriminatorColumn(OrmFactory.eINSTANCE.createXmlDiscriminatorColumn());
		entityResource.getDiscriminatorColumn().setDiscriminatorType(org.eclipse.jpt.core.resource.orm.DiscriminatorType.STRING);
		assertEquals(DiscriminatorType.STRING, ormColumn.getSpecifiedDiscriminatorType());
		assertEquals(org.eclipse.jpt.core.resource.orm.DiscriminatorType.STRING, entityResource.getDiscriminatorColumn().getDiscriminatorType());
	
		//set discriminator type to null in the resource model
		entityResource.getDiscriminatorColumn().setDiscriminatorType(null);
		assertNull(ormColumn.getSpecifiedDiscriminatorType());
		assertNull(entityResource.getDiscriminatorColumn().getDiscriminatorType());
		
		entityResource.getDiscriminatorColumn().setDiscriminatorType(org.eclipse.jpt.core.resource.orm.DiscriminatorType.CHAR);
		assertEquals(DiscriminatorType.CHAR, ormColumn.getSpecifiedDiscriminatorType());
		assertEquals(org.eclipse.jpt.core.resource.orm.DiscriminatorType.CHAR, entityResource.getDiscriminatorColumn().getDiscriminatorType());

		entityResource.setDiscriminatorColumn(null);
		assertNull(ormColumn.getSpecifiedDiscriminatorType());
		assertNull(entityResource.getDiscriminatorColumn());
	}
	
	public void testModifySpecifiedDiscriminatorType() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmDiscriminatorColumn ormColumn = ormEntity.getDiscriminatorColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);

		assertNull(ormColumn.getSpecifiedDiscriminatorType());
		assertNull(entityResource.getDiscriminatorColumn());
		
		//set discriminator type in the context model, verify resource model modified
		ormColumn.setSpecifiedDiscriminatorType(DiscriminatorType.STRING);
		assertEquals(DiscriminatorType.STRING, ormColumn.getSpecifiedDiscriminatorType());
		assertEquals(org.eclipse.jpt.core.resource.orm.DiscriminatorType.STRING, entityResource.getDiscriminatorColumn().getDiscriminatorType());
		
		//set discriminator type to null in the context model
		ormColumn.setSpecifiedDiscriminatorType(null);
		assertNull(ormColumn.getSpecifiedDiscriminatorType());
		assertNull(entityResource.getDiscriminatorColumn());
	}
}
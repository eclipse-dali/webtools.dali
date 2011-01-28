/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.core.internal.context.orm.VirtualOrmPersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class OrmJoinTableTests extends ContextModelTestCase
{
	public OrmJoinTableTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString());
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}

	private ICompilationUnit createTestEntityWithValidManyToMany() throws Exception {		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.ID, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToMany").append(CR);
				sb.append("    private Collection<Project> projects;").append(CR);
			}
		});
	}
	
	private void createTargetEntity() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ID);
					sb.append(";");
					sb.append(CR);
				sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class Project {").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int proj_id;").append(CR);
				sb.append(CR);
				sb.append("}");
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Project.java", sourceWriter);
	}

	private void createTargetEntityWithBackPointer() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ID);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.MANY_TO_MANY);
					sb.append(";");
					sb.append(CR);
				sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class Project {").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int proj_id;").append(CR);
				sb.append("    @ManyToMany(mappedBy=\"projects\"").append(CR);
				sb.append("    private java.util.Collection<" + TYPE_NAME + "> employees;").append(CR);
				sb.append(CR);
				sb.append("}");
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Project.java", sourceWriter);
	}

	public void testUpdateSpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		assertNull(ormJoinTable.getSpecifiedName());
		assertNull(manyToMany.getJoinTable());
		
		
		//set name in the resource model, verify context model updated
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		manyToMany.getJoinTable().setName("FOO");
		assertEquals("FOO", ormJoinTable.getSpecifiedName());
		assertEquals("FOO", manyToMany.getJoinTable().getName());
	
		//set name to null in the resource model
		manyToMany.getJoinTable().setName(null);
		assertNull(ormJoinTable.getSpecifiedName());
		assertNull(manyToMany.getJoinTable().getName());
		
		manyToMany.getJoinTable().setName("FOO");
		assertEquals("FOO", ormJoinTable.getSpecifiedName());
		assertEquals("FOO", manyToMany.getJoinTable().getName());

		manyToMany.setJoinTable(null);
		assertNull(ormJoinTable.getSpecifiedName());
		assertNull(manyToMany.getJoinTable());
	}
	
	public void testModifySpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		assertNull(ormJoinTable.getSpecifiedName());
		assertNull(manyToMany.getJoinTable());
		
		//set name in the context model, verify resource model modified
		ormJoinTable.setSpecifiedName("foo");
		assertEquals("foo", ormJoinTable.getSpecifiedName());
		assertEquals("foo", manyToMany.getJoinTable().getName());
		
		//set name to null in the context model
		ormJoinTable.setSpecifiedName(null);
		assertNull(ormJoinTable.getSpecifiedName());
		assertNull(manyToMany.getJoinTable());
	}
	
	public void testVirtualJoinTable() throws Exception {
		createTestEntityWithValidManyToMany();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);

		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("projects");
		ManyToManyMapping virtualManyToManyMapping = (ManyToManyMapping) ormPersistentAttribute.getMapping();
		JoinTable virtualJoinTable = virtualManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		assertTrue(ormPersistentAttribute.isVirtual());
		assertEquals(null, virtualJoinTable.getSpecifiedName());

		createTargetEntity();
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME_ + "Project");

		assertEquals(TYPE_NAME + "_Project", virtualJoinTable.getName());
		assertNull(virtualJoinTable.getSpecifiedCatalog());
		assertNull(virtualJoinTable.getSpecifiedSchema());
		assertEquals(0, virtualJoinTable.specifiedJoinColumnsSize());
		assertEquals(0, virtualJoinTable.specifiedInverseJoinColumnsSize());
		JoinColumn ormJoinColumn = virtualJoinTable.getDefaultJoinColumn();
//TODO need to test joinColumn defaults here as well as in java and all the relatioship mapping types
//		assertEquals("id_project_id", ormJoinColumn.getDefaultName());
//		assertEquals("id_project_id", ormJoinColumn.getDefaultReferencedColumnName());
		JoinColumn inverseOrmJoinColumn = virtualJoinTable.getDefaultInverseJoinColumn();
//		assertEquals("id_project_id", inverseOrmJoinColumn.getDefaultName());
//		assertEquals("id_project_id", inverseOrmJoinColumn.getDefaultReferencedColumnName());
	
		JavaPersistentAttribute javaPersistentAttribute = ormPersistentAttribute.getJavaPersistentAttribute();
		JavaManyToManyMapping javaManyToManyMapping = (JavaManyToManyMapping) javaPersistentAttribute.getMapping();
		JavaJoinTable javaJoinTable = javaManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		javaJoinTable.setSpecifiedName("FOO");
		javaJoinTable.setSpecifiedCatalog("CATALOG");
		javaJoinTable.setSpecifiedSchema("SCHEMA");
		JavaJoinColumn javaJoinColumn = javaJoinTable.addSpecifiedJoinColumn(0);
		javaJoinColumn.setSpecifiedName("NAME");
		javaJoinColumn.setSpecifiedReferencedColumnName("REFERENCED_NAME");
		JavaJoinColumn inverseJavaJoinColumn = javaJoinTable.addSpecifiedInverseJoinColumn(0);
		inverseJavaJoinColumn.setSpecifiedName("INVERSE_NAME");
		inverseJavaJoinColumn.setSpecifiedReferencedColumnName("INVERSE_REFERENCED_NAME");
		
		assertEquals("FOO", virtualJoinTable.getSpecifiedName());
		assertEquals("CATALOG", virtualJoinTable.getSpecifiedCatalog());
		assertEquals("SCHEMA", virtualJoinTable.getSpecifiedSchema());
		assertEquals(1, virtualJoinTable.specifiedJoinColumnsSize());
		assertEquals(1, virtualJoinTable.specifiedInverseJoinColumnsSize());
		ormJoinColumn = virtualJoinTable.specifiedJoinColumns().next();
		assertEquals("NAME", ormJoinColumn.getSpecifiedName());
		assertEquals("REFERENCED_NAME", ormJoinColumn.getSpecifiedReferencedColumnName());
		inverseOrmJoinColumn = virtualJoinTable.specifiedInverseJoinColumns().next();
		assertEquals("INVERSE_NAME", inverseOrmJoinColumn.getSpecifiedName());
		assertEquals("INVERSE_REFERENCED_NAME", inverseOrmJoinColumn.getSpecifiedReferencedColumnName());
	}
	
	public void testUpdateDefaultNameFromJavaTable() throws Exception {
		createTestEntityWithValidManyToMany();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "projects");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		
		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		assertEquals(null, ormJoinTable.getDefaultName());
		
		createTargetEntity();
		OrmPersistentType targetPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Project");
		assertEquals(TYPE_NAME + "_Project", ormJoinTable.getDefaultName());

		
		((JavaEntity) targetPersistentType.getJavaPersistentType().getMapping()).getTable().setSpecifiedName("FOO");
		assertEquals(TYPE_NAME + "_FOO", ormJoinTable.getDefaultName());
		
		((JavaEntity) ormPersistentType.getJavaPersistentType().getMapping()).getTable().setSpecifiedName("BAR");
		assertEquals("BAR_FOO", ormJoinTable.getDefaultName());
		
		ormPersistentType.getJavaPersistentType().getAttributeNamed("projects").setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		JavaManyToManyMapping javaManyMapping = (JavaManyToManyMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("projects").getMapping();
		javaManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("JAVA_JOIN_TABLE");
		
		assertEquals("BAR_FOO", ormJoinTable.getDefaultName());

		
		//set metadata-complete to true, will ignore java annotation settings
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		//ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(TYPE_NAME + "_Project", ormJoinTable.getDefaultName());
		
		
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		//remove m-m mapping from the orm.xml file
		ormPersistentAttribute.convertToVirtual();
		//ormPersistentType.getMapping().setSpecifiedMetadataComplete(null);
		OrmReadOnlyPersistentAttribute ormPersistentAttribute2 = ormPersistentType.getAttributeNamed("projects");
		ManyToManyMapping virtualManyToManyMapping = (ManyToManyMapping) ormPersistentAttribute2.getMapping();
		JoinTable virtualJoinTable = virtualManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		assertTrue(ormPersistentAttribute2.isVirtual());
		assertEquals("JAVA_JOIN_TABLE", virtualJoinTable.getSpecifiedName());//specifiedName since this is a virtual mapping now
		
		javaManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName(null);
		javaManyMapping.getRelationship().setStrategyToJoinTable();
		assertNull(virtualJoinTable.getSpecifiedName());
		assertEquals("BAR_FOO", virtualJoinTable.getDefaultName());
		
		((OrmEntity) ormPersistentType.getMapping()).getTable().setSpecifiedName("ORM_TABLE_NAME");
		assertEquals("ORM_TABLE_NAME_FOO", virtualJoinTable.getDefaultName());
		
		((OrmEntity) targetPersistentType.getMapping()).getTable().setSpecifiedName("ORM_TARGET");
		assertEquals("ORM_TABLE_NAME_ORM_TARGET", virtualJoinTable.getDefaultName());
	}

	public void testUpdateSpecifiedSchema() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		assertNull(ormJoinTable.getSpecifiedSchema());
		assertNull(manyToMany.getJoinTable());
		
		//set schema in the resource model, verify context model updated
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		manyToMany.getJoinTable().setSchema("FOO");
		assertEquals("FOO", ormJoinTable.getSpecifiedSchema());
		assertEquals("FOO", manyToMany.getJoinTable().getSchema());
	
		//set Schema to null in the resource model
		manyToMany.getJoinTable().setSchema(null);
		assertNull(ormJoinTable.getSpecifiedSchema());
		assertNull(manyToMany.getJoinTable().getSchema());
		
		manyToMany.getJoinTable().setSchema("FOO");
		assertEquals("FOO", ormJoinTable.getSpecifiedSchema());
		assertEquals("FOO", manyToMany.getJoinTable().getSchema());

		manyToMany.setJoinTable(null);
		assertNull(ormJoinTable.getSpecifiedSchema());
		assertNull(manyToMany.getJoinTable());
	}
	
//	public void testUpdateDefaultSchemaFromJavaTable() throws Exception {
//		createTestEntity();
//		
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlEntity xmlEntity = (XmlEntity) ormPersistentType.getMapping();
//		assertNull(xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.javaEntity().getTable().setSpecifiedSchema("Foo");
//		assertEquals("Foo", xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
//		assertNull(xmlEntity.getTable().getDefaultSchema());
//
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
//		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
//		assertNull(xmlEntity.getTable().getDefaultSchema());
//	
//		xmlEntity.setSpecifiedMetadataComplete(null);
//		assertNull(xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
//		assertEquals("Foo", xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.getTable().setSpecifiedName("Bar");
//		assertNull(xmlEntity.getTable().getDefaultSchema());
//	}
//	
//	public void testUpdateDefaultSchemaNoJava() throws Exception {
//		createTestEntity();
//		
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlEntity xmlEntity = (XmlEntity) ormPersistentType.getMapping();
//		assertNull(xmlEntity.getTable().getDefaultSchema());
//	}
//	
//	public void testUpdateDefaultSchemaFromParent() throws Exception {
//		createTestEntity();
//		createTestSubType();
//		
//		OrmPersistentType parentOrmPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		OrmPersistentType childOrmPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
//		XmlEntity parentXmlEntity = (XmlEntity) parentOrmPersistentType.getMapping();
//		XmlEntity childXmlEntity = (XmlEntity) childOrmPersistentType.getMapping();
//		
//		assertNull(parentXmlEntity.getTable().getDefaultSchema());
//		assertNull(childXmlEntity.getTable().getDefaultSchema());
//		
//		parentXmlEntity.getTable().setSpecifiedSchema("FOO");
//		assertNull(parentXmlEntity.getTable().getDefaultSchema());
//		assertEquals("FOO", childXmlEntity.getTable().getDefaultSchema());
//
//		parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
//		assertNull(parentXmlEntity.getTable().getDefaultSchema());
//		assertNull(childXmlEntity.getTable().getDefaultSchema());
//	}
//	
//	public void testUpdateDefaultSchemaFromPersistenceUnitDefaults() throws Exception {
//		createTestEntity();
//		
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlEntity xmlEntity = (XmlEntity) ormPersistentType.getMapping();
//		assertNull(xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema("FOO");
//		assertEquals("FOO", xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.entityMappings().setSpecifiedSchema("BAR");
//		assertEquals("BAR", xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.javaEntity().getTable().setSpecifiedSchema("JAVA_SCHEMA");
//		assertEquals("JAVA_SCHEMA", xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.getTable().setSpecifiedName("BLAH");
//		//xml entity now has a table element so default schema is not taken from java
//		assertEquals("BAR", xmlEntity.getTable().getDefaultSchema());
//
//		
//		xmlEntity.entityMappings().setSpecifiedSchema(null);
//		assertEquals("FOO", xmlEntity.getTable().getDefaultSchema());
//
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema(null);
//		assertNull(xmlEntity.getTable().getDefaultSchema());
//		
//		xmlEntity.getTable().setSpecifiedName(null);
//		assertEquals("JAVA_SCHEMA", xmlEntity.getTable().getDefaultSchema());
//	}

	public void testModifySpecifiedSchema() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		assertNull(ormJoinTable.getSpecifiedSchema());
		assertNull(manyToMany.getJoinTable());
		
		//set Schema in the context model, verify resource model modified
		ormJoinTable.setSpecifiedSchema("foo");
		assertEquals("foo", ormJoinTable.getSpecifiedSchema());
		assertEquals("foo", manyToMany.getJoinTable().getSchema());
		
		//set Schema to null in the context model
		ormJoinTable.setSpecifiedSchema(null);
		assertNull(ormJoinTable.getSpecifiedSchema());
		assertNull(manyToMany.getJoinTable());
	}
	
	public void testUpdateSpecifiedCatalog() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		assertNull(ormJoinTable.getSpecifiedCatalog());
		assertNull(manyToMany.getJoinTable());
		
		//set Catalog in the resource model, verify context model updated
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		manyToMany.getJoinTable().setCatalog("FOO");
		assertEquals("FOO", ormJoinTable.getSpecifiedCatalog());
		assertEquals("FOO", manyToMany.getJoinTable().getCatalog());
	
		//set Catalog to null in the resource model
		manyToMany.getJoinTable().setCatalog(null);
		assertNull(ormJoinTable.getSpecifiedCatalog());
		assertNull(manyToMany.getJoinTable().getCatalog());
		
		manyToMany.getJoinTable().setCatalog("FOO");
		assertEquals("FOO", ormJoinTable.getSpecifiedCatalog());
		assertEquals("FOO", manyToMany.getJoinTable().getCatalog());

		manyToMany.setJoinTable(null);
		assertNull(ormJoinTable.getSpecifiedCatalog());
		assertNull(manyToMany.getJoinTable());
	}
	
	public void testModifySpecifiedCatalog() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		assertNull(ormJoinTable.getSpecifiedCatalog());
		assertNull(manyToMany.getJoinTable());
		
		//set Catalog in the context model, verify resource model modified
		ormJoinTable.setSpecifiedCatalog("foo");
		assertEquals("foo", ormJoinTable.getSpecifiedCatalog());
		assertEquals("foo", manyToMany.getJoinTable().getCatalog());
		
		//set Catalog to null in the context model
		ormJoinTable.setSpecifiedCatalog(null);
		assertNull(ormJoinTable.getSpecifiedCatalog());
		assertNull(manyToMany.getJoinTable());
	}
	
//	public void testUpdateDefaultCatalogFromJavaTable() throws Exception {
//		createTestEntity();
//		
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlEntity xmlEntity = (XmlEntity) ormPersistentType.getMapping();
//		assertNull(xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.javaEntity().getTable().setSpecifiedCatalog("Foo");
//		assertEquals("Foo", xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.setSpecifiedMetadataComplete(Boolean.TRUE);
//		assertNull(xmlEntity.getTable().getDefaultCatalog());
//
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
//		xmlEntity.setSpecifiedMetadataComplete(Boolean.FALSE);
//		assertNull(xmlEntity.getTable().getDefaultCatalog());
//	
//		xmlEntity.setSpecifiedMetadataComplete(null);
//		assertNull(xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
//		assertEquals("Foo", xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.getTable().setSpecifiedName("Bar");
//		assertNull(xmlEntity.getTable().getDefaultCatalog());
//	}
//	
//	public void testUpdateDefaultCatalogNoJava() throws Exception {
//		createTestEntity();
//		
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlEntity xmlEntity = (XmlEntity) ormPersistentType.getMapping();
//		assertNull(xmlEntity.getTable().getDefaultCatalog());
//	}
//	
//	public void testUpdateDefaultCatalogFromParent() throws Exception {
//		createTestEntity();
//		createTestSubType();
//		
//		OrmPersistentType parentOrmPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		OrmPersistentType childOrmPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
//		XmlEntity parentXmlEntity = (XmlEntity) parentOrmPersistentType.getMapping();
//		XmlEntity childXmlEntity = (XmlEntity) childOrmPersistentType.getMapping();
//		
//		assertNull(parentXmlEntity.getTable().getDefaultCatalog());
//		assertNull(childXmlEntity.getTable().getDefaultCatalog());
//		
//		parentXmlEntity.getTable().setSpecifiedCatalog("FOO");
//		assertNull(parentXmlEntity.getTable().getDefaultCatalog());
//		assertEquals("FOO", childXmlEntity.getTable().getDefaultCatalog());
//
//		parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
//		assertNull(parentXmlEntity.getTable().getDefaultCatalog());
//		assertNull(childXmlEntity.getTable().getDefaultCatalog());
//	}
//	
//	public void testUpdateDefaultCatalogFromPersistenceUnitDefaults() throws Exception {
//		createTestEntity();
//		
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		XmlEntity xmlEntity = (XmlEntity) ormPersistentType.getMapping();
//		assertNull(xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog("FOO");
//		assertEquals("FOO", xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.entityMappings().setSpecifiedCatalog("BAR");
//		assertEquals("BAR", xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.javaEntity().getTable().setSpecifiedCatalog("JAVA_CATALOG");
//		assertEquals("JAVA_CATALOG", xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.getTable().setSpecifiedName("BLAH");
//		//xml entity now has a table element so default schema is not taken from java
//		assertEquals("BAR", xmlEntity.getTable().getDefaultCatalog());
//
//		
//		xmlEntity.entityMappings().setSpecifiedCatalog(null);
//		assertEquals("FOO", xmlEntity.getTable().getDefaultCatalog());
//
//		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog(null);
//		assertNull(xmlEntity.getTable().getDefaultCatalog());
//		
//		xmlEntity.getTable().setSpecifiedName(null);
//		assertEquals("JAVA_CATALOG", xmlEntity.getTable().getDefaultCatalog());
//}

//	
//	public void testUpdateName() throws Exception {
//		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//		XmlEntity xmlEntity = (XmlEntity) ormPersistentType.getMapping();
//		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
//		assertEquals("Foo", xmlEntity.getName());
//		
//		//set class in the resource model, verify context model updated
//		entityResource.setClassName("com.Bar");
//		assertEquals("Bar", xmlEntity.getName());
//		
//		entityResource.setName("Baz");
//		assertEquals("Baz", xmlEntity.getName());
//		
//		//set class to null in the resource model
//		entityResource.setClassName(null);
//		assertEquals("Baz", xmlEntity.getName());
//		
//		entityResource.setName(null);
//		assertNull(xmlEntity.getName());
//	}


	public void testAddSpecifiedJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		OrmJoinColumn joinColumn = ormJoinTable.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		assertEquals("FOO", joinTableResource.getJoinColumns().get(0).getName());
		
		OrmJoinColumn joinColumn2 = ormJoinTable.addSpecifiedJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", joinTableResource.getJoinColumns().get(0).getName());
		assertEquals("FOO", joinTableResource.getJoinColumns().get(1).getName());
		
		OrmJoinColumn joinColumn3 = ormJoinTable.addSpecifiedJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", joinTableResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", joinTableResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", joinTableResource.getJoinColumns().get(2).getName());
		
		ListIterator<OrmJoinColumn> joinColumns = ormJoinTable.specifiedJoinColumns();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = ormJoinTable.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		ormJoinTable.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		ormJoinTable.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		ormJoinTable.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		assertEquals(3, joinTableResource.getJoinColumns().size());
		
		ormJoinTable.removeSpecifiedJoinColumn(0);
		assertEquals(2, joinTableResource.getJoinColumns().size());
		assertEquals("BAR", joinTableResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", joinTableResource.getJoinColumns().get(1).getName());

		ormJoinTable.removeSpecifiedJoinColumn(0);
		assertEquals(1, joinTableResource.getJoinColumns().size());
		assertEquals("BAZ", joinTableResource.getJoinColumns().get(0).getName());
		
		ormJoinTable.removeSpecifiedJoinColumn(0);
		assertEquals(0, joinTableResource.getJoinColumns().size());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		ormJoinTable.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		ormJoinTable.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		ormJoinTable.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		assertEquals(3, joinTableResource.getJoinColumns().size());
		
		
		ormJoinTable.moveSpecifiedJoinColumn(2, 0);
		ListIterator<OrmJoinColumn> joinColumns = ormJoinTable.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", joinTableResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", joinTableResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", joinTableResource.getJoinColumns().get(2).getName());


		ormJoinTable.moveSpecifiedJoinColumn(0, 1);
		joinColumns = ormJoinTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", joinTableResource.getJoinColumns().get(0).getName());
		assertEquals("BAR", joinTableResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", joinTableResource.getJoinColumns().get(2).getName());
	}
	
	public void testUpdateInverseJoinColumns() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
	
		joinTableResource.getInverseJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		joinTableResource.getInverseJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		joinTableResource.getInverseJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		
		joinTableResource.getInverseJoinColumns().get(0).setName("FOO");
		joinTableResource.getInverseJoinColumns().get(1).setName("BAR");
		joinTableResource.getInverseJoinColumns().get(2).setName("BAZ");

		ListIterator<OrmJoinColumn> joinColumns = ormJoinTable.specifiedInverseJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.getInverseJoinColumns().move(2, 0);
		joinColumns = ormJoinTable.specifiedInverseJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		joinTableResource.getInverseJoinColumns().move(0, 1);
		joinColumns = ormJoinTable.specifiedInverseJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		joinTableResource.getInverseJoinColumns().remove(1);
		joinColumns = ormJoinTable.specifiedInverseJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		joinTableResource.getInverseJoinColumns().remove(1);
		joinColumns = ormJoinTable.specifiedInverseJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.getInverseJoinColumns().remove(0);
		assertFalse(ormJoinTable.specifiedInverseJoinColumns().hasNext());
	}

	public void testAddSpecifiedInverseJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		OrmJoinColumn joinColumn = ormJoinTable.addSpecifiedInverseJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		assertEquals("FOO", joinTableResource.getInverseJoinColumns().get(0).getName());
		
		OrmJoinColumn joinColumn2 = ormJoinTable.addSpecifiedInverseJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", joinTableResource.getInverseJoinColumns().get(0).getName());
		assertEquals("FOO", joinTableResource.getInverseJoinColumns().get(1).getName());
		
		OrmJoinColumn joinColumn3 = ormJoinTable.addSpecifiedInverseJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", joinTableResource.getInverseJoinColumns().get(0).getName());
		assertEquals("BAZ", joinTableResource.getInverseJoinColumns().get(1).getName());
		assertEquals("FOO", joinTableResource.getInverseJoinColumns().get(2).getName());
		
		ListIterator<OrmJoinColumn> joinColumns = ormJoinTable.specifiedInverseJoinColumns();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = ormJoinTable.specifiedInverseJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedInverseJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		ormJoinTable.addSpecifiedInverseJoinColumn(0).setSpecifiedName("FOO");
		ormJoinTable.addSpecifiedInverseJoinColumn(1).setSpecifiedName("BAR");
		ormJoinTable.addSpecifiedInverseJoinColumn(2).setSpecifiedName("BAZ");
		
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		assertEquals(3, joinTableResource.getInverseJoinColumns().size());
		
		ormJoinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(2, joinTableResource.getInverseJoinColumns().size());
		assertEquals("BAR", joinTableResource.getInverseJoinColumns().get(0).getName());
		assertEquals("BAZ", joinTableResource.getInverseJoinColumns().get(1).getName());

		ormJoinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(1, joinTableResource.getInverseJoinColumns().size());
		assertEquals("BAZ", joinTableResource.getInverseJoinColumns().get(0).getName());
		
		ormJoinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(0, joinTableResource.getInverseJoinColumns().size());
	}
	
	public void testMoveSpecifiedInverseJoinColumn() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		ormJoinTable.addSpecifiedInverseJoinColumn(0).setSpecifiedName("FOO");
		ormJoinTable.addSpecifiedInverseJoinColumn(1).setSpecifiedName("BAR");
		ormJoinTable.addSpecifiedInverseJoinColumn(2).setSpecifiedName("BAZ");
		
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		assertEquals(3, joinTableResource.getInverseJoinColumns().size());
		
		
		ormJoinTable.moveSpecifiedInverseJoinColumn(2, 0);
		ListIterator<OrmJoinColumn> joinColumns = ormJoinTable.specifiedInverseJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", joinTableResource.getInverseJoinColumns().get(0).getName());
		assertEquals("BAZ", joinTableResource.getInverseJoinColumns().get(1).getName());
		assertEquals("FOO", joinTableResource.getInverseJoinColumns().get(2).getName());


		ormJoinTable.moveSpecifiedInverseJoinColumn(0, 1);
		joinColumns = ormJoinTable.specifiedInverseJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", joinTableResource.getInverseJoinColumns().get(0).getName());
		assertEquals("BAR", joinTableResource.getInverseJoinColumns().get(1).getName());
		assertEquals("FOO", joinTableResource.getInverseJoinColumns().get(2).getName());
	}
	
	public void testUpdateJoinColumns() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
	
		joinTableResource.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		joinTableResource.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		joinTableResource.getJoinColumns().add(OrmFactory.eINSTANCE.createXmlJoinColumn());
		
		joinTableResource.getJoinColumns().get(0).setName("FOO");
		joinTableResource.getJoinColumns().get(1).setName("BAR");
		joinTableResource.getJoinColumns().get(2).setName("BAZ");

		ListIterator<OrmJoinColumn> joinColumns = ormJoinTable.specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.getJoinColumns().move(2, 0);
		joinColumns = ormJoinTable.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		joinTableResource.getJoinColumns().move(0, 1);
		joinColumns = ormJoinTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		joinTableResource.getJoinColumns().remove(1);
		joinColumns = ormJoinTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		joinTableResource.getJoinColumns().remove(1);
		joinColumns = ormJoinTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.getJoinColumns().remove(0);
		assertFalse(ormJoinTable.specifiedJoinColumns().hasNext());
	}
	

	public void testUniqueConstraints() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		
		ListIterator<OrmUniqueConstraint> uniqueConstraints = ormJoinTable.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
		
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		joinTableResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "foo");
		
		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		joinTableResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "bar");
		
		uniqueConstraints = ormJoinTable.uniqueConstraints();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("bar", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("foo", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsSize() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		
		assertEquals(0,  ormJoinTable.uniqueConstraintsSize());
		
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		joinTableResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "foo");
		
		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		joinTableResource.getUniqueConstraints().add(1, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "bar");
		
		assertEquals(2,  ormJoinTable.uniqueConstraintsSize());
	}

	public void testAddUniqueConstraint() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		
		ormJoinTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		ormJoinTable.addUniqueConstraint(0).addColumnName(0, "BAR");
		ormJoinTable.addUniqueConstraint(0).addColumnName(0, "BAZ");

		ListIterator<XmlUniqueConstraint> uniqueConstraints = joinTableResource.getUniqueConstraints().listIterator();
		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().get(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testAddUniqueConstraint2() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();

		ormJoinTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		ormJoinTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		ormJoinTable.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		ListIterator<XmlUniqueConstraint> uniqueConstraints = joinTableResource.getUniqueConstraints().listIterator();
		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().get(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		
		ormJoinTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		ormJoinTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		ormJoinTable.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		assertEquals(3, joinTableResource.getUniqueConstraints().size());

		ormJoinTable.removeUniqueConstraint(1);
		
		ListIterator<XmlUniqueConstraint> uniqueConstraintResources = joinTableResource.getUniqueConstraints().listIterator();
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));		
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertFalse(uniqueConstraintResources.hasNext());
		
		Iterator<OrmUniqueConstraint> uniqueConstraints = ormJoinTable.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		
		ormJoinTable.removeUniqueConstraint(1);
		uniqueConstraintResources = joinTableResource.getUniqueConstraints().listIterator();
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));		
		assertFalse(uniqueConstraintResources.hasNext());

		uniqueConstraints = ormJoinTable.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());		
		assertFalse(uniqueConstraints.hasNext());

		
		ormJoinTable.removeUniqueConstraint(0);
		uniqueConstraintResources = joinTableResource.getUniqueConstraints().listIterator();
		assertFalse(uniqueConstraintResources.hasNext());
		uniqueConstraints = ormJoinTable.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
		
		ormJoinTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		ormJoinTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		ormJoinTable.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		assertEquals(3, joinTableResource.getUniqueConstraints().size());
		
		
		ormJoinTable.moveUniqueConstraint(2, 0);
		ListIterator<OrmUniqueConstraint> uniqueConstraints = ormJoinTable.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());

		ListIterator<XmlUniqueConstraint> uniqueConstraintResources = joinTableResource.getUniqueConstraints().listIterator();
		assertEquals("BAR", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));


		ormJoinTable.moveUniqueConstraint(0, 1);
		uniqueConstraints = ormJoinTable.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());

		uniqueConstraintResources = joinTableResource.getUniqueConstraints().listIterator();
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("BAR", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));
	}
	
	public void testUpdateUniqueConstraints() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);

		OrmJoinTable ormJoinTable = ormManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		XmlJoinTable joinTableResource = manyToMany.getJoinTable();
	
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		joinTableResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "FOO");

		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		joinTableResource.getUniqueConstraints().add(1, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "BAR");

		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		joinTableResource.getUniqueConstraints().add(2, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "BAZ");

		
		ListIterator<OrmUniqueConstraint> uniqueConstraints = ormJoinTable.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		joinTableResource.getUniqueConstraints().move(2, 0);
		uniqueConstraints = ormJoinTable.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		joinTableResource.getUniqueConstraints().move(0, 1);
		uniqueConstraints = ormJoinTable.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		joinTableResource.getUniqueConstraints().remove(1);
		uniqueConstraints = ormJoinTable.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		joinTableResource.getUniqueConstraints().remove(1);
		uniqueConstraints = ormJoinTable.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		joinTableResource.getUniqueConstraints().remove(0);
		uniqueConstraints = ormJoinTable.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
	}
	

	public void testUniqueConstraintsFromJava() throws Exception {
		createTestEntityWithValidManyToMany();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ReadOnlyPersistentAttribute virtualAttribute = ormPersistentType.attributes().next();
		ManyToManyMapping virtualManyToManyMapping = (ManyToManyMapping) virtualAttribute.getMapping();
		JoinTable virtualJoinTable = virtualManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		assertTrue(virtualAttribute.isVirtual());
		
		ListIterator<UniqueConstraint> uniqueConstraints = (ListIterator<UniqueConstraint>) virtualJoinTable.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());

		JavaManyToManyMapping javaManyToManyMapping = (JavaManyToManyMapping) ormPersistentType.getJavaPersistentType().attributes().next().getMapping();
		JavaJoinTable javaJoinTable = javaManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		javaJoinTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		javaJoinTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		javaJoinTable.addUniqueConstraint(2).addColumnName(0, "BAZ");

		uniqueConstraints = (ListIterator<UniqueConstraint>) virtualJoinTable.uniqueConstraints();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		OrmManyToManyMapping specifiedManyToManyMapping = (OrmManyToManyMapping) ((VirtualOrmPersistentAttribute) virtualAttribute).convertToSpecified().getMapping();
		assertEquals(0,  specifiedManyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().uniqueConstraintsSize());
	}
	
	public void testDefaultName() throws Exception {
		createTestEntityWithValidManyToMany();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ormPersistentType.getAttributeNamed("projects").convertToSpecified();
		OrmManyToManyMapping manyToManyMapping = (OrmManyToManyMapping) ormPersistentType.getAttributeNamed("projects").getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		
		//joinTable default name is null because targetEntity is not in the persistence unit
		assertNull(joinTable.getDefaultName());

		//add target entity to the persistence unit, now join table name is [table name]_[target table name]
		createTargetEntity();
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Project");
		assertEquals(TYPE_NAME + "_Project", joinTable.getDefaultName());
		
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		assertNull(manyToMany.getJoinTable());
	
		//target entity does not resolve, default name is null
		manyToManyMapping.setSpecifiedTargetEntity("Foo");
		assertNull(joinTable.getDefaultName());

		//default target entity does resolve, so default name is again [table name]_[target table name]
		manyToManyMapping.setSpecifiedTargetEntity(null);
		assertEquals(TYPE_NAME + "_Project", joinTable.getDefaultName());

		//add the join table xml element, verify default join table name is the same
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		assertEquals(TYPE_NAME + "_Project", joinTable.getDefaultName());
		assertNotNull(manyToMany.getJoinTable());
		
		//set a table on the target entity, very default join table name updates
		manyToManyMapping.getResolvedTargetEntity().getTable().setSpecifiedName("FOO");
		assertEquals(TYPE_NAME + "_FOO", joinTable.getDefaultName());
		
		//set a table on the owning entity, very default join table name updates
		((OrmEntity) ormPersistentType.getMapping()).getTable().setSpecifiedName("BAR");
		assertEquals("BAR_FOO", joinTable.getDefaultName());
	}

	public void testDefaultJoinColumns() throws Exception {
		createTestEntityWithValidManyToMany();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ormPersistentType.getAttributeNamed("projects").convertToSpecified();
		OrmManyToManyMapping manyToManyMapping = (OrmManyToManyMapping) ormPersistentType.getAttributeNamed("projects").getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		JoinColumn joinColumn = joinTable.joinColumns().next();
		JoinColumn inverseJoinColumn = joinTable.inverseJoinColumns().next();
		
		//joinTable default name is null because targetEntity is not in the persistence unit
		assertNull(joinColumn.getDefaultName());
		assertNull(joinColumn.getDefaultReferencedColumnName());
		assertNull(inverseJoinColumn.getDefaultName());
		assertNull(inverseJoinColumn.getDefaultReferencedColumnName());

		//add target entity to the persistence unit, join column default name and referenced column still null because owning entity has no primary key
		createTargetEntity();
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Project");
		assertNull(joinColumn.getDefaultName());
		assertNull(joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());
		
		//create primary key  in owning entity
		ormPersistentType.getJavaPersistentType().getAttributeNamed("id").setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(TYPE_NAME + "_id", joinColumn.getDefaultName());
		assertEquals("id", joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());

		//set specified column name on primary key in owning entity
		((IdMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName("MY_ID");
		assertEquals(TYPE_NAME + "_MY_ID", joinColumn.getDefaultName());
		assertEquals("MY_ID", joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());
		
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		assertNull(manyToMany.getJoinTable());
	
		//target entity does not resolve, inverse join column name and referenced column name is null
		manyToManyMapping.setSpecifiedTargetEntity("Foo");
		assertEquals(TYPE_NAME + "_MY_ID", joinColumn.getDefaultName());
		assertEquals("MY_ID", joinColumn.getDefaultReferencedColumnName());
		assertNull(inverseJoinColumn.getDefaultName());
		assertNull(inverseJoinColumn.getDefaultReferencedColumnName());

		//default target entity does resolve, so defaults for join column are back
		manyToManyMapping.setSpecifiedTargetEntity(null);
		assertEquals(TYPE_NAME + "_MY_ID", joinColumn.getDefaultName());
		assertEquals("MY_ID", joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());

		//add the join table xml element, verify default join column defaults are the same
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		assertEquals(TYPE_NAME + "_MY_ID", joinColumn.getDefaultName());
		assertEquals("MY_ID", joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());
		assertNotNull(manyToMany.getJoinTable());
	}

	public void testDefaultJoinColumnsBidirectionalRelationship() throws Exception {
		createTestEntityWithValidManyToMany();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ormPersistentType.getAttributeNamed("projects").convertToSpecified();
		OrmManyToManyMapping manyToManyMapping = (OrmManyToManyMapping) ormPersistentType.getAttributeNamed("projects").getMapping();
		JoinTable joinTable = manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable();
		JoinColumn joinColumn = joinTable.joinColumns().next();
		JoinColumn inverseJoinColumn = joinTable.inverseJoinColumns().next();
		
		//joinTable default name is null because targetEntity is not in the persistence unit
		assertNull(joinColumn.getDefaultName());
		assertNull(joinColumn.getDefaultReferencedColumnName());
		assertNull(inverseJoinColumn.getDefaultName());
		assertNull(inverseJoinColumn.getDefaultReferencedColumnName());

		//add target entity to the persistence unit, join column default name and referenced column still null because owning entity has no primary key
		createTargetEntityWithBackPointer();
		getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Project");
		assertNull(joinColumn.getDefaultName());
		assertNull(joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());
		
		//create primary key  in owning entity
		ormPersistentType.getJavaPersistentType().getAttributeNamed("id").setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals("employees_id", joinColumn.getDefaultName());
		assertEquals("id", joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());

		//set specified column name on primary key in owning entity
		((IdMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping()).getColumn().setSpecifiedName("MY_ID");
		assertEquals("employees_MY_ID", joinColumn.getDefaultName());
		assertEquals("MY_ID", joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());
		
		XmlManyToMany manyToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		assertNull(manyToMany.getJoinTable());
	
		//target entity does not resolve, inverse join column name and referenced column name is null
		manyToManyMapping.setSpecifiedTargetEntity("Foo");
		assertEquals(TYPE_NAME + "_MY_ID", joinColumn.getDefaultName());
		assertEquals("MY_ID", joinColumn.getDefaultReferencedColumnName());
		assertNull(inverseJoinColumn.getDefaultName());
		assertNull(inverseJoinColumn.getDefaultReferencedColumnName());

		//default target entity does resolve, so defaults for join column are back
		manyToManyMapping.setSpecifiedTargetEntity(null);
		assertEquals("employees_MY_ID", joinColumn.getDefaultName());
		assertEquals("MY_ID", joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());

		//add the join table xml element, verify default join column defaults are the same
		manyToMany.setJoinTable(OrmFactory.eINSTANCE.createXmlJoinTable());
		assertEquals("employees_MY_ID", joinColumn.getDefaultName());
		assertEquals("MY_ID", joinColumn.getDefaultReferencedColumnName());
		assertEquals("projects_proj_id", inverseJoinColumn.getDefaultName());
		assertEquals("proj_id", inverseJoinColumn.getDefaultReferencedColumnName());
		assertNotNull(manyToMany.getJoinTable());
	}

}
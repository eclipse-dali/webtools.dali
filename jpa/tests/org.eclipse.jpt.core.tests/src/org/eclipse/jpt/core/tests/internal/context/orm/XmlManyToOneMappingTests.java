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
import java.util.ListIterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.orm.XmlCascade;
import org.eclipse.jpt.core.internal.context.orm.XmlJoinColumn;
import org.eclipse.jpt.core.internal.context.orm.XmlManyToOneMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.orm.ManyToOne;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class XmlManyToOneMappingTests extends ContextModelTestCase
{
	public XmlManyToOneMappingTests(String name) {
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
	
	private void createManyToOneAnnotation() throws Exception{
		this.createAnnotationAndMembers("ManyToOne", 
			"Class targetEntity() default void.class;" +
			"CascadeType[] cascade() default {};" +
			"FetchType fetch() default EAGER;" +
			"boolean optional() default true;");		
	}
	
	private void createJoinColumnAnnotation() throws Exception{
		this.createAnnotationAndMembers("JoinColumn", 
			"String name() default \"\";" +
			"String referencedColumnName() default \"\";" +
			"boolean unique() default false;" +
			"boolean nullable() default true;" +
			"boolean insertable() default true;" +
			"boolean updatable() default true;" +
			"String columnDefinition() default \"\";" +
			"String table() default \"\";");		
	}
	
	private IType createTestEntityManyToOneMapping() throws Exception {
		createEntityAnnotation();
		createManyToOneAnnotation();
		createJoinColumnAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE, JPA.JOIN_COLUMN, JPA.FETCH_TYPE, JPA.CASCADE_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToOne(fetch=FetchType.LAZY, optional=false, targetEntity=Address.class, cascade={CascadeType.ALL, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})");
				sb.append(CR);
				sb.append("    @JoinColumn(name=\"MY_COLUMN\", referencedColumnName=\"MY_REFERENCED_COLUMN\", unique=true, nullable=false, insertable=false, updatable=false, columnDefinition=\"COLUMN_DEFINITION\", table=\"MY_TABLE\")");
				sb.append(CR);
				sb.append("    private Address address;").append(CR);
				sb.append(CR);
				sb.append("    @Id");				
			}
		});
	}	
	
	private IType createTestTargetEntityAddress() throws Exception {
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
				sb.append("public class ").append("Address").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int id;").append(CR);
				sb.append(CR);
				sb.append("    private String city;").append(CR);
				sb.append(CR);
				sb.append("    private String state;").append(CR);
				sb.append(CR);
				sb.append("    private int zip;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		return this.javaProject.createType(PACKAGE_NAME, "Address.java", sourceWriter);
	}	
	
	public void testUpdateName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOneMapping");
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		ManyToOne manyToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertEquals("manyToOneMapping", xmlManyToOneMapping.getName());
		assertEquals("manyToOneMapping", manyToOne.getName());
				
		//set name in the resource model, verify context model updated
		manyToOne.setName("newName");
		assertEquals("newName", xmlManyToOneMapping.getName());
		assertEquals("newName", manyToOne.getName());
	
		//set name to null in the resource model
		manyToOne.setName(null);
		assertNull(xmlManyToOneMapping.getName());
		assertNull(manyToOne.getName());
	}
	
	public void testModifyName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOneMapping");
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		ManyToOne manyToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertEquals("manyToOneMapping", xmlManyToOneMapping.getName());
		assertEquals("manyToOneMapping", manyToOne.getName());
				
		//set name in the context model, verify resource model updated
		xmlManyToOneMapping.setName("newName");
		assertEquals("newName", xmlManyToOneMapping.getName());
		assertEquals("newName", manyToOne.getName());
	
		//set name to null in the context model
		xmlManyToOneMapping.setName(null);
		assertNull(xmlManyToOneMapping.getName());
		assertNull(manyToOne.getName());
	}
	
	public void testUpdateTargetEntity() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOneMapping");
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		ManyToOne manyToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertNull(xmlManyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
				
		//set target entity in the resource model, verify context model updated
		manyToOne.setTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", xmlManyToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", manyToOne.getTargetEntity());
	
		//set target entity to null in the resource model
		manyToOne.setTargetEntity(null);
		assertNull(xmlManyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
	}
	
	public void testModifyTargetEntity() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOneMapping");
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		ManyToOne manyToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertNull(xmlManyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
				
		//set target entity in the context model, verify resource model updated
		xmlManyToOneMapping.setSpecifiedTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", xmlManyToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", manyToOne.getTargetEntity());
	
		//set target entity to null in the context model
		xmlManyToOneMapping.setSpecifiedTargetEntity(null);
		assertNull(xmlManyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOneMapping");
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		ManyToOne manyToOneResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertNull(xmlManyToOneMapping.getSpecifiedFetch());
		assertNull(manyToOneResource.getFetch());
				
		//set fetch in the resource model, verify context model updated
		manyToOneResource.setFetch(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER);
		assertEquals(FetchType.EAGER, xmlManyToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER, manyToOneResource.getFetch());
	
		manyToOneResource.setFetch(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY);
		assertEquals(FetchType.LAZY, xmlManyToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY, manyToOneResource.getFetch());

		//set fetch to null in the resource model
		manyToOneResource.setFetch(null);
		assertNull(xmlManyToOneMapping.getSpecifiedFetch());
		assertNull(manyToOneResource.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOneMapping");
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		ManyToOne manyToOneResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertNull(xmlManyToOneMapping.getSpecifiedFetch());
		assertNull(manyToOneResource.getFetch());
				
		//set fetch in the context model, verify resource model updated
		xmlManyToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER, manyToOneResource.getFetch());
		assertEquals(FetchType.EAGER, xmlManyToOneMapping.getSpecifiedFetch());
	
		xmlManyToOneMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY, manyToOneResource.getFetch());
		assertEquals(FetchType.LAZY, xmlManyToOneMapping.getSpecifiedFetch());

		//set fetch to null in the context model
		xmlManyToOneMapping.setSpecifiedFetch(null);
		assertNull(manyToOneResource.getFetch());
		assertNull(xmlManyToOneMapping.getSpecifiedFetch());
	}
	
	public void testUpdateSpecifiedOptional() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOneMapping");
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		ManyToOne manyToOneResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertNull(xmlManyToOneMapping.getSpecifiedOptional());
		assertNull(manyToOneResource.getOptional());
				
		//set optional in the resource model, verify context model updated
		manyToOneResource.setOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, xmlManyToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.TRUE, manyToOneResource.getOptional());
	
		manyToOneResource.setOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlManyToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.FALSE, manyToOneResource.getOptional());

		//set optional to null in the resource model
		manyToOneResource.setOptional(null);
		assertNull(xmlManyToOneMapping.getSpecifiedOptional());
		assertNull(manyToOneResource.getOptional());
	}
	
	public void testModifySpecifiedOptional() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOneMapping");
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		ManyToOne manyToOneResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertNull(xmlManyToOneMapping.getSpecifiedOptional());
		assertNull(manyToOneResource.getOptional());
				
		//set optional in the context model, verify resource model updated
		xmlManyToOneMapping.setSpecifiedOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, manyToOneResource.getOptional());
		assertEquals(Boolean.TRUE, xmlManyToOneMapping.getSpecifiedOptional());
	
		xmlManyToOneMapping.setSpecifiedOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, manyToOneResource.getOptional());
		assertEquals(Boolean.FALSE, xmlManyToOneMapping.getSpecifiedOptional());

		//set optional to null in the context model
		xmlManyToOneMapping.setSpecifiedOptional(null);
		assertNull(manyToOneResource.getOptional());
		assertNull(xmlManyToOneMapping.getSpecifiedOptional());
	}
	
	public void testAddSpecifiedJoinColumn() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOneMapping");
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		ManyToOne manyToOneResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		XmlJoinColumn joinColumn = xmlManyToOneMapping.addSpecifiedJoinColumn(0);
		ormResource().save(null);
		joinColumn.setSpecifiedName("FOO");
		ormResource().save(null);
				
		assertEquals("FOO", manyToOneResource.getJoinColumns().get(0).getName());
		
		XmlJoinColumn joinColumn2 = xmlManyToOneMapping.addSpecifiedJoinColumn(0);
		ormResource().save(null);
		joinColumn2.setSpecifiedName("BAR");
		ormResource().save(null);
		
		assertEquals("BAR", manyToOneResource.getJoinColumns().get(0).getName());
		assertEquals("FOO", manyToOneResource.getJoinColumns().get(1).getName());
		
		XmlJoinColumn joinColumn3 = xmlManyToOneMapping.addSpecifiedJoinColumn(1);
		ormResource().save(null);
		joinColumn3.setSpecifiedName("BAZ");
		ormResource().save(null);
		
		assertEquals("BAR", manyToOneResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", manyToOneResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", manyToOneResource.getJoinColumns().get(2).getName());
		
		ListIterator<XmlJoinColumn> joinColumns = xmlManyToOneMapping.specifiedJoinColumns();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = xmlManyToOneMapping.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOneMapping");
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		ManyToOne manyToOneResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);

		xmlManyToOneMapping.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		xmlManyToOneMapping.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		xmlManyToOneMapping.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, manyToOneResource.getJoinColumns().size());
		
		xmlManyToOneMapping.removeSpecifiedJoinColumn(0);
		assertEquals(2, manyToOneResource.getJoinColumns().size());
		assertEquals("BAR", manyToOneResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", manyToOneResource.getJoinColumns().get(1).getName());

		xmlManyToOneMapping.removeSpecifiedJoinColumn(0);
		assertEquals(1, manyToOneResource.getJoinColumns().size());
		assertEquals("BAZ", manyToOneResource.getJoinColumns().get(0).getName());
		
		xmlManyToOneMapping.removeSpecifiedJoinColumn(0);
		assertEquals(0, manyToOneResource.getJoinColumns().size());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOneMapping");
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		ManyToOne manyToOneResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);

		xmlManyToOneMapping.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		xmlManyToOneMapping.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		xmlManyToOneMapping.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, manyToOneResource.getJoinColumns().size());
		
		
		xmlManyToOneMapping.moveSpecifiedJoinColumn(2, 0);
		ListIterator<XmlJoinColumn> joinColumns = xmlManyToOneMapping.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", manyToOneResource.getJoinColumns().get(0).getName());
		assertEquals("BAZ", manyToOneResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", manyToOneResource.getJoinColumns().get(2).getName());


		xmlManyToOneMapping.moveSpecifiedJoinColumn(0, 1);
		joinColumns = xmlManyToOneMapping.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", manyToOneResource.getJoinColumns().get(0).getName());
		assertEquals("BAR", manyToOneResource.getJoinColumns().get(1).getName());
		assertEquals("FOO", manyToOneResource.getJoinColumns().get(2).getName());
	}
	
	
	public void testManyToOneMappingNoUnderylingJavaAttribute() throws Exception {
		createTestEntityManyToOneMapping();
		createTestTargetEntityAddress();

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "foo");
		assertEquals(3, xmlPersistentType.virtualAttributesSize());
		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.specifiedAttributes().next();
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		
		assertEquals("foo", xmlManyToOneMapping.getName());

		assertNull(xmlManyToOneMapping.getSpecifiedFetch());
		assertNull(xmlManyToOneMapping.getSpecifiedOptional());
		assertNull(xmlManyToOneMapping.getSpecifiedTargetEntity());
		assertEquals(FetchType.EAGER, xmlManyToOneMapping.getFetch());
		assertEquals(Boolean.TRUE, xmlManyToOneMapping.getOptional());
		assertNull(xmlManyToOneMapping.getTargetEntity());

		
		assertFalse(xmlManyToOneMapping.specifiedJoinColumns().hasNext());
		//TODO default joinColumns
		//assertTrue(xmlManyToOneMapping.defaultJoinColumns().hasNext());
	
	
		XmlCascade xmlCascade = xmlManyToOneMapping.getCascade();
		assertFalse(xmlCascade.isAll());
		assertFalse(xmlCascade.isMerge());
		assertFalse(xmlCascade.isPersist());
		assertFalse(xmlCascade.isRemove());
		assertFalse(xmlCascade.isRefresh());
	}
	
	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityManyToOneMapping();
		createTestTargetEntityAddress();

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		assertEquals(3, xmlPersistentType.virtualAttributesSize());		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.virtualAttributes().next();
		
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();	
		assertEquals("address", xmlManyToOneMapping.getName());
		assertEquals(FetchType.LAZY, xmlManyToOneMapping.getSpecifiedFetch());
		assertEquals(Boolean.FALSE, xmlManyToOneMapping.getSpecifiedOptional());
		assertEquals("Address", xmlManyToOneMapping.getSpecifiedTargetEntity());

		XmlJoinColumn xmlJoinColumn = xmlManyToOneMapping.specifiedJoinColumns().next();
		assertEquals("MY_COLUMN", xmlJoinColumn.getSpecifiedName());
		assertEquals("MY_REFERENCED_COLUMN", xmlJoinColumn.getSpecifiedReferencedColumnName());
		assertEquals(Boolean.TRUE, xmlJoinColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, xmlJoinColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, xmlJoinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, xmlJoinColumn.getSpecifiedUpdatable());
		assertEquals("COLUMN_DEFINITION", xmlJoinColumn.getColumnDefinition());
		assertEquals("MY_TABLE", xmlJoinColumn.getSpecifiedTable());

		XmlCascade xmlCascade = xmlManyToOneMapping.getCascade();
		assertTrue(xmlCascade.isAll());
		assertTrue(xmlCascade.isMerge());
		assertTrue(xmlCascade.isPersist());
		assertTrue(xmlCascade.isRemove());
		assertTrue(xmlCascade.isRefresh());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityManyToOneMapping();
		createTestTargetEntityAddress();

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");
		xmlPersistentType.getMapping().setSpecifiedMetadataComplete(true);
		assertEquals(3, xmlPersistentType.virtualAttributesSize());		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.virtualAttributes().next();
		
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();	
		assertEquals("address", xmlManyToOneMapping.getName());
		assertEquals(FetchType.EAGER, xmlManyToOneMapping.getSpecifiedFetch());
		assertEquals(Boolean.TRUE, xmlManyToOneMapping.getSpecifiedOptional());
		//TODO hmm, is this correct?
		assertEquals("test.Address", xmlManyToOneMapping.getSpecifiedTargetEntity());
		
		XmlJoinColumn xmlJoinColumn = xmlManyToOneMapping.specifiedJoinColumns().next();
		//TODO java default columns name in JavaSingleRelationshipMapping.JoinColumnOwner
		//assertEquals("address", xmlJoinColumn.getSpecifiedName());
		//assertEquals("address", xmlJoinColumn.getSpecifiedReferencedColumnName());
		assertEquals(Boolean.FALSE, xmlJoinColumn.getSpecifiedUnique());
		assertEquals(Boolean.TRUE, xmlJoinColumn.getSpecifiedNullable());
		assertEquals(Boolean.TRUE, xmlJoinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.TRUE, xmlJoinColumn.getSpecifiedUpdatable());
		assertNull(xmlJoinColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, xmlJoinColumn.getSpecifiedTable());

		XmlCascade xmlCascade = xmlManyToOneMapping.getCascade();
		assertFalse(xmlCascade.isAll());
		assertFalse(xmlCascade.isMerge());
		assertFalse(xmlCascade.isPersist());
		assertFalse(xmlCascade.isRemove());
		assertFalse(xmlCascade.isRefresh());
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityManyToOneMapping();
		createTestTargetEntityAddress();

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".Address");

		xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "address");
		assertEquals(2, xmlPersistentType.virtualAttributesSize());
		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.specifiedAttributes().next();
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		
		assertEquals("address", xmlManyToOneMapping.getName());
		assertNull(xmlManyToOneMapping.getSpecifiedFetch());
		assertNull(xmlManyToOneMapping.getSpecifiedOptional());
		assertNull(xmlManyToOneMapping.getSpecifiedTargetEntity());
		assertEquals(FetchType.EAGER, xmlManyToOneMapping.getFetch());
		assertEquals(Boolean.TRUE, xmlManyToOneMapping.getOptional());
		//TODO default target entity in xml
		//assertEquals("test.Address", xmlManyToOneMapping.getDefaultTargetEntity());
		
		assertFalse(xmlManyToOneMapping.specifiedJoinColumns().hasNext());
		
		//TODO default join columns for specified xmlManyToOne mapping
//		XmlJoinColumn xmlJoinColumn = xmlManyToOneMapping.defaultJoinColumns().next();
//		assertNull(xmlJoinColumn.getSpecifiedName());
//		assertNull(xmlJoinColumn.getSpecifiedReferencedColumnName());
//		assertNull(xmlJoinColumn.getSpecifiedUnique());
//		assertNull(xmlJoinColumn.getSpecifiedNullable());
//		assertNull(xmlJoinColumn.getSpecifiedInsertable());
//		assertNull(xmlJoinColumn.getSpecifiedUpdatable());
//		assertNull(xmlJoinColumn.getColumnDefinition());
//		assertNull(xmlJoinColumn.getSpecifiedTable());
//		
//		assertEquals("address", xmlJoinColumn.getDefaultName());
//		assertEquals("address", xmlJoinColumn.getDefaultReferencedColumnName());
//		assertEquals(Boolean.FALSE, xmlJoinColumn.getDefaultUnique());
//		assertEquals(Boolean.TRUE, xmlJoinColumn.getDefaultNullable());
//		assertEquals(Boolean.TRUE, xmlJoinColumn.getDefaultInsertable());
//		assertEquals(Boolean.TRUE, xmlJoinColumn.getDefaultUpdatable());
//		assertEquals(null, xmlJoinColumn.getColumnDefinition());
//		assertEquals(TYPE_NAME, xmlJoinColumn.getDefaultTable());

		XmlCascade xmlCascade = xmlManyToOneMapping.getCascade();
		assertFalse(xmlCascade.isAll());
		assertFalse(xmlCascade.isMerge());
		assertFalse(xmlCascade.isPersist());
		assertFalse(xmlCascade.isRemove());
		assertFalse(xmlCascade.isRefresh());
	}
	
	//3 things tested above
	//1. virtual mapping metadata complete=false - defaults are taken from the java annotations
	//2. virtual mapping metadata complete=true - defaults are taken from java defaults,annotations ignored
	//3. specified mapping (metadata complete=true/false - defaults are taken from java annotations

}
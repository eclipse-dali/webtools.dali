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
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.IClassRef;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IManyToOneMapping;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaJoinColumn;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.java.Column;
import org.eclipse.jpt.core.internal.resource.java.GeneratedValue;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.JoinColumn;
import org.eclipse.jpt.core.internal.resource.java.JoinColumns;
import org.eclipse.jpt.core.internal.resource.java.ManyToOne;
import org.eclipse.jpt.core.internal.resource.java.SequenceGenerator;
import org.eclipse.jpt.core.internal.resource.java.TableGenerator;
import org.eclipse.jpt.core.internal.resource.java.Temporal;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaManyToOneMappingTests extends ContextModelTestCase
{

	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createManyToOneAnnotation() throws Exception{
		this.createAnnotationAndMembers("ManyToOne", "");		
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

	private IType createTestEntityWithManyToOneMapping() throws Exception {
		createEntityAnnotation();
		createManyToOneAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToOne").append(CR);
			}
		});
	}
	
	private IType createTestEntityWithValidManyToOneMapping() throws Exception {
		createEntityAnnotation();
		createManyToOneAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToOne").append(CR);				
				sb.append("    private Address address;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	private IType createTestEntityWithCollectionManyToOneMapping() throws Exception {
		createEntityAnnotation();
		createManyToOneAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE, JPA.ID, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToOne").append(CR);				
				sb.append("    private Collection address;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	private IType createTestEntityWithGenericizedCollectionManyToOneMapping() throws Exception {
		createEntityAnnotation();
		createManyToOneAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE, JPA.ID, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToOne").append(CR);				
				sb.append("    private Collection<Address> address;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}

	public JavaManyToOneMappingTests(String name) {
		super(name);
	}
	
	public void testMorphToBasic() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();
		assertFalse(manyToOneMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IBasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(ManyToOne.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(TableGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(SequenceGenerator.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(GeneratedValue.ANNOTATION_NAME));
	}
	
//	public void testMorphToDefault() throws Exception {
//		createTestEntityWithEmbeddedMapping();
//		createEmbeddableType();
//		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
//		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
//		
//		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
//		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) persistentAttribute.getMapping();
//		embeddedMapping.addSpecifiedAttributeOverride(0);
//		assertFalse(embeddedMapping.isDefault());
//		
//		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
//		assertTrue(((IEmbeddedMapping) persistentAttribute.getMapping()).attributeOverrides().hasNext());
//		assertTrue(persistentAttribute.getMapping().isDefault());
//	
//		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
//		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
//		assertNull(attributeResource.mappingAnnotation(Embedded.ANNOTATION_NAME));
//		assertNotNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
//	}
//	
//	public void testDefaultEmbedded() throws Exception {
//		createTestEntityWithEmbeddedMapping();
//		createEmbeddableType();
//		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
//		
//		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
//		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) persistentAttribute.getMapping();
//		assertFalse(embeddedMapping.isDefault());
//		
//		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
//		assertTrue(persistentAttribute.getMapping() instanceof JavaNullAttributeMapping);
//		assertTrue(persistentAttribute.getMapping().isDefault());
//		
//		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
//		assertTrue(persistentAttribute.getMapping() instanceof IEmbeddedMapping);
//		assertTrue(persistentAttribute.getMapping().isDefault());
//	}
//	
//	public void testMorphToVersion() throws Exception {
//		createTestEntityWithEmbeddedMapping();
//		createEmbeddableType();
//		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
//		
//		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
//		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) persistentAttribute.getMapping();
//		embeddedMapping.addSpecifiedAttributeOverride(0);
//		assertFalse(embeddedMapping.isDefault());
//		
//		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
//		assertTrue(persistentAttribute.getMapping() instanceof IVersionMapping);
//	
//		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
//		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
//		assertNull(attributeResource.mappingAnnotation(Embedded.ANNOTATION_NAME));
//		assertNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
//	}
//	
//	public void testMorphToTransient() throws Exception {
//		createTestEntityWithEmbeddedMapping();
//		createEmbeddableType();
//		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
//		
//		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
//		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) persistentAttribute.getMapping();
//		embeddedMapping.addSpecifiedAttributeOverride(0);
//		assertFalse(embeddedMapping.isDefault());
//		
//		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
//		assertTrue(persistentAttribute.getMapping() instanceof ITransientMapping);
//		
//		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
//		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
//		assertNull(attributeResource.mappingAnnotation(Embedded.ANNOTATION_NAME));
//		assertNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
//	}
//	
//	public void testMorphToId() throws Exception {
//		createTestEntityWithEmbeddedMapping();
//		createEmbeddableType();
//		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
//		
//		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
//		IEmbeddedMapping embeddedMapping = (IEmbeddedMapping) persistentAttribute.getMapping();
//		embeddedMapping.addSpecifiedAttributeOverride(0);
//		assertFalse(embeddedMapping.isDefault());
//		
//		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
//		assertTrue(persistentAttribute.getMapping() instanceof IIdMapping);
//		
//		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
//		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
//		assertNull(attributeResource.mappingAnnotation(Embedded.ANNOTATION_NAME));
//		assertNull(attributeResource.annotation(AttributeOverride.ANNOTATION_NAME));
//	}

	
	public void testUpdateSpecifiedTargetEntity() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		ManyToOne manyToOne = (ManyToOne) attributeResource.mappingAnnotation();
		
		assertNull(manyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
				
		//set target entity in the resource model, verify context model updated
		manyToOne.setTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", manyToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", manyToOne.getTargetEntity());
	
		//set target entity to null in the resource model
		manyToOne.setTargetEntity(null);
		assertNull(manyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
	}
	
	public void testModifySpecifiedTargetEntity() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		ManyToOne manyToOne = (ManyToOne) attributeResource.mappingAnnotation();
		
		assertNull(manyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
				
		//set target entity in the context model, verify resource model updated
		manyToOneMapping.setSpecifiedTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", manyToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", manyToOne.getTargetEntity());
	
		//set target entity to null in the context model
		manyToOneMapping.setSpecifiedTargetEntity(null);
		assertNull(manyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
	}
	
	public void testUpdateSpecifiedOptional() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		ManyToOne manyToOne = (ManyToOne) attributeResource.mappingAnnotation();
		
		assertNull(manyToOneMapping.getSpecifiedOptional());
		assertNull(manyToOne.getOptional());
				
		//set optional in the resource model, verify context model updated
		manyToOne.setOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, manyToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.TRUE, manyToOne.getOptional());
	
		manyToOne.setOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, manyToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.FALSE, manyToOne.getOptional());

		
		//set optional to null in the resource model
		manyToOne.setOptional(null);
		assertNull(manyToOneMapping.getSpecifiedOptional());
		assertNull(manyToOne.getOptional());
	}
	
	public void testModifySpecifiedOptional() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		ManyToOne manyToOne = (ManyToOne) attributeResource.mappingAnnotation();
		
		assertNull(manyToOneMapping.getSpecifiedOptional());
		assertNull(manyToOne.getOptional());
				
		//set optional in the context model, verify resource model updated
		manyToOneMapping.setSpecifiedOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, manyToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.TRUE, manyToOne.getOptional());
	
		manyToOneMapping.setSpecifiedOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, manyToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.FALSE, manyToOne.getOptional());

		
		//set optional to null in the context model
		manyToOneMapping.setSpecifiedOptional(null);
		assertNull(manyToOneMapping.getSpecifiedOptional());
		assertNull(manyToOne.getOptional());
	}
	
	
	public void testSpecifiedJoinColumns() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();
		
		ListIterator<IJavaJoinColumn> specifiedJoinColumns = manyToOneMapping.specifiedJoinColumns();
		
		assertFalse(specifiedJoinColumns.hasNext());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();

		//add an annotation to the resource model and verify the context model is updated
		JoinColumn joinColumn = (JoinColumn) attributeResource.addAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		joinColumn.setName("FOO");
		specifiedJoinColumns = manyToOneMapping.specifiedJoinColumns();	
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());

		joinColumn = (JoinColumn) attributeResource.addAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		joinColumn.setName("BAR");
		specifiedJoinColumns = manyToOneMapping.specifiedJoinColumns();		
		assertEquals("BAR", specifiedJoinColumns.next().getName());
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());


		joinColumn = (JoinColumn) attributeResource.addAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		joinColumn.setName("BAZ");
		specifiedJoinColumns = manyToOneMapping.specifiedJoinColumns();		
		assertEquals("BAZ", specifiedJoinColumns.next().getName());
		assertEquals("BAR", specifiedJoinColumns.next().getName());
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());
	
		//move an annotation to the resource model and verify the context model is updated
		attributeResource.move(1, 0, JPA.JOIN_COLUMNS);
		specifiedJoinColumns = manyToOneMapping.specifiedJoinColumns();		
		assertEquals("BAR", specifiedJoinColumns.next().getName());
		assertEquals("BAZ", specifiedJoinColumns.next().getName());
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());

		attributeResource.removeAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		specifiedJoinColumns = manyToOneMapping.specifiedJoinColumns();		
		assertEquals("BAZ", specifiedJoinColumns.next().getName());
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());
	
		attributeResource.removeAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		specifiedJoinColumns = manyToOneMapping.specifiedJoinColumns();		
		assertEquals("FOO", specifiedJoinColumns.next().getName());
		assertFalse(specifiedJoinColumns.hasNext());

		
		attributeResource.removeAnnotation(0, JPA.JOIN_COLUMN, JPA.JOIN_COLUMNS);
		specifiedJoinColumns = manyToOneMapping.specifiedJoinColumns();		
		assertFalse(specifiedJoinColumns.hasNext());
	}
	
	public void testDefaultJoinColumns() {
		//TODO
	}
	
	public void testSpecifiedJoinColumnsSize() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();

		assertEquals(0, manyToOneMapping.specifiedJoinColumnsSize());
		
		manyToOneMapping.addSpecifiedJoinColumn(0);
		assertEquals(1, manyToOneMapping.specifiedJoinColumnsSize());
		
		manyToOneMapping.removeSpecifiedJoinColumn(0);
		assertEquals(0, manyToOneMapping.specifiedJoinColumnsSize());
	}

	public void testAddSpecifiedJoinColumn() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();

		manyToOneMapping.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		manyToOneMapping.addSpecifiedJoinColumn(0).setSpecifiedName("BAR");
		manyToOneMapping.addSpecifiedJoinColumn(0).setSpecifiedName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Iterator<JavaResource> joinColumns = attributeResource.annotations(JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		
		assertEquals("BAZ", ((JoinColumn) joinColumns.next()).getName());
		assertEquals("BAR", ((JoinColumn) joinColumns.next()).getName());
		assertEquals("FOO", ((JoinColumn) joinColumns.next()).getName());
		assertFalse(joinColumns.hasNext());
	}
	
	public void testAddSpecifiedJoinColumn2() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();

		manyToOneMapping.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		manyToOneMapping.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		manyToOneMapping.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Iterator<JavaResource> joinColumns = attributeResource.annotations(JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		
		assertEquals("FOO", ((JoinColumn) joinColumns.next()).getName());
		assertEquals("BAR", ((JoinColumn) joinColumns.next()).getName());
		assertEquals("BAZ", ((JoinColumn) joinColumns.next()).getName());
		assertFalse(joinColumns.hasNext());
	}
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();

		manyToOneMapping.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		manyToOneMapping.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		manyToOneMapping.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
	
		assertEquals(3, CollectionTools.size(attributeResource.annotations(JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME)));

		manyToOneMapping.removeSpecifiedJoinColumn(1);
		
		Iterator<JavaResource> joinColumnResources = attributeResource.annotations(JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		assertEquals("FOO", ((JoinColumn) joinColumnResources.next()).getName());		
		assertEquals("BAZ", ((JoinColumn) joinColumnResources.next()).getName());
		assertFalse(joinColumnResources.hasNext());
		
		Iterator<IJoinColumn> joinColumns = manyToOneMapping.specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());		
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		
		manyToOneMapping.removeSpecifiedJoinColumn(1);
		joinColumnResources = attributeResource.annotations(JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		assertEquals("FOO", ((JoinColumn) joinColumnResources.next()).getName());		
		assertFalse(joinColumnResources.hasNext());

		joinColumns = manyToOneMapping.specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		
		manyToOneMapping.removeSpecifiedJoinColumn(0);
		joinColumnResources = attributeResource.annotations(JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		assertFalse(joinColumnResources.hasNext());
		joinColumns = manyToOneMapping.specifiedJoinColumns();
		assertFalse(joinColumns.hasNext());

		assertNull(attributeResource.annotation(JoinColumns.ANNOTATION_NAME));
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();

		manyToOneMapping.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		manyToOneMapping.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		manyToOneMapping.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JavaPersistentAttributeResource attributeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME).attributes().next();
		
		ListIterator<JoinColumn> javaJoinColumns = attributeResource.annotations(JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaJoinColumns));
		
		
		manyToOneMapping.moveSpecifiedJoinColumn(2, 0);
		ListIterator<IJoinColumn> primaryKeyJoinColumns = manyToOneMapping.specifiedJoinColumns();
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaJoinColumns = attributeResource.annotations(JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		assertEquals("BAR", javaJoinColumns.next().getName());
		assertEquals("BAZ", javaJoinColumns.next().getName());
		assertEquals("FOO", javaJoinColumns.next().getName());


		manyToOneMapping.moveSpecifiedJoinColumn(0, 1);
		primaryKeyJoinColumns = manyToOneMapping.specifiedJoinColumns();
		assertEquals("BAZ", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("BAR", primaryKeyJoinColumns.next().getSpecifiedName());
		assertEquals("FOO", primaryKeyJoinColumns.next().getSpecifiedName());

		javaJoinColumns = attributeResource.annotations(JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		assertEquals("BAZ", javaJoinColumns.next().getName());
		assertEquals("BAR", javaJoinColumns.next().getName());
		assertEquals("FOO", javaJoinColumns.next().getName());
	}

	public void testUpdateSpecifiedJoinColumns() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();
		JavaPersistentAttributeResource attributeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME).attributes().next();
	
		((JoinColumn) attributeResource.addAnnotation(0, JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME)).setName("FOO");
		((JoinColumn) attributeResource.addAnnotation(1, JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME)).setName("BAR");
		((JoinColumn) attributeResource.addAnnotation(2, JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME)).setName("BAZ");
			
		ListIterator<IJoinColumn> joinColumns = manyToOneMapping.specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		attributeResource.move(2, 0, JoinColumns.ANNOTATION_NAME);
		joinColumns = manyToOneMapping.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		attributeResource.move(0, 1, JoinColumns.ANNOTATION_NAME);
		joinColumns = manyToOneMapping.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		attributeResource.removeAnnotation(1,  JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		joinColumns = manyToOneMapping.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		attributeResource.removeAnnotation(1,  JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		joinColumns = manyToOneMapping.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		attributeResource.removeAnnotation(0,  JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		joinColumns = manyToOneMapping.specifiedJoinColumns();
		assertFalse(joinColumns.hasNext());
	}
	public void testJoinColumnIsVirtual() throws Exception {
		createTestEntityWithManyToOneMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();

		manyToOneMapping.addSpecifiedJoinColumn(0);
		IJoinColumn specifiedJoinColumn = manyToOneMapping.specifiedJoinColumns().next();
		assertFalse(specifiedJoinColumn.isVirtual());
		
		IJoinColumn defaultJoinColumn = manyToOneMapping.defaultJoinColumns().next();
		assertTrue(defaultJoinColumn.isVirtual());
	}
	
	public void testDefaultTargetEntity() throws Exception {
		createTestEntityWithValidManyToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();

		//targetEntity not in the persistence unit, default still set, handled by validation
		assertEquals(PACKAGE_NAME + ".Address", manyToOneMapping.getDefaultTargetEntity());
		
		//add targetEntity to the persistence unit
		addXmlClassRef(PACKAGE_NAME + ".Address");
		assertEquals(PACKAGE_NAME + ".Address", manyToOneMapping.getDefaultTargetEntity());

		//test default still the same when specified target entity it set
		manyToOneMapping.setSpecifiedTargetEntity("foo");
		assertEquals(PACKAGE_NAME + ".Address", manyToOneMapping.getDefaultTargetEntity());
		
		ListIterator<IClassRef> classRefs = persistenceUnit().classRefs();
		classRefs.next();
		IClassRef addressClassRef = classRefs.next();
		IJavaPersistentType addressPersistentType = addressClassRef.getJavaPersistentType();

		//test target is not an Entity, default target entity still exists, this case handled with validation
		addressPersistentType.setMappingKey(IMappingKeys.NULL_TYPE_MAPPING_KEY);
		assertEquals(PACKAGE_NAME + ".Address", manyToOneMapping.getDefaultTargetEntity());
	}
	
	public void testDefaultTargetEntityCollectionType() throws Exception {
		createTestEntityWithCollectionManyToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();

		assertNull(manyToOneMapping.getDefaultTargetEntity());
	}
	
	public void testDefaultTargetEntityGenericizedCollectionType() throws Exception {
		createTestEntityWithGenericizedCollectionManyToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
	
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();

		assertNull(manyToOneMapping.getDefaultTargetEntity());
	}
	
	public void testTargetEntity() throws Exception {
		createTestEntityWithValidManyToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();

		assertEquals(PACKAGE_NAME + ".Address", manyToOneMapping.getTargetEntity());

		manyToOneMapping.setSpecifiedTargetEntity("foo");
		assertEquals("foo", manyToOneMapping.getTargetEntity());
		
		manyToOneMapping.setSpecifiedTargetEntity(null);
		assertEquals(PACKAGE_NAME + ".Address", manyToOneMapping.getTargetEntity());
	}
	
	public void testResolvedTargetEntity() throws Exception {
		createTestEntityWithValidManyToOneMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IManyToOneMapping manyToOneMapping = (IManyToOneMapping) persistentAttribute.getMapping();

		//targetEntity not in the persistence unit
		assertNull(manyToOneMapping.getResolvedTargetEntity());
		
		//add targetEntity to the persistence unit, now target entity should resolve
		addXmlClassRef(PACKAGE_NAME + ".Address");
		ListIterator<IClassRef> classRefs = persistenceUnit().classRefs();
		classRefs.next();
		IClassRef addressClassRef = classRefs.next();
		ITypeMapping addressTypeMapping = addressClassRef.getJavaPersistentType().getMapping();
		assertEquals(addressTypeMapping, manyToOneMapping.getResolvedTargetEntity());

		//test default still the same when specified target entity it set
		manyToOneMapping.setSpecifiedTargetEntity("foo");
		assertNull(manyToOneMapping.getResolvedTargetEntity());
		
		
		manyToOneMapping.setSpecifiedTargetEntity(PACKAGE_NAME + ".Address");
		assertEquals(addressTypeMapping, manyToOneMapping.getResolvedTargetEntity());
		

		manyToOneMapping.setSpecifiedTargetEntity(null);
		assertEquals(addressTypeMapping, manyToOneMapping.getResolvedTargetEntity());
	}

}

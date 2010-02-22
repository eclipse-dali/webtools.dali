/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.jpa2.context.ManyToManyMapping2_0;
import org.eclipse.jpt.core.jpa2.context.OrderColumn2_0;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyClass2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.MapKeyColumn2_0Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJoinFetchAnnotation;
import org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.EclipseLink2_0ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLink2_0JavaManyToManyMappingTests
	extends EclipseLink2_0ContextModelTestCase
{
	public EclipseLink2_0JavaManyToManyMappingTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestEntityWithJoinFetchManyToMany() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY, EclipseLink.JOIN_FETCH);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToMany").append(CR);
				sb.append("@JoinFetch").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithValidManyToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.ID, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToMany").append(CR);				
				sb.append("    private Collection<Address> addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}

	private ICompilationUnit createTestEntityWithValidGenericMapManyToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToMany").append(CR);				
				sb.append("    private java.util.Map<Integer, Address> addresses;").append(CR);			
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithValidNonGenericMapManyToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToMany").append(CR);				
				sb.append("    private java.util.Map addresses;").append(CR);			
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
	
	private void createTestTargetEntityAddress() throws Exception {
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
					sb.append(JPA.EMBEDDED);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.MANY_TO_MANY);
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
				sb.append("    @Embedded").append(CR);
				sb.append("    private State state;").append(CR);
				sb.append(CR);
				sb.append("    private int zip;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToMany").append(CR);
				sb.append("    private java.util.Collection<AnnotationTestType> employees;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
	}

	private void createTestTargetEntityAddressWithElementCollection() throws Exception {
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
					sb.append(JPA2_0.ELEMENT_COLLECTION);
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
				sb.append("    @ElementCollection").append(CR);
				sb.append("    private java.util.Collection<State> state;").append(CR);
				sb.append(CR);
				sb.append("    private int zip;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
	}

	private void createTestEmbeddableState() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.EMBEDDABLE);
					sb.append(";");
					sb.append(CR);
				sb.append("@Embeddable");
				sb.append(CR);
				sb.append("public class ").append("State").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    private String foo;").append(CR);
				sb.append(CR);
				sb.append("    private Address address;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "State.java", sourceWriter);
	}
	
	public void testGetJoinFetchValue() throws Exception {
		createTestEntityWithJoinFetchManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkRelationshipMapping manyToManyMapping = (EclipseLinkRelationshipMapping) persistentAttribute.getSpecifiedMapping();
		EclipseLinkJoinFetch contextJoinFetch = manyToManyMapping.getJoinFetch();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkJoinFetchAnnotation joinFetchAnnotation = (EclipseLinkJoinFetchAnnotation) attributeResource.getAnnotation(EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME);
		
		// base annotated, test context value
		
		assertNull(joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
		
		// change resource to INNER specifically, test context
		
		joinFetchAnnotation.setValue(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.INNER);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.INNER, joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
		
		// change resource to OUTER, test context
		
		joinFetchAnnotation.setValue(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.OUTER);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.OUTER, joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.OUTER, contextJoinFetch.getValue());
		
		// remove value from resource, test context
		
		joinFetchAnnotation.setValue(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
		
		// remove annotation, text context
		
		attributeResource.removeAnnotation(EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		assertNull(joinFetchAnnotation.getValue());
		assertNull(contextJoinFetch.getValue());
	}
	
	public void testSetJoinFetchValue() throws Exception {
		createTestEntityWithJoinFetchManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkRelationshipMapping manyToManyMapping = (EclipseLinkRelationshipMapping) persistentAttribute.getSpecifiedMapping();
		EclipseLinkJoinFetch contextJoinFetch = manyToManyMapping.getJoinFetch();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkJoinFetchAnnotation joinFetchAnnotation = (EclipseLinkJoinFetchAnnotation) attributeResource.getAnnotation(EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME);
		
		// base annotated, test resource value
		
		assertNull(joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
		
		// change context to INNER specifically, test resource
		
		contextJoinFetch.setValue(EclipseLinkJoinFetchType.INNER);
		
		assertNull(joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
		
		// change context to OUTER, test resource
		
		contextJoinFetch.setValue(EclipseLinkJoinFetchType.OUTER);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.OUTER, joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.OUTER, contextJoinFetch.getValue());
		
		// set context to null, test resource
		
		contextJoinFetch.setValue(null);
		
		assertNull(attributeResource.getAnnotation(EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME));
		assertNull(contextJoinFetch.getValue());
		
		// change context to INNER specifically (this time from no annotation), test resource
		
		contextJoinFetch.setValue(EclipseLinkJoinFetchType.INNER);
		joinFetchAnnotation = (EclipseLinkJoinFetchAnnotation) attributeResource.getAnnotation(EclipseLinkJoinFetchAnnotation.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.INNER, joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
	}

	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = (getJavaPersistentType()).attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertEquals("employees", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		manyToManyMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertFalse(attributeNames.hasNext());
		
		manyToManyMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertEquals("employees", attributeNames.next());
		assertFalse(attributeNames.hasNext());

		AttributeMapping stateFooMapping = manyToManyMapping.getResolvedTargetEntity().resolveAttributeMapping("state.foo");
		assertEquals("foo", stateFooMapping.getName());
	}
	
	public void testCandidateMappedByAttributeNamesElementCollection() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		createTestTargetEntityAddressWithElementCollection();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = (getJavaPersistentType()).attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		manyToManyMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertFalse(attributeNames.hasNext());
		
		manyToManyMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());

		AttributeMapping stateFooMapping = manyToManyMapping.getResolvedTargetEntity().resolveAttributeMapping("state.foo");
		assertEquals("foo", stateFooMapping.getName());
	}
	
	public void testUpdateMapKey() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		assertNull(manyToManyMapping.getSpecifiedMapKey());
		assertNull(attributeResource.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME));
		
		//set mapKey in the resource model, verify context model does not change
		attributeResource.addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertNull(manyToManyMapping.getSpecifiedMapKey());
		MapKeyAnnotation mapKey = (MapKeyAnnotation) attributeResource.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertNotNull(mapKey);
				
		//set mapKey name in the resource model, verify context model updated
		mapKey.setName("myMapKey");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("myMapKey", manyToManyMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", mapKey.getName());
		
		//set mapKey name to null in the resource model
		mapKey.setName(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(manyToManyMapping.getSpecifiedMapKey());
		assertNull(mapKey.getName());
		
		mapKey.setName("myMapKey");
		attributeResource.removeAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertNull(manyToManyMapping.getSpecifiedMapKey());
		assertNull(attributeResource.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME));
	}
	
	public void testModifyMapKey() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		assertNull(manyToManyMapping.getSpecifiedMapKey());
		assertNull(attributeResource.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME));
					
		//set mapKey  in the context model, verify resource model updated
		manyToManyMapping.setSpecifiedMapKey("myMapKey");
		MapKeyAnnotation mapKey = (MapKeyAnnotation) attributeResource.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertEquals("myMapKey", manyToManyMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", mapKey.getName());
	
		//set mapKey to null in the context model
		manyToManyMapping.setSpecifiedMapKey(null);
		assertNull(manyToManyMapping.getSpecifiedMapKey());
		assertNull(attributeResource.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME));
	}
	
	public void testCandidateMapKeyNames() throws Exception {
		createTestEntityWithValidGenericMapManyToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping2_0 = (ManyToManyMapping) persistentAttribute.getMapping();

		Iterator<String> mapKeyNames = 
			manyToManyMapping2_0.candidateMapKeyNames();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.foo", mapKeyNames.next());
		assertEquals("state.address", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertEquals("employees", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
	}
	
	public void testCandidateMapKeyNames2() throws Exception {
		createTestEntityWithValidNonGenericMapManyToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping manyToManyMapping2_0 = (ManyToManyMapping) persistentAttribute.getMapping();

		Iterator<String> mapKeyNames = manyToManyMapping2_0.candidateMapKeyNames();
		assertEquals(false, mapKeyNames.hasNext());
		
		manyToManyMapping2_0.setSpecifiedTargetEntity("Address");
		mapKeyNames = manyToManyMapping2_0.candidateMapKeyNames();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.foo", mapKeyNames.next());
		assertEquals("state.address", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertEquals("employees", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		manyToManyMapping2_0.setSpecifiedTargetEntity("String");
		mapKeyNames = manyToManyMapping2_0.candidateMapKeyNames();
		assertEquals(false, mapKeyNames.hasNext());
	}
	
	public void testUpdateMapKeyClass() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping2_0 manyToManyMapping = (ManyToManyMapping2_0) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		assertNull(manyToManyMapping.getSpecifiedMapKeyClass());
		assertNull(attributeResource.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME));
		
		//set mapKey in the resource model, verify context model does not change
		attributeResource.addAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
		assertNull(manyToManyMapping.getSpecifiedMapKeyClass());
		MapKeyClass2_0Annotation mapKeyClass = (MapKeyClass2_0Annotation) attributeResource.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
		assertNotNull(mapKeyClass);
				
		//set mapKey name in the resource model, verify context model updated
		mapKeyClass.setValue("myMapKeyClass");
		assertEquals("myMapKeyClass", manyToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("myMapKeyClass", mapKeyClass.getValue());
		
		//set mapKey name to null in the resource model
		mapKeyClass.setValue(null);
		assertNull(manyToManyMapping.getSpecifiedMapKeyClass());
		assertNull(mapKeyClass.getValue());
		
		mapKeyClass.setValue("myMapKeyClass");
		attributeResource.removeAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();

		assertNull(manyToManyMapping.getSpecifiedMapKeyClass());
		assertNull(attributeResource.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testModifyMapKeyClass() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping2_0 manyToManyMapping = (ManyToManyMapping2_0) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		
		assertNull(manyToManyMapping.getSpecifiedMapKeyClass());
		assertNull(attributeResource.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME));
					
		//set mapKey  in the context model, verify resource model updated
		manyToManyMapping.setSpecifiedMapKeyClass("String");
		MapKeyClass2_0Annotation mapKeyClass = (MapKeyClass2_0Annotation) attributeResource.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME);
		assertEquals("String", manyToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("String", mapKeyClass.getValue());
	
		//set mapKey to null in the context model
		manyToManyMapping.setSpecifiedMapKeyClass(null);
		assertNull(manyToManyMapping.getSpecifiedMapKeyClass());
		assertNull(attributeResource.getAnnotation(MapKeyClass2_0Annotation.ANNOTATION_NAME));
	}

	public void testDefaultMapKeyClass() throws Exception {
		createTestEntityWithValidGenericMapManyToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping2_0 manyToManyMapping = (ManyToManyMapping2_0) persistentAttribute.getMapping();

		assertEquals("java.lang.Integer", manyToManyMapping.getDefaultMapKeyClass());

		//test default still the same when specified target entity it set
		manyToManyMapping.setSpecifiedMapKeyClass("foo");
		assertEquals("java.lang.Integer", manyToManyMapping.getDefaultMapKeyClass());
	}
	
	public void testDefaultMapKeyClassCollectionType() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping2_0 manyToManyMapping = (ManyToManyMapping2_0) persistentAttribute.getMapping();

		assertNull(manyToManyMapping.getDefaultMapKeyClass());
	}
	
	public void testMapKeyClass() throws Exception {
		createTestEntityWithValidGenericMapManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping2_0 manyToManyMapping = (ManyToManyMapping2_0) persistentAttribute.getMapping();

		assertEquals("java.lang.Integer", manyToManyMapping.getMapKeyClass());

		manyToManyMapping.setSpecifiedMapKeyClass("foo");
		assertEquals("foo", manyToManyMapping.getMapKeyClass());
		
		manyToManyMapping.setSpecifiedMapKeyClass(null);
		assertEquals("java.lang.Integer", manyToManyMapping.getMapKeyClass());
	}

	public void testOrderColumnDefaults() throws Exception {
		createTestEntityPrintQueue();
		createTestEntityPrintJob();
		
		addXmlClassRef(PACKAGE_NAME + ".PrintQueue");
		addXmlClassRef(PACKAGE_NAME + ".PrintJob");
		JavaPersistentType printQueuePersistentType = (JavaPersistentType) getPersistenceUnit().getPersistentType("test.PrintQueue");
		ManyToManyMapping jobsMapping = (ManyToManyMapping) printQueuePersistentType.getAttributeNamed("jobs").getMapping();
		JavaPersistentType printJobPersistentType = (JavaPersistentType) getPersistenceUnit().getPersistentType("test.PrintJob");
		ManyToManyMapping queuesMapping = (ManyToManyMapping) printJobPersistentType.getAttributeNamed("queues").getMapping();

		Orderable2_0 jobsOrderable = ((Orderable2_0) jobsMapping.getOrderable());
		OrderColumn2_0 jobsOrderColumn = jobsOrderable.getOrderColumn();
		assertEquals(true, jobsOrderable.isOrderColumnOrdering());
		assertEquals(null, jobsOrderColumn.getSpecifiedName());
		assertEquals("jobs_ORDER", jobsOrderColumn.getDefaultName());
		assertEquals("PrintJob_PrintQueue", jobsOrderColumn.getTable()); //the join table name
		Orderable2_0 queuesOrderable = ((Orderable2_0) queuesMapping.getOrderable());
		OrderColumn2_0 queuesOrderColumn = queuesOrderable.getOrderColumn();
		assertEquals(true, queuesOrderable.isOrderColumnOrdering());
		assertEquals(null, queuesOrderColumn.getSpecifiedName());
		assertEquals("queues_ORDER", queuesOrderColumn.getDefaultName());
		assertEquals("PrintJob_PrintQueue", queuesOrderColumn.getTable());
		
		jobsOrderColumn.setSpecifiedName("FOO");
		assertEquals("FOO", jobsOrderColumn.getSpecifiedName());
		assertEquals("jobs_ORDER", jobsOrderColumn.getDefaultName());
		assertEquals("PrintJob_PrintQueue", jobsOrderColumn.getTable());
		queuesOrderColumn.setSpecifiedName("BAR");
		assertEquals("BAR", queuesOrderColumn.getSpecifiedName());
		assertEquals("queues_ORDER", queuesOrderColumn.getDefaultName());
		assertEquals("PrintJob_PrintQueue", queuesOrderColumn.getTable());
		
		((Entity) printJobPersistentType.getMapping()).getTable().setSpecifiedName("MY_TABLE");
		assertEquals("MY_TABLE_PrintQueue", jobsOrderColumn.getTable());
		assertEquals("MY_TABLE_PrintQueue", queuesOrderColumn.getTable());
		
		((Entity) printQueuePersistentType.getMapping()).getTable().setSpecifiedName("OTHER_TABLE");
		assertEquals("MY_TABLE_OTHER_TABLE", jobsOrderColumn.getTable());
		assertEquals("MY_TABLE_OTHER_TABLE", queuesOrderColumn.getTable());
		
		queuesMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		assertEquals("MY_JOIN_TABLE", jobsOrderColumn.getTable());
		assertEquals("MY_JOIN_TABLE", queuesOrderColumn.getTable());
	}
	
	private void createTestEntityPrintQueue() throws Exception {
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
					sb.append("import ");
					sb.append(JPA2_0.ORDER_COLUMN);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("PrintQueue").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private String name;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToMany(mappedBy=\"queues\")").append(CR);
				sb.append("    @OrderColumn").append(CR);
				sb.append("    private java.util.List<PrintJob> jobs;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "PrintQueue.java", sourceWriter);
	}
	
	private void createTestEntityPrintJob() throws Exception {
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
					sb.append("import ");
					sb.append(JPA2_0.ORDER_COLUMN);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("PrintJob").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int id;").append(CR);
				sb.append(CR);
				sb.append("    @ManyToMany").append(CR);
				sb.append("    @OrderColumn").append(CR);
				sb.append("    private java.util.List<PrintQueue> queues;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "PrintJob.java", sourceWriter);
	}

	public void testGetMapKeyColumnMappedByStrategy() throws Exception {
		createTestEntityWithValidGenericMapManyToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping2_0 manyToManyMapping = (ManyToManyMapping2_0) persistentAttribute.getSpecifiedMapping();
		manyToManyMapping.getRelationshipReference().setMappedByJoiningStrategy();
		manyToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().setMappedByAttribute("employees");
		
		assertNull(manyToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("addresses_KEY", manyToManyMapping.getMapKeyColumn().getName());
		assertEquals("Address_" + TYPE_NAME, manyToManyMapping.getMapKeyColumn().getTable());//join table name of owning many-to-many
		
		PersistentType persistentType = getPersistenceUnit().getPersistentType("test.Address");
		ManyToManyMapping owningManyToManyMapping = (ManyToManyMapping) persistentType.getAttributeNamed("employees").getMapping();
		((JoinTableJoiningStrategy) owningManyToManyMapping.getRelationshipReference().getPredominantJoiningStrategy()).getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		assertEquals("MY_JOIN_TABLE", manyToManyMapping.getMapKeyColumn().getTable());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) attributeResource.addAnnotation(JPA2_0.MAP_KEY_COLUMN);
		column.setName("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("foo", manyToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("foo", manyToManyMapping.getMapKeyColumn().getName());
		assertEquals("addresses_KEY", manyToManyMapping.getMapKeyColumn().getDefaultName());
	}

	public void testGetMapKeyColumnJoinTableStrategy() throws Exception {
		createTestEntityWithValidGenericMapManyToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ManyToManyMapping2_0 manyToManyMapping = (ManyToManyMapping2_0) persistentAttribute.getSpecifiedMapping();
		
		assertNull(manyToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("addresses_KEY", manyToManyMapping.getMapKeyColumn().getName());
		assertEquals(TYPE_NAME + "_Address", manyToManyMapping.getMapKeyColumn().getTable());//join table name
		
		manyToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		assertEquals("MY_JOIN_TABLE", manyToManyMapping.getMapKeyColumn().getTable());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		MapKeyColumn2_0Annotation column = (MapKeyColumn2_0Annotation) attributeResource.addAnnotation(JPA2_0.MAP_KEY_COLUMN);
		column.setName("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("foo", manyToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("foo", manyToManyMapping.getMapKeyColumn().getName());
		assertEquals("addresses_KEY", manyToManyMapping.getMapKeyColumn().getDefaultName());
	}
}

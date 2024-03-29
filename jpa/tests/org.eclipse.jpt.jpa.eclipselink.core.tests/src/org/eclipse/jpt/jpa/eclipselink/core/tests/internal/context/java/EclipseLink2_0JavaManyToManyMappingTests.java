/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.tests.internal.projects.JavaProjectTestHarness.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.EnumType;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.jpa2.context.ManyToManyMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedOrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyClassAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyColumnAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyEnumeratedAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyTemporalAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.MapKeyAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetch;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkRelationshipMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.JoinFetchAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLink2_0ContextModelTestCase;

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
				return IteratorTools.iterator(JPA.ENTITY, JPA.MANY_TO_MANY, EclipseLink.JOIN_FETCH);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.ID, "java.util.Collection");
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.ID);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.ID);
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
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
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
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
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
				sb.append("    private String address;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "State.java", sourceWriter);
	}

	private ICompilationUnit createTestEntityWithEmbeddableKeyManyToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.MANY_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToMany").append(CR);				
				sb.append("    private java.util.Map<Address, PropertyInfo> parcels;").append(CR);			
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}

	private void createTestEmbeddableAddress() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.EMBEDDABLE);
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
				sb.append("@Embeddable");
				sb.append(CR);
				sb.append("public class ").append("Address").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    private String city;").append(CR);
				sb.append(CR);
				sb.append("    @Embedded").append(CR);
				sb.append("    private State state;").append(CR);
				sb.append(CR);
				sb.append("    private int zip;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "Address.java", sourceWriter);
	}

	private void createTestEntityPropertyInfo() throws Exception {
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
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("PropertyInfo").append(" ");
				sb.append("{").append(CR);
				sb.append(CR);
				sb.append("    private Integer parcelNumber;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
				sb.append("    private Integer size;").append(CR);
				sb.append(CR);
				sb.append("    private java.math.BigDecimal tax;").append(CR);
				sb.append(CR);
				sb.append("}").append(CR);
		}
		};
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "PropertyInfo.java", sourceWriter);
	}
	
	public void testGetJoinFetchValue() throws Exception {
		createTestEntityWithJoinFetchManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkRelationshipMapping manyToManyMapping = (EclipseLinkRelationshipMapping) persistentAttribute.getMapping();
		EclipseLinkJoinFetch contextJoinFetch = manyToManyMapping.getJoinFetch();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		JoinFetchAnnotation joinFetchAnnotation = (JoinFetchAnnotation) resourceField.getAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		
		// base annotated, test context value
		
		assertNull(joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
		
		// change resource to INNER specifically, test context
		
		joinFetchAnnotation.setValue(org.eclipse.jpt.jpa.eclipselink.core.resource.java.JoinFetchType.INNER);
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.JoinFetchType.INNER, joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
		
		// change resource to OUTER, test context
		
		joinFetchAnnotation.setValue(org.eclipse.jpt.jpa.eclipselink.core.resource.java.JoinFetchType.OUTER);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.JoinFetchType.OUTER, joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.OUTER, contextJoinFetch.getValue());
		
		// remove value from resource, test context
		
		joinFetchAnnotation.setValue(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
		
		// remove annotation, text context
		
		resourceField.removeAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		assertNull(joinFetchAnnotation.getValue());
		assertNull(contextJoinFetch.getValue());
	}
	
	public void testSetJoinFetchValue() throws Exception {
		createTestEntityWithJoinFetchManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkRelationshipMapping manyToManyMapping = (EclipseLinkRelationshipMapping) persistentAttribute.getMapping();
		EclipseLinkJoinFetch contextJoinFetch = manyToManyMapping.getJoinFetch();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		JoinFetchAnnotation joinFetchAnnotation = (JoinFetchAnnotation) resourceField.getAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		
		// base annotated, test resource value
		
		assertNull(joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
		
		// change context to INNER specifically, test resource
		
		contextJoinFetch.setValue(EclipseLinkJoinFetchType.INNER);
		
		assertNull(joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
		
		// change context to OUTER, test resource
		
		contextJoinFetch.setValue(EclipseLinkJoinFetchType.OUTER);
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.JoinFetchType.OUTER, joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.OUTER, contextJoinFetch.getValue());
		
		// set context to null, test resource
		
		contextJoinFetch.setValue(null);
		
		assertNull(resourceField.getAnnotation(JoinFetchAnnotation.ANNOTATION_NAME));
		assertNull(contextJoinFetch.getValue());
		
		// change context to INNER specifically (this time from no annotation), test resource
		
		contextJoinFetch.setValue(EclipseLinkJoinFetchType.INNER);
		joinFetchAnnotation = (JoinFetchAnnotation) resourceField.getAnnotation(JoinFetchAnnotation.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.JoinFetchType.INNER, joinFetchAnnotation.getValue());
		assertEquals(EclipseLinkJoinFetchType.INNER, contextJoinFetch.getValue());
	}

	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		SpecifiedPersistentAttribute persistentAttribute = (getJavaPersistentType()).getAttributes().iterator().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			manyToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
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
			manyToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertFalse(attributeNames.hasNext());
		
		manyToManyMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			manyToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
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
		
		SpecifiedPersistentAttribute persistentAttribute = (getJavaPersistentType()).getAttributes().iterator().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			manyToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		manyToManyMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			manyToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
		assertFalse(attributeNames.hasNext());
		
		manyToManyMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			manyToManyMapping.getRelationship().getMappedByStrategy().getCandidateMappedByAttributeNames().iterator();
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
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		assertNull(manyToManyMapping.getSpecifiedMapKey());
		assertNull(resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME));
		
		//set mapKey in the resource model, verify context model does not change
		resourceField.addAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertNull(manyToManyMapping.getSpecifiedMapKey());
		MapKeyAnnotation mapKey = (MapKeyAnnotation) resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
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
		resourceField.removeAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertNull(manyToManyMapping.getSpecifiedMapKey());
		assertNull(resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME));
	}
	
	public void testModifyMapKey() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		assertNull(manyToManyMapping.getSpecifiedMapKey());
		assertNull(resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME));
					
		//set mapKey  in the context model, verify resource model updated
		manyToManyMapping.setSpecifiedMapKey("myMapKey");
		MapKeyAnnotation mapKey = (MapKeyAnnotation) resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertEquals("myMapKey", manyToManyMapping.getSpecifiedMapKey());
		assertEquals("myMapKey", mapKey.getName());
		//set mapKey to null in the context model
		manyToManyMapping.setSpecifiedMapKey(null);
		assertNull(manyToManyMapping.getSpecifiedMapKey());
		mapKey = (MapKeyAnnotation) resourceField.getAnnotation(MapKeyAnnotation.ANNOTATION_NAME);
		assertNull(mapKey.getName());
	}
	
	public void testCandidateMapKeyNames() throws Exception {
		createTestEntityWithValidGenericMapManyToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToManyMapping manyToManyMapping2_0 = (ManyToManyMapping) persistentAttribute.getMapping();

		Iterator<String> mapKeyNames = 
			manyToManyMapping2_0.getCandidateMapKeyNames().iterator();
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
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToManyMapping manyToManyMapping2_0 = (ManyToManyMapping) persistentAttribute.getMapping();

		Iterator<String> mapKeyNames = manyToManyMapping2_0.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
		
		manyToManyMapping2_0.setSpecifiedTargetEntity("Address");
		mapKeyNames = manyToManyMapping2_0.getCandidateMapKeyNames().iterator();
		assertEquals("id", mapKeyNames.next());
		assertEquals("city", mapKeyNames.next());
		assertEquals("state", mapKeyNames.next());
		assertEquals("state.foo", mapKeyNames.next());
		assertEquals("state.address", mapKeyNames.next());
		assertEquals("zip", mapKeyNames.next());
		assertEquals("employees", mapKeyNames.next());
		assertFalse(mapKeyNames.hasNext());
		
		manyToManyMapping2_0.setSpecifiedTargetEntity("String");
		mapKeyNames = manyToManyMapping2_0.getCandidateMapKeyNames().iterator();
		assertEquals(false, mapKeyNames.hasNext());
	}
	
	public void testUpdateMapKeyClass() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToManyMapping2_0 manyToManyMapping = (ManyToManyMapping2_0) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		assertNull(manyToManyMapping.getSpecifiedMapKeyClass());
		assertNull(resourceField.getAnnotation(MapKeyClassAnnotation2_0.ANNOTATION_NAME));
		
		//set mapKey in the resource model, verify context model does not change
		resourceField.addAnnotation(MapKeyClassAnnotation2_0.ANNOTATION_NAME);
		assertNull(manyToManyMapping.getSpecifiedMapKeyClass());
		MapKeyClassAnnotation2_0 mapKeyClassAnnotation = (MapKeyClassAnnotation2_0) resourceField.getAnnotation(MapKeyClassAnnotation2_0.ANNOTATION_NAME);
		assertNotNull(mapKeyClassAnnotation);
				
		//set mapKey name in the resource model, verify context model updated
		mapKeyClassAnnotation.setValue("myMapKeyClass");
		this.getJpaProject().synchronizeContextModel();
		assertEquals("myMapKeyClass", manyToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("myMapKeyClass", mapKeyClassAnnotation.getValue());
		
		//set mapKey name to null in the resource model
		mapKeyClassAnnotation.setValue(null);
		this.getJpaProject().synchronizeContextModel();
		assertNull(manyToManyMapping.getSpecifiedMapKeyClass());
		assertNull(mapKeyClassAnnotation.getValue());
		
		mapKeyClassAnnotation.setValue("myMapKeyClass");
		resourceField.removeAnnotation(MapKeyClassAnnotation2_0.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();

		assertNull(manyToManyMapping.getSpecifiedMapKeyClass());
		assertNull(resourceField.getAnnotation(MapKeyClassAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testModifyMapKeyClass() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToManyMapping2_0 manyToManyMapping = (ManyToManyMapping2_0) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		assertNull(manyToManyMapping.getSpecifiedMapKeyClass());
		assertNull(resourceField.getAnnotation(MapKeyClassAnnotation2_0.ANNOTATION_NAME));
					
		//set mapKey  in the context model, verify resource model updated
		manyToManyMapping.setSpecifiedMapKeyClass("String");
		MapKeyClassAnnotation2_0 mapKeyClass = (MapKeyClassAnnotation2_0) resourceField.getAnnotation(MapKeyClassAnnotation2_0.ANNOTATION_NAME);
		assertEquals("String", manyToManyMapping.getSpecifiedMapKeyClass());
		assertEquals("String", mapKeyClass.getValue());
	
		//set mapKey to null in the context model
		manyToManyMapping.setSpecifiedMapKeyClass(null);
		assertNull(manyToManyMapping.getSpecifiedMapKeyClass());
		assertNull(resourceField.getAnnotation(MapKeyClassAnnotation2_0.ANNOTATION_NAME));
	}

	public void testDefaultMapKeyClass() throws Exception {
		createTestEntityWithValidGenericMapManyToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToManyMapping2_0 manyToManyMapping = (ManyToManyMapping2_0) persistentAttribute.getMapping();

		assertEquals("java.lang.Integer", manyToManyMapping.getDefaultMapKeyClass());

		//test default still the same when specified target entity it set
		manyToManyMapping.setSpecifiedMapKeyClass("foo");
		assertEquals("java.lang.Integer", manyToManyMapping.getDefaultMapKeyClass());
	}
	
	public void testDefaultMapKeyClassCollectionType() throws Exception {
		createTestEntityWithValidManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToManyMapping2_0 manyToManyMapping = (ManyToManyMapping2_0) persistentAttribute.getMapping();

		assertNull(manyToManyMapping.getDefaultMapKeyClass());
	}
	
	public void testMapKeyClass() throws Exception {
		createTestEntityWithValidGenericMapManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
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
		SpecifiedOrderColumn2_0 jobsOrderColumn = jobsOrderable.getOrderColumn();
		assertEquals(true, jobsOrderable.isOrderColumnOrdering());
		assertEquals(null, jobsOrderColumn.getSpecifiedName());
		assertEquals("jobs_ORDER", jobsOrderColumn.getDefaultName());
		assertEquals("PrintJob_PrintQueue", jobsOrderColumn.getTableName()); //the join table name
		Orderable2_0 queuesOrderable = ((Orderable2_0) queuesMapping.getOrderable());
		SpecifiedOrderColumn2_0 queuesOrderColumn = queuesOrderable.getOrderColumn();
		assertEquals(true, queuesOrderable.isOrderColumnOrdering());
		assertEquals(null, queuesOrderColumn.getSpecifiedName());
		assertEquals("queues_ORDER", queuesOrderColumn.getDefaultName());
		assertEquals("PrintJob_PrintQueue", queuesOrderColumn.getTableName());
		
		jobsOrderColumn.setSpecifiedName("FOO");
		assertEquals("FOO", jobsOrderColumn.getSpecifiedName());
		assertEquals("jobs_ORDER", jobsOrderColumn.getDefaultName());
		assertEquals("PrintJob_PrintQueue", jobsOrderColumn.getTableName());
		queuesOrderColumn.setSpecifiedName("BAR");
		assertEquals("BAR", queuesOrderColumn.getSpecifiedName());
		assertEquals("queues_ORDER", queuesOrderColumn.getDefaultName());
		assertEquals("PrintJob_PrintQueue", queuesOrderColumn.getTableName());
		
		((Entity) printJobPersistentType.getMapping()).getTable().setSpecifiedName("MY_TABLE");
		assertEquals("MY_TABLE_PrintQueue", jobsOrderColumn.getTableName());
		assertEquals("MY_TABLE_PrintQueue", queuesOrderColumn.getTableName());
		
		((Entity) printQueuePersistentType.getMapping()).getTable().setSpecifiedName("OTHER_TABLE");
		assertEquals("MY_TABLE_OTHER_TABLE", jobsOrderColumn.getTableName());
		assertEquals("MY_TABLE_OTHER_TABLE", queuesOrderColumn.getTableName());
		
		queuesMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		assertEquals("MY_JOIN_TABLE", jobsOrderColumn.getTableName());
		assertEquals("MY_JOIN_TABLE", queuesOrderColumn.getTableName());
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
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "PrintQueue.java", sourceWriter);
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
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "PrintJob.java", sourceWriter);
	}

	public void testGetMapKeyColumnMappedByStrategy() throws Exception {
		createTestEntityWithValidGenericMapManyToManyMapping();
		createTestTargetEntityAddress();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToManyMapping2_0 manyToManyMapping = (ManyToManyMapping2_0) persistentAttribute.getMapping();
		manyToManyMapping.getRelationship().setStrategyToMappedBy();
		manyToManyMapping.getRelationship().getMappedByStrategy().setMappedByAttribute("employees");
		
		assertNull(manyToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("addresses_KEY", manyToManyMapping.getMapKeyColumn().getName());
		assertEquals("Address_" + TYPE_NAME, manyToManyMapping.getMapKeyColumn().getTableName());//join table name of owning many-to-many
		
		PersistentType persistentType = getPersistenceUnit().getPersistentType("test.Address");
		ManyToManyMapping owningManyToManyMapping = (ManyToManyMapping) persistentType.getAttributeNamed("employees").getMapping();
		((SpecifiedJoinTableRelationshipStrategy) owningManyToManyMapping.getRelationship().getStrategy()).getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		assertEquals("MY_JOIN_TABLE", manyToManyMapping.getMapKeyColumn().getTableName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		MapKeyColumnAnnotation2_0 column = (MapKeyColumnAnnotation2_0) resourceField.addAnnotation(JPA2_0.MAP_KEY_COLUMN);
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
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		ManyToManyMapping2_0 manyToManyMapping = (ManyToManyMapping2_0) persistentAttribute.getMapping();
		
		assertNull(manyToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("addresses_KEY", manyToManyMapping.getMapKeyColumn().getName());
		assertEquals(TYPE_NAME + "_Address", manyToManyMapping.getMapKeyColumn().getTableName());//join table name
		
		manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		assertEquals("MY_JOIN_TABLE", manyToManyMapping.getMapKeyColumn().getTableName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		MapKeyColumnAnnotation2_0 column = (MapKeyColumnAnnotation2_0) resourceField.addAnnotation(JPA2_0.MAP_KEY_COLUMN);
		column.setName("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("foo", manyToManyMapping.getMapKeyColumn().getSpecifiedName());
		assertEquals("foo", manyToManyMapping.getMapKeyColumn().getName());
		assertEquals("addresses_KEY", manyToManyMapping.getMapKeyColumn().getDefaultName());
	}

	public void testMapKeySpecifiedAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddableKeyManyToManyMapping();
		createTestEmbeddableAddress();
		createTestEmbeddableState();
		createTestEntityPropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		
		JavaCollectionMapping2_0 manyToManyMapping = (JavaCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		JavaAttributeOverrideContainer mapKeyAttributeOverrideContainer = manyToManyMapping.getMapKeyAttributeOverrideContainer();
		
		ListIterator<JavaSpecifiedAttributeOverride> specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());

		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(1, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("value.BAR");
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("FOO", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("value.BAR", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());


		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.BAZ");
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("value.BAR", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());
	
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.BLAH");
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BLAH", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("BAZ", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("value.BAR", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());

		//move an annotation to the resource model and verify the context model is updated
		resourceField.moveAnnotation(1, 0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BAZ", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("BLAH", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("value.BAR", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());

		resourceField.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BLAH", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("FOO", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("value.BAR", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());
	
		resourceField.removeAnnotation(1, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("BLAH", specifiedMapKeyAttributeOverrides.next().getName());
		assertEquals("value.BAR", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());

		
		resourceField.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertEquals("value.BAR", specifiedMapKeyAttributeOverrides.next().getName());
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());

		resourceField.removeAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		specifiedMapKeyAttributeOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();		
		assertFalse(specifiedMapKeyAttributeOverrides.hasNext());
	}

	public void testMapKeyValueVirtualAttributeOverrides() throws Exception {
		createTestEntityWithEmbeddableKeyManyToManyMapping();
		createTestEmbeddableAddress();
		createTestEmbeddableState();
		createTestEntityPropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		JavaCollectionMapping2_0 manyToManyMapping = (JavaCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		JavaAttributeOverrideContainer mapKeyAttributeOverrideContainer = manyToManyMapping.getMapKeyAttributeOverrideContainer();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertEquals("parcels", resourceField.getName());
		assertEquals(0, resourceField.getAnnotationsSize(AttributeOverrideAnnotation.ANNOTATION_NAME));
		
		assertEquals(4, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
		AttributeOverride defaultAttributeOverride = mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("city", defaultAttributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME +"_PropertyInfo", defaultAttributeOverride.getColumn().getTableName());
		assertEquals(null, defaultAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, defaultAttributeOverride.getColumn().isInsertable());
		assertEquals(true, defaultAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, defaultAttributeOverride.getColumn().isUnique());
		assertEquals(true, defaultAttributeOverride.getColumn().isNullable());
		assertEquals(255, defaultAttributeOverride.getColumn().getLength());
		assertEquals(0, defaultAttributeOverride.getColumn().getPrecision());
		assertEquals(0, defaultAttributeOverride.getColumn().getScale());
		
		ListIterator<ClassRef> classRefs = getPersistenceUnit().getSpecifiedClassRefs().iterator();
		classRefs.next();
		Embeddable addressEmbeddable = (Embeddable) classRefs.next().getJavaPersistentType().getMapping();
		
		BasicMapping cityMapping = (BasicMapping) addressEmbeddable.getPersistentType().getAttributeNamed("city").getMapping();
		cityMapping.getColumn().setSpecifiedName("FOO");
		cityMapping.getColumn().setSpecifiedTableName("BAR");
		cityMapping.getColumn().setColumnDefinition("COLUMN_DEF");
		cityMapping.getColumn().setSpecifiedInsertable(Boolean.FALSE);
		cityMapping.getColumn().setSpecifiedUpdatable(Boolean.FALSE);
		cityMapping.getColumn().setSpecifiedUnique(Boolean.TRUE);
		cityMapping.getColumn().setSpecifiedNullable(Boolean.FALSE);
		cityMapping.getColumn().setSpecifiedLength(Integer.valueOf(5));
		cityMapping.getColumn().setSpecifiedPrecision(Integer.valueOf(6));
		cityMapping.getColumn().setSpecifiedScale(Integer.valueOf(7));
		
		assertEquals("parcels", resourceField.getName());
		assertEquals(0, resourceField.getAnnotationsSize(AttributeOverrideAnnotation.ANNOTATION_NAME));
		
		assertEquals(4, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
		defaultAttributeOverride = mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("FOO", defaultAttributeOverride.getColumn().getName());
		assertEquals("BAR", defaultAttributeOverride.getColumn().getTableName());
		assertEquals("COLUMN_DEF", defaultAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(false, defaultAttributeOverride.getColumn().isInsertable());
		assertEquals(false, defaultAttributeOverride.getColumn().isUpdatable());
		assertEquals(true, defaultAttributeOverride.getColumn().isUnique());
		assertEquals(false, defaultAttributeOverride.getColumn().isNullable());
		assertEquals(5, defaultAttributeOverride.getColumn().getLength());
		assertEquals(6, defaultAttributeOverride.getColumn().getPrecision());
		assertEquals(7, defaultAttributeOverride.getColumn().getScale());
		
		cityMapping.getColumn().setSpecifiedName(null);
		cityMapping.getColumn().setSpecifiedTableName(null);
		cityMapping.getColumn().setColumnDefinition(null);
		cityMapping.getColumn().setSpecifiedInsertable(null);
		cityMapping.getColumn().setSpecifiedUpdatable(null);
		cityMapping.getColumn().setSpecifiedUnique(null);
		cityMapping.getColumn().setSpecifiedNullable(null);
		cityMapping.getColumn().setSpecifiedLength(null);
		cityMapping.getColumn().setSpecifiedPrecision(null);
		cityMapping.getColumn().setSpecifiedScale(null);
		defaultAttributeOverride = mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("city", defaultAttributeOverride.getName());
		assertEquals("city", defaultAttributeOverride.getColumn().getName());
		assertEquals(TYPE_NAME +"_PropertyInfo", defaultAttributeOverride.getColumn().getTableName());
		assertEquals(null, defaultAttributeOverride.getColumn().getColumnDefinition());
		assertEquals(true, defaultAttributeOverride.getColumn().isInsertable());
		assertEquals(true, defaultAttributeOverride.getColumn().isUpdatable());
		assertEquals(false, defaultAttributeOverride.getColumn().isUnique());
		assertEquals(true, defaultAttributeOverride.getColumn().isNullable());
		assertEquals(255, defaultAttributeOverride.getColumn().getLength());
		assertEquals(0, defaultAttributeOverride.getColumn().getPrecision());
		assertEquals(0, defaultAttributeOverride.getColumn().getScale());
		
		AttributeOverrideAnnotation annotation = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		annotation.setName("key.city");
		getJpaProject().synchronizeContextModel();
		assertEquals(3, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
	}
	
	public void testMapKeyValueSpecifiedAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddableKeyManyToManyMapping();
		createTestEmbeddableAddress();
		createTestEmbeddableState();
		createTestEntityPropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		JavaCollectionMapping2_0 manyToManyMapping = (JavaCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		JavaAttributeOverrideContainer mapKeyAttributeOverrideContainer = manyToManyMapping.getMapKeyAttributeOverrideContainer();
		assertEquals(0, mapKeyAttributeOverrideContainer.getSpecifiedOverridesSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.BAR");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("value.FOO2");
		getJpaProject().synchronizeContextModel();

		assertEquals(3, mapKeyAttributeOverrideContainer.getSpecifiedOverridesSize());
	}
	
	public void testMapKeyValueAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddableKeyManyToManyMapping();
		createTestEmbeddableAddress();
		createTestEmbeddableState();
		createTestEntityPropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");

		JavaCollectionMapping2_0 manyToManyMapping = (JavaCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		JavaAttributeOverrideContainer mapKeyAttributeOverrideContainer = manyToManyMapping.getMapKeyAttributeOverrideContainer();
		assertEquals(4, mapKeyAttributeOverrideContainer.getOverridesSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.BAR");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("value.FOO2");
		getJpaProject().synchronizeContextModel();

		assertEquals(7, mapKeyAttributeOverrideContainer.getOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("city");
		getJpaProject().synchronizeContextModel();
		assertEquals(7, mapKeyAttributeOverrideContainer.getOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.state.foo");
		getJpaProject().synchronizeContextModel();
		assertEquals(7, mapKeyAttributeOverrideContainer.getOverridesSize());
	}
	
	public void testMapKeyValueVirtualAttributeOverridesSize() throws Exception {
		createTestEntityWithEmbeddableKeyManyToManyMapping();
		createTestEmbeddableAddress();
		createTestEmbeddableState();
		createTestEntityPropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		JavaCollectionMapping2_0 manyToManyMapping = (JavaCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		JavaAttributeOverrideContainer mapKeyAttributeOverrideContainer = manyToManyMapping.getMapKeyAttributeOverrideContainer();
		assertEquals(4, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		//add an annotation to the resource model and verify the context model is updated
		AttributeOverrideAnnotation attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("FOO");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.BAR");
		getJpaProject().synchronizeContextModel();

		assertEquals(4, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());

		
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.city");
		getJpaProject().synchronizeContextModel();
		assertEquals(3, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
		
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("key.state.foo");
		attributeOverride = (AttributeOverrideAnnotation) resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		attributeOverride.setName("size");
		getJpaProject().synchronizeContextModel();
		assertEquals(2, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
	}

	public void testMapKeyValueAttributeOverrideSetVirtual() throws Exception {
		createTestEntityWithEmbeddableKeyManyToManyMapping();
		createTestEmbeddableAddress();
		createTestEmbeddableState();
		createTestEntityPropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");
				
		JavaCollectionMapping2_0 manyToManyMapping = (JavaCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		JavaAttributeOverrideContainer mapKeyAttributeOverrideContainer = manyToManyMapping.getMapKeyAttributeOverrideContainer();
		mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		Iterator<NestableAnnotation> attributeOverrides = resourceField.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		
		assertEquals("key.city", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("key.state.foo", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		attributeOverrides = resourceField.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("key.state.foo", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator().next().convertToVirtual();
		attributeOverrides = resourceField.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();
		assertFalse(attributeOverrides.hasNext());
		
		Iterator<JavaVirtualAttributeOverride> virtualAttributeOverrides = mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator();
		assertEquals("city", virtualAttributeOverrides.next().getName());
		assertEquals("state.foo", virtualAttributeOverrides.next().getName());
		assertEquals("state.address", virtualAttributeOverrides.next().getName());
		assertEquals("zip", virtualAttributeOverrides.next().getName());
		assertEquals(4, mapKeyAttributeOverrideContainer.getVirtualOverridesSize());
	}
	
	
	public void testMapKeyValueMoveSpecifiedAttributeOverride() throws Exception {
		createTestEntityWithEmbeddableKeyManyToManyMapping();
		createTestEmbeddableAddress();
		createTestEmbeddableState();
		createTestEntityPropertyInfo();
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".PropertyInfo");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		JavaCollectionMapping2_0 manyToManyMapping = (JavaCollectionMapping2_0) getJavaPersistentType().getAttributeNamed("parcels").getMapping();
		JavaAttributeOverrideContainer mapKeyAttributeOverrideContainer = manyToManyMapping.getMapKeyAttributeOverrideContainer();
		mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		mapKeyAttributeOverrideContainer.getVirtualOverrides().iterator().next().convertToSpecified();
		
		ListIterator<JavaSpecifiedAttributeOverride> specifiedOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("city", specifiedOverrides.next().getName());
		assertEquals("state.foo", specifiedOverrides.next().getName());
		assertFalse(specifiedOverrides.hasNext());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		
		resourceField.moveAnnotation(1, 0, AttributeOverrideAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		Iterator<NestableAnnotation> attributeOverrides = resourceField.getAnnotations(AttributeOverrideAnnotation.ANNOTATION_NAME).iterator();

		assertEquals("key.state.foo", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertEquals("key.city", ((AttributeOverrideAnnotation) attributeOverrides.next()).getName());
		assertFalse(attributeOverrides.hasNext());
		
		specifiedOverrides = mapKeyAttributeOverrideContainer.getSpecifiedOverrides().iterator();
		assertEquals("state.foo", specifiedOverrides.next().getName());
		assertEquals("city", specifiedOverrides.next().getName());
		assertFalse(specifiedOverrides.hasNext());
	}

	public void testSetSpecifiedMapKeyEnumerated() throws Exception {
		createTestEntityWithValidGenericMapManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaCollectionMapping2_0 manyToManyMapping = (JavaCollectionMapping2_0) persistentAttribute.getMapping();
		assertNull(manyToManyMapping.getMapKeyConverter().getConverterType());
		
		manyToManyMapping.setMapKeyConverter(BaseEnumeratedConverter.class);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		MapKeyEnumeratedAnnotation2_0 enumerated = (MapKeyEnumeratedAnnotation2_0) resourceField.getAnnotation(MapKeyEnumeratedAnnotation2_0.ANNOTATION_NAME);
		
		assertNotNull(enumerated);
		assertEquals(null, enumerated.getValue());
		
		((BaseEnumeratedConverter) manyToManyMapping.getMapKeyConverter()).setSpecifiedEnumType(EnumType.STRING);
		assertEquals(org.eclipse.jpt.jpa.core.resource.java.EnumType.STRING, enumerated.getValue());
		
		((BaseEnumeratedConverter) manyToManyMapping.getMapKeyConverter()).setSpecifiedEnumType(null);
		assertNotNull(resourceField.getAnnotation(MapKeyEnumeratedAnnotation2_0.ANNOTATION_NAME));
		assertNull(enumerated.getValue());
		
		manyToManyMapping.setMapKeyConverter(null);
		assertNull(resourceField.getAnnotation(MapKeyEnumeratedAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testGetSpecifiedMapKeyEnumeratedUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithValidGenericMapManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaCollectionMapping2_0 manyToManyMapping = (JavaCollectionMapping2_0) persistentAttribute.getMapping();

		assertNull(manyToManyMapping.getMapKeyConverter().getConverterType());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		MapKeyEnumeratedAnnotation2_0 enumerated = (MapKeyEnumeratedAnnotation2_0) resourceField.addAnnotation(MapKeyEnumeratedAnnotation2_0.ANNOTATION_NAME);
		enumerated.setValue(org.eclipse.jpt.jpa.core.resource.java.EnumType.STRING);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(EnumType.STRING, ((BaseEnumeratedConverter) manyToManyMapping.getMapKeyConverter()).getSpecifiedEnumType());
		
		enumerated.setValue(null);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(MapKeyEnumeratedAnnotation2_0.ANNOTATION_NAME));
		assertNull(((BaseEnumeratedConverter) manyToManyMapping.getMapKeyConverter()).getSpecifiedEnumType());
		assertFalse(manyToManyMapping.isDefault());
		assertSame(manyToManyMapping, persistentAttribute.getMapping());
	}

	public void testSetMapKeyTemporal() throws Exception {
		createTestEntityWithValidGenericMapManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaCollectionMapping2_0 manyToManyMapping = (JavaCollectionMapping2_0) persistentAttribute.getMapping();
		assertNull(manyToManyMapping.getMapKeyConverter().getConverterType());
		
		manyToManyMapping.setMapKeyConverter(BaseTemporalConverter.class);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		MapKeyTemporalAnnotation2_0 temporal = (MapKeyTemporalAnnotation2_0) resourceField.getAnnotation(MapKeyTemporalAnnotation2_0.ANNOTATION_NAME);
		
		assertNotNull(temporal);
		assertEquals(null, temporal.getValue());
		
		((BaseTemporalConverter) manyToManyMapping.getMapKeyConverter()).setTemporalType(TemporalType.TIME);
		assertEquals(org.eclipse.jpt.jpa.core.resource.java.TemporalType.TIME, temporal.getValue());
		
		((BaseTemporalConverter) manyToManyMapping.getMapKeyConverter()).setTemporalType(null);
		assertNull(resourceField.getAnnotation(MapKeyTemporalAnnotation2_0.ANNOTATION_NAME));
	}
	
	public void testGetMapKeyTemporalUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithValidGenericMapManyToManyMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaCollectionMapping2_0 manyToManyMapping = (JavaCollectionMapping2_0) persistentAttribute.getMapping();

		assertNull(manyToManyMapping.getMapKeyConverter().getConverterType());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		MapKeyTemporalAnnotation2_0 temporal = (MapKeyTemporalAnnotation2_0) resourceField.addAnnotation(MapKeyTemporalAnnotation2_0.ANNOTATION_NAME);
		temporal.setValue(org.eclipse.jpt.jpa.core.resource.java.TemporalType.TIME);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(TemporalType.TIME, ((BaseTemporalConverter) manyToManyMapping.getMapKeyConverter()).getTemporalType());
		
		temporal.setValue(null);
		getJpaProject().synchronizeContextModel();
		assertNotNull(resourceField.getAnnotation(MapKeyTemporalAnnotation2_0.ANNOTATION_NAME));
		assertNull(((BaseTemporalConverter) manyToManyMapping.getMapKeyConverter()).getTemporalType());
		assertFalse(manyToManyMapping.isDefault());
		assertSame(manyToManyMapping, persistentAttribute.getMapping());
	}
}

/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.java;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.jpa2.context.OneToManyMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.jpa2.resource.java.OneToMany2_0Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class GenericJavaOneToManyMapping2_0Tests
	extends Generic2_0ContextModelTestCase
{
	public GenericJavaOneToManyMapping2_0Tests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEntityWithValidOneToManyMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany").append(CR);				
				sb.append("    private Collection<Address> addresses;").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);				
			}
		});
	}
		
	private ICompilationUnit createTestEntityWithValidOneToManyMappingOrphanRemovalSpecified() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_MANY, JPA.ID, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @OneToMany(orphanRemoval=false)").append(CR);
				sb.append("    private Collection<Address> addresses;").append(CR);
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
	
	public void testCandidateMappedByAttributeNames() throws Exception {
		createTestEntityWithValidOneToManyMapping();
		createTestTargetEntityAddress();
		createTestEmbeddableState();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(PACKAGE_NAME + ".Address");
		addXmlClassRef(PACKAGE_NAME + ".State");
		
		PersistentAttribute persistentAttribute = (getJavaPersistentType()).attributes().next();
		OneToManyMapping oneToManyMapping = (OneToManyMapping) persistentAttribute.getMapping();

		Iterator<String> attributeNames = 
			oneToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());
		
		oneToManyMapping.setSpecifiedTargetEntity("foo");
		attributeNames = 
			oneToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertFalse(attributeNames.hasNext());
		
		oneToManyMapping.setSpecifiedTargetEntity(null);
		attributeNames = 
			oneToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().candidateMappedByAttributeNames();
		assertEquals("id", attributeNames.next());
		assertEquals("city", attributeNames.next());
		assertEquals("state", attributeNames.next());
		assertEquals("state.foo", attributeNames.next());
		assertEquals("state.address", attributeNames.next());
		assertEquals("zip", attributeNames.next());
		assertFalse(attributeNames.hasNext());

		AttributeMapping stateFooMapping = oneToManyMapping.getResolvedTargetEntity().resolveAttributeMapping("state.foo");
		assertEquals("foo", stateFooMapping.getName());
	}
	
	private JavaOrphanRemovable2_0 getOrphanRemovalOf(OneToManyMapping2_0 oneToManyMapping) {
		return ((JavaOrphanRemovalHolder2_0) oneToManyMapping).getOrphanRemoval();
	}

	public void testDefaultOneToManyGetDefaultOrphanRemoval() throws Exception {
		this.createTestEntityWithValidOneToManyMapping();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		assertEquals(false, this.getOrphanRemovalOf(oneToManyMapping).isDefaultOrphanRemoval());
	}
	
	public void testSpecifiedOneToManyGetDefaultOrphanRemoval() throws Exception {
		this.createTestEntityWithValidOneToManyMapping();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getSpecifiedMapping();
		assertEquals(false, this.getOrphanRemovalOf(oneToManyMapping).isDefaultOrphanRemoval());
	}
	
	public void testGetOrphanRemoval() throws Exception {
		this.createTestEntityWithValidOneToManyMapping();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getSpecifiedMapping();
		JavaOrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToManyMapping);

		assertEquals(false, mappingsOrphanRemoval.isOrphanRemoval());
		
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(Boolean.TRUE);
		assertEquals(true, mappingsOrphanRemoval.isOrphanRemoval());
	}
	
	public void testGetSpecifiedOrphanRemoval() throws Exception {
		this.createTestEntityWithValidOneToManyMapping();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getSpecifiedMapping();
		JavaOrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToManyMapping);

		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToMany2_0Annotation oneToMany = (OneToMany2_0Annotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		oneToMany.setOrphanRemoval(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(Boolean.FALSE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
	}
	
	public void testGetSpecifiedOrphanRemoval2() throws Exception {
		this.createTestEntityWithValidOneToManyMappingOrphanRemovalSpecified();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getSpecifiedMapping();
		JavaOrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToManyMapping);

		assertEquals(Boolean.FALSE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
	}

	public void testSetSpecifiedOrphanRemoval() throws Exception {
		this.createTestEntityWithValidOneToManyMapping();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		JavaOrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToManyMapping);
		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(Boolean.TRUE);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToMany2_0Annotation oneToMany = (OneToMany2_0Annotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		
		assertEquals(Boolean.TRUE, oneToMany.getOrphanRemoval());
		
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(null);
		assertNotNull(attributeResource.getAnnotation(JPA.ONE_TO_MANY)); 	// .getElement);
	}
	
	public void testSetSpecifiedOrphanRemoval2() throws Exception {
		this.createTestEntityWithValidOneToManyMapping();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		JavaOrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToManyMapping);
		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		
		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(Boolean.TRUE);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToMany2_0Annotation oneToMany = (OneToMany2_0Annotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		
		assertEquals(Boolean.TRUE, oneToMany.getOrphanRemoval());
		
		oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
		assertEquals(Boolean.TRUE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());

		mappingsOrphanRemoval.setSpecifiedOrphanRemoval(null);
		assertNotNull(attributeResource.getAnnotation(JPA.ONE_TO_MANY));
		
		oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getMapping();
	}

	public void testGetSpecifiedOrphanRemovalUpdatesFromResourceModelChange() throws Exception {
		this.createTestEntityWithValidOneToManyMapping();
		this.addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		OneToManyMapping2_0 oneToManyMapping = (OneToManyMapping2_0) persistentAttribute.getSpecifiedMapping();
		JavaOrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(oneToManyMapping);

		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		OneToMany2_0Annotation oneToMany = (OneToMany2_0Annotation) attributeResource.getAnnotation(JPA.ONE_TO_MANY);
		oneToMany.setOrphanRemoval(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();

		assertEquals(Boolean.FALSE, mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		
		oneToMany.setOrphanRemoval(null);
		getJpaProject().synchronizeContextModel();
		assertNull(mappingsOrphanRemoval.getSpecifiedOrphanRemoval());
		assertSame(oneToManyMapping, persistentAttribute.getSpecifiedMapping());
		
		oneToMany.setOrphanRemoval(Boolean.FALSE);
		attributeResource.setPrimaryAnnotation(null, EmptyIterable.<String>instance());
		getJpaProject().synchronizeContextModel();
		
		assertNull(persistentAttribute.getSpecifiedMapping());
	}
	
}

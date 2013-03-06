/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2011 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.relation;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.RelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.SpecifiedMappedByRelationshipStrategy;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.AbstractRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToManyBiDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToManyUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToOneBiDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToOneUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.OneToManyUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.OneToOneBiDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.OneToOneUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IEclipseFacade;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.JPACreateFactory;
import org.junit.Before;
import org.junit.Test;
@SuppressWarnings("nls")

public class CreateRelationsInFieldAnnotatedEntitiesTest {

	private IJPAEditorFeatureProvider featureProvider;
	final String TEST_PROJECT = "Test";
	private JpaProject jpaProject = null;
	private JPACreateFactory factory = null;
	IEclipseFacade eclipseFacade = null;
	
	@Before
	public void setUp() throws Exception {
		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPAProject(TEST_PROJECT + "_" + System.currentTimeMillis());
		assertNotNull(jpaProject);
		IFile entity = factory.createEntity(jpaProject, "org.eclipse.Entity1");
		Thread.sleep(2000);
		featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		expect(featureProvider.getBusinessObjectForPictogramElement(null)).andReturn(JPACreateFactory.getPersistentType(entity));
		expect(featureProvider.getCompilationUnit(isA(PersistentType.class))).andReturn(JavaCore.createCompilationUnitFrom(entity)).anyTimes();
		replay(featureProvider);
	}
	
	public ICompilationUnit createCompilationUnitFrom(IFile file) {
		return JavaCore.createCompilationUnitFrom(file);
	}
	

	@Test
	public void testCreateOneToOneUnidirRelation() throws Exception {
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createFieldAnnotatedEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		
		IFile addressFile = factory.createFieldAnnotatedEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Address");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(customerFile.exists());
		JavaResourceAbstractType customerType = jpaProject.getJavaResourceType("com.test.Customer");
		assertNotNull(customerType);
		
		
		PersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		}
		
		assertTrue(addressFile.exists());
		JavaResourceAbstractType addressType = jpaProject.getJavaResourceType("com.Address");
		assertNotNull(customerType);

		
		PersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getTypeBinding().getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getTypeBinding().getQualifiedName());
		}
		ICompilationUnit cu1 = createCompilationUnitFrom((IFile)t1.getResource());
		AbstractRelation rel = new OneToOneUniDirRelation(featureProvider, t1, t2, "address", true, false);
		assertNotNull(rel);
		assertSame(t1, rel.getOwner());
		assertSame(t2, rel.getInverse());
		assertEquals("address", rel.getOwnerAttributeName());
		PersistentAttribute ownerAt = t1.getAttributeNamed("address");
		assertNotNull(ownerAt);	
		
		AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(ownerAt);
		assertTrue(RelationshipMapping.class.isInstance(attributeMapping));
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, attributeMapping.getKey());
		RelationshipStrategy strategy = ((RelationshipMapping)attributeMapping).getRelationship().getStrategy();
		assertTrue(SpecifiedMappedByRelationshipStrategy.class.isInstance(strategy));
		assertNull(((SpecifiedMappedByRelationshipStrategy)strategy).getMappedByAttribute());
		
		assertTrue(cu1.isWorkingCopy());
		IType javaType = cu1.findPrimaryType();
		IField f = javaType.getField("address");
		assertTrue(f.exists());
				
	}
		
	@Test
	public void testCreateOneToOneBidirRelation() throws Exception {
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createFieldAnnotatedEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		
		IFile addressFile = factory.createFieldAnnotatedEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Address");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(customerFile.exists());
		JavaResourceAbstractType customerType = jpaProject.getJavaResourceType("com.test.Customer");
		assertNotNull(customerType);
		
		
		PersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		}
		
		assertTrue(addressFile.exists());
		JavaResourceAbstractType addressType = jpaProject.getJavaResourceType("com.Address");
		assertNotNull(customerType);

		
		PersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getTypeBinding().getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getTypeBinding().getQualifiedName());
		}
		ICompilationUnit cu1 = createCompilationUnitFrom((IFile)t1.getResource());
		ICompilationUnit cu2 = createCompilationUnitFrom((IFile)t2.getResource());
		AbstractRelation rel = new OneToOneBiDirRelation(featureProvider, t1, t2, "address", "customer", true, null, false);
		assertNotNull(rel);
		assertSame(t1, rel.getOwner());
		assertSame(t2, rel.getInverse());
		assertEquals("address", rel.getOwnerAttributeName());
		PersistentAttribute ownerAt = t1.getAttributeNamed("address");
		assertNotNull(ownerAt);

		AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(ownerAt);
		assertTrue(RelationshipMapping.class.isInstance(attributeMapping));
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, attributeMapping.getKey());
		RelationshipStrategy strategy = ((RelationshipMapping)attributeMapping).getRelationship().getStrategy();
		assertTrue(SpecifiedMappedByRelationshipStrategy.class.isInstance(strategy));
		assertNull(((SpecifiedMappedByRelationshipStrategy)strategy).getMappedByAttribute());
		
		PersistentAttribute inverseAt = t2.getAttributeNamed("customer");
		assertNotNull(inverseAt);
		
		attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(inverseAt);
		assertTrue(RelationshipMapping.class.isInstance(attributeMapping));
		assertEquals(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, attributeMapping.getKey());
		strategy = ((RelationshipMapping)attributeMapping).getRelationship().getStrategy();
		assertTrue(SpecifiedMappedByRelationshipStrategy.class.isInstance(strategy));
		assertEquals("address", ((SpecifiedMappedByRelationshipStrategy)strategy).getMappedByAttribute());

		assertTrue(cu1.isWorkingCopy());
		assertTrue(cu2.isWorkingCopy());
		
		IType javaType1 = cu1.findPrimaryType();
		IField f1 = javaType1.getField("address");
		assertTrue(f1.exists());
		IType javaType2 = cu2.findPrimaryType();
		IField f2 = javaType2.getField("customer");
		assertTrue(f2.exists());		
				
	}
	
	@Test
	public void testCreateOneToManyUnidirRelation() throws Exception {
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createFieldAnnotatedEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		
		IFile addressFile = factory.createFieldAnnotatedEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Address");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(customerFile.exists());
		JavaResourceAbstractType customerType = jpaProject.getJavaResourceType("com.test.Customer");
		assertNotNull(customerType);
		
		
		PersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		}
		
		assertTrue(addressFile.exists());
		JavaResourceAbstractType addressType = jpaProject.getJavaResourceType("com.Address");
		assertNotNull(customerType);

		
		PersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getTypeBinding().getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getTypeBinding().getQualifiedName());
		}
		ICompilationUnit cu1 = createCompilationUnitFrom((IFile)t1.getResource());
		AbstractRelation rel = new OneToManyUniDirRelation(featureProvider, t1, t2, "address", true);
		assertNotNull(rel);
		assertSame(t1, rel.getOwner());
		assertSame(t2, rel.getInverse());
		assertEquals("address", rel.getOwnerAttributeName());
		PersistentAttribute ownerAt = t1.getAttributeNamed("address");
		assertNotNull(ownerAt);

		AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(ownerAt);
		assertTrue(RelationshipMapping.class.isInstance(attributeMapping));
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, attributeMapping.getKey());
		RelationshipStrategy strategy = ((RelationshipMapping)attributeMapping).getRelationship().getStrategy();
		assertTrue(SpecifiedMappedByRelationshipStrategy.class.isInstance(strategy));
		assertNull(((SpecifiedMappedByRelationshipStrategy)strategy).getMappedByAttribute());
		
		assertTrue(cu1.isWorkingCopy());
		
		IType javaType1 = cu1.findPrimaryType();
		IField f1 = javaType1.getField("address");
		assertTrue(f1.exists());
	
	}
	
	@Test
	public void testCreateManyToOneUnidirRelation() throws Exception {
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createFieldAnnotatedEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		
		IFile addressFile = factory.createFieldAnnotatedEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Address");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(customerFile.exists());
		JavaResourceAbstractType customerType = jpaProject.getJavaResourceType("com.test.Customer");
		assertNotNull(customerType);
		
		
		PersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		}
		
		assertTrue(addressFile.exists());
		JavaResourceAbstractType addressType = jpaProject.getJavaResourceType("com.Address");
		assertNotNull(customerType);

		
		PersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getTypeBinding().getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getTypeBinding().getQualifiedName());
		}
		ICompilationUnit cu1 = createCompilationUnitFrom((IFile)t1.getResource());
		AbstractRelation rel = new ManyToOneUniDirRelation(featureProvider, t1, t2, "address", true, false);
		assertNotNull(rel);
		assertSame(t1, rel.getOwner());
		assertSame(t2, rel.getInverse());
		assertEquals("address", rel.getOwnerAttributeName());
		PersistentAttribute ownerAt = t1.getAttributeNamed("address");
		assertNotNull(ownerAt);
		
		AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(ownerAt);
		assertTrue(RelationshipMapping.class.isInstance(attributeMapping));
		assertEquals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, attributeMapping.getKey());
		RelationshipStrategy strategy = ((RelationshipMapping)attributeMapping).getRelationship().getStrategy();
		assertTrue(SpecifiedMappedByRelationshipStrategy.class.isInstance(strategy));
		assertNull(((SpecifiedMappedByRelationshipStrategy)strategy).getMappedByAttribute());
		
		assertTrue(cu1.isWorkingCopy());
		
		IType javaType1 = cu1.findPrimaryType();
		IField f1 = javaType1.getField("address");
		assertTrue(f1.exists());	
		
	}
	
	
	@Test
	public void testCreateManyToOneBidirRelation() throws Exception {
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createFieldAnnotatedEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		
		IFile addressFile = factory.createFieldAnnotatedEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Address");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(customerFile.exists());
		JavaResourceAbstractType customerType = jpaProject.getJavaResourceType("com.test.Customer");
		assertNotNull(customerType);
		
		
		PersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		}
		
		assertTrue(addressFile.exists());
		JavaResourceAbstractType addressType = jpaProject.getJavaResourceType("com.Address");
		assertNotNull(customerType);

		
		PersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getTypeBinding().getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getTypeBinding().getQualifiedName());
		}
		ICompilationUnit cu1 = createCompilationUnitFrom((IFile)t1.getResource());
		ICompilationUnit cu2 = createCompilationUnitFrom((IFile)t2.getResource());
		AbstractRelation rel = new ManyToOneBiDirRelation(featureProvider, t1, t2, "address", "customer", true, null, false);
		assertNotNull(rel);
		assertSame(t1, rel.getOwner());
		assertSame(t2, rel.getInverse());
		assertEquals("address", rel.getOwnerAttributeName());
		PersistentAttribute ownerAt = t1.getAttributeNamed("address");
		assertNotNull(ownerAt);

		AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(ownerAt);
		assertTrue(RelationshipMapping.class.isInstance(attributeMapping));
		assertEquals(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, attributeMapping.getKey());
		RelationshipStrategy strategy = ((RelationshipMapping)attributeMapping).getRelationship().getStrategy();
		assertTrue(SpecifiedMappedByRelationshipStrategy.class.isInstance(strategy));
		assertNull(((SpecifiedMappedByRelationshipStrategy)strategy).getMappedByAttribute());
		
		PersistentAttribute inverseAt = t2.getAttributeNamed("customer");
		assertNotNull(inverseAt);
		
		attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(inverseAt);
		assertTrue(RelationshipMapping.class.isInstance(attributeMapping));
		assertEquals(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, attributeMapping.getKey());
		strategy = ((RelationshipMapping)attributeMapping).getRelationship().getStrategy();
		assertTrue(SpecifiedMappedByRelationshipStrategy.class.isInstance(strategy));
		assertEquals("address", ((SpecifiedMappedByRelationshipStrategy)strategy).getMappedByAttribute());
		
		assertTrue(cu1.isWorkingCopy());
		assertTrue(cu2.isWorkingCopy());
		
		IType javaType1 = cu1.findPrimaryType();
		IField f1 = javaType1.getField("address");
		assertTrue(f1.exists());
		IType javaType2 = cu2.findPrimaryType();
		IField f2 = javaType2.getField("customer");
		assertTrue(f2.exists());	
				
	}
	
	@Test
	public void testCreateManyToManyUnidirRelation() throws Exception {
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createFieldAnnotatedEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		
		IFile addressFile = factory.createFieldAnnotatedEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Address");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(customerFile.exists());
		JavaResourceAbstractType customerType = jpaProject.getJavaResourceType("com.test.Customer");
		assertNotNull(customerType);
		
		
		PersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		}
		
		assertTrue(addressFile.exists());
		JavaResourceAbstractType addressType = jpaProject.getJavaResourceType("com.Address");
		assertNotNull(customerType);

		
		PersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getTypeBinding().getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getTypeBinding().getQualifiedName());
		}
		ICompilationUnit cu1 = createCompilationUnitFrom((IFile)t1.getResource());
		AbstractRelation rel = new ManyToManyUniDirRelation(featureProvider, t1, t2, "address", true);
		assertNotNull(rel);
		assertSame(t1, rel.getOwner());
		assertSame(t2, rel.getInverse());
		assertEquals("address", rel.getOwnerAttributeName());
		PersistentAttribute ownerAt = t1.getAttributeNamed("address");
		assertNotNull(ownerAt);
		
		AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(ownerAt);
		assertTrue(RelationshipMapping.class.isInstance(attributeMapping));
		assertEquals(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, attributeMapping.getKey());
		RelationshipStrategy strategy = ((RelationshipMapping)attributeMapping).getRelationship().getStrategy();
		assertTrue(SpecifiedMappedByRelationshipStrategy.class.isInstance(strategy));
		assertNull(((SpecifiedMappedByRelationshipStrategy)strategy).getMappedByAttribute());
		
		assertTrue(cu1.isWorkingCopy());
		
		IType javaType1 = cu1.findPrimaryType();
		IField f1 = javaType1.getField("address");
		assertTrue(f1.exists());		
		
	}
	
	@Test
	public void testCreateManyToManyBidirRelation() throws Exception {
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createFieldAnnotatedEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		
		IFile addressFile = factory.createFieldAnnotatedEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Address");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(customerFile.exists());
		JavaResourceAbstractType customerType = jpaProject.getJavaResourceType("com.test.Customer");
		assertNotNull(customerType);
		
		
		PersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		}
		
		assertTrue(addressFile.exists());
		JavaResourceAbstractType addressType = jpaProject.getJavaResourceType("com.Address");
		assertNotNull(customerType);

		
		PersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getTypeBinding().getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getTypeBinding().getQualifiedName());
		}
		ICompilationUnit cu1 = createCompilationUnitFrom((IFile)t1.getResource());
		ICompilationUnit cu2 = createCompilationUnitFrom((IFile)t2.getResource());
		AbstractRelation rel = new ManyToManyBiDirRelation(featureProvider, t1, t2, "address", "customer", true, null);
		assertNotNull(rel);
		assertSame(t1, rel.getOwner());
		assertSame(t2, rel.getInverse());
		assertEquals("address", rel.getOwnerAttributeName());
		PersistentAttribute ownerAt = t1.getAttributeNamed("address");
		assertNotNull(ownerAt);

		AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(ownerAt);
		assertTrue(RelationshipMapping.class.isInstance(attributeMapping));
		assertEquals(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, attributeMapping.getKey());
		RelationshipStrategy strategy = ((RelationshipMapping)attributeMapping).getRelationship().getStrategy();
		assertTrue(SpecifiedMappedByRelationshipStrategy.class.isInstance(strategy));
		assertNull(((SpecifiedMappedByRelationshipStrategy)strategy).getMappedByAttribute());
		
		PersistentAttribute inverseAt = t2.getAttributeNamed("customer");
		assertNotNull(inverseAt);
		
		
		attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(inverseAt);
		assertTrue(RelationshipMapping.class.isInstance(attributeMapping));
		assertEquals(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, attributeMapping.getKey());
		strategy = ((RelationshipMapping)attributeMapping).getRelationship().getStrategy();
		assertTrue(SpecifiedMappedByRelationshipStrategy.class.isInstance(strategy));
		assertEquals("address", ((SpecifiedMappedByRelationshipStrategy)strategy).getMappedByAttribute());

		assertTrue(cu1.isWorkingCopy());
		assertTrue(cu2.isWorkingCopy());
		
		IType javaType1 = cu1.findPrimaryType();
		IField f1 = javaType1.getField("address");
		assertTrue(f1.exists());
		IType javaType2 = cu2.findPrimaryType();
		IField f2 = javaType2.getField("customer");
		assertTrue(f2.exists());
		
	}
	
	public boolean isMappedAs(PersistentAttribute jpa, String mappingKey){
		AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(jpa);
		assertTrue(RelationshipMapping.class.isInstance(attributeMapping));
		assertEquals(mappingKey, attributeMapping.getKey());
		
		return false;
	}
}



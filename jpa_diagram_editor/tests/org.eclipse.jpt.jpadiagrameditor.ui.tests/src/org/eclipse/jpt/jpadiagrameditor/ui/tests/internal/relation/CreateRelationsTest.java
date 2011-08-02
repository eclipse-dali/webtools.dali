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
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Properties;

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ManyToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
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


@SuppressWarnings({ "unused" })
public class CreateRelationsTest {

	private IJPAEditorFeatureProvider featureProvider;
	final String TEST_PROJECT = "Test";
	private JpaProject jpaProject = null;
	private JPACreateFactory factory = null;
	IEclipseFacade eclipseFacade = null;
	private static final int MAX_NUM_OF_ITERATIONS = 250;
	JavaPersistentType t1 = null;
	ICompilationUnit cu1 = null;
	JavaPersistentType t2 = null;
	ICompilationUnit cu2 = null;
	private IJPAEditorFeatureProvider featureProvider20;
	private JpaProject jpa20Project;
	
	
	@Before
	public void setUp() throws Exception {
		
		JptJpaCorePlugin.getJpaProjectManager();
		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPAProject(TEST_PROJECT + "_" + System.currentTimeMillis());
		assertNotNull(jpaProject);
		
		IFile entity = factory.createEntity(jpaProject, "org.eclipse.Entity1");
		Thread.sleep(2000);
		featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		expect(featureProvider.getBusinessObjectForPictogramElement(null)).andReturn(getPersistentType(entity));
		expect(featureProvider.getCompilationUnit(isA(JavaPersistentType.class))).andReturn(JavaCore.createCompilationUnitFrom(entity)).anyTimes();
		
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		
		IFile addressFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Address");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(customerFile.exists());
		JavaResourceAbstractType customerType = jpaProject.getJavaResourceType("com.test.Customer");
		assertNotNull(customerType);
		
		
		t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		int c = 0;
		while ((t1 == null) && (c < MAX_NUM_OF_ITERATIONS)) {
			jpaProject.update(null);
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
			c++;
		}
		
		expect(featureProvider.getPictogramElementForBusinessObject(t1)).andStubReturn(isA(Shape.class));
		cu1 = JavaCore.createCompilationUnitFrom(customerFile);
		expect(featureProvider.getCompilationUnit(t1)).andStubReturn(cu1);		
		
		assertTrue(addressFile.exists());
		JavaResourceAbstractType addressType = jpaProject.getJavaResourceType("com.Address");
		assertNotNull(customerType);

		
		t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		c = 0;
		while ((t2 == null) && (c < MAX_NUM_OF_ITERATIONS)) {
			jpaProject.update(null);			
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
			c++;
		}
		
		expect(featureProvider.getPictogramElementForBusinessObject(t2)).andStubReturn(isA(Shape.class));
		cu2 = JavaCore.createCompilationUnitFrom(addressFile);
		expect(featureProvider.getCompilationUnit(t2)).andStubReturn(cu2);
		
		Properties props = new Properties();
		props.setProperty(JPADiagramPropertyPage.PROP_COLLECTION_TYPE.getLocalName(), "collection");
		expect(featureProvider.loadProperties(jpaProject.getProject())).andStubReturn(props);
		
		replay(featureProvider);	
		
		jpa20Project = factory.createJPA20Project(TEST_PROJECT + "_" + System.currentTimeMillis());
		assertNotNull(jpa20Project);

		IFile entity20 = factory.createEntity(jpa20Project,	"org.eclipse.Entity1");
		Thread.sleep(2000);
		featureProvider20 = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		expect(featureProvider20.getBusinessObjectForPictogramElement(null)).andReturn(getPersistentType(entity20));
		expect(featureProvider20.getCompilationUnit(isA(JavaPersistentType.class)))
				.andReturn(JavaCore.createCompilationUnitFrom(entity20)).anyTimes();
	}
	
	public static JavaPersistentType getPersistentType(IFile file){
		JpaFile jpaFile = JptJpaCorePlugin.getJpaFile(file);
		for (JpaStructureNode node: getRootNodes(jpaFile)) {
			JavaPersistentType entity = (JavaPersistentType) node;
			return entity;
		}
		return null;
	}	
	
	private static Iterable<JpaStructureNode> getRootNodes(JpaFile jpaFile) {
		if(jpaFile == null){
			return EmptyIterable.instance();
		}
		return jpaFile.getRootStructureNodes();
	}
	
	public ICompilationUnit createCompilationUnitFrom(IFile file) {
		return JavaCore.createCompilationUnitFrom(file);
	}
	
	@Test
	public void testCreateOneToOneUnidirRelation() throws Exception {
				
		AbstractRelation rel = new OneToOneUniDirRelation(featureProvider, t1, t2, "address",
								  					true, 
								  					cu1,
								  					cu2);
		assertNotNull(rel);
		assertSame(t1, rel.getOwner());
		assertSame(t2, rel.getInverse());
		assertEquals("address", rel.getOwnerAttributeName());
		JavaPersistentAttribute ownerAt = t1.getAttributeNamed("address");
		assertNotNull(ownerAt);
		OneToOneAnnotation an = (OneToOneAnnotation)ownerAt.getResourceAttribute().getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		assertNotNull(an);
		assertNull(an.getMappedBy());
	}
		
	
	@Test
	public void testCreateOneToOneBidirRelation() throws Exception {
		
		AbstractRelation rel = new OneToOneBiDirRelation(featureProvider, t1, t2, "address", "customer",
								  					true, 
								  					cu1,
								  					cu2);
		assertNotNull(rel);
		assertSame(t1, rel.getOwner());
		assertSame(t2, rel.getInverse());
		assertEquals("address", rel.getOwnerAttributeName());
		JavaPersistentAttribute ownerAt = t1.getAttributeNamed("address");
		assertNotNull(ownerAt);
		OneToOneAnnotation an = (OneToOneAnnotation)ownerAt.getResourceAttribute().getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		assertNotNull(an);
		assertNull(an.getMappedBy());
				
		JavaPersistentAttribute inverseAt = t2.getAttributeNamed("customer");
		assertNotNull(inverseAt);
		an = (OneToOneAnnotation)inverseAt.getResourceAttribute().getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
		assertNotNull(an);
		assertEquals("address", an.getMappedBy());
		
	}
	
	
	@Test
	public void testCreateOneToManyUnidirRelation() throws Exception {
		AbstractRelation rel = new OneToManyUniDirRelation(featureProvider, t1, t2, "address",
								  					true, 
								  					cu1,
								  					cu2);
		assertNotNull(rel);
		assertSame(t1, rel.getOwner());
		assertSame(t2, rel.getInverse());
		assertEquals("address", rel.getOwnerAttributeName());
		JavaPersistentAttribute ownerAt = t1.getAttributeNamed("address");
		assertNotNull(ownerAt);
		
		OneToManyAnnotation an = (OneToManyAnnotation)ownerAt.getResourceAttribute().getAnnotation(OneToManyAnnotation.ANNOTATION_NAME);
		assertNull(an.getMappedBy());		
	}
	
	
	
	@Test
	public void testCreateManyToOneUnidirRelation() throws Exception {
		AbstractRelation rel = new ManyToOneUniDirRelation(featureProvider, t1, t2, "address",
								  					true, 
								  					cu1,
								  					cu2);
		assertNotNull(rel);
		assertSame(t1, rel.getOwner());
		assertSame(t2, rel.getInverse());
		assertEquals("address", rel.getOwnerAttributeName());
		JavaPersistentAttribute ownerAt = t1.getAttributeNamed("address");
		assertNotNull(ownerAt);
		ManyToOneAnnotation an = (ManyToOneAnnotation)ownerAt.getResourceAttribute().getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME);
		assertNotNull(an);
	}
	
	@Test
	public void testCreateManyToOneBidirRelation() throws Exception {
		AbstractRelation rel = new ManyToOneBiDirRelation(featureProvider, t1, t2, "address", "customer",
								  					true, 
								  					cu1,
								  					cu2);
		assertNotNull(rel);
		assertSame(t1, rel.getOwner());
		assertSame(t2, rel.getInverse());
		assertEquals("address", rel.getOwnerAttributeName());
		JavaPersistentAttribute ownerAt = t1.getAttributeNamed("address");
		assertNotNull(ownerAt);
		ManyToOneAnnotation an = (ManyToOneAnnotation)ownerAt.getResourceAttribute().getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME);
		assertNotNull(an);
		
		assertEquals("customer", rel.getInverseAttributeName());
		JavaPersistentAttribute inverseAt = t2.getAttributeNamed("customer");
		assertNotNull(inverseAt);
		OneToManyAnnotation an1 = (OneToManyAnnotation)inverseAt.getResourceAttribute().getAnnotation(OneToManyAnnotation.ANNOTATION_NAME);
		assertNotNull(an1);
		assertEquals("address", an1.getMappedBy());
	}
	
	@Test
	public void testCreateManyToManyUnidirRelation() throws Exception {
		AbstractRelation rel = new ManyToManyUniDirRelation(featureProvider, t1, t2, "address",
								  					true, 
								  					cu1,
								  					cu2);
		assertNotNull(rel);
		assertSame(t1, rel.getOwner());
		assertSame(t2, rel.getInverse());
		assertEquals("address", rel.getOwnerAttributeName());
		JavaPersistentAttribute ownerAt = t1.getAttributeNamed("address");
		assertNotNull(ownerAt);
		ManyToManyAnnotation an = (ManyToManyAnnotation)ownerAt.getResourceAttribute().getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME);
		assertNotNull(an);
		assertNull(an.getMappedBy());
	}
	
	@Test
	public void testCreateManyToManyBidirRelation() throws Exception {
		AbstractRelation rel = new ManyToManyBiDirRelation(featureProvider, t1, t2, "address", "customer",
								  					true, 
								  					createCompilationUnitFrom((IFile)t1.getResource()),
								  					createCompilationUnitFrom((IFile)t2.getResource()));
		assertNotNull(rel);
		assertSame(t1, rel.getOwner());
		assertSame(t2, rel.getInverse());
		assertEquals("address", rel.getOwnerAttributeName());
		JavaPersistentAttribute ownerAt = t1.getAttributeNamed("address");
		assertNotNull(ownerAt);
		ManyToManyAnnotation an = (ManyToManyAnnotation)ownerAt.getResourceAttribute().getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME);
		assertNotNull(an);
		assertNull(an.getMappedBy());
				
		JavaPersistentAttribute inverseAt = t2.getAttributeNamed("customer");
		assertNotNull(inverseAt);
		an = (ManyToManyAnnotation)inverseAt.getResourceAttribute().getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME);
		assertNotNull(an);
		assertEquals("address", an.getMappedBy());
		
	}

	@Test
		public void testCreateOneToManyUnidirRelation20() throws Exception {
			
			assertNotNull(jpa20Project);	
			IFile customerFile = factory.createEntityInProject(jpa20Project.getProject(), new String[]{"com","test"}, "Customer");
			
			IFile addressFile = factory.createEntityInProject(jpa20Project.getProject(), new String[]{"com"}, "Address");
			
			jpa20Project.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
	
			assertTrue(customerFile.exists());
			JavaResourceAbstractType customerType = jpa20Project.getJavaResourceType("com.test.Customer");
			assertNotNull(customerType);
					
			JavaPersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpa20Project, customerType.getQualifiedName());
			int c = 0;
			while ((t1 == null) && (c < MAX_NUM_OF_ITERATIONS)) {
				Thread.sleep(200);
				jpa20Project.update(null);
				t1 = JpaArtifactFactory.instance().getContextPersistentType(jpa20Project, customerType.getQualifiedName());
				c++;
			}
					
			expect(featureProvider20.getPictogramElementForBusinessObject(t1)).andStubReturn(isA(Shape.class));
			ICompilationUnit cu1 = JavaCore.createCompilationUnitFrom(customerFile);
			expect(featureProvider20.getCompilationUnit(t1)).andStubReturn(cu1);
				
			assertTrue(addressFile.exists());
			JavaResourceAbstractType addressType = jpa20Project.getJavaResourceType("com.Address");
			assertNotNull(customerType);
	
			
			JavaPersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpa20Project, addressType.getQualifiedName());
			while ((t2 == null) && (c < MAX_NUM_OF_ITERATIONS)) {
				Thread.sleep(200);
				jpa20Project.update(null);
				t2 = JpaArtifactFactory.instance().getContextPersistentType(jpa20Project, addressType.getQualifiedName());
				c++;
			}
			expect(featureProvider20.getPictogramElementForBusinessObject(t2)).andStubReturn(isA(Shape.class));
			ICompilationUnit cu2 = JavaCore.createCompilationUnitFrom(addressFile);
			expect(featureProvider20.getCompilationUnit(t2)).andStubReturn(cu2);
			
			Properties props = new Properties();
			props.setProperty(JPADiagramPropertyPage.PROP_COLLECTION_TYPE.getLocalName(), "list");
			expect(featureProvider20.loadProperties(jpa20Project.getProject())).andReturn(props);
			replay(featureProvider20);
							
			AbstractRelation rel = new OneToManyUniDirRelation(featureProvider20, t1, t2, "address",
									  					true, 
									  					cu1,
									  					cu2);
			assertNotNull(rel);
			assertSame(t1, rel.getOwner());
			assertSame(t2, rel.getInverse());
			assertEquals("address", rel.getOwnerAttributeName());
			JavaPersistentAttribute ownerAt = t1.getAttributeNamed("address");
			assertNotNull(ownerAt);
			
			Object o1 = ownerAt.getResourceAttribute().getAnnotation(OneToManyAnnotation.ANNOTATION_NAME);
			assertNotNull(o1);
			
			Object o2 = ownerAt.getResourceAttribute().getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
			assertNotNull(o2);
			
			JoinColumnAnnotation joinColumn = (JoinColumnAnnotation) o2;
			assertNotNull(joinColumn.getName());
			
			assertNotNull(joinColumn.getReferencedColumnName());
			
			assertEquals("Customer_id", joinColumn.getName());
			assertEquals("id", joinColumn.getReferencedColumnName());
						
		}
		
		
		@Test
		public void testCreateOneToManyUnidirIDClassRelation20() throws Exception {
			
			assertNotNull(jpa20Project);	
			IFile simpleEmployeeFile = factory.createIdClassInProject(jpa20Project.getProject(), new String[]{"com","test"}, "Employee");
			IFile employeeFile = factory.createEntityWithCompositePKInProject(jpa20Project.getProject(), new String[]{"com","test"}, "Employee");
	
			IFile projectFile = factory.createEntityInProject(jpa20Project.getProject(), new String[]{"com"}, "Project");
			
			jpa20Project.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
			
			assertTrue(simpleEmployeeFile.exists());
			assertTrue(employeeFile.exists());
			JavaResourceAbstractType employeeType = jpa20Project.getJavaResourceType("com.test.Employee");
			assertNotNull(employeeType);
					
			JavaPersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpa20Project, employeeType.getQualifiedName());
			int c = 0;
			while ((t1 == null) && (c < MAX_NUM_OF_ITERATIONS)) {
				Thread.sleep(200);
				jpa20Project.update(null);
				t1 = JpaArtifactFactory.instance().getContextPersistentType(jpa20Project, employeeType.getQualifiedName());
				c++;
			}
					
			expect(featureProvider20.getPictogramElementForBusinessObject(t1)).andStubReturn(isA(Shape.class));
			ICompilationUnit cu1 = JavaCore.createCompilationUnitFrom(employeeFile);
			expect(featureProvider20.getCompilationUnit(t1)).andStubReturn(cu1);
				
			assertTrue(projectFile.exists());
			JavaResourceAbstractType projectType = jpa20Project.getJavaResourceType("com.Project");
			assertNotNull(employeeType);
	
			
			JavaPersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpa20Project, projectType.getQualifiedName());
			while ((t2 == null) && (c < MAX_NUM_OF_ITERATIONS)) {
				Thread.sleep(200);
				jpa20Project.update(null);
				t2 = JpaArtifactFactory.instance().getContextPersistentType(jpa20Project, projectType.getQualifiedName());
				c++;
			}
			expect(featureProvider20.getPictogramElementForBusinessObject(t2)).andStubReturn(isA(Shape.class));
			ICompilationUnit cu2 = JavaCore.createCompilationUnitFrom(projectFile);
			expect(featureProvider20.getCompilationUnit(t2)).andStubReturn(cu2);
			Properties props = new Properties();
			props.setProperty(JPADiagramPropertyPage.PROP_COLLECTION_TYPE.getLocalName(), "list");
			expect(featureProvider20.loadProperties(jpa20Project.getProject())).andReturn(props);
			replay(featureProvider20);
							
			AbstractRelation rel = new OneToManyUniDirRelation(featureProvider20, t1, t2, "project",
									  					true, 
									  					cu1,
									  					cu2);
			assertNotNull(rel);
			assertSame(t1, rel.getOwner());
			assertSame(t2, rel.getInverse());
			assertEquals("project", rel.getOwnerAttributeName());
			JavaPersistentAttribute ownerAt = t1.getAttributeNamed("project");
			assertNotNull(ownerAt);
			
			Object o1 = ownerAt.getResourceAttribute().getAnnotation(OneToManyAnnotation.ANNOTATION_NAME);
			assertNotNull(o1);
			
			Object o2 = ownerAt.getResourceAttribute().getAnnotation(JPA.JOIN_COLUMNS);
			assertNotNull(o2);
			
			assertEquals(2, ownerAt.getResourceAttribute().getAnnotationsSize(JPA.JOIN_COLUMN));			
						
		}
		
		@Test
		public void testCreateOneToManyUnidirEmbeddedRelation20() throws Exception {
			
			assertNotNull(jpa20Project);	
			IFile simpleEmployeeFile = factory.createEmbeddedClassInProject(jpa20Project.getProject(), new String[]{"com","test"}, "EmployeerId");
			IFile employeeFile = factory.createEntityWithEmbeddedPKInProject(jpa20Project.getProject(), new String[]{"com","test"}, "Employeer");
	
			IFile projectFile = factory.createEntityInProject(jpa20Project.getProject(), new String[]{"com"}, "Person");
			
			jpa20Project.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
			
			assertTrue(simpleEmployeeFile.exists());
			assertTrue(employeeFile.exists());
			JavaResourceAbstractType employeeType = jpa20Project.getJavaResourceType("com.test.Employeer");
			assertNotNull(employeeType);
			
					
			JavaPersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpa20Project, employeeType.getQualifiedName());
			int c = 0;
			while ((t1 == null) && (c < MAX_NUM_OF_ITERATIONS)) {
				Thread.sleep(200);
				jpa20Project.update(null);
				t1 = JpaArtifactFactory.instance().getContextPersistentType(jpa20Project, employeeType.getQualifiedName());
				c++;
			}
					
			expect(featureProvider20.getPictogramElementForBusinessObject(t1)).andStubReturn(isA(Shape.class));
			ICompilationUnit cu1 = JavaCore.createCompilationUnitFrom(employeeFile);
			expect(featureProvider20.getCompilationUnit(t1)).andStubReturn(cu1);
			
			
			Embeddable emb = JpaArtifactFactory.instance().getPersistenceUnit(t1).getEmbeddable("com.test.EmployeerId");
			Iterator<AttributeMapping> embIt = emb.getAllAttributeMappings().iterator();
			int c1 = 0;
			while ((embIt.hasNext() == false) && (c1 < MAX_NUM_OF_ITERATIONS)) {
				Thread.sleep(200);
				jpa20Project.update(null);
				emb.update();
				embIt = emb.getAllAttributeMappings().iterator();
				c1++;
			}
							
			assertTrue(projectFile.exists());
			JavaResourceAbstractType projectType = jpa20Project.getJavaResourceType("com.Person");
			assertNotNull(employeeType);
	
			
			JavaPersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpa20Project, projectType.getQualifiedName());
			while ((t2 == null) && (c < MAX_NUM_OF_ITERATIONS)) {
				Thread.sleep(200);
				jpa20Project.update(null);
				t2 = JpaArtifactFactory.instance().getContextPersistentType(jpa20Project, projectType.getQualifiedName());
				c++;
			}
			
			expect(featureProvider20.getPictogramElementForBusinessObject(t2)).andStubReturn(isA(Shape.class));
			ICompilationUnit cu2 = JavaCore.createCompilationUnitFrom(projectFile);
			expect(featureProvider20.getCompilationUnit(t2)).andStubReturn(cu2);
			Properties props = new Properties();
			props.setProperty(JPADiagramPropertyPage.PROP_COLLECTION_TYPE.getLocalName(), "list");
			expect(featureProvider20.loadProperties(jpa20Project.getProject())).andReturn(props);
			replay(featureProvider20);
										
			AbstractRelation rel = new OneToManyUniDirRelation(featureProvider20, t1, t2, "person",
									  					true, 
									  					cu1,
									  					cu2);
			assertNotNull(rel);
			assertSame(t1, rel.getOwner());
			assertSame(t2, rel.getInverse());
			assertEquals("person", rel.getOwnerAttributeName());
		    JavaPersistentAttribute ownerAt = t1.getAttributeNamed("person");
			assertNotNull(ownerAt);
						
			Object o1 = ownerAt.getResourceAttribute().getAnnotation(OneToManyAnnotation.ANNOTATION_NAME);
			assertNotNull(o1);
			
			Object o2 = ownerAt.getResourceAttribute().getAnnotation(JPA.JOIN_COLUMNS);
			assertNotNull(o2);
			
			assertEquals(1,  ownerAt.getResourceAttribute().getAnnotationsSize(JPA.JOIN_COLUMN));
			Iterable<JoinColumnAnnotation> nestedAnnotations = new SubIterableWrapper<NestableAnnotation, JoinColumnAnnotation>(ownerAt.getResourceAttribute().getAnnotations(JPA.JOIN_COLUMN));
			Iterator<JoinColumnAnnotation> nestedIterator = nestedAnnotations.iterator();
			while(nestedIterator.hasNext()){
				JoinColumnAnnotation joinColumn = nestedIterator.next();
				assertEquals("Employeer_firstName",joinColumn.getName());
				assertEquals("firstName",joinColumn.getReferencedColumnName());
			}
						
		}
	
}

/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IEclipseFacade;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.JPACreateFactory;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("restriction")
public class CreateRelationAttributesTest {
	
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
		expect(featureProvider.getBusinessObjectForPictogramElement(null)).andReturn(getPersistentType(entity));
		expect(featureProvider.getCompilationUnit(isA(JavaPersistentType.class))).andReturn(JavaCore.createCompilationUnitFrom(entity)).anyTimes();
		replay(featureProvider);
	}
		
	public static PersistentType getPersistentType(IFile file){
		JpaFile jpaFile = JptJpaCorePlugin.getJpaFile(file);
		for (JpaStructureNode node : getRootNodes(jpaFile)) {
			PersistentType entity = (PersistentType) node;
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
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);
		if (!cu.isWorkingCopy())
			try {
				cu.becomeWorkingCopy(new NullProgressMonitor());
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		return cu;
	}
	
	@Test
	public void testAddAnnotation() throws Exception {
		assertNotNull(jpaProject);	
		//jpaProject.setUpdater(new SynchronousJpaProjectUpdater(jpaProject));
		IFile customerFile = JPACreateFactory.instance().createEntityInProject(jpaProject.getProject(), new String[]{"abc"}, "Customer");	
		IFile addressFile = JPACreateFactory.instance().createEntityInProject(jpaProject.getProject(), new String[]{"abc"}, "Address");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(customerFile.exists());
		JavaResourcePersistentType customerType = jpaProject.getJavaResourcePersistentType("abc.Customer");
		assertNotNull(customerType);
		
		
		PersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		}
		
		assertTrue(addressFile.exists());
		JavaResourcePersistentType addressType = jpaProject.getJavaResourcePersistentType("abc.Address");
		assertNotNull(customerType);

		
		PersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		}
 
		JavaPersistentAttribute attr = JpaArtifactFactory.instance().
				addAttribute(featureProvider, (JavaPersistentType)t1, (JavaPersistentType)t2, "add", "add", false, 
						createCompilationUnitFrom(customerFile),
						createCompilationUnitFrom(addressFile));
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
		PersistenceUnit pu = jpaProject.getRootContextNode().getPersistenceXml().getPersistence().persistenceUnits().next();
		t1 = pu.getPersistentType("abc.Customer");
		assertNotNull(attr);			

	}
	
	
	@Test
	public void testAddOneToOneUnidirectionalRelationAttributes() throws Exception {
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		
		IFile addressFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Address");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(customerFile.exists());
		JavaResourcePersistentType customerType = jpaProject.getJavaResourcePersistentType("com.test.Customer");
		assertNotNull(customerType);
		
		
		PersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		}
		
		assertTrue(addressFile.exists());
		JavaResourcePersistentType addressType = jpaProject.getJavaResourcePersistentType("com.Address");
		assertNotNull(customerType);

		
		PersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		}
 
		JavaPersistentAttribute attr = JpaArtifactFactory.instance().
			addAttribute(featureProvider, (JavaPersistentType)t1, (JavaPersistentType)t2, "address", "address", false, 						
					createCompilationUnitFrom((IFile)t1.getResource()),
					createCompilationUnitFrom((IFile)t2.getResource()));		
		assertNotNull(attr);
			
		JpaArtifactFactory.instance().addOneToOneUnidirectionalRelation(featureProvider, (JavaPersistentType)t1, attr);
		
		PersistentType pt = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		while (pt == null) {
			Thread.sleep(200);
			pt = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		}
		
		ReadOnlyPersistentAttribute cPersistentAttribute = pt.resolveAttribute("address");	
		assertNotNull(cPersistentAttribute);

	}	
		
	
	@Test
	public void testAddOneToOneBidirectionalRelationAttributes() throws Exception {
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Customer");
		
		IFile creditCardFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com"}, "CreditCard");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(customerFile.exists());
		JavaResourcePersistentType customerType = jpaProject.getJavaResourcePersistentType("com.Customer");
		assertNotNull(customerType);
		
		
		PersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		}
		
		assertTrue(creditCardFile.exists());
		JavaResourcePersistentType creditCardType = jpaProject.getJavaResourcePersistentType("com.CreditCard");
		assertNotNull(creditCardFile);

		
		PersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, creditCardType.getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, creditCardType.getQualifiedName());
		}
 
		JavaPersistentAttribute attr = JpaArtifactFactory.instance().
			addAttribute( featureProvider, (JavaPersistentType)t1, (JavaPersistentType)t2, "creditCard", "creditCard", false,
					createCompilationUnitFrom((IFile)t1.getResource()),
					createCompilationUnitFrom((IFile)t2.getResource()));		
		assertNotNull(attr);

		JavaPersistentAttribute attr2 = JpaArtifactFactory.instance().
			addAttribute(featureProvider, (JavaPersistentType)t2, (JavaPersistentType)t1, "customer", "customer", false, 
					createCompilationUnitFrom((IFile)t2.getResource()),
					createCompilationUnitFrom((IFile)t1.getResource()));		
		assertNotNull(attr2);
		
		JpaArtifactFactory.instance().addOneToOneBidirectionalRelation(featureProvider, (JavaPersistentType)t1, attr, (JavaPersistentType)t2, attr2);
		
		PersistentType ownerPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		while (ownerPersistentType == null) {
			Thread.sleep(200);
			ownerPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		}
		
		ReadOnlyPersistentAttribute ownerPersistentAttribute = ownerPersistentType.resolveAttribute("creditCard");
		assertNotNull(ownerPersistentAttribute);
		assertTrue(ownerPersistentAttribute instanceof JavaPersistentAttribute);
		
		AttributeMapping ownerSideMapping = ownerPersistentAttribute.getMapping();
		assertTrue(ownerSideMapping instanceof JavaOneToOneMapping); 
		
		assertTrue(((JavaOneToOneMapping)ownerSideMapping).isRelationshipOwner());
		
		PersistentType inversePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, creditCardType.getQualifiedName());
		while (inversePersistentType == null) {
			Thread.sleep(200);
			inversePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, creditCardType.getQualifiedName());
		}
		
		ReadOnlyPersistentAttribute inversePersistentAttribute = inversePersistentType.resolveAttribute("customer");
		assertNotNull(inversePersistentAttribute);
		assertTrue(inversePersistentAttribute instanceof JavaPersistentAttribute);
		
		AttributeMapping inverseSideMapping = inversePersistentAttribute.getMapping();
		assertTrue(inverseSideMapping instanceof JavaOneToOneMapping); 
		
		assertFalse(((JavaOneToOneMapping)inverseSideMapping).isRelationshipOwner());
	}	
	
	
	@Test
	public void testAddOneToManyUnidirectionalRelationAttributes() throws Exception {
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Customer");
		
		IFile phoneFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Phone");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(customerFile.exists());
		JavaResourcePersistentType customerType = jpaProject.getJavaResourcePersistentType("com.Customer");
		assertNotNull(customerType);
		
		
		PersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		}
		
		assertTrue(phoneFile.exists());
		JavaResourcePersistentType phoneType = jpaProject.getJavaResourcePersistentType("com.Phone");
		assertNotNull(customerType);

		
		PersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, phoneType.getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, phoneType.getQualifiedName());
		}
 
		JavaPersistentAttribute attr = JpaArtifactFactory.instance().addAttribute(featureProvider, (JavaPersistentType)t1, (JavaPersistentType)t2, "phones", "phones", true, 
				createCompilationUnitFrom((IFile)t1.getResource()),
				createCompilationUnitFrom((IFile)t2.getResource()));		
		assertNotNull(attr);
			
		JpaArtifactFactory.instance().addOneToManyUnidirectionalRelation(featureProvider, (JavaPersistentType)t1, attr);
		
		PersistentType pt = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		while (pt == null) {
			Thread.sleep(200);
			pt = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, phoneType.getQualifiedName());
		}
		
		ReadOnlyPersistentAttribute cPersistentAttribute = pt.resolveAttribute("phones");		
		assertNotNull(cPersistentAttribute);
		
		AttributeMapping mapping = cPersistentAttribute.getMapping();
		assertNotNull(mapping);
		assertTrue(mapping instanceof JavaOneToManyMapping); 
		
		assertTrue(((JavaOneToManyMapping)mapping).isRelationshipOwner());
	}	
	
	
	@Test
	public void testAddOneToManyBidirectionalRelationAttributes() throws Exception {
		assertNotNull(jpaProject);	
		IFile cruiseFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Cruise");		
		IFile reservationFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Reservation");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(cruiseFile.exists());
		JavaResourcePersistentType cruiseType = jpaProject.getJavaResourcePersistentType("com.Cruise");
		assertNotNull(cruiseType);
		
		
		PersistentType singleSidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cruiseType.getQualifiedName());
		while (singleSidePersistentType == null) {
			Thread.sleep(200);
			singleSidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cruiseType.getQualifiedName());
		}
		
		assertTrue(reservationFile.exists());
		JavaResourcePersistentType reservationType= jpaProject.getJavaResourcePersistentType("com.Reservation");
		assertNotNull(reservationFile);

		
		PersistentType manySidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getQualifiedName());
		while (manySidePersistentType == null) {
			Thread.sleep(200);
			manySidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getQualifiedName());
		}
 
		JavaPersistentAttribute singleSideAttribute = JpaArtifactFactory.instance().
			addAttribute(featureProvider, (JavaPersistentType)singleSidePersistentType, (JavaPersistentType)manySidePersistentType, "reservations", "reservations", true, 
				createCompilationUnitFrom((IFile)singleSidePersistentType.getResource()),
				createCompilationUnitFrom((IFile)manySidePersistentType.getResource()));		
		assertNotNull(singleSideAttribute);

		JavaPersistentAttribute manySideAttribute = JpaArtifactFactory.instance().
			addAttribute(featureProvider, (JavaPersistentType)manySidePersistentType, (JavaPersistentType)singleSidePersistentType, "cruise", "cruise", false, 
				createCompilationUnitFrom((IFile)manySidePersistentType.getResource()),
				createCompilationUnitFrom((IFile)singleSidePersistentType.getResource()));		
		assertNotNull(manySideAttribute);
		
		JpaArtifactFactory.instance().addOneToManyBidirectionalRelation(featureProvider, (JavaPersistentType)singleSidePersistentType, singleSideAttribute, (JavaPersistentType)manySidePersistentType, manySideAttribute);
		
		// In one-to-many bidirectional relation many side is ALWAYS owner
		PersistentType inversePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cruiseType.getQualifiedName());
		while (inversePersistentType == null) {
			Thread.sleep(200);
			inversePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cruiseType.getQualifiedName());
		}
		
		ReadOnlyPersistentAttribute inversePersistentAttribute = inversePersistentType.resolveAttribute("reservations");
		assertNotNull(inversePersistentAttribute);
		assertTrue(inversePersistentAttribute instanceof JavaPersistentAttribute);
		
		AttributeMapping inverseSideMapping = inversePersistentAttribute.getMapping();
		assertTrue(inverseSideMapping instanceof JavaOneToManyMapping); 
		
		assertFalse(((JavaOneToManyMapping)inverseSideMapping).isRelationshipOwner());
		
		// In one-to-many bidirectional relation many side is ALWAYS owner
		PersistentType ownerPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getQualifiedName());
		int c = 0;
		while ((ownerPersistentType == null) && (c < 50)) {
			Thread.sleep(200);
			ownerPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getQualifiedName());
			c++;
		}
		assertNotNull(ownerPersistentType);
		
		ReadOnlyPersistentAttribute ownerPersistentAttribute = ownerPersistentType.resolveAttribute("cruise");
		c = 0;
		while ((ownerPersistentAttribute == null) && (c < 50)) {
			Thread.sleep(200);
			ownerPersistentAttribute = ownerPersistentType.resolveAttribute("cruise");
			c++;
		}		
		
		assertNotNull(ownerPersistentAttribute);
		assertTrue(ownerPersistentAttribute instanceof JavaPersistentAttribute);
		
		AttributeMapping ownerSideMapping = ownerPersistentAttribute.getMapping();
		c = 0;
		while ((ownerSideMapping == null) && (c < 50)) {
			Thread.sleep(200);
			ownerSideMapping = ownerPersistentAttribute.getMapping();
			c++;
		}	
		if (ownerSideMapping == null)
			return;
		assertNotNull("ownerSideMapping must not be null", ownerSideMapping);
		assertTrue("ownerSideMapping class is " + ownerSideMapping.getClass().getName(), JavaManyToOneMapping.class.isInstance(ownerSideMapping));
		
		assertTrue(((JavaManyToOneMapping)ownerSideMapping).isRelationshipOwner());
	}	

	
	@Test
	public void testAddManyToOneUnidirectionalRelationAttributes() throws Exception {
		assertNotNull(jpaProject);	
		IFile cruiseFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Cruise");
		IFile shipFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Ship");
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(cruiseFile.exists());
		JavaResourcePersistentType cruiseType = jpaProject.getJavaResourcePersistentType("com.Cruise");
		assertNotNull(cruiseType);
			
		PersistentType manySidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cruiseType.getQualifiedName());
		while (manySidePersistentType == null) {
			Thread.sleep(200);
			manySidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cruiseType.getQualifiedName());
		}
		
		assertTrue(shipFile.exists());
		JavaResourcePersistentType shipType = jpaProject.getJavaResourcePersistentType("com.Ship");
		assertNotNull(cruiseType);

		
		PersistentType singleSidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, shipType.getQualifiedName());
		while (singleSidePersistentType == null) {
			Thread.sleep(200);
			singleSidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, shipType.getQualifiedName());
		}
 
		JavaPersistentAttribute mappedAttribute = JpaArtifactFactory.instance().
			addAttribute(featureProvider, (JavaPersistentType)manySidePersistentType, (JavaPersistentType)singleSidePersistentType, "ship", "ship", true, 
					createCompilationUnitFrom((IFile)manySidePersistentType.getResource()),
					createCompilationUnitFrom((IFile)singleSidePersistentType.getResource()));		
		assertNotNull(mappedAttribute);
			
		JpaArtifactFactory.instance().addManyToOneUnidirectionalRelation(featureProvider, (JavaPersistentType)manySidePersistentType, mappedAttribute);
		
		PersistentType pt = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cruiseType.getQualifiedName());
		while (pt == null) {
			Thread.sleep(200);
			pt = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, shipType.getQualifiedName());
		}
		
		ReadOnlyPersistentAttribute cPersistentAttribute = pt.resolveAttribute("ship");	
		int cnt = 0;
		while ((cPersistentAttribute == null) && (cnt < 100)) {
			Thread.sleep(250);
			cPersistentAttribute = pt.resolveAttribute("ship");	
			cnt++;
		}		
		assertNotNull(cPersistentAttribute);
		
		AttributeMapping mapping = cPersistentAttribute.getMapping();
		cnt = 0;
		while ((mapping == null) && (cnt < 100)) {
			Thread.sleep(250);
			mapping = cPersistentAttribute.getMapping();
			cnt++;
		}
		assertNotNull(mapping);
		assertTrue(mapping instanceof JavaManyToOneMapping); 
		
		assertTrue(((JavaManyToOneMapping)mapping).isRelationshipOwner());
	}	
	
	
	@Test
	public void testAddManyToManyBidirectionalRelationAttributes() throws Exception {
		assertNotNull(jpaProject);	
		IFile reservationFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Reservation");
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Customer");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(reservationFile.exists());
		JavaResourcePersistentType reservationType = jpaProject.getJavaResourcePersistentType("com.Reservation");
		assertNotNull(reservationType);
		
		PersistentType ownerSidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getQualifiedName());
		while (ownerSidePersistentType == null) {
			Thread.sleep(200);
			ownerSidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getQualifiedName());
		}
		
		assertTrue(customerFile.exists());
		JavaResourcePersistentType customerType = jpaProject.getJavaResourcePersistentType("com.Customer");
		assertNotNull(customerFile);

		
		PersistentType inverseSidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		while (inverseSidePersistentType == null) {
			Thread.sleep(200);
			inverseSidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		}
 
		JavaPersistentAttribute ownerSideAttribute = JpaArtifactFactory.instance().
			addAttribute(featureProvider, (JavaPersistentType)ownerSidePersistentType, (JavaPersistentType)inverseSidePersistentType, "customers", "customers", true,
					createCompilationUnitFrom((IFile)ownerSidePersistentType.getResource()),
					createCompilationUnitFrom((IFile)inverseSidePersistentType.getResource()));		
		assertNotNull(ownerSideAttribute);

		JavaPersistentAttribute inverseSideAttributes = JpaArtifactFactory.instance().addAttribute(featureProvider, (JavaPersistentType)inverseSidePersistentType, (JavaPersistentType)ownerSidePersistentType, "reservations", "reservations", true, 
				createCompilationUnitFrom((IFile)inverseSidePersistentType.getResource()),
				createCompilationUnitFrom((IFile)ownerSidePersistentType.getResource()));		
		assertNotNull(inverseSideAttributes);
		
		JpaArtifactFactory.instance().addManyToManyBidirectionalRelation(featureProvider, (JavaPersistentType)ownerSidePersistentType, ownerSideAttribute, (JavaPersistentType)inverseSidePersistentType, inverseSideAttributes);
		
		PersistentType ownerPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getQualifiedName());
		while (ownerPersistentType == null) {
			Thread.sleep(200);
			ownerPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getQualifiedName());
		}
		
		ReadOnlyPersistentAttribute ownerPersistentAttribute = ownerPersistentType.resolveAttribute("customers");
		assertNotNull(ownerPersistentAttribute);
		assertTrue(ownerPersistentAttribute instanceof JavaPersistentAttribute);
		
		AttributeMapping ownerSideMapping = ownerPersistentAttribute.getMapping();
		assertTrue(ownerSideMapping instanceof JavaManyToManyMapping); 
		
		assertTrue(((JavaManyToManyMapping)ownerSideMapping).isRelationshipOwner());
	
		PersistentType inversePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		while (inversePersistentType == null) {
			Thread.sleep(200);
			inversePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		}
		
		ReadOnlyPersistentAttribute inversePersistentAttribute = inversePersistentType.resolveAttribute("reservations");
		assertNotNull(inversePersistentAttribute);
		assertTrue(inversePersistentAttribute instanceof JavaPersistentAttribute);
		
		AttributeMapping inverseSideMapping = inversePersistentAttribute.getMapping();
		assertTrue(inverseSideMapping instanceof JavaManyToManyMapping); 
		
		assertFalse(((JavaManyToManyMapping)inverseSideMapping).isRelationshipOwner());
	}	
	
	@Test
	public void testAddManyToManyUnidirectionalRelationAttributes() throws Exception {
		assertNotNull(jpaProject);	
		IFile reservationFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Reservation");
		IFile cabinFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Cabin");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(reservationFile.exists());
		JavaResourcePersistentType reservationType = jpaProject.getJavaResourcePersistentType("com.Reservation");
		assertNotNull(reservationType);
		
		PersistentType annotatedPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getQualifiedName());
		while (annotatedPersistentType == null) {
			Thread.sleep(200);
			annotatedPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getQualifiedName());
		}
		
		assertTrue(cabinFile.exists());
		JavaResourcePersistentType cabinType = jpaProject.getJavaResourcePersistentType("com.Cabin");
		assertNotNull(cabinFile);

		
		PersistentType referencedPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cabinType.getQualifiedName());
		while (referencedPersistentType == null) {
			Thread.sleep(200);
			referencedPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cabinType.getQualifiedName());
		}
 
		JavaPersistentAttribute annotatedSideAttribute = JpaArtifactFactory.instance().
			addAttribute(featureProvider, (JavaPersistentType)annotatedPersistentType, (JavaPersistentType)referencedPersistentType, "cabins", "cabins", true,
					createCompilationUnitFrom((IFile)annotatedPersistentType.getResource()),
					createCompilationUnitFrom((IFile)referencedPersistentType.getResource()));		
		assertNotNull(annotatedSideAttribute);
		
		JpaArtifactFactory.instance().addManyToManyUnidirectionalRelation(featureProvider, (JavaPersistentType)annotatedPersistentType, annotatedSideAttribute);
		
		PersistentType persistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getQualifiedName());
		while (persistentType == null) {
			Thread.sleep(200);
			persistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getQualifiedName());
		}
		
		ReadOnlyPersistentAttribute persistentAttribute = persistentType.resolveAttribute("cabins");
		assertNotNull(persistentAttribute);
		assertTrue(persistentAttribute instanceof JavaPersistentAttribute);
		
		AttributeMapping mapping = persistentAttribute.getMapping();
		assertTrue(mapping instanceof JavaManyToManyMapping); 
		
		assertTrue(((JavaManyToManyMapping)mapping).isRelationshipOwner());
		}	
	

	
	
}

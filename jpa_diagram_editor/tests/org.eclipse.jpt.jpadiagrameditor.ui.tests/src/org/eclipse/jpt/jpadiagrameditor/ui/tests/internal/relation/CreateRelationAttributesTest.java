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

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IEclipseFacade;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.JPACreateFactory;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("nls")
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
		expect(featureProvider.getBusinessObjectForPictogramElement(null)).andReturn(JPACreateFactory.getPersistentType(entity));
		expect(featureProvider.getCompilationUnit(isA(JavaPersistentType.class))).andReturn(JavaCore.createCompilationUnitFrom(entity)).anyTimes();
		replay(featureProvider);
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
		JavaResourceAbstractType customerType = jpaProject.getJavaResourceType("abc.Customer");
		assertNotNull(customerType);
		
		
		PersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		}
		
		assertTrue(addressFile.exists());
		JavaResourceAbstractType addressType = jpaProject.getJavaResourceType("abc.Address");
		assertNotNull(customerType);

		
		PersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getTypeBinding().getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getTypeBinding().getQualifiedName());
		}
 
		JavaSpecifiedPersistentAttribute attr = JpaArtifactFactory.instance().
				addAttribute(featureProvider, (JavaPersistentType)t1, (JavaPersistentType)t2, null, "add", "add", false, 
						createCompilationUnitFrom(customerFile));
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
		PersistenceUnit pu = jpaProject.getContextModelRoot().getPersistenceXml().getRoot().getPersistenceUnits().iterator().next();
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
 
		JavaSpecifiedPersistentAttribute attr = JpaArtifactFactory.instance().
			addAttribute(featureProvider, (JavaPersistentType)t1, (JavaPersistentType)t2, null, "address", "address", false, 						
					createCompilationUnitFrom((IFile)t1.getResource()));		
		assertNotNull(attr);
			
		JpaArtifactFactory.instance().addOneToOneUnidirectionalRelation(featureProvider, (JavaPersistentType)t1, attr);
		
		PersistentType pt = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		while (pt == null) {
			Thread.sleep(200);
			pt = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getTypeBinding().getQualifiedName());
		}
		
		PersistentAttribute cPersistentAttribute = pt.resolveAttribute("address");	
		assertNotNull(cPersistentAttribute);

	}	
		
	
	@Test
	public void testAddOneToOneBidirectionalRelationAttributes() throws Exception {
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Customer");
		
		IFile creditCardFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com"}, "CreditCard");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(customerFile.exists());
		JavaResourceAbstractType customerType = jpaProject.getJavaResourceType("com.Customer");
		assertNotNull(customerType);
		
		
		PersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		}
		
		assertTrue(creditCardFile.exists());
		JavaResourceAbstractType creditCardType = jpaProject.getJavaResourceType("com.CreditCard");
		assertNotNull(creditCardFile);

		
		PersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, creditCardType.getTypeBinding().getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, creditCardType.getTypeBinding().getQualifiedName());
		}
 
		JavaSpecifiedPersistentAttribute attr = JpaArtifactFactory.instance().
			addAttribute( featureProvider, (JavaPersistentType)t1, (JavaPersistentType)t2, null, "creditCard", "creditCard", false,
					createCompilationUnitFrom((IFile)t1.getResource()));		
		assertNotNull(attr);

		JavaSpecifiedPersistentAttribute attr2 = JpaArtifactFactory.instance().
			addAttribute(featureProvider, (JavaPersistentType)t2, (JavaPersistentType)t1, null, "customer", "customer", false, 
					createCompilationUnitFrom((IFile)t2.getResource()));		
		assertNotNull(attr2);
		
		JpaArtifactFactory.instance().addOneToOneBidirectionalRelation(featureProvider, (JavaPersistentType)t1, attr, (JavaPersistentType)t2, attr2);
		
		PersistentType ownerPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		while (ownerPersistentType == null) {
			Thread.sleep(200);
			ownerPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		}
		
		PersistentAttribute ownerPersistentAttribute = ownerPersistentType.resolveAttribute("creditCard");
		assertNotNull(ownerPersistentAttribute);
		assertTrue(ownerPersistentAttribute instanceof JavaSpecifiedPersistentAttribute);
		
		AttributeMapping ownerSideMapping = ownerPersistentAttribute.getMapping();
		assertTrue(ownerSideMapping instanceof JavaOneToOneMapping); 
		
		assertTrue(((JavaOneToOneMapping)ownerSideMapping).isRelationshipOwner());
		
		PersistentType inversePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, creditCardType.getTypeBinding().getQualifiedName());
		while (inversePersistentType == null) {
			Thread.sleep(200);
			inversePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, creditCardType.getTypeBinding().getQualifiedName());
		}
		
		PersistentAttribute inversePersistentAttribute = inversePersistentType.resolveAttribute("customer");
		assertNotNull(inversePersistentAttribute);
		assertTrue(inversePersistentAttribute instanceof JavaSpecifiedPersistentAttribute);
		
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
		JavaResourceAbstractType customerType = jpaProject.getJavaResourceType("com.Customer");
		assertNotNull(customerType);
		
		
		PersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		}
		
		assertTrue(phoneFile.exists());
		JavaResourceAbstractType phoneType = jpaProject.getJavaResourceType("com.Phone");
		assertNotNull(customerType);

		
		PersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, phoneType.getTypeBinding().getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, phoneType.getTypeBinding().getQualifiedName());
		}
 
		JavaSpecifiedPersistentAttribute attr = JpaArtifactFactory.instance().addAttribute(featureProvider, (JavaPersistentType)t1, (JavaPersistentType)t2, null, "phones", "phones", true, 
				createCompilationUnitFrom((IFile)t1.getResource()));		
		assertNotNull(attr);
			
		JpaArtifactFactory.instance().addOneToManyUnidirectionalRelation(featureProvider, (JavaPersistentType)t1, attr, false);
		
		PersistentType pt = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		while (pt == null) {
			Thread.sleep(200);
			pt = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, phoneType.getTypeBinding().getQualifiedName());
		}
		
		PersistentAttribute cPersistentAttribute = pt.resolveAttribute("phones");		
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
		JavaResourceAbstractType cruiseType = jpaProject.getJavaResourceType("com.Cruise");
		assertNotNull(cruiseType);
		
		
		PersistentType singleSidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cruiseType.getTypeBinding().getQualifiedName());
		while (singleSidePersistentType == null) {
			Thread.sleep(200);
			singleSidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cruiseType.getTypeBinding().getQualifiedName());
		}
		
		assertTrue(reservationFile.exists());
		JavaResourceAbstractType reservationType= jpaProject.getJavaResourceType("com.Reservation");
		assertNotNull(reservationFile);

		
		PersistentType manySidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getTypeBinding().getQualifiedName());
		while (manySidePersistentType == null) {
			Thread.sleep(200);
			manySidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getTypeBinding().getQualifiedName());
		}
 
		JavaSpecifiedPersistentAttribute singleSideAttribute = JpaArtifactFactory.instance().
			addAttribute(featureProvider, (JavaPersistentType)singleSidePersistentType, (JavaPersistentType)manySidePersistentType, null, "reservations", "reservations", true, 
				createCompilationUnitFrom((IFile)singleSidePersistentType.getResource()));		
		assertNotNull(singleSideAttribute);

		JavaSpecifiedPersistentAttribute manySideAttribute = JpaArtifactFactory.instance().
			addAttribute(featureProvider, (JavaPersistentType)manySidePersistentType, (JavaPersistentType)singleSidePersistentType, null, "cruise", "cruise", false, 
				createCompilationUnitFrom((IFile)manySidePersistentType.getResource()));		
		assertNotNull(manySideAttribute);
		
		JpaArtifactFactory.instance().addOneToManyBidirectionalRelation(featureProvider, (JavaPersistentType)singleSidePersistentType, singleSideAttribute, (JavaPersistentType)manySidePersistentType, manySideAttribute, false);
		
		// In one-to-many bidirectional relation many side is ALWAYS owner
		PersistentType inversePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cruiseType.getTypeBinding().getQualifiedName());
		while (inversePersistentType == null) {
			Thread.sleep(200);
			inversePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cruiseType.getTypeBinding().getQualifiedName());
		}
		
		PersistentAttribute inversePersistentAttribute = inversePersistentType.resolveAttribute("reservations");
		assertNotNull(inversePersistentAttribute);
		assertTrue(inversePersistentAttribute instanceof JavaSpecifiedPersistentAttribute);
		
		AttributeMapping inverseSideMapping = inversePersistentAttribute.getMapping();
		assertTrue(inverseSideMapping instanceof JavaOneToManyMapping); 
		
		assertFalse(((JavaOneToManyMapping)inverseSideMapping).isRelationshipOwner());
		
		// In one-to-many bidirectional relation many side is ALWAYS owner
		PersistentType ownerPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getTypeBinding().getQualifiedName());
		int c = 0;
		while ((ownerPersistentType == null) && (c < 50)) {
			Thread.sleep(200);
			ownerPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getTypeBinding().getQualifiedName());
			c++;
		}
		assertNotNull(ownerPersistentType);
		
		PersistentAttribute ownerPersistentAttribute = ownerPersistentType.resolveAttribute("cruise");
		c = 0;
		while ((ownerPersistentAttribute == null) && (c < 50)) {
			Thread.sleep(200);
			ownerPersistentAttribute = ownerPersistentType.resolveAttribute("cruise");
			c++;
		}		
		
		assertNotNull(ownerPersistentAttribute);
		assertTrue(ownerPersistentAttribute instanceof JavaSpecifiedPersistentAttribute);
		
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
		JavaResourceAbstractType cruiseType = jpaProject.getJavaResourceType("com.Cruise");
		assertNotNull(cruiseType);
			
		PersistentType manySidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cruiseType.getTypeBinding().getQualifiedName());
		while (manySidePersistentType == null) {
			Thread.sleep(200);
			manySidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cruiseType.getTypeBinding().getQualifiedName());
		}
		
		assertTrue(shipFile.exists());
		JavaResourceAbstractType shipType = jpaProject.getJavaResourceType("com.Ship");
		assertNotNull(cruiseType);

		
		PersistentType singleSidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, shipType.getTypeBinding().getQualifiedName());
		while (singleSidePersistentType == null) {
			Thread.sleep(200);
			singleSidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, shipType.getTypeBinding().getQualifiedName());
		}
 
		JavaSpecifiedPersistentAttribute mappedAttribute = JpaArtifactFactory.instance().
			addAttribute(featureProvider, (JavaPersistentType)manySidePersistentType, (JavaPersistentType)singleSidePersistentType, null, "ship", "ship", true, 
					createCompilationUnitFrom((IFile)manySidePersistentType.getResource()));		
		assertNotNull(mappedAttribute);
			
		JpaArtifactFactory.instance().addManyToOneUnidirectionalRelation(featureProvider, (JavaPersistentType)manySidePersistentType, mappedAttribute);
		
		PersistentType pt = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cruiseType.getTypeBinding().getQualifiedName());
		while (pt == null) {
			Thread.sleep(200);
			pt = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, shipType.getTypeBinding().getQualifiedName());
		}
		
		PersistentAttribute cPersistentAttribute = pt.resolveAttribute("ship");	
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
		JavaResourceAbstractType reservationType = jpaProject.getJavaResourceType("com.Reservation");
		assertNotNull(reservationType);
		
		PersistentType ownerSidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getTypeBinding().getQualifiedName());
		while (ownerSidePersistentType == null) {
			Thread.sleep(200);
			ownerSidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getTypeBinding().getQualifiedName());
		}
		
		assertTrue(customerFile.exists());
		JavaResourceAbstractType customerType = jpaProject.getJavaResourceType("com.Customer");
		assertNotNull(customerFile);

		
		PersistentType inverseSidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		while (inverseSidePersistentType == null) {
			Thread.sleep(200);
			inverseSidePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		}
 
		JavaSpecifiedPersistentAttribute ownerSideAttribute = JpaArtifactFactory.instance().
			addAttribute(featureProvider, (JavaPersistentType)ownerSidePersistentType, (JavaPersistentType)inverseSidePersistentType, null, "customers", "customers", true,
					createCompilationUnitFrom((IFile)ownerSidePersistentType.getResource()));		
		assertNotNull(ownerSideAttribute);

		JavaSpecifiedPersistentAttribute inverseSideAttributes = JpaArtifactFactory.instance().addAttribute(featureProvider, (JavaPersistentType)inverseSidePersistentType, (JavaPersistentType)ownerSidePersistentType, null, "reservations", "reservations", true, 
				createCompilationUnitFrom((IFile)inverseSidePersistentType.getResource()));		
		assertNotNull(inverseSideAttributes);
		
		JpaArtifactFactory.instance().addManyToManyBidirectionalRelation(featureProvider, (JavaPersistentType)ownerSidePersistentType, ownerSideAttribute, (JavaPersistentType)inverseSidePersistentType, inverseSideAttributes, false);
		
		PersistentType ownerPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getTypeBinding().getQualifiedName());
		while (ownerPersistentType == null) {
			Thread.sleep(200);
			ownerPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getTypeBinding().getQualifiedName());
		}
		
		PersistentAttribute ownerPersistentAttribute = ownerPersistentType.resolveAttribute("customers");
		assertNotNull(ownerPersistentAttribute);
		assertTrue(ownerPersistentAttribute instanceof JavaSpecifiedPersistentAttribute);
		
		AttributeMapping ownerSideMapping = ownerPersistentAttribute.getMapping();
		assertTrue(ownerSideMapping instanceof JavaManyToManyMapping); 
		
		assertTrue(((JavaManyToManyMapping)ownerSideMapping).isRelationshipOwner());
	
		PersistentType inversePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		while (inversePersistentType == null) {
			Thread.sleep(200);
			inversePersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getTypeBinding().getQualifiedName());
		}
		
		PersistentAttribute inversePersistentAttribute = inversePersistentType.resolveAttribute("reservations");
		assertNotNull(inversePersistentAttribute);
		assertTrue(inversePersistentAttribute instanceof JavaSpecifiedPersistentAttribute);
		
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
		JavaResourceAbstractType reservationType = jpaProject.getJavaResourceType("com.Reservation");
		assertNotNull(reservationType);
		
		PersistentType annotatedPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getTypeBinding().getQualifiedName());
		while (annotatedPersistentType == null) {
			Thread.sleep(200);
			annotatedPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getTypeBinding().getQualifiedName());
		}
		
		assertTrue(cabinFile.exists());
		JavaResourceAbstractType cabinType = jpaProject.getJavaResourceType("com.Cabin");
		assertNotNull(cabinFile);

		
		PersistentType referencedPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cabinType.getTypeBinding().getQualifiedName());
		while (referencedPersistentType == null) {
			Thread.sleep(200);
			referencedPersistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, cabinType.getTypeBinding().getQualifiedName());
		}
 
		JavaSpecifiedPersistentAttribute annotatedSideAttribute = JpaArtifactFactory.instance().
			addAttribute(featureProvider, (JavaPersistentType)annotatedPersistentType, (JavaPersistentType)referencedPersistentType, null, "cabins", "cabins", true,
					createCompilationUnitFrom((IFile)annotatedPersistentType.getResource()));		
		assertNotNull(annotatedSideAttribute);
		
		JpaArtifactFactory.instance().addManyToManyUnidirectionalRelation(featureProvider, (JavaPersistentType)annotatedPersistentType, annotatedSideAttribute, false);
		
		PersistentType persistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getTypeBinding().getQualifiedName());
		while (persistentType == null) {
			Thread.sleep(200);
			persistentType = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, reservationType.getTypeBinding().getQualifiedName());
		}
		
		PersistentAttribute persistentAttribute = persistentType.resolveAttribute("cabins");
		assertNotNull(persistentAttribute);
		assertTrue(persistentAttribute instanceof JavaSpecifiedPersistentAttribute);
		
		AttributeMapping mapping = persistentAttribute.getMapping();
		assertTrue(mapping instanceof JavaManyToManyMapping); 
		
		assertTrue(((JavaManyToManyMapping)mapping).isRelationshipOwner());
		}	
	

	
	
}

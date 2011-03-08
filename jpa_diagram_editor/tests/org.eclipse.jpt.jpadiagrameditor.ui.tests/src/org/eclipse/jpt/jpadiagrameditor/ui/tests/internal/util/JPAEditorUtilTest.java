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
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.util;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.BasicInternalEList;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IPeServiceUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.SizePosition;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants.ShapeType;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.JPACreateFactory;
import org.eclipse.swt.graphics.Point;
import org.junit.Test;

public class JPAEditorUtilTest {
	@Test
	public void testCapitalizeFirstLetter() {
		String s = "abcdef";
		String res = JPAEditorUtil.capitalizeFirstLetter(s);
		assertEquals(res.substring(0, 1), "A");
		assertEquals(s.substring(1), res.substring(1));
	}
	
	@Test
	public void testDecapitalizeFirstLetter() { 		
		String s = "ABCDEFGHIJK";
		String res = JPAEditorUtil.decapitalizeFirstLetter(s);
		assertEquals(res.substring(0, 1), "a");
		assertEquals(s.substring(1), res.substring(1));
	}
	
	@Test
	public void testRevertFirstLetterCase() {
		String s = "ABCDEFGHIJK";
		String res = JPAEditorUtil.revertFirstLetterCase(s);
		assertEquals(res.substring(0, 1), "a");
		assertEquals(s.substring(1), res.substring(1));
		String res1 = JPAEditorUtil.revertFirstLetterCase(res);
		assertEquals(res1.substring(0, 1), "A");
		assertEquals(res1.substring(1), res.substring(1));
	}
	
	@Test
	public void testEqualsIgnoreFirstLetterCase() {
		String s1 = "abcdefg";
		String s2 = "Abcdefg";
		assertTrue(JPAEditorUtil.equalsIgnoreFirstLetterCase(s1, s2));
		
		s1 = "";
		s2 = "";
		assertTrue(JPAEditorUtil.equalsIgnoreFirstLetterCase(s1, s2));
		
		s1 = "gjgIyguiyGUYuGUYGuyg";
		s2 = "gjgIyguiyGUYuGUYGuyg";
		assertTrue(JPAEditorUtil.equalsIgnoreFirstLetterCase(s1, s2));
		
		s1 = "LjgIyguiyGUYuGUYGuyg";
		s2 = "LjgIyguiyGUYuGUYGuyg";
		assertTrue(JPAEditorUtil.equalsIgnoreFirstLetterCase(s1, s2));		
		
		s1 = "gjgIyguiyGUYuGUYGuygs";
		s2 = "gjgIyguiyGUYuGUYGuyg";
		assertFalse(JPAEditorUtil.equalsIgnoreFirstLetterCase(s1, s2));
		
		s1 = "LjgIyguiyGUyuGUYGuyg";
		s2 = "LjgIyguiyGUYuGUYGuyg";
		assertFalse(JPAEditorUtil.equalsIgnoreFirstLetterCase(s1, s2));			
	}
	
	@Test
	public void testCutFromLastDot() {
		String s = "";
		String res = JPAEditorUtil.cutFromLastDot(s);
		assertEquals(s, res);
		s = "jdhksajhdk";
		res = JPAEditorUtil.cutFromLastDot(s);
		assertEquals(s, res);		
		s = "jdhksajhdk.";
		res = JPAEditorUtil.cutFromLastDot(s);
		assertEquals("", res);
		s = ".jdhksajhdk";
		res = JPAEditorUtil.cutFromLastDot(s);
		assertEquals("jdhksajhdk", res);
		s = "jdhks.ajhdk";
		res = JPAEditorUtil.cutFromLastDot(s);
		assertEquals("ajhdk", res);
		s = "dss.dsdsd.jd.hks.ajhdk";
		res = JPAEditorUtil.cutFromLastDot(s);
		assertEquals("ajhdk", res);
	}
	
	
	@Test
	public void testStripQuotes() {
		String s = "";
		String res = JPAEditorUtil.stripQuotes(s);
		assertEquals("", res);
		
		s = "\"\"";
		res = JPAEditorUtil.stripQuotes(s);
		assertEquals("", res);
		
		s = "\"fdsjfjslkdjflks\"";
		res = JPAEditorUtil.stripQuotes(s);
		assertEquals("fdsjfjslkdjflks", res);
		
		s = "\"fdsjfjslkdjflks";
		res = JPAEditorUtil.stripQuotes(s);
		assertEquals("\"fdsjfjslkdjflks", res);	
		
		s = "fdsjfjslkdjflks\"";
		res = JPAEditorUtil.stripQuotes(s);
		assertEquals("fdsjfjslkdjflks\"", res);			
	}
	
	@Test 
	public void testGetJPType() throws Exception {
		
		String TEST_PROJECT = "Test";
		JpaProject jpaProject = null;
		JPACreateFactory factory = null;
		String testProjectName = TEST_PROJECT + "_" + System.currentTimeMillis();
	
		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPAProject(testProjectName);
		assertNotNull(jpaProject);
		Thread.sleep(2000);
	
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
		
		assertTrue(customerFile.exists());
		JavaResourcePersistentType customerType = jpaProject.getJavaResourcePersistentType("com.test.Customer");
		assertNotNull(customerType);
		
		ICompilationUnit cu = createCompilationUnitFrom(customerFile);
		Thread.sleep(2000);
		JavaPersistentType jpt = JPAEditorUtil.getJPType(cu);
		assertEquals(customerType.getQualifiedName(), jpt.getName());
	}
	
	@Test
	public void testProduceValidAttributeName() {
		String s = "a";
		String res = JPAEditorUtil.produceValidAttributeName(s);
		assertEquals(s, res);
		
		s = "A";
		res = JPAEditorUtil.produceValidAttributeName(s);
		assertEquals("a", res);		
		
		s = "aT";
		res = JPAEditorUtil.produceValidAttributeName(s);
		assertEquals("AT", res);
		
		s = "At";
		res = JPAEditorUtil.produceValidAttributeName(s);
		assertEquals("at", res);
		
		s = "AT";
		res = JPAEditorUtil.produceValidAttributeName(s);
		assertEquals("AT", res);		
		
		s = "a1";
		res = JPAEditorUtil.produceValidAttributeName(s);
		assertEquals("a1", res);		
		
		s = "A1";
		res = JPAEditorUtil.produceValidAttributeName(s);
		assertEquals("a1", res);				

	}
	
	@Test
	public void testCreateImports() throws Exception {
		String TEST_PROJECT = "Test";
		JpaProject jpaProject = null;
		JPACreateFactory factory = null;
		String testProjectName = TEST_PROJECT + "_" + System.currentTimeMillis();
	
		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPAProject(testProjectName);
		assertNotNull(jpaProject);
		Thread.sleep(2000);		
	
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
		
		assertTrue(customerFile.exists());
		JavaResourcePersistentType customerType = jpaProject.getJavaResourcePersistentType("com.test.Customer");
		assertNotNull(customerType);
		
		ICompilationUnit cu = createCompilationUnitFrom(customerFile);
		
		JPAEditorUtil.createImports(cu, "java.util.Hashtable<java.lang.StringBuffer,java.lang.Set<java.lang.String>>");
	}
	
	@Test 
	public void testSizePosition() {
		SizePosition sp = new SizePosition(1,2,3,4);
		assertTrue(sp.getWidth() == 1);
		assertTrue(sp.getHeight() == 2);		
		assertTrue(sp.getX() == 3);
		assertTrue(sp.getY() == 4);
	}
	
	@Test
	public void testGetText() throws Exception {
		String TEST_PROJECT = "Test";
		JpaProject jpaProject = null;
		JPACreateFactory factory = null;
		String testProjectName = TEST_PROJECT + "_" + System.currentTimeMillis();
	
		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPAProject(testProjectName);
		assertNotNull(jpaProject);
		Thread.sleep(2000);		
	
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
		
		assertTrue(customerFile.exists());
		JavaResourcePersistentType customerType = jpaProject.getJavaResourcePersistentType("com.test.Customer");
		assertNotNull(customerType);
		
		JavaPersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		int cnt = 0;
		while ((cnt < 25) && (t1 == null)) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
			cnt++;
		}
		if (t1 == null)
			return;
		assertNotNull(JPAEditorUtil.getText(t1));
		assertNotNull(JPAEditorUtil.getTooltipText(t1));
		JavaPersistentAttribute jpa = t1.getAttributeNamed("id");
		assertNotNull(jpa);
		assertNotNull(JPAEditorUtil.getText(jpa));
		assertNotNull(JPAEditorUtil.getTooltipText(jpa));

	}
	
	@Test
	public void testSetJPTNameInShape() {
		final String NEW_NAME = "NewJPTName";
		ContainerShape cs = EasyMock.createMock(ContainerShape.class);
		Shape sh = EasyMock.createMock(Shape.class);
		GraphicsAlgorithm ga = EasyMock.createMock(GraphicsAlgorithm.class);
		IPeServiceUtil peUtil = EasyMock.createMock(IPeServiceUtil.class);
		EasyMock.expect(peUtil.getPropertyValue(sh, JPAEditorConstants.PROP_SHAPE_TYPE)).andStubReturn(ShapeType.HEADER.toString());
		EList<Shape> shapes = new BasicInternalEList<Shape>(Shape.class);
		shapes.add(sh);
		EasyMock.expect(cs.getChildren()).andStubReturn(shapes);
		Text txt = EasyMock.createMock(Text.class);
		EList<GraphicsAlgorithm> gaCh = new BasicInternalEList<GraphicsAlgorithm>(GraphicsAlgorithm.class);  
		gaCh.add(txt);
		EasyMock.expect(sh.getGraphicsAlgorithm()).andStubReturn(ga);
		EasyMock.expect(ga.getGraphicsAlgorithmChildren()).andStubReturn(gaCh);
		txt.setValue(NEW_NAME);
		EasyMock.replay(cs, sh, ga, peUtil, txt);
		JPAEditorUtil.setJPTNameInShape(cs, NEW_NAME, peUtil);
	}
	
	@Test
	public void testCreateBendPointList1() {
		FreeFormConnection c = EasyMock.createMock(FreeFormConnection.class);
		Anchor startAnchor = EasyMock.createMock(Anchor.class);
		Anchor endAnchor = EasyMock.createMock(Anchor.class);
		AnchorContainer startAnchorContainer = EasyMock.createMock(AnchorContainer.class);
		AnchorContainer endAnchorContainer = EasyMock.createMock(AnchorContainer.class);
		expect(c.getStart()).andStubReturn(startAnchor);
		expect(c.getEnd()).andStubReturn(endAnchor);
		expect(startAnchor.getParent()).andStubReturn(startAnchorContainer);
		expect(endAnchor.getParent()).andStubReturn(endAnchorContainer);
		RoundedRectangle rectStart = createMock(RoundedRectangle.class);
		RoundedRectangle rectEnd = createMock(RoundedRectangle.class);
		expect(startAnchorContainer.getGraphicsAlgorithm()).andStubReturn(rectStart);
		expect(endAnchorContainer.getGraphicsAlgorithm()).andStubReturn(rectEnd);
		expect(rectStart.getX()).andStubReturn(100);
		expect(rectStart.getY()).andStubReturn(100);
		expect(rectStart.getWidth()).andStubReturn(200);
		expect(rectStart.getHeight()).andStubReturn(120);
		expect(rectEnd.getX()).andStubReturn(100);
		expect(rectEnd.getY()).andStubReturn(800);
		expect(rectEnd.getWidth()).andStubReturn(200);
		expect(rectEnd.getHeight()).andStubReturn(120);
		
		EList<Connection> ccc = new BasicInternalEList<Connection>(Connection.class);  
		expect(startAnchor.getOutgoingConnections()).andStubReturn(ccc);
		expect(endAnchor.getOutgoingConnections()).andStubReturn(ccc);

		EasyMock.replay(c, startAnchor, endAnchor, startAnchorContainer, endAnchorContainer, rectStart, rectEnd);
		
		
		
		List<Point> lst = JPAEditorUtil.createBendPointList(c, false);
		assertTrue(lst.size() == 2);
		for (Point p : lst) {
			assertTrue(p.x >= 0);
			assertTrue(p.y >= 0);
			
			assertTrue(p.x <= 1000000);
			assertTrue(p.y <= 1000000);			
		}
		
	}
	
	@Test
	public void testCreateBendPointList2() {
		FreeFormConnection c = EasyMock.createMock(FreeFormConnection.class);
		Anchor startAnchor = EasyMock.createMock(Anchor.class);
		Anchor endAnchor = EasyMock.createMock(Anchor.class);
		AnchorContainer startAnchorContainer = EasyMock.createMock(AnchorContainer.class);
		AnchorContainer endAnchorContainer = EasyMock.createMock(AnchorContainer.class);
		expect(c.getStart()).andStubReturn(startAnchor);
		expect(c.getEnd()).andStubReturn(endAnchor);
		expect(startAnchor.getParent()).andStubReturn(startAnchorContainer);
		expect(endAnchor.getParent()).andStubReturn(endAnchorContainer);
		RoundedRectangle rectStart = createMock(RoundedRectangle.class);
		RoundedRectangle rectEnd = createMock(RoundedRectangle.class);
		expect(startAnchorContainer.getGraphicsAlgorithm()).andStubReturn(rectStart);
		expect(endAnchorContainer.getGraphicsAlgorithm()).andStubReturn(rectEnd);
		expect(rectStart.getX()).andStubReturn(100);
		expect(rectStart.getY()).andStubReturn(100);
		expect(rectStart.getWidth()).andStubReturn(200);
		expect(rectStart.getHeight()).andStubReturn(120);
		expect(rectEnd.getX()).andStubReturn(800);
		expect(rectEnd.getY()).andStubReturn(100);
		expect(rectEnd.getWidth()).andStubReturn(200);
		expect(rectEnd.getHeight()).andStubReturn(120);

		EList<Connection> ccc = new BasicInternalEList<Connection>(Connection.class);  
		expect(startAnchor.getOutgoingConnections()).andStubReturn(ccc);		
		expect(endAnchor.getOutgoingConnections()).andStubReturn(ccc);		
		
		EasyMock.replay(c, startAnchor, endAnchor, startAnchorContainer, endAnchorContainer, rectStart, rectEnd);
		
		List<Point> lst = JPAEditorUtil.createBendPointList(c, false);
		assertTrue(lst.size() == 2);
		for (Point p : lst) {
			assertTrue(p.x >= 0);
			assertTrue(p.y >= 0);
			
			assertTrue(p.x <= 1000000);
			assertTrue(p.y <= 1000000);			
		}
		
	}
	
	public ICompilationUnit createCompilationUnitFrom(IFile file) {
		return JavaCore.createCompilationUnitFrom(file);
	}	
	
}

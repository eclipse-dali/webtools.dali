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
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import org.easymock.EasyMock;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.BasicInternalEList;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.mm.Property;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Color;
import org.eclipse.graphiti.mm.algorithms.styles.Font;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramsPackage;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.UpdateAttributeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.junit.Before;
import org.junit.Test;

public class AddJPAEntityFeatureTest {
	
	private IJPAEditorFeatureProvider featureProvider;
	
	
	@Before
	public void setUp() throws Exception {
		Thread.sleep(2000);
		featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		expect(featureProvider.getAttribsNum((Shape) EasyMock.anyObject())).andStubReturn(0);
		IDiagramTypeProvider idp = EasyMock.createMock(IDiagramTypeProvider.class);
		expect(featureProvider.getDiagramTypeProvider()).andStubReturn(idp);
		expect(featureProvider.increaseAttribsNum((Shape) EasyMock.anyObject())).andStubReturn(1);
		Diagram d = EasyMock.createMock(Diagram.class);
		expect(idp.getDiagram()).andStubReturn(d);
		Color c = EasyMock.createMock(Color.class);
		expect(c.getBlue()).andStubReturn(0);
		expect(c.getRed()).andStubReturn(0);
		expect(c.getGreen()).andStubReturn(0);
		EList<Color> col = new BasicInternalEList<Color>(Color.class);
		expect(d.getColors()).andStubReturn(col);
		Color c1 = EasyMock.createMock(Color.class);
		c1.setRed(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		c1.setGreen(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		c1.setBlue(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		EList<Font> fonts = new BasicInternalEList<Font>(Font.class);
		expect(d.getFonts()).andStubReturn(fonts);
		replay(d, c, c1, idp, featureProvider);
	}
	

	@Test
	public void testAddText() {
		Font f = EasyMock.createMock(Font.class); 
		Text t = EasyMock.createMock(Text.class);
		t.setFont(f);
		EasyMock.expectLastCall().asStub();
		expect(t.getX()).andStubReturn(0);
		expect(t.getY()).andStubReturn(0);
		
		f.setName("Arial");
		EasyMock.expectLastCall().asStub();
		f.setSize(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();		
		f.setItalic(EasyMock.anyBoolean());
		EasyMock.expectLastCall().asStub();	
		f.setBold(EasyMock.anyBoolean());
		EasyMock.expectLastCall().asStub();
		t.setWidth(EasyMock.anyInt());	
		EasyMock.expectLastCall().asStub();
		t.setHeight(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		t.setValue("abc");	
		EasyMock.expectLastCall().asStub();
		
		t.setForeground((Color) EasyMock.anyObject());
		EasyMock.expectLastCall().asStub();
		t.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
		EasyMock.expectLastCall().asStub();
		t.setVerticalAlignment(Orientation.ALIGNMENT_TOP);
		EasyMock.expectLastCall().asStub();
		
		Font f1 = EasyMock.createMock(Font.class);
		f1.setBold(true);
		EasyMock.expectLastCall().asStub();
		expect(t.getFont()).andStubReturn(f1);
		
		Rectangle textRectangle = EasyMock.createMock(Rectangle.class);
		EList<GraphicsAlgorithm> lst = new BasicInternalEList<GraphicsAlgorithm>(GraphicsAlgorithm.class);
		expect(textRectangle.getGraphicsAlgorithmChildren()).andStubReturn(lst);
		
		replay(f1, f, t, textRectangle);
		UpdateAttributeFeature.addText(featureProvider, textRectangle, "abc");
	}
	
	@Test
	public void testaddRectangleForIcon() {
		ContainerShape cs = EasyMock.createMock(ContainerShape.class);
		Rectangle rect = EasyMock.createMock(Rectangle.class);
		expect(rect.getX()).andStubReturn(0);
		expect(rect.getY()).andStubReturn(0);
		rect.setWidth(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		rect.setHeight(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		cs.setGraphicsAlgorithm(rect);
		EasyMock.expectLastCall().asStub();
		rect.setFilled(EasyMock.anyBoolean());
		EasyMock.expectLastCall().asStub();
		rect.setLineVisible(EasyMock.anyBoolean());
		EasyMock.expectLastCall().asStub();
		rect.setX(EasyMock.anyInt());
		rect.setLineVisible(EasyMock.anyBoolean());
		rect.setY(EasyMock.anyInt());
		rect.setLineVisible(EasyMock.anyBoolean());
		rect.setWidth(EasyMock.anyInt());
		rect.setLineVisible(EasyMock.anyBoolean());
		cs.setGraphicsAlgorithm(EasyMock.isA(Rectangle.class));
		EasyMock.expectLastCall().asStub();
		replay(rect, cs);		
		UpdateAttributeFeature.addRectangleForIcon(cs, 0);
	}
	
	@Test
	public void testAddRectangleForText() {
		ContainerShape cs = EasyMock.createMock(ContainerShape.class);
		Rectangle rect = EasyMock.createMock(Rectangle.class);
		expect(rect.getX()).andStubReturn(0);
		expect(rect.getY()).andStubReturn(0);
		rect.setWidth(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		rect.setHeight(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		cs.setGraphicsAlgorithm(rect);
		EasyMock.expectLastCall().asStub();
		rect.setFilled(EasyMock.anyBoolean());
		EasyMock.expectLastCall().asStub();
		rect.setLineVisible(EasyMock.anyBoolean());
		EasyMock.expectLastCall().asStub();
		rect.setX(EasyMock.anyInt());
		rect.setLineVisible(EasyMock.anyBoolean());
		rect.setY(EasyMock.anyInt());
		rect.setLineVisible(EasyMock.anyBoolean());
		rect.setWidth(EasyMock.anyInt());
		rect.setLineVisible(EasyMock.anyBoolean());
		cs.setGraphicsAlgorithm(EasyMock.isA(Rectangle.class));
		EasyMock.expectLastCall().asStub();
		replay(rect, cs);		
		UpdateAttributeFeature.addRectangleForText(cs, 0, 120);
	}
	
	@Test
	public void testAddAttribute() {
		GraphicsAlgorithm ga = EasyMock.createMock(GraphicsAlgorithm.class);
		expect(ga.getWidth()).andStubReturn(20);
		ContainerShape cs = EasyMock.createMock(ContainerShape.class);
		expect(cs.getGraphicsAlgorithm()).andStubReturn(ga);
		Rectangle rect = EasyMock.createMock(Rectangle.class);
		expect(rect.getX()).andStubReturn(0);
		expect(rect.getY()).andStubReturn(0);
		rect.setWidth(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		rect.setHeight(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		rect.setFilled(EasyMock.anyBoolean());
		EasyMock.expectLastCall().asStub();
		rect.setLineVisible(EasyMock.anyBoolean());
		EasyMock.expectLastCall().asStub();
		rect.setX(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		rect.setY(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();

		EList<Property> props = new BasicInternalEList<Property>(Property.class);
		EList<Property> props1 = new BasicInternalEList<Property>(Property.class);

		expect(cs.getProperties()).andStubReturn(props);
		
		cs.setVisible(EasyMock.anyBoolean());
		EasyMock.expectLastCall().asStub();
		cs.setActive(EasyMock.anyBoolean());
		EasyMock.expectLastCall().asStub();
		cs.setContainer((ContainerShape) EasyMock.anyObject());
		EasyMock.expectLastCall().asStub();
		ContainerShape cs1 = EasyMock.createMock(ContainerShape.class);
		expect(cs1.getProperties()).andStubReturn(props1);	
		cs1.setVisible(EasyMock.anyBoolean());
		EasyMock.expectLastCall().asStub();
		cs1.setActive(EasyMock.anyBoolean());
		EasyMock.expectLastCall().asStub();
		cs1.setContainer((ContainerShape) EasyMock.anyObject());
		EasyMock.expectLastCall().asStub();
		PictogramsPackage p = EasyMock.createMock(PictogramsPackage.class);
		Property pr = EasyMock.createMock(Property.class);
		cs1.setGraphicsAlgorithm((GraphicsAlgorithm) EasyMock.anyObject());
		EasyMock.expectLastCall().asStub();		
		Image img = EasyMock.createMock(Image.class);
		expect(img.getX()).andStubReturn(0);
		expect(img.getY()).andStubReturn(0);
		img.setWidth(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		img.setHeight(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		img.setX(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		img.setY(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		img.setId("org.eclisp.jpt.ui.diagrameditor.field");
		EasyMock.expectLastCall().asStub();
		img.setProportional(EasyMock.anyBoolean());
		EasyMock.expectLastCall().asStub();
		img.setStretchH(EasyMock.anyBoolean());
		EasyMock.expectLastCall().asStub();
		img.setStretchV(EasyMock.anyBoolean());
		EasyMock.expectLastCall().asStub();
		EList<GraphicsAlgorithm> ch = new BasicInternalEList<GraphicsAlgorithm>(GraphicsAlgorithm.class);
		expect(rect.getGraphicsAlgorithmChildren()).andStubReturn(ch);
		expect(pr.getKey()).andStubReturn("prop_shape_type");
		Text t = EasyMock.createMock(Text.class);
		expect(t.getX()).andStubReturn(0);
		expect(t.getY()).andStubReturn(0);
		t.setWidth(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		t.setX(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		t.setY(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		t.setHeight(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		t.setValue("");
		EasyMock.expectLastCall().asStub();
		Font f = EasyMock.createMock(Font.class);
		f.setName((String) EasyMock.anyObject());
		EasyMock.expectLastCall().asStub();
		
		f.setSize(EasyMock.anyInt());
		EasyMock.expectLastCall().asStub();
		f.setItalic(EasyMock.anyBoolean());
		EasyMock.expectLastCall().asStub();
		f.setBold(EasyMock.anyBoolean());
		EasyMock.expectLastCall().asStub();
		expect(t.getFont()).andStubReturn(f);
		t.setFont((Font) EasyMock.anyObject());
		EasyMock.expectLastCall().asStub();
		EasyMock.expectLastCall().asStub();
		t.setForeground((Color) EasyMock.anyObject());
		t.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
		t.setVerticalAlignment(Orientation.ALIGNMENT_TOP);
		f.setBold(true);
		replay(rect, ga, img, cs, cs1, p, pr, t, f);
	}
	
}

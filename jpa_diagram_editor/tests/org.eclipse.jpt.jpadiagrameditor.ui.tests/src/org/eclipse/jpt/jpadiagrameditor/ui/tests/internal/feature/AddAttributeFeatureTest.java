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
 *    Kiril Mitov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.Arrays;

import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;
import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.AddAttributeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.junit.Before;
import org.junit.Test;

public class AddAttributeFeatureTest {

	private IJPAEditorFeatureProvider featureProvider;

	private AddContext context;

	private JavaPersistentAttribute jpa;

	private ContainerShape entityShape;

	private ICustomFeature expandFeature;

	private IAddFeature graphicalAdd;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		jpa = EasyMock.createMock(JavaPersistentAttribute.class);
		context = new AddContext();
		context.setNewObject(jpa);
		context.setTargetContainer(entityShape);
		graphicalAdd = EasyMock.createMock(IAddFeature.class);
		expandFeature = EasyMock.createMock(ICustomFeature.class);
	}

	@Test
	public void testAddNotJPA() {
		context.setNewObject(new Object());
		assertNull(callAdd());
	}
	

	@SuppressWarnings("unused")
	private void confgirueAttributeShape(ContainerShape attributeContainer) {
		ContainerShape attributeShape = EasyMock.createMock(ContainerShape.class);
		expect(featureProvider.getPictogramElementForBusinessObject(jpa)).andReturn(attributeShape);
		expect(attributeShape.getContainer()).andReturn(attributeContainer);
		replay(attributeShape);
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private ContainerShape replayTextShape(Text text) {
		EList<GraphicsAlgorithm> children = EasyMock.createMock(EList.class);
		children.add(text);
		GraphicsAlgorithm ga = EasyMock.createMock(GraphicsAlgorithm.class);
		expect(ga.getGraphicsAlgorithmChildren()).andReturn(children);
		replay(ga);
		ContainerShape textShape = EasyMock.createMock(ContainerShape.class);
		expect(textShape.getGraphicsAlgorithm()).andReturn(ga);
		replay(textShape);
		return textShape;
	}

	@SuppressWarnings("unused")
	private IDirectEditingInfo configureDirectEditing(Text text, ContainerShape textShape) {
		IDirectEditingInfo info = EasyMock.createMock(IDirectEditingInfo.class);
		info.setGraphicsAlgorithm(text);
		info.setMainPictogramElement(textShape);
		info.setPictogramElement(textShape);
		expect(featureProvider.getDirectEditingInfo()).andReturn(info);
		replay(info);
		return info;
	}

	@SuppressWarnings("unused")
	private ICustomContext createCustomContextMatcher(final PictogramElement[] elements) {
		EasyMock.reportMatcher(new IArgumentMatcher() {
			public void appendTo(StringBuffer buffer) {
			}

			public boolean matches(Object argument) {
				if (!ICustomContext.class.isInstance(argument))
					return false;
				ICustomContext context = (ICustomContext) argument;
				return Arrays.equals(context.getPictogramElements(), elements);
			}
		});
		return null;
	}

	@SuppressWarnings("unused")
	private IAddContext contextMatcher() {
		EasyMock.reportMatcher(new IArgumentMatcher() {

			public void appendTo(StringBuffer buffer) {
			}

			public boolean matches(Object argument) {
				if (!IAddContext.class.isInstance(argument))
					return false;
				IAddContext context = (IAddContext) argument;
				// compare by reference
				if (jpa != context.getNewObject())
					return false;
				if (entityShape != context.getTargetContainer())
					return false;
				return true;

			}

		});
		return null;
	}

	private PictogramElement callAdd() {
		IAddFeature fixture = createFeature();
		return fixture.add(context);
	}

	@Test
	public void testGetFeatureProvider() {
		assertSame(featureProvider, createFeature().getFeatureProvider());
	}

	@Test
	public void testCanAddObject() {
		context.setNewObject(new Object());
		// test
		assertEquals(false, callCanAdd());
	}

	@Test
	public void testCanAddJpt() {
		// test
		assertEquals(true, callCanAdd());
	}

	private boolean callCanAdd() {
		IAddFeature fixture = createFeature();
		boolean result = fixture.canAdd(context);
		return result;
	}

	private IAddFeature createFeature() {
		replay(featureProvider);
		replay(graphicalAdd);
		replay(expandFeature);
		return new AddAttributeFeature(featureProvider, graphicalAdd, expandFeature);
	}

}

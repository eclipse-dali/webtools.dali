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
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.RefactorAttributeTypeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.JPACreateFactory;
import org.junit.Before;
import org.junit.Test;

public class RefactorAttributeTypeFeatureTest {
	
	private IJPAEditorFeatureProvider featureProvider;
	private ICustomContext context;
	final String TEST_PROJECT = "Test"+(new Date()).getTime();
	private JpaProject jpaProject = null;
	private JPACreateFactory factory = null;
	private IFile entity;
	
	@Before
	public void setUp() throws Exception{
		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPAProject(TEST_PROJECT + "_" + System.currentTimeMillis());
		assertNotNull(jpaProject);
		entity = factory.createEntity(jpaProject, "org.eclipse.Ent");
		Thread.sleep(2000);
		factory.addAttributes(entity, "att", "java.lang.String", "", "att", false);
		
	}
	
	@Test
	public void testRefactorAttributeTypeFeature(){
		PictogramElement[] elements = new PictogramElement[1];
		PictogramElement element = EasyMock.createNiceMock(PictogramElement.class);
		replay(element);
		elements[0] = element;
		
		featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		expect(featureProvider.getBusinessObjectForPictogramElement(element)).andReturn(null);
		replay(featureProvider);
		
		context = EasyMock.createMock(ICustomContext.class);
		expect(context.getPictogramElements()).andReturn(elements);
		replay(context);
		
		RefactorAttributeTypeFeature feature = new RefactorAttributeTypeFeature(featureProvider);
		feature.execute(context);
	}
	
	@Test
	public void testAttributeUtils(){
		PersistenceUnit pu = JpaArtifactFactory.instance().getPersistenceUnit(jpaProject);
		JavaPersistentType jpt = (JavaPersistentType)pu.getPersistentType("org.eclipse.Ent");
		JavaPersistentAttribute at = jpt.getAttributeNamed("id");
		List<String> lst = JpaArtifactFactory.instance().getAnnotationStrings(at);
		assertEquals(1, lst.size());
		assertTrue(lst.contains("@Id"));
		String typeName1 = JPAEditorUtil.getAttributeTypeName(at);
		assertEquals("int", typeName1);
		String typeName2 = JPAEditorUtil.getAttributeTypeName(at.getResourceAttribute());
		assertEquals("int", typeName2);		
	}
	
}

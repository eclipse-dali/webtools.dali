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
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.provider;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.mm.Property;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.ClickRemoveAttributeButtonFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.DeleteJPAEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.DeleteRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.AbstractRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToManyUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJpaSolver;
import org.junit.Before;
import org.junit.Test;


public class JPAEditorFeatureProviderTest {
	

	private IJpaSolver solver;
	private String businessObjectKey;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		solver = EasyMock.createMock(IJpaSolver.class);
		businessObjectKey = "someValue";
	}
	
	@Test
	public void testAttributesGroupDeleteFeature(){		
		PictogramElement pe = replayPictogramElement();
	
		expect(solver.getBusinessObjectForKey(businessObjectKey)).andReturn(new Object());
 		IJPAEditorFeatureProvider provider = createFeatureProvider();
 		
		//test
		IDeleteContext context = replayDeleteContext(pe);
		assertNull(provider.getDeleteFeature(context));
	}
	
	@Test
	public void testAttributeDeleteFeature(){
		PictogramElement pe = replayPictogramElement();
		JavaPersistentAttribute jpa = replayAttribute();
		expect(solver.getBusinessObjectForKey(businessObjectKey)).andStubReturn(jpa);
		IJPAEditorFeatureProvider provider = createFeatureProvider();
		
		//test
		IDeleteContext context = replayDeleteContext(pe);
		assertNotNull(provider.getDeleteFeature(context));
		IDeleteFeature feature = provider.getDeleteFeature(context);
		assertTrue(feature instanceof ClickRemoveAttributeButtonFeature);
	}
	
	@Test
	public void testEntityDeleteFeature(){
		PictogramElement pe = replayPictogramElement();
		JavaPersistentType jpt = replayJPT("TestEntity");
		
		expect(solver.getBusinessObjectForKey(businessObjectKey)).andStubReturn(jpt);
		IJPAEditorFeatureProvider provider = createFeatureProvider();
		
		//test
		IDeleteContext context = replayDeleteContext(pe);
		assertNotNull(provider.getDeleteFeature(context));
		IDeleteFeature deleteFeature = provider.getDeleteFeature(context);
		assertTrue(deleteFeature instanceof DeleteJPAEntityFeature);
	}
	
	@Test
	public void testRelationDeleteFeature(){
		PictogramElement pe = replayPictogramElement();
		JavaPersistentType jpt1 = replayJPT("TestEntity1");
		JavaPersistentType jpt2 = replayJPT("TestEntity2");
		IJPAEditorFeatureProvider provider = createFeatureProvider();

		AbstractRelation relation = new ManyToManyUniDirRelation(provider, jpt1, jpt2, "attribute1", false, null, null);
		expect(solver.getBusinessObjectForKey(businessObjectKey)).andStubReturn(relation);
		
		//test
		IDeleteContext context = replayDeleteContext(pe);
		assertNotNull(provider.getDeleteFeature(context));
		IDeleteFeature deleteFeature = provider.getDeleteFeature(context);
		assertTrue(deleteFeature instanceof DeleteRelationFeature);
	}

	private JavaPersistentType replayJPT(String name){
		JavaPersistentType jpt = EasyMock.createMock(JavaPersistentType.class);
		expect(jpt.getName()).andReturn(name);
		replay(jpt);
		return jpt;
	}
	
	private JavaPersistentAttribute replayAttribute(){
		JavaPersistentAttribute attribute = EasyMock.createMock(JavaPersistentAttribute.class);
		expect(attribute.getName()).andReturn("attribute");
		replay(attribute);
		return attribute;
	}
	
	@SuppressWarnings("unchecked")
	private PictogramElement replayPictogramElement(){
		PictogramElement pe = EasyMock.createMock(PictogramElement.class);
		Resource r = EasyMock.createMock(Resource.class);
		expect(pe.eResource()).andStubReturn(r);
		//expect(pe.is___Alive()).andStubReturn(true);
		EList<Property> properties = EasyMock.createMock(EList.class);
		Property prop = EasyMock.createMock(Property.class);
		expect(prop.getKey()).andStubReturn("independentObject");
		expect(prop.getValue()).andStubReturn(businessObjectKey);
		replay(prop, r);
		properties.add(prop);
		expect(pe.getProperties()).andStubReturn(properties);
		replay(pe);
		return pe;
	}

	private IJPAEditorFeatureProvider createFeatureProvider() {
		IDiagramTypeProvider dtp = EasyMock.createMock(IDiagramTypeProvider.class);
		replay(dtp);
		solver.setFeatureProvider(isA(IJPAEditorFeatureProvider.class));
		replay(solver);
		IJPAEditorFeatureProvider provider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		expect(provider.getDiagramTypeProvider()).andStubReturn(dtp);
		//provider.get
		replay(provider);
		return provider;
	}
	
	private IDeleteContext replayDeleteContext(PictogramElement pe){
		IDeleteContext context = EasyMock.createMock(IDeleteContext.class);
		expect(context.getPictogramElement()).andStubReturn(pe);
		replay(context);
		return context;
	}

}

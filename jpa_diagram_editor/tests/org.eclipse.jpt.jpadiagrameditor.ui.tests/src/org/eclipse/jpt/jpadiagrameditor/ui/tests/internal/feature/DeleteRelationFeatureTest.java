/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2013 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;

import org.easymock.EasyMock;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.editor.IDiagramBehaviorUI;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.DeleteRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IBidirectionalRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.util.IEditor;
import org.eclipse.ui.IWorkbenchPartSite;
import org.junit.Test;

public class DeleteRelationFeatureTest {		
	
	@Test
	public void testPostDelete(){		
		IJPAEditorFeatureProvider featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		expect(featureProvider.getRemoveFeature(isA(IRemoveContext.class))).andReturn(null);
		IJPAEditorUtil ut = EasyMock.createMock(IJPAEditorUtil.class);
		expect(featureProvider.getJPAEditorUtil()).andStubReturn(ut);
		IDeleteContext ctx = EasyMock.createMock(IDeleteContext.class);
		PictogramElement pe = EasyMock.createMock(PictogramElement.class);
		expect(ctx.getPictogramElement()).andStubReturn(pe);
		IBidirectionalRelation rel = EasyMock.createMock(IBidirectionalRelation.class); 
		ICompilationUnit cu1 = EasyMock.createMock(ICompilationUnit.class);
		ICompilationUnit cu2 = EasyMock.createMock(ICompilationUnit.class);
		PersistentType jpt1 = EasyMock.createMock(PersistentType.class);
		PersistentType jpt2 = EasyMock.createMock(PersistentType.class);

		expect(featureProvider.getBusinessObjectForPictogramElement(pe)).andStubReturn(rel);
		expect(rel.getOwner()).andStubReturn(jpt1);
		expect(rel.getInverse()).andStubReturn(jpt2);
		expect(featureProvider.getCompilationUnit(jpt1)).andStubReturn(cu1);
		expect(featureProvider.getCompilationUnit(jpt2)).andStubReturn(cu2);
		
		IWorkbenchPartSite ws = EasyMock.createMock(IWorkbenchPartSite.class);
		IDiagramBehaviorUI db = EasyMock.createMock(IDiagramBehaviorUI.class);
		IDiagramTypeProvider p = EasyMock.createMock(IDiagramTypeProvider.class);
		IEditor e = EasyMock.createMock(IEditor.class);
		expect(featureProvider.getDiagramTypeProvider()).andStubReturn(p);
		expect(p.getDiagramBehavior()).andStubReturn(db);
		expect(db.getDiagramContainer()).andStubReturn(e);
		expect(e.getSite()).andStubReturn(ws);
		ut.organizeImports(cu1, ws);
		ut.organizeImports(cu2, ws);
		
		replay(featureProvider, ctx, ut, rel, jpt1, jpt2, cu1, cu2, pe, p, e, ws, db);
		
		DeleteRelationFeature feature = new DeleteRelationFeature(featureProvider);		
		feature.postDelete(ctx);
	}
}


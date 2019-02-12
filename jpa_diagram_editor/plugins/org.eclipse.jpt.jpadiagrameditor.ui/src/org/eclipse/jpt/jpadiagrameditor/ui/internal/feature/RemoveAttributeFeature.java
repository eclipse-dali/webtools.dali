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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.context.impl.RemoveContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.features.impl.DefaultRemoveFeature;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;

public class RemoveAttributeFeature extends DefaultRemoveFeature {

	private boolean skipRemoveRelations = false;
	private boolean skipCreateRelations = false;
	private PersistentType jpt = null;
	private ICustomFeature graphicalRemove;

	public RemoveAttributeFeature(IFeatureProvider fp) {
		this(fp, new GraphicalRemoveAttributeFeature(fp));
	}

	public RemoveAttributeFeature(IFeatureProvider fp, boolean skipRemoveRelations, boolean skipCreateRelations) {
		this(fp);
		this.skipRemoveRelations = skipRemoveRelations;
		this.skipCreateRelations = skipCreateRelations;
	}

	public RemoveAttributeFeature(IFeatureProvider fp, ICustomFeature graphicalRemove) {
		super(fp);
		this.graphicalRemove = graphicalRemove;
	}

	public void setKey(String key) {
	}

	@Override
	public boolean isAvailable(IContext context) {
		return false;
	}

	@Override
	public boolean canExecute(IContext context) {
		return false;
	}

	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider) super.getFeatureProvider();
	}

	@Override
	public void preRemove(IRemoveContext context) {
		final PictogramElement pe = context.getPictogramElement();
		if (pe == null) {
			JPADiagramEditorPlugin.logError("PictogramElement is null\n", new Exception());  //$NON-NLS-1$		 							
			return;
		}
		IJPAEditorFeatureProvider fp = getFeatureProvider();
		Object bo = fp.getBusinessObjectForPictogramElement(pe);
		if(bo == null)
			return;
		if (bo instanceof PersistentAttribute) {
			PersistentAttribute jpa = (PersistentAttribute) bo;			
			HashSet<String> ignores = ((JPAEditorFeatureProvider) getFeatureProvider()).getAddIgnore();
			if (!ignores.isEmpty()) {
				Iterator<String> iter = ignores.iterator();
				if (iter.hasNext()) {
					String iterStr = iter.next();
					if (iterStr.endsWith(jpa.getName())) {
						ignores.remove(iterStr);
					}
				}
			}
			
			jpt = (PersistentType) jpa.getParent();
			fp.remove(fp.getKeyForBusinessObject(bo));
			if (!skipRemoveRelations) {
				Set<IRelation> rels = fp.getRelationRelatedToAttribute(jpa, JpaArtifactFactory.instance().getRelTypeName(jpa));
				Iterator<IRelation> iter = rels.iterator();
				while(iter.hasNext()){
					IRelation rel = iter.next();
					removeRelation(rel);
				}
				
				HasReferanceRelation embedRel = fp.getEmbeddedRelationRelatedToAttribute(jpa);
				if(embedRel != null) {
					removeRelation(embedRel);
				}
			}
		}

		ContainerShape entityShape = ((ContainerShape) pe).getContainer().getContainer();
		try{
			graphicalRemoveAttribute(entityShape);
		} catch (Exception e){
			JPADiagramEditorPlugin.logError(e); 
		}
	}

	private void graphicalRemoveAttribute(PictogramElement pe) {
		CustomContext customContext = new CustomContext();
		customContext.setInnerPictogramElement(pe);
		graphicalRemove.execute(customContext);
	}

	private void removeRelation(Object rel) {
		if (rel == null)
			return;
		Connection conn = (Connection) getFeatureProvider().getPictogramElementForBusinessObject(rel);
		RemoveContext ctx = new RemoveContext(conn);
		RemoveRelationFeature ft = new RemoveRelationFeature(getFeatureProvider());
		ft.remove(ctx);
	}

	@Override
	public void postRemove(IRemoveContext context) {
		if (skipCreateRelations)
			return;
		if (jpt == null)
			return;
		JpaArtifactFactory.instance().addNewRelations(getFeatureProvider(), jpt);
		JpaArtifactFactory.instance().rearrangeIsARelations(getFeatureProvider());
	}

}

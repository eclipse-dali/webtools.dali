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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.context.impl.RemoveContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.features.impl.DefaultRemoveFeature;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class RemoveAttributeFeature extends DefaultRemoveFeature {

	private boolean skipRemoveRelations = false;
	private boolean skipCreateRelations = false;
	private JavaPersistentType jpt = null;
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

	public boolean isAvailable(IContext context) {
		return false;
	}

	public boolean canExecute(IContext context) {
		return false;
	}

	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider) super.getFeatureProvider();
	}

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
		
		if (bo instanceof JavaPersistentAttribute) {
			JavaPersistentAttribute jpa = (JavaPersistentAttribute) bo;
			
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
			
			jpt = (JavaPersistentType)jpa.getParent();
			fp.remove(fp.getKeyForBusinessObject(bo));
			if (!skipRemoveRelations) {
				IRelation rel = fp.getRelationRelatedToAttribute(jpa);
				removeRelation(rel);
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

	private void removeRelation(IRelation rel) {
		if (rel == null)
			return;
		Connection conn = (Connection) getFeatureProvider().getPictogramElementForBusinessObject(rel);
		RemoveContext ctx = new RemoveContext(conn);
		RemoveRelationFeature ft = new RemoveRelationFeature(getFeatureProvider());
		ft.remove(ctx);
	}

	public void postRemove(IRemoveContext context) {
		if (skipCreateRelations)
			return;
		if (jpt == null)
			return;
		JpaArtifactFactory.instance().refreshEntityModel(getFeatureProvider(), jpt);

		Collection<IRelation> rels = JpaArtifactFactory.instance()
				.produceAllRelations(jpt, getFeatureProvider());
		Iterator<IRelation> iter = rels.iterator();
		while (iter.hasNext()) {
			IRelation rel = iter.next();
			ContainerShape ownerShape = (ContainerShape) getFeatureProvider().getPictogramElementForBusinessObject(
					rel.getOwner());
			ContainerShape inverseShape = (ContainerShape) getFeatureProvider().getPictogramElementForBusinessObject(
					rel.getInverse());
			AddConnectionContext cntx = new AddConnectionContext(JPAEditorUtil.getAnchor(ownerShape), JPAEditorUtil
					.getAnchor(inverseShape));
			cntx.setNewObject(rel);
			AddRelationFeature ft = new AddRelationFeature(getFeatureProvider());
			ft.add(cntx);
//			JpaArtifactFactory.instance().refreshEntityModel(getFeatureProvider(), jpt);
		}
	}

}
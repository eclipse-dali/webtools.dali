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
import java.util.Iterator;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;


public class MoveEntityShapeFeature extends DefaultMoveShapeFeature {

	public MoveEntityShapeFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	protected void moveAllBendpoints(IMoveShapeContext context) {
	}
	
	protected void postMoveShape(IMoveShapeContext context) {
		ContainerShape cs = (ContainerShape)context.getShape();
		JPAEditorUtil.rearrangeAllConnections(cs, getFeatureProvider(), false);
		Collection<ContainerShape> css = JPAEditorUtil.getRelatedShapes(cs);
		Iterator<ContainerShape> csIt = css.iterator();
		while (csIt.hasNext()) {
			ContainerShape cs1 = csIt.next();
			JPAEditorUtil.rearrangeAllConnections(cs1, getFeatureProvider(), true);
		}
	}	
	
		@Override
	 	protected void internalMove(final IMoveShapeContext context) {
	 		PictogramElement el = context.getPictogramElement();
	 		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(el);
	 		ted.getCommandStack().execute(new RecordingCommand(ted) {
	 			protected void doExecute() {
	 			   move(context);
	 			}
	 		});
	 	}
	 	
	 	public void move(IMoveShapeContext context){
	 		super.internalMove(context);
	 	}
	
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return  (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}	

}

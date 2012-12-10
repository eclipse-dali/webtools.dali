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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.impl.RemoveContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;

public abstract class RemoveAllEntitiesFeature extends AbstractCustomFeature {

	protected Set<Shape> allShapes = new HashSet<Shape>();
	
	public RemoveAllEntitiesFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	public abstract String getConfirmationText();

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}
	
	public void execute(ICustomContext context) {
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(getFeatureProvider().getDiagram());
		Iterator<Shape> it = allShapes.iterator();
		while (it.hasNext()) {
			Shape sh = it.next();
			final RemoveJPAEntityFeature ft = new RemoveJPAEntityFeature(this.getFeatureProvider(), false);
			final IRemoveContext ctx = new RemoveContext(sh);
			ted.getCommandStack().execute(new RecordingCommand(ted) {
				@Override
				protected void doExecute() {
					ft.remove(ctx);
				}
			});
		}
	}

	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider) super.getFeatureProvider();
	}

}

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

import java.util.Iterator;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.impl.RemoveContext;
import org.eclipse.graphiti.features.impl.AbstractFeature;
import org.eclipse.graphiti.internal.Messages;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.AbstractRelation;


@SuppressWarnings("restriction")
public class RemoveRelationFeature extends AbstractFeature implements IRemoveFeature {
	
	private static final String NAME = Messages.DefaultRemoveFeature_0_xfld;	

	public RemoveRelationFeature(IFeatureProvider fp) {
		super(fp);
	}
	
    public boolean isAvailable(IContext context) {
        return true;
    }	

    public boolean canRemove(IRemoveContext context) {
    	return true;
    }
    
    public boolean canExecute(IContext context) {
    	return true;
    }
    
    public void preRemove(IRemoveContext context) {
    	PictogramElement pe = context.getPictogramElement();
    	Object bo = getFeatureProvider().getBusinessObjectForPictogramElement(pe);
    	if (bo instanceof AbstractRelation) {
    		AbstractRelation rel = (AbstractRelation)bo;
    		getFeatureProvider().remove(rel.getId());
    	}    	
    }    
        
	public final void remove(final IRemoveContext context) {
		if (!getUserDecision()) {
			return;
		}
		preRemove(context);
		final PictogramElement pe = context.getPictogramElement();
		if (pe == null) 
			return;
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(pe);
		RecordingCommand rc = new RecordingCommand(ted) {
			protected void doExecute() {
				if (pe instanceof Shape) {
					Shape shape = (Shape) pe;
					removeAllConnections(shape);
				}
				Graphiti.getPeService().deletePictogramElement(pe);
				postRemove(context);
			}
		};
		ted.getCommandStack().execute(rc);
	}
    
	protected void removeAllConnections(Shape shape) {
		IFeatureProvider featureProvider = getFeatureProvider();
		for (Iterator<Anchor> iter = shape.getAnchors().iterator(); iter.hasNext();) {
			Anchor anchor = iter.next();
			for (Iterator<Connection> iterator = Graphiti.getPeService().getAllConnections(anchor).iterator(); iterator.hasNext();) {
				Connection connection = iterator.next();
				if (connection.eResource() != null) {
					IRemoveContext rc = new RemoveContext(connection);
					IRemoveFeature removeFeature = featureProvider.getRemoveFeature(rc);
					if (removeFeature != null) {
						ConnectionDecorator decorators[] = connection.getConnectionDecorators().toArray(new ConnectionDecorator[0]);
						for (ConnectionDecorator decorator : decorators) {
							if (decorator != null && (decorator.eResource() != null)) {
								EcoreUtil.delete(decorator, true);
							}
						}
						removeFeature.remove(rc);
					}
				}
			}
		}
	}

	public void postRemove(IRemoveContext context) {
	}
	
	@Override
	public String getName() {
		return NAME;
	}
		
    
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return  (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}    
    

	public void execute(IContext context) {
		if (context instanceof IRemoveContext) {
			remove((IRemoveContext)context);
		}
	}
	
}

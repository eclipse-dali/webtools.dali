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

import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.impl.DeleteContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.AbstractRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.BidirectionalRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.UnidirectionalRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJPAEditorUtil;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchSite;


public class DeleteRelationFeature extends DefaultDeleteFeature{
	
	private static IJPAEditorUtil ut = null;
		
	public DeleteRelationFeature(IJPAEditorFeatureProvider fp) {
		super(fp);
		ut = fp.getJPAEditorUtil();
	}
	
	public boolean canDelete(IDeleteContext context) {
		return true;
	}
	
	public boolean isAvailable(IContext context) {
		return true;
	}
	
	public boolean canExecute(IContext context) {
		return true;
	}	
	
    protected String getQuestionToUser() {
    	return JPAEditorMessages.DeleteRelationFeature_deleteRelationQuestion;
    }
	
    public void delete(IDeleteContext context) {
        PictogramElement pe = context.getPictogramElement();
        Object businessObjectForPictogramElement = getBusinessObjectForPictogramElement(pe);
        if (businessObjectForPictogramElement != null) {
            if (!getUserDecision(context)) {
                return;
            }
        }

        preDelete(context);
        AbstractRelation rel = (AbstractRelation)businessObjectForPictogramElement;
		
        if (rel instanceof UnidirectionalRelation) {
        	UnidirectionalRelation relation = (UnidirectionalRelation)rel;
        	ClickRemoveAttributeButtonFeature feat = new ClickRemoveAttributeButtonFeature(getFeatureProvider());
    		JavaPersistentAttribute attribute = relation.getAnnotatedAttribute();
    		PictogramElement textShape = getFeatureProvider().getPictogramElementForBusinessObject(attribute);
    		IDeleteContext delCtx = new DeleteContext(textShape);    		
    		feat.delete(delCtx, false);
    	}    	

        if (rel instanceof BidirectionalRelation) { 			
        	BidirectionalRelation relation = (BidirectionalRelation)(rel);
        	ClickRemoveAttributeButtonFeature feat = new ClickRemoveAttributeButtonFeature(getFeatureProvider());
        	
    		JavaPersistentAttribute ownerAttribute = relation.getOwnerAnnotatedAttribute();
    		PictogramElement ownerAttributeTextShape = getFeatureProvider().getPictogramElementForBusinessObject(ownerAttribute);
    		IDeleteContext deleteOwnerAttributeContext = new DeleteContext(ownerAttributeTextShape);
    		feat.delete(deleteOwnerAttributeContext, false);
    		
    		JavaPersistentAttribute inverseAttribute = relation.getInverseAnnotatedAttribute();
    		PictogramElement inverseAttributeTextShape = getFeatureProvider().getPictogramElementForBusinessObject(inverseAttribute);
    		IDeleteContext deleteInverseAttributeContext = new DeleteContext(inverseAttributeTextShape);
    		feat.delete(deleteInverseAttributeContext, false);    		
    	}    	

        postDelete(context);
        
        /*
        IRemoveContext rc = new RemoveContext(pe);
        IFeatureProvider featureProvider = getFeatureProvider();
        IRemoveFeature removeFeature = featureProvider.getRemoveFeature(rc);
        if (removeFeature != null) {
            removeFeature.remove(rc);
        }
        */
        
        
    }	
    
	public void postDelete(IDeleteContext context) {
        PictogramElement pe = context.getPictogramElement();
        Object businessObjectForPictogramElement = getBusinessObjectForPictogramElement(pe);
        IRelation rel = (IRelation)businessObjectForPictogramElement;	
		IWorkbenchSite ws = ((IEditorPart)getDiagramEditor()).getSite();
		ICompilationUnit cu = getFeatureProvider().getCompilationUnit(rel.getOwner());		
        ut.organizeImports(cu, ws);        
		if (rel instanceof BidirectionalRelation) {
			cu = getFeatureProvider().getCompilationUnit(rel.getInverse());
			ut.organizeImports(cu, ws);  
		}
	}
    
    
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return  (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}    
	
}
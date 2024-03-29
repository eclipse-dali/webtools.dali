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

import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.impl.DeleteContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.AbstractRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IBidirectionalRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IUnidirectionalRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IsARelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.ui.PlatformUI;


public class DeleteRelationFeature extends DefaultDeleteFeature{
	
	private static IJPAEditorUtil ut = null;
		
	public DeleteRelationFeature(IJPAEditorFeatureProvider fp) {
		super(fp);
		ut = fp.getJPAEditorUtil();
	}
	
	@Override
	public boolean canDelete(IDeleteContext context) {
		return true;
	}
	
	@Override
	public boolean isAvailable(IContext context) {
		return true;
	}
	
	@Override
	public boolean canExecute(IContext context) {
		return true;
	}	
	
    protected String getQuestionToUser() {
    	return JPAEditorMessages.DeleteRelationFeature_deleteRelationQuestion;
    }
	
    @Override
	public void delete(IDeleteContext context) {
        PictogramElement pe = context.getPictogramElement();
        Object businessObjectForPictogramElement = getBusinessObjectForPictogramElement(pe);
        if (businessObjectForPictogramElement != null) {
            if (!getUserDecision(context)) {
                return;
            }
        }

        preDelete(context);
        
        if(businessObjectForPictogramElement instanceof AbstractRelation) {
        	deleteAbstractRelation(businessObjectForPictogramElement);   
        } else if (businessObjectForPictogramElement instanceof HasReferanceRelation){
        	deleteEmbeddedRelation(businessObjectForPictogramElement);
        } else if (businessObjectForPictogramElement instanceof IsARelation) {
        	deleteInheritanceRelation(businessObjectForPictogramElement);
        }

        postDelete(context);
    }

    private void deleteInheritanceRelation(Object businessObjectForPictogramElement) {
		IsARelation rel = (IsARelation)businessObjectForPictogramElement;
		PersistentType superclass = rel.getSuperclass();
		PersistentType subclass = rel.getSubclass();
		JpaArtifactFactory.instance().buildHierarchy(superclass, subclass, false);
		
		ut.getCompilationUnit(subclass);
//		subclass.getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
		
		getFeatureProvider().addJPTForUpdate(subclass.getName());
	}
    
	private void deleteEmbeddedRelation(Object businessObjectForPictogramElement) {
		HasReferanceRelation rel = (HasReferanceRelation)businessObjectForPictogramElement;
		PersistentAttribute attribute = rel.getEmbeddedAnnotatedAttribute();
		PictogramElement textShape = getFeatureProvider().getPictogramElementForBusinessObject(attribute);
		if(textShape == null)
			return;
		ClickRemoveAttributeButtonFeature feat = new ClickRemoveAttributeButtonFeature(getFeatureProvider());
		IDeleteContext delCtx = new DeleteContext(textShape);    		
		feat.delete(delCtx, false);
	}

	private void deleteAbstractRelation(Object businessObjectForPictogramElement) {
		AbstractRelation rel = (AbstractRelation)businessObjectForPictogramElement;
		
        if (rel instanceof IUnidirectionalRelation) {
        	IUnidirectionalRelation relation = (IUnidirectionalRelation)rel;
    		PersistentAttribute attribute = relation.getAnnotatedAttribute();
    		PictogramElement textShape = getFeatureProvider().getPictogramElementForBusinessObject(attribute);
    		if(textShape == null)
    			return;
        	ClickRemoveAttributeButtonFeature feat = new ClickRemoveAttributeButtonFeature(getFeatureProvider());
    		IDeleteContext delCtx = new DeleteContext(textShape);    		
    		feat.delete(delCtx, false);
    	}    	

        if (rel instanceof IBidirectionalRelation) { 			
        	IBidirectionalRelation relation = (IBidirectionalRelation)(rel);
        	ClickRemoveAttributeButtonFeature feat = new ClickRemoveAttributeButtonFeature(getFeatureProvider());
    		PersistentAttribute ownerAttribute = relation.getOwnerAnnotatedAttribute();
    		PictogramElement ownerAttributeTextShape = getFeatureProvider().getPictogramElementForBusinessObject(ownerAttribute);
    		if(ownerAttributeTextShape == null)
    			return;
    		IDeleteContext deleteOwnerAttributeContext = new DeleteContext(ownerAttributeTextShape);
    		feat.delete(deleteOwnerAttributeContext, false);

    		PersistentAttribute inverseAttribute = relation.getInverseAnnotatedAttribute();
    		PictogramElement inverseAttributeTextShape = getFeatureProvider().getPictogramElementForBusinessObject(inverseAttribute);
    		if(inverseAttributeTextShape == null)
    			return;
    		IDeleteContext deleteInverseAttributeContext = new DeleteContext(inverseAttributeTextShape);
    		feat.delete(deleteInverseAttributeContext, false);
    	}
	}	

	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return  (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}    
	
	@Override
	protected boolean getUserDecision(IDeleteContext context) {
		return MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				JPAEditorMessages.DeleteFeature_deleteConfirm, JPAEditorMessages.DeleteRelationFeature_deleteRelationQuestion);
	}
	
}

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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.AbstractRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IBidirectionalRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IUnidirectionalRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IsARelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;


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
		JavaPersistentType superclass = rel.getSuperclass();
		JavaPersistentType subclass = rel.getSubclass();
		JpaArtifactFactory.instance().buildHierarchy(superclass, subclass, false);
		
		JPAEditorUtil.getCompilationUnit(subclass);
//		subclass.getJavaResourceType().getJavaResourceCompilationUnit().synchronizeWithJavaSource();
		
		getFeatureProvider().addJPTForUpdate(subclass.getName());
	}
    
	private void deleteEmbeddedRelation(Object businessObjectForPictogramElement) {
		HasReferanceRelation rel = (HasReferanceRelation)businessObjectForPictogramElement;
		JavaPersistentAttribute attribute = rel.getEmbeddedAnnotatedAttribute();
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
    		JavaPersistentAttribute attribute = relation.getAnnotatedAttribute();
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
        	
    		JavaPersistentAttribute ownerAttribute = relation.getOwnerAnnotatedAttribute();
    		PictogramElement ownerAttributeTextShape = getFeatureProvider().getPictogramElementForBusinessObject(ownerAttribute);
    		if(ownerAttributeTextShape == null)
    			return;
    		IDeleteContext deleteOwnerAttributeContext = new DeleteContext(ownerAttributeTextShape);
    		feat.delete(deleteOwnerAttributeContext, false);
    		
    		JavaPersistentAttribute inverseAttribute = relation.getInverseAnnotatedAttribute();
    		PictogramElement inverseAttributeTextShape = getFeatureProvider().getPictogramElementForBusinessObject(inverseAttribute);
    		if(inverseAttributeTextShape == null)
    			return;
    		IDeleteContext deleteInverseAttributeContext = new DeleteContext(inverseAttributeTextShape);
    		feat.delete(deleteInverseAttributeContext, false);
    	}
	}	
    
	public void postDelete(IDeleteContext context) {
        PictogramElement pe = context.getPictogramElement();
        Object businessObjectForPictogramElement = getBusinessObjectForPictogramElement(pe);
		IWorkbenchSite ws = ((IEditorPart) getDiagramEditor()).getSite();
		if (businessObjectForPictogramElement instanceof IRelation) {
			IRelation rel = (IRelation) businessObjectForPictogramElement;
			ICompilationUnit cu = getFeatureProvider().getCompilationUnit(rel.getOwner());
			ut.organizeImports(cu, ws);
			if (rel instanceof IBidirectionalRelation) {
				cu = getFeatureProvider().getCompilationUnit(rel.getInverse());
				ut.organizeImports(cu, ws);
			}
		} else if(businessObjectForPictogramElement instanceof HasReferanceRelation){
			HasReferanceRelation rel = (HasReferanceRelation) businessObjectForPictogramElement;
			ICompilationUnit cu = getFeatureProvider().getCompilationUnit(rel.getEmbeddingEntity());
			ut.organizeImports(cu, ws); 
		} else if (businessObjectForPictogramElement instanceof IsARelation){
			IsARelation rel = (IsARelation) businessObjectForPictogramElement;
			ICompilationUnit cu = getFeatureProvider().getCompilationUnit(rel.getSubclass());
			ut.organizeImports(cu, ws); 
		}
	}
    
    
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return  (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}    
	
	protected boolean getUserDecision(IDeleteContext context) {
		return MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				JPAEditorMessages.DeleteFeature_deleteConfirm, JPAEditorMessages.DeleteRelationFeature_deleteRelationQuestion);
	}
	
}
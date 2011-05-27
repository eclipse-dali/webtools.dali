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

import java.text.MessageFormat;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.internal.features.context.impl.base.PictogramElementContext;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;



@SuppressWarnings("restriction")
public class ClickRemoveAttributeButtonFeature extends DefaultDeleteFeature {

	private String attrName = ""; //$NON-NLS-1$
	
	public ClickRemoveAttributeButtonFeature(IFeatureProvider provider) {
		super(provider);
	}	
		
    protected String getQuestionToUser() {
    	return MessageFormat.format(JPAEditorMessages.ClickRemoveAttributeButtonFeature_deleteAttributeQuestion, new Object[] {attrName}); 
    }
	
	
	public boolean canUndo(IContext context) {
		return false;
	}
	
	private String getAttrName(ContainerShape textShape) {
		String txt = ((Text)(textShape.getGraphicsAlgorithm().getGraphicsAlgorithmChildren().get(0))).getValue();
		String attrName = txt.substring(txt.indexOf(':') + 1);
		attrName = attrName.trim();	
		return attrName;
	}
		
	private void deleteAttribute(ContainerShape pe, String attrName) {
		JavaPersistentType jpt = (JavaPersistentType)getFeatureProvider().getBusinessObjectForPictogramElement(pe.getContainer().getContainer());		
		JpaArtifactFactory.instance().deleteAttribute(jpt, attrName, getFeatureProvider());
	}	
	
    public void delete(IDeleteContext context) {
    	delete(context, true);
    }
    
	@Override
	public void preDelete(IDeleteContext context) {
		super.preDelete(context);
	}
    
    public void delete(IDeleteContext context, boolean haveToAsk) {
		PictogramElementContext ctx = (PictogramElementContext)context;
		ContainerShape textShape = (ContainerShape)ctx.getPictogramElement();	
		if ((textShape == null) || textShape.getGraphicsAlgorithm() == null) 
			return;
		String attrName = getAttrName(textShape);
		this.attrName = attrName;
    	if (haveToAsk) {
    		if (!getUserDecision(context)) {
    			return;
    		}
    	}    	
    	
        preDelete(context); 
        if (textShape.getGraphicsAlgorithm() == null){
        	return;
        }        
		deleteAttribute(textShape, attrName);
    }

    
    protected void deleteBusinessObjects(Object[] businessObjects) {
        if (businessObjects != null) {
            for (Object bo : businessObjects) {
                deleteBusinessObject(bo);
            }
        }
    }    
		
	public boolean isAvailable(IContext context) {
		return true;
	}

	public String getName() {
		return JPAEditorMessages.ClickRemoveAttributeButtonFeature_createAttributeButtonlabel; 
	}

	public String getDescription() {
		return JPAEditorMessages.ClickRemoveAttributeButtonFeature_createAttributeButtonDescription; 
	}
	
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}

		
}
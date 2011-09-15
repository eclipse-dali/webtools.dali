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

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchSite;


public class ClickAddAttributeButtonFeature extends AbstractCreateFeature {
	
	public ClickAddAttributeButtonFeature(IFeatureProvider provider) {
		super(provider, "", "");	//$NON-NLS-1$ //$NON-NLS-2$
	}
	
	public boolean canExecute(IContext context) {
		return true;
	}

	public boolean canUndo(IContext context) {
		return false;
	}

	public boolean canCreate(ICreateContext context) {
		return true;
	}
	
	public Object[] create(ICreateContext context) {
		ContainerShape entityShape = context.getTargetContainer();
		JavaPersistentType jpt = (JavaPersistentType)getFeatureProvider().getBusinessObjectForPictogramElement(entityShape);
		String newAttrName = JpaArtifactFactory.instance().createNewAttribute(jpt, false, getFeatureProvider());
		JpaArtifactFactory.instance().refreshEntityModel(getFeatureProvider(), jpt);
		JavaPersistentAttribute newAttr = jpt.getAttributeNamed(newAttrName);
		int cnt = 0;
		while ((newAttr == null) && (cnt < 25)) { 
			newAttr = jpt.getAttributeNamed(newAttrName);
			if (newAttr == null) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					JPADiagramEditorPlugin.logError("Thread sleep interrupted", e);  //$NON-NLS-1$		 
				}
			}
			cnt++;
		}
		getFeatureProvider().addAddIgnore((JavaPersistentType)newAttr.getParent(), newAttr.getName());
		addGraphicalRepresentation(context, newAttr);
        getFeatureProvider().getDirectEditingInfo().setActive(true);
        
        IWorkbenchSite ws = ((IEditorPart)getDiagramEditor()).getSite();
        ICompilationUnit cu = getFeatureProvider().getCompilationUnit(jpt);
        getFeatureProvider().getJPAEditorUtil().formatCode(cu, ws);

		return new Object[] {newAttr};
	}	

	public boolean isAvailable(IContext context) {
		return true;
	}

	public String getName() {
		return JPAEditorMessages.ClickAddAttributeButtonFeature_createAttributeButtonLabel;  			
	}

	public String getDescription() {
		return JPAEditorMessages.ClickAddAttributeButtonFeature_createAttributeButtonDescription;  
	}
	
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}
	
	
}
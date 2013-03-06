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

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IMultiDeleteInfo;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.ui.PlatformUI;


public class DeleteJPAEntityFeature extends DefaultDeleteFeature {
	
	private String entityClassName = null;
	private String entityName = null;
	public DeleteJPAEntityFeature(IFeatureProvider fp) {
		super(fp);		
	}
	
    @Override
	public void delete(final IDeleteContext context) {
    	PictogramElement pe = context.getPictogramElement();
    	
    	PersistentType jpt = (PersistentType)getFeatureProvider().getBusinessObjectForPictogramElement(pe);
    	entityClassName = jpt.getName();
    	entityName = JPAEditorUtil.returnSimpleName(JpaArtifactFactory.instance().getEntityName(jpt));
    	TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(pe);
    	ted.getCommandStack().execute(new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				deleteEl(context);
			}
		});
    	
    }
    	
    public void deleteEl(IDeleteContext context){
    	super.delete(context);
    }
	
    @Override
	protected void deleteBusinessObject(Object bo) {
    	PersistentType jpt = null;
		if (bo instanceof PersistentType) {
			jpt = (PersistentType) bo;
			
		
			JpaProject jpaProject = jpt.getJpaProject();
			String name = jpt.getName();
			
			JpaArtifactFactory.instance().deletePersistentTypeFromORMXml(jpaProject, jpt);
//			JpaArtifactFactory.instance().forceSaveEntityClass(jpt, getFeatureProvider());
			JpaArtifactFactory.instance().deleteEntityClass(jpt, getFeatureProvider());
			if (! JpaPreferences.getDiscoverAnnotatedClasses(jpt.getJpaProject().getProject())) {
				JPAEditorUtil.createUnregisterEntityFromXMLJob(jpaProject, name);
			}
						
		} 	
    }

	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}		    
	
	@Override
	protected boolean getUserDecision(IDeleteContext context) {
		String msg = "";  //$NON-NLS-1$
		IMultiDeleteInfo multiDeleteInfo = context.getMultiDeleteInfo();
		if (multiDeleteInfo != null) {
			msg = JPAEditorMessages.DeleteJPAEntityFeature_deleteJPAEntitiesQuestion;
		} else {
			if (entityClassName != null && entityClassName.length() > 0) {
				msg = MessageFormat.format(JPAEditorMessages.DeleteJPAEntityFeature_deleteJPAEntityQuestion, entityName, entityClassName);
			}
		}
		return MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				JPAEditorMessages.DeleteFeature_deleteConfirm, msg);
	}

	
	
}
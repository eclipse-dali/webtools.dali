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
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class DeleteJPAEntityFeature extends DefaultDeleteFeature {
	
	private String entityName = null;
	public DeleteJPAEntityFeature(IFeatureProvider fp) {
		super(fp);		
	}
	
    public void delete(final IDeleteContext context) {
    	PictogramElement pe = context.getPictogramElement();
    	
    	JavaPersistentType jpt = (JavaPersistentType)getFeatureProvider().getBusinessObjectForPictogramElement(pe);
    	entityName = jpt.getName();
    	TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(pe);
    	ted.getCommandStack().execute(new RecordingCommand(ted) {
			protected void doExecute() {
				deleteEl(context);
			}
		});
    	
    }
    	
    public void deleteEl(IDeleteContext context){
    	super.delete(context);
    }
	
    protected void deleteBusinessObject(Object bo) {
    	JavaPersistentType jpt = null;
		if (bo instanceof JavaPersistentType) {
			jpt = (JavaPersistentType) bo;
			
		
			JpaProject jpaProject = jpt.getJpaProject();
			String name = jpt.getName();
			
			
			JpaArtifactFactory.instance().forceSaveEntityClass(jpt, getFeatureProvider());
			JpaArtifactFactory.instance().deleteEntityClass(jpt, getFeatureProvider());
			if (!JptJpaCorePlugin.discoverAnnotatedClasses(jpt.getJpaProject().getProject())) {
				JPAEditorUtil.createUnregisterEntityFromXMLJob(jpaProject, name);
			}
						
		} 	
    }
    
    public String getQuestionToUser() {
    	return MessageFormat.format(JPAEditorMessages.DeleteJPAEntityFeature_deleteJPAEntityQuestion, new Object[] { entityName });
    }
    
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}		    
	
}
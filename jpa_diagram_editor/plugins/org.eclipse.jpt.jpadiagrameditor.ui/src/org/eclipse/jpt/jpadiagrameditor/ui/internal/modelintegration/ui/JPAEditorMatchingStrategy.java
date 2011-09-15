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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.IModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJPADiagramEditorInput;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;


public class JPAEditorMatchingStrategy implements IEditorMatchingStrategy {
	
	private IJPAEditorFeatureProvider fp = null;
	
	private static final String CODE_GENERATED = "CODE_GENERATED"; 	//$NON-NLS-1$
	public static final String DOUBLE_CLICK = "DOUBLE_CLICK";		//$NON-NLS-1$
	
	
	public JPAEditorMatchingStrategy() {}
	
	/**
	 * This constructor is intended solely for the test purposes
	 *  
	 * @param fp the feature provider
	 */
	public JPAEditorMatchingStrategy(IJPAEditorFeatureProvider fp) {
		this.fp = fp;
	}
	
	public boolean matches(IEditorReference editorRef, IEditorInput input) {
		if (input instanceof IFileEditorInput) {
			IFileEditorInput fileInput = (IFileEditorInput) input;
			IFile entityFile = fileInput.getFile();
			if(!entityFile.getName().endsWith(".java")){ //$NON-NLS-1$
				return false;
			}
			try {
				QualifiedName qn = new QualifiedName(null, DOUBLE_CLICK);
				if ("true".equals(entityFile.getSessionProperty(qn))) { //$NON-NLS-1$
					entityFile.setSessionProperty(qn, null);
					return false;
				}
			} catch (CoreException e1) {
				JPADiagramEditorPlugin.logError("Cannot get session property DOUBLE_CLICK", e1);  //$NON-NLS-1$		 							
			}
			JavaPersistentType inputJptType = null; 
			
			if (fp == null) {
				inputJptType = JPAEditorUtil.getJPType(JavaCore.createCompilationUnitFrom(entityFile));
			} else {
				inputJptType = fp.getJPAEditorUtil().getJPType(JavaCore.createCompilationUnitFrom(entityFile));
			}
			if (inputJptType == null)
				return false;
			PersistenceUnit persistenceUnit = inputJptType.getPersistenceUnit();
			JpaProject jpaProject = persistenceUnit.getJpaProject();

			try {
				IEditorInput editorInput = editorRef.getEditorInput();
				if (editorInput instanceof IJPADiagramEditorInput) {
					IJPADiagramEditorInput jpInput = (IJPADiagramEditorInput) editorInput;
					Diagram diagram = jpInput.getDiagram();
					if (diagram != null) {
						
						IJPAEditorFeatureProvider featureProvider = null;
						IDiagramTypeProvider diagramProvider = null;
						if (fp != null) {
							featureProvider = this.fp;
							diagramProvider = featureProvider.getDiagramTypeProvider();
						} else {
							diagramProvider = ModelIntegrationUtil.getProviderByDiagram(diagram.getName());
							featureProvider = (IJPAEditorFeatureProvider)diagramProvider.getFeatureProvider();
						}
						IModelIntegrationUtil moinIntegrationUtil = featureProvider.getMoinIntegrationUtil();
						JpaProject jpaProjectFromEditor = moinIntegrationUtil.getProjectByDiagram(diagram);
						if (jpaProject.equals(jpaProjectFromEditor)) {
							if (fileInput.getName() != CODE_GENERATED) 
								return false;
							PictogramElement pe = featureProvider.getPictogramElementForBusinessObject(inputJptType);
							if (pe != null) {
								diagramProvider.getDiagramEditor().setPictogramElementForSelection(pe);
								return true;
							}
							
							ICustomFeature feature = featureProvider.getAddAllEntitiesFeature();
							CustomContext context = new CustomContext();
							feature.execute(context);
							return true;
						}
					}
				}
			} catch (PartInitException e) {
				JPADiagramEditorPlugin.getDefault().getLog().log(e.getStatus());
			}
		} else if (input instanceof IJPADiagramEditorInput) {
			return editorRef.getPartName().equals(((IJPADiagramEditorInput) input).getProjectName());
		}
		return false;
	}

}

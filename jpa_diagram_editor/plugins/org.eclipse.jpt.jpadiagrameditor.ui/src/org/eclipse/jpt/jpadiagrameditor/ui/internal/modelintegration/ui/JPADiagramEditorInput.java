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
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.ui.editor.DiagramEditorInput;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJPADiagramEditorInput;


public class JPADiagramEditorInput extends DiagramEditorInput 
									implements IJPADiagramEditorInput {
	
	private Diagram diagram;
	private String projectName;
	

	public JPADiagramEditorInput(Diagram diagram, 
								 String diagramUriString, 
								 TransactionalEditingDomain domain, 
								 String providerId, 
								 boolean disposeEditingDomain) {
		
		super(diagramUriString, domain, providerId, disposeEditingDomain);
		this.diagram = diagram; 
		this.projectName = ModelIntegrationUtil.getProjectByDiagram(diagram).getName();		
	}

	public JPADiagramEditorInput(Diagram diagram,
								 URI diagramUri, 
								 TransactionalEditingDomain domain, 
								 String providerId, 
								 boolean disposeEditingDomain) {
		
		super(diagramUri, domain, providerId, disposeEditingDomain);
		this.diagram = diagram;
		this.projectName = ModelIntegrationUtil.getProjectByDiagram(diagram).getName();
	}
	
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if (adapter.equals(EObject.class)) {
			return getDiagram();
		} 
		if (adapter.equals(Diagram.class)) {
			return getDiagram();
		}
		if (adapter.equals(TransactionalEditingDomain.class))
			return ModelIntegrationUtil.getTransactionalEditingDomain(diagram);
		if (adapter.equals(IFile.class)) {
			Resource eResource = diagram.eResource();
			URI eUri = eResource.getURI();
			if (eUri.isPlatformResource()) {
				String platformString = eUri.toPlatformString(true);
				return ResourcesPlugin.getWorkspace().getRoot()
						.findMember(platformString);
			} else {
				IProject project = ModelIntegrationUtil.getProjectByDiagram(
						diagram).getProject();
				return findXMLFile(project);
			}
		}
		return null;
	}
	
	private IFile findXMLFile(IProject project){
		try {
			IResource[] resources = project.members();
			for (IResource res : resources) {
				if (res instanceof IFolder) {
					IFile existingXMLFile = ((IFolder) res)
							.getFile(diagram.getName()
									+ "." + ModelIntegrationUtil.DIAGRAM_XML_FILE_EXTENSION); //$NON-NLS-1$
					if (existingXMLFile != null && existingXMLFile.exists()) {
						return existingXMLFile;
					}
				}
			}
		} catch (CoreException e) {
			JPADiagramEditorPlugin
					.logError(
							JPAEditorMessages.EntitiesCoordinatesXML_CannotObtainProjectErrorMSG,
							e);
		}
		IFile existingXMLFile = project.getFile(ModelIntegrationUtil
				.getDiagramsXMLFolderPath(project)
				.append(diagram.getName())
				.addFileExtension(
						ModelIntegrationUtil.DIAGRAM_XML_FILE_EXTENSION));
		if (existingXMLFile != null && existingXMLFile.exists()) {
			return existingXMLFile;
		}
		return null;
	}
	
	public Diagram getDiagram() {
		return diagram;
	}	
	
	public static JPADiagramEditorInput createEditorInput(Diagram diagram, TransactionalEditingDomain domain, String providerId,
			boolean disposeEditingDomain) {
		final Resource resource = diagram.eResource();
		if (resource == null) {
			throw new IllegalArgumentException();
		}
		final String fragment = resource.getURIFragment(diagram);
		final URI fragmentUri = resource.getURI().appendFragment(fragment);
		JPADiagramEditorInput diagramEditorInput;
		diagramEditorInput = new JPADiagramEditorInput(diagram, fragmentUri, domain, providerId, disposeEditingDomain);
		return diagramEditorInput;
	}
	

	public String getProjectName() {
		return projectName;
	}
}

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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.ui.editor.DiagramEditorInput;
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

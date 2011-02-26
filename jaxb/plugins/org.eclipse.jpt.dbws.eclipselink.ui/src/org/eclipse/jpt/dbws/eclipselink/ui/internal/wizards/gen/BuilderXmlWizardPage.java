/*******************************************************************************
* Copyright (c) 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.ui.internal.wizards.gen;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.common.ui.internal.wizards.JavaProjectWizardPage;
import org.eclipse.jpt.dbws.eclipselink.ui.internal.JptDbwsUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.common.uriresolver.internal.util.URIHelper;

/**
 *  BuilderXmlWizardPage
 */
public class BuilderXmlWizardPage extends WizardPage {
	
	private final IStructuredSelection initialSelection;
	private IProject targetProject;
	
	protected SelectFileOrXMLCatalogIdPanel selectSourcePanel;

	protected static final String[] browseXMLFilterExtensions = {".xml"}; //$NON-NLS-1$

	// ********** static method **********
	
    public static IFile getBuilderXmlFromSelection(IStructuredSelection selection) {
		Object firstElement = selection.getFirstElement();
		if(firstElement instanceof IFile) {
			String elementExtension = ((IFile)firstElement).getFileExtension();
			if(elementExtension != null) {
				if(browseXMLFilterExtensions[0].endsWith(elementExtension)) {
					return ((IFile)firstElement);
				}
			}
		}
		return null;
    }

	// ********** constructor **********
    
	BuilderXmlWizardPage(IStructuredSelection selection) {
		super("BuilderXmlWizardPage"); //$NON-NLS-1$
		
		this.initialSelection = selection;
	}
	
	// ********** IDialogPage implementation  **********
	
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, HELP_CONTEXT_ID);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		this.setControl(composite);

		this.selectSourcePanel = new SelectFileOrXMLCatalogIdPanel(composite, this.initialSelection);
		this.selectSourcePanel.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		SelectFileOrXMLCatalogIdPanel.PanelListener listener = new SelectFileOrXMLCatalogIdPanel.PanelListener() {
			public void completionStateChanged() {
				selectFileOrXMLCatalogIdPanelChanged();
			}
		};
		this.selectSourcePanel.setListener(listener);
		Dialog.applyDialogFont(parent);
	}

    @Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(visible) {

	    	if(this.getBuilderXml() != null) {
	    		this.selectSourcePanel.setSingleFileViewDefaultSelection(new StructuredSelection(this.getBuilderXml()));
	    	}
	    	else {
	    		this.updateTargetProject();
		    	IFile schema = getBuilderXmlFromSelection(this.initialSelection);
		    	if(schema != null) {
		    		this.selectSourcePanel.setSingleFileViewDefaultSelection(new StructuredSelection(schema));
		    	}
		    	else {
		    		this.selectSourcePanel.setSingleFileViewDefaultSelection(new StructuredSelection(this.targetProject));
		    	}
	    	}
	    	this.selectSourcePanel.update();
			
			this.setTitle(JptDbwsUiMessages.BuilderXmlWizardPage_title);
			this.setDescription(JptDbwsUiMessages.BuilderXmlWizardPage_desc);
			this.selectSourcePanel.setFilterExtensions(browseXMLFilterExtensions);
		}
		this.selectSourcePanel.setVisibleHelper(visible);
	}
    
	// ********** IWizardPage implementation  **********
    
    @Override
	public boolean isPageComplete() {

		return this.schemaOrUriSelected() && (this.getErrorMessage() == null);
	}

	// ********** intra-wizard methods **********
	
	public IFile getBuilderXml() {
		return this.selectSourcePanel.getFile();
	}

	public String getSourceURI() {
		String uri = this.selectSourcePanel.getXMLCatalogURI();
		if(uri == null) {
			IFile file = this.selectSourcePanel.getFile();
			if(file != null) {
				uri = URIHelper.getPlatformURI(file);
			}
		}
		return uri;
	}

	// ********** internal methods **********

	private void updateTargetProject() {
    	IWizardPage previousPage = this.getPreviousPage();
    	
		if(previousPage instanceof JavaProjectWizardPage) {
			// get project from previousPage
			this.targetProject = (((JavaProjectWizardPage)previousPage).getJavaProject()).getProject();
		}
		else if(initialSelection != null && ! this.initialSelection.isEmpty()) {
			// no previousPage - get project from initialSelection
			this.targetProject = this.getProjectFromInitialSelection();
		}		
	}
	
    private IProject getProjectFromInitialSelection() {
		Object firstElement = initialSelection.getFirstElement();
		if(firstElement instanceof IProject) {
			return (IProject)firstElement;
		}
		else if(firstElement instanceof IResource) {
			return ((IResource) firstElement).getProject();
		}
		else if(firstElement instanceof IJavaElement) {
			return ((IJavaElement)firstElement).getJavaProject().getProject();
		}
		return null;
    }

	private boolean schemaOrUriSelected() {
		return ((this.getBuilderXml() != null) || (this.getSourceURI() != null));
	}

	private String computeErrorMessage() {
		String errorMessage = null;
		String uri = this.getSourceURI();
		if(uri != null) {
			if( ! URIHelper.isReadableURI(uri, false)) {
				errorMessage = JptDbwsUiMessages.BuilderXmlWizardPage_errorUriCannotBeLocated;
			}
		}
		return errorMessage;
	}

	private void selectFileOrXMLCatalogIdPanelChanged() {
		String errorMessage = this.computeErrorMessage();
		this.setErrorMessage(errorMessage);
		this.setPageComplete(this.isPageComplete());
	}
}

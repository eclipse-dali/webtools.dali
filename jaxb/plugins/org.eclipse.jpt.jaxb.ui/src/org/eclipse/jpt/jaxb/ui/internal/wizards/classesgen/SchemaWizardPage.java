/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.classesgen;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.common.ui.internal.wizards.JavaProjectWizardPage;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.common.uriresolver.internal.util.URIHelper;

/**
 *  SchemaWizardPage
 */
public class SchemaWizardPage
		extends WizardPage {
	
	protected static final String[] browseXSDFilterExtensions = {".xsd"}; //$NON-NLS-1$
	
	private final IStructuredSelection initialSelection;
	private IProject targetProject;
	
	protected SelectFileOrXMLCatalogIdPanel selectSourcePanel;
	
	
	// ********** static method **********
	
    public static IFile getSourceSchemaFromSelection(IStructuredSelection selection) {
		Object firstElement = selection.getFirstElement();
		if(firstElement instanceof IFile) {
			String elementExtension = ((IFile)firstElement).getFileExtension();
			if(elementExtension != null) {
				if(browseXSDFilterExtensions[0].endsWith(elementExtension)) {
					return ((IFile)firstElement);
				}
			}
		}
		return null;
    }
    
    
	// ********** constructor **********
    
	SchemaWizardPage(IStructuredSelection selection) {
		super("SchemaWizardPage"); //$NON-NLS-1$
		
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
			
			if(this.getSchemaFile() != null) {
	    		this.selectSourcePanel.setSingleFileViewDefaultSelection(new StructuredSelection(this.getSchemaFile()));
	    	}
	    	else {
	    		this.updateTargetProject();
		    	IFile schema = getSourceSchemaFromSelection(this.initialSelection);
		    	if(schema != null) {
		    		this.selectSourcePanel.setSingleFileViewDefaultSelection(new StructuredSelection(schema));
		    	}
		    	else {
		    		this.selectSourcePanel.setSingleFileViewDefaultSelection(new StructuredSelection(this.targetProject));
		    	}
	    	}
	    	this.selectSourcePanel.update();
			
			this.setTitle(JptJaxbUiMessages.SchemaWizardPage_title);
			this.setDescription(JptJaxbUiMessages.SchemaWizardPage_desc);
			this.selectSourcePanel.setFilterExtensions(browseXSDFilterExtensions);
		}
		this.selectSourcePanel.setVisibleHelper(visible);
	}
    
    
	// ********** IWizardPage implementation  **********
    
    @Override
	public boolean isPageComplete() {
    	
		return this.fileOrXmlCatalogEntrySelected() && (this.getErrorMessage() == null);
	}
    
    
	// ********** intra-wizard methods **********
	
    /* return the file, if a file is selected */
	public IFile getSchemaFile() {
		return this.selectSourcePanel.getFile();
	}
	
	/* return xml catalog id, if a catalog entry is selected */
	public String getXMLCatalogId() {
		return this.selectSourcePanel.getXMLCatalogId();
	}
	
	/* return identifier of schema location, whether using file or xml catalog */
	public String getSchemaLocation() {
		IFile file = this.selectSourcePanel.getFile();
		if (file != null) {
			return URI.createPlatformResourceURI(file.getFullPath().toString(), false).toString();
		}
		return this.selectSourcePanel.getXMLCatalogId();
	}
	
	/* return local uri of schema, whether using file or xml catalog */
	public URI getLocalSchemaURI() {
		IFile file = this.selectSourcePanel.getFile();
		if (file != null) {
			return URI.createFileURI(file.getLocation().toString());
		}
		
		String uri = this.selectSourcePanel.getXMLCatalogURI();
		if (uri != null) {
			return CommonPlugin.asLocalURI(URI.createURI(uri));
		}
		
		return null;
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
    
	private boolean fileOrXmlCatalogEntrySelected() {
		return this.getSchemaFile() != null || this.getXMLCatalogId() != null;
	}
	
	private String computeErrorMessage() {
		String errorMessage = null;
		URI uri = this.getLocalSchemaURI();
		if (uri != null) {
			if (! URIHelper.isReadableURI(uri.toString(), false)) {
				errorMessage = JptJaxbUiMessages.SchemaWizardPage_errorUriCannotBeLocated;
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

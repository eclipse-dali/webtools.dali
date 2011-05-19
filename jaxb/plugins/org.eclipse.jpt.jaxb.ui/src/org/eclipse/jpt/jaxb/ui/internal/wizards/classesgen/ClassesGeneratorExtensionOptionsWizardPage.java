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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.core.JaxbFacet;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

/**
 *  ClassesGeneratorExtensionOptionsWizardPage
 */
public class ClassesGeneratorExtensionOptionsWizardPage extends WizardPage
{
	public static final String TARGET_OPTION = "-target "; //$NON-NLS-1$

	private ExtensionOptionsComposite additionalArgsComposite;

	// ********** constructor **********

	protected ClassesGeneratorExtensionOptionsWizardPage() {
		super("Classes Generator Extension Options"); //$NON-NLS-1$
		
		this.initialize();
	}

	protected void initialize() {
		this.setTitle(JptJaxbUiMessages.ClassesGeneratorExtensionOptionsWizardPage_title);
		this.setDescription(JptJaxbUiMessages.ClassesGeneratorExtensionOptionsWizardPage_desc);
	}

	// ********** UI components **********

	public void createControl(Composite parent) {
		this.setPageComplete(true);
		this.setControl(this.buildTopLevelControl(parent));
	}

	private Control buildTopLevelControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());

		this.additionalArgsComposite = new ExtensionOptionsComposite(composite);
		
		return composite;
	}

	// ********** intra-wizard methods **********

	protected boolean allowsExtensions() {
		return this.additionalArgsComposite.allowsExtensions();
	}
	
	protected String getClasspath() {
		return this.additionalArgsComposite.getClasspath();
	}
	
	protected String getAdditionalArgs() {
		return this.additionalArgsComposite.getAdditionalArgs();
	}

	// ********** private methods **********

	private String getFacetVersion() {
		IProjectFacetVersion projectFacetVersion = this.getProjectFacetVersion();
		return (projectFacetVersion == null) ? null : projectFacetVersion.getVersionString();
	}

	private IProjectFacetVersion getProjectFacetVersion() {
		IFacetedProject facetedProject = null;
		try {
			facetedProject = ProjectFacetsManager.create(this.getProject());
		}
		catch(CoreException e) {
			JptJaxbUiPlugin.log(e);
			return null;
		}
		return (facetedProject == null) ? null : facetedProject.getProjectFacetVersion(JaxbFacet.FACET);
	}

	private IProject getProject() {
		return ((ClassesGeneratorWizard)this.getWizard()).getJavaProject().getProject();
	}
	
	// ********** ExtensionOptionsComposite **********

	class ExtensionOptionsComposite {

		private boolean allowsExtensions;
		private final Text classpathText;
		private final Button allowsExtensionsCheckBox;
		
		private final Text additionalArgsText;
		
		// ********** constructor **********
	
		private ExtensionOptionsComposite(Composite parent) {
			super();
			this.allowsExtensions = false;
			
			Composite composite = new Composite(parent, SWT.NONE);
			GridLayout layout = new GridLayout(1, false);
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			composite.setLayout(layout);
			composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			// TODO PlatformUI.getWorkbench().getHelpSystem().setHelp(this.group, HELP_CONTEXT_ID);
			
			this.allowsExtensionsCheckBox = this.buildAllowsExtensionsCheckBox(composite, this.buildAllowsExtensionsSelectionListener());

			// Classpath
			Label classpathLabel = new Label(composite, SWT.NONE);
			classpathLabel.setText(JptJaxbUiMessages.ClassesGeneratorExtensionOptionsWizardPage_classpath);
			GridData gridData = new GridData();
			gridData.verticalIndent = 5;
			classpathLabel.setLayoutData(gridData);
			this.classpathText = this.buildClasspathText(composite);
			
			Label additionalArgsLabel = new Label(composite, SWT.NONE);
			additionalArgsLabel.setText(JptJaxbUiMessages.ClassesGeneratorExtensionOptionsWizardPage_additionalArguments);
			gridData = new GridData();
			gridData.verticalIndent = 5;
			additionalArgsLabel.setLayoutData(gridData);
			this.additionalArgsText = this.buildAdditionalArgsText(composite);
			if( ! StringTools.stringIsEmpty(getFacetVersion())) {
				this.additionalArgsText.setText(TARGET_OPTION + getFacetVersion());
			}
		}
		
		// ********** UI components **********
		
		private Button buildAllowsExtensionsCheckBox(Composite parent, SelectionListener listener) {
			Button checkBox = new Button(parent, SWT.CHECK);
			GridData gridData = new GridData();
			gridData.verticalIndent = 5;
			checkBox.setLayoutData(gridData);
			checkBox.setText(JptJaxbUiMessages.ClassesGeneratorExtensionOptionsWizardPage_allowExtensions);
			checkBox.setSelection(this.allowsExtensions());
			checkBox.addSelectionListener(listener);
			return checkBox;
		}

		private Text buildClasspathText(Composite parent) {
			Text text = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 1;
			gridData.verticalIndent = 1;
			gridData.heightHint = text.getLineHeight() * 3;
			gridData.grabExcessHorizontalSpace = true;
			text.setLayoutData(gridData);
			return text;
		}

		private Text buildAdditionalArgsText(Composite parent) {
			Text text = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 1;
			gridData.verticalIndent = 1;
			gridData.heightHint = text.getLineHeight() * 10;
			gridData.grabExcessHorizontalSpace = true;
			text.setLayoutData(gridData);
			return text;
		}

		// ********** listeners **********

		private SelectionListener buildAllowsExtensionsSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					ExtensionOptionsComposite.this.setAllowsExtensions(
						ExtensionOptionsComposite.this.allowsExtensionsCheckBox.getSelection());
				}
			};
		}
		
		// ********** intra-wizard methods **********

		protected boolean allowsExtensions() {
			return this.allowsExtensions;
		}
		
		protected void setAllowsExtensions(boolean allowsExtensions){
			this.allowsExtensions = allowsExtensions;
		}

		protected String getClasspath() {
			return this.classpathText.getText();
		}
		
		protected String getAdditionalArgs() {
			return this.additionalArgsText.getText();
		}
		
	}	
}

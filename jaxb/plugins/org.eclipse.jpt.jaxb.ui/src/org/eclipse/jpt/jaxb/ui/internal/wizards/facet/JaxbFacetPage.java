/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.facet;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jpt.jaxb.core.internal.facet.JaxbFacetDataModelProperties;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiImages;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiMessages;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.jst.common.project.facet.ui.libprov.LibraryProviderFrameworkUi;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.web.ui.internal.wizards.DataModelFacetInstallPage;


public abstract class JaxbFacetPage
		extends DataModelFacetInstallPage
		implements JaxbFacetDataModelProperties {
	
	protected JaxbFacetPage(String pageName) {
		super(pageName);
		setTitle(JptJaxbUiMessages.JAXB_FACET_WIZARD_PAGE_TITLE);
		setDescription(JptJaxbUiMessages.JAXB_FACET_WIZARD_PAGE_DESC);
		setImageDescriptor(JptJaxbUiImages.JAXB_PROJECT_BANNER);
	}
	
	
	@Override
	public void setConfig(Object config) {
		if (! (config instanceof IDataModel)) {
			config = Platform.getAdapterManager().loadAdapter(config, IDataModel.class.getName());
		}
		super.setConfig(config);
	}
	
	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		
		addSubComposites(composite);
		
		Dialog.applyDialogFont(parent);
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, JpaHelpContextIds.DIALOG_JPA_FACET);
		
		return composite;
	}
	
	protected void  addSubComposites(Composite composite) {
		new PlatformGroup(composite);
		new ClasspathConfigGroup(composite);
	}
	
	protected Button createButton(Composite container, int span, String text, int style) {
		Button button = new Button(container, SWT.NONE | style);
		button.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		button.setLayoutData(gd);
		return button;
	}
	
	protected Combo createCombo(Composite container, int span, boolean fillHorizontal) {
		Combo combo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		GridData gd;
		if (fillHorizontal) {
			gd = new GridData(GridData.FILL_HORIZONTAL);
		}
		else {
			gd = new GridData();
		}
		gd.horizontalSpan = span;
		combo.setLayoutData(gd);
		return combo;
	}
	
	@Override
	protected String[] getValidationPropertyNames() {
		return new String[] {
				PLATFORM,
				LIBRARY_INSTALL_DELEGATE
			};
	}
	
	@Override
	public boolean isPageComplete() {
		if (! super.isPageComplete()) {
			return false;
		}
		else {
			IStatus status = this.model.validate(); 
			if (status.getSeverity() == IStatus.ERROR) {
				setErrorMessage(status.getMessage());
				return false;
			};
			setErrorMessage(null);
			return true;
		}
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			setErrorMessage();
		}
	}
	
	@Override
	public void transferStateToConfig() {
		// This method is called when "finish" is being performed.
		// We remove "this" as a listener so that it will stop responding to data model changes.
		// "This" listens only for validation purposes, and we are done with validation at this
		// point. Furthermore, any validation at this point runs into the danger of deadlocking
		// the wizard.
		this.model.removeListener(this);
		this.synchHelper.dispose();
		super.transferStateToConfig();
	}
	
	@Override
	protected void validatePage() {
		// done to prevent "orphan" pages from validating
		if (getWizard() == null) {
			return;
		}
		
		super.validatePage();
	}
	
	@Override
	protected void validatePage(boolean showMessage) {
		// done to prevent "orphan" pages from validating
		if (getWizard() == null) {
			return;
		}
		
		super.validatePage(showMessage);
	}
	
	
	protected final class PlatformGroup
	{
		private final Combo platformCombo;
		
		
		public PlatformGroup(Composite composite) {
			Group group = new Group(composite, SWT.NONE);
			group.setText(JptJaxbUiMessages.JAXB_FACET_WIZARD_PAGE_PLATFORM_LABEL);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//			PlatformUI.getWorkbench().getHelpSystem().setHelp(group, JpaHelpContextIds.DIALOG_JPA_PLATFORM);

			this.platformCombo = createCombo(group, 1, true);
			JaxbFacetPage.this.synchHelper.synchCombo(platformCombo, PLATFORM, null);
		}
	}
	
	
	protected final class ClasspathConfigGroup {
		
		public ClasspathConfigGroup(Composite composite) {
			
			LibraryInstallDelegate librariesInstallDelegate
					= (LibraryInstallDelegate) getDataModel().getProperty(LIBRARY_INSTALL_DELEGATE);
			
			Composite librariesComposite 
					= (Composite) LibraryProviderFrameworkUi.createInstallLibraryPanel(
							composite, librariesInstallDelegate, 
							JptJaxbUiMessages.JAXB_FACET_WIZARD_PAGE_JAXB_IMPLEMENTATION_LABEL);
			librariesComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//			PlatformUI.getWorkbench().getHelpSystem().setHelp(librariesComposite, JpaHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_CLASSPATH);			
		}
	}
}

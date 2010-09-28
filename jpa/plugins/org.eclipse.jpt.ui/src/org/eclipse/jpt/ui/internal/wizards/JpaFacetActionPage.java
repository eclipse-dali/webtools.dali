/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.JptUiMessages;
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
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener;
import org.eclipse.wst.common.project.facet.ui.ModifyFacetedProjectWizard;
import org.eclipse.wst.web.ui.internal.wizards.DataModelFacetInstallPage;

public abstract class JpaFacetActionPage
	extends DataModelFacetInstallPage
	implements JpaFacetDataModelProperties
{
	protected JpaFacetActionPage(String pageName) {
		super(pageName);
		setTitle(JptUiMessages.JpaFacetWizardPage_title);
		setDescription(JptUiMessages.JpaFacetWizardPage_description);
		setImageDescriptor(JptUiPlugin.getImageDescriptor(JptUiIcons.JPA_WIZ_BANNER));
	}
	
	
	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		
		addSubComposites(composite);
		
		setUpRuntimeListener();
		
		Dialog.applyDialogFont(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, JpaHelpContextIds.DIALOG_JPA_FACET);
		
		return composite;
	}
	
	protected abstract void  addSubComposites(Composite composite);
	
	private void setUpRuntimeListener() {
	    final IFacetedProjectWorkingCopy wc = ( (ModifyFacetedProjectWizard) getWizard() ).getFacetedProjectWorkingCopy();
		// must do it manually the first time
		model.setProperty(RUNTIME, wc.getPrimaryRuntime());
		wc.addListener(
			new IFacetedProjectListener() {
				public void handleEvent( final IFacetedProjectEvent event ) {
					model.setProperty(RUNTIME, wc.getPrimaryRuntime());
				}
			},
			IFacetedProjectEvent.Type.PRIMARY_RUNTIME_CHANGED
		);
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
			CONNECTION,
			USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG,
			USER_OVERRIDE_DEFAULT_CATALOG,
			USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA,
			USER_OVERRIDE_DEFAULT_SCHEMA,
			DISCOVER_ANNOTATED_CLASSES,
			LIBRARY_PROVIDER_DELEGATE
		};
	}
	
	@Override
	public boolean isPageComplete() {
		if (! super.isPageComplete()) {
			return false;
		}
		else {
			IStatus status = model.validate(); 
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
	
	protected final IWorkbenchHelpSystem getHelpSystem() {
		return PlatformUI.getWorkbench().getHelpSystem();
	}
	
	
	protected final class PlatformGroup
	{
		private final Combo platformCombo;
		
		
		public PlatformGroup(Composite composite) {
			Group group = new Group(composite, SWT.NONE);
			group.setText(JptUiMessages.JpaFacetWizardPage_platformLabel);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			PlatformUI.getWorkbench().getHelpSystem().setHelp(group, JpaHelpContextIds.DIALOG_JPA_PLATFORM);

			platformCombo = createCombo(group, 1, true);
			synchHelper.synchCombo(platformCombo, PLATFORM, null);
		}
	}
	
	
	protected final class ClasspathConfigGroup
	{
		public ClasspathConfigGroup(Composite composite) {
			
			final LibraryInstallDelegate librariesInstallDelegate
				= (LibraryInstallDelegate) getDataModel().getProperty(LIBRARY_PROVIDER_DELEGATE);
			
			final Composite librariesComposite 
				= (Composite) LibraryProviderFrameworkUi.createInstallLibraryPanel(
					composite, librariesInstallDelegate, 
					JptUiMessages.JpaFacetWizardPage_jpaImplementationLabel );
			librariesComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			PlatformUI.getWorkbench().getHelpSystem().setHelp(librariesComposite, JpaHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_CLASSPATH);			
		}
	}
	
	
	protected final class PersistentClassManagementGroup
	{
		private final Button discoverClassesButton;
		
		private final Button listClassesButton;
		
		
		public PersistentClassManagementGroup(Composite composite) {
			Group group = new Group(composite, SWT.NONE);
			group.setText(JptUiMessages.JpaFacetWizardPage_persistentClassManagementLabel);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			PlatformUI.getWorkbench().getHelpSystem().setHelp(group, JpaHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_CLASSPATH);
			
			discoverClassesButton = createButton(group, 1, JptUiMessages.JpaFacetWizardPage_discoverClassesButton, SWT.RADIO);
			synchHelper.synchRadio(discoverClassesButton, DISCOVER_ANNOTATED_CLASSES, null);
			
			listClassesButton = createButton(group, 1, JptUiMessages.JpaFacetWizardPage_listClassesButton, SWT.RADIO);
			synchHelper.synchRadio(listClassesButton, LIST_ANNOTATED_CLASSES, null);
		}
	}
}

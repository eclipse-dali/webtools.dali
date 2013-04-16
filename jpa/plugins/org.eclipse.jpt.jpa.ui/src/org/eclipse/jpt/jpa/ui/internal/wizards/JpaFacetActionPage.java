/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.jst.common.project.facet.ui.libprov.LibraryProviderFrameworkUi;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener;
import org.eclipse.wst.web.ui.internal.wizards.DataModelFacetInstallPage;

public abstract class JpaFacetActionPage
	extends DataModelFacetInstallPage
	implements JpaFacetDataModelProperties
{
	private IFacetedProjectListener listener;

	protected JpaFacetActionPage(String pageName) {
		super(pageName);
		setTitle(JptJpaUiMessages.JpaFacetWizardPage_title);
		setDescription(JptJpaUiMessages.JpaFacetWizardPage_description);
		setImageDescriptor(JptJpaUiImages.JPA_PROJECT_BANNER);
	}
	
	
	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		
		addSubComposites(composite);
		
		setUpRuntimeListener();
		
		Dialog.applyDialogFont(parent);
		WorkbenchTools.setHelp(parent, JpaHelpContextIds.DIALOG_JPA_FACET);
		
		return composite;
	}
	
	protected abstract void  addSubComposites(Composite composite);
	
	private void setUpRuntimeListener() {
	    final IFacetedProjectWorkingCopy wc = this.getFacetedProjectWorkingCopy();
		// must do it manually the first time
		model.setProperty(RUNTIME, wc.getPrimaryRuntime());
		this.listener = new IFacetedProjectListener() {
			public void handleEvent( final IFacetedProjectEvent event ) {
				model.setProperty(RUNTIME, wc.getPrimaryRuntime());
			}
		};
		
		wc.addListener(this.listener, IFacetedProjectEvent.Type.PRIMARY_RUNTIME_CHANGED);
	}

	protected IFacetedProjectWorkingCopy getFacetedProjectWorkingCopy() {
		return (IFacetedProjectWorkingCopy) getDataModel().getProperty(IFacetDataModelProperties.FACETED_PROJECT_WORKING_COPY);
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

	@Override
	public void dispose() {
	    this.getFacetedProjectWorkingCopy().removeListener(this.listener);
		super.dispose();
	}
	
	
	protected final class PlatformGroup
	{
		private final Combo platformCombo;
		
		
		public PlatformGroup(Composite composite) {
			Group group = new Group(composite, SWT.NONE);
			group.setText(JptJpaUiMessages.JpaFacetWizardPage_platformLabel);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			WorkbenchTools.setHelp(group, JpaHelpContextIds.DIALOG_JPA_PLATFORM);

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
					JptJpaUiMessages.JpaFacetWizardPage_jpaImplementationLabel );
			librariesComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			WorkbenchTools.setHelp(librariesComposite, JpaHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_CLASSPATH);			
		}
	}
	
	
	protected final class PersistentClassManagementGroup
	{
		private final Button discoverClassesButton;
		
		private final Button listClassesButton;
		
		
		public PersistentClassManagementGroup(Composite composite) {
			Group group = new Group(composite, SWT.NONE);
			group.setText(JptJpaUiMessages.JpaFacetWizardPage_persistentClassManagementLabel);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			WorkbenchTools.setHelp(group, JpaHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_CLASSPATH);
			
			discoverClassesButton = createButton(group, 1, JptJpaUiMessages.JpaFacetWizardPage_discoverClassesButton, SWT.RADIO);
			synchHelper.synchRadio(discoverClassesButton, DISCOVER_ANNOTATED_CLASSES, null);
			
			listClassesButton = createButton(group, 1, JptJpaUiMessages.JpaFacetWizardPage_listClassesButton, SWT.RADIO);
			synchHelper.synchRadio(listClassesButton, LIST_ANNOTATED_CLASSES, null);
		}
	}
}

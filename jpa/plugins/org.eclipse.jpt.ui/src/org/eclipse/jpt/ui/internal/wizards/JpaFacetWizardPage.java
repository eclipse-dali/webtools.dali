/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.db.ui.internal.DTPUiTools;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.jst.common.project.facet.ui.libprov.LibraryProviderFrameworkUi;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener;
import org.eclipse.wst.common.project.facet.ui.ModifyFacetedProjectWizard;
import org.eclipse.wst.web.ui.internal.wizards.DataModelFacetInstallPage;

public class JpaFacetWizardPage extends DataModelFacetInstallPage
	implements JpaFacetDataModelProperties
{
	
	public JpaFacetWizardPage() {
		super("jpt.jpa.facet.install.page"); //$NON-NLS-1$
		setTitle(JptUiMessages.JpaFacetWizardPage_title);
		setDescription(JptUiMessages.JpaFacetWizardPage_description);
		setImageDescriptor(JptUiPlugin.getImageDescriptor(JptUiIcons.JPA_WIZ_BANNER));
	}

	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);

		new PlatformGroup(composite);
		new ClasspathConfigGroup(composite);
		new ConnectionGroup(composite);
		new PersistentClassManagementGroup(composite);
		new OrmXmlGroup(composite);

		setUpRuntimeListener();

		Dialog.applyDialogFont(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, JpaHelpContextIds.DIALOG_JPA_FACET);

		return composite;
	}

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

	private Button createButton(Composite container, int span, String text, int style) {
		Button button = new Button(container, SWT.NONE | style);
		button.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		button.setLayoutData(gd);
		return button;
	}

	private Combo createCombo(Composite container, int span, boolean fillHorizontal) {
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
			PLATFORM_ID,
			CONNECTION,
			USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH,
			DB_DRIVER_NAME,
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
			IStatus status =model.validate(); 
			if( status.getSeverity() == IStatus.ERROR){
				setErrorMessage( status.getMessage() );
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


	private final class PlatformGroup
	{
		private final Combo platformCombo;
		
		
		public PlatformGroup(Composite composite) {
			Group group = new Group(composite, SWT.NONE);
			group.setText(JptUiMessages.JpaFacetWizardPage_platformLabel);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			PlatformUI.getWorkbench().getHelpSystem().setHelp(group, JpaHelpContextIds.DIALOG_JPA_PLATFORM);

			platformCombo = createCombo(group, 1, true);
			synchHelper.synchCombo(platformCombo, PLATFORM_ID, null);
		}
	}
	
	
	private final class ClasspathConfigGroup
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
	
	
	private final class ConnectionGroup
	{
		private final Combo connectionCombo;
		
		private Link connectionLink;
		
		private Link connectLink;
		
		private final Button addDriverLibraryButton;
		
		private final Label driverLibraryLabel;
		
		private final Combo driverLibraryCombo;
		
		private final Button overrideDefaultCatalogButton;
		
		private final Label defaultCatalogLabel;
		
		private final Combo defaultCatalogCombo;

		private final Button overrideDefaultSchemaButton;
		
		private final Label defaultSchemaLabel;
		
		private final Combo defaultSchemaCombo;
		
		
		public ConnectionGroup(Composite composite) {
			Group group = new Group(composite, SWT.NONE);
			group.setText(JptUiMessages.JpaFacetWizardPage_connectionLabel);
			group.setLayout(new GridLayout(3, false));
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			PlatformUI.getWorkbench().getHelpSystem().setHelp(group, JpaHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_DATABASE);
			
			connectionCombo = createCombo(group, 3, true);
			synchHelper.synchCombo(connectionCombo, CONNECTION, null);
			connectionCombo.addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						updateConnectLink();
					}
				});
			
			connectionLink = new Link(group, SWT.NONE);
			GridData data = new GridData(GridData.END, GridData.CENTER, false, false);
			data.horizontalSpan = 2;
			connectionLink.setLayoutData(data);
			connectionLink.setText(JptUiMessages.JpaFacetWizardPage_connectionLink);
			connectionLink.addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						openNewConnectionWizard();
					}
				}
			);
			
			connectLink = new Link(group, SWT.NONE);
			data = new GridData(GridData.END, GridData.CENTER, false, false);
			data.horizontalSpan = 2;
			connectLink.setLayoutData(data);
			connectLink.setText(JptUiMessages.JpaFacetWizardPage_connectLink);
			connectLink.setEnabled(false);
			connectLink.addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						openConnectionProfile();
					}
				});
			
			addDriverLibraryButton = createButton(group, 3, JptUiMessages.JpaFacetWizardPage_addDriverLibraryLabel, SWT.CHECK);
			addDriverLibraryButton.setSelection(false);
			synchHelper.synchCheckbox(addDriverLibraryButton, USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH, null);

			driverLibraryLabel = new Label(group, SWT.LEFT);
			driverLibraryLabel.setText(JptUiMessages.JpaFacetWizardPage_driverLibraryLabel);
			GridData gd = new GridData();
			gd.horizontalSpan = 1;
			driverLibraryLabel.setLayoutData(gd);
			
			driverLibraryCombo = createCombo(group, 1, true);
			synchHelper.synchCombo(
				driverLibraryCombo, DB_DRIVER_NAME, 
				new Control[] {driverLibraryLabel});
			
			overrideDefaultCatalogButton = createButton(group, 3, JptUiMessages.JpaFacetWizardPage_overrideDefaultCatalogLabel, SWT.CHECK);
			overrideDefaultCatalogButton.setSelection(false);
			synchHelper.synchCheckbox(overrideDefaultCatalogButton, USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG, null);
			
			defaultCatalogLabel = new Label(group, SWT.LEFT);
			defaultCatalogLabel.setText(JptUiMessages.JpaFacetWizardPage_defaultCatalogLabel);
			gd = new GridData();
			gd.horizontalSpan = 1;
			defaultCatalogLabel.setLayoutData(gd);
			
			defaultCatalogCombo = createCombo(group, 1, true);
			synchHelper.synchCombo(
				defaultCatalogCombo, USER_OVERRIDE_DEFAULT_CATALOG, 
				new Control[] {defaultCatalogLabel});

			overrideDefaultSchemaButton = createButton(group, 3, JptUiMessages.JpaFacetWizardPage_overrideDefaultSchemaLabel, SWT.CHECK);
			overrideDefaultSchemaButton.setSelection(false);
			synchHelper.synchCheckbox(overrideDefaultSchemaButton, USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA, null);
			
			defaultSchemaLabel = new Label(group, SWT.LEFT);
			defaultSchemaLabel.setText(JptUiMessages.JpaFacetWizardPage_defaultSchemaLabel);
			gd = new GridData();
			gd.horizontalSpan = 1;
			defaultSchemaLabel.setLayoutData(gd);
			
			defaultSchemaCombo = createCombo(group, 1, true);
			synchHelper.synchCombo(
				defaultSchemaCombo, USER_OVERRIDE_DEFAULT_SCHEMA, 
				new Control[] {defaultSchemaLabel});
		}
		
		private void openNewConnectionWizard() {
			String connectionName = DTPUiTools.createNewConnectionProfile();
			if (connectionName != null) {
				model.setProperty(CONNECTION, connectionName);
			}
		}
		
		private void openConnectionProfile() {
			ConnectionProfile cp = getConnectionProfile();
			if (cp != null) {
				cp.connect();
				model.setBooleanProperty(CONNECTION_ACTIVE, cp.isActive());
				updateConnectLink();
			}
		}
		
		private void updateConnectLink() {
			ConnectionProfile cp = this.getConnectionProfile();
			connectLink.setEnabled((cp != null) && cp.isDisconnected());
			addDriverLibraryButton.setEnabled(cp != null);
		}
		
		private ConnectionProfile getConnectionProfile() {
			// we just use the connection profile to log in, so go the the db plug-in
			return JptDbPlugin.instance().getConnectionProfileFactory().buildConnectionProfile(model.getStringProperty(CONNECTION));
		}
	}
	
	
	private final class PersistentClassManagementGroup
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


	private final class OrmXmlGroup
	{
		private final Button createOrmXmlButton;


		public OrmXmlGroup(Composite composite) {
			Composite group = new Composite(composite, SWT.NONE);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			PlatformUI.getWorkbench().getHelpSystem().setHelp(group, JpaHelpContextIds.DIALOG_CREATE_ORM);

			createOrmXmlButton = new Button(group, SWT.CHECK);
			createOrmXmlButton.setText(JptUiMessages.JpaFacetWizardPage_createOrmXmlButton);
			synchHelper.synchCheckbox(createOrmXmlButton, CREATE_ORM_XML, null);
		}
	}
}

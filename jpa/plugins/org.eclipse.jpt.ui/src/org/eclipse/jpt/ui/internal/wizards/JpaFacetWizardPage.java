/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards;

import java.util.Iterator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.internal.ui.preferences.UserLibraryPreferencePage;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.core.internal.platform.JpaPlatformRegistry;
import org.eclipse.jpt.db.internal.ConnectionProfileRepository;
import org.eclipse.jpt.db.ui.internal.DTPUiTools;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.prefs.JpaPreferencePage;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.wst.common.frameworks.datamodel.DataModelEvent;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelListener;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener;
import org.eclipse.wst.common.project.facet.ui.ModifyFacetedProjectWizard;
import org.eclipse.wst.web.ui.internal.wizards.DataModelFacetInstallPage;

public class JpaFacetWizardPage 
	extends DataModelFacetInstallPage
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
		new ConnectionGroup(composite);
		new ClasspathConfigGroup(composite);
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
			USE_SERVER_JPA_IMPLEMENTATION,
			JPA_LIBRARY,
			DISCOVER_ANNOTATED_CLASSES
		};
	}
	
	@Override
	public boolean isPageComplete() {
		if (! super.isPageComplete()) {
			return false;
		}
		else {
			return (model.validate().getSeverity() != IStatus.ERROR);
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
		private final ComboViewer platformCombo;
		
		
		public PlatformGroup(Composite composite) {
			Group group = new Group(composite, SWT.NONE);
			group.setText(JptUiMessages.JpaFacetWizardPage_platformLabel);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			PlatformUI.getWorkbench().getHelpSystem().setHelp(group, JpaHelpContextIds.DIALOG_JPA_PLATFORM);
			
			platformCombo = new ComboViewer(createCombo(group, 1, true));
			platformCombo.setContentProvider(
					new IStructuredContentProvider() {
						public Object[] getElements(Object inputElement) {
							return CollectionTools.array(JpaPlatformRegistry.instance().jpaPlatformIds());
						}
						
						public void dispose() {}
						
						public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
					}
				);
			platformCombo.setLabelProvider(
					new ILabelProvider() {
						public Image getImage(Object element) {
							return null;
						}
						
						public String getText(Object element) {
							return JpaPlatformRegistry.instance().jpaPlatformLabel((String) element);
						}
						
						public void addListener(ILabelProviderListener listener) {}
						
						public void removeListener(ILabelProviderListener listener) {}
						
						public void dispose() {}
						
						public boolean isLabelProperty(Object element, String property) {
							return true;
						}
					}
				);
			platformCombo.addSelectionChangedListener(
					new ISelectionChangedListener() {
						public void selectionChanged(SelectionChangedEvent event) {
							model.setProperty(PLATFORM_ID, ((StructuredSelection) event.getSelection()).getFirstElement());
						}
					}
				);
			// we need some input here, even if it means absolutely nothing
			platformCombo.setInput("null input");
			if (platformCombo.getElementAt(0) != null) {
				platformCombo.setSelection(new StructuredSelection(platformCombo.getElementAt(0)));
			}
		}
	}
	
	
	private final class ConnectionGroup
	{
		private final Combo connectionCombo;
		
		private Link connectionLink;
		
		
		public ConnectionGroup(Composite composite) {
			Group group = new Group(composite, SWT.NONE);
			group.setText(JptUiMessages.JpaFacetWizardPage_connectionLabel);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			PlatformUI.getWorkbench().getHelpSystem().setHelp(group, JpaHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_DATABASE);
			
			connectionCombo = createCombo(group, 1, true);
			connectionCombo.addSelectionListener(
					new SelectionListener() {
						public void widgetSelected(SelectionEvent e) {
							model.setProperty(CONNECTION, connectionCombo.getItem(connectionCombo.getSelectionIndex()));
						}
						
						public void widgetDefaultSelected(SelectionEvent e) {
							widgetSelected(e);
						}
					}
				);
			fillConnections();
			if (connectionCombo.getItemCount() > 0) {
				connectionCombo.select(0);
				model.setProperty(CONNECTION, connectionCombo.getItem(0));
			}
			else {
				connectionCombo.clearSelection();
				model.setProperty(CONNECTION, null);
			}
			
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
		}

		private void fillConnections() {
			//clear out connection entries from previous login.
			connectionCombo.removeAll();
			
			for (Iterator stream = ConnectionProfileRepository.instance().profileNames(); stream.hasNext(); ) {
				connectionCombo.add((String) stream.next());
			}
		}
		
		private void openNewConnectionWizard() {
			String connectionName = DTPUiTools.createNewProfile();
			if (connectionName != null) {
				fillConnections();
				model.setProperty(CONNECTION, connectionName);
				connectionCombo.select(connectionCombo.indexOf(connectionName));
			}
		}
	}
	
	
	private final class ClasspathConfigGroup
	{
		private final Button useServerLibButton;
		
		private final Button specifyLibButton;
		
		private final Combo jpaLibCombo;
		
		private final Link jpaPrefsLink;
		
		private final Link userLibsLink;
		
		
		
		public ClasspathConfigGroup(Composite composite) {
			Group group = new Group(composite, SWT.NONE);
			group.setText(JptUiMessages.JpaFacetWizardPage_jpaImplementationLabel);
			group.setLayout(new GridLayout(2, false));
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			PlatformUI.getWorkbench().getHelpSystem().setHelp(group, JpaHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_CLASSPATH);
			
			boolean useServerLib = model.getBooleanProperty(USE_SERVER_JPA_IMPLEMENTATION);
			
			useServerLibButton = createButton(group, 2, JptUiMessages.JpaFacetWizardPage_userServerLibLabel, SWT.RADIO);
			useServerLibButton.setSelection(useServerLib);
			useServerLibButton.addSelectionListener(
					new SelectionListener() {
						public void widgetDefaultSelected(SelectionEvent e) {
							widgetSelected(e);
						}
						
						public void widgetSelected(SelectionEvent e) {
							model.setBooleanProperty(USE_SERVER_JPA_IMPLEMENTATION, true);
						}
					}
				);
			
			specifyLibButton = createButton(group, 1, JptUiMessages.JpaFacetWizardPage_specifyLibLabel, SWT.RADIO);
			specifyLibButton.setSelection(! useServerLib);
			specifyLibButton.addSelectionListener(
					new SelectionListener() {
						public void widgetDefaultSelected(SelectionEvent e) {
							widgetSelected(e);
						}
						
						public void widgetSelected(SelectionEvent e) {
							model.setBooleanProperty(USE_SERVER_JPA_IMPLEMENTATION, false);
						}
					}
				);
			
			jpaLibCombo = createCombo(group, 1, true);
			synchHelper.synchCombo(jpaLibCombo, JPA_LIBRARY, null);
			
			model.addListener(
					new IDataModelListener() {
						public void propertyChanged(DataModelEvent event) {
							if (USE_SERVER_JPA_IMPLEMENTATION.equals(event.getPropertyName())) {
								boolean useServerLib = (Boolean) event.getProperty();
								useServerLibButton.setSelection(useServerLib);
								specifyLibButton.setSelection(! useServerLib);
								jpaLibCombo.setEnabled(! useServerLib);
							}
						}
					}
				);
			
			jpaPrefsLink = new Link(group, SWT.NONE);
			GridData data = new GridData(GridData.END, GridData.CENTER, false, false);
			data.horizontalSpan = 2;
			jpaPrefsLink.setLayoutData(data);
			jpaPrefsLink.setText(JptUiMessages.JpaFacetWizardPage_jpaPrefsLink);
			jpaPrefsLink.addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						promptToConfigJpaPrefs();				
					}
				}
			);
			
			userLibsLink = new Link(group, SWT.NONE);
			data = new GridData(GridData.END, GridData.CENTER, false, false);
			data.horizontalSpan = 2;
			userLibsLink.setLayoutData(data);
			userLibsLink.setText(JptUiMessages.JpaFacetWizardPage_userLibsLink);
			userLibsLink.addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						promptToConfigUserLibraries();				
					}
				}
			);
		}
		
		private void promptToConfigJpaPrefs() {
			PreferenceDialog dlg = 
				PreferencesUtil.createPreferenceDialogOn(
					getShell(), 
					JpaPreferencePage.ID,
					new String[] {JpaPreferencePage.ID},
					null);
			dlg.open();
			model.notifyPropertyChange(JPA_LIBRARY, IDataModel.VALID_VALUES_CHG);
			model.notifyPropertyChange(JPA_LIBRARY, IDataModel.DEFAULT_CHG);
		}
		
		private void promptToConfigUserLibraries() {
			PreferenceDialog dlg = 
				PreferencesUtil.createPreferenceDialogOn(
					getShell(), 
					UserLibraryPreferencePage.ID,
					new String[] {UserLibraryPreferencePage.ID},
					null);
			dlg.open();
			model.notifyPropertyChange(JPA_LIBRARY, IDataModel.VALID_VALUES_CHG);
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
			
			boolean discoverClasses = model.getBooleanProperty(DISCOVER_ANNOTATED_CLASSES);
			
			discoverClassesButton = createButton(group, 1, JptUiMessages.JpaFacetWizardPage_discoverClassesButton, SWT.RADIO);
			discoverClassesButton.setSelection(discoverClasses);
			discoverClassesButton.addSelectionListener(
				new SelectionListener() {
						public void widgetDefaultSelected(SelectionEvent e) {
							widgetSelected(e);
						}
						
						public void widgetSelected(SelectionEvent e) {
							model.setBooleanProperty(DISCOVER_ANNOTATED_CLASSES, true);
						}
					}
				);
			
			listClassesButton = createButton(group, 1, JptUiMessages.JpaFacetWizardPage_listClassesButton, SWT.RADIO);
			listClassesButton.setSelection(! discoverClasses);
			listClassesButton.addSelectionListener(
				new SelectionListener() {
						public void widgetDefaultSelected(SelectionEvent e) {
							widgetSelected(e);
						}
						
						public void widgetSelected(SelectionEvent e) {
							model.setBooleanProperty(DISCOVER_ANNOTATED_CLASSES, false);
						}
					}
				);
			
			model.addListener(
					new IDataModelListener() {
						public void propertyChanged(DataModelEvent event) {
							if (DISCOVER_ANNOTATED_CLASSES.equals(event.getPropertyName())) {
								boolean discoverClasses = (Boolean) event.getProperty();
								discoverClassesButton.setSelection(discoverClasses);
								listClassesButton.setSelection(! discoverClasses);
							}
						}
					}
				);
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
			createOrmXmlButton.setSelection(model.getBooleanProperty(CREATE_ORM_XML));
			createOrmXmlButton.addSelectionListener(
					new SelectionListener() {
						public void widgetDefaultSelected(SelectionEvent e) {
							widgetSelected(e);
						}
						
						public void widgetSelected(SelectionEvent e) {
							model.setBooleanProperty(CREATE_ORM_XML, createOrmXmlButton.getSelection());
						}
					}
				);
		}
	}
}

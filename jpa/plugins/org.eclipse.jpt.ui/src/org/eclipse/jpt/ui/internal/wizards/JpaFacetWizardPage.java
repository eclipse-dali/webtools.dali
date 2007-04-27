/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards;

import java.util.Iterator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.JavaCore;
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
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.JpaPlatformRegistry;
import org.eclipse.jpt.core.internal.facet.IJpaFacetDataModelProperties;
import org.eclipse.jpt.core.internal.prefs.JpaPreferenceConstants;
import org.eclipse.jpt.db.internal.ConnectionProfileRepository;
import org.eclipse.jpt.db.ui.internal.DTPUiTools;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.JptUiPlugin;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.wst.web.ui.internal.wizards.DataModelFacetInstallPage;

public class JpaFacetWizardPage 
	extends DataModelFacetInstallPage
	implements IJpaFacetDataModelProperties
{
	private PlatformGroup platformGroup;
	
	private ConnectionGroup connectionGroup;
	
	private ClasspathConfigGroup classpathConfigGroup;
	
	private OrmXmlGroup ormXmlGroup;
	
	
	public JpaFacetWizardPage() {
		super("jpt.jpa.facet.install.page"); //$NON-NLS-1$
		setTitle(JptUiMessages.JpaFacetWizardPage_title);
		setDescription(JptUiMessages.JpaFacetWizardPage_description);
		setImageDescriptor(JptUiPlugin.getPlugin().getImageDescriptor(JptUiIcons.JPA_WIZ_BANNER));
	}
	
	
	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		
		platformGroup = new PlatformGroup(composite);
		connectionGroup = new ConnectionGroup(composite);
		classpathConfigGroup = new ClasspathConfigGroup(composite);
		ormXmlGroup = new OrmXmlGroup(composite);
		
		Dialog.applyDialogFont(parent);
		// TODO
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IJpaHelpContextIds.DIALOG_JPA_FACET);
		
		return composite;
	}
	
	private Label createLabel(Composite container, int span, String text) {
		Label label = new Label(container, SWT.NONE);
		label.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		label.setLayoutData(gd);
		return label;
	}
	
	private Combo createCombo(Composite container, boolean fillHorizontal) {
		Combo combo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		if (fillHorizontal) {
			combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		else {
			combo.setLayoutData(new GridData());
		}
		return combo;
	}
	
	@Override
	protected String[] getValidationPropertyNames() {
		return new String[] {
			PLATFORM_ID,
			CONNECTION,
			JPA_LIBRARY
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
		private final Label platformLabel;
		
		private final ComboViewer platformCombo;
		
		
		public PlatformGroup(Composite composite) {
			Composite group = new Composite(composite, SWT.NONE);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			// TODO - F1 Help
			// PlatformUI.getWorkbench().getHelpSystem().setHelp(group, IDaliHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_DATABASE);
			
			platformLabel = createLabel(group, 1, JptUiMessages.JpaFacetWizardPage_platformLabel);
			
			platformCombo = new ComboViewer(createCombo(group, true));
			platformCombo.setContentProvider(
					new IStructuredContentProvider() {
						public Object[] getElements(Object inputElement) {
							return CollectionTools.array(JpaPlatformRegistry.INSTANCE.allJpaPlatformIds());
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
							return JpaPlatformRegistry.INSTANCE.getJpaPlatformLabel((String) element);
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
		private final Label connectionLabel;
		
		private final Combo connectionCombo;
		
		private Link connectionLink;
		
		
		public ConnectionGroup(Composite composite) {
			Composite group = new Composite(composite, SWT.NONE);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			PlatformUI.getWorkbench().getHelpSystem().setHelp(group, IJpaHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_DATABASE);
			
			connectionLabel = createLabel(group, 1, JptUiMessages.JpaFacetWizardPage_connectionLabel);
			
			connectionCombo = createCombo(group, true);
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
		
		private String getConnectionName() {
			return connectionCombo.getText();
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
		private final Label jpaLibLabel;
		
		private final Combo jpaLibCombo;
		
		private final Link jpaPrefsLink;
		
		private final Link userLibsLink;
		
		
		
		public ClasspathConfigGroup(Composite composite) {
			Composite group = new Composite(composite, SWT.NONE);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			PlatformUI.getWorkbench().getHelpSystem().setHelp(group, IJpaHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_CLASSPATH);
			
			jpaLibLabel = createLabel(group, 1, JptUiMessages.JpaFacetWizardPage_jpaLibLabel);
			
			jpaLibCombo = createCombo(group, true);
			jpaLibCombo.addSelectionListener(
					new SelectionListener() {
						public void widgetDefaultSelected(SelectionEvent e) {
							widgetSelected(e);
						}
						
						public void widgetSelected(SelectionEvent e) {
							model.setProperty(JPA_LIBRARY, jpaLibCombo.getItem(jpaLibCombo.getSelectionIndex()));
						};
					}
				);
			fillJpaLibs();
			
			jpaPrefsLink = new Link(group, SWT.NONE);
			GridData data = new GridData(GridData.END, GridData.CENTER, false, false);
			data.horizontalSpan = 2;
			jpaPrefsLink.setLayoutData(data);
			jpaPrefsLink.setText(JptUiMessages.JpaFacetWizardPage_jpaPrefsLink);
			jpaPrefsLink.addSelectionListener(
				new SelectionAdapter() {
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
					public void widgetSelected(SelectionEvent e) {
						promptToConfigUserLibraries();				
					}
				}
			);
		}
		
		private void fillJpaLibs() {
			int index = jpaLibCombo.getSelectionIndex();
			String selectedJpaLib = null;
			if (index >= 0) {
				selectedJpaLib = jpaLibCombo.getItem(jpaLibCombo.getSelectionIndex());
			}
			else {
				selectedJpaLib = 
					JptCorePlugin.getPlugin().getPluginPreferences()
						.getString(JpaPreferenceConstants.PREF_DEFAULT_JPA_LIB);
			}
			
			jpaLibCombo.clearSelection();
			jpaLibCombo.setItems(JavaCore.getUserLibraryNames());
			
			if (selectedJpaLib != null) {
				int newIndex = CollectionTools.indexOf(jpaLibCombo.getItems(), selectedJpaLib);
				if (newIndex >= 0) {
					jpaLibCombo.select(newIndex);
					model.setProperty(JPA_LIBRARY, selectedJpaLib);	
				}
			}
		}
		
		private void promptToConfigJpaPrefs() {
			PreferenceDialog dlg = 
				PreferencesUtil.createPreferenceDialogOn(
					getShell(), 
					JpaPreferencePage.ID,
					new String[] {JpaPreferencePage.ID},
					null);
			dlg.open();
			fillJpaLibs();
		}
		
		private void promptToConfigUserLibraries() {
			PreferenceDialog dlg = 
				PreferencesUtil.createPreferenceDialogOn(
					getShell(), 
					UserLibraryPreferencePage.ID,
					new String[] {UserLibraryPreferencePage.ID},
					null);
			dlg.open();
			fillJpaLibs();
		}
	}
	
	
	private final class OrmXmlGroup
	{
		private final Button createOrmXmlButton;
		
		
		public OrmXmlGroup(Composite composite) {
			Composite group = new Composite(composite, SWT.NONE);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			// TODO - F1 Help
			// PlatformUI.getWorkbench().getHelpSystem().setHelp(group, IDaliHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_DATABASE);
			
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

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
package org.eclipse.jpt.ui.internal.properties;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JpaPlatformRegistry;
import org.eclipse.jpt.core.internal.JpaProject;
import org.eclipse.jpt.core.internal.facet.IJpaFacetDataModelProperties;
import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProvider;
import org.eclipse.jpt.core.internal.facet.JpaFacetUtils;
import org.eclipse.jpt.db.internal.ConnectionProfileRepository;
import org.eclipse.jpt.db.ui.internal.DTPUiTools;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.JptUiPlugin;
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
import org.eclipse.wst.common.frameworks.datamodel.DataModelEvent;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelListener;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntime;

public class JpaProjectPropertiesPage 
	extends DataModelPropertyPage
	implements IJpaFacetDataModelProperties
{
	private PlatformGroup platformGroup;
	
	private ConnectionGroup connectionGroup;
	
	private PersistentClassManagementGroup persistentClassManagementGroup;
	
	
	/**
	 * Constructor for SamplePropertyPage.
	 */
	public JpaProjectPropertiesPage() {
		super(DataModelFactory.createDataModel(new JpaFacetDataModelProvider()));
	}
	
	
	@Override
	protected Composite createTopLevelComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		
		platformGroup = new PlatformGroup(composite);
		connectionGroup = new ConnectionGroup(composite);
		persistentClassManagementGroup = new PersistentClassManagementGroup(composite);
		
		setRuntime();
		
		Dialog.applyDialogFont(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, IJpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE);
		
		return composite;
	}
	
	private void setRuntime() {
		IFacetedProject facetedProject = null;
		try {
			facetedProject = ProjectFacetsManager.create(getJpaProject().getProject());
		}
		catch (CoreException ce) {
			JptUiPlugin.log(ce);
			return;
		}
		IRuntime runtime = facetedProject.getPrimaryRuntime();
		model.setProperty(IJpaFacetDataModelProperties.RUNTIME, runtime);
	}
	
	@Override
	protected String[] getValidationPropertyNames() {
		return new String[] {
			IJpaFacetDataModelProperties.PLATFORM_ID,
			IJpaFacetDataModelProperties.CONNECTION,
			IJpaFacetDataModelProperties.DISCOVER_ANNOTATED_CLASSES
		};
	}
	
	protected JpaProject getJpaProject() {
		return (JpaProject) getElement().getAdapter(IJpaProject.class);
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
	
	private Button createButton(Composite container, int span, String text, int style) {
		Button button = new Button(container, SWT.NONE | style);
		button.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		button.setLayoutData(gd);
		return button;
	}
	
	protected void performDefaults() {
		platformGroup.performDefaults();
		connectionGroup.performDefaults();
		persistentClassManagementGroup.performDefaults();
	}
	
	public boolean performOk() {
		JpaProject jpaProject = getJpaProject();
		
		// the facet has been uninstalled during our trip to the properties ...
		if (jpaProject == null) {
			return true;
		}
		
		try {
			JpaFacetUtils.setPlatform(jpaProject.getProject(), model.getStringProperty(IJpaFacetDataModelProperties.PLATFORM_ID));
			JpaFacetUtils.setConnectionName(jpaProject.getProject(), model.getStringProperty(IJpaFacetDataModelProperties.CONNECTION));
			JpaFacetUtils.setDiscoverAnnotatedClasses(jpaProject.getProject(), model.getBooleanProperty(IJpaFacetDataModelProperties.DISCOVER_ANNOTATED_CLASSES));
		}
		catch (CoreException ce) {
			return false;
		}
		
		buildProject();
		
		return true;
	}
	
	private void buildProject() {
		IRunnableWithProgress r= new IRunnableWithProgress() {
			public void run(IProgressMonitor pm) throws InvocationTargetException {
				try {
					getJpaProject().getProject().build(IncrementalProjectBuilder.FULL_BUILD, pm);
				} 
				catch (CoreException ce) {
					JptUiPlugin.log(ce);
				}
			}
		};
		try {
			PlatformUI.getWorkbench().getProgressService().run(true, false, r);
		}
		catch (InterruptedException ie) { /* nothing to do */ }
		catch (InvocationTargetException ie) { /* nothing to do */ }
	}
	
	
	private final class PlatformGroup
	{
		private final ComboViewer platformCombo;
		
		
		public PlatformGroup(Composite composite) {
			Group group = new Group(composite, SWT.NONE);
			group.setText(JptUiMessages.JpaFacetWizardPage_platformLabel);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			// TODO
			// PlatformUI.getWorkbench().getHelpSystem().setHelp(group, IDaliHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_DATABASE);
			
			platformCombo = new ComboViewer(createCombo(group, true));
			platformCombo.setContentProvider(
					new IStructuredContentProvider() {
						public Object[] getElements(Object inputElement) {
							return CollectionTools.array(JpaPlatformRegistry.instance().allJpaPlatformIds());
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
							model.setProperty(PLATFORM_ID, ((StructuredSelection) platformCombo.getSelection()).getFirstElement());
						}
					}
				);
			// we need some input here, even if it means absolutely nothing
			platformCombo.setInput("null input");
			performDefaults();
		}
		
		private void performDefaults() {
			String platformId = getJpaProject().getPlatform().getId();
			model.setProperty(PLATFORM_ID, platformId);
			platformCombo.setSelection(new StructuredSelection(platformId));
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
			
			connectionCombo = createCombo(group, true);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(group, IJpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE_CONNECTION);
			connectionCombo.addSelectionListener(
					new SelectionListener() {
						public void widgetDefaultSelected(SelectionEvent e) {
							widgetSelected(e);
						}
						
						public void widgetSelected(SelectionEvent e) {
							model.setProperty(CONNECTION, connectionCombo.getItem(connectionCombo.getSelectionIndex()));
						}
					}
				);
			fillConnections();
			
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
			performDefaults();
		}

		private void fillConnections() {
			//clear out connection entries from previous login.
			connectionCombo.removeAll();
			
			for (Iterator<String> stream = ConnectionProfileRepository.instance().profileNames(); stream.hasNext(); ) {
				connectionCombo.add(stream.next());
			}
		}
		
		private void performDefaults() {
			String connectionName = getJpaProject().getDataSource().getConnectionProfileName();
			model.setProperty(CONNECTION, connectionName);
			if (connectionName == null) {
				connectionCombo.clearSelection();
			}
			else {
				connectionCombo.setText(connectionName);
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
	
	
	private final class PersistentClassManagementGroup
	{
		private final Button discoverClassesButton;
		
		private final Button listClassesButton;
		
		
		public PersistentClassManagementGroup(Composite composite) {
			Group group = new Group(composite, SWT.NONE);
			group.setText(JptUiMessages.JpaFacetWizardPage_persistentClassManagementLabel);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			PlatformUI.getWorkbench().getHelpSystem().setHelp(group, IJpaHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_CLASSPATH);
			
			discoverClassesButton = createButton(group, 1, JptUiMessages.JpaFacetWizardPage_discoverClassesButton, SWT.RADIO);
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
			
			performDefaults();
		}
		
		private void performDefaults() {
			boolean discoverClasses = getJpaProject().isDiscoverAnnotatedClasses();
			model.setProperty(DISCOVER_ANNOTATED_CLASSES, discoverClasses);
			discoverClassesButton.setSelection(discoverClasses);
			listClassesButton.setSelection(! discoverClasses);
		}
	}
}
package org.eclipse.jpt.ui.internal.properties;

import java.util.Iterator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.Dialog;
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
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;

public class JpaProjectPropertiesPage 
	extends DataModelPropertyPage
	implements IJpaFacetDataModelProperties
{
	private PlatformGroup platformGroup;
	
	private ConnectionGroup connectionGroup;
	
	
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
		//classpathConfigGroup = new ClasspathConfigGroup(composite);
		
		Dialog.applyDialogFont(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, IJpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE);
		
		return composite;
	}
	
	@Override
	protected String[] getValidationPropertyNames() {
		return new String[] {
			IJpaFacetDataModelProperties.PLATFORM_ID,
			IJpaFacetDataModelProperties.CONNECTION
		};
	}
	
	protected JpaProject getJpaProject() {
		return (JpaProject) getElement().getAdapter(IJpaProject.class);
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
	
	protected void performDefaults() {
		platformGroup.performDefaults();
		connectionGroup.performDefaults();
	}
	
	public boolean performOk() {
		JpaProject jpaProject = getJpaProject();
		try {
			JpaFacetUtils.setPlatform(jpaProject.getProject(), model.getStringProperty(IJpaFacetDataModelProperties.PLATFORM_ID));
			JpaFacetUtils.setConnectionName(jpaProject.getProject(), model.getStringProperty(IJpaFacetDataModelProperties.CONNECTION));
		}
		catch (CoreException ce) {
			return false;
		}
		
		return true;
	}
	
	
	private final class PlatformGroup
	{
		private final Label platformLabel;
		
		private final ComboViewer platformCombo;
		
		
		public PlatformGroup(Composite composite) {
			Composite group = new Composite(composite, SWT.NONE);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			// TODO
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
		private final Label connectionLabel;
		
		private final Combo connectionCombo;
		
		private Link connectionLink;
		
		
		public ConnectionGroup(Composite composite) {
			Composite group = new Composite(composite, SWT.NONE);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			
			connectionLabel = createLabel(group, 1, JptUiMessages.JpaFacetWizardPage_connectionLabel);
			
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
			
			for (Iterator stream = ConnectionProfileRepository.instance().profileNames(); stream.hasNext(); ) {
				connectionCombo.add((String) stream.next());
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
}
package org.eclipse.jpt.ui.internal.dialogs;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.JptUiPlugin;
import org.eclipse.jpt.ui.internal.java.details.IAttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.BasicMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EmbeddedIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.IdMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.ManyToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.ManyToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.OneToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.OneToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.TransientMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.VersionMappingUiProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddPersistentAttributeToXmlAndMapDialog extends StatusDialog
{
	private XmlPersistentAttribute unmappedPersistentAttribute;
	
	private Label attributeLabel;
		
	private Text attributeText;
	
	private Label mappingLabel;
	
	private ComboViewer mappingCombo;
		
	
	public AddPersistentAttributeToXmlAndMapDialog(Shell parentShell, XmlPersistentAttribute unmappedPersistentAttribute) {
		super(parentShell);
		this.unmappedPersistentAttribute = unmappedPersistentAttribute;
		setTitle(JptUiMessages.AddPersistentAttributeDialog_title);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);
		
		Composite composite = new Composite(dialogArea, SWT.NULL);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setLayout(new GridLayout());
		
		attributeLabel = createLabel(composite, 1, JptUiMessages.AddPersistentAttributeDialog_attributeLabel);
			
		attributeText = createText(composite, 1);
//		attributeText.addModifyListener(
//				new ModifyListener() {
//					public void modifyText(ModifyEvent e) {
//						validate();
//					}
//				}
//			);
		attributeText.setText(unmappedPersistentAttribute.getName());
		attributeText.setEditable(false);
		
		mappingLabel = createLabel(composite, 1, JptUiMessages.AddPersistentClassDialog_mappingLabel);
		
		mappingCombo = new ComboViewer(createCombo(composite, 1));
		mappingCombo.setContentProvider(
			new IStructuredContentProvider() {
				public void dispose() {}
				
				public Object[] getElements(Object inputElement) {
					return new Object[] {
						BasicMappingUiProvider.instance(),
						IdMappingUiProvider.instance(),
						VersionMappingUiProvider.instance(),
						OneToOneMappingUiProvider.instance(),
						OneToManyMappingUiProvider.instance(),
						ManyToOneMappingUiProvider.instance(),
						ManyToManyMappingUiProvider.instance(),
						EmbeddedMappingUiProvider.instance(),
						EmbeddedIdMappingUiProvider.instance(),
						TransientMappingUiProvider.instance()
					};
				}
				
				public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
			});
		mappingCombo.setLabelProvider(
			new LabelProvider() {
				@Override
				public String getText(Object element) {
					return ((IAttributeMappingUiProvider) element).label();
				}
			});
		mappingCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				validate();
			}
		});
		mappingCombo.setInput("FOO");
		mappingCombo.getCombo().select(0);  // select Basic to begin
		
		// TODO - F1 Help
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(group, IDaliHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_DATABASE);
		
		//getButton(IDialogConstants.OK_ID).setEnabled(false);  // disabled to start
		applyDialogFont(dialogArea);		
		
		validate();
		
		return dialogArea;
	}
	
	private Label createLabel(Composite container, int span, String text) {
		Label label = new Label(container, SWT.NONE);
		label.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		label.setLayoutData(gd);
		return label;
	}
	
	private Text createText(Composite container, int span) {
		Text text = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = span;
		gd.widthHint = 250;
		text.setLayoutData(gd);
		return text;
	}
	
	private Combo createCombo(Composite container, int span) {
		Combo combo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = span;
		combo.setLayoutData(gd);
		return combo;
	}
	
	private IJpaProject getJpaProject() {
		return unmappedPersistentAttribute.getJpaProject();
	}
	
	public String getAttributeName() {
		return attributeText.getText();
	}
	
	public String getMappingKey() {
		StructuredSelection selection = (StructuredSelection) mappingCombo.getSelection();
		return (selection.isEmpty()) ? null : ((IAttributeMappingUiProvider) selection.getFirstElement()).key();
	}
	
	private void validate() {
//		if (entityMappings.containsPersistentType(type)) {
//			updateStatus(
//				new Status(
//					IStatus.WARNING, JptUiPlugin.PLUGIN_ID, 
//					JptUiMessages.AddPersistentClassDialog_duplicateClassWarning));
//			return;
//		}
//		
		String mappingKey = getMappingKey();
		if (mappingKey == null) {
			updateStatus(
				new Status(
					IStatus.ERROR, JptUiPlugin.PLUGIN_ID,
					JptUiMessages.AddPersistentAttributeDialog_noMappingKeyError));
			return;
		}
		
		updateStatus(Status.OK_STATUS);
	}
	
	@Override
	protected void okPressed() {
		unmappedPersistentAttribute.setSpecifiedMappingKey(getMappingKey());
		unmappedPersistentAttribute.setVirtual(false);
		super.okPressed();
	}
}

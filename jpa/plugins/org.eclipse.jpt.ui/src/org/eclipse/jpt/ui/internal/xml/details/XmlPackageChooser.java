 /*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.ControlContentAssistHelper;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.JavaPackageCompletionProcessor;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.core.internal.resource.orm.EntityMappings;
import org.eclipse.jpt.core.internal.resource.orm.OrmPackage;
import org.eclipse.jpt.ui.internal.JptUiPlugin;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.xml.JptUiXmlMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class XmlPackageChooser extends BaseJpaController
{
	private EntityMappingsInternal entityMappings;
	private Adapter entityMappingsListener;
    private IContentAssistProcessor contentAssistProcessor;
	
    private Composite composite;
	private Text text;
	
	
	public XmlPackageChooser(Composite parent, CommandStack theCommandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, theCommandStack, widgetFactory);
		buildSchemaHolderListener();
	}
	
	
	private void buildSchemaHolderListener() {
		this.entityMappingsListener = new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				entityMappingsChanged(notification);
			}
		};
	}
	
	@Override
	protected void buildWidget(Composite parent) {
		this.composite = getWidgetFactory().createComposite(parent);
	    GridLayout gridLayout = new GridLayout();
	    gridLayout.marginHeight = 0;
	    gridLayout.marginWidth = 1;
	    gridLayout.numColumns = 2;
	    this.composite.setLayout(gridLayout);
		this.text = getWidgetFactory().createText(this.composite, "");
		GridData data = new GridData();
	    data.grabExcessHorizontalSpace = true;
	    data.horizontalAlignment = GridData.FILL;
	    this.text.setLayoutData(data);
		this.contentAssistProcessor = new JavaPackageCompletionProcessor(new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_ROOT));
		ControlContentAssistHelper.createTextContentAssistant(this.text, this.contentAssistProcessor);

	    this.text.addModifyListener(
			new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					textModified(e);
				}
			});
	    
		Button browseButton = getWidgetFactory().createButton(this.composite, "Browse...", SWT.FLAT);
		data = new GridData();
		browseButton.setLayoutData(data);
		browseButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				IPackageFragment packageFragment = choosePackage();
				if (packageFragment != null) {
					text.setText(packageFragment.getElementName());
				}
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				IPackageFragment packageFragment = choosePackage();
				if (packageFragment != null) {
					text.setText(packageFragment.getElementName());
				}
			}
		});
	}
	
	private void textModified(ModifyEvent e) {
		if (isPopulating()) {
			return;
		}
		
		String text = ((Text) e.getSource()).getText();
		this.entityMappings.setPackage(text);
		//TODO set a JEM Package??
		//.setJavaClass(JavaRefFactory.eINSTANCE.createClassRef(text));
		
		// TODO Does this need to be done?
		//this.editingDomain.getCommandStack().execute(SetCommand.create(this.editingDomain, this.entity, MappingsPackage.eINSTANCE.getEntity_SpecifiedName(), text));
	}
	
	private void entityMappingsChanged(Notification notification) {
		if (notification.getFeatureID(EntityMappings.class) == 
				OrmPackage.ENTITY_MAPPINGS__PACKAGE) {
			Display.getDefault().asyncExec(
				new Runnable() {
					public void run() {
						populate();
					}
				});
		}
	}
	
	@Override
	protected void engageListeners() {
		if (this.entityMappings != null) {
			entityMappings.eAdapters().add(this.entityMappingsListener);
		}
	}
	
	@Override
	protected void disengageListeners() {
		if (this.entityMappings != null) {
			this.entityMappings.eAdapters().remove(this.entityMappingsListener);
		}
	}
	
	@Override
	public void doPopulate(EObject obj) {
		this.entityMappings = (EntityMappingsInternal) obj;
		populateText();
	}
	
	@Override
	protected void doPopulate() {
		populateText();
	}
	
	private void populateText() {
		if (entityMappings == null) {
			text.clearSelection();
			return;
		}

		IPackageFragmentRoot root = getPackageFragmentRoot();
		if (root != null) {
			((JavaPackageCompletionProcessor)this.contentAssistProcessor).setPackageFragmentRoot(root);
		}
		
		String package_ = this.entityMappings.getPackage();
		
		if (package_ == null) {
			setTextData("");
		}
		else {
			setTextData(package_);
		}
	}
	
	private IPackageFragmentRoot getPackageFragmentRoot() {
		IProject project = ((XmlEObject) this.entityMappings).getJpaProject().getProject();
		IJavaProject root = JavaCore.create(project);
		try {
			return root.getAllPackageFragmentRoots()[0];
		}
		catch (JavaModelException e) {
			JptUiPlugin.log(e);
		}
		return null;
	}
	
	private void setTextData(String textData) {
		if (! textData.equals(text.getText())) {
			text.setText(textData);
		}
	}
	
	@Override
	public Control getControl() {
		return this.composite;
	}
	
	/**
	 * Opens a selection dialog that allows to select a package. 
	 * 
	 * @return returns the selected package or <code>null</code> if the dialog has been canceled.
	 * The caller typically sets the result to the package input field.
	 * <p>
	 * Clients can override this method if they want to offer a different dialog.
	 * </p>
	 * 
	 * @since 3.2
	 */
	protected IPackageFragment choosePackage() {		
		SelectionDialog selectionDialog;
		try {
			selectionDialog = JavaUI.createPackageDialog(text.getShell(), getPackageFragmentRoot());
		}
		catch (JavaModelException e) {
			JptUiPlugin.log(e);
			throw new RuntimeException(e);
		}
		selectionDialog.setTitle(JptUiXmlMessages.XmlPackageChooser_PackageDialog_title); 
		selectionDialog.setMessage(JptUiXmlMessages.XmlPackageChooser_PackageDialog_message); 
		selectionDialog.setHelpAvailable(false);
		IPackageFragment pack= getPackageFragment();
		if (pack != null) {
			selectionDialog.setInitialSelections(new Object[] { pack });
		}

		if (selectionDialog.open() == Window.OK) {
			return (IPackageFragment) selectionDialog.getResult()[0];
		}
		return null;
	}

	/**
	 * Returns the package fragment corresponding to the current input.
	 * 
	 * @return a package fragment or <code>null</code> if the input 
	 * could not be resolved.
	 */
	public IPackageFragment getPackageFragment() {
		String packageString = this.text.getText();
		return getPackageFragmentRoot().getPackageFragment(packageString);
	}
}

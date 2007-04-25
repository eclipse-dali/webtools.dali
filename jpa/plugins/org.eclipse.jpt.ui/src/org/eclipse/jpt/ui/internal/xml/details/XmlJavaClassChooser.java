/*******************************************************************************
 *  Copyright (c) 2006 Oracle. All rights reserved. This
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
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.ControlContentAssistHelper;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.JavaTypeCompletionProcessor;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.content.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping;
import org.eclipse.jpt.ui.internal.JpaUiPlugin;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.xml.JpaUiXmlMessages;
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
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

//TODO possibly help the user and if they have chosen a package at the entity-mappings level
//only insert the class name in the xml file if they choose a class from the package.  
//Not sure if this should be driven by the UI or by ui api in the model
public class XmlJavaClassChooser extends BaseJpaController
{
	private XmlPersistentType persistentType;
	private Adapter persistentTypeListener;
	
	private Composite composite;
	private Text text;
	private JavaTypeCompletionProcessor javaTypeCompletionProcessor;
	
	public XmlJavaClassChooser(Composite parent, CommandStack theCommandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, theCommandStack, widgetFactory);
		buildPersistentTypeListener();
	}
	
	
	private void buildPersistentTypeListener() {
		persistentTypeListener = new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				persistentTypeChanged(notification);
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
	    
		text = getWidgetFactory().createText(this.composite, "");
		GridData data = new GridData();
	    data.grabExcessHorizontalSpace = true;
	    data.horizontalAlignment = GridData.FILL;
	    this.text.setLayoutData(data);
		text.addModifyListener(
			new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					textModified(e);
				}
			});

		//TODO bug 156185 - when this is fixed there should be api for this
		this.javaTypeCompletionProcessor = new JavaTypeCompletionProcessor(false, false);
		ControlContentAssistHelper.createTextContentAssistant(this.text,  this.javaTypeCompletionProcessor);

		
		Button browseButton = getWidgetFactory().createButton(this.composite, "Browse...", SWT.FLAT);
		data = new GridData();
		browseButton.setLayoutData(data);
		browseButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				IType type = chooseType();
				if (type != null) {
					text.setText(type.getFullyQualifiedName());
				}
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				IType type = chooseType();
				if (type != null) {
					text.setText(type.getFullyQualifiedName());
				}
			}
		});

	}

	private void textModified(ModifyEvent e) {
		if (isPopulating()) {
			return;
		}
		
		String text = ((Text) e.getSource()).getText();
		persistentType.setClass(text);
		
		// TODO Does this need to be done?
		//this.editingDomain.getCommandStack().execute(SetCommand.create(this.editingDomain, this.entity, MappingsPackage.eINSTANCE.getEntity_SpecifiedName(), text));
	}
	
	
	protected IType chooseType() {
		IPackageFragmentRoot root= getPackageFragmentRoot();
		if (root == null) {
			return null;
		}	
		
		IJavaElement[] elements= new IJavaElement[] { root.getJavaProject() };
		IJavaSearchScope scope= SearchEngine.createJavaSearchScope(elements);
		IProgressService service = PlatformUI.getWorkbench().getProgressService();
		
		SelectionDialog typeSelectionDialog;
		try {
			typeSelectionDialog = JavaUI.createTypeDialog(this.text.getShell(), service, scope, IJavaElementSearchConstants.CONSIDER_CLASSES, false, getType());
		}
		catch (JavaModelException e) {
			JpaUiPlugin.log(e);
			throw new RuntimeException(e);
		}
		typeSelectionDialog.setTitle(JpaUiXmlMessages.XmlJavaClassChooser_XmlJavaClassDialog_title); 
		typeSelectionDialog.setMessage(JpaUiXmlMessages.XmlJavaClassChooser_XmlJavaClassDialog_message); 

		if (typeSelectionDialog.open() == Window.OK) {
			return (IType) typeSelectionDialog.getResult()[0];
		}
		return null;
	}
	
	private String getType() {
		return this.text.getText();
	}
	
	private IPackageFragmentRoot getPackageFragmentRoot() {
		IProject project = this.persistentType.getJpaProject().getProject();
		IJavaProject root = JavaCore.create(project);
		try {
			return root.getAllPackageFragmentRoots()[0];
		}
		catch (JavaModelException e) {
			JpaUiPlugin.log(e);
		}
		return null;
	}

	private void persistentTypeChanged(Notification notification) {
		if (notification.getFeatureID(XmlPersistentType.class) == 
				OrmPackage.XML_PERSISTENT_TYPE__CLASS) {
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
		if (persistentType != null) {
			persistentType.eAdapters().add(persistentTypeListener);
		}
	}
	
	@Override
	protected void disengageListeners() {
		if (persistentType != null) {
			persistentType.eAdapters().remove(persistentTypeListener);
		}
	}
	
	@Override
	public void doPopulate(EObject obj) {
		persistentType = (obj == null) ? null : ((XmlTypeMapping) obj).getPersistentType();
		populateText();
	}
	
	@Override
	protected void doPopulate() {
		populateText();
	}
	
	private void populateText() {
		if (persistentType == null) {
			text.clearSelection();
			return;
		}
		
		IPackageFragmentRoot root = getPackageFragmentRoot();
		if (root != null) {
			 this.javaTypeCompletionProcessor.setPackageFragment(root.getPackageFragment(""));
		}
		

		String javaClass = persistentType.getClass_();
		
		if (javaClass == null) {
			setTextData("");
		}
		else {
			setTextData(javaClass);
		}
	}
	
	private void setTextData(String textData) {
		if (! textData.equals(text.getText())) {
			text.setText(textData);
		}
	}
	
	public Control getControl() {
		return this.composite;
	}
}

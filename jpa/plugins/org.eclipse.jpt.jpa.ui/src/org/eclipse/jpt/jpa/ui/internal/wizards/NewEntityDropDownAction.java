/*******************************************************************************
 * Copyright (c) 2000, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Oracle - copied and modified from NewJavaEEDropDownAction and NewTypeDropDownAction
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate2;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.eclipse.jdt.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jdt.internal.ui.util.CoreUtility;


/**
 * A type wizard is added to the type drop down if it has a paramater 'javatype':
 *     <wizard
 *         name="My JPT Wizard"
 *         icon="icons/wiz.gif"
 *         category="mycategory"
 *         id="xx.MyWizard">
 *         <class class="org.xx.MyWizard">
 *             <parameter name="jptartifact" value="true"/>
 *         </class> 
 *         <description>
 *             My JPT Wizard
 *         </description>
 *      </wizard>
 */
public class NewEntityDropDownAction extends Action implements IMenuCreator, IWorkbenchWindowPulldownDelegate2 {

	public static class OpenJptWizardAction extends AbstractOpenWizardAction implements Comparable<Object>  {

		private final static String ATT_NAME = "name";//$NON-NLS-1$
		private final static String ATT_CLASS = "class";//$NON-NLS-1$
		private final static String ATT_ICON = "icon";//$NON-NLS-1$
		private static final String TAG_DESCRIPTION = "description";	//$NON-NLS-1$
		
		private IConfigurationElement fConfigurationElement;
		private int menuIndex;

		public OpenJptWizardAction(IConfigurationElement element) {
			this.fConfigurationElement= element;
			setText(element.getAttribute(ATT_NAME));
			
			String description= getDescriptionFromConfig(this.fConfigurationElement);
			setDescription(description);
			setToolTipText(description);
			setImageDescriptor(getIconFromConfig(this.fConfigurationElement));
			setMenuIndex(getMenuIndexFromConfig(this.fConfigurationElement));
		}
		
		private String getDescriptionFromConfig(IConfigurationElement config) {
			IConfigurationElement [] children = config.getChildren(TAG_DESCRIPTION);
			if (children.length>=1) {
				return children[0].getValue();
			}
			return ""; //$NON-NLS-1$
		}

		private ImageDescriptor getIconFromConfig(IConfigurationElement config) {
			String iconName = config.getAttribute(ATT_ICON);
			if (iconName != null) {
				return AbstractUIPlugin.imageDescriptorFromPlugin(config.getContributor().getName(), iconName);
			}
			return null;
		}
		
		private int getMenuIndexFromConfig(IConfigurationElement config) {
			IConfigurationElement[] classElements = config.getChildren(TAG_CLASS);
			if (classElements.length > 0) {
				for (IConfigurationElement classElement : classElements) {
					IConfigurationElement[] paramElements = classElement.getChildren(TAG_PARAMETER);
					for (IConfigurationElement paramElement : paramElements) {
						if (ATT_MENUINDEX.equals(paramElement.getAttribute(TAG_NAME))) {
							return Integer.parseInt(paramElement.getAttribute(TAG_VALUE));
						}
					}
				}
			}
			return Integer.MAX_VALUE;
		}
		
		@Override
		public void run() {
			Shell shell = getShell();
			try {
				INewWizard wizard = createWizard();
				wizard.init(PlatformUI.getWorkbench(), getSelection());
				
				WizardDialog dialog = new WizardDialog(shell, wizard);
				PixelConverter converter = new PixelConverter(JFaceResources.getDialogFont());
				dialog.setMinimumPageSize(converter.convertWidthInCharsToPixels(70), converter.convertHeightInCharsToPixels(20));
				dialog.create();
				int res = dialog.open();
				
				notifyResult(res == Window.OK);
			} catch (CoreException e) {
				JptJpaUiPlugin.log(e);
			}
		}

		@Override
		protected INewWizard createWizard() throws CoreException {
			return (INewWizard) CoreUtility.createExtension(fConfigurationElement, ATT_CLASS);
		}

		public int getMenuIndex() {
			return this.menuIndex;
		}

		public void setMenuIndex(int menuIndex) {
			this.menuIndex = menuIndex;
		}

		public int compareTo(Object o) {
			OpenJptWizardAction action = (OpenJptWizardAction) o;
			return getMenuIndex() - action.getMenuIndex();
		}

	}
	
	
	
	private final static String TAG_WIZARD = "wizard";//$NON-NLS-1$
	private final static String ATT_JPTARTIFACT = "jptartifact";//$NON-NLS-1$
	
	private final static String TAG_PARAMETER = "parameter";//$NON-NLS-1$
	private final static String TAG_NAME = "name";//$NON-NLS-1$
	private final static String TAG_VALUE = "value";//$NON-NLS-1$
	protected final static String ATT_MENUINDEX = "menuIndex";//$NON-NLS-1$
	
	private static final String PL_NEW = "newWizards"; //$NON-NLS-1$
	private static final String TAG_CLASS = "class"; //$NON-NLS-1$
	
	private Menu fMenu;
	
	private Shell fWizardShell;
	
	public NewEntityDropDownAction() {
		this.fMenu= null;
		setMenuCreator(this);
		//PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IJavaHelpContextIds.OPEN_CLASS_WIZARD_ACTION);
	}

	public void dispose() {
		if (this.fMenu != null) {
			this.fMenu.dispose();
			this.fMenu= null;
		}
	}

	public Menu getMenu(Menu parent) {
		return null;
	}

	public Menu getMenu(Control parent) {
		if (this.fMenu == null) {
			this.fMenu = new Menu(parent);
			OpenJptWizardAction[] actions = getActionFromDescriptors();
			for (int i = 0; i < actions.length; i++) {
				OpenJptWizardAction curr = actions[i];
				curr.setShell(this.fWizardShell);
				ActionContributionItem item = new ActionContributionItem(curr);
				item.fill(this.fMenu, -1);				
			}			
		
		}
		return this.fMenu;
	}
	
	@Override
	public void run() {
		getDefaultAction().run();
	}
	
	public Action getDefaultAction() {
		Action[] actions = getActionFromDescriptors();
		if (actions.length > 0)
			return actions[0];
		return null;
	}
	
	public static OpenJptWizardAction[] getActionFromDescriptors() {
		ArrayList<OpenJptWizardAction> containers= new ArrayList<OpenJptWizardAction>();
		
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(PlatformUI.PLUGIN_ID, PL_NEW);
		if (extensionPoint != null) {
			IConfigurationElement[] elements = extensionPoint.getConfigurationElements();
			for (int i = 0; i < elements.length; i++) {
				IConfigurationElement element= elements[i];
				if (element.getName().equals(TAG_WIZARD) && isJptArtifactWizard(element)) {
					containers.add(new OpenJptWizardAction(element));
				}
			}
		}
		OpenJptWizardAction[] actions = containers.toArray(new OpenJptWizardAction[containers.size()]);
		Arrays.sort(actions);
		return actions;
	}
		
	private static boolean isJptArtifactWizard(IConfigurationElement element) {
		IConfigurationElement[] classElements= element.getChildren(TAG_CLASS);
		if (classElements.length > 0) {
			for (int i= 0; i < classElements.length; i++) {
				IConfigurationElement[] paramElements= classElements[i].getChildren(TAG_PARAMETER);
				for (int k = 0; k < paramElements.length; k++) {
					IConfigurationElement curr= paramElements[k];
					if (ATT_JPTARTIFACT.equals(curr.getAttribute(TAG_NAME))) {
						return Boolean.valueOf(curr.getAttribute(TAG_VALUE)).booleanValue();
					}
				}
			}
		}
		return false;
	}
		
	public void init(IWorkbenchWindow window) {
		fWizardShell= window.getShell();
	}
	
	public void run(IAction action) {
		run();
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
	}

}

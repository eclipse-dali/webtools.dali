/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.dialog;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.forms.IMessage;
import org.eclipse.ui.progress.IProgressService;


@SuppressWarnings("restriction")
public class SelectTypeDialog extends TitleAreaDialog {
	
	protected String title;
	protected String message;
	protected Label label;
	protected Text text;
	protected String type;
	protected Button browseButton;
	protected ContentProposalAdapter contentProposalAdapter;
	
	
	protected static KeyStroke ks = null;
	static {
		try {
			ks = KeyStroke.getInstance("Ctrl+Space");			//$NON-NLS-1$
		} catch (ParseException e1) {
			System.err.println("Can't create keystroke object");	//$NON-NLS-1$
			e1.printStackTrace();
		} 
	}
	
	public SelectTypeDialog(String message,
							String initialType) {
		super(PlatformUI.getWorkbench().getDisplay().getActiveShell());
		this.title = JPAEditorMessages.SelectTypeDialog_chooseAttributeTypeDialogTitle;
		this.message = message;
		this.type = initialType;
	}
	
    protected Control createContents(Composite parent) {
        Control contents = super.createContents(parent);
        initializeControls();
        validatePage();
        return contents;
    }
    
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(JPAEditorMessages.SelectTypeDialog_chooseAttributeTypeDialogWindowTitle);
    }
    
    
	protected Point getInitialSize() {
		return new Point(convertHorizontalDLUsToPixels(320),
						 convertVerticalDLUsToPixels(120));
	}    
    
	public String getTypeName() {
		return type;
	}
	
	protected void buttonPressed(int buttonId) {
		type = text.getText().trim();
		super.buttonPressed(buttonId);
	}		
	
	protected boolean isResizable() {
		return true;
	}	
		
	private void createLabel(Composite composite) {
		label = new Label(composite, SWT.LEFT);
		label.setText(JPAEditorMessages.SelectTypeDialog_typeLabel);
	}
	
	private void createTextField(Composite composite) {
		text = new Text(composite, SWT.SINGLE | SWT.BORDER);		
	}
	
	private void createBrowseBtn(Composite composite) {
		browseButton = new Button(composite, SWT.PUSH);
		browseButton.setText(JPAEditorMessages.SelectTypeDialog_browseBtnTxt);
		browseButton.setToolTipText(JPAEditorMessages.SelectTypeDialog_browseBtnDesc);
		browseButton.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				IJavaSearchScope scope= SearchEngine.createWorkspaceScope();
				IProgressService service = PlatformUI.getWorkbench().getProgressService();				
				SelectionDialog d = null;
				try {
					d = JavaUI.createTypeDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), 
					service, 
					scope, 
					IJavaElementSearchConstants.CONSIDER_ALL_TYPES, 
					false, 
					text.getText().trim());
				} catch (JavaModelException e1) {
					System.err.println("Can't create type selaction dialog instance");	//$NON-NLS-1$
					e1.printStackTrace();
				}
				d.open();
				Object[] res = d.getResult(); 
				if (res == null)
					return;
				Object[] obj = d.getResult();
				if (obj == null)
					return;
				IType tp = (IType) obj[0];
				text.setText(tp.getFullyQualifiedName());
				text.setSelection(0, tp.getFullyQualifiedName().length());
			}

			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	private IContentProposalProvider createContentProposalProvider() {
		SimpleContentProposalProvider contProvider = new SimpleContentProposalProvider(JPAEditorConstants.PRIMITIVE_TYPES_AND_WRAPPERS);
		contProvider.setFiltering(true);
		
		contentProposalAdapter = new ContentProposalAdapter(
				text,
				new TextContentAdapter(), 
				contProvider,
				ks,
				new char[] {'b', 'c', 'd', 'i', 'f', 'l', 's', 'j', 'B', 'C', 'D', 'I', 'F', 'L', 'S', 'J'});
		contentProposalAdapter.setEnabled(true);
		contentProposalAdapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		return contProvider;
	}
	
	@Override
	public Control createDialogArea(Composite parent) {
        Composite parentComposite = (Composite) super.createDialogArea(parent);
        GridLayout rowLayout = new GridLayout(3, false);
        Composite composite = new Composite(parentComposite, SWT.NONE);
       
        GridData d = new GridData(GridData.FILL_BOTH);
        d.grabExcessHorizontalSpace = true;
        d.heightHint = 400;
        composite.setLayoutData(d);
        
 		rowLayout.marginLeft = 5;
 		rowLayout.marginTop = 5;
 		rowLayout.marginRight = 5;
 		rowLayout.marginBottom = 30;
 		
 		composite.setLayout(rowLayout);
		createLabel(composite);
		GridData data = new GridData(40,20);
		data.verticalAlignment = SWT.CENTER;
		data.horizontalAlignment = SWT.BEGINNING;
		label.setLayoutData(data);
		createTextField(composite);
		data = new GridData(300, 17);
		data.horizontalAlignment = SWT.FILL;
		data.grabExcessHorizontalSpace = true;
		data.verticalAlignment = SWT.CENTER;
		text.setLayoutData(data);
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setDialogComplete(validatePage());
			}			
		});
		createBrowseBtn(composite);
		data = new GridData(100, 25);
		data.verticalAlignment = SWT.TOP;
		data.horizontalAlignment = SWT.LEFT;
		data.minimumWidth = 80;
		browseButton.setLayoutData(data);		
		createContentProposalProvider();
		return parentComposite;
	}
	
    private void initializeControls() {
        setTitle(title);
        setMessage(message);
        text.setText(type);
        text.setSelection(0, type.length());
        setDialogComplete(validatePage());
    }	
	
    protected void setDialogComplete(boolean value) {
        this.getButton(IDialogConstants.OK_ID).setEnabled(value);
    }	
    
    private boolean validatePage() {
		String type = text.getText().trim();
		if (StringTools.stringIsEmpty(type)) {
			setErrorMessage(JPAEditorMessages.SelectTypeDialog_emptyTypeErrorText);
			return false;
		}
		IStatus stat = getStatus();
		if (stat.isOK()) {
			setErrorMessage(null);
			setMessage(message,	IMessage.NONE);
		} else { 
			setMessage(stat.getMessage(), IMessage.ERROR);
		}
		return (stat.getSeverity() != IStatus.ERROR);
    }
    
    private IStatus getStatus() {
    	String type = text.getText().trim();
    	String[] types = type.split("[<>\\[\\],]");		//$NON-NLS-1$
    	for (int i = 0; i < types.length; i++) {
    		type = types[i].trim();
			IStatus stat = JavaConventions.validateJavaTypeName(type, JavaCore.VERSION_1_6, JavaCore.VERSION_1_6);
			boolean isPrimitive = JPAEditorConstants.PRIMITIVE_TYPES_AND_WRAPPERS_SET.contains(type);
			if (isPrimitive || (stat.getSeverity() != IStatus.ERROR))
				continue;
			return stat;
    	}
		return Status.OK_STATUS;
    }

}

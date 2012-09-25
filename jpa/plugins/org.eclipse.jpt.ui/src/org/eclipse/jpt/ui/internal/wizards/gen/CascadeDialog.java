/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.ui.internal.wizards.gen;

import java.util.Collections;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jpt.gen.internal.AssociationRole;
import org.eclipse.jpt.gen.internal.util.StringUtil;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;

/**
 * Simple dialog allows user to set the cascade property of an associationRole.
 * The value of cascade can be "all", or any combination of other selections.
 * 
 */
public class CascadeDialog extends TrayDialog {

	private static String[] ALL_CASCADES = new String[] {TagNames.ALL_CASCADE, TagNames.PERSIST_CASCADE, TagNames.MERGE_CASCADE
			, TagNames.REMOVE_CASCADE, TagNames.REFRESH_CASCADE};

	private static String[] ALL_CASCADES_LABELS 
		= new String[] { "&all", 		//$NON-NLS-1$
						"&persist",		//$NON-NLS-1$
						"&merge",		//$NON-NLS-1$
						"&remove",		//$NON-NLS-1$
						"r&efresh"};	//$NON-NLS-1$

	
	private Button[] allButtons = new Button[ALL_CASCADES.length];

	private AssociationRole associationRole;
	private List<String> cascades;
	
	protected CascadeDialog(Shell parentShell) {
		super(parentShell);
	}

	public static CascadeDialog create(AssociationRole role) {
		CascadeDialog dlg = new CascadeDialog(Display.getDefault().getActiveShell() );
		dlg.setAssociationRole(role);
		return dlg;
	}

    /*
     * (non-Javadoc) Method declared on Window.
     */
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(JptUiEntityGenMessages.selectCascadeDlgTitle);
		this.getHelpSystem().setHelp(newShell, JpaHelpContextIds.GENERATE_ENTITIES_WIZARD_SELECT_CASCADE);
    }
	
	private void setAssociationRole(AssociationRole role) {
		this.associationRole = role;
		List<String> list = StringUtil.strToList(associationRole.getCascade(), ',', true/*trim*/); //role.getCascade() contains the comma separed cascades (see below)
		if (list == null) {
			list = Collections.emptyList();
		}
		cascades = list;
		
	}

	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		createCascadeTypesGroup(container);
		Dialog.applyDialogFont(container);
		return container;
	}

	private void createCascadeTypesGroup(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setLayout(new GridLayout());
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 275;
		group.setLayoutData(gd);
		group.setText(JptUiEntityGenMessages.cascade);

		for( int i=0; i< ALL_CASCADES.length; i ++ ){
			Button checkbox = new Button(group, SWT.CHECK);
			checkbox.setText( ALL_CASCADES_LABELS[i] );
			checkbox.setSelection( isInList(ALL_CASCADES[i]) ); //$NON-NLS-1$
			checkbox.setData(ALL_CASCADES[i]);
			allButtons[i] = checkbox;
			/*if <code>all</code> is selected then deselect all others*/
			checkbox.addSelectionListener( new SelectionListener(){
				public void widgetDefaultSelected(SelectionEvent e) {}
				public void widgetSelected(SelectionEvent e) {
					Button b = (Button)e.getSource();
					if( b.getSelection() ){
						if( b == allButtons[0] ){
							for( Button btn : allButtons ){
								if( btn != e.getSource() ) btn.setSelection(false);
							}
						}else{
							allButtons[0].setSelection(false); 
						}
					}
				}
			});
		}
	}

	protected void okPressed() {
		StringBuilder builder = new StringBuilder();
		for( Button b : allButtons ){
			if( b.getSelection() ){
				if( builder.length()>0 ){
					builder.append( ',');
				}
				builder.append( b.getData() );
			}
		}
		this.associationRole.setCascade( builder.toString() );
		super.okPressed();
	}	

	private boolean isInList(String cascade) {
		for( String s : cascades ){
			if( s.equals(cascade )){
				return true;
			}
		}
		return false;
	}
	
	protected final IWorkbenchHelpSystem getHelpSystem() {
		return PlatformUI.getWorkbench().getHelpSystem();
	}

}

class TagNames
{
	public static final String BASIC_TAG = "basic";
	public static final String CASCADE_TAG = "cascade";
	public static final String COLUMN_TAG = "column";
	public static final String EMBEDDED_TAG = "embedded";
	public static final String EMBEDDED_ID_TAG = "embedded-id";
	public static final String GENERATED_VALUE_TAG = "generated-value";
	public static final String ID_TAG = "id";
	public static final String ID_CLASS_TAG = "id";
	public static final String JOIN_COLUMN_TAG = "join-column";
	public static final String INVERSE_JOIN_COLUMN_TAG = "inverse-join-column";
	public static final String LOB_TAG = "lob";
	public static final String MANY_TO_MANY_TAG = "many-to-many";
	public static final String MANY_TO_ONE_TAG = "many-to-one";
	public static final String MAPPED_BY_TAG = "mapped-by";
	public static final String ONE_TO_MANY_TAG = "one-to-many";
	public static final String ONE_TO_ONE_TAG = "one-to-one";
	public static final String PK_JOIN_COLUMN_TAG = "primary-key-join-column";
	public static final String TABLE_TAG = "table";
	public static final String VERSION_TAG = "version";
	public static final String JOIN_TABLE_TAG = "join-table";

	/*cascade tags*/
	public static final String ALL_CASCADE = "all";
	public static final String PERSIST_CASCADE = "persist";
	public static final String MERGE_CASCADE = "merge";
	public static final String REMOVE_CASCADE = "remove";
	public static final String REFRESH_CASCADE = "refresh";
}

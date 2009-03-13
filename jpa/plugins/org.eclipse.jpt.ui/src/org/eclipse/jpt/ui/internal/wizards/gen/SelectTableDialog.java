/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.ui.internal.wizards.gen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.ui.CommonImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * A database table selection dialog which allows user to filter tables by name
 */
public class SelectTableDialog extends ElementListSelectionDialog {
	
	public SelectTableDialog(Shell shell){
		super(shell, new ILabelProvider(){
			public Image getImage(Object element) {
				return CommonImages.createImage(CommonImages.TABLE_IMAGE) ;
			}

			public String getText(Object element) {
				return element.toString();
			}
			public void addListener(ILabelProviderListener listener) {}
			public void dispose() {}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			public void removeListener(ILabelProviderListener listener) {}

		});
		this.setTitle( JptUiEntityGenMessages.selectTableDlgTitle );//
		this.setMessage( JptUiEntityGenMessages.selectTableDlgDesc);//
	}
	
	public SelectTableDialog(Shell shell, Schema schema){
		this(shell);
		
		ArrayList<String> list = new ArrayList<String>();
		Iterator<Table> tablesIt = schema.tables();
		while (tablesIt.hasNext())
		{
			Table table = tablesIt.next();
			list.add(table.getName());
		}
		this.setElements( list.toArray() );
		
	}	
	
	public SelectTableDialog(Shell shell, List<String> tableNames){
		this(shell);
		this.setElements( tableNames.toArray() );
	}	
	public String getSelectedTable()
	{
		String tableName = (String)this.getFirstResult();
		return tableName ;
	}
	
}

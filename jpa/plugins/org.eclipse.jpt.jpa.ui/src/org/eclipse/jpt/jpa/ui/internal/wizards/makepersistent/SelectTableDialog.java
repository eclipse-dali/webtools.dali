/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.ui.internal.wizards.makepersistent;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * A database table selection dialog which allows user to filter tables by name
 */
public class SelectTableDialog extends ElementListSelectionDialog {
	
	public SelectTableDialog(Shell shell, final ResourceManager resourceManager){
		super(shell, new ILabelProvider(){
			public Image getImage(Object element) {
				return resourceManager.createImage(JptJpaUiImages.TABLE);
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
		this.setTitle( JptJpaUiMakePersistentMessages.SELECT_TABLE_DLG_TITLE );
		this.setMessage( JptJpaUiMakePersistentMessages.SELECT_TABLE_DLG_DESC);
	}
	
	public SelectTableDialog(Shell shell, ResourceManager resourceManager, Schema schema){
		this(shell, resourceManager);
		
		ArrayList<String> list = new ArrayList<String>();
		for( Table table : schema.getTables() )
		{
			list.add(table.getName());
		}
		this.setElements( list.toArray() );
		
	}	
	
	public SelectTableDialog(Shell shell, ResourceManager resourceManager, List<String> tableNames){
		this(shell, resourceManager);
		this.setElements( tableNames.toArray() );
	}	
	public String getSelectedTable()
	{
		String tableName = (String)this.getFirstResult();
		return tableName ;
	}
	
}
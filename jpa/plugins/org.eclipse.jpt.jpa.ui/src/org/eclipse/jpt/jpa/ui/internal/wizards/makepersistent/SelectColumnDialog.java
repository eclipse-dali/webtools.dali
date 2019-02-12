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

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class SelectColumnDialog extends ElementListSelectionDialog
{		
	public SelectColumnDialog(Shell shell, Table table, String attributeName)
	{
		super(shell, new ILabelProvider()
		{
			public Image getImage(Object element) 
			{
				return null;
			}

			public String getText(Object element) 
			{
				return element.toString();
			}
			public void addListener(ILabelProviderListener listener) {}
			public void dispose() {}

			public boolean isLabelProperty(Object element, String property) 
			{
				return false;
			}

			public void removeListener(ILabelProviderListener listener) {}
		});
		setTitle(JptJpaUiMakePersistentMessages.SELECT_COLUMN_DLG_TITLE);
		String text = String.format(
				JptJpaUiMakePersistentMessages.SELECT_COLUMN_DLG_DESC, 
				attributeName);
		setMessage(text);
	
		ArrayList<String> columnList = new ArrayList<String>();
		for( Column column : table.getColumns() )
		{
			columnList.add(column.getName());
		}
		setElements(columnList.toArray());		
	}
		
	public String getSelectedColumn()
	{
		return (String)this.getFirstResult();
	}
		
}

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
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jpt.jpa.annotate.mapping.EntityRefPropertyElem;
import org.eclipse.jpt.jpa.annotate.util.AnnotateMappingUtil;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class SelectPropertyDialog extends ElementListSelectionDialog
{
	private String entityClass;
	EntityRefPropertyElem refElem;
	private IType refType;
	private boolean isManyToMany;
	
	public SelectPropertyDialog(Shell shell, IProject project,
			String entityClass, EntityRefPropertyElem refElem, boolean isManyToMany)
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
		setTitle(JptJpaUiMakePersistentMessages.CHOOSE_PROPERTY_TITLE);
		
		this.entityClass = entityClass;
		this.refElem = refElem;
		try
		{
			refType = AnnotateMappingUtil.getType(refElem.getRefEntityClassName(), project);
		}
		catch (JavaModelException je)
		{
			JptJpaUiPlugin.instance().logError(je);
		}
		String desc = String.format(
				JptJpaUiMakePersistentMessages.CHOOSE_PROPERTY_DESC, refElem.getRefEntityClassName(),
				refElem.getPropertyName());
		setMessage(desc);
		this.isManyToMany = isManyToMany;
		addProperties();
	}
	
	private void addProperties()
	{
		try 
		{
			Set mappedBySet = AnnotateMappingUtil.getMappedByList(entityClass, refType, isManyToMany);
			Iterator mappedByIt = mappedBySet.iterator();
			ArrayList<String> propList = new ArrayList<String>();
			while (mappedByIt.hasNext())
			{
				propList.add((String)mappedByIt.next());
			}
			setElements(propList.toArray());
		}
		catch (JavaModelException je)
		{
			JptJpaUiPlugin.instance().logError(je);
		}		
	}
		
	public String getSelectedProp()
	{
		return (String)this.getFirstResult();
	}

}

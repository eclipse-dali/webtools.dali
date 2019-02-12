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

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.jpa.annotate.mapping.EntityRefPropertyElem;
import org.eclipse.jpt.jpa.annotate.mapping.JoinTableAttributes;
import org.eclipse.jpt.jpa.annotate.util.AnnotateMappingUtil;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatformFactory;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class JoinPropertiesPage extends WizardPage
{
	protected PersistenceUnit persistenceUnit;
	protected ResourceManager resourceManager;
	protected IProject project;
	protected String javaClass;	
	protected Schema schema;
	protected Table table;
	protected Table inverseTable;
	protected EntityRefPropertyElem refElem;
	protected String tagName;
	private Composite joinPropsComposite;
	
	public JoinPropertiesPage(PersistenceUnit persistenceUnit, ResourceManager resourceManager,
			IProject project, String javaClass, 
			Schema schema, Table table, Table inverseTable, EntityRefPropertyElem refElem)
	{
		super("Join Properties Page"); //$NON-NLS-1$
		this.persistenceUnit = persistenceUnit;
		this.resourceManager = resourceManager;
		this.project = project;
		this.javaClass = javaClass;
		this.refElem = refElem;
		this.schema = schema;		
		this.table = table;
		this.inverseTable = inverseTable;
		setTitle(JptJpaUiMakePersistentMessages.JOIN_PROPS_PAGE_TITLE);
		setMessage(JptJpaUiMakePersistentMessages.JOIN_PROPS_PAGE_DESC);					
	}
	
	public void createControl(Composite parent)
	{
		initializeDialogUnits(parent);
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gl = new GridLayout(1, true);
		composite.setLayout(gl);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.widthHint = 400;
		composite.setLayoutData(gd);
		joinPropsComposite = createJoinProps(composite);
		addListeners();
		setControl(composite);
	}

	@Override
	public IWizardPage getNextPage()
	{
		return null;
	}
	
	@Override
	public void setVisible(boolean visible) 
	{
		super.setVisible(visible);
		if (visible)
		{
			String newTagName = refElem.getTagName();
			if (!AnnotateMappingUtil.areEqual(tagName, newTagName))
			{
				tagName = newTagName;
				initFields();
				refreshJoinProperties();
			}
		}
	}
	
	protected Composite createJoinProps(Composite parent) { return null;}
	
	protected void refreshJoinProperties() {};
	
	protected Composite getJoinPropsComposite()
	{
		return joinPropsComposite;
	}
	
	protected void addListeners() {};
	
	protected void initFields() {};
	
	protected String getJoinTableName()
	{
		String joinTableName = null;
		JoinTableAttributes joinTableAttrs = refElem.getJoinTable();
		if (joinTableAttrs != null)
		{
			joinTableName = joinTableAttrs.getTableName();
		}
		return joinTableName;
	}	
	
	protected boolean isJpa1_0Project()
	{
        return this.persistenceUnit.getJpaPlatform().getJpaVersion().getVersion().equals("1.0");
	}
	
	protected boolean isGeneric1_0Project()
	{
		return isJpa1_0Project() && 
				this.persistenceUnit.getJpaPlatform().getConfig().getId().equals(GenericJpaPlatformFactory.ID);
	}
	
	protected void chooseJoinTable(Text joinTableText)
	{
		SelectTableDialog dlg = new SelectTableDialog(Display.getDefault().getActiveShell(),
				resourceManager, schema);
		if (dlg.open() == Dialog.OK)
        {
			joinTableText.setText(dlg.getSelectedTable());
        }		
	}

	protected void handleJoinTableChange(Text joinTableText)
	{
		String newJoinTable = joinTableText.getText();
		
		if (newJoinTable != null && newJoinTable.length() > 0)
		{
			JoinTableAttributes joinTblAttr = refElem.getJoinTable();
			if (joinTblAttr != null)
			{
				String oldJoinTable = joinTblAttr.getTableName();
				if (oldJoinTable == null || !oldJoinTable.equalsIgnoreCase(newJoinTable))	
				{
					joinTblAttr.setTableName(newJoinTable);
					joinTblAttr.setSchema(schema.getName());
					joinTblAttr.removeAllJoinColumns();
					joinTblAttr.removeAllInverseJoinColumns();
				}
			}
			else
			{
				joinTblAttr = new JoinTableAttributes();
				joinTblAttr.setTableName(newJoinTable);
				joinTblAttr.setSchema(schema.getName());
				refElem.setJoinTable(joinTblAttr);
			}
		}
		else 
		{
			refElem.removeJoinTable();
		}		
		refreshJoinProperties();
	}
	
	protected void chooseMappedBy(Text mappedByText)
	{
		SelectPropertyDialog dlg = new SelectPropertyDialog(Display.getDefault().getActiveShell(),
				project, javaClass, refElem, false);
		if (dlg.open() == Dialog.OK)
        {
			mappedByText.setText(dlg.getSelectedProp());
        }
	}
	
	protected void handleMappedByChange(Text mappedByText)
	{
		if (mappedByText.getText() == null || mappedByText.getText().length() == 0)
			refElem.removeMappedBy();
		else
			refElem.setMappedBy(mappedByText.getText());
	}
	
}

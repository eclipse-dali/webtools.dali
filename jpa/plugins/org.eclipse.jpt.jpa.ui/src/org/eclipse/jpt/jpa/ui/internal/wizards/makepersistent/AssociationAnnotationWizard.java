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
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.jpa.annotate.mapping.EntityRefPropertyElem;
import org.eclipse.jpt.jpa.annotate.util.DefaultTableUtil;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.orm.JPA;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

public class AssociationAnnotationWizard extends Wizard
{
	protected final static int JOIN_PROP_GROUP_HEIGHT = 300;
	private PersistenceUnit persistenceUnit;
	private ResourceManager resourceManager;
	private EntityRefPropertyElem refElem;
	private IProject project;
	private Schema schema;
	private String javaClass;
	private Table table;
	private CardinalityPage cardinalityPage;
	private ManyToManyMappingPage mtmPage;
	private OneToManyMappingPage otmPage;
	private ManyToOneMappingPage mtoPage;
	private OneToOneMappingPage otoPage;
	private ManyToManyJoinPropsPage mtmJoinPropsPage;
	private OneToManyJoinPropsPage otmJoinPropsPage;
	private ManyToOneJoinPropsPage mtoJoinPropsPage;
	private OneToOneJoinPropsPage otoJoinPropsPage;
	private Button oneToManyRadio;
	private Button manyToManyRadio;
	private Button oneToOneRadio;
	private Button manyToOneRadio;
	
	public AssociationAnnotationWizard(PersistenceUnit persistenceUnit, ResourceManager resourceManager, 
			IProject project, String javaClass, 
			Schema schema, Table table, EntityRefPropertyElem refElem)
	{
		this.persistenceUnit = persistenceUnit;
		this.resourceManager = resourceManager;
		this.project = project;
		this.javaClass = javaClass;
		this.schema = schema;
		this.table = table;
		this.refElem = refElem;
		setWindowTitle(JptJpaUiMakePersistentMessages.ASSOCIATION_WIZARD_TITLE);
	}
	
	@Override
	public void addPages()
	{
		try 
		{
			super.addPages();
			cardinalityPage = new CardinalityPage(refElem);
			
			String refClass = refElem.getRefEntityClassName();
			Table refTable = null;
			if (refClass.equals(javaClass))
			{
				refTable = table;
			}
			else
			{
				refTable = DefaultTableUtil.findTable(schema, refClass, persistenceUnit);
			}
			if (refTable != null)
			{
				this.refElem.setReferencedTable(refTable);
			}
			mtmJoinPropsPage = new ManyToManyJoinPropsPage(persistenceUnit, resourceManager, project, javaClass, schema, table, refTable, refElem);
			otmJoinPropsPage = new OneToManyJoinPropsPage(persistenceUnit, resourceManager, project, javaClass, schema, table, refTable, refElem);
			mtoJoinPropsPage = new ManyToOneJoinPropsPage(persistenceUnit, resourceManager, project, javaClass, schema, table, refTable, refElem);
			otoJoinPropsPage = new OneToOneJoinPropsPage(persistenceUnit, resourceManager, project, javaClass, schema, table, refTable, refElem);
			mtmPage = new ManyToManyMappingPage(persistenceUnit, resourceManager, project, refElem, mtmJoinPropsPage);
			otmPage = new OneToManyMappingPage(persistenceUnit, resourceManager, project, refElem, otmJoinPropsPage);
			mtoPage = new ManyToOneMappingPage(persistenceUnit, resourceManager, project, refElem, mtoJoinPropsPage);
			otoPage = new OneToOneMappingPage(persistenceUnit, resourceManager, project, refElem, otoJoinPropsPage);
			addPage(cardinalityPage);
			addPage(mtmPage);
			addPage(otmPage);
			addPage(mtoPage);
			addPage(otoPage);
			addPage(mtmJoinPropsPage);
			addPage(otmJoinPropsPage);
			addPage(mtoJoinPropsPage);
			addPage(otoJoinPropsPage);
		}
		catch (Exception e)
		{
			JptJpaUiPlugin.instance().logError(e);
		}
	}
	
	@Override
	public boolean performFinish()
	{
		return true;
	}

	static void displayNoTargetEntityError(String propertyName)
	{
		MessageBox msgBox = new MessageBox(Display.getDefault().getActiveShell(), SWT.ERROR);
		msgBox.setText(JptJpaUiMakePersistentMessages.ASSOCIATION_WIZARD_ERROR);
		String msg = String.format(JptJpaUiMakePersistentMessages.ASSOCIATION_WIZARD_NO_TARGET_ENTITY, 
				propertyName);
		msgBox.setMessage(msg);
		msgBox.open();		
	}
	
	private class CardinalityPage extends WizardPage
	{
		private EntityRefPropertyElem refElem;
		
		public CardinalityPage(EntityRefPropertyElem refElem)
		{
			super("Cardinality Page"); //$NON-NLS-1$
			this.refElem = refElem;
			setTitle(JptJpaUiMakePersistentMessages.CARDINALITY_PAGE_TITLE);
			setMessage(JptJpaUiMakePersistentMessages.CARDINALITY_PAGE_DESC);		
		}
		
		public void createControl(Composite parent)
		{
			initializeDialogUnits(parent);
			
			Composite composite = new Composite(parent, SWT.NULL);
			GridLayout gl = new GridLayout(1, false);
			composite.setLayout(gl);
			SelectionAdapter selectionAdapter = new SelectionAdapter()
			{
				@Override
				public void widgetSelected(SelectionEvent e)
				{			
					handleCardinalityChange();
				}				
			};
			if (refElem.isOneToMany() || refElem.isManyToMany())
			{
				oneToManyRadio = new Button(composite, SWT.RADIO);
				oneToManyRadio.setText(JPA.ONE_TO_MANY);
				oneToManyRadio.addSelectionListener(selectionAdapter);
				manyToManyRadio = new Button(composite, SWT.RADIO);
				manyToManyRadio.setText(JPA.MANY_TO_MANY);
				manyToManyRadio.addSelectionListener(selectionAdapter);
				if (refElem.isOneToMany())
					oneToManyRadio.setSelection(true);
				else
					manyToManyRadio.setSelection(true);
			}
			else
			{
				oneToOneRadio = new Button(composite, SWT.RADIO);
				oneToOneRadio.setText(JPA.ONE_TO_ONE);
				oneToOneRadio.addSelectionListener(selectionAdapter);
				manyToOneRadio = new Button(composite, SWT.RADIO);
				manyToOneRadio.setText(JPA.MANY_TO_ONE);
				manyToOneRadio.addSelectionListener(selectionAdapter);
				if (refElem.isOneToOne())
					oneToOneRadio.setSelection(true);
				else
					manyToOneRadio.setSelection(true);			
			}
			
			setControl(composite);
		}
		
		@Override
		public IWizardPage getNextPage()
		{
			if (refElem.isOneToMany())
				return otmPage;
			else if (refElem.isManyToMany())
				return mtmPage;
			else if (refElem.isManyToOne())
				return mtoPage;
			else if (refElem.isOneToOne())
				return otoPage;
			return null;
		}
		
		private void handleCardinalityChange()
		{
			if (oneToManyRadio != null && oneToManyRadio.getSelection())
			{
				refElem.setTagName(JPA.ONE_TO_MANY);
			}
			else if (manyToManyRadio != null && manyToManyRadio.getSelection())
			{
				refElem.setTagName(JPA.MANY_TO_MANY);
			}
			else if (manyToOneRadio != null && manyToOneRadio.getSelection())
			{
				refElem.setTagName(JPA.MANY_TO_ONE);
			}
			else if (oneToOneRadio != null && oneToOneRadio.getSelection())
			{
				refElem.setTagName(JPA.ONE_TO_ONE);
			}
			// need to clear the "mappedBy" attr when switching cardinalities
			refElem.removeMappedBy();
		}
	}
	
	static Label createLabel(Composite container, int span, String text)
	{
		return createLabel(container, span, text, -1);
	}

	static Label createLabel(Composite container, int span, String text, int widthHint)
	{
		Label label = new Label(container, SWT.NONE);
		label.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		gd.widthHint = widthHint;
		label.setLayoutData(gd);
		return label;		
	}

	static Button createButton(Composite container, int span, String text, int widthHint, int style)
	{
		Button button = new Button(container, style);
		if (text != null)
			button.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		gd.widthHint = widthHint;
		button.setLayoutData(gd);
		return button;		
	}

	static Text createText(Composite container, int span, boolean fillHorizontal, String text, int style)
	{
		return createText(container, span, fillHorizontal, text, style, -1);
	}

	static Text createText(Composite container, int span, boolean fillHorizontal, String text, int style, int widthHint)
	{
		Text textCtl = new Text(container, style);
		if (text != null)
			textCtl.setText(text);
		GridData gd;
		if (fillHorizontal)
			gd = new GridData(GridData.FILL_HORIZONTAL);
		else
			gd = new GridData();
		gd.widthHint = widthHint;
		gd.horizontalSpan = span;
		textCtl.setLayoutData(gd);
		return textCtl;
	}
	
	static Button createImageButton(Composite container, Image image, int span, int style, String toolTipText)
	{
		Button button = new Button(container, style);
		button.setImage(image);
		GridData gd = new GridData(GridData.END, GridData.CENTER, false, false);
		gd.horizontalSpan = span;
		button.setLayoutData(gd);
		if (toolTipText != null)
			button.setToolTipText(toolTipText);
		return button;		
	}
	
	static Combo createCombo(Composite container, boolean fillHorizontal, int colSpan, int style)
	{
		return createCombo(container, fillHorizontal, colSpan, style, -1);
	}
	
	static Combo createCombo(Composite container, boolean fillHorizontal, int colSpan, int style, int widthHint)
	{
		Combo combo = new Combo(container, style);
		GridData gd;
		if (fillHorizontal)
			gd = new GridData(GridData.FILL_HORIZONTAL);
		else
			gd = new GridData();
		gd.widthHint = widthHint;
		gd.horizontalSpan = colSpan;
		combo.setLayoutData(gd);
		return combo;
	}
	
	static Text createText(Composite container, boolean fillHorizontal, int colSpan, int style)
	{
		Text text = new Text(container, style);
		GridData gd;
		if (fillHorizontal)
		{
			gd = new GridData(GridData.FILL_HORIZONTAL);
		}
		else
		{
			gd = new GridData();
		}
		gd.horizontalSpan = colSpan;
		text.setLayoutData(gd);
		return text;
	}
	
}

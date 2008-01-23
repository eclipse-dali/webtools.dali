/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiPlugin;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                ---------------------------------------------------------- |
 * | Target Entity: |                                                      |v| |
 * |                ---------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IRelationshipMapping
 * @see OneToOneMappingComposite - A container of this pane
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class TargetEntityComposite extends BaseJpaController<IRelationshipMapping>
{
	private CCombo targetEntityCombo;

	/**
	 * Creates a new <code>TargetEntityComposite</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 */
	protected TargetEntityComposite(BaseJpaController<? extends IRelationshipMapping> parentController,
	                                Composite parent) {

		super(parentController, parent);
	}

	/**
	 * Creates a new <code>TargetEntityComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IRelationshipMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public TargetEntityComposite(PropertyValueModel<? extends IRelationshipMapping> subjectHolder,
	                             Composite parent,
	                             TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private Runnable buildOpenTargetEntityAction() {
		return new Runnable() {
			public void run() {
				System.out.println("TODO: Open Editor");
			}
		};
	}


	private Runnable buildOpenTypeAction() {
		return new Runnable() {
			public void run() {
				BusyIndicator.showWhile(targetEntityCombo.getDisplay(), new Runnable() {
					public void run() {
						doOpenSelectionDialog();
					}
				});
			}
		};
	}

	private ModifyListener buildTargetEntityModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {

				String targetEntityName = targetEntityCombo.getText();

				if (StringTools.stringIsEmpty(targetEntityName)) {
					targetEntityName = null;
				}
				else if (targetEntityName.equals(targetEntityCombo.getItem(0)) || targetEntityName.equals("")) { //$NON-NLS-1$
					targetEntityName = null;
				}
				else if (!subject().targetEntityIsValid(targetEntityName)) {
					return;
				}

				if (targetEntityName != null) {
					targetEntityName = targetEntityName.trim();
				}

				subject().setSpecifiedTargetEntity(targetEntityName);
			}
		};
	}

	private Button buildTargetEntitySelectionButton(Composite parent) {
		return buildPushButton(
			parent,
			JptUiMappingsMessages.TargetEntityChooser_browse,
			buildOpenTypeAction()
		);
	}

	private void doOpenSelectionDialog() {
		SelectionDialog dialog;

		try {
			dialog = JavaUI.createTypeDialog(
				getControl().getShell(),
				PlatformUI.getWorkbench().getProgressService(),
				SearchEngine.createWorkspaceScope(),
				IJavaElementSearchConstants.CONSIDER_ALL_TYPES,
				false,
				"");
		}
		catch (JavaModelException e) {
			JptUiPlugin.log(e);
			return;
		}

		dialog.setTitle(JptUiMappingsMessages.TargetEntityChooser_selectTypeTitle);

		if (dialog.open() == Window.OK) {
			IType type = (IType) dialog.getResult()[0];
			this.targetEntityCombo.setText(type.getFullyQualifiedName('$'));
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		this.populateCombo();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		targetEntityCombo = buildCombo(container);
		targetEntityCombo.add(JptUiMappingsMessages.TargetEntityChooser_defaultEmpty);
		targetEntityCombo.addModifyListener(buildTargetEntityModifyListener());

		Hyperlink labelLink = buildHyperLink(container,
			JptUiMappingsMessages.TargetEntityChooser_label,
			buildOpenTargetEntityAction()
		);

		buildLabeledComposite(
			container,
			labelLink,
			targetEntityCombo,
			buildTargetEntitySelectionButton(container),
			IJpaHelpContextIds.MAPPING_TARGET_ENTITY
		);
	}

	private void populateCombo() {

		if (targetEntityCombo.isDisposed()) {
			return;
		}

		targetEntityCombo.clearSelection();

		if (subject() != null) {
			String targetEntity = subject().getSpecifiedTargetEntity();
			targetEntityCombo.setItem(0, NLS.bind(JptUiMappingsMessages.TargetEntityChooser_defaultWithOneParam, subject().getDefaultTargetEntity()));

			if (targetEntity != null) {
				if (!targetEntityCombo.getText().equals(targetEntity)) {
					targetEntityCombo.setText(targetEntity);
				}
			}
			else {
				if (targetEntityCombo.getSelectionIndex() != 0) {
					targetEntityCombo.select(0);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == IRelationshipMapping.DEFAULT_TARGET_ENTITY_PROPERTY ||
		    propertyName == IRelationshipMapping.SPECIFIED_TARGET_ENTITY_PROPERTY) {

			populateCombo();
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String[] propertyNames() {
		return new String[] {
			IRelationshipMapping.DEFAULT_TARGET_ENTITY_PROPERTY,
			IRelationshipMapping.SPECIFIED_TARGET_ENTITY_PROPERTY
		};
	}
}

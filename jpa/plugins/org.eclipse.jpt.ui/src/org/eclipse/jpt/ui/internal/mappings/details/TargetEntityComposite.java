/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Collection;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.forms.widgets.Hyperlink;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                -------------------------------------------- ------------- |
 * | Target Entity: | I                                      |v| | Browse... | |
 * | ¯¯¯¯¯¯¯¯¯¯¯¯¯¯ -------------------------------------------- ------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see RelationshipMapping
 * @see ManyToManyMappingComposite - A container of this pane
 * @see ManyToOneMappingComposite - A container of this pane
 * @see OneToManyMappingComposite - A container of this pane
 * @see OneToOneMappingComposite - A container of this pane
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class TargetEntityComposite extends AbstractFormPane<RelationshipMapping>
{
	private CCombo combo;

	/**
	 * Creates a new <code>TargetEntityComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public TargetEntityComposite(AbstractFormPane<? extends RelationshipMapping> parentPane,
	                             Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>TargetEntityComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IRelationshipMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public TargetEntityComposite(PropertyValueModel<? extends RelationshipMapping> subjectHolder,
	                             Composite parent,
	                             WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(RelationshipMapping.DEFAULT_TARGET_ENTITY_PROPERTY);
		propertyNames.add(RelationshipMapping.SPECIFIED_TARGET_ENTITY_PROPERTY);
	}

	private FocusListener buildFocusListener() {

		return new FocusListener() {

			public void focusGained(FocusEvent e) {

				if (!isPopulating()) {
					CCombo combo = (CCombo) e.widget;

					if (combo.getSelectionIndex() == 0) {
						setPopulating(true);

						try {
							combo.setText("");
						}
						finally {
							setPopulating(false);
						}
					}
				}
			}

			public void focusLost(FocusEvent e) {
				if (!isPopulating()) {
					CCombo combo = (CCombo) e.widget;

					if (combo.getText().length() == 0) {
						setPopulating(true);

						try {
							combo.select(0);
						}
						finally {
							setPopulating(false);
						}
					}
				}
			}
		};
	}

	private Runnable buildOpenTargetEntityAction() {
		return new Runnable() {
			public void run() {
				TargetEntityComposite.this.openEditor();
			}
		};
	}

	private Runnable buildOpenTypeAction() {
		return new Runnable() {
			public void run() {
				BusyIndicator.showWhile(combo.getDisplay(), new Runnable() {
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
				if (!isPopulating()) {
					CCombo combo = (CCombo) e.widget;
					valueChanged(combo.getText());
				}
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
				false
			);
		}
		catch (JavaModelException e) {
			JptUiPlugin.log(e);
			return;
		}

		dialog.setTitle(JptUiMappingsMessages.TargetEntityChooser_selectTypeTitle);

		if (dialog.open() == Window.OK) {
			IType type = (IType) dialog.getResult()[0];
			this.combo.setText(type.getFullyQualifiedName('$'));
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {

		super.doPopulate();

		combo.removeAll();
		populateCombo();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		combo = buildEditableCCombo(container);
		combo.add(JptUiMappingsMessages.TargetEntityChooser_defaultEmpty);
		combo.addModifyListener(buildTargetEntityModifyListener());
		combo.addFocusListener(buildFocusListener());

		Hyperlink labelLink = buildHyperLink(container,
			JptUiMappingsMessages.TargetEntityChooser_label,
			buildOpenTargetEntityAction()
		);

		buildLabeledComposite(
			container,
			labelLink,
			combo,
			buildTargetEntitySelectionButton(container),
			JpaHelpContextIds.MAPPING_TARGET_ENTITY
		);
	}

	private void openEditor() {

		String targetEntity = subject().getTargetEntity();

		if (targetEntity != null) {

			try {
				IType type = subject().getJpaProject().getJavaProject().findType(targetEntity);

				if (type != null) {
					IJavaElement javaElement = type.getParent();
					JavaUI.openInEditor(javaElement, true, true);
				}
			}
			catch (JavaModelException e) {
				JptUiPlugin.log(e);
			}
			catch (PartInitException e) {
				JptUiPlugin.log(e);
			}
		}
	}

	private void populateCombo() {

		populateDefaultValue();
		// TODO Add possible target entity names
		updateSelectedItem();
	}

	/**
	 * Adds the default value to the combo if one exists.
	 */
	private void populateDefaultValue() {

		RelationshipMapping entity = subject();
		String defaultValue = (entity != null) ? entity.getDefaultTargetEntity() : null;

		if (defaultValue != null) {
			combo.add(NLS.bind(
				JptUiMappingsMessages.TargetEntityChooser_defaultWithOneParam,
				defaultValue
			));
		}
		else {
			combo.add(JptUiMappingsMessages.TargetEntityChooser_defaultEmpty);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == RelationshipMapping.DEFAULT_TARGET_ENTITY_PROPERTY ||
		    propertyName == RelationshipMapping.SPECIFIED_TARGET_ENTITY_PROPERTY) {

			populateCombo();
		}
	}

	/**
	 * Updates the selected item by selected the current value, if not
	 * <code>null</code>, or select the default value if one is available,
	 * otherwise remove the selection.
	 * <p>
	 * <b>Note:</b> It seems the text can be shown as truncated, changing the
	 * selection to (0, 0) makes the entire text visible.
	 */
	private void updateSelectedItem() {
		RelationshipMapping subject = subject();
		String value = (subject != null) ? subject.getSpecifiedTargetEntity() : null;

		if (value != null) {
			combo.setText(value);
			combo.setSelection(new Point(0, 0));
		}
		else {
			String defaultValue = (subject != null) ? subject.getDefaultTargetEntity() : null;
			String displayString = JptUiMappingsMessages.TargetEntityChooser_defaultEmpty;

			if (defaultValue != null) {
				displayString = NLS.bind(
					JptUiMappingsMessages.ColumnComposite_defaultWithOneParam,
					defaultValue
				);
			}

			// Selected the default value
			if (displayString != null) {
				combo.select(0);
				combo.setSelection(new Point(0, 0));
			}
			// Remove the selection
			else {
				combo.select(-1);
			}
		}
	}

	private void valueChanged(String value) {

		RelationshipMapping subject = subject();
		String oldValue = (subject != null) ? subject.getSpecifiedTargetEntity() : null;

		// Check for null value
		if (StringTools.stringIsEmpty(value)) {
			value = null;

			if (StringTools.stringIsEmpty(oldValue)) {
				return;
			}
		}

		// The default value
		if (value != null &&
		    combo.getItemCount() > 0 &&
		    value.equals(combo.getItem(0)))
		{
			value = null;
		}

		// Nothing to change
		if ((oldValue == value) && value == null) {
			return;
		}

		// Set the new value
		if ((value != null) && (oldValue == null) ||
		   ((oldValue != null) && !oldValue.equals(value))) {

			setPopulating(true);

			try {
				subject.setSpecifiedTargetEntity(value);
			}
			finally {
				setPopulating(false);
			}
		}
	}
}
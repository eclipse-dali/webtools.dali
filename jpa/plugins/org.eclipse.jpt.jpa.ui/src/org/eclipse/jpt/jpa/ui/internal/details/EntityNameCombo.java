/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.StringObjectTransformer;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |              ------------------------------------------------------------ |
 * | Entity Name: | I                                                      |v| |
 * |              ------------------------------------------------------------ |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Entity
 * @see AbstractEntityComposite - The parent container
 *
 * @version 2.3
 * @since 1.0
 */
public class EntityNameCombo
	extends Pane<Entity>
{
	private Combo combo;

	/**
	 * Creates a new <code>EntityNameComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EntityNameCombo(
			Pane<? extends Entity> parentPane,
			Composite parent) {
		
		super(parentPane, parent);
	}

	@Override
	protected boolean addsComposite() {
		return false;
	}

	@Override
	public Control getControl() {
		return this.combo;
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.combo = addEditableCombo(
			container,
			buildDefaultEntityNameListHolder(),
			buildEntityNameHolder(),
			StringObjectTransformer.<String>instance(),
			JpaHelpContextIds.ENTITY_NAME);
		
		SWTUtil.attachDefaultValueHandler(this.combo);
	}

	private ListValueModel<String> buildDefaultEntityNameListHolder() {
		return new PropertyListValueModelAdapter<String>(
			buildDefaultEntityNameHolder()
		);
	}

	private PropertyValueModel<String> buildDefaultEntityNameHolder() {
		return new PropertyAspectAdapter<Entity, String>(getSubjectHolder(), Entity.DEFAULT_NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return defaultValue(this.subject);
			}
		};
	}

	private ModifiablePropertyValueModel<String> buildEntityNameHolder() {
		return new PropertyAspectAdapter<Entity, String>(getSubjectHolder(), Entity.SPECIFIED_NAME_PROPERTY, Entity.DEFAULT_NAME_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = this.subject.getSpecifiedName();

				if (name == null) {
					name = defaultValue(this.subject);
				}

				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (defaultValue(this.subject).equals(value)) {
					value = null;
				}

				this.subject.setSpecifiedName(value);
			}
		};
	}

	private String defaultValue(Entity subject) {
		String defaultValue = subject.getDefaultName();

		if (defaultValue != null) {
			return NLS.bind(
				JptCommonUiMessages.DefaultWithOneParam,
				defaultValue
			);
		}
		return JptCommonUiMessages.DefaultEmpty;
	}
}

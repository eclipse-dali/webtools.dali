/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

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
public class EntityNameComposite
	extends Pane<Entity>
{
	/**
	 * Creates a new <code>EntityNameComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EntityNameComposite(
			Pane<? extends Entity> parentPane,
			Composite parent) {
		
		super(parentPane, parent);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		Combo combo = addLabeledEditableCombo(
			container,
			JptUiDetailsMessages.EntityNameComposite_name,
			buildDefaultEntityNameListHolder(),
			buildEntityNameHolder(),
			JpaHelpContextIds.ENTITY_NAME);
		
		SWTUtil.attachDefaultValueHandler(combo);
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

	private WritablePropertyValueModel<String> buildEntityNameHolder() {
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

/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.jpa2.details.JptJpaUiDetailsMessages2_0;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | [X]  Cacheable (true/false)
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Cacheable2_0
 * @see JavaEntityComposite2_0 - A container of this widget
 * @see OrmEntityComposite2_0 - A container of this widget
 */
public class CacheableTriStateCheckBox2_0
	extends Pane<Cacheable2_0>
{
	private TriStateCheckBox checkBox;

	public CacheableTriStateCheckBox2_0(
			Pane<?> parentPane,
			PropertyValueModel<? extends Cacheable2_0> subjectHolder,
	        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected boolean addsComposite() {
		return false;
	}

	@Override
	public Control getControl() {
		return this.checkBox.getCheckBox();
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.checkBox = addTriStateCheckBoxWithDefault(
			container,
			JptJpaUiDetailsMessages2_0.ENTITY_CACHEABLE_LABEL,
			buildCacheableBooleanHolder(),
			buildCacheableStringHolder(),
			JpaHelpContextIds.ENTITY_CACHEABLE
		);
	}
	

	private ModifiablePropertyValueModel<Boolean> buildCacheableBooleanHolder() {
		return new PropertyAspectAdapter<Cacheable2_0, Boolean>(
			getSubjectHolder(),
			Cacheable2_0.DEFAULT_CACHEABLE_PROPERTY,
			Cacheable2_0.SPECIFIED_CACHEABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedCacheable();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedCacheable(value);
			}
		};
	}

	private PropertyValueModel<String> buildCacheableStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultCacheableHolder()) {

			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaUiDetailsMessages2_0.ENTITY_CACHEABLE_WITH_DEFAULT_LABEL, defaultStringValue);
				}
				return JptJpaUiDetailsMessages2_0.ENTITY_CACHEABLE_LABEL;
			}
		};
	}

	private PropertyValueModel<Boolean> buildDefaultCacheableHolder() {
		return new PropertyAspectAdapter<Cacheable2_0, Boolean>(
			getSubjectHolder(),
			Cacheable2_0.SPECIFIED_CACHEABLE_PROPERTY,
			Cacheable2_0.DEFAULT_CACHEABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedCacheable() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultCacheable());
			}
		};
	}
}

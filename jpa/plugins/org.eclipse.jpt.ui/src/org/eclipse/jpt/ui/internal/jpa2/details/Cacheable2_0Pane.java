/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | [X]  Cacheable (true/false)
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Cacheable2_0
 * @see JavaEntity2_0Composite - A container of this widget
 * @see OrmEntity2_0Composite - A container of this widget
 */
public class Cacheable2_0Pane 
	extends Pane<Cacheable2_0>
{
	public Cacheable2_0Pane(
			Pane<?> parentPane,
			PropertyValueModel<? extends Cacheable2_0> subjectHolder,
	        Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		addTriStateCheckBoxWithDefault(
			container,
			JptUiDetailsMessages2_0.Entity_cacheableLabel,
			buildCacheableBooleanHolder(),
			buildCacheableStringHolder(),
			JpaHelpContextIds.ENTITY_CACHEABLE
		);
	}
	

	private WritablePropertyValueModel<Boolean> buildCacheableBooleanHolder() {
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
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(JptUiDetailsMessages2_0.Entity_cacheableWithDefaultLabel, defaultStringValue);
				}
				return JptUiDetailsMessages2_0.Entity_cacheableLabel;
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

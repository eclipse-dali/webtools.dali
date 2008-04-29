/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.caching;

import org.eclipse.jpt.eclipselink.core.internal.context.caching.Caching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 *  SharedCacheComposite
 */
public class SharedCacheComposite extends AbstractPane<EntityCacheProperties>
{
	private TriStateCheckBox sharedCacheCheckBox;

	/**
	 * Creates a new <code>ShareCacheComposite</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 */
	public SharedCacheComposite(AbstractPane<EntityCacheProperties> parentComposite,
	                           Composite parent) {

		super(parentComposite, parent);
	}

	private WritablePropertyValueModel<Boolean> buildSharedCacheHolder() {
		return new PropertyAspectAdapter<EntityCacheProperties, Boolean>(
					getSubjectHolder(), Caching.SHARED_CACHE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSharedCache();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSharedCache(value);
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Boolean.TRUE, newValue);
				}
			}
		};
	}

	private PropertyValueModel<String> buildSharedCacheStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildSharedCacheHolder()) {
			@Override
			protected String transform(Boolean value) {
				if ((subject() != null) && (value == null)) {
					Boolean defaultValue = subject().getDefaultSharedCache();
					if (defaultValue != null) {
						String defaultStringValue = defaultValue ? EclipseLinkUiMessages.Boolean_True : EclipseLinkUiMessages.Boolean_False;
						return NLS.bind(EclipseLinkUiMessages.PersistenceXmlCachingTab_defaultSharedCacheLabel, defaultStringValue);
					}
				}
				return EclipseLinkUiMessages.PersistenceXmlCachingTab_sharedCacheLabel;
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.sharedCacheCheckBox = this.buildTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlCachingTab_sharedCacheLabel,
			this.buildSharedCacheHolder(),
			this.buildSharedCacheStringHolder(),
			null
//			EclipseLinkHelpContextIds.CACHING_SHARED_CACHE
		);
	}

	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);
		this.sharedCacheCheckBox.setEnabled(enabled);
	}
}
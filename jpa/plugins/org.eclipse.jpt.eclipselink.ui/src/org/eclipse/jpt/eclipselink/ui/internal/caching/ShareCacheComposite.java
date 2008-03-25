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
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 *  ShareCacheComposite 
 */
public class ShareCacheComposite extends AbstractFormPane<Caching>
{
	private EntityListComposite entitiesComposite;

	/**
	 * Creates a new <code>ShareCacheComposite</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 */
	public ShareCacheComposite(
									AbstractFormPane<? extends Caching> parentComposite, 
									Composite parent, 
									EntityListComposite entitiesComposite) {
		
		super(parentComposite, parent);
		this.entitiesComposite = entitiesComposite;
	}

	private PropertyValueModel<String> buildSharedCacheStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildSharedCacheHolder()) {
			@Override
			protected String transform(Boolean value) {
				if ((subject() != null) && (value == null)) {
					Boolean defaultValue = subject().getSharedCacheDefault();
					if (defaultValue != null) {
						String defaultStringValue = defaultValue ? EclipseLinkUiMessages.Boolean_True : EclipseLinkUiMessages.Boolean_False;
						return NLS.bind(EclipseLinkUiMessages.PersistenceXmlCachingTab_defaultSharedCacheLabelDefault, defaultStringValue);
					}
				}
				return EclipseLinkUiMessages.PersistenceXmlCachingTab_sharedCacheLabel;
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildSharedCacheHolder() {
		return new PropertyAspectAdapter<Caching, Boolean>(getSubjectHolder(), Caching.SHARED_CACHE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				String entityName = ShareCacheComposite.this.getSelection();
				if (!StringTools.stringIsEmpty(entityName)) {
					return this.subject.getSharedCache(entityName);
				}
				return null;
			}

			@Override
			protected void setValue_(Boolean value) {
				String entityName = ShareCacheComposite.this.getSelection();
				if (!StringTools.stringIsEmpty(entityName)) {
					this.subject.setSharedCache(value, entityName);
				}
			}
		};
	}

	protected String getSelection() {
		if (this.entitiesComposite == null) {
			return null;
		}
		return (String) this.entitiesComposite.listPane().getSelectionModel().selectedValue();
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.buildTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlCachingTab_sharedCacheLabel,
			this.buildSharedCacheHolder(),
			this.buildSharedCacheStringHolder(),
			null
//			EclipseLinkHelpContextIds.CACHING_SHARED_CACHE
		);
	}
}

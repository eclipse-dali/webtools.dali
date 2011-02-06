/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.caching;

import java.util.ArrayList;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.caching.Entity;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 *  SharedCacheComposite
 */
public class SharedCacheComposite extends Pane<Entity>
{
	private TriStateCheckBox sharedCacheCheckBox;

	/**
	 * Creates a new <code>ShareCacheComposite</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 */
	public SharedCacheComposite(Pane<Entity> parentComposite,
	                           Composite parent) {

		super(parentComposite, parent);
	}

	private PropertyValueModel<Caching> buildCachingHolder() {
		return new TransformationPropertyValueModel<Entity, Caching>(this.getSubjectHolder()) {
			@Override
			protected Caching transform_(Entity value) {
				return value.getParent();
			}
		};
	}

	private PropertyValueModel<Boolean> buildDefaultAndNonDefaultSharedCacheHolder() {
		return new ListPropertyValueModelAdapter<Boolean>(
			buildDefaultAndNonDefaultSharedCacheListHolder()
		) {
			@Override
			protected Boolean buildValue() {
				// If the number of ListValueModel equals 1, that means the shared
				// Cache properties is not set (partially selected), which means we
				// want to see the default value appended to the text
				if (this.listHolder.size() == 1) {
					return (Boolean) this.listHolder.listIterator().next();
				}
				return null;
			}
		};
	}

	private ListValueModel<Boolean> buildDefaultAndNonDefaultSharedCacheListHolder() {
		ArrayList<ListValueModel<Boolean>> holders = new ArrayList<ListValueModel<Boolean>>(2);
		holders.add(buildSharedCacheListHolder());
		holders.add(buildDefaultSharedCacheListHolder());

		return new CompositeListValueModel<ListValueModel<Boolean>, Boolean>(
			holders
		);
	}

	private PropertyValueModel<Boolean> buildDefaultSharedCacheHolder() {
		return new PropertyAspectAdapter<Caching, Boolean>(buildCachingHolder(), Caching.SHARED_CACHE_DEFAULT_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				Boolean value = this.subject.getSharedCacheDefault();
				if (value == null) {
					value = this.subject.getDefaultSharedCacheDefault();
				}
				return value;
			}
		};
	}

	private ListValueModel<Boolean> buildDefaultSharedCacheListHolder() {
		return new PropertyListValueModelAdapter<Boolean>(
			buildDefaultSharedCacheHolder()
		);
	}

	private WritablePropertyValueModel<Boolean> buildSharedCacheHolder() {
		return new PropertyAspectAdapter<Entity, Boolean>(
					getSubjectHolder(), Entity.SHARED_CACHE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return getSubjectParent().getSharedCacheOf(getSubjectName());
			}

			@Override
			protected void setValue_(Boolean value) {
				getSubjectParent().setSharedCacheOf(getSubjectName(), value);
			}
		};
	}
	
	private String getSubjectName() {
		return this.getSubjectHolder().getValue().getName();
	}
	
	private Caching getSubjectParent() {
		return this.getSubjectHolder().getValue().getParent();
	}

	private ListValueModel<Boolean> buildSharedCacheListHolder() {
		return new PropertyListValueModelAdapter<Boolean>(
			buildSharedCacheHolder()
		);
	}

	private PropertyValueModel<String> buildSharedCacheStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultAndNonDefaultSharedCacheHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlCachingTab_defaultSharedCacheLabel, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlCachingTab_sharedCacheLabel;
			}
		};
	}

	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);
		this.sharedCacheCheckBox.setEnabled(enabled);
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.sharedCacheCheckBox = this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlCachingTab_sharedCacheLabel,
			this.buildSharedCacheHolder(),
			this.buildSharedCacheStringHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_CACHING
		);
	}
}
/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.eclipselink.core.context.Caching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.java.details.EclipseLinkJavaEntityComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * This pane shows the caching options.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | x Shared                                                                  |
 * |    CacheTypeComposite                                                     |
 * |    CacheSizeComposite                                                     |
 * |    > Advanced   	                                                       |
 * |    	ExpiryComposite                                                    |
 * |    	AlwaysRefreshComposite                                             |
 * |   		RefreshOnlyIfNewerComposite                                        |
 * |    	DisableHitsComposite                                               |
 * |    	CacheCoordinationComposite                                         |
 * | ExistenceTypeComposite                                                    |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Entity
 * @see Caching
 * @see EclipseLinkJavaEntityComposite - The parent container
 * @see CacheTypeComposite
 * @see CacheSizeComposite
 * @see AlwaysRefreshComposite
 * @see RefreshOnlyIfNewerComposite
 * @see DisableHitsComposite
 *
 * @version 2.1
 * @since 2.1
 */
public abstract class CachingComposite<T extends Caching> extends FormPane<T>
{

	protected CachingComposite(FormPane<?> parentPane,
        PropertyValueModel<T> subjectHolder,
        Composite parent) {

		super(parentPane, subjectHolder, parent, false);
	}

	@Override
	protected void initializeLayout(Composite container) {

		//Shared Check box, uncheck this and the rest of the panel is disabled
		addTriStateCheckBoxWithDefault(
			addSubPane(container, 8),
			EclipseLinkUiMappingsMessages.CachingComposite_sharedLabel,
			buildSpecifiedSharedHolder(),
			buildSharedStringHolder(),
			EclipseLinkHelpContextIds.CACHING_SHARED
		);

		Composite subPane = addSubPane(container, 0, 16);

		Collection<Pane<?>> panes = new ArrayList<Pane<?>>();
		
		panes.add(new CacheTypeComposite(this, subPane));
		panes.add(new CacheSizeComposite(this, subPane));
		
		// Advanced sub-pane
		Composite advancedSection = addCollapsableSubSection(
			subPane,
			EclipseLinkUiMappingsMessages.CachingComposite_advanced,
			new SimplePropertyValueModel<Boolean>(Boolean.FALSE)
		);

		initializeAdvancedPane(addSubPane(advancedSection, 0, 16), panes);
			
		new PaneEnabler(buildSharedCacheEnabler(), panes);
		
		initializeExistenceCheckingComposite(addSubPane(container, 8));
	}
	
	protected void initializeAdvancedPane(Composite container, Collection<Pane<?>> panes) {
		panes.add(new ExpiryComposite(this, container));
		panes.add(new AlwaysRefreshComposite(this, container));
		panes.add(new RefreshOnlyIfNewerComposite(this, container));
		panes.add(new DisableHitsComposite(this, container));
		panes.add(new CacheCoordinationTypeComposite(this, container));
	}
	
	protected abstract void initializeExistenceCheckingComposite(Composite parent);
	
	private PropertyValueModel<Boolean> buildSharedCacheEnabler() {
		return new PropertyAspectAdapter<Caching, Boolean>(
				getSubjectHolder(), 
				Caching.SPECIFIED_SHARED_PROPERTY, 
				Caching.DEFAULT_SHARED_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isShared());
			}
		};
	}	
	
	private WritablePropertyValueModel<Boolean> buildSpecifiedSharedHolder() {
		return new PropertyAspectAdapter<Caching, Boolean>(getSubjectHolder(), Caching.SPECIFIED_SHARED_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedShared();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedShared(value);
			}
		};
	}

	private PropertyValueModel<String> buildSharedStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultSharedHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptUiMappingsMessages.Boolean_True : JptUiMappingsMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMappingsMessages.CachingComposite_sharedLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMappingsMessages.CachingComposite_sharedLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultSharedHolder() {
		return new PropertyAspectAdapter<Caching, Boolean>(
			getSubjectHolder(),
			Caching.SPECIFIED_SHARED_PROPERTY,
			Caching.DEFAULT_SHARED_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedShared() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultShared());
			}
		};
	}
}
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
import org.eclipse.jpt.ui.internal.util.LabeledControlUpdater;
import org.eclipse.jpt.ui.internal.util.LabeledLabel;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

/**
 * CacheSizeComposite
 */
@SuppressWarnings("nls")
public class CacheSizeComposite extends AbstractPane<EntityCacheProperties>
{
	/**
	 * Creates a new <code>CacheTypeComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public CacheSizeComposite(AbstractPane<EntityCacheProperties> parentComposite,
	                          Composite parent) {

		super(parentComposite, parent);
	}

	private WritablePropertyValueModel<Integer> buildCacheSizeHolder() {
		return new PropertyAspectAdapter<EntityCacheProperties, Integer>(getSubjectHolder(), EntityCacheProperties.CACHE_SIZE_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				Integer value = subject.getCacheSize();
				if (value == null) {
					value = -1;
				}
				return value;
			}

			@Override
			protected void setValue_(Integer value) {
				if (value == -1) {
					value = null;
				}
				subject.setCacheSize(value);
			}
		};
	}

	private PropertyValueModel<Caching> buildCachingHolder() {
		return new TransformationPropertyValueModel<EntityCacheProperties, Caching>(getSubjectHolder()) {
			@Override
			protected Caching transform_(EntityCacheProperties value) {
				return value.getCaching();
			}
		};
	}

	private PropertyValueModel<Integer> buildDefaultCacheSizeHolder() {
		return new PropertyAspectAdapter<Caching, Integer>(buildCachingHolder(), Caching.CACHE_SIZE_DEFAULT_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				Integer value = subject.getCacheSizeDefault();
				if (value == null) {
					value = subject.getDefaultCacheSizeDefault();
				}
				return value;
			}
		};
	}

	private Control buildDefaultCacheSizeLabel(Composite container) {

		Label label = buildLabel(
			container,
			EclipseLinkUiMessages.DefaultWithoutValue
		);

		new LabeledControlUpdater(
			new LabeledLabel(label),
			buildDefaultCacheSizeLabelHolder()
		);

		return label;
	}

	private PropertyValueModel<String> buildDefaultCacheSizeLabelHolder() {

		return new TransformationPropertyValueModel<Integer, String>(buildDefaultCacheSizeHolder()) {

			@Override
			protected String transform(Integer value) {

				if (value != null) {
					return NLS.bind(EclipseLinkUiMessages.DefaultWithValue, value);
				}

				return "";
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		Spinner spinner = this.buildLabeledSpinner(
			container,
			EclipseLinkUiMessages.CacheSizeComposite_cacheSize,
			this.buildCacheSizeHolder(),
			-1,
			-1,
			Integer.MAX_VALUE,
			this.buildDefaultCacheSizeLabel(container)
		);

		updateGridData(container, spinner);
	}

	/**
	 * Changes the layout of the given container by changing which widget will
	 * grab the excess of horizontal space. By default, the center control grabs
	 * the excess space, we change it to be the right control.
	 *
	 * @param container The container containing the controls needing their
	 * <code>GridData</code> to be modified from the default values
	 * @param spinner The spinner that got created
	 */
	private void updateGridData(Composite container, Spinner spinner) {

		// It is possible the spinner's parent is not the container of the
		// label, spinner and right control (a pane is sometimes required for
		// painting the spinner's border)
		Composite paneContainer = spinner.getParent();

		while (container != paneContainer.getParent()) {
			paneContainer = paneContainer.getParent();
		}

		Control[] controls = paneContainer.getChildren();

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.horizontalAlignment       = GridData.BEGINNING;
		controls[1].setLayoutData(gridData);

		controls[2].setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		removeAlignRight(controls[2]);
	}
}
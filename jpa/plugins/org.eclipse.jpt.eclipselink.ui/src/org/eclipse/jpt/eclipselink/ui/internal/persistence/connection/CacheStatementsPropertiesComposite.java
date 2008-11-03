/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.connection;

import org.eclipse.jpt.eclipselink.core.internal.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.util.ControlEnabler;
import org.eclipse.jpt.ui.internal.util.LabeledControlUpdater;
import org.eclipse.jpt.ui.internal.util.LabeledLabel;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.ui.internal.widgets.TriStateCheckBox;
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
 *  CacheStatementsPropertiesComposite
 */
@SuppressWarnings("nls")
public class CacheStatementsPropertiesComposite extends Pane<Connection>
{
	public CacheStatementsPropertiesComposite(Pane<Connection> parentComposite, Composite parent) {

		super(parentComposite, parent);
	}

	private WritablePropertyValueModel<Boolean> buildCacheStatementsHolder() {
		return new PropertyAspectAdapter<Connection, Boolean>(getSubjectHolder(), Connection.CACHE_STATEMENTS_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.getCacheStatements();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setCacheStatements(value);
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

	private WritablePropertyValueModel<Integer> buildCacheStatementsSizeHolder() {
		return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder(), Connection.CACHE_STATEMENTS_SIZE_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				Integer value = subject.getCacheStatementsSize();

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
				subject.setCacheStatementsSize(value);
			}
		};
	}

	private PropertyValueModel<Integer> buildDefaultCacheStatementsSizeHolder() {
		return new PropertyAspectAdapter<Connection, Integer>(getSubjectHolder(), "") {
			@Override
			protected Integer buildValue_() {
				return subject.getDefaultCacheStatementsSize();
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Integer.MIN_VALUE, newValue);
				}
			}
		};
	}

	private Label addDefaultCacheStatementsSizeLabel(Composite container) {

		Label label = addUnmanagedLabel(
			container,
			EclipseLinkUiMessages.DefaultWithoutValue
		);

		new LabeledControlUpdater(
			new LabeledLabel(label),
			buildDefaultCacheStatementsSizeLabelHolder()
		);

		return label;
	}

	private PropertyValueModel<String> buildDefaultCacheStatementsSizeLabelHolder() {

		return new TransformationPropertyValueModel<Integer, String>(buildDefaultCacheStatementsSizeHolder()) {

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

		WritablePropertyValueModel<Boolean> cacheStatementsHolder = buildCacheStatementsHolder();

		TriStateCheckBox checkBox = this.addTriStateCheckBox(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_cacheStatementsLabel,
			cacheStatementsHolder,
			null
		);

		Spinner spinner = this.addUnmanagedSpinner(
			container,
			this.buildCacheStatementsSizeHolder(),
			-1,
			-1,
			Integer.MAX_VALUE,
			null
		);

		Label label = this.addDefaultCacheStatementsSizeLabel(container);

		this.addLabeledComposite(
			container,
			checkBox.getCheckBox(),
			(spinner.getParent() == container) ? spinner : spinner.getParent(),
			label,
			null
//			EclipseLinkHelpContextIds.
		);

		this.installControlEnabler(cacheStatementsHolder, label, spinner);
		this.updateGridData(container, spinner);
	}

	private void installControlEnabler(WritablePropertyValueModel<Boolean> cacheStatementsHolder,
	                                   Label label,
	                                   Spinner spinner) {

		new ControlEnabler(cacheStatementsHolder, label, spinner);
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
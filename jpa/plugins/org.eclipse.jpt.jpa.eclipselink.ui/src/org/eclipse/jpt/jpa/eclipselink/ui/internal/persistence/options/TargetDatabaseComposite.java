/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.options;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringConverter;
import org.eclipse.jpt.common.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.options.Options;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.options.TargetDatabase;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import com.ibm.icu.text.Collator;

/**
 * TargetDatabaseComposite
 */
public class TargetDatabaseComposite extends Pane<Options>
{
	/**
	 * Creates a new <code>TargetDatabaseComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public TargetDatabaseComposite(
								Pane<? extends Options> parentPane,
	                           Composite parent) {

		super(parentPane, parent);
	}

	private PropertyValueModel<String> buildDefaultTargetDatabaseHolder() {
		return new PropertyAspectAdapter<Options, String>(this.getSubjectHolder(), Options.DEFAULT_TARGET_DATABASE) {
			@Override
			protected String buildValue_() {
				return TargetDatabaseComposite.this.getDefaultValue(subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultTargetDatabaseListHolder() {
		return new PropertyListValueModelAdapter<String>(
			this.buildDefaultTargetDatabaseHolder()
		);
	}

	private String buildDisplayString(String targetDatabaseName) {
		
		return SWTUtil.buildDisplayString(
			EclipseLinkUiMessages.class, 
			this.getClass(), 
			TargetDatabase.valueOf(targetDatabaseName));
	}

	private Comparator<String> buildTargetDatabaseComparator() {
		return new Comparator<String>() {
			public int compare(String targetDatabase1, String targetDatabase2) {
				targetDatabase1 = buildDisplayString(targetDatabase1);
				targetDatabase2 = buildDisplayString(targetDatabase2);
				return Collator.getInstance().compare(targetDatabase1, targetDatabase2);
			}
		};
	}

	private StringConverter<String> buildTargetDatabaseConverter() {
		return new StringConverter<String>() {
			public String convertToString(String value) {
				try {
					TargetDatabase.valueOf(value);
					value = buildDisplayString(value);
				}
				catch (Exception e) {
					// Ignore since the value is not a TargetDatabase
				}
				return value;
			}
		};
	}

	private WritablePropertyValueModel<String> buildTargetDatabaseHolder() {
		return new PropertyAspectAdapter<Options, String>(this.getSubjectHolder(), Options.TARGET_DATABASE_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = subject.getTargetDatabase();
				if (name == null) {
					name = TargetDatabaseComposite.this.getDefaultValue(subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getDefaultValue(subject).equals(value)) {
					value = null;
				}
				subject.setTargetDatabase(value);
			}
		};
	}

	private ListValueModel<String> buildTargetDatabaseListHolder() {
		ArrayList<ListValueModel<String>> holders = new ArrayList<ListValueModel<String>>(2);
		holders.add(buildDefaultTargetDatabaseListHolder());
		holders.add(buildTargetDatabasesListHolder());
		return new CompositeListValueModel<ListValueModel<String>, String>(holders);
	}

	private Iterator<String> buildTargetDatabases() {
		return new TransformationIterator<TargetDatabase, String>(CollectionTools.iterator(TargetDatabase.values())) {
			@Override
			protected String transform(TargetDatabase next) {
				return next.name();
			}
		};
	}

	private CollectionValueModel<String> buildTargetDatabasesCollectionHolder() {
		return new SimpleCollectionValueModel<String>(
			CollectionTools.collection(buildTargetDatabases())
		);
	}

	private ListValueModel<String> buildTargetDatabasesListHolder() {
		return new SortedListValueModelAdapter<String>(
			buildTargetDatabasesCollectionHolder(),
			buildTargetDatabaseComparator()
		);
	}

	private String getDefaultValue(Options subject) {
		String defaultValue = subject.getDefaultTargetDatabase();

		if (defaultValue != null) {
			return NLS.bind(
				JptCommonUiMessages.DefaultWithOneParam,
				defaultValue
			);
		}
		return JptCommonUiMessages.DefaultEmpty;
	}

	@Override
	protected void initializeLayout(Composite container) {

		Combo combo = addLabeledEditableCombo(
			container,
			EclipseLinkUiMessages.PersistenceXmlOptionsTab_targetDatabaseLabel,
			this.buildTargetDatabaseListHolder(),
			this.buildTargetDatabaseHolder(),
			this.buildTargetDatabaseConverter(),
			EclipseLinkHelpContextIds.PERSISTENCE_OPTIONS_TARGET_DATABASE
		);

		SWTUtil.attachDefaultValueHandler(combo);
	}
}

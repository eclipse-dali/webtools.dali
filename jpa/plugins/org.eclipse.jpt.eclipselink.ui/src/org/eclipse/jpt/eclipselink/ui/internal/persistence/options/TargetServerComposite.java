/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.options;

import com.ibm.icu.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import org.eclipse.jpt.eclipselink.core.context.persistence.options.Options;
import org.eclipse.jpt.eclipselink.core.context.persistence.options.TargetServer;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;

/**
 *  TargetServerComposite
 */
public class TargetServerComposite extends Pane<Options>
{
	/**
	 * Creates a new <code>TargetServerComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public TargetServerComposite(
								Pane<? extends Options> parentPane,
	                           Composite parent) {

		super(parentPane, parent);
	}

	private PropertyValueModel<String> buildDefaultTargetServerHolder() {
		return new PropertyAspectAdapter<Options, String>(this.getSubjectHolder(), Options.DEFAULT_TARGET_SERVER) {
			@Override
			protected String buildValue_() {
				return TargetServerComposite.this.getDefaultValue(subject);
			}
		};
	}

	private ListValueModel<String> buildDefaultTargetServerListHolder() {
		return new PropertyListValueModelAdapter<String>(
			this.buildDefaultTargetServerHolder()
		);
	}

	private String buildDisplayString(String targetServerName) {
		
		return SWTUtil.buildDisplayString(
			EclipseLinkUiMessages.class, 
			this.getClass(), 
			TargetServer.valueOf(targetServerName));
	}

	private Comparator<String> buildTargetServerComparator() {
		return new Comparator<String>() {
			public int compare(String targetServer1, String targetServer2) {
				targetServer1 = buildDisplayString(targetServer1);
				targetServer2 = buildDisplayString(targetServer2);
				return Collator.getInstance().compare(targetServer1, targetServer2);
			}
		};
	}

	private StringConverter<String> buildTargetServerConverter() {
		return new StringConverter<String>() {
			public String convertToString(String value) {
				try {
					TargetServer.valueOf(value);
					value = buildDisplayString(value);
				}
				catch (Exception e) {
					// Ignore since the value is not a TargetServer
				}
				return value;
			}
		};
	}

	private WritablePropertyValueModel<String> buildTargetServerHolder() {
		return new PropertyAspectAdapter<Options, String>(this.getSubjectHolder(), Options.TARGET_SERVER_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = subject.getTargetServer();
				if (name == null) {
					name = TargetServerComposite.this.getDefaultValue(subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getDefaultValue(subject).equals(value)) {
					value = null;
				}
				subject.setTargetServer(value);
			}
		};
	}

	private ListValueModel<String> buildTargetServerListHolder() {
		ArrayList<ListValueModel<String>> holders = new ArrayList<ListValueModel<String>>(2);
		holders.add(buildDefaultTargetServerListHolder());
		holders.add(buildTargetServersListHolder());
		return new CompositeListValueModel<ListValueModel<String>, String>(holders);
	}

	private Iterator<String> buildTargetServers() {
		return new TransformationIterator<TargetServer, String>(CollectionTools.iterator(TargetServer.values())) {
			@Override
			protected String transform(TargetServer next) {
				return next.name();
			}
		};
	}

	private CollectionValueModel<String> buildTargetServersCollectionHolder() {
		return new SimpleCollectionValueModel<String>(
			CollectionTools.collection(buildTargetServers())
		);
	}

	private ListValueModel<String> buildTargetServersListHolder() {
		return new SortedListValueModelAdapter<String>(
			buildTargetServersCollectionHolder(),
			buildTargetServerComparator()
		);
	}

	private String getDefaultValue(Options subject) {
		String defaultValue = subject.getDefaultTargetServer();

		if (defaultValue != null) {
			return NLS.bind(
				EclipseLinkUiMessages.PersistenceXmlOptionsTab_defaultWithOneParam,
				defaultValue
			);
		}
		else {
			return EclipseLinkUiMessages.PersistenceXmlOptionsTab_defaultEmpty;
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		CCombo combo = addLabeledEditableCCombo(
			container,
			EclipseLinkUiMessages.PersistenceXmlOptionsTab_targetServerLabel,
			this.buildTargetServerListHolder(),
			this.buildTargetServerHolder(),
			this.buildTargetServerConverter(),
			EclipseLinkHelpContextIds.PERSISTENCE_OPTIONS_TARGET_SERVER
		);

		SWTUtil.attachDefaultValueHandler(combo);
	}
}
